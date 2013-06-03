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
 * Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	<core>
	
		<property name="name">SIMINOV TEMPLATE</property>	
		<property name="description">Siminov Template Application</property>
		<property name="version">0.9</property>
	
		<property name="load_initially">true</property>
	
		<!-- DATABASE-DESCRIPTORS -->
		<database-descriptors>
			<database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
		</database-descriptors>
	
		
		<!-- SIMINOV EVENTS -->
		<event-handlers>
		    <event-handler>siminov.orm.template.events.SiminovEventHandler</event-handler>
		    <event-handler>siminov.orm.template.events.DatabaseEventHandler</event-handler>
		</event-handlers>
			
	</core>
	}
	
		</pre>
	</p>

 *
 */

function ApplicationDescriptor() {

    var properties = new Dictionary();

	var databaseDescriptorPaths = [];
    var events = [];

	/**
	 * Get Application Descriptor Name as per defined in ApplicationDescriptor.si.xml file.
	 * @return Application Descriptor Name.
	 */
	this.getName = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_NAME);
    }

	/**
	 * Set Application Descriptor Name as per defined in ApplicationDescriptor.si.xml file.
	 * @param name Name of Application Descriptor.
	 */
	this.setName = function(name) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_NAME, name);
    }

	/**
	 * Set Description of Application as per defined in ApplicationDescriptor.si.xml file.
	 * @return Description of application.
	 */
    this.getDescription = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_DESCRIPTION);
    }

	/**
	 * Set Description of Application as per defined in ApplicationDescriptor.si.xml file.
	 * @param description Description of application.
	 */
    this.setDescription = function(description) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_DESCRIPTION, description);
    }

	/**
	 * Get Version of Application as per defined in ApplicationDescriptor.si.xml file.
	 * @return Version of application.
	 */
    this.getVersion = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_VERSION);
    }

	/**
	 * Set Version of Application as per defined in ApplicationDescriptor.si.xml file.
	 * @param version Version of application.
	 */
    this.setVersion = function(version) {
        properties.add(Constants.APPLICATION_DESCRIPTOR_VERSION, version);
    }

	/**
	 * Set load initially to true or false.
	 * @param initialLoad (true/false) defined by ApplicationDescriptor.si.xml file.
	 */
    this.setLoadInitially = function(loadInitially){
        properties.add(Constants.APPLICATION_DESCRIPTOR_LOAD_INITIALLY, loadInitially);
    }

	/**
	 * It defines the behaviour of SIMINOV. (Should core load all database mapping at initialization or on demand).
	 * @return TRUE: If load initially is set to true, FALSE: If load initially is set to false.
	 */
    this.getLoadInitially = function() {
        return properties.get(Constants.APPLICATION_DESCRIPTOR_LOAD_INITIALLY);
    }

	
	this.getProperties = function() {
		return properties.values();
	}

	this.getProperty = function(name) {
		return properties.get(name);
	}

	this.containProperty = function(name) {
		return properties.exists(name);
	}

	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}


	/**
	 * Add Database Descriptor path as per contained in ApplicationDescriptor.si.xml file.
	 * @param databaseDescriptorPath DatabaseDescriptor path.
	 */
    this.addDatabaseDescriptorPath = function(databaseDescriptorPath) {
        databaseDescriptorPaths.push(databaseDescriptorPath);
    }

	/**
	 * Get all database descriptor paths as per contained in ApplicationDescriptor.si.xml file.
	 * @return Array which contains all database descriptor paths.
	 */
    this.getDatabaseDescriptorPaths = function() {
        return databaseDescriptorPaths;
    }

	/**
	 * Get all event handlers as per defined in ApplicationDescriptor.si.xml file.
	 * @return Array all event handlers defined in ApplicationDescriptor.si.xml file
	 */
    this.getEvents = function() {
        return events;
    }

	/**
	 * Add event as per defined in ApplicationDescriptor.si.xml file.
	 * @param event Event Handler class name.
	 */
    this.addEvent = function(event) {
        events.push(event);
    }

}