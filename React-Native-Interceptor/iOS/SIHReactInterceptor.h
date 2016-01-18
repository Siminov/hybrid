//
//  SIHReactInterceptor.h
//  hybridReactSample
//
//  Created by user on 15/10/15.
//  Copyright © 2015 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "RCTBridgeModule.h"
#import "SICResourceManager.h"
#import "SIHResourceManager.h"
#import "SIHAdapterFactory.h"


@interface SIHReactInterceptor : NSObject <RCTBridgeModule, SIHIHandler>

@end
