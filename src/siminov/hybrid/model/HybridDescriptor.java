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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import siminov.hybrid.Constants;

public class HybridDescriptor {

	private Map<String, String> properties = new HashMap<String, String>();

	private Map<String, Adapter> adaptersBasedOnName = new HashMap<String, Adapter>();
	private Map<String, Adapter> adaptersBasedOnPath = new HashMap<String, Adapter>();

	private Map<String, HybridLibraryDescriptor> librariesBasedOnPath = new HashMap<String, HybridLibraryDescriptor>();
	private Map<String, HybridLibraryDescriptor> librariesBasedOnName = new HashMap<String, HybridLibraryDescriptor>();
	
	
	public Iterator<String> getProperties() {
		return this.properties.keySet().iterator();
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}

	public boolean containProperty(String name) {
		return this.properties.containsKey(name);
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public void removeProperty(String name) {
		this.properties.remove(name);
	}

	
	public Iterator<Adapter> getAdapters() {
		return this.adaptersBasedOnName.values().iterator();
	}
	
	public Adapter getAdapterBasedOnName(final String adapterName) {
		return this.adaptersBasedOnName.get(adapterName);
	}

	public Adapter getAdapterBasedOnPath(final String adapterPath) {
		return this.adaptersBasedOnPath.get(adapterPath);
	}
	
	public void addAdapter(final Adapter adapter) {
		this.adaptersBasedOnName.put(adapter.getName(), adapter);
	}

	public void addAdapter(final String adapterPath, final Adapter adapter) {
		this.adaptersBasedOnPath.put(adapterPath, adapter);
		this.adaptersBasedOnName.put(adapter.getName(), adapter);
	}
	
	public boolean containAdapterBasedOnPath(final String adapterPath) {
		return this.adaptersBasedOnPath.containsKey(adapterPath);
	}
	
	public boolean containAdapterBasedOnName(final String adapterName) {
		return this.adaptersBasedOnName.containsKey(adapterName);
	}
	
	public Iterator<String> getAdapterPaths() {
		return this.adaptersBasedOnPath.keySet().iterator();
	}
	
	public void addAdapterPath(final String adapterPath) {
		this.adaptersBasedOnPath.put(adapterPath, null);
	}
	
	public boolean containAdapterPath(final String adapterPath) {
		return this.adaptersBasedOnPath.containsKey(adapterPath);
	}
	
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
	
	public void removeAdapterBasedOnPath(final String adapterPath) {
		Adapter adapter = this.adaptersBasedOnPath.get(adapterPath);
		
		this.adaptersBasedOnName.remove(adapter.getName());
		this.adaptersBasedOnPath.remove(adapterPath);
	}
	
	public void removeAdapter(final Adapter adapter) {
		removeAdapterBasedOnName(adapter.getName());
	}
	
	public Iterator<String> getLibraryPaths() {
		return this.librariesBasedOnPath.keySet().iterator();
	}
	
	public void addLibraryPath(final String libraryPath) {
		this.librariesBasedOnPath.put(libraryPath, null);
	}
	
	public Iterator<HybridLibraryDescriptor> getLibraries() {
		return this.librariesBasedOnName.values().iterator();
	}
	
	public HybridLibraryDescriptor getLibraryDescriptorBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.get(libraryName);
	}
	
	public HybridLibraryDescriptor getLibraryDescriptorBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.get(libraryPath);
	}
	
	public void addLibrary(final String libraryPath, final HybridLibraryDescriptor libraryDescriptor) {
		this.librariesBasedOnPath.put(libraryPath, libraryDescriptor);
		this.librariesBasedOnName.put(libraryDescriptor.getName(), libraryDescriptor);
	}
	
	public boolean containLibraryBasedOnName(final String libraryName) {
		return this.librariesBasedOnName.containsKey(libraryName);
	}
	
	public boolean containLibraryBasedOnPath(final String libraryPath) {
		return this.librariesBasedOnPath.containsKey(libraryPath);
	}
	
	public void removeLibraryBasedOnName(final String libraryName) {
		Iterator<String> libraryPaths = this.librariesBasedOnPath.keySet().iterator();
		
		String keyMatched = null;
		boolean found = false;
		while(libraryPaths.hasNext()) {
			String libraryPath = libraryPaths.next();
			
			HybridLibraryDescriptor libraryDescriptor = this.librariesBasedOnPath.get(libraryPath);
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
	
	public void removeLibraryBasedOnPath(final String libraryPath) {
		HybridLibraryDescriptor libraryDescriptor = this.librariesBasedOnPath.get(libraryPath);
		
		this.librariesBasedOnName.remove(libraryDescriptor.getName());
		this.librariesBasedOnPath.remove(libraryPath);
	}
	
	public void removeLibrary(final HybridLibraryDescriptor libraryDescriptor) {
		removeLibraryBasedOnName(libraryDescriptor.getName());
	}
	
	public static class Adapter {

		private Map<String, String> properties = new HashMap<String, String>();
		
		private Map<String, Handler> handlers = new HashMap<String, Handler>();
		
		public String getName() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME);
		}
		
		public void setName(final String name) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_NAME, name);
		}
		
		public String getDescription() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION);
		}
		
		public void setDescription(final String description) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_DESCRIPTION, description);
		}
		
		public String getType() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE);
		}
		
		public void setType(final String type) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_TYPE, type);
		}
		
		public String getMapTo() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO);
		}
		
		public void setMapTo(final String mapTo) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_MAP_TO, mapTo);
		}

		public boolean hasErrorHandler() {
			String errorHandler = this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER);
			if(errorHandler != null && errorHandler.length() > 0) {
				return true;
			}
			
			return false;
		}
		
		public String getErrorHandler() {
			return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER);
		}
		
		public void setErrorHandler(final String errorHandler) {
			this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_ERROR_HANDLER, errorHandler);
		}
		
		public boolean isCache() {
			String cache = this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE);
			
			if(cache != null && cache.length() > 0 && cache.equalsIgnoreCase("true")) {
				return true;
			}
			
			return false;
		}
		
		public void setCache(final boolean cache) {
			this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_CACHE);
		}

		
		public Iterator<String> getProperties() {
			return this.properties.keySet().iterator();
		}
		
		public String getProperty(String name) {
			return this.properties.get(name);
		}

		public boolean containProperty(String name) {
			return this.properties.containsKey(name);
		}
		
		public void addProperty(String name, String value) {
			this.properties.put(name, value);
		}
		
		public void removeProperty(String name) {
			this.properties.remove(name);
		}

		
		public Iterator<Handler> getHandlers() {
			return this.handlers.values().iterator();
		}
		
		public Handler getHandler(final String handlerName) {
			return this.handlers.get(handlerName);
		}
		
		public void addHandler(final Handler handler) {
			this.handlers.put(handler.getName(), handler);
		}
		
		public boolean containHandler(final String handlerName) {
			return this.handlers.containsKey(handlerName);
		}

		public static class Handler {

			private Map<String, String> properties = new HashMap<String, String>();
			
			private Collection<Parameter> parameters = new LinkedList<Parameter>();
			private Return returnData = null;
			
			public String getName() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME);
			}
			
			public void setName(final String name) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_NAME, name);
			}
			
			public String getType() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE);
			}
			
			public void setType(final String type) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE, type);
			}
			
			public String getCallbackId() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_CALLBACK_ID);
			}
			
			public void setCallbackId(final String callbackId) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_CALLBACK_ID, callbackId);
			}
			
			public String getMapTo() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO);
			}
			
			public void setMapTo(final String mapTo) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_MAP_TO, mapTo);
			}
			
			public String getDescription() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_DESCRIPTION);
			}
			
			public void setDescription(final String description) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_TYPE, description);
			}
			
			public boolean hasErrorHandler() {
				String errorHandler = this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER);
				
				if(errorHandler != null && errorHandler.length() > 0 && errorHandler.equalsIgnoreCase("true")) {
					return true;
				}
				
				return false;
			}
			
			public String getErrorHandler() {
				return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER);
			}
			
			public void setErrorHandler(final String errorHandler) {
				this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_HANDLER_ERROR_HANDLER, errorHandler);
			}

			public Iterator<String> getProperties() {
				return this.properties.keySet().iterator();
			}
			
			public String getProperty(String name) {
				return this.properties.get(name);
			}

			public boolean containProperty(String name) {
				return this.properties.containsKey(name);
			}
			
			public void addProperty(String name, String value) {
				this.properties.put(name, value);
			}
			
			public void removeProperty(String name) {
				this.properties.remove(name);
			}

			public Iterator<Parameter> getParameters() {
				return this.parameters.iterator();
			}
			
			public void addParameter(final Parameter parameter) {
				this.parameters.add(parameter);
			}
			
			public Return getReturn() {
				return this.returnData;
			}
			
			public void setReturn(final Return returnData) {
				this.returnData = returnData;
			}
			
			public static class Parameter {

				private Map<String, String> properties = new HashMap<String, String>();
				
				public String getType() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_TYPE);
				}
				
				public void setType(final String type) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_TYPE, type);
				}
				
				public String getDescription() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_DESCRIPTION);
				}
				
				public void setDescription(final String description) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_PARAMETER_DESCRIPTION, description);
				}
				
				public Iterator<String> getProperties() {
					return this.properties.keySet().iterator();
				}
				
				public String getProperty(String name) {
					return this.properties.get(name);
				}

				public boolean containProperty(String name) {
					return this.properties.containsKey(name);
				}
				
				public void addProperty(String name, String value) {
					this.properties.put(name, value);
				}
				
				public void removeProperty(String name) {
					this.properties.remove(name);
				}

			}
			
			public static class Return {
				
				private Map<String, String> properties = new HashMap<String, String>();
				
				public String getType() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_TYPE);
				}
				
				public void setType(final String type) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_TYPE, type);
				}
				
				public String getDescription() {
					return this.properties.get(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_DESCRIPTION);
				}
				
				public void setDescription(final String description) {
					this.properties.put(Constants.HYBRID_DESCRIPTOR_ADAPTER_RETURN_DESCRIPTION, description);
				}
				
				public Iterator<String> getProperties() {
					return this.properties.keySet().iterator();
				}
				
				public String getProperty(String name) {
					return this.properties.get(name);
				}

				public boolean containProperty(String name) {
					return this.properties.containsKey(name);
				}
				
				public void addProperty(String name, String value) {
					this.properties.put(name, value);
				}
				
				public void removeProperty(String name) {
					this.properties.remove(name);
				}

			}
		}
	}
}
