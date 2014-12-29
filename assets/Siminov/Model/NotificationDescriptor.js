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
 	Exposes methods to GET and SET Notification Descriptor information as per define in NotificationDescriptor.si.xml file by application.
		
	Example:
		
		<siminov>
		    
			<!-- General Application Description Properties -->
			
				<!-- Mandatory Field -->
			<property name="name">application_name</property>	
			
				<!-- Optional Field -->
			<property name="description">application_description</property>
			
				<!-- Mandatory Field (Default is 0.0) -->
			<property name="version">application_version</property>
		
		
			
			<!-- Siminov Framework Performance Properties -->
			
				<!-- Optional Field (Default is true)-->
			<property name="load_initially">true/false</property>
		
		
			
			<!-- Database Descriptors Used By Application (zero-to-many) -->	
				<!-- Optional Field's -->
			<database-descriptors>
				<database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
			</database-descriptors>
				
		
		   	<!-- Services -->
		    <service-descriptors>
		  		
		  			<!-- Service -->
		        <service-descriptor>full_path_of_service</service-descriptor>
		    
		    </service-descriptors>
		
		    
			
		    <!-- Sync Handlers -->
		    	<!-- Sync Handler -->
		    <sync-descriptors>
		        
		        <sync-descriptor>
		            
		           		<!-- Mandatory Field -->
		     		<property name="name">name_of_sync_handler</property>
					
		     			<!-- Optional Field -->
		     		<property name="sync_interval">sync_interval_in_millisecond</property>
		     				
		     			<!-- Optional Field -->
		     				<!-- Default: SCREEN -->
					<property name="type">INTERVAL|SCREEN</property>
					
		     		<!-- Services -->
		     			<!-- Service -->
		     		<services>
		     		    
		     		    <service>name_of_service.name_of_api</service>
		     		    
		     		</services>
		        </sync-descriptor>
		        
		    </sync-descriptors>
		    
		
		    <!-- Push Notification -->
		    <notification-descriptor>
		        
		        	<!-- Optional Field -->
		        <property name="name_of_property">value_of_property</property>
		
		    </notification-descriptor>
		    
			
			<adapter-descriptors>
		        
		        <!-- Adapter Paths -->
			    <adapter-descriptor>adapter_path</adapter-descriptor>
			    	        
		    </adapter-descriptors>
				
			
			<!-- Library Descriptors Used By Application (zero-to-many) -->
				<!-- Optional Field's -->
			<library-descriptors>
			 	<library-descriptor>full_path_of_library_descriptor_file</library-descriptor>   
			</library-descriptors>
			
			
			<!-- Event Handlers Implemented By Application (zero-to-many) -->
			
				<!-- Optional Field's -->
			<event-handlers>
				<event-handler>full_java_class_path_of_event_handler/javascript_class_path_of_event_handler (ISiminovHandler/IDatabaseHandler)</event-handler>
			</event-handlers>
		
		</siminov>

	
	@module Model		
	@class NotificationDescriptor
	@constructor
 */
function NotificationDescriptor() {

	var properties = new Dictionary();
	
	
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
}