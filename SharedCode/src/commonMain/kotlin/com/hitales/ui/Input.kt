package com.hitales.ui

import com.hitales.utils.Frame

expect open class Input : TextView {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open var placeholderText:CharSequence?
    open var placeholderTextColor:Int
    var autoFocus:Boolean
    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)
    open fun cleanFocus()
}