//
//  SIHSyncEventHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIKISyncEvents.h"
#import "SIHResourceManager.h"
#import "SIHEventHandler.h"

@interface SIHSyncEventHandler : NSObject<SIKISyncEvents> {
    
    SIHResourceManager *hybridResourceManager;
    SIHEventHandler *eventHandler;
    
}

@end
