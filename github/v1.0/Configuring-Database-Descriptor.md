Database Descriptor is the one who defines the schema of database.

```xml

                  <!-- Design Of DatabaseDescriptor.si.xml -->

    <database-descriptor>

        <!-- General Database Descriptor Properties -->
            <!-- Mandatory Field -->
        <property name="database_name">name_of_database_file</property>

            <!-- Optional Field (Default is sqlite)-->
        <property name="type">type_of_database</property>

            <!-- Mandatory Field -->
        <property name="version">database_version</property>
			
            <!-- Optional Field -->
        <property name="description">database_description</property>

            <!-- Optional Field (Default is false) -->
        <property name="transaction_safe">true/false</property>
	
            <!-- Optional Field (Default is false) -->
        <property name="external_storage">true/false</property>
		


        <!-- Database Mapping Descriptor Paths Needed Under This Database Descriptor -->
            <!-- Optional Field -->
        <database-mapping-descriptors>
            <database-mapping-descriptor>full_path_of_database_mapping_descriptor_file</database-mapping-descriptor>
        </database-mapping-descriptors>
	
    </database-descriptor>

```

```xml

                 <!-- Android Sample: Database Descriptor -->

    <database-descriptor>

        <property name="database_name">SIMINOV-WEB-PHONEGAP-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Sample Database Config</property>
        <property name="version">1</property>
        <property name="transaction_safe">true</property>
        <property name="external_storage">false</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>Liquor-Mappings/Liquor.si.xml</database-mapping-descriptor>
            <database-mapping-descriptor>Liquor-Mappings/LiquorBrand.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>

    </database-descriptor>

```


```xml

                 <!-- iOS Sample: Database Descriptor -->

    <database-descriptor>

        <property name="database_name">SIMINOV-WEB-PHONEGAP-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Sample Database Config</property>
        <property name="version">1</property>
        <property name="transaction_safe">true</property>
        <property name="external_storage">false</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>Liquor-Mappings/Liquor.si.xml</database-mapping-descriptor>
            <database-mapping-descriptor>Liquor-Mappings/LiquorBrand.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>

    </database-descriptor>

```

```xml

                 <!-- Windows Sample: Database Descriptor -->

    <database-descriptor>

        <property name="database_name">SIMINOV-WEB-PHONEGAP-SAMPLE</property>
        <property name="description">Siminov Web Phonegap Sample Database Config</property>
        <property name="version">1</property>
        <property name="transaction_safe">true</property>
        <property name="external_storage">false</property>

        <database-mapping-descriptors>
            <database-mapping-descriptor>Liquor-Mappings/Liquor.si.xml</database-mapping-descriptor>
            <database-mapping-descriptor>Liquor-Mappings/LiquorBrand.si.xml</database-mapping-descriptor>
        </database-mapping-descriptors>

    </database-descriptor>

```


> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:getProperties | JavaScript:getProperties]**: It will return all properties associated with Database Descriptor.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property) | JavaScript:getProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property | Windows:ContainsProperty(Name-of-Property) | JavaScript:containsProperty(Name-of-Property)]:** It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty:Name-of-Property value:Value-of-Property | Windows:AddProperty(Name-of-Property, Value-of-Property) | JavaScript:addProperty(Name-of-Property, Value-of-Property)]**: It will add new property to the  collection of Database Descriptor properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty:Name-of-Property | Windows:RemoveProperty(Name-of-Property) | JavaScript:removeProperty(Name-of-Property)]**: It will remove property from Database Descriptor properties based on name provided.

## Database Descriptor Elements

###### 1. General Properties About Database

- _**database_name**_*: Name of database. It is mandatory field. All database files (.db)'s will be placed under the this folder name.

**Android Sample: Application data folder structure**
***

![Android Sample: Application data folder structure] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_database_name.png "Android Sample: Application data folder structure")

***

**iOS Sample: Application data folder structure**
***

![iOS Sample: Application data folder structure] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_database_name.png "iOS Sample: Application data folder structure")

***


**Windows Sample: Application data folder structure**
***

![Windows Sample: Application data folder structure] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_application_data_folder_structure_for_database_name.png "Windows Sample: Application data folder structure")

***

- _**type**_: It defines the type of database. It is optional field. Default is sqlite.

- _**descriptor**_: Description of database. It is optional field.

- _**transaction_safe**_: (TRUE|FALSE), Control whether or not the database is made thread-safe by using locks around critical sections.

This is pretty expensive, so if you know that your DB will only be used by a multi threads then you should set this to true.

The default is false. It is optional field.

- _**external_storage**_: It specifies whether database resources needs to be saved on external storage or not (SDCard). It is optional field. Default is false.

> **Note**
>
> Siminov does not provide any security for database. If you want your database data needs to encrypted, then you can include SQLCipher implementation provided by Siminov Framework in your application. For more detail see SQLCipher Encryption section of this developer guide.

###### 2. Paths of database mapping descriptor needed under this database descriptor.

> **Note**
>
> - Provide full database mapping descriptor file path if you have used XML format to define ORM.
>
> - Provide full class path of database mapping descriptor POJO class if you have used annotation to define ORM.


###### 4. Important points about DatabaseDescriptor.si.xml

> **Note**
>
> - You can specify any name for DatabaseDescriptor.si.xml file.
>
> - If any database folder is created, it will be on the name of database defined in DatabaseDescriptor.si.xml file.

## Android Sample: Database Descriptor

***
![Android Sample: Database Descriptor] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_application_database_descriptor_path_example.png "Android Sample: Database Descriptor")
***

## iOS Sample: Database Descriptor

***
![iOS Sample: Database Descriptor] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_application_database_descriptor_path_example.png "iOS Sample: Database Descriptor")
***


## Windows Sample: Database Descriptor

***
![Windows Sample: Database Descriptor] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_application_database_descriptor_path_example.png "Windows Sample: Database Descriptor")
***



