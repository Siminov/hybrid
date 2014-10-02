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



package siminov.hybrid.adapter.constants;

/**
 * Exposes constants to which represents Database Descriptor on Web.
 */
public interface HybridDatabaseDescriptor {

	/**
	 * Web Module Database Descriptor Function Name.
	 */
	public String DATABASE_DESCRIPTOR = "DatabaseDescriptor";
	
	
	/**
	 * Web Database Descriptor Database Name.
	 */
	public String NAME = "databaseName";
	
	/**
	 * Web Database Descriptor Description.
	 */
	public String DESCRIPTION = "description";
	
	
	/**
	 * Web Database Descriptor Is Locking Required.
	 */
	public String IS_LOCKING_REQUIRED = "lockingRequired";
	
	/**
	 * Web Database Descriptor External Storage.
	 */
	public String EXTERNAL_STORAGE = "externalStorage";
	
	
	/**
	 * Web Database Descriptor Database Mapping Descriptor Paths.
	 */
	public String DATABASE_MAPPING_DESCRIPTORS = "Array";
	
	/**
	 * Web Database Descriptor Database Mapping Descriptor Path.
	 */
	public String DATABASE_MAPPING_DESCRIPTOR_PATH = "databaseMappingDescriptorPath";
	
	
	/**
	 * Web Database Descriptor Libraries.
	 */
	public String LIBRARIES = "Array";
	
	/**
	 * Web Database Descriptor Library Path.
	 */
	public String LIBRARY_PATH = "libraryPath";
	
}
