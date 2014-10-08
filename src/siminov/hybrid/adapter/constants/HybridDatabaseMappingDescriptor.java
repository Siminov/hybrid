/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2015] [Siminov Software Solution LLP|support@siminov.com]
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



package siminov.hybrid.adapter.constants;

/**
 * Exposes constants which represents Databse Mapping Descriptor on Web.
 */
public interface HybridDatabaseMappingDescriptor {

	/**
	 * Web Module Database Mapping Descriptor Function Name.
	 */
	public String DATABASE_MAPPING_DESCRIPTOR = "DatabaseMappingDescriptor";
	
	
	/**
	 * Web Database Mapping Descriptor Table Name.
	 */
	public String TABLE_NAME = "tableName";
	
	/**
	 * Web Database Mapping Descriptor Class Name.
	 */
	public String CLASS_NAME = "className";
	
	
	/**
	 * Web Database Mapping Descriptor Columns.
	 */
	public String ENTITIES = "Array";
	
	/**
	 * Web Database Mapping Descriptor Column.
	 */
	public String ATTRIBUTE = "DatabaseMappingDescriptor.Attribute";
	
	
	/**
	 * Web Database Mapping Descriptor Column Variable Name.
	 */
	public String VARIABLE_NAME = "variableName";
	
	/**
	 * Web Database Mapping Descriptor Column Name.
	 */
	public String COLUMN_NAME = "columnName";
	
	/**
	 * Web Database Mapping Descriptor Column Type.
	 */
	public String TYPE = "type";
	
	/**
	 * Web Database Mapping Descriptor Column Is Primary.
	 */
	public String PRIMARY_KEY = "primaryKey";
	
	/**
	 * Web Database Mapping Descriptor Column Is Not Null.
	 */
	public String NOT_NULL = "notNull";
	
	/**
	 * Web Database Mapping Descriptor Column Is Unique.
	 */
	public String UNIQUE = "unique";

	/**
	 * Web Database Mapping Descriptor Column Check Condition.
	 */
	public String CHECK = "check";
	
	/**
	 * Web Database Mapping Descriptor Column Default Value.
	 */
	public String DEFAULT = "defaultValue";
	
	
	/**
	 * Web Database Mapping Descriptor Index's.
	 */
	public String INDEXS = "Array";

	/**
	 * Web Database Mapping Descriptor Index Function.
	 */
	public String INDEX = "DatabaseMappingDescriptor.Index";

	/**
	 * Web Database Mapping Descriptor Index Name.
	 */
	public String INDEX_NAME = "name";
	
	/**
	 * Web Database Mapping Descriptor Column.
	 */
	public String INDEX_UNIQUE = "unique";
	
	/**
	 * Web Database Mapping Descriptor Index Column.
	 */
	public String INDEX_COLUMN = "indexColumn";
	
	
	/**
	 * Web Database Mapping Descriptor Relationships.
	 */
	public String RELATIONSHIPS = "Array";
	
	/**
	 * Web Database Mapping Descriptor Relationship Function.
	 */
	public String RELATIONSHIP = "DatabaseMappingDescriptor.Relationship";
	
	/**
	 * Web Database Mapping Descriptor Relationship Type.
	 */
	public String RELATIONSHIP_TYPE = "relationshipType";
	
	/**
	 * Web Database Mapping Descriptor Relationship Refer.
	 */
	public String REFER = "refer";

	/**
	 * Web Database Mapping Descriptor Relationship Refer To.
	 */
	public String REFER_TO = "referTo";
	
	
	/**
	 * Web Database Mapping Descriptor Relationship On Update. 
	 */
	public String ON_UPDATE = "onUpdate";
	
	/**
	 * Web Database Mapping Descriptor Relationship On Delete.
	 */
	public String ON_DELETE = "onDelete";

	/**
	 * Web Database Mapping Descriptor Relationship Load Property.
	 */
	public String LOAD = "load";
	
}
