package com.hitales.ui.ios

import com.hitales.ui.TextInput
import com.hitales.utils.WeakReference
import com.kern.ios.ui.KInputView
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIWindow

class IOSInputView(val mView: WeakReference<TextInput>) : KInputView(CGRectMake(0.0,0.0,0.0,0.0),null) {

    @ObjCAction
    fun willMoveToWindow(window: UIWindow?){
        val view = mView.get()
        if(window == null){
            view?.onDetachedFromWindow()
        }else{
            view?.onAttachedToWindow()
        }
    }
}