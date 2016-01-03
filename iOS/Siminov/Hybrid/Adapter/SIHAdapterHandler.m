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

#import "SIHAdapterHandler.h"

#import "SIHConstants.h"
#import "SIHAdapterDescriptor.h"
#import "SIHResourceManager.h"
#import "SIHIHandler.h"
#import "SICClassUtils.h"
#import "SIHConstants.h"
#import "SIHUtils.h"


@implementation SIHAdapterHandler

static SIHAdapterHandler *adapterHandler;
static SIHResourceManager *resourceManager;
static id<SIHIHandler> handler;

-(id)init {
    
    SIHAdapterDescriptor *adapterDescriptor = [resourceManager getAdapterDescriptor:HYBRID_TO_NATIVE_ADAPTER];
    id<SIHIHandler> handler = [SICClassUtils createClassInstance:[adapterDescriptor getMapTo]];
    
    [self registerHandler:handler];
    [resourceManager setAdapterHandler:self];
    
    return self;
}

+ (void)initialize {
    resourceManager = [SIHResourceManager getInstance];
}

+ (SIHAdapterHandler *)getInstance {
    
    if(!adapterHandler) {
        adapterHandler = [[super allocWithZone:NULL] init];
    }
    return adapterHandler;
}


/**
 * Registers Handler instance to handle all request processed between Hybrid and Native.
 * @param handler IHandler Instance.
 */
- (void)registerHandler:(id<SIHIHandler>)hndlr {
    
    SIHAdapterDescriptor *adapterDescriptor = [resourceManager getAdapterDescriptor:HYBRID_TO_NATIVE_ADAPTER];
    
    NSString *adapterName = [adapterDescriptor getName];
    
    handler = hndlr;
    
    //SIHHybridInterceptor *hybridInterceptor = [SIHHybridInterceptor alloc] init];
    //hybridInterceptor.view = hybridActivity;
    
    
    //[hybridView setDelegate:hybridInterceptor];
    //[hybridView setDelegate:hybridActivity];
    
    [NSURLProtocol registerClass:[SIHHybridInterceptor class]];
}



/**
 * Get IHandler instance which receives and handler request processed between Hybrid and Native.
 * @return IHandler Instance.
 */
- (id<SIHIHandler>)getHandler {
    return handler;
}

@end



@implementation SIHHybridInterceptor


+ (BOOL)canInitWithRequest:(NSURLRequest *)request {
    
    NSString *url = request.URL.absoluteString;
    if (![url containsString:SIMINOV_HYBRID_TO_NATIVE_ADAPTER_INTEREPTOR]) {
        return NO;
    }
    
    return YES;
}

+ (NSURLRequest *) canonicalRequestForRequest:(NSURLRequest *)request {
    return request;
}

- (void) startLoading {
    
    id<SIHIHandler> handler = [[SIHAdapterHandler getInstance] getHandler];
    
    NSString *requestQuery = self.request.URL.query;
    
    NSURLComponents *components = [[NSURLComponents alloc] init];
    components.query = requestQuery;
    
    NSString *requestId;
    NSString *requestMode;
    NSString *requestAPI;
    NSString *requestData;
    
    for(NSURLQueryItem *item in components.queryItems) {
        
        if([item.name isEqualToString:HTTP_REQUEST_ID]) {
            requestId = item.value;
        } else if([item.name isEqualToString:HTTP_REQUEST_MODE]) {
            requestMode = item.value;
        } else if ([item.name isEqualToString:HTTP_REQUEST_API_QUERY_PARAMETER]) {
            requestAPI = item.value;
        } else if([item.name isEqualToString:HTTP_REQUEST_DATA_QUERY_PARAMETER]) {
            requestData = item.value;
        }
    }
    
    
    requestData = [SIHUtils stringByDecodingURLFormat:requestData];
    
    
    NSString *data;
    if(requestMode && [requestMode caseInsensitiveCompare:HTTP_REQUEST_MODE_ASYNC] == NSOrderedSame) {
        data = [handler handleHybridToNativeAsync:requestId action:requestAPI data:requestData];
    } else {
        
        if(requestData == nil) {
            data = [handler handleHybridToNative:requestAPI];
        } else {
            data = [handler handleHybridToNative:requestAPI data:requestData];
        }
    }
    
    
    NSData *dataStream = [data dataUsingEncoding:NSUTF8StringEncoding];
    NSString *mimeType = self.response.MIMEType;
    NSString *encoding = self.response.textEncodingName;
    
    NSURLResponse *response = [[NSURLResponse alloc] initWithURL:self.request.URL
                                                        MIMEType:mimeType
                                           expectedContentLength:dataStream.length
                                                textEncodingName:encoding];
    
    [self.client URLProtocol:self didReceiveResponse:response cacheStoragePolicy:NSURLCacheStorageNotAllowed];
    [self.client URLProtocol:self didLoadData:dataStream];
    [self.client URLProtocolDidFinishLoading:self];
}

- (void) stopLoading {
    NSLog(@"");
}

@end