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
    
    module.exports = ApplicationDescriptor;
    win.ApplicationDescriptor = ApplicationDescriptor;
}

/**
 	Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.si.xml file by application.
		
	Example:
		<siminov>
		
			<property name="name">SIMINOV HYBRID SAMPLE</property>	
			<property name="description">Siminov Hybrid Sample Application</property>
			<property name="version">0.9</property>
		
			<property name="load_initially">true</property>
		
			<!-- DATABASE-DESCRIPTORS -->
			<database-descriptors>
				<database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
			</database-descriptors>
		
			<!-- SIMINOV EVENTS -->
		 	<event-handlers>
			    <event-handler>siminov.hybrid.sample.events.SiminovEventHandler</event-handler>
			    <event-handler>siminov.hybrid.sample.events.DatabaseEventHandler</event-handler>
			</event-handlers>
				
		</siminov>


	@module Model	
	@class ApplicationDescriptor
	@constructor

 */
function ApplicationDescriptor() {

    var properties = new Dictionary();

	var databaseDescriptorPaths = new Array();
    var events = new Array();

	/**
	 	Get Application Descriptor Name as per defined in ApplicationDescriptor.si.xml file.
	 	
	 	@method getName
	 	@return {String} Application Descriptor Name.
	 */
	this.getName = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_NAME);
    }

	/**
	 	Set Application Descriptor Name as per defined in ApplicationDescriptor.si.xml file.
	 	
	 	@method setName
		@param name {String} Name of Application Descriptor.
	 */
	this.setName = function(name) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_NAME, name);
    }

	/**
	 	Set Description of Application as per defined in ApplicationDescriptor.si.xml file.
	 
	 	@method getDescription
		@return {String} Description of application.
	 */
    this.getDescription = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_DESCRIPTION);
    }

	/**
	 	Set Description of Application as per defined in ApplicationDescriptor.si.xml file.
	 
	 	@method setDescription
		@param description {String} Description of application.
	 */
    this.setDescription = function(description) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_DESCRIPTION, description);
    }

	/**
	 	Get Version of Application as per defined in ApplicationDescriptor.si.xml file.
		
		@method getVersion
		@return {String} Version of application.
	 */
    this.getVersion = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_VERSION);
    }

	/**
	 	Set Version of Application as per defined in ApplicationDescriptor.si.xml file.
	 
	 	@method setVersion
	 	@param version {String} Version of application.
	 */
    this.setVersion = function(version) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_VERSION, version);
    }
    
    
    this.getDeploy = function() {
    	return properties.get(Constants.APPLICATION_DESCRIPTOR_DEPLOY);
    }
    
    this.setDeploy = function(deploy) {
    	properties.add(Constants.APPLICATION_DESCRIPTOR_DEPLOY, deploy);
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
	 	Add Database Descriptor path as per contained in ApplicationDescriptor.si.xml file.
	 
	 	@method addDatabaseDescriptorPath
	 	@param databaseDescriptorPath {String} DatabaseDescriptor path.
	 */
    this.addDatabaseDescriptorPath = function(databaseDescriptorPath) {
        databaseDescriptorPaths.push(databaseDescriptorPath);
    }

	/**
	 	Get all database descriptor paths as per contained in ApplicationDescriptor.si.xml file.
		
		@method getDatabaseDescriptorPaths
		@return {Array} It which contains all database descriptor paths.
	 */
    this.getDatabaseDescriptorPaths = function() {
        return databaseDescriptorPaths;
    }

	/**
		Get all event handlers as per defined in ApplicationDescriptor.si.xml file.
	 
 		@method getEvents
 		@return {Array} All event handlers defined in ApplicationDescriptor.si.xml file
	 */
    this.getEvents = function() {
        return events;
    }

	/**
	 	Add event as per defined in ApplicationDescriptor.si.xml file.
	 	
	 	@method addEvent
	 	@param event {String} Event Handler class name.
	 */
    this.addEvent = function(event) {
        events.push(event);
    }
}
