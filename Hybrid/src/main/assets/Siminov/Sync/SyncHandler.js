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


/**
	Exposes classes which deal with services.
	It allows app to automatically checks for updates in the background, using battery and your data plan. 
	
	You can customise how often it does these checks by adjusting the Refresh Interval. If you don't framework to update regularly, you should set this value to zero to
	conserve both your battery and your data use.
	
	@module Sync
*/



/**
	It exposes APIs to process sync request

	@module Sync
	@class SyncHandler
	@constructor
*/
var SyncHandler = (function() {

	var syncHandler;
	
	return {
		
		/**
		 * Get singleton instance of Sync Handler class
		 * 
		 * @method getInstance
		 * @return {SyncHandler} Singleton instance of Sync Handler 
		 */
		getInstance : function() {
			
			if(syncHandler == null) {
				syncHandler = new SyncHandler();
				
				syncHandler.constructor = null;
			}
			
			return syncHandler;
		}
	}
	

	function SyncHandler() {

		/**
		 * It handles and processes the sync request
		 * 
		 */
		this.handle = function(syncRequest) {
			
			var webSiminovDatas = new WebSiminovDatas();
				
			var webSyncRequest = new WebSiminovDatas.WebSiminovData();
			webSyncRequest.setDataType(Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST);

				var webSyncRequestName = new WebSiminovDatas.WebSiminovData.WebSiminovValue();
				webSyncRequestName.setType(Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_NAME);
				webSyncRequestName.setValue(syncRequest.getName());
			
			webSyncRequest.addValue(webSyncRequestName);
			
				var resources = syncRequest.getResources();
				if(resources != undefined && resources != null && resources.length > 0) {
					
					var webResources = new WebSiminovDatas.WebSiminovData();
					webResources.setDataType(Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_RESOURCES);

					for(var i = 0;i < resources.length;i++) {
						
						var resourceName = resources[i];
						var resourceValue = syncRequest.getResource(resourceName);
						resourceValue = '' + resourceValue;
						
						var webResource = new WebSiminovDatas.WebSiminovData.WebSiminovValue();
						webResource.setType(resourceName);
						webResource.setValue('' + resourceValue);
	
						webResources.addValue(webResource);
					}
	
					webSyncRequest.addData(webResources);
				}


			webSiminovDatas.addWebSiminovData(webSyncRequest);
									
									
			var data = encodeURI(SIJsonHelper.toJson(webSiminovDatas));

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SYNC_ADAPTER);
	        adapter.setHandlerName(Constants.SYNC_ADAPTER_HANDLE_HANDLER);

			adapter.addParameter(data);

			adapter.invoke();
		}		
	}
		
}) ();


