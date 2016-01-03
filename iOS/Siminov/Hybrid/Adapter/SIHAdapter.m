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

#import "SIHAdapter.h"

#import "SIHIHandler.h"

@implementation SIHAdapter


- (id)init {
    
    self = [super init];
    
    if(self) {
        
        adapterHandler = [[SIHAdapterHandler alloc] init];
        parameters = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}


- (NSString *)getAdapterName {
    return adapterName;
}


- (void)setAdapterName:(NSString *)adaptername {
    adapterName = adaptername;
}


- (NSString *)getHandlerName {
    return handlerName;
}


- (void)setHandlerName:(NSString *)handlername {
    handlerName = handlername;
}


- (NSEnumerator *)getParameters {
    return [parameters objectEnumerator];
}


- (void)addParameter:(NSString *)parameter {
    [parameters addObject:parameter];
}



- (void)invoke {
    
    id<SIHIHandler> handler = [adapterHandler getHandler];
    
    if(handlerName == nil && [handlerName length] <= 0) {
        [handler handleNativeToHybrid:adapterName data:parameters];
    } else {
        [handler handleNativeToHybrid:[NSString stringWithFormat:@"%@.%@", adapterName, handlerName] data:parameters];
    }
}


@end