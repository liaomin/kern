//
//  ViewController.m
//  UI
//
//  Created by 廖敏 on 6/11/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "ViewController.h"
#import <HUI/HUI.h>
#include "UIView+Border.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self.view setBorderWidth:2];
    [self.view isFocused];
    // Do any additional setup after loading the view.
}


@end
