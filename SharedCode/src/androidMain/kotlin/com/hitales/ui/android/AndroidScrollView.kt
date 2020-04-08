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

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    companion object {
        fun createFromXLM(view: ScrollView):AndroidScrollView{
            val  scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.scroll_view,null) as AndroidScrollView
            scrollView.mView = view
            AndroidBridge.init(scrollView,view)
            scrollView.setup(view)
            return scrollView
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        val v = mView
        if(v != null){
            v.frame.setSize(PixelUtil.toDIPFromPixel(measuredWidth),PixelUtil.toDIPFromPixel(measuredHeight))
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        val scrollX = PixelUtil.toDIPFromPixel(getScrolledX().toFloat())
        val scrollY = PixelUtil.toDIPFromPixel(getScrolledY().toFloat())
        mView?.onScroll(scrollX,scrollY)
    }

    override fun onBeginScrolling(){
        mView?.onBeginScrolling()
    }

    override fun onEndScrolling(){
        mView?.onEndScrolling()
    }

    override fun onBeginDragging(){
        mView?.onBeginDragging()
    }

    override fun onEndDragging(){
        mView?.onEndDragging()
    }

    override fun onBeginDecelerating(){
        mView?.onBeginDecelerating()
    }

    override fun onEndDecelerating(){
        mView?.onEndDecelerating()
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
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
}