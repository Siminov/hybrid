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
	It contain all parser classes required by  Siminov Framework.
	
	@module Parser
*/

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
    var datas = siDatas.getHybridSiminovDatas();

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

    var model = FunctionUtils.createFunctionInstance(data.getDataType());
    var values = data.getValues();

    if(values != undefined) {
        for(var j = 0;j < values.length;j++) {
            var value = values[j];

            var type = value.getType();
            var val = value.getValue();

            if(data.getDataType() === "Array") {
                if(type != undefined) {
                    model[j] = [type, val];
                } else {
                    model.push(val);
                }
            } else {
                FunctionUtils.invokeAndInflate(model, "set" + type.charAt(0).toUpperCase() + type.substring(1, type.length), val);
            }
        }
    }

    var innerDatas = data.getDatas();
    if(innerDatas != undefined) {
        for(var i = 0;i < innerDatas.length;i++) {
            var innerData = innerDatas[i];
            var innerModel = SIDatasHelper.toModel(innerData);

            var innerDataType = innerData.getDataType();

            if(innerDataType === "Array" && innerModel != undefined) {
                for(var j = 0;j < innerModel.length;j++) {
                    var type = innerModel[j][0];
                    var value = innerModel[j][1];

                    var apiName = "add" + type.charAt(0).toUpperCase() + type.substring(1, type.length);
                    FunctionUtils.invokeAndInflate(model, apiName, value);
                }
            } else {

                if(innerDataType.indexOf('.') !== -1) {
                    innerDataType = innerDataType.substring(innerDataType.lastIndexOf('.') + 1, innerDataType.length);

                    if(data.getDataType() === "Array") {
                        model[i] = [innerDataType, innerModel];
                    } else {

                        var apiName = "add" + innerDataType.charAt(0).toUpperCase() + innerDataType.substring(1, innerDataType.length);
                        FunctionUtils.invokeAndInflate(model, apiName, innerModel);
                    }

                } else {
                    if(data.getDataType() === "Array") {
                        model.push(innerModel);
                    } else {
                        var apiName = "add" + innerDataType.charAt(0).toUpperCase() + innerDataType.substring(1, innerDataType.length);
                        FunctionUtils.invokeAndInflate(model, apiName, innerModel);
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

    var datas = new HybridSiminovDatas();
    var data = SIDatasHelper.parseSI(object);

    datas.addHybridSiminovData(data);

    return datas;
}


SIDatasHelper.parseSI = function(object) {

    var data = new HybridSiminovDatas.HybridSiminovData();

    var modelName = object.getObjectName();
    data.setDataType(modelName);

    var getterProperties = object.getterProperties();

    for(var i = 0;i < getterProperties.length;i++) {

        var value = new HybridSiminovDatas.HybridSiminovData.HybridSiminovValue();

        if(getterProperties[i].indexOf("get") === 0) {
            var type = getterProperties[i].substring(3, getterProperties[i].length);
            var val = FunctionUtils.invokeAndFetch(object, getterProperties[i]);

            value.setType(type.charAt(0).toLowerCase() + type.substring(1, type.length));

            if(val instanceof Object && !(val instanceof Array)) {

                var obj = SIDatasHelper.parseSI(val);
                data.addData(obj);
            } else if(val instanceof Array) {

                if(val != undefined && val != null && val.length > 0) {

                    for(var j = 0;j < val.length;j++) {
                        var obj = SIDatasHelper.parseSI(val[j]);
                        data.addData(obj);
                    }
                }

            } else {
                value.setValue(val);
            }
        } else if(getterProperties[i].indexOf("is") == 0) {
            var type = getterProperties[i].substring(2, getterProperties[i].length);

            value.setType(type.charAt(0).toLowerCase() + type.substring(1, type.length));
            value.setValue(FunctionUtils.invokeAndFetch(object, getterProperties[i]));
        }


        data.addValue(value);
    }

    return data;
}