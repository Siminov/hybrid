//
//  SIHApplicationDescriptorReader.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHApplicationDescriptorReader.h"

#import "SICFileUtils.h"

@implementation SIHApplicationDescriptorReader



/**
 * ApplicationDescriptorReader Constructor
 */
- (id)init {
    self = [super init];
    
    if(self) {
        tempValue = [[NSMutableString alloc] init];
        resourceManager = [SICResourceManager getInstance];
        
        NSData *applicationDescriptorStream = nil;
        
        @try {
            
            NSString *filePath = [[[SICFileUtils alloc] init] getFilePath:APPLICATION_DESCRIPTOR_FILE_NAME inDirectory:DIRECTORY_NAME];
            NSLog([NSString stringWithFormat:@"Application Descriptor Path: %@", filePath], __PRETTY_FUNCTION__);
            
            applicationDescriptorStream = [[NSFileManager defaultManager] contentsAtPath:filePath];
            if (applicationDescriptorStream == nil) {
                @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of application descriptor"]];
            }
        }
        @catch (NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of application descriptor %@", [exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"IOException caught while getting input stream of application descriptor %@", [exception reason]]];
        }
        
        @try {
            [self parseMessage:applicationDescriptorStream];
        }
        @catch (NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR, %@", [exception reason]]];
            @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"process" message:[NSString stringWithFormat:@"Exception caught while parsing APPLICATION-DESCRIPTOR %@", [exception reason]]];
        }
    }
    
    return self;
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict {
    tempValue = [[NSMutableString alloc] init];
    
    if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_SIMINOV] == NSOrderedSame) {
        applicationDescriptor = [[SIHApplicationDescriptor alloc] init];
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [self initializeProperty:attributeDict];
    } else if([elementName caseInsensitiveCompare:NOTIFICATION_DESCRIPTOR] == NSOrderedSame) {
        
        isNotificationDescriptor = true;
        notificationDescriptor = [[SIKNotificationDescriptor alloc] init];
    }
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string {
    [tempValue appendString:(NSMutableString *)[string stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]]];
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName {
    
    if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_PROPERTY] == NSOrderedSame) {
        [self processProperty];
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_DATABASE_DESCRIPTOR] == NSOrderedSame) {
        [applicationDescriptor addDatabaseDescriptorPath:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_EVENT_HANDLER] == NSOrderedSame) {
        
        if(tempValue == nil || [tempValue length] <= 0) {
            return;
        }
        
        [applicationDescriptor addEvent:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_LIBRARY_DESCRIPTOR] == NSOrderedSame) {
        
        if(tempValue == nil || [tempValue length] <= 0) {
            return;
        }
        
        [applicationDescriptor addLibraryDescriptorPath:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_SERVICE_DESCRIPTOR] == NSOrderedSame) {
        [applicationDescriptor addServiceDescriptorPath:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:SYNC_DESCRIPTOR] == NSOrderedSame) {
        [applicationDescriptor addSyncDescriptorPath:(NSString *)tempValue];
    } else if([elementName caseInsensitiveCompare:NOTIFICATION_DESCRIPTOR] == NSOrderedSame) {
        [applicationDescriptor setNotificationDescriptor:notificationDescriptor];
        isNotificationDescriptor = false;
    } else if([elementName caseInsensitiveCompare:APPLICATION_DESCRIPTOR_ADAPTER_DESCRIPTOR] == NSOrderedSame) {
        [applicationDescriptor addAdapterDescriptorPath:(NSString *)tempValue];
    }
}

- (void)initializeProperty:(NSDictionary *)attributes {
    propertyName = [attributes objectForKey:APPLICATION_DESCRIPTOR_NAME];
}

- (void)processProperty {
    
    if(isNotificationDescriptor) {
        [notificationDescriptor addProperty:propertyName value:(NSString *)tempValue];
    } else {
        [applicationDescriptor addProperty:propertyName value:(NSString *)tempValue];
    }
}

-(SIHApplicationDescriptor *)getApplicationDescriptor {
    return applicationDescriptor;
}

@end
