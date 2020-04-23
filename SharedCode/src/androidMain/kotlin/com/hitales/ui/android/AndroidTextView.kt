package com.hitales.ui.android

import android.graphics.Rect
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.TextViewCompat
import com.hitales.ui.Platform
import com.hitales.ui.TextView

open class AndroidTextView(protected val mView: TextView) : AppCompatTextView(Platform.getApplication()){

    init {
        AndroidBridge.init(this,mView)
        gravity = Gravity.CENTER_VERTICAL
        setSingleLine()
        includeFontPadding = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        ellipsize = TextUtils.TruncateAt.END

    }

    fun enableAutoFontSizeToFit(minFontSize:Int,maxFontSize:Int){
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this,TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this,minFontSize,maxFontSize,1, TypedValue.COMPLEX_UNIT_SP)
    }

    fun disableAutoFontSizeToFit(){
        TextViewCompat.setAutoSizeTextTypeWithDefaults(this,TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        AndroidBridge.onAttachedToWindow(mView)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        AndroidBridge.onDetachedFromWindow(mView)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val adjust = AndroidBridge.adjustTouchEvent(mView,event)
        if(AndroidBridge.interceptTouchEvent(mView,event)){
            return false
        }
        val result =  super.dispatchTouchEvent(event) || AndroidBridge.dispatchTouchEvent(mView,event)
        if(adjust) {
            event.setLocation(x,y)
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        AndroidBridge.onTouchEvent(mView,event)
        return super.onTouchEvent(event)
    }

    override fun getHitRect(outRect: Rect) {
        super.getHitRect(outRect)
        AndroidBridge.adjustHitRect(mView,outRect)
    }


}