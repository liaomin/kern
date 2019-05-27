package com.hitales.ui

import com.hitales.ui.android.AndroidScrollView
import com.hitales.utils.Frame


actual open class ScrollView : com.hitales.ui.ViewGroup {


    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): android.widget.FrameLayout {
        return AndroidScrollView(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

}