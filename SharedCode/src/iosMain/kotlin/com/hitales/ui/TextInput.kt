package com.hitales.ui

import com.hitales.ui.ios.IOSInputView
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import platform.Foundation.NSSelectorFromString
import platform.UIKit.UILabel
import platform.UIKit.UITextView
import platform.UIKit.UIView


actual open class TextInput : com.hitales.ui.TextView {

    actual constructor(text: CharSequence?, frame: Frame):super(text, frame) {

    }

    override fun createWidget(): IOSInputView {
        return IOSInputView(WeakReference(this))
    }

    override fun getTextWidget(): UILabel {
        return super.getTextWidget()
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