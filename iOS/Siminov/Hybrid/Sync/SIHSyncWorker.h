//
//  SIHSyncWorker.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SIKSyncWorker.h"

@interface SIHSyncWorker : NSObject<SIKIWorker>

+ (SIHSyncWorker *)getInstance;

@end


@interface SIHSyncWorkerThread : NSThread

@end