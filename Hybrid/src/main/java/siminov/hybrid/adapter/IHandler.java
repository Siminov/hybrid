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



package siminov.hybrid.adapter;


/**
 * Exposes methods to deal with actual request comes from HYBRID-TO-NATIVE or NATIVE-TO-HYBRID. 
 * It has methods to open, create, close, and execute query's.
 */
public interface IHandler {

	/**
	 * Handle request from HYBRID-TO-NATIVE
	 * @param action Action Needs to be performed on Native.
	 * @return Return Data By Native Handler API.
	 */
	public String handleHybridToNative(final String action);
	
	
	/**
	 * Handle request from HYBRID-TO-NATIVE
	 * @param action Action Needs to be performed on Native.
	 * @param data Data to Native Handler.
	 * @return
	 */
	public String handleHybridToNative(final String action, final String data);


	public String handleHybridToNativeAsync(String requestId, String action, String data);

	/**
	 * Handle request from NATIVE-TO-HYBRID.
	 * @param action Action Needs to be performed on Hybrid.
	 * @param data Data to Hybrid Handler.
	 */
	public void handleNativeToHybrid(final String action, final String...data);

	public void handleNativeToHybridAsync(String requestId, final String...data);

	public void handleNativeToHybrid(String functionName, String apiName, String action, String parameters);

}
