package com.hitales.ui.android

import android.graphics.Rect
import android.view.MotionEvent
import com.hitales.ui.Platform
import com.hitales.ui.View


open class AndroidView(val mView: View) : android.view.View(Platform.getApplication()){

    init {
        AndroidBridge.init(this,mView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize= MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if(widthMode != MeasureSpec.EXACTLY){
            widthSize = 0
        }
        if(heightMode != MeasureSpec.EXACTLY){
            heightSize = 0
        }
        setMeasuredDimension(widthSize,heightSize)
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