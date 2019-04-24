package com.hitales.ui

import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame

expect open class View  {
    /**
     * use margin and padding to calculate frame
     */
    var padding:EdgeInsets

    /**
     * use margin and padding to calculate frame
     */
    var margin:EdgeInsets

    /**
     * layout params
     */
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
    var onLongPressListener:((iew:View)->Unit)?
}


