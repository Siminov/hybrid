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
import siminov.hybrid.resource.Resources;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class NotificationEventHandler implements INotificationEvents {

	private Resources hybridResources = Resources.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	
	public void onRegistration(IRegistration registration) {
		
		if(!hybridResources.doesEventsRegistered()) {
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
		
		Iterator<String> appEvents = hybridResources.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);

		//Parameters
		HybridSiminovData hybridRegistration = hybridResources.generateHybridRegistration(registration);
		
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
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onUnregistration(IRegistration registration) {

		if(!hybridResources.doesEventsRegistered()) {
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
		
		Iterator<String> appEvents = hybridResources.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);

		//Parameters
		HybridSiminovData hybridRegistration = hybridResources.generateHybridRegistration(registration);
		
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
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onNotification(IMessage message) {

		if(!hybridResources.doesEventsRegistered()) {
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
		
		Iterator<String> appEvents = hybridResources.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);

		//Parameters
		HybridSiminovData hybridMessage = hybridResources.generateHybridMessage(message);
		
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
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onError(NotificationException notificationException) {
		
		if(!hybridResources.doesEventsRegistered()) {
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
		
		Iterator<String> appEvents = hybridResources.getEvents();
		while(appEvents.hasNext()) {
			String event = appEvents.next();
			event = event.substring(event.lastIndexOf(".") + 1, event.length());
			
			HybridSiminovValue hybridEvent = new HybridSiminovValue();
			hybridEvent.setValue(event);
			
			events.addValue(hybridEvent);
		}
		
		hybridSiminovDatas.addHybridSiminovData(events);

		//Parameters
		HybridSiminovData hybridNotificationException = hybridResources.generateHybridNotificationException(notificationException);
		
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
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
}
