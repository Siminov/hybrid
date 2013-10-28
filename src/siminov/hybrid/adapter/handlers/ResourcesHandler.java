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


package siminov.hybrid.adapter.handlers;

import java.util.Iterator;

import siminov.hybrid.adapter.constants.HybridApplicationDescriptor;
import siminov.hybrid.adapter.constants.HybridLibraryDescriptor;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.parsers.HybridSiminovDataBuilder;
import siminov.orm.exception.SiminovException;
import siminov.orm.model.ApplicationDescriptor;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.LibraryDescriptor;

/**
 * It handles all request related to resources.
 * LIKE: Application Descriptor, Database Descriptor, Library Descriptor, Database Mapping Descriptor, Hybrid Descriptor.
 */
public class ResourcesHandler {

	private static siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
	private static siminov.hybrid.resource.Resources hybridResources = siminov.hybrid.resource.Resources.getInstance(); 

	
	
	/**
	 * Handles Get Application Descriptor Request From Web.
	 * @return Application Descriptor.
	 * @throws SiminovException If any error occur while getting Application Descriptor.
	 */
	public String getApplicationDescriptor() throws SiminovException {
		
		ApplicationDescriptor applicationDescriptor = ormResources.getApplicationDescriptor();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridApplicationDescriptor = new HybridSiminovData();
		
		hybridApplicationDescriptor.setDataType(HybridApplicationDescriptor.APPLICATION_DESCRIPTOR);
		
		/*
		 * Application Name.
		 */
		HybridSiminovValue applicationName = new HybridSiminovValue();
		applicationName.setType(HybridApplicationDescriptor.NAME);
		applicationName.setValue(applicationDescriptor.getName());

		hybridApplicationDescriptor.addValue(applicationName);
		
		/*
		 * Application Description.
		 */
		HybridSiminovValue applicationDescription = new HybridSiminovValue();
		applicationDescription.setType(HybridApplicationDescriptor.DESCRIPTION);
		applicationDescription.setValue(applicationDescriptor.getDescription());
		
		hybridApplicationDescriptor.addValue(applicationDescription);
		
		/*
		 * Application Version.
		 */
		HybridSiminovValue applicationVersion = new HybridSiminovValue();
		applicationVersion.setType(HybridApplicationDescriptor.VERSION);
		applicationVersion.setValue(Double.toString(applicationDescriptor.getVersion()));
		
		hybridApplicationDescriptor.addValue(applicationVersion);
		
		/*
		 * DatabaseDescriptor Paths.
		 */
		HybridSiminovData hybridDatabaseDescriptorPaths = new HybridSiminovData();
		hybridDatabaseDescriptorPaths.setDataType(HybridApplicationDescriptor.DATABASE_DESCRIPTORS);

		Iterator<String> databaseDescriptorPaths = applicationDescriptor.getDatabaseDescriptorPaths();
		while(databaseDescriptorPaths.hasNext()) {
			String databaseDescriptorPath = databaseDescriptorPaths.next();
			
			HybridSiminovValue hybridDatabaseDescriptorPath = new HybridSiminovValue();
			hybridDatabaseDescriptorPath.setType(HybridApplicationDescriptor.DATABASE_DESCRIPTOR_PATH);
			hybridDatabaseDescriptorPath.setValue(databaseDescriptorPath);
			
			hybridDatabaseDescriptorPaths.addValue(hybridDatabaseDescriptorPath);
		}
		
		hybridApplicationDescriptor.addData(hybridDatabaseDescriptorPaths);
		
		/*
		 * Event Notifiers.
		 */
		HybridSiminovData hybridEventNotifiers = new HybridSiminovData();
		hybridEventNotifiers.setDataType(HybridApplicationDescriptor.EVENT_NOTIFIERS);

		Iterator<String> events = hybridResources.getEvents();
		while(events.hasNext()) {
			String event = events.next();
			
			HybridSiminovValue hybridSiminovEventHandler = new HybridSiminovValue();
			hybridSiminovEventHandler.setType(HybridApplicationDescriptor.EVENT_NOTIFIER);
			hybridSiminovEventHandler.setValue(event);
			
			hybridEventNotifiers.addValue(hybridSiminovEventHandler);
			
		}
		
		
		hybridApplicationDescriptor.addData(hybridEventNotifiers);
		
		hybridSiminovDatas.addHybridSiminovData(hybridApplicationDescriptor);

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
	}

	
	/**
	 * Handle Get Database Descriptor Paths Request From Web.
	 * @return Database Descriptor Paths.
	 * @throws SiminovException If any error occur while getting Database Descriptor Paths.
	 */
	public String getDatabaseDescriptorPaths() throws SiminovException {
		
		Iterator<String> databaseDescriptorPaths = ormResources.getDatabaseDescriptorPaths();
		
		HybridSiminovDatas hybridDatabaseDescriptorPaths = new HybridSiminovDatas();
		HybridSiminovData hybridSiminovData = new HybridSiminovData();
		hybridSiminovData.setDataType(HybridApplicationDescriptor.DATABASE_DESCRIPTORS);
		
		while(databaseDescriptorPaths.hasNext()) {

			HybridSiminovValue hybridDatabaseDescriptorPath = new HybridSiminovValue();
			hybridDatabaseDescriptorPath.setValue(databaseDescriptorPaths.next());
			
			hybridSiminovData.addValue(hybridDatabaseDescriptorPath);
		}
		
		hybridDatabaseDescriptorPaths.addHybridSiminovData(hybridSiminovData);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridDatabaseDescriptorPaths);
	}

	
	/**
	 * Handle Get Database Descriptor Based On Database Descriptor Path Request From Web.
	 * @param databaseDescriptorPath Database Descriptor Path.
	 * @return Database Descriptor
	 * @throws SiminovException If any error occur while getting Database Descriptor.
	 */
	public String getDatabaseDescriptorBasedOnPath(final String databaseDescriptorPath) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnPath(databaseDescriptorPath);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Database Descriptor Based On Database Descriptor Name Request From Web.
	 * @param databaseDescriptorName Database Descriptor Name.
	 * @return Database Descriptor.
	 * @throws SiminovException If any error occur while getting Database Descriptor.
	 */
	public String getDatabaseDescriptorBasedOnName(final String databaseDescriptorName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnName(databaseDescriptorName);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Descriptors Request From Web.
	 * @return Database Descriptors.
	 * @throws SiminovException If any error occur while getting Database Descriptors.
	 */
	public String getDatabaseDescriptors() throws SiminovException {
		
		Iterator<DatabaseDescriptor> databaseDescriptors = ormResources.getDatabaseDescriptors();
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		while(databaseDescriptors.hasNext()) {
			DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
			hybridDatabaseDescriptors.addHybridSiminovData(hybridResources.generateHybridDatabaseDescriptor(databaseDescriptor));
		}
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridDatabaseDescriptors);

	}

	
	/**
	 * Handle Get Database Descriptor Based On Web Class Name Request From Web.
	 * @param className Web Model Class Name.
	 * @return Database Descriptor.
	 * @throws SiminovException If any error occur while getting Database Descriptor.
	 */
	public String getDatabaseDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Descriptor Name Based On Web Model Class Name Request From Web.
	 * @param className Web Model Class Name.
	 * @return Database Descriptor Name.
	 * @throws SiminovException If any error occur while getting Database Descriptor.
	 */
	public String getDatabaseDescriptorNameBasedOnClassName(final String className) throws SiminovException {
		
		String databaseDescriptorName = hybridResources.getDatabaseDescriptorNameBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setValue(databaseDescriptorName);
		
		siminovData.addValue(name);
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Descriptor Name Based On Table Name Request From Web.
	 * @param tableName Name of Table.
	 * @return Database Descriptor Name.
	 * @throws SiminovException If any error occur while getting Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnTableName(final String tableName) throws SiminovException {
		
		String databaseDescriptorName = hybridResources.getDatabaseDescriptorNameBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setValue(databaseDescriptorName);
		
		siminovData.addValue(name);
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Database Descriptor Based On Table Name Request From Web.
	 * @param tableName Name of Table.
	 * @return Database Descriptor Name.
	 * @throws SiminovException If any error occur while getting Database Descriptor Name.
	 */
	public String getDatabaseDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Database Mapping Descriptor Based On Web Model Class Name.
	 * @param className Name of Web Model Class.
	 * @return Database Mapping Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getDatabaseMappingDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Mapping Descriptor Based On Table Name Request From Web.
	 * @param tableName Name of Table.
	 * @return Database Mapping Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getDatabaseMappingDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Database Mapping Descriptors Request From Web.
	 * @return Get Database Mapping Descriptors.
	 * @throws SiminovException if any error occur while getting Database Mapping Descriptors.
	 */
	public String getDatabaseMappingDescriptors() throws SiminovException {
		
		Iterator<DatabaseMappingDescriptor> databaseMappingDescriptors = ormResources.getDatabaseMappingDescriptors();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		while(databaseMappingDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptors.next()));
		}
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Descriptors Paths Request From Web.
	 * @return Library Descriptors Paths.
	 * @throws SiminovException If any error occur while getting Library Descriptor Paths.
	 */
	public String getLibraryDescriptorPaths() throws SiminovException {
		
		Iterator<String> libraryDescriptorPaths = ormResources.getLibraryPaths();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridLibraryDescriptorPaths = new HybridSiminovData();
		hybridLibraryDescriptorPaths.setDataType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATHS);
		
		while(libraryDescriptorPaths.hasNext()) {
			
			HybridSiminovValue libraryDescriptorPath = new HybridSiminovValue();
			libraryDescriptorPath.setType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATH);
			libraryDescriptorPath.setValue(libraryDescriptorPaths.next());

			hybridLibraryDescriptorPaths.addValue(libraryDescriptorPath);
			
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridLibraryDescriptorPaths);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Paths Based On Database Descriptor Name Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @return Library Paths
	 * @throws SiminovException If any error occur while getting library paths.
	 */
	public String getLibraryPathsBasedOnDatabaseDescriptorName(final String databaseDescriptorName) throws SiminovException {
	
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnName(databaseDescriptorName); 
		Iterator<String> libraryDescriptorPaths = databaseDescriptor.getLibraryPaths();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridLibraryDescriptorPaths = new HybridSiminovData();
		hybridLibraryDescriptorPaths.setDataType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATHS);
		
		while(libraryDescriptorPaths.hasNext()) {
			
			HybridSiminovValue libraryDescriptorPath = new HybridSiminovValue();
			libraryDescriptorPath.setType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATH);
			libraryDescriptorPath.setValue(libraryDescriptorPaths.next());

			hybridLibraryDescriptorPaths.addValue(libraryDescriptorPath);
			
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridLibraryDescriptorPaths);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	

	/**
	 * Handle Get Library Descriptors Request From Web.
	 * @return Library Descriptors.
	 * @throws SiminovException If any error occur while getting Library Descriptors.
	 */
	public String getLibraryDescriptors() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		Iterator<LibraryDescriptor> libraryDescriptors = ormResources.getLibraries();
		while(libraryDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridLibraryDescriptor(libraryDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Libraries Based On Database Descriptor Name Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @return Libraries.
	 * @throws SiminovException If any occur while getting libraries.
	 */
	public String getLibrariesBasedOnDatabaseDescriptorName(final String databaseDescriptorName) throws SiminovException {
	
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
		Iterator<LibraryDescriptor> libraryDescriptors = databaseDescriptor.getLibraryDescriptors();

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		while(libraryDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridLibraryDescriptor(libraryDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Database Mapping Descriptors Based On Library Descriptor Path Request From Web.
	 * @param libraryDescriptorPath Library Descriptor Path.
	 * @return Library Database Mapping Descriptors.
	 * @throws SiminovException If any error occur while getting Library Database Mapping Descriptors.
	 */
	public String getLibraryDatabaseMappingDescriptorsBasedOnLibraryDescriptorPath(final String libraryDescriptorPath) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		Iterator<DatabaseMappingDescriptor> libraryDatabaseMappingDescriptors = ormResources.getLibraryDatabaseMappingDescriptorsBasedOnLibraryDescriptorPath(libraryDescriptorPath);
		
		while(libraryDatabaseMappingDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDatabaseMappingDescriptor(libraryDatabaseMappingDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Remove Database Based On Database Descriptor Name Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws SiminovException If any error occur while removing Database.
	 */
	public void removeDatabaseBasedOnDatabaseDescriptorName(final String databaseDescriptorName) throws SiminovException {
		
		ormResources.removeDatabaseBundleBasedOnDatabaseDescriptorName(databaseDescriptorName);
		
	}
	
	
	/**
	 * Handle Remove Database Based On Database Mapping Descriptor Class Name.
	 * @param databaseMappingDescriptorClassName Name of Database Mapping Descriptor Class Name.
	 * @throws SiminovException If any error occur while removing Database Mapping Descriptor Class Name.
	 */
	public void removeDatabaseBasedOnDatabaseMappingDescriptorClassName(final String databaseMappingDescriptorClassName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(databaseMappingDescriptorClassName);
		
		ormResources.removeDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		
	}
	
	
	/**
	 * Handle Remove Database Based On Database Mapping Descriptor Table Name Request From Web.
	 * @param databaseMappingDescriptorTableName Name of Table.
	 * @throws SiminovException If any error occur while removing Database.
	 */
	public void removeDatabaseBasedOnDatabaseMappingDescriptorTableName(final String databaseMappingDescriptorTableName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnTableName(databaseMappingDescriptorTableName);
		
		ormResources.removeDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(databaseMappingDescriptor.getTableName());

	}
	
	
	/**
	 * Handle Get Hybrid Descriptor Request From Web.
	 * @return Hybrid Descriptor
	 * @throws SiminovException If any error occur while getting Hybrid Descriptor.
	 */
	public String getHybridDescriptor() throws SiminovException {
	
		HybridDescriptor hybridDescriptor = hybridResources.getHybridDescriptor();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridDescriptor(hybridDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Adapters Request From Web.
	 * @return Adapters.
	 * @throws SiminovException If any error occur while getting Adapters.
	 */
	public String getAdapters() throws SiminovException {
	
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData jsAdapters = new HybridSiminovData();
		jsAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getAdapters();
		while(adapters.hasNext()) {
			jsAdapters.addData(hybridResources.generateHybridAdapter(adapters.next()));
		}
		
		jsSiminovDatas.addHybridSiminovData(jsAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Adapters Request From Web.
	 * @return Library Adapters.
	 * @throws SiminovException If any error occur while getting Library Adapters.
	 */
	public String getLibrariesAdapters() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibrariesAdapters();
		while(adapters.hasNext()) {
			hybridAdapters.addData(hybridResources.generateHybridAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Adapters Based On Paths Request From Web.
	 * @return Adapter Paths
	 * @throws SiminovException If any error occur while getting adapter paths.
	 */
	public String getAdaptersBasedOnPaths() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getAdaptersBasedOnPaths();
		while(adapters.hasNext()) {
			hybridAdapters.addData(hybridResources.generateHybridAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Adapters Based On Library Name Request From Web. 
	 * @param libraryName Name of Library.
	 * @return Library Adapters 
	 * @throws SiminovException If any error occur while getting Library Adapters.
	 */
	public String getLibraryAdaptersBasedOnName(final String libraryName)  throws SiminovException {
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibraryAdaptersBasedOnName(libraryName);
		while(adapters.hasNext()) {
			hybridAdapters.addData(hybridResources.generateHybridAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Library Adapters Based On Library Path Request From Web.
	 * @param libraryPath Library Path
	 * @return Library Adapters.
	 * @throws SiminovException If any error occur while getting Library Adapters.
	 */
	public String getLibraryAdaptersBasedOnPath(final String libraryPath)  throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibraryAdaptersBasedOnPath(libraryPath);
		while(adapters.hasNext()) {
			hybridAdapters.addData(hybridResources.generateHybridAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Adapter Based On Name Request From Web. 
	 * @param adapterName Name of Adapter.
	 * @return Adapter.
	 * @throws SiminovException If any error occur while getting Adapter.
	 */
	public String getAdapterBasedOnName(final String adapterName) throws SiminovException {

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		jsSiminovDatas.addHybridSiminovData(hybridResources.generateHybridAdapter(hybridResources.getAdapterBasedOnName(adapterName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Adapter Based On Path Request From Web.
	 * @param adapterPath Adapter Path
	 * @return Adapter.
	 * @throws SiminovException If any error occur while getting Adapter.
	 */
	public String getAdapterBasedOnPath(final String adapterPath) throws SiminovException {

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		jsSiminovDatas.addHybridSiminovData(hybridResources.generateHybridAdapter(hybridResources.getAdapterBasedOnPath(adapterPath)));
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}

	
	/**
	 * Handle Get Library Adapter Based On Name Request From Web.
	 * @param libraryName Name of Library.
	 * @param adapterName Name of Adapter.
	 * @return Library Adapter.
	 * @throws SiminovException If any error occur while getting Library Adapter.
	 */
	public String getLibraryAdapterBasedOnName(final String libraryName, final String adapterName) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridAdapter(hybridResources.getLibraryAdapterBasedOnName(libraryName, adapterName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Library Adapter Based On Path Request From Web.
	 * @param libraryPath Library Path
	 * @param adapterPath Adapter Path
	 * @return Library Adapter.
	 * @throws SiminovException If any error occur while getting Library Adapter.
	 */
	public String getLibraryAdapterBasedOnPath(final String libraryPath, final String adapterPath) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridAdapter(hybridResources.getLibraryAdapterBasedOnPath(libraryPath, adapterPath)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Check Adapter Based On Adapter Name Request From Web.
	 * @param adapterName Name of Adapter.
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Adapter exist or not.
	 */
	public boolean containAdapterBasedOnName(final String adapterName) throws SiminovException {
		return hybridResources.containAdapterBasedOnName(adapterName);
	}

	
	/**
	 * Handle Check Adapter Based On Path Request From Web.
	 * @param adapterPath Adapter Path
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Adapter exist or not.
	 */
	public boolean containAdapterBasedOnPath(final String adapterPath) throws SiminovException {
		return hybridResources.containAdapterBasedOnPath(adapterPath);
	}

	
	/**
	 * Handle Check Adapter Based On Library Name Request From Web.
	 * @param libraryName Name of Library.
	 * @param adapterName Name of Adapter.
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Adapter exist or not.
	 */
	public boolean containAdapterBasedOnLibraryName(final String libraryName, final String adapterName) throws SiminovException {
		return hybridResources.containAdapterBasedOnLibraryName(libraryName, adapterName);
	}

	
	/**
	 * Handle Check Adapter Based On Library Path Request From Web.
	 * @param libraryPath Library Path
	 * @param adapterPath Adapter Path
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Adapter exist or not.
	 */
	public boolean containAdapterBasedOnLibraryPath(final String libraryPath, final String adapterPath) throws SiminovException {
		return hybridResources.containAdapterBasedOnLibraryPath(libraryPath, adapterPath);
	}

	
	/**
	 * Handle Check Library Based On Library Name Request From Web.
	 * @param libraryName Name of Library.
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Library exist or not.
	 */
	public boolean containLibraryBasedOnName(final String libraryName) throws SiminovException {
		return hybridResources.containLibraryBasedOnName(libraryName);
	}

	
	/**
	 * Handle Check Library Based On Library Path Request From Web.
	 * @param libraryPath Library Path.
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Library exist or not.
	 */
	public boolean containLibraryBasedOnPath(final String libraryPath) throws SiminovException {
		return hybridResources.containLibraryBasedOnPath(libraryPath);
	}

	
	/**
	 * Handle Get Handlers Request From Web.
	 * @return Handlers
	 * @throws SiminovException If any error occur while getting Handlers.
	 */
	public String getHandlers() throws SiminovException {
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridHandlers = new HybridSiminovData();
		hybridHandlers.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLERS);
		
		Iterator<Handler> handlers = hybridResources.getHandlers();
		while(handlers.hasNext()) {
			hybridHandlers.addData(hybridResources.generateHybridHandler(handlers.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridHandlers);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Get Handler Based On Adapter Name And Handler Name Request From Web.
	 * @param adapterName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return Handler
	 * @throws SiminovException If any error occur while getting Handler.
	 */
	public String getHandler(final String adapterName, final String handlerName) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridHandler(hybridResources.getHandler(adapterName, handlerName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	/**
	 * Handle Check Handler Based On Adapter Name And Handler Name Request From Web.
	 * @param adapterName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return true/false
	 * @throws SiminovException If any error occur while checking Handler exist or not.
	 */
	public boolean containHander(final String adapterName, final String handlerName) throws SiminovException {
		return hybridResources.containHandler(adapterName, handlerName);
	}
	
	
	/**
	 * Handle Check Handler Based On Handler Name Request From Web.
	 * @param handlerName Name of Handler.
	 * @return Handler.
	 * @throws SiminovException If any error occur while getting Handler.
	 */
	public boolean containHandler(final String handlerName) throws SiminovException {
		return hybridResources.containHandler(handlerName);
	}
	
}

