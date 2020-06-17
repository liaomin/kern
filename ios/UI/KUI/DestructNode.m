//
//  DestructNode.m
//  KUI
//
//  Created by 廖敏 on 4/22/20.
//  Copyright © 2020 廖敏. All rights reserved.
//

#import "DestructNode.h"
#import <objc/runtime.h>
#import <objc/message.h>

@implementation DestructNode

-(void)dealloc
{
    [self onDestruct];
}

-(void)onDestruct
{
}

@end

@implementation NSObject (Destruct)

static NSMutableDictionary* _cacheMap;

+(void)load
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _cacheMap = [[NSMutableDictionary alloc] init];
    });
}

+(void)setDestruct:(SEL)sel forClass:(__unsafe_unretained Class)cls
{
    NSString* key = [NSString stringWithUTF8String:class_getName(cls)];
    @synchronized (_cacheMap) {
        if(![_cacheMap objectForKey:key]){
            [_cacheMap setValue:cls forKey:key];
            SEL dealloc = sel_registerName("dealloc");
            Class superCls = class_getSuperclass(cls);
            Method deallocMethod = class_getInstanceMethod(cls, dealloc);
            Method setDeallocMethod = class_getInstanceMethod(cls, sel);
            __block void (*deallocMethodBlock)(__unsafe_unretained id, SEL) = NULL;
            __block void (*setDeallocMethodBlock)(__unsafe_unretained id, SEL) = NULL;
            deallocMethodBlock = (__typeof__(deallocMethodBlock)) method_getImplementation(deallocMethod);
            setDeallocMethodBlock = (__typeof__(setDeallocMethodBlock))method_getImplementation(setDeallocMethod);
            if(!setDeallocMethod){
                return;
            }
            if(!class_addMethod(cls, dealloc, imp_implementationWithBlock(^(__unsafe_unretained id self){
                setDeallocMethodBlock(self,sel);
                struct objc_super superInfo = {.receiver = self,.super_class = superCls};
                void (*msgSend)(struct objc_super *, SEL) = (__typeof__(msgSend))objc_msgSendSuper;
                msgSend(&superInfo, dealloc);
            }), "v@:")){
                IMP newImp = imp_implementationWithBlock(^(__unsafe_unretained id self){
                    setDeallocMethodBlock(self,sel);
                    deallocMethodBlock(self,dealloc);
                
                });
                method_setImplementation(deallocMethod, newImp);
            }
        }
    }

}

-(void)setDestruct:(SEL)sel
{
    [NSObject setDestruct:sel forClass:self.class];
}

@end
