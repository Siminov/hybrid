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
    module.exports = ISyncEvents;
}


/**
    It is a blue print for class which handles sync events

    @module Events
    @class ISyncEvents
    @constructor
*/
function ISyncEvents(syncEvents) {
    
    return {

        /**
            This method is called then a Sync is started.
            In this you can initialize resources related to Sync.
            Once OnStart has finished, Connect will call OnQueue.

            @method onStart
            @param syncRequest {ISyncRequest} Sync Request
        */
        onStart: syncEvents.onStart,
        
        /**
            This method is called then the Sync request is added to the Queue.

            @method onQueue
            @param syncRequest {ISyncRequest} Sync Request
        */
        onQueue: syncEvents.onQueue,
        
        /**
            This method is called then Sync request completes its all synchronization data with web service.

            @method onFinish
            @param syncRequest {ISyncRequest} Sync Request
        */
        onFinish: syncEvents.onFinish,
        
        /**
            This method is called if there is any error/exception while synchronizing data with web service

            @method onTerminate
            @param syncRequest {ISyncRequest} Sync Request
        */
        onTerminate: syncEvents.onTerminate
    }
}



