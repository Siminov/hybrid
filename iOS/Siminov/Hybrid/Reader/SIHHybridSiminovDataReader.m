//
//  SIHHybridSiminovDataReader.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHHybridSiminovDataReader.h"

#import "SICLog.h"
#import "SICSiminovException.h"
#import "SIHConstants.h"
#import "SICSiminovCriticalException.h"
#import "SIHConstants.h"


@implementation SIHHybridSiminovDataReader


- (id)init {
    self = [super init];
    
    if(self) {
        hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
        return self;
    }
    
    return self;
}


- (id)initWithData:(NSString *const)data {
    
    self = [super init];
    hybridSiminovDatas = [[SIHHybridSiminovDatas alloc] init];
    
    if(data == nil || [data length] <= 0) {
        return nil;
    }
    
    NSDictionary *jsonData = [self toJSON:data];
    NSArray *datas = [self getJSONArray:HYBRID_SIMINOV_DATAS object:jsonData];
    
    @try {
        if(datas != nil && [datas count] > 0) {
            for(int i = 0;i < [datas count];i++) {
                NSDictionary *jsonObject = nil;
                @try {
                    jsonObject = [datas objectAtIndex:i];
                } @catch(NSException *exception) {
                    [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from datas, %@", [exception reason]]];
                    @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from datas, %@", [exception reason]]];
                }
                
                SIHHybridSiminovData *hybridData = [self parseData:jsonObject];
                [hybridSiminovDatas addHybridSiminovData:hybridData];
            }
        }
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"Constructor" message:[NSString stringWithFormat:@"Exception caught while parsing data tag, %@", [siminovException getMessage]]];
        @throw siminovException;
    }
    
    return self;
}

- (SIHHybridSiminovData *)parseData:(NSDictionary *)data {
    
    if(data == nil) {
        return nil;
    }
    
    NSString *dataType = nil;
    NSString *dataValue = nil;
    NSArray *values = nil;
    NSArray *innerDatas = nil;
    
    NSEnumerator *keys = [[data allKeys] objectEnumerator];
    NSString *key;
    
    while(key = [keys nextObject]) {
        
        if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_TYPE] == NSOrderedSame) {
            dataType = (NSString *) [self getValue:key object:data];
        } else if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_VALUE] == NSOrderedSame) {
            dataValue = (NSString *) [self getValue:key object:data];
        } else if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_VALUES] == NSOrderedSame) {
            
            @try {
                values = [data objectForKey:HYBRID_SIMINOV_DATA_VALUES];
            } @catch(NSException *exception) {
                [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while getting values array, %@", [exception reason]]];
                @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while getting values array, %@", [exception reason]]];
            }
        } else if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_DATAS] == NSOrderedSame) {
            
            @try {
                innerDatas = [data objectForKey:HYBRID_SIMINOV_DATA_DATAS];
            } @catch(NSException *exception) {
                [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while getting datas array, %@", [exception reason]]];
                @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while getting datas array, %@", [exception reason]]];
            }
        }
    }
    
    SIHHybridSiminovData *hybridSiminovData = [[SIHHybridSiminovData alloc] init];
    [hybridSiminovData setDataType:dataType];
    [hybridSiminovData setDataValue:dataValue];
    
    [self parseDataValue:hybridSiminovData values:values];
    
    @try {
        if(innerDatas != nil && [innerDatas count] > 0) {
            for(int i = 0;i < [innerDatas count];i++) {
                NSDictionary *jsonObject = nil;
                @try {
                    jsonObject = [innerDatas objectAtIndex:i];
                } @catch(NSException *exception) {
                    [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from datas, %@", [exception reason]]];
                    @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from datas, %@", [exception reason]]];
                }
                
                SIHHybridSiminovData *hybridData = [self parseData:jsonObject];
                [hybridSiminovData addData:hybridData];
            }
        }
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while parsing data tag, %@", [siminovException getMessage]]];
        @throw siminovException;
    }
    
    
    return hybridSiminovData;
}


- (void)parseDataValue:(SIHHybridSiminovData *)hybridSiminovData values:(NSArray *)values {
    
    if(values == nil || [values count] <= 0) {
        return;
    }
    
    for(int i = 0;i < [values count];i++) {
        
        NSDictionary *jsonObject = nil;
        @try {
            jsonObject = [values objectAtIndex:i];
        } @catch(NSException *exception) {
            [SICLog error:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from values, %@", [exception reason]]];
            @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"parseData" message:[NSString stringWithFormat:@"Exception caught while geeting json object from values, %@", [exception reason]]];
        }
        
        NSString *valueType = nil;
        NSString *value = nil;
        
        NSEnumerator *keys = [[jsonObject allKeys] objectEnumerator];
        NSString *key;
        
        while(key = [keys nextObject]) {
            
            if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_VALUE_TYPE] == NSOrderedSame) {
                valueType = (NSString *) [self getValue:key object:jsonObject];
            } else if([key caseInsensitiveCompare:HYBRID_SIMINOV_DATA_VALUE_VALUE] == NSOrderedSame) {
                value = (NSString *) [self getValue:key object:jsonObject];
            }
        }
        
        SIHHybridSiminovValue *hybridSiminovValue = [[SIHHybridSiminovValue alloc] init];
        [hybridSiminovValue setType:valueType];
        [hybridSiminovValue setValue:value];
        
        [hybridSiminovData addValue:hybridSiminovValue];
        
    }
    
}


- (NSDictionary *)toJSON:(NSString *)data {
    
    if(data == nil || [data length] <= 0) {
        return [[NSDictionary alloc] init];
    }
    
    @try {
        return [NSJSONSerialization JSONObjectWithData:[data dataUsingEncoding:NSUTF8StringEncoding] options:kNilOptions error:nil];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"toJSON" message:[NSString stringWithFormat:@"Exception caught while converting string to json object, %@", [exception reason]]];
        @throw [[SICSiminovCriticalException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"toJSON" message:[NSString stringWithFormat:@"Exception caught while converting string to json object, %@", [exception reason]]];
    }
}



- (id)getValue:(NSString *)name object:(NSDictionary *)jsonObject {
    
    @try {
        return [jsonObject objectForKey:name];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting value, %@", [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting value, %@", [exception reason]]];
    }
}

- (NSDictionary *)getJSONObject:(NSString *)name object:(NSDictionary *)jsonObject {
    
    @try {
        return [jsonObject objectForKey:name];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting json object, %@", [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting json object, %@", [exception reason]]];
    }
}

- (NSArray *)getJSONArray:(NSString *)name object:(NSDictionary *)jsonObject {
    
    @try {
        return [jsonObject objectForKey:name];
    } @catch(NSException *exception) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting json array, %@", [exception reason]]];
        @throw [[SICSiminovException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"" message:[NSString stringWithFormat:@"Exception caught while getting json array, %@", [exception reason]]];
    }
}

- (SIHHybridSiminovDatas *)getDatas {
    return hybridSiminovDatas;
}


@end