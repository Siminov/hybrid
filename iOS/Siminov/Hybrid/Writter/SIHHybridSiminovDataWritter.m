//
//  SIHHybridSiminovDataWritter.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHHybridSiminovDataWritter.h"

#import "SICLog.h"
#import "SIHConstants.h"
#import "SICSiminovException.h"


@implementation SIHHybridSiminovDataWritter


/**
 * Build JSON using Hybrid Siminov Datas.
 * @param jsSiminovDatas Hybrid Siminov Datas.
 * @return Siminov JSON
 * @throws SiminovException If any error occur while generating JSON out of Hybrid Siminov Datas.
 */
+ (NSString *)jsonBuidler:(SIHHybridSiminovDatas *)jsSiminovDatas {
    
    NSMutableDictionary *jsonHybridSiminovData = [[NSMutableDictionary alloc] init];
    NSMutableArray *jsonHybridSiminovDatas = [[NSMutableArray alloc] init];
    
    NSEnumerator *hybridDatas = [jsSiminovDatas getHybridSiminovDatas];
    SIHHybridSiminovData *hybridSiminovData;
    
    while(hybridSiminovData = [hybridDatas nextObject]) {
        
        NSMutableDictionary *jsonSiminovData = [self jsonBuilder:hybridSiminovData];
        
        [jsonHybridSiminovDatas addObject:jsonSiminovData];
    }
    
    
    @try {
        [jsonHybridSiminovData setValue:jsonHybridSiminovDatas forKey:HYBRID_SIMINOV_DATAS];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuidler" message:[NSString stringWithFormat:@"Exception caught while adding final js core data, %@", [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuidler" message:[NSString stringWithFormat:@"Exception caught while adding final js core data, %@", [exception reason]]];
    }
    
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:jsonHybridSiminovData options:NSJSONWritingPrettyPrinted error:&error];
    
    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

+ (id)jsonBuilder:(SIHHybridSiminovData *)hybridSiminovData {
    
    NSString *dataType = [hybridSiminovData getDataType];
    NSString *dataValue = [hybridSiminovData getDataValue];
    
    NSMutableDictionary *jsonHybridSiminovData = [[NSMutableDictionary alloc] init];
    
    @try {
        [jsonHybridSiminovData setValue:dataType forKey:HYBRID_SIMINOV_DATA_TYPE];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data type to json js core data, DATA-TYPE: %@, %@", dataType, [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data type to json js core data, DATA-TYPE: %@, %@", dataType, [exception reason]]];
    }
    
    @try {
        [jsonHybridSiminovData setValue:dataValue forKey:HYBRID_SIMINOV_DATA_VALUE];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data to json js core data, DATA: %@, %@", dataValue, [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data to json js core data, DATA: %@, %@", dataValue, [exception reason]]];
    }
    
    NSMutableArray *jsonHybridSiminovValues = [[NSMutableArray alloc] init];
    NSMutableArray *jsonHybridSiminovSubDatas = [[NSMutableArray alloc] init];
    
    NSEnumerator *hybridSiminovValues = [hybridSiminovData getValues];
    NSEnumerator *hybridSiminovSubDatas = [hybridSiminovData getDatas];
    
    SIHHybridSiminovValue *hybridSiminovValue;
    while(hybridSiminovValue = [hybridSiminovValues nextObject]) {
        
        NSString *valueType = [hybridSiminovValue getType];
        NSString *value = [hybridSiminovValue getValue];
        
        NSMutableDictionary *jsonHybridSiminovValue = [[NSMutableDictionary alloc] init];
        
        @try {
            [jsonHybridSiminovValue setValue:valueType forKey:HYBRID_SIMINOV_DATA_VALUE_TYPE];
        } @catch(NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value type, VALUE-TYPE: %@, %@", valueType, [exception reason]]];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value type, VALUE-TYPE: %@, %@", valueType, [exception reason]]];
        }
        
        @try {
            [jsonHybridSiminovValue setValue:value forKey:HYBRID_SIMINOV_DATA_VALUE_VALUE];
        } @catch(NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value, VALUE: %@, %@", value, [exception reason]]];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value, VALUE: %@, %@", value, [exception reason]]];
        }
        
        [jsonHybridSiminovValues addObject:jsonHybridSiminovValue];
    }
    
    SIHHybridSiminovData *hybridSiminovSubData;
    while(hybridSiminovSubData = [hybridSiminovSubDatas nextObject]) {
        NSMutableArray *jsonHybridSiminovSubData = [self jsonBuilder:hybridSiminovSubData];
        
        [jsonHybridSiminovSubDatas addObject:jsonHybridSiminovSubData];
    }
    
    if([jsonHybridSiminovValues count] >  0) {
        
        @try {
            [jsonHybridSiminovData setValue:jsonHybridSiminovValues forKey:HYBRID_SIMINOV_DATA_VALUES];
        } @catch(NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value to json js core data, %@", [exception reason]]];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding value to json js core data, %@", [exception reason]]];
        }
    }
    
    
    if([jsonHybridSiminovSubDatas count] > 0) {
        
        @try {
            [jsonHybridSiminovData setValue:jsonHybridSiminovSubDatas forKey:HYBRID_SIMINOV_DATA_DATAS];
        } @catch(NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data to js core data, %@", [exception reason]]];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"jsonBuilder" message:[NSString stringWithFormat:@"Exception caught while adding data to js core data, %@", [exception reason]]];
        }
    }
    
    return jsonHybridSiminovData;
}


@end
