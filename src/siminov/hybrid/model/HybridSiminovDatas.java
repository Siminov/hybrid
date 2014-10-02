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



package siminov.hybrid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Exposes methods to GET and SET Siminov Hybrid Datas structure.
 * 

	<p>
		<pre>
		
Example:
	{@code
	
	<siminov-hybrid-data>
	
	    <data type="data_type">
	        <value type="value_type">value</value>
	        <data type="data_type"/>
	    </data>
	
	    <data type="data_type" />
	    
	</siminov-hybrid-data>

	}
	
		</pre>
	</p>
	
 * 
 */
public class HybridSiminovDatas {
	
	private Collection<HybridSiminovData> hybridSiminovDatas = new LinkedList<HybridSiminovDatas.HybridSiminovData>();

	/**
	 * Get Hybrid Siminov Datas.
	 * @return Hybrid Siminov Datas.
	 */
	public Iterator<HybridSiminovData> getHybridSiminovDatas() {
		return this.hybridSiminovDatas.iterator();
	}

	/**
	 * Get Hybrid Siminov Data based on Data Type provided.
 	 * @param dataType Data Type.
	 * @return Hybrid Siminov Datas.
	 */
	public HybridSiminovData getHybridSiminovDataBasedOnDataType(final String dataType) {
		Iterator<HybridSiminovData> hybridSiminovDatas = this.hybridSiminovDatas.iterator();
		while(hybridSiminovDatas.hasNext()) {
			HybridSiminovData hybridSiminovData = hybridSiminovDatas.next();
			String hybridDataType = hybridSiminovData.getDataType();
			
			if(hybridDataType != null && hybridDataType.length() > 0) {
				if(hybridDataType.equalsIgnoreCase(dataType)) {
					return hybridSiminovData;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Add Hybrid Siminov Data.
	 * @param hybridSiminovData Hybrid Siminov Data.
	 */
	public void addHybridSiminovData(HybridSiminovData hybridSiminovData) {
		this.hybridSiminovDatas.add(hybridSiminovData);
	}
	
	/**
	 * Check whether Hybrid Siminov Data contain or not.
	 * @param hybridSiminovData Hybrid Siminov Data.
	 * @return true/falsel TRUE if siminov data exist, FALSE if siminov does not exist.
	 */
	public boolean containHybridSiminovData(HybridSiminovData hybridSiminovData) {
		return this.hybridSiminovDatas.contains(hybridSiminovData);
	}
	
	/**
	 * Remove Hybrid Siminov Data.
 	 * @param hybridSiminovData Hybrid Siminov Data.
	 */
	public void removeHybridSiminovData(HybridSiminovData hybridSiminovData) {
		this.hybridSiminovDatas.remove(hybridSiminovData);
	}
	
	/**
	 * 
	 * Exposes methods to GET and SET Siminov Hybrid Data structure.
	 
 	    <data type="data_type">
	        <value type="value_type">value</value>
	        <data type="data_type"/>
	    </data>
	 
	 */
	public static class HybridSiminovData {
		
		private String dataType = null;
		private String dataValue = null;

		private Collection<HybridSiminovValue>  values = new ArrayList<HybridSiminovValue>();
		private Collection<HybridSiminovData> datas = new ArrayList<HybridSiminovDatas.HybridSiminovData>();

		/**
		 * Get Data Type of Data.
		 * @return Data Type of Data.
		 */
		public String getDataType() {
			return this.dataType;
		}
		
		/**
		 * Set Data Type of Data.
		 * @param dataType Data Type of Data.
		 */
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		
		/**
		 * Get Data Value of Data.
		 * @return Data Value of Data.
		 */
		public String getDataValue() {
			return this.dataValue;
		}
		
		/**
		 * Set Data Value of Data.
		 * @param dataValue Data Value of Data.
		 */
		public void setDataValue(String dataValue) {
			this.dataValue = dataValue;
		}
		
		/**
		 * Get All Values contain within Data.
		 * @return All Values.
		 */
		public Iterator<HybridSiminovValue> getValues() {
			return this.values.iterator();
		}
		
		/**
		 * Get Value based on Value Type.
		 * @param type Type of Value.
		 * @return Hybrid Siminov Value.
		 */
		public HybridSiminovValue getValueBasedOnType(final String type) {
			Iterator<HybridSiminovValue> values = this.values.iterator();
			while(values.hasNext()) {
				HybridSiminovValue value = values.next();
				
				if(value.getType().equalsIgnoreCase(type)) {
					return value;
				}
			}
			
			return null;
		}
		
		/**
		 * Add Value.
		 * @param hybridSiminovValue Hybrid Siminov Value.
		 */
		public void addValue(HybridSiminovValue hybridSiminovValue) {
			this.values.add(hybridSiminovValue);
		}
		
		/**
		 * Remove Hybrid Siminov Value.
		 * @param hybridSiminovValue Hybrid Siminov Value.
		 */
		public void removeValue(HybridSiminovValue hybridSiminovValue) {
			this.values.remove(hybridSiminovValue);
		}

		/**
		 * Check whether Hybrid Siminov Value exist or not.
		 * @param hybridSiminovValue Hybrid Siminov Value.
		 * @return true/false; TRUE if Hybrid Siminov Value exist, FALSE if Hybrid Siminov Value does not exist.
		 */
		public boolean containValue(HybridSiminovValue hybridSiminovValue) {
			return this.values.contains(hybridSiminovValue);
		}
		
		/**
		 * Get All Hybrid Siminov Data.
		 * @return All Hybrid Siminov Data.
		 */
		public Iterator<HybridSiminovData> getDatas() {
			return this.datas.iterator();
		}
		
		/**
		 * Get Hybrid Siminov Data based on Data Type.
		 * @param dataType Data Type of Data.
		 * @return Hybrid Siminov Data.
		 */
		public HybridSiminovData getHybridSiminovDataBasedOnDataType(final String dataType) {
			Iterator<HybridSiminovData> hybridSiminovDatas = this.datas.iterator();
			while(hybridSiminovDatas.hasNext()) {
				HybridSiminovData hybridSiminovData = hybridSiminovDatas.next();
				String hybridDataType = hybridSiminovData.getDataType();
				
				if(hybridDataType != null && hybridDataType.length() > 0) {
					if(hybridDataType.equalsIgnoreCase(dataType)) {
						return hybridSiminovData;
					}
				}
			}
			
			return null;
		}
		
		/**
		 * Add Hybrid Siminon Data.
		 * @param hybridSiminovData Hybrid Siminov Data.
		 */
		public void addData(HybridSiminovData hybridSiminovData) {
			this.datas.add(hybridSiminovData);
		}
		
		/**
		 * Check whether Hybrid Siminov Data exist or not.
		 * @param hybridSiminovData Hybrid Siminov Data.
		 * @return true/false; TRUE if Hybrid Siminov Data exist, FALSE if Hybrid Siminov Data does not exist. 
		 */
		public boolean containData(HybridSiminovData hybridSiminovData) {
			return this.datas.contains(hybridSiminovData);
		}
		
		/**
		 * Remove Hybrid Siminov Data.
		 * @param hybridSiminovData Hybrid Siminov Data.
		 */
		public void removeData(HybridSiminovData hybridSiminovData) {
			this.datas.remove(hybridSiminovData);
		}
		
		/**
		 * Exposes methods to GET and SET Siminov Hybrid Value structure.
		 */
		public static class HybridSiminovValue {

			private String type = null;
			private String value = null;
			
			/**
			 * Get Type of Hybrid Siminov Value.
			 * @return Type of Hybrid Siminov Value.
			 */
			public String getType() {
				return this.type;
			}
			
			/**
			 * Set Type of Hybrid Siminov Value.
			 * @param type Type of Hybrid Siminov Value.
			 */
			public void setType(String type) {
				this.type = type;
			}
			
			/**
			 * Get Value of Hybrid Siminov Value.
			 * @return Value of Hybrid Siminov Value.
			 */
			public String getValue() {
				return this.value;
			}
			
			/**
			 * Set Value of Hybrid Siminov Value.
			 * @param value Value of Hybrid Siminov Value.
			 */
			public void setValue(String value) {
				this.value = value;
			}
			
		}
	}
	
}
