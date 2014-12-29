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
	Service is a client-side communication component that process and handles any web service request. It performs long running operations in the background.
	A Service is a group of APIs which deals on one particular web service.
	
	@module Service
*/



/**
	It exposes APIs to Get and Set service information by extending IService

	@module Service
	@class Service
	@constructor
*/
function Service() {
	
	var requestId;
	
	var service;
	var request;
	
	var resources = new Dictionary();

	var serviceDescriptor;	
	
	
	/*
	 * IService APIs
	 */
	 
	/**
	 * Get request id
	 * 
	 * @method getRequestId
	 * @return Request Id
	 */ 
	this.getRequestId = function() {
		return requestId;
	}
	
	/**
	 * Set request id
	 * 
	 * @method setRequestId
	 * @param val {String} Request Id
	 */
	this.setRequestId = function(val) {
		requestId = val;
	}
	
	/**
	 * Get service name
	 * 
	 * @method getService
	 * @return {String} Name of service
	 */
	this.getService = function() {
		return service;
	}
	
	/**
	 * Set service name
	 * 
	 * @method setService
	 * @param val {String} Name of service
	 */
	this.setService = function(val) {
		service = val;
	}
	
	/**
	 * Get service request name
	 * 
	 * @method getRequest
	 * @return {String} Name of Service Request
	 */
	this.getRequest = function() {
		return request;
	}
	
	/**
	 * Set 
	 */
	this.setRequest = function(val) {
		request = val;
	}
	
	/**
	 * Get service descriptor
	 * 
	 * @method getServiceDescriptor
	 * @return {ServiceDescriptor} Service Descriptor
	 */
	this.getServiceDescriptor = function() {
		return serviceDescriptor;
	}
	
	/**
	 * Set service descriptor
	 * 
	 * @method setServiceDescriptor
	 * @param val {ServiceDescriptor} Service Descriptor
	 */
	this.setServiceDescriptor = function(val) {
		serviceDescriptor = val;
	}
	
	/**
	 * It invokes the respective service request
	 * 
	 * @method invoke
	 */
	this.invoke = function() {

		var serviceHandler = ServiceHandler.getInstance();
		try {
			serviceHandler.handle(this);
		} catch(se) {
			alert(se);
			this.onServiceTerminate(se);
		}
	}
	
	
	/*
	 * IResource APIs
	 */
	 
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
	 * Get resource based on name
	 * 
	 * @method getResource
	 * @param val {String} Name of resource
	 * @return {String} Resource
	 */
	this.getResource = function(val) {
		return resources.get(val);
	}
	
	/**
	 * Add resource
	 * 
	 * @method addResource 
	 * @param name {String} Name of resource
	 * @param value {String} Value of resource
	 */
	this.addResource = function(name, value) {
		resources.add(name, value);
	}


	/**
	 * Check whether it contain resource or not
	 * 
	 * @method containResource
	 * @param val {String} Name of resource
	 * @return {boolean} (true/false) TRUE: If resource exists | FALSE: If resource does not exists
	 */
	this.containResource = function(val) {
		return resources.exists(val);
	}
}