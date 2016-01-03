///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///



/**
 * Exposes all constants required by Siminov Hybrid Framework.
 */

#import <Foundation/Foundation.h>

//Application Descriptor Constants

/**
 
 * Application Descriptor Adapter Descriptor
 */
static NSString * const APPLICATION_DESCRIPTOR_ADAPTER_DESCRIPTOR = @"adapter-descriptor";


//Adapter Descriptor Constants

/**
 * Adapter Descriptor Property
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY = @"property";


/**
 * Adapter Descriptor Property Name
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY_NAME = @"name";


/**
 * Adapter Descriptor Handler
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER = @"handler";


/**
 * Adapter Descriptor Handler Parameter
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PARAMETER = @"parameter";


/**
 * Adapter Descriptor Return
 */
static NSString * const ADAPTER_DESCRIPTOR_RETURN = @"return";



/**
 * Adapter Descriptor Property Description
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION = @"description";


/**
 * Adapter Descriptor Property Type
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY_TYPE = @"type";


/**
 * Adapter Descriptor Property Map To
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO = @"map_to";


/**
 * Adapter Descriptor Property Cache
 */
static NSString * const ADAPTER_DESCRIPTOR_PROPERTY_CACHE = @"cache";



/**
 * Adapter Descriptor Handler Property Name
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME = @"name";


/**
 * Adapter Descriptor Handler Property Description
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION = @"description";


/**
 * Adapter Descriptor Handler Property Map To
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO = @"map_to";



/**
 * Adapter Descriptor Handler Parameter Property Name
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME = @"name";


/**
 * Adapter Descriptor Handler Property Type
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE = @"type";


/**
 * Adapter Descriptor Handler Parameter Property Descriptor
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION = @"description";



/**
 * Adapter Descriptor Handler Return Property Type
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE = @"type";


/**
 * Adapter Descriptor Handler Return Property Description
 */
static NSString * const ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION = @"description";

//SiminovDataFormat

/**
 * Hybrid Siminov Data TAG.
 */
static NSString * const HYBRID_SIMINOV_DATAS = @"datas";

/**
 * Hybrid Siminov Data Type
 */
static NSString * const HYBRID_SIMINOV_DATA_TYPE = @"type";

/**
 * Hybrid Siminov Data Datas
 */
static NSString * const HYBRID_SIMINOV_DATA_DATAS = @"datas";

/**
 * Hybrid Siminov Data Value
 */
static NSString * const HYBRID_SIMINOV_DATA_VALUE = @"value";

/**
 * Hybrid Siminov Data Values
 */
static NSString * const HYBRID_SIMINOV_DATA_VALUES = @"values";

/**
 * Hybrid Siminov Data Value Type
 */
static NSString * const HYBRID_SIMINOV_DATA_VALUE_TYPE = @"type";

/**
 * Hybrid Siminov Data Value Value
 */
static NSString * const HYBRID_SIMINOV_DATA_VALUE_VALUE = @"value";


//Hybrid Library Descriptor

/**
 * LibraryDescriptor.xml file Adapter TAG.
 */
static NSString * const HYBRID_LIBRARY_DESCRIPTOR_ADAPTER_DESCRIPTOR = @"adapter-descriptor";


//Siminov Adapter Handler.

/**
 * Hybrid To Native Adapter Name.
 */
static NSString * const HYBRID_TO_NATIVE_ADAPTER = @"SIMINOV";

/**
 * Siminov Hybrid To Native Adapter Interceptor
 */
static NSString * const SIMINOV_HYBRID_TO_NATIVE_ADAPTER_INTEREPTOR = @"siminov://";

/**
 * Native To Hybrid Adapter Name.
 */
static NSString * const NATIVE_TO_HYBRID_ADAPTER = @"SIMINOV-NATIVE-TO-HYBRID";

/**
 * Native To Hybrid Adapter Name.
 */
static NSString * const NATIVE_TO_HYBRID_ADAPTER_HANDLER = @"HANDLE-NATIVE-TO-HYBRID";

/**
 * Native To Hybrid Adapter Async Handler
 */
static NSString * const NATIVE_TO_HYBRID_ADAPTER_ASYNC_HANDLER = @"HANDLE-NATIVE-TO-HYBRID-ASYNC";


//Siminov Events Handler.

/**
 * Event Handler Adapter Name.
 */
static NSString * const EVENT_HANDLER_ADAPTER = @"EVENT-HANDLER";

/**
 * Event Handler Trigger Event Handler Name.
 */
static NSString * const EVENT_HANDLER_TRIGGER_EVENT_HANDLER = @"TRIGGER-EVENT";

/**
 * Library Descriptor File Path
 */
static NSString * const LIBRARY_DESCRIPTOR_FILE_PATH = @"Siminov.Hybrid.Adapter.Resources";


// Service Handler Constants
/**
 * Service Event Handler Adapter
 */
static NSString * const SERVICE_EVENT_HANDLER_ADAPTER = @"SERVICE-EVENT-HANDLER";

/**
 * Service Event Handler Trigger Event Handler
 */
static NSString * const SERVICE_EVENT_HANDLER_TRIGGER_EVENT_HANDLER = @"TRIGGER-EVENT";

/**
 * Adapter Invoke Handler Service
 */
static NSString * const ADAPTER_INVOKE_HANDLER_SERVICE = @"SERVICE";

/**
 * Adapter Invoke Handler Request Id
 */
static NSString * const ADAPTER_INVOKE_HANDLER_REQUEST_ID = @"REQUEST_ID";

/**
 * Adapter Invoke Handler Service Name
 */
static NSString * const ADAPTER_INVOKE_HANDLER_SERVICE_NAME = @"SERVICE_NAME";

/**
 * Adapter Invoke Handler Service API Name
 */
static NSString * const ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST_NAME = @"REQUEST_NAME";

/**
 * Adapter Invoke Handler Service Resources
 */
static NSString * const ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES = @"RESOURCES";

/**
 * HTTP Request API Query Parameter
 */
static NSString * const HTTP_REQUEST_API_QUERY_PARAMETER = @"request_api";

/**
 * HTTP Request Id
 */
static NSString * const HTTP_REQUEST_ID = @"request_id";

/**
 * HTTP Request Mode
 */
static NSString * const HTTP_REQUEST_MODE = @"request_mode";

/**
 * HTTP Request Mode Async
 */
static NSString * const HTTP_REQUEST_MODE_ASYNC = @"ASYNC";


/**
 * HTTP Request Data Query Parameter
 */
static NSString * const HTTP_REQUEST_DATA_QUERY_PARAMETER = @"request_data";



/**
 * Hybrid Undefined
 */
static NSString * const HYBRID_UNDEFINED = @"undefined";