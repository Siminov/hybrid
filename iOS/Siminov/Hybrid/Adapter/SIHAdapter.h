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

#import "SIHAdapterHandler.h"

/**
 * Handle Request between NATIVE-TO-HYBRID.
 * Exposes method to GET and SET information about request.
 */
@interface SIHAdapter : NSObject {
    
    SIHAdapterHandler *adapterHandler;
    
    NSString *adapterName;
    NSString *handlerName;
    
    NSMutableArray *parameters;
    
}

/**
 * Get Adapter Name.
 * @return Name of Adapter.
 */
- (NSString *)getAdapterName;

/**
 * Set Adapter Name.
 * @param adapterName Name of Adapter.
 */
- (void)setAdapterName:(NSString *)adaptername;

/**
 * Get Handler Name.
 * @return Name of Handler.
 */
- (NSString *)getHandlerName;

/**
 * Set Handler Name.
 * @param handlerName Name of Handler.
 */
- (void)setHandlerName:(NSString *)handlername;

/**
 * Get Handler Parameters.
 * @return Handler Parameters.
 */
- (NSEnumerator *)getParameters;

/**
 * Set Handler Parameters.
 * @param parameter Handler Parameters.
 */
- (void)addParameter:(NSString *)parameter;

/**
 * Invokes Handler based on request parameter set.
 */
- (void)invoke;

@end
