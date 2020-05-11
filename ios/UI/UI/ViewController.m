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
#import <KUI/KInputView.h>

@interface ViewController ()

@end

@implementation ViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    
    Class cls = [self class];
    void *cd = &cls;
    id q = (__bridge id)cd;
    
    int c = 10;
    void (^block)(int,int) = ^(int a,int b){
        NSLog(@"%d",c);
    };
    c= 20;
    block(10,10);
    
    KView* v = [[KView alloc] initWithFrame:CGRectMake(50, 50, 100, 100)];
    
    [v setBackgroundColorInt:0xffff0000];
    [v setBorderWidth:5];
    [v setBorderRadius:4 topRightRadius:4 bottomLeftRadius:40 bottomRightRadius:4];
    [v setShadow:0xff0000ff radius:10 dx:-5 dy:5];
    [v setBorderColor:0xff0000ff top:0xff00ff00 right:0xff00fff0 bootom:0xffff7f00];
    
    
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
    
    k.clipsToBounds = YES;
    [k setBorderWidth:5];
       [k setBorderRadius:4 topRightRadius:4 bottomLeftRadius:40 bottomRightRadius:4];
       [k setBorderColor:0xff0000ff top:0xff00ff00 right:0xff00fff0 bootom:0xffff7f00];
    
    [[self view] addSubview:k];
    
    KInputView* input = [[KInputView alloc] initWithFrame:CGRectMake(10, 460, 160, 50)];
    [input setTextColor:[UIColor blueColor]];
    [input setPadding:UIEdgeInsetsMake(10, 10, 10, 10)];

    [input setBorderColor:0xffff0000 top:0xffff0000 right:0xffff0000 bootom:0xffff0000];
    [input setBorderWidth:2];
    [input setBorderRadius:4 topRightRadius:4 bottomLeftRadius:4 bottomRightRadius:4];
    [input setBackgroundColor:[UIColor yellowColor]];
    [input setBorderRadius:4 topRightRadius:4 bottomLeftRadius:40 bottomRightRadius:4];
          [input setBorderColor:0xff0000ff top:0xff00ff00 right:0xff00fff0 bootom:0xffff7f00];
    input.text = @"这真是文本这真是文本";
    CGSize qs = [input measure:100 widthMode:MeasureModeAtMost height:100 heightMode:MeasureModeAtMost];
       input.frame = CGRectMake(10, 460, qs.width, qs.height);
    input.textContainer.maximumNumberOfLines = 1;
    input.textContainer.lineBreakMode = NSLineBreakByTruncatingHead;
    [[self view] addSubview:input];
    
    KTextView* t = [[KTextView alloc] initWithFrame:CGRectMake(10, 350, 160, 50)];
    [t setPadding:UIEdgeInsetsMake(10, 10, 10, 10)];
    [t setBorderWidth:5];
    [t setBorderRadius:4 topRightRadius:4 bottomLeftRadius:40 bottomRightRadius:4];
    [t setBorderColor:0xff0000ff top:0xff00ff00 right:0xff00fff0 bootom:0xffff7f00];
    t.text = @"这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本这真是文本";
//    t.attributedText = [[NSMutableAttributedString alloc] initWithString:@"这真是文2本" attributes:@{NSForegroundColorAttributeName:[UIColor blueColor], NSFontAttributeName:[UIFont systemFontOfSize:30]}];
    t.textColor = UIColor.blueColor;
    t.letterSpacing = 0.1;
    t.lineHeight = 40;
    t.decorationLine = TextDecorationLineUnderLineThrough;
//    t.adjustsFontSizeToFitWidth = YES;
    t.fontSize = 16;
    [t setPadding: UIEdgeInsetsMake(20, 20, 20, 20)];
    t.numberOfLines = 10;
    t.ellipsizeMode = TextEllipsizeModeMiddle;
//    t.textAlignment = TextAlignmentCenter;
//    [t setFontStye:FontStyleDefault widthName:@"经典行书简"];
//    t.text = @"这真是文本这真是文本这真是文本这";
    w = [t measure:100 widthMode:MeasureModeAtMost height:100 heightMode:MeasureModeAtMost];
    t.frame = CGRectMake(10, 350, w.width, w.height);
    
    [t setBackgroundColor:UIColor.redColor];
    [[self view] addSubview:t];

    
    // Do any additional setup after loading the view.
}

@end
