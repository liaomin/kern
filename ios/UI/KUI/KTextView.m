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

- (void)drawTextInRect:(CGRect)rect
{
    [super drawTextInRect:UIEdgeInsetsInsetRect(rect, _pddding)];
}

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode
{
    if(widthMode == MeasureModeExactly && heightMode == MeasureModeExactly){
        return CGSizeMake(width, height);
    }
    NSMutableAttributedString* attr = [self getNSAttributedString];
    CGFloat paddingWidth = _pddding.left + _pddding.right;
    CGFloat paddingHeight = _pddding.top + _pddding.bottom;
    CGFloat maxWidth = width - paddingWidth;
    CGFloat maxHeight = height - paddingHeight;
    if(widthMode == MeasureModeUnspecified){
        maxWidth = CGFLOAT_MAX;
    }
    if(heightMode == MeasureModeUnspecified){
        maxHeight = CGFLOAT_MAX;
    }
    
    NSMutableDictionary* attrubutes = [[NSMutableDictionary alloc] initWithCapacity:1];
    UIFont* font = self.font;
    [attrubutes setObject:font forKey:NSFontAttributeName];
    if(_lineHeight > font.lineHeight){
        NSMutableParagraphStyle *style  = [[NSMutableParagraphStyle alloc] init];
        style.minimumLineHeight = _lineHeight;
        style.maximumLineHeight = _lineHeight;
        [attrubutes setObject:style forKey:NSParagraphStyleAttributeName];
    }
    CGRect rect =  [self.text boundingRectWithSize:CGSizeMake(maxWidth, maxHeight) options:NSStringDrawingTruncatesLastVisibleLine | NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading attributes:attrubutes context:nil];
    
    CGFloat measureWidth = rect.size.width;
    CGFloat measureHeight = rect.size.height;
    if(widthMode == MeasureModeExactly){
        measureWidth = maxWidth;
    }else if (widthMode == MeasureModeAtMost){
        measureWidth = MIN(measureWidth, maxWidth) + paddingWidth;
    }
    if(heightMode == MeasureModeExactly){
        measureHeight = maxHeight;
    }else if (heightMode == MeasureModeAtMost){
        measureHeight = MIN(measureHeight, maxHeight) + paddingHeight;
    }
    return CGSizeMake(measureWidth, measureHeight);
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
        NSRange range = NSMakeRange(0, attr.length);
        [attr addAttribute:NSFontAttributeName value:self.font range:range];
        self.attributedText = attr;
        [self updateLetterSpacing];
        [self updateLineHeight];
        [self updateDecorationLine];
    }else{
        self.attributedText = nil;
    }
}

-(nonnull NSMutableAttributedString*)getNSAttributedString
{
    NSAttributedString* t = self.attributedText;
    if(t && [t isKindOfClass:[NSMutableAttributedString class]]){
        NSMutableAttributedString* attr = (NSMutableAttributedString*)t;
        return attr;
    }
    NSMutableAttributedString* att = [[NSMutableAttributedString alloc] initWithString:self.text?:@""];
    self.attributedText = att;
    return  att;
}

- (void)setLineHeight:(CGFloat)lineHeight
{
    _lineHeight = lineHeight;
    [self updateLineHeight];
}

-(void)updateLineHeight
{
    NSMutableAttributedString* attr = [self getNSAttributedString];
    NSRange range = NSMakeRange(0, attr.length);
    [attr removeAttribute:NSBaselineOffsetAttributeName range:range];
    [attr removeAttribute:NSParagraphStyleAttributeName range:range];
    if(_lineHeight > self.font.lineHeight){
        NSMutableParagraphStyle *style  = [[NSMutableParagraphStyle alloc] init];
        style.minimumLineHeight = _lineHeight;
        style.maximumLineHeight = _lineHeight;
        CGFloat baseLineOffset =  (_lineHeight - self.font.lineHeight) / 4.0;
        [attr addAttribute:NSBaselineOffsetAttributeName value:@(baseLineOffset) range:range];
//        [attr addAttribute:NSBaselineOffsetAttributeName value:@(10) range:range];
        [attr addAttribute:NSParagraphStyleAttributeName value:style range:range];
    }
    [self setNeedsDisplay];
}

- (void)setLetterSpacing:(CGFloat)letterSpacing
{
    _letterSpacing = letterSpacing;
    [self updateLetterSpacing];
}

-(void)updateLetterSpacing
{
    NSMutableAttributedString* attr = [self getNSAttributedString];
    [attr addAttribute:NSKernAttributeName value:@(self.font.pointSize*_letterSpacing) range:NSMakeRange(0, attr.length)];
    [self setNeedsDisplay];
}

- (void)setDecorationLine:(TextDecorationLine)decorationLine
{
    _decorationLine = decorationLine;
    [self updateDecorationLine];
}

-(void)updateDecorationLine
{
    NSMutableAttributedString* attr = [self getNSAttributedString];
    NSRange range = NSMakeRange(0, attr.length);
    if(_decorationLine & TextDecorationLineUnderLine){
        [attr addAttribute:NSUnderlineStyleAttributeName value:@(NSUnderlineStyleSingle) range:range];
    }else{
        [attr removeAttribute:NSUnderlineStyleAttributeName range:range];
    }
    if(_decorationLine & TextDecorationLineLineThrough){
        [attr addAttribute:NSStrikethroughStyleAttributeName value:@(NSUnderlineStyleSingle) range:NSMakeRange(0, attr.length)];
    }else{
        [attr removeAttribute:NSStrikethroughStyleAttributeName range:range];
    }
    [self setNeedsDisplay];
}

- (void)setFont:(UIFont *)font
{
    if(font){
        NSMutableAttributedString* attr = [self getNSAttributedString];
        NSRange range = NSMakeRange(0, attr.length);
        [attr addAttribute:NSFontAttributeName value:font range:range];
        [super setFont:font];
        [self updateLineHeight];
    }
}

- (void)setFontSize:(CGFloat)fontSize
{
    _fontSize = fontSize;
    UIFont* font = self.font;
    [self setFont:[font fontWithSize:fontSize]];
}

- (void)setAlignment:(TextAlignment)alignment
{
    _alignment = alignment;
    if (alignment == TextAlignmentLeft) {
        [self setTextAlignment:NSTextAlignmentLeft];
    } else if(alignment == TextAlignmentCenter){
        [self setTextAlignment:NSTextAlignmentCenter];
    }else{
         [self setTextAlignment:NSTextAlignmentRight];
    }
    
}

- (void)setEllipsizeMode:(TextEllipsizeMode)ellipsizeMode
{
    _ellipsizeMode = ellipsizeMode;
    if (ellipsizeMode == TextEllipsizeModeHead) {
        [self setLineBreakMode:NSLineBreakByTruncatingHead];
    } else if(ellipsizeMode == TextEllipsizeModeMiddle){
       [self setLineBreakMode:NSLineBreakByTruncatingMiddle];
    }else{
        [self setLineBreakMode:NSLineBreakByTruncatingTail];
    }
}

- (void)setFontStye:(FontStyle)style
{
    [self setSyle:style forFont:self.font];
}

- (void)setFontStye:(FontStyle)style widthName:(NSString*)fontName
{
    UIFont* font = [UIFont fontWithName:fontName size:self.font.pointSize];
    if(font){
        [self setSyle:style forFont:font];
    }
}

-(void)setSyle:(FontStyle)style forFont:(UIFont*)font
{
    if(font){
        UIFontDescriptorSymbolicTraits traits = 0;
        if(style & FontStyleBold){
            traits |= UIFontDescriptorTraitBold;
        }
        if(style & FontStyleItalic){
            traits |= UIFontDescriptorTraitItalic;
        }
        if(traits > 0){
            UIFontDescriptor* fontD = [font.fontDescriptor
                        fontDescriptorWithSymbolicTraits:traits];
            font = [UIFont fontWithDescriptor:fontD size:0];
            if(font){
                 [self setFont:font];
            }
        }else{
            [self setFont:font];
        }
    }
}

- (void)dealloc
{
    [self onDestruct];
}

- (void)onDestruct
{
    
}


@end
