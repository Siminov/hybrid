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

/**

	<hybrid-descriptor>
	
	    <!-- Adapter -->
	    <adapter>
	        
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
	
	        	<!-- Optional Error Handler -->
	        <property name="error_handler">error_handler_id</property>
	        
	        <!-- Handlers -->
	        	<!-- Handler -->
	        <handlers>
	            
		        <handler>
		            
		            <!-- General Handler Properties -->
		            	<!-- Mandatory Field -->
		            <property name="name">handler_name</property>
		            
		            	<!-- Optional Field -->
		            <property name="type">SYNC|ASYNC</property>
		            
		            	<!-- Optional Field -->
		            <property name="callback_id">callback_adapter_name</property>
		
		            	<!-- Mandatory Field -->
		            <property name="map_to">name_of_handler_method</property>	            
	
		            	<!-- Optional Field -->
		            <property name="description">handler_description</property>	            
	
			       		<!-- Optional Error Handler -->
			       	<property name="error_handler">error_handler_id</property>
			            	            
		            	            	           
		            <!-- Parameters -->
		            <parameters>
		                <!-- Parameter -->
		                <parameter>
		                    
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
	
	    </adapter>
	    
	
	    <!-- Adapter Paths -->
	    <adapter path="adapter_path" />
	    
	
	    <!-- Library Needed Under This HybridDescriptor -->
	    <libraries>
	        
	        <library>full_path_of_library_descriptor_file</library>
	        
	    </libraries>
	    
	</hybrid-descriptor>
*/

function HybridDescriptor() {

    var properties = new Dictionary();

    var adapters = [];
    var adapterPaths = [];

    var libraries = [];

		
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

    this.getAdapter = function() {
    	return adapters;
	}
	
    this.addAdapter = function(adapter) {
    	adapters.push(adapter);
	}

    this.getAdapterPaths = function() {
    	return adapterPaths;
	}
	
    this.addAdapterPath = function(adapterPath) {
    	adapterPaths.push(adapterPath);
	}

    this.getLibraries = function() {
    	return libraries;
	}
	
    this.addLibrary = function(library) {
    	libraries.push(library);
	}

}


HybridDescriptor.Adapter = function() {

    var properties = new Dictionary();

    var handlers=[];
    

    this.getName = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME);
	}
	
    this.setName = function(name) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME, name);
	}

    this.getDescription = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION);
	}
	
    this.setDescription = function(description) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION, description);
	}

    this.getType = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE);
	}
	
    this.setType = function(type) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE, type);
	}

    this.getMapTo = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO);
	}
	
    this.setMapTo = function(mapTo) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO, mapTo);
	}

    this.setCache = function(cache) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE, cache);
	}
	
    this.isCache = function() {
        return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE);
	}

    this.getErrorHandler = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER);
	}
    
    this.setErrorHandler = function(errorHandler) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER, errorHandler);
	}

    this.getHandlers = function() {
    	return handlers;
	}
	
    this.addHandler = function(handler) {
    	handlers.push(handler);
	}
    
} 


HybridDescriptor.Adapter.Handler = function() {

    var properties = new Dictionary();

    var parameters=[];

    this.getName = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME);
	}
	
    this.setName = function(name) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME, name);
	}

    this.getDescription = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION);
	}
	
    this.setDescription = function(description) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION, description);
	}

    this.getType = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE);
	}
	
    this.setType = function(type) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE, type);
	}

    this.getMapTo = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO);
	}
	
    this.setMapTo = function(mapTo) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO, mapTo);
	}

    this.getErrorHandler = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER);
	}
	
    this.setErrorHandler = function(errorHandler) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER, errorHandler);
	}

    this.getCallBackId = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_CALLBACK_ID);
	}
	
    this.setCallBackId = function(callBackId) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_CALLBACK_ID, callBackId);
	}

    this.getParameters = function() {
    	return parameters;
	}
    
    this.addParameter = function(parameter) {
    	parameters.push(parameter);
	}

} 


HybridDescriptor.Adapter.Handler.Parameter = function() {

    var properties = new Dictionary();

    this.getDescription = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_DESCRIPTION);
	}
	
    this.setDescription = function(description) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_DESCRIPTION, description);
	}

    this.getType = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_TYPE);
	}
	
    this.setType = function(type) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_TYPE, type);
	}
} 


HybridDescriptor.Adapter.Handler.Return = function() {

    var properties = new Dictionary();

    this.getDescription = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_DESCRIPTION);
	}
	
    this.setDescription = function(description) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_DESCRIPTION, description);
	}

    this.getType = function() {
    	return properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_TYPE);
	}
	
    this.setType = function(type) {
    	properties.add(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_TYPE, type);
	}
} 