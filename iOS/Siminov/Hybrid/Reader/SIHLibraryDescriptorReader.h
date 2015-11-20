//
//  SIHLibraryDescriptorReader.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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
