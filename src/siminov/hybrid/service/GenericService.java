package siminov.hybrid.service;

import java.util.Iterator;

import siminov.connect.design.connection.IConnectionRequest;
import siminov.connect.design.connection.IConnectionResponse;
import siminov.connect.design.service.IService;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.API;
import siminov.connect.service.Service;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.Adapter;
import siminov.hybrid.adapter.constants.HybridServiceHandler;
import siminov.hybrid.events.SiminovEventHandler;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.resource.Resources;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ClassUtils;

public class GenericService extends Service {

	public void onServiceStart() {
		
		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceStart();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);

		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_START);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceStart", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceQueue() {

		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceQueue();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);

		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_QUEUE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceQueue", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServicePause() {

		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServicePause();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_PAUSE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceResume() {

		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceResume();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_RESUME);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceResume", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceFinish() {

		siminov.connect.resource.Resources conncetResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_PAUSE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceApiInvoke(IConnectionRequest connectionRequest) {

		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		Resources hybridResources = Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceApiInvoke(connectionRequest);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_API_INVOKE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridConnectionRequest(connectionRequest));

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiInvoke", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceApiFinish(IConnectionResponse connectionResponse) {

		siminov.connect.resource.Resources connectResources = siminov.connect.resource.Resources.getInstance();
		Resources hybridResources = Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceApiFinish(connectionResponse);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_API_FINISH);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);

		hybridSiminovDatas.addHybridSiminovData(hybridResources.generateHybridConnectionResponse(connectionResponse));
		
		
		//Event Resources
		HybridSiminovData serviceResources = new HybridSiminovData();
		serviceResources.setDataType(HybridServiceHandler.ISERVICE_RESOURCES);
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			Object resourceValue = getResource(resourceName);
			
			HybridSiminovData serviceResource = new HybridSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue.toString());
			
			serviceResources.addData(serviceResource);
		}

		hybridSiminovDatas.addHybridSiminovData(serviceResources);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiFinish", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	public void onServiceTerminate(ServiceException serviceException) {

		siminov.connect.resource.Resources conncetResources = siminov.connect.resource.Resources.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResources.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		API api = serviceDescriptor.getApi(getApi());
		String apiHandler = api.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onServiceFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		HybridSiminovData hybridAPIHandler = new HybridSiminovData();
		hybridAPIHandler.setDataType(HybridServiceHandler.ISERVICE_API_HANDLER);
		hybridAPIHandler.setDataValue(apiHandler);

		hybridSiminovDatas.addHybridSiminovData(hybridAPIHandler);
		
		//Triggered Event
		HybridSiminovData triggeredEvent = new HybridSiminovData();
		
		triggeredEvent.setDataType(HybridServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(HybridServiceHandler.ISERVICE_ON_SERVICE_TERMINATE);
		
		hybridSiminovDatas.addHybridSiminovData(triggeredEvent);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.HYBRID_SIMINOV_SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
	
	private IService getNativeHandler(String apiHandler) {
		
		Class<?> classObject = null;
		try {
			classObject = Class.forName(apiHandler);
		} catch(Exception exception) {
			Log.debug(ClassUtils.class.getName(), "getNativeHandlerEvent", "Exception caught while creating service native handler object, API-HANDLER: " + apiHandler + ", " + exception.getMessage());
			return null;
		}
		

		Object object = null;
		try {
			object = classObject.newInstance();
		} catch(Exception exception) {
			Log.debug(ClassUtils.class.getName(), "getNativeHandlerEvent", "Exception caught while creating service native handler object, API-HANDLER: " + apiHandler + ", " + exception.getMessage());
			return null;
		}

		return (IService) object;
	}
}
