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

var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}


if(dom == undefined) {
    var Constants = require('../Constants');
    var Dictionary = require('../Collection/Dictionary');
    
    module.exports = AdapterDescriptor;
    win.AdapterDescriptor = AdapterDescriptor;    
}

/**
 	Exposes methods to GET and SET Adapter Descriptor information as per define in AdapterDescriptor.si.xml file by application.
		
	Example:
		<adapter-descriptor>
    
		    <!-- General Adapter Properties -->
		    	<!-- Mandatory Field -->
		    <property name="name">adapter_name</property>
		    	
		    	<!-- Optional Field -->
		    <property name="description">adapter_description</property>
		    
		    	<!-- Mandatory Field -->
		    <property name="type">HYBRID-TO-NATIVE|NATIVE-TO-HYBRID</property>
		    
		    	<!-- Optional Field -->
		    <property name="map_to">name_of_adapter_class</property>
		
		    	<!-- Optional Field (DEFAULT: FALSE)-->
		    <property name="cache">true/false</property>
		    
		    <!-- Handlers -->
		    	<!-- Handler -->
		    <handlers>
		        
		     <handler>
		         
		         <!-- General Handler Properties -->
		         	<!-- Mandatory Field -->
		         <property name="name">handler_name</property>
		         
		         	<!-- Optional Field -->
		         <property name="description">handler_description</property>	            
		          	            
		         	<!-- Mandatory Field -->
		         <property name="map_to">name_of_handler_method</property>	            
		         
		         		            	            	           
		         <!-- Parameters -->
		         <parameters>
		             
		             <!-- Parameter -->
		             <parameter>
		                 
		                 	<!-- Mandatory Field -->
		                 <property name="name">name_of_parameter</property>
		                 
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
	
		</adapter-descriptor>

	@module Model	
	@class ApplicationDescriptor
	@constructor

 */
 function AdapterDescriptor() {

    var properties = new Dictionary();

    var handlers = new Array();
    
	
	/**
	 * Get Name of Adapter.
	 * @return Name of Adapter.
	 */
    this.getName = function() {
    	return properties.get(Constants.ADAPER_DESCRIPTOR_NAME);
	}
	
	/**
	 * Set Name of Adapter.
	 * @param name Name of Adapter.
	 */
    this.setName = function(name) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_NAME, name);
	}

	/**
	 * Get Description of Adapter.
	 * @return Description of Adapter.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_DESCRIPTION);
	}
	
	/**
	 * Set Description of Adapter.
	 * @param description Description of Adapter.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_DESCRIPTION, description);
	}

	/**
	 * Get Type of Adapter.
	 * @return Type of Adapter.
	 */
    this.getType = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_TYPE);
	}
	
	/**
	 * Set Type of Adapter.
	 * @param type Type of Adapter.
	 */
    this.setType = function(type) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_TYPE, type);
	}

	/**
	 * Get Map To Name.
	 * @return Map To Name.
	 */
    this.getMapTo = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_MAP_TO);
	}
	
	/**
	 * Set Map To Name.
	 * @param mapTo Map To Name.
	 */
    this.setMapTo = function(mapTo) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_MAP_TO, mapTo);
	}

	/**
	 * Check whether cache is enabled or disabled.
	 * @return true/false; TRUE if cache enabled, FALSE if cache disabled.
	 */
    this.isCache = function() {
        return properties.get(Constants.ADAPTER_DESCRIPTOR_CACHE);
	}

	/**
	 * Set Cache value.
	 * @param cache Cache Enabled or Disabled.
	 */
    this.setCache = function(cache) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_CACHE, cache);
	}
	

	/**
	 	Get All Handler defined in descriptors.
	 
	 	@method getHandlers
		@return {Array} All Handlers.
	 */
    this.getHandlers = function() {
    	return handlers;
	}
	
	/**
	 	Add Handler.
	 	
	 	@method getHandlers
	 	@param handler {Handler} Handler.
	 */
    this.addHandler = function(handler) {
    	handlers.push(handler);
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
	 
	 	@method removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
	
    
} 


/**
 	Exposes methods to GET and SET Adapter Descriptor Handler information as per define in AdapterDescriptor.si.xml file or in standalone adapter xml file in application.

	@class AdapterDescriptor.Handler
 */
AdapterDescriptor.Handler = function() {

    var properties = new Dictionary();

    var parameters = new Array();


	/**
	 	Get Name of Handler.
	 	
	 	@method getName
	 	@return {String} Name of Handler. 
	 */
    this.getName = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_NAME);
	}
	
	/**
	 	Set Name of Handler.
	 	
	 	@method setName
	 	@param name {String} Name of handler.
	 */
    this.setName = function(name) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_HANDLER_NAME, name);
	}


	/**
	 	Get Description about Handler.
	 
	 	@method getDescription
		@return {String} Description about Handler.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_DESCRIPTION);
	}
	
	/**
	 	Set Description about Handler.
	 
	 	@method setDescription
		@param description {String} Description about Handler.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_HANDLER_DESCRIPTION, description);
	}

	/**
	 	Get Map To Name.
	 
	 	@method getMapTo
		@return {String} Map To Name.
	 */
    this.getMapTo = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_MAP_TO);
	}
	
	/**
	 	Set Map To Name.
	 
	 	@method setMapTo
		@param mapTo {String} Map To Name.
	 */
    this.setMapTo = function(mapTo) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_HANDLER_MAP_TO, mapTo);
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
	 
	 	@method removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
	

} 


/**
 	Exposes methods to GET and SET Parameter information as per define in AdapterDescriptor.si.xml file or in standalone adapter xml file in application.
 	
 	@class AdapterDescriptor.Handler.Parameter
 */
AdapterDescriptor.Handler.Parameter = function() {

    var properties = new Dictionary();

	/**
	 	Get Name of Parameter.
	 	
	 	@method getDescription
	 	@return {String} Name of Parameter.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_PARAMETER_DESCRIPTION);
	}
	
	/**
	 	Set Name of Parameter.
	 	
	 	@method setDescription
	 	@param name {String} Name of Parameter.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_PARAMETER_DESCRIPTION, description);
	}

	/**
	 	Get Type of Parameter.
	 	
	 	@method getType
	 	@return {String} Type of Parameter.
	 */
    this.getType = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_PARAMETER_TYPE);
	}
	
	/**
	 	Get Type of Parameter.
	 	
	 	@method setType
	 	@param type {String} Type of Parameter.
	 */
    this.setType = function(type) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_PARAMETER_TYPE, type);
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
	 
	 	@method removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}

} 


/**
 	Exposes methods to GET and SET Return information as per define in AdapterDescriptor.si.xml file or in standalone adapter xml file in application.
 */
AdapterDescriptor.Handler.Return = function() {

    var properties = new Dictionary();


	/**
	 	Get Description about Return.
	 	
	 	@method getDescription
	 	@return {String} Description about Return.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_RETURN_DESCRIPTION);
	}
	
	/**
	 	Set Description about Return.
	 	
	 	@method setDescription
	 	@param description {String} Description about Return.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_RETURN_DESCRIPTION, description);
	}

	/**
	 	Get Type of Return.
	 	
	 	@method getType
	 	@return {String} Type of Return.
	 */
    this.getType = function() {
    	return properties.get(Constants.ADAPTER_DESCRIPTOR_RETURN_TYPE);
	}
	
	/**
	 	Set Type of Return.
	 	
	 	@method setType
	 	@param type {String} Type of Return.
	 */
    this.setType = function(type) {
    	properties.add(Constants.ADAPTER_DESCRIPTOR_RETURN_TYPE, type);
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
	 
	 	@method removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
} 
