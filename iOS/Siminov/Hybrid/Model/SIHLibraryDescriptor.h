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

#import "SIKLibraryDescriptor.h"
#import "SIHAdapterDescriptor.h"


/**
 * Exposes methods to GET and SET Library Descriptor information as per define in LibraryDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
	
	<library>
 
         <!-- General Properties Of Library -->
         
         <!-- Mandatory Field -->
         <property name="name">name_of_library</property>
         
         <!-- Optional Field -->
         <property name="description">description_of_library</property>
            
         
         
         <!-- Entity Descriptors Needed Under This Library Descriptor -->
         
         <!-- Optional Field -->
             <!-- Entity Descriptors -->
         <entity-descriptors>
             <entity-descriptor>name_of_database_descriptor.full_path_of_entity_descriptor_file</entity-descriptor>
         </entity-descriptors>
         
         
         <!-- Hybrid Adapters Needed Under This Library Descriptor -->
         
             <!-- Optional Field -->
             <!-- Hybrid Adapters -->
         <adapters>
             <adapter>full_path_of_hybrid_adapter_file</adapter>
         </adapters>
         
 
	</library>
 
	}
	
 </pre>
	</p>
 *
 */
@interface SIHLibraryDescriptor : SIKLibraryDescriptor {
    
    NSMutableArray *adapterDescriptorPaths;
    
    NSMutableDictionary *adapterDescriptorsBasedOnPath;
    NSMutableDictionary *adapterDescriptorsBasedOnName;
    
}


/**
 * Get All Adapter Descriptor Paths.
 * @return All Adapter Descriptor Paths.
 */
- (NSEnumerator *)getAdapterDescriptorPaths;

/**
 * Get All Adapter Descriptors.
 * @return All Adapter Descriptors.
 */
- (NSEnumerator *)getAdapterDescriptors;

/**
 * Get Adapter Descriptor Based on adapter descriptor name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 * @return Adapter Descriptor.
 */
- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Get Adapter Descriptor Based on adapter descriptor path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 * @return Adapter Descriptor.
 */
- (SIHAdapterDescriptor *)getAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Add Adapter Descriptor Path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 */
- (void)addAdapterDescriptorPath:(NSString *)adapterDescriptorPath;

/**
 * Add Adapter Descriptor based on adapter descriptor path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 * @param adapterDescriptor Adapter Descriptor.
 */
- (void)addAdapterDescriptor:(NSString *)adapterDescriptorPath adapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor;

/**
 * Check whether Adapter Descriptor exist based on adapter descriptor path.
 * @param adapterDescriptorPath Path of Adapter Descriptor.
 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
 */
- (bool)containAdapterDescriptorBasedOnPath:(NSString *)adapterDescriptorPath;

/**
 * Check whether Adapter Descriptor exist based on adapter name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
 */
- (bool)containAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Remove Adapter Descriptor based on adapter descriptor name.
 * @param adapterDescriptorName Name of Adapter Descriptor.
 */
- (void)removeAdapterDescriptorBasedOnName:(NSString *)adapterDescriptorName;

/**
 * Remove Adapter Descriptor based on adapter descriptor path.
 * @param adapterPath Path of Adapter Descriptor.
 */
- (void)removeAdapterBasedOnPath:(NSString *)adapterPath;

/**
 * Remove Adapter Descriptor.
 * @param adapterDescriptor Adapter Descriptor.
 */
- (void)removeAdapater:(SIHAdapterDescriptor *)adapterDescriptor;


@end
