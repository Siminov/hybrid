Sync Descriptor is the one which define synchronization of data through service.


```xml

                   <!-- Design of Sync Descriptor -->
    <sync-descriptor>
            
            <!-- Mandatory Field -->
        <property name="name">name_of_sync_handler</property>
			
            <!-- Optional Field -->
        <property name="sync_interval">sync_interval_in_millisecond</property>
     				
            <!-- Optional Field -->
                <!-- Default: SCREEN -->
        <property name="type">INTERVAL|SCREEN|INTERVAL-SCREEN</property>
			
        <!-- Service Descriptors -->
            <!-- Service Descriptor -->
        <service-descriptors>
     		    
            <service-descriptor>name_of_service_descriptor.name_of_api</service-descriptor>
     		    
        </service-descriptors>

    </sync-descriptor>

```

```xml

               <!-- Android Sample: Sync Descriptor -->

    <sync-descriptor>
        <property name="name">SYNC-LIQUORS</property>
        <property name="interval">5000</property>
	
        <service-descriptors>
            <service-descriptor>SIMINOV-WEB-LIQUORS-SERVICE.GET-LIQUORS</service-descriptor>
        </service-descriptors>
    </sync-descriptor>

```

```xml

               <!-- iOS Sample: Sync Descriptor -->

    <sync-descriptor>
        <property name="name">SYNC-LIQUORS</property>
        <property name="interval">5000</property>
	
        <service-descriptors>
            <service-descriptor>SIMINOV-WEB-LIQUORS-SERVICE.GET-LIQUORS</service-descriptor>
        </service-descriptors>
    </sync-descriptor>

```

```xml

               <!-- Windows Sample: Sync Descriptor -->

    <sync-descriptor>
        <property name="name">SYNC-LIQUORS</property>
        <property name="interval">5000</property>
	
        <service-descriptors>
            <service-descriptor>SIMINOV-WEB-LIQUORS-SERVICE.GET-LIQUORS</service-descriptor>
        </service-descriptors>
    </sync-descriptor>

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

## Sync Descriptor Elements

- _**name**_*: Name of Sync Descriptor. It is mandatory property.

- **sync_interval**: Sync Interval related to Sync. It is optional property.

- _**type**_: INTERVAL|SCREEN|INTERVAL-SCREEN. Type of Sync. It is optional property.

- _**Services**_: Name of services include under the Sync Descriptor.

###### Services: 
Name of services include under the Sync Descriptor.

> **Note**
>
> - Name of the service descriptor.
>
> - Name of the API needed for sync in Service Descriptor.
>
> - Put (dot). between service name and api name.

## Android Sample: Sync Descriptor
***
![Android Sample: Sync Descriptor] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_sync_descriptor_path_example.png "Android Sample: Sync Descriptor")
***

## iOS Sample: Sync Descriptor
***
![iOS Sample: Sync Descriptor] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_sync_descriptor_path_example.png "iOS Sample: Sync Descriptor")
***


## Windows Sample: Sync Descriptor
***
![Windows Sample: Sync Descriptor] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_sync_descriptor_path_example.png "Windows Sample: Sync Descriptor")
***

