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
	Exposes API to deal with conditions and other constraints used in query.

	@module Database
	@class Select
	@constructor
	@param object {Select}
*/
function Select(object) {

    var where;
    var whereClause;

    var distinct = false;

    var orderBy;
    var whichOrderBy;

    var limit;

    var groupBy;
    var having;
    var havingClause;

    var column;
    var columns;

    var delimiter;


	/**
	 	Used to specify DISTINCT condition.
	 	
	 	@method distinct
	 	@return {Object} Select Interface Implementation.
	 */
    this.distinct = function(val) {
        distinct = val;
        
        return this;
    }


	/**
	 	Column name of which condition will be specified.
	 	
	 	@method where
	 	@param column {String} Name of column.
	 	@return {Object} Clause Interface Implementation.
	 */
    this.where = function(column) {
        where = new Clause(this);
        where.addColumn(column);

        return where;
    }


	/**
	 	Used to provide manually created Where clause, instead of using API's.
	 	
	 	@method whereClause
	 	@param whereClause {String} Manually created where clause.
	 	@return {Object} Select Interface Implementation.
	 */
    this.whereClause = function(where) {
        whereClause = where;
        return this;
    }


	/**
	 	Used to specify AND condition between where clause.
	 	
	 	@method and
	 	@param column {String} Name of column on which condition need to be specified.
	 	@return {Object} Clause Interface Implementation.
	 */
    this.add = function(column) {
        where.and(column);
        return where;
    }


	/**
	 	Used to specify OR condition between where clause.
	 	
	 	@method or
	 	@param column {String} Name of column on which condition need to be specified.
	 	@return {Object} Clause Interface Implementation.
	 */
    this.or = function(column) {
        where.or(column);
        return where;
    }


	/**
	 	Used to specify ORDER BY keyword to sort the result-set.
		
		@method orderBy
		@param columns {String} Name of columns which need to be sorted.
	 	@return {oBJECT} Select Interface Implementation.
	 */
    this.orderBy = function(columns) {
        orderBy = columns;
        return this;
    }


	/**
	 	Used to specify ORDER BY ASC keyword to sort the result-set in ascending order.
	 	
	 	@method ascendingOrderBy
	 	@param columns {String} Name of columns which need to be sorted.
	 	@return {Object} Select Interface Implementation.
	 */
    this.ascendingOrderBy = function(columns) {
        orderBy = columns;
        whichOrderBy = Clause.ASC_ORDER_BY;

        return this;
    }


	/**
	 	Used to specify ORDER BY DESC keyword to sort the result-set in descending order.
	 	
	 	@method descendingOrderBy
	 	@param columns {String} Name of columns which need to be sorted.
	 	@return {Object} Select Interface Implementation.
	 */
    this.descendingOrderBy = function(columns) {
        orderBy = columns;
        whichOrderBy = Clause.DESC_ORDER_BY;

        return this;
    }


	/**
	 	Used to specify the range of data need to fetch from table.
	 	
	 	@method limit
	 	@param limit {String} LIMIT of data.
	 	@return {Object} Select Interface Implementation.
	 */
    this.limit = function(val) {
        limit = val;
        return this;
    }


	/**
	 	Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 	
	 	@method groupBy
	 	@param columns {Array} Name of columns.
	 	@return {Object} Select Interface Implementation.
	 */
    this.groupBy = function(columns) {
        groupBy = orderBy;
        return this;
    }


	/**
	 	Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 	
	 	@method having
	 	@param column {String} Name of column on which condition need to be applied.
	 	@return {Clause} Clause Interface Implementation.
	 */
    this.having = function(column) {
        having = new Clause(this);
        having.addColumn(column);

        return having;
    }


	/**
	 	Used to provide manually created Where clause, instead of using API's.
	 	
	 	@method havingClause
	 	@param havingClause {String} Where clause.
	 	@return {Object} Select Interface Implementation.
	 */
    this.havingClause = function(val) {
        havingClause = val;
        return this;
    }


	/**
	 	Used to provide name of column only for which data will be fetched.
	 	
	 	@method column
	 	@param column {String} Name of column.
	 	@return {Object} Select Interface Implementation.
	 */
    this.column = function(val) {
        column = val;
        return this;
    }


	/**
	 	Used to provide name of columns only for which data will be fetched.
	 	
	 	@method columns
	 	@param column {Array} Name of columns.
	 	@return {Object} Select Interface Implementation.
	 */
    this.columns = function(val) {
        columns = val;
        return this;
    }


    this.delimiter = function(val) {
        delimiter = val;
        return this;
    }


	/**
		Process the request specified by application.
		
		@method execute
	*/
    this.execute = function() {

        var whereCondition = "";
        if(whereClause == undefined || whereClause.length <= 0) {
            if(where != undefined && where != null) {
                whereCondition = where.toString();
            }
        } else {
            whereCondition = whereClause;
        }

        var havingCondition = "";
        if(havingClause == undefined || havingClause.length <= 0) {
            if(having != undefined && having !=  null) {
                havingCondition = having.toString();
            }
        } else {
            havingCondition = havingClause;
        }

        if(columns == undefined || columns == null) {
            columns = [];
        }


        if(orderBy == undefined || orderBy == null) {
            orderBy = [];
        }


        if(groupBy == undefined || groupBy == null) {
            groupBy = [];
        }


        if(this.interfaceName ==  "ICount") {
            return Database.count(object.getObjectName(), column, distinct, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "IAverage") {
            return Database.avg(object.getObjectName(), column, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "ISum") {
            return Database.sum(object.getObjectName(), column, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "ITotal") {
            return Database.total(object.getObjectName(), column, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "IMax") {
            return Database.max(object.getObjectName(), column, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "IMin") {
            return Database.min(object.getObjectName(), column, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "IGroupConcat") {
            return Database.groupConcat(object.getObjectName(), column, delimiter, whereCondition, groupBy, havingCondition);
        } else if(this.interfaceName == "IDelete") {

            if(whereCondition == undefined && whereCondition == null && whereCondition.length <= 0) {
                var datas = SIDatasHelper.toSI(object);
                var json = SIJsonHelper.toJson(datas);

                Database['delete'](object.getObjectName(), undefined, json);

            } else {
                Database['delete'](object.getObjectName(), whereCondition, undefined);
            }
        }
    }


	/**
	 	Used to get tuples, this method should be called in last to get tuples from table.
	 	
	 	@method fetch
	 	@return {Object} Return array of model objects.
	 	@throws {SiminovException} Throws exception if any error occur while getting tuples from table. 
	 */
    this.fetch = function() {

        var whereCondition = "";
        if(whereClause == undefined || whereClause.length <= 0) {
            if(where != undefined && where != null) {
                whereCondition = where.toString();
            }
        } else {
            whereCondition = whereClause;
        }


        var havingCondition = "";
        if(havingClause == undefined || havingClause.length <= 0) {
            if(having != undefined && having !=  null) {
                havingCondition = having.toString();
            }
        } else {
            havingCondition = havingClause;
        }


        if(columns == undefined || columns == null) {
            columns = [];
        }


        if(orderBy == undefined || orderBy == null) {
            orderBy = [];
        }


        if(groupBy == undefined || groupBy == null) {
            groupBy = [];
        }


        if(limit == undefined || limit.length <= 0) {
            limit = "0";
        }

        return Database.select(object.getObjectName(), distinct, columns, groupBy, having, orderBy, whichOrderBy, limit);

    }

}
