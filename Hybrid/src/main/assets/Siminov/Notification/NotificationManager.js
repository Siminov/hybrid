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
	It contain all Classes related to push notification.

	@module Notification
*/

/**
	It exposes APIs to Get and Set push notification 
	
	@module Notification
	@class NotificationManager
	@constructor
*/
var NotificationManager = (function() {

	var notificationManager;
	
	return {
		
		/**
		 * It provides singleton instance of Notification Manager
		 * @return NotificationManager singleton instance
		 */
		getInstance : function() {
			if(notificationManager == null) {
				notificationManager = new NotificationManager();
				
				notificationManager.constructor = null;
			}
			
			return notificationManager;
		}
	
	}
	
	
	function NotificationManager() {
		
		/**
		 * It executes the registration process of push notification
		 * 
		 * @method doRegistration
		 */
		this.doRegistration = function() {

		    var adapter = new Adapter();
		    adapter.setAdapterName(Constants.NOTIFICATION_ADAPTER);
		    adapter.setHandlerName(Constants.NOTIFICATION_ADAPTER_DO_REGISTRATION_HANDLER);
		
			adapter.invoke();				
		}
			
			
		/**
		 * It executes the unregistration process of push notification
		 * 
		 * @method doUnregistration
		 */	
		this.doUnregistration = function() {

		    var adapter = new Adapter();
		    adapter.setAdapterName(Constants.NOTIFICATION_ADAPTER);
		    adapter.setHandlerName(Constants.NOTIFICATION_ADPATER_DO_UNREGISTRATION_HANDLER);
		
			adapter.invoke();				
		}
	}

		
}) ();


