package com.hitales.ui

import com.hitales.utils.Frame
import platform.UIKit.UIButton
import platform.UIKit.UIControlEventTouchDown
import platform.UIKit.UILabel
import platform.UIKit.UIResponder
import platform.objc.sel_registerName



fun touchDown(){
    println("button touch down 22")
}

actual open class Button :  com.hitales.ui.TextView {

    actual  var onPressListener:((view:View)->Unit)? = null

    actual  var onLongPressListener:((view:View)->Unit)? = null

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getIOSWidget()
        widget.titleLabel?.text = text?.toString()
        setTextColor(0xFF0000FF.toInt())
        widget.addTarget(this, sel_registerName("touchDown"),UIControlEventTouchDown)
//        widget.setOnClickListener{
//            onPressListener?.invoke(this)
//        }
//        widget.setOnLongClickListener{
//            onLongPressListener?.invoke(this)
//            if(onLongPressListener != null){
//                return@setOnLongClickListener true
//            }
//            return@setOnLongClickListener false
//        }
    }

    fun touchDown(){
        println("button touch down")
        onPressListener?.invoke(this)
    }



    override fun createWidget(): UIResponder {
        return UIButton()
    }

    override fun getWidget(): UILabel {
        return getIOSWidget().titleLabel!!
    }

    private fun getIOSWidget(): UIButton {
        return mWidget as UIButton
    }



    actual open fun setBackgroundColor(color: Int, state: ViewState) {
    }

    actual open fun setImage(image: Image, state: ViewState) {
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
//        textColorList.setColorForState(color,state)
    }



}
