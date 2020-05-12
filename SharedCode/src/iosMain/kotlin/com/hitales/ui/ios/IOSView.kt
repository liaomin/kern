package com.hitales.ui.ios

import com.hitales.ui.Layout
import com.hitales.ui.Touches
import com.hitales.ui.View
import com.hitales.utils.WeakReference
import com.kern.ios.ui.KView
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIEvent
import platform.UIKit.UIView
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
        val view = mView.get()
        if(view != null && view is Layout){
            this.frame.useContents {
                view.layoutSubviews(this.size.width.toFloat(),this.size.height.toFloat())
            }
        }
    }

    override fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        super.touchesBegan(touches, withEvent)
        val view = mView.get()
        if(view  != null && withEvent != null){
            view.onTouchesBegan(Touches(withEvent,this))
        }
    }

    override fun touchesMoved(touches: Set<*>, withEvent: UIEvent?) {
        super.touchesMoved(touches, withEvent)
        val view = mView.get()
        if(view  != null && withEvent != null){
            view.onTouchesMoved(Touches(withEvent,this))
        }
    }

    override fun touchesEnded(touches: Set<*>, withEvent: UIEvent?) {
        super.touchesEnded(touches, withEvent)
        val view = mView.get()
        if(view  != null && withEvent != null){
            view.onTouchesEnded(Touches(withEvent,this))
        }
    }

    override fun touchesCancelled(touches: Set<*>, withEvent: UIEvent?) {
        super.touchesCancelled(touches, withEvent)
        val view = mView.get()
        if(view  != null && withEvent != null){
            view.onTouchesCancelled(Touches(withEvent,this))
        }
    }

    override fun hitTest(point: CValue<CGPoint>, withEvent: UIEvent?): UIView? {
        val view = mView.get()
        if(view  != null && withEvent != null){
            if(view.dispatchTouchEvent(Touches(withEvent,this))){
                return null
            }
        }
        return super.hitTest(point, withEvent)
    }

    override fun onDestruct() {
        super.onDestruct()
        mView.get()?.onDestruct()
    }

}