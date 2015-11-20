//
//  SIHDatabaseHandler.m
//  Hybrid
//
//  Created by user on 18/05/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIHDatabaseHandler.h"

#import "SIHHybridSiminovDatas.h"
#import "SIHHybridSiminovDataReader.h"
#import "SICEntityDescriptor.h"
#import "SICDatabaseDescriptor.h"
#import "SICDatabaseBundle.h"
#import "SICResourceManager.h"
#import "SIHResourceManager.h"
#import "SIHHybridSiminovDataWritter.h"
#import "SIHConstants.h"
#import "SIHHybridSiminovDatas.h"
#import "SIHHybridAdapter.h"
#import "SIHIHandler.h"
#import "SIHSiminovHandler.h"
#import "SIHUtils.h"


@implementation SIHDatabaseHandler

static SICResourceManager *coreResourceManager;
static SIHResourceManager *hybridResourceManager;

+(void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    hybridResourceManager = [SIHResourceManager getInstance];
}

- (void)save:(NSString *)data {
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [self parseHybridSiminovDatas:data];
    [SIHDatabaseHandler saveDatas:hybridSiminovDatas];
    
}

+ (void)saveDatas:(SIHHybridSiminovDatas *)hybridSiminovDatas {
    
    NSEnumerator *hybridDatas = [hybridSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridData;
    
    while(hybridData = [hybridDatas nextObject]) {
        [self saveData:hybridData parentObject:nil];
    }
    
}

+ (void)saveData:(SIHHybridSiminovData *)hybridSiminovData parentObject:(SIHHybridSiminovData *)parentHybridSiminovDatas {
    
    NSString *className = [hybridSiminovData getDataType];
    NSEnumerator *hybridValues = [hybridSiminovData getValues];
    
    NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
    SIHHybridSiminovValue *hybridSiminovValue;
    
    while(hybridSiminovValue = [hybridValues nextObject]) {
        [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
    }
    
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:className];
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    NSString *tableName = [entityDescriptor getTableName];
    
    NSMutableArray *columnNames = [[NSMutableArray alloc] init];
    NSMutableArray *columnValues = [[NSMutableArray alloc] init];
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        NSString *columnName = [attribute getColumnName];
        id columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName] ] getValue];
        
        [columnNames addObject:columnName];
        
        if(columnValue) {
            [columnValues addObject:columnValue];
        } else {
            [columnValues addObject:[NSNull null]];
        }
    }
    
    [SIHRelationshipHelper processRelationship:hybridSiminovData parentObject:parentHybridSiminovDatas columnNames:columnNames columnValues:columnValues];
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:tableName forKey:FORM_SAVE_BIND_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:[columnNames objectEnumerator] forKey:FORM_SAVE_BIND_QUERY_COLUMN_NAMES_PARAMETER];
    
    NSString *query = [queryBuilder formSaveBindQuery:parameters];
    [database executeBindQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query columnValues:[columnValues objectEnumerator]];
    
    
    /*
     * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
     */
    NSEnumerator *relationships = [entityDescriptor getRelationships];
    SICRelationship *relationship;
    
    while(relationship = [relationships nextObject]) {
        
        bool isLoad = [relationship isLoad];
        if(!isLoad) {
            continue;
        }
        
        NSString *relationshipType = [relationship getRelationshipType];
        if(relationshipType == nil || [relationshipType length] <= 0) {
            continue;
        }
        
        if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_ONE] == NSOrderedSame) {
            
            SIHHybridSiminovData *referedData = nil;
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    referedData = data;
                    break;
                }
            }
            
            
            if(referedData == nil) {
                continue;
            }
            
            [SIHDatabaseHandler saveData:referedData parentObject:nil];
        } else if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_MANY] == NSOrderedSame) {
            
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    [data addData:hybridSiminovData];
                    [self saveData:data parentObject:nil];
                }
            }
            
        } else if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_MANY_TO_MANY] == NSOrderedSame) {
            
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    [data addData:hybridSiminovData];
                    [SIHDatabaseHandler saveData:data parentObject:nil];
                }
            }
        }
    }
}



- (void)update:(NSString *)data {
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [self parseHybridSiminovDatas:data];
    [SIHDatabaseHandler updateDatas:hybridSiminovDatas];
    
}

+ (void)updateDatas:(SIHHybridSiminovDatas *)hybridSiminovDatas {
    
    NSEnumerator *hybridDatas = [hybridSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridData;
    
    while(hybridData = [hybridDatas nextObject]) {
        [SIHDatabaseHandler updateData:hybridData parentObject:nil];
    }
    
}

+ (void)updateData:(SIHHybridSiminovData *)hybridSiminovData parentObject:(SIHHybridSiminovData *)parentHybridSiminovData {
    
    NSString *className = [hybridSiminovData getDataType];
    NSEnumerator *hybridSiminovValue = [hybridSiminovData getValues];
    
    NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
    SIHHybridSiminovValue *hybridValue;
    
    while(hybridValue = [hybridSiminovValue nextObject]) {
        [hybridSiminovValues setValue:hybridValue forKey:[hybridValue getType]];
    }
    
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:className];
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    
    NSMutableString *whereClause = [[NSMutableString alloc] init];
    NSString *tableName = [entityDescriptor getTableName];
    
    NSMutableArray *columnNames = [[NSMutableArray alloc] init];
    NSMutableArray *columnValues = [[NSMutableArray alloc] init];
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        NSString *columnName = [attribute getColumnName];
        id columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
        
        [columnNames addObject:columnName];
        
        if(columnValue) {
            [columnValues addObject:columnValue];
        } else {
            [columnValues addObject:[NSNull null]];
        }
        
        if([attribute isPrimaryKey]) {
            if([whereClause length] == 0) {
                [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
            } else {
                [whereClause appendString:[NSString stringWithFormat:@" AND %@ = '%@'", columnName, columnValue]];
            }
        }
    }
    
    
    [SIHRelationshipHelper processRelationship:hybridSiminovData parentObject:parentHybridSiminovData whereClause:whereClause];
    [SIHRelationshipHelper processRelationship:hybridSiminovData parentObject:parentHybridSiminovData columnNames:columnNames columnValues:columnValues];
    
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:tableName forKey:FORM_UPDATE_BIND_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:[columnNames objectEnumerator] forKey:FORM_UPDATE_BIND_QUERY_COLUMN_NAMES_PARAMETER];
    [parameters setValue:(NSString *)whereClause forKey:FORM_UPDATE_BIND_QUERY_WHERE_CLAUSE_PARAMETER];
    
    
    NSString *query = [queryBuilder formUpdateBindQuery:parameters];
    
    NSEnumerator *values = [columnValues objectEnumerator];
    [database executeBindQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query columnValues:values];
    
    
    
    /*
     * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
     */
    NSEnumerator *relationships = [entityDescriptor getRelationships];
    SICRelationship *relationship;
    
    while(relationship = [relationships nextObject]) {
        bool isLoad = [relationship isLoad];
        if(!isLoad) {
            continue;
        }
        
        NSString *relationshipType = [relationship getRelationshipType];
        if(relationshipType == nil || [relationshipType length] <= 0) {
            continue;
        }
        
        if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_ONE] == NSOrderedSame) {
            
            SIHHybridSiminovData *referedData = nil;
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    referedData = data;
                    break;
                }
            }
            
            
            if(referedData == nil) {
                continue;
            }
            
            [SIHDatabaseHandler updateData:referedData parentObject:nil];
        } else if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_MANY] == NSOrderedSame) {
            
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    [data addData:hybridSiminovData];
                    [SIHDatabaseHandler updateData:data parentObject:nil];
                }
            }
            
        } else if([relationshipType caseInsensitiveCompare:ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_MANY_TO_MANY] == NSOrderedSame) {
            
            NSEnumerator *datas = [hybridSiminovData getDatas];
            SIHHybridSiminovData *data;
            
            while(data = [datas nextObject]) {
                
                NSString *mappedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
                if([mappedClassName caseInsensitiveCompare:[relationship getReferTo]] == NSOrderedSame) {
                    [data addData:hybridSiminovData];
                    [SIHDatabaseHandler updateData:data parentObject:nil];
                }
            }
        }
    }
    
}


- (void)saveOrUpdate:(NSString *)data {
    
    data = [data stringByReplacingOccurrencesOfString:@"'"
                                           withString:@"\""];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [self parseHybridSiminovDatas:data];
    [SIHDatabaseHandler saveOrUpdateDatas:hybridSiminovDatas];
    
}

+ (void)saveOrUpdateDatas:(SIHHybridSiminovDatas *)hybridSiminovDatas {
    
    NSEnumerator *hybridDatas = [hybridSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridSiminovData;
    
    while(hybridSiminovData = [hybridDatas nextObject]) {
        [SIHDatabaseHandler saveOrUpdateData:hybridSiminovData parentObject:nil];
    }
}

+ (void)saveOrUpdateData:(SIHHybridSiminovData *)hybridSiminovData parentObject:(SIHHybridSiminovData *)parentHybridSiminovData {
    
    NSString *className = [hybridSiminovData getDataType];
    NSEnumerator *hybridValues = [hybridSiminovData getValues];
    
    NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
    SIHHybridSiminovValue *hybridSiminovValue;
    
    while(hybridSiminovValue = [hybridValues nextObject]) {
        [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
    }
    
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSMutableString *whereClause = [[NSMutableString alloc] init];
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        
        if([attribute isPrimaryKey]) {
            
            NSString *columnName = [attribute getColumnName];
            id columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
            
            if([whereClause length] <= 0) {
                [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
            } else {
                [whereClause appendString:[NSString stringWithFormat:@" AND %@= '%@'", columnName, columnValue]];
            }
        }
        
    }
    
    
    [SIHRelationshipHelper processRelationship:hybridSiminovData parentObject:nil whereClause:whereClause];
    
    if(whereClause == nil || [whereClause length] <= 0) {
        [SIHDatabaseHandler saveData:hybridSiminovData parentObject:parentHybridSiminovData];
        return;
    }
    
    
    /*
     * 4. IF EXISTS: call update method, ELSE: call save method.
     */
    int count = [SIHDatabaseHandler countRaw:entityDescriptor column:nil distinct:false whereClause:(NSString *)whereClause groupBys:nil having:nil];
    if(count <= 0) {
        [SIHDatabaseHandler saveData:hybridSiminovData parentObject:parentHybridSiminovData];
    } else {
        [SIHDatabaseHandler updateData:hybridSiminovData parentObject:parentHybridSiminovData];
    }
}



- (void)delete:(NSString *)className whereClause:(NSString *)whereClause data:(NSString *)data {
    
    SIHHybridSiminovDatas *hybridSiminovDatas = nil;
    
    if(data != nil && [data length] > 0) {
        hybridSiminovDatas = [self parseHybridSiminovDatas:data];
        
        [self delete:hybridSiminovDatas];
        return;
    }
    
    [SIHDatabaseHandler deleteData:nil parentObject:nil whereClause:whereClause];
    
}


+ (void)deleteDatas:(SIHHybridSiminovDatas *)hybridSiminovDatas {
    
    NSEnumerator *hybridDatas = [hybridSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridSiminovData;
    
    while(hybridSiminovData = [hybridDatas nextObject]) {
        
        NSString *className = [hybridSiminovData getDataType];
        NSEnumerator *hybridValues = [hybridSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
        
        NSMutableString *whereClause = [[NSMutableString alloc] init];
        
        NSMutableArray *columnNames = [[NSMutableArray alloc] init];
        NSMutableArray *columnValues = [[NSMutableArray alloc] init];
        
        NSEnumerator *attributes = [entityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [attributes nextObject]) {
            NSString *columnName = [attribute getColumnName];
            id columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
            
            [columnNames addObject:columnName];
            [columnValues addObject:columnValue];
            
            if([attribute isPrimaryKey]) {
                if([whereClause length] == 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@= '%@'", columnName, columnValue]];
                }
            }
        }
        
        
        [SIHRelationshipHelper processRelationship:hybridSiminovData parentObject:nil whereClause:whereClause];
        
        [SIHDatabaseHandler deleteData:hybridSiminovData parentObject:nil whereClause:(NSString *)whereClause];
    }
}


+ (void)deleteData:(SIHHybridSiminovData *)hybridSiminovData parentObject:(SIHHybridSiminovData *)parentHybridSiminovData whereClause:(NSString *)whereClause {
    
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:[hybridSiminovData getDataType]];
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[hybridSiminovData getDataType]];
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_DELETE_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:(NSString *)whereClause forKey:FORM_DELETE_QUERY_WHERE_CLAUSE_PARAMETER];
    
    
    NSString *query = [queryBuilder formDeleteQuery:parameters];
    [database executeQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    
}


- (NSString *)select:(NSString *)className distinct:(NSString *)distinct whereClause:(NSString *)whereClause columnNames:(NSEnumerator *)columnNames groupBys:(NSEnumerator *)groupBys havingClause:(NSString *)havingClause orderBy:(NSEnumerator *)orderBy whichOrderBy:(NSString *)whichOrderBy limit:(NSString *)limit {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    SIHHybridSiminovDatas *hybridSiminovDatas = [SIHDatabaseHandler selectDatas:entityDescriptor distinct:distinct whereClause:whereClause columnNames:columnNames groupBys:groupBys havingClause:havingClause orderBy:orderBy whichOrderBy:whichOrderBy limit:limit];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
}


+ (SIHHybridSiminovDatas *)selectDatas:(SICEntityDescriptor *)entityDescriptor distinct:(bool)distinct whereClause:(NSString *)whereClause columnNames:(NSEnumerator *)columnNames groupBys:(NSEnumerator *)groupBys havingClause:(NSString *)havingClause orderBy:(NSEnumerator *)orderBy whichOrderBy:(NSString *)whichOrderBy limit:(NSString *)limit {
    
    NSString *className = [entityDescriptor getClassName];
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:className];
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", className]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", className]];
    }
    
    NSMutableArray *columnNameCollection = [[NSMutableArray alloc] init];
    NSMutableArray *groupByCollection = [[NSMutableArray alloc] init];
    NSMutableArray *orderByCollection = [[NSMutableArray alloc] init];
    
    
    NSString *whereCondition = @"";
    if(whereClause != nil && [whereClause length] > 0) {
        whereCondition = [SIHUtils stringByDecodingURLFormat:whereClause];
    }
    
    if(columnNames != nil) {
        
        NSString *columnName;
        while(columnName = [columnNames nextObject]) {
            
            if(columnName == nil || [columnName caseInsensitiveCompare:HYBRID_UNDEFINED] == NSOrderedSame) {
                columnName = [[NSString alloc] init];
            }
            
            [columnNameCollection addObject:columnName];
        }
    }
    
    
    if(groupBys != nil) {
        
        NSString *groupBy;
        while(groupBy = [groupBys nextObject]) {
            
            if(groupBy == nil || [groupBy caseInsensitiveCompare:HYBRID_UNDEFINED] == NSOrderedSame) {
                groupBy = [[NSString alloc] init];
            }
            
            [groupByCollection addObject:groupBy];
        }
    }
    
    
    if(orderBy != nil) {
        
        NSString *currentOrderBy;
        while(currentOrderBy = [orderBy nextObject]) {
            
            if(currentOrderBy == nil || [currentOrderBy caseInsensitiveCompare:HYBRID_UNDEFINED] == NSOrderedSame) {
                currentOrderBy = [[NSString alloc] init];
            }
            
            [orderByCollection addObject:currentOrderBy];
        }
    }
    
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_SELECT_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:false forKey:FORM_SELECT_QUERY_DISTINCT_PARAMETER];
    [parameters setValue:whereCondition forKey:FORM_SELECT_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[columnNameCollection objectEnumerator] forKey:FORM_SELECT_QUERY_COLUMN_NAMES_PARAMETER];
    [parameters setValue:[groupByCollection objectEnumerator] forKey:FORM_SELECT_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:havingClause forKey:FORM_SELECT_QUERY_HAVING_PARAMETER];
    [parameters setValue:[orderByCollection objectEnumerator] forKey:FORM_SELECT_QUERY_ORDER_BYS_PARAMETER];
    [parameters setValue:nil forKey:FORM_SELECT_QUERY_WHICH_ORDER_BY_PARAMETER];
    [parameters setValue:limit forKey:FORM_SELECT_QUERY_LIMIT_PARAMETER];
    
    NSString *query = [queryBuilder formSelectQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:[SIHDatabaseHandler getDatabaseDescriptor:className] entityDescriptor:entityDescriptor query:query];
    NSMutableArray *datasBundle = [[NSMutableArray alloc] init];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        [datasBundle addObject:data];
    }
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [SIHDatabaseHandler parseData:entityDescriptor values:[datasBundle objectEnumerator]];
    datas = [datasBundle objectEnumerator];
    
    NSEnumerator *siminovDatas = [hybridSiminovDatas getHybridSiminovDatas];
    NSMutableDictionary *siData;
    SIHHybridSiminovData *siminovData;
    
    while((siminovData = [siminovDatas nextObject]) && (siData = [datas nextObject])) {
        
        [SIHRelationshipHelper processRelationship:siminovData parentObject:nil];
        [SIHRelationshipHelper processRelationship:siminovData parentObject:nil data:siData];
    }
    
    
    return hybridSiminovDatas;
}



- (NSString *)selectManual:(NSString *)className query:(NSString *)query {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:className];
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", className]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", className]];
    }
    
    
    NSEnumerator* datas = [database executeSelectQuery:[SIHDatabaseHandler getDatabaseDescriptor:className] entityDescriptor:entityDescriptor query:query];
    NSMutableArray *datasBundle = [[NSMutableArray alloc] init];
    NSMutableDictionary *dataBundle;
    
    while(dataBundle = [datas nextObject]) {
        [datasBundle addObject:dataBundle];
    }
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [SIHDatabaseHandler parseData:entityDescriptor values:[datasBundle objectEnumerator]];
    datas = [datasBundle objectEnumerator];
    
    NSEnumerator *siminovDatas = [hybridSiminovDatas getHybridSiminovDatas];
    NSMutableDictionary *data;
    SIHHybridSiminovData *siminovData;
    
    while((siminovData = [siminovDatas nextObject]) && (data = [datas nextObject])) {
        
        [SIHRelationshipHelper processRelationship:siminovData parentObject:nil];
        [SIHRelationshipHelper processRelationship:siminovData parentObject:nil data:data];
    }
    
    
    NSString *jsonData = nil;
    @try {
        jsonData = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return jsonData;
}



- (void)beginTransaction:(NSString *)databaseDescriptorName {
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:databaseDescriptorName];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"beginTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"beginTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
    }
    
    [database executeMethod:SQLITE_DATABASE_BEGIN_TRANSACTION parameter:nil];
    
}


- (NSString *)beginTransactionAsync:(NSString *)databaseDescriptorName data:(NSString *)data {
    
    SIHHybridSiminovDatas *hybridResponseDatas = [[SIHHybridSiminovDatas alloc] init];
    
    [self beginTransaction:databaseDescriptorName];
    
    SIHHybridSiminovDatas *hybridSiminovDatasArray = [self parseHybridSiminovDatas:data];
    NSEnumerator *hybridSiminovData = [hybridSiminovDatasArray getHybridSiminovDatas];
    SIHHybridSiminovData *siminovData;
    
    while(siminovData = [hybridSiminovData nextObject]) {
        
        SIHHybridSiminovData *datas = [siminovData getHybridSiminovDataBasedOnDataType:ADAPTER];
        
        SIHHybridSiminovValue *hybridRequestId = [datas getValueBasedOnType:REQUEST_ID];
        SIHHybridSiminovValue *hybridAdapterName = [datas getValueBasedOnType:ADAPTER_NAME];
        SIHHybridSiminovValue *hybridHandlerName = [datas getValueBasedOnType:HANDLER_NAME];
        
        SIHHybridSiminovValue *hybridParameters = [datas getValueBasedOnType:PARAMETERS];
        
        NSString *requestId = [hybridRequestId getValue];
        
        NSString *adapterName = [hybridAdapterName getValue];
        NSString *handlerName = [hybridHandlerName getValue];
        
        NSString *parameters = [hybridParameters getValue];
        
        SIHSiminovHandler *siminovHandler = (SIHSiminovHandler *) [[SIHAdapterHandler getInstance] getHandler];
        NSString *response = [siminovHandler processHandler:[NSString stringWithFormat:@"%@.%@", adapterName, handlerName] data:parameters];
        if(response == nil || [response length] <= 0) {
            continue;
        }
        
        SIHHybridSiminovData *selectResponse = [[SIHHybridSiminovData alloc] init];
        [selectResponse setDataType:requestId];
        [selectResponse setDataValue:[SIHUtils stringByEncodeingURLFormat:response]];
        
        [hybridResponseDatas addHybridSiminovData:selectResponse];
    }
    
    [self commitTransaction:databaseDescriptorName];
    
    
    NSString *response = nil;
    @try {
        response = [SIHHybridSiminovDataWritter jsonBuidler:hybridResponseDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", databaseDescriptorName]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"select" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
    }
    
    return response;
}

- (void)commitTransaction:(NSString *)databaseDescriptorName {
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:databaseDescriptorName];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"commitTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"commitTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
    }
    
    [database executeMethod:SQLITE_DATABASE_COMMIT_TRANSACTION parameter:nil];
    
}



- (void)endTransaction:(NSString *)databaseDescriptorName {
    
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:databaseDescriptorName];
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"endTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"endTransaction" message:[NSString stringWithFormat:@"No Database Instance Found For CLASS: %@", databaseDescriptorName]];
    }
    
    @try {
        [database executeMethod:SQLITE_DATABASE_END_TRANSACTION parameter:nil];
    } @catch(SICDatabaseException *databaseException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"endTransaction" message:[NSString stringWithFormat:@"DatabaseException caught while executing end transaction method, %@", [databaseException getMessage]]];
    }
}


- (NSString *)count:(NSString *)className column:(NSString *)column distinct:(NSString *)distinct whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    int count = [SIHDatabaseHandler countRaw:entityDescriptor column:column distinct:[distinct boolValue] whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", count]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"count" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"count" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (int)countRaw:(SICEntityDescriptor *)entityDescriptor column:(NSString *)column distinct:(bool)distinct whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"count" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"count" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    
    /*
     * Add Parameters
     */
    
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_COUNT_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:nil forKey:FORM_COUNT_QUERY_COLUMN_PARAMETER];
    [parameters setValue:false forKey:FORM_COUNT_QUERY_DISTINCT_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_COUNT_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:nil forKey:FORM_COUNT_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:nil forKey:FORM_COUNT_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formCountQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
}


- (NSString *)avg:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    int avg = [SIHDatabaseHandler avgRaw:entityDescriptor columnName:columnName whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", avg]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"avg" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"avg" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (int)avgRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBy having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [self getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"avg" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"avg" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    
    NSMutableArray *groupBys = [[NSMutableArray alloc] init];
    if(groupBy != nil) {
        
        NSString *currentGroupBy;
        while(currentGroupBy = [groupBy nextObject]) {
            [groupBys addObject:currentGroupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_AVG_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_AVG_QUERY_COLUMN_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_AVG_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[groupBys objectEnumerator] forKey:FORM_AVG_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_AVG_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formAvgQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
    
}


- (NSString *)sum:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    int sum =  [SIHDatabaseHandler sumRaw:entityDescriptor columnName:columnName whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", sum]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"sum" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"sum" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}


+ (int)sumRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"sum" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"sum" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    NSMutableArray *allGroupBys =  [[NSMutableArray alloc] init];
    if(groupBys != nil) {
        
        NSString *groupBy;
        while(groupBy = [groupBys nextObject]) {
            [allGroupBys addObject:groupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_SUM_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_SUM_QUERY_COLUMN_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_SUM_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[allGroupBys objectEnumerator] forKey:FORM_SUM_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_SUM_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formSumQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
    
}


- (NSString *)total:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *) groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    int total = [SIHDatabaseHandler totalRaw:entityDescriptor columnName:columnName whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", total]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"total" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"total" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", siminovException.getMessage]];
    }
    
    return data;
    
}



+ (int const)totalRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [self getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"total" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"total" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    NSMutableArray *allGroupBys = [[NSMutableArray alloc] init];
    if(groupBys != nil) {
        
        NSString *groupBy;
        while(groupBy = [groupBys nextObject]) {
            [allGroupBys addObject:groupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_TOTAL_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_TOTAL_QUERY_COLUMN_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_TOTAL_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[allGroupBys objectEnumerator] forKey:FORM_TOTAL_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_TOTAL_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formTotalQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
    
}



- (NSString *)min:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    int min = [SIHDatabaseHandler minRaw:entityDescriptor columnName:columnName whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", min]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"min" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"min" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (int const)minRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [self getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"min" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"min" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    NSMutableArray *allGroupBys = [[NSMutableArray alloc] init];
    if(groupBys != nil) {
        
        NSString *currentGroupBy;
        while(currentGroupBy = [groupBys nextObject]) {
            [allGroupBys addObject:currentGroupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_MIN_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_MIN_QUERY_COLUMN_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_MIN_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[allGroupBys objectEnumerator] forKey:FORM_MIN_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_MIN_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formMinQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
    
}



- (NSString *)max:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    int max = [self maxRaw:entityDescriptor columnName:columnName whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:[NSString stringWithFormat:@"%i", max]];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"avg" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"max" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}


- (int const)maxRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"max" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"max" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    NSMutableArray *allGroupBys = [[NSMutableArray alloc] init];
    if(groupBys != nil) {
        
        NSString *groupBy;
        while(groupBy = [groupBys nextObject]) {
            [allGroupBys addObject:groupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_MAX_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_MAX_QUERY_COLUMN_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_MAX_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[allGroupBys objectEnumerator] forKey:FORM_MAX_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_MAX_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formMaxQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            
            if([value isKindOfClass:[NSNumber class]]) {
                const char *objectType = [value objCType];
                
                if (strcmp(objectType, @encode(int)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(long)) == 0) {
                    return [value intValue];
                } else if(strcmp(objectType, @encode(float)) == 0) {
                    return [value floatValue];
                }
            }
        }
    }
    
    return 0;
    
}




- (NSString *)groupConcat:(NSString *)className columnName:(NSString *)columnName delimiter:(NSString *)delimiter whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSString *groupConcat = [SIHDatabaseHandler groupConcatRaw:entityDescriptor columnName:columnName delimiter:delimiter whereClause:whereClause groupBys:groupBys having:having];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:groupConcat];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"groupConcat" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"groupConcat" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (NSString *)groupConcatRaw:(SICEntityDescriptor *)entityDescriptor columnName:(NSString *)columnName delimiter:(NSString *)delimiter whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having {
    
    SICDatabaseDescriptor *databaseDescriptor = [SIHDatabaseHandler getDatabaseDescriptor:[entityDescriptor getClassName]];
    SICDatabaseBundle *databaseBundle = [coreResourceManager getDatabaseBundle:[databaseDescriptor getDatabaseName]];
    
    id<SICIDatabaseImpl> database = [databaseBundle getDatabase];
    id<SICIQueryBuilder> queryBuilder = [databaseBundle getQueryBuilder];
    
    if(database == nil) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"groupConcat" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"groupConcat" message:[NSString stringWithFormat:@"No Database Instance Found For ENTITY-DESCRIPTOR: %@", [entityDescriptor getClassName]]];
    }
    
    NSMutableArray *allGroupBys = [[NSMutableArray alloc] init];
    if(groupBys != nil) {
        
        NSString *groupBy;
        while(groupBy = [groupBys nextObject]) {
            [allGroupBys addObject:groupBy];
        }
    }
    
    
    /*
     * Add Parameters
     */
    
    NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
    [parameters setValue:[entityDescriptor getTableName] forKey:FORM_GROUP_CONCAT_QUERY_TABLE_NAME_PARAMETER];
    [parameters setValue:columnName forKey:FORM_GROUP_CONCAT_QUERY_COLUMN_PARAMETER];
    [parameters setValue:delimiter forKey:FORM_GROUP_CONCAT_QUERY_DELIMITER_PARAMETER];
    [parameters setValue:whereClause forKey:FORM_GROUP_CONCAT_QUERY_WHERE_CLAUSE_PARAMETER];
    [parameters setValue:[allGroupBys objectEnumerator] forKey:FORM_GROUP_CONCAT_QUERY_GROUP_BYS_PARAMETER];
    [parameters setValue:having forKey:FORM_GROUP_CONCAT_QUERY_HAVING_PARAMETER];
    
    
    NSString *query = [queryBuilder formGroupConcatQuery:parameters];
    
    NSEnumerator *datas = [database executeSelectQuery:databaseDescriptor entityDescriptor:entityDescriptor query:query];
    NSMutableDictionary *data;
    
    while(data = [datas nextObject]) {
        NSArray *parse = [data allValues];
        
        NSEnumerator *values = [parse objectEnumerator];
        id value;
        
        while(value = [values nextObject]) {
            return (NSString *) value;
        }
    }
    
    return @"";
    
}



- (NSString *)getTableName:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSString *tableName = [SIHDatabaseHandler getTableNameRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
    
    [siminovData setDataValue:tableName];
    
    [hybridSiminovDatas addHybridSiminovData:siminovData];
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getTableName" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getTableName" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", siminovException.getMessage]];
    }
    
    return data;
    
}

+ (NSString *)getTableNameRaw:(SICEntityDescriptor *)entityDescriptor {
    return [entityDescriptor getTableName];
}


- (NSString *)getColumnNames:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSEnumerator *columnNames = [SIHDatabaseHandler getColumnNamesRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *columnName;
    
    while(columnName = [columnNames nextObject]) {
        SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
        [siminovData setDataValue:columnName];
        
        [hybridSiminovDatas addHybridSiminovData:siminovData];
    }
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getTableName" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getTableName" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (NSEnumerator *)getColumnNamesRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    
    NSMutableArray *columnNames = [[NSMutableArray alloc] init];
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        [columnNames addObject:[attribute getColumnName]];
    }
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [self getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnNames addObject:[attribute getColumnName]];
            }
        }
    }
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnNames addObject:[attribute getColumnName]];
            }
        }
    }
    
    
    return [columnNames objectEnumerator];
    
}



- (NSString *)getColumnTypes:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    return [SIHDatabaseHandler getColumnTypeRaw:entityDescriptor];
    
}



+ (NSString *)getColumnTypeRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSMutableDictionary *columnTypes = [[NSMutableDictionary alloc] init];
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        [columnTypes setValue:[attribute getType] forKey:[attribute getColumnName]];
    }
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnTypes setValue:[attribute getType] forKey:[attribute getColumnName]];
            }
        }
    }
    
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnTypes setValue:[attribute getType] forKey:[attribute getColumnName]];
            }
        }
    }
    
    
    NSMutableArray *values = [[NSMutableArray alloc] init];
    [values addObject:columnTypes];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [self parseData:nil values:[values objectEnumerator]];
    
    NSString *returnData = nil;
    @try {
        returnData = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getColumnTypes" message:[NSString stringWithFormat:@"SiminovException caught while building json output, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getColumnTypes" message:[NSString stringWithFormat:@"SiminovException caught while building json output, %@", [siminovException getMessage]]];
    }
    
    return returnData;
    
}



- (NSString *)getPrimaryKeys:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSEnumerator *primaryKeys = [SIHDatabaseHandler getPrimaryKeysRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *columnName;
    
    while(columnName = [primaryKeys nextObject]) {
        SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
        [siminovData setDataValue:columnName];
        
        [hybridSiminovDatas addHybridSiminovData:siminovData];
    }
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getPrimaryKeys" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getPrimaryKeys" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (NSEnumerator *)getPrimaryKeysRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    NSMutableArray *primaryKeys = [[NSMutableArray alloc] init];
    
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        bool isPrimary = [attribute isPrimaryKey];
        if(isPrimary) {
            [primaryKeys addObject:[attribute getColumnName]];
        }
    }
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [primaryKeys addObject:[attribute getColumnName]];
            }
        }
    }
    
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [primaryKeys addObject:[attribute getColumnName]];
            }
        }
    }
    
    return [primaryKeys objectEnumerator];
    
}



- (NSString *)getMandatoryFields:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSEnumerator *mandatoryFields = [SIHDatabaseHandler getMandatoryFieldsRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *columnName;
    
    while(columnName = [mandatoryFields nextObject]) {
        SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
        [siminovData setDataValue:columnName];
        
        [hybridSiminovDatas addHybridSiminovData:siminovData];
    }
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getMandatoryFields" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getMandatoryFields" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", siminovException.getMessage]];
    }
    
    return data;
    
}



+ (NSEnumerator *)getMandatoryFieldsRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    NSMutableArray *mandatoryFields = [[NSMutableArray alloc] init];
    
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        if([attribute isNotNull]) {
            [mandatoryFields addObject:[attribute getColumnName]];
        }
    }
    
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                if([attribute isNotNull]) {
                    [mandatoryFields addObject:[attribute getColumnName]];
                }
            }
        }
    }
    
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                if([attribute isNotNull]) {
                    [mandatoryFields addObject:[attribute getColumnName]];
                }
            }
        }
    }
    
    return [mandatoryFields objectEnumerator];
    
}



- (NSString *)getUniqueFields:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSEnumerator *uniqueFields = [SIHDatabaseHandler getUniqueFieldsRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *columnName;
    
    while(columnName = [uniqueFields nextObject]) {
        SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
        [siminovData setDataValue:columnName];
        
        [hybridSiminovDatas addHybridSiminovData:siminovData];
    }
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getUniqueFields" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getUniqueFields" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (NSEnumerator *)getUniqueFieldsRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    NSMutableArray *uniqueFields = [[NSMutableArray alloc] init];
    
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        bool isUnique = [attribute isUnique];
        if(isUnique) {
            [uniqueFields addObject:[attribute getColumnName]];
        }
    }
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                
                bool isUnique = [attribute isUnique];
                if(isUnique) {
                    [uniqueFields addObject:[attribute getColumnName]];
                }
            }
        }
    }
    
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                
                bool isUnique = [attribute isUnique];
                if(isUnique) {
                    [uniqueFields addObject:[attribute getColumnName]];
                }
            }
        }
    }
    
    
    return [uniqueFields objectEnumerator];
    
}



- (NSString *)getForeignKeys:(NSString *)className {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:className];
    
    NSEnumerator *foreignKeys = [SIHDatabaseHandler getForeignKeysRaw:entityDescriptor];
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    NSString *columnName;
    
    while(columnName = [foreignKeys nextObject]) {
        SIHHybridSiminovData *siminovData = [[SIHHybridSiminovData alloc] init];
        [siminovData setDataValue:columnName];
        
        [hybridSiminovDatas addHybridSiminovData:siminovData];
    }
    
    
    NSString *data = nil;
    @try {
        data = [SIHHybridSiminovDataWritter jsonBuidler:hybridSiminovDatas];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"getForeignKeys" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"getForeignKeys" message:[NSString stringWithFormat:@"SiminovException caught while building json, %@", [siminovException getMessage]]];
    }
    
    return data;
    
}



+ (NSEnumerator *)getForeignKeysRaw:(SICEntityDescriptor *)entityDescriptor {
    
    NSMutableArray *foreignKeys = [[NSMutableArray alloc] init];
    
    /*
     * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
     */
    NSEnumerator *oneToManyRelationships = [entityDescriptor getManyToOneRelationships];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [self getEntityDescriptor:[oneToManyRelationship getReferTo]];
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [foreignKeys addObject:[attribute getColumnName]];
            }
        }
    }
    
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *parentEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        
        NSEnumerator *parentAttributes = [parentEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [foreignKeys addObject:[attribute getColumnName]];
            }
        }
    }
    
    return [foreignKeys objectEnumerator];
    
}


- (SIHHybridSiminovDatas *)parseHybridSiminovDatas:(NSString *)data {
    
    SIHHybridSiminovDataReader *hybridSiminovDataParser = nil;
    data = [SIHUtils stringByDecodingURLFormat:data];
    
    @try {
        hybridSiminovDataParser = [[SIHHybridSiminovDataReader alloc] initWithData:data];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"parseHybridSiminovDatas" message:[NSString stringWithFormat:@"SiminovException caught while parsing js core data, %@", [siminovException getMessage]]];
        @throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseHybridSiminovDatas" message:[NSString stringWithFormat:@"SiminovException caught while parsing js core data, %@", [siminovException getMessage]]];
    }
    
    return [hybridSiminovDataParser getDatas];
}



+ (SICDatabaseDescriptor *)getDatabaseDescriptor:(NSString *)className {
    return [hybridResourceManager getDatabaseDescriptorBasedOnClassName:className];
}



+ (SICEntityDescriptor *)getEntityDescriptor:(NSString *)className {
    return [hybridResourceManager getEntityDescriptorBasedOnClassName:className];
}


+ (SIHHybridSiminovDatas *)parseData:(SICEntityDescriptor *)entityDescriptor values:(NSEnumerator *)values {
    
    NSMutableArray *tuples = [[NSMutableArray alloc] init];
    SIHHybridSiminovDatas *hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    NSMutableDictionary *value;
    
    while(value = [values nextObject]) {
        SIHHybridSiminovData *hybridSiminovData = [[SIHHybridSiminovData alloc] init];
        [hybridSiminovData setDataType:[hybridResourceManager getMappedHybridClassName:entityDescriptor == nil?@"":[entityDescriptor getClassName]]];
        
        NSEnumerator *keys = [[value allKeys] objectEnumerator];
        NSString *columnName;
        
        while(columnName = [keys nextObject]) {
            
            if(entityDescriptor != nil && ![entityDescriptor containsAttributeBasedOnColumnName:columnName]) {
                continue;
            }
            
            NSString *variableName = entityDescriptor == nil?@"":[[entityDescriptor getAttributeBasedOnColumnName:columnName] getVariableName];
            
            id object = [value objectForKey:columnName];
            
            SIHHybridSiminovValue *hybridSiminovValue = [[SIHHybridSiminovValue alloc] init];
            [hybridSiminovValue setType:variableName];
            [hybridSiminovValue setValue:object];
            
            [hybridSiminovData addValue:hybridSiminovValue];
        }
        
        [hybridSiminovDatas addHybridSiminovData:hybridSiminovData];
        [tuples addObject:value];
    }
    
    values = [tuples objectEnumerator];
    
    return hybridSiminovDatas;
}



@end

@implementation SIHRelationshipHelper

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData {
    
    [SIHRelationshipHelper processOneToOneRelationship:siminovData parentObject:parentSiminovData];
    [SIHRelationshipHelper processOneToManyRelationship:siminovData parentObject:parentSiminovData];
    [SIHRelationshipHelper processManyToOneRelationship:siminovData parentObject:parentSiminovData];
    [SIHRelationshipHelper processManyToManyRelationship:siminovData parentObject:parentSiminovData];
}

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues {
    
    [SIHRelationshipHelper processOneToOneRelationship:siminovData parentObject:parentSiminovData columnNames:columnNames columnValues:columnValues];
    [SIHRelationshipHelper processOneToManyRelationship:siminovData parentObject:parentSiminovData columnNames:columnNames columnValues:columnValues];
    [SIHRelationshipHelper processManyToOneRelationship:siminovData parentObject:parentSiminovData columnNames:columnNames columnValues:columnValues];
    [SIHRelationshipHelper processManyToManyRelationship:siminovData parentObject:parentSiminovData columnNames:columnNames columnValues:columnValues];
}

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause {
    
    [SIHRelationshipHelper processOneToOneRelationship:siminovData parentObject:parentSiminovData whereClause:whereClause];
    [SIHRelationshipHelper processOneToManyRelationship:siminovData parentObject:parentSiminovData whereClause:whereClause];
    [SIHRelationshipHelper processManyToOneRelationship:siminovData parentObject:parentSiminovData whereClause:whereClause];
    [SIHRelationshipHelper processManyToManyRelationship:siminovData parentObject:parentSiminovData whereClause:whereClause];
}

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data {
    
    [SIHRelationshipHelper processOneToOneRelationship:siminovData parentObject:parentSiminovData data:data];
    [SIHRelationshipHelper processOneToManyRelationship:siminovData parentObject:parentSiminovData data:data];
    [SIHRelationshipHelper processManyToOneRelationship:siminovData parentObject:parentSiminovData data:data];
    [SIHRelationshipHelper processManyToManyRelationship:siminovData parentObject:parentSiminovData data:data];
}


+ (void)processOneToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *oneToOneRelationships = [entityDescriptor getOneToOneRelationships];
    
    SICRelationship *oneToOneRelationship;
    while(oneToOneRelationship = [oneToOneRelationships nextObject]) {
        
        BOOL isLoad = [oneToOneRelationship isLoad];
        if(!isLoad) {
            continue;
        }
        
        
        NSMutableString *whereClause = [[NSMutableString alloc] init];
        NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:entityDescriptor];
        
        NSString *foreignKey;
        
        while(foreignKey = [foreignKeys nextObject]) {
            SICAttribute *attribute = [entityDescriptor getAttributeBasedOnColumnName:foreignKey];
            if(attribute == nil) {
                continue;
            }
            
            SIHHybridSiminovValue *value = [siminovData getValueBasedOnType:[attribute getVariableName]];
            id columnValue = [value getValue];
            
            if(whereClause.length <= 0) {
                [whereClause appendString:[NSString stringWithFormat:@"%@='%@'",foreignKey,(NSString *)columnValue]];
            } else {
                [whereClause appendString:[NSString stringWithFormat:@" AND %@='%@'",foreignKey,(NSString *)columnValue]];
            }
        }
        
        SICEntityDescriptor *referedEntityDescriptor = [oneToOneRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToOneRelationship getReferTo]];
            
            [oneToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        if(siminovData != nil && [[siminovData getDataType] caseInsensitiveCompare:[referedEntityDescriptor getClassName]] == NSOrderedSame) {
            continue;
        }
        
        id referedObject = [SIHDatabaseHandler selectDatas:referedEntityDescriptor distinct:false whereClause:whereClause columnNames:nil groupBys:nil havingClause:nil orderBy:nil whichOrderBy:nil limit:nil];
        
        id referedObjects = referedObject;
        
        if(referedObjects == nil || [(NSArray *)referedObjects count] <= 0) {
            continue;
        }
        
        if(referedObjects[0] == nil) {
            continue;
        }
        
        if(referedObjects[0] == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToOneRelationship" message:[NSString stringWithFormat:@"Unable To Create Parent Relationship. REFER-TO: %@", [oneToOneRelationship getReferTo]]];
            continue;
        }
        
        [siminovData addData:referedObject];
    }
}


+ (void)processOneToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *oneToManyRelationships = [entityDescriptor getOneToManyRelationships];
    
    SICRelationship *oneToManyRelationship;
    while(oneToManyRelationship = [oneToManyRelationships nextObject]) {
        
        bool isLoad = [oneToManyRelationship isLoad];
        if(!isLoad) {
            continue;
        }
        
        NSMutableString *whereClause = [[NSMutableString alloc] init];
        NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:entityDescriptor];
        
        NSString *foreignKey;
        
        while(foreignKey = [foreignKeys nextObject]) {
            SICAttribute *attribute = [entityDescriptor getAttributeBasedOnColumnName:foreignKey];
            if(attribute == nil) {
                continue;
            }
            
            SIHHybridSiminovValue *value = [siminovData getValueBasedOnType:[attribute getVariableName]];
            id columnValue = [value getValue];
            
            if(whereClause.length <= 0) {
                [whereClause appendString:[NSString stringWithFormat:@"%@='%@'",foreignKey,(NSString *)columnValue]];
            } else {
                [whereClause appendString:[NSString stringWithFormat:@" AND %@='%@'",foreignKey,(NSString *)columnValue]];
            }
        }
        
        SICEntityDescriptor *referedEntityDescriptor = [oneToManyRelationship getReferedEntityDescriptor];
        if(referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToManyRelationship getReferTo]];
            
            [oneToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        id referedObject = [SIHDatabaseHandler selectDatas:referedEntityDescriptor distinct:false whereClause:whereClause columnNames:[[[NSMutableArray alloc]init] objectEnumerator] groupBys:[[[NSMutableArray alloc]init] objectEnumerator] havingClause:@"" orderBy:[[[NSMutableArray alloc]init] objectEnumerator] whichOrderBy:@"" limit:@""];
        
        SIHHybridSiminovDatas *referedObjects = referedObject;
        
        if(referedObjects != nil) {
            
            NSEnumerator *referedObjectsDatas = [referedObjects getHybridSiminovDatas];
            SIHHybridSiminovData *referedObjectsData;
            
            while(referedObjectsData = [referedObjectsDatas nextObject]) {
                [siminovData addData:referedObjectsData];
            }
        }
    }
}


+ (void)processManyToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData {
    
}

+ (void)processManyToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData {
    
}


+(void)processOneToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *oneToOneRelationships = [entityDescriptor getOneToOneRelationships];
    
    SICRelationship *oneToOneRelationship;
    while(oneToOneRelationship = [oneToOneRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToOneRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToOneRelationship getReferTo]];
            [oneToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *data;
        
        while(data = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[referedEntityDescriptor getClassName]] == NSOrderedSame) {
                referedSiminovData = data;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [oneToOneRelationship getReferTo]]];
            
            continue;
        }
        
        
        [SIHRelationshipHelper processOneToManyRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        [SIHRelationshipHelper processManyToOneRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        [SIHRelationshipHelper processManyToManyRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnNames addObject:[attribute getColumnName]];
                [columnValues addObject:[[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue]];
            }
        }
    }
}


+ (void)processOneToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues {
    
}



+(void)processManyToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToOneRelationships = [entityDescriptor getManyToOneRelationships];
    
    SICRelationship *manyToOneRelationship;
    while(manyToOneRelationship = [manyToOneRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToOneRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToOneRelationship getReferTo]];
            [manyToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *data;
        
        while(data = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[referedEntityDescriptor getClassName]] == NSOrderedSame) {
                referedSiminovData = data;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [manyToOneRelationship getReferTo]]];
            
            continue;
        }
        
        
        
        [SIHRelationshipHelper processOneToOneRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        [SIHRelationshipHelper processManyToManyRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnNames addObject:[attribute getColumnName]];
                [columnValues addObject:[[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue]];
            }
        }
    }
}


+ (void)processOneToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *oneToOneRelationships = [entityDescriptor getOneToOneRelationships];
    
    SICRelationship *oneToOneRelationship;
    while(oneToOneRelationship = [oneToOneRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [oneToOneRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[oneToOneRelationship getReferTo]];
            [oneToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *data;
        
        while(data = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[oneToOneRelationship getGetterReferMethodName]] == NSOrderedSame) {
                referedSiminovData = data;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [oneToOneRelationship getReferTo]]];
            
            continue;
        }
        
        
        [SIHRelationshipHelper processOneToManyRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        [SIHRelationshipHelper processManyToOneRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        [SIHRelationshipHelper processManyToManyRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                NSString *columnName = [attribute getColumnName];
                NSString *columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
                
                if (whereClause.length <= 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@= '%@'", columnName, columnValue]];
                }
            }
        }
    }
}


+ (void)processOneToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause {
    
}


+ (void)processManyToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToOneRelationships = [entityDescriptor getManyToOneRelationships];
    
    SICRelationship *manyToOneRelationship;
    while(manyToOneRelationship = [manyToOneRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToOneRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToOneRelationship getReferTo]];
            [manyToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *data;
        
        while(data = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[data getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[manyToOneRelationship getGetterReferMethodName]] == NSOrderedSame) {
                referedSiminovData = data;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"manyToOneRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [manyToOneRelationship getReferTo]]];
            
            continue;
            //@throw [[SICDatabaseException alloc] initWithClassName:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"manyToOneRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: %@", [manyToOneRelationship getReferTo]]];
        }
        
        
        [SIHRelationshipHelper processOneToOneRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        [SIHRelationshipHelper processManyToManyRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        
        
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                NSString *columnName = [attribute getColumnName];
                NSString *columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
                
                if (whereClause.length <= 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@= '%@'", columnName, columnValue]];
                }
            }
        }
    }
}


+ (void)processOneToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data {
    
}

+ (void)processOneToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data {
    
}


+ (void)processManyToOneRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToOneRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *manyToOneRelationship;
    while(manyToOneRelationship = [manyToOneRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToOneRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToOneRelationship getReferTo]];
            [manyToOneRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *siminovReferedData;
        
        while(siminovData = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[siminovReferedData getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[manyToOneRelationship getGetterReferMethodName]] == NSOrderedSame) {
                referedSiminovData = siminovReferedData;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"manyToOneRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [manyToOneRelationship getReferTo]]];
            
            continue;
        }
        
        
        [SIHRelationshipHelper processOneToOneRelationship:referedSiminovData parentObject:siminovData data:data];
        [SIHRelationshipHelper processManyToManyRelationship:referedSiminovData parentObject:siminovData data:data];
        
        
        if([manyToOneRelationship isLoad]) {
            
            NSMutableString *whereClause = [[NSMutableString alloc] init];
            
            NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:referedEntityDescriptor];
            NSString *foreignKey;
            
            while(foreignKey = [foreignKeys nextObject]) {
                SICAttribute *attribute = [referedEntityDescriptor getAttributeBasedOnColumnName:foreignKey];
                id columnValue = [data objectForKey:[attribute getColumnName]];
                
                if([whereClause length] <= 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@='%@'", foreignKey, (NSString *)columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@='%@'", foreignKey, (NSString *)columnValue]];
                }
            }
            
            
            SIHHybridSiminovDatas *fetchedObjects = [SIHDatabaseHandler selectDatas:referedEntityDescriptor distinct:false whereClause:whereClause columnNames:nil groupBys:nil havingClause:nil orderBy:nil whichOrderBy:nil limit:nil];
            
            
            referedSiminovData = [[fetchedObjects getHybridSiminovDatas] nextObject];
            
        } else {
            
            NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:referedEntityDescriptor];
            NSString *foreignKey;
            
            while(foreignKey = [foreignKeys nextObject]) {
                SICAttribute *attribute = [referedEntityDescriptor getAttributeBasedOnColumnName:foreignKey];
                
                id columnValue = [data objectForKey:[attribute getColumnName]];
                if(columnValue == nil) {
                    continue;
                }
                
                SIHHybridSiminovValue *value = [[SIHHybridSiminovValue alloc] init];
                [value setType:foreignKey];
                [value setValue:(NSString *)columnValue];
                
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToOneRelationship" message:[NSString stringWithFormat:@"Unable To Create Parent Relationship. REFER-TO: %@",[manyToOneRelationship getReferTo]]];
            continue;
        }
        
        [siminovData addData:referedSiminovData];
    }
}


+ (void)processManyToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToManyRelationship getReferTo]];
            [manyToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *siminovReferedData;
        
        while(siminovData = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[siminovReferedData getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[manyToManyRelationship getGetterReferMethodName]] == NSOrderedSame) {
                referedSiminovData = siminovReferedData;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"manyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please proper relationship for REFER-TO: %@", [manyToManyRelationship getReferTo]]];
            continue;
        }
        
        [SIHRelationshipHelper processRelationship:referedSiminovData parentObject:siminovData columnNames:columnNames columnValues:columnValues];
        
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                [columnNames addObject:[attribute getColumnName]];
                [columnValues addObject:[[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue]];
            }
        }
    }
}

+ (void)processManyToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToManyRelationship getReferTo]];
            [manyToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        
        SIHHybridSiminovData *referedSiminovData = nil;
        NSEnumerator *datas = [siminovData getDatas];
        SIHHybridSiminovData *siminovReferedData;
        
        while(siminovData = [datas nextObject]) {
            NSString *referedClassName = [hybridResourceManager getMappedNativeClassName:[siminovReferedData getDataType]];
            
            if([referedClassName caseInsensitiveCompare:[manyToManyRelationship getGetterReferMethodName]] == NSOrderedSame) {
                referedSiminovData = siminovReferedData;
                break;
            }
        }
        
        if(referedSiminovData == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"manyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@", [manyToManyRelationship getReferTo]]];
            continue;
        }
        
        
        [SIHRelationshipHelper processRelationship:referedSiminovData parentObject:siminovData whereClause:whereClause];
        
        NSEnumerator *hybridValues = [referedSiminovData getValues];
        
        NSMutableDictionary *hybridSiminovValues = [[NSMutableDictionary alloc] init];
        SIHHybridSiminovValue *hybridSiminovValue;
        
        while(hybridSiminovValue = [hybridValues nextObject]) {
            [hybridSiminovValues setValue:hybridSiminovValue forKey:[hybridSiminovValue getType]];
        }
        
        NSEnumerator *parentAttributes = [referedEntityDescriptor getAttributes];
        SICAttribute *attribute;
        
        while(attribute = [parentAttributes nextObject]) {
            bool isPrimary = [attribute isPrimaryKey];
            if(isPrimary) {
                NSString *columnName = [attribute getColumnName];
                NSString *columnValue = [[hybridSiminovValues objectForKey:[attribute getVariableName]] getValue];
                
                if (whereClause.length <= 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@= '%@'", columnName, columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@= '%@'", columnName, columnValue]];
                }
            }
        }
    }
}

+ (void)processManyToManyRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data {
    
    SICEntityDescriptor *entityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[siminovData getDataType]];
    NSEnumerator *manyToManyRelationships = [entityDescriptor getManyToManyRelationships];
    
    SICRelationship *manyToManyRelationship;
    while(manyToManyRelationship = [manyToManyRelationships nextObject]) {
        SICEntityDescriptor *referedEntityDescriptor = [manyToManyRelationship getReferedEntityDescriptor];
        if (referedEntityDescriptor == nil) {
            referedEntityDescriptor = [SIHDatabaseHandler getEntityDescriptor:[manyToManyRelationship getReferTo]];
            [manyToManyRelationship setReferedEntityDescriptor:referedEntityDescriptor];
        }
        
        id referedObject = [SICClassUtils createClassInstance:[[manyToManyRelationship getReferedEntityDescriptor] getClassName]];
        if(referedObject == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToManyRelationship" message:[NSString stringWithFormat:@"Parent Object Not Set, Please provide relationship for REFER-TO: %@",[manyToManyRelationship getReferTo]]];
            continue;
        }
        
        [SIHRelationshipHelper processRelationship:referedObject parentObject:siminovData data:data];
        
        if([manyToManyRelationship isLoad]) {
            
            NSMutableString *whereClause = [[NSMutableString alloc]init];
            NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:referedObject];
            
            NSString *foreignKey;
            
            while(foreignKey = [foreignKeys nextObject]) {
                
                SICAttribute *attribute = [referedEntityDescriptor getAttributeBasedOnColumnName:foreignKey];
                id columnValue = [data objectForKey:[attribute getColumnName]];
                
                if(whereClause.length <= 0) {
                    [whereClause appendString:[NSString stringWithFormat:@"%@='%@'",foreignKey,(NSString *)columnValue]];
                } else {
                    [whereClause appendString:[NSString stringWithFormat:@" AND %@='%@'",foreignKey,(NSString *)columnValue]];
                }
            }
            
            
            id fetchedObjects = [SIHDatabaseHandler selectDatas:referedEntityDescriptor distinct:false whereClause:whereClause columnNames:nil groupBys:nil havingClause:nil orderBy:nil whichOrderBy:nil limit:nil];
            
            referedObject = fetchedObjects;
            
        } else {
            
            
            NSEnumerator *foreignKeys = [SIHDatabaseHandler getPrimaryKeysRaw:referedEntityDescriptor];
            NSString *foreignKey;
            
            while(foreignKey = [foreignKeys nextObject]) {
                SICAttribute *attribute = [referedEntityDescriptor getAttributeBasedOnColumnName:foreignKey];
                
                id columnValue = [data objectForKey:[attribute getColumnName]];
                if(columnValue == nil) {
                    continue;
                }
                
                SIHHybridSiminovValue *value = [[SIHHybridSiminovValue alloc] init];
                [value setType:foreignKey];
                [value setValue:(NSString *)columnValue];
                
            }
        }
        
        if(referedObject == nil) {
            [SICLog error:NSStringFromClass([SIHDatabaseHandler class]) methodName:@"processManyToManyRelationship" message:[NSString stringWithFormat:@"Unable To Create Parent Relationship. REFER-TO: %@",[manyToManyRelationship getReferTo]]];
            continue;
        }
        
        [siminovData addData:referedObject];
    }
}

@end

