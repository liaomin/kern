package com.hitales.ui.ios

import com.hitales.ui.Layout
import com.hitales.ui.ScrollView
import com.hitales.utils.Log
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIScrollView
import platform.UIKit.UIScrollViewDelegateProtocol
import platform.UIKit.UIView
import platform.UIKit.UIWindow

class IOSScrollView(val mView: WeakReference<ScrollView>) : UIScrollView(CGRectMake(0.0,0.0,0.0,0.0)),UIScrollViewDelegateProtocol{

    init {
        this.delegate = this
        this.delaysContentTouches = false
        this.canCancelContentTouches = true
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
        Log.d("$mView layoutSubviews")
        val view = mView.get()
        if(view != null && view is Layout){
            this.frame.useContents {
                view.layoutSubviews(this.size.width.toFloat(),this.size.height.toFloat())
            }

        }
    }

    override fun touchesShouldCancelInContentView(view: UIView): Boolean {
        return true
//        return super.touchesShouldCancelInContentView(view)
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