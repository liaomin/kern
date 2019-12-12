//
//  BorderView.m
//  ios
//
//  Created by 廖敏 on 6/6/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "BorderView.h"
#import <objc/runtime.h>
#import <objc/objc.h>
#import <SharedCode/SharedCode.h>


//#import "ios-Swift.h"


@implementation BorderView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/


- (UIView *)hitTest:(CGPoint)point withEvent:(UIEvent *)event{
    return [super hitTest:point withEvent:event];
}

-(void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    [super touchesBegan:touches withEvent:event];
}

- (void)touchesMoved:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    
}

- (void)touchesCancelled:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    
}

- (void)touchesEnded:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event
{
    
}

-(void)test
{
    [self.layer setNeedsDisplay];
    [self setBackgroundColor:UIColor.blueColor];
    UIImage* image = [UIImage imageNamed:@"1.jpg"];
    self.image = image;
    self.contentMode = UIViewContentModeScaleAspectFit;
    
//    [self.layer setNeedsDisplay];

//    CALayer* l = [[CALayer alloc] init];
//    l.frame = self.bounds;
//    l.backgroundColor = [UIColor redColor].CGColor;
//    [l setNeedsDisplay];
//    [self.layer addSublayer:l];
//    l.delegate = self;
    
    

    
    
//    self.layer.transform = CATransform3DRotate(CATransform3DIdentity, M_PI_4, 0, 1, 0);
//    self.transform = CGAffineTransformRotate(CGAffineTransformIdentity, M_PI_4);
//    CGRect r = [self convertRect:self.bounds toView:self.superview];

    
//    [self setBackgroundImage:[UIImage imageNamed:@"1.jpg"] forState:UIControlStateNormal];
//    [self setBackgroundImage:[UIImage imageNamed:@"2.jpg"] forState:UIControlStateHighlighted];
//
    
//    UIGestureRecognizer* tap =[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tap)];
//     [self t];
//    tap.cancelsTouchesInView = false;
//    [self addGestureRecognizer:tap];
//    tap =[[UILongPressGestureRecognizer alloc] initWithTarget:self action:sel_registerName("tapL:")];
//    tap.cancelsTouchesInView = false;
//    [self addGestureRecognizer:tap];
    
//    id s = NSAttributedStringKey;
    
    
}

-(void)down
{
    
}


- (void)observeValueForKeyPath:(NSString *)keyPath ofObject:(id)object change:(NSDictionary<NSKeyValueChangeKey,id> *)change context:(void *)context
{
}


-(void)tap
{

    NSLog(@"sss");
}

-(void)tapL:(UILongPressGestureRecognizer*)sender
{
    
    NSLog(@"ssls %@",sender);
}


-(UIImage*)t{
    
    UIGraphicsBeginImageContextWithOptions(self.bounds.size,NO,0);
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    const CGPoint points[] = {
        (CGPoint){1, 0},
       (CGPoint){1, 0},
       (CGPoint){1, 0},
        (CGPoint){1, 0},
    };
    
    CGContextSetFillColorWithColor(ctx,UIColor.blueColor.CGColor);
    CGContextAddRect(ctx, CGRectMake(30.0,30.0,50.5,50.0));
    CGContextFillPath(ctx);
    
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return newImage;
}


-(void)dealloc
{
}

- (void)willMoveToSuperview:(UIView *)newSuperview
{
}

- (void)displayLayer:(CALayer *)layer
{
    NSLog(@":");
}

- (void)drawLayer:(CALayer *)layer inContext:(CGContextRef)ctx
{
    
}

- (void)layerWillDraw:(CALayer *)layer
{
}

- (void)setBorderLayer:(CAShapeLayer *)borderLayer{
}

- (void)setState:(UIControlState)state{
    
}

@end
