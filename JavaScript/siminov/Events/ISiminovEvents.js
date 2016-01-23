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
    module.exports = ISiminovEvents;
}


/**
    Exposes events to deal with life cycle of SIMINOV FRAMEWORK.
    It has methods such as (onFirstTimeSiminovInitialized, onSiminovInitialized, onSiminovStopped).

    @module Events
    @class ISiminovEvents
    @constructor
*/
function ISiminovEvents(siminovEvents) {
    
    return {

        /**
            This event gets fired when SIMINOV is initialize for first time.

            @method onFirstTimeSiminovInitialized
        */
        onFirstTimeSiminovInitialized: siminovEvents.onFirstTimeSiminovInitialized,

        /**
            This event gets fired when SIMINOV is initialize.

            @method onSiminovInitialized
        */
        onSiminovInitialized: siminovEvents.onSiminovInitialized,
        
        /**
            This event gets fired when SIMINOV is stopped.

            @method onSiminovStopped
        */
        onSiminovStopped: siminovEvents.onSiminovStopped
    }
}

