package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.Iterator;

import siminov.connect.exception.ServiceException;
import siminov.connect.service.NameValuePair;
import siminov.connect.service.design.IService;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.service.GenericService;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;

public class ServiceHandler implements IAdapter {

	public void invoke(String data) throws ServiceException {

		HybridSiminovDataReader hybridSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
			throw new ServiceException(ServiceHandler.class.getName(), "invoke", "SiminovException caught while parsing siminov hybrid core data, " + siminovException.getMessage());
		}

		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();
		
		HybridSiminovData hybridService = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE);
		
		HybridSiminovValue hybridServiceName = hybridService.getValueBasedOnType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_NAME);
		HybridSiminovValue hybridAPIName = hybridService.getValueBasedOnType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_API_NAME);
		
		HybridSiminovData hybridResources = hybridService.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES);
		
		
		IService genericService = new GenericService();
		
		genericService.setService(hybridServiceName.getValue());
		genericService.setApi(hybridAPIName.getValue());

		
		if(hybridResources != null) {
			
			Iterator<HybridSiminovValue> hybridResourceValues = hybridResources.getValues();
			while(hybridResourceValues.hasNext()) {
				
				HybridSiminovValue hybridResource = hybridResourceValues.next();
				
				String hybridResourceName = hybridResource.getType();
				Object hybridResourceValue = hybridResource.getValue();
				hybridResourceValue = URLDecoder.decode(hybridResourceValue.toString());
				
				genericService.addResource(new NameValuePair(hybridResourceName, hybridResourceValue));
			}
		}
		
		genericService.invoke();
	}
}
