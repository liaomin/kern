package com.hitales.ui

import com.hitales.ui.ios.IOSScrollView
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.ObjCMethod
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGRectZero
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIScrollView
import platform.UIKit.UIView
import platform.UIKit.UIWindow
import platform.UIKit.layoutSubviews
import kotlin.math.max



actual open class ScrollView : ViewGroup {


    actual constructor(frame: Frame):super(frame)

    override fun createWidget(): UIScrollView {
        return IOSScrollView(WeakReference(this))
    }

    override fun getWidget(): UIScrollView {
        return super.getWidget() as UIScrollView
    }

    actual open fun layoutSubViews(offsetX: Float, offsetY: Float) {
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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

}