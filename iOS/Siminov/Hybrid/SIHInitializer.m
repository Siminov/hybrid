//
//  SIHInitializer.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHInitializer.h"

#import "SICResourceManager.h"
#import "SIHSiminov.h"
#import "SIHResourceManager.h"

@implementation SIHInitializer

static SICResourceManager *coreResourceManager = nil;
static SIHResourceManager *hybridResourceManager = nil;

-(id)init {
    
    self = [super init];
    
    if(self) {
        coreResourceManager = [SICResourceManager getInstance];
        hybridResourceManager = [SIHResourceManager getInstance];
        
        parameters = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}

- (void)addParameter:(id)object {
    [parameters addObject:object];
}

- (void)initialize {
    
    for(int i = 0;i < [parameters count];i++) {
        
        id parameter = [parameters objectAtIndex:i];
        if([parameter isKindOfClass:[UIWebView class]]) {
            [hybridResourceManager setWebView:parameter];
        }
    }
    
    [SIHSiminov start];
}

@end
