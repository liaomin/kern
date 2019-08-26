package com.hitales.ui.ios

import com.hitales.ui.TextView
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.UILabel
import platform.UIKit.UIWindow

class IOSTextView(val mView: WeakReference<TextView>) : UILabel(CGRectMake(0.0,0.0,0.0,0.0)) {

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

//    override fun displayLayer(layer: CALayer) {
//        val view = mView.get()
//        if(view != null){
//            view.mBackground?.onDraw(layer,view.mBackgroundColor)
//        }
//    }

    override fun layerWillDraw(layer: CALayer) {
        super.layerWillDraw(layer)
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
    }

}