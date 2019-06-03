package com.hitales.ui

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import com.hitales.ui.android.AndroidEditTextView
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame




actual open class TextInput : com.hitales.ui.TextView {

    actual var autoFocus:Boolean = false

    actual constructor(text: CharSequence?, frame: Frame):super(text,frame){
        padding = EdgeInsets(10f,15f,10f,15f)
        numberOfLines = 1
    }

    override fun createWidget(): AndroidEditTextView {
        return AndroidEditTextView(this)
//        return AndroidEditTextView.fromXLM(this)
    }

    override fun getWidget(): AndroidEditTextView {
        return super.getWidget() as AndroidEditTextView
    }

    actual open var placeholder: CharSequence?
        get() = getWidget().hint
        set(value) {
            getWidget().hint = value
        }

    actual open var placeholderColor: Int
        get() = getWidget().currentHintTextColor
        set(value) {
            getWidget().highlightColor = value
        }

    actual open fun setTextColor(color: Int, state: ViewState) {
        textColorList.setColorForState(color,state)
    }

    actual open fun cleanFocus() {
        mWidget.clearFocus()
    }

    actual open fun focus() {
        mWidget.requestFocus()
    }


}