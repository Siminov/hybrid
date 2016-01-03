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


#import "SIHDatabaseEventHandler.h"
#import "SIHHybridEventHandler.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHAdapter.h"
#import "SIHConstants.h"



@implementation SIHDatabaseEventHandler


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        hybridResourceManager = [SIHResourceManager getInstance];
        eventHandler = [SIHEventHandler getInstance];
        
        return self;
    }
    
    return self;
}


- (void)onDatabaseCreated:(SICDatabaseDescriptor *)databaseDescriptor {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onDatabaseCreated:databaseDescriptor];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_CREATED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridDatabaseDescriptor];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"databaseCreated" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onDatabaseDropped:(SICDatabaseDescriptor *)databaseDescriptor {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onDatabaseDropped:databaseDescriptor];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_DROPPED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *parameteres = [[SIHHybridSiminovData alloc] init];
    [parameteres setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    [parameteres addData:hybridDatabaseDescriptor];
    
    [hybridSiminovDatas addHybridSiminovData:parameteres];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"databaseDropped" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


- (void)onTableCreated:(SICDatabaseDescriptor *)databaseDescriptor entityDescriptor:(SICEntityDescriptor *)entityDescriptor {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onTableCreated:databaseDescriptor entityDescriptor:entityDescriptor];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_CREATED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptor = [hybridResourceManager generateHybridEntityDescriptor:entityDescriptor];
    
    
    SIHHybridSiminovData *parameters = [[SIHHybridSiminovData alloc] init];
    [parameters setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    
    [parameters addData:hybridDatabaseDescriptor];
    [parameters addData:hybridEntityDescriptor];
    
    
    [hybridSiminovDatas addHybridSiminovData:parameters];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"tableCreated" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


- (void)onTableDropped:(SICDatabaseDescriptor *)databaseDescriptor entityDescriptor:(SICEntityDescriptor *)entityDescriptor {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onTableDropped:databaseDescriptor entityDescriptor:entityDescriptor];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_DROPPED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptor = [hybridResourceManager generateHybridEntityDescriptor:entityDescriptor];
    
    SIHHybridSiminovData *parameters = [[SIHHybridSiminovData alloc] init];
    [parameters setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    
    [parameters addData:hybridDatabaseDescriptor];
    [parameters addData:hybridEntityDescriptor];
    
    
    [hybridSiminovDatas addHybridSiminovData:parameters];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"tableDrpped" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


- (void)onIndexCreated:(SICDatabaseDescriptor *)databaseDescriptor entityDescriptor:(SICEntityDescriptor *)entityDescriptor index:(SICIndex *)index {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onIndexCreated:databaseDescriptor entityDescriptor:entityDescriptor index:index];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_CREATED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptor = [hybridResourceManager generateHybridEntityDescriptor:entityDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptorIndex = [hybridResourceManager generateHybridEntityDescriptorIndex:index];
    
    SIHHybridSiminovData *parameters = [[SIHHybridSiminovData alloc] init];
    [parameters setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    
    [parameters addData:hybridDatabaseDescriptor];
    [parameters addData:hybridEntityDescriptor];
    [parameters addData:hybridEntityDescriptorIndex];
    
    [hybridSiminovDatas addHybridSiminovData:parameters];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"indexCreated" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


- (void)onIndexDropped:(SICDatabaseDescriptor *)databaseDescriptor entityDescriptor:(SICEntityDescriptor *)entityDescriptor index:(SICIndex *)index {
    
    if(![hybridResourceManager doesEventsRegistered]) {
        return;
    }
    
    
    id<SICIDatabaseEvents> databaseEvents = [eventHandler getDatabaseEvents];
    if(databaseEvents != nil) {
        [databaseEvents onIndexDropped:databaseDescriptor entityDescriptor:entityDescriptor index:index];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_DROPPED];
    
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
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptor = [hybridResourceManager generateHybridEntityDescriptor:entityDescriptor];
    
    SIHHybridSiminovData *hybridEntityDescriptorIndex = [hybridResourceManager generateHybridEntityDescriptorIndex:index];
    
    SIHHybridSiminovData *parameters = [[SIHHybridSiminovData alloc] init];
    [parameters setDataType:HYBRID_EVENT_HANDLER_EVENT_PARAMETERS];
    
    [parameters addData:hybridDatabaseDescriptor];
    [parameters addData:hybridEntityDescriptor];
    [parameters addData:hybridEntityDescriptorIndex];
    
    [hybridSiminovDatas addHybridSiminovData:parameters];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"indexDropped" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
    
}


@end
