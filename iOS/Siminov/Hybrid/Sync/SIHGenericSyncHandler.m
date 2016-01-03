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
