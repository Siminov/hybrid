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
	Handle Request between NATIVE-TO-WEB and WEB-TO-NATIVE.
	Exposes method to GET and SET information about request.

	@class Adapter
 */
function Adapter() {

    var adapterName;
    var handlerName;

    var parameters = [];


	/**
		Get Adapter Name.
		
		@method getAdapterName
		@return Name of Adapter.	
	*/
    this.getAdapterName = function() {
        return adapterName;
    }

	/**
		Set Adapter Name.
		
		@method setAdapterName
		@param Name of Adapter.
	*/
    this.setAdapterName = function(value) {
        adapterName = value;
    }


	/**
		Get Handler Name.
		
		@method getHandlerName
		@return Name of Handler.
	*/
    this.getHandlerName = function() {
        return handlerName;
    }

	/**
		Set Handler Name.
		
		@method setHandlerName
		@param Name of Handler
	*/
    this.setHandlerName = function(value) {
        handlerName = value;
    }


	/**
		Add Adapter Parameter.
		
		@method addParameter
		@param Parameter.
	*/
    this.addParameter = function(parameter) {
        parameters.push(parameter);
    }

	/**
		Get All Parameters.
		
		@method getParameters
		@return All Parameters.
	*/
	this.getParameters = function() {
		return parameters;
	}

	
	/**
		Invokes Handler based on request parameter set.
		It invokes Native API.
		
		@method invoke
	*/
    this.invoke = function() {

        var siminovDatas = new HybridSiminovDatas();
        for(var i = 0;i < parameters.length;i++) {
            var siminovData = new HybridSiminovDatas.HybridSiminovData();

            siminovData.setDataValue(parameters[i]);
            siminovDatas.addHybridSiminovData(siminovData);
        }

        var json = SIJsonHelper.toJson(siminovDatas);
        return window.SIMINOV.handleWebToNative(adapterName + "." + handlerName, json);
    }


	/**
		Any request from NATIVE-TO-WEB is first handled by this API.
		
		@method handle
		@param Name of Action. Action Represent WEB API Needs To Be Invoke.
		@param Data Is Basically Parameter To WEB API.
	*/
    this.handle = function(action, data) {

        var functionName = action.substring(0, action.indexOf('.'));
        var apiName = action.substring(action.lastIndexOf('.') + 1, action.length);

        var obj = FunctionUtils.createFunctionInstance(functionName);
        FunctionUtils.invokeAndInflate(obj, apiName, data);

    }


}