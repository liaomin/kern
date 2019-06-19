package com.hitales.ui

import com.hitales.ui.ios.IOSImageView
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.useContents
import platform.UIKit.*

actual open class ImageView : com.hitales.ui.View {

//    actual constructor(frame: Frame) : super(frame)
//
//    override fun getWidget(): android.widget.ImageView {
//        return super.getWidget() as android.widget.ImageView
//    }
//
//    override fun createWidget(): android.widget.ImageView {
//        return android.widget.ImageView(Platform.getApplication())
//    }
//
//    actual open var image: Image? = null
//        set(value) {
//            field = value
//            if(value != null){
//                getWidget().setImageBitmap(value.bitmap)
//            }
//        }
    actual constructor(frame: Frame):super(frame)

    override fun createWidget(): UIImageView {
        val imageView = IOSImageView(WeakReference(this))
        imageView.clipsToBounds = true
        return imageView
    }

    override fun getWidget(): UIImageView {
        return super.getWidget() as UIImageView
    }

    actual open var image: Image? = null
        set(value) {
            field = value
            if(value != null){
                getWidget().image = value.mImage
            }else{
                getWidget().image = null
            }

        }
    /**
     *
     * default SCALE_FIT
     */
    actual open var resizeMode: ImageResizeMode = ImageResizeMode.SCALE_FIT
        set(value) {
            field = value
            when(value){
                ImageResizeMode.SCALE_FIT -> getWidget().contentMode = UIViewContentMode.UIViewContentModeScaleAspectFit
                ImageResizeMode.SCALE_FILL -> getWidget().contentMode = UIViewContentMode.UIViewContentModeScaleToFill
                ImageResizeMode.CENTER -> getWidget().contentMode = UIViewContentMode.UIViewContentModeCenter
                ImageResizeMode.SCALE_CENTER_CROP -> getWidget().contentMode = UIViewContentMode.UIViewContentModeScaleAspectFill
            }
        }

    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        return image?.mImage?.size?.useContents {
            return@useContents Size(this.width.toFloat(),this.height.toFloat())
        }?:super.measureSize(maxWidth, maxHeight)
    }

}