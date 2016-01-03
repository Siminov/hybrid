///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

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
