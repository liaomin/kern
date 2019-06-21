package com.hitales.ui.ios

import com.hitales.ios.ui.HView
import com.hitales.ui.View
import com.hitales.ui.ViewGroup
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.memScoped
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIEvent
import platform.UIKit.UIView
import platform.UIKit.UIWindow


class IOSView(protected val mView: WeakReference<View>) : UIView(CGRectMake(0.0,0.0,0.0,0.0)) {

    @ObjCAction
    fun willMoveToSuperview(superview: UIView?){
        if(superview != null){
            println("not null")
        }else{
            println("null")
        }
    }

    @ObjCAction
    fun willMoveToWindow(window: UIWindow?){
        val view = mView.get()
        if(window == null){
            view?.onDetachedFromWindow()
            this.layer.contents = null
        }else{
            view?.onAttachedToWindow()
            this.layer.setNeedsDisplay()
        }
    }

    @ObjCAction
    fun layoutSubviews() {
        val view = mView.get()
        if(view != null && view is ViewGroup){
            view.layout()
        }
    }

    override fun displayLayer(layer: CALayer) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
    }

}