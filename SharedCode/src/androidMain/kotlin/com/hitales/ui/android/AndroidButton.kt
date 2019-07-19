package com.hitales.ui.android

import android.graphics.Canvas
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.hitales.ui.Button
import com.hitales.ui.Platform

class AndroidButton(protected val mView: Button) : AppCompatButton(Platform.getApplication()){

    protected val mViewHelper = ViewHelper(this,mView)

    init {
        gravity = Gravity.CENTER
    }

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