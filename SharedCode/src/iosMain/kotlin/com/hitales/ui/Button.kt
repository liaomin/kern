package com.hitales.ui

import com.hitales.utils.Frame
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*
import platform.objc.sel_registerName


actual open class Button :  com.hitales.ui.TextView {

    actual  var onPressListener:((view:View)->Unit)? = null

    actual  var onLongPressListener:((view:View)->Unit)? = null

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getIOSWidget()
        widget.setTitle(text?.toString(),UIControlStateNormal)
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

    override fun initWidget(text: CharSequence?, frame: Frame) {

    }

    override fun createWidget(): UIView {
        return UIButton.buttonWithType(UIButtonTypeCustom)
    }

    override fun getWidget(): UILabel {
        return getIOSWidget().titleLabel!!
    }

    override fun getIOSWidget(): UIButton {
        return mWidget as UIButton
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {

    }

    actual open fun setImage(image: Image, state: ViewState) {
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
//        textColorList.setColorForState(color,state)
    }

    override fun setWidgetFrame(value: Frame) {
        getIOSWidget().setFrame(CGRectMake(value.x.toDouble(),value.y.toDouble(),value.width.toDouble(),value.height.toDouble()))
    }

    override fun setBackgroundColor(color: Int) {
        getIOSWidget().backgroundColor = color.toUIColor()
    }


}
