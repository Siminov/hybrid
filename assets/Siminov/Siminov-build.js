/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
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
 	Exposes methods to deal with SIMINOV HYBRID FRAMEWORK.

 		Such As

 			1. Initialize: Entry point to the SIMINOV HYBRID.

	@class Siminov
	@constructor
	
 */
function Siminov() {

}




/**
 	It is the entry point to the SIMINOV HYBRID FRAMEWORK.

 	When application starts it should call this method to activate SIMINOV HYBRID FRAMEWORK.

	Siminov will initialize all databases, and do necessary processing.

	EXAMPLE: 
          document.addEventListener("deviceready", Siminov.initialize, false);

	@Siminov
	@method initialize
	@static
	@constructor
 */
Siminov.initialize = function() {

    var adapter = new Adapter();
    adapter.setAdapterName(Constants.SIMINOV_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_ADAPTER_INITIALIZE_SIMINOV_HANDLER);

    adapter.invoke();
}



/**
	It shudown's Siminov Framework, and releases all resources acquired by Siminov.

	@method shutdown
	@static
	@constructor
*/
Siminov.shutdown = function() {

	var adapter = new Adapter();
	
    adapter.setAdapterName(Constants.SIMINOV_ADAPTER);
    adapter.setHandlerName(Constants.SIMINOV_ADAPTER_SHUTDOWN_SIMINOV_HANDLER);

    adapter.invoke();
}