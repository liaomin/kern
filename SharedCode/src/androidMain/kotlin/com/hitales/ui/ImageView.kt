package com.hitales.ui

import androidx.appcompat.widget.AppCompatImageView
import com.hitales.ui.android.AndroidImageView
import com.hitales.utils.Frame

actual open class ImageView : com.hitales.ui.View {

    actual constructor(frame: Frame) : super(frame)

    override fun getWidget(): AppCompatImageView {
        return super.getWidget() as AppCompatImageView
    }

    override fun createWidget(): AndroidImageView {
        return AndroidImageView()
    }

    actual open var image: Image? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().setImageBitmap(value.bitmap)
            }
        }
    actual open var resizeMode: ImageResizeMode
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

}