package com.hitales.ui.android

import android.graphics.Rect
import android.view.MotionEvent
import com.hitales.ui.Touches
import com.hitales.ui.View
import com.hitales.ui.utils.PixelUtil

class ViewHelper {

    var mView:View

    constructor(androidView: android.view.View,view:View){
        mView = view
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

    fun dispatchTouchEvent(ev:MotionEvent):Boolean {
        val touches = Touches(ev)
        return mView.dispatchTouchEvent(touches)
    }

    fun adjustTouchEvent(ev: MotionEvent): Boolean {
        val padding = mView.innerPadding
        if(padding != null){
            val x = ev.x + padding.left
            val y = ev.y + padding.top
            ev.setLocation(x,y)
            return true
        }
        return false
    }

    fun interceptTouchEvent(ev: MotionEvent): Boolean {
        val padding = mView.innerPadding
        if(padding != null){
            val x = ev.x
            val y = ev.y
            if(ev.action == MotionEvent.ACTION_DOWN){
                val widget = mView.getWidget()
                val width = PixelUtil.toPixelFromDIP(mView.frame.width)
                val height = PixelUtil.toPixelFromDIP(mView.frame.height)
                if( x < 0 || x > width   || y < 0 || y > height ){
                    return true
                }
            }
        }
        return false
    }

    fun onTouchEvent(ev:MotionEvent){
        val touches = Touches(ev)
        when (ev.action){
            MotionEvent.ACTION_DOWN -> mView.onTouchesBegan(touches)
            MotionEvent.ACTION_MOVE -> mView.onTouchesMoved(touches)
            MotionEvent.ACTION_UP -> mView.onTouchesEnded(touches)
            MotionEvent.ACTION_CANCEL -> mView.onTouchesCancelled(touches)
        }
    }

    fun adjustHitRect(rect: Rect){
        val padding = mView.innerPadding
        if(padding != null){
            rect.left += padding.left.toInt()
            rect.top += padding.top.toInt()
            rect.right += padding.right.toInt()
            rect.bottom += padding.bottom.toInt()
        }
    }

}