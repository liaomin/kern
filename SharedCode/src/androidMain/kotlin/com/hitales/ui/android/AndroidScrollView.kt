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


open class AndroidScrollView : com.hitales.ui.android.scrollview.ScrollView {

    var mView: ScrollView? = null

    var mViewHelper :ViewHelper? = null

    constructor(view: ScrollView) : super() {
        mView = view
        mViewHelper = ViewHelper(this,view)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    companion object {
        fun createFromXLM(view: ScrollView):AndroidScrollView{
            val  scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.scroll_view,null) as AndroidScrollView
            scrollView.mView = view
            scrollView.mViewHelper = ViewHelper(scrollView,view)
            return scrollView
        }
    }

    var contentSize: Size = Size()
        set(value) {
            field = value
//            mFrameLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        }


    init {
//        mFrameLayout.setBackgroundColor(Colors.CLEAR)
//        super.addView(mFrameLayout, -1, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
//        mFrameLayout.isFocusable = true
//        mFrameLayout.isFocusableInTouchMode = true
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        val scrollX = PixelUtil.toDIPFromPixel(l.toFloat())
        val scrollY = PixelUtil.toDIPFromPixel(t.toFloat())
        mView?.onScroll(scrollX,scrollY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewHelper?.onMeasure(measuredWidth,measuredHeight)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mViewHelper?.onTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                return if (scrollEnabled) super.onTouchEvent(ev) else scrollEnabled
            }
            else -> return super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (!scrollEnabled) false else super.onInterceptTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewHelper?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper?.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        mViewHelper?.dispatchDraw(canvas)
        super.dispatchDraw(canvas)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return mViewHelper!!.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
    }

}