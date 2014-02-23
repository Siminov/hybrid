
function Service() {
	
	var requestId;
	
	var service;
	var api;
	
	var inlineResource = new Dictionary();

	var serviceDescriptor;	
	
	
	/*
	 * IService APIs
	 */
	 
	this.getRequestId = function() {
		return requestId;
	}
	
	this.setRequestId = function(val) {
		requestId = val;
	}
	
	this.getService = function() {
		return service;
	}
	
	this.setService = function(val) {
		service = val;
	}
	
	this.getApi = function() {
		return api;
	}
	
	this.setApi = function(val) {
		api = val;
	}
	
	this.getServiceDescriptor = function() {
		return serviceDescriptor;
	}
	
	this.setServiceDescriptor = function(val) {
		serviceDescriptor = val;
	}
	
	this.invoke = function() {
	
		var serviceHandler = ServiceHandler.getInstance();
		try {
			serviceHandler.handle(new IServiceHandler(this));
		} catch(se) {
			this.onServiceTerminate(se);
		}
	}
	
	
	/*
	 * IInlineResource APIs
	 */
	
	this.getInlineResources = function() {
		return inlineResource.keys();
	} 
	
	this.getInlineResource = function(val) {
		return inlineResource.get(val);
	}
	
	this.addInlineResource = function(key, value) {
		inlineResource.add(key, value);
	}
	
	this.containInlineResource = function(val) {
		return inlineResource.exists(val);
	}
}