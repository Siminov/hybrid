//
//  SIHReactInterceptor.m
//  hybridReactSample
//
//  Created by user on 15/10/15.
//  Copyright © 2015 Facebook. All rights reserved.
//


#import "SIHReactInterceptor.h"
#import "RCTLog.h"
#import "RCTBridgeModule.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#import "SIHUtils.h"

@implementation SIHReactInterceptor


static SIHAdapterHandler *adapterHandler;
static id<SIHIHandler> handler;

static SIHResourceManager *resourceManager;

RCT_EXPORT_MODULE();

@synthesize bridge = _bridge;

-(id)init {
  
  adapterHandler = [SIHAdapterHandler getInstance];
  handler = [adapterHandler getHandler];
  
  resourceManager = [SIHResourceManager getInstance];
  [resourceManager setInterceptor:self];
  
  return self;
}


-(NSString *)handleHybridToNative:(NSString *)action {
  return nil;
}

-(NSString *)handleHybridToNative:(NSString *)action data:(NSString *)data {
  return nil;
}

-(NSString *)handleHybridToNativeAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data {
  return nil;
}


RCT_EXPORT_METHOD(handleHybridToNative:(NSString *)protocol action:(NSString *)action data:(NSString *)data callback:(RCTResponseSenderBlock)callback) {
  NSString *response = [handler handleHybridToNative:action data:data];
  callback(@[[NSNull null], response]);
}


RCT_EXPORT_METHOD(handleHybridToNativeAsync:(NSString *)protocol requestId:(NSString *)requestId action:(NSString *)action data:(NSString *)data) {
  
  if(data) {
    data = [SIHUtils stringByDecodingURLFormat:data];
  }
  
  [handler handleHybridToNativeAsync:requestId action:action data:data];
}


- (void)handleNativeToHybrid:(NSString *)action data:(NSArray *)data {
  
}

- (void)handleNativeToHybridAsync:(NSString *)requestId data:(NSArray *)data {
  
}

- (void)handleNativeToHybrid:(NSString *)functionName apiName:(NSString *)apiName action:(NSString *)action parameters:(NSString *)parameters {
  [_bridge.eventDispatcher sendDeviceEventWithName:functionName body:@{@"api": apiName, @"action": action, @"parameters": parameters}];
}


@end
