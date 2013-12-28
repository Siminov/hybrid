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
import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.model.HybridDescriptor;
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

	private StringBuilder tempValue = new StringBuilder();
	private Resources resources = Resources.getInstance();
	
	private HybridDescriptor hybridDescriptor = new HybridDescriptor();
	
	private AdapterDescriptor adapterDescriptor = null;
	private AdapterDescriptor.Handler handler = null;
	private AdapterDescriptor.Handler.Parameter parameter = null;
	private AdapterDescriptor.Handler.Return returnData = null;
	
	private boolean isAdapterDescriptor = false;
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

		tempValue = new StringBuilder();

		if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER)) {
			
			adapterDescriptor = new AdapterDescriptor();
			isAdapterDescriptor = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER)) {
			
			handler = new AdapterDescriptor.Handler();
			isHandler = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER)) {
			
			parameter = new AdapterDescriptor.Handler.Parameter();
			isParameter = true;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN)) {
			
			returnData = new AdapterDescriptor.Handler.Return();
			isReturn = true;
		} 
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		String value = new String(ch,start,length);
		
		if(value == null || value.length() <= 0 || value.equalsIgnoreCase(siminov.orm.Constants.NEW_LINE)) {
			return;
		}
		
		value = value.trim();
		tempValue.append(value);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_ADAPTER)) {

			hybridDescriptor.addAdapterDescriptor(adapterDescriptor);
			
			adapterDescriptor = null;
			isAdapterDescriptor = false;
		} else if(localName.equalsIgnoreCase(HYBRID_DESCRIPTOR_ADAPTER_HANDLER)) {
			
			adapterDescriptor.addHandler(handler);
			
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
			hybridDescriptor.addAdapterDescriptorPath(tempValue.toString());
		}
	}
	
	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(HYBRID_DESCRIPTOR_PROPERTY_NAME);
	}
	
	private void processProperty() {
		
		if(isReturn) {
			returnData.addProperty(propertyName, tempValue.toString());
		} else if(isParameter) {
			parameter.addProperty(propertyName, tempValue.toString());
		} else if(isHandler) {
			handler.addProperty(propertyName, tempValue.toString());
		} else if(isAdapterDescriptor) {
			adapterDescriptor.addProperty(propertyName, tempValue.toString());
		} else {
			hybridDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	public HybridDescriptor getHybridDescriptor() {
		return this.hybridDescriptor;
	}
	
}
