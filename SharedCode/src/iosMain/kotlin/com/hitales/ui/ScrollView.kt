package com.hitales.ui

import com.hitales.ui.ios.IOSScrollView
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.utils.WeakReference
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIScrollView
import platform.UIKit.layoutSubviews
import kotlin.math.max



actual open class ScrollView : FlexLayout {

    actual constructor(layoutParams: LayoutParams?):super(layoutParams)

    override fun createWidget(): UIScrollView {
        return IOSScrollView(WeakReference(this))
    }

    override fun getWidget(): UIScrollView {
        return super.getWidget() as UIScrollView
    }

    override fun layoutSubviews(width: Float, height: Float) {
        super.layoutSubviews(width, height)
        calculateContentSize()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mWidget.layoutSubviews()
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
    actual open var orientation: Orientation = Orientation.VERTICAL

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
        get() {
            return getWidget().contentOffset.useContents { x.toFloat() }
        }
        set(value) {
            getWidget().contentOffset = CGPointMake(value.toDouble(),scrollY.toDouble())
        }


    actual var scrollY: Float
        get() {
            return getWidget().contentOffset.useContents { x.toFloat() }
        }
        set(value) {
            getWidget().contentOffset = CGPointMake(scrollX.toDouble(),value.toDouble())
        }

    actual open fun onScroll(offsetX: Float, offsetY: Float) {
    }

    actual var isPageEnable: Boolean
        get() = getWidget().isPagingEnabled()
        set(value) {
            getWidget().pagingEnabled = value
        }

    actual fun scrollTo(dx: Float, dy: Float) {
    }

    actual fun scrollBy(dx: Float, dy: Float) {
    }

    actual fun smoothScrollBy(dx: Float, dy: Float) {
    }
}