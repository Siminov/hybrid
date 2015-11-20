//
//  SIHAdapter.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHAdapter.h"

#import "SIHIHandler.h"

@implementation SIHAdapter


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        adapterHandler = [[SIHAdapterHandler alloc] init];
        parameters = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getAdapterName {
    return adapterName;
}


- (void)setAdapterName:(NSString *)adaptername {
    adapterName = adaptername;
}


- (NSString *)getHandlerName {
    return handlerName;
}


- (void)setHandlerName:(NSString *)handlername {
    handlerName = handlername;
}


- (NSEnumerator *)getParameters {
    return [parameters objectEnumerator];
}


- (void)addParameter:(NSString *)parameter {
    [parameters addObject:parameter];
}



- (void)invoke {
    
    id<SIHIHandler> handler = [adapterHandler getHandler];
    
    if(handlerName == nil && [handlerName length] <= 0) {
        [handler handleNativeToHybrid:adapterName data:parameters];
    } else {
        [handler handleNativeToHybrid:[NSString stringWithFormat:@"%@.%@", adapterName, handlerName] data:parameters];
    }
}


@end