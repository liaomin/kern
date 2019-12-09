//
//  HCollectionView.h
//  HUI
//
//  Created by 廖敏 on 8/27/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface HAttributes : UICollectionViewLayoutAttributes

@property (nonatomic,weak) id tag;

@property (nonatomic,weak) id tag1;

@property (nonatomic,strong) id tag2;

@end

@interface HLayout : UICollectionViewLayout

-(void)prepareLayout;

- (CGSize)collectionViewContentSize;

 -(nullable NSArray<__kindof UICollectionViewLayoutAttributes *> *)layoutAttributesForElementsInRect:(CGRect)rect;

@end

@protocol HCellDelegate <NSObject>

- (void)applyLayoutAttributes:(HAttributes *)layoutAttributes;

@end

@interface HCell : UICollectionViewCell

@property (nonatomic,weak) UIView* content;

@property (nonatomic,weak) HAttributes* attributes;

@property (nonatomic,strong) NSArray* constraints;

@property (nonatomic,strong) id<HCellDelegate> store;

- (void)setContent:(UIView *)content tag:(id<HCellDelegate>)store;

- (void)applyLayoutAttributes:(HAttributes *)layoutAttributes;

@end




@interface HCollectionView : UICollectionView<UICollectionViewDataSource,UICollectionViewDelegateFlowLayout>

@property (nonatomic,strong) NSMutableDictionary* registedClass;

-(NSInteger)getNumberOfSection;

-(NSInteger)getNumberOfItem:(NSInteger)sectionIndex;

-(void)createItemView:(NSInteger)sectionIndex row:(NSInteger)row cellType:(NSInteger)type  withView:(HCell*)view;

-(void)createHeaderView:(NSInteger)sectionIndex withView:(HCell*)view;

-(void)createFooterView:(NSInteger)sectionIndex withView:(HCell*)view;

-(void)onBindHeaderView:(NSInteger)sectionIndex withView:(HCell*)view;

-(void)onBindFooterView:(NSInteger)sectionIndex withView:(HCell*)view;

-(void)onBindItemView:(NSInteger)sectionIndex row:(NSInteger)row viewType:(NSInteger)type withView:(HCell*)view;

-(NSInteger)getItemType:(NSInteger)sectionIndex row:(NSInteger)row;

-(CGSize)getItemSize:(NSInteger)sectionIndex row:(NSInteger)row;

@end

NS_ASSUME_NONNULL_END
