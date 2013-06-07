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
	A mutable sequence of characters.
	The principal operations on a StringBuilder are the append methods.
	The append method always adds these characters at the end of the builder;
 
 	@class StringBuilder
*/
function StringBuilder(value) {
	this.strings = new Array("");
	this.append(value);
}

/**
	Append adds given characters at the end of the builder.
	
	@method append
	@param Characters need to be append.
*/
StringBuilder.prototype.append = function (value) {
	if (value) {
		this.strings.push(value);
	}
}


/**
	Clear buffered StringBuider.
	Remove all elements saved.
	
	@method clear
*/
StringBuilder.prototype.clear = function () {
	this.strings.length = 1;
}


/**
	Get characters saved in StringBuilder.
	
	@method toString
	@return All Characters
*/
StringBuilder.prototype.toString = function () {
	return this.strings.join("");
}