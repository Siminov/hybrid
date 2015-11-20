//
//  SIHIHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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

- (NSString *)handleHybridToNativeAsync:(NSString *)requestId action:(NSString *)action data:(NSString *)data;


/**
 * Handle request from NATIVE-TO-HYBRID.
 * @param action Action Needs to be performed on Hybrid.
 * @param data Data to Hybrid Handler.
 */
- (void)handleNativeToHybrid:(NSString *)action data:(NSArray *)data;

- (void)handleNativeToHybridAsync:(NSString *)requestId data:(NSArray *)data;

- (void)handleNativeToHybrid:(NSString *)functionName apiName:(NSString *)apiName action:(NSString *)action parameters:(NSString *)parameters;


@end
