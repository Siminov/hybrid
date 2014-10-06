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

import siminov.connect.events.INotificationEvents;
import siminov.connect.exception.NotificationException;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.IRegistration;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.Adapter;
import siminov.hybrid.adapter.constants.HybridEventHandler;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.resource.ResourceManager;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

/**
 * It handles notification related events 
 */
public class NotificationEventHandler implements INotificationEvents {

	private ResourceManager hybridResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * It handles on registration event
	 */
	public void onRegistration(IRegistration registration) {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onRegistration(registration);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.INOTIFICATION_EVENT_ON_REGISTRATION);
		
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

		//Parameters
		HybridSiminovData hybridRegistration = hybridResourceManager.generateHybridRegistration(registration);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridRegistration);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onRegistration", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles on unregistration event
	 */
	public void onUnregistration(IRegistration registration) {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onUnregistration(registration);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.INOTIFICATION_EVENT_ON_UNREGISTRATION);
		
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

		//Parameters
		HybridSiminovData hybridRegistration = hybridResourceManager.generateHybridRegistration(registration);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridRegistration);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onUnregistration", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles on notification event
	 */
	public void onNotification(IMessage message) {

		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onNotification(message);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.INOTIFICATION_EVENT_ON_NOTIFICATION);
		
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

		//Parameters
		HybridSiminovData hybridMessage = hybridResourceManager.generateHybridMessage(message);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridMessage);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onNotification", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles on error event
	 */
	public void onError(NotificationException notificationException) {
		
		if(!hybridResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onError(notificationException);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.INOTIFICATION_EVENT_ON_ERROR);
		
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

		//Parameters
		HybridSiminovData hybridNotificationException = hybridResourceManager.generateHybridNotificationException(notificationException);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridNotificationException);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onError", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
}
