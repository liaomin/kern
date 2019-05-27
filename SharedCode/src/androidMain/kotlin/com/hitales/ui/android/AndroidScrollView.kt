package com.hitales.ui.android

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hitales.android.R
import com.hitales.ui.Colors
import com.hitales.ui.Platform
import com.hitales.ui.ScrollView


open class AndroidScrollView : DampScrollView {

    var mView: ScrollView? = null

    constructor(view: ScrollView) : super(Platform.getApplication()) {
        this.mView = view
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    companion object {
        fun fromXLM(view: ScrollView):AndroidScrollView{
            val  scrollView = LayoutInflater.from(Platform.getApplication()).inflate(R.layout.scroll_view,null) as AndroidScrollView
            scrollView.mView = view
            return scrollView
        }
    }

    private var mFrameLayout = object : FrameLayout(Platform.getApplication()) {
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
        mFrameLayout.setBackgroundColor(Colors.CLEAR)
        super.addView(mFrameLayout, -1, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mView?.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mView?.onDetachedFromWindow()
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

}