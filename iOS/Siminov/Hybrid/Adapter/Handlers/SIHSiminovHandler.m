//
//  SIHSiminovHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <objc/runtime.h>
#import "SIHSiminovHandler.h"

#import "SIHHybridSiminovDataReader.h"
#import "SICSiminovException.h"
#import "SIHConstants.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHHybridSiminovException.h"
#import "SIHDataTypeHandler.h"
#import "SIHIHandler.h"


@implementation SIHSiminovHandler

- (id)init {
    self = [super init];
    
    if(self) {
        coreResourceManager = [SICResourceManager getInstance];
        hybridResourceManager = [SIHResourceManager getInstance];
        
        adapterResources = [SIHAdapterFactory getInstance];
        
        return self;
    }
    
    return self;
}


- (NSString *)handleHybridToNative:(NSString *)action {
    return [self handleHybridToNative:action data:nil];
}


- (NSString *)handleHybridToNative:(NSString *)action data:(NSString *)data {
    return [self processHandler:action data:data];
}


- (NSString *)handleHybridToNativeAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data {
    
    NSLog([NSString stringWithFormat:@"handleHybridToNativeAsync:action:data, %@", action], __PRETTY_FUNCTION__);
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [self processHandlerAsync:requestId action:action data:data];
    });
    
    return [self generateHybridSiminovEmptyData];
}

- (void)processHandlerAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data {
    
    NSLog([NSString stringWithFormat:@"processHandlerAsync before, %@", action], __PRETTY_FUNCTION__);

    NSString *response = [self processHandler:action data:data];
    //NSString *response = [self generateHybridSiminovEmptyData];
    NSMutableArray *responseData = [[NSMutableArray alloc] init];
    [responseData addObject:response];
    
    NSLog([NSString stringWithFormat:@"processHandlerAsync after, %@", action], __PRETTY_FUNCTION__);
    [self handleNativeToHybridAsync:requestId data:responseData];
}


- (NSString *)processHandler:(NSString *)action data:(NSString *)data {
    
    SIHHybridSiminovDataReader *hybridSiminovDataParser = nil;
    @try {
        hybridSiminovDataParser = [[SIHHybridSiminovDataReader alloc] initWithData:data];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"handleHybridToNative" message:[NSString stringWithFormat:@"SiminovException caught while parsing siminov hybrid data, %@", [siminovException getMessage]]];
    }
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [hybridSiminovDataParser getDatas];
    NSMutableArray *parameterValues = [[NSMutableArray alloc] init];
    
    NSEnumerator *hybridSiminovData = [hybridSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridData;
    
    while(hybridData = [hybridSiminovData nextObject]) {
        
        NSString *dataValue = [hybridData getDataValue];
        if(dataValue == nil) {
            dataValue = @"";
        }
        
        [parameterValues addObject:dataValue];
    }
    
    
    NSRange adapterRange = NSMakeRange(0, [action rangeOfString:@"."].location);
    NSString *adapterDescriptorName = [action substringWithRange:adapterRange];
    
    NSRange handlerRange = NSMakeRange([action rangeOfString:@"."].location + 1, [action length] - [action rangeOfString:@"."].location - 1);
    NSString *handlerName = [action substringWithRange:handlerRange];
    
    SIHAdapterDescriptor *adapterDescriptor = [hybridResourceManager getAdapterDescriptor:adapterDescriptorName];
    SIHHandler *handler = [hybridResourceManager getHandler:adapterDescriptorName handlerName:handlerName];
    
    NSEnumerator *parameterTypes = [self getParameterTypes:[handler getParameters]];
    NSMutableArray *parameterObjects = nil;
    @try {
        parameterObjects = [self createAndInflateParameter:parameterTypes values:[parameterValues objectEnumerator]];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"SiminovException caught while create and inflate parameters, %@", [siminovException getMessage]]];
    }
    
    
    id adapterInstanceObject = nil;
    Method handlerInstanceObject = nil;
    
    bool cache = [adapterDescriptor isCache];
    if(cache) {
        adapterInstanceObject = [adapterResources requireAdapterInstance:adapterDescriptorName];
        handlerInstanceObject = [adapterResources requireHandlerInstance:adapterDescriptorName handlerName:handlerName handlerParameterTypes:parameterTypes];
    } else {
        adapterInstanceObject = [adapterResources getAdapterInstance:adapterDescriptorName];
        handlerInstanceObject = (Method) [adapterResources getHandlerInstance:adapterDescriptorName handlerName:handlerName handlerParameterTypes:parameterTypes];
    }
    
    
    id returnData = nil;
    
    @try {
        returnData = [SICClassUtils invokeMethodBasedOnMethod:adapterInstanceObject method:handlerInstanceObject parameters:parameterObjects];
    } @catch(NSException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"processHandler" message:[NSString stringWithFormat:@"SiminovException caught while invoking handler, %@", [siminovException reason]]];
        
        return [self generateHybridSiminovException:NSStringFromClass([self class]) methodName:@"processHandler" message:[NSString stringWithFormat:@"SiminovException caught while invoking handler, %@", [siminovException reason]]];
    }
    
    if(returnData != nil) {
        return (NSString *)returnData;
    }
    
    return [self generateHybridSiminovEmptyData];
    
}


- (void)handleNativeToHybridAsync:(NSString *)requestId data:(NSArray *)data {
    NSLog([NSString stringWithFormat:@"handleNativeToHybridAsync start, %@", requestId], __PRETTY_FUNCTION__);
    
    SIHAdapterDescriptor *adapterDescriptor = [hybridResourceManager getAdapterDescriptor:NATIVE_TO_HYBRID_ADAPTER];
    SIHHandler *handler = [hybridResourceManager getHandler:NATIVE_TO_HYBRID_ADAPTER handlerName:NATIVE_TO_HYBRID_ADAPTER_ASYNC_HANDLER];
    
    NSString *parameters = @"";
    if(data != nil && [data count] > 0) {
        for(int i = 0; i < [data count]; i++) {
            if(i == 0) {
                parameters = [NSString stringWithFormat:@"%@ '%@'", parameters, [data objectAtIndex:i]];
                continue;
            }
            
            parameters = [NSString stringWithFormat:@"%@, '%@'", parameters, [data objectAtIndex:i]];
        }
        
        NSArray* parametersArray = [parameters componentsSeparatedByCharactersInSet :[NSCharacterSet whitespaceAndNewlineCharacterSet]];
        parameters = [parametersArray componentsJoinedByString:@""];
    }
    
    NSLog([NSString stringWithFormat:@"handleNativeToHybridAsync end, %@", requestId], __PRETTY_FUNCTION__);
    
    [self handleNativeToHybrid:[adapterDescriptor getMapTo] apiName:[handler getMapTo] action:requestId parameters:parameters];
}



- (void)handleNativeToHybrid:(NSString *)action data:(NSArray *)data {
    
    
    SIHAdapterDescriptor *adapterDescriptor = [hybridResourceManager getAdapterDescriptor:NATIVE_TO_HYBRID_ADAPTER];
    SIHHandler *handler = [hybridResourceManager getHandler:NATIVE_TO_HYBRID_ADAPTER handlerName:NATIVE_TO_HYBRID_ADAPTER_HANDLER];
    
    NSString *parameters = @"";
    if(data != nil) {
        
        for(int i = 0; i < [data count]; i++) {
            if(i == 0) {
                parameters = [NSString stringWithFormat:@"'%@'", [data objectAtIndex:i]];
                continue;
            }
            
            parameters = [NSString stringWithFormat:@"%@, '%@'", parameters, [data objectAtIndex:i]];
        }
        
        NSArray* parametersArray = [parameters componentsSeparatedByCharactersInSet :[NSCharacterSet whitespaceAndNewlineCharacterSet]];
        parameters = [parametersArray componentsJoinedByString:@""];
    }
    
    NSString *adapterDescriptorName = @"";
    NSString *handlerName = @"";
    
    
    int indexOfHandler = (int) [action rangeOfString:@"."].location;
    if(indexOfHandler > 0) {
        adapterDescriptorName = [action substringWithRange:NSMakeRange(0, indexOfHandler)];
        
        handlerName = [action substringWithRange:NSMakeRange([action rangeOfString:@"."].location + 1, [action length] - [action rangeOfString:@"."].location - 1)];
    } else {
        adapterDescriptorName = action;
    }
    
    NSString *invokeAction = @"";
    
    if([hybridResourceManager containAdapterBasedOnName:adapterDescriptorName]) {
        SIHAdapterDescriptor *invokeAdapterDescriptor = [hybridResourceManager getAdapterDescriptor:adapterDescriptorName];
        invokeAction = [invokeAdapterDescriptor getMapTo];
    }
    
    if(handlerName != nil && [handlerName length] > 0 && [hybridResourceManager containHandler:handlerName]) {
        SIHHandler *invokeHandler = [hybridResourceManager getHandler:adapterDescriptorName handlerName:handlerName];
        invokeAction = [NSString stringWithFormat:@"%@.%@", invokeAction, [invokeHandler getMapTo]];
    }
    
    
    [self handleNativeToHybrid:[adapterDescriptor getMapTo] apiName:[handler getMapTo] action:invokeAction parameters:parameters];
}

- (void)handleNativeToHybrid:(NSString *)functionName apiName:(NSString *)apiName action:(NSString *)action parameters:(NSString *)parameters {
    NSLog([NSString stringWithFormat:@"handleNativeToHybrid start, %@", action], __PRETTY_FUNCTION__);
    
    UIWebView *webView = [hybridResourceManager getWebView];
    id<SIHIHandler> handler = [hybridResourceManager getInterceptor];
    
    dispatch_async(dispatch_get_main_queue(), ^{
        
        if(functionName != nil && [functionName length] > 0 && apiName != nil && [apiName length] > 0) {
            
            if(handler) {
                [handler handleNativeToHybrid:functionName apiName:apiName action:action parameters:parameters];
            } else {
                NSString *invokeFunction = [NSString stringWithFormat:@"javascript:%@.%@('%@', %@);", functionName, apiName, action, parameters];
                
                [webView stringByEvaluatingJavaScriptFromString:invokeFunction];
            }
        } else if(functionName != nil && [functionName length] > 0) {
            
            if(handler) {
                [handler handleNativeToHybrid:functionName apiName:apiName action:action parameters:parameters];
            } else {
                NSString *invokeFunction = [NSString stringWithFormat:@"javascript:%@('%@', %@);", functionName, action, parameters];
                
                [webView stringByEvaluatingJavaScriptFromString:invokeFunction];
            }
        } else if(functionName != nil && [apiName length] > 0) {
            
            if(handler) {
                [handler handleNativeToHybrid:functionName apiName:apiName action:action parameters:parameters];
            } else {
                NSString *invokeFunction = [NSString stringWithFormat:@"javascript:%@('%@', %@);", functionName, action, parameters];
                
                [webView stringByEvaluatingJavaScriptFromString:invokeFunction];
            }
        }
    });
}



- (NSMutableArray *)createAndInflateParameter:(NSEnumerator *)parameterTypes values:(NSEnumerator *)parameterValues {
    
    NSMutableArray *parameters = [[NSMutableArray alloc] init];
    
    id parameterType;
    while(parameterType = [parameterTypes nextObject]) {
        
        NSString *parameterValue = [parameterValues nextObject];
        if(parameterValue == nil) {
            [parameters addObject:[NSNull null]];
            continue;
        }
        
        id parameter;
        
        const char *parameterTypeClassNameChars = class_getName([parameterType class]);
        NSString *parameterTypeClassName = [[NSString alloc] initWithUTF8String:parameterTypeClassNameChars];
        
        if([parameterTypeClassName caseInsensitiveCompare:[[NSString class] description]] == NSOrderedSame) {
            
            if(parameterValue == nil || [parameterValue caseInsensitiveCompare:HYBRID_UNDEFINED] == NSOrderedSame) {
                parameterValue = [[NSString alloc] init];
            }
            
            parameter = parameterValue;
            /*} else if([parameterType isKindOfClass:[int class]) {
             parameter = Integer.getInteger(parameterValue);
             } else if(long.class == parameterType) {
             parameter = Long.getLong(parameterValue);
             } else if(float.class == parameterType) {
             parameter = Float.parseFloat(parameterValue);
             } else if(double.class == parameterType) {
             parameter = Double.parseDouble(parameterValue);
             */} else if([parameterTypeClassName caseInsensitiveCompare:[[NSArray class] description]] == NSOrderedSame) {
                 
                 parameter = [parameterValue componentsSeparatedByString:@","];
             } else if([parameterTypeClassName caseInsensitiveCompare:[[NSEnumerator class] description]] == NSOrderedSame) {
                 
                 NSArray *parameterSplit = [parameterValue componentsSeparatedByString:@","];
                 parameter = [parameterSplit objectEnumerator];
             } /*else if(int[].class == parameterType) {
                
                StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
                parameter = Array.newInstance(int.class, dataValues.countTokens());
                
                int index = 0;
                while(dataValues.hasMoreElements()) {
                Array.set(parameter, index++, Integer.getInteger((String) dataValues.nextElement()));
                }
                
                } else if(long.class == parameterType || Long.class == parameterType) {
                
                StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
                parameter = Array.newInstance(long.class, dataValues.countTokens());
                
                int index = 0;
                while(dataValues.hasMoreElements()) {
                Array.set(parameter, index++, Long.getLong((String) dataValues.nextElement()));
                }
                
                } else if(float.class == parameterType || Float.class == parameterType) {
                
                StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
                parameter = Array.newInstance(float.class, dataValues.countTokens());
                
                int index = 0;
                while(dataValues.hasMoreElements()) {
                Array.set(parameter, index++, Float.parseFloat((String) dataValues.nextElement()));
                }
                
                } else if(double.class == parameterType || Double.class == parameterType) {
                
                StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
                parameter = Array.newInstance(int.class, dataValues.countTokens());
                
                int index = 0;
                while(dataValues.hasMoreElements()) {
                Array.set(parameter, index++, Double.parseDouble((String) dataValues.nextElement()));
                }
                
                }*/
        
            if(parameter) {
                [parameters addObject:parameter];
            }
    }
    
    
    return parameters;
}

- (NSEnumerator *)getParameterTypes:(NSEnumerator *)parameters {
    
    NSMutableArray *parameterTypes = [[NSMutableArray alloc] init];
    SIHParameter *parameter;
    
    while(parameter = [parameters nextObject]) {
        [parameterTypes addObject:[SICClassUtils createClass:[SIHDataTypeHandler convert:[parameter getType]]]];
    }
    
    NSMutableArray *parameterTypeArray = [[NSMutableArray alloc] init];
    for(int i = 0;i < [parameterTypes count];i++) {
        [parameterTypeArray addObject:[parameterTypes objectAtIndex:i]];
    }
    
    return [parameterTypeArray objectEnumerator];
}


- (NSString *)generateHybridSiminovEmptyData {
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *data = nil;
    
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"generateHybridSiminovException" message:[NSString stringWithFormat:@"SiminovException caught while generating empty siminov js data: %@", [siminovException getMessage]]];
        
        return @"{\"datas\":{}}";
    }
    
    return data;
}

- (NSString *)generateHybridSiminovException:(NSString *)className methodName:(NSString *)methodName message:(NSString *)message {
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *exception = [[SIHHybridSiminovData alloc] init];
    
    [exception setDataType:HYBRID_SIMINOV_EXCEPTION_SIMINOV_EXCEPTION];
    
    SIHHybridSiminovValue *classNameValue = [[SIHHybridSiminovValue alloc] init];
    [classNameValue setType:HYBRID_SIMINOV_EXCEPTION_CLASS_NAME];
    [classNameValue setValue:className];
    
    [exception addValue:classNameValue];
    
    
    SIHHybridSiminovValue *methodNameValue = [[SIHHybridSiminovValue alloc] init];
    [methodNameValue setType:HYBRID_SIMINOV_EXCEPTION_METHOD_NAME];
    [methodNameValue setValue:methodName];
    
    [exception addValue:methodNameValue];
    
    
    SIHHybridSiminovValue *messageValue = [[SIHHybridSiminovValue alloc] init];
    [messageValue setType:HYBRID_SIMINOV_EXCEPTION_MESSAGE];
    [messageValue setValue:message];
    
    [exception addValue:messageValue];
    
    
    [hybridSiminovDatas addHybridSiminovData:exception];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"generateHybridSiminovException" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}


- (void)initializeSiminov {
    
    [SIKSiminov processDatabase];
    [SIHSiminov siminovInitialized];
}


- (void)shutdownSiminov {
    
    [SIHSiminov shutdown];
}


@end
