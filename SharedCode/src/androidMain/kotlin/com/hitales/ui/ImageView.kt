package com.hitales.ui

import android.widget.ImageView
import com.hitales.ui.android.AndroidImageView

actual open class ImageView : com.hitales.ui.View {

    actual constructor(layoutParams: LayoutParams) : super(layoutParams)

    override fun getWidget(): AndroidImageView {
        return super.getWidget() as AndroidImageView
    }

    override fun createWidget(): AndroidImageView {
        val androidImageView = AndroidImageView(this)
        androidImageView.scaleType =  ImageView.ScaleType.FIT_CENTER
        return androidImageView
    }

    actual open var image: Image? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().setImageBitmap(value.bitmap)
            }
        }
    actual open var resizeMode: ImageResizeMode = ImageResizeMode.SCALE_FIT
        set(value) {
            field = value
            when(value){
                ImageResizeMode.SCALE_FIT -> getWidget().scaleType = ImageView.ScaleType.FIT_CENTER
                ImageResizeMode.SCALE_FILL -> getWidget().scaleType = ImageView.ScaleType.FIT_XY
                ImageResizeMode.CENTER -> getWidget().scaleType = ImageView.ScaleType.CENTER_INSIDE
                ImageResizeMode.SCALE_CENTER_CROP -> getWidget().scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
}