package com.hitales.ui.android

import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import com.hitales.ui.Platform
import com.hitales.ui.TextInput


class AndroidEditTextView constructor(val mView: TextInput) : AppCompatEditText(Platform.getApplication(),null, android.R.attr.editTextStyle) {
//    open class AndroidEditTextView(private val view: TextInput) : AppCompatEditText(Platform.getApplication()){

    private val mInputMethodManager: InputMethodManager = Platform.getApplication().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    init {
        AndroidBridge.init(this,mView)
        isFocusableInTouchMode = false
        setSingleLine(true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        postDelayed({
            isFocusableInTouchMode = true
        },0)
        AndroidBridge.onAttachedToWindow(mView)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        AndroidBridge.onDetachedFromWindow(mView)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val adjust = AndroidBridge.adjustTouchEvent(mView,event)
        if(AndroidBridge.interceptTouchEvent(mView,event)){
            return false
        }
        val result =  super.dispatchTouchEvent(event) || AndroidBridge.dispatchTouchEvent(mView,event)
        if(adjust) {
            event.setLocation(x,y)
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        AndroidBridge.onTouchEvent(mView,event)
        return super.onTouchEvent(event)
    }

    override fun getHitRect(outRect: Rect) {
        super.getHitRect(outRect)
        AndroidBridge.adjustHitRect(mView,outRect)
    }

    private fun showSoftKeyboard(): Boolean {
        return mInputMethodManager.showSoftInput(this, 0)
    }

    private fun hideSoftKeyboard() {
        if(mView.canHideSoftKeyboard()){
            mInputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        }
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {

        showSoftKeyboard()
        return super.requestFocus(direction, previouslyFocusedRect)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER && !isMultiline()) {
            hideSoftKeyboard()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

    private fun isMultiline(): Boolean {
        return inputType and InputType.TYPE_TEXT_FLAG_MULTI_LINE != 0
    }

    override fun clearFocus() {
        super.clearFocus()
        hideSoftKeyboard()
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        mView.onFocusChanged(focused)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        mView?.onSelectionChanged(selStart,selEnd)
    }

}