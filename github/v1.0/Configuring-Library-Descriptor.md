Library Descriptor is the one who defines the properties of library.

```xml

                    <!-- Design Of LibraryDescriptor.si.xml -->

    <library-descriptor>

        <!-- General Properties Of Library -->
            <!-- Mandatory Field -->
        <property name="name">name_of_library</property>
	
            <!-- Optional Field -->
        <property name="description">description_of_library</property>

	
        <!-- Database Mappings Needed Under This Library Descriptor -->
	
            <!-- Optional Field -->
                <!-- Database Mappings -->
        <database-mapping-descriptors>
            <database-mapping-descriptor>name_of_database_descriptor.full_path_of_database_mapping_descriptor_file</database-mapping-descriptor>
        </database-mapping-descriptors>
	 
	
        <!-- Service Descriptors -->
            <!-- Optional Field -->
            <!-- Service Descriptor -->
        <service-descriptors>
            <service-descriptor>full_path_of_service-descriptor_file</service-descriptor>
        </service-descriptors>
	
	
        <!-- Sync Descriptors -->
            <!-- Optional Field -->
            <!-- Sync Descriptor -->
        <sync-descriptors>
            <sync-descriptor>full_path_of_sync_descriptor_file</sync-descriptor>
        </sync-descriptors>
	
        <!-- Adapter Descriptors -->
            <!-- Optional Field -->
            <!-- Adapter Descriptor -->
        <adapter-descriptors>
            <adapter-descriptor>full_path_of_adapter_descriptor_file</adapter-descriptor>
        </adapter-descriptors>	
		
    </library-descriptor>

```


```xml

                 <!-- Android Sample: Library Descriptor -->

    <library-descriptor>

        <property name="name">SIMINOV-WEB-PHONEGAP-LIBRARY-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Library Sample</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>SIMINOV-WEB-PHONEGAP-SAMPLE.Credential.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>
	 
    </library-descriptor>

```


```xml

                 <!-- iOS Sample: Library Descriptor -->

    <library-descriptor>

        <property name="name">SIMINOV-WEB-PHONEGAP-LIBRARY-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Library Sample</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>SIMINOV-WEB-PHONEGAP-SAMPLE.Credential.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>
	 
    </library-descriptor>

```


```xml

                 <!-- Windows Sample: Library Descriptor -->

    <library-descriptor>

        <property name="name">SIMINOV-WEB-PHONEGAP-LIBRARY-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Library Sample</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>SIMINOV-WEB-PHONEGAP-SAMPLE.Credential.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>
	 
    </library-descriptor>

```

> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:GetProperties | JavaScript:getProperties]**: It will return all properties associated with Library Descriptor.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property) | JavaScript:getProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property | Windows:ContainsProperty(Name-of-Property) | JavaScript:containsProperty(Name-of-Property)]**: It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty(Name-of-Property, Value-of-Property) | Windows:AddProperty(Name-of-Property, Value-of-Property) | JavaScript:addProperty(Name-of-Property, Value-of-Property)]:** It will add new property to the  collection of Library Descriptor properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty:Name-of-Property | Windows:RemoveProperty(Name-of-Property) | JavaScript:removeProperty(Name-of-Property)]:** It will remove property from Library Descriptor properties based on name provided.


## Library Descriptor Elements

###### 1. General properties about library

- _**name**_*: Name of library. It is mandatory field.

- _**descriptor**_: Description of library. It is optional field.

###### 2. Database mapping descriptor paths needed under this database descriptor.

> **Note**
>
> - Provide full database mapping descriptor file path if you have used xml format to define ORM.
>
> - Provide full class path of database mapping descriptor model class if you have used annotation to define ORM.


###### 3. Service descriptor paths needed under the library
- Provide full service descriptor file path.


###### 4. Sync descriptor paths needed under the library
- Provide full sync descriptor file path.

###### 5. Adapter descriptor paths needed under this database descriptor.

> **Note**
>
> - Provide full web adapter file path.

###### Important points about library descriptor

> **Note**
>
> - Library descriptor file name should be same as LibraryDescriptor.si.xml.
>
> - It should always be in root package specified in DatabaseDescriptor.si.xml file.

## Android Sample: Library Descriptor

***
![Android Sample: Library Descriptor] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_library_sample_path_example.png "Android Sample: Library Descriptor")
***

## iOS Sample: Library Descriptor

***
![iOS Sample: Library Descriptor] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_library_sample_path_example.png "iOS Sample: Library Descriptor")
***



## Windows Sample: Library Descriptor

***
![Windows Sample: Library Descriptor] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_library_sample_path_example.png "Windows Sample: Library Descriptor")
***
