package com.hitales.ui.ios

import com.hitales.ui.ImageView
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UIWindow

class IOSImageView(val mView: WeakReference<ImageView>) : UIImageView(CGRectMake(0.0,0.0,0.0,0.0)) {

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

    override fun drawLayer(layer: CALayer, inContext: CGContextRef?) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
        super.drawLayer(layer, inContext)
    }

    override fun setImage(image: UIImage?) {
        val pre = this.image
        super.setImage(image)
        if( pre != this.image ){
            this.layer.setNeedsDisplay()
        }
    }
}