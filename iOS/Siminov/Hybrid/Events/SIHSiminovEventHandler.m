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


#import "SIHSiminovEventHandler.h"

#import "SIHHybridEventHandler.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHConstants.h"
#import "SIHAdapter.h"
#import "SIHHybridEventHandler.h"

@implementation SIHSiminovEventHandler


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        hybridResourceManager = [SIHResourceManager getInstance];
        eventHandler = [SIHEventHandler getInstance];
        
        return self;
    }
    
    return self;
}

- (void)onFirstTimeSiminovInitialized {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICISiminovEvents> siminovEvents = [eventHandler getSiminovEvent];
    if(siminovEvents != nil) {
        [siminovEvents onFirstTimeSiminovInitialized];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
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
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"firstTimeSiminovInitialized" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onSiminovInitialized {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICISiminovEvents> siminovEvents = [eventHandler getSiminovEvent];
    if(siminovEvents != nil) {
        [siminovEvents onSiminovInitialized];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        long startLocation = [event rangeOfString:@"."].location;
        if(startLocation <= -1 || startLocation == NSNotFound) {
            startLocation = 0;
        }
        
        NSLog([NSString stringWithFormat:@"Start Location: %ld, %@", startLocation, event], __PRETTY_FUNCTION__);
        NSRange eventRange = NSMakeRange(startLocation, [event length] - startLocation);
        event = [event substringWithRange:eventRange];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"siminovInitialized" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


- (void)onSiminovStopped {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICISiminovEvents> siminovEvents = [eventHandler getSiminovEvent];
    if(siminovEvents != nil) {
        [siminovEvents onSiminovStopped];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_STOPPED];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
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
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"siminovStopped" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


@end
