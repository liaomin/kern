package com.hitales.liam.ui

import com.hitales.liam.utils.Frame

expect open class View  {
    var frame:Frame
    constructor(frame: Frame = Frame.zero())
    var superView:View?
    fun setId(id:Int)
    fun getId():Int
    fun setTag(tag:Any?)
    fun getTag():Any?
    /**
     * argb color
     */
    fun setBackgroundColor(color:Long)
//    fun removeFromSuperView()
}


expect open class TextView : View {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open fun setText(text:CharSequence?)
    open fun getText():CharSequence?
}


expect open class Button : TextView {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    var onPressListener:((iew:View)->Unit)?
    var onLongPressListener:((iew:View)->Unit)?}


