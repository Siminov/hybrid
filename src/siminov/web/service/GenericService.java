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

package siminov.web.service;

import java.util.Iterator;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.ServiceException;
import siminov.connect.model.ServiceDescriptor;
import siminov.connect.model.ServiceDescriptor.Request;
import siminov.connect.service.Service;
import siminov.connect.service.design.IService;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.utils.ClassUtils;
import siminov.web.Constants;
import siminov.web.adapter.Adapter;
import siminov.web.adapter.constants.WebServiceHandler;
import siminov.web.events.SiminovEventHandler;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.resource.ResourceManager;
import siminov.web.writter.WebSiminovDataWritter;

/**
 * It is a Generic Service handler on behalf of web service request
 */
public class GenericService extends Service {

	/**
	 * It handles generic service start event, and delivers the call to registered classes
	 */
	public void onStart() {
		
		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onStart();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);

		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_START);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceStart", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service queue event, and delivers the call to registered classes
	 */
	public void onQueue() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onQueue();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);

		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_QUEUE);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceQueue", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service pause event, and delivers the call to registered classes
	 */
	public void onPause() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onPause();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_PAUSE);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);


		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service resume event, and delivers the call to registered classes
	 */
	public void onResume() {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onResume();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_RESUME);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceResume", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service finish event, and delivers the call to registered classes
	 */
	public void onFinish() {

		siminov.connect.resource.ResourceManager conncetResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_PAUSE);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service request invoke event, and delivers the call to registered classes
	 */
	public void onRequestInvoke(IConnectionRequest connectionRequest) {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		ResourceManager webResourceManager = ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onRequestInvoke(connectionRequest);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_REQUEST_INVOKE);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		webSiminovDatas.addWebSiminovData(webResourceManager.generateWebConnectionRequest(connectionRequest));

		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiInvoke", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}

	/**
	 * It handles generic service request finish event, and delivers the call to registered classes
	 */
	public void onRequestFinish(IConnectionResponse connectionResponse) {

		siminov.connect.resource.ResourceManager connectResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		ResourceManager webResourceManager = ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = connectResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onRequestFinish(connectionResponse);
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_REQUEST_FINISH);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);

		webSiminovDatas.addWebSiminovData(webResourceManager.generateWebConnectionResponse(connectionResponse));
		
		
		//Event Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServiceApiFinish", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
		adapter.addParameter(data);
		
		adapter.invoke();
	}
	/**
	 * It handles generic service terminate event, and delivers the call to registered classes
	 */
	public void onTerminate(ServiceException serviceException) {

		siminov.connect.resource.ResourceManager conncetResourceManager = siminov.connect.resource.ResourceManager.getInstance();
		
		ServiceDescriptor serviceDescriptor = getServiceDescriptor();
		if(serviceDescriptor == null) {
			serviceDescriptor = conncetResourceManager.requiredServiceDescriptorBasedOnName(getService());
			setServiceDescriptor(serviceDescriptor);
		}
		

		Request request = serviceDescriptor.getRequest(getRequest());
		String apiHandler = request.getHandler();

		/*
		 * Invoke Native Handler
		 */
		IService service = getNativeHandler(apiHandler);
		if(service != null) {
			service.onFinish();
		}
		
		
		/*
		 * Invoke Web Handler
		 */
		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		
		WebSiminovData webAPIHandler = new WebSiminovData();
		webAPIHandler.setDataType(WebServiceHandler.ISERVICE_API_HANDLER);
		webAPIHandler.setDataValue(apiHandler);

		webSiminovDatas.addWebSiminovData(webAPIHandler);
		
		//Triggered Event
		WebSiminovData triggeredEvent = new WebSiminovData();
		
		triggeredEvent.setDataType(WebServiceHandler.TRIGGERED_EVENT);
		triggeredEvent.setDataValue(WebServiceHandler.ISERVICE_ON_TERMINATE);
		
		webSiminovDatas.addWebSiminovData(triggeredEvent);
		
		//Add Resources
		WebSiminovData serviceResources = new WebSiminovData();
		serviceResources.setDataType(WebServiceHandler.ISERVICE_RESOURCES);
		
		
		Iterator<String> resources = getResources();
		while(resources.hasNext()) {
			String resourceName = resources.next();
			String resourceValue = (String) getResource(resourceName);
			
			WebSiminovData serviceResource = new WebSiminovData();
			serviceResource.setDataType(resourceName);
			serviceResource.setDataValue(resourceValue);
			
			serviceResources.addData(serviceResource);
		}

		webSiminovDatas.addWebSiminovData(serviceResources);

		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovEventHandler.class.getName(), "onServicePause", "SiminovException caught while generating json: " + siminovException.getMessage());
		}
		
		
		Adapter adapter = new Adapter();
		adapter.setAdapterName(Constants.SERVICE_EVENT_HANDLER_ADAPTER);
		adapter.setHandlerName(Constants.SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER);
		
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
