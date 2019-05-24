package com.hitales.ui.android

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.ui.Platform
import com.hitales.ui.View

open class AndroidView(val mView: View) : android.view.View(Platform.getApplication()){

//    protected val mViewHelper = ViewHelper(this,mView)


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mView.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView.onDetachedFromWindow()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
    }

}