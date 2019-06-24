package com.hitales.ui

import com.hitales.ui.ios.IOSScrollView
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIScrollView
import kotlin.math.max

actual open class HorizontalScrollView : ViewGroup {

    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): UIScrollView {
        return IOSScrollView(WeakReference(this))
    }

    override fun getWidget(): UIScrollView {
        return super.getWidget() as UIScrollView
    }

    actual open fun layoutSubViews(offsetX: Float, offsetY: Float) {
    }

    override fun layout() {
        super.layout()
        calculateContentSize()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layout()
    }

    protected open fun calculateContentSize() {
        val scrollView = getWidget()
        var width = frame.width
        var height = frame.height
        children.forEach {
            val w = it.frame.width + it.frame.x
            val h = it.frame.height + it.frame.y
            width = max(width, w)
            height = max(height, h)
        }
        scrollView.setContentSize(CGSizeMake(width.toDouble(), height.toDouble()))
    }

    override fun getContentHeight(): Float {
        return Float.MAX_VALUE
    }

    override fun getContentWidth(): Float {
        return Float.MAX_VALUE
    }
}