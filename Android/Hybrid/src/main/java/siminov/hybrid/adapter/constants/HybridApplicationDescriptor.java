/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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
 * Exposes constants which represents Application Descriptor on Hybrid.
 */
public interface HybridApplicationDescriptor {

	/**
	 * Hybrid Module Application Descriptor Function Name.
	 */
	public String APPLICATION_DESCRIPTOR = "ApplicationDescriptor";
	
	/**
	 * Hybrid Application Descriptor Name.
	 */
	public String NAME = "name";
	
	/**
	 * Hybrid Application Descriptor Description.
	 */
	public String DESCRIPTION = "description";
	
	/**
	 * Hybrid Application Descriptor Version.
	 */
	public String VERSION = "version";
	
	
	
	/**
	 * Hybrid Application Descriptor Database Descriptor Paths.
	 */
	public String DATABASE_DESCRIPTORS = "Array";
	
	/**
	 * Hybrid Application Descriptor Database Descriptor Path Property.
	 */
	public String DATABASE_DESCRIPTOR_PATH = "databaseDescriptorPath";
	
	
	
	/**
	 * Hybrid Application Descriptor Event Notifiers.
	 */
	public String EVENT_NOTIFIERS = "Array";
	
	/**
	 * Hybrid Application Descriptor Event Notifier.
	 */
	public String EVENT_NOTIFIER = "event";
	
}
