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



var Resources = (function() {

	var resources;
	
	return {
	
		getInstance : function() {
			if(resources == null) {
				resources = new Resources();
				
				resources.constructor = null;
			}
			
			return resources;
		}
	
	}
	

	function Resources() {
		
		var databases = new Dictionary();
		
	    this.getApplicationDescriptor = function() {

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_APPLICATION_DESCRIPTOR_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var applicationDescriptor = SIDatasHelper.toModels(datas);
	
	        return applicationDescriptor[0];
	
	    }
	
	    this.getDatabaseDescriptorPaths = function() {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_PATHS_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptorPaths = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptorPaths;
	    }
	
	
	    this.getDatabaseDescriptorBasedOnPath = function(path) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_BASED_ON_PATH_HANDLER);
	
	        adapter.addParameter(path);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptor[0];
	
	    }
	
	    this.getDatabaseDescriptorBasedOnName = function(databaseName) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_BASED_ON_NAME_HANDLER);
	
	        adapter.addParameter(databaseName);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptor[0];
	
	    }
	
	
	    this.getDatabaseDescriptors = function() {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTORS_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptors = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptors;
	
	    }
	
	
	    this.getDatabaseDescriptorBasedOnClassName = function(className) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER);
	
	        adapter.addParameter(className);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptor[0];
	
	    }
	
	
	    this.getDatabaseDescriptorBasedOnTableName = function(tableName) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER);
	
	        adapter.addParameter(tableName);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptor[0];
	
	    }
	
	
		this.getDatabaseDescriptorNameBasedOnClassName = function(className) {

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_NAME_BASED_ON_CLASS_NAME_HANDLER);
	
	        adapter.addParameter(tableName);
	
	        var data = adapter.invoke();
						
		 	var hybridSiminovDatas = SIJsonHelper.toSI(data);
			if(hybridSiminovDatas != undefined && hybridSiminovDatas != null) {
				var datas = datas.getHybridSiminovDatas();
				if(datas != undefined && datas != null && datas.length > 0) {
					var data = datas[0];
					
					if(data != undefined && data != null) {
						var values = data.getValues();
						
						if(values != undefined && values != null && values.length > 0) {
							return value[0].getValue();
						}
					}
				} 
			}
			
		}
		
		
		this.getDatabaseDescriptorNameBasedOnTableName = function(className) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_NAME_BASED_ON_TABLE_NAME_HANDLER);
	
	        adapter.addParameter(tableName);
	
	        var data = adapter.invoke();
						
		 	var hybridSiminovDatas = SIJsonHelper.toSI(data);
			if(hybridSiminovDatas != undefined && hybridSiminovDatas != null) {
				var datas = datas.getHybridSiminovDatas();
				if(datas != undefined && datas != null && datas.length > 0) {
					var data = datas[0];
					
					if(data != undefined && data != null) {
						var values = data.getValues();
						
						if(values != undefined && values != null && values.length > 0) {
							return value[0].getValue();
						}
					}
				} 
			}
			
		}
		
	
	    this.getDatabaseMappingDescriptorBasedOnClassName = function(className) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_MAPPING_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER);
	
	        adapter.addParameter(className);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseMappingDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseMappingDescriptor[0];
	
	    }
	
	
	    this.getDatabaseMappingDescriptorBasedOnTableName = function(tableName) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_MAPPING_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER);
	
	        adapter.addParameter(tableName);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseMappingDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseMappingDescriptor[0];
	
	    }
	
		
		this.getDatabaseMappingDescriptors = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_MAPPING_DESCRIPTORS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var databaseMappingDescriptors = SIDatasHelper.toModels(datas);
			
			return databaseMappingDescriptors;
		
		}
		
		
		this.getLibraryDescriptorPaths = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_DESCRIPTOR_PATHS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptorPaths = SIDatasHelper.toModels(datas);
			
			return libraryDescriptorPaths;
		
		}
		
		
		this.getLibraryPathsBasedOnDatabaseDescriptorName = function(databaseDescriptorName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_PATHS_BASED_ON_DATABASE_DESCRIPTOR_NAME_HANDLER);
	        
	        adapter.addParameter(databaseDescriptorName);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptorPaths = SIDatasHelper.toModels(datas);
			
			return libraryDescriptorPaths;
		
		}
		
		
		this.getLibraryDescriptors = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_DESCRIPTORS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptors = SIDatasHelper.toModels(datas);
			
			return libraryDescriptors;
			
		}
		
		
		this.getLibrariesBasedOnDatabaseDescriptorName = function(databaseDescriptorName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARIES_BASED_ON_DATABASE_DESCRIPTOR_NAME_HANDLER);
	        
	        adapter.addParameter(databaseDescriptorName);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptors = SIDatasHelper.toModels(datas);
			
			return libraryDescriptors;
			
		}
		
		
		this.getLibraryDatabaseMappingDescriptorsBasedOnLibraryDescriptorPath = function(libraryDescriptorPath) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_DATABASE_MAPPING_DESCRIPTORS_BASED_ON_LIBRARY_DESCRIPTOR_PATH_HANDLER);
	        
	        adapter.addParameter(libraryDescriptorPath);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptors = SIDatasHelper.toModels(datas);
			
			return libraryDescriptors;
			
		}
	
		
		this.getHybridDescriptor = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_HYBRID_DESCRIPTOR_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var hybridDescriptor = SIDatasHelper.toModels(datas);
			
			return hybridDescriptor;
			
		}
		
		
		this.getAdapters = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
			
		}
		
		
		this.getLibrariesAdapters = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARIES_ADAPTERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryAdapters = SIDatasHelper.toModels(datas);
			
			return libraryAdapters;
			
		}
		
		
		this.getAdaptersBasedOnPaths = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTERS_BASED_ON_PATHS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
		
		}
		
		
		this.getLibraryAdaptersBasedOnName = function(libraryName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_ADAPTERS_BASED_ON_NAME_HANDLER);
	        
	        adapter.addParameter(libraryName);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
			
		}
	
	
		this.getLibraryAdaptersBasedOnPath = function(libraryPath) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_ADAPTERS_BASED_ON_PATH_HANDLER);
	        
	        adapter.addParameter(libraryPath);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
		
		}
		
			
		this.getAdapter = function(adapterName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTER_HANDLER);
	        
	        adapter.addParameter(libraryPath);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapter = SIDatasHelper.toModels(datas);
			
			return adapter[0];
			
		}	
		
		
		this.getAdapterBasedOnPath = function(adapterPath) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTER_BASED_ON_PATH_HANDLER);
	        
	        adapter.addParameter(adapterPath);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapter = SIDatasHelper.toModels(datas);
			
			return adapter[0];
			
		}
		
		
		this.getLibraryAdapterBasedOnName = function(libraryName, adapterName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_ADAPTER_BASED_ON_NAME_HANDLER);
	        
	        adapter.addParameter(libraryName);
	        adapter.addParameter(adapterName);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapter = SIDatasHelper.toModels(datas);
			
			return adapter[0];
			
		}
			
			
		this.getLibraryAdapterBasedOnPath = function(libraryPath, adapterPath) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_ADAPTER_BASED_ON_PATH_HANDLER);
	        
	        adapter.addParameter(libraryPath);
	        adapter.addParameter(adapterPath);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapter = SIDatasHelper.toModels(datas);
			
			return adapter[0];
			
		}
		
		
		this.getHandlers = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_HANDLERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var handlers = SIDatasHelper.toModels(datas);
			
			return handlers;
			
		}
			
			
		this.getHandler = function(adapterName, handlerName) {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_HANDLER_HANDLER);
	        
	        adapter.addParameter(adapterName);
	        adapter.addParameter(handlerName);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var handler = SIDatasHelper.toModels(datas);
			
			return handler[0];
			
		}	
		
		
		this.getDatabases = function() {
			return database.values();
		}
		
		
		this.getDatabaseBasedOnDatabaseDescriptorName = function(databaseDescriptorName) {
			
			if(this.containDatabaseBasedOnDatabaseDescriptorName(databaseDescriptorName)) {
				return databases.get(databaseDescriptorName);
			}
			
			return this.requireDatabase(databaseDescriptorName);

		}
		
		
		this.getDatabaseBasedOnDatabaseMappingDescriptorClassName = function(className) {
			var databaseDescriptorName = getDatabaseDescriptorNameBasedOnClassName(className);
			return getDatabaseBasedOnDatabaseDescriptorName(databaseDescriptorName);
		}
		
		
		this.getDatabaseBasedOnDatabaseMappingDescriptorTableName = function(tableName) {
			var databaseDescriptorName = getDatabaseDescriptorNameBasedOnTableName(tableName);
			return getDatabaseBasedOnDatabaseDescriptorName(databaseDescriptorName);						
		}
		
		
		this.containDatabaseBasedOnDatabaseDescriptorName = function(databaseDescriptorName) {
			return databases.exists(databaseDescriptorName);
		}
		
		this.addDatabase = function(databaseDescriptorName, database) {
			databases.add(databaseDescriptorName, database);
		}
		
		
		this.requireDatabase = function(databaseDescriptorName) {
		
			if(this.containDatabaseBasedOnDatabaseDescriptorName(databaseDescriptorName)) {
				return databases.get(databaseDescriptorName);
			}
		
			var databaseDescriptor = this.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
			var database = DatabaseFactory.getInstance().getDatabase(databaseDescriptor);
		
			databases.add(databaseDescriptor.getDatabaseName(), database);
		
			return database;
		
		}
		
	}

		
}) ();