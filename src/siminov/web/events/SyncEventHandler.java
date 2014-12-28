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

import siminov.connect.events.ISyncEvents;
import siminov.connect.sync.design.ISyncRequest;
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
 * It handles all events related to Sync Event 
 */
public class SyncEventHandler implements ISyncEvents {

	private ResourceManager webResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();
	
	/**
	 * It handles On Start Sync event
	 */
	public void onStart(ISyncRequest syncRequest) {

		ISyncEvents syncEvents = eventHandler.getSyncEvent();
		if(syncEvents != null) {
			syncEvents.onStart(syncRequest);
		}
		
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISYNC_EVENT_ON_SYNC_STARTED);
		
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
		WebSiminovData webSyncRequest = webResourceManager.generateWebSyncRequest(syncRequest);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webSyncRequest);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "onSyncStarted", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles On Queue Sync event
	 */
	public void onQueue(ISyncRequest syncRequest) {

		ISyncEvents syncEvents = eventHandler.getSyncEvent();
		if(syncEvents != null) {
			syncEvents.onQueue(syncRequest);
		}
		
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISYNC_EVENT_ON_SYNC_QUEUED);
		
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
		WebSiminovData webSyncRequest = webResourceManager.generateWebSyncRequest(syncRequest);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webSyncRequest);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "onSyncQueued", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles On Finish Event
	 */
	public void onFinish(ISyncRequest syncRequest) {

		ISyncEvents syncEvents = eventHandler.getSyncEvent();
		if(syncEvents != null) {
			syncEvents.onFinish(syncRequest);
		}
		
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISYNC_EVENT_ON_SYNC_REMOVED);
		
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
		WebSiminovData webSyncRequest = webResourceManager.generateWebSyncRequest(syncRequest);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webSyncRequest);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "onSyncRemoved", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles On Terminate Event
	 */
	public void onTerminate(ISyncRequest syncRequest) {

		ISyncEvents syncEvents = eventHandler.getSyncEvent();
		if(syncEvents != null) {
			syncEvents.onTerminate(syncRequest);
		}
		
		
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.ISYNC_EVENT_ON_SYNC_TERMINATED);
		
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
		WebSiminovData webSyncRequest = webResourceManager.generateWebSyncRequest(syncRequest);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webSyncRequest);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseEventHandler.class.getName(), "onSyncTerminated", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
}
