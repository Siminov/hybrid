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



package siminov.hybrid.resource;

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
import siminov.hybrid.adapter.AdapterFactory;
import siminov.hybrid.adapter.AdapterHandler;
import siminov.hybrid.adapter.constants.HybridAdapterDescriptor;
import siminov.hybrid.adapter.constants.HybridConnectionRequest;
import siminov.hybrid.adapter.constants.HybridConnectionResponse;
import siminov.hybrid.adapter.constants.HybridDatabaseDescriptor;
import siminov.hybrid.adapter.constants.HybridEntityDescriptor;
import siminov.hybrid.adapter.constants.HybridLibraryDescriptor;
import siminov.hybrid.adapter.constants.HybridMessage;
import siminov.hybrid.adapter.constants.HybridNotificationException;
import siminov.hybrid.adapter.constants.HybridRegistration;
import siminov.hybrid.adapter.constants.HybridSyncRequest;
import siminov.hybrid.events.EventHandler;
import siminov.hybrid.model.AdapterDescriptor;
import siminov.hybrid.model.AdapterDescriptor.Handler;
import siminov.hybrid.model.AdapterDescriptor.Handler.Parameter;
import siminov.hybrid.model.AdapterDescriptor.Handler.Return;
import siminov.hybrid.model.ApplicationDescriptor;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import android.app.Activity;
import android.webkit.WebView;

/**
 * It handles and provides all resources needed by SIMINOV HYBRID.
 * <p>
 * Such As: Provides HybridDescriptor, AdapterDescriptor.
 */
public class ResourceManager {

	private static ResourceManager hybridResources = null;
	private siminov.core.resource.ResourceManager coreResourceManager = null;
	
	private ApplicationDescriptor applicationDescriptor = null;
	
	private WebView webView = null;
	private Activity webActivity = null;
	
	private EventHandler eventHandler = null;
	
	private AdapterHandler adapterHandler = null;
	private AdapterFactory adapterFactory = null;

	private Map<String, String> hybridNativeClassMapping = new ConcurrentHashMap<String, String>();
	
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
		
		if(hybridResources == null) {
			hybridResources = new ResourceManager();
		}
		
		return hybridResources;
	}

	
	/**
	 * Get Application Descriptor.
	 * @return Application Descriptor.
	 */
	public ApplicationDescriptor getApplicationDescriptor() {
		return this.applicationDescriptor;
	}
	
	/**
	 * Set Application Descriptor.
	 * @param applicationDescriptor Application Descriptor.
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
	 * Get Database Descriptor based on Hybrid model class name.
	 * @param className Name of Hybrid Model Class.
	 * @return Database Descriptor.
	 */
	public DatabaseDescriptor getDatabaseDescriptorBasedOnClassName(String className) {

		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = hybridNativeClassMapping.get(className);
		return coreResourceManager.getDatabaseDescriptorBasedOnClassName(nativeClassName);
		
	}
	
	/**
	 * Get Database Descriptor Name based on Hybrid Model class name.
	 * @param className Name of Hybrid Model Class.
	 * @return Database Descriptor Name.
	 */
	public String getDatabaseDescriptorNameBasedOnClassName(String className) {
	
		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = hybridNativeClassMapping.get(className);
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
	 * Get Entity Descriptor based on Hybrid Model Class Name.
	 * @param className Name of Hybrid Model Class.
	 * @return Entity Descriptor.
	 */
	public EntityDescriptor getEntityDescriptorBasedOnClassName(String className) {
		
		className = className.substring(className.lastIndexOf(".") + 1, className.length());

		String nativeClassName = hybridNativeClassMapping.get(className);
		if(nativeClassName == null ||nativeClassName.length() <= 0) {
			EntityDescriptor entityDescriptor = coreResourceManager.requiredEntityDescriptorBasedOnClassName(className);
			synchronizeEntityDescriptors();
			
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
	 * @param hybridClassName Hybrid Model Class Name.
	 * @return Native Model Class Name.
	 */
	public String getMappedNativeClassName(String hybridClassName) {
		hybridClassName = hybridClassName.substring(hybridClassName.lastIndexOf(".") + 1, hybridClassName.length());
		return hybridNativeClassMapping.get(hybridClassName);
	}
	
	/**
	 * Get Mapped Hybrid Model Class Name.
	 * @param nativeClassName Native Model Class Name.
	 * @return Hybrid Model Class Name.
	 */
	public String getMappedHybridClassName(final String nativeClassName) {
		Iterator<String> keys = hybridNativeClassMapping.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = hybridNativeClassMapping.get(key);
			
			if(value.equalsIgnoreCase(nativeClassName)) {
				return key;
			}
			
		}
		
		return null;
	}
	
	/**
	 * Map Native Model Class Name with Hybrid Model Class Name.
	 */
	public void synchronizeEntityDescriptors() {

		Iterator<EntityDescriptor> entityDescriptors = coreResourceManager.getEntityDescriptors();
		while(entityDescriptors.hasNext()) {
			EntityDescriptor entityDescriptor = entityDescriptors.next();
			
			String nativeClassName = entityDescriptor.getClassName();
			String hybridClassName = nativeClassName.substring(nativeClassName.lastIndexOf(".") + 1, nativeClassName.length());
			
			hybridNativeClassMapping.put(hybridClassName, nativeClassName);
			
		}
	}

	
	/**
	 * Generates Hybrid Database Descriptor
	 * @param databaseDescriptor Database Descriptor.
	 * @return Hybrid Application Descriptor. 
	 */
	public HybridSiminovData generateHybridDatabaseDescriptor(DatabaseDescriptor databaseDescriptor) {
		
		HybridSiminovData hybridDatabaseDescriptor = new HybridSiminovData();
		hybridDatabaseDescriptor.setDataType(HybridDatabaseDescriptor.DATABASE_DESCRIPTOR);
		
		HybridSiminovValue databaseName = new HybridSiminovValue();
		databaseName.setType(HybridDatabaseDescriptor.NAME);
		databaseName.setValue(databaseDescriptor.getDatabaseName());

		hybridDatabaseDescriptor.addValue(databaseName);
		
		HybridSiminovValue databaseDescription = new HybridSiminovValue();
		databaseDescription.setType(HybridDatabaseDescriptor.DESCRIPTION);
		databaseDescription.setValue(databaseDescriptor.getDescription());
		
		hybridDatabaseDescriptor.addValue(databaseDescription);
		
		HybridSiminovValue isLockingRequired = new HybridSiminovValue();
		isLockingRequired.setType(HybridDatabaseDescriptor.IS_TRANSACTION_SAFE);
		isLockingRequired.setValue(Boolean.toString(databaseDescriptor.isTransactionSafe()));
		
		hybridDatabaseDescriptor.addValue(isLockingRequired);
		
		HybridSiminovValue externalStorage = new HybridSiminovValue();
		externalStorage.setType(HybridDatabaseDescriptor.EXTERNAL_STORAGE);
		externalStorage.setValue(Boolean.toString(databaseDescriptor.isExternalStorageEnable()));
		
		hybridDatabaseDescriptor.addValue(externalStorage);
		
		HybridSiminovData hybridEntityDescriptorPaths = new HybridSiminovData();
		hybridEntityDescriptorPaths.setDataType(HybridDatabaseDescriptor.ENTITY_DESCRIPTORS);
		
		Iterator<String> entityDescriptors = databaseDescriptor.getEntityDescriptorPaths();
		while(entityDescriptors.hasNext()) {
			
			HybridSiminovValue entityDescriptorPath = new HybridSiminovValue();
			entityDescriptorPath.setType(HybridDatabaseDescriptor.ENTITY_DESCRIPTOR_PATH);
			entityDescriptorPath.setValue(entityDescriptors.next());
			
			hybridEntityDescriptorPaths.addValue(entityDescriptorPath);
			
		}
		
		hybridDatabaseDescriptor.addData(hybridEntityDescriptorPaths);
		
		return hybridDatabaseDescriptor;
	}

	
	/**
	 * Generate Hybrid Entity Descriptor.
	 * @param entityDescriptor Entity Descriptor.
	 * @return Hybrid Entity Descriptor.
	 */
	public HybridSiminovData generateHybridEntityDescriptor(final EntityDescriptor entityDescriptor) {
		
		HybridSiminovData hybridEntityDescriptor = new HybridSiminovData();
		hybridEntityDescriptor.setDataType(HybridEntityDescriptor.ENTITY_DESCRIPTOR);
		
		
		HybridSiminovValue tableName = new HybridSiminovValue();
		tableName.setType(HybridEntityDescriptor.TABLE_NAME);
		tableName.setValue(entityDescriptor.getTableName());
		
		hybridEntityDescriptor.addValue(tableName);
		
		
		HybridSiminovValue className = new HybridSiminovValue();
		className.setType(HybridEntityDescriptor.CLASS_NAME);
		className.setValue(entityDescriptor.getClassName());
		
		hybridEntityDescriptor.addValue(className);
		
		
		HybridSiminovData hybridColumns = new HybridSiminovData();
		hybridColumns.setDataType(HybridEntityDescriptor.ENTITIES);
		
		Iterator<Attribute> attributes = entityDescriptor.getAttributes();
		while(attributes.hasNext()) {
			
			Attribute attribute = attributes.next();
			HybridSiminovData hybridColumn = generateHybridEntityDescriptorColumn(attribute);
			hybridColumns.addData(hybridColumn);
			
		}

		
		hybridEntityDescriptor.addData(hybridColumns);
		
		
		HybridSiminovData hybridIndexs = new HybridSiminovData();
		hybridIndexs.setDataType(HybridEntityDescriptor.INDEXS);
		
		Iterator<Index> indexs = entityDescriptor.getIndexes();
		while(indexs.hasNext()) {
			
			Index index = indexs.next();
			HybridSiminovData hybridIndex = generateHybridEntityDescriptorIndex(index);
			hybridIndexs.addData(hybridIndex);
		}
			
		hybridEntityDescriptor.addData(hybridIndexs);
		
		
		HybridSiminovData hybridRelationships = new HybridSiminovData();
		hybridRelationships.setDataType(HybridEntityDescriptor.RELATIONSHIPS);
		
		Iterator<Relationship> relationships = entityDescriptor.getRelationships();
		while(relationships.hasNext()) {
			Relationship relationship = relationships.next();
			
			HybridSiminovData hybridRelationship = generateHybridEntityDescriptorRelationship(relationship);
			hybridRelationships.addData(hybridRelationship);
		}

		hybridEntityDescriptor.addData(hybridRelationships);
			
		return hybridEntityDescriptor;
	}

	
	/**
	 * Generate Hybrid Entity Descriptor Column.
	 * @param attribute Entity Descriptor Column.
	 * @return Hybrid Siminov Data.
	 */
	public HybridSiminovData generateHybridEntityDescriptorColumn(final Attribute attribute) {
		
		HybridSiminovData hybridColumn = new HybridSiminovData();
		hybridColumn.setDataType(HybridEntityDescriptor.ATTRIBUTE);
		
		HybridSiminovValue variableName = new HybridSiminovValue();
		variableName.setType(HybridEntityDescriptor.VARIABLE_NAME);
		variableName.setValue(attribute.getVariableName());
		
		hybridColumn.addValue(variableName);
		
		
		HybridSiminovValue columnName = new HybridSiminovValue();
		columnName.setType(HybridEntityDescriptor.COLUMN_NAME);
		columnName.setValue(attribute.getColumnName());
		
		hybridColumn.addValue(columnName);
		
		
		HybridSiminovValue type = new HybridSiminovValue();
		type.setType(HybridEntityDescriptor.TYPE);
		type.setValue(attribute.getType());
		
		hybridColumn.addValue(type);
		
		
		HybridSiminovValue primaryKey = new HybridSiminovValue();
		primaryKey.setType(HybridEntityDescriptor.PRIMARY_KEY);
		primaryKey.setValue(Boolean.toString(attribute.isPrimaryKey()));
		
		hybridColumn.addValue(primaryKey);
		
		
		HybridSiminovValue notNull = new HybridSiminovValue();
		notNull.setType(HybridEntityDescriptor.NOT_NULL);
		notNull.setValue(Boolean.toString(attribute.isNotNull()));
		
		hybridColumn.addValue(notNull);
		
		
		HybridSiminovValue unique = new HybridSiminovValue();
		unique.setType(HybridEntityDescriptor.UNIQUE);
		unique.setValue(Boolean.toString(attribute.isUnique()));
		
		hybridColumn.addValue(unique);
		
		
		HybridSiminovValue check = new HybridSiminovValue();
		check.setType(HybridEntityDescriptor.CHECK);
		check.setValue(attribute.getCheck());
		
		hybridColumn.addValue(check);
		
		
		HybridSiminovValue defaultValue = new HybridSiminovValue();
		defaultValue.setType(HybridEntityDescriptor.DEFAULT);
		defaultValue.setValue(attribute.getDefaultValue());
		
		hybridColumn.addValue(defaultValue);

		return hybridColumn;

	}
	
	
	/**
	 * Generate Hybrid Entity Descriptor Index.
	 * @param index Entity Descriptor Index.
	 * @return Hybrid Entity Descriptor Index.
	 */
	public HybridSiminovData generateHybridEntityDescriptorIndex(final Index index) {
		
		HybridSiminovData hybridIndex = new HybridSiminovData();
		hybridIndex.setDataType(HybridEntityDescriptor.INDEX);
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setType(HybridEntityDescriptor.INDEX_NAME);
		name.setValue(index.getName());
		
		hybridIndex.addValue(name);
		
		
		HybridSiminovValue unique = new HybridSiminovValue();
		unique.setType(HybridEntityDescriptor.INDEX_UNIQUE);
		unique.setValue(Boolean.toString(index.isUnique()));
		
		hybridIndex.addValue(unique);
		
		HybridSiminovData hybridIndexColumns = new HybridSiminovData();
		
		Iterator<String> columnNames = index.getColumns();
		while(columnNames.hasNext()) {
			HybridSiminovValue column = new HybridSiminovValue();
			column.setType(HybridEntityDescriptor.INDEX_COLUMN);
			column.setValue(columnNames.next());

			hybridIndexColumns.addValue(column);
			
		}

		return hybridIndex;
		
	}
	
	
	/**
	 * Generate Hybrid Database Entity Relationship.
	 * @param relationship Entity Descriptor Relationship.
	 * @return Hybrid Entity Descriptor Relationship.
	 */
	public HybridSiminovData generateHybridEntityDescriptorRelationship(final Relationship relationship) {
		
		HybridSiminovData hybridRelationship = new HybridSiminovData();
		hybridRelationship.setDataType(HybridEntityDescriptor.RELATIONSHIP);
		
		HybridSiminovValue relationshipType = new HybridSiminovValue();
		relationshipType.setType(HybridEntityDescriptor.RELATIONSHIP_TYPE);
		relationshipType.setValue(relationship.getType());
		
		hybridRelationship.addValue(relationshipType);
		
		
		HybridSiminovValue refer = new HybridSiminovValue();
		refer.setType(HybridEntityDescriptor.REFER);
		refer.setValue(relationship.getRefer());
		
		hybridRelationship.addValue(refer);
		
		
		HybridSiminovValue referTo = new HybridSiminovValue();
		referTo.setType(HybridEntityDescriptor.REFER_TO);
		referTo.setValue(relationship.getReferTo());
		
		hybridRelationship.addValue(referTo);
		
		
		HybridSiminovValue onUpdate = new HybridSiminovValue();
		onUpdate.setType(HybridEntityDescriptor.ON_UPDATE);
		onUpdate.setValue(relationship.getOnUpdate());
		
		hybridRelationship.addValue(onUpdate);
		
		
		HybridSiminovValue  onDelete = new HybridSiminovValue();
		onDelete.setType(HybridEntityDescriptor.ON_DELETE);
		onDelete.setValue(relationship.getOnDelete());
		
		hybridRelationship.addValue(onDelete);
		
		
		HybridSiminovValue load = new HybridSiminovValue();
		load.setType(HybridEntityDescriptor.LOAD);
		load.setValue(Boolean.toString(relationship.isLoad()));
		
		hybridRelationship.addValue(load);

		return hybridRelationship;
		
	}

	
	/**
	 * Generate Hybrid Library Descriptor.
	 * @param libraryDescriptor Library Descriptor.
	 * @return Hybrid Library Descriptor.
	 */
	public HybridSiminovData generateHybridLibraryDescriptor(final siminov.core.model.LibraryDescriptor libraryDescriptor) {
		
		HybridSiminovData hybridLibraryDescriptor = new HybridSiminovData();
		hybridLibraryDescriptor.setDataType(HybridLibraryDescriptor.LIBRARY_DESCRIPTOR);
		
		
		HybridSiminovValue name = new HybridSiminovValue();
		name.setType(HybridLibraryDescriptor.NAME);
		name.setValue(libraryDescriptor.getName());
		
		hybridLibraryDescriptor.addValue(name);
		
		
		HybridSiminovValue description = new HybridSiminovValue();
		description.setType(HybridLibraryDescriptor.DESCRIPTION);
		description.setValue(libraryDescriptor.getDescription());
		
		
		HybridSiminovData hybridDatabaseMappingDescriptorPaths = new HybridSiminovData();
		hybridDatabaseMappingDescriptorPaths.setDataType(HybridLibraryDescriptor.ENTITY_DESCRIPTOR_PATHS);
		
		Iterator<String> databaseMappingDescriptorPaths = libraryDescriptor.getEntityDescriptorPaths();
		while(databaseMappingDescriptorPaths.hasNext()) {
			
			HybridSiminovValue hybridDatabaseMappingDescriptorPath = new HybridSiminovValue();
			hybridDatabaseMappingDescriptorPath.setType(HybridLibraryDescriptor.ENTITY_DESCRIPTOR_PATH);
			
			hybridDatabaseMappingDescriptorPaths.addValue(hybridDatabaseMappingDescriptorPath);
		}
		
		hybridLibraryDescriptor.addData(hybridDatabaseMappingDescriptorPaths);
		
		return hybridLibraryDescriptor;
		
	}

	
	/**
	 * Generate Hybrid Adapter.
	 * @param adapterDescriptor Adapter.
	 * @return Hybrid Adapter.
	 */
	public HybridSiminovData generateHybridAdapterDescriptor(final AdapterDescriptor adapterDescriptor) {
		
		HybridSiminovData hybridAdapterDescriptor = new HybridSiminovData();
		hybridAdapterDescriptor.setDataType(HybridAdapterDescriptor.ADAPTER);
		
		
		HybridSiminovValue adapterName = new HybridSiminovValue();
		adapterName.setType(HybridAdapterDescriptor.NAME);
		adapterName.setValue(adapterDescriptor.getName());
		
		hybridAdapterDescriptor.addValue(adapterName);
		

		HybridSiminovValue adapterDescriptorDescription = new HybridSiminovValue();
		adapterDescriptorDescription.setType(HybridAdapterDescriptor.DESCRIPTION);
		adapterDescriptorDescription.setValue(adapterDescriptor.getDescription());
		
		hybridAdapterDescriptor.addValue(adapterDescriptorDescription);
		
		
		HybridSiminovValue adapterDescriptorMapTo = new HybridSiminovValue();
		adapterDescriptorMapTo.setType(HybridAdapterDescriptor.MAP_TO);
		adapterDescriptorMapTo.setValue(adapterDescriptor.getMapTo());
		
		hybridAdapterDescriptor.addValue(adapterDescriptorMapTo);
		
		
		HybridSiminovValue cache = new HybridSiminovValue();
		cache.setType(HybridAdapterDescriptor.CACHE);
		cache.setValue(Boolean.toString(adapterDescriptor.isCache()));
		
		hybridAdapterDescriptor.addValue(cache);
		
		
		HybridSiminovData hybridHandlers = new HybridSiminovData();
		hybridHandlers.setDataType(HybridAdapterDescriptor.HANDLERS);
		
		Iterator<Handler> handlers = adapterDescriptor.getHandlers();
		while(handlers.hasNext()) {
			hybridHandlers.addData(generateHybridHandler(handlers.next()));
		}
		

		hybridAdapterDescriptor.addData(hybridHandlers);
		
		return hybridAdapterDescriptor;
	}

	
	/**
	 * Generate Hybrid Handler.
	 * @param handler Handler.
	 * @return Hybrid Handler.
	 */
	public HybridSiminovData generateHybridHandler(final Handler handler) {
		
		HybridSiminovData hybridHandler = new HybridSiminovData();
		hybridHandler.setDataType(HybridAdapterDescriptor.HANDLER);
		
		
		HybridSiminovValue handlerName = new HybridSiminovValue();
		handlerName.setType(HybridAdapterDescriptor.HANDLER_NAME);
		handlerName.setValue(handler.getName());
		
		hybridHandler.addValue(handlerName);
		
		
		HybridSiminovValue handlerMapTo = new HybridSiminovValue();
		handlerMapTo.setType(HybridAdapterDescriptor.HANDLER_MAP_TO);
		handlerMapTo.setValue(handler.getMapTo());
		
		hybridHandler.addValue(handlerMapTo);
		
		
		HybridSiminovValue handlerDescription = new HybridSiminovValue();
		handlerDescription.setType(HybridAdapterDescriptor.HANDLER_DESCRIPTION);
		handlerDescription.setValue(handler.getDescription());
		
		hybridHandler.addValue(handlerDescription);
		
		
		HybridSiminovData hybridHandlerParameters = new HybridSiminovData();
		hybridHandlerParameters.setDataType(HybridAdapterDescriptor.HANDLER_PARAMETERS);
		
		Iterator<Parameter> parameters = handler.getParameters();
		while(parameters.hasNext())  {
			
			Parameter parameter = parameters.next();
			
			HybridSiminovData hybridHandlerParameter = new HybridSiminovData();
			hybridHandlerParameter.setDataType(HybridAdapterDescriptor.HANDLER_PARAMETER);
			
			
			HybridSiminovValue parameterType = new HybridSiminovValue();
			parameterType.setType(HybridAdapterDescriptor.HANDLER_PARAMETER_TYPE);
			parameterType.setValue(parameter.getType());
			
			hybridHandlerParameter.addValue(parameterType);
			
			
			HybridSiminovValue parameterDescription = new HybridSiminovValue();
			parameterDescription.setType(HybridAdapterDescriptor.HANDLER_PARAMETER_DESCRIPTION);
			parameterDescription.setValue(parameter.getDescription());
			
			hybridHandlerParameter.addValue(parameterDescription);
			
			hybridHandlerParameters.addData(hybridHandlerParameter);
			
		}
		
		hybridHandler.addData(hybridHandlerParameters);

		Return returnType = handler.getReturn();
		if(returnType != null) {

			HybridSiminovData hybridReturn = new HybridSiminovData();
			hybridReturn.setDataType(HybridAdapterDescriptor.HANDLER_RETURN);
			
			
			HybridSiminovValue hybridReturnType = new HybridSiminovValue();
			hybridReturnType.setType(HybridAdapterDescriptor.HANDLER_RETURN_TYPE);
			hybridReturnType.setValue(returnType.getType());
			
			hybridReturn.addValue(hybridReturnType);
					
			
			HybridSiminovValue hybridReturnDescription = new HybridSiminovValue();
			hybridReturnDescription.setType(HybridAdapterDescriptor.HANDLER_RETURN_DESCRIPTION);
			hybridReturnDescription.setValue(returnType.getDescription());
			
			hybridReturn.addValue(hybridReturnDescription);
			
			hybridHandler.addData(hybridReturn);
		}

		return hybridHandler;
	}
	
	public HybridSiminovData generateHybridConnectionRequest(final IConnectionRequest connectionRequest) {
		
		HybridSiminovData hybridConnectionRequest = new HybridSiminovData();
		hybridConnectionRequest.setDataType(HybridConnectionRequest.CONNECTION_REQUEST);
		
		HybridSiminovValue hybridUrl = new HybridSiminovValue();
		hybridUrl.setType(HybridConnectionRequest.URL);
		hybridUrl.setValue(connectionRequest.getUrl());
		
		hybridConnectionRequest.addValue(hybridUrl);
		
		
		HybridSiminovValue hybridProtocol = new HybridSiminovValue();
		hybridProtocol.setType(HybridConnectionRequest.PROTOCOL);
		hybridProtocol.setValue(connectionRequest.getProtocol());
		
		hybridConnectionRequest.addValue(hybridProtocol);
		
		
		HybridSiminovValue hybridType = new HybridSiminovValue();
		hybridType.setType(HybridConnectionRequest.TYPE);
		hybridType.setValue(connectionRequest.getType());
		
		hybridConnectionRequest.addValue(hybridType);
		
		HybridSiminovValue hybridDataStream = new HybridSiminovValue();
		hybridDataStream.setType(HybridConnectionRequest.DATA_STREAM);
		hybridDataStream.setValue(new String(connectionRequest.getDataStream()));
		
		hybridConnectionRequest.addValue(hybridDataStream);

		
		HybridSiminovData hybridQueryParameters = new HybridSiminovData();
		hybridQueryParameters.setDataType(HybridConnectionRequest.QUERY_PARAMETERS_TYPE);

		
		Iterator<String> queryParameters = connectionRequest.getQueryParameters();
		while(queryParameters.hasNext()) {

			String queryParameterKey = queryParameters.next();
			QueryParameter queryParameter = connectionRequest.getQueryParameter(queryParameterKey);
			
			HybridSiminovData hybridQueryParameter = new HybridSiminovData();
			hybridQueryParameter.setDataType(HybridConnectionRequest.QUERY_PARAMETER_TYPE);
			
			/*
			 * Hybrid Query Parameter Name
			 */
			HybridSiminovValue hybridQueryParameterName = new HybridSiminovValue();
			hybridQueryParameterName.setType(HybridConnectionRequest.QUERY_PARAMETER_NAME);
			hybridQueryParameterName.setValue(queryParameter.getName());
			
			hybridQueryParameter.addValue(hybridQueryParameterName);

			/*
			 * Hybrid Query Parameter Value
			 */
			HybridSiminovValue hybridQueryParameterValue = new HybridSiminovValue();
			hybridQueryParameterValue.setType(HybridConnectionRequest.QUERY_PARAMETER_VALUE);
			hybridQueryParameterValue.setValue(queryParameter.getValue());
			
			hybridQueryParameter.addValue(hybridQueryParameterValue);
			
			hybridQueryParameters.addData(hybridQueryParameter);
		}
		
		hybridConnectionRequest.addData(hybridQueryParameters);
		
		
		HybridSiminovData hybridHeaderParameters = new HybridSiminovData();
		hybridHeaderParameters.setDataType(HybridConnectionRequest.HEADER_PARAMETERS_TYPE);

		
		Iterator<String> headerParameters = connectionRequest.getHeaderParameters();
		while(headerParameters.hasNext()) {

			String headerParameterKey = headerParameters.next();
			HeaderParameter headerParameter = connectionRequest.getHeaderParameter(headerParameterKey);
			
			HybridSiminovData hybridHeaderParameter = new HybridSiminovData();
			hybridHeaderParameter.setDataType(HybridConnectionRequest.HEADER_PARAMETER_TYPE);
			
			/*
			 * Hybrid Header Parameter Name
			 */
			HybridSiminovValue hybridHeaderParameterName = new HybridSiminovValue();
			hybridHeaderParameterName.setType(HybridConnectionRequest.HEADER_PARAMETER_NAME);
			hybridHeaderParameterName.setValue(headerParameter.getName());
			
			hybridHeaderParameter.addValue(hybridHeaderParameterName);

			/*
			 * Hybrid Query Parameter Value
			 */
			HybridSiminovValue hybridQueryParameterValue = new HybridSiminovValue();
			hybridQueryParameterValue.setType(HybridConnectionRequest.QUERY_PARAMETER_NAME);
			hybridQueryParameterValue.setValue(headerParameter.getName());
			
			hybridHeaderParameter.addValue(hybridQueryParameterValue);
			
			hybridQueryParameters.addData(hybridHeaderParameter);
		}
		
		hybridConnectionRequest.addData(hybridHeaderParameters);

		return hybridConnectionRequest;
	}


	public HybridSiminovData generateHybridConnectionResponse(final IConnectionResponse connectionResponse) {
		
		HybridSiminovData hybridConnectionResponse = new HybridSiminovData();
		hybridConnectionResponse.setDataType(HybridConnectionResponse.CONNECTION_RESPONSE);
		
		HybridSiminovValue hybridStatusCode = new HybridSiminovValue();
		hybridStatusCode.setType(HybridConnectionResponse.STATUS_CODE);
		hybridStatusCode.setValue(String.valueOf(connectionResponse.getStatusCode()));
		
		hybridConnectionResponse.addValue(hybridStatusCode);
		
		
		HybridSiminovValue hybridStatusMessage = new HybridSiminovValue();
		hybridStatusMessage.setType(HybridConnectionResponse.STATUS_MESSAGE);
		hybridStatusMessage.setValue(connectionResponse.getStatusMessage());
		
		hybridConnectionResponse.addValue(hybridStatusMessage);
		
		
		HybridSiminovValue hybridResponse = new HybridSiminovValue();
		hybridResponse.setType(HybridConnectionResponse.RESPONSE);
		
		try {
			hybridResponse.setValue(URLEncoder.encode(Utils.toString(connectionResponse.getResponse())));
		} catch(SiminovException se) {
			Log.debug(ResourceManager.class.getName(), "generateHybridConnectionRequest", "Siminov Exception caught while converting connection response input stream to string, " + se.getMessage());
		}
		
		hybridConnectionResponse.addValue(hybridResponse);
		
		return hybridConnectionResponse;
	}
	
	public HybridSiminovData generateHybridRegistration(final IRegistration registration) {
		
		HybridSiminovData hybridRegistration = new HybridSiminovData();
		hybridRegistration.setDataType(HybridRegistration.REGISTRATION);
		
		HybridSiminovValue hybridRegistrationId = new HybridSiminovValue();
		hybridRegistrationId.setType(HybridRegistration.REGISTRATION_ID);
		hybridRegistrationId.setValue(registration.getRegistrationId());

		hybridRegistration.addValue(hybridRegistrationId);
		
		return hybridRegistration;
	}
	
	public HybridSiminovData generateHybridMessage(final IMessage message) {

		HybridSiminovData hybridMessage = new HybridSiminovData();
		hybridMessage.setDataType(HybridMessage.MESSAGE);
		
		HybridSiminovValue hybridMsg = new HybridSiminovValue();
		hybridMsg.setType(HybridMessage.MSG);
		hybridMsg.setValue(message.getMessage());
		
		hybridMessage.addValue(hybridMsg);
		
		return hybridMessage;
	}
	
	
	public HybridSiminovData generateHybridNotificationException(final NotificationException notificationException) {

		HybridSiminovData hybridNotificationException = new HybridSiminovData();
		hybridNotificationException.setDataType(HybridNotificationException.NOTIFICATION_EXCEPTION);
		
		HybridSiminovValue hybridClassName = new HybridSiminovValue();
		hybridClassName.setType(HybridNotificationException.CLASS_NAME);
		hybridClassName.setValue(notificationException.getClassName());
		
		hybridNotificationException.addValue(hybridClassName);
		
		
		HybridSiminovValue hybridMethodName = new HybridSiminovValue();
		hybridMethodName.setType(HybridNotificationException.METHOD_NAME);
		hybridMethodName.setValue(notificationException.getMessage());
		
		hybridNotificationException.addValue(hybridMethodName);

		
		HybridSiminovValue hybridMessage = new HybridSiminovValue();
		hybridMessage.setType(HybridNotificationException.MESSAGE);
		hybridMessage.setValue(notificationException.getMessage());
		
		hybridNotificationException.addValue(hybridMessage);

		return hybridNotificationException;
	}
	
	
	public HybridSiminovData generateHybridSyncRequest(ISyncRequest syncRequest) {
		
		HybridSiminovData hybridSyncRequest = new HybridSiminovData();
		hybridSyncRequest.setDataType(HybridSyncRequest.SYNC_REQUEST);
		
		HybridSiminovValue hybridUrl = new HybridSiminovValue();
		hybridUrl.setType(HybridSyncRequest.NAME);
		hybridUrl.setValue(syncRequest.getName());
		
		hybridSyncRequest.addValue(hybridUrl);
		return hybridSyncRequest;
	}
}
