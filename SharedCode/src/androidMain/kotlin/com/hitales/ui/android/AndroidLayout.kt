package com.hitales.ui.android

import android.view.MotionEvent
import android.view.ViewGroup
import com.hitales.ui.Layout
import com.hitales.ui.Platform
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.ui.utils.PixelUtil

open class AndroidLayout(private val view: Layout) : ViewGroup(Platform.getApplication()){

    val mViewHelper:ViewHelper by lazy { ViewHelper(this, view) }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val w = PixelUtil.toDIPFromPixel(maxWidth)
        val h = PixelUtil.toDIPFromPixel(maxHeight)

        val layoutParams = view.layoutParams
        val frame = view.frame
        val oldWidth = layoutParams.width
        val oldHeight = layoutParams.height
        var changeWidth = false
        var changeHeight = false
        //没有设置宽高，在measure时又指定宽高，只有作为rootView才会出现
        if(layoutParams.width.isNaN() && widthMode == MeasureSpec.EXACTLY){
            layoutParams.width = w
            changeWidth = true
        }
        if(layoutParams.height.isNaN() && heightMode == MeasureSpec.EXACTLY){
            layoutParams.height = h
            changeHeight = true
        }

        val tempSize = FlexLayout.tempSize
        view.measure(w,h, tempSize)
        frame.width = tempSize.width
        frame.height = tempSize.height
        if(changeWidth){
            layoutParams.width = oldWidth
        }
        if(changeHeight){
            layoutParams.height = oldHeight
        }
        setMeasuredDimension(PixelUtil.toPixelFromDIP(frame.width).toInt(), PixelUtil.toPixelFromDIP(frame.height).toInt())
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (it in view.children) {
            it.onLayout()
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

}