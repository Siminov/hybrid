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


package siminov.hybrid.adapter.constants;

/**
 * Exposes constants to which represents Application Descriptor on Web.
 */
public interface HybridApplicationDescriptor {

	/**
	 * Web Module Application Descriptor Function Name.
	 */
	public String APPLICATION_DESCRIPTOR = "ApplicationDescriptor";
	
	/**
	 * Web Application Descriptor Name.
	 */
	public String NAME = "name";
	
	/**
	 * Web Application Descriptor Description.
	 */
	public String DESCRIPTION = "description";
	
	/**
	 * Web Application Descriptor Version.
	 */
	public String VERSION = "version";
	
	/**
	 * Web Application Descriptor Load Initially.
	 */
	public String LOAD_INITIALLY = "loadInitially";
	
	
	
	/**
	 * Web Application Descriptor Database Descriptor Paths.
	 */
	public String DATABASE_DESCRIPTORS = "Array";
	
	/**
	 * Web Application Descriptor Database Descriptor Path Property.
	 */
	public String DATABASE_DESCRIPTOR_PATH = "databaseDescriptorPath";
	
	
	
	/**
	 * Web Application Descriptor Event Notifiers.
	 */
	public String EVENT_NOTIFIERS = "Array";
	
	/**
	 * Web Application Descriptor Event Notifier.
	 */
	public String EVENT_NOTIFIER = "event";
	
}
