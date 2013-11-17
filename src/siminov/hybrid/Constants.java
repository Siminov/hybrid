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


package siminov.hybrid;

/**
 * Exposes all constants required by Siminov Hybrid ORM Framework.
 */
public interface Constants {

	//HybridDescriptor Constants.
	
	/**
	 * HybridDesriptor.si.xml file name.
	 */
	public String HYBRID_DESCRIPTOR_FILE_NAME = "HybridDescriptor.si.xml";
	
	
	/**
	 * HybridDescriptor.si.xml file Property TAG.
	 */
	public String HYBRID_DESCRIPTOR_PROPERTY = "property";
	
	/**
	 * HybridDescriptor.si.xml file Property Name TAG.
	 */
	public String HYBRID_DESCRIPTOR_PROPERTY_NAME = "name";

	
	/**
	 * HybridDesriptor.si.xml file Adapter TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_ADAPTER = "adapter";
	
	/**
	 * HybridDescriptor.si.xml file Adapter property NAME.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_NAME = "name";
	
	/**
	 * HybridDescriptor.si.xml file Adapter property DESCRIPTION.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION = "description";
	
	/**
	 * HybridDescriptor.si.xml file Adapter property TYPE.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_TYPE = "type";
	
	/**
	 * HybridDescriptor.si.xml file Adapter property MAP_TO.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_MAP_TO = "map_to";
	
	/**
	 * HybridDescriptor.si.xml file Adapter property CACHE.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_CACHE = "cache";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handlers TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLERS = "handlers";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER = "handler";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler property NAME.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME = "name";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler property MAP_TO.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO = "map_to";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler property DESCRIPTION.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION = "description";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler property ERROR_HANDLER.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER = "error_handler";
	
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Parameters TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETERS = "parameters";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Parameter TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER = "parameter";

	/**
	 * HybridDescriptor.si.xml file Adapter Handler Parameter property NAME.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_NAME = "name";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Parameter property TYPE.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_TYPE = "type";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Parameter property DESCRIPTION.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_DESCRIPTION = "description";
	
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Return TAG.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN = "return";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Hander Return property TYPE.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_TYPE = "type";
	
	/**
	 * HybridDescriptor.si.xml file Adapter Handler Return property DESCRIPTION.
	 */
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_DESCRIPTION = "description";
	
	
	//SiminovDataFormat
	
	/**
	 * Hybrid Siminov Data TAG.
	 */
	public String HYBRID_SIMINOV_DATA = "siminov-hybrid-data";
	
	/**
	 * Hybrid Siminov Data DATA TAG.
	 */
	public String HYBRID_SIMINOV_DATA_DATA = "data";
	
	/**
	 * Hybrid Siminov Data DATA TYPE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_DATA_TYPE = "type";
	
	/**
	 * Hybrid Siminov Data VALUE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_VALUE = "value";
	
	/**
	 * Hybrid Siminov Data VALUE TYPE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_VALUE_TYPE = "type";
	
	/**
	 * Hybrid Siminov Data JSON TEXT TAG.
	 */
	public String HYBRID_SIMINOV_DATA_JSON_TEXT = "#text";
	
	/**
	 * Hybrid Siminov Data JSON TYPE TAG.
	 */
	public String HYBRID_SIMINOV_DATA_JSON_TYPE = "-type";
	
	/**
	 * Hybrid Siminov Data JSON CDATA SECTION TAG.
	 */
	public String HYBRID_SIMINOV_DATA_JSON_CDATA_SECTION = "#cdata-section";
	
	
	//HYBRID-Library Descriptor
	
	/**
	 * LibraryDescriptor.si.xml file Adapters TAG.
	 */
	public String HYBRID_LIBRARY_DESCRIPTOR_HYBRID_ADAPTER = "adapters";
	
	/**
	 * LibraryDescriptor.si.xml file Adapter TAG.
	 */
	public String HYBRID_LIBRARY_DESCRIPTOR_ADAPTER = "adapter";
	

	//Siminov Adapter Handler.
	
	/**
	 * Siminov Hybrid Web To Native Adapter Name.
	 */
	public String HYBRID_SIMINOV_WEB_TO_NATIVE_ADAPTER = "SIMINOV";
	
	/**
	 * Siminov Hybrid Native To Web Adapter Name.
	 */
	public String HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER = "SIMINOV-NATIVE-TO-WEB";
	
	/**
	 * Siminov Hybrid Native To Web Adapter Name.
	 */
	public String HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER_HANDLER = "HANDLE-NATIVE-TO-WEB";

	//Siminov Events Handler.
	
	/**
	 * Siminov Hybrid Event Handler Adapter Name.
	 */
	public String HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER = "EVENT-HANDLER";
	
	/**
	 * Siminov Hybrid Event Handler Trigger Event Handler Name.
	 */
	public String HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER = "TRIGGER-EVENT";
	
}
