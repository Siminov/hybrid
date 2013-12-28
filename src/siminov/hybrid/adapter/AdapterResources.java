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


package siminov.hybrid.adapter;

import java.util.HashMap;
import java.util.Map;

import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.model.AdapterDescriptor.Handler;
import siminov.hybrid.resource.Resources;
import siminov.orm.utils.ClassUtils;

/**
 * It caches Adapter Instance and based on requirement server request.
 */
public class AdapterResources {

	private Map<String, Object> adapters = new HashMap<String, Object>();
	private Map<String, Object> handlers = new HashMap<String, Object>();
	
	private Resources resources = Resources.getInstance();
	
	private static AdapterResources adapterResources = null;
	
	private AdapterResources() {
		resources.setAdapterResources(adapterResources);
	}
	
	/**
	 * It provides an instance of AdapterResources class.
	 * 
	 * @return AdapterResources instance.
	 */
	public static final AdapterResources getInstance() {
		if(adapterResources == null) {
			adapterResources = new AdapterResources();
		}
		
		return adapterResources;
	}
	

	/**
	 * Returns Adapter Descriptor mapped class instance based on adapter descriptor name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return Adapter Class Instance.
	 */
	public Object getAdapterInstance(String adapterDescriptorName) {
		AdapterDescriptor adapterDescriptor = resources.getAdapterDescriptor(adapterDescriptorName);
		String mapTo = adapterDescriptor.getMapTo();
		
		return ClassUtils.createClassInstance(mapTo);
	}
	
	
	/**
	 * Returns Adapter mapped class instance based on adapter name. If adapter instance is not cache it will create new instance of that class.
	 * @param adapterName Name of Adapter.
	 * @return Adapter Class Instance.
	 */
	public Object requireAdapterInstance(String adapterName) {
		boolean contain = adapters.containsValue(adapterName);
		if(contain) {
			return adapters.get(adapterName);
		}
		
		Object adapterObject = getAdapterInstance(adapterName);
		adapters.put(adapterName, adapterObject);
		
		return adapterObject;
	}
	
	
	/**
	 * Returns Handler mapped method instance based on adapter name, handler name and its handler parameter types.
	 * @param adapterDescriptorName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @param handlerParameterTypes Type of Parameters.
	 * @return Handler Method Instance.
	 */
	public Object getHandlerInstance(String adapterDescriptorName, String handlerName, Class<?>...handlerParameterTypes) {

		AdapterDescriptor adapterDescriptor = resources.getAdapterDescriptor(adapterDescriptorName);
		Handler handler = adapterDescriptor.getHandler(handlerName);
		
		return ClassUtils.createMethodBasedOnClassName(adapterDescriptor.getMapTo(), handler.getMapTo(), handlerParameterTypes);
	}
	
	
	/**
	 * Returns Handler mapped method instance based on adapter name, handler name and its handler parameter types. If instance is not cached it will create new instance for handler.
	 * @param adapterDescriptorName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @param handlerParameterTypes Type of Parameters.
	 * @return
	 */
	public Object requireHandlerInstance(String adapterDescriptorName, String handlerName, Class<?>...handlerParameterTypes) {
		
		boolean contain = handlers.containsKey(handlerName);
		if(contain) {
			return handlers.get(handlerName);
		}
		
		Object handler = getHandlerInstance(adapterDescriptorName, handlerName, handlerParameterTypes);
		handlers.put(handlerName, handler);
		
		return handler;
	}
	
}
