package com.hitales.ui

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.ui.android.DampScrollView
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame

open class AndroidScrollView(val view: ScrollView) : DampScrollView(Platform.getApplication()) {

    var frameLayout = object : FrameLayout(Platform.getApplication()) {
        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            var maxWidth = 0
            var maxHeight = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val params = child.layoutParams as FrameLayout.LayoutParams
                val width = params.leftMargin + params.width
                val height = params.topMargin + params.height
                maxWidth = Math.max(maxWidth, width)
                maxHeight = Math.max(maxHeight, height)
            }
            setMeasuredDimension(
                Math.max(measuredWidth, paddingLeft + paddingRight + maxWidth),
                Math.max(measuredHeight, paddingTop + paddingBottom + maxHeight)
            )
        }
    }

    init {
        frameLayout.setBackgroundColor(Colors.CLEAR)
        super.addView(
            frameLayout,
            -1,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        )
        setVerticalScrollBarEnabled(true)
//        setBackgroundColor(Color.GREEN)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        view.onDetachedFromWindow()
    }

    override fun addView(child: View?) {
        frameLayout.addView(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        frameLayout.addView(child, params)
    }

    override fun addView(child: View?, index: Int) {
        frameLayout.addView(child, index)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        frameLayout.addView(child, width, height)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        frameLayout.addView(child, index, params)
    }

}

actual open class ScrollView : com.hitales.ui.ViewGroup {


    actual constructor(frame: Frame) : super(frame)

    override fun createWidget(): android.widget.FrameLayout {
        return AndroidScrollView(this)
    }

    override fun getWidget(): android.widget.FrameLayout {
        return super.getWidget()
    }

}