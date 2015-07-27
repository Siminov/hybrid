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



package siminov.web.adapter.constants;

/**
 * Exposes constants which represents Entity Descriptor on Web.
 */
public interface WebEntityDescriptor {

	/**
	 * Web Module Entity Descriptor Function Name.
	 */
	public String ENTITY_DESCRIPTOR = "EntityDescriptor";
	
	
	/**
	 * Web Entity Descriptor Table Name.
	 */
	public String TABLE_NAME = "tableName";
	
	/**
	 * Web Entity Descriptor Class Name.
	 */
	public String CLASS_NAME = "className";
	
	
	/**
	 * Web Entity Descriptor Columns.
	 */
	public String ENTITIES = "Array";
	
	/**
	 * Web Entity Descriptor Column.
	 */
	public String ATTRIBUTE = "EntityDescriptor.Attribute";
	
	
	/**
	 * Web Entity Descriptor Column Variable Name.
	 */
	public String VARIABLE_NAME = "variableName";
	
	/**
	 * Web Entity Descriptor Column Name.
	 */
	public String COLUMN_NAME = "columnName";
	
	/**
	 * Web Entity Descriptor Column Type.
	 */
	public String TYPE = "type";
	
	/**
	 * Web Entity Descriptor Column Is Primary.
	 */
	public String PRIMARY_KEY = "primaryKey";
	
	/**
	 * Web Entity Descriptor Column Is Not Null.
	 */
	public String NOT_NULL = "notNull";
	
	/**
	 * Web Entity Descriptor Column Is Unique.
	 */
	public String UNIQUE = "unique";

	/**
	 * Web Entity Descriptor Column Check Condition.
	 */
	public String CHECK = "check";
	
	/**
	 * Web Entity Descriptor Column Default Value.
	 */
	public String DEFAULT = "defaultValue";
	
	
	/**
	 * Web Entity Descriptor Index's.
	 */
	public String INDEXS = "Array";

	/**
	 * Web Entity Descriptor Index Function.
	 */
	public String INDEX = "EntityDescriptor.Index";

	/**
	 * Web Entity Descriptor Index Name.
	 */
	public String INDEX_NAME = "name";
	
	/**
	 * Web Entity Descriptor Column.
	 */
	public String INDEX_UNIQUE = "unique";
	
	/**
	 * Web Entity Descriptor Index Column.
	 */
	public String INDEX_COLUMN = "indexColumn";
	
	
	/**
	 * Web Entity Descriptor Relationships.
	 */
	public String RELATIONSHIPS = "Array";
	
	/**
	 * Web Entity Descriptor Relationship Function.
	 */
	public String RELATIONSHIP = "EntityDescriptor.Relationship";
	
	/**
	 * Web Entity Descriptor Relationship Type.
	 */
	public String RELATIONSHIP_TYPE = "relationshipType";
	
	/**
	 * Web Entity Descriptor Relationship Refer.
	 */
	public String REFER = "refer";

	/**
	 * Web Entity Descriptor Relationship Refer To.
	 */
	public String REFER_TO = "referTo";
	
	
	/**
	 * Web Entity Descriptor Relationship On Update. 
	 */
	public String ON_UPDATE = "onUpdate";
	
	/**
	 * Web Entity Descriptor Relationship On Delete.
	 */
	public String ON_DELETE = "onDelete";

	/**
	 * Web Entity Descriptor Relationship Load Property.
	 */
	public String LOAD = "load";
	
}
