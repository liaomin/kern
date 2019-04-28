package com.hitales.ui

import com.hitales.utils.Frame

actual open class ImageView : com.hitales.ui.View {

    actual constructor(frame: Frame) : super(frame)

    override fun getWidget(): android.widget.ImageView {
        return super.getWidget() as android.widget.ImageView
    }

    override fun createWidget(): android.widget.ImageView {
        return android.widget.ImageView(Platform.getApplication())
    }

    actual open var image: Image? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().setImageBitmap(value.bitmap)
            }
        }

}