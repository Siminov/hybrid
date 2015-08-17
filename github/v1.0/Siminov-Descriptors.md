![Siminov Descriptors] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_descriptors.png "Siminov Descriptors")

Siminov Framework works based on a set of defined descriptors which can be broadly classified as

- _**Application Descriptor - ApplicationDescriptor.si.xml**_: Application Descriptor is the one who connects application to Siminov Framework. It provide basic information about application, which defines the behavior of application.

- _**Database Descriptor - DatabaseDescriptor.si.xml**_: Database Descriptor is the one who defines the schema of database.

- _**Library Descriptor - LibraryDescriptor.si.xml**_: Library Descriptor is the one who defines the properties of library.

- _**Database Mapping Descriptor - DatabaseMappingDescriptor.si.xml**_: Database Mapping Descriptor is one which does ORM, it maps POJO class to database table.

- _**Service Descriptor - ServiceDescriptor.si.xml**_: Service Descrptor is one which define the structure of your RESTful Web Service API.

- _**Sync Descriptor - SyncDescriptor.si.xml**_: Sync Descriptor is the one which define synchronization of data through service.

- _**Adapter Descriptor - AdapterDescriptor.si.xml**_: Adapter Descriptor is one which describes properties required to map Web To Native and vice-versa. It is optional descriptor.

All these descriptor should be placed in application assets folder.

**Android Sample: Application Structure**
***

![Siminov Web Sample Application] (https://raw.github.com/Siminov/android-web/doc-resources/github-wiki-resources/application_assests_structure.png "Siminov Web Sample Application")

***

**iOS Sample: Application Structure**
***

![iOS Sample: Application Structure] (https://raw.github.com/Siminov/ios-web/doc-resources/github-wiki-resources/application_assests_structure.png "iOS Sample: Application Structure")

***


> Note:
>
> - All descriptor file name should end with **si.xml**