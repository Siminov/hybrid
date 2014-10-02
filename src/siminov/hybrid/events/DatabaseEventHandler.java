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



package siminov.hybrid.events;

import java.util.Iterator;

import siminov.hybrid.Constants;
import siminov.hybrid.adapter.Adapter;
import siminov.hybrid.adapter.constants.HybridEventHandler;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.resource.ResourceManager;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.events.IDatabaseEvents;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor.Index;

/**
 * Handles SIMINOV FRAMEWORK DATABASE events, and redirect to application if registered for IDatabase Event.
 *
 */
public class DatabaseEventHandler implements IDatabaseEvents {

	private ResourceManager hybridResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * Handle Database Created Event, and redirect to application databaseCreated event API.
	 */
	public void onDatabaseCreated(DatabaseDescriptor databaseDescriptor) {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onDatabaseCreated(databaseDescriptor);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_DATABASE_CREATED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData jsDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(jsDatabaseDescriptor);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "databaseCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * Handle Database Dropped Event, and redirect to application databaseDropped event API.
	 */
	public void onDatabaseDropped(DatabaseDescriptor databaseDescriptor) {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onDatabaseDropped(databaseDescriptor);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_DATABASE_DROPPED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);;
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridDatabaseDescriptor);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "databaseDropped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Table Created Event, and redirect to application tableCreated event API.
	 */
	public void onTableCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor) {
	
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onTableCreated(databaseDescriptor, databaseMappingDescriptor);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_TABLE_CREATED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptor = hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		
		HybridSiminovData parameters = new HybridSiminovData();
		parameters.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(hybridDatabaseDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptor);
		
		
		hybridSiminovDatas.addHybridSiminovData(parameters);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "tableCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
		
	}

	/**
	 * Handle Table Dropped Event, and redirect to application tableDropped event API.
	 */
	public void onTableDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor) {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onTableDropped(databaseDescriptor, databaseMappingDescriptor);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_TABLE_DROPPED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptor = hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor);;
		
		HybridSiminovData parameters = new HybridSiminovData();
		parameters.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(hybridDatabaseDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptor);
		
		
		hybridSiminovDatas.addHybridSiminovData(parameters);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "tableDrpped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Index Created Event, and redirect to application indexCreated event API.
	 */
	public void onIndexCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor, Index index) {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onIndexCreated(databaseDescriptor, databaseMappingDescriptor, index);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_INDEX_CREATED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptor = hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptorIndex = hybridResourceManager.generateHybridDatabaseMappingDescriptorIndex(index);
		
		HybridSiminovData parameters = new HybridSiminovData();
		parameters.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(hybridDatabaseDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptorIndex);
		
		hybridSiminovDatas.addHybridSiminovData(parameters);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "indexCreated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle Index Dropped Event, and redirect to application indexDropped event API.
	 */
	public void onIndexDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMappingDescriptor, Index index) {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		IDatabaseEvents databaseEvents = eventHandler.getDatabaseEvents();
		if(databaseEvents != null) {
			databaseEvents.onIndexDropped(databaseDescriptor, databaseMappingDescriptor, index);
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IDATABASE_EVENT_ON_INDEX_DROPPED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
		HybridSiminovData events = new HybridSiminovData();
		events.setDataType(HybridEventHandler.EVENTS);
		
		Iterator<String> appEvents = hybridResourceManager.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);
		
		
		//Parameters
		HybridSiminovData hybridDatabaseDescriptor = hybridResourceManager.generateHybridDatabaseDescriptor(databaseDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptor = hybridResourceManager.generateHybridDatabaseMappingDescriptor(databaseMappingDescriptor);
		
		HybridSiminovData hybridDatabaseMappingDescriptorIndex = hybridResourceManager.generateHybridDatabaseMappingDescriptorIndex(index);
		
		HybridSiminovData parameters = new HybridSiminovData();
		parameters.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		
		parameters.addData(hybridDatabaseDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptor);
		parameters.addData(hybridDatabaseMappingDescriptorIndex);
		
		hybridSiminovDatas.addHybridSiminovData(parameters);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "indexDropped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

}
