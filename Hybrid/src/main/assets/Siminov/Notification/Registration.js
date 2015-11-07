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
	It exposes APIs to Get and Set push notification registration information
	
	@module Notification
	@class Registration
	@constructor
*/
function Registration() {

	var registrationId;
	
	/**
	 * Get registration id 
	 * 
	 * @method getRegistrationId
	 * @return {String} Registration id
	 */
	this.getRegistrationId = function() {
		return registrationId;
	}
	
	/**
	 * Set registration id
	 * 
	 * @method setRegistrationId
	 * @param val {String} Registration Id
	 */
	this.setRegistrationId = function(val) {
		registrationId = val;		
	}
}