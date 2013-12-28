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


package siminov.hybrid.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<library>
	
		<property name="name">SIMINOV LIBRARY TEMPLATE</property>
		<property name="description">Siminov Library Template</property>
	
		<!-- Adapters -->
			<adapters>
				<adapter path="full_path_of_adapter" />
			</adapters>
		 
	</library>
	}
	
		</pre>
	</p>
 *
 */
public class LibraryDescriptor extends siminov.orm.model.LibraryDescriptor {

	private Collection<String> adapterDescriptorPaths = new ConcurrentLinkedQueue<String> ();

	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnPath = new ConcurrentHashMap<String, AdapterDescriptor>();
	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnName = new ConcurrentHashMap<String, AdapterDescriptor>();
	
	/**
	 * Get All Adapter Descriptor Paths.
	 * @return All Adapter Descriptor Paths.
	 */
	public Iterator<String> getAdapterDescriptorPaths() {
		return this.adapterDescriptorPaths.iterator();
	}
	
	/**
	 * Get All Adapter Descriptors.
	 * @return All Adapter Descriptors.
	 */
	public Iterator<AdapterDescriptor> getAdapterDescriptors() {
		return this.adapterDescriptorsBasedOnName.values().iterator();
	}
	
	/**
	 * Get Adapter Descriptor Based on adapter descriptor name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterDescriptorBasedOnName(String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.get(adapterDescriptorName);
	}
	
	/**
	 * Get Adapter Descriptor Based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterDescriptorBasedOnPath(String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.get(adapterDescriptorPath);
	}
	
	/**
	 * Add Adapter Descriptor Path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 */
	public void addAdapterDescriptorPath(String adapterDescriptorPath) {
		this.adapterDescriptorPaths.add(adapterDescriptorPath);
	}

	/**
	 * Add Adapter Descriptor based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @param adapterDescriptor Adapter Descriptor.
	 */
	public void addAdapterDescriptor(String adapterDescriptorPath, AdapterDescriptor adapterDescriptor) {
		this.adapterDescriptorsBasedOnPath.put(adapterDescriptorPath, adapterDescriptor);
		this.adapterDescriptorsBasedOnName.put(adapterDescriptor.getName(), adapterDescriptor);
	}
	
	/**
	 * Check whether Adapter Descriptor exist based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterDescriptorBasedOnPath(String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.containsKey(adapterDescriptorPath);
	}
	
	/**
	 * Check whether Adapter Descriptor exist based on adapter name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterDescriptorBasedOnName(String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.containsKey(adapterDescriptorName);
	}
	
	/**
	 * Remove Adapter Descriptor based on adapter descriptor name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 */
	public void removeAdapterDescriptorBasedOnName(String adapterDescriptorName) {
		Iterator<String> libraryPaths = this.adapterDescriptorsBasedOnPath.keySet().iterator();
		
		boolean found = false;
		String keyMatched = null;
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			AdapterDescriptor adapterDescriptor = this.adapterDescriptorsBasedOnPath.get(libraryPath);
			if(adapterDescriptor.getName().equalsIgnoreCase(adapterDescriptorName)) {
				keyMatched = libraryPath;
				found = true;
				break;
			}
		}
		
		if(found) {
			removeAdapterBasedOnPath(keyMatched);
		}
	}
	
	/**
	 * Remove Adapter Descriptor based on adapter descriptor path.
	 * @param adapterPath Path of Adapter Descriptor.
	 */
	public void removeAdapterBasedOnPath(String adapterPath) {
		AdapterDescriptor adapaterDescriptor = this.adapterDescriptorsBasedOnPath.get(adapterPath);

		this.adapterDescriptorsBasedOnName.remove(adapaterDescriptor.getName());
		this.adapterDescriptorsBasedOnPath.remove(adapterPath);
	}
	
	/**
	 * Remove Adapter Descriptor.
	 * @param adapterDescriptor Adapter Descriptor.
	 */
	public void removeAdapater(AdapterDescriptor adapterDescriptor) {
		removeAdapterDescriptorBasedOnName(adapterDescriptor.getName());
	}
	
}
