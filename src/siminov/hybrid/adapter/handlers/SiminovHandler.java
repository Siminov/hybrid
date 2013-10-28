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

package siminov.hybrid.adapter.handlers;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import siminov.hybrid.Constants;
import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.adapter.AdapterResources;
import siminov.hybrid.adapter.IHandler;
import siminov.hybrid.adapter.constants.HybridSiminovException;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler.Parameter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataBuilder;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.resource.Resources;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.utils.ClassUtils;
import android.app.Activity;
import android.webkit.JavascriptInterface;


/**
 * This actually deals with Request Handling Between WEB-TO-NATIVE and NATIVE-TO-WEB.
 *
 */
public class SiminovHandler extends siminov.hybrid.Siminov implements IHandler {

	protected siminov.orm.resource.Resources ormResources = siminov.orm.resource.Resources.getInstance();
	protected Resources hybridResources = Resources.getInstance();

	protected static AdapterResources adapterResources = AdapterResources.getInstance();

	@JavascriptInterface
	public String handleWebToNative(final String action) {
		return handleWebToNative(action, null);
	}
	
	@JavascriptInterface
	public String handleWebToNative(final String action, final String data) {

		HybridSiminovDataReader hybridSiminovDataParser = null; 
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.loge(AdapterHandler.class.getName(), "handleJSToNative", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}
		
		HybridSiminovDatas hybridSiminovDatas = hybridSiminovDataParser.getDatas();
		Collection<String> parameterValues = new LinkedList<String>();
		
		Iterator<HybridSiminovData> hybridSiminovData = hybridSiminovDatas.getHybridSiminovDatas();
		while(hybridSiminovData.hasNext()) {
			HybridSiminovData hybridData = hybridSiminovData.next();
			
			String dataValue = hybridData.getDataValue();
			parameterValues.add(dataValue);
		}
		
		String adapterName = action.substring(0, action.indexOf("."));
		String handlerName = action.substring(action.indexOf(".") + 1, action.length());

		Adapter adapter = hybridResources.getAdapter(adapterName);
		Adapter.Handler handler = hybridResources.getHandler(adapterName, handlerName);
		
		Iterator<Parameter> parameters = handler.getParameters();
		Class<?>[] parameterTypes = getParameterTypes(parameters);
		Object[] parameterObjects = null;
		try {
			parameterObjects = createAndInflateParameter(parameterTypes, parameterValues.iterator());
		} catch(SiminovException siminovException) {
			Log.loge(AdapterHandler.class.getName(), "", "SiminovException caught while create and inflate parameters, " + siminovException.getMessage());
		}
		

		Object adapterInstanceObject = null;
		Method handlerInstanceObject = null;
		
		boolean cache = adapter.isCache();
		if(cache) {
			adapterInstanceObject = adapterResources.requireAdapterInstance(adapterName);
			handlerInstanceObject = (Method) adapterResources.requireHandlerInstance(adapterName, handlerName, parameterTypes);
		} else {
			adapterInstanceObject = adapterResources.getAdapterInstance(adapterName);
			handlerInstanceObject = (Method) adapterResources.getHandlerInstance(adapterName, handlerName, parameterTypes);
		}


		Object returnData = null;
			
		try {
			returnData = ClassUtils.invokeMethod(adapterInstanceObject, handlerInstanceObject, parameterObjects);
		} catch(SiminovException siminovException) {
			Log.loge(AdapterHandler.class.getName(), "", "SiminovException caught while invoking handler, " + siminovException.getMessage());

			return generateHybridSiminovException(siminovException.getClassName(), siminovException.getMethodName(), siminovException.getMessage());
		}

		if(returnData != null) {
			return returnData.toString();
		}
			
		return generateHybridSiminovEmptyData();

	}

	@JavascriptInterface
	public void handleNativeToWeb(final String action, final String...data) {

		Adapter adapter = hybridResources.getAdapter(Constants.HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER);
		Handler handler = hybridResources.getHandler(Constants.HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER, Constants.HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER_HANDLER);
		
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
		
		String adapterName = "";
		String handlerName = "";
		
		
		int indexOfHandler = action.indexOf(".");
		if(indexOfHandler > 0) {
			adapterName = action.substring(0, indexOfHandler);
			handlerName = action.substring(action.indexOf(".") + 1, action.length());
		} else {
			adapterName = action;
		}
		
		String invokeAction = "";
		
		if(hybridResources.containAdapterBasedOnName(adapterName)) {
			Adapter invokeAdapter = hybridResources.getAdapter(adapterName);
			invokeAction = invokeAdapter.getMapTo();
		}
		
		if(handlerName != null && handlerName.length() > 0 && hybridResources.containHandler(handlerName)) {
			Adapter.Handler invokeHandler = hybridResources.getHandler(adapterName, handlerName);
			invokeAction += "." + invokeHandler.getMapTo();
		}
		

		final Adapter finalAdapter = adapter;
		final Handler finalHandler = handler;
		final String finalInvokeAction = invokeAction;
		final String finalParameters = parameters;
		
		Activity webActivity = hybridResources.getWebActivity();
		webActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				
				if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0 && finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					hybridResources.getWebView().loadUrl("javascript: new " + finalAdapter.getMapTo() + "()." + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalAdapter.getMapTo() != null && finalAdapter.getMapTo().length() > 0) {
					hybridResources.getWebView().loadUrl("javascript:" + finalAdapter.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
				} else if(finalHandler.getMapTo() != null && finalHandler.getMapTo().length() > 0) {
					hybridResources.getWebView().loadUrl("javascript:" + finalHandler.getMapTo() + "('" + finalInvokeAction + "', " + finalParameters + ");");
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

	
	private String generateHybridSiminovEmptyData() {
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		String data = null;
		
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(SiminovHandler.class.getName(), "generateHybridSiminovException", "SiminovException caught while generating empty siminov js data: " + siminovException.getMessage());
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
			data = HybridSiminovDataBuilder.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "generateHybridSiminovException", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	

	
	public void initializeSiminov() {

		siminov.orm.Siminov.processDatabase();
		siminov.hybrid.Siminov.siminovInitialized();
		
	}

	
	public void shutdownSiminov() {
		
		shutdown();	
	}
	
}
