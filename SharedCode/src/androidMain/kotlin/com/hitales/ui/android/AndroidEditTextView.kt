package com.hitales.ui.android

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.hitales.ui.Platform
import com.hitales.ui.TextInput


open class AndroidEditTextView : EditText {
//    open class AndroidEditTextView(private val view: TextInput) : AppCompatEditText(Platform.getApplication()){

    var mView: TextInput? = null

    constructor(view: TextInput):super(Platform.getApplication()){
        mView = view
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        EditorInfo.TYPE_CLASS_NUMBER
        mView?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView?.onDetachedFromWindow()
    }

}