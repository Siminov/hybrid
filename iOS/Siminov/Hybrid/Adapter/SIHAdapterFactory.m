//
//  SIHAdapterFactory.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <objc/runtime.h>
#import "SIHAdapterFactory.h"
#import "SIHAdapterDescriptor.h"
#import "SICClassUtils.h"

#import "SIHResourceManager.h"


@implementation SIHAdapterFactory


static NSMutableDictionary *adapters;
static NSMutableDictionary *handlers;

static SIHResourceManager *resourceManager;

static SIHAdapterFactory *adapterFactory;


+ (void)initialize {
    adapters = [[NSMutableDictionary alloc] init];
    handlers = [[NSMutableDictionary alloc] init];
    
    resourceManager = [SIHResourceManager getInstance];
}

+ (SIHAdapterFactory *)getInstance {
    
    if(!adapterFactory) {
        adapterFactory = [[super allocWithZone:NULL] init];
    }
    return adapterFactory;
}




- (id<SIHIAdapter>)getAdapterInstance:(NSString *)adapterDescriptorName {
    SIHAdapterDescriptor *adapterDescriptor = [resourceManager getAdapterDescriptor:adapterDescriptorName];
    NSString *mapTo = [adapterDescriptor getMapTo];
    
    return (id<SIHIAdapter>)[SICClassUtils createClassInstance:mapTo];
}


- (id<SIHIAdapter>)requireAdapterInstance:(NSString *)adapterName {
    bool contain = [[adapters allKeys] containsObject:adapterName];
    if(contain) {
        return [adapters objectForKey:adapterName];
    }
    
    id<SIHIAdapter> adapterObject = [self getAdapterInstance:adapterName];
    [adapters setValue:adapterObject forKey:adapterName];
    
    return adapterObject;
}



- (Method)getHandlerInstance:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName handlerParameterTypes:(NSEnumerator *)handlerParameterTypes {
    
    SIHAdapterDescriptor *adapterDescriptor = [resourceManager getAdapterDescriptor:adapterDescriptorName];
    SIHHandler *handler = [adapterDescriptor getHandler:handlerName];
    
    return [SICClassUtils createMethodBasedOnClassName:[adapterDescriptor getMapTo] methodName:[handler getMapTo] parameterTypes:handlerParameterTypes];
}



- (Method)requireHandlerInstance:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName handlerParameterTypes:(NSEnumerator *)handlerParameterTypes {
    
    bool contain = [[handlers allKeys] containsObject:handlerName];
    if(contain) {
        Method method = [[handlers objectForKey:handlerName] pointerValue];
        return method;
    }
    
    Method handler = [self getHandlerInstance:adapterDescriptorName handlerName:handlerName handlerParameterTypes:handlerParameterTypes];
    [handlers setObject:[NSValue valueWithPointer:handler] forKey:handlerName];
    
    return handler;
}

@end
