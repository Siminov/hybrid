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

import siminov.core.events.ISiminovEvents;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.web.Constants;
import siminov.web.adapter.Adapter;
import siminov.web.adapter.constants.WebEventHandler;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.resource.ResourceManager;
import siminov.web.writter.WebSiminovDataWritter;

/**
 * Handles SIMINOV FRAMEWORK life cycle events, and redirect to application if registered for ISiminov Event.
 *
 */
public class SiminovEventHandler implements ISiminovEvents {

	private ResourceManager webResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * Handle event if Siminov is initialized for first time, and redirect to application firstTimeSiminovInitialized event API.
	 */
	public void onFirstTimeSiminovInitialized() {

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}
		

		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onFirstTimeSiminovInitialized();
		}
		
	
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "firstTimeSiminovInitialized", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
		
	}

	/**
	 * Handle event if Siminov is initialized, and redirect to application siminovInitialized event API.
	 */
	public void onSiminovInitialized() {

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onSiminovInitialized();
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "siminovInitialized", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

	/**
	 * Handle event if Siminov is stopped, and redirect to application siminovStopped event API.
	 */
	public void onSiminovStopped() {
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}

		
		ISiminovEvents siminovEvents = eventHandler.getSiminovEvent();
		if(siminovEvents != null) {
			siminovEvents.onSiminovStopped();
		}

		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISIMINOV_EVENT_ON_SIMINOV_STOPPED);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Events
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
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "siminovStopped", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();

	}

}
