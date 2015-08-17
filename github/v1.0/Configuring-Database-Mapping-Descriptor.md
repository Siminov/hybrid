Database Mapping Descriptor is one which does ORM, it maps Native/JavaScript model class to database table.

```xml

                    <!-- Design Of DatabaseMappingDescriptor.si.xml -->
    
    <database-mapping-descriptor>

        <!-- General Properties Of Table And Class -->
    
            <!-- TABLE_NAME: Mandatory Field -->
            <!-- CLASS_NAME: Mandatory Field -->
        <entity table_name="name_of_table" class_name="mapped_model_class_name/mapped_javascript_model_class_name">
		
                <!-- Column Properties Required Under This Table -->
	    
                    <!-- Optional Field -->
		
                        <!-- VARIABLE_NAME: Mandatory Field -->
                        <!-- COLUMN_NAME: Mandatory Field -->
                <attribute variable_name="class_variable_name" column_name="column_name_of_table">
		    
                        <!-- Mandatory Field -->
                    <property name="type">native_variable_data_type/javascript_variable_data_type</property>
			
                        <!-- Optional Field (Default is false) -->
                    <property name="primary_key">true/false</property>
			
                        <!-- Optional Field (Default is false) -->
                    <property name="not_null">true/false</property>
			
                        <!-- Optional Field (Default is false) -->
                    <property name="unique">true/false</property>
			
                        <!-- Optional Field -->
                    <property name="check">condition_to_be_checked (Eg: variable_name 'condition' value; variable_name > 0)</property>
			
                        <!-- Optional Field -->
                    <property name="default">default_value_of_column (Eg: 0.1)</property>
		
                </attribute>		

		
                <!-- Index Properties -->
				
                    <!-- Optional Field -->
                        <!-- NAME: Mandatory Field -->
                        <!-- UNIQUE: Optional Field (Default is false) -->
                <index name="name_of_index" unique="true/false">
                    <column>column_name_needs_to_add</column>
                </index>
		

                <!-- Map Relationship Properties -->
                     <!-- Optional Field's -->	
                <relationships>
		    
                    <!-- REFER: Mandatory Field -->
                    <!-- REFER_TO: Mandatory Field -->
                    <one-to-one refer="class_variable_name" refer_to="mapped_model_class_name/mapped_javascript_model_class_name" on_update="cascade/restrict/no_action/set_null/set_default" on_delete="cascade/restrict/no_action/set_null/set_default">
					
                            <!-- Optional Field (Default is false) -->
                        <property name="load">true/false</property>
                    </one-to-one>		
			
                    <!-- REFER: Mandatory Field -->
                    <!-- REFER_TO: Mandatory Field -->
                    <one-to-many refer="class_variable_name" refer_to="mapped_model_class_name/mapped_javascript_model_class_name" on_update="cascade/restrict/no_action/set_null/set_default" on_delete="cascade/restrict/no_action/set_null/set_default">
					
                            <!-- Optional Field (Default is false) -->
                        <property name="load">true/false</property>
                    </one-to-many>		


                    <!-- REFER: Mandatory Field -->
                    <!-- REFER_TO: Mandatory Field -->
                    <many-to-one refer="class_variable_name" refer_to="mapped_model_class_name/mapped_javascript_model_class_name" on_update="cascade/restrict/no_action/set_null/set_default" on_delete="cascade/restrict/no_action/set_null/set_default">
					
                            <!-- Optional Field (Default is false) -->
                        <property name="load">true/false</property>
                    </many-to-one>		


                    <!-- REFER: Mandatory Field -->
                    <!-- REFER_TO: Mandatory Field -->
                    <many-to-many refer="class_variable_name" refer_to="mapped_model_class_name/mapped_javascript_model_class_name" on_update="cascade/restrict/no_action/set_null/set_default" on_delete="cascade/restrict/no_action/set_null/set_default">
					
                            <!-- Optional Field (Default is false) -->
                        <property name="load">true/false</property>
                    </many-to-many>		
									
                </relationships>

         </entity>

    </database-mapping-descriptor>

```


```xml

                      <!-- JavaScript Sample: Database Mapping Descriptor -->

    <database-mapping-descriptor>

        <entity table_name="LIQUOR" class_name="Liquor">
		
            <attribute variable_name="liquorType" column_name="LIQUOR_TYPE">
                <property name="type">String</property>
                <property name="primary_key">true</property>
                <property name="not_null">true</property>
                <property name="unique">true</property>
            </attribute>		

            <attribute variable_name="description" column_name="DESCRIPTION">
                <property name="type">String</property>
            </attribute>

            <attribute variable_name="history" column_name="HISTORY">
                <property name="type">String</property>
            </attribute>

            <attribute variable_name="link" column_name="LINK">
                <property name="type">String</property>
                <property name="default">www.wikipedia.org</property>
            </attribute>

            <attribute variable_name="alcholContent" column_name="ALCHOL_CONTENT">
                <property name="type">String</property>
            </attribute>

            <index name="LIQUOR_INDEX_BASED_ON_LINK" unique="true">
                <attribute>HISTORY</attribute>
            </index>

            <relationships>

                <one-to-many refer="liquorBrands" refer_to="LiquorBrand" on_update="cascade" on_delete="cascade">
                    <property name="load">true</property>
                </one-to-many>		
	
            </relationships>
											
        </entity>

    </database-mapping-descriptor>		

```

## Database Mapping Descriptor Elements

###### 1. Entity Tag

It map database table to its corresponding model class.

- **table_name***: Name of table. It is mandatory field.
- **class_name***: Name of Native/JavaScript model class name which is to be mapped to table name. It is mandatory field.

> **Note**
>
> - **Model Class:** It can be define in both Native and Web.
>
> - **Native Class:** If you mapped model class is in Native then use the class name. Specify full model class name in class_name TAG.
>
> - **Web Class:** If you have mapped model class is in Web then use JavaScript to define class. Specify only JavaScript Function name in class_name TAG.
>
> - **Both (Native/Web) Class:** If you mapped model class is in both Native and Web then both Native and JavaScript to define classes. Specify full model class path and name in class_name TAG, no need to define for JavaScript because Web will automatically assume same name for it.

###### 2. Attribute Tag
It map database table column to its corresponding variable of model class.

- **column_name***: Name of column. It is mandatory field.
- **variable_name***: Name of variable. It is mandatory field.

**Properties Of Attribute Tag**
- _**type**_*: Variable data type. It is mandatory property.

For more details see Data Type section of this developer guide.

> **Note**
>
> - **Model Class:** It be define in both Native and Web.
>
> - **Native Class:** If you have mapped model class in Native then specify Native Variable data type.
>
>   **Java Data Type's**
>
>   **Android**: _java.lang.int, java.lang.Integer, java.lang.long, java.lang.Long, java.lang.float, java.lang.Float, java.lang.boolean, java.lang.Boolean, java.lang.char, java.lang.Character, java.lang.String, java.lang.byte, java.lang.Byte, java.lang.void, java.lang.Void, java.lang.short, java.lang.Short_
>
>   **iOS**: _int, long, float, bool, char, NSString, byte, void, short_
>
> - **Web Class**: If you have mapped model class in Web then specify JavaScript Variable data type.
>
> **JavaScript Data Type's**
>
>   _String, Number, Boolean, Array, Object, Null, Undefined_
>
> - **Native and Web Class**: If your mapped model class is in both Native and Web then only specify Native Variable data type, for JavaScript Web will take care.

- **primary_key***: **TRUE/FALSE**. It defines whether the column is primary key of table or not. It is optional property. Default value is false.

- **not_null***: **TRUE/FALSE**. It defines whether the column value can be empty or not. It is optional property. Default value is false.

- _**unique**_*: **TRUE/FALSE**. It defines whether the column value should be unique or not. It is optional property. Default value is false.

- _**default**_: It defines the default value of column. It is optional property.

- _**check**_: It is used to put condition on column value. It is optional property.


> **Note**: Application Developer can provide their own properties also, and by using following API's they can use properties.
>
> - **Get Properties - [Android:getProperties | iOS:getProperties | Windows:GetProperties | JavaScript:getProperties]**: It will return all properties associated with Database Mapping Descriptor Column.
>
> - **Get Property - [Android:getProperty(Name-of-Property) | iOS:getProperty:Name-of-Property | Windows:GetProperty(Name-of-Property) | JavaScript:getProperty(Name-of-Property)]**: It will return property value associated with property name provided.
>
> - **Contains Property - [Android:containsProperty(Name-of-Property) | iOS:containsProperty:Name-of-Property | Windows:ContainsProperty(Name-of-Property) | JavaScript:containsProperty(Name-of-Property)]**: It will return TRUE/FALSE whether property exists or not.
>
> - **Add Property - [Android:addProperty(Name-of-Property, Value-of-Property) | iOS:addProperty:Name-of-Property value:Value-of-Property | Windows:AddProperty(Name-of-Property, Value-of-Property) | JavaScript:addProperty(Name-of-Property, Value-of-Property)]**: It will add new property to the  collection of Database Mapping Descriptor Column properties.
>
> - **Remove Property - [Android:removeProperty(Name-of-Property) | iOS:removeProperty:Name-of-Property | Windows:RemoveProperty(Name-of-Property) | JavaScript:removeProperty(Name-of-Property)]**: It will remove property from Database Mapping Descriptor Column properties based on name provided.

###### 3. Index Tag
It defines the structure of index needed on the table.

- _**name**_*: Name of the index. It is mandatory field.

- _**unique**_: _TRUE/FALSE_. It defines whether index needs to be unique or not. It is not mandatory property. Default value is false.

(A unique index guarantees that the index key contains no duplicate values and therefore every row in the table is in some way unique).

**Index Column Tag**

- _**column**_*: Name of columns included in index. At least one column should be included.

###### 4. Relationship Tag
It defines relationship between object. Relationship can be of four types:

- _**One To One Relationship (one-to-one)**_: In a one-to-one relationship, each row in one database table is linked to one and only one other row in another table.

- _**One To Many Relationship (one-to-many)**_: In a one-to-many relationship, each row in the related to table can be related to many rows in the relating table. This effectively save storage as the related
record does not need to be stored multiple times in the relating table.

- _**Many To One Relationship (many-to-one)**_: In a many-to-one relationship one entity (typically a column or set of columns) contains values that refer to another entity (a column or set of columns) that has unique values.

- _**Many To Many Relationship (many-to-many)**_: In a many-to-many relationship, one or more rows in a table can be related to 0, 1 or many rows in another table. A mapping table is required in order to implement such a relationship.


**Relationship Attributes**

- _**refer**_*: Name of variable which needs to be mapped. It is mandatory field.

- **refer_to***: Class name of mapped variable. It is mandatory field.

> **Note**
>
> - **Model Class**: It can be define in both Native and Web.
>
> - **Native Class**: If you have mapped model class in Native then specify Java class path and name in refer_to TAG.
>
> - **Web Class**: If you have mapped model class in Web then specify only JavaScript function name in refer_to TAG.
>
> - **Both (Native/Web) Class**: If you have mapped model class in both Native and Web then specify full model class path and name in refer_to TAG, no need to define for JavaScript because Web will automatically assume same name for it.


- **on_update***: _cascade/restrict/no action/set null/set_default_. It defines action needs to be done, when update occur.

- **on_delete***: _cascade/restrict/no action/set null/set_default_. It defines action needs to be done, when delete occur.


**Relationship Properties**

- _**load**_*: It defines whether it need to be load or not.

> **Note**
>
> - Application developer can assign any name to DatabaseMappingDescriptor.si.xml file.
>
> - It descriptor le should be in same place as per defined in DatabaseDescriptor.si.xml file.


## Android Sample: Database Mapping Descriptor

***
![Android Sample: Database Mapping Descriptor] (https://raw.github.com/Siminov/android-web/docs/github/v1.0/siminov_web_sample_database_descriptor_mapping_path_example.png "Android Sample: Database Mapping Descriptor")
***

## iOS Sample: Database Mapping Descriptor

***
![iOS Sample: Database Mapping Descriptor] (https://raw.github.com/Siminov/ios-web/docs/github/v1.0/siminov_web_sample_database_descriptor_mapping_path_example.png "iOS Sample: Database Mapping Descriptor")
***


## Windows Sample: Database Mapping Descriptor

***
![Windows Sample: Database Mapping Descriptor] (https://raw.github.com/Siminov/windows-web/docs/github/v1.0/siminov_web_sample_database_descriptor_mapping_path_example.png "Windows Sample: Database Mapping Descriptor")
***
