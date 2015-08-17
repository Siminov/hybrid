Siminov Framework provides few event notifiers which gets triggered based on particular action. Application have to provide implementation for these event notifiers and register them with Siminov.

> **Note**
>
> - **Event Handler:** It can be define in both Native and Web.
>
> - **Native Event Handler:** If you want to handle Event in Native then define Event Handler using Native language. Specify full model class path and name in ApplicationDescriptor.si.xml.
>
> - **Web Event Handler:** If you want to handle Event in Web then define Event Handler using JavaScript. Specify only JavaScript Function name in ApplicationDescriptor.si.xml.
>
> - **Both (Native/Web) Event Handler:** If you want to handle Event in both Native and Web then define Event Handler in both Native and JavaScript. Specify full Java class path and name in ApplicationDescriptor.si.xml, no need to define for JavaScript because Siminov will automatically assume same name for it.

***
**IDatabase** is a event handler which automatically gets triggered when action happen with database. Application have to provide implementation for _IDatabase_ event notifier and register them with siminov.

**Android API: Database Event**

```java

    public interface IDatabaseEvents {

        public void onDatabaseCreated(final DatabaseDescriptor databaseDescriptor);
	
        public void onDatabaseDropped(final DatabaseDescriptor databaseDescriptor);
	
        public void onTableCreated(final DatabaseDescriptor databaseDescriptor, final DatabaseMappingDescriptor databaseMapping);
	
        public void onTableDropped(final DatabaseDescriptor databaseDescriptor, final DatabaseMappingDescriptor databaseMapping);
	
        public void onIndexCreated(final DatabaseDescriptor databaseDescriptor, final DatabaseMappingDescriptor databaseMapping, Index index);
	
        public void onIndexDropped(final DatabaseDescriptor databaseDescriptor, final DatabaseMappingDescriptor databaseMapping, Index index);
	
    }

```

#### iOS API: Database Events

```objective-c

    @protocol SICIDatabaseEvents <NSObject>

    - (void)onDatabaseCreated:(SICDatabaseDescriptor* const)databaseDescriptor;

    - (void)onDatabaseDropped:(SICDatabaseDescriptor* const)databaseDescriptor;

    - (void)onTableCreated:(SICDatabaseDescriptor* const)databaseDescriptor databaseMappingDescriptor:(SICDatabaseMappingDescriptor* const)databaseMappingDescriptor;

     - (void)onTableDropped:(SICDatabaseDescriptor* const)databaseDescriptor databaseMappingDescriptor:(SICDatabaseMappingDescriptor* const)databaseMappingDescriptor;

     - (void)onIndexCreated:(SICDatabaseDescriptor* const)databaseDescriptor databaseMappingDescriptor:(SICDatabaseMappingDescriptor * const)databaseMappingDescriptor index:(SICIndex *)index;

     - (void)onIndexDropped:(SICDatabaseDescriptor* const)databaseDescriptor databaseMappingDescriptor:(SICDatabaseMappingDescriptor* const)databaseMappingDescriptor index:(SICIndex *)index;

    @end

```

**Windows Sample: Database Events**

```c#

    public interface IDatabaseEvents 
    {

        void OnDatabaseCreated(DatabaseDescriptor databaseDescriptor);
	
        void OnDatabaseDropped(DatabaseDescriptor databaseDescriptor);
	
        void OnTableCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMapping);
	
        void OnTableDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMapping);
	
        void OnIndexCreated(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMapping, Index index);
	
        void OnIndexDropped(DatabaseDescriptor databaseDescriptor, DatabaseMappingDescriptor databaseMapping, Index index);
	
    }

```


**JavaScript API: Database Event**

```javascript

    function IDatabaseEvents {

        this.onDatabaseCreated = function(databaseDescriptor) {};
	
        this.onDatabaseDropped = function(databaseDescriptor) {};
	
        this.onTableCreated = function(databaseDescriptor, databaseMappingDescriptor) {};
	
        this.onTableDropped = function(databaseDescriptor, databaseMappingDescriptor) {};
	
        this.onIndexCreated = function(databaseDescriptor, databaseMappingDescriptor, index) {};
	
        this.onIndexDropped = function(databaseDescriptor, databaseMappingDescriptor, index) {};
	
    }

```



###### 1. On Database Created - [Android:onDatabaseCreated(DatabaseDescriptor) | iOS:onDatabaseCreated:DatabaseDescriptor | Windows:OnDatabaseCreated(DatabaseDescriptor) | JavaScript:Android:onDatabaseCreated(databaseDescriptor)]

It is triggered when database is created based on schema defined in DatabaseDescriptor.si.xml file. This API provides DatabaseDescriptor object for which database is created.

###### 2. On Database Dropped - [Android:onDatabaseDropped(DatabaseDescriptor) |  iOS:onDatabaseDropped:DatabaseDescriptor | Windows:OnDatabaseDropped(DatabaseDescriptor) | JavaScript:onDatabaseDropped(databaseDescriptor)]

It is triggered when database is dropped. This API provides DatabaseDescriptor object for which database is dropped.

###### 3. On Table Created - [Android:onTableCreated(DatabaseDescriptor, DatabaseMappingDescriptor) | iOS:onTableCreated:DatabaseDescriptor databaseMappingDescriptor:DatabaseMappingDescriptor | Windows:OnTableCreated(DatabaseDescriptor, DatabaseMappingDescriptor) | JavaScript:onTableCreated(databaseDescriptor, databaseMappingDescriptor)]

It is triggered when a table is created in database. This API provides Database descriptor object and Database mapping descriptor object which describes table structure.

###### 4. On Table Dropped - [Android:onTableDropped(DatabaseDescriptor, DatabaseMappingDescriptor) | iOS:onTableDropped:DatabaseDescriptor databaseMappingDescriptor:DatabaseMappingDescriptor | Windows:OnTableDropped(DatabaseDescriptor, DatabaseMappingDescriptor) | JavaScript:onTableDropped(databaseDescriptor, databaseMappingDescriptor)]

It is triggered when a table is deleted from database. This API provides Database descriptor object and Database mapping descriptor object for which table is dropped.

###### 5. On Index Created - [Android:onIndexCreated(DatabaseDescriptor, DatabaseMappingDescriptor, Index) | iOS:onIndexCreated:DatabaseDescriptor databaseMappingDescriptor:DatabaseMappingDescriptor index:Index | Windows:OnIndexCreated(DatabaseDescriptor, DatabaseMappingDescriptor, Index) | JavaScript:onIndexCreated(databaseDescriptor, databaseMappingDescriptor, index)]

It is triggered when a index is created on table. This API provides DatabaseMappingDescriptor and Index object which defines table and index structure.

###### 6. On Index Dropped - [Android:onIndexDropped(DatabaseDescriptor, DatabaseMappingDescriptor, Index) | iOS:onIndexDropped:DatabaseDescriptor databaseMappingDescriptor:DatabaseMappingDescriptor index:Index | Windows:onIndexDropped(DatabaseDescriptor, DatabaseMappingDescriptor, Index) | JavaScript:onIndexDropped(databaseDescriptor, databaseMappingDescriptor, index)]

It is triggered when a index is dropped from table. This API provides Database descriptor object, Database mapping descriptor object and Index object which defines table and index for which index is dropped.

## Android Sample: IDatabase Event Handler

***

![Android Sample: IDatabase Event Handler] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/idatabase_application_sample_example.png "Android Sample: IDatabase Event Handler")

***

## iOS Sample: SICIDatabase Event Handler

***

![iOS Sample: SICIDatabase Event Handler] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/idatabase_application_sample_example.png "iOS Sample: SICIDatabase Event Handler")

***

## Windows Sample: IDatabase Event Handler

***

![Windows Sample: IDatabase Event Handler] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/idatabase_application_sample_example.png "Windows Sample: IDatabase Event Handler")

***