//
//  SIHSyncHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKISyncRequest.h"

@interface SIHSyncHandler : NSObject


/**
 * It handles sync request from hybrid
 */
- (void)handle:(NSString *)data;

@end
