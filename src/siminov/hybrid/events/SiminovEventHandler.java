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
import siminov.orm.events.ISiminovEvents;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

/**
 * Handles SIMINOV FRAMEWORK life cycle events, and redirect to application if registered for ISiminov Event.
 *
 */
public class SiminovEventHandler implements ISiminovEvents {

	private ResourceManager hybridResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * Handle event if Siminov is initialized for first time, and redirect to application firstTimeSiminovInitialized event API.
	 */
	public void onFirstTimeSiminovInitialized() {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}
		

		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onFirstTimeSiminovInitialized();
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "firstTimeSiminovInitialized", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
		
	}

	/**
	 * Handle event if Siminov is initialized, and redirect to application siminovInitialized event API.
	 */
	public void onSiminovInitialized() {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onSiminovInitialized();
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "siminovInitialized", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle event if Siminov is stopped, and redirect to application siminovStopped event API.
	 */
	public void onSiminovStopped() {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}

		
		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onSiminovStopped();
		}

		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.ISIMINOV_EVENT_ON_SIMINOV_STOPPED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "siminovStopped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

}
