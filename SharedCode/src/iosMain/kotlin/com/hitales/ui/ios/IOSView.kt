package com.hitales.ui.ios

import com.hitales.ios.ui.HView
import com.hitales.ui.View
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
    fun willMoveToWindow(window: UIWindow?){
        if(window == null){
            this.layer.contents = null
        }
    }


    @ObjCAction
    fun willMoveToSuperview(superview: UIView?){
        if(superview != null){
            println("not null")
        }else{
            println("null")
        }
    }

    override fun displayLayer(layer: CALayer) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
    }

    override fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        super.touchesBegan(touches, withEvent)
    }

}