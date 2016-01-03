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

#import "SIHAdapterFactory.h"
#import "SIHIAdapter.h"
@class SIHResourceManager;


@interface SIHAdapterFactory : NSObject

+ (SIHAdapterFactory *)getInstance;


/**
 * Returns Adapter Descriptor mapped class instance based on adapter descriptor name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 * @return Adapter Class Instance.
 */
- (id<SIHIAdapter>)getAdapterInstance:(NSString *)adapterDescriptorName;


/**
 * Returns Adapter mapped class instance based on adapter name. If adapter instance is not cache it will create new instance of that class.
 * @param adapterName Name of Adapter.
 * @return Adapter Class Instance.
 */
- (id<SIHIAdapter>)requireAdapterInstance:(NSString *)adapterName;


/**
 * Returns Handler mapped method instance based on adapter name, handler name and its handler parameter types.
 * @param adapterDescriptorName Name of Adapter.
 * @param handlerName Name of Handler.
 * @param handlerParameterTypes Type of Parameters.
 * @return Handler Method Instance.
 */
- (Method)getHandlerInstance:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName handlerParameterTypes:(NSEnumerator *)handlerParameterTypes;


/**
 * Returns Handler mapped method instance based on adapter name, handler name and its handler parameter types. If instance is not cached it will create new instance for handler.
 * @param adapterDescriptorName Name of Adapter.
 * @param handlerName Name of Handler.
 * @param handlerParameterTypes Type of Parameters.
 * @return
 */
- (Method)requireHandlerInstance:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName handlerParameterTypes:(NSEnumerator *)handlerParameterTypes;


@end