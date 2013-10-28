/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution LLP|support@siminov.com]
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
	A Collection represents a group of objects, know as its elements.
	Siminov Collection (SI Collection) is a set of classes and interfaces that implement commonly reusable collection data structures.
	
	@module Collection
*/


/**
	Dictionary maps keys to values. A Dictionary cannot contain duplicate keys; each key can map to at most on value.

	@module Collection	 
 	@class Dictionary
 	@constructor
*/
function Dictionary() {
    var data = {};

	/**
		Add a Key Value pair.
		
		@method add
		@param key {String} Key
		@param value {Object} Value
	*/
    this.add = function(key, value) {
        if(!this.exists(key)) {
            data[key] = value;
        }
    }


	/**
		Check whether key exists or not.
		
		@method exists
		@param key {String} Key
		@return {Boolean} true/false; TRUE: If key exists, FALSE: If key does not exists.
	*/
    this.exists = function(key) {
        return data[key] != null;
    }

	/**
		Get value based on Key.
		
		@method get
		@param key {String} Key
		@return {Object} Value
	*/
    this.get = function(key) {
        return data[key];
    }

	/**
		Remove value based on key.
		
		@method remove
		@param key {String} Key
	*/
	this.remove = function(key) {
		delete data[key];
	}


	/**
		Get All Keys.
		
		@method keys
		@return {Array} All Keys
	*/
    this.keys = function() {
        var keys = [];

        var properties = data.properties();
        return properties;
    }


	/**
		Get All Values.
		
		@method values
		@return {Array} Values.
	*/
	this.values = function() {
		return data;
	}

	/**
		Get All Key and Values
		
		@method toArray
		@return {Array} Key and Values.
	*/
    this.toArray = function() {
        return data;
    }

}
