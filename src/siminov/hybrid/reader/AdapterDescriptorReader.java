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

package siminov.hybrid.reader;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.hybrid.Constants;
import siminov.hybrid.model.AdapterDescriptor;
import siminov.orm.exception.DeploymentException;
import siminov.orm.log.Log;
import siminov.orm.reader.SiminovSAXDefaultHandler;
import siminov.orm.resource.ResourceManager;
import android.content.Context;




/**
 * Exposes methods to parse Adapter Descriptor information as per define in AdapterDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	
	<adapter-descriptor>
	    
	    <!-- General Adapter Properties -->
	    	<!-- Mandatory Field -->
	    <property name="name">adapter_name</property>
	    	
	    	<!-- Optional Field -->
	    <property name="description">adapter_description</property>
	    
	    	<!-- Mandatory Field -->
	    <property name="type">WEB-TO-NATIVE|NATIVE-TO-WEB</property>
	    
	    	<!-- Optional Field -->
	    <property name="map_to">name_of_adapter_class</property>
	
	    	<!-- Optional Field (DEFAULT: FALSE)-->
	    <property name="cache">true/false</property>
	    
	    <!-- Handlers -->
	    	<!-- Handler -->
	    <handlers>
	        
	     <handler>
	         
	         <!-- General Handler Properties -->
	         	<!-- Mandatory Field -->
	         <property name="name">handler_name</property>
	         
	         	<!-- Optional Field -->
	         <property name="description">handler_description</property>	            
	          	            
	         	<!-- Mandatory Field -->
	         <property name="map_to">name_of_handler_method</property>	            
	         
	         		            	            	           
	         <!-- Parameters -->
	         <parameters>
	             
	             <!-- Parameter -->
	             <parameter>
	                 
	                 	<!-- Mandatory Field -->
	                 <property name="name">name_of_parameter</property>
	                 
	                 	<!-- Mandatory Field -->
	                 <property name="type">parameter_type</property>
	                 
	                 	<!-- Optional Field -->
	                 <property name="description">description_of_parameter</property>
	                 
	             </parameter>
	             
	         </parameters>
	         
	         <return>
	             
	             	<!-- Mandatory Field -->
	             <property name="type">return_type</property>
	             
	             	<!-- Optional Field -->
	             <property name="description">return_data_description</property>
	             
	         </return>
	         
	     </handler>
	         
	    </handlers>
	
	</adapter-descriptor>


	}
	
		</pre>
	</p>
 *
 */
public class AdapterDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private ResourceManager resourceManager = ResourceManager.getInstance();

	private AdapterDescriptor adapterDescriptor = new AdapterDescriptor();
	private AdapterDescriptor.Handler handler = null;
	private AdapterDescriptor.Handler.Parameter parameter = null;
	private AdapterDescriptor.Handler.Return returnData = null;

	private boolean isHandler = false;
	private boolean isParameter = false;
	private boolean isReturn = false;

	private String propertyName = null;

	public AdapterDescriptorReader(String adapterPath) {
		parse(adapterPath);
	}

	private void parse(String fileName) {

		Context context = resourceManager.getApplicationContext();
		if(context == null) {
			Log.error(getClass().getName(), "Constructor", "Invalid context found.");
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
			Log.debug(getClass().getName(), "Constructor", "IOException caught while getting input stream of application descriptor, " + ioException.getMessage());

			//Ignore If Hybrid Descriptor Not Defined.

			return;
		}

		try {
			parseMessage(applicationDescriptorStream);
		} catch(Exception exception) {
			Log.error(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
			throw new DeploymentException(getClass().getName(), "Constructor", "Exception caught while parsing APPLICATION-DESCRIPTOR, " + exception.getMessage());
		}
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		tempValue = new StringBuilder();

		if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_HANDLER)) {

			handler = new AdapterDescriptor.Handler();
			isHandler = true;
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_HANDLER_PARAMETER)) {

			parameter = new AdapterDescriptor.Handler.Parameter();
			isParameter = true;
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_RETURN)) {

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

		if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_PROPERTY)) {
			processProperty();
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_HANDLER)) {

			adapterDescriptor.addHandler(handler);

			handler = null;
			isHandler = false;
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_HANDLER_PARAMETER)) {

			handler.addParameter(parameter);

			parameter = null;
			isParameter = false;
		} else if(localName.equalsIgnoreCase(ADAPTER_DESCRIPTOR_RETURN)) {

			handler.setReturn(returnData);

			returnData = null;
			isReturn = false;
		}
	}

	private void initializeProperty(Attributes attributes) {
		propertyName = attributes.getValue(ADAPTER_DESCRIPTOR_PROPERTY_NAME);
	}

	private void processProperty() {

		if(isReturn) {
			returnData.addProperty(propertyName, tempValue.toString());
		} else if(isParameter) {
			parameter.addProperty(propertyName, tempValue.toString());
		} else if(isHandler) {
			handler.addProperty(propertyName, tempValue.toString());
		} else {
			adapterDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	/**
	 * Get adapter descriptor
	 * @return Adapter Descriptor
	 */
	public AdapterDescriptor getAdapterDescriptor() {
		return this.adapterDescriptor;
	}
}