/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution|support@siminov.com]
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

public interface Constants {

	//HybridDescriptor Constants.
	
	public String HYBRID_DESCRIPTOR_FILE_NAME = "HybridDescriptor.si.xml";
	
	public String HYBRID_DESCRIPTOR_PROPERTY = "property";
	public String HYBRID_DESCRIPTOR_PROPERTY_NAME = "name";

	public String HYBRID_DESCRIPTOR_ADAPTER_ADAPTER = "adapter";
	public String HYBRID_DESCRIPTOR_ADAPTER_ADAPTER_PATH = "path";
	public String HYBRID_DESCRIPTOR_ADAPTER_NAME = "name";
	public String HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION = "description";
	public String HYBRID_DESCRIPTOR_ADAPTER_TYPE = "type";
	public String HYBRID_DESCRIPTOR_ADAPTER_MAP_TO = "map_to";
	public String HYBRID_DESCRIPTOR_ADAPTER_CACHE = "cache";
	public String HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER = "error_handler";
	
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLERS = "handlers";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER = "handler";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME = "name";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE = "type";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE_SYNC = "SYNC"; 
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE_ASYNC = "ASYNC"; 
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_CALLBACK_ID = "callback_id";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO = "map_to";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION = "description";
	public String HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER = "error_handler";
	
	public String HYBRID_DESCRIPTOR_ADAPTER_PARAMETERS = "parameters";
	public String HYBRID_DESCRIPTOR_ADAPTER_PARAMETER = "parameter";
	public String HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_TYPE = "type"; 
	public String HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_DESCRIPTION = "description";
	
	public String HYBRID_DESCRIPTOR_ADAPTER_RETURN = "return";
	public String HYBRID_DESCRIPTOR_ADAPTER_RETURN_TYPE = "type";
	public String HYBRID_DESCRIPTOR_ADAPTER_RETURN_DESCRIPTION = "description";
	
	public String HYBRID_DESCRIPTOR_LIBRARIES = "libraries";
	public String HYBRID_DESCRIPTOR_LIBRARY = "library";
	public String HYBRID_DESCRIPTOR_LIBRARY_PATH = "path";
	
	
	//SiminovDataFormat
	public String HYBRID_SIMINOV_DATA = "siminov-hybrid-data";
	public String HYBRID_SIMINOV_DATA_DATA = "data";
	public String HYBRID_SIMINOV_DATA_DATA_TYPE = "type";
	public String HYBRID_SIMINOV_DATA_VALUE = "value";
	public String HYBRID_SIMINOV_DATA_VALUE_TYPE = "type";
	
	public String HYBRID_SIMINOV_DATA_JSON_TEXT = "#text";
	public String HYBRID_SIMINOV_DATA_JSON_TYPE = "-type";
	public String HYBRID_SIMINOV_DATA_JSON_CDATA_SECTION = "#cdata-section";
	
	//Siminov - Hybrid Library Path
	public String HYBRID_DESCRIPTOR_SIMINOV_HYBRID_LIBRARY_PATH = "siminov.hybrid.adapter.resource";

	
	//HYBRID-Library Descriptor
	public String HYBRID_LIBRARY_DESCRIPTOR_FILE_NAME = "LibraryDescriptor.si.xml";
	
	public String HYBRID_LIBRARY_DESCRIPTOR_PROPERTY = "property";
	public String HYBRID_LIBRARY_DESCRIPTOR_PROPERTY_NAME = "name";
	public String HYBRID_LIBRARY_DESCRIPTOR_NAME = "name";
	public String HYBRID_LIBRARY_DESCRIPTOR_DESCRIPTION = "description";
	public String HYBRID_LIBRARY_DESCRIPTOR_HYBRID_ADAPTER = "hybrid-adapters";
	public String HYBRID_LIBRARY_DESCRIPTOR_ADAPTER = "adapter";
	public String HYBRID_LIBRARY_DESCRIPTOR_ADAPTER_PATH = "path";
	
	
	//Siminov Adapter Handler.
	public String HYBRID_SIMINOV_WEB_TO_NATIVE_ADAPTER = "SIMINOV";
	public String HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER = "SIMINOV-NATIVE-TO-WEB";
	
	public String HYBRID_SIMINOV_NATIVE_TO_WEB_ADAPTER_HANDLER = "HANDLE-NATIVE-TO-WEB";

	//Siminov Events Handler.
	public String HYBRID_SIMINOV_EVENT_HANDLER_ADAPTER = "EVENT-HANDLER";
	
	public String HYBRID_SIMINOV_EVENT_HANDLER_TRIGGER_EVENT_HANDLER = "TRIGGER-EVENT";
	
}
