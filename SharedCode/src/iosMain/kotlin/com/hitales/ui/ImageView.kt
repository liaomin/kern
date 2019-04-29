package com.hitales.ui

import com.hitales.utils.Frame

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
    actual constructor(frame: Frame) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual open var image: Image?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

}