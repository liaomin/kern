package com.hitales.ui

import com.hitales.utils.Frame


actual open class TextInput : com.hitales.ui.TextView {
    actual constructor(text: CharSequence?, frame: Frame) {

    }

    actual open var placeholder: CharSequence?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual open var placeholderColor: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual var autoFocus: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual open fun focus() {
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
    }

    actual open fun cleanFocus() {
    }


}