package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import siminov.connect.exception.ServiceException;
import siminov.connect.service.NameValuePair;
import siminov.connect.service.design.IService;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
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
		
		HybridSiminovData serviceHybridData = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_PARAMETER);
		HybridSiminovData apiHybridData = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_API_PARAMETER);
	
		HybridSiminovData resourcesHybridDatas = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(Constants.SIMINOV_SERVICE_ADAPTER_INVOKE_HANDLER_RESOURCES);
		Iterator<HybridSiminovData>	resourcesHybridData = resourcesHybridDatas.getDatas();
				
				
		String service = serviceHybridData.getDataValue();
		String api = apiHybridData.getDataValue();
		
		Map<String, String> resources = new HashMap<String, String>();
		while(resourcesHybridData.hasNext()) {
			
			HybridSiminovData resourceHybridData = resourcesHybridData.next();
			resources.put(resourceHybridData.getDataType(), resourceHybridData.getDataValue());
		}
		
		
		IService genericService = new GenericService();
		
		genericService.setService(service);
		genericService.setApi(api);
		
		for(String resource: resources.keySet()) {
			genericService.addResource(new NameValuePair(resource, resources.get(resource)));
		}
		
		genericService.invoke();
	}
}
