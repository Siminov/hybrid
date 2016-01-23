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
	A Collection represents a group of objects, know as its elements.
	Siminov Collection (SI Collection) is a set of classes and interfaces that implement commonly reusable collection data structures.

	@module Collection
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
    module.exports = String;
}

/**
    Check whether string starts with the passed string or not

    @method startsWith
    @param str {String} String to check whether it start with this or not
    @return bool {Bool} true/false True if it contains the string else false
*/
String.prototype.startsWith = function (str) {
	return this.indexOf(str) === 0;
};


/**
    Replace the string occurrence with the stirng

    @method replaceAll
    @param find {String}
    @param replace {String}
*/
String.prototype.replaceAll = function (find, replace) {
    var str = this;
    return str.replace(new RegExp(find.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&'), 'g'), replace);
};