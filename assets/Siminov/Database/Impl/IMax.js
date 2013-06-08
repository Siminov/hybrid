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
 * Exposes API's to returns the maximum value of all values in the group.
 * The maximum value is the value that would be returned last in an ORDER BY on the same column. 
 * Aggregate max() returns NULL if and only if there are no non-NULL values in the group.
	
	@module Database
	@submodule Impl
	@class IMax
	@constructor
	@param select {Select} Select class object.
*/
function IMax(select) {

    return {

		/**
			Name of Interface
		*/
        interfaceName : "IMax",


		/**
		 	Column name of which condition will be specified.
		 	
		 	@method where
		 	@param column {String} Name of column.
		 	@return {IMaxClause} IMaxClause Interface.
		 */
        where : select.where,


		/**
		 	Used to provide manually created Where clause, instead of using API's.
		 
		 	@method whereClause
			@param whereClause {String} Manually created where clause.
		 	@return {IMax} IMax Interface.
		 */
        whereClause : select.whereClause,


		/**
		 	Used to specify AND condition between where clause.
		 	
		 	@method and
		 	@param column {String} Name of column on which condition need to be specified.
		 	@return {IMaxClause} IMaxClause Interface.
		 */
        and : select.and,


		/**
		 	Used to specify OR condition between where clause.
		 	
		 	@method or
		 	@param column {String} Name of column on which condition need to be specified.
		 	@return {IMaxClause} IMaxClause Interface.
		 */
        or : select.or,


		/**
		 	Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
		 	
		 	@method groupBy
		 	@param columns {Array} Name of columns.
		 	@return {IMax} IMax Interface.
		 */
        groupBy : select.groupBy,


		/**
		 	Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
		 	
		 	@method having
		 	@param column {String} Name of column on which condition need to be applied.
		 	@return {IMaxClause} IMaxClause Interface.
		 */
        having : select.having,


		/**
		 	Used to provide manually created Where clause, instead of using API's.
		 	
		 	@method havingClause
		 	@param havingClause {String} Where clause.
		 	@return {IMax} IMax Interface.
		 */
        havingClause : select.havingClause,


		/**
		 	Used to provide name of column for which maximum will be calculated.
		 	
		 	@method column
			@param column {String} Name of column.
		 	@return {IMax} IMax Interface.
		 */
        column : select.column,


		/**
		 	Used to get maximum, this method should be called in last to calculate maximum.
		 	
		 	@method execute
		 	@return {Object} Return maximum.
		 	@throws {SiminovException} Throws exception if any error occur while calculating maximum. 
		 */
        execute : select.execute

    }

}
