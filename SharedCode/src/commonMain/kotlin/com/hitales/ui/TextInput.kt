package com.hitales.ui

import com.hitales.utils.WeakReference

interface TextInputDelegate : ViewDelegate {
    fun onTextChanged(view: TextInput,s: CharSequence)
    fun onFocusChanged(view: TextInput,focused: Boolean)
    fun onSelectionChanged(view: TextInput,selStart: Int, selEnd: Int)
    fun shouldChangeText(view: TextInput,beforeText: CharSequence,start:Int,length:Int,replaceText: CharSequence):Boolean
}

expect open class TextInput : TextView {

    constructor(text:CharSequence? = null,layoutParams: LayoutParams? = null)

    var nextInput:WeakReference<TextInput>?

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