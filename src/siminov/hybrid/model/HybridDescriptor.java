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



package siminov.hybrid.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.orm.model.IDescriptor;

/**
 * Exposes methods to GET and SET Hybrid Descriptor information as per define in HybridDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<hybrid-descriptor>
	
		<adapter-descriptors>
			
			<adapter-descriptor>
				<property name="name">name_of_adapter</property>
				<property name="description">description_about_adapter</property>
				<property name="type">type_of_adapter</property>
				<property name="map_to">map_to_class_name</property>
				<property name="cache">should_be_cached_or_not</property>
				
				<handlers>
				
					<handler>
						<property name="name">name_of_handler</property>
						<property name="description">description_about_handler</property>
						<property name="map_to">map_to_function_name</property>
						
						<parameters>
							
							<parameter>
								<property name="name"></property>
								<property name="description"></property>
								<property name="type"></property>
							</parameter>
						
						</parameter>
						
						<return>
								<property name="type">return_data_type</property>
								<property name="description">description_about_return_data</property>
						</return>
						
					</handler>
				
				</handlers>
				
			</adapter-descriptor>
			
		</adapter-descriptors>
		
			
			
	</hybrid-descriptor>
	}
	
		</pre>
	</p>
 *
 */
public class HybridDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String>();

	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnName = new HashMap<String, AdapterDescriptor>();
	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnPath = new HashMap<String, AdapterDescriptor>();

	
	/**
	 * Get all Properties defined in descriptor.
	 * @return All Property Values.
	 */
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	/**
	 * Get Property based on name provided.
	 * @param name Name of Property.
	 * @return Property value.
	 */
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	/**
	 * Check whether Property exist or not.
	 * @param name Name of Property.
	 * @return true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	/**
	 * Add Property in property pool.
	 * @param name Name of Property.
	 * @param value value of Property.
	 */
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	/**
	 * Remove Property from property pool.
	 * @param name Name of Property.
	 */
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

	/**
	 * Get All Adapter Descriptors. Defined in HybridDescriptor.si.xml file.
	 * @return All Adapter Descriptors.
	 */
	public Iterator<AdapterDescriptor> getAdapterDescriptors() {
		return this.adapterDescriptorsBasedOnName.values().iterator();
	}
	
	/**
	 * Get Adapter Descriptor based on name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterDescriptorBasedOnName(final String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.get(adapterDescriptorName);
	}

	/**
	 * Get Adapter Descriptor based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor File.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterBasedOnPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.get(adapterDescriptorPath);
	}
	
	/**
	 * Add Adapter Descriptor.
	 * @param adapterDescriptor Adapter Descriptor.
	 */
	public void addAdapterDescriptor(final AdapterDescriptor adapterDescriptor) {
		this.adapterDescriptorsBasedOnName.put(adapterDescriptor.getName(), adapterDescriptor);
	}

	/**
	 * Add Adapter Descriptor based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @param adapterDescriptor Adapter Descriptor.
	 */
	public void addAdapterDescriptor(final String adapterDescriptorPath, final AdapterDescriptor adapterDescriptor) {
		this.adapterDescriptorsBasedOnPath.put(adapterDescriptorPath, adapterDescriptor);
		this.adapterDescriptorsBasedOnName.put(adapterDescriptor.getName(), adapterDescriptor);
	}
	
	/**
	 * Check whether Adapter Descriptor exist or not based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterDescriptorBasedOnPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.containsKey(adapterDescriptorPath);
	}
	
	/**
	 * Check whether Adapter Descriptor exist or not based on name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterDescriptorBasedOnName(final String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.containsKey(adapterDescriptorName);
	}
	
	/**
	 * Get all Adapter Descriptor Paths.
	 * @return Paths of all adapter descriptors.
	 */
	public Iterator<String> getAdapterDescriptorPaths() {
		return this.adapterDescriptorsBasedOnPath.keySet().iterator();
	}
	
	/**
	 * Add Adapter based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 */
	public void addAdapterDescriptorPath(final String adapterDescriptorPath) {
		this.adapterDescriptorsBasedOnPath.put(adapterDescriptorPath, null);
	}
	
	/**
	 * Check whether adapter exist or not based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterDescriptorPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.containsKey(adapterDescriptorPath);
	}
	
	/**
	 * Remove Adapter Descriptor based on adapter descriptor name.
	 * @param adapterDescriptorName Name of adapter descriptor.
	 */
	public void removeAdapterDescriptorBasedOnName(final String adapterDescriptorName) {
		
		Iterator<String> adapterDescriptorPaths = this.adapterDescriptorsBasedOnPath.keySet().iterator();
		
		String keyMatched = null;
		boolean found = false;
		while(adapterDescriptorPaths.hasNext()) {
			String adapterPath = adapterDescriptorPaths.next();
			
			AdapterDescriptor adapterDescriptor = this.adapterDescriptorsBasedOnPath.get(adapterPath);
			if(adapterDescriptor.getName().equalsIgnoreCase(adapterDescriptorName)) {
				keyMatched = adapterPath;
				found = true;
				break;
			}
		}
		
		if(found) {
			removeAdapterDescriptorBasedOnPath(keyMatched);
		} else {
			this.adapterDescriptorsBasedOnName.remove(adapterDescriptorName);
		}
		
	}
	
	/**
	 * Remove Adapter Descriptor based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 */
	public void removeAdapterDescriptorBasedOnPath(final String adapterDescriptorPath) {
		AdapterDescriptor adapterDescriptor = this.adapterDescriptorsBasedOnPath.get(adapterDescriptorPath);
		
		this.adapterDescriptorsBasedOnName.remove(adapterDescriptor.getName());
		this.adapterDescriptorsBasedOnPath.remove(adapterDescriptorPath);
	}
	
	/**
	 * Remove Adapter Descriptor based on Adapter Descriptor.
	 * @param adapterDescriptor Adapter Descriptor.
	 */
	public void removeAdapter(final AdapterDescriptor adapterDescriptor) {
		removeAdapterDescriptorBasedOnName(adapterDescriptor.getName());
	}
}
