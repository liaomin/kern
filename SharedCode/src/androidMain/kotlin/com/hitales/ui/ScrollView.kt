package com.hitales.ui

import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame


actual open class ScrollView : com.hitales.ui.ViewGroup {


    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): AndroidScrollView {
        return AndroidScrollView.fromXLM(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

    actual open fun layoutSubViews(offsetX: Float, offsetY: Float) {
    }

}