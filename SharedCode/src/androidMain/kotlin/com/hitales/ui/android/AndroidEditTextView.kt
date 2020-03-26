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
//        val editText = this
//        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
////文本显示的位置在EditText的最上方
//        editText.setGravity(Gravity.CENTER_VERTICAL)
////改变默认的单行模式
//        editText.setSingleLine(false)
////水平滚动设置为False
//        editText.setHorizontallyScrolling(false)
        setSingleLine(true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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
        mInputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        isFocusableInTouchMode = true
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
        isFocusableInTouchMode = false
        super.clearFocus()
        hideSoftKeyboard()
    }


}