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

package siminov.hybrid.adapter.handlers;

import java.util.Iterator;

import siminov.hybrid.adapter.constants.HybridApplicationDescriptor;
import siminov.hybrid.adapter.constants.HybridDatabaseDescriptor;
import siminov.hybrid.adapter.constants.HybridDatabaseMappingDescriptor;
import siminov.hybrid.adapter.constants.HybridLibraryDescriptor;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler.Parameter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler.Return;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.parsers.HybridSiminovDataBuilder;
import siminov.orm.exception.SiminovException;
import siminov.orm.model.ApplicationDescriptor;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor.Column;
import siminov.orm.model.DatabaseMappingDescriptor.Index;
import siminov.orm.model.DatabaseMappingDescriptor.Relationship;
import siminov.orm.model.LibraryDescriptor;

public class ResourcesHandler {

	private static siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
	private static siminov.hybrid.resource.Resources hybridResources = siminov.hybrid.resource.Resources.getInstance(); 

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

	public String getDatabaseDescriptorBasedOnPath(final String databaseDescriptorPath) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnPath(databaseDescriptorPath);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getDatabaseDescriptorBasedOnName(final String databaseDescriptorName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnName(databaseDescriptorName);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	public String getDatabaseDescriptors() throws SiminovException {
		
		Iterator<DatabaseDescriptor> databaseDescriptors = ormResources.getDatabaseDescriptors();
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		while(databaseDescriptors.hasNext()) {
			DatabaseDescriptor databaseDescriptor = databaseDescriptors.next();
			hybridDatabaseDescriptors.addHybridSiminovData(generateHybridDatabaseDescriptor(databaseDescriptor));
		}
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridDatabaseDescriptors);

	}

	public String getDatabaseDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

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
	
	public String getDatabaseDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseDescriptor(databaseDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getDatabaseMappingDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	public String getDatabaseMappingDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getDatabaseMappingDescriptors() throws SiminovException {
		
		Iterator<DatabaseMappingDescriptor> databaseMappingDescriptors = ormResources.getDatabaseMappingDescriptors();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		while(databaseMappingDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseMappingDescriptor(databaseMappingDescriptors.next()));
		}
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
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
	

	public String getLibraryDescriptors() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		Iterator<LibraryDescriptor> libraryDescriptors = ormResources.getLibraries();
		while(libraryDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(generateLibraryDescriptor(libraryDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getLibrariesBasedOnDatabaseDescriptorName(final String databaseDescriptorName) throws SiminovException {
	
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
		Iterator<LibraryDescriptor> libraryDescriptors = databaseDescriptor.getLibraryDescriptors();

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		while(libraryDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(generateLibraryDescriptor(libraryDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	public String getLibraryDatabaseMappingDescriptorsBasedOnLibraryDescriptorPath(final String libraryDescriptorPath) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		Iterator<DatabaseMappingDescriptor> libraryDatabaseMappingDescriptors = ormResources.getLibraryDatabaseMappingDescriptorsBasedOnLibraryDescriptorPath(libraryDescriptorPath);
		
		while(libraryDatabaseMappingDescriptors.hasNext()) {
			hybridSiminovDatas.addHybridSiminovData(generateHybridDatabaseMappingDescriptor(libraryDatabaseMappingDescriptors.next()));
		}

		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public void removeDatabaseBasedOnDatabaseDescriptorName(final String databaseDescriptorName) throws SiminovException {
		
		ormResources.removeDatabaseBundleBasedOnDatabaseDescriptorName(databaseDescriptorName);
		
	}
	
	public void removeDatabaseBasedOnDatabaseMappingDescriptorClassName(final String databaseMappingDescriptorClassName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(databaseMappingDescriptorClassName);
		
		ormResources.removeDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		
	}
	
	public void removeDatabaseBasedOnDatabaseMappingDescriptorTableName(final String databaseMappingDescriptorTableName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnTableName(databaseMappingDescriptorTableName);
		
		ormResources.removeDatabaseBundleBasedOnDatabaseMappingDescriptorTableName(databaseMappingDescriptor.getTableName());

	}
	
	
	public String getHybridDescriptor() throws SiminovException {
	
		HybridDescriptor hybridDescriptor = hybridResources.getHybridDescriptor();
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(getHybridDescriptor(hybridDescriptor));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	public String getAdapters() throws SiminovException {
	
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData jsAdapters = new HybridSiminovData();
		jsAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getAdapters();
		while(adapters.hasNext()) {
			jsAdapters.addData(getAdapter(adapters.next()));
		}
		
		jsSiminovDatas.addHybridSiminovData(jsAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}
	
	
	public String getLibrariesAdapters() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibrariesAdapters();
		while(adapters.hasNext()) {
			hybridAdapters.addData(getAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getAdaptersBasedOnPaths() throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getAdaptersBasedOnPaths();
		while(adapters.hasNext()) {
			hybridAdapters.addData(getAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	
	public String getLibraryAdaptersBasedOnName(final String libraryName)  throws SiminovException {
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibraryAdaptersBasedOnName(libraryName);
		while(adapters.hasNext()) {
			hybridAdapters.addData(getAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getLibraryAdaptersBasedOnPath(final String libraryPath)  throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridResources.getLibraryAdaptersBasedOnPath(libraryPath);
		while(adapters.hasNext()) {
			hybridAdapters.addData(getAdapter(adapters.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridAdapters);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getAdapter(final String adapterName) throws SiminovException {

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		jsSiminovDatas.addHybridSiminovData(getAdapter(hybridResources.getAdapter(adapterName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}

	
	public String getAdapterBasedOnName(final String adapterName) throws SiminovException {

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		jsSiminovDatas.addHybridSiminovData(getAdapter(hybridResources.getAdapterBasedOnName(adapterName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}
	
	public String getAdapterBasedOnPath(final String adapterPath) throws SiminovException {

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		jsSiminovDatas.addHybridSiminovData(getAdapter(hybridResources.getAdapterBasedOnPath(adapterPath)));
		
		return HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		
	}

	
	public String getLibraryAdapterBasedOnName(final String libraryName, final String adapterName) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(getAdapter(hybridResources.getLibraryAdapterBasedOnName(libraryName, adapterName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	public String getLibraryAdapterBasedOnPath(final String libraryPath, final String adapterPath) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(getAdapter(hybridResources.getLibraryAdapterBasedOnPath(libraryPath, adapterPath)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}

	
	public boolean containAdapterBasedOnName(final String adapterName) throws SiminovException {
		return hybridResources.containAdapterBasedOnName(adapterName);
	}

	
	public boolean containAdapterBasedOnPath(final String adapterPath) throws SiminovException {
		return hybridResources.containAdapterBasedOnPath(adapterPath);
	}

	
	public boolean containAdapterBasedOnLibraryName(final String libraryName, final String adapterName) throws SiminovException {
		return hybridResources.containAdapterBasedOnLibraryName(libraryName, adapterName);
	}

	public boolean containAdapterBasedOnLibraryPath(final String libraryPath, final String adapterPath) throws SiminovException {
		return hybridResources.containAdapterBasedOnLibraryPath(libraryPath, adapterPath);
	}

	public boolean containLibraryBasedOnName(final String libraryName) throws SiminovException {
		return hybridResources.containLibraryBasedOnName(libraryName);
	}

	public boolean containLibraryBasedOnPath(final String libraryPath) throws SiminovException {
		return hybridResources.containLibraryBasedOnPath(libraryPath);
	}

	
	public String getHandlers() throws SiminovException {
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData hybridHandlers = new HybridSiminovData();
		hybridHandlers.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLERS);
		
		Iterator<Handler> handlers = hybridResources.getHandlers();
		while(handlers.hasNext()) {
			hybridHandlers.addData(getHandler(handlers.next()));
		}
		
		hybridSiminovDatas.addHybridSiminovData(hybridHandlers);
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public String getHandler(final String adapterName, final String handlerName) throws SiminovException {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(getHandler(hybridResources.getHandler(adapterName, handlerName)));
		
		return HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		
	}
	
	public boolean containHander(final String adapterName, final String handlerName) throws SiminovException {
		return hybridResources.containHandler(adapterName, handlerName);
	}
	
	public boolean containHandler(final String handlerName) throws SiminovException {
		return hybridResources.containHandler(handlerName);
	}
	
	public HybridSiminovData generateHybridDatabaseDescriptor(DatabaseDescriptor databaseDescriptor) {
		
		HybridSiminovData hybridDatabaseDescriptor = new HybridSiminovData();
		hybridDatabaseDescriptor.setDataType(HybridDatabaseDescriptor.DATABASE_DESCRIPTOR);
		
		HybridSiminovValue databaseName = new HybridSiminovValue();
		databaseName.setType(HybridDatabaseDescriptor.NAME);
		databaseName.setValue(databaseDescriptor.getDatabaseName());

		hybridDatabaseDescriptor.addValue(databaseName);
		
		HybridSiminovValue databaseDescription = new HybridSiminovValue();
		databaseDescription.setType(HybridDatabaseDescriptor.DESCRIPTION);
		databaseDescription.setValue(databaseDescriptor.getDescription());
		
		hybridDatabaseDescriptor.addValue(databaseDescription);
		
		HybridSiminovValue isLockingRequired = new HybridSiminovValue();
		isLockingRequired.setType(HybridDatabaseDescriptor.IS_LOCKING_REQUIRED);
		isLockingRequired.setValue(Boolean.toString(databaseDescriptor.isLockingRequired()));
		
		hybridDatabaseDescriptor.addValue(isLockingRequired);
		
		HybridSiminovValue externalStorage = new HybridSiminovValue();
		externalStorage.setType(HybridDatabaseDescriptor.EXTERNAL_STORAGE);
		externalStorage.setValue(Boolean.toString(databaseDescriptor.isExternalStorageEnable()));
		
		hybridDatabaseDescriptor.addValue(externalStorage);
		
		HybridSiminovData jsDatabaseMappingDescriptorPaths = new HybridSiminovData();
		jsDatabaseMappingDescriptorPaths.setDataType(HybridDatabaseDescriptor.DATABASE_MAPPING_DESCRIPTORS);
		
		Iterator<String> databaseMappingDescriptors = databaseDescriptor.getDatabaseMappingPaths();
		while(databaseMappingDescriptors.hasNext()) {
			
			HybridSiminovValue databaseMappingDescriptorPath = new HybridSiminovValue();
			databaseMappingDescriptorPath.setType(HybridDatabaseDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATH);
			databaseMappingDescriptorPath.setValue(databaseMappingDescriptors.next());
			
			jsDatabaseMappingDescriptorPaths.addValue(databaseMappingDescriptorPath);
			
		}
		
		hybridDatabaseDescriptor.addData(jsDatabaseMappingDescriptorPaths);
		
		HybridSiminovData hybridLibraries = new HybridSiminovData();
		hybridLibraries.setDataType(HybridDatabaseDescriptor.LIBRARIES);
		
		Iterator<String> libraries = databaseDescriptor.getLibraryPaths();
		while(libraries.hasNext()) {
			
			HybridSiminovValue library = new HybridSiminovValue();
			library.setType(HybridDatabaseDescriptor.LIBRARY_PATH);
			library.setValue(libraries.next());
			
			hybridLibraries.addValue(library);
		}
		
		hybridDatabaseDescriptor.addData(hybridLibraries);
		
		return hybridDatabaseDescriptor;
	}

	public HybridSiminovData generateHybridDatabaseMappingDescriptor(final DatabaseMappingDescriptor databaseMappingDescriptor) {
		
		HybridSiminovData hybridDatabaseMappingDescriptor = new HybridSiminovData();
		hybridDatabaseMappingDescriptor.setDataType(HybridDatabaseMappingDescriptor.DATABASE_MAPPING_DESCRIPTOR);
		
		
		HybridSiminovValue tableName = new HybridSiminovValue();
		tableName.setType(HybridDatabaseMappingDescriptor.TABLE_NAME);
		tableName.setValue(databaseMappingDescriptor.getTableName());
		
		hybridDatabaseMappingDescriptor.addValue(tableName);
		
		
		HybridSiminovValue className = new HybridSiminovValue();
		className.setType(HybridDatabaseMappingDescriptor.CLASS_NAME);
		className.setValue(databaseMappingDescriptor.getClassName());
		
		hybridDatabaseMappingDescriptor.addValue(className);
		
		
		HybridSiminovData hybridColumns = new HybridSiminovData();
		hybridColumns.setDataType(HybridDatabaseMappingDescriptor.COLUMNS);
		
		Iterator<Column> columns = databaseMappingDescriptor.getColumns();
		while(columns.hasNext()) {
			
			Column column = columns.next();
			HybridSiminovData hybridColumn = generateHybridDatabaseMappingDescriptorColumn(column);
			hybridColumns.addData(hybridColumn);
			
		}

		
		hybridDatabaseMappingDescriptor.addData(hybridColumns);
		
		
		HybridSiminovData hybridIndexs = new HybridSiminovData();
		hybridIndexs.setDataType(HybridDatabaseMappingDescriptor.INDEXS);
		
		Iterator<Index> indexs = databaseMappingDescriptor.getIndexes();
		while(indexs.hasNext()) {
			
			Index index = indexs.next();
			HybridSiminovData jsIndex = generateHybridDatabaseMappingDescriptorIndex(index);
			hybridIndexs.addData(jsIndex);
		}
			
		hybridDatabaseMappingDescriptor.addData(hybridIndexs);
		
		
		HybridSiminovData hybridRelationships = new HybridSiminovData();
		hybridRelationships.setDataType(HybridDatabaseMappingDescriptor.RELATIONSHIPS);
		
		Iterator<Relationship> relationships = databaseMappingDescriptor.getRelationships();
		while(relationships.hasNext()) {
			Relationship relationship = relationships.next();
			
			HybridSiminovData hybridRelationship = generateHybridDatabaseMappingDescriptorRelationship(relationship);
			hybridRelationships.addData(hybridRelationship);
		}

		hybridDatabaseMappingDescriptor.addData(hybridRelationships);
			
		return hybridDatabaseMappingDescriptor;
	}

	
	public HybridSiminovData generateHybridDatabaseMappingDescriptorColumn(final Column column) {
		
		HybridSiminovData hybridColumn = new HybridSiminovData();
		hybridColumn.setDataType(HybridDatabaseMappingDescriptor.COLUMN);
		
		HybridSiminovValue variableName = new HybridSiminovValue();
		variableName.setType(HybridDatabaseMappingDescriptor.VARIABLE_NAME);
		variableName.setValue(column.getVariableName());
		
		hybridColumn.addValue(variableName);
		
		
		HybridSiminovValue columnName = new HybridSiminovValue();
		columnName.setType(HybridDatabaseMappingDescriptor.COLUMN_NAME);
		columnName.setValue(column.getColumnName());
		
		hybridColumn.addValue(columnName);
		
		
		HybridSiminovValue type = new HybridSiminovValue();
		type.setType(HybridDatabaseMappingDescriptor.TYPE);
		type.setValue(column.getType());
		
		hybridColumn.addValue(type);
		
		
		HybridSiminovValue primaryKey = new HybridSiminovValue();
		primaryKey.setType(HybridDatabaseMappingDescriptor.PRIMARY_KEY);
		primaryKey.setValue(Boolean.toString(column.isPrimaryKey()));
		
		hybridColumn.addValue(primaryKey);
		
		
		HybridSiminovValue notNull = new HybridSiminovValue();
		notNull.setType(HybridDatabaseMappingDescriptor.NOT_NULL);
		notNull.setValue(Boolean.toString(column.isNotNull()));
		
		hybridColumn.addValue(notNull);
		
		
		HybridSiminovValue unique = new HybridSiminovValue();
		unique.setType(HybridDatabaseMappingDescriptor.UNIQUE);
		unique.setValue(Boolean.toString(column.isUnique()));
		
		hybridColumn.addValue(unique);
		
		
		HybridSiminovValue check = new HybridSiminovValue();
		check.setType(HybridDatabaseMappingDescriptor.CHECK);
		check.setValue(column.getCheck());
		
		hybridColumn.addValue(check);
		
		
		HybridSiminovValue defaultValue = new HybridSiminovValue();
		defaultValue.setType(HybridDatabaseMappingDescriptor.DEFAULT);
		defaultValue.setValue(column.getDefaultValue());
		
		hybridColumn.addValue(defaultValue);

		return hybridColumn;

	}
	
	public HybridSiminovData generateHybridDatabaseMappingDescriptorIndex(final Index index) {
		
		HybridSiminovData hybridIndex = new HybridSiminovData();
		hybridIndex.setDataType(HybridDatabaseMappingDescriptor.INDEX);
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setType(HybridDatabaseMappingDescriptor.INDEX_NAME);
		name.setValue(index.getName());
		
		hybridIndex.addValue(name);
		
		
		HybridSiminovValue unique = new HybridSiminovValue();
		unique.setType(HybridDatabaseMappingDescriptor.INDEX_UNIQUE);
		unique.setValue(Boolean.toString(index.isUnique()));
		
		hybridIndex.addValue(unique);
		
		HybridSiminovData hybridIndexColumns = new HybridSiminovData();
		
		Iterator<String> columnNames = index.getColumns();
		while(columnNames.hasNext()) {
			HybridSiminovValue column = new HybridSiminovValue();
			column.setType(HybridDatabaseMappingDescriptor.INDEX_COLUMN);
			column.setValue(columnNames.next());

			hybridIndexColumns.addValue(column);
			
		}

		return hybridIndex;
		
	}
	
	
	public HybridSiminovData generateHybridDatabaseMappingDescriptorRelationship(final Relationship relationship) {
		
		HybridSiminovData hybridRelationship = new HybridSiminovData();
		hybridRelationship.setDataType(HybridDatabaseMappingDescriptor.RELATIONSHIP);
		
		HybridSiminovValue relationshipType = new HybridSiminovValue();
		relationshipType.setType(HybridDatabaseMappingDescriptor.RELATIONSHIP_TYPE);
		relationshipType.setValue(relationship.getRelationshipType());
		
		hybridRelationship.addValue(relationshipType);
		
		
		HybridSiminovValue refer = new HybridSiminovValue();
		refer.setType(HybridDatabaseMappingDescriptor.REFER);
		refer.setValue(relationship.getRefer());
		
		hybridRelationship.addValue(refer);
		
		
		HybridSiminovValue referTo = new HybridSiminovValue();
		referTo.setType(HybridDatabaseMappingDescriptor.REFER_TO);
		referTo.setValue(relationship.getReferTo());
		
		hybridRelationship.addValue(referTo);
		
		
		HybridSiminovValue onUpdate = new HybridSiminovValue();
		onUpdate.setType(HybridDatabaseMappingDescriptor.ON_UPDATE);
		onUpdate.setValue(relationship.getOnUpdate());
		
		hybridRelationship.addValue(onUpdate);
		
		
		HybridSiminovValue  onDelete = new HybridSiminovValue();
		onDelete.setType(HybridDatabaseMappingDescriptor.ON_DELETE);
		onDelete.setValue(relationship.getOnDelete());
		
		hybridRelationship.addValue(onDelete);
		
		
		HybridSiminovValue load = new HybridSiminovValue();
		load.setType(HybridDatabaseMappingDescriptor.LOAD);
		load.setValue(Boolean.toString(relationship.isLoad()));
		
		hybridRelationship.addValue(load);

		return hybridRelationship;
		
	}

	public HybridSiminovData generateLibraryDescriptor(final LibraryDescriptor libraryDescriptor) {
		
		HybridSiminovData hybridLibraryDescriptor = new HybridSiminovData();
		hybridLibraryDescriptor.setDataType(HybridLibraryDescriptor.LIBRARY_DESCRIPTOR);
		
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setType(HybridLibraryDescriptor.NAME);
		name.setValue(libraryDescriptor.getName());
		
		hybridLibraryDescriptor.addValue(name);
		
		
		HybridSiminovValue description = new HybridSiminovValue();
		description.setType(HybridLibraryDescriptor.DESCRIPTION);
		description.setValue(libraryDescriptor.getDescription());
		
		
		HybridSiminovData hybridDatabaseMappingDescriptorPaths = new HybridSiminovData();
		hybridDatabaseMappingDescriptorPaths.setDataType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATHS);
		
		Iterator<String> databaseMappingDescriptorPaths = libraryDescriptor.getDatabaseMappingPaths();
		while(databaseMappingDescriptorPaths.hasNext()) {
			
			HybridSiminovValue hybridDatabaseMappingDescriptorPath = new HybridSiminovValue();
			hybridDatabaseMappingDescriptorPath.setType(HybridLibraryDescriptor.DATABASE_MAPPING_DESCRIPTOR_PATH);
			
			hybridDatabaseMappingDescriptorPaths.addValue(hybridDatabaseMappingDescriptorPath);
		}
		
		hybridLibraryDescriptor.addData(hybridDatabaseMappingDescriptorPaths);
		
		return hybridLibraryDescriptor;
		
	}


	private HybridSiminovData getHybridDescriptor(final HybridDescriptor hybridDescriptor) {
		
		HybridSiminovData hybridWebDescriptor = new HybridSiminovData();
		hybridWebDescriptor.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.JS_DESCRIPTOR);
		
		
		HybridSiminovData hybridAdapters = new HybridSiminovData();
		hybridAdapters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTERS);
		
		Iterator<Adapter> adapters = hybridDescriptor.getAdapters();
		while(adapters.hasNext()) {
			hybridAdapters.addData(getAdapter(adapters.next()));
		}

		HybridSiminovData hybridAdapterPaths = new HybridSiminovData();
		hybridAdapterPaths.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_PATHS);
		
		Iterator<String> adapterPaths = hybridDescriptor.getAdapterPaths();
		while(adapterPaths.hasNext()) {
			
			HybridSiminovValue hybridAdapterPath = new HybridSiminovValue();
			hybridAdapterPath.setType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_PATH);
			hybridAdapterPath.setValue(adapterPaths.next());
			
		}
		
		hybridWebDescriptor.addData(hybridAdapterPaths);
		
		
		HybridSiminovData hybridLibraries = new HybridSiminovData();
		hybridLibraries.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.LIBRARIES);
		
		Iterator<String> libraries = hybridDescriptor.getLibraryPaths();
		while(libraries.hasNext()) {
			
			HybridSiminovValue jsLibrary = new HybridSiminovValue();
			jsLibrary.setType(siminov.hybrid.adapter.constants.HybridDescriptor.LIBRARY);
			jsLibrary.setValue(libraries.next());
			
			hybridLibraries.addValue(jsLibrary);
			
		}
		
		hybridWebDescriptor.addData(hybridLibraries);
		
		hybridWebDescriptor.addData(hybridAdapters);

		return hybridWebDescriptor;
		
	}
	
	public HybridSiminovData getAdapter(final Adapter adapter) {
		
		HybridSiminovData hybridAdapter = new HybridSiminovData();
		hybridAdapter.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER);
		
		
		HybridSiminovValue adapterName = new HybridSiminovValue();
		adapterName.setType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_NAME);
		adapterName.setValue(adapter.getName());
		
		hybridAdapter.addValue(adapterName);
		

		HybridSiminovValue adapterDescription = new HybridSiminovValue();
		adapterDescription.setType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_DESCRIPTION);
		adapterDescription.setValue(adapter.getDescription());
		
		hybridAdapter.addValue(adapterDescription);
		
		
		HybridSiminovValue adapterMapTo = new HybridSiminovValue();
		adapterMapTo.setType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_MAP_TO);
		adapterMapTo.setValue(adapter.getMapTo());
		
		hybridAdapter.addValue(adapterMapTo);
		
		
		HybridSiminovValue cache = new HybridSiminovValue();
		cache.setType(siminov.hybrid.adapter.constants.HybridDescriptor.ADAPTER_CACHE);
		cache.setValue(Boolean.toString(adapter.isCache()));
		
		hybridAdapter.addValue(cache);
		
		
		HybridSiminovData hybridHandlers = new HybridSiminovData();
		hybridHandlers.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLERS);
		
		Iterator<Handler> handlers = adapter.getHandlers();
		while(handlers.hasNext()) {
			hybridHandlers.addData(getHandler(handlers.next()));
		}
		

		hybridAdapter.addData(hybridHandlers);
		
		return hybridAdapter;
	}

	public HybridSiminovData getHandler(final Handler handler) {
		
		HybridSiminovData hybridHandler = new HybridSiminovData();
		hybridHandler.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER);
		
		
		HybridSiminovValue handlerName = new HybridSiminovValue();
		handlerName.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_NAME);
		handlerName.setValue(handler.getName());
		
		hybridHandler.addValue(handlerName);
		
		
		HybridSiminovValue handlerType = new HybridSiminovValue();
		handlerType.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_TYPE);
		handlerType.setValue(handler.getType());
		
		hybridHandler.addValue(handlerType);
		
		
		HybridSiminovValue handlerCallbackId = new HybridSiminovValue();
		handlerCallbackId.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_CALLBACK_ID);
		handlerCallbackId.setValue(handler.getCallbackId());
		
		hybridHandler.addValue(handlerCallbackId);
		
		
		HybridSiminovValue handlerMapTo = new HybridSiminovValue();
		handlerMapTo.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_MAP_TO);
		handlerMapTo.setValue(handler.getMapTo());
		
		hybridHandler.addValue(handlerMapTo);
		
		
		HybridSiminovValue handlerDescription = new HybridSiminovValue();
		handlerDescription.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_DESCRIPTION);
		handlerDescription.setValue(handler.getDescription());
		
		hybridHandler.addValue(handlerDescription);
		
		
		HybridSiminovData hybridHandlerParameters = new HybridSiminovData();
		hybridHandlerParameters.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_PARAMETERS);
		
		Iterator<Parameter> parameters = handler.getParameters();
		while(parameters.hasNext())  {
			
			Parameter parameter = parameters.next();
			
			HybridSiminovData hybridHandlerParameter = new HybridSiminovData();
			hybridHandlerParameter.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_PARAMETER);
			
			
			HybridSiminovValue parameterType = new HybridSiminovValue();
			parameterType.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_PARAMETER_TYPE);
			parameterType.setValue(parameter.getType());
			
			hybridHandlerParameter.addValue(parameterType);
			
			
			HybridSiminovValue parameterDescription = new HybridSiminovValue();
			parameterDescription.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_PARAMETER_DESCRIPTION);
			parameterDescription.setValue(parameter.getDescription());
			
			hybridHandlerParameter.addValue(parameterDescription);
			
			hybridHandlerParameters.addData(hybridHandlerParameter);
			
		}
		
		hybridHandler.addData(hybridHandlerParameters);

		Return returnType = handler.getReturn();
		if(returnType != null) {

			HybridSiminovData hybridReturn = new HybridSiminovData();
			hybridReturn.setDataType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_RETURN);
			
			
			HybridSiminovValue hybridReturnType = new HybridSiminovValue();
			hybridReturnType.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_RETURN_TYPE);
			hybridReturnType.setValue(returnType.getType());
			
			hybridReturn.addValue(hybridReturnType);
					
			
			HybridSiminovValue hybridReturnDescription = new HybridSiminovValue();
			hybridReturnDescription.setType(siminov.hybrid.adapter.constants.HybridDescriptor.HANDLER_RETURN_DESCRIPTION);
			hybridReturnDescription.setValue(returnType.getDescription());
			
			hybridReturn.addValue(hybridReturnDescription);
			
			hybridHandler.addData(hybridReturn);
		}

		return hybridHandler;
	}
	
}

