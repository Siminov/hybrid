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



package siminov.hybrid.adapter.handlers;

import java.net.URLDecoder;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import siminov.core.Constants;
import siminov.core.database.DatabaseBundle;
import siminov.core.database.design.IDatabaseImpl;
import siminov.core.database.design.IQueryBuilder;
import siminov.core.exception.DatabaseException;
import siminov.core.exception.DeploymentException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.model.DatabaseDescriptor;
import siminov.core.model.EntityDescriptor;
import siminov.core.model.EntityDescriptor.Attribute;
import siminov.core.model.EntityDescriptor.Relationship;
import siminov.core.resource.ResourceManager;
import siminov.hybrid.adapter.IAdapter;
import siminov.hybrid.adapter.constants.HybridAdapter;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.reader.HybridSiminovDataReader;
import siminov.hybrid.writter.HybridSiminovDataWritter;


/**
 * It handles all request related to database.
 * LIKE: save, update, saveOrUpdate, delete.
 */
public class DatabaseHandler implements IAdapter {

	private static ResourceManager coreResourceManager = ResourceManager.getInstance();
	private static siminov.hybrid.resource.ResourceManager hybridResourceManager = siminov.hybrid.resource.ResourceManager.getInstance();

	
	/**
	 * Handles Database Save Request From Hybrid.
	 * @param data Data Need To Be Save In Database.
	 * @throws DatabaseException If any exception occur while saving data in Database.
	 */
	public void save(final String data) throws DatabaseException {

		HybridSiminovDatas hybridSiminovDatas = parseHybridSiminovDatas(data);
		saveDatas(hybridSiminovDatas);
		
	}
	
	private void saveDatas(HybridSiminovDatas hybridSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> hybridDatas = hybridSiminovDatas.getHybridSiminovDatas();
		while(hybridDatas.hasNext()) {
			saveData(hybridDatas.next(), null);
		}
		
	}

	private void saveData(HybridSiminovData hybridSiminovData, HybridSiminovData parentHybridSiminovData) throws DatabaseException {
		
		String className = hybridSiminovData.getDataType();
		Iterator<HybridSiminovValue> hybridValues = hybridSiminovData.getValues();
		
		Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(hybridValues.hasNext()) {
			HybridSiminovValue hybridSiminovValue = hybridValues.next();
			hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		}
		
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(className);
		
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		String tableName = entityDescriptor.getTableName();
		
		Collection<String> columnNames = new LinkedList<String>();
		Collection<Object> columnValues = new LinkedList<Object>();

		Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			String columnName = attribute.getColumnName();
			String columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
			
			columnNames.add(columnName);
			columnValues.add(columnValue);
			
		}

		
		RelationshipHelper.processRelationship(hybridSiminovData, parentHybridSiminovData, columnNames, columnValues);

		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_SAVE_BIND_QUERY_TABLE_NAME_PARAMETER, tableName);
		parameters.put(IQueryBuilder.FORM_SAVE_BIND_QUERY_COLUMN_NAMES_PARAMETER, columnNames.iterator());

		String query = queryBuilder.formSaveBindQuery(parameters);
		database.executeBindQuery(databaseDescriptor, entityDescriptor, query, columnValues.iterator());

		

		/*
		 * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
		 */
		Iterator<EntityDescriptor.Relationship> relationships = entityDescriptor.getRelationships();
		while(relationships.hasNext()) {
			EntityDescriptor.Relationship relationship = relationships.next();
			
			boolean isLoad = relationship.isLoad();
			if(!isLoad) {
				continue;
			}
			
			String relationshipType = relationship.getType();
			if(relationshipType == null || relationshipType.length() <= 0) {
				continue;
			}
			
			if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_ONE)) {
				
				HybridSiminovData referedData = null;
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						referedData = data;
						break;
					}
				}
				

				if(referedData == null) {
					continue;
				}

				saveData(referedData, null);
			} else if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(hybridSiminovData);
						saveData(data, null);
					}
				}
				
			} else if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_MANY_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(hybridSiminovData);
						saveData(data, null);
					}
				}
			}
		}
	}

	
	
	/**
	 * Handles Database Update Request From Hybrid.
	 * @param data Data Need To Be Update In Database.
	 * @throws DatabaseException If any exception occur while updating data in Database.
	 */
	public void update(String data) throws DatabaseException {
		
		HybridSiminovDatas hybridSiminovDatas = parseHybridSiminovDatas(data);
		updateDatas(hybridSiminovDatas);
		
	}

	private void updateDatas(HybridSiminovDatas hybridSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> hybridDatas = hybridSiminovDatas.getHybridSiminovDatas();
		while(hybridDatas.hasNext()) {
			updateData(hybridDatas.next(), null);
		}
		
	}
	
	private void updateData(HybridSiminovData hybridSiminovData, HybridSiminovData parentHybridSiminovData) throws DatabaseException {
		
		String className = hybridSiminovData.getDataType();
		Iterator<HybridSiminovValue> hybridSiminovValue = hybridSiminovData.getValues();
		
		Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(hybridSiminovValue.hasNext()) {
			HybridSiminovValue hybridValue = hybridSiminovValue.next();
			hybridSiminovValues.put(hybridValue.getType(), hybridValue);
		}
		
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(className);

		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		
		StringBuilder whereClause = new StringBuilder();
		String tableName = entityDescriptor.getTableName();
		
		Collection<String> columnNames = new LinkedList<String>();
		Collection<Object> columnValues = new LinkedList<Object>();

		Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			String columnName = attribute.getColumnName();
			Object columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
			
			columnNames.add(columnName);
			columnValues.add(columnValue);
			
			if(attribute.isPrimaryKey()) {
				if(whereClause.length() == 0) {
					whereClause.append(columnName + "= '" + columnValue + "'");
				} else {
					whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
				}
			}
		}

		RelationshipHelper.processRelationship(hybridSiminovData, parentHybridSiminovData, whereClause);
		RelationshipHelper.processRelationship(hybridSiminovData, parentHybridSiminovData, columnNames, columnValues);
		

		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_UPDATE_BIND_QUERY_TABLE_NAME_PARAMETER, tableName);
		parameters.put(IQueryBuilder.FORM_UPDATE_BIND_QUERY_COLUMN_NAMES_PARAMETER, columnNames.iterator());
		parameters.put(IQueryBuilder.FORM_UPDATE_BIND_QUERY_WHERE_CLAUSE_PARAMETER, whereClause.toString());

		
		String query = queryBuilder.formUpdateBindQuery(parameters);

		Iterator<Object> values = columnValues.iterator();
		database.executeBindQuery(databaseDescriptor, entityDescriptor, query, values);

		
		
		/*
		 * 5. Check for relationship's if any, IF EXISTS: process it, ELSE: return all objects.
		 */
		Iterator<EntityDescriptor.Relationship> relationships = entityDescriptor.getRelationships();
		while(relationships.hasNext()) {
			EntityDescriptor.Relationship relationship = relationships.next();
			
			boolean isLoad = relationship.isLoad();
			if(!isLoad) {
				continue;
			}
			
			String relationshipType = relationship.getType();
			if(relationshipType == null || relationshipType.length() <= 0) {
				continue;
			}
			
			if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_ONE)) {
				
				HybridSiminovData referedData = null;
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						referedData = data;
						break;
					}
				}
				

				if(referedData == null) {
					continue;
				}

				updateData(referedData, null);
			} else if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_ONE_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(hybridSiminovData);
						updateData(data, null);
					}
				}
				
			} else if(relationshipType.equalsIgnoreCase(Constants.ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE_MANY_TO_MANY)) {
				
				Iterator<HybridSiminovData> datas = hybridSiminovData.getDatas();
				while(datas.hasNext()) {
					
					HybridSiminovData data = datas.next();
					
					String mappedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
					if(mappedClassName.equalsIgnoreCase(relationship.getReferTo())) {
						data.addData(hybridSiminovData);
						updateData(data, null);
					}
				}
			}
		}
		
	}
	

	/**
	 * Handles Database Save Or Update Request From Hybrid.
	 * @param data Data Need To Be Save Or Update In Database.
	 * @throws DatabaseException If any exception occur while saving or updating data in Database.
	 */
	public void saveOrUpdate(String data) throws DatabaseException {

		HybridSiminovDatas hybridSiminovDatas = parseHybridSiminovDatas(data);
		saveOrUpdateDatas(hybridSiminovDatas);
		
	}
	
	private void saveOrUpdateDatas(HybridSiminovDatas hybridSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> hybridDatas = hybridSiminovDatas.getHybridSiminovDatas();
		
		while(hybridDatas.hasNext()) {
			HybridSiminovData hybridSiminovData = hybridDatas.next();
			saveOrUpdateData(hybridSiminovData, null);
		}
	}

	private void saveOrUpdateData(HybridSiminovData hybridSiminovData, HybridSiminovData parentHybridSiminovData) throws DatabaseException {

		String className = hybridSiminovData.getDataType();
		Iterator<HybridSiminovValue> hybridValues = hybridSiminovData.getValues();
		
		Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		while(hybridValues.hasNext()) {
			HybridSiminovValue hybridSiminovValue = hybridValues.next();
			hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		}
		
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		
		StringBuilder whereClause = new StringBuilder();
		Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			if(attribute.isPrimaryKey()) {

				String columnName = attribute.getColumnName();
				Object columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();

				if(whereClause.length() <= 0) {
					whereClause.append(columnName + "= '" + columnValue + "'");
				} else {
					whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
				}
			}
			
		}

		
		RelationshipHelper.processRelationship(hybridSiminovData, parentHybridSiminovData, whereClause);
		if(whereClause == null || whereClause.length() <= 0) {
			saveData(hybridSiminovData, parentHybridSiminovData);
			return;
		}

		
		/*
		 * 4. IF EXISTS: call update method, ELSE: call save method.
		 */
		int count = countRaw(entityDescriptor, null, false, whereClause.toString(), null, null);
		if(count <= 0) {
			saveData(hybridSiminovData, parentHybridSiminovData);
		} else {
			updateData(hybridSiminovData, parentHybridSiminovData);
		}
	}
	

	
	/**
	 * Handles Database Delete Request From Hybrid.
	 * @param className Hybrid Model Class Name of which delete request is sent.
	 * @param whereClause Based on which tuple will be deleted from table.
	 * @param data Data Need To Be Delete In Database.
	 * @throws DatabaseException If any exception occur while deleting data in Database.
	 */
	public void delete(final String className, final String whereClause, final String data) throws DatabaseException {
		
		HybridSiminovDatas hybridSiminovDatas = null;
		
		if(data != null && data.length() > 0) {
			hybridSiminovDatas = parseHybridSiminovDatas(data);			
		
			deleteDatas(hybridSiminovDatas);
			return;
		}
		
		deleteData(className, null, whereClause);
	}

	private void deleteDatas(final HybridSiminovDatas hybridSiminovDatas) throws DatabaseException {
		
		Iterator<HybridSiminovData> hybridDatas = hybridSiminovDatas.getHybridSiminovDatas();
		
		while(hybridDatas.hasNext()) {
			HybridSiminovData hybridSiminovData = hybridDatas.next();
			
			String className = hybridSiminovData.getDataType();
			Iterator<HybridSiminovValue> hybridValues = hybridSiminovData.getValues();
			
			Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
			while(hybridValues.hasNext()) {
				HybridSiminovValue hybridSiminovValue = hybridValues.next();
				hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
			}
			
			EntityDescriptor entityDescriptor = getEntityDescriptor(className);
			
			StringBuilder whereClause = new StringBuilder();
			
			Collection<String> columnNames = new LinkedList<String>();
			Collection<Object> columnValues = new LinkedList<Object>();
	
			Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();
			while(attributes.hasNext()) {
				Attribute attribute = attributes.next();
				
				String columnName = attribute.getColumnName();
				Object columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
				
				columnNames.add(columnName);
				columnValues.add(columnValue);
				
				if(attribute.isPrimaryKey()) {
					if(whereClause.length() == 0) {
						whereClause.append(columnName + "= '" + columnValue + "'");
					} else {
						whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
					}
				}
			}
	
			
			RelationshipHelper.processRelationship(hybridSiminovData, null, whereClause);
			deleteData(hybridSiminovData.getDataType(), null, whereClause.toString());
		}
	}

	private void deleteData(String className, HybridSiminovDatas parentHybridSiminovData, String whereClause) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(className);
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(whereClause == null) {
			whereClause = new String();
		}
		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_DELETE_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_DELETE_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		
		
		String query = queryBuilder.formDeleteQuery(parameters);
		database.executeQuery(databaseDescriptor, entityDescriptor, query);
	}
	

	/**
	 * Handles Database Select Request From Hybrid.
	 * @param className Hybrid Model Function Name.
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
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		HybridSiminovDatas hybridSiminovDatas = selectDatas(entityDescriptor, distinct, whereClause, columnNames, groupBy, havingClause, orderBy, whichOrderBy, limit);
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
	}

	
	private static HybridSiminovDatas selectDatas(final EntityDescriptor entityDescriptor, final Boolean distinct, final String whereClause, final String[] columnNames, final String[] groupBy, final String havingClause, final String[] orderBy, final String whichOrderBy, final String limit) throws DatabaseException {

		String className = entityDescriptor.getClassName();
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(className);
		
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "select", "No Database Instance Found For ENTITY-DESCRIPTOR: " + className);
			throw new DeploymentException(DatabaseHandler.class.getName(), "select", "No Database Instance Found For ENTITY-DESCRIPTOR: " + className);
		}
		
		String whereCondition = "";
		if(whereClause != null && whereClause.length() > 0) {
			whereCondition = URLDecoder.decode(whereClause);
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

		
		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_DISTINCT_PARAMETER, false);
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_WHERE_CLAUSE_PARAMETER, whereCondition);
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_COLUMN_NAMES_PARAMETER, columnNameCollection.iterator());
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_GROUP_BYS_PARAMETER, groupByCollection.iterator());
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_HAVING_PARAMETER, havingClause);
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_ORDER_BYS_PARAMETER, orderByCollection.iterator());
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_WHICH_ORDER_BY_PARAMETER, null);
		parameters.put(IQueryBuilder.FORM_SELECT_QUERY_LIMIT_PARAMETER, limit);

		
		Iterator<Map<String, Object>> datas = database.executeSelectQuery(getDatabaseDescriptor(className), entityDescriptor, queryBuilder.formSelectQuery(parameters));
		Collection<Map<String, Object>> datasBundle = new LinkedList<Map<String,Object>>();
		while(datas.hasNext()) {
			datasBundle.add(datas.next());
		}

		HybridSiminovDatas hybridSiminovDatas = parseData(entityDescriptor, datasBundle.iterator());
		datas = datasBundle.iterator();
		
		Iterator<HybridSiminovData> siminovDatas = hybridSiminovDatas.getHybridSiminovDatas();
		while(siminovDatas.hasNext() && datas.hasNext()) {
			
			Map<String, Object> data = datas.next();
			HybridSiminovData siminovData = siminovDatas.next();
			
			RelationshipHelper.processRelationship(siminovData, null);
			RelationshipHelper.processRelationship(siminovData, null, data);
		}
		

		return hybridSiminovDatas;
	}

		
	
	public String selectManual(final String className, final String query) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(className);

		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		IDatabaseImpl database = databaseBundle.getDatabase();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "select", "No Database Instance Found For ENTITY-DESCRIPTOR: " + className);
			throw new DeploymentException(DatabaseHandler.class.getName(), "select", "No Database Instance Found For ENTITY-DESCRIPTOR: " + className);
		}
		
		
		Iterator<Map<String, Object>> datas = database.executeSelectQuery(getDatabaseDescriptor(className), entityDescriptor, query);
		Collection<Map<String, Object>> datasBundle = new LinkedList<Map<String,Object>>();
		while(datas.hasNext()) {
			datasBundle.add(datas.next());
		}

		HybridSiminovDatas hybridSiminovDatas = parseData(entityDescriptor, datasBundle.iterator());
		datas = datasBundle.iterator();
		
		Iterator<HybridSiminovData> siminovDatas = hybridSiminovDatas.getHybridSiminovDatas();
		while(siminovDatas.hasNext() && datas.hasNext()) {
			
			Map<String, Object> data = datas.next();
			HybridSiminovData siminovData = siminovDatas.next();
			
			RelationshipHelper.processRelationship(siminovData, null);
			RelationshipHelper.processRelationship(siminovData, null, data);
		}
		
		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "select", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
	}
	
	
	/**
	 * Handles Database Begin Transaction Request From Hybrid.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws DatabaseException If any exception occur while beginning transaction.
	 */
	public void beginTransaction(final String databaseDescriptorName) throws DatabaseException {
		
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptorName);
		IDatabaseImpl database = databaseBundle.getDatabase();

		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "beginTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
			throw new DeploymentException(DatabaseHandler.class.getName(), "beginTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
		}
		
		database.executeMethod(Constants.SQLITE_DATABASE_BEGIN_TRANSACTION, null);
		
	}

	
	public void beginTransactionAsync(String databaseDescriptorName, String data) throws DatabaseException {
		
		data = URLDecoder.decode(data);

		beginTransaction(databaseDescriptorName);
		
		String[] splitStrings = data.split(siminov.hybrid.Constants.BEGIN_TRANSACTION_ASYNC_DELIMITER_WITHOUT_SPECIAL_CHARS);
		for(int i = 0;i < splitStrings.length;i++) {
			
			if(splitStrings[i].length() <= 0) {
				continue;
			}
			
			String request = siminov.hybrid.Constants.BEGIN_TRANSACTION_ASYNC_DELIMITER + splitStrings[i];
			HybridSiminovDatas hybridSiminovDatas = parseHybridSiminovDatas(request);
			
			HybridSiminovData hybridAdapter = hybridSiminovDatas.getHybridSiminovDataBasedOnDataType(HybridAdapter.ADAPTER);
			if(hybridAdapter == null) {
				continue;
			}
			
			HybridSiminovValue hybridRequestId = hybridAdapter.getValueBasedOnType(HybridAdapter.REQUEST_ID);
			HybridSiminovValue hybridAdapterName = hybridAdapter.getValueBasedOnType(HybridAdapter.ADAPTER_NAME);
			HybridSiminovValue hybridHandlerName = hybridAdapter.getValueBasedOnType(HybridAdapter.HANDLER_NAME);
			
			HybridSiminovValue hybridParameters = hybridAdapter.getValueBasedOnType(HybridAdapter.PARAMETERS);

			String requestId = hybridRequestId.getValue();
			
			String adapterName = hybridAdapterName.getValue();
			String handlerName = hybridHandlerName.getValue();
			
			String parameters = hybridParameters.getValue();
			
			HybridSiminovDatas hybridSiminovParameters = parseHybridSiminovDatas(parameters);
			
			if(!adapterName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER)) {
				continue;
			}
			
			if(handlerName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER_SAVE_HANDLER)) {
				saveDatas(hybridSiminovParameters);
			} else if(handlerName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER_UPDATE_HANDLER)) {
				updateDatas(hybridSiminovParameters);
			} else if(handlerName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER_SAVE_OR_UPDATE_HANDLER)) {
				saveOrUpdateDatas(hybridSiminovParameters);
			} else if(handlerName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER_DELETE_HANDLER)) {
				
			} else if(handlerName.equalsIgnoreCase(siminov.hybrid.Constants.DATABASE_ADAPTER_SELECT_HANDLER)) {
				
			}
		}
		
		
		commitTransaction(databaseDescriptorName);
	}
	
	/**
	 * Handles Database Commit Transaction Request From Hybrid.
	 * @param databaseDescriptorName Name of Database Descriptor.
	 * @throws DatabaseException If any error occur while committing transaction. 
	 */
	public void commitTransaction(final String databaseDescriptorName) throws DatabaseException {
		
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptorName);
		IDatabaseImpl database = databaseBundle.getDatabase();

		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "commitTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
			throw new DeploymentException(DatabaseHandler.class.getName(), "commitTransaction", "No Database Instance Found For CLASS: " + databaseDescriptorName);
		}

		database.executeMethod(Constants.SQLITE_DATABASE_COMMIT_TRANSACTION, null);

		try {
			database.executeMethod(Constants.SQLITE_DATABASE_END_TRANSACTION, null);
		} catch(DatabaseException databaseException) {
			Log.error(DatabaseHandler.class.getName(), "endTransaction", "DatabaseException caught while executing end transaction method, " + databaseException.getMessage());
		}
	}

	
	/**
	 * Handles Database Count Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Count Needs To Be Find.
	 * @param distinct Distinct tuples needs to be calculated or not.
	 * @param whereClause Where Clause based on which Count needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 * @return Count Of Tuples.
	 * @throws DatabaseException If any error occur while getting count.
	 */
	public String count(final String className, final String column, final Boolean distinct, final String whereClause, final String[] groupBys, final String having) throws DatabaseException {

		EntityDescriptor entityDescriptor = getEntityDescriptor(className);
		int count = countRaw(entityDescriptor, column, distinct, whereClause, groupBys, having);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(count));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "count", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "count", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	
	private int countRaw(final EntityDescriptor entityDescriptor, final String column, final Boolean distinct, final String whereClause, final String[] groupBys, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "count", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "count", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		
		/*
		 * Add Parameters
		 */
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_COLUMN_PARAMETER, null);
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_DISTINCT_PARAMETER, false);
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_GROUP_BYS_PARAMETER, null);
		parameters.put(IQueryBuilder.FORM_COUNT_QUERY_HAVING_PARAMETER, null);
		
		
		String query = queryBuilder.formCountQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Average Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Average Needs To Be Find.
	 * @param whereClause Where Clause based on which Average needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with aggregate functions.
	 * @return Average Of Tuples.
	 * @throws DatabaseException If any error occur while getting count.
	 */
	public String avg(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		int avg = avgRaw(entityDescriptor, columnName, whereClause, groupBy, having);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(avg));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private int avgRaw(final EntityDescriptor entityDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());
		
		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "avg", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "avg", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		
		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		

		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_AVG_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_AVG_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_AVG_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_AVG_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_AVG_QUERY_HAVING_PARAMETER, having);

		
		String query = queryBuilder.formAvgQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Sum Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Sum Needs To Be Find.
	 * @param whereClause Where Clause based on which Sum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Sum functions.
	 * @return Sum Of Tuples.
	 * @throws DatabaseException If any error occur while getting sum.
	 */
	public String sum(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		int sum =  sumRaw(entityDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(sum));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "sum", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "sum", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private int sumRaw(final EntityDescriptor entityDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());

		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "sum", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "sum", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		Collection<String> groupBys =  new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_SUM_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_SUM_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_SUM_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_SUM_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_SUM_QUERY_HAVING_PARAMETER, having);
		
		
		String query = queryBuilder.formSumQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Total Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Total Needs To Be Find.
	 * @param whereClause Where Clause based on which Total needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Total functions.
	 * @return Total Of Tuples.
	 * @throws DatabaseException If any error occur while getting total.
	 */
	public String total(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		int total = totalRaw(entityDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(total));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "total", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "total", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private int totalRaw(final EntityDescriptor entityDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());

		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "total", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "total", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_TOTAL_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_TOTAL_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_TOTAL_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_TOTAL_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_TOTAL_QUERY_HAVING_PARAMETER, having);
		
		
		String query = queryBuilder.formTotalQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Minimum Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Minimum Needs To Be Find.
	 * @param whereClause Where Clause based on which Minimum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Minimum functions.
	 * @return Minimum Of Tuples.
	 * @throws DatabaseException If any error occur while getting minimum.
	 */
	public String min(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		int min = minRaw(entityDescriptor, columnName, whereClause, groupBy, having);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(min));

		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "min", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "min", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private int minRaw(final EntityDescriptor entityDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());

		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "min", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "min", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		
		/*
		 * Add Parameters
		 */
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_MIN_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_MIN_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_MIN_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_MIN_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_MIN_QUERY_HAVING_PARAMETER, having);
		

		String query = queryBuilder.formMinQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Maximum Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Maximum Needs To Be Find.
	 * @param whereClause Where Clause based on which Maximum needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Maximum functions.
	 * @return Maximum Of Tuples.
	 * @throws DatabaseException If any error occur while getting maximum.
	 */
	public String max(final String className, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		int max = maxRaw(entityDescriptor, columnName, whereClause, groupBy, having);

		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(max));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "avg", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	

	private int maxRaw(final EntityDescriptor entityDescriptor, final String columnName, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());

		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "max", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "max", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		
		/*
		 * Add Parameters
		 */
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_MAX_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_MAX_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_MAX_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_MAX_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_MAX_QUERY_HAVING_PARAMETER, having);
		

		String query = queryBuilder.formMaxQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Group Concat Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @param column Name of Column For Which Group Concat Needs To Be Find.
	 * @param whereClause Where Clause based on which Group Concat needs to be found.
	 * @param groupBys Used to specify GROUP BY statement in conjunction with the aggregate functions to group the result-set by one or more columns.
	 * @param having Used to specify HAVING clause to SQL because the WHERE keyword could not be used with Group Concat functions.
	 * @return Group Concat Of Tuples.
	 * @throws DatabaseException If any error occur while getting Group Concat.
	 */
	public String groupConcat(final String className, final String columnName, final String delimiter, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {

		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		String groupConcat = groupConcatRaw(entityDescriptor, columnName, delimiter, whereClause, groupBy, having);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(groupConcat);
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "groupConcat", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "groupConcat", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	private String groupConcatRaw(final EntityDescriptor entityDescriptor, final String columnName, final String delimiter, final String whereClause, final String[] groupBy, final String having) throws DatabaseException {
		
		DatabaseDescriptor databaseDescriptor = getDatabaseDescriptor(entityDescriptor.getClassName());
		DatabaseBundle databaseBundle = coreResourceManager.getDatabaseBundle(databaseDescriptor.getDatabaseName());

		IDatabaseImpl database = databaseBundle.getDatabase();
		IQueryBuilder queryBuilder = databaseBundle.getQueryBuilder();
		
		if(database == null) {
			Log.error(DatabaseHandler.class.getName(), "groupConcat", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
			throw new DeploymentException(DatabaseHandler.class.getName(), "groupConcat", "No Database Instance Found For ENTITY-DESCRIPTOR: " + entityDescriptor.getClassName());
		}

		Collection<String> groupBys = new ArrayList<String>();
		if(groupBy != null && groupBy.length > 0) {
			for(int i = 0;i < groupBy.length;i++) {
				groupBys.add(groupBy[i]);
			}
		}
		
		
		/*
		 * Add Parameters
		 */
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_TABLE_NAME_PARAMETER, entityDescriptor.getTableName());
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_COLUMN_PARAMETER, columnName);
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_DELIMITER_PARAMETER, delimiter);
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_WHERE_CLAUSE_PARAMETER, whereClause);
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_GROUP_BYS_PARAMETER, groupBys.iterator());
		parameters.put(IQueryBuilder.FORM_GROUP_CONCAT_QUERY_HAVING_PARAMETER, having);
		

		String query = queryBuilder.formGroupConcatQuery(parameters);

		Iterator<Map<String, Object>> datas = database.executeSelectQuery(databaseDescriptor, entityDescriptor, query);
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
	 * Handles Database Get Table Name Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Table Name.
	 * @throws DatabaseException If any error occur while get table name mapped to Hybrid model class name.
	 */
	public String getTableName(final String className) throws DatabaseException {
	
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		String tableName = getTableNameRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		HybridSiminovData siminovData = new HybridSiminovData();
		
		siminovData.setDataValue(String.valueOf(tableName));
		
		hybridSiminovDatas.addHybridSiminovData(siminovData);

		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	
	private String getTableNameRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		return entityDescriptor.getTableName();
	}

	
	/**
	 * Handles Database Get Columns Names Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Column Names.
	 * @throws DatabaseException If any error occur while getting column names mapped to Hybrid model class name.
	 */
	public String getColumnNames(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		Iterator<String> columnNames = getColumnNamesRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();

		while(columnNames.hasNext()) {
			String columnName = columnNames.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			hybridSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getTableName", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;
		
	}
	
	public static final Iterator<String> getColumnNamesRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();

		Collection<String> columnNames = new ArrayList<String>();
		while(attributes.hasNext()) {
			columnNames.add(attributes.next().getColumnName());
		}

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(attributes.next().getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					columnNames.add(attributes.next().getColumnName());
				}
			}
		}
		

		return columnNames.iterator();
		
	}
	

	
	/**
	 * Handles Database Get Columns Types Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Column Types.
	 * @throws DatabaseException If any error occur while getting column types mapped to Hybrid model class name.
	 */
	public String getColumnTypes(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		return getColumnTypesRaw(entityDescriptor);
		
	}
	
	public static final String getColumnTypesRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Map<String, Object> columnTypes = new HashMap<String, Object> ();
		Iterator<EntityDescriptor.Attribute> attributes = entityDescriptor.getAttributes();
		
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			columnTypes.put(attribute.getColumnName(), attribute.getType());
		}
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			
			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					columnTypes.put(attribute.getColumnName(), attribute.getType());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					columnTypes.put(attribute.getColumnName(), attribute.getType());
				}
			}
		}


		Collection<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		values.add(columnTypes);
		
		HybridSiminovDatas hybridSiminovDatas = parseData(entityDescriptor, values.iterator());
		
		String returnData = null;
		try {
			returnData = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);		
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getColumnTypes", "SiminovException caught while building json output, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getColumnTypes", "SiminovException caught while building json output, " + siminovException.getMessage());
		}
		
		return returnData;
		
	}
	
	
	/**
	 * Handles Database Get Primary Column Names Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Primary Column Names.
	 * @throws DatabaseException If any error occur while get primary column names mapped to Hybrid model class name.
	 */
	public String getPrimaryKeys(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		Iterator<String> primaryKeys = getPrimaryKeysRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();

		while(primaryKeys.hasNext()) {
			String columnName = primaryKeys.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			hybridSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getPrimaryKeys", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getPrimaryKeys", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	public static final Iterator<String> getPrimaryKeysRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Iterator<Attribute> attributes = entityDescriptor.getAttributes();
		Collection<String> primaryKeys = new ArrayList<String>();

		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			boolean isPrimary = attribute.isPrimaryKey();
			if(isPrimary) {
				primaryKeys.add(attribute.getColumnName());
			}
		}

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			
			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					primaryKeys.add(attribute.getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					primaryKeys.add(attribute.getColumnName());
				}
			}
		}
		
		return primaryKeys.iterator();
		
	}
	

	/**
	 * Handles Database Get Mandatory Column Names Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Mandatory Names.
	 * @throws DatabaseException If any error occur while getting mandatory column names mapped to Hybrid model class name.
	 */
	public String getMandatoryFields(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		Iterator<String> mandatoryFields = getMandatoryFieldsRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();

		while(mandatoryFields.hasNext()) {
			String columnName = mandatoryFields.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			hybridSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getMandatoryFields", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getMandatoryFields", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	public static final Iterator<String> getMandatoryFieldsRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Iterator<Attribute> attributes = entityDescriptor.getAttributes();
		Collection<String> mandatoryFields = new ArrayList<String>();
		
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			if(attribute.isNotNull()) {
				mandatoryFields.add(attribute.getColumnName());
			}
		}
		

		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			
			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					if(attribute.isNotNull()) {
						mandatoryFields.add(attribute.getColumnName());
					}
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					if(attribute.isNotNull()) {
						mandatoryFields.add(attribute.getColumnName());
					}
				}
			}
		}
		
		return mandatoryFields.iterator();
		
	}
	
	
	/**
	 * Handles Database Get Unique Column Names Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Unique Column Names.
	 * @throws DatabaseException If any error occur while getting unique column names. 
	 */
	public String getUniqueFields(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		Iterator<String> uniqueFields = getUniqueFieldsRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();

		while(uniqueFields.hasNext()) {
			String columnName = uniqueFields.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			hybridSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getUniqueFields", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getUniqueFields", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	public static final Iterator<String> getUniqueFieldsRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Iterator<Attribute> attributes = entityDescriptor.getAttributes();
		Collection<String> uniqueFields = new ArrayList<String>();
		
		while(attributes.hasNext()) {
			Attribute attribute = attributes.next();
			
			boolean isUnique = attribute.isUnique();
			if(isUnique) {
				uniqueFields.add(attribute.getColumnName());
			}
		}
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			
			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {

					boolean isUnique = attribute.isUnique();
					if(isUnique) {
						uniqueFields.add(attribute.getColumnName());
					}
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {

					boolean isUnique = attribute.isUnique();
					if(isUnique) {
						uniqueFields.add(attribute.getColumnName());
					}
				}
			}
		}
		

		return uniqueFields.iterator();
		
	}
	
	
	/**
	 * Handle Database Get Foreign Column Names Request From Hybrid.
	 * @param className Hybrid Model Class Name.
	 * @return Foreign Column Names.
	 * @throws DatabaseException If any error occur while getting foreign column names.
	 */
	public String getForeignKeys(final String className) throws DatabaseException {
		
		EntityDescriptor entityDescriptor = getEntityDescriptor(className);

		Iterator<String> foreignKeys = getForeignKeysRaw(entityDescriptor);
		
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();

		while(foreignKeys.hasNext()) {
			String columnName = foreignKeys.next();
			
			HybridSiminovData siminovData = new HybridSiminovData();
			siminovData.setDataValue(columnName);
			
			hybridSiminovDatas.addHybridSiminovData(siminovData);
		}

		
		String data = null;
		try {
			data = HybridSiminovDataWritter.jsonBuidler(hybridSiminovDatas);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "getForeignKeys", "SiminovException caught while building json, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "getForeignKeys", "SiminovException caught while building json, " + siminovException.getMessage());
		}

		return data;

	}
	
	public static final Iterator<String> getForeignKeysRaw(final EntityDescriptor entityDescriptor) throws DatabaseException {
		
		Collection<String> foreignKeys = new LinkedList<String>();
		
		/*
		 * Add ONE-TO-MANY And MANY-TO-MANY Relationship Columns.
		 */
		Iterator<Relationship> oneToManyRelationships = entityDescriptor.getManyToOneRelationships();
		Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		
		while(oneToManyRelationships.hasNext()) {
			Relationship oneToManyRelationship = oneToManyRelationships.next();
			EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
			if(referedEntityDescriptor == null) {
				referedEntityDescriptor = getEntityDescriptor(oneToManyRelationship.getReferTo());
				oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
			}

			
			Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					foreignKeys.add(attribute.getColumnName());
				}
			}
		}
		
		while(manyToManyRelationships.hasNext()) {
			Relationship manyToManyRelationship = manyToManyRelationships.next();
			EntityDescriptor parentEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
			
			Iterator<Attribute> parentAttributes = parentEntityDescriptor.getAttributes();
			while(parentAttributes.hasNext()) {
				Attribute attribute = parentAttributes.next();
				
				boolean isPrimary = attribute.isPrimaryKey();
				if(isPrimary) {
					foreignKeys.add(attribute.getColumnName());
				}
			}
		}
		
		return foreignKeys.iterator();
		
	}
	
	
	private HybridSiminovDatas parseHybridSiminovDatas(String data) throws DatabaseException {
		
		HybridSiminovDataReader hybridSiminovDataParser = null; 
		data = URLDecoder.decode(data);
		
		try {
			hybridSiminovDataParser = new HybridSiminovDataReader(data);
		} catch(SiminovException siminovException) {
			Log.error(DatabaseHandler.class.getName(), "parseHybridSiminovDatas", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
			throw new DatabaseException(DatabaseHandler.class.getName(), "parseHybridSiminovDatas", "SiminovException caught while parsing js core data, " + siminovException.getMessage());
		}

		return hybridSiminovDataParser.getDatas();
	}
	
	public static final DatabaseDescriptor getDatabaseDescriptor(final String className) throws DatabaseException {
		return hybridResourceManager.getDatabaseDescriptorBasedOnClassName(className);
	}

	public static final EntityDescriptor getEntityDescriptor(final String className) throws DatabaseException {
		return hybridResourceManager.getEntityDescriptorBasedOnClassName(className);
	}
	
	/**
		Iterates the provided cursor, and returns tuples in form of actual objects.
	 */
	private static HybridSiminovDatas parseData(final EntityDescriptor entityDescriptor, Iterator<Map<String, Object>> values) throws DatabaseException {
		
		Collection<Map<String, Object>> tuples = new LinkedList<Map<String, Object>>();
		HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
		
		while(values.hasNext()) {
			Map<String, Object> value = values.next();
			
			HybridSiminovData hybridSiminovData = new HybridSiminovData();
			hybridSiminovData.setDataType(hybridResourceManager.getMappedHybridClassName(entityDescriptor.getClassName()));
			
			Iterator<String> keys = value.keySet().iterator();
			while(keys.hasNext()) {
				
				String columnName = keys.next();
				if(!entityDescriptor.containsAttributeBasedOnColumnName(columnName)) {
					continue;
				}
				
				String variableName = entityDescriptor.getAttributeBasedOnColumnName(columnName).getVariableName();
				
				Object object = value.get(columnName);
				
				HybridSiminovValue hybridSiminovValue = new HybridSiminovValue();
				hybridSiminovValue.setType(variableName);

				if(object instanceof String) {
					hybridSiminovValue.setValue((String) object);
				} else if(object instanceof Long) {
					hybridSiminovValue.setValue(((Long) object).toString());
				} else if(object instanceof Float) {
					hybridSiminovValue.setValue(((Float) object).toString());
				} else if(object instanceof Blob) {
					hybridSiminovValue.setValue(((Blob) object).toString());
				}

				hybridSiminovData.addValue(hybridSiminovValue);
			}
			
			hybridSiminovDatas.addHybridSiminovData(hybridSiminovData);
			tuples.add(value);
		}
		
		values = tuples.iterator();
		
		return hybridSiminovDatas;
	}

	
	
	private static class RelationshipHelper {
		
		public static void processRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData) throws DatabaseException {
		    
		    RelationshipHelper.processOneToOneRelationship(siminovData, parentSiminovData);
		    RelationshipHelper.processOneToManyRelationship(siminovData, parentSiminovData);
		    RelationshipHelper.processManyToOneRelationship(siminovData, parentSiminovData);
		    RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData);
		}

		public static void processRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Collection<String> columnNames, Collection<Object> columnValues) throws DatabaseException {
		    
		    RelationshipHelper.processOneToOneRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		    RelationshipHelper.processOneToManyRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		    RelationshipHelper.processManyToOneRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		    RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		}

		public static void processRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, StringBuilder whereClause) throws DatabaseException {
		    
		    RelationshipHelper.processOneToOneRelationship(siminovData, parentSiminovData, whereClause);
		    RelationshipHelper.processOneToManyRelationship(siminovData, parentSiminovData, whereClause);
		    RelationshipHelper.processManyToOneRelationship(siminovData, parentSiminovData, whereClause);
		    RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData, whereClause);
		}

		public static void processRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Map<String, Object> data) throws DatabaseException {
		    
		    RelationshipHelper.processOneToOneRelationship(siminovData, parentSiminovData, data);
		    RelationshipHelper.processOneToManyRelationship(siminovData, parentSiminovData, data);
		    RelationshipHelper.processManyToOneRelationship(siminovData, parentSiminovData, data);
		    RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData, data);
		}
		
		
		private static void processOneToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<EntityDescriptor.Relationship> oneToOneRelationships = entityDescriptor.getOneToOneRelationships();
		    
		    while(oneToOneRelationships.hasNext()) {
			    Relationship oneToOneRelationship = oneToOneRelationships.next();
		        
		        boolean isLoad = oneToOneRelationship.isLoad();
		        if(!isLoad) {
		            continue;
		        }
		        
		        
		        StringBuffer whereClause = new StringBuffer();
		        Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(entityDescriptor);
		        
		        while(foreignKeys.hasNext()) {
			        String foreignKey = foreignKeys.next();
		            Attribute attribute = entityDescriptor.getAttributeBasedOnColumnName(foreignKey);
			        
		            if(attribute == null) {
		            	continue;
		            }
		            
		            HybridSiminovValue value = siminovData.getValueBasedOnType(attribute.getVariableName());
		            String columnValue = value.getValue();
		            
		            if(whereClause.length() <= 0) {
		                whereClause.append(foreignKey + "='" + columnValue + "'");
		            } else {
		                whereClause.append(" AND " + foreignKey + "='" + columnValue + "'");
		            }
		        }
		        
		        EntityDescriptor referedEntityDescriptor = oneToOneRelationship.getReferedEntityDescriptor();
		        if(referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(oneToOneRelationship.getReferTo());
		            oneToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        if(siminovData != null && (siminovData.getDataType().equalsIgnoreCase(referedEntityDescriptor.getClassName()))) {
		            continue;
		            //return;
		        }
		        
		        HybridSiminovDatas referedObjects = DatabaseHandler.selectDatas(referedEntityDescriptor, Boolean.valueOf(false), whereClause.toString(), null, null, null, null, null, null);
		        HybridSiminovData referedObject = referedObjects.getHybridSiminovDatas().next();
		        
		        if(referedObject == null) {
		        	continue;
		        }
		        
		        siminovData.addData(referedObject);
		    }
		}


		private static void processOneToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<EntityDescriptor.Relationship> oneToManyRelationships = entityDescriptor.getOneToManyRelationships();
		    
		    while(oneToManyRelationships.hasNext()) {
			    Relationship oneToManyRelationship = oneToManyRelationships.next();
		        
		        boolean isLoad = oneToManyRelationship.isLoad();
		        if(!isLoad) {
		            continue;
		        }
		        
		        StringBuilder whereClause = new StringBuilder();
		        Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(entityDescriptor);
		        
		        while(foreignKeys.hasNext()) {
			        String foreignKey = foreignKeys.next();
			        Attribute attribute = entityDescriptor.getAttributeBasedOnColumnName(foreignKey);
		            
			        if(attribute == null) {
			        	continue;
			        }
			        
		            HybridSiminovValue value = siminovData.getValueBasedOnType(attribute.getVariableName());
		            String columnValue = value.getValue();
		            
		            if(whereClause.length() <= 0) {
		                whereClause.append(foreignKey + "='" + columnValue + "'");
		            } else {
		                whereClause.append(" AND " + foreignKey + "='" + columnValue + "'");
		            }
		        }
		        
		        EntityDescriptor referedEntityDescriptor = oneToManyRelationship.getReferedEntityDescriptor();
		        if(referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(oneToManyRelationship.getReferTo());
		            
		            oneToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        HybridSiminovDatas referedObjects = DatabaseHandler.selectDatas(referedEntityDescriptor, Boolean.valueOf(false), whereClause.toString(), null, null, "", null, "", "");
		        if(referedObjects == null) {
		        	continue;
		        }
		            
	            Iterator<HybridSiminovData> referedObjectsDatas = referedObjects.getHybridSiminovDatas();
	            while(referedObjectsDatas.hasNext()) {
	            	HybridSiminovData referedObjectsData = referedObjectsDatas.next();
	            	siminovData.addData(referedObjectsData);
	            }
		    }
		}


		private static void processManyToOneRelationship(HybridSiminovData object, HybridSiminovData parentObject) throws DatabaseException {
		    
		}

		private static void processManyToManyRelationship(HybridSiminovData object, HybridSiminovData parentObject) throws DatabaseException {
		    
		}


		private static void processOneToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Collection<String> columnNames, Collection<Object> columnValues) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<EntityDescriptor.Relationship> oneToOneRelationships = entityDescriptor.getOneToOneRelationships();
		    
		    while(oneToOneRelationships.hasNext()) {
			    Relationship oneToOneRelationship = oneToOneRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = oneToOneRelationship.getReferedEntityDescriptor();
		        if(referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(oneToOneRelationship.getReferTo());
		            oneToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData data = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(referedEntityDescriptor.getClassName())) {
		                referedSiminovData = data;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + oneToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        RelationshipHelper.processOneToManyRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		        RelationshipHelper.processManyToOneRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		        RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                columnNames.add(attribute.getColumnName());
		                columnValues.add(hybridSiminovValues.get(attribute.getVariableName()).getValue());
		            }
		        }
		    }
		}


		private static void processOneToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Collection<String> columnNames, Collection<Object> columnValues) throws DatabaseException {
		    
		}



		private static void processManyToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Collection<String> columnNames, Collection<Object> columnValues) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<EntityDescriptor.Relationship> manyToOneRelationships = entityDescriptor.getManyToOneRelationships();
		    
		    while(manyToOneRelationships.hasNext()) {
			    Relationship manyToOneRelationship = manyToOneRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToOneRelationship.getReferedEntityDescriptor();
		        if(referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToOneRelationship.getReferTo());
		            manyToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData data = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(referedEntityDescriptor.getClassName())) {
		                referedSiminovData = data;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        
		        RelationshipHelper.processOneToOneRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		        RelationshipHelper.processManyToManyRelationship(siminovData, parentSiminovData, columnNames, columnValues);
		        
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                columnNames.add(attribute.getColumnName());
		                columnValues.add(hybridSiminovValues.get(attribute.getVariableName()).getValue());
		            }
		        }
		    }
		}


		private static void processOneToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, StringBuilder whereClause) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> oneToOneRelationships = entityDescriptor.getOneToOneRelationships();
		    
		    while(oneToOneRelationships.hasNext()) {
			    Relationship oneToOneRelationship = oneToOneRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = oneToOneRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(oneToOneRelationship.getReferTo());
		            oneToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData data = datas.next(); 
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(oneToOneRelationship.getGetterReferMethodName())) {
		                referedSiminovData = data;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + oneToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        RelationshipHelper.processOneToManyRelationship(referedSiminovData, siminovData, whereClause);
		        RelationshipHelper.processManyToOneRelationship(referedSiminovData, siminovData, whereClause);
		        RelationshipHelper.processManyToManyRelationship(referedSiminovData, siminovData, whereClause);
		        
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                String columnName = attribute.getColumnName();
		                String columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
		                
		                if (whereClause.length() <= 0) {
		                    whereClause.append(columnName + "= '" + columnValue + "'");
		                } else {
		                    whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
		                }
		            }
		        }
		    }
		}


		private static void processOneToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, StringBuilder whereClause) throws DatabaseException {
		    
		}


		private static void processManyToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, StringBuilder whereClause) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> manyToOneRelationships = entityDescriptor.getManyToOneRelationships();
		    
		    while(manyToOneRelationships.hasNext()) {
			    Relationship manyToOneRelationship = manyToOneRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToOneRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToOneRelationship.getReferTo());
		            manyToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData data = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(data.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(manyToOneRelationship.getGetterReferMethodName())) {
		                referedSiminovData = data;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "manyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        RelationshipHelper.processOneToOneRelationship(referedSiminovData, parentSiminovData, whereClause);
		        RelationshipHelper.processManyToManyRelationship(referedSiminovData, parentSiminovData, whereClause);
		        
		        
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                String columnName = attribute.getColumnName();
		                String columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
		                
		                if (whereClause.length() <= 0) {
		                	whereClause.append(columnName + "= '" + columnValue + "'");
		                } else {
		                    whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
		                }
		            }
		        }
		    }
		}


		private static void processOneToOneRelationship(HybridSiminovData object, HybridSiminovData parentObject, Map<String, Object> data) throws DatabaseException {
		    
		}

		private static void processOneToManyRelationship(HybridSiminovData object, HybridSiminovData parentObject, Map<String, Object> data) throws DatabaseException {
		    
		}


		private static void processManyToOneRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Map<String, Object> data) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> manyToOneRelationships = entityDescriptor.getManyToManyRelationships();
		    
		    while(manyToOneRelationships.hasNext()) {
			    Relationship manyToOneRelationship = manyToOneRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToOneRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToOneRelationship.getReferTo());
		            manyToOneRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData siminovReferedData = datas.next();
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(siminovReferedData.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(manyToOneRelationship.getGetterReferMethodName())) {
		                referedSiminovData = siminovReferedData;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "manyToOneRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        RelationshipHelper.processOneToOneRelationship(referedSiminovData, parentSiminovData, data);
		        RelationshipHelper.processManyToManyRelationship(referedSiminovData, parentSiminovData, data);
		        
		        
		        if(manyToOneRelationship.isLoad()) {
		            
		            StringBuilder whereClause = new StringBuilder();
		            
		            Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(referedEntityDescriptor);
		            while(foreignKeys.hasNext()) {
			            String foreignKey = foreignKeys.next();
			            
		                Attribute attribute = referedEntityDescriptor.getAttributeBasedOnColumnName(foreignKey);
		                Object columnValue = data.get(attribute.getColumnName());
		                
		                if(whereClause.length() <= 0) {
		                    whereClause.append(foreignKey + "='" + columnValue + "'");
		                } else {
		                    whereClause.append(" AND " + foreignKey + "='" + columnValue + "'");
		                }
		            }
		            
		            
		            HybridSiminovDatas fetchedObjects = DatabaseHandler.selectDatas(referedEntityDescriptor, Boolean.valueOf(false), whereClause.toString(), null, null, null, null, null, null);
		            referedSiminovData = fetchedObjects.getHybridSiminovDatas().next();
		            
		        } else {
		            
		            Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(referedEntityDescriptor);
		            while(foreignKeys.hasNext()) {
			            String foreignKey = foreignKeys.next();
			            
		                Attribute attribute = referedEntityDescriptor.getAttributeBasedOnColumnName(foreignKey);
		                
		                Object columnValue = data.get(attribute.getColumnName());
		                if(columnValue == null) {
		                    continue;
		                }
		                
		                HybridSiminovValue value = new HybridSiminovValue();
		                value.setType(foreignKey);
		                value.setValue((String) columnValue);
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToOneRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToOneRelationship.getReferTo());
		            continue;
		        }
		        
		        siminovData.addData(referedSiminovData);
		    }
		}


		private static void processManyToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Collection<String> columnNames, Collection<Object> columnValues) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		    
		    while(manyToManyRelationships.hasNext()) {
			    Relationship manyToManyRelationship = manyToManyRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToManyRelationship.getReferTo());
		            manyToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData siminovReferedData = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(siminovReferedData.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(manyToManyRelationship.getGetterReferMethodName())) {
		                referedSiminovData = siminovReferedData;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "manyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
		            continue;
		        }
		        
		        RelationshipHelper.processRelationship(referedSiminovData, parentSiminovData, columnNames, columnValues);
		        
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                columnNames.add(attribute.getColumnName());
		                columnValues.add(hybridSiminovValues.get(attribute.getVariableName()).getValue());
		            }
		        }
		    }
		}

		private static void processManyToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, StringBuilder whereClause) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		    
		    while(manyToManyRelationships.hasNext()) {
			    Relationship manyToManyRelationship = manyToManyRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToManyRelationship.getReferTo());
		            manyToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        
		        HybridSiminovData referedSiminovData = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData siminovReferedData = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(siminovReferedData.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(manyToManyRelationship.getGetterReferMethodName())) {
		                referedSiminovData = siminovReferedData;
		                break;
		            }
		        }
		        
		        if(referedSiminovData == null) {
		            Log.error(DatabaseHandler.class.getName(), "manyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
		            continue;
		        }
		        
		        
		        RelationshipHelper.processRelationship(referedSiminovData, parentSiminovData, whereClause);
		        
		        Iterator<HybridSiminovValue> hybridValues = referedSiminovData.getValues();
		        
		        Map<String, HybridSiminovValue> hybridSiminovValues = new HashMap<String, HybridSiminovValue>();
		        while(hybridValues.hasNext()) {
			        HybridSiminovValue hybridSiminovValue = hybridValues.next();
		            hybridSiminovValues.put(hybridSiminovValue.getType(), hybridSiminovValue);
		        }
		        
		        Iterator<Attribute> parentAttributes = referedEntityDescriptor.getAttributes();
		        while(parentAttributes.hasNext()) {
			        Attribute attribute = parentAttributes.next();
			        
		            boolean isPrimary = attribute.isPrimaryKey();
		            if(isPrimary) {
		                String columnName = attribute.getColumnName();
		                String columnValue = hybridSiminovValues.get(attribute.getVariableName()).getValue();
		                
		                if (whereClause.length() <= 0) {
		                    whereClause.append(columnName + "= '" + columnValue + "'");
		                } else {
		                    whereClause.append(" AND " + columnName + "= '" + columnValue + "'");
		                }
		            }
		        }
		    }
		}

		private static void processManyToManyRelationship(HybridSiminovData siminovData, HybridSiminovData parentSiminovData, Map<String, Object> data) throws DatabaseException {
		    
		    EntityDescriptor entityDescriptor = DatabaseHandler.getEntityDescriptor(siminovData.getDataType());
		    Iterator<Relationship> manyToManyRelationships = entityDescriptor.getManyToManyRelationships();
		    
		    while(manyToManyRelationships.hasNext()) {
			    Relationship manyToManyRelationship = manyToManyRelationships.next();
			    
		        EntityDescriptor referedEntityDescriptor = manyToManyRelationship.getReferedEntityDescriptor();
		        if (referedEntityDescriptor == null) {
		            referedEntityDescriptor = DatabaseHandler.getEntityDescriptor(manyToManyRelationship.getReferTo());
		            manyToManyRelationship.setReferedEntityDescriptor(referedEntityDescriptor);
		        }
		        
		        HybridSiminovData referedObject = null;
		        Iterator<HybridSiminovData> datas = siminovData.getDatas();
		        
		        while(datas.hasNext()) {
			        HybridSiminovData siminovReferedData = datas.next();
			        
		            String referedClassName = hybridResourceManager.getMappedNativeClassName(siminovReferedData.getDataType());
		            
		            if(referedClassName.equalsIgnoreCase(manyToManyRelationship.getGetterReferMethodName())) {
		            	referedObject = siminovReferedData;
		                break;
		            }
		        }
		        
		        if(referedObject == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Parent Object Not Set, Please Provide Proper Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
		            continue;
		        }
		        
		        RelationshipHelper.processRelationship(referedObject, parentSiminovData, data);
		        
		        if(manyToManyRelationship.isLoad()) {
		            
		            StringBuilder whereClause = new StringBuilder();
		            Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(referedEntityDescriptor);
		            
		            while(foreignKeys.hasNext()) {
			            String foreignKey = foreignKeys.next();
		                
		                Attribute attribute = referedEntityDescriptor.getAttributeBasedOnColumnName(foreignKey);
		                Object columnValue = data.get(attribute.getColumnName());
		                
		                if(whereClause.length() <= 0) {
		                    whereClause.append(foreignKey = "='" + columnValue + "'");
		                } else {
		                    whereClause.append(" AND " + foreignKey + "='" + columnValue + "'");
		                }
		            }
		            
		            
		            HybridSiminovDatas fetchedObjects = DatabaseHandler.selectDatas(referedEntityDescriptor, Boolean.FALSE, whereClause.toString(), null, null, null, null, null, null);
		            
		            referedObject = fetchedObjects.getHybridSiminovDatas().next();
		            
		        } else {
		            
		            
		            Iterator<String> foreignKeys = DatabaseHandler.getPrimaryKeysRaw(referedEntityDescriptor);
		            while(foreignKeys.hasNext()) {
			            String foreignKey = foreignKeys.next();
			            
		                Attribute attribute = referedEntityDescriptor.getAttributeBasedOnColumnName(foreignKey);
		                
		                Object columnValue = data.get(attribute.getColumnName());
		                if(columnValue == null) {
		                    continue;
		                }
		                
		                HybridSiminovValue value = new HybridSiminovValue();
		                value.setType(foreignKey);
		                value.setValue((String) columnValue);
		            }
		        }
		        
		        if(referedObject == null) {
		            Log.error(DatabaseHandler.class.getName(), "processManyToManyRelationship", "Unable To Create Parent Relationship. REFER-TO: " + manyToManyRelationship.getReferTo());
		            continue;
		        }
		        
		        siminovData.addData(referedObject);
		    }
		}
	}
}