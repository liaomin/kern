//
//  UIView+Border.m
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "UIView+Border.h"
#import <objc/runtime.h>

@implementation UIView (Border)


-(nullable BackgroundLayer*)getBackgroundLayer
{
    id obj = objc_getAssociatedObject(self,  "__BACKGROUN_LAYER_KEY__");
    if(obj){
        if([obj isKindOfClass:[BackgroundLayer class]]){
            return (BackgroundLayer*)obj;
        }else{
            objc_setAssociatedObject(self, "__BACKGROUN_LAYER_KEY__", NULL, OBJC_ASSOCIATION_ASSIGN);
        }
    }
    
    return NULL;
}

-(BackgroundLayer*)getOrCreateBackgroundLayer
{
    BackgroundLayer* bglayer = [self getBackgroundLayer];
    if(bglayer == NULL){
        bglayer = [[BackgroundLayer alloc] initWithForgroundLayer:self.layer];
        bglayer.bgColor = [self getBackgroundColorInt];
        objc_setAssociatedObject(self, "__BACKGROUN_LAYER_KEY__", bglayer, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    }
    return bglayer;
}

-(void)setBackgroundColorInt:(uint32_t)color
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.bgColor = color;
        self.backgroundColor = NULL;
    }else{
        [self setBackgroundColor:[BackgroundLayer int2UIColor:color]];
    }
}

-(uint32_t)getBackgroundColorInt
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
       return bgLayer.bgColor;
    }else{
        UIColor* bg = self.backgroundColor;
        if(bg){
            return [BackgroundLayer uIColor2Int:bg];
        }
    }
    return 0;
}

-(void)setBorderColor:(uint32_t)leftColor top:(uint32_t)topColor right:(uint32_t)rightColor bootom:(uint32_t)bottomColor
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    bgLayer.borderLeftColor = leftColor;
    bgLayer.borderTopColor = topColor;
    bgLayer.borderRightColor = rightColor;
    bgLayer.borderBottomColor = bottomColor;
}

-(void)setBorderWidth:(CGFloat)borderWidth
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    [bgLayer setAllBorderWidth:borderWidth top:borderWidth right:borderWidth bootom:borderWidth];
}

-(void)setBorderStyle:(BorderStyle)style
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    bgLayer.borderStyle = style;
}

-(void)setBorderWidth:(CGFloat)left top:(CGFloat)top right:(CGFloat)right bootom:(CGFloat)bottom
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    [bgLayer setAllBorderWidth:left top:top right:right bootom:bottom];
}

-(CGFloat)getLeftBorderWidth;
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderLeftWidth;
    }
    return 0;
}

-(CGFloat)getTopBorderWidth
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderTopWidth;
    }
    return 0;
}

-(CGFloat)getRightBorderWidth
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderRightWidth;
    }
    return 0;
}

-(CGFloat)getBottomBorderWidth
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderBottomWidth;
    }
    return 0;
}

-(void)setBorderRadius:(CGFloat)topLeftRadius topRightRadius:(CGFloat)topRightRadius bottomLeftRadius:(CGFloat)bottomLeftRadius bottomRightRadius:(CGFloat)bottomRightRadius;
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    [bgLayer setAllBorderRadius:topLeftRadius topRightRadius:topRightRadius bottomLeftRadius:bottomLeftRadius bottomRightRadius:bottomRightRadius];
}

-(CGFloat)getTopLeftBorderRadius
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderTopLeftRadius;
    }
    return 0;
}

-(CGFloat)getTopRightBorderRadius
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderTopLeftRadius;
    }
    return 0;
}

-(CGFloat)getBottomLeftBorderRadius
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderBottomLeftRadius;
    }
    return 0;
}

-(CGFloat)getBottomRightBorderRadius
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.borderBottomRightRadius;
    }
    return 0;
}

-(void)setShadow:(uint32_t)color radius:(CGFloat)radius dx:(CGFloat)dx dy:(CGFloat)dy
{
    BackgroundLayer* bgLayer = [self getOrCreateBackgroundLayer];
    [bgLayer setAllShadow:color radius:radius dx:dx dy:dy];
}

-(uint32_t)getShadowColor
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.mShadowColor;
    }
    return 0;
}

-(CGFloat)getShadowRadius
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.mShadowRadius;
    }
    return 0;
}

-(CGFloat)getShadowOffsetX
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.shadowDx;
    }
    return 0;
}

-(CGFloat)getShadowOffsetY
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        return bgLayer.shadowDy;
    }
    return 0;
}

- (void)setPadding:(UIEdgeInsets)padding
{
     objc_setAssociatedObject(self, "__UIVIEW_PADDING_KEY__", [NSValue valueWithUIEdgeInsets:padding], OBJC_ASSOCIATION_RETAIN);
}

- (UIEdgeInsets)getPadding
{
    NSValue* v = objc_getAssociatedObject(self, "__UIVIEW_PADDING_KEY__");
    if(v){
        return [v UIEdgeInsetsValue];
    }
    return UIEdgeInsetsZero;
}

-(void)addOrRemoveBgLayer
{
    UIView* superView = self.superview;
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        if(superView){
            if(!bgLayer.superlayer){
               [superView.layer insertSublayer:bgLayer below:self.layer];
            }
        }else{
             [bgLayer removeFromSuperlayer];
        }
    }
}

-(void)updateMask
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        [self.layer setMask:nil];
        if(([bgLayer haveBorderRadius] || [bgLayer haveBorderWidth])){
            if([bgLayer isSameBorderRadius] && ![bgLayer haveBorderWidth] && [bgLayer isSameBorderWidth]){
                self.layer.cornerRadius = [bgLayer getInnerBorderRadius];
            }else{
                CAShapeLayer* shape = [[CAShapeLayer alloc] init];
                shape.backgroundColor = UIColor.redColor.CGColor;
                shape.path = [bgLayer getInnerPath].CGPath;
                [self.layer setMask:shape];
            }
        }
    }
}

@end
