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

/**
 * Exposes constants which represents Hybrid Event Handler on Hybrid.
 */
#import <Foundation/Foundation.h>

/**
 * Hybrid Triggered Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_TRIGGERED_EVENT = @"TriggeredEvent";

/**
 * Hybrid Event Handler Request Id
 */
static NSString * const HYBRID_EVENT_HANDLER_REQUEST_ID = @"RequestId";


/**
 * Hybrid First Time Siminov Initialized Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_FIRST_TIME_SIMINOV_INITIALIZED = @"onFirstTimeSiminovInitialized";

/**
 * Hybrid Siminov Initialized Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_INITIALIZED = @"onSiminovInitialized";

/**
 * Hybrid Siminov Stopped Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_ISIMINOV_EVENT_ON_SIMINOV_STOPPED = @"onSiminovStopped";


/**
 * Hybrid Database Created Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_CREATED = @"onDatabaseCreated";

/**
 * Hybrid Database Dropped Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_DATABASE_DROPPED = @"onDatabaseDropped";

/**
 * Hybrid Table Created Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_CREATED = @"onTableCreated";

/**
 * Hybrid Table Dropped Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_TABLE_DROPPED = @"onTableDropped";

/**
 * Hybrid Index Created Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_CREATED = @"onIndexCreated";

/**
 * Hybrid Index Dropped Event API.
 */
static NSString * const HYBRID_EVENT_HANDLER_IDATABASE_EVENT_ON_INDEX_DROPPED = @"onIndexDropped";

/**
 * INofitication Event On Registration
 */
static NSString * const HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_REGISTRATION = @"onRegistration";


/**
 * INotification Event On Unregistration
 */
static NSString * const HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_UNREGISTRATION = @"onUnregistration";


/**
 * INotification Event On Notification
 */
static NSString * const HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_NOTIFICATION = @"onNotification";


/**
 * INotification Event On Error
 */
static NSString * const HYBRID_EVENT_HANDLER_INOTIFICATION_EVENT_ON_ERROR = @"onError";


/**
 * ISync Event On Sync Started
 */
static NSString * const HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_STARTED = @"onSyncStarted";


/**
 * ISync Event On Sync Queued
 */
static NSString * const HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_QUEUED = @"onSyncQueued";

/**
 * ISync Event On Sync Removed
 */
static NSString * const HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_REMOVED = @"onSyncRemoved";


/**
 * ISync Event On Sync Terminated
 */
static NSString * const HYBRID_EVENT_HANDLER_ISYNC_EVENT_ON_SYNC_TERMINATED = @"onSyncTerminated";


/**
 * Hybrid Events.
 */
static NSString * const HYBRID_EVENT_HANDLER_EVENTS = @"Events";


/**
 * Hybrid Event Parameters.
 */
static NSString * const HYBRID_EVENT_HANDLER_EVENT_PARAMETERS = @"EventParameters";
