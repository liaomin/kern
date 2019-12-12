package com.hitales.ui.ios

import com.hitales.ui.View
import com.hitales.ui.ViewGroup
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIView
import platform.UIKit.UIWindow


class IOSView(val mView: WeakReference<View>) : UIView(CGRectMake(0.0,0.0,0.0,0.0)) {

    @ObjCAction
    fun willMoveToSuperview(superview: UIView?){
        if(superview != null){
        }else{
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
            view.layoutSubviews()
        }
    }

    override fun drawLayer(layer: CALayer, inContext: CGContextRef?) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
        super.drawLayer(layer, inContext)
    }

}