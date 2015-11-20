//
//  SIHServiceHandler.m
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


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
