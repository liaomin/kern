//
//  ViewController.h
//  ios
//
//  Created by 廖敏 on 4/28/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "BorderView.h"
@interface ViewController : UIViewController<UIScrollViewDelegate>

@property (nonatomic,weak) BorderView* borderView;

@end

