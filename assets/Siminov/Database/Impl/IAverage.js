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
 * Exposes API's to get average value of all non-NULL X within a group. 
 * String and BLOB values that do not look like numbers are interpreted as 0.
 * The result of avg() is always a floating point value as long as at there is at least one non-NULL input even if all inputs are integers.
 * The result of avg() is NULL if and only if there are no non-NULL inputs.

	@module Database
	@submodule Impl
	@class IAverage
	@constructor
	@param select {Select} Select class object.
*/
function IAverage(select) {

    return {

		/**
			Name of Interface
		*/
        interfaceName : "IAverage",


		/**
		 	Column name of which condition will be specified.
		 	
		 	@method where
		 	@param column {String} Name of column.
		 	@return {IAerageClause} IAerageClause Interface.
		*/
        where : select.where,


		/**
		 	Used to provide manually created Where clause, instead of using API's.
	
			@method whereClause
			@param whereClause {String} Manually created where clause.
			@return {IAverage} IAverage Interface.
		*/
        whereClause : select.whereClause,


		/**
		 	Used to specify AND condition between where clause.

			@method and		
	 		@param column {String} Name of column on which condition need to be specified.
	 		@return {IAerageClause} IAerageClause Interface.
		*/
        and : select.and,

		
		/**
	 		Used to specify OR condition between where clause.
	 		
			@method or
	 		@param column {String} Name of column on which condition need to be specified.
	 		@return {IAverageClause} IAverageClause Interface.
		*/
        or : select.or,

		
		/**
	 		Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 		
	 		@method groupBy
	 		@param columns {Array} Name of columns.
	 		@return {IAverage} IAverage Interface.
		*/
        groupBy : select.groupBy,

		
		/**
	 		Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 		
			@method having	 		
	 		@param column {String} Name of column on which condition need to be applied.
	 		@return {IAverageClause} IAverageClause Interface.
		*/
        having : select.having,


		/**
	 		Used to provide manually created Where clause, instead of using API's.
	 		
			@method havingClause	 		
	 		@param havingClause {String} Where clause.
	 		@return {IAverage} IAverage Interface.
		*/
        havingClause : select.havingClause,

			
		/**
	 		Used to provide name of column for which average will be calculated.
	 
			@method column			
			@param column {String} Name of column.
	 		@return {IAverage} IAverage Interface.
		*/
        column : select.column,

			
		/**
	 		Used to get average, this method should be called in last to calculate average.
	 
			@method execute
			@return {Object} Return average.
	 		@throws {SiminovException} Throws exception if any error occur while calculating average. 
		*/
        execute : select.execute

    }

}