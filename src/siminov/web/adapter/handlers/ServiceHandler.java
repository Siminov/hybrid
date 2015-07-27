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

package siminov.web.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.exception.ServiceException;
import siminov.connect.service.design.IService;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.web.Constants;
import siminov.web.adapter.IAdapter;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.reader.WebSiminovDataReader;
import siminov.web.service.GenericService;

/**
 * It handles all calls related to service
 */
public class ServiceHandler implements IAdapter {

	/**
	 * It invoke requested service 
	 * @param data Data Parameter
	 * @throws ServiceException If any exception occur while processing service request
	 */
	public void invoke(String data) throws ServiceException {

		WebSiminovDataReader webSiminovDataParser = null; 
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			data = URLDecoder.decode(data, "UTF-8");
			
			webSiminovDataParser = new WebSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov web core data, " + siminovException.getMessage());
			throw new ServiceException(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov web core data, " + siminovException.getMessage());
		} catch(Exception exception) {
			Log.error(ServiceHandler.class.getName(), "invoke", "Exception caught while parsing siminov web core data, " + exception.getMessage());
			throw new ServiceException(ServiceHandler.class.getName(), "invoke", "Exception caught while parsing siminov web core data, " + exception.getMessage());
		}

		WebSiminovDatas webSiminovDatas = webSiminovDataParser.getDatas();
		
		WebSiminovData webService = webSiminovDatas.getWebSiminovDataBasedOnDataType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE);
		
		WebSiminovValue webServiceName = webService.getValueBasedOnType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_NAME);
		WebSiminovValue webRequestName = webService.getValueBasedOnType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST_NAME);
		
		WebSiminovData webResources = webService.getWebSiminovDataBasedOnDataType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES);
		
		
		IService genericService = new GenericService();
		
		genericService.setService(webServiceName.getValue());
		genericService.setRequest(webRequestName.getValue());

		
		if(webResources != null) {
			
			Iterator<WebSiminovValue> webResourceValues = webResources.getValues();
			while(webResourceValues.hasNext()) {
				
				WebSiminovValue webResource = webResourceValues.next();
				
				String webResourceName = webResource.getType();
				Object webResourceValue = webResource.getValue();
				webResourceValue = URLDecoder.decode(webResourceValue.toString());
				
				genericService.addResource(webResourceName, webResourceValue);
			}
		}
		
		genericService.invoke();
	}
}
