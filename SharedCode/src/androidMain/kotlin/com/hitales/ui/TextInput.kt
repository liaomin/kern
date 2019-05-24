package com.hitales.ui

import com.hitales.utils.Frame


open class AndroidEditTextView(private val view:TextInput) : android.widget.EditText(Platform.getApplication()){

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        view.onDetachedFromWindow()
    }

}


actual open class TextInput : com.hitales.ui.TextView {

    actual var autoFocus:Boolean = false

    actual constructor(text: CharSequence?, frame: Frame):super(text,frame)

    override fun createWidget(): android.widget.EditText {
        return AndroidEditTextView(this)
    }

    override fun getWidget(): android.widget.EditText {
        return super.getWidget() as android.widget.EditText
    }

    actual open var placeholderText: CharSequence?
        get() = getWidget().hint
        set(value) {
            getWidget().hint = value
        }

    actual open var placeholderTextColor: Int
        get() = getWidget().currentHintTextColor
        set(value) {
            getWidget().highlightColor = value
        }

    actual open fun setTextColor(color: Int, state: ViewState) {
        textColorList.setColorForState(color,state)
    }

    actual open fun cleanFocus() {
        getWidget().clearFocus()
    }



}