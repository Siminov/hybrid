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


/**
	It contain all Events triggered by Siminov Framework.

	@module Events
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
    module.exports = INotificationEvents;    
}


/**
    It is a blue print for class which handles notification events

    @module Events
    @class INotificationEvents
    @constructor
*/
function INotificationEvents(notificationEvents) {
    
    return {

        /**
            This is the first method to be called when application is successfully registered with push notification platform service

            @method onRegistration
            @param registration {IRegistration} Registration
        */
        onRegistration: notificationEvents.onRegistration,
        
        /**
            This method is called when application get unregistered on the push notification platform

            @method onUnregistration
            @param registration {IRegistration} Registration
        */
        onUnregistration: notificationEvents.onUnregistration,
        
        /**
            This method is called when application gets any message/notification from server

            @method onNotification
            @param message {IMessage} Message
        */
        onNotification: notificationEvents.onNotification,
        
        /**
            This method is called if there is any error in process of registration/notification

            @method onError
            @param notificationException {NotificationException} Notification Exception
        */
        onError: notificationEvents.onError
    }
}
