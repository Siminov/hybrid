///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

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
