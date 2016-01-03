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

#import "SIHSiminov.h"
#import "SICResourceManager.h"
#import "SIHResourceManager.h"


/**
 * This actually deals with Request Handling Between HYBRID-TO-NATIVE and NATIVE-TO-HYBRID.
 *
 */
@interface SIHSiminovHandler : SIHSiminov<SIHIAdapter, SIHIHandler> {
    
    SICResourceManager *coreResourceManager;
    SIHResourceManager *hybridResourceManager;
    
    SIHAdapterFactory *adapterResources;
}


- (NSString *)handleHybridToNative:(NSString *)action;


- (NSString *)handleHybridToNative:(NSString *)action data:(NSString *)data;


- (void)handleNativeToHybrid:(NSString *)action data:(NSEnumerator *)data;


- (NSString *)handleHybridToNativeAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data;


- (NSString *)processHandler:(NSString *)action data:(NSString *)data;


- (NSMutableArray *) createAndInflateParameter:(NSEnumerator *)parameterTypes values:(NSEnumerator *)parameterValues;


- (NSEnumerator *)getParameterTypes:(NSEnumerator *)parameters;


- (NSString *)generateHybridSiminovEmptyData;


- (NSString *)generateHybridSiminovException:(NSString *)className methodName:(NSString *)methodName message:(NSString *)message;


- (void)initializeSiminov;


- (void)shutdownSiminov;


@end
