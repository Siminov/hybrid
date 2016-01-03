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


#import "SIHServiceHandler.h"

#import "SICSiminovException.h"
#import "SICLog.h"
#import "SIKServiceException.h"
#import "SIHHybridSiminovDataReader.h"
#import "SIHConstants.h"
#import "SIHGenericService.h"
#import "SIHUtils.h"


@implementation SIHServiceHandler


- (void)invoke:(NSString *)data {
    
    SIHHybridSiminovDataReader *hybridSiminovDataParser = nil;
    data = [SIHUtils stringByDecodingURLFormat:data];
    data = [SIHUtils stringByDecodingURLFormat:data];
    
    
    @try {
        hybridSiminovDataParser = [[SIHHybridSiminovDataReader alloc] initWithData:data];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"invoke" message:[NSString stringWithFormat:@"SiminovException caught while parsing siminov hybrid core data, %@", [siminovException getMessage]]];
        @throw [[SIKServiceException alloc] initWithClassName:NSStringFromClass([self class]) methodName:@"invoke" message:[NSString stringWithFormat:@"SiminovException caught while parsing siminov hybrid core data, %@", [siminovException getMessage]]];
    }
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [hybridSiminovDataParser getDatas];
    
    SIHHybridSiminovData *hybridService = [hybridSiminovDatas getHybridSiminovDataBasedOnDataType:ADAPTER_INVOKE_HANDLER_SERVICE];
    
    SIHHybridSiminovValue *hybridRequestId = [hybridService getValueBasedOnType:ADAPTER_INVOKE_HANDLER_REQUEST_ID];
    
    SIHHybridSiminovValue *hybridServiceName = [hybridService getValueBasedOnType:ADAPTER_INVOKE_HANDLER_SERVICE_NAME];
    
    SIHHybridSiminovValue *hybridRequestName = [hybridService getValueBasedOnType:ADAPTER_INVOKE_HANDLER_SERVICE_REQUEST_NAME];
    
    SIHHybridSiminovData *hybridResources = [hybridService getHybridSiminovDataBasedOnDataType:ADAPTER_INVOKE_HANDLER_SERVICE_RESOURCES];
    
    
    id<SIKIService> genericService = [[SIHGenericService alloc] init];
    
    [genericService setRequestId:[[hybridRequestId getValue] longLongValue]];
    [genericService setService:[hybridServiceName getValue]];
    [genericService setRequest:[hybridRequestName getValue]];
    
    
    if(hybridResources != nil) {
        
        NSEnumerator *hybridResourceValues = [hybridResources getValues];
        SIHHybridSiminovValue *hybridResource;
        
        while(hybridResource = [hybridResourceValues nextObject]) {
            
            NSString *hybridResourceName = [hybridResource getType];
            id hybridResourceValue = [hybridResource getValue];
            
            NSString *result = [(NSString *)hybridResourceValue stringByReplacingOccurrencesOfString:@"+" withString:@" "];
            hybridResourceValue = [result stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            
            
            [genericService addResource:hybridResourceName value:hybridResourceValue];
        }
    }
    
    [genericService invoke];
}

@end
