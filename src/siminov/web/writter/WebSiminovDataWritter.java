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



package siminov.web.writter;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import siminov.web.Constants;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;

/**
 * Exposes API to build Siminov Web JSON format data to transfer between WEB-TO-NATIVE or NATIVE-TO-WEB.
 *
 */
public class WebSiminovDataWritter {

	
	/**
	 * Build JSON using Web Siminov Datas.
	 * @param jsSiminovDatas Web Siminov Datas.
	 * @return Siminov JSON
	 * @throws SiminovException If any error occur while generating JSON out of Web Siminov Datas.
	 */
	public static String jsonBuidler(WebSiminovDatas jsSiminovDatas) throws SiminovException {
		
		JSONObject jsonWebSiminovData = new JSONObject();
		JSONObject jsonWebData = new JSONObject();
		JSONArray jsonWebSiminovDatas = new JSONArray();

		Iterator<WebSiminovData> webDatas = jsSiminovDatas.getWebSiminovDatas();
		while(webDatas.hasNext()) {
			
			WebSiminovData webSiminovData = webDatas.next();
			JSONObject jsonSiminovData = jsonBuilder(webSiminovData);
			
			jsonWebSiminovDatas.put(jsonSiminovData);
			
		}

		
		if(jsonWebSiminovDatas.length() > 0) {
			try {
				jsonWebData.accumulate(Constants.WEB_SIMINOV_DATA_DATA, jsonWebSiminovDatas);
			} catch(Exception exception) {
				Log.error(WebSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding json js core datas, " + exception.getMessage());
				throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding json js core datas, " + exception.getMessage());
			}
		}


		try {
			jsonWebSiminovData.accumulate(Constants.WEB_SIMINOV_DATA, jsonWebData);
		} catch(Exception exception) {
			Log.error(WebSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding final js core data, " + exception.getMessage());
			throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding final js core data, " + exception.getMessage());
		}
		
		return jsonWebSiminovData.toString();
		
	}
	
	private static JSONObject jsonBuilder(WebSiminovData webSiminovData) throws SiminovException {
		
		String dataType = webSiminovData.getDataType();
		String dataValue = webSiminovData.getDataValue();
		
		JSONObject jsonWebSiminovData = new JSONObject();

		try {
			jsonWebSiminovData.put(Constants.WEB_SIMINOV_DATA_JSON_TYPE, dataType);
		} catch(Exception exception) {
			Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data type to json js core data, DATA-TYPE: " + dataType + ", " + exception.getMessage());
			throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data type to json js core data, DATA-TYPE: " + dataType + ", " + exception.getMessage());
		}

		try {
			jsonWebSiminovData.put(Constants.WEB_SIMINOV_DATA_JSON_TEXT, dataValue);
		} catch(Exception exception) {
			Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to json js core data, DATA: " + dataValue + ", " + exception.getMessage());
			throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to json js core data, DATA: " + dataValue + ", " + exception.getMessage());
		}
		
		JSONArray jsonWebSiminovValues = new JSONArray();
		JSONArray jsonWebSiminovSubDatas = new JSONArray();
		
		Iterator<WebSiminovValue> webSiminovValues = webSiminovData.getValues();
		Iterator<WebSiminovData> webSiminovSubDatas = webSiminovData.getDatas();
		
		while(webSiminovValues.hasNext()) {
			
			WebSiminovValue webSiminovValue = webSiminovValues.next();
			String valueType = webSiminovValue.getType();
			String value = webSiminovValue.getValue();
			
			JSONObject jsonWebSiminovValue = new JSONObject();

			try {
				jsonWebSiminovValue.put(Constants.WEB_SIMINOV_DATA_JSON_TYPE, valueType);	
			} catch(Exception exception) {
				Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value type, VALUE-TYPE: " + valueType + ", " + exception.getMessage());
				throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value type, VALUE-TYPE: " + valueType + ", " + exception.getMessage());
			}
			
			try {
				jsonWebSiminovValue.put(Constants.WEB_SIMINOV_DATA_JSON_TEXT, value);
			} catch(Exception exception) {
				Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value, VALUE: " + value + ", " + exception.getMessage());
				throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value, VALUE: " + value + ", " + exception.getMessage());
			}
			
			jsonWebSiminovValues.put(jsonWebSiminovValue);
		}
		
		while(webSiminovSubDatas.hasNext()) {
			WebSiminovData webSiminovSubData = webSiminovSubDatas.next();
			JSONObject jsonWebSiminovSubData = jsonBuilder(webSiminovSubData);
			
			jsonWebSiminovSubDatas.put(jsonWebSiminovSubData);
		}

		if(jsonWebSiminovValues.length() >  0) {
			try {
				jsonWebSiminovData.accumulate(Constants.WEB_SIMINOV_DATA_VALUE, jsonWebSiminovValues);
			} catch(Exception exception) {
				Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value to json js core data, " + exception.getMessage());
				throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value to json js core data, " + exception.getMessage());
			}
		}
		

		if(jsonWebSiminovSubDatas.length() > 0) {
			try {
				jsonWebSiminovData.accumulate(Constants.WEB_SIMINOV_DATA_DATA, jsonWebSiminovSubDatas);
			} catch(Exception exception) {
				Log.error(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to js core data, " + exception.getMessage());
				throw new SiminovException(WebSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to js core data, " + exception.getMessage());
			}
		}
		
		return jsonWebSiminovData;
	}
	
}
