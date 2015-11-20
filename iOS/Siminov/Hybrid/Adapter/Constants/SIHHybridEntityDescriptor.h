//
//  SIHHybridEntityDescriptor.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

/**
 * Exposes constants which represents Entity Descriptor on Web.
 */

#import <Foundation/Foundation.h>


/**
 * Hybrid Module Entity Descriptor Function Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_ENTITY_DESCRIPTOR = @"EntityDescriptor";


/**
 * Hybrid Entity Descriptor Table Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_TABLE_NAME = @"tableName";

/**
 * Hybrid Entity Descriptor Class Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_CLASS_NAME = @"className";


/**
 * Hybrid Entity Descriptor Columns.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_ENTITIES = @"Array";

/**
 * Hybrid Entity Descriptor Column.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_ATTRIBUTE = @"EntityDescriptor.Attribute";


/**
 * Hybrid Entity Descriptor Column Variable Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_VARIABLE_NAME = @"variableName";

/**
 * Hybrid Entity Descriptor Column Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_COLUMN_NAME = @"columnName";

/**
 * Hybrid Entity Descriptor Column Type.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_TYPE = @"type";

/**
 * Hybrid Entity Descriptor Column Is Primary.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_PRIMARY_KEY = @"primaryKey";

/**
 * Hybrid Entity Descriptor Column Is Not Null.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_NOT_NULL = @"notNull";

/**
 * Hybrid Entity Descriptor Column Is Unique.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_UNIQUE = @"unique";

/**
 * Hybrid Entity Descriptor Column Check Condition.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_CHECK = @"check";

/**
 * Hybrid Entity Descriptor Column Default Value.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_DEFAULT = @"defaultValue";


/**
 * Hybrid Entity Descriptor Index's.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_INDEXS = @"Array";

/**
 * Hybrid Entity Descriptor Index Function.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_INDEX = @"EntityDescriptor.Index";

/**
 * Hybrid Entity Descriptor Index Name.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_INDEX_NAME = @"name";

/**
 * Hybrid Entity Descriptor Column.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_INDEX_UNIQUE = @"unique";

/**
 * Hybrid Entity Descriptor Index Column.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_INDEX_COLUMN = @"indexColumn";


/**
 * Hybrid Entity Descriptor Relationships.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIPS = @"Array";

/**
 * Hybrid Entity Descriptor Relationship Function.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIP = @"EntityDescriptor.Relationship";

/**
 * Hybrid Entity Descriptor Relationship Type.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE = @"relationshipType";

/**
 * Hybrid Entity Descriptor Relationship Refer.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_REFER = @"refer";

/**
 * Hybrid Entity Descriptor Relationship Refer To.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_REFER_TO = @"referTo";


/**
 * Hybrid Entity Descriptor Relationship On Update.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_ON_UPDATE = @"onUpdate";

/**
 * Hybrid Entity Descriptor Relationship On Delete.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_ON_DELETE = @"onDelete";

/**
 * Hybrid Entity Descriptor Relationship Load Property.
 */
static NSString * const HYBRID_ENTITY_DESCRIPTOR_LOAD = @"load";
