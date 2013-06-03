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


function Adapter() {

    var adapterName;
    var handlerName;

    var parameters = [];

    this.getAdapterName = function() {
        return adapterName;
    }

    this.setAdapterName = function(value) {
        adapterName = value;
    }

    this.getHandlerName = function() {
        return handlerName;
    }

    this.setHandlerName = function(value) {
        handlerName = value;
    }

    this.addParameter = function(parameter) {
        parameters.push(parameter);
    }

	this.getParameters = function() {
		return parameters;
	}


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

    this.handle = function(action, data) {

        var functionName = action.substring(0, action.indexOf('.'));
        var apiName = action.substring(action.lastIndexOf('.') + 1, action.length);

        var obj = FunctionUtils.createFunctionInstance(functionName);
        FunctionUtils.invokeAndInflate(obj, apiName, data);

    }

}