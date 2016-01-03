YUI.add("yuidoc-meta", function(Y) {
   Y.YUIDoc = { meta: {
    "classes": [
        "Adapter",
        "AdapterDescriptor.Handler",
        "AdapterDescriptor.Handler.Parameter",
        "ApplicationDescriptor",
        "Clause",
        "ConnectionRequest",
        "ConnectionResponse",
        "Constants",
        "Database",
        "DatabaseDescriptor",
        "DatabaseException",
        "DeploymentException",
        "Dictionary",
        "EntityDescriptor",
        "EntityDescriptor.Attribute",
        "EntityDescriptor.Index",
        "EntityDescriptor.Relationship",
        "EventHandler",
        "Function",
        "IAverage",
        "IAverageClause",
        "ICount",
        "ICountClause",
        "IDatabaseEvents",
        "IDelete",
        "IDeleteClause",
        "IGroupConcat",
        "IGroupConcatClause",
        "IMax",
        "IMaxClause",
        "IMin",
        "IMinClause",
        "INotificationEvents",
        "IResource",
        "ISelect",
        "ISelectClause",
        "IService",
        "IServiceEvents",
        "ISiminovEvents",
        "ISum",
        "ISumClause",
        "ISyncEvents",
        "ITotal",
        "ITotalClause",
        "LibraryDescriptor",
        "Log",
        "Message",
        "NotificationDescriptor",
        "NotificationException",
        "NotificationManager",
        "Registration",
        "Resources",
        "SIDatasHelper",
        "Select",
        "Service",
        "ServiceDescriptor",
        "ServiceDescriptor.Request",
        "ServiceDescriptor.Request.HeaderParameter",
        "ServiceDescriptor.Request.QueryParameter",
        "ServiceEventHandler",
        "ServiceException",
        "ServiceHandler",
        "Siminov",
        "SiminovException",
        "SiminovExceptionHandler",
        "StringBuilder",
        "SyncDescriptor",
        "SyncException",
        "SyncHandler",
        "SyncRequest",
        "Transaction",
        "Utils"
    ],
    "modules": [
        "Adapter",
        "Collection",
        "Connection",
        "Database",
        "Design",
        "Events",
        "Exception",
        "Function",
        "Impl",
        "Log",
        "Model",
        "Notification",
        "Parser",
        "Resource",
        "Service",
        "Sync",
        "Utils"
    ],
    "allModules": [
        {
            "displayName": "Adapter",
            "name": "Adapter",
            "description": "It is one which describes properties required to map Hybrid TO Native and vice-versa."
        },
        {
            "displayName": "Collection",
            "name": "Collection",
            "description": "A Collection represents a group of objects, know as its elements.\nSiminov Collection (SI Collection) is a set of classes and interfaces that implement commonly reusable collection data structures."
        },
        {
            "displayName": "Connection",
            "name": "Connection",
            "description": "It is one which describes properties required to describe connection information."
        },
        {
            "displayName": "Database",
            "name": "Database",
            "description": "Exposes methods which deal with events associated with database operation's.\nIt has methods such as (databaseCreated, databaseDroped, tableCreated, tableDroped, indexCreated)."
        },
        {
            "displayName": "Design",
            "name": "Design",
            "description": "Design contain all interfaces required by database layer to deal with database."
        },
        {
            "displayName": "Events",
            "name": "Events",
            "description": "It contain all Events triggered by Siminov Framework."
        },
        {
            "displayName": "Exception",
            "name": "Exception",
            "description": "It contain Siminov defined exceptions."
        },
        {
            "displayName": "Function",
            "name": "Function",
            "description": "It provide Util class needed by Siminov Framework."
        },
        {
            "displayName": "Impl",
            "name": "Impl",
            "description": "Exposes API's to get average value of all non-NULL X within a group. \nString and BLOB values that do not look like numbers are interpreted as 0.\nThe result of avg() is always a floating point value as long as at there is at least one non-NULL input even if all inputs are integers.\nThe result of avg() is NULL if and only if there are no non-NULL inputs."
        },
        {
            "displayName": "Log",
            "name": "Log",
            "description": "It provide Siminov Log features."
        },
        {
            "displayName": "Model",
            "name": "Model",
            "description": "It contain all Models as per required by Siminov Framework."
        },
        {
            "displayName": "Notification",
            "name": "Notification",
            "description": "It contain all Classes related to push notification."
        },
        {
            "displayName": "Parser",
            "name": "Parser",
            "description": "It contain all parser classes required by  Siminov Framework."
        },
        {
            "displayName": "Resource",
            "name": "Resource",
            "description": "It contain all class related to Siminov Framework resource."
        },
        {
            "displayName": "Service",
            "name": "Service",
            "description": "Exposes classes which deal with services.\nService is a client-side communication component that process and handles any hybrid service request. It performs long running operations in the background.\nA Service is a group of APIs which deals on one particular hybrid service."
        },
        {
            "displayName": "Sync",
            "name": "Sync",
            "description": "Exposes classes which deal with services.\nIt allows app to automatically checks for updates in the background, using battery and your data plan. \n\nYou can customise how often it does these checks by adjusting the Refresh Interval. If you don't framework to update regularly, you should set this value to zero to\nconserve both your battery and your data use."
        },
        {
            "displayName": "Utils",
            "name": "Utils",
            "description": "Exposes utility api's to framework/application"
        }
    ],
    "elements": []
} };
});