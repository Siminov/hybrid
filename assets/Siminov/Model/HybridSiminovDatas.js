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

/*
    <siminov-hybrid-data>

        <data type="data_type">

            <value type="value_type">value</value>

            <data type="data_type"/>

        </data>

        <data type="data_type" />

    </siminov-hybrid-data>

*/

function HybridSiminovDatas() {

    var hybridSiminovDatas = [];

    this.getHybridSiminovDatas = function() {
        return hybridSiminovDatas;
    }

    this.addHybridSiminovData = function(hybridSiminovData) {
        hybridSiminovDatas.push(hybridSiminovData);
    }

}

HybridSiminovDatas.HybridSiminovData = function() {

    var dataType;
    var dataValue;

    var values = [];
    var datas = [];

    this.getDataType = function() {
        return dataType;
    }

    this.setDataType = function(val) {
        dataType = val;
    }

    this.getDataValue = function() {
        return dataValue;
    }

    this.setDataValue = function(val) {
        dataValue = val;
    }

    this.getValues = function() {
        return values;
    }

    this.addValue = function(val) {
        values.push(val);
    }

    this.removeValue = function(val) {
        var indexVal = values.indexOf(val);

        if(indexVal != -1) {
            values.removeByIndex(indexVal);
        }
    }

    this.containValue = function(val) {
        return values.isAvailable(val);
    }

    this.getDatas = function() {
        return datas;
    }

    this.addData = function(data) {
        datas.push(data);
    }

    this.removeData = function(data) {
        var indexVal = values.indexOf(data);
        if(indexVal != -1)
            datas.removeByIndex(indexVal);
    }

    this.containData = function(data) {
        return datas.isAvailable(data);
    }

}

HybridSiminovDatas.HybridSiminovData.HybridSiminovValue = function() {
    var type;
    var value;

    this.getType = function() {
        return type;
    }

    this.setType = function(val) {
        type = val;
    }

    this.getValue = function() {
        return value;
    }

    this.setValue = function(val) {
        value = val;
    }
}
