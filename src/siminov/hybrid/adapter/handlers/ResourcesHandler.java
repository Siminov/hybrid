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

import siminov.core.exception.SiminovException;
import siminov.core.model.ApplicationDescriptor;
import siminov.core.model.DatabaseDescriptor;
import siminov.core.model.EntityDescriptor;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.constants.HybridApplicationDescriptor;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.writter.HybridSiminovDataWritter;

/**
 * It handles all request related to resources.
 * LIKE: Application Descriptor, Database Descriptor, Library Descriptor, Entity Descriptor, Hybrid Descriptor.
 */
public class ResourcesHandler implements IAdapter {

	private static siminov.core.resource.ResourceManager coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
	private static siminov.hybrid.resource.ResourceManager hybridResourceManager = siminov.hybrid.resource.ResourceManager.getInstance(); 

	
	
	/**
	 * Handles Get Application Descriptor Request From Hybrid.
	 * @return Application Descriptor.
	 * @throws SiminovException If any error occur while getting Application Descriptor.
	 */
	public String getApplicationDescriptor() throws SiminovException {
		
		ApplicationDescriptor applicationDescriptor = coreResourceManager.getApplicationDescriptor();
		
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

	
	/**
	 * It handles database descriptor request from hybrid
	 * @param databaseDescriptorName Name of database descriptor
	 * @return Hybrid Database Descriptor 
	 * @throws SiminovException If any exception occur while generating hybrid database descriptor 
	 */
	public String getDatabaseDescriptor(final String databaseDescriptorName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = coreResourceManager.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		hybridDatabaseDescriptors.addHybridSiminovData(hybridDatabaseDescriptor);
		
		return HybridSiminovDataWritter.jsonBuidler(hybridDatabaseDescriptors);
	}
	
	
	public String getDatabaseDescriptorBasedOnClassName(String className) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = coreResourceManager.getDatabaseDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		hybridDatabaseDescriptors.addHybridSiminovData(hybridDatabaseDescriptor);
		
		return HybridSiminovDataWritter.jsonBuidler(hybridDatabaseDescriptors);
	}
	
	public String getDatabaseDescriptorBasedOnTableName(String tableName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = coreResourceManager.getDatabaseDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridDatabaseDescriptors = new HybridSiminovDatas();
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		hybridDatabaseDescriptors.addHybridSiminovData(hybridDatabaseDescriptor);
		
		return HybridSiminovDataWritter.jsonBuidler(hybridDatabaseDescriptors);
	}
	
	
	
	/**
	 * Handle Get Entity Descriptor Based On Hybrid Model Class Name.
	 * @param className Name of Hybrid Model Class.
	 * @return Entity Descriptor.
	 * @throws SiminovException If any error occur while getting Entity Descriptor.
	 */
	public String getEntityDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		EntityDescriptor entityDescriptor = hybridResourceManager.getEntityDescriptorBasedOnClassName(className);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridEntityDescriptor(entityDescriptor));
		
		return HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		
	}

	
	/**
	 * Handle Get Entity Descriptor Based On Table Name Request From Hybrid.
	 * @param tableName Name of Table.
	 * @return Entity Descriptor.
	 * @throws SiminovException If any error occur while getting Entity Descriptor.
	 */
	public String getEntityDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		EntityDescriptor entityDescriptor = hybridResourceManager.getEntityDescriptorBasedOnTableName(tableName);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		hybridSiminovDatas.addHybridSiminovData(hybridResourceManager.generateHybridEntityDescriptor(entityDescriptor));
		
		return HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		
	}
}
