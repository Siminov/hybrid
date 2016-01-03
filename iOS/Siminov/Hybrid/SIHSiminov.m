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


#import "SIHSiminov.h"

#import "SICDeploymentException.h"
#import "SIHInitializer.h"
#import "SIHApplicationDescriptorReader.h"
#import "SIKResourceManager.h"
#import "SIHResourceManager.h"
#import "SIHLibraryDescriptorReader.h"
#import "SIHAdapterDescriptorReader.h"

#import "SIHSiminovEventHandler.h"
#import "SIHDatabaseEventHandler.h"
#import "SIHSyncEventHandler.h"
#import "SIHNotificationEventHandler.h"


@implementation SIHSiminov


static bool isHybridActive = false;


static SICResourceManager *coreResourceManager;
static SIKResourceManager *connectResourceManager;
static SIHResourceManager *hybridResourceManager;


+ (void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    connectResourceManager = [SIKResourceManager getInstance];
    hybridResourceManager = [SIHResourceManager getInstance];
}



+ (void)isActive {
    
    if(!isHybridActive) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"isActive" message:@"Siminov Not Active."];
    }
}

+ (bool)getActive {
    return isHybridActive;
}

+ (void)setActive:(BOOL)active {
    isHybridActive = active;
}


+ (id<SICIInitializer>)initializer {
    return [[SIHInitializer alloc] init];
}



+ (void)start {

    [self processApplicationDescriptor];
    
    [self processDatabaseDescriptors];
    [self processLibraries];
    
    
    [self processEvents];
    
    
    [self processEntityDescriptors];
    [self processSyncDescriptors];
    
    
    [self processAdapterDescriptors];
    [self processHybridServices];
}


+ (void)shutdown {
    
    [SIKSiminov shutdown];
}

+ (void)processApplicationDescriptor {
    
    SIHApplicationDescriptorReader *applicationDescriptorParser = [[SIHApplicationDescriptorReader alloc] init];
    
    SIHApplicationDescriptor *applicationDescriptor = [applicationDescriptorParser getApplicationDescriptor];
    if(applicationDescriptor == nil) {
        [SICLog debug:NSStringFromClass([self class]) methodName:@"processApplicationDescriptor" message:@"Invalid Application Descriptor Found."];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"processApplicationDescriptor" message:@"Invalid Application Descriptor Found."];
    }
    
    [coreResourceManager setApplicationDescriptor:applicationDescriptor];
    [connectResourceManager setApplicationDescriptor:applicationDescriptor];
    [hybridResourceManager setApplicationDescriptor:applicationDescriptor];
}


+ (void)processDatabaseDescriptors {
    [SIKSiminov processDatabaseDescriptors];
}


+ (void)processLibraries {
    
    SIHApplicationDescriptor *applicationDescriptor = [hybridResourceManager getApplicationDescriptor];
    [applicationDescriptor addLibraryDescriptorPath:LIBRARY_DESCRIPTOR_FILE_PATH];
    
    
    NSEnumerator *libraries = [applicationDescriptor getLibraryDescriptorPaths];
    NSString *library;
    
    while(library = [libraries nextObject]) {
        
        /*
         * Parse LibraryDescriptor.
         */
        SIHLibraryDescriptorReader *libraryDescriptorReader = [[SIHLibraryDescriptorReader alloc] initWithLibraryName:library];
        SIHLibraryDescriptor *libraryDescriptor = [libraryDescriptorReader getLibraryDescriptor];
        
        
        /*
         * Map Entity Descriptors
         */
        NSEnumerator *entityDescriptors = [libraryDescriptor getEntityDescriptorPaths];
        NSString *libraryEntityDescriptorPath;
        
        while(libraryEntityDescriptorPath = [entityDescriptors nextObject]) {
            
            NSUInteger index = 0;
            NSRange range = [libraryEntityDescriptorPath rangeOfString:LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR];
            if (range.length == 0 && range.location > libraryEntityDescriptorPath.length) {
                index = 0;
            } else {
                index = range.location;
            }
            
            NSString *databaseDescriptorName = [libraryEntityDescriptorPath substringWithRange:NSMakeRange(0,index)];
            NSString *entityDescriptor = [libraryEntityDescriptorPath substringFromIndex:index+1];
            
            
            NSEnumerator *databaseDescriptors = [applicationDescriptor getDatabaseDescriptors];
            SICDatabaseDescriptor *databaseDescriptor;
            
            while(databaseDescriptor = [databaseDescriptors nextObject]) {
                
                if([[databaseDescriptor getDatabaseName] caseInsensitiveCompare:databaseDescriptorName] == NSOrderedSame) {
                    [databaseDescriptor addEntityDescriptorPath:[NSString stringWithFormat:@"%@%@%@",library,LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR,entityDescriptor]];
                }
            }
        }
        
        
        /*
         * Map Adapters
         */
        NSEnumerator *adapterDescriptorPaths = [libraryDescriptor getAdapterDescriptorPaths];
        NSString *adapterDescriptorPath;
        
        while(adapterDescriptorPath = [adapterDescriptorPaths nextObject]) {
            [applicationDescriptor addAdapterDescriptorPath:[NSString stringWithFormat:@"%@.%@",library,adapterDescriptorPath]];
            //[applicationDescriptor addAdapterDescriptorPath:[NSString stringWithFormat:@"%@%@%@",[library stringByReplacingOccurrencesOfString:@"." withString:@"/"], @"/", adapterDescriptorPath]];
        }
    }
}



+ (void)processEntityDescriptors {
    [SIKSiminov processEntityDescriptors];
    
    [hybridResourceManager synchronizeEntityDescriptors];
}



+ (void)processSyncDescriptors {
    [SIKSiminov processSyncDescriptors];
}



+ (void)processServices {
    [SIKSiminov processServices];
}


+ (void)processDatabase {
    [SIKSiminov processDatabase];
}



+ (void)processAdapterDescriptors {
    
    SIHApplicationDescriptor *applicationDescriptor = [hybridResourceManager getApplicationDescriptor];
    NSEnumerator *adapterDescriptorPaths = [applicationDescriptor getAdapterDescriptorPaths];
    NSString *adapterDescriptorPath;
    
    while(adapterDescriptorPath = [adapterDescriptorPaths nextObject]) {
        SIHAdapterDescriptorReader *adapterDescriptorParser = [[SIHAdapterDescriptorReader alloc] initWithPath:adapterDescriptorPath];
        [applicationDescriptor addAdapterDescriptor:adapterDescriptorPath adapterDescriptor:[adapterDescriptorParser getAdapterDescriptor]];
    }
}



+ (void)processEvents {
    
    SIHApplicationDescriptor *applicationDescriptor = [hybridResourceManager getApplicationDescriptor];
    
    NSMutableArray *siminovEvents = [[NSMutableArray alloc] init];
    NSEnumerator *events = [applicationDescriptor getEvents];
    NSString *event;
    
    while(event = [events nextObject]) {
        [hybridResourceManager addEvent:event];
        [siminovEvents addObject:event];
    }
    
    events = [siminovEvents objectEnumerator];
    
    while(event = [events nextObject]) {
        [applicationDescriptor removeEvent:event];
    }
    
    
    [applicationDescriptor addEvent:NSStringFromClass([SIHSiminovEventHandler class])];
    [applicationDescriptor addEvent:NSStringFromClass([SIHDatabaseEventHandler class])];
    [applicationDescriptor addEvent:NSStringFromClass([SIHSyncEventHandler class])];
    [applicationDescriptor addEvent:NSStringFromClass([SIHNotificationEventHandler class])];
}




+ (void)processHybridServices {
    [SIHAdapterHandler getInstance];
}



+ (void)siminovInitialized {
    
    isHybridActive = true;
    [super setActive:true];
    
    [self processServices];
    
    id<SICISiminovEvents> coreEventHandler = [coreResourceManager getSiminovEventHandler];
    if([coreResourceManager getSiminovEventHandler] != nil) {
        
        if([SICSiminov isFirstTimeInitialized]) {
            //[coreEventHandler onFirstTimeSiminovInitialized];
        } else {
            //[coreEventHandler onSiminovInitialized];
        }
    }
}

@end
