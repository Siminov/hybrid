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


package siminov.hybrid.adapter;

import siminov.hybrid.Constants;
import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.resource.ResourceManager;
import siminov.core.utils.ClassUtils;
import android.webkit.WebView;


/**
 * It works has a bridge between HYBRID-TO-NATIVE and NATIVE-TO-HYBRID, basically every request is handled by this class.
 *
 */
public class AdapterHandler {

	private static ResourceManager resourceManager = ResourceManager.getInstance();
	private static AdapterHandler adapterHandler = null;

	private IHandler handler = null;
	
	/**
	 * AdapterHandler Private Constructor
	 */
	private AdapterHandler() {
		
		AdapterDescriptor adapterDescriptor = resourceManager.getAdapterDescriptor(Constants.HYBRID_TO_NATIVE_ADAPTER);
		register((IHandler) ClassUtils.createClassInstance(adapterDescriptor.getMapTo()));

		resourceManager.setAdapterHandler(adapterHandler);
	
	}
	
	/**
	 * It provides an singleton instance of Adapter Handler class.
	 * 
	 * @return Adapter Handler instance.
	 */
	public static final AdapterHandler getInstance() {
		if(adapterHandler == null) {
			adapterHandler = new AdapterHandler();
		}
		
		return adapterHandler;
	}
	
	
	/**
	 * Registers Handler instance to handle all request processed between Hybrid and Native.  
	 * @param handler IHandler Instance.
	 */
	public void register(IHandler handler) {
		
		AdapterDescriptor adapterDescriptor = resourceManager.getAdapterDescriptor(Constants.HYBRID_TO_NATIVE_ADAPTER);
		
		String adapterName = adapterDescriptor.getName();
		WebView webView = resourceManager.getWebView();

		this.handler = handler;
		if(webView != null) {
			webView.addJavascriptInterface(this.handler, adapterName);
		}
	}
	
	
	/**
	 * Get IHandler instance which receives and handler request processed between Hybrid and Native.
	 * @return IHandler Instance.
	 */
	public IHandler getHandler() {
		return this.handler;
	}

}