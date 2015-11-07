/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
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
	Exposes API to deal with conditions used in query.

	@module Database
	@class Clause
	@constructor {Select} Select Class Object.
*/
function Clause(select) {

    var where = new StringBuilder();


	/**
	 * Add column
	 * 
	 * @method addColumn
	 * @param column {String} Column
	 */
    this.addColumn = function(column) {
        where.append(column);
    }

	/**
	 * Equal to conditiion
	 * 
	 * @method equalTo
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.equalTo = function(val) {
        where.append(this.EQUAL_TO + " '" + val + "'");
        return select;
    }


	/**
	 * Not equal to condition
	 * 
	 * @method notEqualTo
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.notEqualTo = function(val) {
        where.append(this.NOT_EQUAL_TO + " '" + val + "' ");
        return select;
    }


	/**
	 * Greater then condition
	 * 
	 * @method greaterThan
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.greaterThan = function(val) {
        where.append(this.GREATER_THAN + " '" + val + "' ");
        return select;
    }


	/**
	 * Greater than equal condition
	 * 
	 * @method greaterThanEqual
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.greaterThanEqual = function(val) {
        where.append(this.GREATER_THAN_EQUAL + " '" + val + "' ");
        return this.select;
    }

	/**
	 * Less than condition
	 * 
	 * @method lessThan
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.lessThan = function(val) {
        where.append(this.LESS_THAN + " '" + val + "' ");
        return select;
    }


	/**
	 * Less than equal condition
	 * 
	 * @method lessThanEqual
	 * @param val {String} Value of the condition
	 * @return {ISelect}
	 */
    this.lessThanEqual = function(val) {
        where.append(this.LESS_THAN_EQUAL + " '" + val + "' ");
        return select;
    }

	
	/**
	 * Between condition
	 * 
	 * @method between
	 * @param start {String} Start Condition
	 * @param end {String} End Condition
	 * @return {ISelect}
	 */
    this.between = function(start, end) {
        where.append(this.BETWEEN + " '" + start + "' " + this.AND + " '" + end + "' ");
        return select;
    }

	/**
	 * Like condition
	 * 
	 * @method like
	 * @param val {String} Value of the condition
	 * @return {Iselect}
	 */
    this.like = function(val) {
        where.append(this.LIKE + " '" + val + "' ");
        return select;
    }


	/**
	 * In condition
	 * 
	 * @method in
	 * @param values {Array} Values
	 * @method {ISelect}
	 */
    this['in'] = function(values) {
        where.append(this.IN + "(");

        if(values != null && values.length > 0) {
            for(var i = 0;i < values.length;i++) {
                if(i == 0) {
                    where.append("'" + values[i] + "'");
                    continue;
                }

                where.append(" ,'" + values[i] + "'");
            }
        }

        where.append(")");

        return select;
    }

	
	/**
	 * Add condition
	 * 
	 * @method add
	 * @param column {String} Column
	 */
    this.add = function(column) {
        where.append(" " + this.AND + " " + column);
    }

	/**
	 * Or condition
	 * 
	 * @method or
	 * @param column {String} Column
	 */
    this.or = function(column) {
        where.append(" " + this.OR + " " + column);
    }

	/**
	 * Where Clause
	 * 
	 * @method toString
	 * @return Where Clause
	 */
    this.toString = function() {
        return where.toString();
    }

}



/**
	Equal To Condition
	
	@property EQUAL_TO
	@type String 
	@static
	@final
	@readOnly
*/
Clause.EQUAL_TO = "=";


/**
	Not Equal To Condition
	
	@property NOT_EQUAL_TO
	@type String
	@static
	@final
	@readOnly
*/
Clause.NOT_EQUAL_TO = "!=";

/**
	Greater Than Condition
	
	@property 
	@type String
	@static
	@final
	@readOnly
*/
Clause.GREATER_THAN = ">";

/**
	Greater Than Equal Condition
	
	@property GREATER_THAN_EQUAL
	@type String
	@static
	@final
	@readOnly
*/
Clause.GREATER_THAN_EQUAL = ">=";

/**
	Less Then Condition	

	@property LESS_THAN
	@type String
	@static
	@final
	@readOnly
*/
Clause.LESS_THAN = "<";

/**
	Less Than Equal Condition

	@property LESS_THAN_EQUAL
	@type String
	@static
	@final
	@readOnly
*/
Clause.LESS_THAN_EQUAL = "<=";

/**
 	Between Condition

	@property Between
	@type String
	@static
	@final
	@readOnly
*/
Clause.BETWEEN = "BETWEEN";

/**
	Like Condition

	@property LIKE
	@type String
	@static
	@final
	@readOnly
*/
Clause.LIKE = "LIKE";

/**
	In Condition

	@property IN
	@type String
	@static
	@final
	@readOnly
*/
Clause.IN = "IN";

/**
 	And Condition

	@property AND
	@type String
	@static
	@final
	@readOnly
*/
Clause.AND = "AND";

/**
	Or Condition
	
	@property OR
	@type String
	@static
	@final
	@readOnly
*/
Clause.OR = "OR";


/**
	Asc Order By Condition

	@property ASC_ORDER_BY
	@type String
	@static
	@final
	@readOnly
*/
Clause.ASC_ORDER_BY = "ASC";

/**
 	Desc Order By Condition

	@property DESC_ORDER_BY
	@type String
	@static
	@final
	@readOnly
*/
Clause.DESC_ORDER_BY = "DESC";
