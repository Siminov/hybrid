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
	It contain Siminov defined exceptions.
	
	@module Exception
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
    module.exports = SiminovExceptionHandler;
    win.SiminovExceptionHandler = SiminovExceptionHandler;    
}

/**
	Any exception thrown from NATIVE-TO-HYBRID is handled and show to user. 
	
	@module Exception
	@class SiminovExceptionHandler
	@constructor	
*/
function SiminovExceptionHandler() {

	/**
		Display error to user.
		
		@method display
	*/
    this.display = function(data) {
        alert(data);
    }
}
