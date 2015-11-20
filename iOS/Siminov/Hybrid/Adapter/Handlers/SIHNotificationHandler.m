//
//  SIHNotificationHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIHNotificationHandler.h"

@implementation SIHNotificationHandler

- (id)init {
    self = [super init];
    
    if(self) {
        notificationManager = [SIKNotificationManager getInstance];
        return self;
    }
    
    return self;
}


- (void)doRegistration {
    [notificationManager doRegistration];
}


- (void) doUnregistration {
    [notificationManager doUnregistration];
}

@end
