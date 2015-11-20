//
//  SIHServiceHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SIHIAdapter.h"

@interface SIHServiceHandler : NSObject<SIHIAdapter>

/**
 * It invoke requested service
 * @param data Data Parameter
 * @throws ServiceException If any exception occur while processing service request
 */
- (void)invoke:(NSString *)data;

@end
