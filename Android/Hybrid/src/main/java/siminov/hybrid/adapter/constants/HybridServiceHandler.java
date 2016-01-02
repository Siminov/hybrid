/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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
 * Exposes constants which represents Service on Hybrid.
 */
public interface HybridServiceHandler {

	/**
	 * Hybrid Service Triggered Event
	 */
	public String TRIGGERED_EVENT = "TRIGGERED_EVENT";

	/**
	 * Hybrid Service Request Id
	 */
	public String ISERVICE_REQUEST_ID = "ISERVICE_REQUEST_ID";

	
	/**
	 * Hybrid Service API Handler
	 */
	public String ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";
	
	/**
	 * Hybrid Service On Start
	 */
	public String ISERVICE_ON_START = "onStart";

	/**
	 * Hybrid Service On Queue
	 */
	public String ISERVICE_ON_QUEUE = "onQueue";

	/**
	 * Hybrid Service On Pause
	 */
	public String ISERVICE_ON_PAUSE = "onPause";
	
	/**
	 * Hybrid Service On Resume
	 */
	public String ISERVICE_ON_RESUME = "onResume";
	
	/**
	 * Hybrid Service On Finish
	 */
	public String ISERVICE_ON_FINISH = "onFinish";
	
	/**
	 * Hybrid Service On Request Invoke
	 */
	public String ISERVICE_ON_REQUEST_INVOKE = "onRequestInvoke";
	
	/**
	 * Hybrid Service On Request Finish
	 */
	public String ISERVICE_ON_REQUEST_FINISH = "onRequestFinish";
	
	/**
	 * Hybrid Service On Terminate
	 */
	public String ISERVICE_ON_TERMINATE = "onTerminate";
	
	/**
	 * Hybrid Service Resources
	 */
	public String ISERVICE_RESOURCES = "RESOURCES";
}
