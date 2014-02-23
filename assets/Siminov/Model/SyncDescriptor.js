

function SyncDescriptor() {

	var properties = new Dictionary();
	
	var services = new Array();

	this.getName = function() {
		return properties.get(Constants.SYNC_DESCRIPTOR_NAME);
	}
	
	this.setName = function(name) {
		properties.put(Constants.SYNC_DESCRIPTOR_NAME, name);
	}
	
	this.getSyncInterval = function() {
		return properties.get(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL);
	}
	
	this.setSyncInterval = function(syncInterval) {
		properties.put(Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL, syncInterval);	
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
	
	this.getServices = function() {
		return services;
	}
	
	this.addService = function(service) {
		services.push(service);
	}
}