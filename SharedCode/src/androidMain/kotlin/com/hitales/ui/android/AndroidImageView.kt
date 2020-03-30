package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import android.widget.ImageView
import com.hitales.ui.Platform

class AndroidImageView : ImageView {

    private val mView:com.hitales.ui.ImageView

    constructor(view:com.hitales.ui.ImageView):super(Platform.getApplication()){
        scaleType = ScaleType.FIT_CENTER
        mView = view
        AndroidBridge.init(this,mView)
    }

    override fun onDraw(canvas: Canvas) {
        val bg = mView.mBackground
        if(bg != null && (bg.haveBorderRadius() || bg.shadowRadius >= 0f)){
            val saveCount = canvas.save()
            canvas.clipPath(bg.getOuterPath(width.toFloat(),height.toFloat()))
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
        }else{
            super.onDraw(canvas)
        }
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