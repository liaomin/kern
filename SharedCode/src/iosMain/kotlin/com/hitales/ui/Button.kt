package com.hitales.ui

import com.hitales.utils.Frame
import com.hitales.utils.runOnUiThread
import platform.CoreGraphics.CGRectMake
import platform.UIKit.*
import platform.objc.sel_registerName
import kotlin.system.getTimeMillis


actual open class Button :  com.hitales.ui.TextView {

    private var touchDownTime:Long = 0

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getIOSWidget()
        widget.setTitle(text?.toString(),UIControlStateNormal)
        setTextColor(0xFF0000FF.toInt())
//        widget.addTarget(this, sel_registerName("touchDown"),UIControlEventTouchDown)
//        widget.addTarget(this, sel_registerName("touchUpInside"),UIControlEventTouchUpInside)
//        widget.addTarget(this, sel_registerName("touchUpOutside"),UIControlEventTouchUpOutside)
    }

    fun touchDown(){
        touchDownTime = getTimeMillis()
        runOnUiThread(800){
            if(touchDownTime > 0 && onLongPressListener != null){
                //点击事件没有释放
                releaseTouchEvent()
                onLongPressListener?.invoke(this)
            }
        }
    }

    fun touchUpInside(){
        if(touchDownTime > 0){
            onPressListener?.invoke(this)
        }
        releaseTouchEvent()
    }

    fun touchUpOutside(){
        releaseTouchEvent()
    }

    private fun releaseTouchEvent(){
        touchDownTime = 0
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

    actual open fun setTextColor(color: Int, state: ViewState) {
//        textColorList.setColorForState(color,state)
    }

    override fun setWidgetFrame(value: Frame) {
        getIOSWidget().setFrame(CGRectMake(value.x.toDouble(),value.y.toDouble(),value.width.toDouble(),value.height.toDouble()))
    }

    override fun setBackgroundColor(color: Int) {
        getIOSWidget().backgroundColor = color.toUIColor()
    }


    actual open fun setBackgroundImage(image: Image, state: ViewState) {
    }

}
