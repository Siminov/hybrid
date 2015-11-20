//
//  SIHApplicationDescriptorReader.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SICSiminovSAXDefaultHandler.h"
#import "SIHApplicationDescriptor.h"
#import "SICResourceManager.h"
#import "SIHConstants.h"

/**
 * Exposes methods to parse Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
	
	
	<siminov>
 
 <!-- General Application Description Properties -->
 
 <!-- Mandatory Field -->
 <property name="name">application_name</property>
 
 <!-- Optional Field -->
 <property name="description">application_description</property>
 
 <!-- Mandatory Field (Default is 0.0) -->
 <property name="version">application_version</property>
	
	
 
 <!-- Siminov Framework Performance Properties -->
 
 <!-- Optional Field (Default is true)-->
 <property name="load_initially">true/false</property>
	
	
 
 <!-- Database Descriptors Used By Application (zero-to-many) -->
 <!-- Optional Field's -->
 <database-descriptors>
 <database-descriptor>full_path_of_database_descriptor_file</database-descriptor>
 </database-descriptors>
 
	
 <!-- Services -->
 <service-descriptors>
 
 <!-- Service -->
 <service-descriptor>full_path_of_service</service-descriptor>
 
 </service-descriptors>
	
 
 
 <!-- Sync Handlers -->
 <!-- Sync Handler -->
 <sync-descriptors>
 
 <sync-descriptor>
 
 <!-- Mandatory Field -->
 <property name="name">name_of_sync_handler</property>
 
 <!-- Optional Field -->
 <property name="sync_interval">sync_interval_in_millisecond</property>
 
 <!-- Optional Field -->
 <!-- Default: SCREEN -->
 <property name="type">INTERVAL|SCREEN</property>
 
 <!-- Services -->
 <!-- Service -->
 <services>
 
 <service>name_of_service.name_of_api</service>
 
 </services>
 </sync-descriptor>
 
 </sync-descriptors>
 
	
 <!-- Push Notification -->
 <notification-descriptor>
 
 <!-- Optional Field -->
 <property name="name_of_property">value_of_property</property>
	
 </notification-descriptor>
 
 
 <adapter-descriptors>
 
 <!-- Adapter Paths -->
 <adapter-descriptor>adapter_path</adapter-descriptor>
 
 </adapter-descriptors>
 
 
 <!-- Library Descriptors Used By Application (zero-to-many) -->
 <!-- Optional Field's -->
 <library-descriptors>
 <library-descriptor>full_path_of_library_descriptor_file</library-descriptor>
 </library-descriptors>
 
 
 <!-- Event Handlers Implemented By Application (zero-to-many) -->
 
 <!-- Optional Field's -->
 <event-handlers>
 <event-handler>full_java_class_path_of_event_handler/javascript_class_path_of_event_handler (ISiminovHandler/IDatabaseHandler)</event-handler>
 </event-handlers>
	
	</siminov>
 
	}
	
 </pre>
	</p>
 *
 */
@interface SIHApplicationDescriptorReader : SICSiminovSAXDefaultHandler {
    
    SIHApplicationDescriptor *applicationDescriptor;
    
    SICResourceManager *resourceManager;
    
    NSMutableString *tempValue;
    NSString *propertyName;
    
    SIKNotificationDescriptor *notificationDescriptor;
    
    bool isNotificationDescriptor;
}

/**
 * Get application descriptor object.
 * @return Application Descriptor Object.
 */
- (SIHApplicationDescriptor *)getApplicationDescriptor;


@end
