package com.hitales.ui

import com.hitales.ui.android.AndroidHorizontalScrollView
import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame


actual open class HorizontalScrollView : com.hitales.ui.ViewGroup {

    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): AndroidHorizontalScrollView {
        return AndroidHorizontalScrollView(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

    actual open fun layoutSubviews(offsetX: Float, offsetY: Float) {
        getWidget()
    }

    override fun getContentWidth(): Float {
        return Float.MAX_VALUE
    }

    override fun getContentHeight(): Float {
        return Float.MAX_VALUE
    }

}