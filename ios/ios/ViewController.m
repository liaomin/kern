//
//  ViewController.m
//  ios
//
//  Created by 廖敏 on 4/28/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "ViewController.h"
#import <SharedCode/SharedCode.h>
#import "BorderView.h"


@implementation ViewController


- (void)viewDidLoad {
    [super viewDidLoad];
    
    BorderView* b = [[BorderView alloc] initWithFrame:CGRectMake(20, 50, 200, 200)];
    [b test];
    [b setNeedsDisplay];
    b.layer.shadowOffset = CGSizeMake(0, 0);
    b.layer.shadowOpacity = 1;
    b.layer.shadowRadius = 10;
//    b.layer.cornerRadius = 80;
    b.layer.contents = b.image;
    b.layer.shadowColor = [UIColor redColor].CGColor;
    b.layer.mask = NULL;

    
//    UILabel* b2 = [[BorderView alloc] initWithFrame:CGRectMake(0, 0, 200, 200)];
//    b2.text = @"dwdw";
//    [b addSubview:b2];
    
//    BorderView* b2 = [[BorderView alloc] initWithFrame:CGRectMake(20, 50, 200, 200)];
//    [b addSubview:b2];
    [self.view addSubview:b];
    self.borderView = b;
    
    CATransform3D transform = CATransform3DIdentity;
    transform.m34 = 1.0 / -500.0;
    self.borderView.layer.transform = transform;
//    [b.layer setValue:[NSNumber numberWithDouble:100] forKeyPath:@"transform.translation.x"];

    [[SharedCodePlatformCompanion companion] doInitViewController:self];

    
}

+(void)load{
    
}

- (IBAction)x:(id)sender {
    CABasicAnimation *animation = [CABasicAnimation animationWithKeyPath:@"transform.rotation.x"];
    animation.duration = 5;
    animation.fromValue = [NSNumber numberWithFloat:0];
    animation.toValue = [NSNumber numberWithFloat: M_PI];
    animation.removedOnCompletion = NO;
    animation.repeatCount = 3;
    animation.autoreverses = YES;
    [self.borderView.layer addAnimation:animation forKey:animation.keyPath];
}

- (IBAction)y:(id)sender {
    CABasicAnimation *animation = [CABasicAnimation animationWithKeyPath:@"transform.rotation.y"];
    animation.duration = 5;
    animation.fromValue = [NSNumber numberWithFloat:0];
    animation.toValue = [NSNumber numberWithFloat:2 * M_PI];
    animation.removedOnCompletion = true;
    [self.borderView.layer addAnimation:animation forKey:animation.keyPath];

}
- (IBAction)z:(id)sender {
    CABasicAnimation *animation = [CABasicAnimation animationWithKeyPath:@"transform.rotation.z"];
    animation.duration = 5;
    animation.fromValue = [NSNumber numberWithFloat:0];
    animation.toValue = [NSNumber numberWithFloat:2 * M_PI];
    animation.removedOnCompletion = true;
    [self.borderView.layer addAnimation:animation forKey:animation.keyPath];

}

- (IBAction)m:(id)sender {
    CABasicAnimation *rx = [CABasicAnimation animationWithKeyPath:@"transform.rotation.x"];
    rx.fromValue = [NSNumber numberWithFloat:0];
    rx.toValue = [NSNumber numberWithFloat:2 * M_PI];
    CABasicAnimation *ry = [CABasicAnimation animationWithKeyPath:@"transform.rotation.y"];
    ry.fromValue = [NSNumber numberWithFloat:0];
    ry.toValue = [NSNumber numberWithFloat:2 * M_PI];
    CABasicAnimation *rz = [CABasicAnimation animationWithKeyPath:@"transform.rotation.z"];
    ry.fromValue = [NSNumber numberWithFloat:0];
    ry.toValue = [NSNumber numberWithFloat:2 * M_PI];
    CABasicAnimation *tx = [CABasicAnimation animationWithKeyPath:@"transform.translation.x"];
    tx.fromValue = [NSNumber numberWithFloat:0];
    tx.toValue = [NSNumber numberWithFloat:20];
    CABasicAnimation *ty = [CABasicAnimation animationWithKeyPath:@"transform.translation.y"];
    ty.fromValue = [NSNumber numberWithFloat:0];
    ty.toValue = [NSNumber numberWithFloat:20];
    CABasicAnimation *tz = [CABasicAnimation animationWithKeyPath:@"transform.translation.z"];
    tz.fromValue = [NSNumber numberWithFloat:0];
    tz.toValue = [NSNumber numberWithFloat:22];
    
    CAAnimationGroup* g = [[CAAnimationGroup alloc] init];
    g.animations = @[rx,ry,rz,tx,ty,tz];
    g.duration = 5;
    g.removedOnCompletion = true;
    
    tx.toValue = [NSNumber numberWithFloat:200];
    [self.borderView.layer addAnimation:g forKey:@""];
    
}

-(void)test{
    NSLog(@"touchDOwn");
}

-(void)dealloc{
   
}



@end
