
function NotificationDescriptor() {

	var properties = new Dictionary();
	
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