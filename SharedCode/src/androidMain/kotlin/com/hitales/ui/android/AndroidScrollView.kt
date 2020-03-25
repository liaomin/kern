package com.hitales.ui.android

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import com.hitales.android.R
import com.hitales.ui.Platform
import com.hitales.ui.ScrollView
import com.hitales.ui.utils.PixelUtil


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
            scrollView.setup(view)
            return scrollView
        }
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        val scrollX = PixelUtil.toDIPFromPixel(getScrolledX().toFloat())
        val scrollY = PixelUtil.toDIPFromPixel(getScrolledY().toFloat())
        mView?.onScroll(scrollX,scrollY)
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return mViewHelper!!.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
    }
}