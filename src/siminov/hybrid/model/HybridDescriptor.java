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


package siminov.hybrid.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import siminov.hybrid.Constants;

/**
 * Exposes methods to GET and SET Hybrid Descriptor information as per define in HybridDescriptor.si.xml file by application.
	<p>
		<pre>
		
Example:
	{@code
	<hybrid-descriptor>
	
		<adapters>
			
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
			
		</adapters>
		
			
			
	</hybrid-descriptor>
	}
	
		</pre>
	</p>
 *
 */
public class HybridDescriptor {

	private Map<String, String> properties = new HashMap<String, String>();

	private Map<String, Adapter> adaptersBasedOnName = new HashMap<String, Adapter>();
	private Map<String, Adapter> adaptersBasedOnPath = new HashMap<String, Adapter>();

	private Map<String, LibraryDescriptor> librariesBasedOnPath = new HashMap<String, LibraryDescriptor>();
	private Map<String, LibraryDescriptor> librariesBasedOnName = new HashMap<String, LibraryDescriptor>();
	
	
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
	 * Get All Adapters. Defined in HybridDescriptor.si.xml file.
	 * @return All Adapters.
	 */
	public Iterator<Adapter> getAdapters() {
		return this.adaptersBasedOnName.values().iterator();
	}
	
	/**
	 * Get Adapter based on name.
	 * @param adapterName Name of Adapter.
	 * @return Adapter.
	 */
	public Adapter getAdapterBasedOnName(final String adapterName) {
		return this.adaptersBasedOnName.get(adapterName);
	}

	/**
	 * Get Adapter based on adapter path.
	 * @param adapterPath Path of Adapter File.
	 * @return Adapter.
	 */
	public Adapter getAdapterBasedOnPath(final String adapterPath) {
		return this.adaptersBasedOnPath.get(adapterPath);
	}
	
	/**
	 * Add Adapter.
	 * @param adapter Adapter.
	 */
	public void addAdapter(final Adapter adapter) {
		this.adaptersBasedOnName.put(adapter.getName(), adapter);
	}

	/**
	 * Add Adapter based on adapter path.
	 * @param adapterPath Path of Adapter.
	 * @param adapter Adapter.
	 */
	public void addAdapter(final String adapterPath, final Adapter adapter) {
		this.adaptersBasedOnPath.put(adapterPath, adapter);
		this.adaptersBasedOnName.put(adapter.getName(), adapter);
	}
	
	/**
	 * Check whether Adapter exist or not based on adapter path.
	 * @param adapterPath Path of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterBasedOnPath(final String adapterPath) {
		return this.adaptersBasedOnPath.containsKey(adapterPath);
	}
	
	/**
	 * Check whether Adapter exist or not based on name.
	 * @param adapterName Name of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterBasedOnName(final String adapterName) {
		return this.adaptersBasedOnName.containsKey(adapterName);
	}
	
	/**
	 * Get all Adapter Paths.
	 * @return Paths of all adapters.
	 */
	public Iterator<String> getAdapterPaths() {
		return this.adaptersBasedOnPath.keySet().iterator();
	}
	
	/**
	 * Add Adapter based on adapter path.
	 * @param adapterPath Path of Adapter.
	 */
	public void addAdapterPath(final String adapterPath) {
		this.adaptersBasedOnPath.put(adapterPath, null);
	}
	
	/**
	 * Check whether adapter exist or not based on adapter path.
	 * @param adapterPath Path of Adapter.
	 * @return true/false; TRUE if adapter exist, FALSE if adapter does not exist.
	 */
	public boolean containAdapterPath(final String adapterPath) {
		return this.adaptersBasedOnPath.containsKey(adapterPath);
	}
	
	/**
	 * Remove Adapter based on adapter name.
	 * @param adapterName Name of adapter.
	 */
	public void removeAdapterBasedOnName(final String adapterName) {
		
		Iterator<String> adapterPaths = this.adaptersBasedOnPath.keySet().iterator();
		
		String keyMatched = null;
		boolean found = false;
		while(adapterPaths.hasNext()) {
			String adapterPath = adapterPaths.next();
			
			Adapter adapter = this.adaptersBasedOnPath.get(adapterPath);
			if(adapter.getName().equalsIgnoreCase(adapterName)) {
				keyMatched = adapterPath;
				found = true;
				break;
			}
		}
		
		if(found) {
			removeAdapterBasedOnPath(keyMatched);
		} else {
			this.adaptersBasedOnName.remove(adapterName);
		}
		
	}
	
	/**
	 * Remove Adapter based on adapter path.
	 * @param adapterPath Path of Adapter.
	 */
	public void removeAdapterBasedOnPath(final String adapterPath) {
		Adapter adapter = this.adaptersBasedOnPath.get(adapterPath);
		
		this.adaptersBasedOnName.remove(adapter.getName());
		this.adaptersBasedOnPath.remove(adapterPath);
	}
	
	/**
	 * Remove Adapter based on Adapter.
	 * @param adapter Adapter.
	 */
	public void removeAdapter(final Adapter adapter) {
		removeAdapterBasedOnName(adapter.getName());
	}
	
	/**
	 * Get All Libraries defined in HybridDescriptor.si.xml file.
	 * @return All Library Paths.
	 */
	public Iterator<String> getLibraryPaths() {
		return this.librariesBasedOnPath.keySet().iterator();
	}
	
	/**
	 * Add Library Path.
	 * @param libraryPath Path of library.
	 */
	public void addLibraryPath(final String libraryPath) {
		this.librariesBasedOnPath.put(libraryPath, null);
	}
	
	/**
	 * Get All Library Descriptor defined in HybridDescriptor.si.xml file.
	 * @return All LibraryDescriptors.
	 */
	public Iterator<LibraryDescriptor> getLibraries() {
		return this.librariesBasedOnName.values().iterator();
	}
	
	/**
	 * Get Library Descriptor based on library name.
	 * @param libraryName Name of Library.
	 * @return Library Descriptor.
	 */
	public LibraryDescriptor getLibraryDescriptorBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.get(libraryName);
	}
	
	/**
	 * get Library Descriptor based on library path. 
	 * @param libraryPath Path of Library.
	 * @return Library Descriptor.
	 */
	public LibraryDescriptor getLibraryDescriptorBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.get(libraryPath);
	}
	
	/**
	 * Add Library Descriptor based on library path.
	 * @param libraryPath Path of Library.
	 * @param libraryDescriptor LibraryDescriptor.
	 */
	public void addLibrary(final String libraryPath, final LibraryDescriptor libraryDescriptor) {
		this.librariesBasedOnPath.put(libraryPath, libraryDescriptor);
		this.librariesBasedOnName.put(libraryDescriptor.getName(), libraryDescriptor);
	}
	
	/**
	 * Check whether library exist or not based on name.
	 * @param libraryName Name of Library.
	 * @return true/false; TRUE if library exist, FALSE if library does not exist.
	 */
	public boolean containLibraryBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.containsKey(libraryName);
	}
	
	/**
	 * Check whether library exist or not based on library path.
	 * @param libraryPath Path of Library.
	 * @return true/false; TRUE if library exist, FALSE if library does not exist.
	 */
	public boolean containLibraryBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.containsKey(libraryPath);
	}
	
	/**
	 * Remove Library based on library name.
	 * @param libraryName Name of library.
	 */
	public void removeLibraryBasedOnName(final String libraryName) {
		Iterator<String> libraryPaths = this.librariesBasedOnPath.keySet().iterator();
		
		String keyMatched = null;
		boolean found = false;
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			LibraryDescriptor libraryDescriptor = this.librariesBasedOnPath.get(libraryPath);
			if(libraryDescriptor.getName().equalsIgnoreCase(libraryName)) {
				keyMatched = libraryPath;
				found = true;
				break;
			}
		}
		
		if(found) {
			removeAdapterBasedOnPath(keyMatched);
		}
	}
	
	/**
	 * Remove Library based on path.
	 * @param libraryPath Path of Library.
	 */
	public void removeLibraryBasedOnPath(final String libraryPath) {
		LibraryDescriptor libraryDescriptor = this.librariesBasedOnPath.get(libraryPath);
		
		this.librariesBasedOnName.remove(libraryDescriptor.getName());
		this.librariesBasedOnPath.remove(libraryPath);
	}
	
	/**
	 * Remove Library based on Library Descriptor
	 * @param libraryDescriptor Library Descriptor.
	 */
	public void removeLibrary(final LibraryDescriptor libraryDescriptor) {
		removeLibraryBasedOnName(libraryDescriptor.getName());
	}
	
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
	public static class Adapter {

		private Map<String, String> properties = new HashMap<String, String>();
		
		private Map<String, Handler> handlers = new HashMap<String, Handler>();
		
		/**
		 * Get Name of Adapter.
		 * @return Name of Adapter.
		 */
		public String getName() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME);
		}
		
		/**
		 * Set Name of Adapter.
		 * @param name Name of Adapter.
		 */
		public void setName(final String name) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME, name);
		}
		
		/**
		 * Get Description of Adapter.
		 * @return Description of Adapter.
		 */
		public String getDescription() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION);
		}
		
		/**
		 * Set Description of Adapter.
		 * @param description Description of Adapter.
		 */
		public void setDescription(final String description) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION, description);
		}
		
		/**
		 * Get Type of Adapter.
		 * @return Type of Adapter.
		 */
		public String getType() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE);
		}
		
		/**
		 * Set Type of Adapter.
		 * @param type Type of Adapter.
		 */
		public void setType(final String type) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE, type);
		}
		
		/**
		 * Get Map To Name.
		 * @return Map To Name.
		 */
		public String getMapTo() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO);
		}
		
		/**
		 * Set Map To Name.
		 * @param mapTo Map To Name.
		 */
		public void setMapTo(final String mapTo) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO, mapTo);
		}

		/**
		 * Check whether cache is enabled or disabled.
		 * @return true/false; TRUE if cache enabled, FALSE if cache disabled.
		 */
		public boolean isCache() {
			String cache = this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE);
			
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
			this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE);
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
		public static class Handler {

			private Map<String, String> properties = new HashMap<String, String>();
			
			private Collection<Parameter> parameters = new LinkedList<Parameter>();
			private Return returnData = null;

			/**
			 * Get Name of Handler.
			 * @return Name of Handler. 
			 */
			public String getName() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME);
			}
			
			/**
			 * Set Name of Handler.
			 * @param name Name of handler.
			 */
			public void setName(final String name) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME, name);
			}
			
			/**
			 * Get Map To Name.
			 * @return Map To Name.
			 */
			public String getMapTo() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO);
			}
			
			/**
			 * Set Map To Name.
			 * @param mapTo Map To Name.
			 */
			public void setMapTo(final String mapTo) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO, mapTo);
			}
			
			/**
			 * Get Description about Handler.
			 * @return Description about Handler.
			 */
			public String getDescription() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION);
			}
			
			/**
			 * Set Description about Handler.
			 * @param description Description about Handler.
			 */
			public void setDescription(final String description) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION, description);
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
			public static class Parameter {

				private Map<String, String> properties = new HashMap<String, String>();

				/**
				 * Get Name of Parameter.
				 * @return Name of Parameter.
				 */
				public String getName() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_NAME);
				}
				
				/**
				 * Set Name of Parameter.
				 * @param name Name of Parameter.
				 */
				public void setName(final String name) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_NAME, name);
				}
				
				/**
				 * Get Type of Parameter.
				 * @return Type of Parameter.
				 */
				public String getType() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_TYPE);
				}
				
				/**
				 * Get Type of Parameter.
				 * @param type Type of Parameter.
				 */
				public void setType(final String type) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_TYPE, type);
				}
				
				/**
				 * Get Description about Parameter.
				 * @return Description about Parameter.
				 */
				public String getDescription() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_DESCRIPTION);
				}
				
				/**
				 * Set Description about Parameter.
				 * @param description Description about Parameter.
				 */
				public void setDescription(final String description) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_PARAMETER_DESCRIPTION, description);
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
			public static class Return {
				
				private Map<String, String> properties = new HashMap<String, String>();

				/**
				 * Get Type of Return.
				 * @return Type of Return.
				 */
				public String getType() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_TYPE);
				}
				
				/**
				 * Set Type of Return.
				 * @param type Type of Return.
				 */
				public void setType(final String type) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_TYPE, type);
				}
				
				/**
				 * Get Description about Return.
				 * @return Description about Return.
				 */
				public String getDescription() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_DESCRIPTION);
				}
				
				/**
				 * Set Description about Return.
				 * @param description Description about Return.
				 */
				public void setDescription(final String description) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_RETURN_DESCRIPTION, description);
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
}
