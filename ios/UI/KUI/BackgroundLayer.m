//
//  BackgroundLayer.m
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "BackgroundLayer.h"

@interface BackgroundLayer()
{
    BOOL isDirt;
}
@end

@implementation BackgroundLayer

+(uint32_t)uIColor2Int:(UIColor*)color
{
    CGFloat r,g,b,a;
    [color getRed:&r green:&g blue:&b alpha:&a];
    return ((uint32_t)(a*255) << 24) | ((uint32_t)(r*255) << 16) | ((uint32_t)(g*255) << 8) | ((uint32_t)(b*255));
}

+(UIColor*)int2UIColor:(uint32_t)color
{
    return [UIColor colorWithRed:color >> 16 & 0x000000FF green:color >> 8 & 0x000000FF blue:color & 0x000000FF alpha:color >> 24];
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        isDirt = YES;
        self.zPosition = CGFLOAT_MIN;
        _bgColor = _mShadowColor = _shadowDx = _shadowDy = _mShadowColor = 0;
        _borderStyle = BorderStyleSolid;
        _borderTopWidth = _borderBottomWidth = _borderRightWidth = _borderLeftWidth = 0;
        _borderTopRightRadius = _borderTopLeftRadius = _borderBottomLeftRadius = _borderTopRightRadius = 0;
    }
    return self;
}


- (void)setAllBorderRadius:(CGFloat)topLeftRadius topRightRadius:(CGFloat)topRightRadius bottomLeftRadius:(CGFloat)bottomLeftRadius bottomRightRadius:(CGFloat)bottomRightRadius
{
    isDirt = YES;
    _borderTopLeftRadius = topLeftRadius;
    _borderTopRightRadius = topRightRadius;
    _borderBottomLeftRadius = bottomLeftRadius;
    _borderTopLeftRadius = topLeftRadius;
}


- (void)setAllBorderWidth:(CGFloat)left top:(CGFloat)top right:(CGFloat)right bootom:(CGFloat)bottom
{
    isDirt = YES;
    _borderLeftWidth = left;
    _borderTopWidth = top;
    _borderRightWidth = right;
    _borderBottomWidth = bottom;
}

- (void)setAllShadow:(uint32_t)color radius:(CGFloat)radius dx:(CGFloat)dx dy:(CGFloat)dy
{
    isDirt = YES;
    _mShadowColor = color;
    _shadowDx = dx;
    _shadowDy = dy;
    _mShadowRadius = radius;
}


-(void)dirt
{
    if(isDirt){
        [self onDrit];
    }
    isDirt = NO;
}

- (void)setBounds:(CGRect)bounds
{
    CGRect temp = self.bounds;
    [super setBounds:bounds];
    if(!CGSizeEqualToSize(temp.size, bounds.size)){
        [self dirt];
    }
}

/* borderWidth */
- (void)setBorderLeftWidth:(CGFloat)borderLeftWidth
{
    _borderLeftWidth = borderLeftWidth;
    isDirt = YES;
}

- (void)setBorderTopWidth:(CGFloat)borderTopWidth
{
    _borderTopWidth = borderTopWidth;
    isDirt = YES;
}

- (void)setBorderRightWidth:(CGFloat)borderRightWidth
{
    _borderRightWidth = borderRightWidth;
    isDirt = YES;
}

- (void)setBorderBottomWidth:(CGFloat)borderBottomWidth
{
    _borderBottomWidth = borderBottomWidth;
    isDirt = YES;
}

- (void)setBgColor:(uint32_t)bgColor
{
    _bgColor = bgColor;
    isDirt = YES;
}

- (void)setBorderStyle:(BorderStyle)borderStyle
{
    _borderStyle = borderStyle;
    isDirt = YES;
}

/* borderRadius */
- (void)setBorderTopLeftRadius:(CGFloat)borderTopLeftRadius
{
    _borderTopLeftRadius = borderTopLeftRadius;
    isDirt = YES;
}

- (void)setBorderTopRightRadius:(CGFloat)borderTopRightRadius
{
    _borderTopRightRadius = borderTopRightRadius;
    isDirt = YES;
}

- (void)setBorderBottomRightRadius:(CGFloat)borderBottomRightRadius
{
    _borderBottomRightRadius = borderBottomRightRadius;
    isDirt = YES;
}

- (void)setBorderBottomLeftRadius:(CGFloat)borderBottomLeftRadius
{
    _borderBottomLeftRadius = borderBottomLeftRadius;
    isDirt = YES;
}


/* borderColor */
- (void)setBorderLeftColor:(uint32_t)borderLeftColor
{
    _borderLeftColor = borderLeftColor;
    isDirt = YES;
}

- (void)setBorderTopColor:(uint32_t)borderTopColor
{
    _borderTopColor= borderTopColor;
    isDirt = YES;
}

- (void)setBorderRightColor:(uint32_t)borderRightColor
{
    _borderRightColor = borderRightColor;
    isDirt = YES;
}

- (void)setBorderBottomColor:(uint32_t)borderBottomColor
{
    _borderBottomColor = borderBottomColor;
    isDirt = YES;
}

/* shadow */
- (void)setShadowDx:(CGFloat)shadowDx
{
    _shadowDx = shadowDx;
    isDirt = YES;
}

- (void)setShadowDy:(CGFloat)shadowDy
{
    _shadowDy = shadowDy;
    isDirt = YES;
}

- (void)setMShadowColor:(uint32_t)mShadowColor
{
    _mShadowColor = mShadowColor;
    isDirt = YES;
}

- (void)setMShadowRadius:(uint32_t)mShadowRadius
{
    _mShadowRadius = mShadowRadius;
     isDirt = YES;
}


-(void)onDrit
{
    self.backgroundColor  = [UIColor blueColor].CGColor;
}

@end
