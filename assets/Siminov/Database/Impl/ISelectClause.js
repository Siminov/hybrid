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
 * Exposes API's to provide condition on where clause to get tuples from table..
 
	@module Database
 	@submodule Impl
 	@class ISelectClause
	@constructor
	@param clause {Clause} Clause class object
*/
function ISelectClause(clause) {

    return {

		/**
			Name of Interface
		*/
        interfaceName : "ISelectClause",

		
		/**
		 	Used to specify EQUAL TO (=) condition.
		 	
		 	@method equalTo
		 	@param value {String} Value for which EQUAL TO (=) condition will be applied.
		 	@return {ISelect} ISelect Interface.
		 */
        equalTo : clause.equalTo,

	
		/**
		 	Used to specify NOT EQUAL TO (!=) condition.
		 	
		 	@method notEqualTo
		 	@param value {String} Value for which NOT EQUAL TO (=) condition will be applied.
		 	@return {ISelect} ISelect Interface.
		 */
        notEqualTo : clause.notEqualTo,


		/**
		 	Used to specify GREATER THAN (>) condition.
		 	
		 	@method greaterThan
		 	@param value {String} Value for while GREATER THAN (>) condition will be specified.
		 	@return {ISelect} ISelect Interface.
		 */
        greaterThan : clause.greaterThan,


		/**
		 	Used to specify GREATER THAN EQUAL (>=) condition.
		 
		 	@method greaterThanEqual
			@param value {String} Value for which GREATER THAN EQUAL (>=) condition will be specified.
		 	@return {ISelect} ISelect Interface.
		 */
        greaterThanEqual : clause.greaterThanEqual,


		/**
		 	Used to specify LESS THAN (<) condition.
		 	
		 	@method lessThan
		 	@param value {String} Value for which LESS THAN (<) condition will be specified.
		 	@return {ISelect} ISelect Interface.
		 */
        lessThan : clause.lessThan,


		/**
		 	Used to specify LESS THAN EQUAL (<=) condition.
		 	
		 	@method lessThanEqual
		 	@param value {String} Value for which LESS THAN EQUAL (<=) condition will be specified.
		 	@return {ISelect} ISelect Interface.
		 */
        lessThanEqual : clause.lessThanEqual,


		/**
		 	Used to specify BETWEEN condition.
		 	
		 	@method between
		 	@param start {String} Start Range.
		 	@param end {String} End Range.
		 	@return {ISelect} ISelect Interface.
		 */
        between : clause.between,


		/**
		 	Used to specify LIKE condition.
		 	
		 	@method like
		 	@param like {String} LIKE condition.
		 	@return {ISelect} ISelect Interface.
		 */
        like : clause.like,


		/**
		 	Used to specify IN condition.
		 	
		 	@method in
		 	@param values {Array} Values for IN condition.
		 	@return {ISelect} ISelect Interface.
		 */
        'in' : clause['in']

    }

}