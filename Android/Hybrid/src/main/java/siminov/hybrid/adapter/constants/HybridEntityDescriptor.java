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
 * Exposes constants which represents Entity Descriptor on Hybrid.
 */
public interface HybridEntityDescriptor {

	/**
	 * Hybrid Module Entity Descriptor Function Name.
	 */
	public String ENTITY_DESCRIPTOR = "EntityDescriptor";
	
	
	/**
	 * Hybrid Entity Descriptor Table Name.
	 */
	public String TABLE_NAME = "tableName";
	
	/**
	 * Hybrid Entity Descriptor Class Name.
	 */
	public String CLASS_NAME = "className";
	
	
	/**
	 * Hybrid Entity Descriptor Columns.
	 */
	public String ENTITIES = "Array";
	
	/**
	 * Hybrid Entity Descriptor Column.
	 */
	public String ATTRIBUTE = "EntityDescriptor.Attribute";
	
	
	/**
	 * Hybrid Entity Descriptor Column Variable Name.
	 */
	public String VARIABLE_NAME = "variableName";
	
	/**
	 * Hybrid Entity Descriptor Column Name.
	 */
	public String COLUMN_NAME = "columnName";
	
	/**
	 * Hybrid Entity Descriptor Column Type.
	 */
	public String TYPE = "type";
	
	/**
	 * Hybrid Entity Descriptor Column Is Primary.
	 */
	public String PRIMARY_KEY = "primaryKey";
	
	/**
	 * Hybrid Entity Descriptor Column Is Not Null.
	 */
	public String NOT_NULL = "notNull";
	
	/**
	 * Hybrid Entity Descriptor Column Is Unique.
	 */
	public String UNIQUE = "unique";

	/**
	 * Hybrid Entity Descriptor Column Check Condition.
	 */
	public String CHECK = "check";
	
	/**
	 * Hybrid Entity Descriptor Column Default Value.
	 */
	public String DEFAULT = "defaultValue";
	
	
	/**
	 * Hybrid Entity Descriptor Index's.
	 */
	public String INDEXS = "Array";

	/**
	 * Hybrid Entity Descriptor Index Function.
	 */
	public String INDEX = "EntityDescriptor.Index";

	/**
	 * Hybrid Entity Descriptor Index Name.
	 */
	public String INDEX_NAME = "name";
	
	/**
	 * Hybrid Entity Descriptor Column.
	 */
	public String INDEX_UNIQUE = "unique";
	
	/**
	 * Hybrid Entity Descriptor Index Column.
	 */
	public String INDEX_COLUMN = "indexColumn";
	
	
	/**
	 * Hybrid Entity Descriptor Relationships.
	 */
	public String RELATIONSHIPS = "Array";
	
	/**
	 * Hybrid Entity Descriptor Relationship Function.
	 */
	public String RELATIONSHIP = "EntityDescriptor.Relationship";
	
	/**
	 * Hybrid Entity Descriptor Relationship Type.
	 */
	public String RELATIONSHIP_TYPE = "relationshipType";
	
	/**
	 * Hybrid Entity Descriptor Relationship Refer.
	 */
	public String REFER = "refer";

	/**
	 * Hybrid Entity Descriptor Relationship Refer To.
	 */
	public String REFER_TO = "referTo";
	
	
	/**
	 * Hybrid Entity Descriptor Relationship On Update. 
	 */
	public String ON_UPDATE = "onUpdate";
	
	/**
	 * Hybrid Entity Descriptor Relationship On Delete.
	 */
	public String ON_DELETE = "onDelete";

	/**
	 * Hybrid Entity Descriptor Relationship Load Property.
	 */
	public String LOAD = "load";
	
}
