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
var win;
var dom;

try {

    if(!window) {
    	window = global || window;
    }

	win = window;
	dom = window['document'];
} catch(e) {
	win = Ti.App.Properties;
}



if(dom == undefined) {
    module.exports = NotificationManager;    
}

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

			var callback = arguments && arguments[0];

		    var adapter = new Adapter();
		    adapter.setAdapterName(Constants.NOTIFICATION_ADAPTER);
		    adapter.setHandlerName(Constants.NOTIFICATION_ADAPTER_DO_REGISTRATION_HANDLER);
		
			
			if(callback) {
				adapter.setCallback(doRegstrationCallback);
				adapter.setAdapterMode(Adapter.REQUEST_ASYNC_MODE);
				
				Adapter.invoke(adapter);
			} else {
				var data = Adapter.invoke(adapter);
				return doRegistrationCallback(data);				
			}
			
			
			function doRegistrationCallback(data) {
				
				if(callback) {
					callback && callback.onSuccess && callback.onSuccess();
				} else {
					return;
				}
			}
		}
			
		this.doRegistrationAsync = function(callback) {
			this.doRegistration(callback?callback:new Callback());
		}	
			
		/**
		 * It executes the unregistration process of push notification
		 * 
		 * @method doUnregistration
		 */	
		this.doUnregistration = function() {

			var callback = arguments && arguments[0];

		    var adapter = new Adapter();
		    adapter.setAdapterName(Constants.NOTIFICATION_ADAPTER);
		    adapter.setHandlerName(Constants.NOTIFICATION_ADPATER_DO_UNREGISTRATION_HANDLER);
		
		
			if(callback) {
				adapter.setCallback(doUnregistrationCallback);
				adapter.setAdapterMode(Adapter.REQUEST_ASYNC_MODE);
				
				Adapter.invoke(adapter);
			} else {
				var data = Adapter.invoke(adapter);
				return doUnregistrationCallback(data);				
			}
			
			
			function doUnregistrationCallback(data) {
				
				if(callback) {
					callback && callback.onSuccess && callback.onSuccess();
				} else {
					return;
				}
			}
		}
		
		
		this.doUnregistrationAsync = function(callback) {
			this.doUnregistration(callback);
		}
		
	}

		
}) ();


