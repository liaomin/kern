package com.hitales.ui.ios

import com.hitales.ui.Layout
import com.hitales.ui.View
import com.hitales.utils.Log
import com.hitales.utils.WeakReference
import com.kern.ios.ui.KView
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIWindow

class IOSView(val mView: WeakReference<View>) : KView(CGRectMake(0.0,0.0,0.0,0.0)) {

    override fun willMoveToWindow(window: UIWindow) {
        super.willMoveToWindow(window)
        val view = mView.get()
        if(window == null){
            view?.onDetachedFromWindow()
        }else{
            view?.onAttachedToWindow()
        }
    }

    override fun layoutSubviews() {
        super.layoutSubviews()
        Log.d("$mView layoutSubviews")
        val view = mView.get()
        if(view != null && view is Layout){
            this.frame.useContents {
                view.layoutSubviews(this.size.width.toFloat(),this.size.height.toFloat())
            }

        }
    }


}