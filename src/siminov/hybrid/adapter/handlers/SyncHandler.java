package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

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
		
		
		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();

		HybridSiminovData hybridSyncRequest = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.SYNC_REQUEST);
		HybridSiminovValue hybridSyncName = hybridSyncRequest.getValueBasedOnType(HybridSyncRequest.NAME);
		
		HybridSiminovData hybridResources = hybridSyncRequest.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.RESOURCES);
		

		ISyncRequest syncRequest = new SyncRequest();
		syncRequest.setName(hybridSyncName.getValue());
		
		
		if(hybridResources != null) {
			
			Iterator<HybridSiminovValue> hybridResourceValues = hybridResources.getValues();
			while(hybridResourceValues.hasNext()) {
				
				HybridSiminovValue hybridResource = hybridResourceValues.next();
				
				String hybridResourceName = hybridResource.getType();
				Object hybridResourceValue = hybridResource.getValue();
				hybridResourceValue = URLDecoder.decode(hybridResourceValue.toString());
				
				syncRequest.addResource(new NameValuePair(hybridResourceName, hybridResourceValue));
			}
		}

		
		siminov.hybrid.sync.SyncHandler syncHandler = siminov.hybrid.sync.SyncHandler.getInstance();
		syncHandler.handle(syncRequest);
	}
}
