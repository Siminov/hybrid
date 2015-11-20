//
//  SIHNotificationEventHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHNotificationEventHandler.h"

#import "SIHHybridEventHandler.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHAdapter.h"
#import "SIHConstants.h"


@implementation SIHNotificationEventHandler


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        hybridResourceManager = [SIHResourceManager getInstance];
        eventHandler = [SIHEventHandler getInstance];
        
        return self;
    }
    
    return self;
}

- (void)onRegistration:(id<SIKIRegistration>)registration {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SIKINotificationEvents> notificationEvents = [eventHandler getNotificationEvent];
    if(notificationEvents != nil) {
        [notificationEvents onRegistration:registration];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_REGISTRATION];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        event = [event substringWithRange:NSMakeRange([event rangeOfString:@"."].location + 1, [event length])];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    //Parameters
    SIHHybridSiminovData *hybridRegistration = [hybridResourceManager generateHybridRegistration:registration];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridRegistration];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onRegistration" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onUnregistration:(id<SIKIRegistration>)registration {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SIKINotificationEvents> notificationEvents = [eventHandler getNotificationEvent];
    if(notificationEvents != nil) {
        [notificationEvents onUnregistration:registration];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_UNREGISTRATION];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        event = [event substringWithRange:NSMakeRange([event rangeOfString:@"."].location + 1, [event length])];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    //Parameters
    SIHHybridSiminovData *hybridRegistration = [hybridResourceManager generateHybridRegistration:registration];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridRegistration];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onUnregistration" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onNotification:(id<SIKIMessage>)message {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SIKINotificationEvents> notificationEvents = [eventHandler getNotificationEvent];
    if(notificationEvents != nil) {
        [notificationEvents onNotification:message];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_NOTIFICATION];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        event = [event substringWithRange:NSMakeRange([event rangeOfString:@"."].location + 1, [event length])];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    //Parameters
    SIHHybridSiminovData *hybridMessage = [hybridResourceManager generateHybridMessage:message];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridMessage];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onNotification" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onError:(SIKNotificationException *)notificationException {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SIKINotificationEvents> notificationEvents = [eventHandler getNotificationEvent];
    if(notificationEvents != nil) {
        [notificationEvents onError:notificationException];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_ERROR];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Events
    SIHHybridSiminovData *events = [[SIHHybridSiminovData alloc] init];
    [events setDataType:HYBRID_EVENT_HANDLER_EVENTS];
    
    NSEnumerator *appEvents = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [appEvents nextObject]) {
        event = [event substringWithRange:NSMakeRange([event rangeOfString:@"."].location + 1, [event length])];
        
        SIHHybridSiminovValue *hybridEvent = [[SIHHybridSiminovValue alloc] init];
        [hybridEvent setValue:event];
        
        [events addValue:hybridEvent];
    }
    
    [hybridSiminovDatas addHybridSiminovData:events];
    
    //Parameters
    SIHHybridSiminovData *hybridNotificationException = [hybridResourceManager generateHybridNotificationException:notificationException];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridNotificationException];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onError" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}

@end
