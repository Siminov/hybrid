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
    module.exports = ConnectionRequest;
    
	win.ConnectionRequest = ConnectionRequest;    	
}


/**
	It contains connection request information.
	Exposes method to GET and SET connection request details.

	@module Connection
	@class ConnectionRequest
	@constructor
 */
function ConnectionRequest() {

	var url;

	var protocol;
	var type;
	
	var queryParameters = new Dictionary();
	var headerParameters = new Dictionary();
	
	var dataStream;
	

	/**
		Get Instance Url.
		
		@method getUrl
		@return {String} Url of instance.	
	*/
	this.getUrl = function() {
		return url;
	}
	
	/**
		Set Instance Url.
		
		@method setUrl
		@param value {String} Instance Url.
	*/
	this.setUrl = function(value) {
		url = value;
	}

	/**
		Get Protocol.
		
		@method getProtocol
		@return {String} Protocol of request.	
	*/
	this.getProtocol = function() {
		return protocol;
	}
	
	/**
		Set Request Protocol.
		
		@method setProtocol
		@param value {String} Request Protocol.
	*/
	this.setProtocol = function(value) {
		protocol = value;
	}
	
	/**
		Get Type.
		
		@method getType
		@return {String} Type of request.	
	*/
	this.getType = function() {
		return type;
	}
	
	/**
		Set Request Type.
		
		@method setType
		@param value {String} Reequest Type.
	*/
	this.setType = function(value) {
		type = value;
	}
	
	/**
		Get all query parameters.
		
		@method getQueryParameters
		@return {Array} Query Parameters.	
	*/
	this.getQueryParameters = function() {
		return queryParameters.keys();
	}
	
	/**
		Get query parameter.
		
		@method getQueryParameter
		@param value {String} Name of query parameter
		@return {String} Query Parameter.	
	*/
	this.getQueryParameter = function(value) {
		return queryParameters.get(value);
	}
	
	/**
	 * Add query parameter
	 * 
	 * @method addQueryParameter
	 * @param queryParameterName {String} Name of query parameter
	 * @param queryParameterValue {String} Value of query parameter
	 * 
	 */
	this.addQueryParameter = function(queryParameterName, queryParameterValue) {
		queryParameters.add(queryParameterName, queryParameterValue);
	}
	
	/**
	 * Get all header parameters
	 * 
	 * @method getHeaderParameters
	 * @return {Array} Query Parameters
	 */
	this.getHeaderParameters = function() {
		return headerParameters.keys();
	}
	
	/**
	 * Get header parameter
	 * 
	 * @method getHeaderParameter
	 * @param name {String} Name of header parameter
	 * @return {String} Header Parameter
	 */
	this.getHeaderParameter = function(name) {
		return headerParameters.get(name);
	}

	/**
	 * Add header parameter
	 * 
	 * @method addHeaderParameter
	 * @param headerParameterName {String} Name of header parameter
	 * @param headerParameterValue {String} Value of header parameter
	 */
	this.addHeaderParameter = function(headerParameterName, headerParameterValue) {
		headerParameters.add(headerParameterName, headerParameterValue);
	}

	/**
	 * Get data stream
	 * 
	 * @method getDataStream
	 * @return {String} Data Stream
	 */
	this.getDataStream = function() {
		return dataStream;
	}
	
	/**
	 * Set data stream
	 * 
	 * @method setDataStream
	 * @return {String}
	 */
	this.setDataStream = function(value) {
		dataStream = value;
	}
}
