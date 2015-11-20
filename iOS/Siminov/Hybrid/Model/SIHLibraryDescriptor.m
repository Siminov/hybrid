//
//  SIHLibraryDescriptor.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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
