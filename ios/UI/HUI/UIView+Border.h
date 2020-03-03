//
//  UIView+Border.h
//  HUI
//
//  Created by 廖敏 on 2/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface UIView (Border)

@property (nonatomic,assign) CGFloat mLeftBorderWidth;

@property (nonatomic,assign) CGFloat mTopBorderWidth;

@property (nonatomic,assign) CGFloat mRightBorderWidth;

@property (nonatomic,assign) CGFloat mBottomBorderWidth;

-(UIView*)setBorderWidth:(CGFloat)width;

-(UIView*)setBorderWidth:(CGFloat)leftWidth top:(CGFloat)topWidth right:(CGFloat)rightWidth bottom:(CGFloat)bottomWidth;

@end

NS_ASSUME_NONNULL_END
