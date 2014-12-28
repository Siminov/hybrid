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

package siminov.web.adapter.constants;


/**
 * Exposes constants which represents Service on Web.
 */
public interface WebServiceHandler {

	/**
	 * Web Service Triggered Event
	 */
	public String TRIGGERED_EVENT = "TRIGGERED_EVENT";

	/**
	 * Web Service API Handler
	 */
	public String ISERVICE_API_HANDLER = "ISERVICE_API_HANDLER";
	
	/**
	 * Web Service On Start
	 */
	public String ISERVICE_ON_START = "onStart";

	/**
	 * Web Service On Queue
	 */
	public String ISERVICE_ON_QUEUE = "onQueue";

	/**
	 * Web Service On Pause
	 */
	public String ISERVICE_ON_PAUSE = "onPause";
	
	/**
	 * Web Service On Resume
	 */
	public String ISERVICE_ON_RESUME = "onResume";
	
	/**
	 * Web Service On Finish
	 */
	public String ISERVICE_ON_FINISH = "onFinish";
	
	/**
	 * Web Service On Request Invoke
	 */
	public String ISERVICE_ON_REQUEST_INVOKE = "onRequestInvoke";
	
	/**
	 * Web Service On Request Finish
	 */
	public String ISERVICE_ON_REQUEST_FINISH = "onRequestFinish";
	
	/**
	 * Web Service On Terminate
	 */
	public String ISERVICE_ON_TERMINATE = "onTerminate";
	
	/**
	 * Web Service Resources
	 */
	public String ISERVICE_RESOURCES = "RESOURCES";
}
