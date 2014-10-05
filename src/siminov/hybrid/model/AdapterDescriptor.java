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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import siminov.hybrid.Constants;
import siminov.orm.model.IDescriptor;


/**
 * Exposes methods to GET and SET Hybrid Descriptor Adapter information as per define in HybridDescriptor.si.xml file or standalone xml file in application.
	<p>
		<pre>
		
Example:
	{@code
	<adapter>
		<property name="name">name_of_adapter</property>
		<property name="description">description_about_adapter</property>
		<property name="type">type_of_adapter</property>
		<property name="map_to">map_to_class_name</property>
		<property name="cache">should_be_cached_or_not</property>
		
		<handlers>
		
			<handler>
				<property name="name">name_of_handler</property>
				<property name="description">description_about_handler</property>
				<property name="map_to">map_to_function_name</property>
				
				<parameters>
					
					<parameter>
						<property name="name"></property>
						<property name="description"></property>
						<property name="type"></property>
					</parameter>
				
				</parameter>
				
				<return>
						<property name="type">return_data_type</property>
						<property name="description">description_about_return_data</property>
				</return>
				
			</handler>
		
		</handlers>
		
	</adapter>
			
	}
	
		</pre>
	</p>
 */
public class AdapterDescriptor implements IDescriptor {

	private Map<String, String> properties = new HashMap<String, String>();
	
	private Map<String, Handler> handlers = new HashMap<String, Handler>();
	
	/**
	 * Get Name of Adapter.
	 * @return Name of Adapter.
	 */
	public String getName() {
		return this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_NAME);
	}
	
	/**
	 * Set Name of Adapter.
	 * @param name Name of Adapter.
	 */
	public void setName(final String name) {
		this.properties.put(Constants.ADAPTER_DESCRIPTOR_PROPERTY_NAME, name);
	}
	
	/**
	 * Get Description of Adapter.
	 * @return Description of Adapter.
	 */
	public String getDescription() {
		return this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION);
	}
	
	/**
	 * Set Description of Adapter.
	 * @param description Description of Adapter.
	 */
	public void setDescription(final String description) {
		this.properties.put(Constants.ADAPTER_DESCRIPTOR_PROPERTY_DESCRIPTION, description);
	}
	
	/**
	 * Get Type of Adapter.
	 * @return Type of Adapter.
	 */
	public String getType() {
		return this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_TYPE);
	}
	
	/**
	 * Set Type of Adapter.
	 * @param type Type of Adapter.
	 */
	public void setType(final String type) {
		this.properties.put(Constants.ADAPTER_DESCRIPTOR_PROPERTY_TYPE, type);
	}
	
	/**
	 * Get Map To Name.
	 * @return Map To Name.
	 */
	public String getMapTo() {
		return this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO);
	}
	
	/**
	 * Set Map To Name.
	 * @param mapTo Map To Name.
	 */
	public void setMapTo(final String mapTo) {
		this.properties.put(Constants.ADAPTER_DESCRIPTOR_PROPERTY_MAP_TO, mapTo);
	}

	/**
	 * Check whether cache is enabled or disabled.
	 * @return true/false; TRUE if cache enabled, FALSE if cache disabled.
	 */
	public boolean isCache() {
		String cache = this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_CACHE);
		
		if(cache != null && cache.length() > 0 && cache.equalsIgnoreCase("true")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Set Cache value.
	 * @param cache Cache Enabled or Disabled.
	 */
	public void setCache(final boolean cache) {
		this.properties.get(Constants.ADAPTER_DESCRIPTOR_PROPERTY_CACHE);
	}

	/**
	 * Get all Properties defined in descriptor.
	 * @return All Property Values.
	 */
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	/**
	 * Get Property based on name provided.
	 * @param name Name of Property.
	 * @return Property value.
	 */
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	/**
	 * Check whether Property exist or not.
	 * @param name Name of Property.
	 * @return true/false, TRUE if property exist, FALSE if property does not exist.
	 */
	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	/**
	 * Add Property in property pool.
	 * @param name Name of Property.
	 * @param value value of Property.
	 */
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	/**
	 * Remove Property from property pool.
	 * @param name Name of Property.
	 */
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

	
	/**
	 * Get All Handler defined in descriptors.
	 * @return All Handlers.
	 */
	public Iterator<Handler> getHandlers() {
		return this.handlers.values().iterator();
	}
	
	/**
	 * Get Handler based on handler name.
	 * @param handlerName Name of Handler.
	 * @return Handler.
	 */
	public Handler getHandler(final String handlerName) {
		return this.handlers.get(handlerName);
	}
	
	/**
	 * Add Handler.
	 * @param handler Handler.
	 */
	public void addHandler(final Handler handler) {
		this.handlers.put(handler.getName(), handler);
	}
	
	/**
	 * Check whether handler exist or not based on handler name.
	 * @param handlerName Name of handler.
	 * @return
	 */
	public boolean containHandler(final String handlerName) {
		return this.handlers.containsKey(handlerName);
	}

	/**
	 * Exposes methods to GET and SET Hybrid Descriptor Adapter Handler information as per define in HybridDescriptor.si.xml file or in standalone adapter xml file in application.
	 *
	 */
	public static class Handler implements IDescriptor {

		private Map<String, String> properties = new HashMap<String, String>();
		
		private Collection<Parameter> parameters = new LinkedList<Parameter>();
		private Return returnData = null;

		/**
		 * Get Name of Handler.
		 * @return Name of Handler. 
		 */
		public String getName() {
			return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME);
		}
		
		/**
		 * Set Name of Handler.
		 * @param name Name of handler.
		 */
		public void setName(final String name) {
			this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_NAME, name);
		}
		
		/**
		 * Get Map To Name.
		 * @return Map To Name.
		 */
		public String getMapTo() {
			return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO);
		}
		
		/**
		 * Set Map To Name.
		 * @param mapTo Map To Name.
		 */
		public void setMapTo(final String mapTo) {
			this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_MAP_TO, mapTo);
		}
		
		/**
		 * Get Description about Handler.
		 * @return Description about Handler.
		 */
		public String getDescription() {
			return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION);
		}
		
		/**
		 * Set Description about Handler.
		 * @param description Description about Handler.
		 */
		public void setDescription(final String description) {
			this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PROPERTY_DESCRIPTION, description);
		}
		
		/**
		 * Get all Properties defined in descriptor.
		 * @return All Property Values.
		 */
		public Iterator<String> getProperties() {
			return this.properties.keySet().iterator();
		}
		
		/**
		 * Get Property based on name provided.
		 * @param name Name of Property.
		 * @return Property value.
		 */
		public String getProperty(String name) {
			return this.properties.get(name);
		}

		/**
		 * Check whether Property exist or not.
		 * @param name Name of Property.
		 * @return true/false, TRUE if property exist, FALSE if property does not exist.
		 */
		public boolean containProperty(String name) {
			return this.properties.containsKey(name);
		}
		
		/**
		 * Add Property in property pool.
		 * @param name Name of Property.
		 * @param value value of Property.
		 */
		public void addProperty(String name, String value) {
			this.properties.put(name, value);
		}
		
		/**
		 * Remove Property from property pool.
		 * @param name Name of Property.
		 */
		public void removeProperty(String name) {
			this.properties.remove(name);
		}

		/**
		 * Get All Parameters defined.
		 * @return All Parameters.
		 */
		public Iterator<Parameter> getParameters() {
			return this.parameters.iterator();
		}
		
		/**
		 * Add Parameter.
		 * @param parameter Parameter.
		 */
		public void addParameter(final Parameter parameter) {
			this.parameters.add(parameter);
		}
		
		/**
		 * Get Return Object of Handler.
		 * @return Return Object.
		 */
		public Return getReturn() {
			return this.returnData;
		}
		
		/**
		 * Set Return Object of Handler.
		 * @param returnData Return Object.
		 */
		public void setReturn(final Return returnData) {
			this.returnData = returnData;
		}
		
		/**
		 * Exposes methods to GET and SET Parameter information as per define in HybridDescriptor.si.xml file or in standalone adapter xml file in application.
		 *
		 */
		public static class Parameter implements IDescriptor {

			private Map<String, String> properties = new HashMap<String, String>();

			/**
			 * Get Name of Parameter.
			 * @return Name of Parameter.
			 */
			public String getName() {
				return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME);
			}
			
			/**
			 * Set Name of Parameter.
			 * @param name Name of Parameter.
			 */
			public void setName(final String name) {
				this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_NAME, name);
			}
			
			/**
			 * Get Type of Parameter.
			 * @return Type of Parameter.
			 */
			public String getType() {
				return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE);
			}
			
			/**
			 * Get Type of Parameter.
			 * @param type Type of Parameter.
			 */
			public void setType(final String type) {
				this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_TYPE, type);
			}
			
			/**
			 * Get Description about Parameter.
			 * @return Description about Parameter.
			 */
			public String getDescription() {
				return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION);
			}
			
			/**
			 * Set Description about Parameter.
			 * @param description Description about Parameter.
			 */
			public void setDescription(final String description) {
				this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_PARAMETER_PROPERTY_DESCRIPTION, description);
			}
			
			/**
			 * Get all Properties defined in descriptor.
			 * @return All Property Values.
			 */
			public Iterator<String> getProperties() {
				return this.properties.keySet().iterator();
			}
			
			/**
			 * Get Property based on name provided.
			 * @param name Name of Property.
			 * @return Property value.
			 */
			public String getProperty(String name) {
				return this.properties.get(name);
			}

			/**
			 * Check whether Property exist or not.
			 * @param name Name of Property.
			 * @return true/false, TRUE if property exist, FALSE if property does not exist.
			 */
			public boolean containProperty(String name) {
				return this.properties.containsKey(name);
			}
			
			/**
			 * Add Property in property pool.
			 * @param name Name of Property.
			 * @param value value of Property.
			 */
			public void addProperty(String name, String value) {
				this.properties.put(name, value);
			}
			
			/**
			 * Remove Property from property pool.
			 * @param name Name of Property.
			 */
			public void removeProperty(String name) {
				this.properties.remove(name);
			}

		}

		
		
		/**
		 * Exposes methods to GET and SET Return information as per define in HybridDescriptor.si.xml file or in standalone adapter xml file in application.
		 *
		 */
		public static class Return implements IDescriptor {
			
			private Map<String, String> properties = new HashMap<String, String>();

			/**
			 * Get Type of Return.
			 * @return Type of Return.
			 */
			public String getType() {
				return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE);
			}
			
			/**
			 * Set Type of Return.
			 * @param type Type of Return.
			 */
			public void setType(final String type) {
				this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_TYPE, type);
			}
			
			/**
			 * Get Description about Return.
			 * @return Description about Return.
			 */
			public String getDescription() {
				return this.properties.get(Constants.ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION);
			}
			
			/**
			 * Set Description about Return.
			 * @param description Description about Return.
			 */
			public void setDescription(final String description) {
				this.properties.put(Constants.ADAPTER_DESCRIPTOR_HANDLER_RETURN_PROPERTY_DESCRIPTION, description);
			}
			
			/**
			 * Get all Properties defined in descriptor.
			 * @return All Property Values.
			 */
			public Iterator<String> getProperties() {
				return this.properties.keySet().iterator();
			}
			
			/**
			 * Get Property based on name provided.
			 * @param name Name of Property.
			 * @return Property value.
			 */
			public String getProperty(String name) {
				return this.properties.get(name);
			}

			/**
			 * Check whether Property exist or not.
			 * @param name Name of Property.
			 * @return true/false, TRUE if property exist, FALSE if property does not exist.
			 */
			public boolean containProperty(String name) {
				return this.properties.containsKey(name);
			}
			
			/**
			 * Add Property in property pool.
			 * @param name Name of Property.
			 * @param value value of Property.
			 */
			public void addProperty(String name, String value) {
				this.properties.put(name, value);
			}
			
			/**
			 * Remove Property from property pool.
			 * @param name Name of Property.
			 */
			public void removeProperty(String name) {
				this.properties.remove(name);
			}

		}
	}
}
