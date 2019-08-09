package com.hitales.ui

import com.hitales.ui.android.AndroidHorizontalScrollView
import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame


actual open class HorizontalScrollView : com.hitales.ui.ScrollView {

    actual constructor(frame: Frame) : super(frame)

    override var showScrollBar: Boolean
        get() = mWidget.isHorizontalScrollBarEnabled
        set(value) {
            mWidget.isHorizontalScrollBarEnabled = value
        }

    override var scrollEnabled: Boolean
        get() = (getWidget() as AndroidHorizontalScrollView).scrollEnabled
        set(value) {
            (getWidget() as AndroidHorizontalScrollView).scrollEnabled = value
        }

    override fun createWidget(): AndroidHorizontalScrollView {
        return AndroidHorizontalScrollView(this)
    }


}