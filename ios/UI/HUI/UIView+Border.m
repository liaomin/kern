//
//  UIView+Border.m
//  HUI
//
//  Created by 廖敏 on 2/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "UIView+Border.h"
#import <objc/runtime.h>

@implementation UIView (Border)

#define addFloatPropy(name) \
        - (void)setM##name:(CGFloat)temp \
        { \
            objc_setAssociatedObject(self, #name, [NSNumber numberWithFloat:temp], OBJC_ASSOCIATION_COPY);\
        }\
        -(CGFloat)m##name  \
        {\
            NSNumber* temp = objc_getAssociatedObject(self, #name); \
            if(temp){ \
                return [temp floatValue]; \
            } \
            return 0.f; \
        }


-(UIView*)setBorderWidth:(CGFloat)width
{
    return [self setBorderWidth:width top:width right:width bottom:width];
}

-(UIView*)setBorderWidth:(CGFloat)leftWidth top:(CGFloat)topWidth right:(CGFloat)rightWidth bottom:(CGFloat)bottomWidth{
    CGFloat d = self.mLeftBorderWidth;
    self.mLeftBorderWidth = leftWidth;
    d = self.mLeftBorderWidth;
    self.mTopBorderWidth = topWidth;
    self.mRightBorderWidth = rightWidth;
    self.mBottomBorderWidth = bottomWidth;
    return [self needsUpdateBorder];
}

-(UIView*)needsUpdateBorder{
    CALayer* borderLayer = [[CALayer alloc] init];
    [self.layer addSublayer:borderLayer];
    return self;
}

addFloatPropy(LeftBorderWidth)
addFloatPropy(TopBorderWidth)
addFloatPropy(RightBorderWidth)
addFloatPropy(BottomBorderWidth)

@end
