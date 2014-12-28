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



package siminov.web.reader;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import siminov.core.exception.SiminovCriticalException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.web.Constants;
import siminov.web.model.WebSiminovDatas;
import siminov.web.model.WebSiminovDatas.WebSiminovData;
import siminov.web.model.WebSiminovDatas.WebSiminovData.WebSiminovValue;
import siminov.web.utils.Utils;

/**
 * Exposes API to parse Siminov Web JSON format data to transfer between WEB-TO-NATIVE or NATIVE-TO-WEB.
 *
 */
public class WebSiminovDataReader implements Constants {

	private WebSiminovDatas webSiminovDatas = new WebSiminovDatas();
	
	public WebSiminovDataReader(String data) throws SiminovException {
		
		if(data == null || data.length() <= 0) {
			return;
		}
		
		JSONObject jsonData = toJSON(data);
	
		JSONObject webSiminovData = getJSONObject(WEB_SIMINOV_DATA, jsonData);
		JSONArray datas = getJSONArray(WEB_SIMINOV_DATA_DATA, webSiminovData);

		try {
			if(datas != null && datas.length() > 0) {
				for(int i = 0;i < datas.length();i++) {
					JSONObject jsonObject = null;
					try {
						jsonObject = datas.getJSONObject(i);
					} catch(Exception exception) {
						Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
						throw new SiminovException(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
					}
					
					WebSiminovData webData = parseData(jsonObject);
					webSiminovDatas.addWebSiminovData(webData);
				}
			}
		} catch(SiminovException siminovException) {
			Log.error(WebSiminovDataReader.class.getName(), "Constructor", "Exception caught while parsing data tag, " + siminovException.getMessage());
			throw siminovException;
		}
	}
	
	private WebSiminovData parseData(JSONObject data) throws SiminovException {
		
		if(data == null) {
			return null;
		}
		
		String dataType = null;
		String dataValue = null;
		JSONArray values = null;
		JSONArray innerDatas = null;
		
		Iterator<String> keys = data.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_JSON_TYPE)) {
				dataType = (String) getValue(key, data);
			} else if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_JSON_CDATA_SECTION) || key.equalsIgnoreCase(WEB_SIMINOV_DATA_JSON_TEXT)) { 
				dataValue = (String) getValue(key, data);
			} else if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_VALUE)) {
				
				try {
					values = data.getJSONArray(WEB_SIMINOV_DATA_VALUE);
				} catch(Exception exception) {
					Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while getting values array, " + exception.getMessage());
					throw new SiminovException(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while getting values array, " + exception.getMessage());
				}
			} else if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_DATA)) {
				
				try {
					innerDatas = data.getJSONArray(WEB_SIMINOV_DATA_DATA);
				} catch(Exception exception) {
					Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while getting datas array, " + exception.getMessage());
					throw new SiminovException(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while getting datas array, " + exception.getMessage());
				}
			}
		}
		
		WebSiminovData webSiminovData = new WebSiminovData();
		webSiminovData.setDataType(dataType);
		webSiminovData.setDataValue(dataValue);
		
		parseDataValue(webSiminovData, values);
		
		try {
			if(innerDatas != null && innerDatas.length() > 0) {
				for(int i = 0;i < innerDatas.length();i++) {
					JSONObject jsonObject = null;
					try {
						jsonObject = innerDatas.getJSONObject(i);
					} catch(Exception exception) {
						Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
						throw new SiminovException(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
					}
					
					WebSiminovData webData = parseData(jsonObject);
					webSiminovData.addData(webData);
				}
			}
		} catch(SiminovException siminovException) {
			Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while parsing data tag, " + siminovException.getMessage());
			throw siminovException;
		}
		
		
		return webSiminovData;
	}

	private void parseDataValue(WebSiminovData webSiminovData, JSONArray values) throws SiminovException {
		
		if(values == null || values.length() <= 0) {
			return;
		}
		
		for(int i = 0;i < values.length();i++) {
			
			JSONObject jsonObject = null;
			try {
				jsonObject = values.getJSONObject(i);
			} catch(Exception exception) {
				Log.error(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from values, " + exception.getMessage());
				throw new SiminovException(WebSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from values, " + exception.getMessage());
			}

			String valueType = null;
			String value = null;
			
			Iterator<String> keys = jsonObject.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_JSON_TYPE)) {
					valueType = (String) getValue(key, jsonObject);
				} else if(key.equalsIgnoreCase(WEB_SIMINOV_DATA_JSON_TEXT)){
					value = (String) getValue(key, jsonObject);
				}
			}
			
			WebSiminovValue webSiminovValue = new WebSiminovValue();
			webSiminovValue.setType(valueType);
			webSiminovValue.setValue(value);
			
			webSiminovData.addValue(webSiminovValue);
			
		}
		
	}

	private Object getValue(String name, JSONObject jsonObject) throws SiminovException {
		
		try {
			return jsonObject.get(name);
		} catch(Exception exception) {
			Log.error(WebSiminovDataReader.class.getName(), "", "Exception caught while getting value, " + exception.getMessage());
			throw new SiminovException(WebSiminovDataReader.class.getName(), "", "Exception caught while getting value, " + exception.getMessage());
		}
	}
	
	private JSONObject getJSONObject(String name, JSONObject jsonObject) throws SiminovException {
		
		try {
			return jsonObject.getJSONObject(name);
		} catch(Exception exception) {
			Log.error(WebSiminovDataReader.class.getName(), "", "Exception caught while getting json object, " + exception.getMessage());
			throw new SiminovException(WebSiminovDataReader.class.getName(), "", "Exception caught while getting json object, " + exception.getMessage());
		}
	}
	
	private JSONArray getJSONArray(String name, JSONObject jsonObject) throws SiminovException {
		
		try {
			return jsonObject.getJSONArray(name);
		} catch(Exception exception) {
			Log.error(WebSiminovDataReader.class.getName(), "", "Exception caught while getting json array, " + exception.getMessage());
			throw new SiminovException(WebSiminovDataReader.class.getName(), "", "Exception caught while getting json array, " + exception.getMessage());
		}
	}
	
	private JSONObject toJSON(String data) {
		
		if(data == null || data.length() <= 0) {
			return new JSONObject();
		}
		
		try {
			return new JSONObject(data);
		} catch(Exception exception) {
			Log.error(Utils.class.getName(), "toJSON", "Exception caught while converting string to json object, " + exception.getMessage());
			throw new SiminovCriticalException(Utils.class.getName(), "toJSON", "Exception caught while converting string to json object, " + exception.getMessage());
		}
		
	}

	/**
	 * Get Web Siminov Datas instance
	 * @return WebSiminovDatas instance
	 */
	public WebSiminovDatas getDatas() {
		return this.webSiminovDatas;
	}
	
}
