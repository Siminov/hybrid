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



/**
	Exposes classes which deal with services.
	It allows app to automatically checks for updates in the background, using battery and your data plan. 
	
	You can customise how often it does these checks by adjusting the Refresh Interval. If you don't framework to update regularly, you should set this value to zero to
	conserve both your battery and your data use.
	
	@module Sync
*/

var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}



if(dom == undefined) {
    var HybridSiminovDatas = require('../Model/HybridSiminovDatas');
    var Constants = require('../Constants');
    var Callback = require('../Callback');
    var Dictionary = require('../Collection/Dictionary');
    
    var Adapter = require('../Adapter/Adapter');    
}



/**
	It exposes APIs to process sync request

	@module Sync
	@class SyncHandler
	@constructor
*/

var syncHandler = null;


SyncHandler.getInstance = function() {
    
    if(syncHandler == null) {
        syncHandler = new SyncHandler();
    }
    
    return syncHandler;
}

var getInstance = function() {
    
    if(syncHandler == null) {
        syncHandler = new SyncHandler();
    }
    
    return syncHandler;
}

function SyncHandler() {

    
    var requestQueue = new Dictionary();
    
    var getRequest = function(requestId) {
        return requestQueue.get(requestId);
    }
    
    
    var removeRequest = function(requestId) {
        requestQueue.remove(requestId);
    }
    

    
    /**
     * It handles and processes the sync request
     *
     * @method handle
     * @param syncRequest {ISyncRequest} Sync Request
     */
    var handle = function(syncRequest) {
			
        var callback = arguments && arguments[1];
			
        var hybridSiminovDatas = Object.create(HybridSiminovDatas);
        hybridSiminovDatas.datas = new Array();
			
        var hybridSyncRequest = Object.create(HybridSiminovDatas.HybridSiminovData);
        hybridSyncRequest.datas = new Array();
        hybridSyncRequest.values = new Array();
			
        hybridSyncRequest.type = Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST;

        var hybridSyncRequestId = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
        hybridSyncRequestId.type = Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_ID;
        hybridSyncRequestId.value = syncRequest.getRequestId();
        
        hybridSyncRequest.values.push(hybridSyncRequestId);

        var hybridSyncRequestName = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
        hybridSyncRequestName.type = Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_NAME;
        hybridSyncRequestName.value = syncRequest.getName();
			
        hybridSyncRequest.values.push(hybridSyncRequestName);
			
            var resources = syncRequest.getResources();
            if(resources != undefined && resources != null && resources.length > 0) {
					
                var hybridResources = Object.create(HybridSiminovDatas.HybridSiminovData);
                hybridResources.datas = new Array();
                hybridResources.values = new Array();
					
                hybridResources.type = Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_RESOURCES;

                for(var i = 0;i < resources.length;i++) {
						
                    var resourceName = resources[i];
                    var resourceValue = syncRequest.getResource(resourceName);
                    if(resourceValue && !(typeof resourceValue == 'string')) {
                        continue;
                    }

                    resourceValue = '' + resourceValue;
						
                    var hybridResource = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
                    hybridResource.type = resourceName;
                    hybridResource.value = '' + resourceValue;
	
                    hybridResources.values.push(hybridResource);
                }
					
                hybridSyncRequest.datas.push(hybridResources);
            }


        hybridSiminovDatas.datas.push(hybridSyncRequest);
        var data = encodeURI(JSON.stringify(hybridSiminovDatas));
									
        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SYNC_ADAPTER);
        adapter.setHandlerName(Constants.SYNC_ADAPTER_HANDLE_HANDLER);

        adapter.addParameter(data);

        requestQueue.add(syncRequest.getRequestId(), syncRequest);
        if(callback) {
            adapter.setCallback(syncCallback);
            adapter.setAdapterMode(Adapter.REQUEST_ASYNC_MODE);
				
            Adapter.invoke(adapter);
				
            function syncCallback() {
                callback && callback.onSuccess && callback.onSuccess();
            }
        } else {
            Adapter.invoke(adapter);
        }
    };
		
		
    /**
     * It handles and processes the sync request asynchronous
     *
     * @method handleAsync
     * @param syncRequest {ISyncRequest} Sync Request
     * @param callback {Callback} Request Callback
     */
    var handleAsync = function(syncRequest, callback) {
        this.handle(syncRequest, callback?callback:new Callback());
    };
    
    
    return {
        getRequest: getRequest,
        removeRequest: removeRequest,
        handleAsync: handleAsync,
        handle: handle
    }
};


if(dom == undefined) {
    exports.getInstance = getInstance;    
}
