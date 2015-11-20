//
//  SIHGenericService.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SIKService.h"
#import "SIKIConnectionRequest.h"
#import "SIKIConnectionResponse.h"
#import "SIKServiceException.h"


@interface SIHGenericService : SIKService

/**
 * It handles generic service start event, and delivers the call to registered classes
 */
- (void)onStart;

/**
 * It handles generic service queue event, and delivers the call to registered classes
 */
- (void)onQueue;

/**
 * It handles generic service pause event, and delivers the call to registered classes
 */
- (void)onPause;

/**
 * It handles generic service resume event, and delivers the call to registered classes
 */
- (void)onResume;

/**
 * It handles generic service finish event, and delivers the call to registered classes
 */
- (void)onFinish;

/**
 * It handles generic service request invoke event, and delivers the call to registered classes
 */
- (void)onRequestInvoke:(id<SIKIConnectionRequest>)connectionRequest;

/**
 * It handles generic service request finish event, and delivers the call to registered classes
 */
- (void)onRequestFinish:(id<SIKIConnectionResponse>)connectionResponse;

/**
 * It handles generic service terminate event, and delivers the call to registered classes
 */
- (void)onTerminate:(SIKServiceException *)serviceException;

@end
