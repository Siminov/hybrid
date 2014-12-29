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
	It contain all Models as per required by Siminov Framework.

	@module Model
*/


/**
 	Exposes methods to GET and SET Sync Descriptor information as per define in SyncDescriptor.si.xml file by application.
		
	Example:
		
		<sync-descriptor>
		            
				<!-- Mandatory Field -->
			<property name="name">name_of_sync_handler</property>
					
				<!-- Optional Field -->
			<property name="sync_interval">sync_interval_in_millisecond</property>
		     				
				<!-- Optional Field -->
					<!-- Default: SCREEN -->
			<property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
					
			<!-- Services -->
				<!-- Service -->
			<services>
		     		    
			    <service>name_of_service.name_of_api</service>
		     		    
			</services>
		
		</sync-descriptor>
		

	@module Model	
	@class SyncDescriptor
	@constructor
 */
function SyncDescriptor() {

	var properties = new Dictionary();
	
	var services = new Array();

	/**
	 * Get name of sync descriptor
	 * 
	 * @method getName
	 * @return {String} Name of sync descriptor
	 */
	this.getName = function() {
		return properties.get(Constants.SYNC_DESCRIPTOR_NAME);
	}
	
	/**
	 * Set name of sync descriptor
	 * 
	 * @method setName
	 * @param name {String} Name of sync descriptor
	 */
	this.setName = function(name) {
		properties.put(Constants.SYNC_DESCRIPTOR_NAME, name);
	}
	
	/**
	 * Get sync interval
	 * 
	 * @method getSyncInterval
	 * @return {String} Sync Interval
	 */
	this.getSyncInterval = function() {
		return properties.get(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL);
	}
	
	/**
	 * Set sync interval
	 * 
	 * @method setSyncInterval
	 * @param syncInterval {String} Sync Interval
	 */
	this.setSyncInterval = function(syncInterval) {
		properties.put(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL, syncInterval);	
	}
	

	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
	
	/**
	 * Get all service descriptor names
	 * 
	 * @method getServices 
	 * @return {Array} Name of Services
	 */
	this.getServices = function() {
		return services;
	}
	
	/**
	 * Add service
	 * 
	 * @method addService
	 * @param service {String} Name of Service
	 */
	this.addService = function(service) {
		services.push(service);
	}
}