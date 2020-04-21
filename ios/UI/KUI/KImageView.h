//
//  KImageView.h
//  KUI
//
//  Created by 廖敏 on 4/19/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MeasureNode.h"

NS_ASSUME_NONNULL_BEGIN

@interface KImageView : UIImageView <MeasureNode>

-(UIImage*)getImage;

@end

NS_ASSUME_NONNULL_END
