//
//  SIHSiminovHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIHSiminov.h"
#import "SICResourceManager.h"
#import "SIHResourceManager.h"


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
