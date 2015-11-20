//
//  SIHAdapterFactory.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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