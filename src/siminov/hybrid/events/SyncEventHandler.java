package siminov.hybrid.events;

import java.util.Iterator;

import siminov.connect.events.ISyncEvents;
import siminov.connect.sync.design.ISyncRequest;
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

public class SyncEventHandler implements ISyncEvents {

	private Resources hybridResources = Resources.getInstance();
	private EventHandler eventHandler = EventHandler.getInstance();
	
	public void onSyncStarted(ISyncRequest syncRequest) {

		ISyncEvents syncEvents = eventHandler.getSyncEvent();
		if(syncEvents != null) {
			syncEvents.onSyncStarted(syncRequest);
		}
		
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridEventHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridEventHandler.ISYNC_EVENT_ON_SYNC_STARTED);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		//Event
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
		HybridSiminovData hybridSyncRequest = hybridResources.generateHybridSyncRequest(syncRequest);
		
		HybridSiminovData parameteres = new HybridSiminovData();
		parameteres.setDataType(HybridEventHandler.EVENT_PARAMETERS);
		parameteres.addData(hybridSyncRequest);
		
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

	public void onSyncQueued(ISyncRequest syncRequest) {
		System.out.print("");
	}

	public void onSyncRemoved(ISyncRequest syncRequest) {
		System.out.print("");
	}

	public void onSyncTerminated(ISyncRequest syncRequest) {
		System.out.print("");
	}
}
