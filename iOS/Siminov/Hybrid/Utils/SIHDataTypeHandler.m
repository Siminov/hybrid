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
