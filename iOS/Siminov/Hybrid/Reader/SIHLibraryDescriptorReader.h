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


#import "SICSiminovSAXDefaultHandler.h"
#import "SIHLibraryDescriptor.h"
#import "SIHLibraryDescriptor.h"


/**
 * Exposes methods to parse Library Descriptor information as per define in LibraryDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
	<library>
 
         <property name="name">SIMINOV LIBRARY SAMPLE</property>
         <property name="description">Siminov Library Sample</property>
            
         <!-- Entity Descriptors -->
         <entity-descriptors>
             <entity-descriptor>Credential.xml</entity-descriptor>
         </entity-descriptors>
            
         <!-- Adapters -->
         <adapters>
             <adapter path="adapter_full_path" />
         </adapters>
 
	</library>
	}
	
 </pre>
	</p>
 *
 */
@interface SIHLibraryDescriptorReader : SICSiminovSAXDefaultHandler {
    
    NSString *libraryName;
    
    SIHLibraryDescriptor *libraryDescriptor;
    
    NSMutableString *tempValue;
    NSString *propertyName;
}


- (id)initWithLibraryName:(NSString * const)libraryname;

/**
 * Get library descriptor
 * @return Library Descriptor
 */
- (SIHLibraryDescriptor *)getLibraryDescriptor;


@end
