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


package siminov.hybrid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import siminov.core.IInitializer;
import siminov.core.resource.ResourceManager;
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;


/**
 * It implements IInitializer Interface.
 * It handle initialization of framework.
 */
public class Initializer implements IInitializer {

	private ResourceManager coreResourceManager = ResourceManager.getInstance();
	private siminov.hybrid.resource.ResourceManager hybridResourceManager = siminov.hybrid.resource.ResourceManager.getInstance(); 
	
	private List<Object> parameters = new ArrayList<Object> ();
	
	/**
	 * Add Initialization Parameter.
	 */
	public void addParameter(Object object) {
		parameters.add(object);
	}
	
	/**
	 * It is used to initialize and start the framework
	 */
	public void initialize() {
		
		Context context = null;
		WebView webView = null;
		Activity activity = null;
		
		
		Iterator<Object> iterator = parameters.iterator();
		while(iterator.hasNext()) {
			
			Object object = iterator.next();
			if(object instanceof Activity) {
				activity = (Activity) object;
			} else if(object instanceof Context) {
				context = (Context) object;
			} else if(object instanceof WebView) {
				webView = (WebView) object;
			} 
			
		}
		
		coreResourceManager.setApplicationContext(context);

		hybridResourceManager.setWebView(webView);
		hybridResourceManager.setWebActivity(activity);
		
		Siminov.start();
		
	}

}
