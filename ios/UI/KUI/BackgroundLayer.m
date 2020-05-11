//
//  BackgroundLayer.m
//  KUI
//
//  Created by 廖敏 on 4/10/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "BackgroundLayer.h"

@interface BackgroundLayer()<CALayerDelegate>
{
    BOOL isDirt;
}
@end

@implementation BackgroundLayer

+(uint32_t)uIColor2Int:(UIColor*)color
{
    CGFloat r,g,b,a;
    [color getRed:&r green:&g blue:&b alpha:&a];
    return ((uint32_t)(a*255) << 24) | ((uint32_t)(r*255) << 16) | ((uint32_t)(g*255) << 8) | ((uint32_t)(b*255));
}

+(UIColor*)int2UIColor:(uint32_t)color
{
    return [UIColor colorWithRed:color >> 16 & 0x000000FF green:color >> 8 & 0x000000FF blue:color & 0x000000FF alpha:color >> 24];
}

- (instancetype)initWithForgroundLayer:(id)layer
{
    self = [super init];
    if (self) {
        isDirt = YES;
        self.needsDisplayOnBoundsChange = YES;
        self.delegate = self;
        _bgColor = _mShadowColor = _shadowDx = _shadowDy = _mShadowColor = 0;
        _borderStyle = BorderStyleSolid;
        _borderTopWidth = _borderBottomWidth = _borderRightWidth = _borderLeftWidth = 0;
        _borderTopRightRadius = _borderTopLeftRadius = _borderBottomLeftRadius = _borderTopRightRadius = 0;
    }
    _forgroundLayer = layer;
    return self;
}

- (void)setAllBorderRadius:(CGFloat)topLeftRadius topRightRadius:(CGFloat)topRightRadius bottomLeftRadius:(CGFloat)bottomLeftRadius bottomRightRadius:(CGFloat)bottomRightRadius
{
    isDirt = YES;
    _borderTopLeftRadius = topLeftRadius;
    _borderTopRightRadius = topRightRadius;
    _borderBottomLeftRadius = bottomLeftRadius;
    _borderBottomRightRadius = bottomRightRadius;
}


- (void)setAllBorderWidth:(CGFloat)left top:(CGFloat)top right:(CGFloat)right bootom:(CGFloat)bottom
{
    isDirt = YES;
    _borderLeftWidth = left;
    _borderTopWidth = top;
    _borderRightWidth = right;
    _borderBottomWidth = bottom;
}

- (void)setAllShadow:(uint32_t)color radius:(CGFloat)radius dx:(CGFloat)dx dy:(CGFloat)dy
{
    isDirt = YES;
    _mShadowColor = color;
    _shadowDx = dx;
    _shadowDy = dy;
    _mShadowRadius = radius;
}


-(void)dirt
{
    if(isDirt){
        [self onDrit];
    }
    isDirt = NO;
}

/* borderWidth */
- (void)setBorderLeftWidth:(CGFloat)borderLeftWidth
{
    _borderLeftWidth = borderLeftWidth;
    isDirt = YES;
}

- (void)setBorderTopWidth:(CGFloat)borderTopWidth
{
    _borderTopWidth = borderTopWidth;
    isDirt = YES;
}

- (void)setBorderRightWidth:(CGFloat)borderRightWidth
{
    _borderRightWidth = borderRightWidth;
    isDirt = YES;
}

- (void)setBorderBottomWidth:(CGFloat)borderBottomWidth
{
    _borderBottomWidth = borderBottomWidth;
    isDirt = YES;
}

- (void)setBgColor:(uint32_t)bgColor
{
    _bgColor = bgColor;
    isDirt = YES;
}

- (void)setBorderStyle:(BorderStyle)borderStyle
{
    _borderStyle = borderStyle;
    isDirt = YES;
}

/* borderRadius */
- (void)setBorderTopLeftRadius:(CGFloat)borderTopLeftRadius
{
    _borderTopLeftRadius = borderTopLeftRadius;
    isDirt = YES;
}

- (void)setBorderTopRightRadius:(CGFloat)borderTopRightRadius
{
    _borderTopRightRadius = borderTopRightRadius;
    isDirt = YES;
}

- (void)setBorderBottomRightRadius:(CGFloat)borderBottomRightRadius
{
    _borderBottomRightRadius = borderBottomRightRadius;
    isDirt = YES;
}

- (void)setBorderBottomLeftRadius:(CGFloat)borderBottomLeftRadius
{
    _borderBottomLeftRadius = borderBottomLeftRadius;
    isDirt = YES;
}


/* borderColor */
- (void)setBorderLeftColor:(uint32_t)borderLeftColor
{
    _borderLeftColor = borderLeftColor;
    isDirt = YES;
}

- (void)setBorderTopColor:(uint32_t)borderTopColor
{
    _borderTopColor= borderTopColor;
    isDirt = YES;
}

- (void)setBorderRightColor:(uint32_t)borderRightColor
{
    _borderRightColor = borderRightColor;
    isDirt = YES;
}

- (void)setBorderBottomColor:(uint32_t)borderBottomColor
{
    _borderBottomColor = borderBottomColor;
    isDirt = YES;
}

/* shadow */
- (void)setShadowDx:(CGFloat)shadowDx
{
    _shadowDx = shadowDx;
    isDirt = YES;
}

- (void)setShadowDy:(CGFloat)shadowDy
{
    _shadowDy = shadowDy;
    isDirt = YES;
}

- (void)setMShadowColor:(uint32_t)mShadowColor
{
    _mShadowColor = mShadowColor;
    isDirt = YES;
}

- (void)setMShadowRadius:(uint32_t)mShadowRadius
{
    _mShadowRadius = mShadowRadius;
    isDirt = YES;
}

- (void)setBounds:(CGRect)bounds
{
    [super setBounds:bounds];
    [self onDrit];
}

-(void)onDrit
{
    [self setNeedsDisplay];
}


-(UIBezierPath*)getOuterPath:(CGSize)size{
    CGFloat halfWidth = size.width / 2;
    CGFloat halfHeight = size.height / 2;
    CGFloat maxRadius = MIN(halfWidth,halfHeight);
    CGFloat borderLeftWidth = MIN(_borderLeftWidth,halfWidth);
    CGFloat borderTopWidth =  MIN(_borderTopWidth,halfHeight);
    CGFloat borderRightWidth = MIN(_borderRightWidth,halfWidth);
    CGFloat borderBottomWidth = MIN(_borderBottomWidth,halfHeight);
    CGFloat borderTopLeftRadius = MIN(_borderTopLeftRadius,maxRadius);
    CGFloat borderTopRightRadius = MIN(_borderTopRightRadius,maxRadius);
    CGFloat borderBottomRightRadius = MIN(_borderBottomRightRadius,maxRadius);
    CGFloat borderBottomLeftRadius = MIN(_borderBottomLeftRadius,maxRadius);
    return [self getOuterPath:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius];
}

- (UIBezierPath *)getInnerPath
{
    CGSize size = self.bounds.size;
    CGFloat halfWidth = size.width / 2;
    CGFloat halfHeight = size.height / 2;
    CGFloat maxRadius = MIN(halfWidth,halfHeight);
    CGFloat borderLeftWidth = MIN(_borderLeftWidth,halfWidth);
    CGFloat borderTopWidth =  MIN(_borderTopWidth,halfHeight);
    CGFloat borderRightWidth = MIN(_borderRightWidth,halfWidth);
    CGFloat borderBottomWidth = MIN(_borderBottomWidth,halfHeight);
    CGFloat borderTopLeftRadius = MIN(_borderTopLeftRadius,maxRadius);
    CGFloat borderTopRightRadius = MIN(_borderTopRightRadius,maxRadius);
    CGFloat borderBottomRightRadius = MIN(_borderBottomRightRadius,maxRadius);
    CGFloat borderBottomLeftRadius = MIN(_borderBottomLeftRadius,maxRadius);
    return [self getInnerPath:(CGSize)size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius];
}

-(UIBezierPath*)getOuterPath:(CGSize)size borderLeftWidth:(CGFloat)borderLeftWidth borderTopWidth:(CGFloat)borderTopWidth borderRightWidth:(CGFloat)borderRightWidth borderBottomWidth:(CGFloat)borderBottomWidth borderTopLeftRadius:(CGFloat)borderTopLeftRadius borderTopRightRadius:(CGFloat)borderTopRightRadius borderBottomRightRadius:(CGFloat)borderBottomRightRadius borderBottomLeftRadius:(CGFloat)borderBottomLeftRadius{
    UIBezierPath* path = [UIBezierPath bezierPath];
    
    [path moveToPoint:CGPointMake(0, borderTopLeftRadius)];
    [path addArcWithCenter:CGPointMake(borderTopLeftRadius,borderTopLeftRadius) radius:borderTopLeftRadius startAngle:M_PI endAngle:3*M_PI_2 clockwise:YES];
    
    [path addLineToPoint:CGPointMake(size.width - borderTopRightRadius,0)];
    [path addArcWithCenter:CGPointMake(size.width - borderTopRightRadius,borderTopRightRadius) radius:borderTopRightRadius startAngle:3*M_PI_2 endAngle:0 clockwise:YES];
    
    [path addLineToPoint:CGPointMake(size.width ,size.height - borderBottomRightRadius)];
    [path addArcWithCenter:CGPointMake(size.width - borderBottomRightRadius,size.height - borderBottomRightRadius) radius:borderBottomRightRadius startAngle:0 endAngle:M_PI_2 clockwise:YES];
   
    
    [path addLineToPoint:CGPointMake(borderBottomLeftRadius,size.height)];
    [path addArcWithCenter:CGPointMake(borderBottomLeftRadius,size.height - borderBottomLeftRadius) radius:borderBottomLeftRadius startAngle:M_PI_2 endAngle:M_PI clockwise:YES];

    [path closePath];
    
    return path;
}

-(UIBezierPath*)getInnerPath:(CGSize)size borderLeftWidth:(CGFloat)borderLeftWidth borderTopWidth:(CGFloat)borderTopWidth borderRightWidth:(CGFloat)borderRightWidth borderBottomWidth:(CGFloat)borderBottomWidth borderTopLeftRadius:(CGFloat)borderTopLeftRadius borderTopRightRadius:(CGFloat)borderTopRightRadius borderBottomRightRadius:(CGFloat)borderBottomRightRadius borderBottomLeftRadius:(CGFloat)borderBottomLeftRadius{
    UIBezierPath* path = [UIBezierPath bezierPath];
    CGFloat lineWidth = borderLeftWidth;
    CGFloat halfWidth = lineWidth;
    CGFloat r = MAX(0.0, borderTopLeftRadius - halfWidth);
    if(r > 0){
        [path moveToPoint:CGPointMake(halfWidth,borderTopLeftRadius)];
        [path addArcWithCenter:CGPointMake(borderTopLeftRadius,borderTopLeftRadius) radius:r startAngle:M_PI endAngle:3*M_PI_2 clockwise:YES];
    }else{
        [path moveToPoint:CGPointMake(halfWidth,halfWidth)];
    }
    r = MAX(0.0, borderTopRightRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(size.width - borderTopRightRadius,halfWidth)];
        [path addArcWithCenter:CGPointMake(size.width - borderTopRightRadius,borderTopRightRadius) radius:r startAngle:3*M_PI_2 endAngle:0 clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(size.width - halfWidth,halfWidth)];
    }
    r = MAX(0.0, borderBottomRightRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(size.width - halfWidth,size.height - borderBottomRightRadius)];
         [path addArcWithCenter:CGPointMake(size.width - borderBottomRightRadius,size.height - borderBottomRightRadius) radius:r startAngle:0.0 endAngle:M_PI_2 clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(size.width - halfWidth,size.height - halfWidth)];
    }
    r = MAX(0.0, borderBottomLeftRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(borderBottomLeftRadius,size.height - halfWidth)];
        [path addArcWithCenter:CGPointMake(borderBottomLeftRadius,size.height - borderBottomLeftRadius) radius:r startAngle:M_PI_2 endAngle:M_PI clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(halfWidth,size.height - halfWidth)];
    }
    [path closePath];
    return path;
}

-(UIBezierPath*)getDashedInnerPath:(CGSize)size borderLeftWidth:(CGFloat)borderLeftWidth borderTopWidth:(CGFloat)borderTopWidth borderRightWidth:(CGFloat)borderRightWidth borderBottomWidth:(CGFloat)borderBottomWidth borderTopLeftRadius:(CGFloat)borderTopLeftRadius borderTopRightRadius:(CGFloat)borderTopRightRadius borderBottomRightRadius:(CGFloat)borderBottomRightRadius borderBottomLeftRadius:(CGFloat)borderBottomLeftRadius{
    UIBezierPath* path = [UIBezierPath bezierPath];
    CGFloat lineWidth = borderLeftWidth;
    CGFloat halfWidth = lineWidth / 2;
    CGFloat r = MAX(0.0, borderTopLeftRadius - halfWidth);
    if(r > 0){
        [path moveToPoint:CGPointMake(halfWidth,borderTopLeftRadius)];
        [path addArcWithCenter:CGPointMake(borderTopLeftRadius,borderTopLeftRadius) radius:r startAngle:M_PI endAngle:3*M_PI_2 clockwise:YES];
    }else{
        [path moveToPoint:CGPointMake(halfWidth,halfWidth)];
    }
    r = MAX(0.0, borderTopRightRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(size.width - borderTopRightRadius,halfWidth)];
        [path addArcWithCenter:CGPointMake(size.width - borderTopRightRadius,borderTopRightRadius) radius:r startAngle:3*M_PI_2 endAngle:0 clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(size.width - halfWidth,halfWidth)];
    }
    r = MAX(0.0, borderBottomRightRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(size.width - halfWidth,size.height - borderBottomRightRadius)];
         [path addArcWithCenter:CGPointMake(size.width - borderBottomRightRadius,size.height - borderBottomRightRadius) radius:r startAngle:0.0 endAngle:M_PI_2 clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(size.width - halfWidth,size.height - halfWidth)];
    }
    r = MAX(0.0, borderBottomLeftRadius - halfWidth);
    if(r > 0){
        [path addLineToPoint:CGPointMake(borderBottomLeftRadius,size.height - halfWidth)];
        [path addArcWithCenter:CGPointMake(borderBottomLeftRadius,size.height - borderBottomLeftRadius) radius:r startAngle:M_PI_2 endAngle:M_PI clockwise:YES];
    }else{
        [path addLineToPoint:CGPointMake(halfWidth,size.height - halfWidth)];
    }
    [path closePath];
    return path;
}

-(UIImage*)toImage:(CGSize)size borderLeftWidth:(CGFloat)borderLeftWidth borderTopWidth:(CGFloat)borderTopWidth borderRightWidth:(CGFloat)borderRightWidth borderBottomWidth:(CGFloat)borderBottomWidth borderTopLeftRadius:(CGFloat)borderTopLeftRadius borderTopRightRadius:(CGFloat)borderTopRightRadius borderBottomRightRadius:(CGFloat)borderBottomRightRadius borderBottomLeftRadius:(CGFloat)borderBottomLeftRadius color:(CGColorRef)bgColor{
    UIGraphicsBeginImageContextWithOptions(size,NO,0);
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    UIBezierPath* path = [UIBezierPath bezierPath];
    CGFloat width = size.width;
    CGFloat height = size.height;
    CGFloat points[] = {MAX(borderTopLeftRadius,borderLeftWidth),borderTopWidth,
        width-borderRightWidth,borderTopWidth,
        width-borderRightWidth,borderTopWidth,
        width-borderRightWidth,height-borderBottomWidth,
        width-borderRightWidth,height-borderBottomWidth,
        borderLeftWidth,height-borderBottomWidth,
        borderLeftWidth,height-borderBottomWidth,
        borderLeftWidth,borderTopWidth};
    [path moveToPoint:CGPointMake(points[0],points[1])];
    if(borderTopRightRadius > 0){
        points[2] = width-MAX(borderRightWidth,borderTopRightRadius);
        points[5] = MAX(borderTopRightRadius,borderTopWidth);
        [path addLineToPoint:CGPointMake(points[2], points[3])];
        if(borderTopWidth == borderRightWidth){
            [path addArcWithCenter:CGPointMake(points[2],points[5]) radius:points[5] - points[3] startAngle:3*M_PI_2 endAngle:0 clockwise:YES];
        }else{
            [path addQuadCurveToPoint:CGPointMake(points[4],points[5]) controlPoint:CGPointMake(width-borderRightWidth,borderTopWidth)];
        }
    }else{
        [path addLineToPoint:CGPointMake(width-borderRightWidth,borderTopWidth)];
    }
    
    if(borderBottomRightRadius > 0){
        points[7] = height - MAX(borderBottomRightRadius,borderBottomWidth);
        points[8] = width - MAX(borderRightWidth,borderBottomRightRadius);
        [path addLineToPoint:CGPointMake(points[6],points[7])];
        if(borderRightWidth == borderBottomWidth){
            [path addArcWithCenter:CGPointMake(points[8],points[7]) radius:points[9] - points[7] startAngle:0 endAngle:M_PI_2 clockwise:YES];
        }else{
           [path addQuadCurveToPoint:CGPointMake(points[8],points[9]) controlPoint:CGPointMake(width-borderRightWidth,height - borderBottomWidth)];
        }
    }else{
        [path addLineToPoint:CGPointMake(width-borderRightWidth,height-borderBottomWidth)];
    }
    
    if(borderBottomLeftRadius > 0){
        points[10] = MAX(borderLeftWidth,borderBottomLeftRadius);
        points[13] = height - MAX(borderBottomLeftRadius,borderBottomWidth);
        [path addLineToPoint:CGPointMake(points[10],points[11])];
        if(borderBottomWidth == borderLeftWidth){
            [path addArcWithCenter:CGPointMake(points[10],points[13]) radius:points[11] - points[13] startAngle:M_PI_2 endAngle:M_PI clockwise:YES];
        }else{
           [path addQuadCurveToPoint:CGPointMake(points[12],points[13]) controlPoint:CGPointMake(borderLeftWidth,height - borderBottomWidth)];
        }
    }else{
        [path addLineToPoint:CGPointMake(borderLeftWidth,height-borderBottomWidth)];
    }
    
    if(borderTopLeftRadius > 0){
        points[15] = MAX(borderTopLeftRadius,borderTopWidth);
        [path addLineToPoint:CGPointMake(points[14],points[15])];
        if(borderTopWidth == borderLeftWidth){
            [path addArcWithCenter:CGPointMake(points[0],points[15]) radius:points[15] - points[1] startAngle:M_PI endAngle:3*M_PI_2 clockwise:YES];
        }else {
            [path addQuadCurveToPoint:CGPointMake(points[0],borderTopWidth) controlPoint:CGPointMake(borderLeftWidth, borderTopWidth)];
        }
    }else{
        [path addLineToPoint:CGPointMake(borderLeftWidth,borderTopWidth)];
    }
    [path closePath];
    UIBezierPath* innerPath = path;
    

    CGContextSetFillColorWithColor(ctx,bgColor);
    UIBezierPath* outerPath = [self getOuterPath:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius];
    CGContextAddPath(ctx, outerPath.CGPath);
    CGContextFillPath(ctx);
    
    CGContextAddPath(ctx, outerPath.CGPath);
    CGContextAddPath(ctx, innerPath.CGPath);
    CGContextEOClip(ctx);
    
    BOOL sameBorderColor = [self isSameBorderColor];
    if(sameBorderColor){
        CGContextSetFillColorWithColor(ctx,[BackgroundLayer int2UIColor:_borderLeftColor].CGColor);
        CGContextAddRect(ctx, CGRectMake(0.0,0.0,size.width,size.height));
        CGContextFillPath(ctx);
    }else{
        CGFloat topLeftX = points[0];
        CGFloat topLeftY = borderTopWidth;
        CGFloat topRightX = points[2];
        CGFloat topRightY = borderTopWidth;
        CGFloat bottomRightX =  points[8];
        CGFloat bottomRightY =  height - borderBottomWidth;
        CGFloat bottomLeftX =  points[10];
        CGFloat bottomLeftY =  height - borderBottomWidth;
        if(borderLeftWidth > 0){
            CGFloat k = topLeftX / borderLeftWidth;
            topLeftY =  borderTopWidth *k;
            bottomLeftY =  height - borderBottomWidth * k;
        }
        if(borderRightWidth > 0){
            CGFloat k =  (width - topRightX) /  borderRightWidth;
            topRightY =  borderTopWidth * k;
            bottomRightY =  height - borderBottomWidth * k;
        }
        uint32_t currentColor = _borderLeftColor;
        //left
        CGContextSetFillColorWithColor(ctx,[BackgroundLayer int2UIColor:currentColor].CGColor);
        if(_borderLeftWidth > 0 && currentColor >> 24 != 0){
            CGPoint lines[] = {(CGPoint){0,0},(CGPoint){topLeftX,topLeftY},(CGPoint){bottomLeftX,bottomLeftY},(CGPoint){0,size.height}};
            CGContextAddLines(ctx,lines,4);
        }
        //top
        if(currentColor != _borderTopColor){
            CGContextFillPath(ctx);
            currentColor = _borderTopColor;
            CGContextSetFillColorWithColor(ctx,[BackgroundLayer int2UIColor:currentColor].CGColor);
        }
         if(_borderTopWidth > 0 && currentColor >>  24 != 0){
             CGPoint lines[] = {(CGPoint){0,0},(CGPoint){size.width,0},(CGPoint){topRightX,topRightY},(CGPoint){topLeftX,topLeftY}};
             CGContextAddLines(ctx,lines,4);
         }
        //right
        if(currentColor != _borderRightColor){
            CGContextFillPath(ctx);
            currentColor = _borderRightColor;
            CGContextSetFillColorWithColor(ctx,[BackgroundLayer int2UIColor:currentColor].CGColor);
        }
         if(_borderRightWidth > 0 && currentColor >>  24 != 0){
             CGPoint lines[] = {(CGPoint){topRightX,topRightY},(CGPoint){size.width,0},(CGPoint){size.width,size.height},(CGPoint){bottomRightX,bottomRightY}};
             CGContextAddLines(ctx,lines,4);
         }
        //bottom
        if(currentColor != _borderBottomColor){
            CGContextFillPath(ctx);
            currentColor = _borderBottomColor;
            CGContextSetFillColorWithColor(ctx,[BackgroundLayer int2UIColor:currentColor].CGColor);
        }
         if(_borderBottomWidth > 0 && currentColor >>  24 != 0){
             CGPoint lines[] = {(CGPoint){bottomLeftX,bottomLeftY},(CGPoint){bottomRightX,bottomRightY},(CGPoint){size.width,size.height},(CGPoint){0,size.height}};
             CGContextAddLines(ctx,lines,4);
         }
        CGContextFillPath(ctx);
    }
    
    UIImage* image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

-(UIImage*)toDashedImage:(CGSize)size borderLeftWidth:(CGFloat)borderLeftWidth borderTopWidth:(CGFloat)borderTopWidth borderRightWidth:(CGFloat)borderRightWidth borderBottomWidth:(CGFloat)borderBottomWidth borderTopLeftRadius:(CGFloat)borderTopLeftRadius borderTopRightRadius:(CGFloat)borderTopRightRadius borderBottomRightRadius:(CGFloat)borderBottomRightRadius borderBottomLeftRadius:(CGFloat)borderBottomLeftRadius dashedScale:(CGFloat)dashedScale bgColor:(CGColorRef)bgColor{
    UIGraphicsBeginImageContextWithOptions(size,NO,0);
    CGContextRef ctx = UIGraphicsGetCurrentContext();
    
    UIBezierPath* outerPath = [self getOuterPath:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius];
    UIBezierPath* innerPath = [self getDashedInnerPath:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius];
    CGContextSetFillColorWithColor(ctx,bgColor);
    CGContextAddPath(ctx, outerPath.CGPath);
    CGContextFillPath(ctx);
    CGContextSetLineWidth(ctx, borderLeftWidth);
    CGFloat dashLengths[] = {dashedScale * borderLeftWidth,dashedScale * borderLeftWidth};
    CGContextSetLineDash(ctx, 0.0, dashLengths, 2);
    CGContextAddPath(ctx,  innerPath.CGPath);
    CGContextSetStrokeColorWithColor(ctx, [BackgroundLayer int2UIColor:_borderLeftColor].CGColor);
    CGContextStrokePath(ctx);

    UIImage* image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    return image;
}

-(void)displayLayer:(CALayer *)layer
{
    if(layer == self && _forgroundLayer){
        
        _forgroundLayer.backgroundColor = nil;
        _forgroundLayer.shadowRadius = 0;
        _forgroundLayer.shadowPath = nil;
        _forgroundLayer.shadowOffset = CGSizeZero;
        CGColorRef bgColor = [BackgroundLayer int2UIColor:_bgColor].CGColor;
        layer.backgroundColor = bgColor;
        CGSize size = layer.bounds.size;

        if(size.width > 0 && size.height > 0){
            CGFloat halfWidth = size.width / 2;
            CGFloat halfHeight = size.height / 2;
            CGFloat maxRadius = MIN(halfWidth,halfHeight);
            CGFloat borderLeftWidth = MIN(_borderLeftWidth,halfWidth);
            CGFloat borderTopWidth =  MIN(_borderTopWidth,halfHeight);
            CGFloat borderRightWidth = MIN(_borderRightWidth,halfWidth);
            CGFloat borderBottomWidth = MIN(_borderBottomWidth,halfHeight);
            CGFloat borderTopLeftRadius = MIN(_borderTopLeftRadius,maxRadius);
            CGFloat borderTopRightRadius = MIN(_borderTopRightRadius,maxRadius);
            CGFloat borderBottomRightRadius = MIN(_borderBottomRightRadius,maxRadius);
            CGFloat borderBottomLeftRadius = MIN(_borderBottomLeftRadius,maxRadius);
            
            if(_mShadowRadius > 0){
                layer.shadowOpacity = 1;
                layer.shadowColor = [BackgroundLayer int2UIColor:_mShadowColor].CGColor;
                layer.shadowRadius = _mShadowRadius;
                layer.shadowOffset = CGSizeMake(_shadowDx,_shadowDy);
                layer.shadowPath = [self getOuterPath:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius].CGPath;
            }else{
                layer.shadowOpacity = 0;
            }
            
            BOOL haveBorderWidth = [self haveBorderWidth];
            BOOL sameBorderRadius = [self isSameBorderRadius];
            BOOL sameBorderWidth = [self isSameBorderWidth];
            BOOL sameBorderColor = [self isSameBorderColor];
            BOOL haveBorderRadius = [self haveBorderRadius];
            do{
                if(haveBorderWidth || haveBorderRadius){
                    UIImage* borderImage = nil;
                    switch (_borderStyle) {
                        case BorderStyleSolid:{
                            if( sameBorderWidth && sameBorderColor && sameBorderRadius){
                                CGFloat halfWidth = size.width / 2;
                                CGFloat halfHeight = size.height / 2;
                                CGFloat maxRadius = MIN(halfWidth,halfHeight);
                                layer.contents = nil;
                                layer.borderColor =[BackgroundLayer int2UIColor:_borderLeftColor].CGColor;
                                layer.borderWidth = MIN(_borderLeftWidth, maxRadius);
                                layer.cornerRadius = MIN(maxRadius,_borderTopLeftRadius);
                            }else{
                                borderImage = [self toImage:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius color:bgColor];
                            }
                            break;
                        }
                        case BorderStyleDashed:{
                            borderImage = [self toDashedImage:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius dashedScale:3 bgColor:bgColor];
                            break;
                        }
                            
                        case BorderStyleDotted:{
                            borderImage = [self toDashedImage:size borderLeftWidth:borderLeftWidth borderTopWidth:borderTopWidth borderRightWidth:borderRightWidth borderBottomWidth:borderBottomWidth borderTopLeftRadius:borderTopLeftRadius borderTopRightRadius:borderTopRightRadius borderBottomRightRadius:borderBottomRightRadius borderBottomLeftRadius:borderBottomLeftRadius dashedScale:1 bgColor:bgColor];
                            break;
                        }
                        default:
                            break;
                    }
                    if(borderImage){
                        layer.backgroundColor = nil;
                        layer.contents = (__bridge id)borderImage.CGImage;
                        layer.contentsScale = borderImage.scale;
                    }
                    break;
                }
                layer.contents = nil;
                layer.borderWidth = 0;
                layer.cornerRadius = 0;
            }while (false);
        }
        
    }
}

-(BOOL)haveBorderWidth{
    return _borderLeftWidth > 0 ||
    _borderTopWidth > 0 ||
    _borderRightWidth > 0 ||
    _borderBottomWidth > 0;
}

-(BOOL)isSameBorderWidth{
    return _borderLeftWidth == _borderTopWidth &&
    _borderLeftWidth ==_borderRightWidth &&
    _borderLeftWidth == _borderBottomWidth;
}

-(BOOL)haveBorderRadius{
    return _borderTopLeftRadius > 0 ||
    _borderTopRightRadius > 0 ||
    _borderBottomLeftRadius > 0 ||
    _borderBottomRightRadius > 0;
}

-(BOOL)isSameBorderRadius{
    return _borderTopLeftRadius == _borderTopRightRadius &&
    _borderTopLeftRadius ==_borderBottomLeftRadius &&
    _borderTopLeftRadius == _borderBottomRightRadius;
}

-(BOOL)isSameBorderColor{
    return _borderTopColor == _borderRightColor &&
    _borderTopColor ==_borderLeftColor &&
    _borderTopColor == _borderBottomColor;
}

- (CGFloat)getInnerBorderRadius{
    CGSize size = self.bounds.size;
    CGFloat halfWidth = size.width / 2;
    CGFloat halfHeight = size.height / 2;
    CGFloat maxRadius = MIN(halfWidth,halfHeight);
    return MIN(_borderTopLeftRadius, maxRadius);
}

- (void)dealloc
{
    
}

@end
