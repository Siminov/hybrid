package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.design.sync.ISyncRequest;
import siminov.connect.exception.SyncException;
import siminov.connect.sync.SyncRequest;
import siminov.hybrid.adapter.constants.HybridSyncRequest;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class SyncHandler {

	public void handle(String data) throws SyncException {
		
		HybridSiminovDataReader hybridSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "handle", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}
		
		
		handle(hybridSiminovDataParser.getDatas());
	}
	
	private void handle(HybridSiminovDatas hybridSiminovDatas) {

		ISyncRequest syncRequest = new SyncRequest();
		
		HybridSiminovData hybridSyncRequest = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.SYNC_REQUEST);
		
		HybridSiminovValue hybridName = hybridSyncRequest.getValueBasedOnType(HybridSyncRequest.NAME);
		syncRequest.setName(hybridName.getValue());
		
		
		HybridSiminovData hybridResources = hybridSyncRequest.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.RESOURCES);
		Iterator<HybridSiminovValue> hybridResourceValues = hybridResources.getValues();
		
		while(hybridResourceValues.hasNext()) {

			HybridSiminovValue hybridSiminovValue = hybridResourceValues.next();
			syncRequest.addResource(hybridSiminovValue.getType(), hybridSiminovValue.getValue());
		}

		
		siminov.connect.sync.SyncHandler syncHandler = siminov.connect.sync.SyncHandler.getInstance();
		syncHandler.handle(syncRequest);
	}
}
