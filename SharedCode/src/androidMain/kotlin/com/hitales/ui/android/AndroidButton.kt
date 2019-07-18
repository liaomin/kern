package com.hitales.ui.android

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
        mView.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView.onDetachedFromWindow()
    }
}