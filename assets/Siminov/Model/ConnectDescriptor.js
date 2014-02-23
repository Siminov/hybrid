
function ApplicationDescriptor() {

    var properties = new Dictionary();
	
	var serviceDescriptorPaths = new Array();

	var syncDescriptors = new Array();
	var authenticationDescriptor;
	var notificationDescriptor;
	
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
	
	this.addServiceDescriptorPath = function(serviceDescriptorPath) {
		serviceDescriptorPaths.push(serviceDescriptorPath);		
	}
	
	this.getServiceDescriptorPaths = function() {
		return serviceDescriptorPaths;
	}
	
	this.getSyncDescriptors = function() {
		return syncDescriptors;
	}
	
	this.addSyncDescriptor = function(syncDescriptor) {
		syncDescriptors.push(syncDescriptor);		
	}
	
	this.getAuthenticationDescriptor = function() {
		return authenticationDescriptor;		
	}
	
	this.setAuthenticationDescriptor = function(ad) {
		authenticationDescriptor = ad;
	}
}


