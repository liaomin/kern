package com.hitales.ui.android

import android.graphics.Rect
import android.view.MotionEvent
import com.hitales.ui.Platform
import com.hitales.ui.View


open class AndroidView(val mView: View) : android.view.View(Platform.getApplication()){

    protected val mViewHelper:ViewHelper by lazy { ViewHelper(this, mView) }

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
        mViewHelper.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper.onDetachedFromWindow()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val adjust = mViewHelper.adjustTouchEvent(event)
        if(mViewHelper.interceptTouchEvent(event)){
            return false
        }
        val result =  super.dispatchTouchEvent(event) || mViewHelper.dispatchTouchEvent(event)
        if(adjust) {
            event.setLocation(x,y)
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewHelper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun getHitRect(outRect: Rect) {
        super.getHitRect(outRect)
        mViewHelper.adjustHitRect(outRect)
    }

}