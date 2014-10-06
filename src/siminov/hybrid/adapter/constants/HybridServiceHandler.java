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

package siminov.hybrid.adapter.constants;


/**
 * Exposes constants which represents Service on Web.
 */
public interface HybridServiceHandler {

	/**
	 * Web Service Triggered Event
	 */
	public String TRIGGERED_EVENT = "TRIGGERED_EVENT";

	/**
	 * Web Service API Handler
	 */
	public String ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";
	
	/**
	 * Web Service On Service Start
	 */
	public String ISERVICE_ON_SERVICE_START = "onServiceStart";

	/**
	 * Web Service On Service Queue
	 */
	public String ISERVICE_ON_SERVICE_QUEUE = "onServiceQueue";

	/**
	 * Web Service On Service Pause
	 */
	public String ISERVICE_ON_SERVICE_PAUSE = "onServicePause";
	
	/**
	 * Web Service On Service Resume
	 */
	public String ISERVICE_ON_SERVICE_RESUME = "onServiceResume";
	
	/**
	 * Web Service On Service Finish
	 */
	public String ISERVICE_ON_SERVICE_FINISH = "onServiceFinish";
	
	/**
	 * Web Service On Service Request Invoke
	 */
	public String ISERVICE_ON_SERVICE_REQUEST_INVOKE = "onServiceRequestInvoke";
	
	/**
	 * Web Service On Service Request Finish
	 */
	public String ISERVICE_ON_SERVICE_REQUEST_FINISH = "onServiceRequestFinish";
	
	/**
	 * Web Service On Service Terminate
	 */
	public String ISERVICE_ON_SERVICE_TERMINATE = "onServiceTerminate";
	
	/**
	 * Web Service Resources
	 */
	public String ISERVICE_RESOURCES = "RESOURCES";
}
