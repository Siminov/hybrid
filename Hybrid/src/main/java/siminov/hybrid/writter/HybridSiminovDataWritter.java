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



package siminov.hybrid.writter;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import siminov.hybrid.Constants;
import siminov.hybrid.model.HybridSiminovDatas;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData;
import siminov.hybrid.model.HybridSiminovDatas.HybridSiminovData.HybridSiminovValue;
import siminov.core.exception.SiminovException;
import siminov.core.log.Log;

/**
 * Exposes API to build Siminov Hybrid JSON format data to transfer between HYBRID-TO-NATIVE or NATIVE-TO-HYBRID.
 *
 */
public class HybridSiminovDataWritter {

	
	/**
	 * Build JSON using Hybrid Siminov Datas.
	 * @param hybridSiminovDatas Hybrid Siminov Datas.
	 * @return Siminov JSON
	 * @throws SiminovException If any error occur while generating JSON out of Hybrid Siminov Datas.
	 */
	public static String jsonBuidler(HybridSiminovDatas hybridSiminovDatas) throws SiminovException {
		
		JSONObject jsonHybridData = new JSONObject();
		JSONArray jsonHybridSiminovDatas = new JSONArray();

		Iterator<HybridSiminovData> hybridDatas = hybridSiminovDatas.getHybridSiminovDatas();
		while(hybridDatas.hasNext()) {
			
			HybridSiminovData hybridSiminovData = hybridDatas.next();
			JSONObject jsonSiminovData = jsonBuilder(hybridSiminovData);
			
			jsonHybridSiminovDatas.put(jsonSiminovData);
			
		}

		
		try {
			jsonHybridData.accumulate(Constants.HYBRID_SIMINOV_DATAS, jsonHybridSiminovDatas);
		} catch(Exception exception) {
			Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding json js core datas, " + exception.getMessage());
			throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuidler", "Exception caught while adding json js core datas, " + exception.getMessage());
		}
		
		return jsonHybridData.toString();
	}
	
	private static JSONObject jsonBuilder(HybridSiminovData hybridSiminovData) throws SiminovException {
		
		String dataType = hybridSiminovData.getDataType();
		String dataValue = hybridSiminovData.getDataValue();
		
		JSONObject jsonHybridSiminovData = new JSONObject();

		try {
			jsonHybridSiminovData.accumulate(Constants.HYBRID_SIMINOV_DATA_TYPE, dataType);
		} catch(Exception exception) {
			Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data type to json js core data, DATA-TYPE: " + dataType + ", " + exception.getMessage());
			throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data type to json js core data, DATA-TYPE: " + dataType + ", " + exception.getMessage());
		}

		try {
			jsonHybridSiminovData.accumulate(Constants.HYBRID_SIMINOV_DATA_VALUE, dataValue);
		} catch(Exception exception) {
			Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to json js core data, DATA: " + dataValue + ", " + exception.getMessage());
			throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to json js core data, DATA: " + dataValue + ", " + exception.getMessage());
		}
		
		JSONArray jsonHybridSiminovValues = new JSONArray();
		JSONArray jsonHybridSiminovSubDatas = new JSONArray();
		
		Iterator<HybridSiminovValue> hybridSiminovValues = hybridSiminovData.getValues();
		Iterator<HybridSiminovData> hybridSiminovSubDatas = hybridSiminovData.getDatas();
		
		while(hybridSiminovValues.hasNext()) {
			
			HybridSiminovValue hybridSiminovValue = hybridSiminovValues.next();
			String valueType = hybridSiminovValue.getType();
			String value = hybridSiminovValue.getValue();
			
			JSONObject jsonHybridSiminovValue = new JSONObject();

			try {
				jsonHybridSiminovValue.accumulate(Constants.HYBRID_SIMINOV_DATA_VALUE_TYPE, valueType);	
			} catch(Exception exception) {
				Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value type, VALUE-TYPE: " + valueType + ", " + exception.getMessage());
				throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value type, VALUE-TYPE: " + valueType + ", " + exception.getMessage());
			}
			
			try {
				jsonHybridSiminovValue.accumulate(Constants.HYBRID_SIMINOV_DATA_VALUE_VALUE, value);
			} catch(Exception exception) {
				Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value, VALUE: " + value + ", " + exception.getMessage());
				throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value, VALUE: " + value + ", " + exception.getMessage());
			}
			
			jsonHybridSiminovValues.put(jsonHybridSiminovValue);
		}
		
		while(hybridSiminovSubDatas.hasNext()) {
			HybridSiminovData hybridSiminovSubData = hybridSiminovSubDatas.next();
			JSONObject jsonHybridSiminovSubData = jsonBuilder(hybridSiminovSubData);
			
			jsonHybridSiminovSubDatas.put(jsonHybridSiminovSubData);
		}

		if(jsonHybridSiminovValues.length() >  0) {
			try {
				jsonHybridSiminovData.accumulate(Constants.HYBRID_SIMINOV_DATA_VALUES, jsonHybridSiminovValues);
			} catch(Exception exception) {
				Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value to json js core data, " + exception.getMessage());
				throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding value to json js core data, " + exception.getMessage());
			}
		}
		

		if(jsonHybridSiminovSubDatas.length() > 0) {
			try {
				jsonHybridSiminovData.accumulate(Constants.HYBRID_SIMINOV_DATA_DATAS, jsonHybridSiminovSubDatas);
			} catch(Exception exception) {
				Log.error(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to js core data, " + exception.getMessage());
				throw new SiminovException(HybridSiminovDataWritter.class.getName(), "jsonBuilder", "Exception caught while adding data to js core data, " + exception.getMessage());
			}
		}
		
		return jsonHybridSiminovData;
	}
	
}
