//
//  KTextView.m
//  KUI
//
//  Created by 廖敏 on 4/20/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KTextView.h"


@interface KTextView()
{
    UIEdgeInsets _pddding;
}
@end

@implementation KTextView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (void)drawTextInRect:(CGRect)rect
{
    [super drawTextInRect:UIEdgeInsetsInsetRect(rect, _pddding)];
}

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode
{
    return CGSizeZero;
}

- (void)setPadding:(UIEdgeInsets)padding
{
    _pddding = padding;
}

- (UIEdgeInsets)getPadding
{
    return _pddding;
}

- (void)setText:(NSString *)text
{
    NSAttributedString* attr = self.attributedText;
    if(attr && [attr.string isEqualToString:text]){
        return;
    }
    [self toNSAttributedString:text];
}

-(void)toNSAttributedString:(NSString*)text
{
    if(text){
        NSMutableAttributedString* attr = [[NSMutableAttributedString alloc] initWithString:text];
        self.attributedText = attr;
        [self updateLetterSpacing];
        [self updateLineHeight];
        [self updateDecorationLine];
    }else{
        self.attributedText = nil;
    }
}

-(nullable NSMutableAttributedString*)getNSAttributedString
{
    NSAttributedString* t = self.attributedText;
    if(t && [t isKindOfClass:[NSMutableAttributedString class]]){
        NSMutableAttributedString* attr = (NSMutableAttributedString*)t;
        return attr;
    }
    return nil;
}

- (void)setLineHeight:(CGFloat)lineHeight
{
    _lineHeight = lineHeight;
    [self updateLineHeight];
}

-(void)updateLineHeight
{
    NSAttributedString* t = self.attributedText;
    if(t && [t isKindOfClass:[NSMutableAttributedString class]]){
        NSMutableAttributedString* attr = (NSMutableAttributedString*)t;
        CGFloat baseLineOffset =  (_lineHeight - self.font.lineHeight) / 2.0;
        [attr addAttribute:NSBaselineOffsetAttributeName value:[NSNumber numberWithFloat:baseLineOffset] range:NSMakeRange(0, attr.length)];
        [self setNeedsDisplay];
    }
}

- (void)setLetterSpacing:(CGFloat)letterSpacing
{
    _letterSpacing = letterSpacing;
    [self updateLetterSpacing];
}

-(void)updateLetterSpacing
{
    NSAttributedString* t = self.attributedText;
    if(t && [t isKindOfClass:[NSMutableAttributedString class]]){
        NSMutableAttributedString* attr = (NSMutableAttributedString*)t;
        [attr addAttribute:NSKernAttributeName value:[NSNumber numberWithFloat:self.font.pointSize*_letterSpacing] range:NSMakeRange(0, attr.length)];
        [self setNeedsDisplay];
    }
}

- (void)setDecorationLine:(TextDecorationLine)decorationLine
{
    _decorationLine = decorationLine;
    [self updateDecorationLine];
}

-(void)updateDecorationLine
{
    NSAttributedString* t = self.attributedText;
    if(t && [t isKindOfClass:[NSMutableAttributedString class]]){
        NSMutableAttributedString* attr = (NSMutableAttributedString*)t;
        NSRange range = NSMakeRange(0, attr.length);
        if(_decorationLine & TextDecorationLineUnderLine){
            [attr addAttribute:NSUnderlineStyleAttributeName value:[NSNumber numberWithInt:NSUnderlineStyleSingle] range:range];
        }else{
            [attr removeAttribute:NSUnderlineStyleAttributeName range:range];
        }
        if(_decorationLine & TextDecorationLineLineThrough){
            [attr addAttribute:NSStrikethroughStyleAttributeName value:[NSNumber numberWithInt:NSUnderlineStyleSingle] range:NSMakeRange(0, attr.length)];
        }else{
            [attr removeAttribute:NSStrikethroughStyleAttributeName range:range];
        }
        [self setNeedsDisplay];
    }
}

- (void)setFont:(UIFont *)font
{
    NSMutableAttributedString* attr = [self getNSAttributedString];
    if(attr){
        NSRange range = NSMakeRange(0, attr.length);
        [attr addAttribute:NSFontAttributeName value:font range:range];
    }
    [super setFont:font];
}

@end
