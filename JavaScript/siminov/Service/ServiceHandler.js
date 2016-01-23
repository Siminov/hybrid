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
    var Callback = require('../Callback');
    var Constants = require('../Constants');
    var Adapter = require('../Adapter/Adapter');
    var Dictionary = require('../Collection/Dictionary');
    var HybridSiminovDatas = require('../Model/HybridSiminovDatas');
}



/**
	It exposes APIs to process service request

	@module Service
	@class ServiceHandler
	@constructor
*/

var serviceHandler = null;

ServiceHandler.getInstance = function() {
    
    if(serviceHandler == null) {
        serviceHandler = new ServiceHandler();
    }
    
    return serviceHandler;
};

var getInstance = function() {
    
    if(serviceHandler == null) {
        serviceHandler = new ServiceHandler();
    }
    
    return serviceHandler;
};


function ServiceHandler() {
    
    var requestQueue = new Dictionary();
    
    var getRequest = function(requestId) {
        return requestQueue.get(requestId);
    }
    
    
    var removeRequest = function(requestId) {
        requestQueue.remove(requestId);
    }
    
    
    var handleAsync = function(iService, callback) {
        this.handle(iService, callback?callback:new Callback());
    };
    
    
    /**
     * It handles the service request
     *
     * @method handler
     * @param iService {Service} Service instance
     */
    var handle = function(iService) {
        
        var callback = arguments && arguments[1];
        
        var adapter = new Adapter();
        adapter.setAdapterName(Constants.SERVICE_ADAPTER);
        adapter.setHandlerName(Constants.SERVICE_ADAPTER_INVOKE_HANDLER);
        
        
        var hybridServiceDatas = Object.create(HybridSiminovDatas);
        hybridServiceDatas.datas = new Array();
        
        var hybridService = Object.create(HybridSiminovDatas.HybridSiminovData);
        hybridService.datas = new Array();
        hybridService.values = new Array();
        
        hybridService.type = Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE;
        
        
        var hybridRequestId = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
        hybridRequestId.type = Constants.SERVICE_ADAPTER_INVOKE_HANDLER_REQUEST_ID;
        hybridRequestId.value = iService.getRequestId();
        
        hybridService.values.push(hybridRequestId);
        
        var hybridServiceName = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
        hybridServiceName.type = Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_NAME;
        hybridServiceName.value = iService.getService();
        
        hybridService.values.push(hybridServiceName);
        
        var hybridServiceValue = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
        hybridServiceValue.type = Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST;
        hybridServiceValue.value = iService.getRequest();
        
        hybridService.values.push(hybridServiceValue);
        
        
        var resources = iService.getResources();
        if(resources != undefined && resources != null && resources.length > 0) {
            
            var hybridResources = Object.create(HybridSiminovDatas.HybridSiminovData);
            hybridResources.datas = new Array();
            hybridResources.values = new Array();
            
            hybridResources.type = Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES;
            
            for(var i = 0;i < resources.length;i++) {
                var resourceName = resources[i];
                var resourceValue = iService.getResource(resourceName);
                if(resourceValue && !(typeof resourceValue == 'string')) {
                    continue;
                }
                
                resourceValue = '' + resourceValue;
                
                var hybridResource = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);
                hybridResource.type = resourceName;
                hybridResource.value = resourceValue;
                
                hybridResources.values.push(hybridResource);
            }
            
            hybridService.datas.push(hybridResources);
        }
        
        
        hybridServiceDatas.datas.push(hybridService);
        
        var data = encodeURI(JSON.stringify(hybridServiceDatas));
        adapter.addParameter(data);
        
        requestQueue.add(iService.getRequestId(), iService);
        if(callback) {
            adapter.setCallback(serviceCallback);
            adapter.setAdapterMode(Adapter.REQUEST_ASYNC_MODE);
            
            Adapter.invoke(adapter);		
            
            function serviceCallback() {
                callback && callback.onSuccess && callback.onSuccess();
            }	
        } else {
            Adapter.invoke(adapter);
        }
    };
    
    return {
        getRequest: getRequest,
        removeRequest: removeRequest,
        handleAsync: handleAsync,
        handle: handle
    };
};



if(dom == undefined) {
    exports.getInstance = getInstance;
}
