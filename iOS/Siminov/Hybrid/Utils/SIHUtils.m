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
