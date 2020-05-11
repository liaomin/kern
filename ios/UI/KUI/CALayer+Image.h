//
//  CALayer+Image.h
//  KUI
//
//  Created by 廖敏 on 4/26/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <QuartzCore/QuartzCore.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface CALayer (Image)

-(void)setContentsWithImage:(UIImage*) image;

-(void)setTransformM34:(CGFloat) m34;

@end

NS_ASSUME_NONNULL_END
