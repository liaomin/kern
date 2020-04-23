//
//  DestructNode.m
//  KUI
//
//  Created by 廖敏 on 4/22/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "DestructNode.h"

@implementation DestructNode

-(void)dealloc
{
    [self onDestruct];
}

-(void)onDestruct
{
}

@end
