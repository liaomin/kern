//
//  KView.h
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeasureNode.h"

NS_ASSUME_NONNULL_BEGIN

@interface KView : UIView<MeasureNode>

@end

@interface UIEvent(GetPosition)

-(CGFloat)getLocationX:(UIView*)view pointerIndex:(int)pointerIndex;

-(CGFloat)getLocationY:(UIView*)view pointerIndex:(int)pointerIndex;

-(CGFloat)getLocationInWindowX:(int)pointerIndex;

-(CGFloat)getLocationInWindowY:(int)pointerIndex;

-(int)getPointerSize;

-(CGFloat)getLocationX:(UIView*)view;

-(CGFloat)getLocationY:(UIView*)view;

@end

NS_ASSUME_NONNULL_END
