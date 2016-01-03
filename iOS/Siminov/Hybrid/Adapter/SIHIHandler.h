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



/**
 * Exposes methods to deal with actual request comes from HYBRID-TO-NATIVE or NATIVE-TO-HYBRID.
 * It has methods to open, create, close, and execute query's.
 */
@protocol SIHIHandler <NSObject>

/**
 * Handle request from HYBRID-TO-NATIVE
 * @param action Action Needs to be performed on Native.
 * @return Return Data By Native Handler API.
 */
- (NSString *)handleHybridToNative:(NSString *)action;


/**
 * Handle request from HYBRID-TO-NATIVE
 * @param action Action Needs to be performed on Native.
 * @param data Data to Native Handler.
 * @return
 */
- (NSString *)handleHybridToNative:(NSString *)action data:(NSString *)data;

/**
 * Handle reuqest from HYBRID-TO-NATIVE
 * @param requestId Id of the request
 * @param action Action needs to be performed on native
 * @param data Data to native handler
 * @return
 */
- (NSString *)handleHybridToNativeAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data;


/**
 * Handle request from NATIVE-TO-HYBRID.
 * @param action Action Needs to be performed on Hybrid.
 * @param data Data to Hybrid Handler.
 */
- (void)handleNativeToHybrid:(NSString *)action data:(NSArray *)data;

/**
 * Handle request from NATIVE-TO-HYBRID
 * @param requestId Id of the request
 * @param data Data to hybrid handler
 */
- (void)handleNativeToHybridAsync:(NSString *)requestId data:(NSArray *)data;

/**
 * Handle request from NATIVE-TO-HYBRID
 * @param functionName Name of the hybrid function
 * @param apiName Name of the hybrid api
 * @param action Action needs to be performed on Hybrid
 * @param parameters Parameters to the hybrid api
 */
- (void)handleNativeToHybrid:(NSString *)functionName apiName:(NSString *)apiName action:(NSString *)action parameters:(NSString *)parameters;


@end
