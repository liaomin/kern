package com.hitales.ui.android

import android.graphics.Rect
import android.view.Gravity
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton
import com.hitales.ui.Button
import com.hitales.ui.Platform

class AndroidButton(val mView: Button) : AppCompatButton(Platform.getApplication()){

    init {
        AndroidBridge.init(this,mView)
        gravity = Gravity.CENTER
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



}