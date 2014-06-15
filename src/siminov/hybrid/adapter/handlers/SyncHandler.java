package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.exception.SyncException;
import siminov.connect.service.NameValuePair;
import siminov.connect.sync.SyncRequest;
import siminov.connect.sync.design.ISyncRequest;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.constants.HybridSyncRequest;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class SyncHandler implements IAdapter {

	public void handle(String data) throws SyncException {
		
		HybridSiminovDataReader hybridSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "handle", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}
		
		
		handle(hybridSiminovDataParser.getDatas());
	}
	
	private void handle(HybridSiminovDatas hybridSiminovDatas) {

		ISyncRequest syncRequest = new SyncRequest();
		
		HybridSiminovData hybridSyncRequest = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.SYNC_REQUEST);
		HybridSiminovData resourcesHybridDatas = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.RESOURCES);
		Iterator<HybridSiminovData>	resourcesHybridData = resourcesHybridDatas.getDatas();
		
		HybridSiminovValue hybridName = hybridSyncRequest.getValueBasedOnType(HybridSyncRequest.NAME);
		syncRequest.setName(hybridName.getValue());
		
		
		Map<String, String> resources = new HashMap<String, String>();
		while(resourcesHybridData.hasNext()) {
			
			HybridSiminovData resourceHybridData = resourcesHybridData.next();
			resources.put(resourceHybridData.getDataType(), resourceHybridData.getDataValue());
		}
		
		for(String resource: resources.keySet()) {
			syncRequest.addResource(new NameValuePair(resource, resources.get(resource)));
		}

		siminov.hybrid.sync.SyncHandler syncHandler = siminov.hybrid.sync.SyncHandler.getInstance();
		syncHandler.handle(syncRequest);
	}
}
