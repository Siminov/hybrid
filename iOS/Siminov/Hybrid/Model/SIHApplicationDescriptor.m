//
//  SIHApplicationDescriptor.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHApplicationDescriptor.h"

@implementation SIHApplicationDescriptor

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        adapterDescriptorPaths = [[NSMutableArray alloc] init];
        
        adapterDescriptorsBasedOnName = [[NSMutableDictionary alloc] init];
        adapterDescriptorsBasedOnPath = [[NSMutableDictionary alloc] init];
        
        return self;
    }
    
    return self;
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


- (void)addAdapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor {
    [adapterDescriptorsBasedOnName setValue:adapterDescriptor forKey:[adapterDescriptor getName]];
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


- (NSEnumerator *)getAdapterDescriptorPaths {
    return [adapterDescriptorPaths objectEnumerator];
}


- (void)addAdapterDescriptorPath:(NSString *)adapterDescriptorPath {
    [adapterDescriptorPaths addObject:adapterDescriptorPath];
}


- (bool)containAdapterDescriptorPath:(NSString *)adapterDescriptorPath {
    return [[adapterDescriptorsBasedOnPath allKeys] containsObject:adapterDescriptorPath];
}


- (void)removeAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName {
    
    NSEnumerator *adapterDescriptorPaths = [[adapterDescriptorsBasedOnPath allKeys] objectEnumerator];
    
    NSString *keyMatched;
    bool found = false;
    
    NSString *adapterPath;
    while(adapterPath = [adapterDescriptorPaths nextObject]) {
        
        SIHAdapterDescriptor *adapterDescriptor = [adapterDescriptorsBasedOnPath objectForKey:adapterPath];
        if([[adapterDescriptor getName] caseInsensitiveCompare:adapterDescriptorName] == NSOrderedSame) {
            keyMatched = adapterPath;
            found = true;
            break;
        }
    }
    
    if(found) {
        [self removeAdapterDescriptorBasedOnPath:keyMatched];
    } else {
        [adapterDescriptorsBasedOnName removeObjectForKey:adapterDescriptorName];
    }
    
}


- (void)removeAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath {
    SIHAdapterDescriptor *adapterDescriptor = [adapterDescriptorsBasedOnPath objectForKey:adapterDescriptorPath];
    
    [adapterDescriptorsBasedOnName removeObjectForKey:[adapterDescriptor getName]];
    [adapterDescriptorsBasedOnPath removeObjectForKey:adapterDescriptorPath];
    [adapterDescriptorPaths removeObject:adapterDescriptorPath];
}


- (void)removeAdapter:(SIHAdapterDescriptor *)adapterDescriptor {
    [self removeAdapterDescriptorBasedOnName:[adapterDescriptor getName]];
}

@end