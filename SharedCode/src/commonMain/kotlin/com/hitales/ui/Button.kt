package com.hitales.ui

expect open class Button : TextView {

    open var isEnabled:Boolean

    constructor(text:CharSequence? = null,layoutParams: LayoutParams = LayoutParams())

    open fun setBackgroundColor(color:Int,state: ViewState = ViewState.NORMAL)

    open fun setBackgroundImage(image:Image,state: ViewState = ViewState.NORMAL)

    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)

}