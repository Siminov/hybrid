//
//  SIHResourcesHandler.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

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

- (NSString *)getDatabaseDescriptorBasedOnClassName:(NSString *)className;

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
