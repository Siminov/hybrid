//
//  SIHGenericSyncHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SIKResourceManager.h"
#import "SIKSyncWorker.h"
#import "SIKISyncRequest.h"


@interface SIHGenericSyncHandler : NSObject


+ (SIHGenericSyncHandler *)getInstance;


/**
 * It handle sync request from hybrid
 * @param syncRequest Sync Request
 */
- (void)handle:(id<SIKISyncRequest>)syncRequest;

@end
