//
//  KView.m
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KView.h"
#import <objc/runtime.h>
#import "BackgroundLayer.h"

@interface KView()
{
    UIEdgeInsets _padding;
}

@end

@implementation KView

+(void)load
{
    
}

- (CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode{
    CGFloat w = 0;
    if(widthMode == MeasureModeExactly){
        w = width;
    }
    CGFloat h = 0;
    if(heightMode == MeasureModeExactly){
        h = height;
    }
    return CGSizeMake(w, h);
}

- (void)willMoveToSuperview:(UIView *)newSuperview
{
    if(newSuperview){
        
    }
    [super willMoveToSuperview:newSuperview];
}

- (void)didMoveToSuperview{
    [super didMoveToSuperview];
    UIView* superView = self.superview;
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(superView){
        if(bgLayer){
            [superView.layer insertSublayer:bgLayer below:self.layer];
        }
    }else{
        if(bgLayer){
            [bgLayer removeFromSuperlayer];
        }
    }
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.frame = self.frame;
    }
}

- (void)setPadding:(UIEdgeInsets)padding
{
    _padding = padding;
}

-(UIEdgeInsets)getPadding
{
    return _padding;
}

- (void)dealloc
{
    [self onDestruct];
}

-(void)onDestruct
{
    
}

@end
