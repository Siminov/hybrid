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



package siminov.web.resource;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import siminov.connect.connection.design.IConnectionRequest;
import siminov.connect.connection.design.IConnectionResponse;
import siminov.connect.exception.NotificationException;
import siminov.connect.model.ServiceDescriptor.Request.HeaderParameter;
import siminov.connect.model.ServiceDescriptor.Request.QueryParameter;
import siminov.connect.notification.design.IMessage;
import siminov.connect.notification.design.IRegistration;
import siminov.connect.sync.design.ISyncRequest;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.core.model.DatabaseDescriptor;
import siminov.core.model.EntityDescriptor;
import siminov.core.model.EntityDescriptor.Attribute;
import siminov.core.model.EntityDescriptor.Index;
import siminov.core.model.EntityDescriptor.Relationship;
import siminov.core.utils.Utils;
import siminov.web.adapter.AdapterFactory;
import siminov.web.adapter.AdapterHandler;
import siminov.web.adapter.constants.WebAdapterDescriptor;
import siminov.web.adapter.constants.WebConnectionRequest;
import siminov.web.adapter.constants.WebConnectionResponse;
import siminov.web.adapter.constants.WebDatabaseDescriptor;
import siminov.web.adapter.constants.WebEntityDescriptor;
import siminov.web.adapter.constants.WebLibraryDescriptor;
import siminov.web.adapter.constants.WebMessage;
import siminov.web.adapter.constants.WebNotificationException;
import siminov.web.adapter.constants.WebRegistration;
import siminov.web.adapter.constants.WebSyncRequest;
import siminov.web.events.EventHandler;
import siminov.web.model.AdapterDescriptor;
import siminov.web.model.AdapterDescriptor.Handler;
import siminov.web.model.AdapterDescriptor.Handler.Parameter;
import siminov.web.model.AdapterDescriptor.Handler.Return;
import siminov.web.model.ApplicationDescriptor;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import android.app.Activity;
import android.webkit.WebView;

/**
 * It handles and provides all resources needed by SIMINOV WEB.
 * <p>
 * Such As: Provides WebDescriptor, AdapterDescriptor.
 */
public class ResourceManager {

	private static ResourceManager webResources = null;
	private siminov.core.resource.ResourceManager coreResourceManager = null;
	
	private ApplicationDescriptor applicationDescriptor = null;
	
	private WebView webView = null;
	private Activity webActivity = null;
	
	private EventHandler eventHandler = null;
	
	private AdapterHandler adapterHandler = null;
	private AdapterFactory adapterFactory = null;

	private Map<String, String> webNativeClassMapping = new ConcurrentHashMap<String, String>();
	
	private ResourceManager() {
		coreResourceManager = siminov.core.resource.ResourceManager.getInstance();
		eventHandler = EventHandler.getInstance();
	}
	
	/**
	 * It provides an instance of Resources class.
	 * 
	 * @return Resources instance.
	 */
	public static ResourceManager getInstance() {
		
		if(webResources == null) {
			webResources = new ResourceManager();
		}
		
		return webResources;
	}

	
	/**
	 * Get Web Descriptor.
	 * @return Web Descriptor.
	 */
	public ApplicationDescriptor getApplicationDescriptor() {
		return this.applicationDescriptor;
	}
	
	/**
	 * Set Web Descriptor.
	 * @param applicationDescriptor Web Descriptor.
	 */
	public void setApplicationDescriptor(ApplicationDescriptor applicationDescriptor) {
		this.applicationDescriptor = applicationDescriptor;
	}

	
	
	/**
	 * Get All Adapter Descriptors defined by Application.
	 * @return All Adapter Descriptors.
	 */
	public Iterator<AdapterDescriptor> getAdapterDescriptors() {
		return applicationDescriptor.getAdapterDescriptors();
	}
	
	
	/**
	 * Get All Adapter Descriptors Defined By Paths.
	 * @return All Adapter Descriptors.
	 */
	public Iterator<AdapterDescriptor> getAdapterDescriptorsBasedOnPaths() {
		
		Iterator<String> adapterDescriptorPaths = applicationDescriptor.getAdapterDescriptorPaths();
		Collection<AdapterDescriptor> adapterDescriptors = new ArrayList<AdapterDescriptor>();
		
		while(adapterDescriptorPaths.hasNext()) {
			String adapterDescriptorPath = adapterDescriptorPaths.next();
			adapterDescriptors.add(applicationDescriptor.getAdapterDescriptorBasedOnPath(adapterDescriptorPath));
		}
		
		return adapterDescriptors.iterator();
	}
	

	/**
	 * Get Adapter Descriptor based on Adapter Descriptor Name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return Adapter Descriptor
	 */
	public AdapterDescriptor getAdapterDescriptor(final String adapterDescriptorName) {
		boolean contain = applicationDescriptor.containAdapterDescriptorBasedOnName(adapterDescriptorName);
		if(contain) {
			return applicationDescriptor.getAdapterDescriptorBasedOnName(adapterDescriptorName);
		}
		
		return null;
	}
	
	/**
	 * Get Adapter Descriptor based on Adapter Descriptor Name.
	 * @param adapterDescriptorName Name of Adapter Descriptor.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterBasedOnName(final String adapterDescriptorName) {
		
		boolean contain = applicationDescriptor.containAdapterDescriptorBasedOnName(adapterDescriptorName);
		if(contain) {
			return applicationDescriptor.getAdapterDescriptorBasedOnName(adapterDescriptorName);
		}
		
		return null;
	}
	
	/**
	 * Get Adapter Descriptor based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return Adapter Descriptor.
	 */
	public AdapterDescriptor getAdapterBasedOnPath(final String adapterDescriptorPath) {
		
		boolean contain = applicationDescriptor.containAdapterDescriptorBasedOnPath(adapterDescriptorPath);
		if(contain) {
			return applicationDescriptor.getAdapterDescriptorBasedOnPath(adapterDescriptorPath);
		}
		
		return null;
	}
	
	
	/**
	 * Check whether adapter descriptor exist or not based on adapter descriptor name.
	 * @param adapterDescriptorName Name of adapter descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterBasedOnName(final String adapterDescriptorName) {
		
		boolean contain = applicationDescriptor.containAdapterDescriptorBasedOnName(adapterDescriptorName);
		if(contain) {
			return contain;
		}
		
		return false;
	}
	
	/**
	 * Check whether adapter descriptor exist or not based on adapter descriptor path.
	 * @param adapterDescriptorPath Path of Adapter Descriptor.
	 * @return true/false; TRUE if adapter descriptor exist, FALSE if adapter descriptor does not exist.
	 */
	public boolean containAdapterBasedOnPath(final String adapterDescriptorPath) {
		return applicationDescriptor.containAdapterDescriptorBasedOnPath(adapterDescriptorPath);
	}
	
	
	/**
	 * Get All Handlers defined by Application.
	 * @return All Handlers.
	 */
	public Iterator<Handler> getHandlers() {
		
		Collection<Handler> handlers = new ArrayList<Handler>();
		
		Iterator<AdapterDescriptor> adapterDescriptors = getAdapterDescriptors();
		while(adapterDescriptors.hasNext()) {
			AdapterDescriptor adapterDescriptor = adapterDescriptors.next();
			Iterator<Handler> adapterHandlers = adapterDescriptor.getHandlers();
			
			while(adapterHandlers.hasNext()) {
				handlers.add(adapterHandlers.next());
			}
		}
		
		return handlers.iterator();
	}

	/**
	 * Get Handler based on Adapter Name and Handler Name.
	 * @param adapterDescriptorName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return Handler.
	 */
	public Handler getHandler(final String adapterDescriptorName, final String handlerName) {
		
		AdapterDescriptor adapterDescriptor = getAdapterDescriptor(adapterDescriptorName);
		boolean contain = adapterDescriptor.containHandler(handlerName);
		
		if(contain) {
			return adapterDescriptor.getHandler(handlerName);
		}
		
		return null;
	}
	
	/**
	 * Check whether Handler exist or not based on handler name.
	 * @param handlerName Name of Handler.
	 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
	 */
	public boolean containHandler(final String handlerName) {
		
		Iterator<AdapterDescriptor> adapterDescriptors = getAdapterDescriptors();
		while(adapterDescriptors.hasNext()) {
			AdapterDescriptor adapterDescriptor = adapterDescriptors.next();
			Iterator<Handler> handlers = adapterDescriptor.getHandlers();
			
			while(handlers.hasNext()) {
				Handler handler = handlers.next();
				if(handler.getName().equalsIgnoreCase(handlerName)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check whether Handler exist or not based on adapter name and handler name.
	 * @param adapterDescriptorName Name of Adapter.
	 * @param handlerName Name of Handler.
	 * @return true/false; TRUE if handler exist, FALSE if handler does not exist.
	 */
	public boolean containHandler(final String adapterDescriptorName, final String handlerName) {
		AdapterDescriptor adapterDescriptor = getAdapterDescriptor(adapterDescriptorName);
		return adapterDescriptor.containHandler(handlerName);
	}
	
	/**
	 * Get Web View.
	 * @return Web View.
	 */
	public WebView getWebView() {
		return this.webView;
	}
	
	/**
	 * Set Web View.
	 * @param webView Web View.
	 */
	public void setWebView(final WebView webView) {
		this.webView = webView;
	}

	/**
	 * Get Web Activity
	 * @return Web Activity
	 */
	public Activity getWebActivity() {
		return this.webActivity;
	}
	
	/**
	 * Set Web Activity
	 * @param webActivity Web Activity
	 */
	public void setWebActivity(Activity webActivity) {
		this.webActivity = webActivity;
	}
	
	
	/**
	 * Check whether any event registered by application or not.
	 * @return true/false; TRUE if event registered by application, FALSE if event not registered by application.
	 */
	public boolean doesEventsRegistered() {
		return this.eventHandler.doesEventsRegistered();
	}
	
	/**
	 * Add Event.
	 * @param event Event.
	 */
	public void addEvent(String event) {
		this.eventHandler.addEvent(event);
	}
	
	/**
	 * Get All Events registered by application.
	 * @return All Events.
	 */
	public Iterator<String> getEvents() {
		return this.eventHandler.getEvents();
	}

	/**
	 * Get AdapterHandler.
	 * @return AdapterHandler.
	 */
	public AdapterHandler getAdapterHandler() {
		return this.adapterHandler;
	}
	
	/**
	 * Set AdapterHandler.
	 * @param adapterHandler AdapterHandler.
	 */
	public void setAdapterHandler(final AdapterHandler adapterHandler) {
		this.adapterHandler = adapterHandler;
	}
	
	/**
	 * Get Adapter Resources.
	 * @return Adapter Resources.
	 */
	public AdapterFactory getAdapterFactory() {
		return this.adapterFactory;
	}
	
	/**
	 * Set Adapter Resources.
	 * @param adapterFactory Adapter Resources.
	 */
	public void setAdapterFactory(final AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}


	/*
	 * Siminov Core API's.
	 */

	/**
	 * Get Database Descriptor based on Web model class name.
	 * @param className Name of Web Model Class.
	 * @return Database Descriptor.
	 */
	public DatabaseDescriptor getDatabaseDescriptorBasedOnClassName(String className) {

		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = webNativeClassMapping.get(className);
		return coreResourceManager.getDatabaseDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Descriptor Name based on Web Model class name.
	 * @param className Name of Web Model Class.
	 * @return Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnClassName(String className) {
	
		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = webNativeClassMapping.get(className);
		return coreResourceManager.getDatabaseDescriptorNameBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Descriptor Name based on table name.
	 * @param tableName Name of table.
	 * @return Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnTableName(final String tableName) {
		return coreResourceManager.getDatabaseDescriptorNameBasedOnTableName(tableName);
	}

	/**
	 * Get Database Descriptor based on table name.
	 * @param tableName Name of Table.
	 * @return Database Descriptor.
	 */
	public DatabaseDescriptor getDatabaseDescriptorBasedOnTableName(final String tableName) {
		return coreResourceManager.getDatabaseDescriptorBasedOnTableName(tableName);
	}
	
	/**
	 * Get Entity Descriptor based on Web Model Class Name.
	 * @param className Name of Web Model Class.
	 * @return Entity Descriptor.
	 */
	public EntityDescriptor getEntityDescriptorBasedOnClassName(String className) {
		
		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = webNativeClassMapping.get(className);
		if(nativeClassName == null ||nativeClassName.length() <= 0) {
			EntityDescriptor entityDescriptor = coreResourceManager.requiredEntityDescriptorBasedOnClassName(className);
			synchronizeMappings();
			
			return entityDescriptor;
		}
		
		return coreResourceManager.requiredEntityDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Entity Descriptor based on table name.
	 * @param tableName Name of Table.
	 * @return Entity Descriptor.
	 */
	public EntityDescriptor getEntityDescriptorBasedOnTableName(final String tableName) {
		return coreResourceManager.getEntityDescriptorBasedOnTableName(tableName);
	}

	/**
	 * Get Mapped Native Model Class Name.
	 * @param webClassName Web Model Class Name.
	 * @return Native Model Class Name.
	 */
	public String getMappedNativeClassName(String webClassName) {
		webClassName = webClassName.substring(webClassName.lastIndexOf(".") + 1, webClassName.length());
		return webNativeClassMapping.get(webClassName);
	}
	
	/**
	 * Get Mapped Web Model Class Name.
	 * @param nativeClassName Native Model Class Name.
	 * @return Web Model Class Name.
	 */
	public String getMappedWebClassName(final String nativeClassName) {
		Iterator<String> keys = webNativeClassMapping.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = webNativeClassMapping.get(key);
			
			if(value.equalsIgnoreCase(nativeClassName)) {
				return key;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Map Native Model Class Name with Web Model Class Name.
	 */
	public void synchronizeMappings() {

		Iterator<EntityDescriptor> entityDescriptors = coreResourceManager.getEntityDescriptors();
		while(entityDescriptors.hasNext()) {
			EntityDescriptor entityDescriptor = entityDescriptors.next();
			
			String nativeClassName = entityDescriptor.getClassName();
			String webClassName = nativeClassName.substring(nativeClassName.lastIndexOf(".") + 1, nativeClassName.length());
			
			webNativeClassMapping.put(webClassName, nativeClassName);
			
		}
	}

	
	/**
	 * Generates Web Database Descriptor
	 * @param databaseDescriptor Database Descriptor.
	 * @return Web Application Descriptor. 
	 */
	public WebSiminovData generateWebDatabaseDescriptor(DatabaseDescriptor databaseDescriptor) {
		
		WebSiminovData webDatabaseDescriptor = new WebSiminovData();
		webDatabaseDescriptor.setDataType(WebDatabaseDescriptor.DATABASE_DESCRIPTOR);
		
		WebSiminovValue databaseName = new WebSiminovValue();
		databaseName.setType(WebDatabaseDescriptor.NAME);
		databaseName.setValue(databaseDescriptor.getDatabaseName());

		webDatabaseDescriptor.addValue(databaseName);
		
		WebSiminovValue databaseDescription = new WebSiminovValue();
		databaseDescription.setType(WebDatabaseDescriptor.DESCRIPTION);
		databaseDescription.setValue(databaseDescriptor.getDescription());
		
		webDatabaseDescriptor.addValue(databaseDescription);
		
		WebSiminovValue isLockingRequired = new WebSiminovValue();
		isLockingRequired.setType(WebDatabaseDescriptor.IS_TRANSACTION_SAFE);
		isLockingRequired.setValue(Boolean.toString(databaseDescriptor.isTransactionSafe()));
		
		webDatabaseDescriptor.addValue(isLockingRequired);
		
		WebSiminovValue externalStorage = new WebSiminovValue();
		externalStorage.setType(WebDatabaseDescriptor.EXTERNAL_STORAGE);
		externalStorage.setValue(Boolean.toString(databaseDescriptor.isExternalStorageEnable()));
		
		webDatabaseDescriptor.addValue(externalStorage);
		
		WebSiminovData webEntityDescriptorPaths = new WebSiminovData();
		webEntityDescriptorPaths.setDataType(WebDatabaseDescriptor.ENTITY_DESCRIPTORS);
		
		Iterator<String> entityDescriptors = databaseDescriptor.getEntityDescriptorPaths();
		while(entityDescriptors.hasNext()) {
			
			WebSiminovValue entityDescriptorPath = new WebSiminovValue();
			entityDescriptorPath.setType(WebDatabaseDescriptor.ENTITY_DESCRIPTOR_PATH);
			entityDescriptorPath.setValue(entityDescriptors.next());
			
			webEntityDescriptorPaths.addValue(entityDescriptorPath);
			
		}
		
		webDatabaseDescriptor.addData(webEntityDescriptorPaths);
		
		return webDatabaseDescriptor;
	}

	
	/**
	 * Generate Web Entity Descriptor.
	 * @param entityDescriptor Entity Descriptor.
	 * @return Web Entity Descriptor.
	 */
	public WebSiminovData generateWebEntityDescriptor(final EntityDescriptor entityDescriptor) {
		
		WebSiminovData webEntityDescriptor = new WebSiminovData();
		webEntityDescriptor.setDataType(WebEntityDescriptor.ENTITY_DESCRIPTOR);
		
		
		WebSiminovValue tableName = new WebSiminovValue();
		tableName.setType(WebEntityDescriptor.TABLE_NAME);
		tableName.setValue(entityDescriptor.getTableName());
		
		webEntityDescriptor.addValue(tableName);
		
		
		WebSiminovValue className = new WebSiminovValue();
		className.setType(WebEntityDescriptor.CLASS_NAME);
		className.setValue(entityDescriptor.getClassName());
		
		webEntityDescriptor.addValue(className);
		
		
		WebSiminovData webColumns = new WebSiminovData();
		webColumns.setDataType(WebEntityDescriptor.ENTITIES);
		
		Iterator<Attribute> attributes = entityDescriptor.getAttributes();
		while(attributes.hasNext()) {
			
			Attribute attribute = attributes.next();
			WebSiminovData webColumn = generateWebEntityDescriptorColumn(attribute);
			webColumns.addData(webColumn);
			
		}

		
		webEntityDescriptor.addData(webColumns);
		
		
		WebSiminovData webIndexs = new WebSiminovData();
		webIndexs.setDataType(WebEntityDescriptor.INDEXS);
		
		Iterator<Index> indexs = entityDescriptor.getIndexes();
		while(indexs.hasNext()) {
			
			Index index = indexs.next();
			WebSiminovData webIndex = generateWebEntityDescriptorIndex(index);
			webIndexs.addData(webIndex);
		}
			
		webEntityDescriptor.addData(webIndexs);
		
		
		WebSiminovData webRelationships = new WebSiminovData();
		webRelationships.setDataType(WebEntityDescriptor.RELATIONSHIPS);
		
		Iterator<Relationship> relationships = entityDescriptor.getRelationships();
		while(relationships.hasNext()) {
			Relationship relationship = relationships.next();
			
			WebSiminovData webRelationship = generateWebEntityDescriptorRelationship(relationship);
			webRelationships.addData(webRelationship);
		}

		webEntityDescriptor.addData(webRelationships);
			
		return webEntityDescriptor;
	}

	
	/**
	 * Generate Web Entity Descriptor Column.
	 * @param attribute Entity Descriptor Column.
	 * @return Web Siminov Data.
	 */
	public WebSiminovData generateWebEntityDescriptorColumn(final Attribute attribute) {
		
		WebSiminovData webColumn = new WebSiminovData();
		webColumn.setDataType(WebEntityDescriptor.ATTRIBUTE);
		
		WebSiminovValue variableName = new WebSiminovValue();
		variableName.setType(WebEntityDescriptor.VARIABLE_NAME);
		variableName.setValue(attribute.getVariableName());
		
		webColumn.addValue(variableName);
		
		
		WebSiminovValue columnName = new WebSiminovValue();
		columnName.setType(WebEntityDescriptor.COLUMN_NAME);
		columnName.setValue(attribute.getColumnName());
		
		webColumn.addValue(columnName);
		
		
		WebSiminovValue type = new WebSiminovValue();
		type.setType(WebEntityDescriptor.TYPE);
		type.setValue(attribute.getType());
		
		webColumn.addValue(type);
		
		
		WebSiminovValue primaryKey = new WebSiminovValue();
		primaryKey.setType(WebEntityDescriptor.PRIMARY_KEY);
		primaryKey.setValue(Boolean.toString(attribute.isPrimaryKey()));
		
		webColumn.addValue(primaryKey);
		
		
		WebSiminovValue notNull = new WebSiminovValue();
		notNull.setType(WebEntityDescriptor.NOT_NULL);
		notNull.setValue(Boolean.toString(attribute.isNotNull()));
		
		webColumn.addValue(notNull);
		
		
		WebSiminovValue unique = new WebSiminovValue();
		unique.setType(WebEntityDescriptor.UNIQUE);
		unique.setValue(Boolean.toString(attribute.isUnique()));
		
		webColumn.addValue(unique);
		
		
		WebSiminovValue check = new WebSiminovValue();
		check.setType(WebEntityDescriptor.CHECK);
		check.setValue(attribute.getCheck());
		
		webColumn.addValue(check);
		
		
		WebSiminovValue defaultValue = new WebSiminovValue();
		defaultValue.setType(WebEntityDescriptor.DEFAULT);
		defaultValue.setValue(attribute.getDefaultValue());
		
		webColumn.addValue(defaultValue);

		return webColumn;

	}
	
	
	/**
	 * Generate Web Entity Descriptor Index.
	 * @param index Entity Descriptor Index.
	 * @return Web Entity Descriptor Index.
	 */
	public WebSiminovData generateWebEntityDescriptorIndex(final Index index) {
		
		WebSiminovData webIndex = new WebSiminovData();
		webIndex.setDataType(WebEntityDescriptor.INDEX);
		
		WebSiminovValue name = new WebSiminovValue();
		name.setType(WebEntityDescriptor.INDEX_NAME);
		name.setValue(index.getName());
		
		webIndex.addValue(name);
		
		
		WebSiminovValue unique = new WebSiminovValue();
		unique.setType(WebEntityDescriptor.INDEX_UNIQUE);
		unique.setValue(Boolean.toString(index.isUnique()));
		
		webIndex.addValue(unique);
		
		WebSiminovData webIndexColumns = new WebSiminovData();
		
		Iterator<String> columnNames = index.getColumns();
		while(columnNames.hasNext()) {
			WebSiminovValue column = new WebSiminovValue();
			column.setType(WebEntityDescriptor.INDEX_COLUMN);
			column.setValue(columnNames.next());

			webIndexColumns.addValue(column);
			
		}

		return webIndex;
		
	}
	
	
	/**
	 * Generate Web Database Entity Relationship.
	 * @param relationship Entity Descriptor Relationship.
	 * @return Web Entity Descriptor Relationship.
	 */
	public WebSiminovData generateWebEntityDescriptorRelationship(final Relationship relationship) {
		
		WebSiminovData webRelationship = new WebSiminovData();
		webRelationship.setDataType(WebEntityDescriptor.RELATIONSHIP);
		
		WebSiminovValue relationshipType = new WebSiminovValue();
		relationshipType.setType(WebEntityDescriptor.RELATIONSHIP_TYPE);
		relationshipType.setValue(relationship.getType());
		
		webRelationship.addValue(relationshipType);
		
		
		WebSiminovValue refer = new WebSiminovValue();
		refer.setType(WebEntityDescriptor.REFER);
		refer.setValue(relationship.getRefer());
		
		webRelationship.addValue(refer);
		
		
		WebSiminovValue referTo = new WebSiminovValue();
		referTo.setType(WebEntityDescriptor.REFER_TO);
		referTo.setValue(relationship.getReferTo());
		
		webRelationship.addValue(referTo);
		
		
		WebSiminovValue onUpdate = new WebSiminovValue();
		onUpdate.setType(WebEntityDescriptor.ON_UPDATE);
		onUpdate.setValue(relationship.getOnUpdate());
		
		webRelationship.addValue(onUpdate);
		
		
		WebSiminovValue  onDelete = new WebSiminovValue();
		onDelete.setType(WebEntityDescriptor.ON_DELETE);
		onDelete.setValue(relationship.getOnDelete());
		
		webRelationship.addValue(onDelete);
		
		
		WebSiminovValue load = new WebSiminovValue();
		load.setType(WebEntityDescriptor.LOAD);
		load.setValue(Boolean.toString(relationship.isLoad()));
		
		webRelationship.addValue(load);

		return webRelationship;
		
	}

	
	/**
	 * Generate Web Library Descriptor.
	 * @param libraryDescriptor Library Descriptor.
	 * @return Web Library Descriptor.
	 */
	public WebSiminovData generateWebLibraryDescriptor(final siminov.core.model.LibraryDescriptor libraryDescriptor) {
		
		WebSiminovData webLibraryDescriptor = new WebSiminovData();
		webLibraryDescriptor.setDataType(WebLibraryDescriptor.LIBRARY_DESCRIPTOR);
		
		
		WebSiminovValue name = new WebSiminovValue();
		name.setType(WebLibraryDescriptor.NAME);
		name.setValue(libraryDescriptor.getName());
		
		webLibraryDescriptor.addValue(name);
		
		
		WebSiminovValue description = new WebSiminovValue();
		description.setType(WebLibraryDescriptor.DESCRIPTION);
		description.setValue(libraryDescriptor.getDescription());
		
		
		WebSiminovData webDatabaseMappingDescriptorPaths = new WebSiminovData();
		webDatabaseMappingDescriptorPaths.setDataType(WebLibraryDescriptor.ENTITY_DESCRIPTOR_PATHS);
		
		Iterator<String> databaseMappingDescriptorPaths = libraryDescriptor.getEntityDescriptorPaths();
		while(databaseMappingDescriptorPaths.hasNext()) {
			
			WebSiminovValue webDatabaseMappingDescriptorPath = new WebSiminovValue();
			webDatabaseMappingDescriptorPath.setType(WebLibraryDescriptor.ENTITY_DESCRIPTOR_PATH);
			
			webDatabaseMappingDescriptorPaths.addValue(webDatabaseMappingDescriptorPath);
		}
		
		webLibraryDescriptor.addData(webDatabaseMappingDescriptorPaths);
		
		return webLibraryDescriptor;
		
	}

	
	/**
	 * Generate Web Adapter.
	 * @param adapterDescriptor Adapter.
	 * @return Web Adapter.
	 */
	public WebSiminovData generateWebAdapterDescriptor(final AdapterDescriptor adapterDescriptor) {
		
		WebSiminovData webAdapterDescriptor = new WebSiminovData();
		webAdapterDescriptor.setDataType(WebAdapterDescriptor.ADAPTER);
		
		
		WebSiminovValue adapterName = new WebSiminovValue();
		adapterName.setType(WebAdapterDescriptor.NAME);
		adapterName.setValue(adapterDescriptor.getName());
		
		webAdapterDescriptor.addValue(adapterName);
		

		WebSiminovValue adapterDescriptorDescription = new WebSiminovValue();
		adapterDescriptorDescription.setType(WebAdapterDescriptor.DESCRIPTION);
		adapterDescriptorDescription.setValue(adapterDescriptor.getDescription());
		
		webAdapterDescriptor.addValue(adapterDescriptorDescription);
		
		
		WebSiminovValue adapterDescriptorMapTo = new WebSiminovValue();
		adapterDescriptorMapTo.setType(WebAdapterDescriptor.MAP_TO);
		adapterDescriptorMapTo.setValue(adapterDescriptor.getMapTo());
		
		webAdapterDescriptor.addValue(adapterDescriptorMapTo);
		
		
		WebSiminovValue cache = new WebSiminovValue();
		cache.setType(WebAdapterDescriptor.CACHE);
		cache.setValue(Boolean.toString(adapterDescriptor.isCache()));
		
		webAdapterDescriptor.addValue(cache);
		
		
		WebSiminovData webHandlers = new WebSiminovData();
		webHandlers.setDataType(WebAdapterDescriptor.HANDLERS);
		
		Iterator<Handler> handlers = adapterDescriptor.getHandlers();
		while(handlers.hasNext()) {
			webHandlers.addData(generateWebHandler(handlers.next()));
		}
		

		webAdapterDescriptor.addData(webHandlers);
		
		return webAdapterDescriptor;
	}

	
	/**
	 * Generate Web Handler.
	 * @param handler Handler.
	 * @return Web Handler.
	 */
	public WebSiminovData generateWebHandler(final Handler handler) {
		
		WebSiminovData webHandler = new WebSiminovData();
		webHandler.setDataType(WebAdapterDescriptor.HANDLER);
		
		
		WebSiminovValue handlerName = new WebSiminovValue();
		handlerName.setType(WebAdapterDescriptor.HANDLER_NAME);
		handlerName.setValue(handler.getName());
		
		webHandler.addValue(handlerName);
		
		
		WebSiminovValue handlerMapTo = new WebSiminovValue();
		handlerMapTo.setType(WebAdapterDescriptor.HANDLER_MAP_TO);
		handlerMapTo.setValue(handler.getMapTo());
		
		webHandler.addValue(handlerMapTo);
		
		
		WebSiminovValue handlerDescription = new WebSiminovValue();
		handlerDescription.setType(WebAdapterDescriptor.HANDLER_DESCRIPTION);
		handlerDescription.setValue(handler.getDescription());
		
		webHandler.addValue(handlerDescription);
		
		
		WebSiminovData webHandlerParameters = new WebSiminovData();
		webHandlerParameters.setDataType(WebAdapterDescriptor.HANDLER_PARAMETERS);
		
		Iterator<Parameter> parameters = handler.getParameters();
		while(parameters.hasNext())  {
			
			Parameter parameter = parameters.next();
			
			WebSiminovData webHandlerParameter = new WebSiminovData();
			webHandlerParameter.setDataType(WebAdapterDescriptor.HANDLER_PARAMETER);
			
			
			WebSiminovValue parameterType = new WebSiminovValue();
			parameterType.setType(WebAdapterDescriptor.HANDLER_PARAMETER_TYPE);
			parameterType.setValue(parameter.getType());
			
			webHandlerParameter.addValue(parameterType);
			
			
			WebSiminovValue parameterDescription = new WebSiminovValue();
			parameterDescription.setType(WebAdapterDescriptor.HANDLER_PARAMETER_DESCRIPTION);
			parameterDescription.setValue(parameter.getDescription());
			
			webHandlerParameter.addValue(parameterDescription);
			
			webHandlerParameters.addData(webHandlerParameter);
			
		}
		
		webHandler.addData(webHandlerParameters);

		Return returnType = handler.getReturn();
		if(returnType != null) {

			WebSiminovData webReturn = new WebSiminovData();
			webReturn.setDataType(WebAdapterDescriptor.HANDLER_RETURN);
			
			
			WebSiminovValue webReturnType = new WebSiminovValue();
			webReturnType.setType(WebAdapterDescriptor.HANDLER_RETURN_TYPE);
			webReturnType.setValue(returnType.getType());
			
			webReturn.addValue(webReturnType);
					
			
			WebSiminovValue webReturnDescription = new WebSiminovValue();
			webReturnDescription.setType(WebAdapterDescriptor.HANDLER_RETURN_DESCRIPTION);
			webReturnDescription.setValue(returnType.getDescription());
			
			webReturn.addValue(webReturnDescription);
			
			webHandler.addData(webReturn);
		}

		return webHandler;
	}
	
	public WebSiminovData generateWebConnectionRequest(final IConnectionRequest connectionRequest) {
		
		WebSiminovData webConnectionRequest = new WebSiminovData();
		webConnectionRequest.setDataType(WebConnectionRequest.CONNECTION_REQUEST);
		
		WebSiminovValue webUrl = new WebSiminovValue();
		webUrl.setType(WebConnectionRequest.URL);
		webUrl.setValue(connectionRequest.getUrl());
		
		webConnectionRequest.addValue(webUrl);
		
		
		WebSiminovValue webProtocol = new WebSiminovValue();
		webProtocol.setType(WebConnectionRequest.PROTOCOL);
		webProtocol.setValue(connectionRequest.getProtocol());
		
		webConnectionRequest.addValue(webProtocol);
		
		
		WebSiminovValue webType = new WebSiminovValue();
		webType.setType(WebConnectionRequest.TYPE);
		webType.setValue(connectionRequest.getType());
		
		webConnectionRequest.addValue(webType);
		
		WebSiminovValue webDataStream = new WebSiminovValue();
		webDataStream.setType(WebConnectionRequest.DATA_STREAM);
		webDataStream.setValue(new String(connectionRequest.getDataStream()));
		
		webConnectionRequest.addValue(webDataStream);

		
		WebSiminovData webQueryParameters = new WebSiminovData();
		webQueryParameters.setDataType(WebConnectionRequest.QUERY_PARAMETERS_TYPE);

		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		while(queryParameters.hasNext()) {

			String queryParameterKey = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(queryParameterKey);
			
			WebSiminovData webQueryParameter = new WebSiminovData();
			webQueryParameter.setDataType(WebConnectionRequest.QUERY_PARAMETER_TYPE);
			
			/*
			 * Web Query Parameter Name
			 */
			WebSiminovValue webQueryParameterName = new WebSiminovValue();
			webQueryParameterName.setType(WebConnectionRequest.QUERY_PARAMETER_NAME);
			webQueryParameterName.setValue(queryParameter.getName());
			
			webQueryParameter.addValue(webQueryParameterName);

			/*
			 * Web Query Parameter Value
			 */
			WebSiminovValue webQueryParameterValue = new WebSiminovValue();
			webQueryParameterValue.setType(WebConnectionRequest.QUERY_PARAMETER_VALUE);
			webQueryParameterValue.setValue(queryParameter.getValue());
			
			webQueryParameter.addValue(webQueryParameterValue);
			
			webQueryParameters.addData(webQueryParameter);
		}
		
		webConnectionRequest.addData(webQueryParameters);
		
		
		WebSiminovData webHeaderParameters = new WebSiminovData();
		webHeaderParameters.setDataType(WebConnectionRequest.HEADER_PARAMETERS_TYPE);

		
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		while(headerParameters.hasNext()) {

			String headerParameterKey = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(headerParameterKey);
			
			WebSiminovData webHeaderParameter = new WebSiminovData();
			webHeaderParameter.setDataType(WebConnectionRequest.HEADER_PARAMETER_TYPE);
			
			/*
			 * Web Header Parameter Name
			 */
			WebSiminovValue webHeaderParameterName = new WebSiminovValue();
			webHeaderParameterName.setType(WebConnectionRequest.HEADER_PARAMETER_NAME);
			webHeaderParameterName.setValue(headerParameter.getName());
			
			webHeaderParameter.addValue(webHeaderParameterName);

			/*
			 * Web Query Parameter Value
			 */
			WebSiminovValue webQueryParameterValue = new WebSiminovValue();
			webQueryParameterValue.setType(WebConnectionRequest.QUERY_PARAMETER_NAME);
			webQueryParameterValue.setValue(headerParameter.getName());
			
			webHeaderParameter.addValue(webQueryParameterValue);
			
			webQueryParameters.addData(webHeaderParameter);
		}
		
		webConnectionRequest.addData(webHeaderParameters);

		return webConnectionRequest;
	}


	public WebSiminovData generateWebConnectionResponse(final IConnectionResponse connectionResponse) {
		
		WebSiminovData webConnectionResponse = new WebSiminovData();
		webConnectionResponse.setDataType(WebConnectionResponse.CONNECTION_RESPONSE);
		
		WebSiminovValue webStatusCode = new WebSiminovValue();
		webStatusCode.setType(WebConnectionResponse.STATUS_CODE);
		webStatusCode.setValue(String.valueOf(connectionResponse.getStatusCode()));
		
		webConnectionResponse.addValue(webStatusCode);
		
		
		WebSiminovValue webStatusMessage = new WebSiminovValue();
		webStatusMessage.setType(WebConnectionResponse.STATUS_MESSAGE);
		webStatusMessage.setValue(connectionResponse.getStatusMessage());
		
		webConnectionResponse.addValue(webStatusMessage);
		
		
		WebSiminovValue webResponse = new WebSiminovValue();
		webResponse.setType(WebConnectionResponse.RESPONSE);
		
		try {
			webResponse.setValue(URLEncoder.encode(Utils.toString(connectionResponse.getResponse())));
		} catch(SiminovException se) {
			Log.debug(ResourceManager.class.getName(), "generateWebConnectionRequest", "Siminov Exception caught while converting connection response input stream to string, " + se.getMessage());
		}
		
		webConnectionResponse.addValue(webResponse);
		
		return webConnectionResponse;
	}
	
	public WebSiminovData generateWebRegistration(final IRegistration registration) {
		
		WebSiminovData webRegistration = new WebSiminovData();
		webRegistration.setDataType(WebRegistration.REGISTRATION);
		
		WebSiminovValue webRegistrationId = new WebSiminovValue();
		webRegistrationId.setType(WebRegistration.REGISTRATION_ID);
		webRegistrationId.setValue(registration.getRegistrationId());

		webRegistration.addValue(webRegistrationId);
		
		return webRegistration;
	}
	
	public WebSiminovData generateWebMessage(final IMessage message) {

		WebSiminovData webMessage = new WebSiminovData();
		webMessage.setDataType(WebMessage.MESSAGE);
		
		WebSiminovValue webMsg = new WebSiminovValue();
		webMsg.setType(WebMessage.MSG);
		webMsg.setValue(message.getMessage());
		
		webMessage.addValue(webMsg);
		
		return webMessage;
	}
	
	
	public WebSiminovData generateWebNotificationException(final NotificationException notificationException) {

		WebSiminovData webNotificationException = new WebSiminovData();
		webNotificationException.setDataType(WebNotificationException.NOTIFICATION_EXCEPTION);
		
		WebSiminovValue webClassName = new WebSiminovValue();
		webClassName.setType(WebNotificationException.CLASS_NAME);
		webClassName.setValue(notificationException.getClassName());
		
		webNotificationException.addValue(webClassName);
		
		
		WebSiminovValue webMethodName = new WebSiminovValue();
		webMethodName.setType(WebNotificationException.METHOD_NAME);
		webMethodName.setValue(notificationException.getMessage());
		
		webNotificationException.addValue(webMethodName);

		
		WebSiminovValue webMessage = new WebSiminovValue();
		webMessage.setType(WebNotificationException.MESSAGE);
		webMessage.setValue(notificationException.getMessage());
		
		webNotificationException.addValue(webMessage);

		return webNotificationException;
	}
	
	
	public WebSiminovData generateWebSyncRequest(ISyncRequest syncRequest) {
		
		WebSiminovData webSyncRequest = new WebSiminovData();
		webSyncRequest.setDataType(WebSyncRequest.SYNC_REQUEST);
		
		WebSiminovValue webUrl = new WebSiminovValue();
		webUrl.setType(WebSyncRequest.NAME);
		webUrl.setValue(syncRequest.getName());
		
		webSyncRequest.addValue(webUrl);
		return webSyncRequest;
	}
}
