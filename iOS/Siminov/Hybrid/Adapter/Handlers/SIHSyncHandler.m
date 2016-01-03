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

#import "SIHSyncHandler.h"

#import "SIHHybridSiminovDataReader.h"
#import "SIHHybridSiminovDatas.h"
#import "SICSiminovException.h"
#import "SICLog.h"
#import "SIKSyncRequest.h"
#import "SIHHybridSyncRequest.h"
#import "SIHSyncHandler.m"
#import "SIHGenericSyncHandler.h"
#import "SIHUtils.h"


@implementation SIHSyncHandler


- (void)handle:(NSString *)data {
    
    SIHHybridSiminovDataReader *hybridSiminovDataParser = nil;
    data = [SIHUtils stringByDecodingURLFormat:data];
    data = [SIHUtils stringByDecodingURLFormat:data];
    
    @try {
        hybridSiminovDataParser = [[SIHHybridSiminovDataReader alloc] initWithData:data];
    } @catch(SICSiminovException *siminovException) {
        [SICLog error:NSStringFromClass([self class]) methodName:@"handle" message:[NSString stringWithFormat:@"SiminovException caught while parsing js core data, %@", [siminovException getMessage]]];
    }
    
    
    SIHHybridSiminovDatas *hybridSiminovDatas = [hybridSiminovDataParser getDatas];
    
    SIHHybridSiminovData *hybridSyncRequest = [hybridSiminovDatas getHybridSiminovDataBasedOnDataType:HYBRID_SYNC_REQUEST_SYNC_REQUEST];
    
    SIHHybridSiminovValue *hybridRequestId = [hybridSyncRequest getValueBasedOnType:HYBRID_SYNC_REQUEST_ID];
    SIHHybridSiminovValue *hybridSyncName = [hybridSyncRequest getValueBasedOnType:HYBRID_SYNC_REQUEST_NAME];
    
    SIHHybridSiminovData *hybridResources = [hybridSyncRequest getHybridSiminovDataBasedOnDataType:HYBRID_SYNC_REQUEST_RESOURCES];
    
    
    id<SIKISyncRequest> syncRequest = [[SIKSyncRequest alloc] init];
    [syncRequest setRequestId:[[hybridRequestId getValue] longLongValue]];
    [syncRequest setName:[hybridSyncName getValue]];
    
    
    if(hybridResources != nil) {
        
        NSEnumerator *hybridResourceValues = [hybridResources getValues];
        SIHHybridSiminovValue *hybridResource;
        
        while(hybridResource = [hybridResourceValues nextObject]) {
            
            NSString *hybridResourceName = [hybridResource getType];
            id hybridResourceValue = [hybridResource getValue];
            
            NSString *result = [(NSString *)hybridResourceValue stringByReplacingOccurrencesOfString:@"+" withString:@" "];
            hybridResourceValue = [result stringByReplacingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
            
            [syncRequest addResource:hybridResourceName value:hybridResourceValue];
        }
    }
    
    
    SIHGenericSyncHandler *syncHandler = [SIHGenericSyncHandler getInstance];
    [syncHandler handle:syncRequest];
}

@end