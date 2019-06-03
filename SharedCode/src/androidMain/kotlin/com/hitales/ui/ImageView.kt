package com.hitales.ui

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.hitales.ui.android.AndroidImageView
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
import com.hitales.utils.Size

actual open class ImageView : com.hitales.ui.View {

    actual constructor(frame: Frame) : super(frame)

    override fun getWidget(): AndroidImageView {
        return super.getWidget() as AndroidImageView
    }

    override fun createWidget(): AndroidImageView {
        return AndroidImageView(this)
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
                ImageResizeMode.CENTER -> getWidget().scaleType = ImageView.ScaleType.CENTER
                ImageResizeMode.SCALE_CENTER_CROP -> getWidget().scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }


    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        var width = PixelUtil.toPixelFromDIP(maxWidth).toInt()
        var height = PixelUtil.toPixelFromDIP(maxHeight).toInt()
        if( width <= 0 ){
            width = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            width = android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.AT_MOST)
        }
        if( height <= 0 ){
            height = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            height = android.view.View.MeasureSpec.makeMeasureSpec(height, android.view.View.MeasureSpec.AT_MOST)
        }
        mWidget.measure(width,height)
        val measuredWidth = mWidget.measuredWidth
        val measuredHeight = mWidget.measuredHeight
        return Size(PixelUtil.toDIPFromPixel(measuredWidth.toFloat()), PixelUtil.toDIPFromPixel(measuredHeight.toFloat()))
    }
}