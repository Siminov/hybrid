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

package siminov.hybrid.parsers;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.parsers.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;

public class HybridDescriptorParser extends SiminovSAXDefaultHandler implements Constants {

	private String tempValue = null;
	private Resources resources = Resources.getInstance();
	
	private HybridDescriptor hybridDescriptor = new HybridDescriptor();
	
	private Adapter adapter = null;
	private Adapter.Handler handler = null;
	private Adapter.Handler.Parameter parameter = null;
	private Adapter.Handler.Return returnData = null;
	
	private boolean isAdapter = false;
	private boolean isHandler = false;
	private boolean isParameter = false;
	private boolean isReturn = false;

	private String propertyName = null;
	
	public HybridDescriptorParser() throws SiminovException, DeploymentException {
		parse(HYBRID_DESCRIPTOR_FILE_NAME);
	}

	public HybridDescriptorParser(String adapterPath) throws SiminovException, DeploymentException {
		parse(adapterPath);
	}
	
	public HybridDescriptorParser(String libraryPackageName, String adapterPath) throws SiminovException, DeploymentException {
		
		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid context found.");
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse Adapter.
		 */
		InputStream adapterStream = null;
		
		try {
			adapterStream = getClass().getClassLoader().getResourceAsStream(libraryPackageName.replace(".", "/") + "/" + adapterPath);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + exception.getMessage());
			throw new SiminovException(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + exception.getMessage());
		}
		
		try {
			parseMessage(adapterStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new SiminovException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	private void parse(String fileName) throws SiminovException, DeploymentException {

		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid context found.");
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse ApplicationDescriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {
			applicationDescriptorStream = context.getAssets().open(fileName);
		} catch(IOException ioException) {
			Log.loge(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
			throw new SiminovException(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
		}
		
		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new SiminovException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		tempValue = "";

		if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER)) {
			
			adapter = new Adapter();
			isAdapter = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER)) {
			
			handler = new Adapter.Handler();
			isHandler = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_PARAMETER)) {
			
			parameter = new Adapter.Handler.Parameter();
			isParameter = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_RETURN)) {
			
			returnData = new Adapter.Handler.Return();
			isReturn = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER_PATH)) {
			String path = attributes.getValue(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER_PATH);
			if(path != null && path.length() > 0) {
				hybridDescriptor.addAdapterPath(path);
			}
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_LIBRARY)) {
			String library = attributes.getValue(HYBRID_DESCRIPTOR_LIBRARY_PATH);
			hybridDescriptor.addLibraryPath(library);
		}
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempValue = new String(ch,start,length);
		
		if(tempValue == null || tempValue.length() <= 0) {
			return;
		}
		
		tempValue.trim();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER)) {

			hybridDescriptor.addAdapter(adapter);
			
			adapter = null;
			isAdapter = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER)) {
			
			adapter.addHandler(handler);
			
			handler = null;
			isHandler = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_PARAMETER)) {
			
			handler.addParameter(parameter);
			
			parameter = null;
			isParameter = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_RETURN)) {
			
			handler.setReturn(returnData);
			
			returnData = null;
			isReturn = false;
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(HYBRID_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		
		if(isReturn) {
			returnData.addProperty(propertyName, tempValue);
		} else if(isParameter) {
			parameter.addProperty(propertyName, tempValue);
		} else if(isHandler) {
			handler.addProperty(propertyName, tempValue);
		} else if(isAdapter) {
			adapter.addProperty(propertyName, tempValue);
		} else {
			hybridDescriptor.addProperty(propertyName, tempValue);
		}
	}

	public HybridDescriptor getJSDescriptor() {
		return this.hybridDescriptor;
	}
	
}
