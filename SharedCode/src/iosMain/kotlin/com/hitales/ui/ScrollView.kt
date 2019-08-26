package com.hitales.ui

import com.hitales.ui.ios.IOSScrollView
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIScrollView
import kotlin.math.max



actual open class ScrollView : ViewGroup {

    actual constructor(frame: Frame):super(frame)

    override fun createWidget(): UIScrollView {
        return IOSScrollView(WeakReference(this))
    }

    override fun getWidget(): UIScrollView {
        return super.getWidget() as UIScrollView
    }


    override fun layoutSubviews() {
        super.layoutSubviews()
        calculateContentSize()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutSubviews()
    }

    protected open fun calculateContentSize(){
        val scrollView = getWidget()
        var width = frame.width
        var height = frame.height
        children.forEach {
            val w = it.frame.width + it.frame.x
            val h = it.frame.height + it.frame.y
            width = max(width,w)
            height = max(height,h)
        }
        scrollView.setContentSize(CGSizeMake(width.toDouble(),height.toDouble()))
    }

    /**
     * default VERTICAL
     */
    actual open var orientation: Orientation
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default true
     */
    actual open var showScrollBar: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default true
     */
    actual open var scrollEnabled: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual var scrollX: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual var scrollY: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual open fun onScroll(offsetX: Float, offsetY: Float) {
    }
}