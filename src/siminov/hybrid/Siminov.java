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


package siminov.hybrid;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.events.DatabaseEventHandler;
import siminov.hybrid.events.SiminovEventHandler;
import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.LibraryDescriptor;
import siminov.hybrid.reader.HybridDescriptorReader;
import siminov.hybrid.reader.LibraryDescriptorReader;
import siminov.hybrid.resource.Resources;
import siminov.orm.Constants;
import siminov.orm.IInitializer;
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.ApplicationDescriptor;
import siminov.orm.model.DatabaseDescriptor;

/**
 * Exposes methods to deal with SIMINOV HYBRID FRAMEWORK.
 *	<p>
 *		Such As
 *		<p>
 *			1. Initializer: Entry point to the SIMINOV HYBRID.
 *		</p>
 *	
 *
 *	Note*: siminov.hybrid.Siminov extends siminov.orm.Siminov.
 *
 *	</p>
 */
public class Siminov extends siminov.connect.Siminov {

	protected static Resources hybridResources = Resources.getInstance();
	
	protected static boolean isActive = false;

	/**
	 * It is used to check whether SIMINOV HYBRID FRAMEWORK is active or not.
	 * <p>
	 * SIMINOV become active only when its dependent and itself get initialized.
	 * 
	 * @exception If SIMINOV is not active it will throw DeploymentException which is RuntimeException.
	 * 
	 */
	public static void isActive() {

		if(!isActive && !siminov.orm.Siminov.isActive) {
			throw new DeploymentException(Siminov.class.getName(), "isActive", "Siminov Not Active.");
		}
	}
	
	
	public static IInitializer initializer() {
		return new Initializer();
	}
	
	
	/**
	 * It is the entry point to the SIMINOV HYBRID FRAMEWORK.
	 * <p>
	 * When application starts it should call this method to activate SIMINOV HYBRID FRAMEWORK, by providing ApplicationContext and WebView as the parameter.
	 * </p>
	 * 
	 * <p>
	 * Siminov will read all descriptor defined by application, and do necessary processing.
	 * </p>
	 * 
	 * 	EXAMPLE
	 * 	@code {
	 * 

		 	public class Siminov extends DroidGap {
	
				public void onCreate(Bundle savedInstanceState) { 
			
					super.onCreate(savedInstanceState);
					super.init();
					super.appView.getSettings().setJavaScriptEnabled(true);
			
					initializeSiminov();
			
					super.loadUrl("file:///android_asset/www/home.html");
			
				}
			
			
				private void initializeSiminov() {
					siminov.hybrid.Siminov.initialize(getApplicationContext(), this.appView);
				}
			
			}

	 * }
	 * 
	 * @param context Application content.
	 * @param webView WebView.
	 * @exception If any exception occur while deploying application it will through DeploymentException, which is RuntimeException.
	 */
	static void start() {
		
		processApplicationDescriptor();
		
		processDatabaseDescriptors();
		processHybridDescriptor();
		processLibraries();

		
		processEvents();

		
		processDatabaseMappingDescriptors();

		
		processAdapterDescriptors();
		processHybridServices();
	}


	/**
	 * It is used to stop all service started by SIMINOV.
	 * <p>
	 * When application shutdown they should call this. It do following services: 
	 * <p>
	 * 		<pre>
	 * 			<ul>
	 * 				<li> Close all database's opened by SIMINOV.
	 * 				<li> Deallocate all resources held by SIMINOV.
	 * 			</ul>
	 *		</pre>
	 *	</p>
	 * 
	 * @throws SiminovException If any error occur while shutting down SIMINOV.
	 */
	public static void shutdown() {
		
		siminov.connect.Siminov.shutdown();
	}
	
	protected static void processApplicationDescriptor() {
		siminov.connect.Siminov.processApplicationDescriptor();
	}

	
	protected static void processDatabaseDescriptors() {
		siminov.connect.Siminov.processDatabaseDescriptors();
	}
	
	/**
	 * It process all LibraryDescriptor.si.xml files defined by application, and stores in Resources.
	 */
	protected static void processLibraries() {
		
		ApplicationDescriptor applicationDescriptor = ormResources.getApplicationDescriptor();
		applicationDescriptor.addLibrary(siminov.hybrid.Constants.HYBRID_LIBRARY_DESCRIPTOR_FILE_PATH);


		Iterator<String> libraries = applicationDescriptor.getLibraries();
		
		while(libraries.hasNext()) {
			
			String library = libraries.next();

			/*
			 * Parse LibraryDescriptor.
			 */
			LibraryDescriptorReader libraryDescriptorReader = new LibraryDescriptorReader(library);
			LibraryDescriptor libraryDescriptor = libraryDescriptorReader.getLibraryDescriptor();
			

			/*
			 * Map Database Mapping Descriptors
			 */
			Iterator<String> databaseMappingDescriptors = libraryDescriptor.getDatabaseMappingPaths();
			while(databaseMappingDescriptors.hasNext()) {

				String libraryDatabaseMappingDescriptorPath = databaseMappingDescriptors.next();
				
				String databaseDescriptorName = libraryDatabaseMappingDescriptorPath.substring(0, libraryDatabaseMappingDescriptorPath.indexOf(Constants.LIBRARY_DESCRIPTOR_DATABASE_MAPPING_SEPRATOR));
				String databaseMappingDescriptor = libraryDatabaseMappingDescriptorPath.substring(libraryDatabaseMappingDescriptorPath.indexOf(Constants.LIBRARY_DESCRIPTOR_DATABASE_MAPPING_SEPRATOR) + 1, libraryDatabaseMappingDescriptorPath.length());
				
				
				Iterator<DatabaseDescriptor> databaseDescriptors = applicationDescriptor.getDatabaseDescriptors();
				while(databaseDescriptors.hasNext()) {
					
					DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
					if(databaseDescriptor.getDatabaseName().equalsIgnoreCase(databaseDescriptorName)) {
						databaseDescriptor.addDatabaseMappingDescriptorPath(library.replace(".", "/") + File.separator + databaseMappingDescriptor);
					}
				}
			}
			
			
			/*
			 * Map Adapters
			 */
			HybridDescriptor hybridDescriptor = hybridResources.getHybridDescriptor();
			Iterator<String> adapterDescriptorPaths = libraryDescriptor.getAdapterDescriptorPaths();

			while(adapterDescriptorPaths.hasNext()) {
				
				String adapterDescriptorPath = adapterDescriptorPaths.next();
				hybridDescriptor.addAdapterDescriptorPath(library.replace(".", "/") + File.separator + adapterDescriptorPath);
			}
		}
	}
	

	protected static void processDatabaseMappingDescriptors() {
		siminov.connect.Siminov.processDatabaseMappingDescriptors();
		
		hybridResources.synchronizeMappings();
	}

	
	protected static void processDatabase() {
		siminov.connect.Siminov.processDatabase();
	}
	
	
	protected static void processKonnectDescriptor() {
		siminov.connect.Siminov.processKonnectDescriptor();
	}
	
	
	protected static void processServices() {
		siminov.connect.Siminov.processServices();
	}
	
	
	/**
	 * It process HybridDescriptor.si.xml file defined in Application, and stores in Resources.
	 */
	protected static void processHybridDescriptor() {
		
		HybridDescriptorReader hybridDescriptorReader = new HybridDescriptorReader();
		
		HybridDescriptor hybridDescriptor = hybridDescriptorReader.getHybridDescriptor();
		if(hybridDescriptor == null) {
			Log.logd(Siminov.class.getName(), "processHybridDescriptor", "Invalid Hybrid Descriptor Found.");
			throw new DeploymentException(Siminov.class.getName(), "processHybridDescriptor", "Invalid Hybrid Descriptor Found.");
		}
		

		hybridResources.setHybridDescriptor(hybridDescriptor);
	}

	
	protected static void processAdapterDescriptors() {
		
		HybridDescriptor hybridDescriptor = hybridResources.getHybridDescriptor();
		Iterator<String> adapterDescriptorPaths = hybridDescriptor.getAdapterDescriptorPaths();

		while(adapterDescriptorPaths.hasNext()) {
			String adapterDescriptorPath = adapterDescriptorPaths.next();
			HybridDescriptorReader hybridDescriptorParser = new HybridDescriptorReader(adapterDescriptorPath);
			
			Iterator<AdapterDescriptor> adapterDescriptors = hybridDescriptorParser.getHybridDescriptor().getAdapterDescriptors();
			while(adapterDescriptors.hasNext()) {
				AdapterDescriptor adapterDescriptor = adapterDescriptors.next();
				hybridDescriptor.addAdapterDescriptor(adapterDescriptorPath, adapterDescriptor);				
			}
		}
	}
	
	
	/**
	 * It process all Events defined in ApplicationDescriptor.si.xml file in Application, and stores in Resources.
	 */
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
	
	

	/**
	 * It process and initialize all services needed by SIMINOV HYBRID FRAMEWORK to work functionally.
	 */
	protected static void processHybridServices() {
		AdapterHandler.getInstance();
	}

	
	/**
	 * It Triggers Events that SIMINOV HYBRID FRAMEWORK is initialized properly. 
	 */
	protected static void siminovInitialized() {
		
		isActive = true;
		siminov.orm.Siminov.isActive = true;
		
		ISiminovEvents coreEventHandler = ormResources.getSiminovEventHandler();
		if(ormResources.getSiminovEventHandler() != null) {
			if(siminov.orm.Siminov.firstTimeProcessed) {
				coreEventHandler.onFirstTimeSiminovInitialized();
			} else {
				coreEventHandler.onSiminovInitialized();
			}
		} 
	}
}
