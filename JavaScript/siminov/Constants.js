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
    module.exports = Constants;
}


/**
	It contain all contain variables requried by Siminov Framework.
	
	@class Constants
	@constructor
	
*/
function Constants() {

}

//    ApplicationDescriptor.si.xml Constants.


/**
	Application Descriptor Name	
	
	@property APPLICATION_DESCRIPTOR_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.APPLICATION_DESCRIPTOR_NAME = "name";

/**
	Application Descriptor Description

	@property APPLICATION_DESCRIPTOR_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.APPLICATION_DESCRIPTOR_DESCRIPTION = "description";

/**
	Application Descriptor Version

	@property APPLICATION_DESCRIPTOR_VERSION
	@type String
	@static
	@final
	@readOnly
*/
Constants.APPLICATION_DESCRIPTOR_VERSION = "version";


/**
	Application Descriptor Deploy

	@property APPLICATION_DESCRIPTOR_DEPLOY
	@type String
	@static
	@final
	@readonly
*/
Constants.APPLICATION_DESCRIPTOR_DEPLOY = "deploy";


//   DatabaseDescriptor.si.xml Constants.



/**
	Database Descriptor Database Name

	@property DATABASE_DESCRIPTOR_DATABASE_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DESCRIPTOR_DATABASE_NAME = "database_name";

/**
	Database Descriptor Description

	@property DATABASE_DESCRIPTOR_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DESCRIPTOR_DESCRIPTION = "description";

/**
	Database Descriptor Type

	@property DATABASE_DESCRIPTOR_TYPE
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DESCRIPTOR_TYPE = "type";

/**
	Database Descriptor Is Locking Required

	@property DATABASE_DESCRIPTOR_IS_LOCKING_REQUIRED
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DESCRIPTOR_TRANSACTION_SAFE = "is_locking_required";

/**
	Database Descriptor External Storage

	@property DATABASE_DESCRIPTOR_EXTERNAL_STORAGE
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DESCRIPTOR_EXTERNAL_STORAGE = "external_storage";


//   LibraryDescriptor.si.xml Constants.



/**
	Library Descriptor Name

	@property LIBRARY_DESCRIPTOR_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.LIBRARY_DESCRIPTOR_NAME = "name";

/**
	Library Descriptor Description

	@property LIBRARY_DESCRIPTOR_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.LIBRARY_DESCRIPTOR_DESCRIPTION = "description";


//    EntityDescriptor.si.xml Constants.


/**
	Entity Descriptor Table Name

	@property ENTITY_DESCRIPTOR_TABLE_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_TABLE_NAME = "table_name";

/**
	Entity Descriptor Class Name

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_CLASS_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_CLASS_NAME = "class_name";

/**
	Entity Descriptor Attribute Variable Name

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_VARIABLE_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_VARIABLE_NAME = "variable_name";

/**
	Entity Descriptor Attribute Column Name

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_COLUMN_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_COLUMN_NAME = "column_name";


/**
	Entity Descriptor Attribute Primary Key

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_PRIMARY_KEY
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_PRIMARY_KEY = "primary_key";

/**
	Entity Descriptor Attribute Unique

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_UNIQUE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_UNIQUE = "unique";

/**
	Entity Descriptor Attribute Not Null

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_NOT_NULL
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_NOT_NULL = "not_null";

/**
	Entity Descriptor Attribute Default Value

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_DEFAULT_VALUE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_DEFAULT_VALUE = "default";

/**
	Entity Descriptor Attribute Check

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_CHECK
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_CHECK = "check";

/**
	Entity Descriptor Attribute Type

	@property ENTITY_DESCRIPTOR_ATTRIBUTE_TYPE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_TYPE = "type";


/**
	Entity Descriptor Relationship Attribute Load

	@property ENTITY_DESCRIPTOR_RELATIONSHIP_ATTRIBUTE_LOAD
	@type String
	@static
	@final
	@readOnly
*/
Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_ATTRIBUTE_LOAD = "load";


//    AdapterDescriptor.si.xml Constants.



/**
	Adapter Descriptor Name

	@property ADAPTER_DESCRIPTOR_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_NAME = "name";

/**
	Adapter Descriptor Description

	@property ADAPTER_DESCRIPTOR_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_DESCRIPTION = "description";

/**
	Adapter Descriptor Type

	@property ADAPTER_DESCRIPTOR_TYPE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_TYPE = "type";

/**
	Adapter Descriptor Map To

	@property ADAPTER_DESCRIPTOR_MAP_TO
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_MAP_TO = "map_to";

/**
	Adapter Descriptor Cache

	@property ADAPTER_DESCRIPTOR_CACHE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_CACHE = "cache";



/**
	Adapter Descriptor Handler Name

	@property ADAPTER_DESCRIPTOR_HANDLER_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_HANDLER_NAME = "name";

/**
	Adapter Descriptor Handler Map To

	@property ADAPTER_DESCRIPTOR_HANDLER_MAP_TO
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_HANDLER_MAP_TO = "map_to";

/**
	Adapter Descriptor Handler Description

	@property ADAPTER_DESCRIPTOR_HANDLER_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_HANDLER_DESCRIPTION = "description";


/**
	Adapter Descriptor Parameter Type

	@property ADAPTER_DESCRIPTOR_PARAMETER_TYPE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_PARAMETER_TYPE = "type";

/**
	Adapter Descriptor Parameter Description

	@property ADAPTER_DESCRIPTOR_PARAMETER_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_PARAMETER_DESCRIPTION = "description";


/**
	Adapter Descriptor Return Type

	@property ADAPTER_DESCRIPTOR_RETURN_TYPE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_RETURN_TYPE = "type";

/**
	Adapter Descriptor Return Description

	@property ADAPTER_DESCRIPTOR_RETURN_DESCRIPTION
	@type String
	@static
	@final
	@readOnly
*/
Constants.ADAPTER_DESCRIPTOR_RETURN_DESCRIPTION = "description";


//	ServiceDescriptor.si.xml

/**
	Service Descriptor Name

	@property SERVICE_DESCRIPTOR_NAME
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_NAME = "name";

/**
	Service Descriptor Description

	@property SERVICE_DESCRIPTOR_DESCRIPTION
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_DESCRIPTION = "description";

/**
	Service Descriptor Protocol

	@property SERVICE_DESCRIPTOR_PROTOCOL
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_PROTOCOL = "protocol";

/**
	Service Descriptor Instance

	@property SERVICE_DESCRIPTOR_INSTANCE
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_INSTANCE = "instance";

/**
	Service Descriptor Port

	@property SERVICE_DESCRIPTOR_PORT
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_PORT = "port";

/**
	Service Descriptor Context

	@property SERVICE_DESCRIPTOR_CONTEXT
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_CONTEXT = "context";


/**
	Service Descriptor Request Name

	@property SERVICE_DESCRIPTOR_REQUEST_NAME
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_REQUEST_NAME = "name";

/**
	Service Descriptor Request Type

	@property SERVICE_DESCRIPTOR_REQUEST_TYPE
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_REQUEST_TYPE = "type";

/**
	Service Descriptor Request API

	@property SERVICE_DESCRIPTOR_REQUEST_API
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_REQUEST_API = "api";

/**
	Service Descriptor Request Mode

	@property SERVICE_DESCRIPTOR_REQUEST_MODE
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_REQUEST_MODE = "mode";

/**
	Service Descriptor Request Data Stream

	@property SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_DESCRIPTOR_REQUEST_DATA_STREAM = "data-stream";


//Sync Descriptor.si.xml


/**
	Sync Descriptor Name

	@property SYNC_DESCRIPTOR_NAME
	@type String
	@static
	@final
	@readonly
*/
Constants.SYNC_DESCRIPTOR_NAME = "name";

/**
	Sync Descriptor Refresh Interval

	@property SYNC_DESCRIPTOR_REFRESH_INTERVAL
	@type String
	@static
	@final
	@readonly
*/
Constants.SYNC_DESCRIPTOR_REFRESH_INTERVAL = "interval";



//    Siminov Event Handler



/**
	Event Handler Triggered Event

	@property EVENT_HANDLER_TRIGGERED_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_TRIGGERED_EVENT = "TriggeredEvent";


/**
	Event Handler ISiminov Event First Time Siminov Initialized

	@property EVENT_HANDLER_ISIMINOV_EVENT_FIRST_TIME_SIMINOV_INITIALIZED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED = "onFirstTimeSiminovInitialized";

/**
	Event Handler ISiminov Event Siminov Initialized

	@property EVENT_HANDLER_ISIMINOV_EVENT_SIMINOV_INITIALIZED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED = "onSiminovInitialized";

/**
	Event Handler ISiminov Event Siminov Stopped

	@property EVENT_HANDLER_ISIMINOV_EVENT_SIMINOV_STOPPED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_STOPPED = "onSiminovStopped";


/**
	Event Handler IDatabase Event Database Created

	@property EVENT_HANDLER_IDATABASE_EVENT_DATABASE_CREATED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_CREATED = "onDatabaseCreated";

/**
	Event Handler IDatabase Event Database Dropped

	@property EVENT_HANDLER_IDATABASE_EVENT_DATABASE_DROPPED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_DROPPED = "onDatabaseDropped";

/**
	Event Handler IDatabase Event Table Created

	@property EVENT_HANDLER_IDATABASE_EVENT_TABLE_CREATED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_CREATED = "onTableCreated";

/**
	Event Handler IDatabase Event Table Dropped

	@property EVENT_HANDLER_IDATABASE_EVENT_TABLE_DROPPED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_DROPPED = "onTableDropped";

/**
	Event Handler IDatabase Event Index Created

	@property EVENT_HANDLER_IDATABASE_EVENT_INDEX_CREATED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_CREATED = "onIndexCreated";

/**
	Event Handler IDatabase Event Index Dropped

	@property EVENT_HANDLER_IDATABASE_EVENT_INDEX_DROPPED
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_DROPPED = "onIndexDropped";


/**
	Event Handler INotification Event On Registration

	@property EVENT_HANDLER_INOTIFICATION_EVENT_ON_REGISTRATION
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_REGISTRATION = "onRegistration";

/**
	Event Handler INotification Event On Unregistration

	@property EVENT_HANDLER_INOTIFICATION_EVENT_ON_UNREGISTRATION
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_UNREGISTRATION = "onUnregistration";

/**
	Event Handler INotification Event On Notification

	@property EVENT_HANDLER_INOTIFICATION_EVENT_ON_NOTIFICATION
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_NOTIFICATION = "onNotification";

/**
	Event Handler INotification Event On Error

	@property EVENT_HANDLER_INOTIFICATION_EVENT_ON_ERROR
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_INOTIFICATION_EVENT_ON_ERROR = "onError";


/**
	Event Handler ISync Event On Sync Started

	@property EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_STARTED
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_STARTED = "onSyncStarted";

/**
	Event Handler ISync Event On Sync Queued

	@property EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_QUEUED
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_QUEUED = "onSyncQueued";

/**
	Event Handler ISync Event On Sync Removed

	@property EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_REMOVED
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_REMOVED = "onSyncRemoved";

/**
	Event Handler ISync Event On Sync Terminated

	@property EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_TERMINATED
	@type String
	@static
	@final
	@readonly
*/
Constants.EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_TERMINATED = "onSyncTerminated";


/**
	Event Handler Events

	@property EVENT_HANDLER_EVENTS
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_EVENTS = "Events";

/**
	Event Handler Event Parameters

	@property EVENT_HANDLER_EVENT_PARAMETERS
	@type String
	@static
	@final
	@readOnly
*/
Constants.EVENT_HANDLER_EVENT_PARAMETERS = "EventParameters";



//    Siminov Resources Adapter



/**
	Siminov Resources Adapter

	@property SIMINOV_RESOURCES_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.RESOURCE_ADAPTER = "RESOURCES";

/**
	Resource Get Application Descriptor Handler

	@property RESOURCE_GET_APPLICATION_DESCRIPTOR_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_APPLICATION_DESCRIPTOR_HANDLER = "GET-APPLICATION-DESCRIPTOR";

/**
	Resource Get Database Descriptor Based On Name Handler

	@property RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_NAME_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_NAME_HANDLER = "GET-DATABASE-DESCRIPTOR";

/**
	Resource Get Database Descriptor Based On Class Name Handler

	@property RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER = "GET-DATABASE-DESCRIPTOR-BASED-ON-CLASS-NAME";

/**
	Resource Get Database Descriptor Based On Table Name Handler

	@property RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_DATABASE_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER = "GET-DATABASE-DESCRIPTOR-BASED-ON-TABLE-NAME";

/**
	Resource Get Entity Descriptir Based On Class Name Handler

	@property RESOURCE_GET_ENTITY_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_ENTITY_DESCRIPTOR_BASED_ON_CLASS_NAME_HANDLER = "GET-ENTITY-DESCRIPTOR-BASED-ON-CLASS-NAME";

/**
	Resource Get Entity Descriptor Based On Table Name Handler

	@property RESOURCE_GET_ENTITY_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER
	@type String
	@static
	@final
	@readonly
*/
Constants.RESOURCE_GET_ENTITY_DESCRIPTOR_BASED_ON_TABLE_NAME_HANDLER = "GET-ENTITY-DESCRIPTOR-BASED-ON-TABLE-NAME";



//    Siminov Database Adapter



/**
	Siminov Database Adapter

	@property SIMINOV_DATABASE_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_ADAPTER = "DATABASE";


/**
	Siminov Database Save Handler

	@property SIMINOV_DATABASE_SAVE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_SAVE_HANDLER = "SAVE";

/**
	Siminov Database Update Handler

	@property SIMINOV_DATABASE_UPDATE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_UPDATE_HANDLER = "UPDATE";

/**
	Siminov Database Save Or Update Handler

	@property SIMINOV_DATABASE_SAVE_OR_UPDATE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_SAVE_OR_UPDATE_HANDLER = "SAVE-OR-UPDATE";

/**
	Siminov Database Delete Handler

	@property SIMINOV_DATABASE_DELETE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_DELETE_HANDLER = "DELETE";

/**
	Siminov Database Select Handler
	
	@property SIMINOV_DATABASE_SELECT_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_SELECT_HANDLER = "SELECT";


/**
	Siminov Database Select Handler
	
	@property SIMINOV_DATABASE_SELECT_MANUAL_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_SELECT_MANUAL_HANDLER = "SELECT-MANUAL";


/**	
	Siminov Database Count Handler

	@property SIMINOV_DATABASE_COUNT_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_COUNT_HANDLER = "COUNT";

/**
	Siminov Database Average Handler

	@property SIMINOV_DATABASE_AVERAGE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_AVERAGE_HANDLER = "AVG";

/**	
	Siminov Database Max Handler

	@property SIMINOV_DATABASE_MAX_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_MAX_HANDLER = "MAX";

/**
	Siminov Database Min Handler

	@property SIMINOV_DATABASE_MIN_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_MIN_HANDLER = "MIN";

/**
	Siminov Database Sum Handler

	@property SIMINOV_DATABASE_SUM_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_SUM_HANDLER = "SUM";

/**
	Siminov Database Total Handler

	@property SIMINOV_DATABASE_TOTAL_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_TOTAL_HANDLER = "TOTAL";

/**
	Siminov Database Group Concat Handler

	@property SIMINOV_DATABASE_GROUP_CONCAT_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GROUP_CONCAT_HANDLER = "GROUP-CONCAT";


/**
	Siminov Database Get Table Name Handler

	@property SIMINOV_DATABASE_GET_TABLE_NAME_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_TABLE_NAME_HANDLER = "GET-TABLE-NAME";

/**
	Siminov Database Get Column Names Handler

	@property SIMINOV_DATABASE_GET_COLUMN_NAMES_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_COLUMN_NAMES_HANDLER = "GET-COLUMN-NAMES";

/**
	Siminov Database Get Column Types Handler

	@property SIMINOV_DATABASE_GET_COLUMN_TYPES_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_COLUMN_TYPES_HANDLER = "GET-COLUMN-TYPES";

/**
	Siminov Database Get Primary Keys Handler

	@property SIMINOV_DATABASE_GET_PRIMARY_KEYS_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_PRIMARY_KEYS_HANDLER = "GET-PRIMARY-KEYS";

/**
	Siminov Database Get Mandatory Fields Handler

	@property SIMINOV_DATABASE_GET_MANDATORY_FIELDS_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_MANDATORY_FIELDS_HANDLER = "GET-MANDATORY-FIELDS";

/**
	Siminov Database Get Unique Fields Handler

	@property SIMINOV_DATABASE_GET_UNIQUE_FIELDS_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_UNIQUE_FIELDS_HANDLER = "GET-UNIQUE-FIELDS";

/**
	Siminov Database Get Foreign Keys Handler

	@property SIMINOV_DATABASE_GET_FOREIGN_KEYS_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_GET_FOREIGN_KEYS_HANDLER = "GET-FOREIGN-KEYS";



/**
	Siminov Database Begin Transaction Handler

	@property SIMINOV_DATABASE_BEGIN_TRANSACTION_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_BEGIN_TRANSACTION_HANDLER = "BEGIN-TRANSACTION";


Constants.DATABASE_BEGIN_TRANSACTION_ASYNC_HANDLER = "BEGIN-TRANSACTION-ASYNC";

/**
	Siminov Database Commit Transaction Handler

	@property SIMINOV_DATABASE_COMMIT_TRANSACTION_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.DATABASE_COMMIT_TRANSACTION_HANDLER = "COMMIT-TRANSACTION";



//	Siminov Adapter



/**
	Siminov Adapter

	@property SIMINOV_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SIMINOV_ADAPTER = "SIMINOV";


/**
	Siminov Adapter Initialize Siminov Handler

	@property SIMINOV_ADAPTER_INITIALIZE_SIMINOV_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SIMINOV_ADAPTER_INITIALIZE_SIMINOV_HANDLER = "INITIALIZE-SIMINOV";


/**
	Siminov Adapter Shutdown Siminov Handler

	@property SIMINOV_ADAPTER_SHUTDOWN_SIMINOV_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SIMINOV_ADAPTER_SHUTDOWN_SIMINOV_HANDLER = "SHUTDOWN-SIMINOV";


//    JSON Builder



/**
	Siminov Hybrid Datas

	@property SIMINOV_HYBRID_DATAS
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATAS = "datas";

/**
	Siminov Hybrid Data Datas

	@property SIMINOV_HYBRID_DATA_DATAS
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_DATAS = "datas";

/**
	Siminov Hybrid Data Type

	@property SIMINOV_HYBRID_DATA_TYPE
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_TYPE = "type";

/**
	Siminov Hybrid Data Value

	@property SIMINOV_HYBRID_DATA_VALUE
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_VALUE = "value";

/**
	Siminov Hybrid Data Value

	@property SIMINOV_HYBRID_DATA_VALUE
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_VALUE = "value";

/**
	Siminov Hybrid Data Value Text

	@property SIMINOV_HYBRID_DATA_VALUE_TEXT
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_VALUE_TEXT = "text";

/**
	Siminov Hybrid Data Value Value

	@property SIMINOV_HYBRID_DATA_VALUE_VALUE
	@type String
	@static
	@final
	@readonly
*/
Constants.SIMINOV_HYBRID_DATA_VALUE_VALUE = "value";


// Siminov Service 

/**
	Service Adapter

	@property SERVICE_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER = "SERVICE";

/**
	Service Adapter Invoke Handler

	@property SERVICE_ADAPTER_INVOKE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER = "INVOKE";

/**
	Service Adapter Invoke Handler Service

	@property SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE = "SERVICE";

/**
	Service Adapter invoke handler Request Id

	@property SERVICE_ADAPTER_INVOKE_HANDLER_REQUEST_ID
	@type String
	@static
	@final
	@readonly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER_REQUEST_ID = "REQUEST_ID";

/**
	Service Adapter Invoke Handler Service Name

	@property SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_NAME = "SERVICE_NAME";

/**
	Service Adapter Invoke Handler Service API

	@property SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_API
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST = "REQUEST_NAME";

/**
	Service Adapter Invoke Handler Service Resources

	@property SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES
	@type String
	@static
	@final
	@readOnly
*/
Constants.SERVICE_ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES = "RESOURCES";

/**
	IService Request Id

	@property ISERVICE_REQUEST_ID
	@type String
	@static
	@final
	@readonly
*/
Constants.ISERVICE_REQUEST_ID = "ISERVICE_REQUEST_ID";

/**
	IService API Handler

	@property ISERVICE_API_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";

/**
	IService Triggered Event

	@property ISERVICE_TRIGGERED_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_TRIGGERED_EVENT = "TRIGGERED_EVENT";

/**
	IService Connection Request

	@property ISERVICE_CONNECTION_REQUEST
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_CONNECTION_REQUEST = "ConnectionRequest";

/**
	IService Connection Response

	@property ISERVICE_CONNECTION_RESPONSE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_CONNECTION_RESPONSE = "ConnectionResponse";

/**
	IService On Start Event

	@property ISERVICE_ON_START_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_START_EVENT = "onStart";

/**
	IService On Queue Event

	@property ISERVICE_ON_QUEUE_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_QUEUE_EVENT = "onQueue";

/**
	IService On Pause Event

	@property ISERVICE_ON_PAUSE_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_PAUSE_EVENT = "onPause";

/**
	IService On Resume Event

	@property ISERVICE_ON_RESUME_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_RESUME_EVENT = "onResume";

/**
	IService On Finish Event

	@property ISERVICE_ON_FINISH_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_FINISH_EVENT = "onFinish";

/**
	IService On Request Invoke Event

	@property ISERVICE_ON_REQUEST_INVOKE_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_REQUEST_INVOKE_EVENT = "onRequestInvoke";

/**
	IService On Request Finish Event

	@property ISERVICE_ON_REQUEST_FINISH_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_REQUEST_FINISH_EVENT = "onRequestFinish";

/**
	IService On Terminate Event

	@property ISERVICE_ON_TERMINATE_EVENT
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ON_TERMINATE_EVENT = "onTerminate";

/**
	IService Resources	

	@property ISERVICE_RESOURCES
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_RESOURCES = "RESOURCES";

/**
	IService Resource

	@property ISERVICE_RESOURCE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_RESOURCE = "RESOURCE";

/**
	IService Add Resource

	@property ISERVICE_ADD_RESOURCE
	@type String
	@static
	@final
	@readOnly
*/
Constants.ISERVICE_ADD_RESOURCE = "addResource";


//Notification Adapter Constants

/**
	Notification Adapter

	@property NOTIFICATION_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.NOTIFICATION_ADAPTER = "NOTIFICATION";

/**
	Notification Adater Do Registration Handler

	@property NOTIFICATION_ADAPTER_DO_REGISTRATION_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.NOTIFICATION_ADAPTER_DO_REGISTRATION_HANDLER = "DO-REGISTRATION";

/**
	Notification Adapter Do Unregistration handler

	@property NOTIFICATION_ADPATER_DO_UNREGISTRATION_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.NOTIFICATION_ADPATER_DO_UNREGISTRATION_HANDLER = "DO-UNREGISTRATION";


//Sync Adapter Constants

/**
	Sync Adapter

	@property SYNC_ADAPTER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SYNC_ADAPTER = "SYNC";

/**
	Sync Adapter Handler Handler

	@property SYNC_ADAPTER_HANDLE_HANDLER
	@type String
	@static
	@final
	@readOnly
*/
Constants.SYNC_ADAPTER_HANDLE_HANDLER = "HANDLE";

/**
	Sync Adapter Handler Handler Sync Request

	@property SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST
	@type String
	@static
	@final
	@readOnly
*/
Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST = "SyncRequest";

/**
	Sync Adapter Handler Handler Sync Request Id

	@property SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_ID
	@type String
	@static
	@final
	@readonly
*/
Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_ID = "request_id";

/**
	Sync Adapter Handler Handler Sync Request Name

	@property SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_NAME
	@type String
	@static
	@final
	@readOnly
*/
Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_NAME = "name";

/**
	Sync Adapter Handler Handler Sync Request Resources

	@property SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_RESOURCES
	@type String
	@static
	@final
	@readOnly
*/
Constants.SYNC_ADAPTER_HANDLE_HANDLER_SYNC_REQUEST_RESOURCES = "RESOURCES";


/**
	HTTP Get Method

	@property HTTP_GET_METHOD
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_GET_METHOD = "GET";

/**
	HTTP Post Method

	@property HTTP_POST_METHOD
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_POST_METHOD = "POST";

/**
	HTTP Request API Query Parameter

	@property HTTP_REQUEST_API_QUERY_PARAMETER
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_REQUEST_API_QUERY_PARAMETER = "request_api";

/**
	HTTP Request Data Query Parameter

	@property HTTP_REQUEST_DATA_QUERY_PARAMETER
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_REQUEST_DATA_QUERY_PARAMETER = "request_data";

/**
	HTTP Siminov Protocol

	@property HTTP_SIMINOV_PROTOCOL
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_SIMINOV_PROTOCOL = "siminov://";

/**
	Request Unique ID

	@property REQUEST_UNIQUE_ID
	@type String
	@static
	@final
	@readonly
*/
Constants.REQUEST_UNIQUE_ID = "REQUEST_UNIQUE_ID";

/**
	HTTP Request ID

	@property HTTP_REQUEST_ID
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_REQUEST_ID = "request_id";

/**
	HTTP Request Mode

	@property HTTP_REQUEST_MODE
	@type String
	@static
	@final
	@readonly
*/
Constants.HTTP_REQUEST_MODE = "request_mode";

