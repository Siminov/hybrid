/** 
 * [SIMINOV FRAMEWORK]
 * Copyright [2013] [Siminov Software Solution|support@siminov.com]
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

public class HybridSiminovDatas {
	
	private Collection<HybridSiminovData> hybridSiminovDatas = new LinkedList<HybridSiminovDatas.HybridSiminovData>();
	
	public Iterator<HybridSiminovData> getHybridSiminovDatas() {
		return this.hybridSiminovDatas.iterator();
	}

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
	
	public void addHybridSiminovData(HybridSiminovData hybridSiminovData) {
		this.hybridSiminovDatas.add(hybridSiminovData);
	}
	
	public boolean containHybridSiminovData(HybridSiminovData hybridSiminovData) {
		return this.hybridSiminovDatas.contains(hybridSiminovData);
	}
	
	public void removeHybridSiminovData(HybridSiminovData hybridSiminovData) {
		this.hybridSiminovDatas.remove(hybridSiminovData);
	}
	
	public static class HybridSiminovData {
		
		private String dataType = null;
		private String dataValue = null;

		private Collection<HybridSiminovValue>  values = new ArrayList<HybridSiminovValue>();
		private Collection<HybridSiminovData> datas = new ArrayList<HybridSiminovDatas.HybridSiminovData>();
		
		public String getDataType() {
			return this.dataType;
		}
		
		public void setDataType(String dataType) {
			this.dataType = dataType;
		}
		
		public String getDataValue() {
			return this.dataValue;
		}
		
		public void setDataValue(String dataValue) {
			this.dataValue = dataValue;
		}
		
		public Iterator<HybridSiminovValue> getValues() {
			return this.values.iterator();
		}
		
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
		
		public void addValue(HybridSiminovValue hybridSiminovValue) {
			this.values.add(hybridSiminovValue);
		}
		
		public void removeValue(HybridSiminovValue hybridSiminovValue) {
			this.values.remove(hybridSiminovValue);
		}

		public boolean containValue(HybridSiminovValue hybridSiminovValue) {
			return this.values.contains(hybridSiminovValue);
		}
		
		public Iterator<HybridSiminovData> getDatas() {
			return this.datas.iterator();
		}
		
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
		
		public void addData(HybridSiminovData hybridSiminovData) {
			this.datas.add(hybridSiminovData);
		}
		
		public boolean containData(HybridSiminovData hybridSiminovData) {
			return this.datas.contains(hybridSiminovData);
		}
		
		public void removeData(HybridSiminovData hybridSiminovData) {
			this.datas.remove(hybridSiminovData);
		}
		
		public static class HybridSiminovValue {

			private String type = null;
			private String value = null;
			
			public String getType() {
				return this.type;
			}
			
			public void setType(String type) {
				this.type = type;
			}
			
			public String getValue() {
				return this.value;
			}
			
			public void setValue(String value) {
				this.value = value;
			}
			
		}
	}
	
}
