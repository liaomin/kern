package com.hitales.ui.ios

import com.hitales.ui.Button
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CALayer
import platform.UIKit.*

class IOSButtonView(val mView: WeakReference<Button>) : UIButton(CGRectMake(0.0,0.0,0.0,0.0)) {


    var preState:UIControlState = UIControlStateNormal

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

    override fun displayLayer(layer: CALayer) {
        val view = mView.get()
        if(view != null){
            view.mBackground?.onDraw(layer,view.mBackgroundColor)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        preState = this.state
        super.setEnabled(enabled)
        checkState()
    }

    override fun setSelected(selected: Boolean) {
        preState = this.state
        super.setSelected(selected)
        checkState()
    }

    override fun setHighlighted(highlighted: Boolean) {
        preState = this.state
        super.setHighlighted(highlighted)
        checkState()
    }

    override fun touchesBegan(touches: Set<*>, withEvent: UIEvent?) {
        preState = this.state
        super.touchesBegan(touches, withEvent)
        checkState()
    }

    override fun touchesMoved(touches: Set<*>, withEvent: UIEvent?) {
        preState = this.state
        super.touchesMoved(touches, withEvent)
        checkState()
    }

    override fun touchesEnded(touches: Set<*>, withEvent: UIEvent?) {
        preState = this.state
        super.touchesEnded(touches, withEvent)
        checkState()
    }

    override fun touchesCancelled(touches: Set<*>, withEvent: UIEvent?) {
        preState = this.state
        super.touchesCancelled(touches, withEvent)
        checkState()
    }

    fun checkState(){
        if(preState != this.state){
            preState = this.state
            mView.get()?.onIOSStateChange(this.state)
        }
    }

}