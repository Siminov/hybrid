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
	It contain all class related to Siminov Framework resource.
	
	@module Resource
*/

/**
	It handles and provides all resources needed by SIMINOV HYBRID.
	
	@module Resource
	@class Resources
	@constructor
	
*/
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
		
		/**
	 		Get Application Descriptor object of application.
	 		
	 		@method getApplicationDescriptor
	 		@return {ApplicationDescriptor} Application Descriptor.
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
		 	Get all Database Descriptors object.
		 	
		 	@method getDatabaseDescriptors 
		 	@return {Array} It which contains all Database Descriptors.
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
		 	Get Database Descriptor based on database descriptor name provided as per defined in Database Descriptor file.
				
			Example: DatabaseDescriptor.si.xml
				
				<database-descriptor>
				
					<property name="database_name">SIMINOV-HYBRID-TEMPLATE</property>
					
				</database-descriptor>
		 
		 	@method getDatabaseDescriptor
		 	@param databaseDescriptorName Database Descriptor object based on database descriptor name provided.
		 	@return {DatabaseDescriptor} Database Descriptor
		 */
	    this.getDatabaseDescriptor = function(databaseName) {
	
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
		 	Get all database mapping descriptors
		 	
		 	@method getDatabaseMappingDescriptors
		 	@return {Array} Database Mapping Descriptors
		 */
		this.getDatabaseMappingDescriptors = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_DATABASE_MAPPING_DESCRIPTORS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var databaseMappingDescriptors = SIDatasHelper.toModels(datas);
			
			return databaseMappingDescriptors;
		
		}
		

		/**
		 	Get Database Mapping based on POJO class name provided.

			@method getDatabaseMappingDescriptorBasedOnClassName
		 	@param className {String} POJO class name.
		 	@return {DatabaseMappingDescriptor} Database Mapping object in respect to POJO class name.
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
		 	Get Database Mapping Descriptor based on table name provided.

			@method getDatabaseMappingDescriptorBasedOnTableName
		 	@param tableName {String} Name of table.
		 	@return {DatabaseMappingDescriptor} Database Mapping Descriptor object in respect to table name.
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
	
		
		
		/**
			Get all library descriptors
			
			@method getLibraryDescriptors
			@return {Array} All Library Descriptors
		*/
		this.getLibraryDescriptors = function() {
		
			var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SIMINOV_RESOURCES_ADAPTER);
	        adapter.setHandlerName(Constants.SIMINOV_RESOURCES_GET_LIBRARY_DESCRIPTORS_HANDLER);
	        
	        var data = adapter.invoke();
	        
	        var datas = SIJsonHelper.toSI(data);
			var libraryDescriptors = SIDatasHelper.toModels(datas);
			
			return libraryDescriptors;
			
		}


		this.getLibraryDescriptor = function(libraryName) {
			
		}
				
		
		/**
		 	Get Hybrid Descriptor.
		 	
		 	@method getHybridDescriptor 
		 	@return {HybridDescriptor} Hybrid Descriptor.
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
		 	Get All Adapters defined by Application.
		 	
		 	@method getAdapters 
		 	@return {Array} All Adapters.
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
		 	Get Adapter based on Adapter Name.
		 	
		 	@method getAdapter 
		 	@param adapterName {String} Name of Adapter.
		 	@return {Adapter} Adapter
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
		 	Get All Handlers defined by Application.
		 	
		 	@method getHandlers 
		 	@return {Array} All Handlers.
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
		 	Get Handler based on Adapter Name and Handler Name.
		 	
		 	@method getHandler 
		 	@param adapterName {String} Name of Adapter.
		 	@param handlerName {String} Name of Handler.
		 	@return {Handler} Handler.
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
	}

		
}) ();
