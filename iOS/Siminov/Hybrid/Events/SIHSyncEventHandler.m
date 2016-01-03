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


#import "SIHSyncEventHandler.h"

#import "SIHHybridEventHandler.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHAdapter.h"
#import "SIHConstants.h"


@implementation SIHSyncEventHandler

- (id)init {
    
    self = [super init];
    
    if(self) {
        
        hybridResourceManager = [SIHResourceManager getInstance];
        eventHandler = [SIHEventHandler getInstance];
        
        return self;
    }
    
    return self;
}

- (void)onStart:(id<SIKISyncRequest>)syncRequest {
    
    id<SIKISyncEvents> syncEvents = [eventHandler getSyncEvent];
    if(syncEvents != nil) {
        [syncEvents onStart:syncRequest];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Request Id
    SIHHybridSiminovData *requestId = [[SIHHybridSiminovData alloc] init];
    
    [requestId setDataType:HYBRID_EVENT_HANDLER_REQUEST_ID];
    [requestId setDataValue:[NSString stringWithFormat:@"%ld", [syncRequest getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:requestId];
    
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_STARTED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Event
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        NSUInteger startLocation = [event rangeOfString:@"."].location;
        if(startLocation <= -1 || startLocation == NSNotFound) {
            startLocation = 0;
        }
        
        NSRange eventRange = NSMakeRange(startLocation, [event length] - startLocation);
        event = [event substringWithRange:eventRange];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    
    //Parameters
    SIHHybridSiminovData *hybridSyncRequest = [hybridResourceManager generateHybridSyncRequest:syncRequest];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridSyncRequest];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onSyncStarted" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onQueue:(id<SIKISyncRequest>)syncRequest {
    
    id<SIKISyncEvents> syncEvents = [eventHandler getSyncEvent];
    if(syncEvents != nil) {
        [syncEvents onQueue:syncRequest];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_QUEUED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Event
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        NSUInteger startLocation = [event rangeOfString:@"."].location;
        if(startLocation <= -1 || startLocation == NSNotFound) {
            startLocation = 0;
        }
        
        NSRange eventRange = NSMakeRange(startLocation, [event length] - startLocation);
        event = [event substringWithRange:eventRange];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    
    //Parameters
    SIHHybridSiminovData *hybridSyncRequest = [hybridResourceManager generateHybridSyncRequest:syncRequest];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridSyncRequest];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onSyncQueued" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onFinish:(id<SIKISyncRequest>)syncRequest {
    
    id<SIKISyncEvents> syncEvents = [eventHandler getSyncEvent];
    if(syncEvents != nil) {
        [syncEvents onFinish:syncRequest];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_REMOVED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Event
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        NSUInteger startLocation = [event rangeOfString:@"."].location;
        if(startLocation <= -1 || startLocation == NSNotFound) {
            startLocation = 0;
        }
        
        NSRange eventRange = NSMakeRange(startLocation, [event length] - startLocation);
        event = [event substringWithRange:eventRange];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    
    //Parameters
    SIHHybridSiminovData *hybridSyncRequest = [hybridResourceManager generateHybridSyncRequest:syncRequest];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridSyncRequest];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onSyncRemoved" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onTerminate:(id<SIKISyncRequest>)syncRequest {
    
    id<SIKISyncEvents> syncEvents = [eventHandler getSyncEvent];
    if(syncEvents != nil) {
        [syncEvents onTerminate:syncRequest];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_TERMINATED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Event
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        NSUInteger startLocation = [event rangeOfString:@"."].location;
        if(startLocation <= -1 || startLocation == NSNotFound) {
            startLocation = 0;
        }
        
        NSRange eventRange = NSMakeRange(startLocation, [event length] - startLocation);
        event = [event substringWithRange:eventRange];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    
    //Parameters
    SIHHybridSiminovData *hybridSyncRequest = [hybridResourceManager generateHybridSyncRequest:syncRequest];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridSyncRequest];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onSyncTerminated" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}

@end
