package com.hitales.ui.android

import android.graphics.Canvas
import android.view.MotionEvent
import com.hitales.ui.Touches
import com.hitales.ui.View
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

    fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int){
        if(changed){
            val frame = mView.frame
            frame.x = left.toFloat()
            frame.y = top.toFloat()
            frame.width = PixelUtil.toDIPFromPixel((right - left).toFloat())
            frame.height = PixelUtil.toDIPFromPixel((bottom - top).toFloat())
        }

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