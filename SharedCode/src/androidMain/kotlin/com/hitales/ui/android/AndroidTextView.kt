package com.hitales.ui.android

import android.graphics.Rect
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.hitales.ui.Platform
import com.hitales.ui.TextView

class AndroidTextView(protected val mView: TextView) : AppCompatTextView(Platform.getApplication()){

    init {
        gravity = Gravity.CENTER_VERTICAL
        includeFontPadding = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        ellipsize = TextUtils.TruncateAt.END
    }



    protected val mViewHelper:ViewHelper by lazy { ViewHelper(this, mView) }

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