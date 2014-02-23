
function ServiceDescriptor() {
	
	var properties = new Dictionary();
	var apis = new Array();
	
		
	this.getName = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_NAME);
	}	
	
	this.setName = function(name) {
		properties.add(Constants.SERVICE_DESCRIPTOR_NAME, name);
	}	
	
	this.getDescription = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_DESCRIPTION);
	}
	
	this.setDescription = function(description) {
		properties.add(Constants.SERVICE_DESCRIPTOR_DESCRIPTION, description);
	}
	
	this.getProtocol = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_PROTOCOL);
	}
	
	this.setProtocol = function(protocol) {
		properties.add(Constants.SERVICE_DESCRIPTOR_PROTOCOL, protocol);
	}
	
	this.getInstance = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_INSTANCE);		
	}
	
	this.setInstance = function(instance) {
		properties.add(Constants.SERVICE_DESCRIPTOR_INSTANCE, instance);
	}
	
	this.getPort = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_PORT);	
	}
	
	this.setPort = function(port) {
		properties.add(Constants.SERVICE_DESCRIPTOR_PORT, port);
	}
	
	this.getContext = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	this.setContext = function(context) {
		properties.add(Constants.SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	this.getProperties = function() {
		return properties.values();
	}
	
	this.getProperty = function(name) {
		return properties.get(name);
	}
	
	this.containProperty = function(name) {
		return properties.exists(name);
	}
	
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}

	this.getApis = function() {
		return apis;
	}
	
	this.addApi = function(api) {
		apis.push(api);
	}
}



ServiceDescriptor.API = function() {
	
	var properties = new Dictionary();
	
	var queryParameters = new Array();
	var headerParameters = new Array();

	var dataStream;

	this.getName = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_NAME);		
	}
	
	this.setName = function(name) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_NAME, name);
	}
	
	this.getType = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_TYPE);
	}
	
	this.setType = function(type) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_TYPE, type);
	}
	
	this.getApi = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_API);
	}
	
	this.setApi = function(api) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_API, api);
	}
	
	this.getHandler = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_HANDLER);
	}
	
	this.setHandler = function(handler) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_HANDLER, handler);
	}
	
	this.getMode = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_MODE);		
	}
	
	this.setMode = function(mode) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_MODE, mode);
	}
	
	this.getDataStream = function() {
		return dataStream;
	}
	
	this.setDataStream = function(ds) {
		dataStream = ds;
	}
	
	this.getProperties = function() {
		return properties.values();
	}
	
	this.getProperty = function(name) {
		return properties.get(name);
	}
	
	this.containProperty = function(name) {
		return properties.exists(name);
	}
	
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}
	
	this.getQueryParameters = function() {
		return queryParameters;
	}
	
	this.addQueryParameter = function(queryParameter) {
		queryParameters.push(queryParameter);
	}
	
	this.getHeaderParameters = function() {
		return headerParameters;
	}
	
	this.addHeaderParameter = function(headerParameter) {
		headerParameters.push(headerParameter);
	}
}


ServiceDescriptor.API.QueryParameter = function() {
	
	var properties = new Dictionary();

	var name;
	var value;

	this.getName = function() {
		return name;		
	}

	this.setName = function(val) {
		name = val
	}	
	
	this.getValue = function() {
		return value;
	}
	
	this.setValue = function(val) {
		value = val;
	}
	
	this.getProperties = function() {
		return properties.values();
	}
	
	this.getProperty = function(name) {
		return properties.get(name);
	}
	
	this.containProperty = function(name) {
		return properties.exists(name);
	}
	
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}
}


ServiceDescriptor.API.HeaderParameter = function() {

	var properties = new Dictionary();
	
	var name;
	var value;
	
	this.getName = function() {
		return name;
	}

	this.setName = function(val) {
		name = val;
	}	
	
	this.getValue = function() {
		return value;
	}
	
	this.setValue = function(val) {
		value = val;
	}

	this.getProperties = function() {
		return properties.values();
	}
	
	this.getProperty = function(name) {
		return properties.get(name);
	}
	
	this.containProperty = function(name) {
		return properties.exists(name);
	}
	
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}
}