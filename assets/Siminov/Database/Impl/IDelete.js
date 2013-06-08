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
 * Exposes API's to delete tuples from table.
 
	@module Database
 	@submodule Impl
 	@class IDelete
	@constructor
	@param select {Select} Select class object.
*/
function IDelete(select) {


	return {
	
		
		/**
			Name of Interface
		*/
		interfaceName : "IDelete",
		
		
		/**
		 	Column name of which condition will be specified.
		 
		 	@method where
			@param column {String} Name of column.
		 	@return {IDeleteClause} IDeleteClause Interface.
		 */
		where : select.where,
		

		/**
		 	Used to provide manually created Where clause, instead of using API's.
		 
		 	@method whereClause
			@param whereClause {String} Manually created where clause.
		 	@return {IDelete} IDelete Interface.
		 */
		whereClause : select.whereClause,
		

		/**
		 	Used to specify AND condition between where clause.
		 
		 	@method and
			@param column {String} Name of column on which condition need to be specified.
		 	@return {IDeleteClause} IDeleteClause Interface.
		 */
		and : select.and,
		

		/**
		 	Used to specify OR condition between where clause.
		 
		 	@method or
			@param column {String} Name of column on which condition need to be specified.
		 	@return {IDeleteClause} IDeleteClause Interface.
		 */
        or : select.or,


		/**
		 	Used to delete, this method should be called in last to delete tuples from table.
		 	
		 	@method execute
		 	@throws {SiminovException} Throws exception if any error occur while deleting tuples from table. 
		 */
        execute : select.execute
		
	}

}