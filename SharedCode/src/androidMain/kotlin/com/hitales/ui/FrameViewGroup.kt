package com.hitales.ui

import android.widget.FrameLayout
import com.hitales.utils.Frame


actual class FrameViewGroup : View {

    actual constructor(frame: Frame):super(frame)

    actual val children:ArrayList<View> = ArrayList()

    actual open fun addView(
        view: View,
        index: Int
    ) {
        if(index < 0){
            children.add(view)
            getWidget().addView(view.getWidget())
            view.superView = this
        }
    }


    override fun createWidget(): FrameLayout {
        return FrameLayout(Platform.getApplication())
    }

    override fun getWidget(): FrameLayout {
        return super.getWidget() as FrameLayout
    }

}