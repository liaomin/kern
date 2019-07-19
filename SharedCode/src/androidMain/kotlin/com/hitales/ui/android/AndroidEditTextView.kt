package com.hitales.ui.android

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.hitales.ui.Button
import com.hitales.ui.Platform
import com.hitales.ui.TextInput


class AndroidEditTextView(protected val mView: TextInput) : EditText(Platform.getApplication()) {
//    open class AndroidEditTextView(private val view: TextInput) : AppCompatEditText(Platform.getApplication()){

    protected val mViewHelper = ViewHelper(this,mView)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewHelper.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        mViewHelper.dispatchDraw(canvas)
        super.dispatchDraw(canvas)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if(mViewHelper.interceptTouchEvent(event)){
            return false
        }
        return mViewHelper.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewHelper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}