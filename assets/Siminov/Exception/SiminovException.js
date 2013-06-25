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
	It contain Siminov defined exceptions.
	
	@module Exception
*/


/**
	This is general exception, which is thrown through Siminov APIs, if any exception occur while performing any tasks.

	@module Exception
	@class SiminovException
	@constructor 
	@param className {String} Name of Class
	@param methodName {String} Name of Method
	@param message {String} Message

*/
function SiminovException(className, methodName, message) {

    var className = className;
    var methodName = methodName;
    var message = message;


	/**
	 	Get POJO class name.
	 	
	 	@method getClassName
	 	@return POJO Class Name.
	 */
    this.getClassName = function() {
        return className;
    }


	/**
	 	Set POJO class name.
	 	
	 	@method setClassName
	 	@param className POJO Class Name.
	 */
    this.setClassName = function(val) {
        className = val;
    }


	/**
	 	Get method Name.
	 	
	 	@method getMethodName
	 	@return Name Of Method.
	 */
    this.getMethodName = function() {
        return methodName;
    }


	/**
	 	Set method Name.
	 	
	 	@Method setMethodName
	 	@param methodName Name Of Method.
	 */
    this.setMethodName = function(val) {
        methodName = val;
    }


	/**
	 	Get message.

		@method getMessage	 	
	 	@return Message.
	 */
    this.getMessage = function() {
        return message;
    }


	/**
	 	Set message.
	 	
	 	@method setMessage
	 	@param message Message.
	 */
    this.setMessage = function(val) {
        message = val;
    }

}

