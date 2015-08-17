Application Descriptor is the one who connects application to Siminov framework. It provide basic information about application, which defines the behaviour of application.

```xml

                   <!-- Design Of ApplicationDescriptor.si.xml -->
    <siminov>
    
        <!-- General Application Description Properties -->
            <!-- Mandatory Field -->
        <property name="name">application_name</property>	
	
            <!-- Optional Field -->
        <property name="description">application_description</property>
	
            <!-- Mandatory Field (Default is 0.0) -->
        <property name="version">application_version</property>


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

```


```xml

                <!-- Android Sample: Application Descriptor -->

    <siminov>

        <property name="name">SIMINOV WEB PHONEGAP SAMPLE</property>	
        <property name="description">Siminov Web Phonegap Sample Application</property>
        <property name="version">0.9</property>


        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
    
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	
	
        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">853402201726</property>
        </notification-descriptor>
	

        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>siminov.web.phonegap.sample.events.SiminovEventHandler</event-handler>
            <event-handler>siminov.web.phonegap.sample.events.DatabaseEventHandler</event-handler>
            <event-handler>siminov.web.phonegap.sample.events.NotificationEventHandler</event-handler>
            <event-handler>siminov.web.phonegap.sample.events.SyncEventHandler</event-handler>
        </event-handlers>

 	
        <!-- Library Descriptors -->
        <library-descriptors>
            <library-descriptor>siminov.web.phonegap.library.sample.resources</library-descriptor>
        </library-descriptors> 
 			
    </siminov>

```


```xml

                <!-- iOS Sample: Application Descriptor -->

    <siminov>

        <property name="name">SIMINOV WEB PHONEGAP SAMPLE</property>	
        <property name="description">Siminov Web Phonegap Sample Application</property>
        <property name="version">0.9</property>


        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
    
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	
	
        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">853402201726</property>
        </notification-descriptor>
	

        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>SiminovEventHandler</event-handler>
            <event-handler>DatabaseEventHandler</event-handler>
            <event-handler>NotificationEventHandler</event-handler>
            <event-handler>SyncEventHandler</event-handler>
        </event-handlers>

 	
        <!-- Library Descriptors -->
        <library-descriptors>
            <library-descriptor>Siminov.Web.Phonegap.Library.Sample.Resources</library-descriptor>
        </library-descriptors> 
 			
    </siminov>

```

```xml
                <!-- Windows Sample: Application Descriptor -->

    <siminov>

        <property name="name">SIMINOV WEB PHONEGAP SAMPLE</property>	
        <property name="description">Siminov Web Phonegap Sample Application</property>
        <property name="version">0.9</property>


        <!-- Database Descriptors -->
        <database-descriptors>
            <database-descriptor>DatabaseDescriptor.si.xml</database-descriptor>
        </database-descriptors>

	
        <!-- Service Descriptors -->
        <service-descriptors>
            <service-descriptor>Liquor-Services/Liquors.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/LiquorBrands.si.xml</service-descriptor>
            <service-descriptor>Liquor-Services/NotificationService.si.xml</service-descriptor>
        </service-descriptors>
	
    
        <!-- Sync Descriptors -->
        <syn-descriptors>
            <sync-descriptor>Liquor-Sync/Liquors.si.xml</sync-descriptor>
            <sync-descriptor>Liquor-Sync/LiquorBrands.si.xml</sync-descriptor>
        </syn-descriptors>
	
	
        <!-- Notification Descriptor -->
        <notification-descriptor>
            <property name="sender_id">853402201726</property>
        </notification-descriptor>
	

        <!-- Event Handlers -->
        <event-handlers>
            <event-handler>Siminov.Web.Phonegap.Sample.Events.SiminovEventHandler</event-handler>
            <event-handler>Siminov.Web.Phonegap.Sample.Events.DatabaseEventHandler</event-handler>
            <event-handler>Siminov.Web.Phonegap.Sample.Events.NotificationEventHandler</event-handler>
            <event-handler>Siminov.Web.Phonegap.Sample.Events.SyncEventHandler</event-handler>
        </event-handlers>

 	
        <!-- Library Descriptors -->
        <library-descriptors>
            <library-descriptor>Siminov.Web.Phonegap.Library.Sample.Resources</library-descriptor>
        </library-descriptors> 
 			
    </siminov>

```

> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:GetProperties | JavaScript:getProperties]**: It will return all properties associated with Application Descriptor.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property) | JavaScript:getProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property | Windows:ContainsProperty(Name-of-Property) | JavaScript:containsProperty(Name-of-Property)]**: It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty:Name-of-Property value:Value-of-Property | Windows:AddProperty(Name-of-Property, Value-of-Property) | JavaScript:addProperty(Name-of-Property, Value-of-Property)]**: It will add new property to the  collection of Application Descriptor properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty:Name-of-Property | Windows:RemoveProperty(Name-of-Property) | JavaScript:removeProperty(Name-of-Property)]**: It will remove property from Application Descriptor properties based on name provided.


## Application Descriptor Elements

###### 1. General properties about application

- _**name**_*: Name of application. It is mandatory field. If any resources is created by Siminov Framework then it will be under this folder name.

**Android Sample: Application data folder based on name defined**

***

![Application Data Folder Based On Name Defined] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_application_name.png "Application Data Folder Based On Name Defined")

***


**iOS Sample: Application data folder based on name defined**

***

![Application data folder based on name defined] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_application_name.png "Application data folder based on name defined")

***

**Windows Sample: Application data folder based on name defined**

***

![Application data folder based on name defined] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_application_name.png "Application data folder based on name defined")

***


- _**descriptor**_: Description of application. It is optional field. 

- _**version**_: Version of application. It is mandatory field. Default is 0.0.

###### 3. Path of database descriptor's used in application
- Path of all database descriptor's used in application.
- Every database descriptor will have its own database object.

###### 4. Path of service descriptor's used in application
- Path of all service descriptor's used in application.

###### 5. Path of sync descriptor's used in application
- Path of all sync descriptor's used in application.

###### 6. Properties of notification descriptor used in application
- All properties needed to support push notification.

###### 7. Paths of adapter descriptor's needed by the application
- Path of all adapter descriptor's used in application.

###### 8. Event handlers implemented by application 

- Siminov Framework provides two type of event handlers

- **Android: ISiminovEvents | iOS:SICISiminovEvents | Windows:ISiminovEvents | JavaScript:ISiminovEvents**:
It contains events associated with life cycle of Siminov Framework. such as **On Siminov Initialized**, **On First Time Siminov Initialized**, **On Siminov Stopped**.

- **Android:IDatabaseEvents | iOS:SICIDatabaseEvents | Windows:IDatabaseEvents | JavaScript:IDatabaseEvents**:
It contains events associated with database operations. such as **On Database Created**, **On Database Dropped**, **On Table Created**, **On Table Dropped**, **On Index Created**, **On Index Dropped**.

- **Android:ISyncEvents | iOS:SIKISyncEvents | Windows:ISyncEvents | JavaScript:ISyncEvents**:
It contain events associated with sync operations. such as **On Sync Start**, **On Sync Queued**, **On Sync Finished**, **On Sync Terminated**.

- **Android:INotificationEvents | iOS:SIKINotificationEvents | Windows:INotificationEvents | JavaScript:INotificationEvents**:
It contain events associated with notification. such as **On Notification Registered**, **On Notification Unregistered**, **On Notification Message**, **On Notification Error**.

- Application can implement these event handlers based on there requirement.

> **Note**
>
> - **Event Handler:** It can be define in both Native and Web.
>
> - **Native Event Handler:** If you want to handle Event in Native then define Event Handler using **Android - Java, iOS - Objective C, Windows - C#**. Specify full class path and name in ApplicationDescriptor.si.xml.
>
> - **Web Event Handler:** If you want to handle Event in Web then define Event Handler using JavaScript. Specify only JavaScript Function name in ApplicationDescriptor.si.xml.
>
> - **Both (Native/Web) Event Handler:** If you want to handle Event in both Native and Web then define Event Handler in both Native and JavaScript. Specify full class path and name in ApplicationDescriptor.si.xml, no need to define for JavaScript because Siminov will automatically assume same name for it. 

###### 8. Paths of library descriptor needed by the application 

> **Note**
>
> - Provide full package name under which LibraryDescriptor.si.xml file is placed.
>
> - Siminov framework will automatically read LibraryDescriptor.si.xml file defined under package name provided.


> **Note**
>
> - Application descriptor name should always be same as ApplicationDescriptor.si.xml only.
>
> - It should always be in root folder of application assets.


## Android Sample: Application Descriptor

***
![Android Sample: Application Descriptor] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_descriptor_path_example.png "Android Sample: Application Descriptor")
***

## iOS Sample: Application Descriptor

***
![iOS Sample: Application Descriptor] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_descriptor_path_example.png "iOS Sample: Application Descriptor")
***

## Windows Sample: Application Descriptor

***
![Windows Sample: Application Descriptor] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_application_descriptor_path_example.png "Windows Sample: Application Descriptor")
***

