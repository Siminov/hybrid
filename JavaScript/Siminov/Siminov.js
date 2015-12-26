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


/*
    Import Required Siminov
 */

if(dom != undefined) {
    document.write('<script type="text/javascript" src="Siminov/Import.js"></script>');
} else {
    
    var Constants = require('./Constants');
    var Callback = require('./Callback');
    
    
    /*
     * Adapter
     */
    var Adapter = require('./Adapter/Adapter');
    
    
    /*
     * Events
     */
    var EventHandler = require('./Events/EventHandler');
    
    
    /*
     * Service
     */
    var ServiceEventHandler = require('./Service/ServiceEventHandler');
    
    
    /*
     * Collection
     */
    var Array = require('./Collection/Array');
    var String = require('./Collection/String');
    
    
    /*
     * Models
     */
    var ApplicationDescriptor = require('./Model/ApplicationDescriptor');
    var AdapterDescriptor = require('./Model/AdapterDescriptor');
    var DatabaseDescriptor = require('./Model/DatabaseDescriptor');
    var EntityDescriptor = require('./Model/EntityDescriptor');
    var LibraryDescriptor = require('./Model/LibraryDescriptor');
    var NotificationDescriptor = require('./Model/NotificationDescriptor');
    var ServiceDescriptor = require('./Model/ServiceDescriptor');
    var SyncDescriptor = require('./Model/SyncDescriptor');
    
    
    /*
     * Connection
     */
    var ConnectionRequest = require('./Connection/ConnectionRequest');
    var ConnectionResponse = require('./Connection/ConnectionResponse');
    
    
    /*
     * Sync
     */
    var SyncRequest = require('./Sync/SyncRequest');
    
    module.exports = Siminov;
}



/**
 	Exposes methods to deal with SIMINOV HYBRDI FRAMEWORK.

 		Such As

 			1. Initialize: Entry point to the SIMINOV HYBRID.

	@class Siminov
	@constructor
	
 */
function Siminov() {

}



/**
 	It is the entry point to the SIMINOV HYBRID FRAMEWORK.

 	When application starts it should call this method to activate SIMINOV HYBRID FRAMEWORK.

	Siminov will initialize all databases, and do necessary processing.

	EXAMPLE: 
          document.addEventListener("deviceready", Siminov.initialize, false);
	
	@class Siminov
	@method initialize
	@static
	@constructor
 */
Siminov.initialize = function() {

	var callback = arguments && arguments[0];
	
    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_ADAPTER_INITIALIZE_SIMINOV_HANDLER);

	if(callback) {
	
		adapter.setAdapterMode(Adapter.REQUEST_ASYNC_MODE);
		adapter.setCallback(initializeCallback);
		
	    Adapter.invoke(adapter);
	    
	    function initializeCallback(data) {
			callback && callback.onSuccess && callback.onSuccess(data);
	    }	
	} else {
	    Adapter.invoke(adapter);
	}
}


Siminov.initializeAsync = function(callback) {
	Siminov.initialize(callback?callback:new Callback());
}


/**
	It shudown's Siminov Framework, and releases all resources acquired by Siminov.

	@class Siminov
	@method shutdown
	@static
	@constructor
*/
Siminov.shutdown = function() {

	var adapter = new Adapter();
	
    adapter.setAdapterName(Constants.SIMINOV_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_ADAPTER_SHUTDOWN_SIMINOV_HANDLER);

    Adapter.invoke(adapter);
}


Siminov.shutdownAsync = function(onSuccess, onError) {
}