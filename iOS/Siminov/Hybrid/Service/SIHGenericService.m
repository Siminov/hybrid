//
//  SIHGenericService.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHGenericService.h"

#import "SIKResourceManager.h"
#import "SIHHybridSiminovDatas.h"
#import "SIHHybridServiceHandler.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SICLog.h"
#import "SIHAdapter.h"
#import "SIHConstants.h"
#import "SIHHybridEventHandler.h"
#import "SIHHybridServiceHandler.h"
#import "SIHResourceManager.h"


@implementation SIHGenericService


- (void)onStart {
    return;
    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onStart];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];

    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_SERVICE_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_START];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *) [self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServiceStart" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onQueue {
    return;
    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onQueue];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_QUEUE];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *)[self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServiceQueue" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onPause {
    return;
    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onPause];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_PAUSE];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *)[self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServicePause" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onResume {
    return;
    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onResume];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_RESUME];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *)[self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServiceResume" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onFinish {
    return;
    SIKResourceManager *conncetResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [conncetResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onFinish];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_FINISH];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *) [self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServicePause" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onRequestInvoke:(id<SIKIConnectionRequest>)connectionRequest {
    return;
    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    SIHResourceManager *hybridResourceManager = [SIHResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onRequestInvoke:connectionRequest];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_REQUEST_INVOKE];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    [hybridSiminovDatas addHybridSiminovData:[hybridResourceManager generateHybridConnectionRequest:connectionRequest]];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *) [self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServiceApiInvoke" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (void)onRequestFinish:(id<SIKIConnectionResponse>)connectionResponse {

    SIKResourceManager *connectResourceManager = [SIKResourceManager getInstance];
    SIHResourceManager *hybridResourceManager = [SIHResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [connectResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onRequestFinish:connectionResponse];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_SERVICE_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_REQUEST_FINISH];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    [hybridSiminovDatas addHybridSiminovData:[hybridResourceManager generateHybridConnectionResponse:connectionResponse]];
    
    
    //Event Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *) [self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServiceApiFinish" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}



- (void)onTerminate:(SIKServiceException *)serviceException {
    return;
    SIKResourceManager *conncetResourceManager = [SIKResourceManager getInstance];
    
    SIKServiceDescriptor *selfServiceDescriptor = [self getServiceDescriptor];
    if(selfServiceDescriptor == nil) {
        selfServiceDescriptor = [conncetResourceManager requiredServiceDescriptorBasedOnName:[self getService]];
        [self setServiceDescriptor:selfServiceDescriptor];
    }
    
    
    SIKRequest *selfRequest = [selfServiceDescriptor getRequest:[self getRequest]];
    NSString *apiHandler = [selfRequest getHandler];
    
    /*
     * Invoke Native Handler
     */
    id<SIKIService> selfService = [self getNativeHandler:apiHandler];
    if(selfService != nil) {
        [selfService onFinish];
    }
    
    
    /*
     * Invoke Hybrid Handler
     */
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    SIHHybridSiminovData *hybridRequestId = [[SIHHybridSiminovData alloc] init];
    [hybridRequestId setDataType:ISERVICE_REQUEST_ID];
    [hybridRequestId setDataValue:[NSString stringWithFormat:@"%ld", [self getRequestId]]];
    
    [hybridSiminovDatas addHybridSiminovData:hybridRequestId];
    
    SIHHybridSiminovData *hybridAPIHandler = [[SIHHybridSiminovData alloc] init];
    [hybridAPIHandler setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_API_HANDLER];
    [hybridAPIHandler setDataValue:apiHandler];
    
    [hybridSiminovDatas addHybridSiminovData:hybridAPIHandler];
    
    //Triggered Event
    SIHHybridSiminovData *triggeredEvent = [[SIHHybridSiminovData alloc] init];
    
    [triggeredEvent setDataType:HYBRID_EVENT_HANDLER_TRIGGERED_EVENT];
    [triggeredEvent setDataValue:HYBRID_SERVICE_HANDLER_ISERVICE_ON_TERMINATE];
    
    [hybridSiminovDatas addHybridSiminovData:triggeredEvent];
    
    //Add Resources
    SIHHybridSiminovData *serviceResources = [[SIHHybridSiminovData alloc] init];
    [serviceResources setDataType:HYBRID_SERVICE_HANDLER_ISERVICE_RESOURCES];
    
    
    NSEnumerator *selfResources = [self getResources];
    NSString *resourceName;
    
    while(resourceName = [selfResources nextObject]) {
        NSString *resourceValue = (NSString *) [self getResource:resourceName];
        
        SIHHybridSiminovData *serviceResource = [[SIHHybridSiminovData alloc] init];
        [serviceResource setDataType:resourceName];
        [serviceResource setDataValue:resourceValue];
        
        [serviceResources addData:serviceResource];
    }
    
    [hybridSiminovDatas addHybridSiminovData:serviceResources];
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"onServicePause" message:[NSString stringWithFormat:@"SiminovException caught while generating json: %@", [siminovException getMessage]]];
    }
    
    
    SIHAdapter *adapter = [[SIHAdapter alloc] init];
    [adapter setAdapterName:SERVICE_EVENT_HANDLER_ADAPTER];
    [adapter setHandlerName:SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER];
    
    [adapter addParameter:data];
    
    [adapter invoke];
}


- (id<SIKIService>)getNativeHandler:(NSString *)apiHandler {
    
    id object = nil;
    @try {
        object = [SICClassUtils createClassInstance:apiHandler];
    } @catch(SICSiminovException *exception) {
        [SICLog debug:NSStringFromClass([self class]) methodName:@"getNativeHandlerEvent" message:[NSString stringWithFormat:@"Exception caught while creating service native handler object, API-HANDLER: %@, %@", apiHandler, [exception getMessage]]];
        return nil;
    }
    
    return (id<SIKIService>)object;
}

@end
