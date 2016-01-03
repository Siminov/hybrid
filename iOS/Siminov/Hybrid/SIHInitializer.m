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


#import "SIHInitializer.h"

#import "SICResourceManager.h"
#import "SIHSiminov.h"
#import "SIHResourceManager.h"

@implementation SIHInitializer

static SICResourceManager *coreResourceManager = nil;
static SIHResourceManager *hybridResourceManager = nil;

-(id)init {
    
    self = [super init];
    
    if(self) {
        coreResourceManager = [SICResourceManager getInstance];
        hybridResourceManager = [SIHResourceManager getInstance];
        
        parameters = [[NSMutableArray alloc] init];
        
        return self;
    }
    
    return self;
}

- (void)addParameter:(id)object {
    [parameters addObject:object];
}

- (void)initialize {
    
    for(int i = 0;i < [parameters count];i++) {
        
        id parameter = [parameters objectAtIndex:i];
        if([parameter isKindOfClass:[UIWebView class]]) {
            [hybridResourceManager setWebView:parameter];
        }
    }
    
    [SIHSiminov start];
}

@end
