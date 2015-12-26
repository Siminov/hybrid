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
	It contain all Classes related to push notification.

	@module Notification
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
    module.exports = Message;
    win.Message = Message;    
}

/**
	It exposes APIs to Get and Set push notification 
	
	@module Notification
	@class Message
	@constructor
*/
function Message() {

	var message;
	
	/**
	 * Get notification message 
	 * 
	 * @method getMessage
	 * @return {String} Notification Message
	 */
	this.getMessage = function() {
		return message;
	}

	/**
	 * Set notification message
	 * 
	 * @method setMessage
	 * @param value {String} Notification Message
	 */
	this.setMessage = function(value) {
		message = value;
	}
}
