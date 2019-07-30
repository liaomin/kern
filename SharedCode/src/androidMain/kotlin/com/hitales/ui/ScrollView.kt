package com.hitales.ui

import android.widget.FrameLayout
import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame
import com.hitales.utils.Size


actual open class ScrollView : com.hitales.ui.ViewGroup {

    actual open var contentSize: Size
        get() = getWidget().contentSize
        set(value) {
            getWidget().contentSize = value
        }


    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): android.view.View {
        return AndroidScrollView.createFromXLM(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

    actual open fun layoutSubViews(offsetX: Float, offsetY: Float) {
    }

    override fun getContentWidth(): Float {
        return contentSize.width
    }

    override fun getContentHeight(): Float {
        return contentSize.height
    }

    /**
     * default true
     */
    actual open var showScrollBar: Boolean
        get() = mWidget.isVerticalScrollBarEnabled
        set(value) {
            mWidget.isVerticalScrollBarEnabled = value
        }

    /**
     * default true
     */
    actual open var scrollEnabled: Boolean
        get() = getWidget().scrollEnabled
        set(value) {
            getWidget().scrollEnabled = value
        }


    override fun getVisibleFrame(frame: Frame) {
        frame.width = this.frame.width
        frame.height = this.frame.height
        super.getVisibleFrame(frame)
    }

    actual open fun onScroll(offsetX: Float, offsetY: Float) {
        layoutSubViews(offsetX,offsetY)
    }

}