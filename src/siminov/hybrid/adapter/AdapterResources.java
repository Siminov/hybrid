/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution|support@siminov.com]
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

import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.model.HybridDescriptor.Adapter.Handler;
import siminov.hybrid.resource.Resources;
import siminov.orm.utils.ClassUtils;

public class AdapterResources {

	private Map<String, Object> adapters = new HashMap<String, Object>();
	private Map<String, Object> handlers = new HashMap<String, Object>();
	
	private Resources resources = Resources.getInstance();
	
	private static AdapterResources adapterResources = null;
	
	private AdapterResources() {
		resources.setAdapterResources(adapterResources);
	}
	
	public static final AdapterResources getInstance() {
		if(adapterResources == null) {
			adapterResources = new AdapterResources();
		}
		
		return adapterResources;
	}
	
	public Object getAdapterInstance(String adapterName) {
		Adapter adapter = resources.getAdapter(adapterName);
		String mapTo = adapter.getMapTo();
		
		return ClassUtils.createClassInstance(mapTo);
	}
	
	public Object requireAdapterInstance(String adapterName) {
		boolean contain = adapters.containsValue(adapterName);
		if(contain) {
			return adapters.get(adapterName);
		}
		
		Object adapterObject = getAdapterInstance(adapterName);
		adapters.put(adapterName, adapterObject);
		
		return adapterObject;
	}
	
	public Object getHandlerInstance(String adapterName, String handlerName, Class<?>...handlerParameterTypes) {

		Adapter adapter = resources.getAdapter(adapterName);
		Handler handler = adapter.getHandler(handlerName);
		
		return ClassUtils.createMethodObject(adapter.getMapTo(), handler.getMapTo(), handlerParameterTypes);
	}
	
	public Object requireHandlerInstance(String adapterName, String handlerName, Class<?>...handlerParameterTypes) {
		
		boolean contain = handlers.containsKey(handlerName);
		if(contain) {
			return handlers.get(handlerName);
		}
		
		Object handler = getHandlerInstance(adapterName, handlerName, handlerParameterTypes);
		handlers.put(handlerName, handler);
		
		return handler;
	}
	
}
