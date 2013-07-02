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

package siminov.hybrid.adapter.constants;

/**
 * Exposes constants to which represents Hybrid Event Handler on Web.
 */
public interface HybridEventHandler {

	/**
	 * Web Triggered Event API.
	 */
	public String TRIGGERED_EVENT = "TriggeredEvent";
	
	/**
	 * Web First Time Siminov Initialized Event API.
	 */
	public String ISIMINOV_EVENT_FIRST_TIME_SIMINOV_INITIALIZED = "firstTimeSiminovInitialized";

	/**
	 * Web Siminov Initialized Event API.
	 */
	public String ISIMINOV_EVENT_SIMINOV_INITIALIZED = "siminovInitialized";

	/**
	 * Web Siminov Stopped Event API.
	 */
	public String ISIMINOV_EVENT_SIMINOV_STOPPED = "siminovStopped";
	
	
	/**
	 * Web Database Created Event API.
	 */
	public String IDATABASE_EVENT_DATABASE_CREATED = "databaseCreated";

	/**
	 * Web Database Dropped Event API.
	 */
	public String IDATABASE_EVENT_DATABASE_DROPPED = "databaseDropped";
	
	/**
	 * Web Table Created Event API.
	 */
	public String IDATABASE_EVENT_TABLE_CREATED = "tableCreated";
	
	/**
	 * Web Table Dropped Event API.
	 */
	public String IDATABASE_EVENT_TABLE_DROPPED = "tableDropped";

	/**
	 * Web Index Created Event API.
	 */
	public String IDATABASE_EVENT_INDEX_CREATED = "indexCreated";
	
	/**
	 * Web Index Dropped Event API.
	 */
	public String IDATABASE_EVENT_INDEX_DROPPED = "indexDropped";
	
	
	/**
	 * Web Events.
	 */
	public String EVENTS = "Events";
	
	
	/**
	 * Web Event Parameters. 
	 */
	public String EVENT_PARAMETERS = "EventParameters";
	
}
