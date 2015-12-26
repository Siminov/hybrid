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
	It contain all parser classes required by  Siminov Framework.
	
	@module Parser
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
    var Function = require('../Function/Function');
    var HybridSiminovDatas = require('../Model/HybridSiminovDatas');
    
    module.exports = SIDatasHelper;
}

/**
	Exposes APIs to deal with Models, it convert si datas to models or models to si datas.
	
	@module Parser
	@class SIDatasHelper
	@constructor
	
*/
function SIDatasHelper() {

}


/**
	Convert SI Datas to Models.
	
	@method toModels
	@static 
*/
SIDatasHelper.toModels = function(siDatas) {

    var models = [];
    var datas = siDatas.datas;

    for(var i = 0;i < datas.length;i++) {

        var data = datas[i];

        var model = SIDatasHelper.toModel(data);
        models[models.length] = model;
    }

    return models;
}



/**
	Convert SI Data to Model
	
	@method toModel
	@static
*/
SIDatasHelper.toModel = function(data) {

    var model = Function.createFunctionInstance(data.type);
    var values = data.values;

    if(values != undefined) {

        for(var j = 0;j < values.length;j++) {
            var value = values[j];

            var type = value.type;
            var val = value.value;

            if(data.type === "Array") {
            	
                if(type != undefined) {
                    model[j] = [type, val];
                } else {
                    model.push(val);
                }
            } else {
                Function.invokeAndInflate(model, "set" + type.charAt(0).toUpperCase() + type.substring(1, type.length), val);
            }
        }
    }

    var innerDatas = data.datas;
    if(innerDatas != undefined) {

        for(var i = 0;i < innerDatas.length;i++) {
            var innerData = innerDatas[i];
            var innerModel = SIDatasHelper.toModel(innerData);

            var innerDataType = innerData.type;
            if(innerDataType === "Array" && innerModel != undefined) {
                
                for(var j = 0;j < innerModel.length;j++) {
                	
                    var type = innerModel[j][0];
                    var value = innerModel[j][1];

					if(type == undefined) {
						var innerModelDataType = innerModel[j].getFunctionName();

						type = innerModelDataType.substring(innerModelDataType.lastIndexOf('.') + 1, innerModelDataType.length);
						value = innerModel[j];
					}
					
                    var apiName = "add" + type.charAt(0).toUpperCase() + type.substring(1, type.length);
                    Function.invokeAndInflate(model, apiName, value);
                }
            } else {

                if(innerDataType.indexOf('.') !== -1) {
                    innerDataType = innerDataType.substring(innerDataType.lastIndexOf('.') + 1, innerDataType.length);

                    if(data.type === "Array") {
                        model[i] = [innerDataType, innerModel];
                    } else {

                        var apiName = "add" + innerDataType.charAt(0).toUpperCase() + innerDataType.substring(1, innerDataType.length);
                        Function.invokeAndInflate(model, apiName, innerModel);
                    }

                } else {
                	
                    if(data.type === "Array") {
                        model.push(innerModel);
                    } else {

                        var apiName = "add" + innerDataType.charAt(0).toUpperCase() + innerDataType.substring(1, innerDataType.length);
                        Function.invokeAndInflate(model, apiName, innerModel);
                    }
                }
            }
        }
    }

    return model;
}



/**
	Convert Model to SI Datas
*/
SIDatasHelper.toSI = function(object) {

    var siminovDatas = Object.create(HybridSiminovDatas);
    siminovDatas.datas = new Array();
    
    var data = SIDatasHelper.buildSI(object);
    siminovDatas.datas.push(data);

    return siminovDatas;
}

/**
 * Convert Model to SI Data
 */
SIDatasHelper.buildSI = function(object) {

	if(object && object.getterProperties().length <= 0) {
		return object;
	}

    var data = Object.create(HybridSiminovDatas.HybridSiminovData);
    data.datas = new Array();
	data.values = new Array();

    var modelName = object.getFunctionName();
    data.type = modelName;

    var getterProperties = object.getterProperties();

    for(var i = 0;i < getterProperties.length;i++) {

        var value = Object.create(HybridSiminovDatas.HybridSiminovData.HybridSiminovValue);

        if(getterProperties[i].indexOf("get") === 0) {
            var type = getterProperties[i].substring(3, getterProperties[i].length);
            var val = Function.invokeAndFetch(object, getterProperties[i]);

            value.type = type.charAt(0).toLowerCase() + type.substring(1, type.length);

            if(val instanceof Object && !(val instanceof Array)) {

				if(!val) {
					continue;
				}
				
                var obj = SIDatasHelper.buildSI(val);
                if(typeof obj === 'string') {
    				value.value = obj;
                } else if(typeof obj === 'object') {
    				data.datas.push(obj);      
                }                
            } else if(val instanceof Array) {

                if(val != undefined && val != null && val.length > 0) {

                    for(var j = 0;j < val.length;j++) {
                    
                        var obj = SIDatasHelper.buildSI(val[j]);
                        if(typeof obj === 'string') {
			                value.value = obj;
                        } else if(typeof obj === 'object') {
                        	data.datas.push(obj);
                        }
                    }
                }
            } else {
            
            	if(val) {
	                value.value = val.toString();
            	}
            }
        } else if(getterProperties[i].indexOf("is") == 0) {
            var type = getterProperties[i].substring(2, getterProperties[i].length);

            value.type = type.charAt(0).toLowerCase() + type.substring(1, type.length);
            var apiValue = Function.invokeAndFetch(object, getterProperties[i]);
            
            if(apiValue) {
            	value.value = apiValue.toString();
            }
        }


        data.values.push(value);
    }

    return data;
}