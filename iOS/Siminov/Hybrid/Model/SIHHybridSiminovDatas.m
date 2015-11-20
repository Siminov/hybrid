//
//  SIHHybridSiminovDatas.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SIHHybridSiminovDatas.h"

@implementation SIHHybridSiminovDatas


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        hybridSiminovDatas = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}



- (NSEnumerator *)getHybridSiminovDatas {
    return [hybridSiminovDatas objectEnumerator];
}


- (SIHHybridSiminovData *)getHybridSiminovDataBasedOnDataType:(NSString *)dataType {
    NSEnumerator *datas = [hybridSiminovDatas objectEnumerator];
    
    SIHHybridSiminovData *hybridSiminovData;
    while(hybridSiminovData = [datas nextObject]) {
        NSString *hybridDataType = [hybridSiminovData getDataType];
        
        if(hybridDataType != nil && [hybridDataType length] > 0) {
            if([hybridDataType caseInsensitiveCompare:dataType] == NSOrderedSame) {
                return hybridSiminovData;
            }
        }
    }
    
    return nil;
}


- (void)addHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData {
    [hybridSiminovDatas addObject:hybridSiminovData];
}


- (bool)containHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData {
    return [hybridSiminovDatas containsObject:hybridSiminovData];
}


- (void)removeHybridSiminovData:(SIHHybridSiminovData *)hybridSiminovData {
    [hybridSiminovDatas removeObject:hybridSiminovData];
}

@end


@implementation SIHHybridSiminovData


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        dataType = nil;
        dataValue = nil;
        
        values = [[NSMutableArray alloc] init];
        datas = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getDataType {
    return dataType;
}


- (void)setDataType:(NSString *)datatype {
    dataType = datatype;
}


- (NSString *)getDataValue {
    return dataValue;
}


- (void)setDataValue:(NSString *)value {
    dataValue = value;
}


- (NSEnumerator *)getValues {
    return [values objectEnumerator];
}


- (SIHHybridSiminovValue *)getValueBasedOnType:(NSString *)type {
    NSEnumerator *vals = [values objectEnumerator];
    
    SIHHybridSiminovValue *value;
    while(value = [vals nextObject]) {
        
        if([[value getType] caseInsensitiveCompare:type] == NSOrderedSame) {
            return value;
        }
    }
    
    return nil;
}

- (void)addValue:(SIHHybridSiminovValue *)hybridSiminovValue {
    [values addObject:hybridSiminovValue];
}


- (void)removeValue:(SIHHybridSiminovValue *)hybridSiminovValue {
    [values removeObject:hybridSiminovValue];
}


- (bool)containValue:(SIHHybridSiminovValue *)hybridSiminovValue {
    return [values containsObject:hybridSiminovValue];
}


- (NSEnumerator *)getDatas {
    return [datas objectEnumerator];
}


- (SIHHybridSiminovData *)getHybridSiminovDataBasedOnDataType:(NSString *)datatype {
    NSEnumerator *hybridSiminovDatas = [datas objectEnumerator];
    
    SIHHybridSiminovData *hybridSiminovData;
    while(hybridSiminovData = [hybridSiminovDatas nextObject]) {
        NSString *hybridDataType = [hybridSiminovData getDataType];
        
        if(hybridDataType != nil && [hybridDataType length] > 0) {
            if([hybridDataType caseInsensitiveCompare:datatype] == NSOrderedSame) {
                return hybridSiminovData;
            }
        }
    }
    
    return nil;
}


- (void)addData:(SIHHybridSiminovData *)hybridSiminovData {
    [datas addObject:hybridSiminovData];
}


- (bool)containData:(SIHHybridSiminovData *)hybridSiminovData {
    return [datas containsObject:hybridSiminovData];
}


- (void)removeData:(SIHHybridSiminovData *)hybridSiminovData {
    [datas removeObject:hybridSiminovData];
}


@end


@implementation SIHHybridSiminovValue


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        type = nil;
        value = nil;
        
        return self;
    }
    
    return self;
}


- (NSString *)getType {
    return type;
}


- (void)setType:(NSString *)typ {
    type = typ;
}


- (NSString *)getValue {
    return value;
}

- (void)setValue:(NSString *)val {
    value = val;
}

@end
