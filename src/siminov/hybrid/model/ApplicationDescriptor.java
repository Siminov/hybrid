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

public class ApplicationDescriptor extends siminov.connect.model.ApplicationDescriptor {

	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnName = new HashMap<String, AdapterDescriptor>();
	private Map<String, AdapterDescriptor> adapterDescriptorsBasedOnPath = new HashMap<String, AdapterDescriptor>();

	public Iterator<AdapterDescriptor> getAdapterDescriptors() {
		return this.adapterDescriptorsBasedOnName.values().iterator();
	}
	
	public AdapterDescriptor getAdapterDescriptorBasedOnName(final String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.get(adapterDescriptorName);
	}

	public AdapterDescriptor getAdapterBasedOnPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.get(adapterDescriptorPath);
	}
	
	public void addAdapterDescriptor(final AdapterDescriptor adapterDescriptor) {
		this.adapterDescriptorsBasedOnName.put(adapterDescriptor.getName(), adapterDescriptor);
	}

	public void addAdapterDescriptor(final String adapterDescriptorPath, final AdapterDescriptor adapterDescriptor) {
		this.adapterDescriptorsBasedOnPath.put(adapterDescriptorPath, adapterDescriptor);
		this.adapterDescriptorsBasedOnName.put(adapterDescriptor.getName(), adapterDescriptor);
	}
	
	public boolean containAdapterDescriptorBasedOnPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.containsKey(adapterDescriptorPath);
	}
	
	public boolean containAdapterDescriptorBasedOnName(final String adapterDescriptorName) {
		return this.adapterDescriptorsBasedOnName.containsKey(adapterDescriptorName);
	}
	
	public Iterator<String> getAdapterDescriptorPaths() {
		return this.adapterDescriptorsBasedOnPath.keySet().iterator();
	}
	
	public void addAdapterDescriptorPath(final String adapterDescriptorPath) {
		this.adapterDescriptorsBasedOnPath.put(adapterDescriptorPath, null);
	}
	
	public boolean containAdapterDescriptorPath(final String adapterDescriptorPath) {
		return this.adapterDescriptorsBasedOnPath.containsKey(adapterDescriptorPath);
	}
	
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
	
	public void removeAdapterDescriptorBasedOnPath(final String adapterDescriptorPath) {
		AdapterDescriptor adapterDescriptor = this.adapterDescriptorsBasedOnPath.get(adapterDescriptorPath);
		
		this.adapterDescriptorsBasedOnName.remove(adapterDescriptor.getName());
		this.adapterDescriptorsBasedOnPath.remove(adapterDescriptorPath);
	}
	
	public void removeAdapter(final AdapterDescriptor adapterDescriptor) {
		removeAdapterDescriptorBasedOnName(adapterDescriptor.getName());
	}
}
