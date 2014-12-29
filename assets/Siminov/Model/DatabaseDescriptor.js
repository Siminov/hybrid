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
 	Exposes methods to GET and SET Database Descriptor information as per define in DatabaseDescriptor.si.xml file by application.
	
		
	Example:
				
		<database-descriptor>
		
		    <!-- General Database Descriptor Properties -->
		    
			    <!-- Mandatory Field -->
			<property name="database_name">name_of_database_file</property>
		
				<!-- Optional Field (Default is sqlite)-->
			<property name="type">type_of_database</property>
		
				<!-- Mandatory Field -->
			<property name="version">database_version</property>
					
				<!-- Optional Field -->
			<property name="description">database_description</property>
		
				<!-- Optional Field (Default is false) -->
			<property name="transaction_safe">true/false</property>
			
				<!-- Optional Field (Default is false) -->
			<property name="external_storage">true/false</property>
				
		
		
			<!-- Database Mapping Descriptor Paths Needed Under This Database Descriptor -->
			
				<!-- Optional Field -->
			<database-mapping-descriptors>
				<database-mapping-descriptor>full_path_of_database_mapping_descriptor_file</database-mapping-descriptor>
			</database-mapping-descriptors>
			
		</database-descriptor>


	@module Model
	@class DatabaseDescriptor
	@constructor

*/
function DatabaseDescriptor() {

    var properties = new Dictionary();

    var databaseMappingPaths = new  Array();
    var libraries = new Array();


	/**
	 	Get database descriptor name as defined in DatabaseDescriptor.si.xml file.
	 
	 	@method getDatabaseName
	 	@return {String} Database Descriptor Name.
	 */
    this.getDatabaseName = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_DATABASE_NAME);
   	}
   	
	/**
	 	Set database descriptor name as per defined in DatabaseDescriptor.si.xml file.
	 
	 	@method setDatabaseName
	 	@param databaseName {String} Database Descriptor Name.
	 */
    this.setDatabaseName = function(databaseName) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_DATABASE_NAME, databaseName);
    }
    
	/**
	 	Get description as per defined in DatabaseDescriptor.si.xml file.
	 
	 	@method getDescription
	 	@return {String} Description defined in DatabaseDescriptor.si.xml file.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_DESCRIPTION);
    }
    
	/**
	 	Set description as per defined in DatabaseDescritor.xml file.
	 
	 	@method setDescription
	 	@param description {String} Description defined in DatabaseDescriptor.si.xml file.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_DESCRIPTION, description);
    }

	this.getType = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_TYPE);
	}

	this.setType = function() {
    	properties.add(Constants.DATABASE_DESCRIPTOR_TYPE, description);
	}

    
	/**
	 	Set database locking as per defined in DatabaseDescriptor.si.xml file.
	 
	 	@method setLockingRequired
	 	@param isLockingRequired {Boolean} (true/false) database locking as per defined in DatabaseDescriptor.si.xml file.
	 */
    this.setTransactionSafe = function(transactionSafe) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_TRANSACTION_SAFE, transactionSafe);
	}
	
	/**
	 	Check whether database transactions to make multi-threading safe or not.
	 
	 	@method isLockingRequired
	 	@return {Boolean} (true/false) TRUE: If locking is required as per defined in DatabaseDescriptor.si.xml file, FALSE: If locking is not required as per defined in DatabaseDescriptor.si.xml file.
	 */
    this.isTransactionSafe = function() {
    	properties.get(Constants.DATABASE_DESCRIPTOR_TRANSACTION_SAFE);
    }
    
	/**
	 	Set the external storage value as per defined in DatabaseDescriptor.si.xml file.
	 
	 	@method setExternalStorage
	 	@param isExternalStorageEnable {Boolean} (true/false) External Storage Enable Or Not.
	 */
    this.setExternalStorage = function(externalStorage) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_EXTERNAL_STORAGE, externalStorage);
	}
	
	/**
	 	Check whether database needs to be stored on SDCard or not.
	 
	 	@method isExternalStorage
	 	@return {Boolean} (true/false) TRUE: If external_storage defined as true in DatabaseDescriptor.si.xml file, FALSE: If external_storage defined as false in DatabaseDescritor.xml file.
	 */
    this.isExternalStorage = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_EXTERNAL_STORAGE)
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
    	return databaseMappingPaths;
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
	 	@param databaseMappingPath Database Mapping Path.
	 */
    this.addDatabaseMappingDescriptorPath = function(databaseMappingPath) {
    	databaseMappingPaths.push(databaseMappingPath);
	}
} 
