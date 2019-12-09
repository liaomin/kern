//
//  CALayer+Image.m
//  HUI
//
//  Created by 廖敏 on 6/11/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "CALayer+Image.h"

@implementation CALayer (Image)

-(void)setContentsWithImage:(UIImage *)image
{
    if(image != nil){
        self.contents = (id)image.CGImage;
    }else{
        self.contents = NULL;
    }
}

-(void)setTransformM34:(CGFloat) m34{
    CATransform3D transform = CATransform3DIdentity;
    transform.m34 = m34;
    self.transform = transform;
}


@end
