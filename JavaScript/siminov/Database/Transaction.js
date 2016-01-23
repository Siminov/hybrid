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
	Exposes classes which deal with database.
	A Siminov Database Abstraction Layer is an application programming interface which unifies the communication between a computer application and database such as SQLite.
	Siminov Database Layer reduce the amount of work by providing a consistent API to the developer and hide the database specifics behind this interface as much as possible.

	@module Database
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
    var Dictionary = require('../Collection/Dictionary');
    
    module.exports = Transaction;    
}


/**
	A transaction is a sequence of operations performed as a single logical unit of work.
	It exposes methods to add request to a transaction

	@module Database
	@class Transaction
	@constructor
*/
function Transaction() {
	
	var requests = new Dictionary();

	
	this.getRequests = function() {
		return requests.values();
	}
	
	this.getRequest = function(requestId) {
		return requests.get(requestId);
	}
	
	this.addRequest = function(adapter) {
		requests.add(adapter.getRequestId(), adapter);
	}
	
	this.removeRequest = function(requestId) {
		requests.remove(requestId);
	}
		
	this.containRequest = function(requestId) {
		return requests.exists(requestId);
	}
}