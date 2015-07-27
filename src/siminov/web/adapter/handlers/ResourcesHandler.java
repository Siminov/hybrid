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



package siminov.web.adapter.handlers;

import java.util.Iterator;

import siminov.core.exception.SiminovException;
import siminov.core.model.ApplicationDescriptor;
import siminov.core.model.DatabaseDescriptor;
import siminov.core.model.EntityDescriptor;
import siminov.web.adapter.IAdapter;
import siminov.web.adapter.constants.WebApplicationDescriptor;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.writter.WebSiminovDataWritter;

/**
 * It handles all request related to resources.
 * LIKE: Application Descriptor, Database Descriptor, Library Descriptor, Database Mapping Descriptor, Web Descriptor.
 */
public class ResourcesHandler implements IAdapter {

	private static siminov.core.resource.ResourceManager coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
	private static siminov.web.resource.ResourceManager webResourceManager = siminov.web.resource.ResourceManager.getInstance(); 

	
	
	/**
	 * Handles Get Application Descriptor Request From Web.
	 * @return Application Descriptor.
	 * @throws SiminovException If any error occur while getting Application Descriptor.
	 */
	public String getApplicationDescriptor() throws SiminovException {
		
		ApplicationDescriptor applicationDescriptor = coreResourceManager.getApplicationDescriptor();
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		WebSiminovData webApplicationDescriptor = new WebSiminovData();
		
		webApplicationDescriptor.setDataType(WebApplicationDescriptor.APPLICATION_DESCRIPTOR);
		
		/*
		 * Application Name.
		 */
		WebSiminovValue applicationName = new WebSiminovValue();
		applicationName.setType(WebApplicationDescriptor.NAME);
		applicationName.setValue(applicationDescriptor.getName());

		webApplicationDescriptor.addValue(applicationName);
		
		/*
		 * Application Description.
		 */
		WebSiminovValue applicationDescription = new WebSiminovValue();
		applicationDescription.setType(WebApplicationDescriptor.DESCRIPTION);
		applicationDescription.setValue(applicationDescriptor.getDescription());
		
		webApplicationDescriptor.addValue(applicationDescription);
		
		/*
		 * Application Version.
		 */
		WebSiminovValue applicationVersion = new WebSiminovValue();
		applicationVersion.setType(WebApplicationDescriptor.VERSION);
		applicationVersion.setValue(Double.toString(applicationDescriptor.getVersion()));
		
		webApplicationDescriptor.addValue(applicationVersion);
		
		/*
		 * DatabaseDescriptor Paths.
		 */
		WebSiminovData webDatabaseDescriptorPaths = new WebSiminovData();
		webDatabaseDescriptorPaths.setDataType(WebApplicationDescriptor.DATABASE_DESCRIPTORS);

		Iterator<String> databaseDescriptorPaths = applicationDescriptor.getDatabaseDescriptorPaths();
		while(databaseDescriptorPaths.hasNext()) {
			String databaseDescriptorPath = databaseDescriptorPaths.next();
			
			WebSiminovValue webDatabaseDescriptorPath = new WebSiminovValue();
			webDatabaseDescriptorPath.setType(WebApplicationDescriptor.DATABASE_DESCRIPTOR_PATH);
			webDatabaseDescriptorPath.setValue(databaseDescriptorPath);
			
			webDatabaseDescriptorPaths.addValue(webDatabaseDescriptorPath);
		}
		
		webApplicationDescriptor.addData(webDatabaseDescriptorPaths);
		
		/*
		 * Event Notifiers.
		 */
		WebSiminovData webEventNotifiers = new WebSiminovData();
		webEventNotifiers.setDataType(WebApplicationDescriptor.EVENT_NOTIFIERS);

		Iterator<String> events = webResourceManager.getEvents();
		while(events.hasNext()) {
			String event = events.next();
			
			WebSiminovValue webSiminovEventHandler = new WebSiminovValue();
			webSiminovEventHandler.setType(WebApplicationDescriptor.EVENT_NOTIFIER);
			webSiminovEventHandler.setValue(event);
			
			webEventNotifiers.addValue(webSiminovEventHandler);
			
		}
		
		
		webApplicationDescriptor.addData(webEventNotifiers);
		
		webSiminovDatas.addWebSiminovData(webApplicationDescriptor);

		return WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
	}

	
	/**
	 * It handles database descriptor request from web
	 * @param databaseDescriptorName Name of database descriptor
	 * @return Web Database Descriptor 
	 * @throws SiminovException If any exception occur while generating web database descriptor 
	 */
	public String getDatabaseDescriptor(final String databaseDescriptorName) throws SiminovException {
		
		DatabaseDescriptor databaseDescriptor = coreResourceManager.getDatabaseDescriptorBasedOnName(databaseDescriptorName);
		
		WebSiminovDatas webDatabaseDescriptors = new WebSiminovDatas();
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		webDatabaseDescriptors.addWebSiminovData(webDatabaseDescriptor);
		
		return WebSiminovDataWritter.jsonBuidler(webDatabaseDescriptors);
	}
	
	/**
	 * Handle Get Entity Descriptor Based On Web Model Class Name.
	 * @param className Name of Web Model Class.
	 * @return Entity Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getEntityDescriptorBasedOnClassName(final String className) throws SiminovException {
		
		EntityDescriptor entityDescriptor = webResourceManager.getEntityDescriptorBasedOnClassName(className);
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		webSiminovDatas.addWebSiminovData(webResourceManager.generateWebEntityDescriptor(entityDescriptor));
		
		return WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		
	}

	
	/**
	 * Handle Get Database Mapping Descriptor Based On Table Name Request From Web.
	 * @param tableName Name of Table.
	 * @return Database Mapping Descriptor.
	 * @throws SiminovException If any error occur while getting Database Mapping Descriptor.
	 */
	public String getEntityDescriptorBasedOnTableName(final String tableName) throws SiminovException {
		
		EntityDescriptor entityDescriptor = webResourceManager.getEntityDescriptorBasedOnTableName(tableName);
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		webSiminovDatas.addWebSiminovData(webResourceManager.generateWebEntityDescriptor(entityDescriptor));
		
		return WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		
	}
}
