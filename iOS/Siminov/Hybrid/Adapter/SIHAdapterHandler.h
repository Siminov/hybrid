//
//  SIHAdapterHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "SIHIHandler.h"

@class SIHResourceManager;


@interface SIHAdapterHandler : NSObject

+ (SIHAdapterHandler *)getInstance;


/**
 * Registers Handler instance to handle all request processed between Hybrid and Native.
 * @param handler IHandler Instance.
 */
- (void)registerHandler:(id<SIHIHandler>)hndlr;


/**
 * Get IHandler instance which receives and handler request processed between Hybrid and Native.
 * @return IHandler Instance.
 */
- (id<SIHIHandler>)getHandler;

@end



@interface SIHHybridInterceptor : NSURLProtocol

@property (nonatomic, strong) NSURLConnection *connection;
@property (nonatomic, strong) NSMutableData *mutableData;
@property (nonatomic, strong) NSURLResponse *response;

@end
