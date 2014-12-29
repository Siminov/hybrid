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



/**
	Exposes classes which deal with services.
	Service is a client-side communication component that process and handles any web service request. It performs long running operations in the background.
	A Service is a group of APIs which deals on one particular web service.
	
	@module Service
*/


/**
	Design contain all interfaces required by service layer to deal with service request.

	@module Service
	@submodule Design
*/


/**
	It exposes APIs to handle service request events

	@module Service
	@submodule Design
	@class IServiceEvents
	@constructor
	@param serviceEvents {ServiceEvents} Service Events class object.
*/
function IServiceEvents(serviceEvents) {

	return {
		
		/**
			This is the first method to be called when a service is created. 
			
			<p>
			OnStart is always overridden to perform any startup initializations that may be required by a Service such as:
			
			<p>
			<ui>
				<li> Initializing variables
				<li> Binding static data to service
				<li> Binding related screen to service
			</ui>
			<p>
			Once OnStart has finished, Connect will call OnServiceQueue if Service is in ASYNC mode else OnServiceApiInvoke.	
		 * 
		 * @method onStart
		 */
		onStart: serviceEvents.onStart,
		
		/**
		 * This method is called when the service is put in the queue for the execution. 
		 */
		onQueue: serviceEvents.onQueue,
		
		/**
			This method is called when there is no network. Services should override this method if they need to:
	
			<p>
			<ui>
				<li> Commit unsaved changes to persistent data
				<li> Destroy or clean up other objects consuming resources
				<li> Display any relevant alerts or dialogs
			</ui>
		 * 
		 * @method onPause
		 */
		onPause: serviceEvents.onPause,
		
		
		/**
			The Connect calls this method when the Service is ready to start executing. 
			<p>
			Services should override this method to perform tasks such as:		
				
			<p>
			<ui>	
				<li> Display any relevant alerts or dialogs
				<li> Wire up external event handlers
				<li> Listening for GPS updates
		 	<ui>
		 * 
		 * @method onResume
		 */
		onResume: serviceEvents.onResume,


		/**
		 * This is the final method that is called on a Service instance before it’s destroyed and completely removed from memory.
			<p>
			There will be no lifecycle methods called after the Activity has been destroyed.
		* 
		* @method onFinish
		 */
		onFinish: serviceEvents.onFinish,


		/**
		 * This method is called before Service calls Web Service Request. 
		 * 
		 * @method onRequestInvoke
		 * @param connectionRequest IConnectionRequest instance
		 */
		onRequestInvoke: serviceEvents.onRequestInvoke,
		
	
		/**
		 * This method is called after Web Service Request is executed.
		 * 
		 * @method onRequestFinish
		 * @param connectionResponse IConnectionResponse instance
		 */
		onRequestFinish: serviceEvents.onRequestFinish,
		
	
		/**
		 * This method is called when there is any exception while executing the service. 
			<p>
			Once this is called the service will be terminated and release from the memory.
			* 
		* @method onTerminate
		 * @param serviceException
		 */
		onTerminate: serviceEvents.onTerminate		
	}
}