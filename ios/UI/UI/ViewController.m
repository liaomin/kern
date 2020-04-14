//
//  ViewController.m
//  UI
//
//  Created by 廖敏 on 6/11/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "ViewController.h"
//#import <HUI/HUI.h>
#import <KUI/KUI.h>

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    KView* v = [[KView alloc] initWithFrame:CGRectMake(50, 50, 100, 100)];
    
    [v setBackgroundColorInt:0xffff0000];
    [v setBorderWidth:12];
    CAAnimationGroup* d;
    [d setRemovedOnCompletion:NO];
    
    [[self view] addSubview:v];
    
    // Do any additional setup after loading the view.
}

@end
