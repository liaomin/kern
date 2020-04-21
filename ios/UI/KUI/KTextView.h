//
//  KTextView.h
//  KUI
//
//  Created by 廖敏 on 4/20/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeasureNode.h"
#import "KShowText.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, TextEllipsizeMode) {
    TextEllipsizeModeHead = 1,
    TextEllipsizeModeMiddle = 2,
    TextEllipsizeModeTail = 3,
};


@interface KTextView : UILabel<MeasureNode>

@property (nonatomic,assign) CGFloat letterSpacing;

@property (nonatomic,assign) CGFloat lineHeight;

@property (nonatomic,assign) TextDecorationLine decorationLine;



@end

NS_ASSUME_NONNULL_END
