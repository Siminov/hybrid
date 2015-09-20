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



package siminov.hybrid.reader;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import siminov.core.exception.SiminovCriticalException;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;
import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.hybrid.utils.Utils;

/**
 * Exposes API to parse Siminov Hybrid JSON format data to transfer between HYBRID-TO-NATIVE or NATIVE-TO-HYBRID.
 *
 */
public class HybridSiminovDataReader implements Constants {

	private HybridSiminovDatas hybridSiminovDatas = new HybridSiminovDatas();
	
	public HybridSiminovDataReader(String data) throws SiminovException {
		
		if(data == null || data.length() <= 0) {
			return;
		}
		
		JSONObject jsonData = toJSON(data);
		JSONArray datas = getJSONArray(HYBRID_SIMINOV_DATAS, jsonData);

		try {
			if(datas != null && datas.length() > 0) {
				for(int i = 0;i < datas.length();i++) {
					
					JSONObject jsonObject = null;
					try {
						jsonObject = datas.getJSONObject(i);
					} catch(Exception exception) {
						Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
						throw new SiminovException(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
					}
					
					HybridSiminovData hybridData = parseData(jsonObject);
					hybridSiminovDatas.addHybridSiminovData(hybridData);
				}
			}
		} catch(SiminovException siminovException) {
			Log.error(HybridSiminovDataReader.class.getName(), "Constructor", "Exception caught while parsing data tag, " + siminovException.getMessage());
			throw siminovException;
		}
	}
	
	private HybridSiminovData parseData(JSONObject data) throws SiminovException {
		
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
			if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_TYPE)) {
				dataType = (String) getValue(key, data);
			} else if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_VALUE)) {
				dataValue = (String) getValue(key, data);
			} else if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_VALUES)) {
				
				try {
					values = data.getJSONArray(HYBRID_SIMINOV_DATA_VALUES);
				} catch(Exception exception) {
					Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while getting values array, " + exception.getMessage());
					throw new SiminovException(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while getting values array, " + exception.getMessage());
				}
			} else if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_DATAS)) {
				
				try {
					innerDatas = data.getJSONArray(HYBRID_SIMINOV_DATA_DATAS);
				} catch(Exception exception) {
					Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while getting datas array, " + exception.getMessage());
					throw new SiminovException(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while getting datas array, " + exception.getMessage());
				}
			}
		}
		
		HybridSiminovData hybridSiminovData = new HybridSiminovData();
		hybridSiminovData.setDataType(dataType);
		hybridSiminovData.setDataValue(dataValue);
		
		parseDataValue(hybridSiminovData, values);
		
		try {
			if(innerDatas != null && innerDatas.length() > 0) {
				for(int i = 0;i < innerDatas.length();i++) {
					JSONObject jsonObject = null;
					try {
						jsonObject = innerDatas.getJSONObject(i);
					} catch(Exception exception) {
						Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
						throw new SiminovException(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from datas, " + exception.getMessage());
					}
					
					HybridSiminovData hybridData = parseData(jsonObject);
					hybridSiminovData.addData(hybridData);
				}
			}
		} catch(SiminovException siminovException) {
			Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while parsing data tag, " + siminovException.getMessage());
			throw siminovException;
		}
		
		
		return hybridSiminovData;
	}

	private void parseDataValue(HybridSiminovData hybridSiminovData, JSONArray values) throws SiminovException {
		
		if(values == null || values.length() <= 0) {
			return;
		}
		
		for(int i = 0;i < values.length();i++) {
			
			JSONObject jsonObject = null;
			
			try {
				jsonObject = values.getJSONObject(i);
			} catch(Exception exception) {
				Log.error(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from values, " + exception.getMessage());
				throw new SiminovException(HybridSiminovDataReader.class.getName(), "parseData", "Exception caught while geeting json object from values, " + exception.getMessage());
			}

			String valueType = null;
			String value = null;
			
			Iterator<String> keys = jsonObject.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				
				if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_VALUE_TYPE)) {
					valueType = (String) getValue(key, jsonObject);
				} else if(key.equalsIgnoreCase(HYBRID_SIMINOV_DATA_VALUE_VALUE)){
					value = (String) getValue(key, jsonObject);
				}
			}
			
			HybridSiminovValue hybridSiminovValue = new HybridSiminovValue();
			hybridSiminovValue.setType(valueType);
			hybridSiminovValue.setValue(value);
			
			hybridSiminovData.addValue(hybridSiminovValue);
		}
	}

	private Object getValue(String name, JSONObject jsonObject) throws SiminovException {
		
		try {
			return jsonObject.get(name);
		} catch(Exception exception) {
			Log.error(HybridSiminovDataReader.class.getName(), "", "Exception caught while getting value, " + exception.getMessage());
			throw new SiminovException(HybridSiminovDataReader.class.getName(), "", "Exception caught while getting value, " + exception.getMessage());
		}
	}
	
	private JSONArray getJSONArray(String name, JSONObject jsonObject) throws SiminovException {
		
		try {
			return jsonObject.getJSONArray(name);
		} catch(Exception exception) {
			Log.error(HybridSiminovDataReader.class.getName(), "getJSONArray", "Exception caught while getting json array, " + exception.getMessage());
			throw new SiminovException(HybridSiminovDataReader.class.getName(), "getJSONArray", "Exception caught while getting json array, " + exception.getMessage());
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
	 * Get Hybrid Siminov Datas instance
	 * @return HybridSiminovDatas instance
	 */
	public HybridSiminovDatas getDatas() {
		return this.hybridSiminovDatas;
	}
}
