//
//  UIView+Border.h
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "BackgroundLayer.h"

NS_ASSUME_NONNULL_BEGIN

@interface UIView (Border)

-(nullable BackgroundLayer*)getBackgroundLayer;

-(BackgroundLayer*)getOrCreateBackgroundLayer;

-(void)setBackgroundColorInt:(uint32_t)color;

-(uint32_t)getBackgroundColorInt;

-(void)setBorderColor:(uint32_t)leftColor top:(uint32_t)topColor right:(uint32_t)rightColor bootom:(uint32_t)bottomColor;

-(void)setBorderWidth:(CGFloat)borderWidth;

-(void)setBorderStyle:(BorderStyle)style;

-(void)setBorderWidth:(CGFloat)left top:(CGFloat)top right:(CGFloat)right bootom:(CGFloat)bottom;

-(CGFloat)getLeftBorderWidth;

-(CGFloat)getTopBorderWidth;

-(CGFloat)getRightBorderWidth;

-(CGFloat)getBottomBorderWidth;

-(void)setBorderRadius:(CGFloat)topLeftRadius topRightRadius:(CGFloat)topRightRadius bottomLeftRadius:(CGFloat)bottomLeftRadius bottomRightRadius:(CGFloat)bottomRightRadius;

-(CGFloat)getTopLeftBorderRadius;

-(CGFloat)getTopRightBorderRadius;

-(CGFloat)getBottomLeftBorderRadius;

-(CGFloat)getBottomRightBorderRadius;

-(void)setShadow:(uint32_t)color radius:(CGFloat)radius dx:(CGFloat)dx dy:(CGFloat)dy;

-(uint32_t)getShadowColor;

-(CGFloat)getShadowRadius;

-(CGFloat)getShadowOffsetX;

-(CGFloat)getShadowOffsetY;

@end

NS_ASSUME_NONNULL_END
