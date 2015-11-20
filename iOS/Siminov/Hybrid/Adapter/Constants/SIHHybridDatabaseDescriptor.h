//
//  SIHHybridDatabaseDescriptor.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


/**
 * Exposes constants which represents Database Descriptor on Hybrid.
 */

#import <Foundation/Foundation.h>


/**
 * Hybrid Module Database Descriptor Function Name.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_DATABASE_DESCRIPTOR = @"DatabaseDescriptor";


/**
 * Hybrid Database Descriptor Database Name.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_NAME = @"databaseName";

/**
 * Hybrid Database Descriptor Description.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_DESCRIPTION = @"description";


/**
 * Hybrid Database Descriptor Is Locking Required.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_IS_TRANSACTION_SAFE = @"transactionSafe";

/**
 * Hybrid Database Descriptor External Storage.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_EXTERNAL_STORAGE = @"externalStorage";


/**
 * Hybrid Database Descriptor Entity Descriptor Paths.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_ENTITY_DESCRIPTORS = @"Array";

/**
 * Hybrid Database Descriptor Entity Descriptor Path.
 */
static NSString * const HYBRID_DATABASE_DESCRIPTOR_ENTITY_DESCRIPTOR_PATH = @"entityDescriptorPath";