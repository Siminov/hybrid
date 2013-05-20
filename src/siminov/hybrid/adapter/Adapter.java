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
