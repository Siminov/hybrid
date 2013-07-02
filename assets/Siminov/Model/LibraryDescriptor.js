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
 	Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.si.xml file by application.
		
	Example:

		<library>
		
			<property name="name">SIMINOV LIBRARY TEMPLATE</property>
			<property name="description">Siminov Library Template</property>
		
			<!-- Database Mappings -->
				<database-mappings>
					<database-mapping path="Credential.si.xml" />
				</database-mappings>
		
				 	<!-- OR -->
				 
				<database-mappings>
					<database-mapping path="siminov.orm.library.template.model.Credential" />
				</database-mappings>
			 
		</library>
		
	
	@module Model		
	@class LibraryDescriptor
	@constructor
 */

function LibraryDescriptor() {

    var properties = new Dictionary();

    var databaseMappingDescriptorPaths=[];

	/**
	 	Get library name.
	 	
	 	@method getName
	 	@return {String} Name of Library
	 */
    this.getName = function() {
    	return properties.get(Constants.LIBRARY_DESCRIPTOR_NAME);
	}
	
	/**
	 	Set library name as per defined in LibraryDescriptor.si.xml
	 
	 	@method setName
 		@param name {String} Name of Library
	 */
    this.setName = function(name) {
    	properties.add(Constants.LIBRARY_DESCRIPTOR_NAME, name);
	}
	
	/**
	 	Get descriptor as per defined in LibraryDescriptor.si.xml
	 	
	 	@method getDescription
	 	@return {String} Description of Library
	 */
    this.getDescription = function() {
    	return properties.get(Constants.LIBRARY_DESCRIPTOR_DESCRIPTION);
	}
	
	/**
	 	Set description as per defined in LibraryDescritor.core.xml
	 
	 	@method setDescription
	 	@param descriptor {String} Description of Library
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.LIBRARY_DESCRIPTOR_DESCRIPTION, description);
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
	 	Get all database mapping paths as per defined in DatabaseDescriptor.si.xml file.
	 
	 	@method getDatabaseMappingDescriptorPaths
	 	@return {Array} It contain all database mapping paths.
	 */
    this.getDatabaseMappingDescriptorPaths = function() {
    	return databaseMappingDescriptorPaths;
	}
	
	/**
	 	Add database mapping path as per defined in DatabaseDescriptor.si.xml file.
	 		
		EXAMPLE:
			<database-descriptor>
				<database-mappings>
					<database-mapping path="Liquor-Mappings/Liquor.xml" />
					<database-mapping path="Liquor-Mappings/LiquorBrand.xml" />
				</database-mappings>
			</database-descriptor>
	
	 
	 	@method addDatabaseMappingDescriptorPath
	 	@param databaseMappingPath {String} Database Mapping Path.
	 */
    this.addDatabaseMappingDescriptorPath = function(databaseMappingDescriptorPath) {
    	databaseMappingDescriptorPaths.push(databaseMappingDescriptorPaths);
	}
    
}
