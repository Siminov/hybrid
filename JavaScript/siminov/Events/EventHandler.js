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
	It contain all Events triggered by Siminov Framework.

	@module Events	
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
    var Constants = require('../Constants');
    var Dictionary = require('../Collection/Dictionary');
    var Function = require('../Function/Function');
    var SIDatasHelper = require('../ReaderWriter/SIDatasHelper');
    var Log = require('../Log/Log');
    
    module.exports = EventHandler;
    
    win.EventHandler = EventHandler;    	
}

/**
	Any event triggered by Siminov is first handled by this function later it will deliver to appropriate Event APIs. 
	
	@module Events
	@class EventHandler
	@constructor	
*/
function EventHandler() {


	/**
		Handle event triggered by Siminov.
		
		@method triggerEvent
		@param data {String} Hybrid Data From Native
	*/
    this.triggerEvent = function(data) {
        Log.debug("EventHandler", "triggerEvent", "Data: " + data);
        
        var hybridSiminovDatas = dom == undefined?JSON.parse(eval('(' + data + ')')):JSON.parse(data);
        var datas = hybridSiminovDatas.datas;

        var functionName;
        var apiName;

        var events = new Array();
        var parameters = new Array();

		var resources = new Dictionary();

        if(datas != undefined && datas != null && datas.length > 0) {

            for(var i = 0;i < datas.length;i++) {
                var data = datas[i];

                var dataType = data.type;
                if(dataType === Constants.EVENT_HANDLER_TRIGGERED_EVENT) {
                    apiName = data.value;
                } else if(dataType === Constants.EVENT_HANDLER_EVENTS) {

                    var values = data.values;
                    if(values != undefined &&  values != null && values.length > 0) {
                        for(var j = 0;j < values.length;j++) {
                            events.push(values[j].value);
                        }
                    }

                } else if(dataType === Constants.EVENT_HANDLER_EVENT_PARAMETERS) {
					
					var parameterDatas = data.datas;
					if(parameterDatas != undefined && parameterDatas != null && parameterDatas.length > 0) {
						
						for(var j = 0;j < parameterDatas.length;j++) {

							var parameter = SIDatasHelper.toModel(parameterDatas[j]);
							parameters.push(parameter);
						}					
					}
                } 
            }
        }


        if(events != undefined && events != null && events.length > 0) {

            for(var i = 0;i < events.length;i++) {

                var obj = Function.createFunctionInstance(events[i]);
                var containEvent = obj.containProperty(apiName);
                
                if(containEvent) {
                    functionName = obj.getFunctionName();
                    break;
                }
            }
        }

	
		if(functionName == undefined || functionName == null || functionName.length <= 0) {
			Log.debug("EventHandler", "triggerEvent", "Event handler not defined, API: " + apiName);
			return;
		}


        var eventHandler = Function.createFunctionInstance(functionName);
        
        /*
         * ISiminov Events
         */
        if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED) {

            Function.invokeAndInflate(eventHandler, apiName);
        } else if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED) {

            Function.invokeAndInflate(eventHandler, apiName);
        } else if(apiName === Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_STOPPED) {

            setTimeout(Function.invokeAndInflate(eventHandler, apiName), 0);
            
        /*
         * IDatabase Events
         */
        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_CREATED) {
            
            var databaseDescriptor = parameters[0];
            Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor);
            
        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_DROPPED) {

			var databaseDescriptor = parameters[0];
			Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_CREATED) {

			var databaseDescriptor = parameters[0];
			var entityDescriptor = parameters[1];
		
			Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor, entityDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_DROPPED) {

			var databaseDescriptor = parameters[0];
			var entityDescriptor = parameters[1];
		
			Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor, entityDescriptor);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_CREATED) {

			var databaseDescriptor = parameters[0];
			var entityDescriptor = parameters[1];
			var index = parameters[2];
			
			Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor, entityDescriptor, index);

        } else if(apiName === Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_DROPPED) {

			var databaseDescriptor = parameters[0];
			var entityDescriptor = parameters[1];
			var index = parameters[2];
		
			Function.invokeAndInflate(eventHandler, apiName, databaseDescriptor, entityDescriptor, index);
			
        /*
         * INotification Events
         */
        } else if(apiName === Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_REGISTRATION) {

        	var registration = parameters[0];
			Function.invokeAndInflate(eventHandler, apiName, registration);        	
        } else if(apiName === Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_UNREGISTRATION) {
        	
        	var registration = parameters[0];
			Function.invokeAndInflate(eventHandler, apiName, registration);        	
        } else if(apiName === Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_NOTIFICATION) {
        	
        	var message = parameters[0];
			Function.invokeAndInflate(eventHandler, apiName, message);        	
        } else if(apiName === Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_ERROR) {
        	
        	var notificationException = parameters[0];
        	Function.invokeAndInflate(eventHandler, apiName, notificationException);
        
        
        /*
         * ISync Events
         */
    	} else if(apiName === Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_STARTED) {
    		
    		var syncRequest = parameters[0];
    		Function.invokeAndInflate(eventHandler, apiName, syncRequest)
    	} else if(apiName === Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_QUEUED) {
    		
    		var syncRequest = parameters[0];
    		Function.invokeAndInflate(eventHandler, apiName, syncRequest)
    	} else if(apiName === Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_REMOVED) {
    		
    		var syncRequest = parameters[0];
    		Function.invokeAndInflate(eventHandler, apiName, syncRequest)
    	} else if(apiName === Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_TERMINATED) {
    		
    		var syncRequest = parameters[0];
    		Function.invokeAndInflate(eventHandler, apiName, syncRequest)
    	}
    }
}
