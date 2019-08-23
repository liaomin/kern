package com.hitales.ui

import com.hitales.ui.android.AndroidEditTextView
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame


actual open class TextInput : com.hitales.ui.TextView {

    actual open var autoFocus:Boolean = false

    actual open var singleLine:Boolean = true
        set(value) {
            field = value
            if (value){
                numberOfLines = 1
            }else{
                numberOfLines = 0
            }
        }

    actual constructor(text: CharSequence?, frame: Frame):super(text,frame){
        padding = EdgeInsets(10f,15f,10f,15f)
        placeholderColor = 0xFF666666.toInt()
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