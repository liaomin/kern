//
//  KInput.m
//  KUI
//
//  Created by 廖敏 on 4/27/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KInputView.h"

@implementation KInputView

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode
{
    self.text = @"司马彦行书${i+1}";
    if(widthMode == MeasureModeExactly && heightMode == MeasureModeExactly){
        return CGSizeMake(width, height);
    }
    UIEdgeInsets _pddding = [self getPadding];
    CGFloat paddingWidth = _pddding.left + _pddding.right;
    CGFloat paddingHeight = _pddding.top + _pddding.bottom;
    CGFloat maxWidth = width - paddingWidth;
    CGFloat maxHeight = height - paddingHeight;
    if(widthMode == MeasureModeUnspecified){
        maxWidth = CGFLOAT_MAX;
    }
    if(heightMode == MeasureModeUnspecified){
        maxHeight = CGFLOAT_MAX;
    }
    
    NSMutableDictionary* attrubutes = [[NSMutableDictionary alloc] initWithCapacity:1];
    UIFont* font = self.font;
    if(font){
        [attrubutes setObject:font forKey:NSFontAttributeName];
    }
    CGRect rect =  [self.text boundingRectWithSize:CGSizeMake(maxWidth, maxHeight) options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:attrubutes context:nil];
    
    CGFloat measureWidth = rect.size.width;
    CGFloat measureHeight = rect.size.height;
    if(widthMode == MeasureModeExactly){
        measureWidth = maxWidth;
    }else if (widthMode == MeasureModeAtMost){
        measureWidth = MIN(measureWidth, maxWidth) + paddingWidth;
    }
    if(heightMode == MeasureModeExactly){
        measureHeight = maxHeight;
    }else if (heightMode == MeasureModeAtMost){
        measureHeight = MIN(measureHeight, maxHeight) + paddingHeight;
    }
    return CGSizeMake(measureWidth, measureHeight);
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


- (void)setPadding:(UIEdgeInsets)padding
{
    [super setPadding:padding];
    self.textContainerInset = padding;
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
