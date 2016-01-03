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


#import "SIHSyncWorker.h"
#import "SIHGenericService.h"

NSMutableArray *hybridSyncRequests;
static SIHSyncWorkerThread *hybridSyncWorkerThread = nil;
static SIKResourceManager *connectResourceManager;
static SIHSyncWorker *hybridSyncWorker;

@implementation SIHSyncWorkerThread


-(void)main {
    
    SIKResourceManager *resourceManager = [SIKResourceManager getInstance];
    NSEnumerator *requests = [hybridSyncRequests objectEnumerator];
    id<SIKISyncRequest> syncRequest;
    
    while(syncRequest = [requests nextObject]) {
        
        /*
         * Fire Sync Started Event
         */
        id<SIKISyncEvents> syncEventHandler = [resourceManager getSyncEventHandler];
        if(syncEventHandler != nil) {
            [syncEventHandler onStart:syncRequest];
        }
        
        
        /*
         * Process Request
         */
        SIKSyncDescriptor *refreshDescriptor = [resourceManager getSyncDescriptor:[syncRequest getName]];
        
        NSEnumerator *services = [refreshDescriptor getServiceDescriptorNames];
        NSString *service;
        
        while(service = [services nextObject]) {
            
            NSUInteger lastIndex = 0;
            NSRange range = [service rangeOfString:SYNC_DESCRIPTOR_SERVICE_SEPARATOR];
            if (range.length == 0 && range.location > service.length) {
                lastIndex = 0;
            } else {
                lastIndex = range.location;
            }
            
            long requestId = [syncRequest getRequestId];
            NSString *serviceName = [service substringWithRange:NSMakeRange(0, lastIndex)];
            NSString *requestName = [service substringFromIndex:lastIndex+1];
            
            SIKServiceDescriptor *serviceDescriptor = [resourceManager requiredServiceDescriptorBasedOnName:serviceName];
            
            id<SIKIService> genericService = [[SIHGenericService alloc] init];
            [genericService setRequestId:requestId];
            [genericService setService:serviceName];
            [genericService setRequest:requestName];
            
            [genericService setServiceDescriptor:serviceDescriptor];
            
            NSEnumerator *resources = [syncRequest getResources];
            NSString *resourceName;
            
            while(resourceName = [resources nextObject]) {
                id resourceValue = [syncRequest getResource:resourceName];
                
                [genericService addResource:resourceName value:resourceValue];
            }
            
            
            [genericService invoke];
        }
        
        
        /*
         * Fire Sync Started Event
         */
        if(syncEventHandler != nil) {
            [syncEventHandler onFinish:syncRequest];
        }
        
        
        [hybridSyncRequests removeObject:syncRequest];
    }
    
    hybridSyncWorkerThread = nil;
}

@end


@implementation SIHSyncWorker

-(id) init
{
    self = [super init];
    
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        connectResourceManager = [SIKResourceManager getInstance];
        hybridSyncRequests = [[NSMutableArray alloc]init];
    });
    
    return self;
}

+ (SIHSyncWorker *)getInstance {
    
    if(hybridSyncWorker == nil) {
        hybridSyncWorker = [[SIHSyncWorker alloc]init];
    }
    
    return hybridSyncWorker;
}


- (void)startWorker {
    
    if(hybridSyncWorkerThread == nil) {
        hybridSyncWorkerThread = [[SIHSyncWorkerThread alloc] init];
    }
    
    [hybridSyncWorkerThread start];
}

- (void)stopWorker {
    
    if(hybridSyncWorkerThread == nil) {
        return;
    }
    
    
    if(![hybridSyncWorkerThread isExecuting]) {
        [hybridSyncWorkerThread start];
    }
}

- (BOOL)isWorkerRunning {
    
    if(hybridSyncWorkerThread == nil) {
        return false;
    }
    
    return [hybridSyncWorkerThread isExecuting];
}

- (void)addRequest:(id<SIKIRequest>)request {
    
    id<SIKISyncRequest> syncRequest = (id<SIKISyncRequest>)request;
    if([self containsRequest:syncRequest]) {
        return;
    }
    
    
    [hybridSyncRequests addObject:syncRequest];
    
    /*
     * Fire Sync Queued Event
     */
    id<SIKISyncEvents> syncEventHandler = [connectResourceManager getSyncEventHandler];
    if(syncEventHandler != nil) {
        [syncEventHandler onQueue:syncRequest];
    }
    
    
    if(![self isWorkerRunning]) {
        [self startWorker];
    }
}

- (BOOL)containsRequest:(id<SIKIRequest>)refreshRequest {
    return [hybridSyncRequests containsObject:refreshRequest];
}

- (void)removeRequest:(id<SIKIRequest>)refreshRequest {
    [hybridSyncRequests removeObject:refreshRequest];
}


@end

