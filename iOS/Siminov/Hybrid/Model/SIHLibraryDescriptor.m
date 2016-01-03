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


#import "SIHLibraryDescriptor.h"

@implementation SIHLibraryDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        adapterDescriptorPaths = [[NSMutableArray alloc] init];
        
        adapterDescriptorsBasedOnPath = [[NSMutableDictionary alloc] init];
        adapterDescriptorsBasedOnName = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSEnumerator *)getAdapterDescriptorPaths {
    return [adapterDescriptorPaths objectEnumerator];
}


- (NSEnumerator *)getAdapterDescriptors {
    return [[adapterDescriptorsBasedOnName allValues] objectEnumerator];
}


- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName {
    return [adapterDescriptorsBasedOnName objectForKey:adapterDescriptorName];
}


- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath {
    return [adapterDescriptorsBasedOnPath objectForKey:adapterDescriptorPath];
}


- (void)addAdapterDescriptorPath:(NSString *)adapterDescriptorPath {
    [adapterDescriptorPaths addObject:adapterDescriptorPath];
}


- (void)addAdapterDescriptor:(NSString *)adapterDescriptorPath adapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor {
    [adapterDescriptorsBasedOnPath setValue:adapterDescriptor forKey:adapterDescriptorPath];
    [adapterDescriptorsBasedOnName setValue:adapterDescriptor forKey:[adapterDescriptor getName]];
}


- (bool)containAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath {
    return [[adapterDescriptorsBasedOnPath allKeys] containsObject:adapterDescriptorPath];
}


- (bool)containAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName {
    return [[adapterDescriptorsBasedOnName allKeys] containsObject:adapterDescriptorName];
}


- (void)removeAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName {
    NSEnumerator *libraryPaths = [ [adapterDescriptorsBasedOnPath allKeys] objectEnumerator];
    
    bool found = false;
    NSString *keyMatched = nil;
    
    NSString *libraryPath;
    while(libraryPath = [libraryPaths nextObject]) {
        
        SIHAdapterDescriptor *adapterDescriptor = [adapterDescriptorsBasedOnPath objectForKey:libraryPath];
        if([[adapterDescriptor getName] caseInsensitiveCompare:adapterDescriptorName] == NSOrderedSame) {
            keyMatched = libraryPath;
            found = true;
            break;
        }
    }
    
    if(found) {
        [self removeAdapterBasedOnPath:keyMatched];
    }
}


- (void)removeAdapterBasedOnPath:(NSString *)adapterPath {
    SIHAdapterDescriptor *adapaterDescriptor = [adapterDescriptorsBasedOnPath objectForKey:adapterPath];
    
    [adapterDescriptorsBasedOnName removeObjectForKey:[adapaterDescriptor getName]];
    [adapterDescriptorsBasedOnPath removeObjectForKey:adapterPath];
}


- (void)removeAdapater:(SIHAdapterDescriptor *)adapterDescriptor {
    [self removeAdapterDescriptorBasedOnName:[adapterDescriptor getName]];
}


@end
