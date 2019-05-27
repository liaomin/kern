package com.hitales.ui

import androidx.appcompat.widget.AppCompatImageView
import com.hitales.utils.Frame

actual open class ImageView : com.hitales.ui.View {

    actual constructor(frame: Frame) : super(frame)

    override fun getWidget(): AppCompatImageView {
        return super.getWidget() as AppCompatImageView
    }

    override fun createWidget(): AppCompatImageView {
        return AppCompatImageView(Platform.getApplication())
    }

    actual open var image: Image? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().setImageBitmap(value.bitmap)
            }
        }

}