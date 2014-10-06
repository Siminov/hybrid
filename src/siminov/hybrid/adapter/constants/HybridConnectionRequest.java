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

package siminov.hybrid.adapter.constants;

/**
 * Exposes constants which represents Connection Request on Web.
 */
public interface HybridConnectionRequest {

	/**
	 * Connection Request
	 */
	public String CONNECTION_REQUEST = "ConnectionRequest";
	
	/**
	 * Name
	 */
	public String URL = "url";
	
	/**
	 * Protocol
	 */
	public String PROTOCOL = "protocol";
	
	/**
	 * Type
	 */
	public String TYPE = "type";

	/**
	 * Data Stream
	 */
	public String DATA_STREAM = "dataStream";

	/**
	 * Query Parameters Type
	 */
	public String QUERY_PARAMETERS_TYPE = "Array";
	
	/**
	 * Query Parameter Type
	 */
	public String QUERY_PARAMETER_TYPE = "ServiceDescriptor.API.QueryParameter";
	
	
	/**
	 * Query Parameter Name
	 */
	public String QUERY_PARAMETER_NAME = "name";
	
	/**
	 * Query Parameter Value
	 */
	public String QUERY_PARAMETER_VALUE = "value";
	
	/**
	 * Header Parameters Type
	 */
	public String HEADER_PARAMETERS_TYPE = "Array";

	/**
	 * Header Parameter Type
	 */
	public String HEADER_PARAMETER_TYPE = "ServiceDescriptor.API.HeaderParameter";
	
	/**
	 * Header Parameter Name
	 */
	public String HEADER_PARAMETER_NAME = "name";
}
