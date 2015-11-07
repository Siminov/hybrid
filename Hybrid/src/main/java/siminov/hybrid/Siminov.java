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



package siminov.hybrid;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.events.DatabaseEventHandler;
import siminov.hybrid.events.NotificationEventHandler;
import siminov.hybrid.events.SiminovEventHandler;
import siminov.hybrid.events.SyncEventHandler;
import siminov.hybrid.model.ApplicationDescriptor;
import siminov.hybrid.model.LibraryDescriptor;
import siminov.hybrid.reader.AdapterDescriptorReader;
import siminov.hybrid.reader.ApplicationDescriptorReader;
import siminov.hybrid.reader.LibraryDescriptorReader;
import siminov.hybrid.resource.ResourceManager;
import siminov.core.Constants;
import siminov.core.IInitializer;
import siminov.core.events.ISiminovEvents;
import siminov.core.exception.DeploymentException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.model.DatabaseDescriptor;

/**
 * Exposes methods to deal with SIMINOV HYBRID FRAMEWORK.
 *	<p>
 *		Such As
 *		<p>
 *			1. Initializer: Entry point to the SIMINOV HYBRID.
 *		</p>
 *	
 *
 *	Note*: siminov.hybrid.Siminov extends siminov.core.Siminov.
 *
 *	</p>
 */
public class Siminov extends siminov.connect.Siminov {

	protected static ResourceManager hybridResourceManager = ResourceManager.getInstance();
	
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

		if(!isActive && !siminov.core.Siminov.isActive) {
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
		processLibraries();

		
		processEvents();

		
		processEntityDescriptors();
		processSyncDescriptors();
		

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

	
	/**
	 * It process ApplicationDescriptor.si.xml file defined in Application, and stores in Resource Manager.
	 */
	protected static void processApplicationDescriptor() {
		
		ApplicationDescriptorReader applicationDescriptorParser = new ApplicationDescriptorReader();
		
		ApplicationDescriptor applicationDescriptor = applicationDescriptorParser.getApplicationDescriptor();
		if(applicationDescriptor == null) {
			Log.debug(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
			throw new DeploymentException(Siminov.class.getName(), "processApplicationDescriptor", "Invalid Application Descriptor Found.");
		}
		
		coreResourceManager.setApplicationDescriptor(applicationDescriptor);		
		connectResourceManager.setApplicationDescriptor(applicationDescriptor);
		hybridResourceManager.setApplicationDescriptor(applicationDescriptor);
	}

	
	/**
	 * It process all DatabaseDescriptor.si.xml files defined by Application and stores in Resource Manager.
	 */
	protected static void processDatabaseDescriptors() {
		siminov.connect.Siminov.processDatabaseDescriptors();
	}
	
	/**
	 * It process all LibraryDescriptor.si.xml files defined by application, and stores in Resource Manager.
	 */
	protected static void processLibraries() {
		
		ApplicationDescriptor applicationDescriptor = hybridResourceManager.getApplicationDescriptor();
		applicationDescriptor.addLibraryDescriptorPath(siminov.hybrid.Constants.LIBRARY_DESCRIPTOR_FILE_PATH);


		Iterator<String> libraries = applicationDescriptor.getLibraryDescriptorPaths();
		
		while(libraries.hasNext()) {
			
			String library = libraries.next();

			/*
			 * Parse LibraryDescriptor.
			 */
			LibraryDescriptorReader libraryDescriptorReader = new LibraryDescriptorReader(library);
			LibraryDescriptor libraryDescriptor = libraryDescriptorReader.getLibraryDescriptor();
			

			/*
			 * Map Entity Descriptors
			 */
			Iterator<String> entityDescriptors = libraryDescriptor.getEntityDescriptorPaths();
			while(entityDescriptors.hasNext()) {

				String libraryEntityDescriptorPath = entityDescriptors.next();
				
				String databaseDescriptorName = libraryEntityDescriptorPath.substring(0, libraryEntityDescriptorPath.indexOf(Constants.LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR));
				String entityDescriptor = libraryEntityDescriptorPath.substring(libraryEntityDescriptorPath.indexOf(Constants.LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR) + 1, libraryEntityDescriptorPath.length());
				
				
				Iterator<DatabaseDescriptor> databaseDescriptors = applicationDescriptor.getDatabaseDescriptors();
				while(databaseDescriptors.hasNext()) {
					
					DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
					if(databaseDescriptor.getDatabaseName().equalsIgnoreCase(databaseDescriptorName)) {
						databaseDescriptor.addEntityDescriptorPath(library.replace(".", "/") + File.separator + entityDescriptor);
					}
				}
			}
			
			
			/*
			 * Map Adapters
			 */
			Iterator<String> adapterDescriptorPaths = libraryDescriptor.getAdapterDescriptorPaths();

			while(adapterDescriptorPaths.hasNext()) {
				
				String adapterDescriptorPath = adapterDescriptorPaths.next();
				applicationDescriptor.addAdapterDescriptorPath(library.replace(".", "/") + File.separator + adapterDescriptorPath);
			}
		}
	}
	

	/**
	 * It process all EntityDescriptor.si.xml file defined in Application, and stores in Resource Manager.
	 */
	protected static void processEntityDescriptors() {
		siminov.connect.Siminov.processEntityDescriptors();
		
		hybridResourceManager.synchronizeEntityDescriptors();
	}

	
	/**
	 * It process all SyncDescriptor.si.xml file defined in Application, and stores in Resource Manager.
	 */
	protected static void processSyncDescriptors() {
		siminov.connect.Siminov.processSyncDescriptors();
	}

	
	/**
	 * It starts all Service Workers
	 */
	protected static void processServices() {
		siminov.connect.Siminov.processServices();
	}
	
	/**
	 * It process all DatabaseDescriptor.si.xml and initialize Database and stores in Resource Manager.
	 */
	protected static void processDatabase() {
		siminov.connect.Siminov.processDatabase();
	}
	
	
	/**
	 * It process all AdapterDescriptor.si.xml and initialize bridge between native and hybrid
	 */
	protected static void processAdapterDescriptors() {
		
		ApplicationDescriptor applicationDescriptor = hybridResourceManager.getApplicationDescriptor();
		Iterator<String> adapterDescriptorPaths = applicationDescriptor.getAdapterDescriptorPaths();

		while(adapterDescriptorPaths.hasNext()) {
			String adapterDescriptorPath = adapterDescriptorPaths.next();

			AdapterDescriptorReader adapterDescriptorParser = new AdapterDescriptorReader(adapterDescriptorPath);
			applicationDescriptor.addAdapterDescriptor(adapterDescriptorPath, adapterDescriptorParser.getAdapterDescriptor());				
		}
	}
	
	
	/**
	 * It process all Events defined in ApplicationDescriptor.si.xml file in Application, and stores in Resource Manager.
	 */
	protected static void processEvents() {
		
		ApplicationDescriptor applicationDescriptor = hybridResourceManager.getApplicationDescriptor();
		
		Collection<String> siminovEvents = new ArrayList<String>();
		Iterator<String> events = applicationDescriptor.getEvents();

		while(events.hasNext()) {
			String event = events.next();
			
			hybridResourceManager.addEvent(event);
			siminovEvents.add(event);
		}

		events = siminovEvents.iterator();
		while(events.hasNext()) {
			String event = events.next();
			applicationDescriptor.removeEvent(event);
		}

		
		applicationDescriptor.addEvent(SiminovEventHandler.class.getName());
		applicationDescriptor.addEvent(DatabaseEventHandler.class.getName());
		applicationDescriptor.addEvent(SyncEventHandler.class.getName());
		applicationDescriptor.addEvent(NotificationEventHandler.class.getName());
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
		siminov.core.Siminov.isActive = true;

		processServices();
		
		ISiminovEvents coreEventHandler = coreResourceManager.getSiminovEventHandler();
		if(coreResourceManager.getSiminovEventHandler() != null) {
			if(siminov.core.Siminov.firstTimeProcessed) {
				coreEventHandler.onFirstTimeSiminovInitialized();
			} else {
				coreEventHandler.onSiminovInitialized();
			}
		} 
	}
}
