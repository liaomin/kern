package com.hitales.ui

import com.hitales.utils.Frame
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGRectZero
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CALayer
import platform.UIKit.UIScrollView
import platform.UIKit.UIView
import kotlin.math.max


class IOSScrollView(val scrollView: ScrollView) : UIScrollView(CGRectMake(0.0,0.0,0.0,0.0)){
    override fun layoutSublayersOfLayer(layer: CALayer) {
        super.layoutSublayersOfLayer(layer)
        var width = scrollView.frame.width
        var height = scrollView.frame.height
        scrollView.children.forEach {
            val w = it.frame.width + it.frame.x
            val h = it.frame.height + it.frame.y
            width = max(width,w)
            height = max(height,h)
        }
        setContentSize(CGSizeMake(width.toDouble(),height.toDouble()))
    }
}


actual open class ScrollView : LayoutView {


    actual constructor(frame: Frame):super(frame)

    override fun createWidget(): UIScrollView {
        return IOSScrollView(this)
    }

    override fun getWidget(): UIScrollView {
        return super.getWidget() as UIScrollView
    }

}