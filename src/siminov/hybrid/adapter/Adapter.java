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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Adapter {

	private AdapterHandler adapterHandler = AdapterHandler.getInstance();
	
	private String adapterName = null;
	private String handlerName = null;
	
	private List<String> parameters = new ArrayList<String>();
	
	public String getAdapterName() {
		return this.adapterName;
	}
	
	public void setAdapterName(String adapterName) {
		this.adapterName = adapterName;
	}
	
	public String getHandlerName() {
		return this.handlerName;
	}
	
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	
	public Iterator<String> getParameters() {
		return this.parameters.iterator();
	}
	
	public void addParameter(String parameter) {
		this.parameters.add(parameter);
	}
	
	
	public void invoke() {
		
		IHandler handler = adapterHandler.getHandler();
		
		if(handlerName == null && handlerName.length() <= 0) {
			handler.handleNativeToWeb(adapterName, parameters.toArray(new String[parameters.size()]));
		} else {
			handler.handleNativeToWeb(adapterName + "." + handlerName, parameters.toArray(new String[parameters.size()]));
		}
		
	}
	
}
