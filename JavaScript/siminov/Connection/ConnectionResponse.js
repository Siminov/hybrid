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
	It is one which describes properties required to describe connection information.

	@module Connection
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
    module.exports = ConnectionResponse;
    
    win.ConnectionResponse = ConnectionResponse;    	
}


/**
	It contains connection response information.
	Exposes method to GET and SET connection response details.

	@module Connection
	@class ConnectionResponse
	@constructor
 */
function ConnectionResponse() {

	var statusCode;
	var statusMessage;

	var response;
	
	/**
	 * Get status code of response
	 * 
	 * @method getStatusCode
	 * @return {String} Status Code
	 */
	this.getStatusCode = function() {
		return statusCode;
	}
	
	/**
	 * Set status code of response
	 * 
	 * @method setStatusCode
	 * @param value {String} Status Code
	 */
	this.setStatusCode = function(value) {
		statusCode = value;
	}
	
	/**
	 * Get status message of response
	 * 
	 * @method getStatusMessage
	 * @return {String} Status Message
	 */
	this.getStatusMessage = function() {
		return statusMessage;
	}
	
	/**
	 * Set status message of response
	 * 
	 * @method setStatusMessage
	 * @param value {String} Status Message
	 */
	this.setStatusMessage = function(value) {
		statusMessage = value;
	}

	/**
	 * Get response content
	 * 
	 * @method getResponse
	 * @return {String} Response content
	 */	
	this.getResponse = function() {
		return response;
	}
	
	/**
	 * Set response content
	 * 
	 * @method setResponse
	 * @param value {String} Response Content
	 */
	this.setResponse = function(value) {
		response = value;
	}
}
