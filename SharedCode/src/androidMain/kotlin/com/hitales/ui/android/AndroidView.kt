package com.hitales.ui.android

import android.graphics.Canvas
import android.view.MotionEvent
import com.hitales.ui.Platform
import com.hitales.ui.View


class AndroidView(val mView: View) : android.view.View(Platform.getApplication()){

    val mViewHelper = ViewHelper(this,mView)

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