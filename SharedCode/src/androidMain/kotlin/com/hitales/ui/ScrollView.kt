package com.hitales.ui

import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame


actual open class ScrollView : com.hitales.ui.ViewGroup {

    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): android.view.View {
        return AndroidScrollView.fromXLM(this)
    }

    actual open fun layoutSubViews(offsetX: Float, offsetY: Float) {
    }

    override fun getContentWidth(): Float {
        return frame.width
    }

    override fun getContentHeight(): Float {
        return Float.MAX_VALUE
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
        get() = (getWidget() as AndroidScrollView).scrollEnabled
        set(value) {
            (getWidget() as AndroidScrollView).scrollEnabled = value
        }


    override fun getVisibleFrame(frame: Frame) {
        frame.width = this.frame.width
        frame.height = this.frame.height
        super.getVisibleFrame(frame)
    }

}