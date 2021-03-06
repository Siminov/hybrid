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
#import "SICResourceManager.h"
#import "SIHAdapterDescriptor.h"


/**
 * Exposes methods to parse Adapter Descriptor information as per define in AdapterDescriptor.xml file by application.
	<p>
 <pre>
 
 Example:
	{@code
	
	<adapter-descriptor>
 
         <!-- General Adapter Properties -->
             <!-- Mandatory Field -->
         <property name="name">adapter_name</property>
         
         <!-- Optional Field -->
         <property name="description">adapter_description</property>
         
         <!-- Mandatory Field -->
         <property name="type">HYBRID-TO-NATIVE|NATIVE-TO-HYBRID</property>
         
         <!-- Optional Field -->
         <property name="map_to">name_of_adapter_class</property>
            
         <!-- Optional Field (DEFAULT: FALSE)-->
         <property name="cache">true/false</property>
         
         <!-- Handlers -->
         <!-- Handler -->
         <handlers>
         
             <handler>
             
                 <!-- General Handler Properties -->
                     <!-- Mandatory Field -->
                 <property name="name">handler_name</property>
                 
                 <!-- Optional Field -->
                 <property name="description">handler_description</property>
                 
                 <!-- Mandatory Field -->
                 <property name="map_to">name_of_handler_method</property>
                 
                 
                 <!-- Parameters -->
                 <parameters>
                 
                     <!-- Parameter -->
                     <parameter>
 
                         <!-- Mandatory Field -->
                         <property name="name">name_of_parameter</property>
                         
                         <!-- Mandatory Field -->
                         <property name="type">parameter_type</property>
                         
                         <!-- Optional Field -->
                         <property name="description">description_of_parameter</property>
 
                     </parameter>
                     
                 </parameters>
                 
                 <return>
                 
                     <!-- Mandatory Field -->
                     <property name="type">return_type</property>
                     
                     <!-- Optional Field -->
                     <property name="description">return_data_description</property>
                     
                 </return>
             
             </handler>
             
         </handlers>
	
	</adapter-descriptor>
 
 
	}
	
 </pre>
	</p>
 *
 */
@interface SIHAdapterDescriptorReader : SICSiminovSAXDefaultHandler {
    
    NSMutableString *tempValue;
    SICResourceManager *resourceManager;
    
    SIHAdapterDescriptor *adapterDescriptor;
    SIHHandler *handler;
    SIHParameter *parameter;
    SIHReturn *returnData;
    
    bool isHandler;
    bool isParameter;
    bool isReturn;
    
    NSString *propertyName;
}

- (id)initWithPath:(NSString * const)adapterdescriptorpath;



/**
 * Get adapter descriptor
 * @return Adapter Descriptor
 */
- (SIHAdapterDescriptor *)getAdapterDescriptor;

@end
