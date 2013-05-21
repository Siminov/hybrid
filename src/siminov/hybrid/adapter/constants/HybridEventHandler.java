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

public interface HybridEventHandler {

	public String TRIGGERED_EVENT = "TriggeredEvent";
	
	public String ISIMINOV_EVENT_FIRST_TIME_SIMINOV_INITIALIZED = "firstTimeSiminovInitialized";
	public String ISIMINOV_EVENT_SIMINOV_INITIALIZED = "siminovInitialized";
	public String ISIMINOV_EVENT_SIMINOV_STOPPED = "siminovStopped";
	
	
	public String IDATABASE_EVENT_DATABASE_CREATED = "databaseCreated";
	public String IDATABASE_EVENT_DATABASE_DROPPED = "databaseDropped";
	public String IDATABASE_EVENT_TABLE_CREATED = "tableCreated";
	public String IDATABASE_EVENT_TABLE_DROPPED = "tableDropped";
	public String IDATABASE_EVENT_INDEX_CREATED = "indexCreated";
	public String IDATABASE_EVENT_INDEX_DROPPED = "indexDropped";
	
	
	public String EVENTS = "Events";
	
	public String EVENT_PARAMETERS = "EventParameters";
	
}
