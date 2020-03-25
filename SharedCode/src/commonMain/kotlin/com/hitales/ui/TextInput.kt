package com.hitales.ui

expect open class TextInput : TextView {

    constructor(text:CharSequence? = null,layoutParams: LayoutParams = LayoutParams())

    open var placeholder:CharSequence?

    /**
     * default #666666
     */
    open var placeholderColor:Int

    /**
     * default false
     */
    open var autoFocus:Boolean

    /**
     * default true
     */
    open var singleLine:Boolean

    open fun focus()

    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)

    open fun cleanFocus()


}