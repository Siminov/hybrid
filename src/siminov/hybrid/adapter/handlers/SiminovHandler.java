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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.utils.ClassUtils;
import siminov.hybrid.Constants;
import siminov.hybrid.adapter.AdapterFactory;
import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.IHandler;
import siminov.hybrid.adapter.constants.HybridSiminovException;
import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.model.AdapterDescriptor.Handler;
import siminov.hybrid.model.AdapterDescriptor.Handler.Parameter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.resource.ResourceManager;
import siminov.hybrid.utils.DataTypeHandler;
import siminov.hybrid.writter.HybridSiminovDataWritter;
import android.app.Activity;
import android.webkit.JavascriptInterface;


/**
 * This actually deals with Request Handling Between HYBRID-TO-NATIVE and NATIVE-TO-HYBRID.
 *
 */
public class SiminovHandler extends siminov.hybrid.Siminov implements IAdapter, IHandler {

	protected siminov.core.resource.ResourceManager coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
	protected ResourceManager hybridResourceManager = ResourceManager.getInstance();

	protected AdapterFactory adapterResources = AdapterFactory.getInstance();

	@JavascriptInterface
	public String handleHybridToNative(final String action) {
		return handleHybridToNative(action, null);
	}
	
	@JavascriptInterface
	public String handleHybridToNative(final String action, final String data) {

		HybridSiminovDataReader hybridSiminovDataParser = null; 
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(AdapterHandler.class.getName(), "handleJSToNative", "SiminovException caught while parsing siminov hybrid data, " + siminovException.getMessage());
		}
		
		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();
		Collection<String> parameterValues = new LinkedList<String>();
		
		Iterator<HybridSiminovData> hybridSiminovData = hybridSiminovDatas.getHybridSiminovDatas();
		while(hybridSiminovData.hasNext()) {
			HybridSiminovData hybridData = hybridSiminovData.next();
			
			String dataValue = hybridData.getDataValue();
			parameterValues.add(dataValue);
		}
		
		String adapterDescriptorName = action.substring(0, action.indexOf("."));
		String handlerName = action.substring(action.indexOf(".") + 1, action.length());

		AdapterDescriptor adapterDescriptor = hybridResourceManager.getAdapterDescriptor(adapterDescriptorName);
		AdapterDescriptor.Handler handler = hybridResourceManager.getHandler(adapterDescriptorName, handlerName);
		
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

			return generateHybridSiminovException(siminovException.getClassName(), siminovException.getMethodName(), siminovException.getMessage());
		}

		if(returnData != null) {
			return returnData.toString();
		}
			
		return generateHybridSiminovEmptyData();

	}

	@JavascriptInterface
	public void handleNativeToHybrid(final String action, final String...data) {

		AdapterDescriptor adapterDescriptor = hybridResourceManager.getAdapterDescriptor(Constants.NATIVE_TO_HYBRID_ADAPTER);
		Handler handler = hybridResourceManager.getHandler(Constants.NATIVE_TO_HYBRID_ADAPTER, Constants.NATIVE_TO_HYBRID_ADAPTER_HANDLER);
		
		String parameters = "";
		if(data != null && data.length > 0) {
			for(int i = 0; i < data.length; i++) {
				if(i == 0) {
					parameters += "'" + data[i] + "'";
					continue;
				} 
				
				parameters += ", '" + data[i] + "',";
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
		
		if(hybridResourceManager.containAdapterBasedOnName(adapterDescriptorName)) {
			AdapterDescriptor invokeAdapterDescriptor = hybridResourceManager.getAdapterDescriptor(adapterDescriptorName);
			invokeAction = invokeAdapterDescriptor.getMapTo();
		}
		
		if(handlerName != null && handlerName.length() > 0 && hybridResourceManager.containHandler(handlerName)) {
			AdapterDescriptor.Handler invokeHandler = hybridResourceManager.getHandler(adapterDescriptorName, handlerName);
			invokeAction += "." + invokeHandler.getMapTo();
		}
		

		final AdapterDescriptor finalAdapter = adapterDescriptor;
		final Handler finalHandler = handler;
		final String finalInvokeAction = invokeAction;
		final String finalParameters = parameters;
		
		Activity webActivity = hybridResourceManager.getWebActivity();
		webActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				
				if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0 && finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					hybridResourceManager.getWebView().loadUrl("javascript: new " + finalAdapter.getMapTo() + "()." + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0) {
					hybridResourceManager.getWebView().loadUrl("javascript:" + finalAdapter.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					hybridResourceManager.getWebView().loadUrl("javascript:" + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
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
			parameterTypes.add(ClassUtils.createClass(DataTypeHandler.convert(parameter.getType())));
		}
		
		int index = 0;
		Class<?>[] parameterTypeArray = new Class<?> [parameterTypes.size()];
		for(Class<?> parameterType : parameterTypes) {
			parameterTypeArray[index++] = parameterType;
		}
		
		
		
		return parameterTypeArray;
	}

	
	private String generateHybridSiminovEmptyData() {
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		String data = null;
		
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(SiminovHandler.class.getName(), "generateHybridSiminovException", "SiminovException caught while generating empty siminov js data: " + siminovException.getMessage());
			return "{\"siminov-hybrid-data\":{}}";
		}
		
		return data;
	}
	
	private String generateHybridSiminovException(final String className, final String methodName, final String message) {

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData exception = new HybridSiminovDatas.HybridSiminovData();
		
		exception.setDataType(HybridSiminovException.SIMINOV_EXCEPTION);
		
		HybridSiminovValue classNameValue = new HybridSiminovData.HybridSiminovValue();
		classNameValue.setType(HybridSiminovException.CLASS_NAME);
		classNameValue.setValue(className);
	
		exception.addValue(classNameValue);
		

		HybridSiminovValue methodNameValue = new HybridSiminovData.HybridSiminovValue();
		methodNameValue.setType(HybridSiminovException.METHOD_NAME);
		methodNameValue.setValue(methodName);
		
		exception.addValue(methodNameValue);
		

		HybridSiminovValue messageValue = new HybridSiminovData.HybridSiminovValue();
		messageValue.setType(HybridSiminovException.MESSAGE);
		messageValue.setValue(message);

		exception.addValue(messageValue);
		
		
		hybridSiminovDatas.addHybridSiminovData(exception);
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "generateHybridSiminovException", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	

	/**
	 * It handles II part of Hybrid initialization
	 */
	public void initializeSiminov() {

		siminov.connect.Siminov.processDatabase();
		siminov.hybrid.Siminov.siminovInitialized();
	}

	
	/**
	 * It handles siminov framework shutdown request
	 */
	public void shutdownSiminov() {
		
		shutdown();	
	}
}
