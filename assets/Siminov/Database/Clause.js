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
	Exposes API to deal with conditions used in query.

	@module Database
	@class Clause
	@constructor {Select} Select Class Object.
*/
function Clause(select) {

    var where = new StringBuilder();

    this.addColumn = function(column) {
        where.append(column);
    }

    this.equalTo = function(val) {
        where.append(this.EQUAL_TO + " '" + val + "'");
        return select;
    }


    this.notEqualTo = function(val) {
        where.append(this.NOT_EQUAL_TO + " '" + val + "' ");
        return select;
    }


    this.greaterThan = function(val) {
        where.append(this.GREATER_THAN + " '" + val + "' ");
        return select;
    }


    this.greaterThanEqual = function(val) {
        where.append(this.GREATER_THAN_EQUAL + " '" + val + "' ");
        return this.select;
    }


    this.lessThan = function(val) {
        where.append(this.LESS_THAN + " '" + val + "' ");
        return select;
    }


    this.lessThanEqual = function(val) {
        where.append(this.LESS_THAN_EQUAL + " '" + val + "' ");
        return select;
    }


    this.between = function(start, end) {
        where.append(this.BETWEEN + " '" + start + "' " + this.AND + " '" + end + "' ");
        return select;
    }


    this.like = function(val) {
        where.append(this.LIKE + " '" + val + "' ");
        return select;
    }


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


    this.add = function(column) {
        where.append(" " + this.AND + " " + column);
    }


    this.or = function(column) {
        where.append(" " + this.OR + " " + column);
    }


    this.toString = function() {
        return where.toString();
    }

}

Clause.EQUAL_TO = "=";
Clause.NOT_EQUAL_TO = "!=";
Clause.GREATER_THAN = ">";
Clause.GREATER_THAN_EQUAL = ">=";
Clause.LESS_THAN = "<";
Clause.LESS_THAN_EQUAL = "<=";
Clause.BETWEEN = "BETWEEN";
Clause.LIKE = "LIKE";
Clause.IN = "IN";
Clause.AND = "AND";
Clause.OR = "OR";

Clause.ASC_ORDER_BY = "ASC";
Clause.DESC_ORDER_BY = "DESC";
