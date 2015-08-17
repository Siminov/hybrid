Adapter allows Web and Native to work together that is normally not possible because of incompatible Technologies. Adapter basically maps JavaScript to Native and vice-versa. Adapter Descriptor is one which describes properties required to map Web to Native and vice-versa. It is optional descriptor.

```xml

                  <!-- Design of AdapterDescriptor.si.xml -->

    <adapter-descriptor>
    
        <!-- General Adapter Properties -->
            <!-- Mandatory Field -->
       <property name="name">adapter_name</property>
    	
            <!-- Optional Field -->
       <property name="description">adapter_description</property>
    
            <!-- Mandatory Field -->
       <property name="type">WEB-TO-NATIVE|NATIVE-TO-WEB</property>
    
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

```

```xml

                <!-- Android Sample: Adapter Descriptor -->

    <adapter-descriptor>
    
        <property name="name">SIMINOV</property>
        <property name="description">Siminov Web Handler</property>
        <property name="type">WEB-TO-NATIVE</property>
        <property name="map_to">siminov.web.adapter.handlers.SiminovHandler</property>
    		
       <handlers>
           <handler>
               <property name="name">INITIALIZE-SIMINOV</property>
               <property name="map_to">initializeSiminov</property>
               <property name="description">Initialize Siminov</property>
               <property name="type">SYNC</property>
           </handler>
           <handler>
               <property name="name">SHUTDOWN-SIMINOV</property>
               <property name="map_to">shutdownSiminov</property>
               <property name="description">Shutdown Siminov</property>
               <property name="type">SYNC</property>
           </handler>
        
       </handlers>
    	     
    </adapter-descriptor>

```

```xml

                <!-- iOS Sample: Adapter Descriptor -->

    <adapter-descriptor>
    
        <property name="name">SIMINOV</property>
        <property name="description">Siminov Web Handler</property>
        <property name="type">WEB-TO-NATIVE</property>
        <property name="map_to">SICSiminovHandler</property>
    		
       <handlers>
           <handler>
               <property name="name">INITIALIZE-SIMINOV</property>
               <property name="map_to">initializeSiminov</property>
               <property name="description">Initialize Siminov</property>
               <property name="type">SYNC</property>
           </handler>
           <handler>
               <property name="name">SHUTDOWN-SIMINOV</property>
               <property name="map_to">shutdownSiminov</property>
               <property name="description">Shutdown Siminov</property>
               <property name="type">SYNC</property>
           </handler>
        
       </handlers>
    	     
    </adapter-descriptor>

```


```xml

                <!-- Windows Sample: Adapter Descriptor -->

    <adapter-descriptor>
    
        <property name="name">SIMINOV</property>
        <property name="description">Siminov Web Handler</property>
        <property name="type">WEB-TO-NATIVE</property>
        <property name="map_to">Siminov.Web.Adapter.Handlers.SiminovHandler</property>
    		
       <handlers>
           <handler>
               <property name="name">INITIALIZE-SIMINOV</property>
               <property name="map_to">initializeSiminov</property>
               <property name="description">Initialize Siminov</property>
               <property name="type">SYNC</property>
           </handler>
           <handler>
               <property name="name">SHUTDOWN-SIMINOV</property>
               <property name="map_to">shutdownSiminov</property>
               <property name="description">Shutdown Siminov</property>
               <property name="type">SYNC</property>
           </handler>
        
       </handlers>
    	     
    </adapter-descriptor>

```

## Adapter Descriptor Elements

###### 1. General properties of adapter descriptor

- _**name**_*:  Name of Adapter. It is mandatory field.
- _**description**_*: Description about Adapter. It is optional field.
- _**type**_*: Type Of Adapter. It is mandatory field.

      - _**WEB-TO-NATIVE**_: It says this adapter maps JavaScript functions to Native functions.
      - _**NATIVE-TO-WEB**_: It says this adapter maps Native functions to JavaScript functions.

- **map_to***:  Name of Class (Web/Native) mapped to this adapter. It is not mandatory field.
- _**cache**_*:  TRUE/FALSE: It says that adapter mapped to Class needs to be cached or not. It is optional field. Default is false.
     

###### 2. Handler Tag
Handler is one which handle request from WEB-TO-NATIVE or NATIVE-TO-WEB.

- _**name**_*:  Name of Handler. It is mandatory field.
- _**description**_*:  Description about Handler. It is optional field.
- **map_to***:  Name of Handler function which handles request from WEB-TO-NATIVE or NATIVE-TO-WEB.

###### 3. Parameter
Parameters are basically arguments passed to handler.

- _**name**_*:  Name of Parameter. It is mandatory field.
- _**description**_*:  Description about Parameter. It is optional field.
- _**type**_*:  Type of Parameter. It is mandatory field.

###### 4. Return
Return defines about data returned from handler.

- _**type**_*:  Type of Returned Data. It is mandatory field.
- _**description**_*:  Description about Return Data. It is optional field.


> **Note**
> - If you define Adapter in AdapterDescriptor.si.xml file then define it in adapters TAG.
> - If you Adapter in separate xml file then specify Adapter file path in AdapterDescriptor.si.xml file.