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
	It contain all Models as per required by Siminov Framework.

	@module Model
*/


/**
	Exposes methods to GET and SET Siminov Hybrid Datas structure.
		
	Example:
		<siminov-hybrid-data>
		
		    <data type="data_type">
		        <value type="value_type">value</value>
		        <data type="data_type"/>
		    </data>
		
		    <data type="data_type" />
		    
		</siminov-hybrid-data>

	@module Model	
	@class HybridSiminovDatas
 	@constructor
 	
 */
function HybridSiminovDatas() {

    var hybridSiminovDatas = [];

	
	/**
	 	Get Hybrid Siminov Datas.
	 	
	 	@method getHybridSiminovDatas
	 	@return Hybrid Siminov Datas.
	 */
    this.getHybridSiminovDatas = function() {
        return hybridSiminovDatas;
    }

	/**
	 	Get Hybrid Siminov Data based on Data Type provided.
 	 	
 	 	@method addHybridSiminovData
 	 	@param dataType {String} Data Type.
	 	@return {String} Hybrid Siminov Datas.
	 */
    this.addHybridSiminovData = function(hybridSiminovData) {
        hybridSiminovDatas.push(hybridSiminovData);
    }

}


/**
 	Exposes methods to GET and SET Siminov Hybrid Data structure.
 
    <data type="data_type">
        <value type="value_type">value</value>
        <data type="data_type"/>
    </data>
 
 	@class HybridSiminovDatas.HybridSiminovData
 	@constructor
 	
 */
HybridSiminovDatas.HybridSiminovData = function() {

    var dataType;
    var dataValue;

    var values = [];
    var datas = [];

	
	/**
	 	Get Data Type of Data.
	 	
	 	@method getDataType
	 	@return {String} Data Type of Data.
	 */
    this.getDataType = function() {
        return dataType;
    }

	/**
	 	Set Data Type of Data.
	 	
	 	@method setDataType
	 	@param dataType {String} Data Type of Data.
	 */
    this.setDataType = function(val) {
        dataType = val;
    }

	/**
	 	Get Data Value of Data.
	 
	 	@method getDataValue
		@return {String} Data Value of Data.
	 */
    this.getDataValue = function() {
        return dataValue;
    }

	/**
	 	Set Data Value of Data.
	 	
	 	@method setDataValue
	 	@param dataValue {String} Data Value of Data.
	 */
    this.setDataValue = function(val) {
        dataValue = val;
    }

	/**
	 	Get All Values contain within Data.
	 	
	 	@method getValues
	 	@return {Array} All Values.
	 */
    this.getValues = function() {
        return values;
    }

	/**
	 	Add Value.
	 	
	 	@method addValue
	 	@param hybridSiminovValue {HybridSiminovDatas.HybridSiminovData.HybridSiminovValue} Hybrid Siminov Value.
	 */
    this.addValue = function(val) {
        values.push(val);
    }

	/**
	 	Remove Hybrid Siminov Value.
	 	
	 	@method removeValue
	 	@param hybridSiminovValue {HybridSiminovDatas.HybridSiminovData.HybridSiminovValue} Hybrid Siminov Value.
	 */
    this.removeValue = function(val) {
        var indexVal = values.indexOf(val);

        if(indexVal != -1) {
            values.removeByIndex(indexVal);
        }
    }

	/**
	 	Check whether Hybrid Siminov Value exist or not.
	 	
	 	@method containValue
	 	@param hybridSiminovValue {HybridSiminovDatas.HybridSiminovData.HybridSiminovValue} Hybrid Siminov Value.
	 	@return {Boolean} true/false; TRUE if Hybrid Siminov Value exist, FALSE if Hybrid Siminov Value does not exist.
	 */
    this.containValue = function(val) {
        return values.isAvailable(val);
    }

	/**
	 	Get All Hybrid Siminov Data.
	 	
	 	@method getDatas
	 	@return {Array} All Hybrid Siminov Data.
	 */
    this.getDatas = function() {
        return datas;
    }

	/**
	 	Add Hybrid Siminon Data.
	 
	 	@method addData
		@param hybridSiminovData {HybridSiminovDatas.HybridSiminovData.HybridSiminovValue} Hybrid Siminov Data.
	 */
    this.addData = function(data) {
        datas.push(data);
    }

	/**
	 	Remove Hybrid Siminov Data.
	 	
	 	@method removeData
	 	@param hybridSiminovData {HybridSiminovDatas.HybridSiminovData} Hybrid Siminov Data.
	 */
    this.removeData = function(data) {
        var indexVal = values.indexOf(data);
        if(indexVal != -1)
            datas.removeByIndex(indexVal);
    }

	/**
	 	Check whether Hybrid Siminov Data exist or not.
	 	
	 	@method containData
	 	@param hybridSiminovData {HybridSiminovDatas.HybridSiminovData} Hybrid Siminov Data.
	 * @return {boolean} true/false; TRUE if Hybrid Siminov Data exist, FALSE if Hybrid Siminov Data does not exist. 
	 */
    this.containData = function(data) {
        return datas.isAvailable(data);
    }

}




/**
 	Exposes methods to GET and SET Siminov Hybrid Value structure.
 */
HybridSiminovDatas.HybridSiminovData.HybridSiminovValue = function() {
    var type;
    var value;


	/**
	 	Get Type of Hybrid Siminov Value.
	 	
	 	@method getType
	 	@return {String} Type of Hybrid Siminov Value.
	 */
    this.getType = function() {
        return type;
    }


	/**
	 	Set Type of Hybrid Siminov Value.
	 	
	 	@method setType
	 	@param type {String} Type of Hybrid Siminov Value.
	 */
    this.setType = function(val) {
        type = val;
    }


	/**
	 	Get Value of Hybrid Siminov Value.
	 
	 	@method getValue
		@return {String} Value of Hybrid Siminov Value.
	 */
    this.getValue = function() {
        return value;
    }


	/**
	 	Set Value of Hybrid Siminov Value.
	 
	 	@method setValue
		@param value {String} Value of Hybrid Siminov Value.
	 */
    this.setValue = function(val) {
        value = val;
    }
}
