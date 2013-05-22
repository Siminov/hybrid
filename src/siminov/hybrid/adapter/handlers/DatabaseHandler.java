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


package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.parsers.HybridSiminovDataBuilder;
import siminov.hybrid.parsers.HybridSiminovDataParser;
import siminov.orm.Constants;
import siminov.orm.database.DatabaseBundle;
import siminov.orm.database.design.IDatabase;
import siminov.orm.database.design.IQueryBuilder;
import siminov.orm.exception.DatabaseException;
import siminov.orm.exception.DeploymentException;
import siminov.orm.exception.SiminovException;
import siminov.orm.log.Log;
import siminov.orm.model.DatabaseDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor;
import siminov.orm.model.DatabaseMappingDescriptor.Column;
import siminov.orm.model.DatabaseMappingDescriptor.Relationship;
import siminov.orm.resource.Resources;


/**
 * It handles all request related to database.
 * LIKE: save, update, saveOrUpdate, delete.
 */
public class DatabaseHandler {

	private static Resources ormResources = Resources.getInstance();
	private static siminov.hybrid.resource.Resources hybridResources = siminov.hybrid.resource.Resources.getInstance();

	
	/**
	 * Handles Database Save Request From Web.
	 * @param data Data Need To Be Save In Database.
	 * @throws DatabaseException If any exception occur while saving data in Database.
	 */
	public void save(final String data) throws DatabaseException {

		HybridSiminovDatas jsSiminovDatas = parseHybridSiminovDatas(data);
		save(jsSiminovDatas);
		
	}
	
	private static final void save(HybridSiminovDatas jsSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> jsDatas = jsSiminovDatas.getHybridSiminovDatas();
		while(jsDatas.hasNext()) {
			save(jsDatas.next());
		}
		
	}

	private static final void save(HybridSiminovData jsSiminovData) throws DatabaseException {
		
		String className = jsSiminovData.getDataType();
		Iterator<HybridSiminovValue> jsValues = jsSiminovData.getValues();
		
		Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(jsValues.hasNext()) {
			HybridSiminovValue jsSiminovValue = jsValues.next();
			jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
		}
		
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnClassName(className);
		
		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(className);
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		String tableName = databaseMappingDescriptor.getTableName();
		
		Collection<String> columnNames = new LinkedList<String>();
		Collection<Object> columnValues = new LinkedList<Object>();

		Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();
		while(columns.hasNext()) {
			Column column = columns.next();
			
			String columnName = column.getColumnName();
			Object columnValue = jsSiminovValues.get(column.getVariableName()).getValue();
			
			columnNames.add(columnName);
			columnValues.add(columnValue);
			
		}

			
		processManyToOneRelationship(jsSiminovData, columnNames, columnValues);
		processManyToManyRelationship(jsSiminovData, columnNames, columnValues);
		

		String query = queryBuilder.formSaveBindQuery(tableName, columnNames.iterator());
		database.executeBindQuery(databaseDescriptor, databaseMappingDescriptor, query, columnValues.iterator());

		

		/*
		 * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
		 */
		Iterator<DatabaseMappingDescriptor.Relationship> relationships = databaseMappingDescriptor.getRelationships();
		while(relationships.hasNext()) {
			DatabaseMappingDescriptor.Relationship relationship = relationships.next();
			
			boolean isLoad = relationship.isLoad();
			if(!isLoad) {
				continue;
			}
			
			String relationshipType = relationship.getRelationshipType();
			if(relationshipType == null || relationshipType.length() <= 0) {
				continue;
			}
			
			if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_ONE_TO_ONE)) {
				
				HybridSiminovData referedData = null;
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						referedData = data;
						break;
					}
				}
				

				if(referedData == null) {
					continue;
				}

				saveOrUpdate(referedData);
			} else if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_ONE_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(jsSiminovData);
						saveOrUpdate(data);
					}
				}
				
			} else if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_MANY_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(jsSiminovData);
						saveOrUpdate(data);
					}
				}
			}
		}
	}

	
	
	/**
	 * Handles Database Update Request From Web.
	 * @param data Data Need To Be Update In Database.
	 * @throws DatabaseException If any exception occur while updating data in Database.
	 */
	public void update(String data) throws DatabaseException {
		
		HybridSiminovDatas jsSiminovDatas = parseHybridSiminovDatas(data);
		update(jsSiminovDatas);
		
	}

	private static final void update(HybridSiminovDatas jsSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> jsDatas = jsSiminovDatas.getHybridSiminovDatas();
		while(jsDatas.hasNext()) {
			update(jsDatas.next());
		}
		
	}
	
	private static final void update(HybridSiminovData jsSiminovData) throws DatabaseException {
		
		String className = jsSiminovData.getDataType();
		Iterator<HybridSiminovValue> jsValues = jsSiminovData.getValues();
		
		Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(jsValues.hasNext()) {
			HybridSiminovValue jsSiminovValue = jsValues.next();
			jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
		}
		
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnClassName(className);

		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(className);
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		
		StringBuilder whereClause = new StringBuilder();
		String tableName = databaseMappingDescriptor.getTableName();
		
		Collection<String> columnNames = new LinkedList<String>();
		Collection<Object> columnValues = new LinkedList<Object>();

		Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();
		while(columns.hasNext()) {
			Column column = columns.next();
			
			String columnName = column.getColumnName();
			Object columnValue = jsSiminovValues.get(column.getVariableName()).getValue();
			
			columnNames.add(columnName);
			columnValues.add(columnValue);
			
			if(column.isPrimaryKey()) {
				if(whereClause.length() == 0) {
					whereClause.append(columnName + "= '" + columnValue + "'");
				} else {
					whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
				}
			}
		}

		
		processManyToOneRelationship(jsSiminovData, whereClause);
		processManyToManyRelationship(jsSiminovData, whereClause);

		processManyToOneRelationship(jsSiminovData, columnNames, columnValues);
		processManyToManyRelationship(jsSiminovData, columnNames, columnValues);
		
		
		String query = queryBuilder.formUpdateBindQuery(tableName, columnNames.iterator(), whereClause.toString());

		Iterator<Object> values = columnValues.iterator();
		database.executeBindQuery(databaseDescriptor, databaseMappingDescriptor, query, values);

		
		
		/*
		 * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
		 */
		Iterator<DatabaseMappingDescriptor.Relationship> relationships = databaseMappingDescriptor.getRelationships();
		while(relationships.hasNext()) {
			DatabaseMappingDescriptor.Relationship relationship = relationships.next();
			
			boolean isLoad = relationship.isLoad();
			if(!isLoad) {
				continue;
			}
			
			String relationshipType = relationship.getRelationshipType();
			if(relationshipType == null || relationshipType.length() <= 0) {
				continue;
			}
			
			if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_ONE_TO_ONE)) {
				
				HybridSiminovData referedData = null;
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						referedData = data;
						break;
					}
				}
				

				if(referedData == null) {
					continue;
				}

				saveOrUpdate(referedData);
			} else if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_ONE_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(jsSiminovData);
						saveOrUpdate(data);
					}
				}
				
			} else if(relationshipType.equalsIgnoreCase(Constants.DATABASE_MAPPING_DESCRIPTOR_RELATIONSHIPS_MANY_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = jsSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(jsSiminovData);
						saveOrUpdate(data);
					}
				}
			}
		}
		
	}
	

	/**
	 * Handles Database Save Or Update Request From Web.
	 * @param data Data Need To Be Save Or Update In Database.
	 * @throws DatabaseException If any exception occur while saving or updating data in Database.
	 */
	public void saveOrUpdate(String data) throws DatabaseException {

		HybridSiminovDatas jsSiminovDatas = parseHybridSiminovDatas(data);
		saveOrUpdate(jsSiminovDatas);
		
	}
	
	private static final void saveOrUpdate(HybridSiminovDatas jsSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> jsDatas = jsSiminovDatas.getHybridSiminovDatas();
		
		while(jsDatas.hasNext()) {
			HybridSiminovData jsSiminovData = jsDatas.next();
			saveOrUpdate(jsSiminovData);
		}
	}

	private static final void saveOrUpdate(HybridSiminovData jsSiminovData) throws DatabaseException {

		String className = jsSiminovData.getDataType();
		Iterator<HybridSiminovValue> jsValues = jsSiminovData.getValues();
		
		Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(jsValues.hasNext()) {
			HybridSiminovValue jsSiminovValue = jsValues.next();
			jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
		}
		
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		
		StringBuilder whereClause = new StringBuilder();
		Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();
		while(columns.hasNext()) {
			Column column = columns.next();
			
			if(column.isPrimaryKey()) {

				String columnName = column.getColumnName();
				Object columnValue = jsSiminovValues.get(column.getVariableName()).getValue();

				if(whereClause.length() <= 0) {
					whereClause.append(columnName + "= '" + columnValue + "'");
				} else {
					whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
				}
			}
			
		}

		
		processManyToOneRelationship(jsSiminovData, whereClause);
		processManyToManyRelationship(jsSiminovData, whereClause);
		

		if(whereClause == null || whereClause.length() <= 0) {
			save(jsSiminovData);
			return;
		}

		
		/*
		 * 4. IF EXISTS: call update method, ELSE: call save method.
		 */
		int count = count(databaseMappingDescriptor, null, false, whereClause.toString(), null, null);
		if(count <= 0) {
			save(jsSiminovData);
		} else {
			update(jsSiminovData);
		}
	}
	

	
	/**
	 * Handles Database Delete Request From Web.
	 * @param className Web Model Class Name of which delete request is sent.
	 * @param whereClause Based on which tuple will be deleted from table.
	 * @param data Data Need To Be Delete In Database.
	 * @throws DatabaseException If any exception occur while deleting data in Database.
	 */
	public void delete(final String className, final String whereClause, final String data) throws DatabaseException {
		
		HybridSiminovDatas jsSiminovDatas = null;
		
		if(data != null && data.length() > 0) {
			jsSiminovDatas = parseHybridSiminovDatas(data);			
		
			delete(jsSiminovDatas);
			return;
		}
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
		
		delete(databaseMappingDescriptor, whereClause);

	}

	static final void delete(final HybridSiminovDatas jsSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> jsDatas = jsSiminovDatas.getHybridSiminovDatas();
		
		while(jsDatas.hasNext()) {
			HybridSiminovData jsSiminovData = jsDatas.next();
			
			String className = jsSiminovData.getDataType();
			Iterator<HybridSiminovValue> jsValues = jsSiminovData.getValues();
			
			Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(jsValues.hasNext()) {
				HybridSiminovValue jsSiminovValue = jsValues.next();
				jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
			}
			
			DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
			
			StringBuilder whereClause = new StringBuilder();
			
			Collection<String> columnNames = new LinkedList<String>();
			Collection<Object> columnValues = new LinkedList<Object>();
	
			Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();
			while(columns.hasNext()) {
				Column column = columns.next();
				
				String columnName = column.getColumnName();
				Object columnValue = jsSiminovValues.get(column.getVariableName()).getValue();
				
				columnNames.add(columnName);
				columnValues.add(columnValue);
				
				if(column.isPrimaryKey()) {
					if(whereClause.length() == 0) {
						whereClause.append(columnName + "= '" + columnValue + "'");
					} else {
						whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
					}
				}
			}
	
			
			processManyToOneRelationship(jsSiminovData, whereClause);
			processManyToManyRelationship(jsSiminovData, whereClause);
			
			
			delete(databaseMappingDescriptor, whereClause.toString());
		}
	}

	static final void delete(final DatabaseMappingDescriptor databaseMappingDescriptor, final String whereClause) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = hybridResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		
		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		
		String query = queryBuilder.formDeleteQuery(databaseMappingDescriptor.getTableName(), whereClause.toString());
		database.executeQuery(databaseDescriptor, databaseMappingDescriptor, query);

	}
	

	/**
	 * 
	 * @param data 
	 * @throws DatabaseException 
	 */
	
	/**
	 * Handles Database Select Request From Web.
	 * @param className Web Model Function Name.
	 * @param distinct Distinct tuples need to be fetched or not.
	 * @param whereClause Where Clause based on which tuples will be fetched from table.
	 * @param columnNames Name of Columns for which data needs to be fetched.
	 * @param groupBy Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param havingClause Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 * @param orderBy Used to specify ORDER BY keyword to sort the result-set.
	 * @param whichOrderBy Used to specify ORDER BY ASC OR DESC keyword to sort the result-set in ascending order.
	 * @param limit Used to specify the range of data need to fetch from table.
	 * @return Return Tuples Fetched From Table.
	 * @throws DatabaseException If any exception occur while selecting data in Database.
	 */
	public String select(final String className, final Boolean distinct, final String whereClause, final String[] columnNames, final String[] groupBy, final String havingClause, final String[] orderBy, final String whichOrderBy, final String limit) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(className);

		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(className);
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "select", "No Database Instance Found For DATABASE-MAPPING: " + className);
			throw new DeploymentException(DatabaseHandler.class.getName(), "select", "No Database Instance Found For DATABASE-MAPPING: " + className);
		}
		
		Collection<String> columnNameCollection = new LinkedList<String>();
		Collection<String> groupByCollection = new LinkedList<String>();
		Collection<String> orderByCollection = new LinkedList<String>();
		
		while(columnNames != null && columnNames.length > 0) {
			for(int i = 0;i < columnNames.length;i++) {
				columnNameCollection.add(columnNames[i]);
			}
		} 
		
		while(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupByCollection.add(groupBy[i]);
			}
		}
		
		while(orderBy != null && orderBy.length > 0) {
			for(int i = 0;i < orderBy.length;i++) {
				orderByCollection.add(orderBy[i]);
			}
		}

		
		Iterator<Map<String, Object>> datas = database.executeFetchQuery(getDatabaseDescriptor(className), databaseMappingDescriptor, queryBuilder.formSelectQuery(databaseMappingDescriptor.getTableName(), false, whereClause, columnNameCollection.iterator(), groupByCollection.iterator(), havingClause, orderByCollection.iterator(), null, limit));
		Collection<Map<String, Object>> datasBundle = new LinkedList<Map<String,Object>>();
		while(datas.hasNext()) {
			datasBundle.add(datas.next());
		}

		HybridSiminovDatas jsSiminovDatas = parseData(databaseMappingDescriptor, datasBundle.iterator());
		datas = datasBundle.iterator();
		
		Iterator<HybridSiminovData> siminovDatas = jsSiminovDatas.getHybridSiminovDatas();
		while(siminovDatas.hasNext() && datas.hasNext()) {
			
			Map<String, Object> data = datas.next();
			HybridSiminovData siminovData = siminovDatas.next();
			
			processOneToOneRelationship(siminovData);
			processOneToManyRelationship(siminovData);

			processManyToOneRelationship(siminovData, data);
			processManyToManyRelationship(siminovData, data);
			
		}
		
		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
	}

	static HybridSiminovDatas lazyFetch(final DatabaseMappingDescriptor databaseMappingDescriptor, final boolean distinct, final String whereClause, final Iterator<String> columnNames, final Iterator<String> groupBy, final String having, final Iterator<String> orderBy, final String whichOrderBy, final String limit) throws DatabaseException {
		
		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "lazyFetch", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "lazyFetch", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}
		
		return parseData(databaseMappingDescriptor, database.executeFetchQuery(getDatabaseDescriptor(databaseMappingDescriptor.getClassName()), databaseMappingDescriptor, queryBuilder.formSelectQuery(databaseMappingDescriptor.getTableName(), false, whereClause, columnNames, groupBy, having, orderBy, null, limit)));
	
	}
	
	
	/**
	 * Handles Database Begin Transaction Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws DatabaseException If any exception occur while beginning transaction.
	 */
	public void beginTransaction(final String databaseDescriptorName) throws DatabaseException {
		
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseDescriptorName(databaseDescriptorName);
		IDatabase database = databaseBundle.getDatabase();

		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "beginTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
			throw new DeploymentException(DatabaseHandler.class.getName(), "beginTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
		}
		
		database.executeMethod(Constants.SQLITE_DATABASE_BEGIN_TRANSACTION, null);
		
	}

	
	/**
	 * Handles Database Commit Transaction Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws DatabaseException If any error occur while committing transaction. 
	 */
	public void commitTransaction(final String databaseDescriptorName) throws DatabaseException {
		
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseDescriptorName(databaseDescriptorName);
		IDatabase database = databaseBundle.getDatabase();

		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "commitTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
			throw new DeploymentException(DatabaseHandler.class.getName(), "commitTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
		}

		database.executeMethod(Constants.SQLITE_DATABASE_COMMIT_TRANSACTION, null);

	}
	
	
	/**
	 * Handles Database End Transaction Request From Web.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws DatabaseException If any error occur while ending transaction.
	 */
	public void endTransaction(final String databaseDescriptorName) throws DatabaseException {
		
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseDescriptorName(databaseDescriptorName);
		IDatabase database = databaseBundle.getDatabase();

		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "endTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
			throw new DeploymentException(DatabaseHandler.class.getName(), "endTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
		}
		
		try {
			database.executeMethod(Constants.SQLITE_DATABASE_END_TRANSACTION, null);
		} catch(DatabaseException databaseException) {
			Log.loge(DatabaseHandler.class.getName(), "endTransaction", "DatabaseException caught while executing end transaction method, " + databaseException.getMessage());
		}
	}
	
	
	/**
	 * Handles Database Count Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Count Needs To Be Find.
	 * @param distinct Distinct tuples needs to be calculated or not.
	 * @param whereClause Where Clause based on which Count needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 * @return Count Of Tuples.
	 * @throws DatabaseException If any error occur while getting count.
	 */
	public String count(final String className, final String column, final Boolean distinct, final String whereClause, final String[] groupBys, final String having) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(className);
		int count = count(databaseMappingDescriptor, column, distinct, whereClause, groupBys, having);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(count));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "count", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "count", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	
	static int count(final DatabaseMappingDescriptor databaseMappingDescriptor, final String column, final Boolean distinct, final String whereClause, final String[] groupBys, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "count", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "count", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		
		String query = queryBuilder.formCountQuery(databaseMappingDescriptor.getTableName(), null, false, whereClause, null, null);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {
				
				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}
				
			}
		}
		
		return 0;
	}
	

	
	/**
	 * Handles Database Average Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Average Needs To Be Find.
	 * @param whereClause Where Clause based on which Average needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 * @return Average Of Tuples.
	 * @throws DatabaseException If any error occur while getting count.
	 */
	public String avg(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		int avg = avg(databaseMappingDescriptor, columnName, whereClause, groupBy, having);

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(avg));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final int avg(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());
		
		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "avg", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "avg", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		
		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formAvgQuery(databaseMappingDescriptor.getTableName(), columnName, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {

				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}
			
			}
		}
		
		return 0;
		
	}


	/**
	 * Handles Database Sum Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Sum Needs To Be Find.
	 * @param whereClause Where Clause based on which Sum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Sum functions.
	 * @return Sum Of Tuples.
	 * @throws DatabaseException If any error occur while getting sum.
	 */
	public String sum(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		int sum =  sum(databaseMappingDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(sum));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "sum", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "sum", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final int sum(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = hybridResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());

		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "sum", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "sum", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		Collection<String> groupBys =  new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formSumQuery(databaseMappingDescriptor.getTableName(), columnName, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {

				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}

			}
		}
		
		return 0;
		
	}

	
	/**
	 * Handles Database Total Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Total Needs To Be Find.
	 * @param whereClause Where Clause based on which Total needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Total functions.
	 * @return Total Of Tuples.
	 * @throws DatabaseException If any error occur while getting total.
	 */
	public String total(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		int total = total(databaseMappingDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(total));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "total", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "total", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final int total(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());

		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "total", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "total", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formTotalQuery(databaseMappingDescriptor.getTableName(), columnName, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {

				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}

			}
		}
		
		return 0;
		
	}


	/**
	 * Handles Database Minimum Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Minimum Needs To Be Find.
	 * @param whereClause Where Clause based on which Minimum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Minimum functions.
	 * @return Minimum Of Tuples.
	 * @throws DatabaseException If any error occur while getting minimum.
	 */
	public String min(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		int min = min(databaseMappingDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(min));

		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "min", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "min", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final int min(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());

		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "min", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "min", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formMinQuery(databaseMappingDescriptor.getTableName(), columnName, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {

				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}

			}
		}
		
		return 0;
		
	}
	

	/**
	 * Handles Database Maximum Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Maximum Needs To Be Find.
	 * @param whereClause Where Clause based on which Maximum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Maximum functions.
	 * @return Maximum Of Tuples.
	 * @throws DatabaseException If any error occur while getting maximum.
	 */
	public String max(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		int max = max(databaseMappingDescriptor, columnName, whereClause, groupBy, having);

		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(max));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	

	private static final int max(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());

		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "max", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "max", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formMaxQuery(databaseMappingDescriptor.getTableName(), columnName, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {

				Object value = values.next();
				if(value instanceof Integer) {
					return ((Integer) value).intValue();
				} else if(value instanceof Long) {
					return ((Long) value).intValue();
				} else if(value instanceof Float) {
					return ((Float) value).intValue();
				}

			}
		}
		
		return 0;
		
	}
	

	
	/**
	 * Handles Database Group Concat Request From Web.
	 * @param className Web Model Class Name.
	 * @param column Name of Column For Which Group Concat Needs To Be Find.
	 * @param whereClause Where Clause based on which Group Concat needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Group Concat functions.
	 * @return Group Concat Of Tuples.
	 * @throws DatabaseException If any error occur while getting Group Concat.
	 */
	public String groupConcat(final String className, final String columnName, final String delimiter, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		String groupConcat = groupConcat(databaseMappingDescriptor, columnName, delimiter, whereClause, groupBy, having);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(groupConcat);
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "groupConcat", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "groupConcat", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static String groupConcat(final DatabaseMappingDescriptor databaseMappingDescriptor, final String columnName, final String delimiter, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = ormResources.getDatabaseDescriptorBasedOnClassName(databaseMappingDescriptor.getClassName());
		DatabaseBundle databaseBundle = ormResources.getDatabaseBundleBasedOnDatabaseMappingDescriptorClassName(databaseMappingDescriptor.getClassName());

		IDatabase database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.loge(DatabaseHandler.class.getName(), "groupConcat", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "groupConcat", "No Database Instance Found For DATABASE-MAPPING: " + databaseMappingDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		String query = queryBuilder.formGroupConcatQuery(databaseMappingDescriptor.getTableName(), columnName, delimiter, whereClause, groupBys.iterator(), having);

		Iterator<Map<String, Object>> datas = database.executeFetchQuery(databaseDescriptor, databaseMappingDescriptor, query);
		while(datas.hasNext()) {
			Map<String, Object> data = datas.next();
			Collection<Object> parse = data.values();

			Iterator<Object> values = parse.iterator();
			while(values.hasNext()) {
				return ((String) values.next());
			}
		}
		
		return "";
		
	}
	

	
	/**
	 * Handles Database Get Table Name Request From Web.
	 * @param className Web Model Class Name.
	 * @return Table Name.
	 * @throws DatabaseException If any error occur while get table name mapped to web model class name.
	 */
	public String getTableName(final String className) throws DatabaseException {
	
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		String tableName = getTableName(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(tableName));
		
		jsSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	
	private static final String getTableName(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		return databaseMappingDescriptor.getTableName();
	}

	
	/**
	 * Handles Database Get Columns Names Request From Web.
	 * @param className Web Model Class Name.
	 * @return Column Names.
	 * @throws DatabaseException If any error occur while getting column names mapped to web model class name.
	 */
	public String getColumnNames(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		Iterator<String> columnNames = getColumnNames(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();

		while(columnNames.hasNext()) {
			String columnName = columnNames.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			jsSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	
	private static final Iterator<String> getColumnNames(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();

		Collection<String> columnNames = new ArrayList<String>();
		while(columns.hasNext()) {
			columnNames.add(columns.next().getColumnName());
		}

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(columns.next().getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(columns.next().getColumnName());
				}
			}
		}
		

		return columnNames.iterator();
		
	}
	

	
	/**
	 * Handles Database Get Columns Types Request From Web.
	 * @param className Web Model Class Name.
	 * @return Column Types.
	 * @throws DatabaseException If any error occur while getting column types mapped to web model class name.
	 */
	public String getColumnTypes(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		return getColumnType(databaseMappingDescriptor);
		
	}
	
	private static final String getColumnType(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Map<String, Object> columnTypes = new HashMap<String, Object> ();
		Iterator<DatabaseMappingDescriptor.Column> columns = databaseMappingDescriptor.getColumns();
		
		while(columns.hasNext()) {
			Column column = columns.next();
			columnTypes.put(column.getColumnName(), column.getType());
		}
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnTypes.put(column.getColumnName(), column.getType());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnTypes.put(column.getColumnName(), column.getType());
				}
			}
		}


		Collection<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		values.add(columnTypes);
		
		HybridSiminovDatas jsSiminovDatas = parseData(databaseMappingDescriptor, values.iterator());
		
		String returnData = null;
		try {
			returnData = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);		
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getColumnTypes", "SiminovException caught while building json output, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getColumnTypes", "SiminovException caught while building json output, " + siminovException.getMessage());
		}
		
		return returnData;
		
	}
	
	
	/**
	 * Handles Database Get Primary Column Names Request From Web.
	 * @param className Web Model Class Name.
	 * @return Primary Column Names.
	 * @throws DatabaseException If any error occur while get primary column names mapped to web model class name.
	 */
	public String getPrimaryKeys(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		Iterator<String> primaryKeys = getPrimaryKeys(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();

		while(primaryKeys.hasNext()) {
			String columnName = primaryKeys.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			jsSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getPrimaryKeys", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getPrimaryKeys", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final Iterator<String> getPrimaryKeys(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Iterator<Column> columns = databaseMappingDescriptor.getColumns();
		Collection<String> primaryKeys = new ArrayList<String>();

		while(columns.hasNext()) {
			Column column = columns.next();
			
			boolean isPrimary = column.isPrimaryKey();
			if(isPrimary) {
				primaryKeys.add(column.getColumnName());
			}
		}

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					primaryKeys.add(column.getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					primaryKeys.add(column.getColumnName());
				}
			}
		}
		
		return primaryKeys.iterator();
		
	}
	

	/**
	 * Handles Database Get Mandatory Column Names Request From Web.
	 * @param className Web Model Class Name.
	 * @return Mandatory Names.
	 * @throws DatabaseException If any error occur while getting mandatory column names mapped to web model class name.
	 */
	public String getMandatoryFields(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		Iterator<String> mandatoryFields = getMandatoryFields(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();

		while(mandatoryFields.hasNext()) {
			String columnName = mandatoryFields.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			jsSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getMandatoryFields", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getMandatoryFields", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final Iterator<String> getMandatoryFields(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Iterator<Column> columns = databaseMappingDescriptor.getColumns();
		Collection<String> mandatoryFields = new ArrayList<String>();
		
		while(columns.hasNext()) {
			Column column = columns.next();
			
			if(column.isNotNull()) {
				mandatoryFields.add(column.getColumnName());
			}
		}
		

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					if(column.isNotNull()) {
						mandatoryFields.add(column.getColumnName());
					}
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					if(column.isNotNull()) {
						mandatoryFields.add(column.getColumnName());
					}
				}
			}
		}
		
		return mandatoryFields.iterator();
		
	}
	
	
	/**
	 * Handles Database Get Unique Column Names Request From Web.
	 * @param className Web Model Class Name.
	 * @return Unique Column Names.
	 * @throws DatabaseException If any error occur while getting unique column names. 
	 */
	public String getUniqueFields(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		Iterator<String> uniqueFields = getUniqueFields(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();

		while(uniqueFields.hasNext()) {
			String columnName = uniqueFields.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			jsSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getUniqueFields", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getUniqueFields", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final Iterator<String> getUniqueFields(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Iterator<Column> columns = databaseMappingDescriptor.getColumns();
		Collection<String> uniqueFields = new ArrayList<String>();
		
		while(columns.hasNext()) {
			Column column = columns.next();
			
			boolean isUnique = column.isUnique();
			if(isUnique) {
				uniqueFields.add(column.getColumnName());
			}
		}
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {

					boolean isUnique = column.isUnique();
					if(isUnique) {
						uniqueFields.add(column.getColumnName());
					}
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {

					boolean isUnique = column.isUnique();
					if(isUnique) {
						uniqueFields.add(column.getColumnName());
					}
				}
			}
		}
		

		return uniqueFields.iterator();
		
	}
	
	
	/**
	 * Handle Database Get Foreign Column Names Request From Web.
	 * @param className Web Model Class Name.
	 * @return Foreign Column Names.
	 * @throws DatabaseException If any error occur while getting foreign column names.
	 */
	public String getForeignKeys(final String className) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);

		Iterator<String> foreignKeys = getForeignKeys(databaseMappingDescriptor);
		
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();

		while(foreignKeys.hasNext()) {
			String columnName = foreignKeys.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			jsSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataBuilder.jsonBuidler(jsSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "getForeignKeys", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getForeignKeys", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private static final Iterator<String> getForeignKeys(final DatabaseMappingDescriptor databaseMappingDescriptor) throws DatabaseException {
		
		Collection<String> foreignKeys = new LinkedList<String>();
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					foreignKeys.add(column.getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor parentDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			
			Iterator<Column> parentColumns = parentDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					foreignKeys.add(column.getColumnName());
				}
			}
		}
		
		return foreignKeys.iterator();
		
	}
	
	
	private HybridSiminovDatas parseHybridSiminovDatas(String data) throws DatabaseException {
		
		HybridSiminovDataParser jsSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			jsSiminovDataParser = new HybridSiminovDataParser(data);
		} catch(SiminovException siminovException) {
			Log.loge(DatabaseHandler.class.getName(), "parseHybridSiminovDatas", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "parseHybridSiminovDatas", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}

		return jsSiminovDataParser.getDatas();
	}
	
	private static DatabaseDescriptor getDatabaseDescriptor(final String className) throws DatabaseException {
		return hybridResources.getDatabaseDescriptorBasedOnClassName(className);
	}

	private static DatabaseMappingDescriptor getDatabaseMappingDescriptor(final String className) throws DatabaseException {
		return hybridResources.getDatabaseMappingDescriptorBasedOnClassName(className);
	}
	
	/**
		Iterates the provided cursor, and returns tuples in form of actual objects.
	 */
	private static HybridSiminovDatas parseData(final DatabaseMappingDescriptor databaseMappingDescriptor, Iterator<Map<String, Object>> values) throws DatabaseException {
		
		Collection<Map<String, Object>> tuples = new LinkedList<Map<String, Object>>();
		HybridSiminovDatas jsSiminovDatas = new HybridSiminovDatas();
		
		while(values.hasNext()) {
			Map<String, Object> value = values.next();
			
			HybridSiminovData jsSiminovData = new HybridSiminovData();
			jsSiminovData.setDataType(hybridResources.getMappedWebClassName(databaseMappingDescriptor.getClassName()));
			
			Iterator<String> keys = value.keySet().iterator();
			while(keys.hasNext()) {
				
				String columnName = keys.next();
				if(!databaseMappingDescriptor.containsColumnBasedOnColumnName(columnName)) {
					continue;
				}
				
				String variableName = databaseMappingDescriptor.getColumnBasedOnColumnName(columnName).getVariableName();
				
				Object object = value.get(columnName);
				
				HybridSiminovValue jsSiminovValue = new HybridSiminovValue();
				jsSiminovValue.setType(variableName);

				if(object instanceof String) {
					jsSiminovValue.setValue((String) object);
				} else if(object instanceof Long) {
					jsSiminovValue.setValue(((Long) object).toString());
				} else if(object instanceof Float) {
					jsSiminovValue.setValue(((Float) object).toString());
				} else if(object instanceof Blob) {
					jsSiminovValue.setValue(((Blob) object).toString());
				}

				jsSiminovData.addValue(jsSiminovValue);
			}
			
			jsSiminovDatas.addHybridSiminovData(jsSiminovData);
			tuples.add(value);
		}
		
		values = tuples.iterator();
		
		return jsSiminovDatas;
	}

	
	static void processManyToOneRelationship(final HybridSiminovData siminovData, final Collection<String> columnNames, final Collection<Object> columnValues) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(siminovData.getDataType());
		Iterator<Relationship> manyToOneRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		
		while(manyToOneRelationships.hasNext()) {
			Relationship manyToOneRelationship = manyToOneRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToOneRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToOneRelationship.getReferTo());
				manyToOneRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedHybridSiminovData = null;
			Iterator<HybridSiminovData> datas = siminovData.getDatas();
			while(datas.hasNext()) {
				HybridSiminovData data = datas.next();
				String referedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
				
				if(referedClassName.equalsIgnoreCase(referedDatabaseMappingDescriptor.getClassName())) {
					referedHybridSiminovData = data;
					break;
				}
			}

			if(referedHybridSiminovData == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
				throw new DatabaseException(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
			}
			
			processManyToOneRelationship(referedHybridSiminovData, columnNames, columnValues);
			
			Iterator<HybridSiminovValue> jsValues = referedHybridSiminovData.getValues();
			
			Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(jsValues.hasNext()) {
				HybridSiminovValue jsSiminovValue = jsValues.next();
				jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
			}
			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(column.getColumnName());
					columnValues.add(jsSiminovValues.get(column.getVariableName()).getValue());
				}
			}
		}
	}
	
	
	static void processManyToManyRelationship(final HybridSiminovData siminovData, final Collection<String> columnNames, final Collection<Object> columnValues) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(siminovData.getDataType());
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToManyRelationship.getReferTo());
				manyToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedHybridSiminovData = null;
			Iterator<HybridSiminovData> datas = siminovData.getDatas();
			while(datas.hasNext()) {
				HybridSiminovData data = datas.next();
				String referedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
				
				if(referedClassName.equalsIgnoreCase(referedDatabaseMappingDescriptor.getClassName())) {
					referedHybridSiminovData = data;
					break;
				}
			}

			if(referedHybridSiminovData == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
				throw new DatabaseException(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
			}
			
			processManyToManyRelationship(referedHybridSiminovData, columnNames, columnValues);
			
			Iterator<HybridSiminovValue> jsValues = referedHybridSiminovData.getValues();
			
			Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(jsValues.hasNext()) {
				HybridSiminovValue jsSiminovValue = jsValues.next();
				jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
			}
			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(column.getColumnName());
					columnValues.add(jsSiminovValues.get(column.getVariableName()).getValue());
				}
			}
		}
	}
	
	
	static void processManyToOneRelationship(final HybridSiminovData siminovData, final StringBuilder whereClause) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(siminovData.getDataType());
		Iterator<Relationship> manyToOneRelationships = databaseMappingDescriptor.getManyToOneRelationships();
		
		while(manyToOneRelationships.hasNext()) {
			Relationship manyToOneRelationship = manyToOneRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToOneRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToOneRelationship.getReferTo());
				manyToOneRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedHybridSiminovData = null;
			Iterator<HybridSiminovData> datas = siminovData.getDatas();
			while(datas.hasNext()) {
				HybridSiminovData data = datas.next();
				String referedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
				
				if(referedClassName.equalsIgnoreCase(referedDatabaseMappingDescriptor.getClassName())) {
					referedHybridSiminovData = data;
					break;
				}
			}

			if(referedHybridSiminovData == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
				throw new DatabaseException(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
			}
			
			processManyToOneRelationship(referedHybridSiminovData, whereClause);
			
			Iterator<HybridSiminovValue> jsValues = referedHybridSiminovData.getValues();
			
			Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(jsValues.hasNext()) {
				HybridSiminovValue jsSiminovValue = jsValues.next();
				jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
			}
			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					String columnValue = jsSiminovValues.get(column.getVariableName()).getValue();
					if(whereClause.length() <= 0) {
						whereClause.append(column.getColumnName() + "= '" + columnValue + "'");
					} else {
						whereClause.append(" AND " + column.getColumnName() + "= '" + columnValue + "'");
					}
				}
			}
		}
	}
	
	
	static void processManyToManyRelationship(final HybridSiminovData siminovData, final StringBuilder whereClause) throws DatabaseException {
		
		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(siminovData.getDataType());
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToManyRelationship.getReferTo());
				manyToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedHybridSiminovData = null;
			Iterator<HybridSiminovData> datas = siminovData.getDatas();
			while(datas.hasNext()) {
				HybridSiminovData data = datas.next();
				String referedClassName = hybridResources.getMappedNativeClassName(data.getDataType());
				
				if(referedClassName.equalsIgnoreCase(referedDatabaseMappingDescriptor.getClassName())) {
					referedHybridSiminovData = data;
					break;
				}
			}

			if(referedHybridSiminovData == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
			}
			
			processManyToManyRelationship(referedHybridSiminovData, whereClause);
			
			Iterator<HybridSiminovValue> jsValues = siminovData.getValues();
			
			Map<String, HybridSiminovValue> jsSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(jsValues.hasNext()) {
				HybridSiminovValue jsSiminovValue = jsValues.next();
				jsSiminovValues.put(jsSiminovValue.getType(), jsSiminovValue);
			}
			
			Iterator<Column> parentColumns = referedDatabaseMappingDescriptor.getColumns();
			while(parentColumns.hasNext()) {
				Column column = parentColumns.next();
				
				boolean isPrimary = column.isPrimaryKey();
				if(isPrimary) {
					String columnValue = jsSiminovValues.get(column.getVariableName()).getValue();
					if(whereClause.length() <= 0) {
						whereClause.append(column.getColumnName() + "= '" + columnValue + "'");
					} else {
						whereClause.append(" AND " + column.getColumnName() + "= '" + columnValue + "'");
					}
				}
			}
		}
	}
	
	
	static void processOneToOneRelationship(final HybridSiminovData jsSiminovData) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(jsSiminovData.getDataType());
		Iterator<DatabaseMappingDescriptor.Relationship> oneToOneRelationships = databaseMappingDescriptor.getOneToOneRelationships();
		
		while(oneToOneRelationships.hasNext()) {
			
			DatabaseMappingDescriptor.Relationship oneToOneRelationship = oneToOneRelationships.next();

			boolean isLoad = oneToOneRelationship.isLoad();
			if(!isLoad) {
				continue;
			}

			
			StringBuilder whereClause = new StringBuilder();
			Iterator<String> foreignKeys = getPrimaryKeys(databaseMappingDescriptor);
			while(foreignKeys.hasNext()) {
				String foreignKey = foreignKeys.next();
				Object columnValue = null;
				
				HybridSiminovValue value = jsSiminovData.getValueBasedOnType(foreignKey);
				columnValue = value.getValue();

				if(whereClause.length() <= 0) {
					whereClause.append(foreignKey + "='" + columnValue.toString() + "'"); 
				} else {
					whereClause.append(", " + foreignKey + "='" + columnValue.toString() + "'");  
				}
			}

			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToOneRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToOneRelationship.getReferTo());
				oneToOneRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			HybridSiminovDatas referedObject = lazyFetch(referedDatabaseMappingDescriptor, false, whereClause.toString(), null, null, null, null, null, null);
			Iterator<HybridSiminovData> siminovDatas = referedObject.getHybridSiminovDatas();
			
			while(siminovDatas.hasNext()) {
				HybridSiminovData siminovData = siminovDatas.next();
				jsSiminovData.addData(siminovData);
			}
		}
		
	}


	static void processOneToManyRelationship(final HybridSiminovData jsSiminovData) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(jsSiminovData.getDataType());
		Iterator<DatabaseMappingDescriptor.Relationship> oneToManyRelationships = databaseMappingDescriptor.getOneToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			
			DatabaseMappingDescriptor.Relationship oneToManyRelationship = oneToManyRelationships.next();

			boolean isLoad = oneToManyRelationship.isLoad();
			if(!isLoad) {
				continue;
			}

			
			StringBuilder whereClause = new StringBuilder();
			Iterator<String> foreignKeys = getPrimaryKeys(databaseMappingDescriptor);
			while(foreignKeys.hasNext()) {
				String foreignKey = foreignKeys.next();
				Column column = databaseMappingDescriptor.getColumnBasedOnColumnName(foreignKey);
				
				Object columnValue = null;
				
				HybridSiminovValue value = jsSiminovData.getValueBasedOnType(column.getVariableName());
				columnValue = value.getValue();

				if(whereClause.length() <= 0) {
					whereClause.append(foreignKey + "='" + columnValue.toString() + "'"); 
				} else {
					whereClause.append(", " + foreignKey + "='" + columnValue.toString() + "'");  
				}
			}

			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = oneToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			
			HybridSiminovDatas referedObject = lazyFetch(referedDatabaseMappingDescriptor, false, whereClause.toString(), null, null, null, null, null, null);
			Iterator<HybridSiminovData> siminovDatas = referedObject.getHybridSiminovDatas();
			
			while(siminovDatas.hasNext()) {
				HybridSiminovData siminovData = siminovDatas.next();
				jsSiminovData.addData(siminovData);
			}
		}
	}
	
	
	static void processManyToOneRelationship(final HybridSiminovData jsSiminovData, Map<String, Object> data) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(jsSiminovData.getDataType());
		Iterator<Relationship> manyToOneRelationships = databaseMappingDescriptor.getManyToOneRelationships();

		while(manyToOneRelationships.hasNext()) {
			Relationship manyToOneRelationship = manyToOneRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToOneRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToOneRelationship.getReferTo());
				manyToOneRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedObject = new HybridSiminovData();
			referedObject.setDataType(hybridResources.getMappedWebClassName(referedDatabaseMappingDescriptor.getClassName()));
			
			processManyToOneRelationship(referedObject, data);

			if(manyToOneRelationship.isLoad()) {

				StringBuilder whereClause = new StringBuilder();

				Iterator<String> foreignKeys = getPrimaryKeys(referedDatabaseMappingDescriptor);
				while(foreignKeys.hasNext()) {
					String foreignKey = foreignKeys.next();
					Column column = referedDatabaseMappingDescriptor.getColumnBasedOnColumnName(foreignKey);
					Object columnValue = data.get(column.getColumnName());

					if(whereClause.length() <= 0) {
						whereClause.append(foreignKey + "='" + columnValue.toString() + "'"); 
					} else {
						whereClause.append(" AND " + foreignKey + "='" + columnValue.toString() + "'");  
					}
				}
				
				HybridSiminovDatas fetchedObjects = lazyFetch(referedDatabaseMappingDescriptor, false, whereClause.toString(), null, null, null, null, null, null);
				referedObject = fetchedObjects.getHybridSiminovDatas().next();
				
			} else {
				Iterator<String> foreignKeys = getPrimaryKeys(referedDatabaseMappingDescriptor);
				while(foreignKeys.hasNext()) {
					String foreignKey = foreignKeys.next();
					Column column = referedDatabaseMappingDescriptor.getColumnBasedOnColumnName(foreignKey);

					Object columnValue = data.get(column.getColumnName());
					if(columnValue == null) {
						continue;
					}
					
					HybridSiminovValue value = new HybridSiminovValue();
					value.setType(foreignKey);
					value.setValue(columnValue.toString());
					
				}
			}
			

			if(referedObject == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
				throw new DatabaseException(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
			}

			jsSiminovData.addData(referedObject);
		}
	}
	
	
	static void processManyToManyRelationship(final HybridSiminovData jsSiminovData, Map<String, Object> data) throws DatabaseException {

		DatabaseMappingDescriptor databaseMappingDescriptor = getDatabaseMappingDescriptor(jsSiminovData.getDataType());
		Iterator<Relationship> manyToManyRelationships = databaseMappingDescriptor.getManyToManyRelationships();

		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			DatabaseMappingDescriptor referedDatabaseMappingDescriptor = manyToManyRelationship.getReferedDatabaseMappingDescriptor();
			if(referedDatabaseMappingDescriptor == null) {
				referedDatabaseMappingDescriptor = getDatabaseMappingDescriptor(manyToManyRelationship.getReferTo());
				manyToManyRelationship.setReferedDatabaseMappingDescriptor(referedDatabaseMappingDescriptor);
			}

			HybridSiminovData referedObject = new HybridSiminovData();
			referedObject.setDataType(hybridResources.getMappedWebClassName(referedDatabaseMappingDescriptor.getClassName()));
			
			processManyToManyRelationship(referedObject, data);

			if(manyToManyRelationship.isLoad()) {

				StringBuilder whereClause = new StringBuilder();

				Iterator<String> foreignKeys = getPrimaryKeys(referedDatabaseMappingDescriptor);
				while(foreignKeys.hasNext()) {
					String foreignKey = foreignKeys.next();
					Column column = referedDatabaseMappingDescriptor.getColumnBasedOnColumnName(foreignKey);
					Object columnValue = data.get(column.getColumnName());

					if(whereClause.length() <= 0) {
						whereClause.append(foreignKey + "='" + columnValue.toString() + "'"); 
					} else {
						whereClause.append(" AND " + foreignKey + "='" + columnValue.toString() + "'");  
					}
				}
				
				HybridSiminovDatas fetchedObjects = lazyFetch(referedDatabaseMappingDescriptor, false, whereClause.toString(), null, null, null, null, null, null);
				referedObject = fetchedObjects.getHybridSiminovDatas().next();
				
			} else {
				Iterator<String> foreignKeys = getPrimaryKeys(referedDatabaseMappingDescriptor);
				while(foreignKeys.hasNext()) {
					String foreignKey = foreignKeys.next();
					Column column = referedDatabaseMappingDescriptor.getColumnBasedOnColumnName(foreignKey);

					Object columnValue = data.get(column.getColumnName());
					if(columnValue == null) {
						continue;
					}
					
					HybridSiminovValue value = new HybridSiminovValue();
					value.setType(foreignKey);
					value.setValue(columnValue.toString());
					
				}
			}
			

			if(referedObject == null) {
				Log.loge(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
				throw new DatabaseException(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
			}

			jsSiminovData.addData(referedObject);
		}
	}
	
}
