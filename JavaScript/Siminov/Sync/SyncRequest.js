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
    var Dictionary = require('../Collection/Dictionary');
    var Utils = require('../Utils/Utils');
    
    module.exports = SyncRequest;
    win.SyncRequest = SyncRequest;    
}


/**
	It exposes APIs to Get and Set sync request information

	@module Sync
	@class SyncRequest
	@constructor
*/
function SyncRequest() {

    var requestId = Utils.uniqueNumber();
	var name;

	var resources = new Dictionary();
	
    this.getRequestId = function() {
        return requestId;
    }
    
    
	/**
	 * Get name of sync request
	 * 
	 * @method getName
	 * @return {String} Name of sync request
	 */
	this.getName = function() {
		return name;
	}
	
	/**
	 * Set the name of sync request
	 * 
	 * @method setName 
	 * @param val {String} Name of sync request
	 */
	this.setName = function(val) {
		name = val;
	}
	
	/**
	 * Get all resources
	 * 
	 * @method getResources
	 * @return {Array} Resources
	 */
	this.getResources = function() {
		return resources.keys();
	}

	/**
	 * Get resource based on its name
	 * 
	 * @method getResource 
	 * @param val {String} Name of the resource
	 * @return {String} Resource
	 */
	this.getResource = function(val) {
		return resources.get(val);
	}

	/**
	 * Add resource
	 * 
	 * @method addResource
	 * @param name {String} Name of the resource
	 * @param value {String} Value of the resource
	 */
	this.addResource = function(name, value) {
		resources.add(name, value);
	}

	/**
	 * Check whether it contains resource or not
	 * 
	 * @method containResource
	 * @param val {String} Name of the resource
	 * @return {boolean} (true/false) TRUE: If resource exists | FALSE: If resource does not exists
	 */
	this.containResource = function(val) {
		return resources.exists(val);
	}
}
