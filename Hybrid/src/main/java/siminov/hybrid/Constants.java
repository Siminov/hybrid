/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
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



package siminov.hybrid;

/**
 * Exposes all constants required by Siminov Hybrid Framework.
 */
public interface Constants {
	
	//Application Descriptor Constants
	
	/**
	 * Application Descriptor Adapter Descriptor
	 */
	public String APPLICATION_DESCRIPTOR_ADAPTER_DESCRIPTOR = "adapter-descriptor";
	
	
	//Adapter Descriptor Constants

	/**
	 * Adapter Descriptor Property
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY = "property";
	

	/**
	 * Adapter Descriptor Property Name
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY_NAME = "name";
	

	/**
	 * Adapter Descriptor Handler
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER = "handler";
	

	/**
	 * Adapter Descriptor Handler Parameter
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PARAMETER = "parameter";
	

	/**
	 * Adapter Descriptor Return
	 */
	public String ADAPTER_DESCRIPTOR_RETURN = "return";
	
	

	/**
	 * Adapter Descriptor Property Description
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION = "description";
	

	/**
	 * Adapter Descriptor Property Type
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY_TYPE = "type";
	

	/**
	 * Adapter Descriptor Property Map To
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO = "map_to";
	

	/**
	 * Adapter Descriptor Property Cache
	 */
	public String ADAPTER_DESCRIPTOR_PROPERTY_CACHE = "cache";
	
	

	/**
	 * Adapter Descriptor Handler Property Name
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME = "name";
	
	public String ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_TYPE = "type";

	/**
	 * Adapter Descriptor Handler Property Description
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION = "description";


	/**
	 * Adapter Descriptor Handler Property Map To
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO = "map_to";
	
	

	/**
	 * Adapter Descriptor Handler Parameter Property Name
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME = "name";


	/**
	 * Adapter Descriptor Handler Property Type
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE = "type";


	/**
	 * Adapter Descriptor Handler Parameter Property Descriptor
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION = "description";

	

	/**
	 * Adapter Descriptor Handler Return Property Type
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE = "type";


	/**
	 * Adapter Descriptor Handler Return Property Description
	 */
	public String ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION = "description";

	//SiminovDataFormat
	
	/**
	 * Hybrid Siminov Data TAG.
	 */
	public String HYBRID_SIMINOV_DATAS = "datas";

	public String HYBRID_SIMINOV_DATA_TYPE = "type";

	public String HYBRID_SIMINOV_DATA_DATAS = "datas";
	
	public String HYBRID_SIMINOV_DATA_VALUE = "value";
	
	/**
	 * Hybrid Siminov Data VALUE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_VALUES = "values";
	
	/**
	 * Hybrid Siminov Data JSON TEXT TAG.
	 */
	public String HYBRID_SIMINOV_DATA_VALUE_TYPE = "type";
	
	/**
	 * Hybrid Siminov Data JSON TYPE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_VALUE_VALUE = "value";
	
	
	//Hybrid Library Descriptor
	
	/**
	 * LibraryDescriptor.si.xml file Adapter TAG.
	 */
	public String HYBRID_LIBRARY_DESCRIPTOR_ADAPTER_DESCRIPTOR = "adapter-descriptor";
	

	//Siminov Adapter Handler.
	
	/**
	 * Hybrid To Native Adapter Name.
	 */
	public String HYBRID_TO_NATIVE_ADAPTER = "SIMINOV";
	
	/**
	 * Native To Hybrid Adapter Name.
	 */
	public String NATIVE_TO_HYBRID_ADAPTER = "SIMINOV-NATIVE-TO-HYBRID";
	
	/**
	 * Native To Hybrid Adapter Name.
	 */
	public String NATIVE_TO_HYBRID_ADAPTER_HANDLER = "HANDLE-NATIVE-TO-HYBRID";
	
	public String NATIVE_TO_HYBRID_ADAPTER_ASYNC_HANDLER = "HANDLE-NATIVE-TO-HYBRID-ASYNC";

	//Siminov Events Handler.
	
	/**
	 * Event Handler Adapter Name.
	 */
	public String EVENT_HANDLER_ADAPTER = "EVENT-HANDLER";
	
	/**
	 * Event Handler Trigger Event Handler Name.
	 */
	public String EVENT_HANDLER_TRIGGER_EVENT_HANDLER = "TRIGGER-EVENT";
	
	/**
	 * Library Descriptor File Path
	 */
	public String LIBRARY_DESCRIPTOR_FILE_PATH = "siminov.hybrid.adapter.resources";

	
	// Service Handler Constants
	/**
	 * Service Event Handler Adapter
	 */
	public String SERVICE_EVENT_HANDLER_ADAPTER = "SERVICE-EVENT-HANDLER";

	/**
	 * Service Event Handler Trigger Event Handler
	 */
	public String SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER = "TRIGGER-EVENT";
	
	/**
	 * Adapter Invoke Handler Service
	 */
	public String ADAPTER_INVOKE_HANDLER_SERVICE = "SERVICE";

	public String ADAPTER_INVOKE_HANDLER_REQUEST_ID = "REQUEST_ID";
	
	/**
	 * Adapter Invoke Handler Service Name
	 */
	public String ADAPTER_INVOKE_HANDLER_SERVICE_NAME = "SERVICE_NAME";
	
	/**
	 * Adapter Invoke Handler Service API Name
	 */
	public String ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST_NAME = "REQUEST_NAME";
	
	/**
	 * Adapter Invoke Handler Service Resources
	 */
	public String ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES = "RESOURCES";

	
	public String REQUEST_SYNC_MODE = "SYNC";
	
	public String REQUEST_ASYNC_MODE = "ASYNC";
	
}
