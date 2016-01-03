///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///


#import <Foundation/Foundation.h>

#import "SIKApplicationDescriptor.h"
#import "SIHAdapterDescriptor.h"

/**
 * Exposes methods to GET and SET Application Descriptor information as per define in ApplicationDescriptor.xml file by application.
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
@interface SIHApplicationDescriptor : SIKApplicationDescriptor {
    
    NSMutableArray *adapterDescriptorPaths;
    
    NSMutableDictionary *adapterDescriptorsBasedOnName;
    NSMutableDictionary *adapterDescriptorsBasedOnPath;
    
}


/**
 * Get all adapter descriptors
 * @return Adapter Descriptors
 */
- (NSEnumerator *)getAdapterDescriptors;

/**
 * Get adapter descriptor based on name
 * @param adapterDescriptorName Name of adapter descriptor
 * @return Adapter Descriptor
 */
- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Get adapter descriptor based on path
 * @param adapterDescriptorPath Path of adapter descriptor
 * @return Adapter Descriptor
 */
- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Add adapter descriptor
 * @param adapterDescriptor Adapter Descriptor
 */
- (void)addAdapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor;

/**
 * Add adapter descriptor based on its path
 * @param adapterDescriptorPath Path of adapter descriptor
 * @param adapterDescriptor Adapter Descriptor
 */
- (void)addAdapterDescriptor:(NSString *)adapterDescriptorPath adapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor;

/**
 * Check whether it contains adapter descriptor or not based on path
 * @param adapterDescriptorPath Path of adapter descriptor
 * @return (true/false) TRUE: If adapter descriptor exists | FALSE: If adapter descriptor does not exists.
 */
- (bool)containAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Check if adapter descriptor exists based on its name
 * @param adapterDescriptorName Name of adapter descriptor
 * @return (true/false) TRUE: If adapter descriptor exists | FALSE: If adapter descriptor does not exists
 */
- (bool)containAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Get all adapter descriptor paths
 * @return Adapter Descriptor Paths
 */
- (NSEnumerator *)getAdapterDescriptorPaths;

/**
 * Add adapter descriptor path
 * @param adapterDescriptorPath Path of adapter descriptor
 */
- (void)addAdapterDescriptorPath:(NSString *)adapterDescriptorPath;

/**
 * Check if adapter descriptor path exists or not
 * @param adapterDescriptorPath Path of adapter descriptor
 * @return (true/false) TRUE: If path exists | FALSE: If path does not exists
 */
- (bool)containAdapterDescriptorPath:(NSString *)adapterDescriptorPath;

/**
 * Remove adapter descriptor based on its name
 * @param adapterDescriptorName Name of adapter descriptor
 */
- (void)removeAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Remove adapter descriptor based on path
 * @param adapterDescriptorPath Path of adapter descriptor
 */
- (void)removeAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Remove adapter descriptor
 * @param adapterDescriptor Adapter Descriptor
 */
- (void)removeAdapter:(SIHAdapterDescriptor *)adapterDescriptor;

@end
