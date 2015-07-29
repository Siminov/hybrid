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

package siminov.hybrid.sync;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import siminov.connect.model.SyncDescriptor;
import siminov.connect.resource.ResourceManager;
import siminov.connect.sync.design.ISyncRequest;

/**
 * It handles sync requests on behalf of hybrid 
 */
public class GenericSyncHandler {

	private ResourceManager connectResourceManager = ResourceManager.getInstance();
	
	private SyncWorker syncWorker = SyncWorker.getInstance();
	private Map<ISyncRequest, Long> requestTimestamps = new HashMap<ISyncRequest, Long>();

	private static GenericSyncHandler syncHandler = null;
	
	/**
	 * SyncHandler Private Constructor
	 */
	private GenericSyncHandler() {
		
	}
	
	/**
	 * It provides singleton instance of SyncHandler 
	 * @return SyncHandler singleton instance
	 */
	public static GenericSyncHandler getInstance() {
		
		if(syncHandler == null) {
			syncHandler = new GenericSyncHandler();
		}
		
		return syncHandler;
	}
	
	/**
	 * It handle sync request from hybrid
	 * @param syncRequest Sync Request
	 */
	public void handle(ISyncRequest syncRequest) {

		SyncDescriptor syncDescriptor = connectResourceManager.getSyncDescriptor(syncRequest.getName());
		
		Long requestTimestamp = requestTimestamps.get(syncRequest);
		if(requestTimestamp == null || requestTimestamp <= 0) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));

			return;
		}
		
		
		long syncInterval = syncDescriptor.getSyncInterval();
		long lastRefreshTimestamp = requestTimestamps.get(syncRequest.getName());
		long currentTimestamp = Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND));
		
		long timeDifference = lastRefreshTimestamp + syncInterval;
		
		if(timeDifference < currentTimestamp) {
			syncWorker.addRequest(syncRequest);
			requestTimestamps.put(syncRequest, Long.valueOf(Calendar.getInstance().get(Calendar.MILLISECOND)));
		}
	}
}
