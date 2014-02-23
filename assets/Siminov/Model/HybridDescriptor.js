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
	It contain all Models as per required by Siminov Framework.

	@module Model
*/


/**
	Exposes methods to GET and SET Hybrid Descriptor information as per define in HybridDescriptor.si.xml file by application.
	
	<hybrid-descriptor>
	
	    <!-- Adapter -->
	    <adapter>
	        
	        <!-- General Adapter Properties -->
	        	<!-- Mandatory Field -->
	        <property name="name">adapter_name</property>
	        	
	        	<!-- Optional Field -->
	        <property name="description">adapter_description</property>
	        
	        	<!-- Mandatory Field -->
	        <property name="type">WEB-TO-NATIVE|NATIVE-TO-WEB</property>
	        
	        	<!-- Optional Field -->
	        <property name="map_to">name_of_adapter_class</property>
	
	        	<!-- Optional Field (DEFAULT: FALSE)-->
	        <property name="cache">true/false</property>
	
	        	<!-- Optional Error Handler -->
	        <property name="error_handler">error_handler_id</property>
	        
	        <!-- Handlers -->
	        	<!-- Handler -->
	        <handlers>
	            
		        <handler>
		            
		            <!-- General Handler Properties -->
		            	<!-- Mandatory Field -->
		            <property name="name">handler_name</property>
		            
		            	<!-- Optional Field -->
		            <property name="type">SYNC|ASYNC</property>
		            
		            	<!-- Optional Field -->
		            <property name="callback_id">callback_adapter_name</property>
		
		            	<!-- Mandatory Field -->
		            <property name="map_to">name_of_handler_method</property>	            
	
		            	<!-- Optional Field -->
		            <property name="description">handler_description</property>	            
	
			       		<!-- Optional Error Handler -->
			       	<property name="error_handler">error_handler_id</property>
			            	            
		            	            	           
		            <!-- Parameters -->
		            <parameters>
		                <!-- Parameter -->
		                <parameter>
		                    
		                    	<!-- Mandatory Field -->
		                    <property name="type">parameter_type</property>
		                    
		                    	<!-- Optional Field -->
		                    <property name="description">description_of_parameter</property>
		                    
		                </parameter>
		                
		            </parameters>
		            
		            <return>
		                
		                	<!-- Mandatory Field -->
		                <property name="type">return_type</property>
		                
		                	<!-- Optional Field -->
		                <property name="description">return_data_description</property>
		                
		            </return>
		            
		        </handler>
		            
	        </handlers>
	
	    </adapter>
	    
	
	    <!-- Adapter Paths -->
	    <adapter path="adapter_path" />
	    
	
	    <!-- Library Needed Under This HybridDescriptor -->
	    <libraries>
	        
	        <library>full_path_of_library_descriptor_file</library>
	        
	    </libraries>
	    
	</hybrid-descriptor>
	
	@module Model
	@class HybridDescriptor
	@constructor
	
*/

function HybridDescriptor() {

    var properties = new Dictionary();

    var adapters = new Array();
    var adapterPaths = new Array();

    var libraries = new Array();

	
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
	 
	 	@method removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
	
	/**
	 	Get All Adapters. Defined in HybridDescriptor.si.xml file.
	 	
	 	@method getAdapters
	 	@return {Array} All Adapters.
	 */
	this.getAdapters = function() {
    	return adapters;
	}
	
	/**
	 	Get Adapter based on name.
	 	
	 	@method addAdapter
	 	@param adapterName {String} Name of Adapter.
	 	@return {Adapter} Adapter.
	 */
	this.addAdapter = function(adapter) {
    	adapters.push(adapter);
	}
	
	/**
	 	Get Adapter based on adapter path.
	 
	 	@method getAdapterPaths
	 	@return {Adapter} Adapter.
	 */
	this.getAdapterPaths = function() {
    	return adapterPaths;
	}
	

	/**
		Path of Adapter.
		
		@method addAdapterPath
		@param Path of Adapter
	*/	
    this.addAdapterPath = function(adapterPath) {
    	adapterPaths.push(adapterPath);
	}
	
	/**
		Get all Libraries.
		
		@method getLibraries
		@return All Libraries
	*/
    this.getLibraries = function() {
    	return libraries;
	}
	
	/**
		Add Library.
		
		@method addLibrary
		@param Library
	*/
    this.addLibrary = function(library) {
    	libraries.push(library);
	}

}
