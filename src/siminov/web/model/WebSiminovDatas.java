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



package siminov.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Exposes methods to GET and SET Siminov Web Datas structure.
 * 

	<p>
		<pre>
		
Example:
	{@code
	
	<siminov-web-data>
	
	    <data type="data_type">
	        <value type="value_type">value</value>
	        <data type="data_type"/>
	    </data>
	
	    <data type="data_type" />
	    
	</siminov-web-data>

	}
	
		</pre>
	</p>
	
 * 
 */
public class WebSiminovDatas {
	
	private Collection<WebSiminovData> webSiminovDatas = new LinkedList<WebSiminovDatas.WebSiminovData>();

	/**
	 * Get Web Siminov Datas.
	 * @return Web Siminov Datas.
	 */
	public Iterator<WebSiminovData> getWebSiminovDatas() {
		return this.webSiminovDatas.iterator();
	}

	/**
	 * Get Web Siminov Data based on Data Type provided.
 	 * @param dataType Data Type.
	 * @return Web Siminov Datas.
	 */
	public WebSiminovData getWebSiminovDataBasedOnDataType(final String dataType) {
		Iterator<WebSiminovData> webSiminovDatas = this.webSiminovDatas.iterator();
		while(webSiminovDatas.hasNext()) {
			WebSiminovData webSiminovData = webSiminovDatas.next();
			String webDataType = webSiminovData.getDataType();
			
			if(webDataType != null && webDataType.length() > 0) {
				if(webDataType.equalsIgnoreCase(dataType)) {
					return webSiminovData;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Add Web Siminov Data.
	 * @param webSiminovData Web Siminov Data.
	 */
	public void addWebSiminovData(WebSiminovData webSiminovData) {
		this.webSiminovDatas.add(webSiminovData);
	}
	
	/**
	 * Check whether Web Siminov Data contain or not.
	 * @param webSiminovData Web Siminov Data.
	 * @return true/falsel TRUE if siminov data exist, FALSE if siminov does not exist.
	 */
	public boolean containWebSiminovData(WebSiminovData webSiminovData) {
		return this.webSiminovDatas.contains(webSiminovData);
	}
	
	/**
	 * Remove Web Siminov Data.
 	 * @param webSiminovData Web Siminov Data.
	 */
	public void removeWebSiminovData(WebSiminovData webSiminovData) {
		this.webSiminovDatas.remove(webSiminovData);
	}
	
	/**
	 * 
	 * Exposes methods to GET and SET Siminov Web Data structure.
	 
 	    <data type="data_type">
	        <value type="value_type">value</value>
	        <data type="data_type"/>
	    </data>
	 
	 */
	public static class WebSiminovData {
		
		private String dataType = null;
		private String dataValue = null;

		private Collection<WebSiminovValue>  values = new ArrayList<WebSiminovValue>();
		private Collection<WebSiminovData> datas = new ArrayList<WebSiminovDatas.WebSiminovData>();

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
		public Iterator<WebSiminovValue> getValues() {
			return this.values.iterator();
		}
		
		/**
		 * Get Value based on Value Type.
		 * @param type Type of Value.
		 * @return Web Siminov Value.
		 */
		public WebSiminovValue getValueBasedOnType(final String type) {
			Iterator<WebSiminovValue> values = this.values.iterator();
			while(values.hasNext()) {
				WebSiminovValue value = values.next();
				
				if(value.getType().equalsIgnoreCase(type)) {
					return value;
				}
			}
			
			return null;
		}
		
		/**
		 * Add Value.
		 * @param webSiminovValue Web Siminov Value.
		 */
		public void addValue(WebSiminovValue webSiminovValue) {
			this.values.add(webSiminovValue);
		}
		
		/**
		 * Remove Web Siminov Value.
		 * @param webSiminovValue Web Siminov Value.
		 */
		public void removeValue(WebSiminovValue webSiminovValue) {
			this.values.remove(webSiminovValue);
		}

		/**
		 * Check whether Web Siminov Value exist or not.
		 * @param webSiminovValue Web Siminov Value.
		 * @return true/false; TRUE if Web Siminov Value exist, FALSE if Web Siminov Value does not exist.
		 */
		public boolean containValue(WebSiminovValue webSiminovValue) {
			return this.values.contains(webSiminovValue);
		}
		
		/**
		 * Get All Web Siminov Data.
		 * @return All Web Siminov Data.
		 */
		public Iterator<WebSiminovData> getDatas() {
			return this.datas.iterator();
		}
		
		/**
		 * Get Web Siminov Data based on Data Type.
		 * @param dataType Data Type of Data.
		 * @return Web Siminov Data.
		 */
		public WebSiminovData getWebSiminovDataBasedOnDataType(final String dataType) {
			Iterator<WebSiminovData> webSiminovDatas = this.datas.iterator();
			while(webSiminovDatas.hasNext()) {
				WebSiminovData webSiminovData = webSiminovDatas.next();
				String webDataType = webSiminovData.getDataType();
				
				if(webDataType != null && webDataType.length() > 0) {
					if(webDataType.equalsIgnoreCase(dataType)) {
						return webSiminovData;
					}
				}
			}
			
			return null;
		}
		
		/**
		 * Add Web Siminon Data.
		 * @param webSiminovData Web Siminov Data.
		 */
		public void addData(WebSiminovData webSiminovData) {
			this.datas.add(webSiminovData);
		}
		
		/**
		 * Check whether Web Siminov Data exist or not.
		 * @param webSiminovData Web Siminov Data.
		 * @return true/false; TRUE if Web Siminov Data exist, FALSE if Web Siminov Data does not exist. 
		 */
		public boolean containData(WebSiminovData webSiminovData) {
			return this.datas.contains(webSiminovData);
		}
		
		/**
		 * Remove Web Siminov Data.
		 * @param webSiminovData Web Siminov Data.
		 */
		public void removeData(WebSiminovData webSiminovData) {
			this.datas.remove(webSiminovData);
		}
		
		/**
		 * Exposes methods to GET and SET Siminov Web Value structure.
		 */
		public static class WebSiminovValue {

			private String type = null;
			private String value = null;
			
			/**
			 * Get Type of Web Siminov Value.
			 * @return Type of Web Siminov Value.
			 */
			public String getType() {
				return this.type;
			}
			
			/**
			 * Set Type of Web Siminov Value.
			 * @param type Type of Web Siminov Value.
			 */
			public void setType(String type) {
				this.type = type;
			}
			
			/**
			 * Get Value of Web Siminov Value.
			 * @return Value of Web Siminov Value.
			 */
			public String getValue() {
				return this.value;
			}
			
			/**
			 * Set Value of Web Siminov Value.
			 * @param value Value of Web Siminov Value.
			 */
			public void setValue(String value) {
				this.value = value;
			}
			
		}
	}
	
}
