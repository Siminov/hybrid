//
//  SIHNotificationHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SIHIAdapter.h"
#import "SIKNotificationManager.h"


@interface SIHNotificationHandler : NSObject<SIHIAdapter> {
    
    SIKNotificationManager *notificationManager;
}


/**
 * It handles registration request from hybrid
 */
- (void)doRegistration;

/**
 * It handles unregistration request from hybrid
 */
- (void)doUnregistration;

@end
