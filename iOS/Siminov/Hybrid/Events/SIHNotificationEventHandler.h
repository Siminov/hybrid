//
//  SIHNotificationEventHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKINotificationEvents.h"
#import "SIHResourceManager.h"
#import "SIHEventHandler.h"

@interface SIHNotificationEventHandler : NSObject<SIKINotificationEvents> {
    
    SIHResourceManager *hybridResourceManager;
    SIHEventHandler *eventHandler;
}

@end
