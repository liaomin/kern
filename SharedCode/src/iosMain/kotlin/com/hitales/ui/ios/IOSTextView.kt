package com.hitales.ui.ios

import com.hitales.ui.TextView
import com.hitales.utils.WeakReference
import com.kern.ios.ui.KTextView
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIWindow

class IOSTextView(val mView: WeakReference<TextView>) : KTextView(CGRectMake(0.0,0.0,0.0,0.0)) {

    @ObjCAction
    fun willMoveToWindow(window: UIWindow?){
        val view = mView.get()
        if(window == null){
            view?.onDetachedFromWindow()
        }else{
            view?.onAttachedToWindow()
        }
    }

//    override fun displayLayer(layer: CALayer) {
//        val view = mView.get()
//        if(view != null){
//            view.mBackground?.onDraw(layer,view.mBackgroundColor)
//        }
//    }

}