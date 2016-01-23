/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


/**
	It contain all Models as per required by Siminov Framework.

	@module Model
*/
var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}



if(dom == undefined) {
    var Constants = require('../Constants');
    var Dictionary = require('../Collection/Dictionary');
    
    module.exports = EntityDescriptor;
    win.EntityDescriptor = EntityDescriptor;
}

/**
 	Exposes methods to GET and SET Entity Descriptor information as per define in EntityDescriptor.xml file by application.
		
	Example:


		<!-- Design Of EntityDescriptor.xml -->

		<entity-descriptor>

			<!-- General Properties Of Table And Class -->

				<!-- Mandatory Field -->
					<!-- NAME OF TABLE -->
			<property name="table_name">name_of_table</property>

				<!-- Mandatory Field -->
					<!-- MAPPED CLASS NAME -->
			<property name="class_name">mapped_class_name/mapped_javascript_class_name</property>


				<!-- Optional Field -->
			<attributes>

				<!-- Column Properties Required Under This Table -->

					<!-- Optional Field -->
				<attribute>

						<!-- Mandatory Field -->
							<!-- COLUMN_NAME: Mandatory Field -->
					<property name="column_name">column_name_of_table</property>

						<!-- Mandatory Field -->
							<!-- VARIABLE_NAME: Mandatory Field -->
					<property name="variable_name">class_variable_name</property>

						<!-- Mandatory Field -->
					<property name="type">variable_data_type</property>

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

			</attributes>


				<!-- Optional Field -->
			<indexes>

				<!-- Index Properties -->
				<index>

						<!-- Mandatory Field -->
							<!-- NAME OF INDEX -->
					<property name="name">name_of_index</property>

						<!-- Mandatory Field -->
							<!-- UNIQUE: Optional Field (Default is false) -->
					<property name="unique">true/false</property>

						<!-- Optional Field -->
							<!-- Name of the column -->
					<property name="column">column_name_needs_to_add</property>

				</index>

			</indexes>


			<!-- Map Relationship Properties -->

				<!-- Optional Field's -->
			<relationships>

				<relationship>

						<!-- Mandatory Field -->
							<!-- Type of Relationship -->
					<property name="type">one-to-one|one-to-many|many-to-one|many-to-many</property>

						<!-- Mandatory Field -->
							<!-- REFER -->
					<property name="refer">class_variable_name</property>

						<!-- Mandatory Field -->
							<!-- REFER TO -->
					<property name="refer_to">mapped_class_name/mapped_javascript_class_name</property>

						<!-- Optional Field -->
					<property name="on_update">cascade/restrict/no_action/set_null/set_default</property>

						<!-- Optional Field -->
					<property name="on_delete">cascade/restrict/no_action/set_null/set_default</property>

						<!-- Optional Field (Default is false) -->
					<property name="load">true/false</property>

				</relationship>

			</relationships>

		</entity-descriptor>



	@module Model	
	@class EntityDescriptor
	@constructor
 */
function EntityDescriptor() {

    var properties = new Dictionary();

    var attributes = new Array();
    var indexes = new Array();
    var relationships = new Array();

	/**
	 	Get table name.
	 
	 	@method getTableName
	 	@return {String} Name of table.
	 */
    this.getTableName = function() {
    	return properties.get(Constants.ENTITY_DESCRIPTOR_TABLE_NAME);
	}
	
	/**
	 	Set table name as per defined in EntityDescriptor.xml file.
	 
	 	@method setTableName
	 	@param tableName Name of table.
	 */
    this.setTableName = function(tableName) {
		properties.add(Constants.ENTITY_DESCRIPTOR_TABLE_NAME, tableName);
	}

	/**
	 	Get Function class name.
	 
	 	@method getClassName
	 	@return {String} Mapped class name.
	 */
    this.getClassName = function() {
    	return properties.get(Constants.ENTITY_DESCRIPTOR_CLASS_NAME);
	}
	
	/**
	 	Set Function class name as per defined in EntityDescriptor.xml file.
	 	
	 	@method setClassName
	 	@param className {String} Mapped class name.
	 */
    this.setClassName = function(className) {
		properties.add(Constants.ENTITY_DESCRIPTOR_CLASS_NAME, className);
	}

	/**
	 	Get all columns.
	 	
	 	@method getColumns
	 	@return {Array} All columns.
	 */
    this.getAttributes = function() {
        return attributes;
	}

	/**
	 	Add column to EntityDescriptor object.
	 
	 	@method addColumn
	 	@param column {Column} Column object.
	 */
    this.addAttribute = function(attribute) {
        attributes.push(attribute);
	}

	/**
	 	Get all indexes.
	 
	 	@method getIndexes
	 	@return {Array} It contain all indexes.
	 */
    this.getIndexes = function() {
    	return indexes;
	}
	
	/**
	 	Add index to EntityDescriptor object.
	 
	 	@method addIndex
	 	@param index {Index} Index object.
	 */
    this.addIndex = function(index) {
    	indexes.push(index);
	}

	/**
	 	Get iterator of relationship objects. 
	 	
	 	@method getRelationship
 		@return {Array} Relationship objects.
	 */
    this.getRelationships = function() {	
    	return relationships;
	}
	
	/**
	 	Add relationship object.
	 
	 	@method addRelationship
	 	@param relationship {Relationship} Relationship object.
	 */
    this.addRelationship = function(relationship) {
    	relationships.push(relationship);
	}

}




/**
 	Exposes methods to GET and SET Column information as per define in EntityDescriptor.xml file by application.

	 Example:
	
		 <attribute>
			 <property name="variable_name">title</property>
			 <property name="column_name">TITLE</property>
			 <property name="type">TEXT</property>
			 <property name="primary_key">true</property>
			 <property name="not_null">true</property>
			 <property name="unique">true</property>
		 </attribute>
	
	@class EntityDescriptor.Attribute
	
 */
EntityDescriptor.Attribute = function() {

    var properties = new Dictionary();

    /**
     	Get variable name.
     
     	@method getVariableName
     	@return {String} Variable Name
     */
    this.getVariableName = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_VARIABLE_NAME);
    }

    /**
     	Set variable name as per defined in EntityDescriptor.core.xml file.
     
     	@method setVariableName
     	@param variableName {String} Name of variable.
     */
    this.setVariableName = function(variableName) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_VARIABLE_NAME, variableName);
    }

    /**
     	Get column name.
     
     	@method getColumnName
     	@return {String} Name Of Column.
     */
    this.getColumnName = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_COLUMN_NAME);
    }

    /**
     	Set column name as per defined in EntityDescriptor.core.xml file.
     
     	@method setColumnName
     	@param columnName {String} Name of column name.
     */
    this.setColumnName = function(columnName) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_VARIABLE_NAME, columnName);
    }

    /**
     	Get type of column.
     	
     	@method getType
     	@return {String} Type of column.
     */
    this.getType = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_TYPE);
    }

    /**
     	Set type of column as per defined in EntityDescriptor.core.xml file.
     	
     	@method setType
     	@param type {String} Type of column.
     */
    this.setType = function(type) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_TYPE, type);
    }

    /**
     	Get check constraint of column.
     
     	@method getCheck
     	@return {String} Check constraint of column.
     */
    this.getCheck = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_CHECK);
    }

    /**
     	Set check constraint of column as per defined in EntityDescriptor.core.xml file.
     
     	@method setCheck
     	@param check {String} Check constraint.
     */
    this.setCheck = function(check) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_CHECK);
    }

    /**
     	Get default value of column.
     	
     	@method getDefaultValue
     	@return Default value of column.
     */
    this.getDefaultValue = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_DEFAULT_VALUE);
    }

    /**
     	Set default value of column as per defined in EntityDescriptor.core.xml file.
     
     	@method setDefaultValue
     	@param defaultValue {String} Default value of column.
     */
    this.setDefaultValue = function(defaultValue) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_DEFAULT_VALUE, defaultValue);
    }

    /**
     	Set column as primary key or not.
     	
     	@method setPrimary
     	@param primaryKey {Boolean} (true/false) TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.setPrimaryKey = function(primaryKey) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_PRIMARY_KEY, primaryKey);
    }

    /**
     	Check whether column is primary key.
     	
     	@method isPrimaryKey
     	@return {Boolean} (true/false) TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.isPrimaryKey = function() {
        properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_PRIMARY_KEY);
    }

    /**
     	Set whether column can be null or not.
     	
     	@method setNotNull
     	@param isNotNull {Boolean} (true/false) TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.setNotNull = function(notNull) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_NOT_NULL, notNull);
    }

    /**
     	Check whether column value can be not or not.
     
     	@method isNotNull
     	@return {Boolean} (true/false) TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.isNotNull = function() {
        properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_NOT_NULL);
    }

    /**
     	Set whether column is unique or not.
     	
     	@method setUnique
     	@param isUnique {Boolean} (true/false) TRUE: If column is unique, FALSE: If column is not unique
     */
    this.setUnique = function(unique) {
        properties.add(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_UNIQUE, unique);
    }

    /**
     	Check whether column is unique or not.
     	
     	@method isUnique
     	@return {Boolean} (true/false) TRUE: If column is unique, FALSE: If column is not unique.
     */
    this.isUnique = function() {
        properties.get(Constants.ENTITY_DESCRIPTOR_ATTRIBUTE_UNIQUE);
    }
    
    	
	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}

    
}




/**
 	Exposes methods to GET and SET Index information as per define in EntityDescriptor.xml file by application.

	Example:
		<index>
			<property name="name">BOOK_INDEX_BASED_ON_AUTHOR</property>
			<property name="unique">true</property>
			<property name="column">AUTHOR</property>
		</index>


	@class EntityDescriptor.Index
	@constructor
*/
EntityDescriptor.Index = function() {

	var properties = new Dictionary();
    var columns = new Array();

    /**
     	Get index name.
     
     	@method getName
     	@return {String} Index Name.
     */
    this.getName = function() {
        properties.get(Constants.ENTITY_DESCRIPTOR_INDEX_NAME);
    }

    /**
     	Set index name as per defined in EntityDescriptor.core.xml file.
     	
     	@method setName
     	@param name {String} Index Name.
     */
    this.setName = function(name) {
        properties.add(Constants.ENTITY_DESCRIPTOR_INDEX_NAME, name);
    }

    /**
     	Set whether unqiue is unique or not.
     
     	@method setUnique
     	@param unique {Boolean} (true/false) TRUE: If index is unique, FALSE: If index is not unique.
     */
    this.setUnique = function(val) {
        properties.get(Constants.ENTITY_DESCRIPTOR_INDEX_UNIQUE);
    }

    /**
     	Check whether index should be unique or not.
     
     	@method isUnique
     	@return {Boolean} (true/false) TRUE: If index is unique, FALSE: If index is not unqiue.
     */
    this.isUnique = function() {
        if(properties.get(Constants.ENTITY_DESCRIPTOR_INDEX_NAME))
            return true;
        else
            return false;
    }

    /**
     	Get all columns.
     	
     	@method getColumns
     	@return {Array} It contain all columns.
     */
    this.getColumns = function() {
        return columns;
    }

    /**
     	Add column to index.
     	
     	@method addColumn
     	@param column {String} Name of column.
     */
    this.addColumn = function(column) {
        columns.push(column);
    }
}



/**
	Contains relationship details.

		<relationship>

				<!-- Mandatory Field -->
					<!-- Type of Relationship -->
			<property name="type">one-to-one|one-to-many|many-to-one|many-to-many</property>

				<!-- Mandatory Field -->
					<!-- REFER -->
			<property name="refer">class_variable_name</property>

				<!-- Mandatory Field -->
					<!-- REFER TO -->
			<property name="refer_to">mapped_class_name/mapped_javascript_class_name</property>

				<!-- Optional Field -->
			<property name="on_update">cascade/restrict/no_action/set_null/set_default</property>

				<!-- Optional Field -->
			<property name="on_delete">cascade/restrict/no_action/set_null/set_default</property>

				<!-- Optional Field (Default is false) -->
			<property name="load">true/false</property>

		</relationship>

	@class EntityDescriptor.Relationship
 */
EntityDescriptor.Relationship = function() {

    var properties = new Dictionary();

    var getterReferMethodName;
    var setterReferMethodName;


    /**
     	Get relationship type.
     
     	@method getRelationshipType
     	@return {String} Type of relationship.
     */
    this.getRelationshipType = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE);
    }

    /**
     	Set relationship type.
     
     	@method setRelationshipType
     	@param relationshipType {String} Type of relationship.
     */
    this.setRelationshipType = function(type) {
        properties.add(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE, type);
    }

    /**
     	Get refer.
     	
     	@method getRefer
     	@return {String} Name of refer.
     */
    this.getRefer = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_REFER);
    }

    /**
     	Set refer.
     	
     	@method setRefer
     	@param refer {String} Name of refer.
     */
    this.setRefer = function(refer) {
        properties.add(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_REFER, refer);
    }

    /**
     	Get refer to.
     
     	@method getReferTo
     	@return {String} Name of refer to.
     */
    this.getReferTo = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_REFER_TO);
    }

    /**
     	Set refer to.
     	
     	@method setReferTo
     	@param referTo {String} Name of refer to.
     */
    this.setReferTo = function(referTo) {
        properties.add(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_REFER_TO, referTo);
    }

    /**
     	Get on update.
     	
     	@method getOnUpdate
     	@return {String} Action on update.
     */
    this.getOnUpdate = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_ON_UPDATE);
    }

    /**
     	Set on update.
     
     	@method setOnUpdate
     	@param onUpdate {String} Action on update.
     */
    this.setOnUpdate = function(onUpdate) {
        properties.add(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_ON_UPDATE, onUpdate);
    }

    /**
     	Get on delete.
     
     	@method getOnDelete
     	@return {String} Action on delete.
     */
    this.getOnDelete = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_ON_DELETE);
    }

    /**
     	Set on delete.
     
     	@method setOnDelete
     	@param onDelete {String} Action on delete.
     */
    this.setOnDelete = function(onDelete) {
        properties.add(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_ON_DELETE, onDelete);
    }

    /**
     	Get getter refer method name.
     
     	@method getGetterReferMethodName
     	@return {String} Getter refer method name.
     */
    this.getGetterReferMethodName = function() {
        return getterReferMethodName;
    }

    /**
     	Set getter refer method name.
     
     	@method setGetterReferMethodName
     	@param getterReferMethodName {String} Name of getter refer method name.
     */
    this.setGetterReferMethodName = function(val) {
        getterReferMethodName = val;
    }

    /**
     	Get setter refer method name.
     	
     	@method getSetterReferMethodName
     	@return {String} Name of setter refer method name.
     */
    this.getSetterReferMethodName = function() {
        return setterReferMethodName;
    }

    /**
     	Set setter refer method name.
     
     	@method setSetterReferMethodName
     	@param setterReferMethodName {String} Name of setter refer name.
     */
    this.setSetterReferMethodName = function(val) {
        setterReferMethodName = val;
    }

    /**
     	Set load property value.
     
     	@method setLoad
     	@param load {Boolean} (true/false) TRUE: If load property value is true; FALSE: If load property value is false.
     */
    this.setLoad = function(isLoad) {
        properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_LOAD);
    }

    /**
     	Check whether load property value is set to TRUE/FASLE.
     	
     	@method isLoad
     	@return {Boolean} (true/false) TRUE: If load property value is set to true; FALSE: If load property value is set to false.
     */
    this.isLoad = function() {
        return properties.get(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_LOAD);
    }

		
	/**
	 	Get all Properties defined in descriptor.
	 
	 	@method getProperties
		@return {Array} All Property Values.
	 */
	this.getProperties = function() {
		return properties.values();
	}


	/**
	 	Get Property based on name provided.
	 
	 	@method getProperty
		@param name {String} Name of Property.
		@return {String} Property value.
	 */
	this.getProperty = function(name) {
		return properties.get(name);
	}


	/**
	 	Check whether Property exist or not.
	 
	 	@method containProperty
		@param name {String} Name of Property.
	 	@return {Boolean} true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	this.containProperty = function(name) {
		return properties.exists(name);
	}


	/**
	 	Add Property in property pool.
	 
	 	@method addProperty
		@param name {String} Name of Property.
	 	@param value {String} value of Property.
	 */
	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	

	/**
	 	Remove Property from property pool.
	 
	 	@method  removeProperty
		@param name {String} Name of Property.
	 */
	this.removeProperty = function(name) {
		properties.remove(name);
	}
}

