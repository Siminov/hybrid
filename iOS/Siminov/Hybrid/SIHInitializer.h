//
//  SIHInitializer.h
//  hybrid
//
//  Created by user on 07/08/15.
//  Copyright (c) 2015 Siminov. All rights reserved.
//


#import "SICIInitializer.h"


/**
 * It implements IInitializer Interface.
 * It handle initialization of framework.
 */
@interface SIHInitializer: NSObject <SICIInitializer> {
    NSMutableArray *parameters;
}

@end