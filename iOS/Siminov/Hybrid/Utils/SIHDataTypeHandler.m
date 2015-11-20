//
//  SIHDataTypeHandler.m
//  Hybrid
//
//  Created by user on 10/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import "SIHDataTypeHandler.h"
#import "SICConstants.h"
#import "SICDeploymentException.h"

@implementation SIHDataTypeHandler

+ (NSString *)convert:(NSString *)dataType {
    
    /*
     * Data Type Conversation
     */
    if([dataType caseInsensitiveCompare:INTEGER_DATA_TYPE] == NSOrderedSame) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"convert" message:@"INTEGER DATA TYPE NOT SUPPORTED ON iOS"];
    } else if([dataType caseInsensitiveCompare:PRIMITIVE_INTEGER_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_PRIMITIVE_INTEGER_DATA_TYPE;
    } else if([dataType caseInsensitiveCompare:LONG_DATA_TYPE] == NSOrderedSame) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"convert" message:@"LONG DATA TYPE NOT SUPPORTED ON iOS"];
    } else if([dataType caseInsensitiveCompare:PRIMITIVE_LONG_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_PRIMITIVE_LONG_DATA_TYPE;
    } else if([dataType caseInsensitiveCompare:FLOAT_DATA_TYPE] == NSOrderedSame) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"convert" message:@"FLOAT DATA TYPE NOT SUPPORTED ON iOS"];
    } else if([dataType caseInsensitiveCompare:PRIMITIVE_FLOAT_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_PRIMITIVE_FLOAT_DATA_TYPE;
    } else if([dataType caseInsensitiveCompare:DOUBLE_DATA_TYPE] == NSOrderedSame) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"convert" message:@"DOUBLE DATA TYPE NOT SUPPORTED ON iOS"];
    } else if([dataType caseInsensitiveCompare:PRIMITIVE_DOUBLE_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_PRIMITIVE_DOUBLE_DATA_TYPE;
    } else if([dataType caseInsensitiveCompare:BOOLEAN_DATA_TYPE] == NSOrderedSame) {
        @throw [[SICDeploymentException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"convert" message:@"BOOLEAN DATA TYPE NOT SUPPORTED ON iOS"];
    } else if([dataType caseInsensitiveCompare:PRIMITIVE_BOOLEAN_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_PRIMITIVE_BOOLEAN_DATA_TYPE;
    } else if([dataType caseInsensitiveCompare:STRING_DATA_TYPE] == NSOrderedSame) {
        return NATIVE_STRING_DATA_TYPE;
    }
    
    
    /*
     * Other Data Type
     */
    else {
        return dataType;
    }
}

@end
