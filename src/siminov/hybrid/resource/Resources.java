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
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.hybrid.model.LibraryDescriptor;
import siminov.orm.database.DatabaseBundle;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import android.webkit.WebView;

/**
 * It handles and provides all resources needed by SIMINOV HYBRID.
 * <p>
 * Such As: Provides HybridDescriptor, AdapterDescriptor.
 */
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
	
	/**
	 * It provides an instance of Resources class.
	 * 
	 * @return Resources instance.
	 */
	public static Resources getInstance() {
		
		if(hybridResources == null) {
			hybridResources = new Resources();
		}
		
		return hybridResources;
	}

	/**
	 * Get Hybrid Descriptor.
	 * @return Hybrid Descriptor.
	 */
	public HybridDescriptor getHybridDescriptor() {
		return this.hybridDescriptor;
	}
	
	/**
	 * Set Hybrid Descriptor.
	 * @param hybridDescriptor Hybrid Descriptor.
	 */
	public void setHybridDescriptor(HybridDescriptor hybridDescriptor) {
		this.hybridDescriptor = hybridDescriptor;
	}

	
	
	/**
	 * Get All Adapters defined by Application.
	 * @return All Adapters.
	 */
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
	
	/**
	 * Get All Adapters defined in Libraries.
	 * @return All Adapters.
	 */
	public Iterator<Adapter> getLibrariesAdapters() {
		
		Iterator<LibraryDescriptor> libraries = hybridDescriptor.getLibraries();
		Collection<Adapter> adapters = new ArrayList<Adapter>();
		
		while(libraries.hasNext()) {
			LibraryDescriptor libraryDescriptor = libraries.next();
			Iterator<Adapter> libraryAdapters = libraryDescriptor.getAdapters();
			
			while(libraryAdapters.hasNext()) {
				adapters.add(libraryAdapters.next());
			}
		}
		
		return adapters.iterator();
	}
	
	/**
	 * Get All Adapters Defined By Paths.
	 * @return All Adapters.
	 */
	public Iterator<Adapter> getAdaptersBasedOnPaths() {
		
		Iterator<String> adapterPaths = hybridDescriptor.getAdapterPaths();
		Collection<Adapter> adapters = new ArrayList<Adapter>();
		
		while(adapterPaths.hasNext()) {
			String adapterPath = adapterPaths.next();
			adapters.add(hybridDescriptor.getAdapterBasedOnPath(adapterPath));
		}
		
		return adapters.iterator();
	}
	
	/**
	 * Get All Adapters based on library name.
	 * @param libraryName Name of Library.
	 * @return All Adapters.
	 */
	public Iterator<Adapter> getLibraryAdaptersBasedOnName(final String libraryName) {
		return hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName).getAdapters();
	}
	
	/**
	 * Get All Adapters based on library path.
	 * @param libraryPath Path of Library.
	 * @return All Adapters.
	 */
	public Iterator<Adapter> getLibraryAdaptersBasedOnPath(final String libraryPath) {
		return hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath).getAdapters();
	}

	/**
	 * Get Adapter based on Adapter Name.
	 * @param adapterName Name of Adapter.
	 * @return
	 */
	public Adapter getAdapter(final String adapterName) {
		boolean contain = hybridDescriptor.containAdapterBasedOnName(adapterName);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		Iterator<LibraryDescriptor> libraries = hybridDescriptor.getLibraries();
		while(libraries.hasNext()) {
			LibraryDescriptor libraryDescriptor = libraries.next();
			contain = libraryDescriptor.containAdapterBasedOnName(adapterName);
			
			if(contain) {
				return libraryDescriptor.getAdapterBasedOnName(adapterName);
			}
		}
		
		return null;
	}
	
	/**
	 * Get Adapter based on Adapter Name.
	 * @param adapterName Name of Adapter.
	 * @return Adapter.
	 */
	public Adapter getAdapterBasedOnName(final String adapterName) {
		
		boolean contain = hybridDescriptor.containAdapterBasedOnName(adapterName);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		return null;
	}
	
	/**
	 * Get Adapter based on adapter path.
	 * @param adapterPath Path of Adapter.
	 * @return Adapter.
	 */
	public Adapter getAdapterBasedOnPath(final String adapterPath) {
		
		boolean contain = hybridDescriptor.containAdapterBasedOnPath(adapterPath);
		if(contain) {
			return hybridDescriptor.getAdapterBasedOnPath(adapterPath);
		}
		
		return null;
	}
	
	/**
	 * Get Adapter based on library name and adapter name,
	 * @param libraryName Name of Library.
	 * @param adapterName Name of Adapter.
	 * @return Adapter.
	 */
	public Adapter getLibraryAdapterBasedOnName(final String libraryName, final String adapterName) {

		LibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName);
		boolean contain = libraryDescriptor.containAdapterBasedOnName(adapterName);
		
		if(contain) {
			return libraryDescriptor.getAdapterBasedOnName(adapterName);
		}
		
		return null;
	}

	/**
	 * Get Adapter based on library path and adapter path.
	 * @param libraryPath Name of Library.
	 * @param adapterPath Path of Adapter.
	 * @return Adapter.
	 */
	public Adapter getLibraryAdapterBasedOnPath(final String libraryPath, final String adapterPath) {
		
		LibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);
		boolean contain = libraryDescriptor.containAdapterBasedOnPath(adapterPath);
		
		if(contain) {
			return libraryDescriptor.getAdapterBasedOnPath(adapterPath);
		}
		
		return null;
	}
	
	/**
	 * Check whether adapter exist or not based on adapter name.
	 * @param adapterName Name of adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
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
	
	/**
	 * Check whether adapter exist or not based on adapter path.
	 * @param adapterPath Path of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterBasedOnPath(final String adapterPath) {
		return hybridDescriptor.containAdapterBasedOnPath(adapterPath);
	}
	
	/**
	 * Check whether adapter exist based on library name and adapter name.
	 * @param libraryName Name of Library.
	 * @param adapterName Name of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterBasedOnLibraryName(final String libraryName, final String adapterName) {
		LibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnName(libraryName);
		return libraryDescriptor.containAdapterBasedOnName(adapterName);
	}

	/**
	 * Check whether adapter exist or not based on library path and adapter path.
	 * @param libraryPath Path of Library.
	 * @param adapterPath Path of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterBasedOnLibraryPath(final String libraryPath, final String adapterPath) {
		LibraryDescriptor libraryDescriptor = hybridDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);
		return libraryDescriptor.containAdapterBasedOnPath(adapterPath);
	}
	
	/**
	 * Check whether Library exist based on library name.
	 * @param libraryName Name of Library.
	 * @return true/false; TRUE if library exist, FALSE if library does not exist.
	 */
	public boolean containLibraryBasedOnName(final String libraryName) {
		return hybridDescriptor.containLibraryBasedOnName(libraryName);
	}

	/**
	 * Check whether Library exist based on library path.
	 * @param libraryPath Path of Library.
	 * @return true/false; TRUE if library exist, FALSE if library does not exist.
	 */
	public boolean containLibraryBasedOnPath(final String libraryPath) {
		return hybridDescriptor.containLibraryBasedOnPath(libraryPath);
	}
	
	/**
	 * Get All Handlers defined by Application.
	 * @return All Handlers.
	 */
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

	/**
	 * Get Handler based on Adapter Name and Handler Name.
	 * @param adapterName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return Handler.
	 */
	public Handler getHandler(final String adapterName, final String handlerName) {
		
		Adapter adapter = getAdapter(adapterName);
		boolean contain = adapter.containHandler(handlerName);
		
		if(contain) {
			return adapter.getHandler(handlerName);
		}
		
		return null;
	}
	
	/**
	 * Check whether Handler exist or not based on handler name.
	 * @param handlerName Name of Handler.
	 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
	 */
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
	
	/**
	 * Check whether Handler exist or not based on adapter name and handler name.
	 * @param adapterName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
	 */
	public boolean containHandler(final String adapterName, final String handlerName) {
		Adapter adapter = getAdapter(adapterName);
		return adapter.containHandler(handlerName);
	}
	
	/**
	 * Get Web View.
	 * @return Web View.
	 */
	public WebView getWebView() {
		return this.webView;
	}
	
	/**
	 * Set Web View.
	 * @param webView Web View.
	 */
	public void setWebView(final WebView webView) {
		this.webView = webView;
	}

	
	/**
	 * Check whether any event registered by application or not.
	 * @return true/false; TRUE if event registered by application, FALSE if event not registered by application.
	 */
	public boolean doesEventsRegistered() {
		return this.eventHandler.doesEventsRegistered();
	}
	
	/**
	 * Add Event.
	 * @param event Event.
	 */
	public void addEvent(String event) {
		this.eventHandler.addEvent(event);
	}
	
	/**
	 * Get All Events registered by application.
	 * @return All Events.
	 */
	public Iterator<String> getEvents() {
		return this.eventHandler.getEvents();
	}

	/**
	 * Get AdapterHandler.
	 * @return AdapterHandler.
	 */
	public AdapterHandler getAdapterHandler() {
		return this.adapterHandler;
	}
	
	/**
	 * Set AdapterHandler.
	 * @param adapterHandler AdapterHandler.
	 */
	public void setAdapterHandler(final AdapterHandler adapterHandler) {
		this.adapterHandler = adapterHandler;
	}
	
	/**
	 * Get Adapter Resources.
	 * @return Adapter Resources.
	 */
	public AdapterResources getAdapterResources() {
		return this.adapterResources;
	}
	
	/**
	 * Set Adapter Resources.
	 * @param adapterResources Adapter Resources.
	 */
	public void setAdapterResources(final AdapterResources adapterResources) {
		this.adapterResources = adapterResources;
	}


	/*
	 * Siminov ORM API's.
	 */

	/**
	 * Get Database Bundle based on database mapping descriptor class name as per Web model.
	 * @param className Name of Web Model Class.
	 * @return Database Bundle.
	 */
	public DatabaseBundle getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(final String className) {
		
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Bundle based on database mapping descriptor table name.
	 * @param tableName Name of Table.
	 * @return Database Bundle.
	 */
	public DatabaseBundle getDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(final String tableName) {
		return ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(tableName);
	}
	
	/**
	 * Get Database Descriptor based on Web model class name.
	 * @param className Name of Web Model Class.
	 * @return Database Descriptor.
	 */
	public DatabaseDescriptor getDatabaseDescriptorBasedOnClassName(final String className) {

		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Descriptor Name based on Web Model class name.
	 * @param className Name of Web Model Class.
	 * @return Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnClassName(final String className) {
	
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseDescriptorNameBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Descriptor Name based on table name.
	 * @param tableName Name of table.
	 * @return Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseDescriptorNameBasedOnTableName(tableName);
	}

	/**
	 * Get Database Descriptor based on table name.
	 * @param tableName Name of Table.
	 * @return Database Descriptor.
	 */
	public DatabaseDescriptor getDatabaseDescriptorBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseDescriptorBasedOnTableName(tableName);
	}
	
	/**
	 * Get Database Mapping Descriptor based on Web Model Class Name.
	 * @param className Name of Web Model Class.
	 * @return Database Mapping Descriptor.
	 */
	public DatabaseMappingDescriptor getDatabaseMappingDescriptorBasedOnClassName(final String className) {
		
		String nativeClassName = webNativeClassMapping.get(className);
		return ormResources.getDatabaseMappingDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Mapping Descriptor based on table name.
	 * @param tableName Name of Table.
	 * @return Database Mapping Descriptor.
	 */
	public DatabaseMappingDescriptor getDatabaseMappingDescriptorBasedOnTableName(final String tableName) {
		return ormResources.getDatabaseMappingDescriptorBasedOnTableName(tableName);
	}

	/**
	 * Get Mapped Native Model Class Name.
	 * @param hybridClassName Web Model Class Name.
	 * @return Native Model Class Name.
	 */
	public String getMappedNativeClassName(final String hybridClassName) {
		return webNativeClassMapping.get(hybridClassName);
	}
	
	/**
	 * Get Mapped Web Model Class Name.
	 * @param nativeClassName Native Model Class Name.
	 * @return Web Model Class Name.
	 */
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
	
	/**
	 * Map Native Model Class Name with Web Model Class Name.
	 */
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
