//
//  BackgroundLayer.h
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, BorderStyle) {
    BorderStyleSolid,
    BorderStyleDotted,
    BorderStyleDashed,
};

@interface BackgroundLayer : CALayer

/* borderWidth */
@property (nonatomic,assign) CGFloat borderLeftWidth;

@property (nonatomic,assign) CGFloat borderTopWidth;

@property (nonatomic,assign) CGFloat borderRightWidth;

@property (nonatomic,assign) CGFloat borderBottomWidth;

/* borderRadius */
@property (nonatomic,assign) CGFloat borderTopLeftRadius;

@property (nonatomic,assign) CGFloat borderTopRightRadius;

@property (nonatomic,assign) CGFloat borderBottomRightRadius;

@property (nonatomic,assign) CGFloat borderBottomLeftRadius;

/* borderColor */
@property (nonatomic,assign) int borderLeftColor;

@property (nonatomic,assign) int borderTopColor;

@property (nonatomic,assign) int borderRightColor;

@property (nonatomic,assign) int borderBottomColor;

/* shadow */
@property (nonatomic,assign) CGFloat shadowDx;

@property (nonatomic,assign) CGFloat shadowDy;

@property (nonatomic,assign) int mShadowColor;

@property (nonatomic,assign) int mShadowRadius;

@end

NS_ASSUME_NONNULL_END
