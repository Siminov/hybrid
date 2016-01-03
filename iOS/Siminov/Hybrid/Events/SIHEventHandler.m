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


#import "SIHEventHandler.h"

#import "SICResourceManager.h"

@implementation SIHEventHandler

static SIHEventHandler *eventHandler = nil;
static NSMutableArray *events;

+ (void)initialize {
    events = [[NSMutableArray alloc] init];
}

+ (SIHEventHandler *)getInstance {
    
    if(!eventHandler) {
        eventHandler = [[super allocWithZone:NULL] init];
    }
    return eventHandler;
}



- (bool)doesEventsRegistered {
    
    if([events count] > 0) {
        return true;
    }
    
    return false;
}



- (void)addEvent:(NSString *)event {
    [events addObject:event];
}



- (NSEnumerator *)getEvents {
    return [events objectEnumerator];
}



- (id<SICISiminovEvents>)getSiminovEvent {
    
    if(siminovEvents != nil) {
        return siminovEvents;
    }
    
    
    NSEnumerator *allEvents = [events objectEnumerator];
    NSString *event;
    
    while(event = [allEvents nextObject]) {
        
        id object = nil;
        @try {
            object = [SICClassUtils createClassInstance:event];
        } @catch(SICSiminovException *exception) {
            [SICLog debug:NSStringFromClass([self class]) methodName:@"getSiminovEvent" message:[NSString stringWithFormat:@"Exception caught while creating class object, CLASS-NAME: %@, %@", event, [exception getMessage]]];
        }
        
        if(object == nil) {
            continue;
        }
        
        if([object conformsToProtocol: @protocol(SICISiminovEvents)]) {
            siminovEvents = (id<SICISiminovEvents>)object;
            break;
        }
    }
    
    
    return siminovEvents;
}



- (id<SICIDatabaseEvents>)getDatabaseEvents {
    
    if(databaseEvents != nil) {
        return databaseEvents;
    }
    
    
    NSEnumerator *allEvents = [events objectEnumerator];
    NSString *event;
    
    while(event = [allEvents nextObject]) {
        
        id object = nil;
        @try {
            object = [SICClassUtils createClassInstance:event];
        } @catch(SICSiminovException *exception) {
            [SICLog debug:NSStringFromClass([self class]) methodName:@"getDatabaseEvents" message:[NSString stringWithFormat:@"Exception caught while creating class object, CLASS-NAME: %@, %@", event, [exception getMessage]]];
        }
        
        if(object == nil) {
            continue;
        }
        
        if([object conformsToProtocol: @protocol(SICIDatabaseEvents)]) {
            databaseEvents = (id<SICIDatabaseEvents>)object;
            break;
        }
    }
    
    
    return databaseEvents;
}


- (id<SIKINotificationEvents>)getNotificationEvent {
    
    if(notificationEvents != nil) {
        return notificationEvents;
    }
    
    
    NSEnumerator *allEvents = [events objectEnumerator];
    NSString *event;
    
    while(event = [allEvents nextObject]) {
        
        id object = nil;
        @try {
            object = [SICClassUtils createClassInstance:event];
        } @catch(SICSiminovException *exception) {
            [SICLog debug:NSStringFromClass([self class]) methodName:@"getNotificationEvent" message:[NSString stringWithFormat:@"Exception caught while creating new instance of class, CLASS-NAME: %@, %@", event, [exception getMessage]]];
        }
        
        if(object == nil) {
            continue;
        }
        
        if([object conformsToProtocol: @protocol(SIKINotificationEvents)]) {
            notificationEvents = (id<SIKINotificationEvents>)object;
            break;
        }
    }
    
    
    return notificationEvents;
}



- (id<SIKISyncEvents>)getSyncEvent {
    
    if(syncEvents != nil) {
        return syncEvents;
    }
    
    
    NSEnumerator *allEvents = [events objectEnumerator];
    NSString *event;
    
    while(event = [allEvents nextObject]) {
        
        id object = nil;
        @try {
            object = [SICClassUtils createClassInstance:event];
        } @catch(SICSiminovException *exception) {
            [SICLog debug:NSStringFromClass([self class]) methodName:@"getSyncEvent" message:[NSString stringWithFormat:@"Exception caught while creating new instance of class, CLASS-NAME: %@, %@", event, [exception getMessage]]];
        }
        
        if(object == nil) {
            continue;
        }
        
        if([object conformsToProtocol: @protocol(SIKISyncEvents)]) {
            syncEvents = (id<SIKISyncEvents>)object;
            break;
        }
    }
    
    
    return syncEvents;
}


@end