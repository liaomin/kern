package com.hitales.ui.android

import android.view.MotionEvent
import com.hitales.ui.Button
import com.hitales.ui.Platform

open class AndroidButton(protected val mView: Button) : android.widget.Button(Platform.getApplication()){

    protected val mViewHelper = ViewHelper(this,mView)


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mView.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView.onDetachedFromWindow()
    }
}