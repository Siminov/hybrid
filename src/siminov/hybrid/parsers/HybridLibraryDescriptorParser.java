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

import java.io.InputStream;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridLibraryDescriptor;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.parsers.SiminovSAXDefaultHandler;
import siminov.orm.resource.Resources;
import android.content.Context;

public class HybridLibraryDescriptorParser extends SiminovSAXDefaultHandler implements Constants {

	private String tempValue = null;
	private HybridLibraryDescriptor hybridLibraryDescriptor = new HybridLibraryDescriptor();
	
	private boolean isName = false;
	private boolean isDescription = false;
	
	public HybridLibraryDescriptorParser(final String libraryName) throws SiminovException {
		if(libraryName == null || libraryName.length() <= 0) {
			Log.loge(getClass().getName(), "Constructor", "Invalid Library Name Found.");
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid Library Name Found.");
		}
		
		Context context = Resources.getInstance().getApplicationContext();
		if(context == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid Application Context Found.");
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid Application Context Found.");
		}

		InputStream libraryDescriptorStream = null;
		libraryDescriptorStream = getClass().getClassLoader().getResourceAsStream(libraryName.replace(".", "/") + "/" + HYBRID_LIBRARY_DESCRIPTOR_FILE_NAME);

		if(libraryDescriptorStream == null) {
			Log.loge(getClass().getName(), "Constructor", "Invalid Library Descriptor Stream Found, LIBRARY-NAME: " + libraryName + ", PATH: " + libraryName.replace(".", "/") + "/" + HYBRID_LIBRARY_DESCRIPTOR_FILE_NAME);
			throw new SiminovException(getClass().getName(), "Constructor", "Invalid Library Descriptor Stream Found, LIBRARY-NAME: " + libraryName + ", PATH: " + libraryName.replace(".", "/") + "/" + HYBRID_LIBRARY_DESCRIPTOR_FILE_NAME);
		}
		
		try {
			parseMessage(libraryDescriptorStream);
		} catch(Exception exception) {
			Log.loge(getClass().getName(), "Constructor", "Exception caught while parsing LIBRARY-DESCRIPTOR: " + libraryName + ", " + exception.getMessage());
			throw new SiminovException(getClass().getName(), "Constructor", "Exception caught while parsing LIBRARY-DESCRIPTOR: " + libraryName + ", " + exception.getMessage());
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(localName.equalsIgnoreCase(HYBRID_LIBRARY_DESCRIPTOR_PROPERTY)) {
			initializeProperty(attributes);
		} else if(localName.equalsIgnoreCase(HYBRID_LIBRARY_DESCRIPTOR_ADAPTER)) {
			String adapterPath = attributes.getValue(HYBRID_LIBRARY_DESCRIPTOR_ADAPTER_PATH);
			hybridLibraryDescriptor.addAdapterPath(adapterPath);
		}

		super.startElement(uri, localName, qName, attributes);
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempValue = new String(ch,start,length);
		
		if(tempValue == null || tempValue.length() <= 0) {
			return;
		}
		
		tempValue.trim();
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(localName.equalsIgnoreCase(HYBRID_LIBRARY_DESCRIPTOR_PROPERTY)) {
			if(isName) {
				hybridLibraryDescriptor.setName(tempValue);
				isName = false;
			} else if(isDescription) {
				hybridLibraryDescriptor.setDescription(tempValue);
				isDescription = false;
			}
		} 
	}
	
	private void initializeProperty(final Attributes attributes) {
		String property = attributes.getValue(HYBRID_LIBRARY_DESCRIPTOR_PROPERTY_NAME);
		
		if(property.equalsIgnoreCase(HYBRID_LIBRARY_DESCRIPTOR_NAME)) {
			isName = true;
		} else if(property.equalsIgnoreCase(HYBRID_LIBRARY_DESCRIPTOR_DESCRIPTION)) {
			isDescription = true;
		}
	}
	
	public HybridLibraryDescriptor getLibraryDescriptor() {
		return this.hybridLibraryDescriptor;
	}
	
}
