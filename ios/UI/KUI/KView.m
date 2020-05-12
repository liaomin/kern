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

@implementation KView

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


@implementation  UIEvent(GetPosition)

-(CGFloat)getLocationX:(UIView*)view pointerIndex:(int)pointerIndex
{
    UITouch* c = [self.allTouches.allObjects objectAtIndex:pointerIndex];
    return [c locationInView:view].x;
}

-(CGFloat)getLocationY:(UIView*)view pointerIndex:(int)pointerIndex
{
    UITouch* c = [self.allTouches.allObjects objectAtIndex:pointerIndex];
    return [c locationInView:view].y;
}

-(CGFloat)getLocationInWindowX:(int)pointerIndex
{
    UITouch* c = [self.allTouches.allObjects objectAtIndex:pointerIndex];
    return [c locationInView:c.window].x;
}

-(CGFloat)getLocationInWindowY:(int)pointerIndex
{
    UITouch* c = [self.allTouches.allObjects objectAtIndex:pointerIndex];
    return [c locationInView:c.window].y;
}

-(int)getPointerSize
{
     return (int)self.allTouches.count;
}

-(CGFloat)getLocationX:(UIView*)view
{
    UITouch* c = [self.allTouches anyObject];
    return [c locationInView:view].x;
}

-(CGFloat)getLocationY:(UIView*)view
{
    UITouch* c = [self.allTouches anyObject];
    return [c locationInView:view].y;
}

@end
