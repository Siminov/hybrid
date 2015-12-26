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
	Service is a client-side communication component that process and handles any hybrid service request. It performs long running operations in the background.
	A Service is a group of APIs which deals on one particular hybrid service.
	
	@module Service
*/


/**
	Design contain all interfaces required by service layer to deal with service request.

	@module Service
	@submodule Design
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
    module.exports = IService;    
}

/**
	It exposes APIs to Get and Set service information

	@module Service
	@submodule Design
	@class IService
	@constructor
	@param service {Service} Service class object.
*/
function IService(service) {

    return {
		
		/**
		 * Get service request id
		 * 
		 * @method getRequestId
		 * @return {String} Service Request Id
		 */
		getRequestId: service.getRequestId,
		
		/**
		 * Set service request id
		 * 
		 * @method setRequestId
		 * @param requestId {String} Service Request Id
		 */
		setRequestId: service.setRequestId,
		
		/**
		 * Get service name
		 * 
		 * @method getService
		 * @return {String} Service Name
		 */
		getService: service.getService,
		
		/**
		 * Set service name
		 * 
		 * @method setService
		 * @param service {String} Name of service
		 */
		setService: service.setService,
		
		/**
		 * Get request name
		 * 
		 * @method getRequest
		 * @return {String} Name of request
		 */
		getRequest: service.getRequest,
		
		/**
		 * Set request name
		 * 
		 * @method setRequest
		 * @param request {String} Name of request
		 */
		setRequest: service.setRequest,

		/**
		 * Get service descriptor 
		 * 
		 * @method getServiceDescriptor
		 * @return {ServiceDescriptor} Service Descriptor
		 */
		getServiceDescriptor: service.getServiceDescriptor,
		
		/**
		 * Set service descriptor
		 * 
		 * @method setServiceDescriptor
		 * @param serviceDescriptor {ServiceDescriptor} Service Descriptor
		 */
		setServiceDescriptor: service.setServiceDesciptor,
		
		/**
		 * It process the service request
		 * 
		 * @method invoke
		 */
		invoke: service.invoke,

	
		invokeAsync: service.invokeAsync,
	

		/**
		 * It terminated the executing service request
		 * 
		 * @method terminate
		 */		
		terminate: service.terminate
    }
}
