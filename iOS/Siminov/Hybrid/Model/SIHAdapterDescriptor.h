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

#import "SICIDescriptor.h"


@class SIHHandler, SIHParameter, SIHReturn;


/**
 * Exposes methods to GET and SET Hybrid Descriptor Adapter information as per define in HybridDescriptor.xml file or standalone xml file in application.
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
 */
@interface SIHAdapterDescriptor : NSObject<SICIDescriptor> {
    
    NSMutableDictionary *properties;
    
    NSMutableDictionary *handlers;
}



/**
 * Get Name of Adapter.
 * @return Name of Adapter.
 */
- (NSString *)getName;

/**
 * Set Name of Adapter.
 * @param name Name of Adapter.
 */
- (void)setName:(NSString *)name;

/**
 * Get Description of Adapter.
 * @return Description of Adapter.
 */
- (NSString *)getDescription;

/**
 * Set Description of Adapter.
 * @param description Description of Adapter.
 */
- (void)setDescription:(NSString *)description;

/**
 * Get Type of Adapter.
 * @return Type of Adapter.
 */
- (NSString *)getType;

/**
 * Set Type of Adapter.
 * @param type Type of Adapter.
 */
- (void)setType:(NSString *)type;

/**
 * Get Map To Name.
 * @return Map To Name.
 */
- (NSString *)getMapTo;

/**
 * Set Map To Name.
 * @param mapTo Map To Name.
 */
- (void)setMapTo:(NSString *)mapTo;

/**
 * Check whether cache is enabled or disabled.
 * @return true/false; TRUE if cache enabled, FALSE if cache disabled.
 */
- (bool)isCache;

/**
 * Set Cache value.
 * @param cache Cache Enabled or Disabled.
 */
- (void)setCache:(bool)cache;


/**
 * Get All Handler defined in descriptors.
 * @return All Handlers.
 */
- (NSEnumerator *)getHandlers;

/**
 * Get Handler based on handler name.
 * @param handlerName Name of Handler.
 * @return Handler.
 */
- (SIHHandler *)getHandler:(NSString *)handlerName;

/**
 * Add Handler.
 * @param handler Handler.
 */
- (void)addHandler:(SIHHandler *)handler;

/**
 * Check whether handler exist or not based on handler name.
 * @param handlerName Name of handler.
 * @return
 */
- (bool)containHandler:(NSString *)handlerName;


@end

/**
 * Exposes methods to GET and SET Hybrid Descriptor Adapter Handler information as per define in HybridDescriptor.xml file or in standalone adapter xml file in application.
 *
 */

@interface SIHHandler : NSObject<SICIDescriptor> {
    
    NSMutableDictionary *properties;
    
    NSMutableArray *parameters;
    SIHReturn *returnData;
}

/**
 * Get Name of Handler.
 * @return Name of Handler.
 */
- (NSString *)getName;

/**
 * Set Name of Handler.
 * @param name Name of handler.
 */
- (void)setName:(NSString *)name;

/**
 * Get Map To Name.
 * @return Map To Name.
 */
- (NSString *)getMapTo;

/**
 * Set Map To Name.
 * @param mapTo Map To Name.
 */
- (void)setMapTo:(NSString *)mapTo;

/**
 * Get Description about Handler.
 * @return Description about Handler.
 */
- (NSString *)getDescription;

/**
 * Set Description about Handler.
 * @param description Description about Handler.
 */
- (void)setDescription:(NSString *)description;


/**
 * Get All Parameters defined.
 * @return All Parameters.
 */
- (NSEnumerator *)getParameters;

/**
 * Add Parameter.
 * @param parameter Parameter.
 */
- (void)addParameter:(SIHParameter *)parameter;

/**
 * Get Return Object of Handler.
 * @return Return Object.
 */
- (SIHReturn *)getReturn;

/**
 * Set Return Object of Handler.
 * @param returnData Return Object.
 */
- (void)setReturn:(SIHReturn *)data;

@end

/**
 * Exposes methods to GET and SET Parameter information as per define in HybridDescriptor.xml file or in standalone adapter xml file in application.
 *
 */
@interface SIHParameter : NSObject<SICIDescriptor> {
    
    NSMutableDictionary *properties;
}

/**
 * Get Name of Parameter.
 * @return Name of Parameter.
 */
- (NSString *)getName;

/**
 * Set Name of Parameter.
 * @param name Name of Parameter.
 */
- (void)setName:(NSString *)name;

/**
 * Get Type of Parameter.
 * @return Type of Parameter.
 */
- (NSString *)getType;

/**
 * Get Type of Parameter.
 * @param type Type of Parameter.
 */
- (void)setType:(NSString *)type;

/**
 * Get Description about Parameter.
 * @return Description about Parameter.
 */
- (NSString *)getDescription;

/**
 * Set Description about Parameter.
 * @param description Description about Parameter.
 */
- (void)setDescription:(NSString *)description;


@end


/**
 * Exposes methods to GET and SET Return information as per define in HybridDescriptor.xml file or in standalone adapter xml file in application.
 *
 */
@interface SIHReturn : NSObject<SICIDescriptor> {
    
    NSMutableDictionary *properties;
}

/**
 * Get Type of Return.
 * @return Type of Return.
 */
- (NSString *)getType;

/**
 * Set Type of Return.
 * @param type Type of Return.
 */
- (void)setType:(NSString *)type;

/**
 * Get Description about Return.
 * @return Description about Return.
 */
- (NSString *)getDescription;

/**
 * Set Description about Return.
 * @param description Description about Return.
 */
- (void)setDescription:(NSString *)description;


@end
