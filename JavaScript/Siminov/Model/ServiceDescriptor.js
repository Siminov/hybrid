/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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
	It contain all Models as per required by Siminov Framework.

	@module Model
*/
var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}



if(dom == undefined) {
    var Constants = require('../Constants');
    var Dictionary = require('../Collection/Dictionary');
    
    module.exports = ServiceDescriptor;
    win.ServiceDescriptor = ServiceDescriptor;
}

/**
 	Exposes methods to GET and SET Service Descriptor information as per define in ServiceDescriptor.xml file by application.
		
	Example:
		
		<service-descriptor>
		    
			<!-- General Service Properties -->
		    	<!-- Mandatory Field -->
		    <property name="name">name_of_service</property>
		    
		    	<!-- Optional Field -->
		    <property name="description">description_of_service</property>
		    
		    	<!-- Optional Field (DEFAULT: HTTP) -->
		    <property name="protocol">HTTP|HTTPS</property>
		    
		    	<!-- Mandatory Field -->
		    <property name="instance">address_of_instance</property>
		    
		    	<!-- Optional Field -->
		    <property name="port">port_number</property>
		
		    	<!-- Optional Field -->
		    <property name="context">context_of_service</property>
		         
			<!-- Requests -->
				<!-- Request -->
		    <request>
		            
				<request>
		
					<!-- General Request Properties -->
		         
			        	<!-- Mandatory Field -->
			       	<property name="name">name_of_request</property>
		         
		         		<!-- Mandatory Field -->
		         	<property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>
		
		         		<!-- Mandatory Field -->
		         	<property name="api">full_request_path</property>
		
		         		<!-- Mandatory Field -->
		         	<property name="handler">handler_of_request</property>
		         	
		      			<!-- Optional Field (DEFAULT: SYNC)-->
		      		<property name="mode">SYNC|ASYNC</property>
		
		      		
		      					
		      		<!-- Query Parameters -->
		      			<!-- Query Parameter -->
		      		<query-parameters>
		          
		      			<query-parameter>
		      			    
		      			    <!-- Mandatory Field -->
		      			    <property name="name">name_of_query_parameter</property>
		      			    
		      			    <!-- Mandatory Field -->
		      			    <property name="value">value_of_query_parameter</property>
		      			    
		      			</query-parameter>
		      		
		      		</query-parameters>
		      
		      
		      		<!-- Header Parameters -->
		      			<!-- Header Parameter -->
		      		<header-parameters>
		          
		      			<header-parameter>
		      			    
		      			    <!-- Mandatory Field -->
		      			    <property name="name">name_of_header_parameter</property>
		      			    
		      			    <!-- Mandatory Field -->
		      			    <property name="value">value_of_header_parameter</property>
		      			    
		      			</header-parameter>
		      		
		        	</header-parameters>
		
		      		
		      		<!-- Stream of Data Under Request Body -->
		      			<!-- It is Optional Property -->
		      		<data-stream>stream_of_data</data-stream>	
		     	
		     	</request>
		    </requests>
		    
		</service-descriptor>
	
	@module Model		
	@class ServiceDescriptor
	@constructor
 */
function ServiceDescriptor() {
	
	var properties = new Dictionary();
	var requests = new Array();
	
	/**
	 * Get service name
	 * 
	 * @method getName
	 * @return {String} Name of service
	 */	
	this.getName = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_NAME);
	}	
	
	/**
	 * Set service name
	 * 
	 * @method setName
	 * @param name {String} Name of service
	 */
	this.setName = function(name) {
		properties.add(Constants.SERVICE_DESCRIPTOR_NAME, name);
	}	
	
	
	/**
	 * Get description
	 * 
	 * @method setDescription
	 * @return {String} Description
	 */
	this.getDescription = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_DESCRIPTION);
	}
	
	/**
	 * Set description
	 * 
	 * @method setDescription
	 * @param description {String} Description
	 */
	this.setDescription = function(description) {
		properties.add(Constants.SERVICE_DESCRIPTOR_DESCRIPTION, description);
	}
	
	/**
	 * Get protocol
	 * 
	 * @method getProtocol
	 * @return {String} Protocol
	 */
	this.getProtocol = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_PROTOCOL);
	}
	
	/**
	 * Set protocol
	 * 
	 * @method setProtocol
	 * @param protocol {String} Protocol
	 */
	this.setProtocol = function(protocol) {
		properties.add(Constants.SERVICE_DESCRIPTOR_PROTOCOL, protocol);
	}
	
	/**
	 * Get instance
	 * 
	 * @method getInstance
	 * @return {String} Instance
	 */
	this.getInstance = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_INSTANCE);		
	}
	
	/**
	 * Set instance
	 * 
	 * @method setInstance
	 * @param instance {String} Instance
	 */
	this.setInstance = function(instance) {
		properties.add(Constants.SERVICE_DESCRIPTOR_INSTANCE, instance);
	}
	
	/**
	 * Get port
	 * 
	 * @method getPort
	 * @return {String} Port
	 */
	this.getPort = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_PORT);	
	}
	
	
	/**
	 * Set port
	 * 
	 * @method setPort
	 * @param port {String} Port
	 */
	this.setPort = function(port) {
		properties.add(Constants.SERVICE_DESCRIPTOR_PORT, port);
	}
	
	/**
	 * Get context
	 * 
	 * @method getContext
	 * @return {String} Context
	 */
	this.getContext = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	/**
	 * Set context
	 * 
	 * @method setContext
	 * @param context {String} Context
	 */
	this.setContext = function(context) {
		properties.add(Constants.SERVICE_DESCRIPTOR_CONTEXT);
	}
	
	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}


	/**
	 * Get all requests
	 * 
	 * @method getRequests
	 * @return Requests
	 */
	this.getRequests = function() {
		return requests;
	}
	
	/**
	 * Add request
	 * 
	 * @method addRequest
	 * @param request {String} Request
	 */
	this.addRequest = function(request) {
		requests.push(request);
	}
}



/**
 * It exposes methods to Get and Set service request details as per defined in ServiceDescriptor.xml file by the application.
 * 
	<request>

		<!-- General Request Properties -->
     
        	<!-- Mandatory Field -->
       	<property name="name">name_of_request</property>
     
     		<!-- Mandatory Field -->
     	<property name="type">GET|HEAD|POST|PUT|DELETE|TRACE|OPTIONS|CONNECT|PATCH</property>

     		<!-- Mandatory Field -->
     	<property name="api">full_request_path</property>

     		<!-- Mandatory Field -->
     	<property name="handler">handler_of_request</property>
     	
  			<!-- Optional Field (DEFAULT: SYNC)-->
  		<property name="mode">SYNC|ASYNC</property>

  		
  					
  		<!-- Query Parameters -->
  			<!-- Query Parameter -->
  		<query-parameters>
      
  			<query-parameter>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="name">name_of_query_parameter</property>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="value">value_of_query_parameter</property>
  			    
  			</query-parameter>
  		
  		</query-parameters>
  
  
  		<!-- Header Parameters -->
  			<!-- Header Parameter -->
  		<header-parameters>
      
  			<header-parameter>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="name">name_of_header_parameter</property>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="value">value_of_header_parameter</property>
  			    
  			</header-parameter>
  		
    	</header-parameters>

  		
  		<!-- Stream of Data Under Request Body -->
  			<!-- It is Optional Property -->
  		<data-stream>stream_of_data</data-stream>	
 	
 	</request>
 * 
 * 
 * @class ServiceDescriptor.Request
 */
ServiceDescriptor.Request = function() {
	
	var properties = new Dictionary();
	
	var queryParameters = new Array();
	var headerParameters = new Array();

	var dataStream;

	/**
	 * Get request name
	 * 
	 * @method getName
	 * @return {String} Name of request
	 */
	this.getName = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_NAME);		
	}
	
	/**
	 * Set request name
	 * 
	 * @method setName
	 * @param name {String} Name of request
	 */
	this.setName = function(name) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_NAME, name);
	}
	
	/**
	 * Get type of request
	 * 
	 * @method getType
	 * @return {String} Get request type
	 */
	this.getType = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_TYPE);
	}
	
	/**
	 * Set type of request
	 * 
	 * @method setType
	 * @param type {String} Request Type
	 */
	this.setType = function(type) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_TYPE, type);
	}
	
	/**
	 * Get API of request
	 * 
	 * @method getApi
	 * @return {String} Api
	 */
	this.getApi = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_API);
	}
	
	/**
	 * Set request api
	 * 
	 * @method setApi
	 * @param api {String} API
	 */
	this.setApi = function(api) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_API, api);
	}
	
	/**
	 * Get handler of request
	 * 
	 * @method getHandler
	 * @return {String} Handler
	 */
	this.getHandler = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_HANDLER);
	}
	
	/**
	 * Set handler of request
	 * 
	 * @method setHandler
	 * @param handler {String} Handler 
	 */
	this.setHandler = function(handler) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_HANDLER, handler);
	}

	/**
	 * Get mode of request
	 * 
	 * @method getMode
	 * @return {String} Mode
	 */	
	this.getMode = function() {
		return properties.get(Constants.SERVICE_DESCRIPTOR_API_MODE);		
	}
	
	/**
	 * Set mode of request
	 * 
	 * @method setMode
	 * @param mode {String}
	 */
	this.setMode = function(mode) {
		properties.add(Constants.SERVICE_DESCRIPTOR_API_MODE, mode);
	}
	
	/**
	 * Get data stream
	 * 
	 * @method getDataStream
	 * @return {String} Data Stream
	 */
	this.getDataStream = function() {
		return dataStream;
	}
	
	/**
	 * Set data stream
	 * 
	 * @method setDataStream
	 * @param val {String} Data Stream
	 */
	this.setDataStream = function(val) {
		dataStream = val;
	}
	

	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}

	/**
	 * Get all query parameters
	 * 
	 * @method getQueryParameters
	 * @return {Array} Query Parameters
	 */
	this.getQueryParameters = function() {
		return queryParameters;
	}
	
	/**
	 * Add query parameter
	 * 
	 * @method addQueryParameter
	 * @param queryParameter {ServiceDescriptor.Request.QueryParameter}
	 */
	this.addQueryParameter = function(queryParameter) {
		queryParameters.push(queryParameter);
	}
	
	/**
	 * Get all header parameters
	 * 
	 * @method getHeaderParameters
	 * @return {Array} Header Parameters
	 */
	this.getHeaderParameters = function() {
		return headerParameters;
	}
	
	/**
	 * Add header parameter
	 * 
	 * @method addHeaderParameter
	 * @param headerParameters {ServiceDescriptor.Request.HeaderParameter}
	 */
	this.addHeaderParameter = function(headerParameter) {
		headerParameters.push(headerParameter);
	}
}


/**
 * 
 * It exposes methods to Get and Set request query parameter details as per defined in ServiceDescriptor.xml file by the application.
 * 
	<request>

  		<!-- Query Parameters -->
  			<!-- Query Parameter -->
  		<query-parameters>
      
  			<query-parameter>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="name">name_of_query_parameter</property>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="value">value_of_query_parameter</property>
  			    
  			</query-parameter>
  		
  		</query-parameters>
 	
 	</request>
 	* 
 * @class ServiceDescriptor.Request.QueryParameter
 */
ServiceDescriptor.Request.QueryParameter = function() {
	
	var properties = new Dictionary();

	var name;
	var value;
	
	/**
	 * Get name of query parameter
	 * 
	 * @method getName
	 * @return {String} Name of Query Parameter
	 */
	this.getName = function() {
		return name;		
	}

	/**
	 * Set name of query parameter
	 * 
	 * @method setName
	 * @param val {String} Name of query parameter
	 */
	this.setName = function(val) {
		name = val
	}	
	
	/**
	 * Get value of query parameter
	 * 
	 * @method getValue
	 * @return {String} Value of Query Parameter
	 */
	this.getValue = function() {
		return value;
	}
	
	/**
	 * Set value of query parameter
	 * 
	 * @method setValue
	 * @param val {String} Value of Query Parameter
	 */
	this.setValue = function(val) {
		value = val;
	}
	
	
	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
}


/**
 * 
 * It exposes methods to Get and Set request header parameter details as per defined in ServiceDescriptor.xml file by the application.
 * 
	<request>

  		<!-- Header Parameters -->
  			<!-- Header Parameter -->
  		<header-parameters>
      
  			<header-parameter>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="name">name_of_header_parameter</property>
  			    
  			    <!-- Mandatory Field -->
  			    <property name="value">value_of_header_parameter</property>
  			    
  			</header-parameter>
  		
    	</header-parameters> 	
    	
 	</request>
 	* 
 * @class ServiceDescriptor.Request.HeaderParameter
 */
ServiceDescriptor.Request.HeaderParameter = function() {

	var properties = new Dictionary();
	
	var name;
	var value;
	
	/**
	 * Get name of header parameter
	 * 
	 * @method getName
	 * @return {String} Name of Header Parameter
	 */
	this.getName = function() {
		return name;		
	}

	/**
	 * Set name of header parameter
	 * 
	 * @method setName
	 * @param val {String} Name of Header Parameter
	 */
	this.setName = function(val) {
		name = val
	}	
	
	/**
	 * Get value of header parameter
	 * 
	 * @method getValue
	 * @return {String} Value of Header Parameter
	 */
	this.getValue = function() {
		return value;
	}
	
	/**
	 * Set value of header parameter
	 * 
	 * @method setValue
	 * @param val {String} Value of Header Parameter
	 */
	this.setValue = function(val) {
		value = val;
	}
	
	
	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
}