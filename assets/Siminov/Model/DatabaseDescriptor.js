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
 * Exposes methods to GET and SET Database Descriptor information as per define in DatabaseDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code

	<database-descriptor>
	
		<property name="database_name">SIMINOV-TEMPLATE</property>
		<property name="description">Siminov Template Database Config</property>
		<property name="is_locking_required">true</property>
		<property name="external_storage">false</property>

		<!-- Database Mappings -->
			<database-mappings>
				<database-mapping path="Liquor-Mappings/Liquor.si.xml" />
				<database-mapping path="Liquor-Mappings/LiquorBrand.si.xml" />
			</database-mappings>
	
			 	<!-- OR -->

			<database-mappings>
				<database-mapping path="siminov.orm.template.model.Liquor" />
				<database-mapping path="siminov.orm.template.model.LiquorBrand" />
			</database-mappings>
		

		<!-- Libraries -->
		<libraries>
			<library>siminov.orm.library.template.resources</library>
		</libraries>
				
	</database-descriptor>

	}
	
		</pre>
	</p>
	
*/
function DatabaseDescriptor() {

    var properties = new Dictionary();

    var databaseMappingPaths = [];
    var libraries = [];


	/**
	 * Get database descriptor name as defined in DatabaseDescriptor.si.xml file.
	 * @return Database Descriptor Name.
	 */
    this.getDatabaseName = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_DATABASE_NAME);
   	}
   	
	/**
	 * Set database descriptor name as per defined in DatabaseDescriptor.si.xml file.
	 * @param databaseName Database Descriptor Name.
	 */
    this.setDatabaseName = function(databaseName) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_DATABASE_NAME, databaseName);
    }
    
	/**
	 * Get description as per defined in DatabaseDescriptor.si.xml file.
	 * @return Description defined in DatabaseDescriptor.si.xml file.
	 */
    this.getDescription = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_DESCRIPTION);
    }
    
	/**
	 * Set description as per defined in DatabaseDescritor.xml file.
	 * @param description Description defined in DatabaseDescriptor.si.xml file.
	 */
    this.setDescription = function(description) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_DESCRIPTION, description);
    }
    
	/**
	 * Set database locking as per defined in DatabaseDescriptor.si.xml file.
	 * @param isLockingRequired (true/false) database locking as per defined in DatabaseDescriptor.si.xml file.
	 */
    this.setLockingRequired = function(lockingRequired) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_IS_LOCKING_REQUIRED, lockingRequired);
	}
	
	/**
	 * Check whether database transactions to make multi-threading safe or not.
	 * @return TRUE: If locking is required as per defined in DatabaseDescriptor.si.xml file, FALSE: If locking is not required as per defined in DatabaseDescriptor.si.xml file.
	 */
    this.isLockingRequired = function() {
    	properties.get(Constants.DATABASE_DESCRIPTOR_IS_LOCKING_REQUIRED);
    }
    
	/**
	 * Set the external storage value as per defined in DatabaseDescriptor.si.xml file.
	 * @param isExternalStorageEnable (true/false) External Storage Enable Or Not.
	 */
    this.setExternalStorage = function(externalStorage) {
    	properties.add(Constants.DATABASE_DESCRIPTOR_EXTERNAL_STORAGE, externalStorage);
	}
	
	/**
	 * Check whether database needs to be stored on SDCard or not.
	 * @return TRUE: If external_storage defined as true in DatabaseDescriptor.si.xml file, FALSE: If external_storage defined as false in DatabaseDescritor.xml file.
	 */
    this.isExternalStorage = function() {
    	return properties.get(Constants.DATABASE_DESCRIPTOR_EXTERNAL_STORAGE)
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
	 * Get all database mapping paths as per defined in DatabaseDescriptor.si.xml file.
	 * @return Array which contain all database mapping paths.
	 */
    this.getDatabaseMappingDescriptorPaths = function() {
    	return databaseMappingPaths;
	}
	
	/**
	 * Add database mapping path as per defined in DatabaseDescriptor.si.xml file.
	 	<p>
	 		<pre>
	 		
EXAMPLE:
	<database-descriptor>
		<database-mappings>
			<database-mapping path="Liquor-Mappings/Liquor.xml" />
			<database-mapping path="Liquor-Mappings/LiquorBrand.xml" />
		</database-mappings>
	</database-descriptor>
	
		</pre>
	</p>
	 
	 * @param databaseMappingPath Database Mapping Path.
	 */
    this.addDatabaseMappingDescriptorPath = function(databaseMappingPath) {
    	databaseMappingPaths.push(databaseMappingPath);
	}
	
	/**
	 * Get all library paths as per defined in DatabaseDescriptor.si.xml file.
	 * @return Array which contains all library paths.
	 */
    this.getLibraryPaths = function() {
    	return libraries;
	}
	
	/**
	 * Add library path as per defined in DatabaseDescriptor.si.xml file.
	 * @param libraryPath Library path defined in DatabaseDescriptor.si.xml file.
	 */
    this.addLibraryPath = function(libraryPath) {
    	libraries.push(libraryPath);
    }
} 