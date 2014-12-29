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
	It exposes APIs to process service request

	@module Service
	@class ServiceHandler
	@constructor
*/
var ServiceHandler = (function() {

	var serviceHandler;
	
	return {
	
		/**
		 * Get singleton instance of Service Handler class
		 * 
		 * @method getInstance
		 * @return {ServiceHandler} Singleton instance of Service Handler
		 */
		getInstance: function() {
			if(serviceHandler == null) {
				serviceHandler = new ServiceHandler();
				
				serviceHandler.constructor = null;
			}
			
			return serviceHandler;
		}
	}
	

	function ServiceHandler() {
		
		/**
		 * It handles the service request 
		 * 
		 * @method handler
		 * @param iService {Service} Service instance
		 */
		this.handle = function(iService) {

	        var adapter = new Adapter();
	        adapter.setAdapterName(Constants.SERVICE_ADAPTER);
	        adapter.setHandlerName(Constants.SERVICE_ADAPTER_INVOKE_HANDLER);
			

			var webServiceDatas = new WebSiminovDatas();

			var webService = new WebSiminovDatas.WebSiminovData();
				webService.setDataType(Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE);

				
				var webServiceName = new WebSiminovDatas.WebSiminovData.WebSiminovValue();
				webServiceName.setType(Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_NAME);
				webServiceName.setValue(iService.getService());
			
			webService.addValue(webServiceName)
			
				var webServiceValue = new WebSiminovDatas.WebSiminovData.WebSiminovValue();
				webServiceValue.setType(Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_API);	
				webServiceValue.setValue(iService.getApi());

			webService.addValue(webServiceValue);


				var resources = iService.getResources();
				if(resources != undefined && resources != null && resources.length > 0) {
					
					var webResources = new WebSiminovDatas.WebSiminovData();
						webResources.setDataType(Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES);
					
					for(var i = 0;i < resources.length;i++) {
						var resourceName = resources[i];
						var resourceValue = iService.getResource(resourceName);
						
						resourceValue = '' + resourceValue;
						
						var webResource = new WebSiminovDatas.WebSiminovData.WebSiminovValue();
						webResource.setType(resourceName);
						webResource.setValue(resourceValue);
						
						webResources.addValue(webResource);
					}				
		
					webService.addData(webResources);	
				}


			webServiceDatas.addWebSiminovData(webService)
			
			var data = encodeURI(SIJsonHelper.toJson(webServiceDatas));
			adapter.addParameter(data);

			adapter.invoke();
		}
	}
		
}) ();


