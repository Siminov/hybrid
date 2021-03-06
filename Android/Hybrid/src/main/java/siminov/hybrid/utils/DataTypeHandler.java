/**
 * [SIMINOV FRAMEWORK - HYBRID]
 * Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
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

package siminov.hybrid.utils;

import siminov.core.Constants;

public class DataTypeHandler {

	private static final String NATIVE_INTEGER_DATA_TYPE = Integer.class.getName();
	private static final String NATIVE_PRIMITIVE_INTEGER_DATA_TYPE = int.class.getName();
	private static final String NATIVE_LONG_DATA_TYPE = Long.class.getName();
	private static final String NATIVE_PRIMITIVE_LONG_DATA_TYPE = long.class.getName();
	private static final String NATIVE_FLOAT_DATA_TYPE = Float.class.getName();
	private static final String NATIVE_PRIMITIVE_FLOAT_DATA_TYPE = float.class.getName();
	private static final String NATIVE_DOUBLE_DATA_TYPE = Double.class.getName();
	private static final String NATIVE_PRIMITIVE_DOUBLE_DATA_TYPE = double.class.getName();
	private static final String NATIVE_BOOLEAN_DATA_TYPE = Boolean.class.getName();
	private static final String NATIVE_PRIMITIVE_BOOLEAN_DATA_TYPE = boolean.class.getName();
	private static final String NATIVE_STRING_DATA_TYPE = String.class.getName();

	
	public static String convert(String dataType) {
	
		/*
		 * Data Type Conversation
		 */
		if(dataType.equalsIgnoreCase(Constants.INTEGER_DATA_TYPE)) {
			return NATIVE_INTEGER_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.PRIMITIVE_INTEGER_DATA_TYPE)) {
			return NATIVE_PRIMITIVE_INTEGER_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.LONG_DATA_TYPE)) {
			return NATIVE_LONG_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.PRIMITIVE_LONG_DATA_TYPE)) {
			return NATIVE_PRIMITIVE_LONG_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.FLOAT_DATA_TYPE)) {
			return NATIVE_FLOAT_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.PRIMITIVE_FLOAT_DATA_TYPE)) {
			return NATIVE_PRIMITIVE_FLOAT_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.DOUBLE_DATA_TYPE)) {
			return NATIVE_DOUBLE_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.PRIMITIVE_DOUBLE_DATA_TYPE)) {
			return NATIVE_PRIMITIVE_DOUBLE_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.BOOLEAN_DATA_TYPE)) {
			return NATIVE_BOOLEAN_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.PRIMITIVE_BOOLEAN_DATA_TYPE)) {
			return NATIVE_PRIMITIVE_BOOLEAN_DATA_TYPE;
		} else if(dataType.equalsIgnoreCase(Constants.STRING_DATA_TYPE)) {
			return NATIVE_STRING_DATA_TYPE;
		}

		
		/*
		 * Other Data Type
		 */
		else {
			return dataType;
		}
		
	}
	
}
