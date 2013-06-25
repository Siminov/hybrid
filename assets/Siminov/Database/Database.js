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
	Exposes methods to deal with database.
	It has methods to create, delete, and perform other common database management tasks.

	@module Database	
	@class Database	
	@constructor
*/
function Database() {


	
	/**
		It adds a record to any single table in a relational database.

	   	<pre>
	   	
		Example: Make Liquor Object
	
			var beer = new Liquor();
			beer.setLiquorType(Liquor.LIQUOR_TYPE_BEER);
			beer.setDescription(beer_description));
			beer.setHistory(beer_history));
			beer.setLink(beer_link));
			beer.setAlcholContent(beer_alchol_content));
		  
			try {
				beer.save();
			} catch(DatabaseException de) {
				//Log it.
			}

	    </pre>

		@method save	 
	   	@throws {SiminovException} If any error occurs while saving tuples in database.
	 */
	this.save = function() {

        var datas = SIDatasHelper.toSI(this);
        var json = SIJsonHelper.toJson(datas);

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_SAVE_HANDLER);

        adapter.addParameter(encodeURI(json));

        var data = adapter.invoke();
        if(data != undefined && data != null) {

            var siminovDatas = SIJsonHelper.toSI(data);
            var exceptions = SIDatasHelper.toModels(siminovDatas);

            if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
                var exception = exceptions[0];
                if(exception != undefined && exception != null) {
                    throw exception;
                }
            }
        }

	}



	/**
		It updates a record to any single table in a relational database.
	
		Example: Make Beer Object
		
			var beer = new Liquor();
			beer.setLiquorType(Liquor.LIQUOR_TYPE_BEER);
			beer.setDescription(beer_description));
			beer.setHistory(beer_history));
			beer.setLink(beer_link));
			beer.setAlcholContent(beer_alchol_content));
		 
			try {
				beer.update();
			} catch(DatabaseException de) {
				//Log it.
			}

		@method update
	   	@throws DatabaseException If any error occurs while saving tuples in database.
	 */
    this.update = function() {

        var datas = SIDatasHelper.toSI(this);
        var json = SIJsonHelper.toJson(datas);

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_UPDATE_HANDLER);

        adapter.addParameter(encodeURI(json));

        var data = adapter.invoke();
        if(data != undefined && data != null) {

            var siminovDatas = SIJsonHelper.toSI(data);
            var exceptions = SIDatasHelper.toModels(siminovDatas);

            if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
                var exception = exceptions[0];
                if(exception != undefined && exception != null) {
                    throw exception;
                }
            }
        }

    }

	
	
	/**
		It finds out whether tuple exists in table or not.
		IF NOT EXISTS:
			adds a record to any single table in a relational database.
		ELSE:
			updates a record to any single table in a relational database.
	   	
		Example: Make Beer Object
		
			var beer = new Liquor();
			beer.setLiquorType(Liquor.LIQUOR_TYPE_BEER);
			beer.setDescription(beer_description));
			beer.setHistory(beer_history));
			beer.setLink(beer_link));
			beer.setAlcholContent(beer_alchol_content));
		  
			try {
				beer.saveOrUpdate();
			} catch(DatabaseException de) {
				//Log it.
			}

		@method saveOrUpdate						
	   	@throws {SiminovException} If any error occurs while saving tuples in database.
	 */
    this.saveOrUpdate = function() {

        var datas = SIDatasHelper.toSI(this);
        var json = SIJsonHelper.toJson(datas);

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_SAVE_OR_UPDATE_HANDLER);

        adapter.addParameter(encodeURI(json));

        var data = adapter.invoke();
        if(data != undefined && data != null) {

            var siminovDatas = SIJsonHelper.toSI(data);
            var exceptions = SIDatasHelper.toModels(siminovDatas);
	
	       if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
                var exception = exceptions[0];
                if(exception != undefined && exception != null) {
                    throw exception;
                }
            }
        }

    }



	/**
	 	Fetch tuples from table.

		Example:
	
		var liquors =  new Liquor().select()
						.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
						.and(Liquor.ALCHOL_CONTENT).equalTo("90%")
						.fetch();
		
		@method select
	 	@return {ISelect} ISelect to provide extra information based on which tuples will be fetched from table.
	 	@throws {SiminovException} If any error occur while fetching tuples from table.
	 */
    this.select = function() {
    
    	if(arguments.length > 0) {
    	
		    var adapter = new Adapter();
		    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
		    adapter.setHandlerName(Constants.SIMINOV_DATABASE_SELECT_MANUAL_HANDLER);
		
		    adapter.addParameter(className);
		    adapter.addParameter(arguments[0]);
		
		    var data = adapter.invoke();
		    var datas = SIJsonHelper.toSI(data);
		    
		    var models = SIDatasHelper.toModels(datas);
			if(models != undefined && models != null && models.length > 0) {
				for(var i = 0;i < models.length;i++) {
					var model = models[i];
					
					if(model instanceof SiminovException) {
						throw model;
					}
				}
			}
		
		    return models;
    	}
    
        return new ISelect(new Select(this));
    }

	

	/**
		It deletes a record to any single table in a relational database.
	
		Example:
		
			var beer = new Liquor();
			beer.setLiquorType(Liquor.LIQUOR_TYPE_BEER);
			beer.setDescription(beer_description));
			beer.setHistory(beer_history));
			beer.setLink(beer_link));
			beer.setAlcholContent(beer_alchol_content));
		  
			try {
				beer.delete();
			} catch(DatabaseException de) {
				//Log it.
			}
					
			OR
					
			try {
				new Liquor().delete();
			} catch(DatabaseException de) {
				//Log It.
			}
			
		@method delete	
	   	@throws {SiminovException} If any error occurs while saving tuples in database.
	 */
    this['delete'] = function() {
        return new IDelete(new Select(this));
    }



	/**
	 	Returns the average based on column name provided.
	 	
		Example:
			var average = 0;
			
			try {
				average = new Liquor().avg()
							.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
		
			} catch(DatabaseException de) {
				//Log it.
			}
		
		@method avg
	 	@return {IAverage} IAverage to provide extra information based on which average will be calculated.
	 	@throws {SiminovException} If any error occur while finding average.
	 */
    this.avg = function() {
        return new IAverage(new Select(this));
    }



	/**
	 	Returns the count of rows based on information provided.
	 	
		Example:
			var count = 0;
			
			try {
				count = new Liquor().count().
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
				
			} catch(DatabaseException de) {
				//Log it.
			}
		
		@method count
	 	@return {ICount} ICount to provide extra information based on which count will be calculated.
	 	@throws {SiminovException} If any error occur while find count.
	 */
    this.count = function() {
        return new ICount(new Select(this));
    }



	/**
	 	Returns the minimum based on column name provided.
	 	
		Example:
			var maximum = 0;
			
			try {
				maximum = new Liquor().max()
							.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
				
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method max
	 	@return {IMax} IMax to provide extra information based on which maximum will be calculated.
	 	@throws {SiminovException} If any error occur while finding minimum.
	 */
    this.max = function() {
        return new IMax(new Select(this));
    }



	/**
	 	Returns the minimum based on column name provided.
	 	
		Example:
			var minimum = 0;
			
			try {
				minimum = new Liquor().min()
							.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
				
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method min
	 	@return {IMin} IMin to provide extra information based on which minimum will be calculated.
	 	@throws {SiminovException} If any error occur while finding minimum.
	 */
    this.min = function() {
        return new IMin(new Select(this));
    }



	/**
	 	Returns the sum based on column name provided.
	 	
		Example:
			var sum = 0;
			
			try {
				sum = new Liquor().sum()
							.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
		
			} catch(DatabaseException de) {
				//Log it.
			}
			
		@method sum	
	 	@return {ISum} ISum to provide extra information based on which sum will be calculated.
	 	@throws {SiminovException} If any error occur while finding sum.
	 */
    this.sum = function() {
        return new ISum(new Select(this));
    }



	/**
	 	Returns the total based on column name provided.
	 	
		Example:
			var total = 0;
			
			try {
				total = new Liquor().total()
							.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
							.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
							.execute();
				
			} catch(DatabaseException de) {
				//Log it.
			}
	    
	    @method total
	 	@return {ITotal} ITotal to provide extra information based on which total will be calculated.
	 	@throws {SiminovException} If any error occur while finding total.
	 */
    this.total = function() {
        return new ITotal(new Select(this));
    }



	/**
	 	Returns the group concat based on column name provided.
	 	
		Example:
			var groupConcat = 0;
			
			try {
				groupConcat = new Liquor().groupConcat()
								.column(Liquor.COLUMN_NAME_WHICH_CONTAIN_NUMBRIC_VALUE)
								.where(Liquor.LIQUOR_TYPE).equalTo("RUM")
								.execute();
								
			} catch(DatabaseException de) {
				//Log it.
			}
		
	 	@method groupConcat
	 	@return {IGroupConcat} IGroupConcat to provide extra information based on which group concat will be calculated.
	 	@throws SiminovException If any error occur while finding group concat.
	 */
    this.groupConcat = function() {
        return new IGroupConcat(new Select(this));
    }



	/**
	 	Returns the mapped table name for invoked class object.
	 
		Example:
			var tableName = null;
			try {
				tableName = new Liquor().getTableName();
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method getTableName
	 	@return {String} Mapped Table name.
	 	@throws {SiminovException} If no mapped table found for invoked class object.
	 */
    this.getTableName = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_TABLE_NAME_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {
                for(var i = 0;i < datas.length;i++) {
                    if(datas[i] != undefined) {
                    	var type = datas[i].getDataType();
                    	
                    	if(type != undefined && type != null) {
                    		var exception = SIJsonHelper.toModel(datas[i]);
                    		if(exception != undefined && exception != null) {
	                    		throw exception;	
                    		}
                    	} else {
	                        return datas[i].getDataValue();
                    	}
                    }
                }
            }
        }

        return undefined;

    }




	/**
	 	Returns all column names of mapped table.
	 	
		Example:
			var columnNames = null;
			try {
				columnNames = new Liquor().getColumnNames();
			} catch(DatabaseException de) {
				//Log it.
			}
	 		
		@method getColumnNames
	 	@return {Array} All column names of mapped table.
	 	@throws {SiminovException} If no mapped table found for invoked class object.
	 */
    this.getColumnNames = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_COLUMN_NAMES_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var columnNames = [];

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {
                for(var i = 0;i < datas.length;i++) {
                    if(datas[i] != undefined) {
                    	var type = datas[i].getDataType();
                    	
                    	if(type != undefined && type != null) {
                    		var exception = SIJsonHelper.toModel(datas[i]);
                    		if(exception != undefined && exception != null) {
	                    		throw exception;	
                    		}
                    	} else {
	                        columnNames[columnNames.length] = datas[i].getDataValue();
                    	}
                    }
                }
            }
        }

        return columnNames;
    }




	/**
	 	Returns all columns with there data types for invoked class object.
	
		Example:
			var columnTypes = null;
			try {
				columnTypes = new Liquor().getColumnTypes();
			} catch(DatabaseException de) {
				//Log it.
			}	
	
		@method getColumnTypes
	 	@return {Dictionary} All columns with there data types.
	 	@throws {SiminovException} If no mapped table found for invoked class object.
	 */
    this.getColumnTypes = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_COLUMN_TYPES_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var columnTypes = new Dictionary();

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {

                for(var i = 0;i < datas.length;i++) {
					var type = datas[i].getDataType();
                    	
                	if(type != undefined && type != null) {
                		var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}
                	} else {
	                    var values = datas[i].getValues();
	                    if(values != undefined) {
	
	                        for(var j = 0;j < values.length;j++) {
	                            var value = values[j];
	
	                            columnTypes.add(value.getType(), value.getValue());
	                        }
	                    }
                	}
                }
            }
        }

        return columnTypes;

    }




	/**
	 	Returns all primary keys of mapped table for invoked class object.
	 	
		Example:
			var primaryKeys = null;
			try {
				primaryKeys = new Liquor().getPrimeryKeys();
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method getPrimaryKeys
	 	@return {Array} All primary keys.
	 	@throws {SiminovException} If not mapped table found for invoked class object.
	 */
    this.getPrimaryKeys = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_PRIMARY_KEYS_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var primaryKeys = [];

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {

                for(var i = 0;i < datas.length;i++) {
                	var type = datas[i].getDataType();
                    	
                	if(type != undefined && type != null) {
                		var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}
                	} else {
                        primaryKeys[i] = datas[i].getDataValue();
                	}
                }
            }
        }

        return primaryKeys;

    }




	/**
	 	Returns all mandatory fields which are associated with mapped table for invoked class object.
	 
		Example:
			var mandatoryFields = null;
			try {
				mandatoryFields = new Liquor().getMandatoryFields();
			} catch(DatabaseException de) {
				//Log it.
			}

		@method getMandatoryFields
	 	@return {Array} All mandatory fields for mapped table.
	 	@throws SiminovException If no mapped table found for invoked class object.
	 */
    this.getMandatoryFields = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_MANDATORY_FIELDS_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var mandatoryFields = [];

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {

                for(var i = 0;i < datas.length;i++) {
                	var type = datas[i].getDataType();
                    	
                	if(type != undefined && type != null) {
                		var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}
                	} else {
	                    mandatoryFields[i] = datas[i].getDataValue();
                	}
                }
            }
        }

        return mandatoryFields;

    }



	/**
	 	Returns all unique fields which are associated with mapped table for invoked class object.
	 
		Example:
			 			
			var uniqueFields = null;
			try {
				uniqueFields = new Liquor().getUniqueFields();
			} catch(DatabaseException de) {
				//Log it.
			}

		@method getUniqueFields	 		
	 	@return {Array} All unique fields for mapped table.
	 	@throws {SiminovException} If no mapped table found for invoked class object.
	 */
    this.getUniqueFields = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_UNIQUE_FIELDS_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var uniqueFields = [];

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {

                for(var i = 0;i < datas.length;i++) {
                	var type = datas[i].getDataType();
                    	
                	if(type != undefined && type != null) {
                		var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}
                	} else {
	                    uniqueFields[i] = datas[i].getDataValue();
                	}
                }
            }
        }

        return uniqueFields;

    }




	/**
	 	Returns all foreign keys of mapped table for invoked class object.
	 
		Example:
			 			
			var foreignKeys = null;
			try {
				foreignKeys = new Liquor().getForeignKeys();
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method getForeignKeys
	 	@return {Array} All foreign keys of mapped table.
	 	@throws {SiminovException} If no mapped table found for invoked class object.
	 */
    this.getForeignKeys = function() {

        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
        adapter.setHandlerName(Constants.SIMINOV_DATABASE_GET_FOREIGN_KEYS_HANDLER);

        adapter.addParameter(this.getObjectName());

        var data = adapter.invoke();

        var hybridData = SIJsonHelper.toSI(data);
        var foreignKeys = [];

        if(hybridData != undefined) {
            var datas = hybridData.getHybridSiminovDatas();
            if(datas != undefined) {

                for(var i = 0;i < datas.length;i++) {
                	var type = datas[i].getDataType();
                    	
                	if(type != undefined && type != null) {
                		var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}
                	} else {
	                    foreignKeys[i] = datas[i].getDataValue();
                	}
                }
            }
        }

        return foreignKeys;

    }




	/**
	 	Returns database descriptor object based on the POJO class called.

		Example:
		
			try {
				DatabaseDescriptor databaseDescriptor = new Liquor().getDatabaseDescriptor();
			} catch(DatabaseException databaseException) {
				//Log It.
			}
	
		@method getDatabaseDescriptor
	 	@return {DatabaseDescriptor} Database Descriptor Object.
	 	@throws {SiminovException} If any error occur while getting database descriptor object.
	 */
    this.getDatabaseDescriptor = function() {

        var resources = Resources.getInstance();
        return resources.getDatabaseDescriptorBasedOnClassName(this.getObjectName());

    }




	/**
	 	Returns the actual database mapping object mapped for invoked class object.
	 
		Example:
			DatabaseMapping databaseMapping = null;
			try {
				databaseMapping = new Liquor().getDatabaseMapping();
			} catch(DatabaseException de) {
				//Log it.
			}
	
		@method getDatabaseMappingDescriptor
	 	@return {DatabaseMappingDescriptor} Database Mapping Descriptor Object
	 	@throws {SiminovException} If database mapping object not mapped for invoked class object.
	 */
    this.getDatabaseMappingDescriptor = function() {

        var resources = Resources.getInstance();
        return resources.getDatabaseMappingDescriptorBasedOnClassName(this.getObjectName());

    }

}



Database.select = function(className, distinct, whereClause, columnNames, groupBy, having, orderBy, whichOrderBy, limit) {

    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_SELECT_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(distinct);
    adapter.addParameter(whereClause);
    adapter.addParameter(columnNames);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);
    adapter.addParameter(orderBy);
    adapter.addParameter(whichOrderBy);
    adapter.addParameter(limit);

    var data = adapter.invoke();
    var datas = SIJsonHelper.toSI(data);
    
    var models = SIDatasHelper.toModels(datas);
	if(models != undefined && models != null && models.length > 0) {
		for(var i = 0;i < models.length;i++) {
			var model = models[i];
			
			if(model instanceof SiminovException) {
				throw model;
			}
		}
	}

    return models;

}



Database.count = function(className, column, distinct, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_COUNT_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(distinct);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                	var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;
}


Database.avg = function(className, column, whereClause, groupBy, hanving) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_AVERAGE_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                	var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}


Database.min = function(className, column, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_MIN_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                    var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}


Database.max = function(className, column, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_MAX_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                    var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}


Database.sum = function(className, column, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_SUM_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                    var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}


Database.total = function(className, column, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_TOTAL_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                    var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}


Database.groupConcat = function(className, column, delimiter, whereClause, groupBy, having) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_GROUP_CONCAT_HANDLER);

    adapter.addParameter(className);
    adapter.addParameter(delimiter);
    adapter.addParameter(column);
    adapter.addParameter(whereClause);
    adapter.addParameter(groupBy);
    adapter.addParameter(having);


    var data = adapter.invoke();

    var hybridData = SIJsonHelper.toSI(data);
    if(hybridData != undefined) {
        var datas = hybridData.getHybridSiminovDatas();
        if(datas != undefined) {
            for(var i = 0;i < datas.length;i++) {
                if(datas[i] != undefined) {
                    var type = datas[i].getDataType();
                	
                	if(type != undefined && type != null) {
						var exception = SIJsonHelper.toModel(datas[i]);
                		if(exception != undefined && exception != null) {
                    		throw exception;	
                		}                	
                	} else {
	                    return datas[i].getDataValue();
                	}
                }
            }
        }
    }

    return 0;

}



Database['delete'] = function(className, whereClause, data) {

    var adapter = new Adapter();

    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_DELETE_HANDLER);


    adapter.addParameter(className);
    adapter.addParameter(whereClause);
    adapter.addParameter(data);

    var data = adapter.invoke();
    if(data != undefined && data != null) {

        var siminovDatas = SIJsonHelper.toSI(data);
        var exceptions = SIDatasHelper.toModels(siminovDatas);

        if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
            var exception = exceptions[0];
            if(exception != undefined && exception != null) {
                throw exception;
            }
        }
    }

}



Database.beginTransaction = function(databaseDescriptor) {

    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_BEGIN_TRANSACTION_HANDLER);

    adapter.addParameter(databaseDescriptor.getDatabaseName());

    var data = adapter.invoke();
    if(data != undefined && data != null) {

        var siminovDatas = SIJsonHelper.toSI(data);
        var exceptions = SIDatasHelper.toModels(siminovDatas);

        if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
            var exception = exceptions[0];
            if(exception != undefined && exception != null) {
                throw exception;
            }
        }
    }

}


Database.commitTransaction = function(databaseDescriptor) {

    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_COMMIT_TRANSACTION_HANDLER);

    adapter.addParameter(databaseDescriptor.getDatabaseName());

    var data = adapter.invoke();
    if(data != undefined && data != null) {

        var siminovDatas = SIJsonHelper.toSI(data);
        var exceptions = SIDatasHelper.toModels(siminovDatas);

        if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
            var exception = exceptions[0];
            if(exception != undefined && exception != null) {
                throw exception;
            }
        }
    }

}


Database.endTransaction = function(databaseDescriptor) {

    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_DATABASE_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_DATABASE_END_TRANSACTION_HANDLER);

    adapter.addParameter(databaseDescriptor.getDatabaseName());

    var data = adapter.invoke();
    if(data != undefined && data != null) {

        var siminovDatas = SIJsonHelper.toSI(data);
        var exceptions = SIDatasHelper.toModels(siminovDatas);

        if(exceptions != undefined && exceptions != null && exceptions.length > 0) {
            var exception = exceptions[0];
            if(exception != undefined && exception != null) {
                throw exception;
            }
        }
    }

}

