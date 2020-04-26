//
//  KTextView.h
//  KUI
//
//  Created by 廖敏 on 4/20/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeasureNode.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, TextEllipsizeMode) {
    TextEllipsizeModeHead = 1,
    TextEllipsizeModeMiddle = 2,
    TextEllipsizeModeTail = 4,
};

typedef NS_ENUM(NSUInteger, TextDecorationLine) {
    TextDecorationLineNone = 0,
    TextDecorationLineUnderLine = 1,
    TextDecorationLineLineThrough = 2,
    TextDecorationLineUnderLineThrough = 3,
};

typedef NS_ENUM(NSUInteger, TextAlignment) {
    TextAlignmentLeft = 1,
    TextAlignmentCenter = 2,
    TextAlignmentRight = 4,
};


typedef NS_ENUM(NSUInteger, FontStyle) {
    FontStyleDefault = 0,
    FontStyleBold = 1,
    FontStyleItalic = 2,
    FontStyleBoldItalic = 3,
};

@interface KTextView : UILabel<MeasureNode>

@property (nonatomic,assign) CGFloat letterSpacing;

@property (nonatomic,assign) CGFloat lineHeight;

@property (nonatomic,assign) TextDecorationLine decorationLine;

@property (nonatomic,assign) CGFloat fontSize;

@property (nonatomic,assign) TextAlignment alignment;

@property (nonatomic,assign) TextEllipsizeMode ellipsizeMode;

-(void)setFontStye:(FontStyle)style;

-(void)setFontStye:(FontStyle)style widthName:(NSString*)fontName;

@end

NS_ASSUME_NONNULL_END
