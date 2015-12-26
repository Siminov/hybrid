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
    module.exports = DeploymentException;
    win.DeploymentException = DeploymentException;    
}

/**
	This is general exception, which is thrown through Deployment APIs, if any exception occur while deploying application.

	@module Exception
	@class DeploymentException
	@constructor 
	@param className {String} Name of Class
	@param methodName {String} Name of Method
	@param message {String} Message

*/
function DeploymentException(className, methodName, message) {

}


//Function.extend(SiminovException, DeploymentException);