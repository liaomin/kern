package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.hitales.ui.Colors
import com.hitales.ui.Touches
import com.hitales.ui.View
import com.hitales.ui.ViewGroup
import com.hitales.ui.utils.PixelUtil


enum class ViewStyle{
    ANDROID,
    IOS
}


class ViewHelper {

    var mView:View

    constructor(androidView: android.view.View,view:View){
        mView = view
//        androidView.setBackgroundColor(Colors.WHITE)
        androidView.tag = view
        if(androidView is android.view.ViewGroup){
            androidView.clipToPadding = false
        }
    }

    fun onAttachedToWindow() {
        mView.onAttachedToWindow()
    }

    fun onDetachedFromWindow() {
        mView.onDetachedFromWindow()
    }

    fun dispatchDraw(canvas: Canvas) {

    }

    fun onMeasure(width:Int,height:Int){
        mView.frame.width = PixelUtil.toDIPFromPixel(width.toFloat())
        mView.frame.height = PixelUtil.toDIPFromPixel(height.toFloat())
    }

    fun dispatchTouchEvent(ev:MotionEvent):Boolean{
        val touches = Touches(ev)
        return mView.dispatchTouchEvent(touches)
    }


    fun interceptTouchEvent(ev: MotionEvent): Boolean {
        val padding = mView.innerPadding
        if(padding != null && ev.action == MotionEvent.ACTION_DOWN){
            val x = ev.x
            val y = ev.y
            val widget = mView.getWidget()
            val width = widget.width
            val height = widget.height
            if( x < -padding.left || x > width - padding.right  || y < -padding.top || y > height - padding.bottom ){
                return true
            }
        }
        return false
    }

    fun onTouchEvent(ev:MotionEvent){
        val touches = Touches(ev)
        when (ev.action){
            MotionEvent.ACTION_DOWN -> mView.touchesBegan(touches)
            MotionEvent.ACTION_MOVE -> mView.touchesMoved(touches)
            MotionEvent.ACTION_UP -> mView.touchesEnded(touches)
            MotionEvent.ACTION_CANCEL -> mView.touchesCancelled(touches)
        }
    }

}