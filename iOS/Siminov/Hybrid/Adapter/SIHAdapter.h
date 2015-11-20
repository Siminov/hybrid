//
//  SIHAdapter.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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
