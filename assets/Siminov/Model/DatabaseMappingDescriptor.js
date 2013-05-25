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
 * Exposes methods to GET and SET Library Descriptor information as per define in DatabaseDescriptor.si.xml or LibraryDescriptor.si.xml  file by application.
	<p>
		<pre>
		
Example:

	<database-mapping>
	
		<table table_name="LIQUOR" class_name="siminov.orm.template.model.Liquor">
			
			<column variable_name="liquorType" column_name="LIQUOR_TYPE">
				<property name="type">TEXT</property>
				<property name="primary_key">true</property>
				<property name="not_null">true</property>
				<property name="unique">true</property>
			</column>		
	
			<column variable_name="description" column_name="DESCRIPTION">
				<property name="type">TEXT</property>
			</column>
	
			<column variable_name="history" column_name="HISTORY">
				<property name="type">TEXT</property>
			</column>
	
			<column variable_name="link" column_name="LINK">
				<property name="type">TEXT</property>
				<property name="default">www.wikipedia.org</property>
			</column>
	
			<column variable_name="alcholContent" column_name="ALCHOL_CONTENT">
				<property name="type">TEXT</property>
			</column>
	
			<index name="LIQUOR_INDEX_BASED_ON_LINK" unique="true">
				<column>HISTORY</column>
			</index>
										
		</table>
	
	</database-mapping>		
		
		}
	
		</pre>
	</p>
	
	
 *
 */


function DatabaseMappingDescriptor() {

    var tableName, className;

    var columns = [];
    var indexes = [];
    var relationships = [];

	/**
	 * Get table name.
	 * @return Name of table.
	 */
    this.getTableName = function() {
    	return tableName;
	}
	
	/**
	 * Set table name as per defined in DatabaseMapping.core.xml file.
	 * @param tableName Name of table.
	 */
    this.setTableName = function(val) {
		tableName = val;
	}

	/**
	 * Get Function class name.
	 * @return POJO class name.
	 */
    this.getClassName = function() {
    	return className;
	}
	
	/**
	 * Set Function class name as per defined in DatabaseMapping.core.xml file.
	 * @param className POJO class name.
	 */
    this.setClassName = function(val) {
		className = val;
	}

	/**
	 * Get all columns.
	 * @return Iterator of all columns.
	 */
    this.getColumns = function() {
        return columns;
	}

	/**
	 * Add column to DatabaseMapping object.
	 * @param column Column object.
	 */
    this.addColumn = function(column) {
        columns.push(column);
	}

	/**
	 * Get all indexes.
	 * @return Array which contain all indexes.
	 */
    this.getIndexes = function() {
    	return indexes;
	}
	
	/**
	 * Add index to DatabaseMapping object.
	 * @param index Index object.
	 */
    this.addIndex = function(index) {
    	indexes.push(index);
	}

	/**
	 * Get iterator of relationship objects. 
	 * @return Array Relationship objects.
	 */
    this.getRelationships = function() {	
    	return relationships;
	}
	
	/**
	 * Add relationship object.
	 * @param relationship Relationship object.
	 */
    this.addRelationship = function(relationship) {
    	relationships.push(relationship);
	}

}




/**
 * Exposes methods to GET and SET Column information as per define in DatabaseMappingDescriptor.si.xml file by application.
 <p>
 <pre>

 Example:

 <database-mapping>

 <table table_name="LIQUOR" class_name="siminov.orm.template.model.Liquor">

 <column variable_name="liquorType" column_name="LIQUOR_TYPE">
 <property name="type">TEXT</property>
 <property name="primary_key">true</property>
 <property name="not_null">true</property>
 <property name="unique">true</property>
 </column>

 </database-mapping>

 </pre>
 </p>
 *
 */
DatabaseMappingDescriptor.Column = function() {

    var variableName;
    var columnName;

    var properties = new Dictionary();

    /**
     * Get variable name.
     * @return
     */
    this.getVariableName = function() {
        return variableName;
    }

    /**
     * Set variable name as per defined in DatabaseMapping.core.xml file.
     * @param variableName Name of variable.
     */
    this.setVariableName = function(val) {
        variableName = val;
    }

    /**
     * Get column name.
     * @return Name Of Column.
     */
    this.getColumnName = function() {
        return columnName;
    }

    /**
     * Set column name as per defined in DatabaseMapping.core.xml file.
     * @param columnName Name of column name.
     */
    this.setColumnName = function(val) {
        columnName = val;
    }

    /**
     * Get type of column.
     * @return Type of column.
     */
    this.getType = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_TYPE);
    }

    /**
     * Set type of column as per defined in DatabaseMapping.core.xml file.
     * @param type Type of column.
     */
    this.setType = function(type) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_TYPE, type);
    }

    /**
     * Get check constraint of column.
     * @return Check constraint of column.
     */
    this.getCheck = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_CHECK);
    }

    /**
     * Set check constraint of column as per defined in DatabaseMapping.core.xml file.
     * @param check Check constraint.
     */
    this.setCheck = function(check) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_CHECK);
    }

    /**
     * Get default value of column.
     * @return Default value of column.
     */
    this.getDefaultValue = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_DEFAULT_VALUE);
    }

    /**
     * Set default value of column as per defined in DatabaseMapping.core.xml file.
     * @param defaultValue Default value of column.
     */
    this.setDefaultValue = function(defaultValue) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_DEFAULT_VALUE, defaultValue);
    }

    /**
     * Set column as primary key or not.
     * @param primaryKey TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.setPrimaryKey = function(primaryKey) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_PRIMARY_KEY, primaryKey);
    }

    /**
     * Check whether column is primary key.
     * @return TRUE: If column is primary key, FALSE: If column is not primary key.
     */
    this.isPrimaryKey = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_PRIMARY_KEY);
    }

    /**
     * Set whether column can be null or not.
     * @param isNotNull TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.setNotNull = function(notNull) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_NOT_NULL, notNull);
    }

    /**
     * Check whether column value can be not or not.
     * @return TRUE: If column value can be null, FALSE: If column value can not be null.
     */
    this.isNotNull = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_NOT_NULL);
    }

    /**
     * Set whether column is unique or not.
     * @param isUnique TRUE: If column is unique, FALSE: If column is not unique
     */
    this.setUnique = function(unique) {
        properties.add(Constants.DATABASE_MAPPING_DESCRIPTOR_UNIQUE, unique);
    }

    /**
     * Check whether column is unique or not.
     * @return TRUE: If column is unique, FALSE: If column is not unique.
     */
    this.isUnique = function() {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_UNIQUE);
    }
    
    	
	this.getProperties = function() {
		return properties.values();
	}

	this.getProperty = function(name) {
		return properties.get(name);
	}

	this.containProperty = function(name) {
		return properties.exists(name);
	}

	this.addProperty = function(name, value) {
		properties.add(name, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}
    
    
}




/**
 * Exposes methods to GET and SET Reference Map information as per define in DatabaseMappingDescriptor.si.xml file by application.
 <p>
 <pre>

 Example:
 <index name="LIQUOR_INDEX_BASED_ON_LINK" unique="true">
 <column>HISTORY</column>
 </index>

 </pre>
 </p>
 *
 */
DatabaseMappingDescriptor.Index = function() {

    var name;
    var unique = false;

    var columns = [];

    /**
     * Get index name.
     * @return Index Name.
     */
    this.getName = function() {
        return name;
    }

    /**
     * Set index name as per defined in DatabaseMapping.core.xml file.
     * @param name Index Name.
     */
    this.setName = function(val) {
        name = val;
    }

    /**
     * Set whether unqiue is unique or not.
     * @param unique TRUE: If index is unique, FALSE: If index is not unique.
     */
    this.setUnique = function(val) {
        unique = val;
    }

    /**
     * Check whether index should be unique or not.
     * @return TRUE: If index is unique, FALSE: If index is not unqiue.
     */
    this.isUnique = function() {
        if(unique)
            return true;
        else
            return false;
    }

    /**
     * Get all columns.
     * @return Array which contain all columns.
     */
    this.getColumns = function() {
        return columns;
    }

    /**
     * Add column to index.
     * @param column Name of column.
     */
    this.addColumn = function(column) {
        columns.push(column);
    }
}



/**
 * Contains relationship details.
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
     * Get relationship type.
     * @return Type of relationship.
     */
    this.getRelationshipType = function() {
        return relationshipType;
    }

    /**
     * Set relationship type.
     * @param relationshipType Type of relationship.
     */
    this.setRelationshipType = function(val) {
        return relationshipType = val;
    }

    /**
     * Get refer.
     * @return Name of refer.
     */
    this.getRefer = function() {
        refer;
    }

    /**
     * Set refer.
     * @param refer Name of refer.
     */
    this.setRefer = function(val) {
        refer = val;
    }

    /**
     * Get refer to.
     * @return Name of refer to.
     */
    this.getReferTo = function() {
        return referTo;
    }

    /**
     * Set refer to.
     * @param referTo Name of refer to.
     */
    this.setReferTo = function(val) {
        referTo = val;
    }

    /**
     * Get on update.
     * @return Action on update.
     */
    this.getOnUpdate = function() {
        return onUpdate;
    }

    /**
     * Set on update.
     * @param onUpdate Action on update.
     */
    this.setOnUpdate = function(val) {
        onUpdate = val;
    }

    /**
     * Get on delete.
     * @return Action on delete.
     */
    this.getOnDelete = function() {
        return onDelete;
    }

    /**
     * Set on delete.
     * @param onDelete Action on delete.
     */
    this.setOnDelete = function(val) {
        onDelete = val;
    }

    /**
     * Get getter refer method name.
     * @return Getter refer method name.
     */
    this.getGetterReferMethodName = function() {
        return getterReferMethodName;
    }

    /**
     * Set getter refer method name.
     * @param getterReferMethodName Name of getter refer method name.
     */
    this.setGetterReferMethodName = function(val) {
        getterReferMethodName = val;
    }

    /**
     * Get setter refer method name.
     * @return Name of setter refer method name.
     */
    this.getSetterReferMethodName = function() {
        return setterReferMethodName;
    }

    /**
     * Set setter refer method name.
     * @param setterReferMethodName Name of setter refer name.
     */
    this.setSetterReferMethodName = function(val) {
        setterReferMethodName = val;
    }

    /**
     * Set load property value.
     * @param load TRUE: If load property value is true; FALSE: If load property value is false.
     */
    this.setLoad = function(isLoad) {
        properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_LOAD);
    }

    /**
     * Check whether load property value is set to TRUE/FASLE.
     * @return TRUE: If load property value is set to true; FALSE: If load property value is set to false.
     */
    this.isLoad = function() {
        return properties.get(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_LOAD);
    }

		
	this.getProperties = function() {
		return properties.values();
	}

	this.getProperty = function(name) {
		return properties.get(name);
	}

	this.containProperty = function(name) {
		return properties.exists(name);
	}

	this.addProperty = function(name, value) {
		properties.add(key, value);
	}
	
	this.removeProperty = function(name) {
		properties.remove(name);
	}

}

