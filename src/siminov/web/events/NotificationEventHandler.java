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

import siminov.connect.events.INotificationEvents;
import siminov.connect.exception.NotificationException;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.IRegistration;
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
 * It handles notification related events 
 */
public class NotificationEventHandler implements INotificationEvents {

	private ResourceManager webResourceManager = ResourceManager.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	/**
	 * It handles on registration event
	 */
	public void onRegistration(IRegistration registration) {
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onRegistration(registration);
		}
		
	
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.INOTIFICATION_EVENT_ON_REGISTRATION);
		
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

		//Parameters
		WebSiminovData webRegistration = webResourceManager.generateWebRegistration(registration);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webRegistration);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
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

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onUnregistration(registration);
		}
		
	
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.INOTIFICATION_EVENT_ON_UNREGISTRATION);
		
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

		//Parameters
		WebSiminovData webRegistration = webResourceManager.generateWebRegistration(registration);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webRegistration);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
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

		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onNotification(message);
		}
		
	
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.INOTIFICATION_EVENT_ON_NOTIFICATION);
		
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

		//Parameters
		WebSiminovData webMessage = webResourceManager.generateWebMessage(message);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webMessage);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
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
		
		if(!webResourceManager.doesEventsRegistered()) {
			return;
		}
		

		INotificationEvents notificationEvents = eventHandler.getNotificationEvent();
		if(notificationEvents != null) {
			notificationEvents.onError(notificationException);
		}
		
	
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebEventHandler.INOTIFICATION_EVENT_ON_ERROR);
		
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

		//Parameters
		WebSiminovData webNotificationException = webResourceManager.generateWebNotificationException(notificationException);
		
		WebSiminovData parameteres = new WebSiminovData();
		parameteres.setDataType(WebEventHandler.EVENT_PARAMETERS);
		parameteres.addData(webNotificationException);
		
		webSiminovDatas.addWebSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
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
