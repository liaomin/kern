//
//  KImageView.m
//  KUI
//
//  Created by 廖敏 on 4/19/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "KImageView.h"

@interface KImageView()
{
    CALayer* _imagelayer;
    UIImage* _imageRef;
}
@end

@implementation KImageView

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

-(CGSize)measure:(CGFloat)width widthMode:(MeasureMode)widthMode height:(CGFloat)height heightMode:(MeasureMode)heightMode
{
    CGFloat w = 0;
    if(widthMode == MeasureModeExactly){
        w = width;
    }
    CGFloat h = 0;
    if(heightMode == MeasureModeExactly){
        h = height;
    }
    if(widthMode == MeasureModeExactly && heightMode == MeasureModeExactly){
        return CGSizeMake(w, h);
    }
    UIEdgeInsets _padding = [self getPadding];
    CGFloat paddingWidth = _padding.left + _padding.right;
    CGFloat paddingHeight = _padding.top + _padding.bottom;
    UIImage* image = _imageRef;
    if(image){
        BOOL resizeWidth = widthMode != MeasureModeExactly;
        BOOL resizeHeight = heightMode != MeasureModeExactly;
    
        CGSize imageSize = image.size;
        CGFloat iScale = image.scale;
        CGFloat mScale = [UIScreen mainScreen].scale;
        if(iScale != mScale){
            CGFloat scale = iScale / mScale;
            imageSize = CGSizeMake(imageSize.width * scale, imageSize.height * scale);
        }
        CGFloat desiredAspect = imageSize.width / imageSize.height;
        UIViewContentMode mode = self.contentMode;
        w = [self resolveAdjustedSize:width imageSize:imageSize.width + paddingWidth mode:widthMode];
        h = [self resolveAdjustedSize:width imageSize:imageSize.height + paddingHeight mode:heightMode];
        if(mode != UIViewContentModeScaleToFill){
            //除了这个模式会拉伸图片，其他模式都是保持原有宽高比
            CGFloat actualAspect = (w - paddingWidth) / (h - paddingHeight);
            if(ABS(actualAspect - desiredAspect) > 0.0000001){
                BOOL done = NO;
                if(resizeWidth){
                    CGFloat newWidth = desiredAspect * (h - paddingHeight) + paddingWidth;
                    if( !resizeHeight ){
                        w = [self resolveAdjustedSize:width imageSize:newWidth mode:widthMode];
                    }
                    if(newWidth <= w){
                        w = newWidth;
                        done = YES;
                    }
                }
                
                if( !done && resizeHeight){
                    CGFloat newHeight = (w - paddingWidth) / desiredAspect + paddingHeight;
                    if( !resizeWidth ){
                        h = [self resolveAdjustedSize:(CGFloat)height imageSize:newHeight  mode:heightMode];
                    }
                    if( newHeight <= h ){
                        h = newHeight;
                    }
                }
            }
        }
    
    }
    return CGSizeMake(w, h);
}

-(CGFloat)resolveAdjustedSize:(CGFloat)measureSize imageSize:(CGFloat)imageSize mode:(MeasureMode)mode
{
    switch (mode) {
        case MeasureModeAtMost:
            return MIN(measureSize, imageSize);
            break;
        case MeasureModeExactly:
           return measureSize;
           break;
        default:
            break;
    }
    return imageSize;
}

- (void)setPadding:(UIEdgeInsets)padding
{
    [super setPadding:padding];
    if(!_imagelayer && !UIEdgeInsetsEqualToEdgeInsets(UIEdgeInsetsZero, padding)){
        _imagelayer = [[CALayer alloc] init];
        self.image = self.image;
        [self updateContentMode];
        [self.layer addSublayer:_imagelayer];
    }
}

- (void)layoutSubviews
{
    [super layoutSubviews];
    [self addOrRemoveBgLayer];
    if(_imagelayer){
        CGRect f = self.frame;
        UIEdgeInsets _padding = [self getPadding];
        if(!UIEdgeInsetsEqualToEdgeInsets(UIEdgeInsetsZero, _padding) && _imagelayer){
            CGFloat w = f.size.width - _padding.right - _padding.left;
            CGFloat h = f.size.height - _padding.top - _padding.bottom;
            _imagelayer.frame = CGRectMake(_padding.left, _padding.top, w, h);
        }
    }
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.frame = self.frame;
        [self updateMask];
    }
}

- (void)setClipsToBounds:(BOOL)clipsToBounds
{
    [super setClipsToBounds:clipsToBounds];
    [self updateMask];
}

- (void)setImage:(UIImage *)image
{
    if(_imagelayer){
        if(image){
            _imagelayer.contents = (__bridge id)image.CGImage;
            _imagelayer.contentsScale = image.scale;
        }else{
            _imagelayer.contents = nil;
            _imagelayer.contentsScale = 1;
        }
        [super setImage:nil];
    }else{
        [super setImage:image];
    }
    _imageRef = image;
}

-(UIImage*)getImage
{
    return _imageRef;
}

-(void)dealloc
{
    if(_imagelayer){
        [_imagelayer removeFromSuperlayer];
        _imagelayer = nil;
    }
    _imageRef = nil;
    [self onDestruct];
}

-(void)onDestruct
{
}

- (void)setContentMode:(UIViewContentMode)contentMode
{
    [super setContentMode:contentMode];
    [self updateContentMode];
}

-(void)updateContentMode
{
    if(_imagelayer){
        _imagelayer.contentsGravity = self.layer.contentsGravity;
    }
}


- (void)willMoveToSuperview:(UIView *)newSuperview
{
    [super willMoveToSuperview:newSuperview];
}

- (void)willMoveToWindow:(UIWindow *)newWindow
{
    [super willMoveToWindow:newWindow];
}

- (void)didMoveToSuperview{
    [super didMoveToSuperview];
    [self addOrRemoveBgLayer];
}

- (void)setBackgroundColor:(UIColor *)backgroundColor
{
    BackgroundLayer* bgLayer = [self getBackgroundLayer];
    if(bgLayer){
        bgLayer.bgColor = [BackgroundLayer uIColor2Int:backgroundColor];
    }else{
        [super setBackgroundColor:backgroundColor];
    }
}

@end
