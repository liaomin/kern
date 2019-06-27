package com.hitales.ui.android

import android.graphics.Canvas
import android.view.MotionEvent
import com.hitales.ui.Colors
import com.hitales.ui.View

class ViewHelper(val androidView: android.view.View,val view:View) {

    init {
        androidView.setBackgroundColor(Colors.TRANSPARENT)
    }

    fun onAttachedToWindow() {
        view.onAttachedToWindow()
    }

    fun onDetachedFromWindow() {
        view.onDetachedFromWindow()
    }

    fun draw(canvas: Canvas) {

    }



}