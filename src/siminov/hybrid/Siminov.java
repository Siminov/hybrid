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

package siminov.hybrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.events.DatabaseEventHandler;
import siminov.hybrid.events.SiminovEventHandler;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridLibraryDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.parsers.HybridDescriptorParser;
import siminov.hybrid.parsers.HybridLibraryDescriptorParser;
import siminov.hybrid.resource.Resources;
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.ApplicationDescriptor;
import android.content.Context;
import android.webkit.WebView;

public class Siminov extends siminov.orm.Siminov {

	protected static Resources hybridResources = Resources.getInstance();
	
	protected static boolean isActive = false;

	public static void validateSiminov() {
		if(!isActive && !siminov.orm.Siminov.isActive) {
			throw new DeploymentException(Siminov.class.getName(), "validateSiminov", "Siminov Not Active.");
		}
	}
	
	
	public static void initialize(Context applicationContext, WebView webView) {
		
		ormResources.setApplicationContext(applicationContext);
		hybridResources.setWebView(webView);
		
		
		siminov.orm.Siminov.processApplicationDescriptor();
		processEvents();
		
		
		siminov.orm.Siminov.processDatabaseDescriptors();
		siminov.orm.Siminov.processLibraries();
		siminov.orm.Siminov.processDatabaseMappingDescriptors();

		
		hybridResources.synchronizeMappings();

		
		processHybridDescriptor();
		processLibraries();
		processAdapters();

		
		processHybridServices();
		
	}

	
	protected static void processHybridDescriptor() {
		
		HybridDescriptorParser jsDescriptorParser = null;
		
		try {
			jsDescriptorParser = new HybridDescriptorParser();
		} catch(SiminovException siminovException) {
			Log.logd(Siminov.class.getName(), "processHybridDescriptor", "SiminovException caught while doing js descriptor parser, " + siminovException.getMessage());
			throw new DeploymentException(Siminov.class.getName(), "processHybridDescriptor", siminovException.getMessage());
		} catch(DeploymentException deploymentException) {
			Log.logd(Siminov.class.getName(), "processHybridDescriptor", "DeploymentException caught while doing js descriptor parser, " + deploymentException.getMessage());
			throw new DeploymentException(Siminov.class.getName(), "processHybridDescriptor", deploymentException.getMessage());
		}
		
		HybridDescriptor jsDescriptor = jsDescriptorParser.getJSDescriptor();
		if(jsDescriptor == null) {
			Log.logd(Siminov.class.getName(), "processHybridDescriptor", "Invalid JS Descriptor Found.");
			throw new DeploymentException(Siminov.class.getName(), "processHybridDescriptor", "Invalid JS Descriptor Found.");
		}
		
		/*
		 * Add Siminov - JS Library
		 */
		jsDescriptor.addLibraryPath(Constants.HYBRID_DESCRIPTOR_SIMINOV_HYBRID_LIBRARY_PATH);
		
		hybridResources.setHybridDescriptor(jsDescriptor);
	}

	
	protected static void processLibraries() {
		
		HybridDescriptor jsDescriptor = hybridResources.getHybridDescriptor();
		Iterator<String> libraryPaths = jsDescriptor.getLibraryPaths();

		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			/*
			 * Parse LibraryDescriptor.
			 */
			HybridLibraryDescriptorParser libraryDescriptorParser = null;
			
			try {
				libraryDescriptorParser = new HybridLibraryDescriptorParser(libraryPath);
			} catch(SiminovException ce) {
				Log.loge(Siminov.class.getName(), "processLibraries", "SiminovException caught while parsing library descriptor, LIBRARY-NAME: " + libraryPath + ", " + ce.getMessage());
				throw new DeploymentException(Siminov.class.getName(), "processLibraries", ce.getMessage());
			}
			
			jsDescriptor.addLibrary(libraryPath, libraryDescriptorParser.getLibraryDescriptor());
		}
		
	}
	
	
	protected static void processAdapters() {
		
		HybridDescriptor jsDescriptor = hybridResources.getHybridDescriptor();
		
		Iterator<String> libraryPaths = jsDescriptor.getLibraryPaths();
		Iterator<String> adapterPaths = jsDescriptor.getAdapterPaths();
		
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			HybridLibraryDescriptor libraryDescriptor = jsDescriptor.getLibraryDescriptorBasedOnPath(libraryPath);

			processAdapters(libraryPath, libraryDescriptor);
		}

		
		processAdapters(adapterPaths);
	}

	
	private static void processAdapters(final Iterator<String> adapterPaths) {
		
		HybridDescriptor jsDescriptor = hybridResources.getHybridDescriptor();
		while(adapterPaths.hasNext()) {
			String adapterPath = adapterPaths.next();
			HybridDescriptorParser jsDescriptorParser = null;
			
			try {
				jsDescriptorParser = new HybridDescriptorParser(adapterPath);
			} catch(SiminovException ce) {
				Log.loge(Siminov.class.getName(), "processAdapters", "SiminovException caught while parsing database mapping, LIBRARY-DATABASE-MAPPING: " + adapterPath + ", " + ce.getMessage());
				throw new DeploymentException(Siminov.class.getName(), "processAdapters", "LIBRARY-DATABASE-MAPPING: " + adapterPath + ", " + ce.getMessage());
			}
			
			Iterator<Adapter> adapters = jsDescriptorParser.getJSDescriptor().getAdapters();
			while(adapters.hasNext()) {
				Adapter adapter = adapters.next();
				jsDescriptor.addAdapter(adapterPath, adapter);				
			}
		}
	}
	
	
	private static void processAdapters(final String libraryPackageName, final HybridLibraryDescriptor libraryDescriptor) {
		
		Iterator<String> libraryAdapterPaths = libraryDescriptor.getAdapterPaths();
		while(libraryAdapterPaths.hasNext()) {
			String libraryAdapterPath = libraryAdapterPaths.next();
			HybridDescriptorParser jsDescriptorParser = null;
			
			try {
				jsDescriptorParser = new HybridDescriptorParser(libraryPackageName, libraryAdapterPath);
			} catch(SiminovException ce) {
				Log.loge(Siminov.class.getName(), "processAdapters", "SiminovException caught while parsing database mapping, LIBRARY-DATABASE-MAPPING: " + libraryAdapterPath + ", " + ce.getMessage());
				throw new DeploymentException(Siminov.class.getName(), "processAdapters", "LIBRARY-DATABASE-MAPPING: " + libraryAdapterPath + ", " + ce.getMessage());
			}
			
			Iterator<Adapter> adapters = jsDescriptorParser.getJSDescriptor().getAdapters();
			while(adapters.hasNext()) {
				Adapter adapter = adapters.next();
				libraryDescriptor.addAdapter(libraryAdapterPath, adapter);				
			}
		}
	}

	
	protected static void processEvents() {
		
		ApplicationDescriptor applicationDescriptor = ormResources.getApplicationDescriptor();
		
		Collection<String> siminovEvents = new ArrayList<String>();
		Iterator<String> events = applicationDescriptor.getEvents();

		while(events.hasNext()) {
			String event = events.next();
			
			hybridResources.addEvent(event);
			siminovEvents.add(event);
		}

		events = siminovEvents.iterator();
		while(events.hasNext()) {
			String event = events.next();
			applicationDescriptor.removeEvent(event);
		}

		applicationDescriptor.addEvent(SiminovEventHandler.class.getName());
		applicationDescriptor.addEvent(DatabaseEventHandler.class.getName());
		
	}
	
	
	protected static void processHybridServices() {
		AdapterHandler.getInstance();
	}

	
	protected static void siminovInitialized() {
		
		isActive = true;
		siminov.orm.Siminov.isActive = true;
		
		ISiminovEvents coreEventHandler = ormResources.getSiminovEventHandler();
		if(ormResources.getSiminovEventHandler() != null) {
			if(siminov.orm.Siminov.firstTimeProcessed) {
				coreEventHandler.firstTimeSiminovInitialized();
			} else {
				coreEventHandler.siminovInitialized();
			}
		} 

	}
	
}
