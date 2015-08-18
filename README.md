Siminov Web (Object Relationship Mapping) & (RESTful) - Android
===================================================

A web application, by definition is derived from a combination of technologies, approaches or elements of different kinds. With respect to mobile applications, a web application leverages best of both native and mobile web technologies.

In web environment, it is very difficult to handle database and web service calls. Siminov makes application developer life easy by mapping JavaScript/Native model objects (Android - Java | iOS - Objective C | Windows - C#) to relational database and handling all web service calls using the native container capability.

Siminov can be used with PhoneGap, React, Sencha, Xamarin. It enables application developers to build applications for mobile devices using JavaScript, HTML5 and CSS3, instead of device-specific languages.

Siminov not only takes care of the mapping from JavaScript/Native model classes to database tables (and from JavaScript/Native model data types to SQL data types), but also provides data query and retrieval facilities. It can significantly reduce development time otherwise spent with manual data handling in SQLite. Siminov design goal is to relieve the developer from 99% of common data persistence-related programming tasks by eliminating the need for manual, hand-crafted data processing using SQLite. However, unlike many other persistence solutions, Siminov does not hide the power of SQLite from you and guarantees that your investment in relational technology and knowledge is as valid as always.

Get Started
-----------
Get the source

  git clone http://github.com/siminov/android-web.git
  
  
Features
--------

###### 1. Easy Configuration
Siminov provides a easy set of defined descriptors which can be broadly classified as 
	
	|- ApplicationDescriptor.si.xml 
	|- DatabaseDescriptor.si.xml
	|- LibraryDescriptor.si.xml
	|- EntityDescriptor.si.xml
	|- AdapterDescriptor.si.xml
	|- ServiceDescriptor.si.xml
	|- SyncDescriptor.si.xml
	|- NotificationDescriptor.si.xml

###### 2. Use With 3rd Party Frameworks
Easy to integrate with any 3rd party frameworks, It provides Object Relationship Mapping between JavaScript Class and Database 

###### 3. Synchronous Database Operations
All Database operations in Siminov Web Framework are Synchronous, because it use communication channel provided by Native Platform. This is an advantage over Phonegap where all database operations are asynchronous.


###### 4. Handle Application Initialization
All resources required by application are created and managed by siminov core. (Eg: Creating Database, Deploying Application).

###### 5. Handle Multiple Schema's
It also supports multiple schema's if required by application.

###### 6. Events Notifier
It provides event notifiers which gets triggered based on particular action

	Eaxmple: 
	|- Siminov Initialized
	|- Siminov Stopped
	|- Database Created and Dropped
	|- Table Create and Dropped
	|- Index Created and Dropped
	
###### 7. Database API's

	|- Database Create and Drop
	|- Table Create and Drop
	|- Index Create and Drop
	|- Fetch
	|- Save
	|- Update
	|- Save Or Update
	|- Delete
	
###### 8. Aggregation API's
	
	|- Count
	|- Average
	|- Sum
	|- Total
	|- Minimum
	|- Maximum
	|- Group Concat
	
###### 9. Database Transaction API's

	|- Begin Transaction
	|- Commit Transaction
	|- End Transaction
	
	

###### 10. Database Encryption (SQLCipher)
Data Secuirty plays important role when we talk about database. It protect your database from desctructive forces and the unwanted actions of unauthorized users.

Siminov provides implementation for SQLCipher to protect application database from any unauthorized users.


###### 11. Handling Libraries
An android library project is a development project that holds shared android source code and resources. Other android application projects can reference the library project and, at build time, include its compiled sources in their .apk files.


###### 12. Push Notification
Push notification is provide by all the platforms but in different forms and implementation. It provides a unique and generic implementation on all platforms with same APIs bundle and architecture.


###### 13. Synchronization
Synchronizing your app data with service data is a tough job.It allows app to automatically checks for updates in the background. There are different ways by which you can synchronize your app data

  |- Time Interval
  |- Screen Click
  |- Time Interval + Screen Click
  
  
  

Siminov provides mechanism to configure Core for your library projects.


LICENSE
-------

 
<b> SIMINOV FRAMEWORK </b>
 <p>
 Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
    http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

