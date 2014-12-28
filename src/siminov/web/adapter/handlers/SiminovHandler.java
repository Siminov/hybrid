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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.utils.ClassUtils;
import siminov.web.Constants;
import siminov.web.adapter.AdapterFactory;
import siminov.web.adapter.AdapterHandler;
import siminov.web.adapter.IAdapter;
import siminov.web.adapter.IHandler;
import siminov.web.adapter.constants.WebSiminovException;
import siminov.web.model.AdapterDescriptor;
import siminov.web.model.AdapterDescriptor.Handler;
import siminov.web.model.AdapterDescriptor.Handler.Parameter;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.reader.WebSiminovDataReader;
import siminov.web.resource.ResourceManager;
import siminov.web.writter.WebSiminovDataWritter;
import android.app.Activity;
import android.webkit.JavascriptInterface;


/**
 * This actually deals with Request Handling Between WEB-TO-NATIVE and NATIVE-TO-WEB.
 *
 */
public class SiminovHandler extends siminov.web.Siminov implements IAdapter, IHandler {

	protected siminov.core.resource.ResourceManager ormResourceManager = siminov.core.resource.ResourceManager.getInstance();
	protected ResourceManager webResourceManager = ResourceManager.getInstance();

	protected AdapterFactory adapterResources = AdapterFactory.getInstance();

	@JavascriptInterface
	public String handleWebToNative(final String action) {
		return handleWebToNative(action, null);
	}
	
	@JavascriptInterface
	public String handleWebToNative(final String action, final String data) {

		WebSiminovDataReader webSiminovDataParser = null; 
		try {
			webSiminovDataParser = new WebSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(AdapterHandler.class.getName(), "handleJSToNative", "SiminovException caught while parsing siminov web data, " + siminovException.getMessage());
		}
		
		WebSiminovDatas webSiminovDatas = webSiminovDataParser.getDatas();
		Collection<String> parameterValues = new LinkedList<String>();
		
		Iterator<WebSiminovData> webSiminovData = webSiminovDatas.getWebSiminovDatas();
		while(webSiminovData.hasNext()) {
			WebSiminovData webData = webSiminovData.next();
			
			String dataValue = webData.getDataValue();
			parameterValues.add(dataValue);
		}
		
		String adapterDescriptorName = action.substring(0, action.indexOf("."));
		String handlerName = action.substring(action.indexOf(".") + 1, action.length());

		AdapterDescriptor adapterDescriptor = webResourceManager.getAdapterDescriptor(adapterDescriptorName);
		AdapterDescriptor.Handler handler = webResourceManager.getHandler(adapterDescriptorName, handlerName);
		
		Iterator<Parameter> parameters = handler.getParameters();
		Class<?>[] parameterTypes = getParameterTypes(parameters);
		Object[] parameterObjects = null;
		try {
			parameterObjects = createAndInflateParameter(parameterTypes, parameterValues.iterator());
		} catch(SiminovException siminovException) {
			Log.error(AdapterHandler.class.getName(), "", "SiminovException caught while create and inflate parameters, " + siminovException.getMessage());
		}
		

		Object adapterInstanceObject = null;
		Method handlerInstanceObject = null;
		
		boolean cache = adapterDescriptor.isCache();
		if(cache) {
			adapterInstanceObject = adapterResources.requireAdapterInstance(adapterDescriptorName);
			handlerInstanceObject = (Method) adapterResources.requireHandlerInstance(adapterDescriptorName, handlerName, parameterTypes);
		} else {
			adapterInstanceObject = adapterResources.getAdapterInstance(adapterDescriptorName);
			handlerInstanceObject = (Method) adapterResources.getHandlerInstance(adapterDescriptorName, handlerName, parameterTypes);
		}


		Object returnData = null;
			
		try {
			returnData = ClassUtils.invokeMethod(adapterInstanceObject, handlerInstanceObject, parameterObjects);
		} catch(SiminovException siminovException) {
			Log.error(AdapterHandler.class.getName(), "", "SiminovException caught while invoking handler, " + siminovException.getMessage());

			return generateWebSiminovException(siminovException.getClassName(), siminovException.getMethodName(), siminovException.getMessage());
		}

		if(returnData != null) {
			return returnData.toString();
		}
			
		return generateWebSiminovEmptyData();

	}

	@JavascriptInterface
	public void handleNativeToWeb(final String action, final String...data) {

		AdapterDescriptor adapterDescriptor = webResourceManager.getAdapterDescriptor(Constants.NATIVE_TO_WEB_ADAPTER);
		Handler handler = webResourceManager.getHandler(Constants.NATIVE_TO_WEB_ADAPTER, Constants.NATIVE_TO_WEB_ADAPTER_HANDLER);
		
		String parameters = "";
		if(data != null && data.length > 0) {
			for(int i = 0; i < data.length; i++) {
				if(i == 0) {
					parameters += "'" + data[i] + "'";
					continue;
				} 
				
				parameters += ", '" + data[i] + ",";
			}
		}
		
		String adapterDescriptorName = "";
		String handlerName = "";
		
		
		int indexOfHandler = action.indexOf(".");
		if(indexOfHandler > 0) {
			adapterDescriptorName = action.substring(0, indexOfHandler);
			handlerName = action.substring(action.indexOf(".") + 1, action.length());
		} else {
			adapterDescriptorName = action;
		}
		
		String invokeAction = "";
		
		if(webResourceManager.containAdapterBasedOnName(adapterDescriptorName)) {
			AdapterDescriptor invokeAdapterDescriptor = webResourceManager.getAdapterDescriptor(adapterDescriptorName);
			invokeAction = invokeAdapterDescriptor.getMapTo();
		}
		
		if(handlerName != null && handlerName.length() > 0 && webResourceManager.containHandler(handlerName)) {
			AdapterDescriptor.Handler invokeHandler = webResourceManager.getHandler(adapterDescriptorName, handlerName);
			invokeAction += "." + invokeHandler.getMapTo();
		}
		

		final AdapterDescriptor finalAdapter = adapterDescriptor;
		final Handler finalHandler = handler;
		final String finalInvokeAction = invokeAction;
		final String finalParameters = parameters;
		
		Activity webActivity = webResourceManager.getWebActivity();
		webActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				
				if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0 && finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					webResourceManager.getWebView().loadUrl("javascript: new " + finalAdapter.getMapTo() + "()." + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0) {
					webResourceManager.getWebView().loadUrl("javascript:" + finalAdapter.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					webResourceManager.getWebView().loadUrl("javascript:" + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				}
			}
		});

		
	}

	private Object[] createAndInflateParameter(Class<?>[] parameterTypes, Iterator<String> parameterValues) throws SiminovException {
		
		Collection<Object> parameters = new LinkedList<Object> ();

		int count = 0;
		while(parameterValues.hasNext()) {

			Class<?> parameterType = parameterTypes[count++];
			String parameterValue = parameterValues.next();
			
			Object parameter = null;

			if(parameterValue == null) {
				parameters.add(parameter);
				continue;
			}
			
			
			if(String.class == parameterType) {
				parameter = parameterValue;
			} else if(int.class == parameterType || Integer.class == parameterType) {
				parameter = Integer.getInteger(parameterValue);
			} else if(long.class == parameterType || Long.class == parameterType) {
				parameter = Long.getLong(parameterValue);
			} else if(float.class == parameterType || Float.class == parameterType) {
				parameter = Float.parseFloat(parameterValue);
			} else if(double.class == parameterType || Double.class == parameterType) {
				parameter = Double.parseDouble(parameterValue);
			} else if(String[].class == parameterType) {
				
				StringTokenizer dataValues = new StringTokenizer(parameterValue, ",");
				parameter = Array.newInstance(String.class, dataValues.countTokens());

				int index = 0;
				while(dataValues.hasMoreElements()) {
					Array.set(parameter, index++, dataValues.nextElement());
				}
				
			} else if(int[].class == parameterType) {
				
				StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
				parameter = Array.newInstance(int.class, dataValues.countTokens());
				
				int index = 0;
				while(dataValues.hasMoreElements()) {
					Array.set(parameter, index++, Integer.getInteger((String) dataValues.nextElement()));
				}
				
			} else if(long.class == parameterType || Long.class == parameterType) {

				StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
				parameter = Array.newInstance(long.class, dataValues.countTokens());
				
				int index = 0;
				while(dataValues.hasMoreElements()) {
					Array.set(parameter, index++, Long.getLong((String) dataValues.nextElement()));
				}

			} else if(float.class == parameterType || Float.class == parameterType) {
			
				StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
				parameter = Array.newInstance(float.class, dataValues.countTokens());
				
				int index = 0;
				while(dataValues.hasMoreElements()) {
					Array.set(parameter, index++, Float.parseFloat((String) dataValues.nextElement()));
				}

			} else if(double.class == parameterType || Double.class == parameterType) {

				StringTokenizer dataValues = new StringTokenizer(parameterValue, ", ");
				parameter = Array.newInstance(int.class, dataValues.countTokens());
				
				int index = 0;
				while(dataValues.hasMoreElements()) {
					Array.set(parameter, index++, Double.parseDouble((String) dataValues.nextElement()));
				}
				
			}

			parameters.add(parameter);
		}	
		
		
		return parameters.toArray();
	}

	private Class<?>[] getParameterTypes(Iterator<Parameter> parameters) {
		
		Collection<Class<?>> parameterTypes = new LinkedList<Class<?>> ();
		while(parameters.hasNext()) {
			Parameter parameter = parameters.next();
			parameterTypes.add(ClassUtils.createClass(parameter.getType()));
		}
		
		int index = 0;
		Class<?>[] parameterTypeArray = new Class<?> [parameterTypes.size()];
		for(Class<?> parameterType : parameterTypes) {
			parameterTypeArray[index++] = parameterType;
		}
		
		
		
		return parameterTypeArray;
	}

	
	private String generateWebSiminovEmptyData() {
		
		WebSiminovDatas jsSiminovDatas = new WebSiminovDatas();
		String data = null;
		
		try {
			data = WebSiminovDataWritter.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovHandler.class.getName(), "generateWebSiminovException", "SiminovException caught while generating empty siminov js data: " + siminovException.getMessage());
			return "{\"siminov-web-data\":{}}";
		}
		
		return data;
	}
	
	private String generateWebSiminovException(final String className, final String methodName, final String message) {

		WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
		WebSiminovData exception = new WebSiminovDatas.WebSiminovData();
		
		exception.setDataType(WebSiminovException.SIMINOV_EXCEPTION);
		
		WebSiminovValue classNameValue = new WebSiminovData.WebSiminovValue();
		classNameValue.setType(WebSiminovException.CLASS_NAME);
		classNameValue.setValue(className);
	
		exception.addValue(classNameValue);
		

		WebSiminovValue methodNameValue = new WebSiminovData.WebSiminovValue();
		methodNameValue.setType(WebSiminovException.METHOD_NAME);
		methodNameValue.setValue(methodName);
		
		exception.addValue(methodNameValue);
		

		WebSiminovValue messageValue = new WebSiminovData.WebSiminovValue();
		messageValue.setType(WebSiminovException.MESSAGE);
		messageValue.setValue(message);

		exception.addValue(messageValue);
		
		
		webSiminovDatas.addWebSiminovData(exception);
		
		
		String data = null;
		try {
			data = WebSiminovDataWritter.jsonBuidler(webSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "generateWebSiminovException", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	

	/**
	 * It handles II part of Web initialization
	 */
	public void initializeSiminov() {

		siminov.connect.Siminov.processDatabase();
		siminov.web.Siminov.siminovInitialized();
	}

	
	/**
	 * It handles siminov framework shutdown request
	 */
	public void shutdownSiminov() {
		
		shutdown();	
	}
}
