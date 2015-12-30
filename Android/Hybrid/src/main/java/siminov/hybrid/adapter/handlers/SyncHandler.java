/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.exception.SyncException;
import siminov.connect.sync.SyncRequest;
import siminov.connect.sync.design.ISyncRequest;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.constants.HybridSyncRequest;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.sync.GenericSyncHandler;


/**
 * It handles all request related to sync
 */
public class SyncHandler implements IAdapter {

	/**
	 * It handles sync request from hybrid
	 */
	public void handle(String data) throws SyncException {
		
		HybridSiminovDataReader hybridSiminovDataParser = null; 
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			data = URLDecoder.decode(data, "UTF-8");

			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(Exception siminovException) {
			Log.error(DatabaseHandler.class.getName(), "handle", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}
		
		
		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();

		HybridSiminovData hybridSyncRequest = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.SYNC_REQUEST);
		HybridSiminovValue hybridRequestId = hybridSyncRequest.getValueBasedOnType(HybridSyncRequest.REQUEST_ID);
		HybridSiminovValue hybridSyncName = hybridSyncRequest.getValueBasedOnType(HybridSyncRequest.NAME);
		
		HybridSiminovData hybridResources = hybridSyncRequest.getHybridSiminovDataBasedOnDataType(HybridSyncRequest.RESOURCES);
		

		ISyncRequest syncRequest = new SyncRequest();
		syncRequest.setRequestId(Long.valueOf(hybridRequestId.getValue()));
		syncRequest.setName(hybridSyncName.getValue());
		
		
		if(hybridResources != null) {
			
			Iterator<HybridSiminovValue> hybridResourceValues = hybridResources.getValues();
			while(hybridResourceValues.hasNext()) {
				
				HybridSiminovValue hybridResource = hybridResourceValues.next();
				
				String hybridResourceName = hybridResource.getType();
				Object hybridResourceValue = hybridResource.getValue();
				hybridResourceValue = URLDecoder.decode(hybridResourceValue.toString());
				
				syncRequest.addResource(hybridResourceName, hybridResourceValue);
			}
		}

		
		GenericSyncHandler syncHandler = GenericSyncHandler.getInstance();
		syncHandler.handle(syncRequest);
	}
}
