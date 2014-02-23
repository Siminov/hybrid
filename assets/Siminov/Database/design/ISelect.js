/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


/**
	Exposes classes which deal with database.
	A Siminov Database Abstraction Layer is an application programming interface which unifies the communication between a computer application and database such as SQLite.
	Siminov Database Layer reduce the amount of work by providing a consistent API to the developer and hide the database specifics behind this interface as much as possible.
	
	@module Database
*/


/**
	Impl contain all interfaces required by database layer to deal with database.

	@module Database
	@submodule Impl
*/


/**
 * Exposes API's to get tuples from table based on information provided.
 
	@module Database
 	@submodule Impl
 	@class ISelect
	@constructor
	@param select {Select} Select class object.
*/
function ISelect(select) {

    return {

		/**
			Name of Interface
		*/
        interfaceName : "ISelect",


		/**
		 	Used to specify DISTINCT condition.
		 	
		 	@method distinct
		 	@return {ICount} ICount Interface.
		 */
        distinct : select.distinct,


		/**
		 	Column name of which condition will be specified.
		 	
		 	@method where
		 	@param column {String} Name of column.
		 	@return {ISelectClause} ISelectClause Interface.
		 */
        where : select.where,


		/**
		 	Used to provide manually created Where clause, instead of using API's.
		 	
		 	@method whereClause
		 	@param whereClause {String} Manually created where clause.
		 	@return {ISelect} ISelect Interface.
		 */
        whereClause : select.whereClause,


		/**
		 	Used to specify AND condition between where clause.
		 	
		 	@method and
		 	@param column {String} Name of column on which condition need to be specified.
		 	@return {ISelectClause} ISelectClause Interface.
		 */
        and : select.and,


		/**
		 	Used to specify OR condition between where clause.
		 	
		 	@method or
		 	@param column {String} Name of column on which condition need to be specified.
		 	@return {ISelectClause} ISelectClause Interface.
		 */
        or : select.or,


		/**
		 	Used to specify ORDER BY keyword to sort the result-set.
			
			@method orderBy
			@param columns {String} Name of columns which need to be sorted.
		 	@return {ISelect} ISelect Interface.
		 */
        orderBy : select.orderBy,


		/**
		 	Used to specify ORDER BY ASC keyword to sort the result-set in ascending order.
		 	
		 	@method ascendingOrderBy
		 	@param columns {String} Name of columns which need to be sorted.
		 	@return {ISelect} ISelect Interface.
		 */
        ascendingOrderBy : select.ascendingOrderBy,


		/**
		 	Used to specify ORDER BY DESC keyword to sort the result-set in descending order.
		 	
		 	@method descendingOrderBy
		 	@param columns {String} Name of columns which need to be sorted.
		 	@return {ISelect} ISelect Interface.
		 */
        descendingOrderBy : select.descendingOrderBy,


		/**
		 	Used to specify the range of data need to fetch from table.
		 	
		 	@method limit
		 	@param limit {String} LIMIT of data.
		 	@return {ISelect} ISelect Interface.
		 */
        limit : select.limit,


		/**
		 	Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
		 	
		 	@method groupBy
		 	@param columns {Array} Name of columns.
		 	@return {ISelect} ISelect Interface.
		 */
        groupBy : select.groupBy,


		/**
		 	Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
		 	
		 	@method having
		 	@param column {String} Name of column on which condition need to be applied.
		 	@return {ISelectClause} ISelectClause Interface.
		 */
        having : select.having,


		/**
		 	Used to provide manually created Where clause, instead of using API's.
		 	
		 	@method havingClause
		 	@param havingClause {String} Where clause.
		 	@return {ISelect} ISelect Interface.
		 */
        havingClause : select.havingClause,

	
		/**
		 	Used to provide name of columns only for which data will be fetched.
		 	
		 	@method columns
		 	@param column {Array} Name of columns.
		 	@return {ISelect} ISelect Interface.
		 */
        columns : select.columns,


		/**
		 	Used to get tuples, this method should be called in last to get tuples from table.
		 	
		 	@method fetch
		 	@return {Object} Return array of model objects.
		 	@throws {SiminovException} Throws exception if any error occur while getting tuples from table. 
		 */
        fetch : select.fetch

    }

}
