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


package siminov.hybrid.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridDescriptor;
import siminov.hybrid.model.HybridDescriptor.Adapter;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;


/**
 * Exposes methods to parse Hybrid Descriptor information as per define in HybridDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<hybrid-descriptor>
	
		<adapters>
			
			<adapter>
				<property name="name">name_of_adapter</property>
				<property name="description">description_about_adapter</property>
				<property name="type">type_of_adapter</property>
				<property name="map_to">map_to_class_name</property>
				<property name="cache">should_be_cached_or_not</property>
				
				<handlers>
				
					<handler>
						<property name="name">name_of_handler</property>
						<property name="description">description_about_handler</property>
						<property name="map_to">map_to_function_name</property>
						
						<parameters>
							
							<parameter>
								<property name="name"></property>
								<property name="description"></property>
								<property name="type"></property>
							</parameter>
						
						</parameter>
						
						<return>
								<property name="type">return_data_type</property>
								<property name="description">description_about_return_data</property>
						</return>
						
					</handler>
				
				</handlers>
				
			</adapter>
			
		</adapters>
		
			
			
	</hybrid-descriptor>
	}
	
		</pre>
	</p>
 *
 */
public class HybridDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

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
	
	public HybridDescriptorReader() {
		parse(HYBRID_DESCRIPTOR_FILE_NAME);
	}

	public HybridDescriptorReader(String adapterPath) {
		parse(adapterPath);
	}
	
	private void parse(String fileName) {

		Context context = resources.getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid context found.");
			throw new DeploymentException(getClass().getName(), "Constructor", "Invalid context found.");
		}

		/*
		 * Parse HybridDescriptor.
		 */
		InputStream applicationDescriptorStream = null;
		
		try {

			applicationDescriptorStream = getClass().getClassLoader().getResourceAsStream(fileName);
			if(applicationDescriptorStream == null) {
				applicationDescriptorStream = context.getAssets().open(fileName);
			}
		} catch(IOException ioException) {
			Log.logd(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());
			
			//Ignore If Hybrid Descriptor Not Defined.
			
			return;
		}
		
		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
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
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER)) {
			
			parameter = new Adapter.Handler.Parameter();
			isParameter = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN)) {
			
			returnData = new Adapter.Handler.Return();
			isReturn = true;
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
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER)) {
			
			handler.addParameter(parameter);
			
			parameter = null;
			isParameter = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN)) {
			
			handler.setReturn(returnData);
			
			returnData = null;
			isReturn = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER)) {
			hybridDescriptor.addAdapterPath(tempValue);
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

	public HybridDescriptor getHybridDescriptor() {
		return this.hybridDescriptor;
	}
	
}
