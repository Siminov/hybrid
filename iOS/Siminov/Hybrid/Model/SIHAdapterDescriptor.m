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



#import "SIHAdapterDescriptor.h"

#import "SIHConstants.h"

@implementation SIHAdapterDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        handlers = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:ADAPTER_DESCRIPTOR_PROPERTY_NAME];
}

- (NSString *)getDescription {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION];
}

- (void)setDescription:(NSString *)description {
    [properties setValue:description forKey:ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION];
}

- (NSString *)getType {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_TYPE];
}

- (void)setType:(NSString *)type {
    [properties setObject:type forKey:ADAPTER_DESCRIPTOR_PROPERTY_TYPE];
}

- (NSString *)getMapTo {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO];
}

- (void)setMapTo:(NSString *)mapTo {
    [properties setObject:mapTo forKey:ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO];
}

- (bool)isCache {
    NSString *cache = [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_CACHE];
    
    if(cache != nil && [cache length] > 0 && [cache caseInsensitiveCompare:@"true"] == NSOrderedSame) {
        return true;
    }
    
    return false;
}

- (void)setCache:(bool)cache {
    [properties objectForKey:ADAPTER_DESCRIPTOR_PROPERTY_CACHE];
}

- (NSEnumerator *)getProperties {
    return [[properties allKeys] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allValues] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}


- (NSEnumerator *)getHandlers {
    return [[handlers allValues] objectEnumerator];
}

- (SIHHandler *)getHandler:(NSString *)handlerName {
    return [handlers objectForKey:handlerName];
}

- (void)addHandler:(SIHHandler *)handler {
    [handlers setValue:handler forKey:[handler getName]];
}

- (bool)containHandler:(NSString *)handlerName {
    return [[handlers allKeys] containsObject:handlerName];
}

@end



@implementation SIHHandler


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        parameters = [[NSMutableArray alloc] init];
        returnData = [[SIHReturn alloc] init];
        
        return self;
    }
    
    return self;
}



- (NSString *)getName {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME];
}

- (NSString *)getMapTo {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO];
}

- (void)setMapTo:(NSString *)mapTo {
    [properties setValue:mapTo forKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO];
}

- (NSString *)getDescription {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION];
}

- (void)setDescription:(NSString *)description {
    [properties setValue: description forKey:ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION];
}

- (NSEnumerator *)getProperties {
    return [[properties allValues] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

- (NSEnumerator *)getParameters {
    return [parameters objectEnumerator];
}

- (void)addParameter:(SIHParameter *)parameter {
    [parameters addObject:parameter];
}

- (SIHReturn *)getReturn {
    return returnData;
}

- (void)setReturn:(SIHReturn *)data {
    returnData = data;
}


@end


@implementation SIHParameter


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getName {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME];
}

- (void)setName:(NSString *)name {
    [properties setValue:name forKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME];
}

- (NSString *)getType {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE];
}

- (void)setType:(NSString *)type {
    [properties setValue:type forKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE];
}

- (NSString *)getDescription {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION];
}

- (void)setDescription:(NSString *)description {
    [properties setValue:description forKey:ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION];
}

- (NSEnumerator *)getProperties {
    return [[properties allValues] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}

- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}

- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

@end


@implementation SIHReturn

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        properties = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}

- (NSString *)getType {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE];
}


- (void)setType:(NSString *)type {
    [properties setValue:type forKey:ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE];
}


- (NSString *)getDescription {
    return [properties objectForKey:ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION];
}


- (void)setDescription:(NSString *)description {
    [properties setValue:description forKey:ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION];
}

- (NSEnumerator *)getProperties {
    return [[properties allValues] objectEnumerator];
}

- (NSString *)getProperty:(NSString *)name {
    return [properties objectForKey:name];
}

- (bool)containProperty:(NSString *)name {
    return [[properties allKeys] containsObject:name];
}


- (void)addProperty:(NSString *)name value:(NSString *)value {
    [properties setValue:value forKey:name];
}


- (void)removeProperty:(NSString *)name {
    [properties removeObjectForKey:name];
}

@end