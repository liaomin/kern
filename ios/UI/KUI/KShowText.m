//
//  KShowText.m
//  KUI
//
//  Created by 廖敏 on 4/21/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KShowText.h"

@interface KShowText()
@property (nonatomic,strong) NSMutableAttributedString* attr;
@end

@implementation KShowText

- (void)setText:(NSString *)text
{
    if(_attr && [[_attr string] isEqualToString:text]){
        return;
    }
    if(text){
        self.attr = [[NSMutableAttributedString alloc] initWithString:text];
    }else{
        _attr = nil;
    }
}

-(NSString*)text
{
    if(_attr){
        return [_attr string];
    }
    return @"";
}

- (CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode
{
    if(_attr){
//        UIFont *d = [UIFont fontWithDescriptor:<#(nonnull UIFontDescriptor *)#> size:<#(CGFloat)#>]
    }
    return CGSizeZero;
}

@end

