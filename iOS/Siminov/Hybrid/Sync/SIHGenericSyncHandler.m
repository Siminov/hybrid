//
//  SIHGenericSyncHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHGenericSyncHandler.h"
#import "SIHSyncWorker.h"


@implementation SIHGenericSyncHandler

static SIHGenericSyncHandler *genericSyncHandler;

static SIKResourceManager *connectResourceManager;

static SIHSyncWorker *syncWorker;
static NSMutableDictionary *requestTimestamps;


+ (void)initialize {
    connectResourceManager = [SIKResourceManager getInstance];
    
    requestTimestamps = [[NSMutableDictionary alloc] init];
    syncWorker = [SIHSyncWorker getInstance];
}

+ (SIHGenericSyncHandler *)getInstance {
    
    if(!genericSyncHandler) {
        genericSyncHandler = [[super allocWithZone:NULL] init];
    }
    return genericSyncHandler;
}


- (void)handle:(id<SIKISyncRequest>)syncRequest {
    
    SIKSyncDescriptor *syncDescriptor = [connectResourceManager getSyncDescriptor:[syncRequest getName]];
    
    NSNumber *requestTimestamp = [requestTimestamps objectForKey:syncRequest];
    if(requestTimestamp <= 0) {
        [syncWorker addRequest:syncRequest];
        [requestTimestamps setValue:[NSNumber numberWithLongLong:[[NSDate date] timeIntervalSince1970] * 1000] forKey:[syncRequest getName]];
        
        return;
    }
    
    
    NSNumber *syncInterval = [NSNumber numberWithLongLong:[syncDescriptor getSyncInterval]];
    NSNumber *lastRefreshTimestamp = [requestTimestamps objectForKey:[syncRequest getName]];
    NSNumber *currentTimestamp = [NSNumber numberWithLongLong:[[NSDate date] timeIntervalSince1970] * 1000];
    
    long timeDifference = [lastRefreshTimestamp longLongValue] + [syncInterval longLongValue];
    
    if(timeDifference < [currentTimestamp longLongValue]) {
        [syncWorker addRequest:syncRequest];
        [requestTimestamps setValue:[NSNumber numberWithLongLong:[[NSDate date] timeIntervalSince1970] * 1000] forKey:[syncRequest getName]];
    }
}


@end
