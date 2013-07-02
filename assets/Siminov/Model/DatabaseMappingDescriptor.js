/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
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


/**
 	Exposes methods to GET and SET Library Descriptor information as per define in DatabaseDescriptor.si.xml or LibraryDescriptor.si.xml  file by application.
		
	Example:

		<database-mapping>
		
			<table table_name="LIQUOR" class_name="Liquor">
		
				<column variable_name="liquorType" column_name="LIQUOR_TYPE">
					<property name="type">String</property>
					<property name="primary_key">true</property>
					<property name="not_null">true</property>
					<property name="unique">true</property>
				</column>		
		
				<column variable_name="description" column_name="DESCRIPTION">
					<property name="type">String</property>
				</column>
		
				<column variable_name="history" column_name="HISTORY">
					<property name="type">String</property>
				</column>
		
				<column variable_name="link" column_name="LINK">
					<property name="type">String</property>
					<property name="default">www.wikipedia.org</property>
				</column>
		
				<column variable_name="alcholContent" column_name="ALCHOL_CONTENT">
					<property name="type">String</property>
				</column>
		
				<index name="LIQUOR_INDEX_BASED_ON_LINK" unique="true">
					<column>HISTORY</column>
				</index>
		
				<relationships>
		
				    <one-to-many refer="liquorBrands" refer_to="LiquorBrand" on_update="cascade" on_delete="cascade">
						<property name="load">true</property>
					</one-to-many>		
		
				</relationships>
		
			</table>
		
		</database-mapping>				

	@module Model	
	@class DatabaseMappingDescriptor
	@constructor
 */


function DatabaseMappingDescriptor() {

    var tableName, className;

    var columns = [];
    var indexes = [];
    var relationships = [];

	/**
	 	Get table name.
	 
	 	@method getTableName
	 	@return {String} Name of table.
	 */
    this.getTableName = function() {
    	return tableName;
	}
	
	/**
	 	Set table name as per defined in DatabaseMapping.core.xml file.
	 
	 	@method setTableName
	 	@param tableName Name of table.
	 */
    this.setTableName = function(val) {
		tableName = val;
	}

	/**
	 	Get Function class name.
	 
	 	@method getClassName
	 	@return {String} POJO class name.
	 */
    this.getClassName = function() {
    	return className;
	}
	
	/**
	 	Set Function class name as per defined in DatabaseMapping.core.xml file.
	 	
	 	@method setClassName
	 	@param className {String} POJO class name.
	 */
    this.setClassName = function(val) {
		className = val;
	}

	/**
	 	Get all columns.
	 	
	 	@method getColumns
	 	@return {Array} All columns.
	 */
    this.getColumns = function() {
        return columns;
	}

	/**
	 	Add column to DatabaseMapping object.
	 
	 	@method addColumn
	 	@param column {Column} Column object.
	 */
    this.addColumn = function(column) {
        columns.push(column);
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
	 	Add index to DatabaseMapping object.
	 
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
 	Exposes methods to GET and SET Column information as per define in DatabaseMappingDescriptor.si.xml file by application.

	 Example:
	
		 <column variable_name="liquorType" column_name="LIQUOR_TYPE">
			 <property name="type">TEXT</property>
			 <property name="primary_key">true</property>
			 <property name="not_null">true</property>
			 <property name="unique">true</property>
		 </column>
	
	@class DatabaseMappingDescriptor.Column
	
 */
DatabaseMappingDescriptor.Column = function() {

    var variableName;
    var columnName;

    var properties = new Dictionary();

    /**
     	Get variable name.
     
     	@method getVariableName
     	@return {String} Variable Name
     */
    this.getVariableName = function() {
        return variableName;
    }

    /**
     	Set variable name as per defined in DatabaseMapping.core.xml file.
     
     	@method setVariableName
     	@param variableName {String} Name of variable.
     */
    this.setVariableName = function(val) {
        variableName = val;
    }

    /**
     	Get column name.
     
     	@method getColumnName
     	@return {String} Name Of Column.
     */
    this.getColumnName = function() {
        return columnName;
    }

    /**
     	Set column name as per defined in DatabaseMapping.core.xml file.
     
     	@method setColumnName
     	@param columnName {String} Name of column name.
     */
    this.setColumnName = function(val) {
        columnName = val;
    }

    /**
     	Get type of column.
     	
     	@method getType
     	@return {String} Type of column.
     */
    this.getType = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_TYPE);
    }

    /**
     	Set type of column as per defined in DatabaseMapping.core.xml file.
     	
     	@methof setType
     	@param type {String} Type of column.
     */
    this.setType = function(type) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_TYPE, type);
    }

    /**
     	Get check constraint of column.
     
     	@method getCheck
     	@return {String} Check constraint of column.
     */
    this.getCheck = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_CHECK);
    }

    /**
     	Set check constraint of column as per defined in DatabaseMapping.core.xml file.
     
     	@method setCheck
     	@param check {String} Check constraint.
     */
    this.setCheck = function(check) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_CHECK);
    }

    /**
     	Get default value of column.
     	
     	@method getDefaultValue
     	@return Default value of column.
     */
    this.getDefaultValue = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_DEFAULT_VALUE);
    }

    /**
     	Set default value of column as per defined in DatabaseMapping.core.xml file.
     
     	@method setDefaultValue
     	@param defaultValue {String} Default value of column.
     */
    this.setDefaultValue = function(defaultValue) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_DEFAULT_VALUE, defaultValue);
    }

    /**
     	Set column as primary key or not.
     	
     	@method setPrimary
     	@param primaryKey {Boolean} (true/false) TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.setPrimaryKey = function(primaryKey) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_PRIMARY_KEY, primaryKey);
    }

    /**
     	Check whether column is primary key.
     	
     	@method isPrimaryKey
     	@return {Boolean} (true/false) TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.isPrimaryKey = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_PRIMARY_KEY);
    }

    /**
     	Set whether column can be null or not.
     	
     	@method setNotNull
     	@param isNotNull {Boolean} (true/false) TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.setNotNull = function(notNull) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_NOT_NULL, notNull);
    }

    /**
     	Check whether column value can be not or not.
     
     	@method isNotNull
     	@return {Boolean} (true/false) TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.isNotNull = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_NOT_NULL);
    }

    /**
     	Set whether column is unique or not.
     	
     	@method setUnique
     	@param isUnique {Boolean} (true/false) TRUE: If column is unique, FALSE: If column is not unique
     */
    this.setUnique = function(unique) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_UNIQUE, unique);
    }

    /**
     	Check whether column is unique or not.
     	
     	@method isUnique
     	@return {Boolean} (true/false) TRUE: If column is unique, FALSE: If column is not unique.
     */
    this.isUnique = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_UNIQUE);
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
 	Exposes methods to GET and SET Reference Map information as per define in DatabaseMappingDescriptor.si.xml file by application.

	Example:
		<index name="LIQUOR_INDEX_BASED_ON_LINK" unique="true">
			<column>HISTORY</column>
		</index>


	@class DatabaseMappingDescriptor.Index
	@constructor
*/
DatabaseMappingDescriptor.Index = function() {

    var name;
    var unique = false;

    var columns = [];

    /**
     	Get index name.
     
     	@method getName
     	@return {String} Index Name.
     */
    this.getName = function() {
        return name;
    }

    /**
     	Set index name as per defined in DatabaseMapping.core.xml file.
     	
     	@method setName
     	@param name {String} Index Name.
     */
    this.setName = function(val) {
        name = val;
    }

    /**
     	Set whether unqiue is unique or not.
     
     	@method setUnique
     	@param unique {Boolean} (true/false) TRUE: If index is unique, FALSE: If index is not unique.
     */
    this.setUnique = function(val) {
        unique = val;
    }

    /**
     	Check whether index should be unique or not.
     
     	@method isUnique
     	@return {Boolean} (true/false) TRUE: If index is unique, FALSE: If index is not unqiue.
     */
    this.isUnique = function() {
        if(unique)
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
	
	@class DatabaseMappingDescriptor.Relationship
 */
DatabaseMappingDescriptor.Relationship = function() {

    var refer;
    var referTo;

    var relationshipType;

    var onUpdate;
    var onDelete;

    var getterReferMethodName;
    var setterReferMethodName;

    var properties = new Dictionary();

    /**
     	Get relationship type.
     
     	@method getRelationshipType
     	@return {String} Type of relationship.
     */
    this.getRelationshipType = function() {
        return relationshipType;
    }

    /**
     	Set relationship type.
     
     	@method setRelationshipType
     	@param relationshipType {String} Type of relationship.
     */
    this.setRelationshipType = function(val) {
        return relationshipType = val;
    }

    /**
     	Get refer.
     	
     	@method getRefer
     	@return {String} Name of refer.
     */
    this.getRefer = function() {
        refer;
    }

    /**
     	Set refer.
     	
     	@method setRefer
     	@param refer {String} Name of refer.
     */
    this.setRefer = function(val) {
        refer = val;
    }

    /**
     	Get refer to.
     
     	@method getReferTo
     	@return {String} Name of refer to.
     */
    this.getReferTo = function() {
        return referTo;
    }

    /**
     	Set refer to.
     	
     	@method setReferTo
     	@param referTo {String} Name of refer to.
     */
    this.setReferTo = function(val) {
        referTo = val;
    }

    /**
     	Get on update.
     	
     	@method getOnUpdate
     	@return {String} Action on update.
     */
    this.getOnUpdate = function() {
        return onUpdate;
    }

    /**
     	Set on update.
     
     	@method setOnUpdate
     	@param onUpdate {String} Action on update.
     */
    this.setOnUpdate = function(val) {
        onUpdate = val;
    }

    /**
     	Get on delete.
     
     	@method getOnDelete
     	@return {String} Action on delete.
     */
    this.getOnDelete = function() {
        return onDelete;
    }

    /**
     	Set on delete.
     
     	@method setOnDelete
     	@param onDelete {String} Action on delete.
     */
    this.setOnDelete = function(val) {
        onDelete = val;
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
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_LOAD);
    }

    /**
     	Check whether load property value is set to TRUE/FASLE.
     	
     	@method isLoad
     	@return {Boolean} (true/false) TRUE: If load property value is set to true; FALSE: If load property value is set to false.
     */
    this.isLoad = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_LOAD);
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

