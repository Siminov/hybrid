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
 * Exposes API's to return sum of all non-NULL values in the group.
 * If there are no non-NULL input rows then sum() returns NULL but total() returns 0.0.
 * NULL is not normally a helpful result for the sum of no rows but the SQL standard requires it and most other SQL database engines implement sum() that way so SQLite does it in the same way in order to be compatible.
 * The result of sum() is an integer value if all non-NULL inputs are integers. 

	@module Database
	@submodule Impl
	@class ISum	
	@constructor
	@param select {Select} Select class object.
*/
function ISum(select) {

    return {

        interfaceName : "ISum",

        where : select.where,

        whereClause : select.whereClause,

        and : select.and,

        or : select.or,

        groupBy : select.groupBy,

        having : select.having,

        havingClause : select.havingClause,

        column : select.column,

        execute : select.execute

    }

}
