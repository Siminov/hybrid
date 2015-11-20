//
//  SIHHybridSiminovDataReader.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import <Foundation/Foundation.h>

#import "SIHHybridSiminovDatas.h"
#import "SIHHybridSiminovDatas.h"


@interface SIHHybridSiminovDataReader : NSObject {
    
    SIHHybridSiminovDatas *hybridSiminovDatas;
}

- (id)initWithData:(NSString * const)data;

- (SIHHybridSiminovDatas *)getDatas;

@end
