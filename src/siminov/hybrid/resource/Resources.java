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

package siminov.hybrid.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.adapter.AdapterResources;
import siminov.hybrid.events.EventHandler;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridLibraryDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.orm.database.DatabaseBundle;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.LibraryDescriptor;
import android.webkit.WebView;

public class Resources {

	private static Resources hybridResources = null;
	private siminov.orm.resource.Resources ormResources = null;
	
	private HybridDescriptor hybridDescriptor = null;
	
	private WebView webView = null;
	
	private EventHandler eventHandler = null;
	
	private AdapterHandler adapterHandler = null;
	private AdapterResources adapterResources = null;

	private Map<String, String> webNativeClassMapping = new ConcurrentHashMap<String, String>();
	
	private Resources() {
		ormResources = siminov.orm.resource.Resources.getInstance();
		eventHandler = EventHandler.getInstance();
	}
	
	public static Resources getInstance() {
		
		if(hybridResources == null) {
			hybridResources = new Resources();
		}
		
		return hybridResources;
	}
	
	public HybridDescriptor getHybridDescriptor() {
		return this.hybridDescriptor;
	}
	
	public void setHybridDescriptor(HybridDescriptor hybridDescriptor) {
		this.hybridDescriptor = hybridDescriptor;
	}

	public Iterator<Adapter> getAdapters() {
		
		Iterator<Adapter> adapters = hybridDescriptor.getAdapters();
		Iterator<Adapter> libraryAdapters = getLibrariesAdapters();

		Collection<Adapter> allAdapters = new ArrayList<Adapter>();
		while(adapters.hasNext()) {
			allAdapters.add(adapters.next());
		}
		
		while(libraryAdapters.hasNext()) {
			allAdapters.add(libraryAdapters.next());
		}
		
		return allAdapters.iterator();
	}
	
	public Iterator<Adapter> getLibrariesAdapters() {
		
		Iterator<HybridLibraryDescriptor> libraries = hybridDescriptor.getLibraries();
		Collection<Adapter> adapters = new ArrayList<Adapter>();
		
		while(libraries.hasNext()) {
			HybridLibraryDescriptor libraryDescriptor = libraries.next();
			Iterator<Adapter> libraryAdapters = libraryDescriptor.getAdapters();
			
			while(libraryAdapters.hasNext()) {
				adapters.add(libraryAdapters.next());
			}
		}
		
		return adapters.iterator();
	}
	
	public Iterator<Adapter> getAdaptersBasedOnPaths() {
		
		Iterator<String> adapterPaths = hybridDescriptor.getAdapterPaths();
		Collection<Adapter> adapters = new ArrayList<Adapter>();
		
		while(adapterPaths.hasNext()) {
			String adapterPath = adapterPaths.next();
			adapters.add(hybridDescriptor.getAdapterBasedOnPath(adapterPath));
		}
		
		return adapters.iterator();
	}
	
	public Iterator<Adapter> getLibraryAdaptersBasedOnName(final String libraryName) {
		return hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName).getAdapters();
	}
	
	public Iterator<Adapter> getLibraryAdaptersBasedOnPath(final String libraryPath) {
		return hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath).getAdapters();
	}
	
	public Adapter getAdapter(final String adapterName) {
		boolean contain = hybridDescriptor.containAdapterBasedOnName(adapterName);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		Iterator<HybridLibraryDescriptor> libraries = hybridDescriptor.getLibraries();
		while(libraries.hasNext()) {
			HybridLibraryDescriptor libraryDescriptor = libraries.next();
			contain = libraryDescriptor.containAdapterBasedOnName(adapterName);
			
			if(contain) {
				return libraryDescriptor.getAdapterBasedOnName(adapterName);
			}
		}
		
		return null;
	}
	
	public Adapter getAdapterBasedOnName(final String adapterName) {
		
		boolean contain = hybridDescriptor.containAdapterBasedOnName(adapterName);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		return null;
	}
	
	public Adapter getAdapterBasedOnPath(final String adapterPath) {
		
		boolean contain = hybridDescriptor.containAdapterBasedOnPath(adapterPath);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnPath(adapterPath);
		}
		
		return null;
	}
	
	public Adapter getLibraryAdapterBasedOnName(final String libraryName, final String adapterName) {

		HybridLibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName);
		boolean contain = libraryDescriptor.containAdapterBasedOnName(adapterName);
		
		if(contain) {
			return libraryDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		return null;
	}
	
	public Adapter getLibraryAdapterBasedOnPath(final String libraryPath, final String adapterPath) {
		
		HybridLibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);
		boolean contain = libraryDescriptor.containAdapterBasedOnPath(adapterPath);
		
		if(contain) {
			return libraryDescriptor.getAdapterBasedOnPath(adapterPath);
		}
		
		return null;
	}
	
	public boolean containAdapterBasedOnName(final String adapterName) {
		boolean contain = hybridDescriptor.containAdapterBasedOnName(adapterName);
		if(contain) {
			return contain;
		}
		
		Iterator<String> libraryPaths = hybridDescriptor.getLibraryPaths();
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			LibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);
			
			contain = containAdapterBasedOnLibraryName(libraryDescriptor.getName(), adapterName);
			if(contain) {
				return contain;
			}
		}
		
		return false;
	}
	
	public boolean containAdapterBasedOnPath(final String adapterPath) {
		return hybridDescriptor.containAdapterBasedOnPath(adapterPath);
	}
	
	public boolean containAdapterBasedOnLibraryName(final String libraryName, final String adapterName) {
		HybridLibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName);
		return libraryDescriptor.containAdapterBasedOnName(adapterName);
	}

	public boolean containAdapterBasedOnLibraryPath(final String libraryPath, final String adapterPath) {
		HybridLibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);
		return libraryDescriptor.containAdapterBasedOnPath(adapterPath);
	}
	
	public boolean containLibraryBasedOnName(final String libraryName) {
		return hybridDescriptor.containLibraryBasedOnName(libraryName);
	}

	public boolean containLibraryBasedOnPath(final String libraryPath) {
		return hybridDescriptor.containLibraryBasedOnPath(libraryPath);
	}
	
	public Iterator<Handler> getHandlers() {
		
		Collection<Handler> handlers = new ArrayList<Handler>();
		
		Iterator<Adapter> adapters = getAdapters();
		while(adapters.hasNext()) {
			Adapter adapter = adapters.next();
			Iterator<Handler> adapterHandlers = adapter.getHandlers();
			
			while(adapterHandlers.hasNext()) {
				handlers.add(adapterHandlers.next());
			}
		}
		
		return handlers.iterator();
	}

	public Handler getHandler(final String adapterName, final String handlerName) {
		
		Adapter adapter = getAdapter(adapterName);
		boolean contain = adapter.containHandler(handlerName);
		
		if(contain) {
			return adapter.getHandler(handlerName);
		}
		
		return null;
	}
	
	public boolean containHandler(final String handlerName) {
		
		Iterator<Adapter> adapters = getAdapters();
		while(adapters.hasNext()) {
			Adapter adapter = adapters.next();
			Iterator<Handler> handlers = adapter.getHandlers();
			
			while(handlers.hasNext()) {
				Handler handler = handlers.next();
				if(handler.getName().equalsIgnoreCase(handlerName)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean containHandler(final String adapterName, final String handlerName) {
		Adapter adapter = getAdapter(adapterName);
		return adapter.containHandler(handlerName);
	}
	
	public WebView getWebView() {
		return this.webView;
	}
	
	public void setWebView(final WebView webView) {
		this.webView = webView;
	}

	
	public boolean doesEventsRegistered() {
		return this.eventHandler.doesEventsRegistered();
	}
	
	public void addEvent(String event) {
		this.eventHandler.addEvent(event);
	}
	
	public Iterator<String> getEvents() {
		return this.eventHandler.getEvents();
	}

	public AdapterHandler getAdapterHandler() {
		return this.adapterHandler;
	}
	
	public void setAdapterHandler(final AdapterHandler adapterHandler) {
		this.adapterHandler = adapterHandler;
	}
	
	public AdapterResources getAdapterResources() {
		return this.adapterResources;
	}
	
	public void setAdapterResources(final AdapterResources adapterResources) {
		this.adapterResources = adapterResources;
	}


	/*
	 * Siminov ORM API's.
	 */

	public DatabaseBundle getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(final String className) {
		
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(nativeClassName);
		
	}
	
	public DatabaseBundle getDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(final String tableName) {
		return ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(tableName);
	}
	
	public DatabaseDescriptor getDatabaseDescriptorBasedOnClassName(final String className) {

		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	
	public String getDatabaseDescriptorNameBasedOnClassName(final String className) {
	
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseDescriptorNameBasedOnClassName(nativeClassName);
		
	}
	

	public String getDatabaseDescriptorNameBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseDescriptorNameBasedOnTableName(tableName);
	}

	
	public DatabaseDescriptor getDatabaseDescriptorBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseDescriptorBasedOnTableName(tableName);
	}
	
	
	public DatabaseMappingDescriptor getDatabaseMappingDescriptorBasedOnClassName(final String className) {
		
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseMappingDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	public DatabaseMappingDescriptor getDatabaseMappingDescriptorBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseMappingDescriptorBasedOnTableName(tableName);
	}


	public String getMappedNativeClassName(final String jsClassName) {
		return webNativeClassMapping.get(jsClassName);
	}
	
	public String getMappedWebClassName(final String nativeClassName) {
		Iterator<String> keys = webNativeClassMapping.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = webNativeClassMapping.get(key);
			
			if(value.equalsIgnoreCase(nativeClassName)) {
				return key;
			}
			
		}
		
		return null;
	}

	public void synchronizeMappings() {

		Iterator<DatabaseMappingDescriptor> databaseMappingDescriptors = ormResources.getDatabaseMappingDescriptors();
		while(databaseMappingDescriptors.hasNext()) {
			DatabaseMappingDescriptor databaseMappingDescriptor = databaseMappingDescriptors.next();
			
			String nativeClassName = databaseMappingDescriptor.getClassName();
			String jsClassName = nativeClassName.substring(nativeClassName.lastIndexOf(".") + 1, nativeClassName.length());
			
			webNativeClassMapping.put(jsClassName, nativeClassName);
			
		}
	}
	
}
