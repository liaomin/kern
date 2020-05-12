//
//  MeasureNode.h
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BackgroundLayer.h"
#import "UIView+Border.h"
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, MeasureMode) {
    MeasureModeUnspecified = 1,
    MeasureModeExactly = 2,
    MeasureModeAtMost = 3,
};

@protocol MeasureNode <NSObject>

@required

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode;

- (nullable UIView *)hitTest:(CGPoint)point withEvent:(nullable UIEvent *)event;

-(void)layoutSubviews;

-(void)willMoveToSuperview:(UIView*)superview;

-(void)willMoveToWindow:(UIWindow*)window;

-(void)setPadding:(UIEdgeInsets)padding;

-(UIEdgeInsets)getPadding;

-(void)onDestruct;

@end

NS_ASSUME_NONNULL_END
