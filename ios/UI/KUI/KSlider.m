//
//  KSlider.m
//  KUI
//
//  Created by 廖敏 on 4/28/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KSlider.h"

@implementation KSlider

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
    [super willMoveToSuperview:newSuperview];
}

- (void)willMoveToWindow:(UIWindow *)newWindow
{
    [super willMoveToWindow:newWindow];
}


- (void)didMoveToSuperview{
    [super didMoveToSuperview];
    [self addOrRemoveBgLayer];
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self addOrRemoveBgLayer];
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.frame = self.frame;
        [self updateMask];
    }
}

- (void)setClipsToBounds:(BOOL)clipsToBounds
{
    [super setClipsToBounds:clipsToBounds];
    [self updateMask];
}

- (void)dealloc
{
    [self onDestruct];
}

-(void)onDestruct
{
    
}

- (void)setBackgroundColor:(UIColor *)backgroundColor
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.bgColor = [BackgroundLayer uIColor2Int:backgroundColor];
    }else{
        [super setBackgroundColor:backgroundColor];
    }
}



@end
