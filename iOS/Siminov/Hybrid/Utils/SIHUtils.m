//
//  SIHUtils.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIHUtils.h"

@implementation SIHUtils

+ (NSString *)stringByDecodingURLFormat:(NSString *)data {
    
    if(data == nil) {
        return data;
    }
    
    data = [data stringByReplacingOccurrencesOfString:@"+" withString:@" "];
    return [data stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}

+ (NSString *)stringByEncodeingURLFormat:(NSString *)data {
    
    if(data == nil) {
        return data;
    }
    
    return [data stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
}

@end
