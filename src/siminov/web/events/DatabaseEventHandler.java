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



package siminov.web.events;

import java.util.Iterator;

import siminov.core.events.IDatabaseEvents;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.model.DatabaseDescriptor;
import siminov.core.model.DatabaseMappingDescriptor;
import siminov.core.model.DatabaseMappingDescriptor.Index;
import siminov.web.Constants;
import siminov.web.adapter.Adapter;
import siminov.web.adapter.constants.WebEventHandler;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.resource.ResourceManager;
import siminov.web.writter.WebSiminovDataWritter;

/**
 * Handles SIMINOV FRAMEWORK DATABASE events, and redirect to application if registered for IDatabase Event.
 *
 */
public class DatabaseEventHandler implements IDatabaseEvents {

	private ResourceManager webResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * Handle Database Created Event, and redirect to application databaseCreated event API.
	 */
	public void onDatabaseCreated(DatabaseDescriptor databaseDescriptor) {
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onDatabaseCreated(databaseDescriptor);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_DATABASE_CREATED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData jsDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(jsDatabaseDescriptor);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "databaseCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * Handle Database Dropped Event, and redirect to application databaseDropped event API.
	 */
	public void onDatabaseDropped(DatabaseDescriptor databaseDescriptor) {

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onDatabaseDropped(databaseDescriptor);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_DATABASE_DROPPED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);;
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webDatabaseDescriptor);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "databaseDropped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Table Created Event, and redirect to application tableCreated event API.
	 */
	public void onTableCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor) {
	
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onTableCreated(databaseDescriptor, databaseMappingDescriptor);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_TABLE_CREATED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptor = webResourceManager.generateWebDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		
		WebSiminovData parameters = new WebSiminovData();
		parameters.setDataType(WebEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(webDatabaseDescriptor);
		parameters.addData(webDatabaseMappingDescriptor);
		
		
		webSiminovDatas.addWebSiminovData(parameters);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "tableCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
		
	}

	/**
	 * Handle Table Dropped Event, and redirect to application tableDropped event API.
	 */
	public void onTableDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor) {
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onTableDropped(databaseDescriptor, databaseMappingDescriptor);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_TABLE_DROPPED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptor = webResourceManager.generateWebDatabaseMappingDescriptor(databaseMappingDescriptor);;
		
		WebSiminovData parameters = new WebSiminovData();
		parameters.setDataType(WebEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(webDatabaseDescriptor);
		parameters.addData(webDatabaseMappingDescriptor);
		
		
		webSiminovDatas.addWebSiminovData(parameters);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "tableDrpped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Index Created Event, and redirect to application indexCreated event API.
	 */
	public void onIndexCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor, Index index) {
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onIndexCreated(databaseDescriptor, databaseMappingDescriptor, index);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_INDEX_CREATED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptor = webResourceManager.generateWebDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptorIndex = webResourceManager.generateWebDatabaseMappingDescriptorIndex(index);
		
		WebSiminovData parameters = new WebSiminovData();
		parameters.setDataType(WebEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(webDatabaseDescriptor);
		parameters.addData(webDatabaseMappingDescriptor);
		parameters.addData(webDatabaseMappingDescriptorIndex);
		
		webSiminovDatas.addWebSiminovData(parameters);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "indexCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Index Dropped Event, and redirect to application indexDropped event API.
	 */
	public void onIndexDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor, Index index) {

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onIndexDropped(databaseDescriptor, databaseMappingDescriptor, index);
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.IDATABASE_EVENT_ON_INDEX_DROPPED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Event
		WebSiminovData events = new WebSiminovData();
		events.setDataType(WebEventHandler.EVENTS);
		
		Iterator<String> appEvents = webResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			WebSiminovValue webEvent = new WebSiminovValue();
			webEvent.setValue(event);
			
			events.addValue(webEvent);
		}
		
		webSiminovDatas.addWebSiminovData(events);
		
		
		//Parameters
		WebSiminovData webDatabaseDescriptor = webResourceManager.generateWebDatabaseDescriptor(databaseDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptor = webResourceManager.generateWebDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		WebSiminovData webDatabaseMappingDescriptorIndex = webResourceManager.generateWebDatabaseMappingDescriptorIndex(index);
		
		WebSiminovData parameters = new WebSiminovData();
		parameters.setDataType(WebEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(webDatabaseDescriptor);
		parameters.addData(webDatabaseMappingDescriptor);
		parameters.addData(webDatabaseMappingDescriptorIndex);
		
		webSiminovDatas.addWebSiminovData(parameters);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "indexDropped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

}
