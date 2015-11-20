//
//  SIHAdapterDescriptorReader.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHAdapterDescriptorReader.h"

#import "SIHConstants.h"
#import "SICFileUtils.h"


@implementation SIHAdapterDescriptorReader

NSString *adapterDescriptorPath;

- (id)initWithPath:(NSString * const)adapterdescriptorpath {
    
    if (self = [super init]) {
        
        adapterDescriptor = [[SIHAdapterDescriptor alloc]init];
        
        if (adapterdescriptorpath == nil || [adapterdescriptorpath length] <= 0) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:@"Invalid Adapter Descriptor path found."];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:@"Invalid Adapter Descriptor path found."];
        }
        
        adapterDescriptorPath = adapterdescriptorpath;
        
        NSData *adapterDescriptorStream = nil;
        
        @try {
            
            NSString *adapterDescriptorPathName;
            
            if ([adapterDescriptorPath hasSuffix:FILE_TYPE]) {
                
                NSUInteger index = 0;
                NSRange range = [adapterDescriptorPath rangeOfString:FILE_TYPE];
                if (range.length == 0 && range.location > adapterDescriptorPath.length) {
                    index = 0;
                } else {
                    index = range.location;
                }
                
                adapterDescriptorPathName = [adapterDescriptorPath substringToIndex:index];
            }
            
            NSString *filePath = [[[SICFileUtils alloc]init] getFilePath:adapterdescriptorpath inDirectory:DIRECTORY_NAME];
            adapterDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
            
            if(adapterDescriptorStream == nil) {
                
                NSUInteger index = 0;
                NSRange range = [adapterDescriptorPathName rangeOfString:LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR options:NSBackwardsSearch];
                if (range.length == 0 && range.location > adapterDescriptorPathName.length) {
                    index = 0;
                } else {
                    index = range.location;
                }
                
                NSMutableArray *directoryParts = [NSMutableArray arrayWithArray:[adapterDescriptorPathName componentsSeparatedByString:@"."]];
                NSString *filename = [directoryParts lastObject];
                
                [directoryParts removeLastObject];
                
                NSString *directoryPartsJoined = [directoryParts componentsJoinedByString:@"."];
                adapterDescriptorPathName = [NSString stringWithFormat:@"%@/%@", directoryPartsJoined, filename];
                
                NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:adapterDescriptorPathName inDirectory:@"include"];
                adapterDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
            }
            
        }
        @catch (NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while getting database descriptor file stream, %@", [exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[exception reason]];
        }
        
        @try {
            [self parseMessage:adapterDescriptorStream];
        }
        @catch (NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing DATABASE-DESCRIPTOR: %@, %@", adapterDescriptorPath, [exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing DATABASE-DESCRIPTOR: %@, %@", adapterDescriptorPath,[exception reason]]];
        }
        
    }
    
    return self;
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    tempValue = [[NSMutableString alloc] init];
    
    if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [self initializeProperty:attributeDict];
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_HANDLER] == NSOrderedSame) {
        
        handler = [[SIHHandler alloc] init];
        isHandler = true;
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER] == NSOrderedSame) {
        
        parameter = [[SIHParameter alloc] init];
        isParameter = true;
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_RETURN] == NSOrderedSame) {
        
        returnData = [[SIHReturn alloc] init];
        isReturn = true;
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [tempValue appendString:(NSMutableString *)[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {
    
    if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [self processProperty];
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_HANDLER] == NSOrderedSame) {
        
        [adapterDescriptor addHandler:handler];
        
        handler = nil;
        isHandler = false;
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER] == NSOrderedSame) {
        
        [handler addParameter:parameter];
        
        parameter = nil;
        isParameter = false;
    } else if([elementName caseInsensitiveCompare:ADAPTER_DESCRIPTOR_RETURN] == NSOrderedSame) {
        
        [handler setReturn:returnData];
        
        returnData = nil;
        isReturn = false;
    }
}

- (void)initializeProperty:(NSDictionary *)attributes {
    propertyName = [attributes objectForKey:APPLICATION_DESCRIPTOR_NAME];
}

- (void)processProperty {
    
    if(isReturn) {
        [returnData addProperty:propertyName value:(NSString *)tempValue];
    } else if(isParameter) {
        [parameter addProperty:propertyName value:(NSString *)tempValue];
    } else if(isHandler) {
        [handler addProperty:propertyName value:(NSString *)tempValue];
    } else {
        [adapterDescriptor addProperty:propertyName value:(NSString *)tempValue];
    }
}

-(SIHAdapterDescriptor *)getAdapterDescriptor {
    return adapterDescriptor;
}


@end
