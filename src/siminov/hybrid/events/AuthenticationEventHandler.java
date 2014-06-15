package siminov.hybrid.events;

import java.util.Iterator;

import siminov.connect.authorization.design.ICredential;
import siminov.connect.events.IAuthenticationEvents;
import siminov.connect.exception.AuthorizationException;
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

public class AuthenticationEventHandler implements IAuthenticationEvents {

	private Resources hybridResources = Resources.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();

	public void onAuthenticationStart(ICredential credential) throws AuthorizationException {

		if(!hybridResources.doesEventsRegistered()) {
			return;
		}
		

		IAuthenticationEvents authenticationEvents = eventHandler.getAuthenticationEvent();
		if(authenticationEvents != null) {
			authenticationEvents.onAuthenticationStart(credential);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IAUTHENTICATION_EVENT_ON_AUTHENTICATION_START);
		
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
		HybridSiminovData hybridRegistration = hybridResources.generateHybridCredential(credential);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridRegistration);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onAuthenticationStart", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onAuthenticationFinish(ICredential credential) throws AuthorizationException {

		if(!hybridResources.doesEventsRegistered()) {
			return;
		}
		

		IAuthenticationEvents authenticationEvents = eventHandler.getAuthenticationEvent();
		if(authenticationEvents != null) {
			authenticationEvents.onAuthenticationStart(credential);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IAUTHENTICATION_EVENT_ON_AUTHENTICATION_FINISH);
		
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
		HybridSiminovData hybridRegistration = hybridResources.generateHybridCredential(credential);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridRegistration);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onAuthenticationFinish", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onAuthenticationTerminate(ICredential credential) throws AuthorizationException {

		if(!hybridResources.doesEventsRegistered()) {
			return;
		}
		

		IAuthenticationEvents authenticationEvents = eventHandler.getAuthenticationEvent();
		if(authenticationEvents != null) {
			authenticationEvents.onAuthenticationStart(credential);
		}
		
	
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.IAUTHENTICATION_EVENT_ON_AUTHENTICATION_TERMINATE);
		
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
		HybridSiminovData hybridRegistration = hybridResources.generateHybridCredential(credential);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridRegistration);
		
		hybridSiminovDatas.addHybridSiminovData(parameteres);
		
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onAuthenticationTerminate", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
}
