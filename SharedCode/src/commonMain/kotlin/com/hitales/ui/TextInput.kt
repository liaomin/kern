package com.hitales.ui

import com.hitales.utils.Frame

expect open class TextInput : TextView {

    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())

    open var placeholder:CharSequence?

    open var placeholderColor:Int

    var autoFocus:Boolean

    open fun focus()

    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)

    open fun cleanFocus()


}