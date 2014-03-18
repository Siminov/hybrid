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
import siminov.orm.resource.Resources;
import android.content.Context;

public class AdapterDescriptorReader extends SiminovSAXDefaultHandler implements Constants {

	private StringBuilder tempValue = new StringBuilder();
	private Resources resources = Resources.getInstance();

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
		} else {
			adapterDescriptor.addProperty(propertyName, tempValue.toString());
		}
	}

	public AdapterDescriptor getAdapterDescriptor() {
		return this.adapterDescriptor;
	}
}