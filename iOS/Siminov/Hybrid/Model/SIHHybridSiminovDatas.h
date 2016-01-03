///
/// [SIMINOV FRAMEWORK - HYBRID]
/// Copyright [2014-2016] [Siminov Software Solution LLP|support@siminov.com]
///
/// Licensed under the Apache License, Version 2.0 (the "License");
/// you may not use this file except in compliance with the License.
/// You may obtain a copy of the License at
///
///     http://www.apache.org/licenses/LICENSE-2.0
///
/// Unless required by applicable law or agreed to in writing, software
/// distributed under the License is distributed on an "AS IS" BASIS,
/// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
/// See the License for the specific language governing permissions and
/// limitations under the License.
///

#import <Foundation/Foundation.h>

@class SIHHybridSiminovData, SIHHybridSiminovValue;

/**
 * Exposes methods to GET and SET Siminov Hybrid Datas structure.
 *
 
	<p>
 <pre>
 
 Example:
	{@code
	
	<datas>
	
     <data type="data_type">
         <value type="value_type">value</value>
         <data type="data_type"/>
     </data>
	
     <data type="data_type" />
 
	</datas>
 
	}
	
 </pre>
	</p>
	
 *
 */
@interface SIHHybridSiminovDatas : NSObject {
    
    NSMutableArray *hybridSiminovDatas;
}

/**
 * Get Hybrid Siminov Datas.
 * @return Hybrid Siminov Datas.
 */
- (NSEnumerator *)getHybridSiminovDatas;

/**
 * Get Hybrid Siminov Data based on Data Type provided.
 * @param dataType Data Type.
 * @return Hybrid Siminov Datas.
 */
- (SIHHybridSiminovData *)getHybridSiminovDataBasedOnDataType:(NSString *)dataType;

/**
 * Add Hybrid Siminov Data.
 * @param hybridSiminovData Hybrid Siminov Data.
 */
- (void)addHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData;

/**
 * Check whether Hybrid Siminov Data contain or not.
 * @param hybridSiminovData Hybrid Siminov Data.
 * @return true/falsel TRUE if siminov data exist, FALSE if siminov does not exist.
 */
- (bool)containHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData;

/**
 * Remove Hybrid Siminov Data.
 * @param hybridSiminovData Hybrid Siminov Data.
 */
- (void)removeHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData;

@end


/**
 *
 * Exposes methods to GET and SET Siminov Hybrid Data structure.
 
 <data type="data_type">
 <value type="value_type">value</value>
 <data type="data_type"/>
 </data>
 
 */
@interface SIHHybridSiminovData : NSObject {
    
    NSString *dataType;
    NSString *dataValue;
    
    NSMutableArray *values;
    NSMutableArray *datas;
}

/**
 * Get Data Type of Data.
 * @return Data Type of Data.
 */
- (NSString *)getDataType;

/**
 * Set Data Type of Data.
 * @param dataType Data Type of Data.
 */
- (void)setDataType:(NSString *)datatype;

/**
 * Get Data Value of Data.
 * @return Data Value of Data.
 */
- (NSString *)getDataValue;

/**
 * Set Data Value of Data.
 * @param dataValue Data Value of Data.
 */
- (void)setDataValue:(NSString *)value;

/**
 * Get All Values contain within Data.
 * @return All Values.
 */
- (NSEnumerator *)getValues;

/**
 * Get Value based on Value Type.
 * @param type Type of Value.
 * @return Hybrid Siminov Value.
 */
- (SIHHybridSiminovValue *)getValueBasedOnType:(NSString *)type;

/**
 * Add Value.
 * @param hybridSiminovValue Hybrid Siminov Value.
 */
- (void)addValue:(SIHHybridSiminovValue *)hybridSiminovValue;

/**
 * Remove Hybrid Siminov Value.
 * @param hybridSiminovValue Hybrid Siminov Value.
 */
- (void)removeValue:(SIHHybridSiminovValue *)hybridSiminovValue;

/**
 * Check whether Hybrid Siminov Value exist or not.
 * @param hybridSiminovValue Hybrid Siminov Value.
 * @return true/false; TRUE if Hybrid Siminov Value exist, FALSE if Hybrid Siminov Value does not exist.
 */
- (bool)containValue:(SIHHybridSiminovValue *)hybridSiminovValue;

/**
 * Get All Hybrid Siminov Data.
 * @return All Hybrid Siminov Data.
 */
- (NSEnumerator *)getDatas;

/**
 * Get Hybrid Siminov Data based on Data Type.
 * @param dataType Data Type of Data.
 * @return Hybrid Siminov Data.
 */
- (SIHHybridSiminovData *)getHybridSiminovDataBasedOnDataType:(NSString *)datatype;

/**
 * Add Hybrid Siminon Data.
 * @param hybridSiminovData Hybrid Siminov Data.
 */
- (void)addData:(SIHHybridSiminovData *)hybridSiminovData;

/**
 * Check whether Hybrid Siminov Data exist or not.
 * @param hybridSiminovData Hybrid Siminov Data.
 * @return true/false; TRUE if Hybrid Siminov Data exist, FALSE if Hybrid Siminov Data does not exist.
 */
- (bool)containData:(SIHHybridSiminovData *)hybridSiminovData;

/**
 * Remove Hybrid Siminov Data.
 * @param hybridSiminovData Hybrid Siminov Data.
 */
- (void)removeData:(SIHHybridSiminovData *)hybridSiminovData;

@end


/**
 * Exposes methods to GET and SET Siminov Hybrid Value structure.
 */
@interface SIHHybridSiminovValue : NSObject {
    
    NSString *type;
    NSString *value;
}

/**
 * Get Type of Hybrid Siminov Value.
 * @return Type of Hybrid Siminov Value.
 */
- (NSString *)getType;

/**
 * Set Type of Hybrid Siminov Value.
 * @param type Type of Hybrid Siminov Value.
 */
- (void)setType:(NSString *)typ;

/**
 * Get Value of Hybrid Siminov Value.
 * @return Value of Hybrid Siminov Value.
 */
- (NSString *)getValue;

/**
 * Set Value of Hybrid Siminov Value.
 * @param value Value of Hybrid Siminov Value.
 */
- (void)setValue:(NSString *)val;

@end
