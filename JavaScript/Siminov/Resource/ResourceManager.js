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
	It contain all class related to Siminov Framework resource.
	
	@module Resource
*/

/**
	It handles and provides all resources needed by SIMINOV WEB.
	
	@module Resource
	@class Resources
	@constructor
	
*/
var ResourceManager = (function() {

	var resourceManager;
	
	return {
	
		getInstance : function() {
			if(resourceManager == null) {
				resourceManager = new ResourceManager();
				
				resourceManager.constructor = null;
			}
			
			return resourceManager;
		}
	
	}
	

	function ResourceManager() {
		
		/**
	 		Get Application Descriptor object of application.
	 		
	 		@method getApplicationDescriptor
	 		@return {ApplicationDescriptor} Application Descriptor.
	 	*/
	    this.getApplicationDescriptor = function() {

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.RESOURCE_ADAPTER);
	        adapter.setHandlerName(Constants.RESOURCE_GET_APPLICATION_DESCRIPTOR_HANDLER);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var applicationDescriptor = SIDatasHelper.toModels(datas);
	
	        return applicationDescriptor[0];
	    }
	
	
		
		/**
		 	Get Database Descriptor based on database descriptor name provided as per defined in Database Descriptor file.
				
			Example: DatabaseDescriptor.si.xml
				
				<database-descriptor>
				
					<property name="database_name">SIMINOV-WEB-SAMPLE</property>
					
				</database-descriptor>
		 
		 	@method getDatabaseDescriptor
		 	@param databaseDescriptorName Database Descriptor object based on database descriptor name provided.
		 	@return {DatabaseDescriptor} Database Descriptor
		 */
	    this.getDatabaseDescriptor = function(databaseName) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.RESOURCE_ADAPTER);
	        adapter.setHandlerName(Constants.RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_NAME_HANDLER);
	
	        adapter.addParameter(databaseName);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseDescriptor[0];
	
	    }
	

		/**
		 	Get Database Mapping based on POJO class name provided.

			@method getDatabaseMappingDescriptorBasedOnClassName
		 	@param className {String} POJO class name.
		 	@return {DatabaseMappingDescriptor} Database Mapping object in respect to POJO class name.
		 */	
	    this.getDatabaseMappingDescriptorBasedOnClassName = function(className) {
	
	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.RESOURCE_ADAPTER);
	        adapter.setHandlerName(Constants.RESOURCE_GET_DATABASE_MAPPING_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER);
	
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
	        adapter.setAdapterName(Constants.RESOURCE_ADAPTER);
	        adapter.setHandlerName(Constants.RESOURCE_GET_DATABASE_MAPPING_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER);
	
	        adapter.addParameter(tableName);
	
	        var data = adapter.invoke();
	
	        var datas = SIJsonHelper.toSI(data);
	        var databaseMappingDescriptor = SIDatasHelper.toModels(datas);
	
	        return databaseMappingDescriptor[0];
	
	    }
	}

		
}) ();
