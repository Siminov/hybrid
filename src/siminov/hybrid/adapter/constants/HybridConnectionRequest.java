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

public interface HybridConnectionRequest {

	public String CONNECTION_REQUEST = "ConnectionRequest";
	
	public String URL = "url";
	
	public String PROTOCOL = "protocol";
	
	public String TYPE = "type";

	public String DATA_STREAM = "dataStream";

	public String QUERY_PARAMETERS_TYPE = "Array";
	
	public String QUERY_PARAMETER_TYPE = "ServiceDescriptor.API.QueryParameter";
	
	public String QUERY_PARAMETER_NAME = "name";
	
	public String QUERY_PARAMETER_VALUE = "value";
	
	public String HEADER_PARAMETERS_TYPE = "Array";

	public String HEADER_PARAMETER_TYPE = "ServiceDescriptor.API.HeaderParameter";
	
	public String HEADER_PARAMETER_NAME = "name";
	
	public String HEADER_PARAMETER_VALUE = "value";
}
