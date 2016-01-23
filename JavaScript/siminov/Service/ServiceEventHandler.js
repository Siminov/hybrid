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
	Service is a client-side communication component that process and handles any hybrid service request. It performs long running operations in the background.
	A Service is a group of APIs which deals on one particular hybrid service.
	
	@module Service
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
    var Function = require('../Function/Function');
    var ServiceHandler = require('../Service/ServiceHandler');
    var SyncHandler = require('../Sync/SyncHandler');
    var Dictionary = require('../Collection/Dictionary');
    var SIDatasHelper = require('../ReaderWriter/SIDatasHelper');
    var Constants = require('../Constants');
    
    module.exports = ServiceEventHandler;
    win.ServiceEventHandler = ServiceEventHandler;
}

/**
	Any service event triggered by Siminov is first handled by this function later it will deliver to appropriate Service Event APIs. 

	@module Service
	@class ServiceEventHandler
	@constructor
*/
function ServiceEventHandler() {

	
	/**
		Handle service event triggered by Siminov.
		
		@method triggerEvent
		@param data {String} Hybrid Data From Native
	*/
	this.triggerEvent = function(data) {

        var hybridSiminovDatas = dom == undefined?JSON.parse(eval('(' + data + ')')):JSON.parse(data);
        var datas = hybridSiminovDatas.datas;

        var serviceHandler = ServiceHandler.getInstance();
        var syncHandler = SyncHandler.getInstance();
        
        var requestId;
        var apiHandler;
        var event;

		var resources = new Dictionary();

		var connectionRequest;
		var connectionResponse;

        if(datas != undefined && datas != null && datas.length > 0) {

            for(var i = 0;i < datas.length;i++) {
                var data = datas[i];

                var dataType = data.type;
                if(dataType === Constants.ISERVICE_REQUEST_ID) {
                    requestId = data.value;
                } else if(dataType === Constants.ISERVICE_API_HANDLER) {
                    apiHandler = data.value;
                } else if(dataType === Constants.ISERVICE_TRIGGERED_EVENT) {
					event = data.value;
                } else if(dataType === Constants.ISERVICE_CONNECTION_REQUEST) {
					connectionRequest = SIDatasHelper.toModel(data);
                } else if(dataType === Constants.ISERVICE_CONNECTION_RESPONSE) {
                	connectionResponse = SIDatasHelper.toModel(data);
                	
                	var response = connectionResponse.getResponse();
                	if(response != undefined && response != null) {
                		response = decodeURIComponent(response);
                		connectionResponse.setResponse(response);
                	}
                } else if(dataType === Constants.ISERVICE_RESOURCES) {
                	
                	var hybridResources = data.datas;
                	if(hybridResources != undefined && hybridResources != null && hybridResources.length > 0) {
                		
                		for(var j = 0;j < hybridResources.length;j++) {
                			
                			var hybridResource = hybridResources[j];
                			
                			var key = hybridResource.type;
                			var value = hybridResource.value;
                			
                			resources.add(key, value);
                		}
                	}
                } 
            }
        }

        var eventHandler;
        
        var service = serviceHandler.getRequest(requestId);
        if(service) {
            eventHandler = service;
        } else {
            eventHandler = Function.createFunctionInstance(apiHandler);
            
            var syncRequest = syncHandler.getRequest(requestId);
            if(syncRequest) {
                
                resources = new Dictionary();
                
                var syncResourcesKeys = syncRequest.getResources();
                for(var i = 0;i < syncResourcesKeys.length;i++) {
                    resources.add(syncResourcesKeys[i], syncRequest.getResource(syncResourcesKeys[i]));
                }
            }
            
            //Inflate Resources
            var resourceKeys = resources.keys();
            for(var i = 0;i < resourceKeys.length;i++) {
                var resourceName = resourceKeys[i];
                var resourceValue = resources.get(resourceName);
                
                Function.invokeAndInflate(eventHandler, Constants.ISERVICE_ADD_RESOURCE, resourceName, resourceValue);
            }
        }
        

		//Invoke Event API
        if(event === Constants.ISERVICE_ON_START_EVENT) {
					
            Function.invokeAndInflate(eventHandler, event);
        } else if(event === Constants.ISERVICE_ON_QUEUE_EVENT) {

            Function.invokeAndInflate(eventHandler, event);
        } else if(event === Constants.ISERVICE_ON_PAUSE_EVENT) {

            Function.invokeAndInflate(eventHandler, event);
        } else if(event === Constants.ISERVICE_ON_RESUME_EVENT) {
            
            Function.invokeAndInflate(eventHandler, event);
        } else if(event === Constants.ISERVICE_ON_FINISH_EVENT) {

            Function.invokeAndInflate(eventHandler, event);
            
            serviceHandler.removeRequest(requestId);
        } else if(event === Constants.ISERVICE_ON_REQUEST_INVOKE_EVENT) {

			Function.invokeAndInflate(eventHandler, event, connectionRequest);
        } else if(event === Constants.ISERVICE_ON_REQUEST_FINISH_EVENT) {

			Function.invokeAndInflate(eventHandler, event, connectionResponse);
        } else if(event === Constants.ISERVICE_ON_TERMINATE_EVENT) {

            Function.invokeAndInflate(eventHandler, event);
            
            serviceHandler.removeRequest(requestId);
        }
    }
}
