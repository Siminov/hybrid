///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///


#import "SIHResourceManager.h"

#import "SIKServiceDescriptor.h"
#import "SIHHybridDatabaseDescriptor.h"
#import "SIHHybridEntityDescriptor.h"
#import "SIHHybridLibraryDescriptor.h"
#import "SIHHybridAdapterDescriptor.h"
#import "SIHHybridConnectionRequest.h"
#import "SIHHybridConnectionResponse.h"
#import "SIHHybridRegistration.h"
#import "SIHHybridMessage.h"
#import "SIHHybridNotificationException.h"
#import "SIHHybridSyncRequest.h"
#import "SIHIHandler.h"


@implementation SIHResourceManager

static SIHResourceManager *hybridResourceManager;
static SICResourceManager *coreResourceManager;

static SIHEventHandler *eventHandler;

static SIHAdapterHandler *adapterHandler;
static SIHAdapterFactory *adapterFactory;

static id<SIHIHandler> interceptor;

SIHApplicationDescriptor *applicationDescriptor;
UIWebView *webView;
UIViewController *webActivity;

static NSMutableDictionary *hybridNativeClassMapping;


+ (void)initialize {
    coreResourceManager = [SICResourceManager getInstance];
    
    eventHandler = [SIHEventHandler getInstance];
    
    //adapterHandler = [SIHAdapterHandler getInstance];
    adapterFactory = [SIHAdapterFactory getInstance];
    
    hybridNativeClassMapping = [[NSMutableDictionary alloc] init];
}

+ (SIHResourceManager *)getInstance {
    
    if(!hybridResourceManager) {
        hybridResourceManager = [[super allocWithZone:NULL] init];
    }
    return hybridResourceManager;
}


- (SIHApplicationDescriptor *)getApplicationDescriptor {
    return applicationDescriptor;
}


- (void)setApplicationDescriptor:(SIHApplicationDescriptor *)applicationdescriptor {
    applicationDescriptor = applicationdescriptor;
}



- (NSEnumerator *)getAdapterDescriptors {
    return [applicationDescriptor getAdapterDescriptors];
}


- (NSEnumerator *)getAdapterDescriptorsBasedOnPaths {
    
    NSEnumerator *adapterDescriptorPaths = [applicationDescriptor getAdapterDescriptorPaths];
    NSMutableArray *adapterDescriptors = [[NSMutableArray alloc] init];
    
    NSString *adapterDescriptorPath;
    while(adapterDescriptorPath = [adapterDescriptorPaths nextObject]) {
        [adapterDescriptors addObject:[applicationDescriptor getAdapterDescriptorBasedOnPath:adapterDescriptorPath]];
    }
    
    return [adapterDescriptors objectEnumerator];
}



- (SIHAdapterDescriptor *)getAdapterDescriptor:(NSString *)adapterDescriptorName {
    bool contain = [applicationDescriptor containAdapterDescriptorBasedOnName:adapterDescriptorName];
    if(contain) {
        return [applicationDescriptor getAdapterDescriptorBasedOnName:adapterDescriptorName];
    }
    
    return nil;
}


- (SIHAdapterDescriptor *)getAdapterBasedOnName:(NSString *)adapterDescriptorName {
    
    bool contain = [applicationDescriptor containAdapterDescriptorBasedOnName:adapterDescriptorName];
    if(contain) {
        return [applicationDescriptor getAdapterDescriptorBasedOnName:adapterDescriptorName];
    }
    
    return nil;
}


- (SIHAdapterDescriptor *)getAdapterBasedOnPath:(NSString *)adapterDescriptorPath {
    
    bool contain = [applicationDescriptor containAdapterDescriptorBasedOnPath:adapterDescriptorPath];
    if(contain) {
        return [applicationDescriptor getAdapterDescriptorBasedOnPath:adapterDescriptorPath];
    }
    
    return nil;
}



- (bool)containAdapterBasedOnName:(NSString *)adapterDescriptorName {
    
    bool contain = [applicationDescriptor containAdapterDescriptorBasedOnName:adapterDescriptorName];
    if(contain) {
        return contain;
    }
    
    return false;
}


- (bool)containAdapterBasedOnPath:(NSString *)adapterDescriptorPath {
    return [applicationDescriptor containAdapterDescriptorBasedOnPath:adapterDescriptorPath];
}



- (NSEnumerator *)getHandlers {
    
    NSMutableArray *handlers = [[NSMutableArray alloc] init];
    
    NSEnumerator *adapterDescriptors = [self getAdapterDescriptors];
    SIHAdapterDescriptor *adapterdescriptor;
    
    while(adapterdescriptor = [adapterDescriptors nextObject]) {
        NSEnumerator *adapterHandlers = [adapterdescriptor getHandlers];
        SIHHandler *adapterhandler;
        
        while(adapterhandler = [adapterHandlers nextObject]) {
            [handlers addObject:adapterhandler];
        }
    }
    
    return [handlers objectEnumerator];
}


- (SIHHandler *)getHandler:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName {
    
    SIHAdapterDescriptor *adapterDescriptor = [self getAdapterDescriptor:adapterDescriptorName];
    bool contain = [adapterDescriptor containHandler:handlerName];
    
    if(contain) {
        return [adapterDescriptor getHandler:handlerName];
    }
    
    return nil;
}


- (bool)containHandler:(NSString *)handlerName {
    
    NSEnumerator *adapterDescriptors = [self getAdapterDescriptors];
    SIHAdapterDescriptor *adapterDescriptor;
    
    while(adapterDescriptor = [adapterDescriptors nextObject]) {
        NSEnumerator *allHandlers = [adapterDescriptor getHandlers];
        SIHHandler *handler;
        
        while(handler = [allHandlers nextObject]) {
            
            if([[handler getName] caseInsensitiveCompare:handlerName] == NSOrderedSame) {
                return true;
            }
        }
    }
    
    return false;
}


- (bool)containHandler:(NSString *)adapterDescriptorName handlerName:(NSString *)handlerName {
    SIHAdapterDescriptor *adapterDescriptor = [self getAdapterDescriptor:adapterDescriptorName];
    return [adapterDescriptor containHandler:handlerName];
}


- (UIWebView *)getWebView {
    return webView;
}


- (void)setWebView:(UIWebView *)webview {
    webView = webview;
}


- (id<SIHIHandler>)getInterceptor {
    return interceptor;
}


- (void)setInterceptor:(id<SIHIHandler>)hybridInteceptor {
    interceptor = hybridInteceptor;
}



- (bool)doesEventsRegistered {
    return [eventHandler doesEventsRegistered];
}


- (void)addEvent:(NSString *)event {
    [eventHandler addEvent:event];
}


- (NSEnumerator *)getEvents {
    return [eventHandler getEvents];
}


- (SIHAdapterHandler *)getAdapterHandler {
    return adapterHandler;
}


- (void)setAdapterHandler:(SIHAdapterHandler *)adapterhandler {
    adapterHandler = adapterhandler;
}


- (SIHAdapterFactory *)getAdapterFactory {
    return adapterFactory;
}


- (void)setAdapterFactory:(SIHAdapterFactory *)adapterfactory {
    adapterFactory = adapterfactory;
}


/*
 * Siminov Core API's.
 */


- (SICDatabaseDescriptor *)getDatabaseDescriptorBasedOnClassName:(NSString *)className {
    
    int classNameIndex;
    if([className rangeOfString:@"."].location > -1) {
        classNameIndex = (int) [className rangeOfString:@"."].location + 1;
    } else {
        classNameIndex = 0;
    }
    
    className = [className substringWithRange:NSMakeRange(classNameIndex, [className length] - classNameIndex)];
    
    NSString *nativeClassName = [hybridNativeClassMapping objectForKey:className];
    return [coreResourceManager getDatabaseDescriptorBasedOnClassName:nativeClassName];
}


- (NSString *)getDatabaseDescriptorNameBasedOnClassName:(NSString *)className {
    
    int classNameIndex;
    if([className rangeOfString:@"."].location > -1) {
        classNameIndex = (int) [className rangeOfString:@"."].location + 1;
    } else {
        classNameIndex = 0;
    }
    
    className = [className substringWithRange:NSMakeRange(classNameIndex, [className length] - classNameIndex)];
    
    NSString *nativeClassName = [hybridNativeClassMapping objectForKey:className];
    return [coreResourceManager getDatabaseDescriptorNameBasedOnClassName:nativeClassName];
}


- (NSString *)getDatabaseDescriptorNameBasedOnTableName:(NSString *)tableName {
    return [coreResourceManager getDatabaseDescriptorNameBasedOnTableName:tableName];
}


- (SICDatabaseDescriptor *)getDatabaseDescriptorBasedOnTableName:(NSString *)tableName {
    return [coreResourceManager getDatabaseDescriptorBasedOnTableName:tableName];
}


- (SICEntityDescriptor *)getEntityDescriptorBasedOnClassName:(NSString *)className {
    
    int classNameIndex;
    if([className rangeOfString:@"."].location > -1) {
        classNameIndex = (int) [className rangeOfString:@"."].location + 1;
    } else {
        classNameIndex = 0;
    }
        
    className = [className substringWithRange:NSMakeRange(classNameIndex, [className length] - classNameIndex)];
    
    NSString *nativeClassName = [hybridNativeClassMapping objectForKey:className];
    if(nativeClassName == nil || [nativeClassName length] <= 0) {
        SICEntityDescriptor *entityDescriptor = [coreResourceManager requiredEntityDescriptorBasedOnClassName:className];
        [self synchronizeEntityDescriptors];
        
        return entityDescriptor;
    }
    
    return [coreResourceManager requiredEntityDescriptorBasedOnClassName:nativeClassName];
}


- (SICEntityDescriptor *)getEntityDescriptorBasedOnTableName:(NSString *)tableName {
    return [coreResourceManager getEntityDescriptorBasedOnTableName:tableName];
}


- (NSString *)getMappedNativeClassName:(NSString *)hybridClassName {
    
    int hybridClassNameIndex;
    if([hybridClassName rangeOfString:@"."].location > -1) {
        hybridClassNameIndex = (int) [hybridClassName rangeOfString:@"."].location + 1;
    } else {
        hybridClassNameIndex = 0;
    }
    
    hybridClassName = [hybridClassName substringWithRange:NSMakeRange(hybridClassNameIndex, [hybridClassName length] - hybridClassNameIndex)];
    
    hybridClassName = [hybridClassName substringWithRange:NSMakeRange(hybridClassNameIndex, [hybridClassName length])];
    return [hybridNativeClassMapping objectForKey:hybridClassName];
}


- (NSString *)getMappedHybridClassName:(NSString *)nativeClassName {
    NSEnumerator *keys = [[hybridNativeClassMapping allKeys] objectEnumerator];
    NSString *key;
    
    while(key = [keys nextObject]) {
        NSString *value = [hybridNativeClassMapping objectForKey:key];
        
        if([value caseInsensitiveCompare:nativeClassName] == NSOrderedSame) {
            return key;
        }
        
    }
    
    return nil;
}


- (void)synchronizeEntityDescriptors {
    
    NSEnumerator *entityDescriptors = [coreResourceManager getEntityDescriptors];
    SICEntityDescriptor *entityDescriptor;
    
    while(entityDescriptor = [entityDescriptors nextObject]) {
        NSString *nativeClassName = [entityDescriptor getClassName];
        
        NSString *hybridClassName = nativeClassName;
        
        if ([nativeClassName hasSuffix:LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR]) {
            
            NSUInteger index = 0;
            NSRange range = [nativeClassName rangeOfString:LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_SEPRATOR];
            if (range.length == 0 && range.location > nativeClassName.length) {
                index = 0;
            } else {
                index = range.location;
            }
            
            hybridClassName = [nativeClassName substringFromIndex:index+1];
        }
        
        [hybridNativeClassMapping setValue:nativeClassName forKey:hybridClassName];
    }
}



- (SIHHybridSiminovData *)generateHybridDatabaseDescriptor:(SICDatabaseDescriptor *)databaseDescriptor {
    
    SIHHybridSiminovData *hybridDatabaseDescriptor = [[SIHHybridSiminovData alloc] init];
    [hybridDatabaseDescriptor setDataType:HYBRID_DATABASE_DESCRIPTOR_DATABASE_DESCRIPTOR];
    
    SIHHybridSiminovValue *databaseName = [[SIHHybridSiminovValue alloc] init];
    [databaseName setType:HYBRID_DATABASE_DESCRIPTOR_NAME];
    [databaseName setValue:[databaseDescriptor getDatabaseName]];
    
    [hybridDatabaseDescriptor addValue:databaseName];
    
    SIHHybridSiminovValue *databaseDescription = [[SIHHybridSiminovValue alloc] init];
    [databaseDescription setType:HYBRID_DATABASE_DESCRIPTOR_DESCRIPTION];
    [databaseDescription setValue:[databaseDescriptor getDescription]];
    
    [hybridDatabaseDescriptor addValue:databaseDescription];
    
    SIHHybridSiminovValue *isLockingRequired = [[SIHHybridSiminovValue alloc] init];
    [isLockingRequired setType:HYBRID_DATABASE_DESCRIPTOR_IS_TRANSACTION_SAFE];
    [isLockingRequired setValue:[NSString stringWithFormat:@"%d", [databaseDescriptor isTransactionSafe]]];
    
    [hybridDatabaseDescriptor addValue:isLockingRequired];
    
    SIHHybridSiminovValue *externalStorage = [[SIHHybridSiminovValue alloc] init];
    [externalStorage setType:HYBRID_DATABASE_DESCRIPTOR_EXTERNAL_STORAGE];
    [externalStorage setValue:[NSString stringWithFormat:@"%d", [databaseDescriptor isExternalStorageEnable]]];
    
    [hybridDatabaseDescriptor addValue:externalStorage];
    
    SIHHybridSiminovData *hybridEntityDescriptorPaths = [[SIHHybridSiminovData alloc] init];
    [hybridEntityDescriptorPaths setDataType:HYBRID_DATABASE_DESCRIPTOR_ENTITY_DESCRIPTORS];
    
    NSEnumerator *entityDescriptors = [databaseDescriptor getEntityDescriptorPaths];
    NSString *entityDescriptor;
    
    while(entityDescriptor = [entityDescriptors nextObject]) {
        
        SIHHybridSiminovValue *entityDescriptorPath = [[SIHHybridSiminovValue alloc] init];
        [entityDescriptorPath setType:HYBRID_DATABASE_DESCRIPTOR_ENTITY_DESCRIPTOR_PATH];
        [entityDescriptorPath setValue:entityDescriptor];
        
        [hybridEntityDescriptorPaths addValue:entityDescriptorPath];
        
    }
    
    [hybridDatabaseDescriptor addData:hybridEntityDescriptorPaths];
    
    return hybridDatabaseDescriptor;
}


- (SIHHybridSiminovData *)generateHybridEntityDescriptor:(SICEntityDescriptor *)entityDescriptor {
    
    SIHHybridSiminovData *hybridEntityDescriptor = [[SIHHybridSiminovData alloc] init];
    [hybridEntityDescriptor setDataType:HYBRID_ENTITY_DESCRIPTOR_ENTITY_DESCRIPTOR];
    
    
    SIHHybridSiminovValue *tableName = [[SIHHybridSiminovValue alloc] init];
    [tableName setType:HYBRID_ENTITY_DESCRIPTOR_TABLE_NAME];
    [tableName setValue:[entityDescriptor getTableName]];
    
    [hybridEntityDescriptor addValue:tableName];
    
    
    SIHHybridSiminovValue *className = [[SIHHybridSiminovValue alloc] init];
    [className setType:HYBRID_ENTITY_DESCRIPTOR_CLASS_NAME];
    [className setValue:[entityDescriptor getClassName]];
    
    [hybridEntityDescriptor addValue:className];
    
    
    SIHHybridSiminovData *hybridColumns = [[SIHHybridSiminovData alloc] init];
    [hybridColumns setDataType:HYBRID_ENTITY_DESCRIPTOR_ENTITIES];
    
    NSEnumerator *attributes = [entityDescriptor getAttributes];
    SICAttribute *attribute;
    
    while(attribute = [attributes nextObject]) {
        
        SIHHybridSiminovData *hybridColumn = [self generateHybridEntityDescriptorColumn:attribute];
        [hybridColumns addData:hybridColumn];
    }
    
    
    [hybridEntityDescriptor addData:hybridColumns];
    
    
    SIHHybridSiminovData *hybridIndexs = [[SIHHybridSiminovData alloc] init];
    [hybridIndexs setDataType:HYBRID_ENTITY_DESCRIPTOR_INDEXS];
    
    NSEnumerator *indexs = [entityDescriptor getIndexes];
    SICIndex *index;
    
    while(index = [indexs nextObject]) {
        
        SIHHybridSiminovData *hybridIndex = [self generateHybridEntityDescriptorIndex:index];
        [hybridIndexs addData:hybridIndex];
    }
    
    [hybridEntityDescriptor addData:hybridIndexs];
    
    
    SIHHybridSiminovData *hybridRelationships = [[SIHHybridSiminovData alloc] init];
    [hybridRelationships setDataType:HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIPS];
    
    NSEnumerator *relationships = [entityDescriptor getRelationships];
    SICRelationship *relationship;
    
    while(relationship = [relationships nextObject]) {
        
        SIHHybridSiminovData *hybridRelationship = [self generateHybridEntityDescriptorRelationship:relationship];
        [hybridRelationships addData:hybridRelationship];
    }
    
    [hybridEntityDescriptor addData:hybridRelationships];
    
    return hybridEntityDescriptor;
}



- (SIHHybridSiminovData *)generateHybridEntityDescriptorColumn:(SICAttribute *)attribute {
    
    SIHHybridSiminovData *hybridColumn = [[SIHHybridSiminovData alloc] init];
    [hybridColumn setDataType:HYBRID_ENTITY_DESCRIPTOR_ATTRIBUTE];
    
    SIHHybridSiminovValue *variableName = [[SIHHybridSiminovValue alloc] init];
    [variableName setType:HYBRID_ENTITY_DESCRIPTOR_VARIABLE_NAME];
    [variableName setValue:[attribute getVariableName]];
    
    [hybridColumn addValue:variableName];
    
    
    SIHHybridSiminovValue *columnName = [[SIHHybridSiminovValue alloc] init];
    [columnName setType:HYBRID_ENTITY_DESCRIPTOR_COLUMN_NAME];
    [columnName setValue:[attribute getColumnName]];
    
    [hybridColumn addValue:columnName];
    
    
    SIHHybridSiminovValue *type = [[SIHHybridSiminovValue alloc] init];
    [type setType:HYBRID_ENTITY_DESCRIPTOR_TYPE];
    [type setValue:[attribute getType]];
    
    [hybridColumn addValue:type];
    
    
    SIHHybridSiminovValue *primaryKey = [[SIHHybridSiminovValue alloc] init];
    [primaryKey setType:HYBRID_ENTITY_DESCRIPTOR_PRIMARY_KEY];
    [primaryKey setValue:[NSString stringWithFormat:@"%d", [attribute isPrimaryKey]]];
    
    [hybridColumn addValue:primaryKey];
    
    
    SIHHybridSiminovValue *notNull = [[SIHHybridSiminovValue alloc] init];
    [notNull setType:HYBRID_ENTITY_DESCRIPTOR_NOT_NULL];
    [notNull setValue:[NSString stringWithFormat:@"%d", [attribute isNotNull]]];
    
    [hybridColumn addValue:notNull];
    
    
    SIHHybridSiminovValue *unique = [[SIHHybridSiminovValue alloc] init];
    [unique setType:HYBRID_ENTITY_DESCRIPTOR_UNIQUE];
    [unique setValue:[NSString stringWithFormat:@"%d", [attribute isUnique]]];
    
    [hybridColumn addValue:unique];
    
    
    SIHHybridSiminovValue *check = [[SIHHybridSiminovValue alloc] init];
    [check setType:HYBRID_ENTITY_DESCRIPTOR_CHECK];
    [check setValue:[attribute getCheck]];
    
    [hybridColumn addValue:check];
    
    
    SIHHybridSiminovValue *defaultValue = [[SIHHybridSiminovValue alloc] init];
    [defaultValue setType:HYBRID_ENTITY_DESCRIPTOR_DEFAULT];
    [defaultValue setValue:[attribute getDefaultValue]];
    
    [hybridColumn addValue:defaultValue];
    
    return hybridColumn;
    
}



- (SIHHybridSiminovData *)generateHybridEntityDescriptorIndex:(SICIndex *)index {
    
    SIHHybridSiminovData *hybridIndex = [[SIHHybridSiminovData alloc] init];
    [hybridIndex setDataType:HYBRID_ENTITY_DESCRIPTOR_INDEX];
    
    SIHHybridSiminovValue *name = [[SIHHybridSiminovValue alloc] init];
    [name setType:HYBRID_ENTITY_DESCRIPTOR_INDEX_NAME];
    [name setValue:[index getName]];
    
    [hybridIndex addValue:name];
    
    
    SIHHybridSiminovValue *unique = [[SIHHybridSiminovValue alloc] init];
    [unique setType:HYBRID_ENTITY_DESCRIPTOR_INDEX_UNIQUE];
    [unique setValue:[NSString stringWithFormat:@"%d", [index isUnique]]];
    
    [hybridIndex addValue:unique];
    
    SIHHybridSiminovData *hybridIndexColumns = [[SIHHybridSiminovData alloc] init];
    
    NSEnumerator *columnNames = [index getColumns];
    NSString *columnName;
    
    while(columnName = [columnNames nextObject]) {
        
        SIHHybridSiminovValue *column = [[SIHHybridSiminovValue alloc] init];
        
        [column setType:HYBRID_ENTITY_DESCRIPTOR_INDEX_COLUMN];
        [column setValue:columnName];
        
        [hybridIndexColumns addValue:column];
    }
    
    return hybridIndex;
}


- (SIHHybridSiminovData *)generateHybridEntityDescriptorRelationship:(SICRelationship *)relationship {
    
    SIHHybridSiminovData *hybridRelationship = [[SIHHybridSiminovData alloc] init];
    [hybridRelationship setDataType:HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIP];
    
    SIHHybridSiminovValue *relationshipType = [[SIHHybridSiminovValue alloc] init];
    [relationshipType setType:HYBRID_ENTITY_DESCRIPTOR_RELATIONSHIP_TYPE];
    [relationshipType setValue:[relationship getRelationshipType]];
    
    [hybridRelationship addValue:relationshipType];
    
    
    SIHHybridSiminovValue *refer = [[SIHHybridSiminovValue alloc] init];
    [refer setType:HYBRID_ENTITY_DESCRIPTOR_REFER];
    [refer setValue:[relationship getRefer]];
    
    [hybridRelationship addValue:refer];
    
    
    SIHHybridSiminovValue *referTo = [[SIHHybridSiminovValue alloc] init];
    [referTo setType:HYBRID_ENTITY_DESCRIPTOR_REFER_TO];
    [referTo setValue:[relationship getReferTo]];
    
    [hybridRelationship addValue:referTo];
    
    
    SIHHybridSiminovValue *onUpdate = [[SIHHybridSiminovValue alloc] init];
    [onUpdate setType:HYBRID_ENTITY_DESCRIPTOR_ON_UPDATE];
    [onUpdate setValue:[relationship getOnUpdate]];
    
    [hybridRelationship addValue:onUpdate];
    
    
    SIHHybridSiminovValue *onDelete = [[SIHHybridSiminovValue alloc] init];
    [onDelete setType:HYBRID_ENTITY_DESCRIPTOR_ON_DELETE];
    [onDelete setValue:[relationship getOnDelete]];
    
    [hybridRelationship addValue:onDelete];
    
    
    SIHHybridSiminovValue *load = [[SIHHybridSiminovValue alloc] init];
    [load setType:HYBRID_ENTITY_DESCRIPTOR_LOAD];
    [load setValue:[NSString stringWithFormat:@"%d", [relationship isLoad]]];
    
    [hybridRelationship addValue:load];
    
    return hybridRelationship;
}



- (SIHHybridSiminovData *)generateHybridLibraryDescriptor:(SICLibraryDescriptor *)libraryDescriptor {
    
    SIHHybridSiminovData *hybridLibraryDescriptor = [[SIHHybridSiminovData alloc] init];
    [hybridLibraryDescriptor setDataType:HYBRID_LIBRARY_DESCRIPTOR_LIBRARY_DESCRIPTOR];
    
    
    SIHHybridSiminovValue *name = [[SIHHybridSiminovValue alloc] init];
    [name setType:HYBRID_LIBRARY_DESCRIPTOR_NAME];
    [name setValue:[libraryDescriptor getName]];
    
    [hybridLibraryDescriptor addValue:name];
    
    
    SIHHybridSiminovValue *description = [[SIHHybridSiminovValue alloc] init];
    [description setType:HYBRID_LIBRARY_DESCRIPTOR_DESCRIPTION];
    [description setValue:[libraryDescriptor getDescription]];
    
    
    SIHHybridSiminovData *hybridEntityDescriptorPaths = [[SIHHybridSiminovData alloc] init];
    [hybridEntityDescriptorPaths setDataType:HYBRID_LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_PATHS];
    
    NSEnumerator *entityDescriptorPaths = [libraryDescriptor getEntityDescriptorPaths];
    SIHHybridSiminovValue *hybridEntityDescriptorPath;
    
    while(hybridEntityDescriptorPath = [entityDescriptorPaths nextObject]) {
        
        [hybridEntityDescriptorPath setType:HYBRID_LIBRARY_DESCRIPTOR_ENTITY_DESCRIPTOR_PATH];
        
        [hybridEntityDescriptorPaths addValue:hybridEntityDescriptorPath];
    }
    
    [hybridLibraryDescriptor addData:hybridEntityDescriptorPaths];
    
    return hybridLibraryDescriptor;
}



- (SIHHybridSiminovData *)generateHybridAdapterDescriptor:(SIHAdapterDescriptor *)adapterDescriptor {
    
    SIHHybridSiminovData *hybridAdapterDescriptor = [[SIHHybridSiminovData alloc] init];
    [hybridAdapterDescriptor setDataType:HYBRID_ADAPTER_DESCRIPTOR_ADAPTER];
    
    
    SIHHybridSiminovValue *adapterName = [[SIHHybridSiminovValue alloc] init];
    [adapterName setType:HYBRID_ADAPTER_DESCRIPTOR_NAME];
    [adapterName setValue:[adapterDescriptor getName]];
    
    [hybridAdapterDescriptor addValue:adapterName];
    
    
    SIHHybridSiminovValue *adapterDescriptorDescription = [[SIHHybridSiminovValue alloc] init];
    [adapterDescriptorDescription setType:HYBRID_ADAPTER_DESCRIPTOR_DESCRIPTION];
    [adapterDescriptorDescription setValue:[adapterDescriptor getDescription]];
    
    [hybridAdapterDescriptor addValue:adapterDescriptorDescription];
    
    
    SIHHybridSiminovValue *adapterDescriptorMapTo = [[SIHHybridSiminovValue alloc] init];
    [adapterDescriptorMapTo setType:HYBRID_ADAPTER_DESCRIPTOR_MAP_TO];
    [adapterDescriptorMapTo setValue:[adapterDescriptor getMapTo]];
    
    [hybridAdapterDescriptor addValue:adapterDescriptorMapTo];
    
    
    SIHHybridSiminovValue *cache = [[SIHHybridSiminovValue alloc] init];
    [cache setType:HYBRID_ADAPTER_DESCRIPTOR_CACHE];
    [cache setValue:[NSString stringWithFormat:@"%d", [adapterDescriptor isCache]]];
    
    [hybridAdapterDescriptor addValue:cache];
    
    
    SIHHybridSiminovData *hybridHandlers = [[SIHHybridSiminovData alloc] init];
    [hybridHandlers setDataType:HYBRID_ADAPTER_DESCRIPTOR_HANDLERS];
    
    NSEnumerator *handlers = [adapterDescriptor getHandlers];
    SIHHandler *handler;
    
    while(handler = [handlers nextObject]) {
        [hybridHandlers addData:[self generateHybridHandler:handler]];
    }
    
    
    [hybridAdapterDescriptor addData:hybridHandlers];
    
    return hybridAdapterDescriptor;
}



- (SIHHybridSiminovData *)generateHybridHandler:(SIHHandler *)handler {
    
    SIHHybridSiminovData *hybridHandler = [[SIHHybridSiminovData alloc] init];
    [hybridHandler setDataType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER];
    
    
    SIHHybridSiminovValue *handlerName = [[SIHHybridSiminovValue alloc] init];
    [handlerName setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_NAME];
    [handlerName setValue:[handler getName]];
    
    [hybridHandler addValue:handlerName];
    
    
    SIHHybridSiminovValue *handlerMapTo = [[SIHHybridSiminovValue alloc] init];
    [handlerMapTo setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_MAP_TO];
    [handlerMapTo setValue:[handler getMapTo]];
    
    [hybridHandler addValue:handlerMapTo];
    
    
    SIHHybridSiminovValue *handlerDescription = [[SIHHybridSiminovValue alloc] init];
    [handlerDescription setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_DESCRIPTION];
    [handlerDescription setValue:[handler getDescription]];
    
    [hybridHandler addValue:handlerDescription];
    
    
    SIHHybridSiminovData *hybridHandlerParameters = [[SIHHybridSiminovData alloc] init];
    [hybridHandlerParameters setDataType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_PARAMETERS];
    
    NSEnumerator *parameters = [handler getParameters];
    SIHParameter *parameter;
    
    while(parameter = [parameters nextObject]) {
        
        SIHHybridSiminovData *hybridHandlerParameter = [[SIHHybridSiminovData alloc] init];
        [hybridHandlerParameter setDataType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_PARAMETER];
        
        
        SIHHybridSiminovValue *parameterType = [[SIHHybridSiminovValue alloc] init];
        [parameterType setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_TYPE];
        [parameterType setValue:[parameter getType]];
        
        [hybridHandlerParameter addValue:parameterType];
        
        
        SIHHybridSiminovValue *parameterDescription = [[SIHHybridSiminovValue alloc] init];
        [parameterDescription setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_DESCRIPTION];
        [parameterDescription setValue:[parameter getDescription]];
        
        [hybridHandlerParameter addValue:parameterDescription];
        
        [hybridHandlerParameters addData:hybridHandlerParameter];
    }
    
    [hybridHandler addData:hybridHandlerParameters];
    
    SIHReturn *returnType = [handler getReturn];
    if(returnType != nil) {
        
        SIHHybridSiminovData *hybridReturn = [[SIHHybridSiminovData alloc] init];
        [hybridReturn setDataType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_RETURN];
        
        
        SIHHybridSiminovValue *hybridReturnType = [[SIHHybridSiminovValue alloc] init];
        [hybridReturnType setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_RETURN_TYPE];
        [hybridReturnType setValue:[returnType getType]];
        
        [hybridReturn addValue:hybridReturnType];
        
        
        SIHHybridSiminovValue *hybridReturnDescription = [[SIHHybridSiminovValue alloc] init];
        [hybridReturnDescription setType:HYBRID_ADAPTER_DESCRIPTOR_HANDLER_RETURN_DESCRIPTION];
        [hybridReturnDescription setValue:[returnType getDescription]];
        
        [hybridReturn addValue:hybridReturnDescription];
        
        [hybridHandler addData:hybridReturn];
    }
    
    return hybridHandler;
}




- (SIHHybridSiminovData *)generateHybridConnectionRequest:(id<SIKIConnectionRequest>)connectionRequest {
    
    SIHHybridSiminovData *hybridConnectionRequest = [[SIHHybridSiminovData alloc] init];
    [hybridConnectionRequest setDataType:HYBRID_CONNECTION_REQUEST_CONNECTION_REQUEST];
    
    SIHHybridSiminovValue *hybridUrl = [[SIHHybridSiminovValue alloc] init];
    [hybridUrl setType:HYBRID_CONNECTION_REQUEST_URL];
    [hybridUrl setValue:[connectionRequest getUrl]];
    
    [hybridConnectionRequest addValue:hybridUrl];
    
    
    SIHHybridSiminovValue *hybridProtocol = [[SIHHybridSiminovValue alloc] init];
    [hybridProtocol setType:HYBRID_CONNECTION_REQUEST_PROTOCOL];
    [hybridProtocol setValue:[connectionRequest getProtocol]];
    
    [hybridConnectionRequest addValue:hybridProtocol];
    
    
    SIHHybridSiminovValue *hybridType = [[SIHHybridSiminovValue alloc] init];
    [hybridType setType:HYBRID_CONNECTION_REQUEST_TYPE];
    [hybridType setValue:[connectionRequest getType]];
    
    [hybridConnectionRequest addValue:hybridType];
    
    SIHHybridSiminovValue *hybridDataStream = [[SIHHybridSiminovValue alloc] init];
    [hybridDataStream setType:HYBRID_CONNECTION_REQUEST_DATA_STREAM];
    [hybridDataStream setValue:[[NSString alloc] initWithData:[connectionRequest getDataStream] encoding:NSASCIIStringEncoding]];
    
    [hybridConnectionRequest addValue:hybridDataStream];
    
    
    SIHHybridSiminovData *hybridQueryParameters = [[SIHHybridSiminovData alloc] init];
    [hybridQueryParameters setDataType:HYBRID_CONNECTION_REQUEST_QUERY_PARAMETERS_TYPE];
    
    
    NSEnumerator *queryParameters = [connectionRequest getQueryParameters];
    NSString *queryParameterKey;
    
    while(queryParameterKey = [queryParameters nextObject]) {
        
        SIKQueryParameter *queryParameter = [connectionRequest getQueryParameter:queryParameterKey];
        
        SIHHybridSiminovData *hybridQueryParameter = [[SIHHybridSiminovData alloc] init];
        [hybridQueryParameter setDataType:HYBRID_CONNECTION_REQUEST_QUERY_PARAMETER_TYPE];
        
        /*
         * Hybrid Query Parameter Name
         */
        SIHHybridSiminovValue *hybridQueryParameterName = [[SIHHybridSiminovValue alloc] init];
        [hybridQueryParameterName setType:HYBRID_CONNECTION_REQUEST_QUERY_PARAMETER_NAME];
        [hybridQueryParameterName setValue:[queryParameter getName]];
        
        [hybridQueryParameter addValue:hybridQueryParameterName];
        
        /*
         * Hybrid Query Parameter Value
         */
        SIHHybridSiminovValue *hybridQueryParameterValue = [[SIHHybridSiminovValue alloc] init];
        [hybridQueryParameterValue setType:HYBRID_CONNECTION_REQUEST_QUERY_PARAMETER_VALUE];
        [hybridQueryParameterValue setValue:[queryParameter getValue]];
        
        [hybridQueryParameter addValue:hybridQueryParameterValue];
        
        [hybridQueryParameters addData:hybridQueryParameter];
    }
    
    [hybridConnectionRequest addData:hybridQueryParameters];
    
    
    SIHHybridSiminovData *hybridHeaderParameters = [[SIHHybridSiminovData alloc] init];
    [hybridHeaderParameters setDataType:HYBRID_CONNECTION_REQUEST_HEADER_PARAMETERS_TYPE];
    
    
    NSEnumerator *headerParameters = [connectionRequest getHeaderParameters];
    NSString *headerParameterKey;
    
    while(headerParameterKey = [headerParameters nextObject]) {
        
        SIKHeaderParameter *headerParameter = [connectionRequest getHeaderParameter:headerParameterKey];
        
        SIHHybridSiminovData *hybridHeaderParameter = [[SIHHybridSiminovData alloc] init];
        [hybridHeaderParameter setDataType:HYBRID_CONNECTION_REQUEST_HEADER_PARAMETER_TYPE];
        
        /*
         * Hybrid Header Parameter Name
         */
        SIHHybridSiminovValue *hybridHeaderParameterName = [[SIHHybridSiminovValue alloc] init];
        [hybridHeaderParameterName setType:HYBRID_CONNECTION_REQUEST_HEADER_PARAMETER_NAME];
        [hybridHeaderParameterName setValue:[headerParameter getName]];
        
        [hybridHeaderParameter addValue:hybridHeaderParameterName];
        
        /*
         * Hybrid Query Parameter Value
         */
        SIHHybridSiminovValue *hybridQueryParameterValue = [[SIHHybridSiminovValue alloc] init];
        [hybridQueryParameterValue setType:HYBRID_CONNECTION_REQUEST_QUERY_PARAMETER_NAME];
        [hybridQueryParameterValue setValue:[headerParameter getName]];
        
        [hybridHeaderParameter addValue:hybridQueryParameterValue];
        
        [hybridQueryParameters addData:hybridHeaderParameter];
    }
    
    [hybridConnectionRequest addData:hybridHeaderParameters];
    
    return hybridConnectionRequest;
}


- (SIHHybridSiminovData *)generateHybridConnectionResponse:(id<SIKIConnectionResponse>)connectionResponse {
    
    SIHHybridSiminovData *hybridConnectionResponse = [[SIHHybridSiminovData alloc] init];
    [hybridConnectionResponse setDataType:HYBRID_CONNECTION_RESPONSE_CONNECTION_RESPONSE];
    
    SIHHybridSiminovValue *hybridStatusCode = [[SIHHybridSiminovValue alloc] init];
    [hybridStatusCode setType:HYBRID_CONNECTION_RESPONSE_STATUS_CODE];
    [hybridStatusCode setValue:[NSString stringWithFormat:@"%d", [connectionResponse getStatusCode]]];
    
    [hybridConnectionResponse addValue:hybridStatusCode];
    
    
    SIHHybridSiminovValue *hybridStatusMessage = [[SIHHybridSiminovValue alloc] init];
    [hybridStatusMessage setType:HYBRID_CONNECTION_RESPONSE_STATUS_MESSAGE];
    [hybridStatusMessage setValue:[connectionResponse getStatusMessage]];
    
    [hybridConnectionResponse addValue:hybridStatusMessage];
    
    
    SIHHybridSiminovValue *hybridResponse = [[SIHHybridSiminovValue alloc] init];
    [hybridResponse setType:HYBRID_CONNECTION_RESPONSE_RESPONSE];
    
    if([connectionResponse getResponse] != nil) {
        
        @try {

            NSString *data = [[NSString alloc] initWithData:[connectionResponse getResponse] encoding:NSUTF8StringEncoding];
            [hybridResponse setValue:data];
        } @catch(SICSiminovException *se) {
            [SICLog debug:NSStringFromClass([self class]) methodName:@"generateHybridConnectionRequest" message:[NSString stringWithFormat:@"Siminov Exception caught while converting connection response input stream to string, %@", [se getMessage]]];
        }
    } else {
        [hybridResponse setValue:@""];
    }
    
    [hybridConnectionResponse addValue:hybridResponse];
    
    return hybridConnectionResponse;
}

- (SIHHybridSiminovData *)generateHybridRegistration:(id<SIKIRegistration>)registration {
    
    SIHHybridSiminovData *hybridRegistration = [[SIHHybridSiminovData alloc] init];
    [hybridRegistration setDataType:HYBRID_REGISTRATION_REGISTRATION];
    
    SIHHybridSiminovValue *hybridRegistrationId = [[SIHHybridSiminovValue alloc] init];
    [hybridRegistrationId setType:HYBRID_REGISTRATION_REGISTRATION_ID];
    [hybridRegistrationId setValue:[registration getRegistrationId]];
    
    [hybridRegistration addValue:hybridRegistrationId];
    
    return hybridRegistration;
}

- (SIHHybridSiminovData *)generateHybridMessage:(id<SIKIMessage>)message {
    
    SIHHybridSiminovData *hybridMessage = [[SIHHybridSiminovData alloc] init];
    [hybridMessage setDataType:HYBRID_MESSAGE_MESSAGE];
    
    SIHHybridSiminovValue *hybridMsg = [[SIHHybridSiminovValue alloc] init];
    [hybridMsg setType:HYBRID_MESSAGE_MSG];
    [hybridMsg setValue:[message getMessage]];
    
    [hybridMessage addValue:hybridMsg];
    
    return hybridMessage;
}


- (SIHHybridSiminovData *)generateHybridNotificationException:(SIKNotificationException *)notificationException {
    
    SIHHybridSiminovData *hybridNotificationException = [[SIHHybridSiminovData alloc] init];
    [hybridNotificationException setDataType:HYBRID_NOTIFICATION_EXCEPTION_NOTIFICATION_EXCEPTION];
    
    SIHHybridSiminovValue *hybridClassName = [[SIHHybridSiminovValue alloc] init];
    [hybridClassName setType:HYBRID_NOTIFICATION_EXCEPTION_CLASS_NAME];
    [hybridClassName setValue:[notificationException getClassName]];
    
    [hybridNotificationException addValue:hybridClassName];
    
    
    SIHHybridSiminovValue *hybridMethodName = [[SIHHybridSiminovValue alloc] init];
    [hybridMethodName setType:HYBRID_NOTIFICATION_EXCEPTION_METHOD_NAME];
    [hybridMethodName setValue:[notificationException getMessage]];
    
    [hybridNotificationException addValue:hybridMethodName];
    
    
    SIHHybridSiminovValue *hybridMessage = [[SIHHybridSiminovValue alloc] init];
    [hybridMessage setType:HYBRID_NOTIFICATION_EXCEPTION_MESSAGE];
    [hybridMessage setValue:[notificationException getMessage]];
    
    [hybridNotificationException addValue:hybridMessage];
    
    return hybridNotificationException;
}


- (SIHHybridSiminovData *)generateHybridSyncRequest:(id<SIKISyncRequest>)syncRequest {
    
    SIHHybridSiminovData *hybridSyncRequest = [[SIHHybridSiminovData alloc] init];
    [hybridSyncRequest setDataType:HYBRID_SYNC_REQUEST_SYNC_REQUEST];
    
    SIHHybridSiminovValue *hybridUrl = [[SIHHybridSiminovValue alloc] init];
    [hybridUrl setType:HYBRID_SYNC_REQUEST_NAME];
    [hybridUrl setValue:[syncRequest getName]];
    
    [hybridSyncRequest addValue:hybridUrl];
    
    return hybridSyncRequest;
}


@end