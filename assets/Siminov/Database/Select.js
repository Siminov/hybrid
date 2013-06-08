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

	@module Database
	@class Select
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


    this.distinct = function(val) {
        distinct = val;
    }


    this.where = function(column) {
        where = new Clause(this);
        where.addColumn(column);

        return where;
    }


    this.whereClause = function(where) {
        whereClause = where;
        return this;
    }


    this.add = function(column) {
        where.and(column);
        return where;
    }


    this.or = function(column) {
        where.or(column);
        return where;
    }


    this.orderBy = function(columns) {
        orderBy = columns;
        return this;
    }


    this.ascendingOrderBy = function(columns) {
        orderBy = columns;
        whichOrderBy = Clause.ASC_ORDER_BY;

        return this;
    }


    this.descendingOrderBy = function(columns) {
        orderBy = columns;
        whichOrderBy = Clause.DESC_ORDER_BY;

        return this;
    }


    this.limit = function(val) {
        limit = val;
        return this;
    }


    this.groupBy = function(columns) {
        groupBy = orderBy;
        return this;
    }


    this.having = function(column) {
        having = new Clause(this);
        having.addColumn(column);

        return having;
    }


    this.havingClause = function(val) {
        havingClause = val;
        return this;
    }


    this.column = function(val) {
        column = val;
        return this;
    }


    this.columns = function(val) {
        columns = val;
        return this;
    }


    this.delimiter = function(val) {
        delimiter = val;
        return this;
    }


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
