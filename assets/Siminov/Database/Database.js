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


function Database() {

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


    this.select = function() {
        return new ISelect(new Select(this));
    }


    this['delete'] = function() {
        return new IDelete(new Select(this));
    }


    this.avg = function() {
        return new IAverage(new Select(this));
    }


    this.count = function() {
        return new ICount(new Select(this));
    }


    this.max = function() {
        return new IMax(new Select(this));
    }


    this.min = function() {
        return new IMin(new Select(this));
    }


    this.sum = function() {
        return new ISum(new Select(this));
    }


    this.total = function() {
        return new ITotal(new Select(this));
    }


    this.groupConcat = function() {
        return new IGroupConcat(new Select(this));
    }


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


    this.getDatabaseDescriptor = function() {

        var resources = Resources.getInstance();
        return resources.getDatabaseDescriptorBasedOnClassName(this.getObjectName());

    }


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

