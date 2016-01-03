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

#import "SIHIAdapter.h"
#import "SIHHybridSiminovDatas.h"


/**
 * It handles all request related to database.
 * LIKE: save, update, saveOrUpdate, delete.
 */
@interface SIHDatabaseHandler : NSObject<SIHIAdapter> {
    
}

/**
 * Handles Database Save Request From Hybrid.
 * @param data Data Need To Be Save In Database.
 * @throws DatabaseException If any exception occur while saving data in Database.
 */
- (void)save:(NSString *)data;


/**
 * Handles Database Update Request From Hybrid.
 * @param data Data Need To Be Update In Database.
 * @throws DatabaseException If any exception occur while updating data in Database.
 */
- (void)update:(NSString *)data;


/**
 * Handles Database Save Or Update Request From Hybrid.
 * @param data Data Need To Be Save Or Update In Database.
 * @throws DatabaseException If any exception occur while saving or updating data in Database.
 */
- (void)saveOrUpdate:(NSString *)data;


/**
 * Handles Database Delete Request From Hybrid.
 * @param className Hybrid Model Class Name of which delete request is sent.
 * @param whereClause Based on which tuple will be deleted from table.
 * @param data Data Need To Be Delete In Database.
 * @throws DatabaseException If any exception occur while deleting data in Database.
 */
- (void)delete:(NSString *)className whereClause:(NSString *)whereClause data:(NSString *)data;


/**
 * Handles Database Select Request From Hybrid.
 * @param className Hybrid Model Function Name.
 * @param distinct Distinct tuples need to be fetched or not.
 * @param whereClause Where Clause based on which tuples will be fetched from table.
 * @param columnNames Name of Columns for which data needs to be fetched.
 * @param groupBy Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param havingClause Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
 * @param orderBy Used to specify ORDER BY keyword to sort the result-set.
 * @param whichOrderBy Used to specify ORDER BY ASC OR DESC keyword to sort the result-set in ascending order.
 * @param limit Used to specify the range of data need to fetch from table.
 * @return Return Tuples Fetched From Table.
 * @throws DatabaseException If any exception occur while selecting data in Database.
 */
- (NSString *)select:(NSString *)className distinct:(NSString *)distinct whereClause:(NSString *)whereClause columnNames:(NSEnumerator *)columnNames groupBys:(NSEnumerator *)groupBys havingClause:(NSString *)havingClause orderBy:(NSEnumerator *)orderBy whichOrderBy:(NSString *)whichOrderBy limit:(NSString *)limit;



- (NSString *)selectManual:(NSString *)className query:(NSString *)query;



/**
 * Handles Database Begin Transaction Request From Hybrid.
 * @param databaseDescriptorName Name of Database Descriptor.
 * @throws DatabaseException If any exception occur while beginning transaction.
 */
- (void)beginTransaction:(NSString *)databaseDescriptorName;


- (NSString *)beginTransactionAsync:(NSString *)databaseDescriptorName data:(NSString *)data;



/**
 * Handles Database Commit Transaction Request From Hybrid.
 * @param databaseDescriptorName Name of Database Descriptor.
 * @throws DatabaseException If any error occur while committing transaction.
 */
- (void)commitTransaction:(NSString *)databaseDescriptorName;


/**
 * Handles Database End Transaction Request From Hybrid.
 * @param databaseDescriptorName Name of Database Descriptor.
 * @throws DatabaseException If any error occur while ending transaction.
 */
- (void)endTransaction:(NSString *)databaseDescriptorName;


/**
 * Handles Database Count Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Count Needs To Be Find.
 * @param distinct Distinct tuples needs to be calculated or not.
 * @param whereClause Where Clause based on which Count needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
 * @return Count Of Tuples.
 * @throws DatabaseException If any error occur while getting count.
 */
- (NSString *)count:(NSString *)className column:(NSString *)column distinct:(NSString *)distinct whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;


/**
 * Handles Database Average Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Average Needs To Be Find.
 * @param whereClause Where Clause based on which Average needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
 * @return Average Of Tuples.
 * @throws DatabaseException If any error occur while getting count.
 */
- (NSString *)avg:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;


/**
 * Handles Database Sum Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Sum Needs To Be Find.
 * @param whereClause Where Clause based on which Sum needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Sum functions.
 * @return Sum Of Tuples.
 * @throws DatabaseException If any error occur while getting sum.
 */
- (NSString *)sum:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;



/**
 * Handles Database Total Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Total Needs To Be Find.
 * @param whereClause Where Clause based on which Total needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Total functions.
 * @return Total Of Tuples.
 * @throws DatabaseException If any error occur while getting total.
 */
- (NSString *)total:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *) groupBys having:(NSString *)having;


/**
 * Handles Database Minimum Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Minimum Needs To Be Find.
 * @param whereClause Where Clause based on which Minimum needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Minimum functions.
 * @return Minimum Of Tuples.
 * @throws DatabaseException If any error occur while getting minimum.
 */
- (NSString *)min:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;


/**
 * Handles Database Maximum Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Maximum Needs To Be Find.
 * @param whereClause Where Clause based on which Maximum needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Maximum functions.
 * @return Maximum Of Tuples.
 * @throws DatabaseException If any error occur while getting maximum.
 */
- (NSString *)max:(NSString *)className columnName:(NSString *)columnName whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;


/**
 * Handles Database Group Concat Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @param column Name of Column For Which Group Concat Needs To Be Find.
 * @param whereClause Where Clause based on which Group Concat needs to be found.
 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Group Concat functions.
 * @return Group Concat Of Tuples.
 * @throws DatabaseException If any error occur while getting Group Concat.
 */
- (NSString *)groupConcat:(NSString *)className columnName:(NSString *)columnName delimiter:(NSString *)delimiter whereClause:(NSString *)whereClause groupBys:(NSEnumerator *)groupBys having:(NSString *)having;


/**
 * Handles Database Get Table Name Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Table Name.
 * @throws DatabaseException If any error occur while get table name mapped to hybrid model class name.
 */
- (NSString *)getTableName:(NSString *)className;



/**
 * Handles Database Get Columns Names Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Column Names.
 * @throws DatabaseException If any error occur while getting column names mapped to hybrid model class name.
 */
- (NSString *)getColumnNames:(NSString *)className;



/**
 * Handles Database Get Columns Types Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Column Types.
 * @throws DatabaseException If any error occur while getting column types mapped to hybrid model class name.
 */
- (NSString *)getColumnTypes:(NSString *)className;



/**
 * Handles Database Get Primary Column Names Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Primary Column Names.
 * @throws DatabaseException If any error occur while get primary column names mapped to hybrid model class name.
 */
- (NSString *)getPrimaryKeys:(NSString *)className;



/**
 * Handles Database Get Mandatory Column Names Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Mandatory Names.
 * @throws DatabaseException If any error occur while getting mandatory column names mapped to hybrid model class name.
 */
- (NSString *)getMandatoryFields:(NSString *)className;


/**
 * Handles Database Get Unique Column Names Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Unique Column Names.
 * @throws DatabaseException If any error occur while getting unique column names.
 */
- (NSString *)getUniqueFields:(NSString *)className;


/**
 * Handle Database Get Foreign Column Names Request From Hybrid.
 * @param className Hybrid Model Class Name.
 * @return Foreign Column Names.
 * @throws DatabaseException If any error occur while getting foreign column names.
 */
- (NSString *)getForeignKeys:(NSString *)className;

@end





@interface SIHRelationshipHelper : NSObject

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData;

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData columnNames:(NSMutableArray * const)columnNames columnValues:(NSMutableArray * const)columnValues;

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData whereClause:(NSMutableString * const)whereClause;

+ (void)processRelationship:(SIHHybridSiminovData *)siminovData parentObject:(SIHHybridSiminovData *)parentSiminovData data:(NSMutableDictionary * const)data ;

@end
