package com.hitales.ui.ios

import com.hitales.ui.ScrollView
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIScrollView
import platform.UIKit.UIScrollViewDelegateProtocol
import platform.UIKit.UIWindow

class IOSScrollView(val mView: WeakReference<ScrollView>) : UIScrollView(CGRectMake(0.0,0.0,0.0,0.0)),UIScrollViewDelegateProtocol{

    init {
        this.delegate = this
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

    override fun drawLayer(layer: CALayer, inContext: CGContextRef?) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
        super.drawLayer(layer, inContext)
    }

    override fun scrollViewDidScroll(scrollView: UIScrollView) {
        scrollView.contentOffset.useContents {
            mView.get()?.onScroll(x.toFloat(),y.toFloat())
        }
    }

    override fun scrollViewDidScrollToTop(scrollView: UIScrollView) {
        scrollView.contentOffset.useContents {
            mView.get()?.onScroll(0f,0f)
        }
    }
}