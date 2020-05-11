package com.hitales.ui.ios

import com.hitales.ui.ImageView
import com.hitales.utils.WeakReference
import com.kern.ios.ui.KImageView
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIImage
import platform.UIKit.UIWindow

class IOSImageView(val mView: WeakReference<ImageView>) : KImageView(CGRectMake(0.0,0.0,0.0,0.0)) {

    @ObjCAction
    fun willMoveToWindow(window: UIWindow?){
        val view = mView.get()
        if(window == null){
            view?.onDetachedFromWindow()
        }else{
            view?.onAttachedToWindow()
        }
    }

    override fun setImage(image: UIImage?) {
        val pre = this.image
        super.setImage(image)
        if( pre != this.image ){
            this.layer.setNeedsDisplay()
        }
    }
}