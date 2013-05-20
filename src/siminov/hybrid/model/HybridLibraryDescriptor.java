/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution|support@siminov.com]
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

import siminov.hybrid.model.HybridDescriptor.Adapter;


public class HybridLibraryDescriptor extends siminov.orm.model.LibraryDescriptor {

	private Collection<String> adapterPaths = new ConcurrentLinkedQueue<String> ();

	private Map<String, Adapter> adaptersBasedOnPath = new ConcurrentHashMap<String, Adapter>();
	private Map<String, Adapter> adaptersBasedOnName = new ConcurrentHashMap<String, Adapter>();
	
	public Iterator<String> getAdapterPaths() {
		return this.adapterPaths.iterator();
	}
	
	public Iterator<Adapter> getAdapters() {
		return this.adaptersBasedOnName.values().iterator();
	}
	
	public Adapter getAdapterBasedOnName(String adapterName) {
		return this.adaptersBasedOnName.get(adapterName);
	}
	
	public Adapter getAdapterBasedOnPath(String adapterPath) {
		return this.adaptersBasedOnPath.get(adapterPath);
	}
	
	public void addAdapterPath(String adapterPath) {
		this.adapterPaths.add(adapterPath);
	}

	public void addAdapter(String adapterPath, Adapter adapter) {
		this.adaptersBasedOnPath.put(adapterPath, adapter);
		this.adaptersBasedOnName.put(adapter.getName(), adapter);
	}
	
	public boolean containAdapterBasedOnPath(String adapterPath) {
		return this.adaptersBasedOnPath.containsKey(adapterPath);
	}
	
	public boolean containAdapterBasedOnName(String adapterName) {
		return this.adaptersBasedOnName.containsKey(adapterName);
	}
	
	public void removeAdapterBasedOnName(String adapterName) {
		Iterator<String> libraryPaths = this.adaptersBasedOnPath.keySet().iterator();
		
		boolean found = false;
		String keyMatched = null;
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			Adapter adapter = this.adaptersBasedOnPath.get(libraryPath);
			if(adapter.getName().equalsIgnoreCase(adapterName)) {
				keyMatched = libraryPath;
				found = true;
				break;
			}
		}
		
		if(found) {
			removeAdapterBasedOnPath(keyMatched);
		}
	}
	
	public void removeAdapterBasedOnPath(String adapterPath) {
		Adapter adapater = this.adaptersBasedOnPath.get(adapterPath);

		this.adaptersBasedOnName.remove(adapater.getName());
		this.adaptersBasedOnPath.remove(adapterPath);
	}
	
	public void removeAdapater(Adapter adapter) {
		removeAdapterBasedOnName(adapter.getName());
	}
	
}
