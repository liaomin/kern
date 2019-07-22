package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent
import android.widget.ImageView
import com.hitales.ui.Image
import com.hitales.ui.Platform

class AndroidImageView : ImageView {

    val mViewHelper:ViewHelper

    private val mView:com.hitales.ui.ImageView

    constructor(view:com.hitales.ui.ImageView):super(Platform.getApplication()){
        scaleType = ScaleType.FIT_CENTER
        mView = view
        mViewHelper = ViewHelper(this,mView)
    }

    override fun onDraw(canvas: Canvas) {
        val bg = mView.mBackground
        if(bg != null && (bg.haveBorderRadius() || bg.shadowRadius >= 0f)){
            val saveCount = canvas.save()
            canvas.clipPath(bg.getOuterPath(canvas.width.toFloat(),canvas.height.toFloat()))
            super.onDraw(canvas)
            canvas.restoreToCount(saveCount)
        }else{
            super.onDraw(canvas)
        }
    }

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