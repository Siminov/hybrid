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
    Exposes utility api's to framework/application

	@module Utils
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
    module.exports = Utils;
}


/**
    Exposes utility api's to framework/application

    @module Utils
    @class Utils
*/
function Utils() {

}


/**
    Generates unique number

    @method uniqueNumber
    @return Unique Number
*/
Utils.uniqueNumber = function() {

	var date = Date.now();
    
    // If created at same millisecond as previous
    if (date <= Utils.uniqueNumberPrevious) {
        date = ++Utils.uniqueNumberPrevious;
    } else {
        Utils.uniqueNumberPrevious = date;
    }
    
    return date.toString();
}


Utils.uniqueNumberPrevious = 0;