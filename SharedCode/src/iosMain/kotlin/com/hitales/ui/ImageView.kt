package com.hitales.ui

import com.hitales.ui.ios.IOSImageView
import com.hitales.utils.WeakReference
import platform.UIKit.UIImageView
import platform.UIKit.UIViewContentMode
import platform.UIKit.clipsToBounds
import platform.UIKit.contentMode

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
    actual constructor(layoutParams: LayoutParams?):super(layoutParams)

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

//    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
//        val size = super.measureSize(maxWidth, maxHeight)
//        image?.mImage?.apply {
//            val scale = this.scale
//            this.size.useContents {
//                val w = this.width * scale
//                val h = this.height * scale
//                val screenScale = UIScreen.mainScreen.scale
//                size.width = (w / screenScale).toFloat()
//                size.height = (h / screenScale).toFloat()
//            }
//        }
//        return size
//    }

    actual var tintColor: Int
        get() = getWidget().tintColor.toInt()
        set(value) {
            getWidget().tintColor = value.toUIColor()
        }

}