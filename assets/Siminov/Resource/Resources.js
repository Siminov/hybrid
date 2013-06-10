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
		
		/**
	 		Get Application Descriptor object of application.
	 		@return Application Descriptor.
	 	*/
	    this.getApplicationDescriptor = function() {

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_APPLICATION_DESCRIPTOR_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var applicationDescriptor = SIDatasHelper.toModels(datas);
	
	        return applicationDescriptor[0];
	
	    }
	
		/**
		 * Get iterator of all database descriptors provided in Application Descriptor file.
			<p>
				<pre>
	Example: ApplicationDescriptor.xml
		
		{@code
		<core>
		
			<database-descriptors>
				<database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
			</database-descriptors>
	
		</core>
		}
		
				</pre>
			</p>
		 * @return Iterator which contains all database descriptor paths provided.
		 */
	    this.getDatabaseDescriptorPaths = function() {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTOR_PATHS_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptorPaths = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptorPaths;
	    }
	
		
		/**
		 * Get DatabaseDescriptor based on path provided as per defined in Application Descriptor file.
			<p>
				<pre>
				
	Example: ApplicationDescriptor.xml
		
		{@code
		<core>
		
			<database-descriptors>
				<database-descriptor>DatabaseDescriptor.xml</database-descriptor>
			</database-descriptors>
	
		</core>
		}
		
				</pre>
			</p>
		 
		 * @param databaseDescriptorPath Iterator which contains all database descriptor paths provided.
		 * @return
		 */
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
	
		/**
		 * Get Database Descriptor based on database descriptor name provided as per defined in Database Descriptor file.
			<p>
				<pre>
				
	Example: DatabaseDescriptor.xml
		
		{@code
		<database-descriptor>
		
			<property name="database_name">SIMINOV-TEMPLATE</property>
			
		</database-descriptor>
		}
		
				</pre>
			</p>
		 
		 * 
		 * @param databaseDescriptorName Database Descriptor object based on database descriptor name provided.
		 * @return
		 */
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
	
		
		/**
		 * Get all Database Descriptors object.
		 * @return Iterator which contains all Database Descriptors.
		 */
	    this.getDatabaseDescriptors = function() {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_DESCRIPTORS_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptors = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptors;
	
	    }
	
		/**
		 * Get Database Descriptor based on POJO class name provided.
		 * 
		 * @param className POJO class name.
		 * @return Database Descriptor object in respect to POJO class name.
		 */
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
	
		
		/**
		 * Get Database Descriptor based on table name provided.
		 * 
		 * @param tableName Name of table.
		 * @return Database Descriptor object in respect to table name.
		 */
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
		
		
		/**
		 * Get Database Mapping based on POJO class name provided.
		 * 
		 * @param className POJO class name.
		 * @return Database Mapping object in respect to POJO class name.
		 */	
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
	
		
		/**
		 * Get Database Mapping based on table name provided.
		 * 
		 * @param tableName Name of table.
		 * @return Database Descriptor object in respect to table name.
		 */
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
		
		
		/**
		 * Get all library paths based on Database Descriptor name.
		 * @param databaseDescriptorName Name of Database Descriptor.
		 * @return Iterator which contains all library paths based on Database Descriptor.
		 */
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
		
		
		/**
		 * Get all Library Descriptor objects based on Database Descriptor name.
		 * @param databaseDescriptorName Name of Database Descriptor.
		 * @return Iterator which contains all Library Descriptor objects based on Database Descriptor name.
		 */
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
		
		
		/**
		 * Get all Library Database Mapping objects based in library descriptor path.
		 * @param libraryPath Library Descriptor path.
		 * @return
		 */
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
	
		
		/**
		 * Get Hybrid Descriptor.
		 * @return Hybrid Descriptor.
		 */
		this.getHybridDescriptor = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_HYBRID_DESCRIPTOR_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var hybridDescriptor = SIDatasHelper.toModels(datas);
			
			return hybridDescriptor;
			
		}
		
		
		/**
		 * Get All Adapters defined by Application.
		 * @return All Adapters.
		 */
		this.getAdapters = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
			
		}
		
		
		/**
		 * Get All Adapters defined in Libraries.
		 * @return All Adapters.
		 */
		this.getLibrariesAdapters = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARIES_ADAPTERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryAdapters = SIDatasHelper.toModels(datas);
			
			return libraryAdapters;
			
		}
		
		
		/**
		 * Get All Adapters Defined By Paths.
		 * @return All Adapters.
		 */
		this.getAdaptersBasedOnPaths = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_ADAPTERS_BASED_ON_PATHS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var adapters = SIDatasHelper.toModels(datas);
			
			return adapters;
		
		}
		
		
		/**
		 * Get All Adapters based on library name.
		 * @param libraryName Name of Library.
		 * @return All Adapters.
		 */
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
	
	
		/**
		 * Get All Adapters based on library path.
		 * @param libraryPath Path of Library.
		 * @return All Adapters.
		 */
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
		
		
		/**
		 * Get Adapter based on Adapter Name.
		 * @param adapterName Name of Adapter.
		 * @return
		 */
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
		
		
		/**
		 * Get Adapter based on adapter path.
		 * @param adapterPath Path of Adapter.
		 * @return Adapter.
		 */
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
		
		
		/**
		 * Get Adapter based on library name and adapter name,
		 * @param libraryName Name of Library.
		 * @param adapterName Name of Adapter.
		 * @return Adapter.
		 */
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
			
		
		/**
		 * Get Adapter based on library path and adapter path.
		 * @param libraryPath Name of Library.
		 * @param adapterPath Path of Adapter.
		 * @return Adapter.
		 */
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
		
		
		/**
		 * Get All Handlers defined by Application.
		 * @return All Handlers.
		 */
		this.getHandlers = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_HANDLERS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var handlers = SIDatasHelper.toModels(datas);
			
			return handlers;
			
		}
			
		
		/**
		 * Get Handler based on Adapter Name and Handler Name.
		 * @param adapterName Name of Adapter.
		 * @param handlerName Name of Handler.
		 * @return Handler.
		 */
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
