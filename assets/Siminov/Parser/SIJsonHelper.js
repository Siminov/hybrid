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
	Exposes APIs to deal with SI Datas, it convert models to SI Datas or SI Datas to models
	
	@module Parser
	@class SIJsonHelper
	
*/
function SIJsonHelper() {

}


/**
	Convert JSON Data to SI.
	
	@method toSI
	@static
*/
SIJsonHelper.toSI = function(datas) {
    Log.logd("SIJsonHelper", "toSI", datas);

    var obj = eval("(" + datas + ")");
    var datas = new HybridSiminovDatas();

    var datasProperties = obj[Constants.SIMINOV_HYBRID_DATA];
    var dataProperties = datasProperties[Constants.SIMINOV_HYBRID_DATA_DATA];

    if(dataProperties != undefined && dataProperties != null && dataProperties.length > 0) {

        for(var i = 0;i < dataProperties.length;i++) {
            var dataProperty = dataProperties[i];
            var data = SIJsonHelper.parseJson(dataProperty);
            datas.addHybridSiminovData(data);
        }
    }
    

    return datas;
}


SIJsonHelper.parseJson = function(dataProperty) {

    var data = new HybridSiminovDatas.HybridSiminovData();

    if(dataProperty[Constants.SIMINOV_HYBRID_DATA_DATA_TYPE] != undefined) {
        data.setDataType(dataProperty[Constants.SIMINOV_HYBRID_DATA_DATA_TYPE]);
    }

    if(dataProperty[Constants.SIMINOV_HYBRID_DATA_DATA_TEXT] != undefined) {
        data.setDataValue(dataProperty[Constants.SIMINOV_HYBRID_DATA_DATA_TEXT]);
    }

    var values = dataProperty[Constants.SIMINOV_HYBRID_DATA_VALUE];
    if(values != undefined) {
        for(var j = 0;j < values.length;j++) {
            var value = values[j];

            var type = value[Constants.SIMINOV_HYBRID_DATA_DATA_TYPE];
            var val = value[Constants.SIMINOV_HYBRID_DATA_TEXT];

            var dataValue = new HybridSiminovDatas.HybridSiminovData.HybridSiminovValue();
            dataValue.setType(type);
            dataValue.setValue(val);

            data.addValue(dataValue);
        }
    }

    var innerDatas =  dataProperty[Constants.SIMINOV_HYBRID_DATA_DATA];
    if(innerDatas != undefined) {
        for(var j = 0;j < innerDatas.length;j++) {
            var innerData = innerDatas[j];

            var innerDataProperty = new HybridSiminovDatas.HybridSiminovData();
            var innerSIData = SIJsonHelper.parseJson(innerData);

            data.addData(innerSIData);
        }
    }

    return data;
}



/**
	Convert SI to JSON Data
	
	@method toJson
	@static
*/
SIJsonHelper.toJson = function(datas) {

    var siDatas = datas.getHybridSiminovDatas();

        var json = new StringBuilder();

        json.append("{");
            json.append('"' + Constants.SIMINOV_HYBRID_DATA + '": {');
            json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA + '": [');

            for(var i = 0;i < siDatas.length;i++) {
                json.append(SIJsonHelper.parseSI(siDatas[i]));
            }

            json.append("]");
            json.append("}");
        json.append("}");

    Log.logd("SIJsonHelper", "toJson", json.toString());
    return JSON.stringify(eval("(" + json.toString() + ")"));

}



SIJsonHelper.parseSI = function(data) {

    var type = data.getDataType();
    var val = data.getDataValue();

    var json = new StringBuilder();
    json.append("{");

    if(type != undefined && type != null && type.length > 0) {
        json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA_TYPE + '": "' + type + '", ');
    }

    var datas = data.getDatas();
    if(datas != undefined && datas != null && datas.length > 0) {
        json.append(SIJsonHelper.parseInnerSI(datas));
    }

    if(val == undefined || val == null) {

        var values = data.getValues();
        if(values != undefined && values.length > 0) {

            json.append('"' + Constants.SIMINOV_HYBRID_DATA_VALUE + '": [');

            for(var j = 0;j < values.length;j++) {

                var value = values[j];

                var valueType = value.getType();
                var valueVal = value.getValue();

                json.append("{");
                    json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA_TYPE + '": "' + valueType + '", ');
                    json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA_TEXT + '": "' + valueVal + '"');
                json.append("},");

            }

            json.append("], ");

        }

    } else {
        if(val != undefined && val != null && val.length > 0) {
            json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA_TEXT + '": "' + val + '"');
        }
    }

    json.append("},");

    return json.toString();
}


SIJsonHelper.parseInnerSI = function(datas) {

    var json = new StringBuilder();
    json.append('"' + Constants.SIMINOV_HYBRID_DATA_DATA + '": [');

    for(var i = 0;i < datas.length;i++) {
        json.append(SIJsonHelper.parseSI(datas[i]));
    }

    json.append("], ");

    return json.toString();
}
