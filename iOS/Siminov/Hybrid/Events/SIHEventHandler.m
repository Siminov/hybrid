//
//  SIHEventHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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