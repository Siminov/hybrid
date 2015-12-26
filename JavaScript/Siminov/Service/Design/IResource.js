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
    module.exports = IResource;    
}

/**
	It exposes APIs to Get and Set service resources

	@module Service
	@submodule Design
	@class IResource
	@constructor
	@param resource {Resource} Resource class object.
*/
function IResource(resource) {
	
	return {
		
		/**
		 * Get all service request resources
		 * 
		 * @method getResources
		 * @return Service Request Resources
		 */
		getResources: resource.getResources,
		
		/**
		 * Get service request resource
		 * 
		 * @method getResource
		 * @return Service Request Resource
		 */
		getResource: resource.getResource,
		
		/**
		 * Add resources
		 * 
		 * @method addResources
		 * @param name {String} Name of resource
		 * @param value {String} Value of resource
		 */
		addResource: resource.addResource,
		
		/**
		 * Check whether it contains resource or not based on its name
		 * 
		 * @method containResource
		 * @param name {String} Name of resource
		 * @return {boolean} (true/false) TRUE: If it contains resource | FALSE: If it does not contain resource
		 */
		containResource: resource.containResource		
	}
}