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
	It contain all Models as per required by Siminov Framework.

	@module Model
*/


/**
	Exposes methods to GET and SET Siminov Web Datas structure.
		
	Example:
		<siminov-web-data>
		
		    <data type="data_type">
		        <value type="value_type">value</value>
		        <data type="data_type"/>
		    </data>
		
		    <data type="data_type" />
		    
		</siminov-web-data>

	@module Model	
	@class WebSiminovDatas
 	@constructor
 	
 */
function WebSiminovDatas() {

    var webSiminovDatas = new Array();

	
	/**
	 	Get Web Siminov Datas.
	 	
	 	@method getWebSiminovDatas
	 	@return Web Siminov Datas.
	 */
    this.getWebSiminovDatas = function() {
        return webSiminovDatas;
    }

	/**
	 	Get Web Siminov Data based on Data Type provided.
 	 	
 	 	@method addWebSiminovData
 	 	@param dataType {String} Data Type.
	 	@return {String} Web Siminov Datas.
	 */
    this.addWebSiminovData = function(webSiminovData) {
        webSiminovDatas.push(webSiminovData);
    }

}


/**
 	Exposes methods to GET and SET Siminov Web Data structure.
 
    <data type="data_type">
        <value type="value_type">value</value>
        <data type="data_type"/>
    </data>
 
 	@class WebSiminovDatas.WebSiminovData
 	@constructor
 	
 */
WebSiminovDatas.WebSiminovData = function() {

    var dataType;
    var dataValue;

    var values = new Array();
    var datas = new Array();

	
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
	 	@param webSiminovValue {WebSiminovDatas.WebSiminovData.WebSiminovValue} Web Siminov Value.
	 */
    this.addValue = function(val) {
        values.push(val);
    }

	/**
	 	Remove Web Siminov Value.
	 	
	 	@method removeValue
	 	@param webSiminovValue {WebSiminovDatas.WebSiminovData.WebSiminovValue} Web Siminov Value.
	 */
    this.removeValue = function(val) {
        var indexVal = values.indexOf(val);

        if(indexVal != -1) {
            values.removeByIndex(indexVal);
        }
    }

	/**
	 	Check whether Web Siminov Value exist or not.
	 	
	 	@method containValue
	 	@param webSiminovValue {WebSiminovDatas.WebSiminovData.WebSiminovValue} Web Siminov Value.
	 	@return {Boolean} true/false; TRUE if Web Siminov Value exist, FALSE if Web Siminov Value does not exist.
	 */
    this.containValue = function(val) {
        return values.isAvailable(val);
    }

	/**
	 	Get All Web Siminov Data.
	 	
	 	@method getDatas
	 	@return {Array} All Web Siminov Data.
	 */
    this.getDatas = function() {
        return datas;
    }

	/**
	 	Add Web Siminon Data.
	 
	 	@method addData
		@param webSiminovData {WebSiminovDatas.WebSiminovData.WebSiminovValue} Web Siminov Data.
	 */
    this.addData = function(data) {
        datas.push(data);
    }

	/**
	 	Remove Web Siminov Data.
	 	
	 	@method removeData
	 	@param webSiminovData {WebSiminovDatas.WebSiminovData} Web Siminov Data.
	 */
    this.removeData = function(data) {
        var indexVal = values.indexOf(data);
        if(indexVal != -1)
            datas.removeByIndex(indexVal);
    }

	/**
	 	Check whether Web Siminov Data exist or not.
	 	
	 	@method containData
	 	@param webSiminovData {WebSiminovDatas.WebSiminovData} Web Siminov Data.
	 * @return {boolean} true/false; TRUE if Web Siminov Data exist, FALSE if Web Siminov Data does not exist. 
	 */
    this.containData = function(data) {
        return datas.isAvailable(data);
    }

}




/**
 	Exposes methods to GET and SET Siminov Web Value structure.
 */
WebSiminovDatas.WebSiminovData.WebSiminovValue = function() {
    var type;
    var value;


	/**
	 	Get Type of Web Siminov Value.
	 	
	 	@method getType
	 	@return {String} Type of Web Siminov Value.
	 */
    this.getType = function() {
        return type;
    }


	/**
	 	Set Type of Web Siminov Value.
	 	
	 	@method setType
	 	@param type {String} Type of Web Siminov Value.
	 */
    this.setType = function(val) {
        type = val;
    }


	/**
	 	Get Value of Web Siminov Value.
	 
	 	@method getValue
		@return {String} Value of Web Siminov Value.
	 */
    this.getValue = function() {
        return value;
    }


	/**
	 	Set Value of Web Siminov Value.
	 
	 	@method setValue
		@param value {String} Value of Web Siminov Value.
	 */
    this.setValue = function(val) {
        value = val;
    }
}
