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

package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.exception.ServiceException;
import siminov.connect.service.design.IService;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.service.GenericService;

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

		HybridSiminovDataReader hybridSiminovDataParser = null; 
		
		try {
			data = URLDecoder.decode(data, "UTF-8");
			data = URLDecoder.decode(data, "UTF-8");
			
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
			throw new ServiceException(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
		} catch(Exception exception) {
			Log.error(ServiceHandler.class.getName(), "invoke", "Exception caught while parsing siminov hybrid core data, " + exception.getMessage());
			throw new ServiceException(ServiceHandler.class.getName(), "invoke", "Exception caught while parsing siminov hybrid core data, " + exception.getMessage());
		}

		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();
		
		HybridSiminovData hybridService = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE);
		
		HybridSiminovValue hybridRequestId = hybridService.getValueBasedOnType(Constants.ADAPTER_INVOKE_HANDLER_REQUEST_ID);
		HybridSiminovValue hybridServiceName = hybridService.getValueBasedOnType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_NAME);
		HybridSiminovValue hybridRequestName = hybridService.getValueBasedOnType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST_NAME);
		
		HybridSiminovData hybridResources = hybridService.getHybridSiminovDataBasedOnDataType(Constants.ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES);
		
		
		IService genericService = new GenericService();
		
		genericService.setRequestId(Long.valueOf(hybridRequestId.getValue()));
		genericService.setService(hybridServiceName.getValue());
		genericService.setRequest(hybridRequestName.getValue());

		
		if(hybridResources != null) {
			
			Iterator<HybridSiminovValue> hybridResourceValues = hybridResources.getValues();
			while(hybridResourceValues.hasNext()) {
				
				HybridSiminovValue hybridResource = hybridResourceValues.next();
				
				String hybridResourceName = hybridResource.getType();
				Object hybridResourceValue = hybridResource.getValue();
				hybridResourceValue = URLDecoder.decode(hybridResourceValue.toString());
				
				genericService.addResource(hybridResourceName, hybridResourceValue);
			}
		}
		
		genericService.invoke();
	}
}
