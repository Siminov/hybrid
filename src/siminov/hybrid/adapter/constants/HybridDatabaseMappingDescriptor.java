/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution|support@siminov.com]
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

public interface HybridDatabaseMappingDescriptor {

	public String DATABASE_MAPPING_DESCRIPTOR = "DatabaseMappingDescriptor";
	
	public String TABLE_NAME = "tableName";
	public String CLASS_NAME = "className";
	
	public String COLUMNS = "Array";
	public String COLUMN = "DatabaseMappingDescriptor.Column";
	
	public String VARIABLE_NAME = "variableName";
	public String COLUMN_NAME = "columnName";
	
	public String TYPE = "type";
	public String PRIMARY_KEY = "primaryKey";
	public String NOT_NULL = "notNull";
	public String UNIQUE = "unique";
	public String CHECK = "check";
	public String DEFAULT = "defaultValue";
	
	public String INDEXS = "Array";
	public String INDEX = "DatabaseMappingDescriptor.Index";
	public String INDEX_NAME = "name";
	public String INDEX_UNIQUE = "unique";
	
	public String INDEX_COLUMNS = "Array";
	public String INDEX_COLUMN = "indexColumn";
	
	public String RELATIONSHIPS = "Array";
	public String RELATIONSHIP = "DatabaseMappingDescriptor.Relationship";
	public String RELATIONSHIP_TYPE = "relationshipType";
	
	public String REFER = "refer";
	public String REFER_TO = "referTo";
	
	public String ON_UPDATE = "onUpdate";
	public String ON_DELETE = "onDelete";
	public String LOAD = "load";
	
}
