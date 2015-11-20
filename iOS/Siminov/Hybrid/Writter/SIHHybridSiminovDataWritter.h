//
//  SIHHybridSiminovDataWritter.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "SIHHybridSiminovDatas.h"

@interface SIHHybridSiminovDataWritter : NSObject


/**
 * Build JSON using Hybrid Siminov Datas.
 * @param jsSiminovDatas Hybrid Siminov Datas.
 * @return Siminov JSON
 * @throws SiminovException If any error occur while generating JSON out of Hybrid Siminov Datas.
 */
+ (NSString *)jsonBuidler:(SIHHybridSiminovDatas *)jsSiminovDatas;


@end
