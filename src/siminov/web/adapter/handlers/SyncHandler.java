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

package siminov.web.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.exception.SyncException;
import siminov.connect.sync.SyncRequest;
import siminov.connect.sync.design.ISyncRequest;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.web.adapter.IAdapter;
import siminov.web.adapter.constants.WebSyncRequest;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.reader.WebSiminovDataReader;


/**
 * It handles all request related to sync
 */
public class SyncHandler implements IAdapter {

	/**
	 * It handles sync request from web
	 */
	public void handle(String data) throws SyncException {
		
		WebSiminovDataReader webSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			webSiminovDataParser = new WebSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "handle", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}
		
		
		WebSiminovDatas webSiminovDatas = webSiminovDataParser.getDatas();

		WebSiminovData webSyncRequest = webSiminovDatas.getWebSiminovDataBasedOnDataType(WebSyncRequest.SYNC_REQUEST);
		WebSiminovValue webSyncName = webSyncRequest.getValueBasedOnType(WebSyncRequest.NAME);
		
		WebSiminovData webResources = webSyncRequest.getWebSiminovDataBasedOnDataType(WebSyncRequest.RESOURCES);
		

		ISyncRequest syncRequest = new SyncRequest();
		syncRequest.setName(webSyncName.getValue());
		
		
		if(webResources != null) {
			
			Iterator<WebSiminovValue> webResourceValues = webResources.getValues();
			while(webResourceValues.hasNext()) {
				
				WebSiminovValue webResource = webResourceValues.next();
				
				String webResourceName = webResource.getType();
				Object webResourceValue = webResource.getValue();
				webResourceValue = URLDecoder.decode(webResourceValue.toString());
				
				syncRequest.addResource(webResourceName, webResourceValue);
			}
		}

		
		siminov.web.sync.SyncHandler syncHandler = siminov.web.sync.SyncHandler.getInstance();
		syncHandler.handle(syncRequest);
	}
}
