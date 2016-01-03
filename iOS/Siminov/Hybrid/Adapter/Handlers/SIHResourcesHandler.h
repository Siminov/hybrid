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

#import "SICResourceManager.h"
#import "SIHResourceManager.h"

@interface SIHResourcesHandler : NSObject<SIHIAdapter> {
    
    SICResourceManager *coreResourceManager;
    SIHResourceManager *hybridResourceManager;
}


/**
 * Handles Get Application Descriptor Request From Hybrid.
 * @return Application Descriptor.
 * @throws SiminovException If any error occur while getting Application Descriptor.
 */
- (NSString *)getApplicationDescriptor;


/**
 * It handles database descriptor request from hybrid
 * @param databaseDescriptorName Name of database descriptor
 * @return Hybrid Database Descriptor
 * @throws SiminovException If any exception occur while generating hybrid database descriptor
 */
- (NSString *)getDatabaseDescriptor:(NSString *)databaseDescriptorName;

/**
 * It handles database descriptor based on class name request from hybrid
 * @param className Name of the class
 * @return Hybrid Database Descriptor
 * @throws SiminovException If any exception occur while generating hybrid database descriptor
 */
- (NSString *)getDatabaseDescriptorBasedOnClassName:(NSString *)className;

/**
 * It handles database descriptor based on table name request from hybrid
 * @param tableName Name of the table
 * @return Hybrid Database Descriptor
 * @throws SiminovException If any exception occur while generating hybrid database descriptor
 */
- (NSString *)getDatabaseDescriptorBasedOnTableName:(NSString *)tableName;


/**
 * Handle Get Entity Descriptor Based On Hybrid Model Class Name.
 * @param className Name of Hybrid Model Class.
 * @return Entity Descriptor.
 * @throws SiminovException If any error occur while getting Entity Descriptor.
 */
- (NSString *)getEntityDescriptorBasedOnClassName:(NSString *)className;


/**
 * Handle Get Entity Descriptor Based On Table Name Request From Hybrid.
 * @param tableName Name of Table.
 * @return Entity Descriptor.
 * @throws SiminovException If any error occur while getting Entity Descriptor.
 */
- (NSString *)getEntityDescriptorBasedOnTableName:(NSString *)tableName;


@end
