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

#import "SIHResourcesHandler.h"

#import "SIHHybridApplicationDescriptor.h"
#import "SIHHybridSiminovDataWritter.h"


@implementation SIHResourcesHandler

- (id)init {
    self = [super init];
    
    if(self) {
        coreResourceManager = [SICResourceManager getInstance];
        hybridResourceManager = [SIHResourceManager getInstance];
        
        return self;
    }
    
    return self;
}


- (NSString *)getApplicationDescriptor {
    
    SICApplicationDescriptor *applicationDescriptor = [coreResourceManager getApplicationDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *hybridApplicationDescriptor = [[SIHHybridSiminovData alloc] init];
    
    [hybridApplicationDescriptor setDataType:HYBRID_APPLICATION_DESCRIPTOR_APPLICATION_DESCRIPTOR];
    
    /*
     * Application Name.
     */
    SIHHybridSiminovValue *applicationName = [[SIHHybridSiminovValue alloc] init];
    [applicationName setType:HYBRID_APPLICATION_DESCRIPTOR_NAME];
    [applicationName setValue:[applicationDescriptor getName]];
    
    [hybridApplicationDescriptor addValue:applicationName];
    
    /*
     * Application Description.
     */
    SIHHybridSiminovValue *applicationDescription = [[SIHHybridSiminovValue alloc] init];
    [applicationDescription setType:HYBRID_APPLICATION_DESCRIPTOR_DESCRIPTION];
    [applicationDescription setValue:[applicationDescriptor getDescription]];
    
    [hybridApplicationDescriptor addValue:applicationDescription];
    
    /*
     * Application Version.
     */
    SIHHybridSiminovValue *applicationVersion = [[SIHHybridSiminovValue alloc] init];
    [applicationVersion setType:APPLICATION_DESCRIPTOR_VERSION];
    [applicationVersion setValue:[NSString stringWithFormat:@"%.20lf", [applicationDescriptor getVersion]]];
    
    [hybridApplicationDescriptor addValue:applicationVersion];
    
    /*
     * DatabaseDescriptor Paths.
     */
    SIHHybridSiminovData *hybridDatabaseDescriptorPaths = [[SIHHybridSiminovData alloc] init];
    [hybridDatabaseDescriptorPaths setDataType:HYBRID_APPLICATION_DESCRIPTOR_DATABASE_DESCRIPTORS];
    
    NSEnumerator *databaseDescriptorPaths = [applicationDescriptor getDatabaseDescriptorPaths];
    NSString *databaseDescriptorPath;
    
    while(databaseDescriptorPath = [databaseDescriptorPaths nextObject]) {
        SIHHybridSiminovValue *hybridDatabaseDescriptorPath = [[SIHHybridSiminovValue alloc] init];
        [hybridDatabaseDescriptorPath setType:HYBRID_APPLICATION_DESCRIPTOR_DATABASE_DESCRIPTOR_PATH];
        [hybridDatabaseDescriptorPath setValue:databaseDescriptorPath];
        
        [hybridDatabaseDescriptorPaths addValue:hybridDatabaseDescriptorPath];
    }
    
    [hybridApplicationDescriptor addData:hybridDatabaseDescriptorPaths];
    
    /*
     * Event Notifiers.
     */
    SIHHybridSiminovData *hybridEventNotifiers = [[SIHHybridSiminovData alloc] init];
    [hybridEventNotifiers setDataType:HYBRID_APPLICATION_DESCRIPTOR_EVENT_NOTIFIERS];
    
    NSEnumerator *events = [hybridResourceManager getEvents];
    NSString *event;
    
    while(event = [events nextObject]) {
        SIHHybridSiminovValue *hybridSiminovEventHandler = [[SIHHybridSiminovValue alloc] init];
        [hybridSiminovEventHandler setType:HYBRID_APPLICATION_DESCRIPTOR_EVENT_NOTIFIER];
        [hybridSiminovEventHandler setValue:event];
        
        [hybridEventNotifiers addValue:hybridSiminovEventHandler];
        
    }
    
    
    [hybridApplicationDescriptor addData:hybridEventNotifiers];
    
    [hybridSiminovDatas addHybridSiminovData:hybridApplicationDescriptor];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
}


- (NSString *)getDatabaseDescriptor:(NSString *)databaseDescriptorName {
    
    SICDatabaseDescriptor *databaseDescriptor = [coreResourceManager getDatabaseDescriptorBasedOnName:databaseDescriptorName];
    
    SIHHybridSiminovDatas *hybridDatabaseDescriptors = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    [hybridDatabaseDescriptors addHybridSiminovData:hybridDatabaseDescriptor];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridDatabaseDescriptors];
}



- (NSString *)getDatabaseDescriptorBasedOnClassName:(NSString *)className {
    
    SICDatabaseDescriptor *databaseDescriptor = [coreResourceManager getDatabaseDescriptorBasedOnClassName:className];
    
    SIHHybridSiminovDatas *hybridDatabaseDescriptors = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    [hybridDatabaseDescriptors addHybridSiminovData:hybridDatabaseDescriptor];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridDatabaseDescriptors];
}


- (NSString *)getDatabaseDescriptorBasedOnTableName:(NSString *)tableName {
    
    SICDatabaseDescriptor *databaseDescriptor = [coreResourceManager getDatabaseDescriptorBasedOnTableName:tableName];
    
    SIHHybridSiminovDatas *hybridDatabaseDescriptors = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *hybridDatabaseDescriptor = [hybridResourceManager generateHybridDatabaseDescriptor:databaseDescriptor];
    
    [hybridDatabaseDescriptors addHybridSiminovData:hybridDatabaseDescriptor];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridDatabaseDescriptors];
}


- (NSString *)getEntityDescriptorBasedOnClassName:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [hybridResourceManager getEntityDescriptorBasedOnClassName:className];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    [hybridSiminovDatas addHybridSiminovData:[hybridResourceManager generateHybridEntityDescriptor:entityDescriptor]];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    
}


- (NSString *)getEntityDescriptorBasedOnTableName:(NSString *)tableName {
    
    SICEntityDescriptor *entityDescriptor = [hybridResourceManager getEntityDescriptorBasedOnTableName:tableName];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    [hybridSiminovDatas addHybridSiminovData:[hybridResourceManager generateHybridEntityDescriptor:entityDescriptor]];
    
    return [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    
}


@end
