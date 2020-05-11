//
//  HCollectionView.m
//  HUI
//
//  Created by 廖敏 on 8/27/19.
//  Copyright © 2019 廖敏. All rights reserved.
//

#import "HCollectionView.h"

NSInteger headerType = -0xFFFFF0;

NSInteger footerType = -0xFFFFF1;

@implementation HAttributes

- (id)copyWithZone:(nullable NSZone *)zone
{
    HAttributes* att = [super copyWithZone:zone];
    att.tag = self.tag;
    att.tag1 = self.tag1;
    att.tag2 = self.tag2;
    return att;
}

@end

@implementation HLayout

-(void)prepareLayout
{
    [super prepareLayout];
}

- (CGSize)collectionViewContentSize
{
    return CGSizeZero;
}

- (NSArray<UICollectionViewLayoutAttributes *> *)layoutAttributesForElementsInRect:(CGRect)rect
{
    return @[];
}

- (BOOL)shouldInvalidateLayoutForBoundsChange:(CGRect)newBounds
{
    return NO;
}



@end

@implementation HCell

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.contentView.backgroundColor = UIColor.clearColor;
    }
    return self;
}

- (void)applyLayoutAttributes:(HAttributes *)layoutAttributes
{
    [super applyLayoutAttributes:layoutAttributes];
    _attributes = layoutAttributes;
//    if(_store){
//        [_store applyLayoutAttributes:layoutAttributes];
//    }
}

- (void)setContent:(UIView *)content tag:(id<HCellDelegate>)store
{
    _store = store;
    if(_store && _attributes){
        [_store applyLayoutAttributes:_attributes];
    }
    if(content != _content){
         _content = content;
        UIView* contentView = self.contentView;
        if(_constraints){
            [contentView removeConstraints:_constraints];
        }
//        _content.translatesAutoresizingMaskIntoConstraints=NO;
        [contentView addSubview:_content];
//        _constraints = @[
//                          // 左边
//                          [NSLayoutConstraint constraintWithItem:_content attribute:NSLayoutAttributeLeading relatedBy:NSLayoutRelationEqual toItem:contentView attribute:NSLayoutAttributeLeading multiplier:1.0 constant:0],
//
//                          // 右边
//                          [NSLayoutConstraint constraintWithItem:_content attribute:NSLayoutAttributeTrailing relatedBy:NSLayoutRelationEqual toItem:contentView attribute:NSLayoutAttributeTrailing multiplier:1.0 constant: 0],
//
//                          // 底部
//                          [NSLayoutConstraint constraintWithItem:_content attribute:NSLayoutAttributeBottom relatedBy:NSLayoutRelationEqual toItem:contentView attribute:NSLayoutAttributeBottom multiplier:1.0 constant: 0],
//                          // 顶部
//                          [NSLayoutConstraint constraintWithItem:_content attribute:NSLayoutAttributeTop relatedBy:NSLayoutRelationEqual toItem:contentView attribute:NSLayoutAttributeTop multiplier:1.0 constant: 0]
//                          ];
//        [contentView addConstraints:_constraints];
    }else if(_content){
        [_content removeFromSuperview];
    }
}

@end

@implementation HCollectionView

- (instancetype)initWithFrame:(CGRect)frame collectionViewLayout:(UICollectionViewLayout *)layout
{
    self = [super initWithFrame:frame collectionViewLayout:layout];
    if (self) {
        self.delegate = self;
        self.dataSource = self;
        self.registedClass = [[NSMutableDictionary alloc] initWithCapacity:3];
        [self registerCell:UICollectionElementKindSectionHeader];
        [self registerCell:UICollectionElementKindSectionFooter];
    }
    return self;
}

-(void)registerCell:(NSString*)type
{
    if(![_registedClass objectForKey:type]){
        [self registerClass:[HCell class] forCellWithReuseIdentifier:type];
        [_registedClass setObject:@1 forKey:type];
    }
}



- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    NSInteger type = [self getItemType:indexPath.section row:indexPath.row];
    NSString* classType;
    if(type == headerType){
        //header
        classType = UICollectionElementKindSectionHeader;
        HCell* cell = [collectionView dequeueReusableCellWithReuseIdentifier:classType forIndexPath:indexPath];
        if(cell.store == NULL){
            [self createHeaderView:indexPath.section withView:cell];
        }
        [self onBindHeaderView:indexPath.section withView:cell];
        return cell;
    }else if (type == footerType){
        //footer
         classType = UICollectionElementKindSectionFooter;
        HCell* cell = [collectionView dequeueReusableCellWithReuseIdentifier:classType forIndexPath:indexPath];
        if(cell.store == NULL){
            [self createFooterView:indexPath.section withView:cell];
        }
        [self onBindFooterView:indexPath.section withView:cell];
        return cell;
    }else{
        classType = [NSString stringWithFormat:@"cell_%ldl",type];
        [self registerCell:classType];
        HCell* cell = [collectionView dequeueReusableCellWithReuseIdentifier:classType forIndexPath:indexPath];
        if(cell.store == NULL){
            [self createItemView:indexPath.section row:indexPath.row cellType:type withView:cell];
        }
        [self onBindItemView:indexPath.section row:indexPath.row viewType:type withView:cell];
        return cell;
    }
}


//- (UICollectionReusableView *)collectionView:(UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath
//{
//    if(kind == UICollectionElementKindSectionHeader){
//       HCell* cell = [collectionView dequeueReusableSupplementaryViewOfKind:kind withReuseIdentifier:kind forIndexPath:indexPath];
//        if(cell.store == NULL){
//            [self createHeaderView:indexPath.section withView:cell];
//        }
//        [self onBindHeaderView:indexPath.section withView:cell];
//        return cell;
//    }else if(kind == UICollectionElementKindSectionFooter){
//       HCell* cell = [collectionView dequeueReusableSupplementaryViewOfKind:kind withReuseIdentifier:kind forIndexPath:indexPath];
//        if(cell.store == NULL){
//            [self createFooterView:indexPath.section withView:cell];
//        }
//        [self onBindFooterView:indexPath.section withView:cell];
//        return cell;
//    }
//    return nil;
//}

- (CGSize)collectionView:(UICollectionView *)collectionView layout:(UICollectionViewLayout*)collectionViewLayout sizeForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return [self getItemSize:indexPath.section row:indexPath.row];
}


-(NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return [self getNumberOfItem:section];
}

- (NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView
{
    return [self getNumberOfSection];
}

-(NSInteger)getNumberOfSection
{
    return 1;
}

-(NSInteger)getNumberOfItem:(NSInteger)sectionIndex
{
    return 0;
}

-(void)createItemView:(NSInteger)sectionIndex row:(NSInteger)row cellType:(NSInteger)type  withView:(HCell*)view;
{
    
}

-(void)createHeaderView:(NSInteger)sectionIndex withView:(HCell*)view
{
    
}

-(void)createFooterView:(NSInteger)sectionIndex withView:(HCell*)view
{
    
}


-(void)onBindHeaderView:(NSInteger)sectionIndex withView:(HCell*)view
{
    
}

-(void)onBindFooterView:(NSInteger)sectionIndex withView:(HCell*)view
{
    
}

-(void)onBindItemView:(NSInteger)sectionIndex row:(NSInteger)row viewType:(NSInteger)type withView:(HCell*)view
{
    
}

-(NSInteger)getItemType:(NSInteger)sectionIndex row:(NSInteger)row
{
    return 0;
}

-(CGSize)getItemSize:(NSInteger)sectionIndex row:(NSInteger)row
{
    return CGSizeMake(0, 0);
}


@end
