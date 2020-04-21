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
#import <KUI/KImageView.h>
#import <KUI/KTextView.h>

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
    
    KImageView* k =  [[KImageView alloc] initWithFrame:CGRectMake(150, 150, 100, 100)];
    k.image = [UIImage imageNamed:@"13.png"];
    UIImage* d2 = k.image;
    k.contentMode = UIViewContentModeScaleAspectFill;
    [k setPadding:UIEdgeInsetsMake(10, 10, 10, 10)];
    CGSize w = [k measure:100 widthMode:MeasureModeUnspecified height:100 heightMode:MeasureModeExactly];
    k.frame = CGRectMake(10, 200, w.width, w.height);
    NSLog(@"%f %f",w.width,w.height);
    
    [k setBackgroundColor:UIColor.redColor];
    [[self view] addSubview:k];
    
    
    KTextView* t = [[KTextView alloc] initWithFrame:CGRectMake(10, 350, 160, 50)];
    [t setPadding:UIEdgeInsetsMake(10, 10, 10, 10)];
    t.text = @"这真是文本";
//    t.attributedText = [[NSMutableAttributedString alloc] initWithString:@"这真是文2本" attributes:@{NSForegroundColorAttributeName:[UIColor blueColor], NSFontAttributeName:[UIFont systemFontOfSize:30]}];
    t.textColor = UIColor.blueColor;
    t.letterSpacing = 0.1;
    t.lineHeight = 20;
    t.decorationLine = TextDecorationLineUnderLineThrough;
    [t setBackgroundColor:UIColor.redColor];
    [[self view] addSubview:t];
    
    // Do any additional setup after loading the view.
}

@end
