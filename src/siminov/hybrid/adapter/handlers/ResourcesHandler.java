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



package siminov.hybrid.adapter.handlers;

import java.util.Iterator;

import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.constants.HybridApplicationDescriptor;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.model.ApplicationDescriptor;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;

/**
 * It handles all request related to resources.
 * LIKE: Application Descriptor, Database Descriptor, Library Descriptor, Database Mapping Descriptor, Hybrid Descriptor.
 */
public class ResourcesHandler implements IAdapter {

	private static siminov.orm.resource.ResourceManager ormResourceManager = siminov.orm.resource.ResourceManager.getInstance();
	private static siminov.hybrid.resource.ResourceManager hybridResourceManager = siminov.hybrid.resource.ResourceManager.getInstance(); 

	
	
	/**
	 * Handles Get Application Descriptor Request From Web.
	 * @return Application Descriptor.
	 * @throws SiminovException If any error occur while getting Application Descriptor.
	 */
	public String getApplicationDescriptor() throws SiminovException {
		
		ApplicationDescriptor applicationDescriptor = ormResourceManager.getApplicationDescriptor();
		
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

		Iterator<String> events = hybridResourceManager.getEvents();
		while(events.hasNext()) {
			String event = events.next();
			
			HybridSiminovValue hybridSiminovEventHandler = new HybridSiminovValue();
			hybridSiminovEventHandler.setType(HybridApplicationDescriptor.EVENT_NOTIFIER);
			hybridSiminovEventHandler.setValue(event);
			
			hybridEventNotifiers.addValue(hybridSiminovEventHandler);
			
		}
		
		
		hybridApplicationDescriptor.addData(hybridEventNotifiers);
		
		hybridSiminovDatas.addHybridSiminovData(hybridApplicationDescriptor);

		return HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
	}

	
	public String getDatabaseDescriptor(final String databaseDescriptorName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = ormResourceManager.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		hybridDatabaseDescriptors.addHybridSiminovData(hybridDatabaseDescriptor);
		
		return HybridSiminovDataWritter.jsonBuidler(hybridDatabaseDescriptors);
	}
	
	/**
	 * Handle Get Database Mapping Descriptor Based On Web Model Class Name.
	 * @param className Name of Web Model Class.
	 * @return Database Mapping Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getDatabaseMappingDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResourceManager.getDatabaseMappingDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Mapping Descriptor Based On Table Name Request From Web.
	 * @param tableName Name of Table.
	 * @return Database Mapping Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getDatabaseMappingDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResourceManager.getDatabaseMappingDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor));
		
		return HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		
	}
}
