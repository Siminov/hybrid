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
 * Exposes API's to returns the minimum non-NULL value of all values in the group.
 * The minimum value is the first non-NULL value that would appear in an ORDER BY of the column.
 * Aggregate min() returns NULL if and only if there are no non-NULL values in the group.

	@module Database
	@submodule Impl
	@class IMin
	@constructor
	@param select {Select} Select class object.
*/
function IMin(select) {

    return {

        interfaceName : "IMin",

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
