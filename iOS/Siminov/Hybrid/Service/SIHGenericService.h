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
