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

import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.hybrid.resource.Resources;
import siminov.orm.utils.ClassUtils;
import android.webkit.WebView;

public class AdapterHandler {

	private static Resources resources = Resources.getInstance();
	private static AdapterHandler adapterHandler = null;

	private IHandler handler = null;
	
	private AdapterHandler() {
		
		Adapter adapter = resources.getAdapter(Constants.HYBRID_SIMINOV_WEB_TO_NATIVE_ADAPTER);
		register((IHandler) ClassUtils.createClassInstance(adapter.getMapTo()));

		resources.setAdapterHandler(adapterHandler);
	
	}
	
	public static final AdapterHandler getInstance() {
		if(adapterHandler == null) {
			adapterHandler = new AdapterHandler();
		}
		
		return adapterHandler;
	}
	
	public void register(IHandler handler) {
		
		Adapter adapter = resources.getAdapter(Constants.HYBRID_SIMINOV_WEB_TO_NATIVE_ADAPTER);
		
		String adapterName = adapter.getName();
		WebView webView = resources.getWebView();

		this.handler = handler;
		webView.addJavascriptInterface(this.handler, adapterName);
	}
	
	public IHandler getHandler() {
		return this.handler;
	}

}