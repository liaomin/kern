//
//  BackgroundLayer.h
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, BorderStyle) {
    BorderStyleSolid = 1,
    BorderStyleDotted = 2,
    BorderStyleDashed = 3,
};

@interface BackgroundLayer : CAShapeLayer

-(instancetype)initWithForgroundLayer:(id)layer;

+(uint32_t)uIColor2Int:(UIColor*)color;

+(UIColor*)int2UIColor:(uint32_t)color;

-(void)setAllBorderWidth:(CGFloat)left top:(CGFloat)top right:(CGFloat)right bootom:(CGFloat)bottom;

-(void)setAllBorderRadius:(CGFloat)topLeftRadius topRightRadius:(CGFloat)topRightRadius bottomLeftRadius:(CGFloat)bottomLeftRadius bottomRightRadius:(CGFloat)bottomRightRadius;

-(void)setAllShadow:(uint32_t)color radius:(CGFloat)radius dx:(CGFloat)dx dy:(CGFloat)dy;

@property (nonatomic,weak,readonly) CALayer* forgroundLayer;

/* borderWidth */
@property (nonatomic,assign) CGFloat borderLeftWidth;

@property (nonatomic,assign) CGFloat borderTopWidth;

@property (nonatomic,assign) CGFloat borderRightWidth;

@property (nonatomic,assign) CGFloat borderBottomWidth;

@property (nonatomic,assign) uint32_t bgColor;

@property (nonatomic,assign) BorderStyle borderStyle;

/* borderRadius */
@property (nonatomic,assign) CGFloat borderTopLeftRadius;

@property (nonatomic,assign) CGFloat borderTopRightRadius;

@property (nonatomic,assign) CGFloat borderBottomRightRadius;

@property (nonatomic,assign) CGFloat borderBottomLeftRadius;

/* borderColor */
@property (nonatomic,assign) uint32_t borderLeftColor;

@property (nonatomic,assign) uint32_t borderTopColor;

@property (nonatomic,assign) uint32_t borderRightColor;

@property (nonatomic,assign) uint32_t borderBottomColor;

/* shadow */
@property (nonatomic,assign) CGFloat shadowDx;

@property (nonatomic,assign) CGFloat shadowDy;

@property (nonatomic,assign) uint32_t mShadowColor;

@property (nonatomic,assign) uint32_t mShadowRadius;

-(UIBezierPath*)getInnerPath;

-(BOOL)haveBorderRadius;

-(BOOL)isSameBorderRadius;

@end

NS_ASSUME_NONNULL_END
