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

package siminov.hybrid.adapter.handlers;

import siminov.hybrid.adapter.IAdapter;

/**
 * It handles all request related to notification.
 * LIKE: Registration, Unregistration.
 */
public class NotificationHandler implements IAdapter {

	private siminov.connect.notification.NotificationManager notificationManager = siminov.connect.notification.NotificationManager.getInstance();
	
	/**
	 * It handles registration request from hybrid
	 */
	public void doRegistration() {
		notificationManager.doRegistration();
	}
	
	/**
	 * It handles unregistration request from hybrid
	 */
	public void doUnregistration() {
		notificationManager.doUnregistration();
	}
}
