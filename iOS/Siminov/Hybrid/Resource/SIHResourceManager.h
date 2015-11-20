//
//  SIHResourceManager.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SICResourceManager.h"
#import "SIHApplicationDescriptor.h"
#import "SIHEventHandler.h"
#import "SIHAdapterHandler.h"
#import "SIHAdapterFactory.h"
#import "SICDatabaseDescriptor.h"
#import "SICEntityDescriptor.h"
#import "SIHHybridSiminovDatas.h"
#import "SICLibraryDescriptor.h"
#import "SIKIConnectionRequest.h"
#import "SIKIConnectionResponse.h"
#import "SIKIRegistration.h"
#import "SIKIMessage.h"
#import "SIKNotificationException.h"
#import "SIKISyncRequest.h"

/**
 * It handles and provides all resources needed by SIMINOV HYBRID.
 * <p>
 * Such As: Provides HybridDescriptor, AdapterDescriptor.
 */
@interface SIHResourceManager : NSObject

/**
 * It provides an instance of Resources class.
 *
 * @return Resources instance.
 */
+ (SIHResourceManager *)getInstance;


/**
 * Get Hybrid Descriptor.
 * @return Hybrid Descriptor.
 */
- (SIHApplicationDescriptor *)getApplicationDescriptor;

/**
 * Set Hybrid Descriptor.
 * @param applicationDescriptor Hybrid Descriptor.
 */
- (void)setApplicationDescriptor:(SIHApplicationDescriptor *)applicationdescriptor;



/**
 * Get All Adapter Descriptors defined by Application.
 * @return All Adapter Descriptors.
 */
- (NSEnumerator *)getAdapterDescriptors;


/**
 * Get All Adapter Descriptors Defined By Paths.
 * @return All Adapter Descriptors.
 */
- (NSEnumerator *)getAdapterDescriptorsBasedOnPaths;


/**
 * Get Adapter Descriptor based on Adapter Descriptor Name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 * @return Adapter Descriptor
 */
- (SIHAdapterDescriptor *)getAdapterDescriptor:(NSString *)adapterDescriptorName;

/**
 * Get Adapter Descriptor based on Adapter Descriptor Name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 * @return Adapter Descriptor.
 */
- (SIHAdapterDescriptor *)getAdapterBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Get Adapter Descriptor based on adapter descriptor path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 * @return Adapter Descriptor.
 */
- (SIHAdapterDescriptor *)getAdapterBasedOnPath:(NSString *)adapterDescriptorPath;


/**
 * Check whether adapter descriptor exist or not based on adapter descriptor name.
 * @param adapterDescriptorName Name of adapter descriptor.
 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
 */
- (bool)containAdapterBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Check whether adapter descriptor exist or not based on adapter descriptor path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
 */
- (bool)containAdapterBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Get All Handlers defined by Application.
 * @return All Handlers.
 */
- (NSEnumerator *)getHandlers;

/**
 * Get Handler based on Adapter Name and Handler Name.
 * @param adapterDescriptorName Name of Adapter.
 * @param handlerName Name of Handler.
 * @return Handler.
 */
- (SIHHandler *)getHandler:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName;

/**
 * Check whether Handler exist or not based on handler name.
 * @param handlerName Name of Handler.
 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
 */
- (bool)containHandler:(NSString *)handlerName;

/**
 * Check whether Handler exist or not based on adapter name and handler name.
 * @param adapterDescriptorName Name of Adapter.
 * @param handlerName Name of Handler.
 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
 */
- (bool)containHandler:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName;

/**
 * Get Web View.
 * @return Web View.
 */
- (UIWebView *)getWebView;

/**
 * Set Web View.
 * @param webView Web View.
 */
- (void)setWebView:(UIWebView *)webview;


- (id<SIHIHandler>)getInterceptor;


- (void)setInterceptor:(id<SIHIHandler>)interceptor;


/**
 * Check whether any event registered by application or not.
 * @return true/false; TRUE if event registered by application, FALSE if event not registered by application.
 */
- (bool)doesEventsRegistered;

/**
 * Add Event.
 * @param event Event.
 */
- (void)addEvent:(NSString *)event;

/**
 * Get All Events registered by application.
 * @return All Events.
 */
- (NSEnumerator *)getEvents;

/**
 * Get AdapterHandler.
 * @return AdapterHandler.
 */
- (SIHAdapterHandler *)getAdapterHandler;

/**
 * Set AdapterHandler.
 * @param adapterHandler AdapterHandler.
 */
- (void)setAdapterHandler:(SIHAdapterHandler *)adapterhandler;

/**
 * Get Adapter Resources.
 * @return Adapter Resources.
 */
- (SIHAdapterFactory *)getAdapterFactory;

/**
 * Set Adapter Resources.
 * @param adapterFactory Adapter Resources.
 */
- (void)setAdapterFactory:(SIHAdapterFactory *)adapterfactory;


/*
 * Siminov Core API's.
 */

/**
 * Get Database Descriptor based on Hybrid model class name.
 * @param className Name of Hybrid Model Class.
 * @return Database Descriptor.
 */
- (SICDatabaseDescriptor *)getDatabaseDescriptorBasedOnClassName:(NSString *)className;

/**
 * Get Database Descriptor Name based on Hybrid Model class name.
 * @param className Name of Hybrid Model Class.
 * @return Database Descriptor Name.
 */
- (NSString *)getDatabaseDescriptorNameBasedOnClassName:(NSString *)className;

/**
 * Get Database Descriptor Name based on table name.
 * @param tableName Name of table.
 * @return Database Descriptor Name.
 */
- (NSString *)getDatabaseDescriptorNameBasedOnTableName:(NSString *)tableName;

/**
 * Get Database Descriptor based on table name.
 * @param tableName Name of Table.
 * @return Database Descriptor.
 */
- (SICDatabaseDescriptor *)getDatabaseDescriptorBasedOnTableName:(NSString *)tableName;

/**
 * Get Entity Descriptor based on Hybrid Model Class Name.
 * @param className Name of Hybrid Model Class.
 * @return Entity Descriptor.
 */
- (SICEntityDescriptor *) getEntityDescriptorBasedOnClassName:(NSString *)className;

/**
 * Get Entity Descriptor based on table name.
 * @param tableName Name of Table.
 * @return Entity Descriptor.
 */
- (SICEntityDescriptor *)getEntityDescriptorBasedOnTableName:(NSString *)tableName;

/**
 * Get Mapped Native Model Class Name.
 * @param hybridClassName Hybrid Model Class Name.
 * @return Native Model Class Name.
 */
- (NSString *)getMappedNativeClassName:(NSString *)hybridClassName;

/**
 * Get Mapped Hybrid Model Class Name.
 * @param nativeClassName Native Model Class Name.
 * @return Hybrid Model Class Name.
 */
- (NSString *)getMappedHybridClassName:(NSString *)nativeClassName;

/**
 * Map Native Model Class Name with Hybrid Model Class Name.
 */
- (void)synchronizeEntityDescriptors;


/**
 * Generates Hybrid Database Descriptor
 * @param databaseDescriptor Database Descriptor.
 * @return Hybrid Application Descriptor.
 */
- (SIHHybridSiminovData *)generateHybridDatabaseDescriptor:(SICDatabaseDescriptor *)databaseDescriptor;


/**
 * Generate Hybrid Entity Descriptor.
 * @param entityDescriptor Entity Descriptor.
 * @return Hybrid Entity Descriptor.
 */
- (SIHHybridSiminovData *)generateHybridEntityDescriptor:(SICEntityDescriptor *)entityDescriptor;

/**
 * Generate Hybrid Entity Descriptor Column.
 * @param attribute Entity Descriptor Column.
 * @return Hybrid Siminov Data.
 */
- (SIHHybridSiminovData *)generateHybridEntityDescriptorColumn:(SICAttribute *)attribute;


/**
 * Generate Hybrid Entity Descriptor Index.
 * @param index Entity Descriptor Index.
 * @return Hybrid Entity Descriptor Index.
 */
- (SIHHybridSiminovData *)generateHybridEntityDescriptorIndex:(SICIndex *)index;


/**
 * Generate Hybrid Entity Descriptor Relationship.
 * @param relationship Entity Descriptor Relationship.
 * @return Hybrid Entity Descriptor Relationship.
 */
- (SIHHybridSiminovData *)generateHybridEntityDescriptorRelationship:(SICRelationship *)relationship;


/**
 * Generate Hybrid Library Descriptor.
 * @param libraryDescriptor Library Descriptor.
 * @return Hybrid Library Descriptor.
 */
- (SIHHybridSiminovData *)generateHybridLibraryDescriptor:(SICLibraryDescriptor *)libraryDescriptor;


/**
 * Generate Hybrid Adapter.
 * @param adapterDescriptor Adapter.
 * @return Hybrid Adapter.
 */
- (SIHHybridSiminovData *)generateHybridAdapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor;


/**
 * Generate Hybrid Handler.
 * @param handler Handler.
 * @return Hybrid Handler.
 */
- (SIHHybridSiminovData *)generateHybridHandler:(SIHHandler *)handler;

- (SIHHybridSiminovData *)generateHybridConnectionRequest:(id<SIKIConnectionRequest>)connectionRequest;


- (SIHHybridSiminovData *)generateHybridConnectionResponse:(id<SIKIConnectionResponse>)connectionResponse;

- (SIHHybridSiminovData *)generateHybridRegistration:(id<SIKIRegistration>)registration;

- (SIHHybridSiminovData *)generateHybridMessage:(id<SIKIMessage>)message;


- (SIHHybridSiminovData *)generateHybridNotificationException:(SIKNotificationException *)notificationException;


- (SIHHybridSiminovData *)generateHybridSyncRequest:(id<SIKISyncRequest>)syncRequest;

@end
