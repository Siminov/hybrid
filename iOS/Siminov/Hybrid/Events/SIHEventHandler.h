//
//  SIHEventHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SICISiminovEvents.h"
#import "SICIDatabaseEvents.h"
#import "SIKINotificationEvents.h"
#import "SIKISyncEvents.h"


/**
 * It provides the event handler instances.
 */
@interface SIHEventHandler : NSObject {
    
    id<SICISiminovEvents> siminovEvents;
    id<SICIDatabaseEvents> databaseEvents;
    id<SIKINotificationEvents> notificationEvents;
    id<SIKISyncEvents> syncEvents;
}


+ (SIHEventHandler *)getInstance;

/**
 * Check whether Application is registered for any of Events.
 * @return true/false.
 */
- (bool)doesEventsRegistered;


/**
 * Add Application Event Notifier.
 * @param event Event
 */
- (void)addEvent:(NSString *)event;


/**
 * Get All Event Notifiers Registered By Application.
 * @return All Events.
 */
- (NSEnumerator *)getEvents;



/**
 * Get ISiminov Event Notifier Defined By Application.
 * @return ISiminov Event Notifier.
 */
- (id<SICISiminovEvents>)getSiminovEvent;

/**
 * Get IDatabase Event Notifier Registered By Application.
 * @return IDatabseEvent Notifier.
 */
- (id<SICIDatabaseEvents>)getDatabaseEvents;

/**
 * Get INotificationEvents Registered by the application
 * @return INotificationEvents Handler
 */
- (id<SIKINotificationEvents>)getNotificationEvent;


/**
 * Get ISyncEvents Handler Registered by the application
 * @return ISyncEvents Handler
 */
- (id<SIKISyncEvents>)getSyncEvent;

@end
