//
//  KShowText.h
//  KUI
//
//  Created by 廖敏 on 4/21/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "MeasureNode.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, TextDecorationLine) {
    TextDecorationLineNone = 0,
    TextDecorationLineUnderLine = 1,
    TextDecorationLineLineThrough = 2,
    TextDecorationLineUnderLineThrough = 3,
};

typedef NS_ENUM(NSUInteger, TextAlignment) {
    TextAlignmentLeft = 1,
    TextAlignmentCenter = 2,
    TextAlignmentRight = 3,
};

@interface KShowText : NSObject

@property (null_resettable,nonatomic,copy) NSString* text;

@property (nonatomic,assign) CGFloat fontSize;

@property (nonatomic,weak) UIFont* font;

@property (nonatomic,assign) BOOL bold;

@property (nonatomic,assign) CGFloat lineHeight;

@property (nonatomic,assign) CGFloat letterSpacing;

@property (nonatomic,assign) TextDecorationLine decorationLine;

@property (nonatomic,assign) TextAlignment alignment;

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode;


@end

NS_ASSUME_NONNULL_END
