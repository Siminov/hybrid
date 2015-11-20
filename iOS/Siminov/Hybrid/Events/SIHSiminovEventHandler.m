//
//  SIHSiminovEventHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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
        int startLocation = [event rangeOfString:@"."].location;
        if(startLocation == -1) {
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
        int startLocation = [event rangeOfString:@"."].location;
        if(startLocation == -1) {
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
        int startLocation = [event rangeOfString:@"."].location;
        if(startLocation == -1) {
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
