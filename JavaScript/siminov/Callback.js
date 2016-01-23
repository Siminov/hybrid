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
    module.exports = Callback;
}


/**
	Used to handle asynchronous request callbacks
	It exposes event function such as onSuccess, onFailure, onExecute

*/
function Callback() {

	/**
		It is invoked when the request is successful completed

		@method onSuccess
	*/
	this.onSuccess;


	/**
		It is invoked when the request is failed

		@method onFailure
	*/
	this.onFailure;
	

	/**
		It is invoked when transaction is executed

		@method onExecute
	*/
	this.onExecute;
}