package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.drawable.RippleDrawable
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.ui.Platform
import com.hitales.ui.View

open class AndroidView(val mView: View) : android.view.View(Platform.getApplication()){

    val mViewHelper = ViewHelper(this,mView)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewHelper.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper.onDetachedFromWindow()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        mViewHelper.draw(canvas)
    }

}