package com.hitales.ui.android

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.android.R
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.ScrollView
import com.hitales.ui.utils.PixelUtil
import android.view.MotionEvent
import com.hitales.utils.Size
import com.hitales.utils.Timer


class AndroidScrollView : DampScrollView {

    var scrollEnabled = true

    var mView: ScrollView? = null

    var mViewHelper :ViewHelper? = null

    constructor(view: ScrollView) : super(Platform.getApplication()) {
        mView = view
        mViewHelper = ViewHelper(this,view)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    companion object {
        fun fromXLM(view: ScrollView):AndroidScrollView{
            val  scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.scroll_view,null) as AndroidScrollView
            scrollView.mView = view
            scrollView.mViewHelper = ViewHelper(scrollView,view)
            return scrollView
        }
    }

    var contentSize: Size = Size()
        set(value) {
            field = value
            mFrameLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }

    private var mFrameLayout = object:FrameLayout(Platform.getApplication()){

        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            if(contentSize.width > 0 && contentSize.height > 0){
                setMeasuredDimension(PixelUtil.toPixelFromDIP(contentSize.width).toInt(),PixelUtil.toPixelFromDIP(contentSize.height).toInt())
            }
        }

    }

    init {
        mFrameLayout.setBackgroundColor(Colors.CLEAR)
        super.addView(mFrameLayout, -1, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        mFrameLayout.isFocusable = true
        mFrameLayout.isFocusableInTouchMode = true
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        val scrollX = PixelUtil.toDIPFromPixel(l.toFloat())
        val scrollY = PixelUtil.toDIPFromPixel(t.toFloat())
        mView?.onScroll(scrollX,scrollY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mView?.layoutSubviews()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun addView(child: View?) {
        mFrameLayout.addView(child)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        mFrameLayout.addView(child, params)
    }

    override fun addView(child: View?, index: Int) {
        mFrameLayout.addView(child, index)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        mFrameLayout.addView(child, width, height)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        mFrameLayout.addView(child, index, params)
    }

    override fun removeView(view: View?) {
        mFrameLayout.removeView(view)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mViewHelper?.onTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                // if we can scroll pass the event to the superclass
                return if (scrollEnabled) super.onTouchEvent(ev) else scrollEnabled
                // only continue to handle the touch event if scrolling enabled
                // scrollable is always false at this point
            }
            else -> return super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!scrollEnabled)
            false
        else
            super.onInterceptTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewHelper?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper?.onDetachedFromWindow()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        mViewHelper?.draw(canvas)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return mViewHelper!!.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
    }

}