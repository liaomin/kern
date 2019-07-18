package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.hitales.ui.Colors
import com.hitales.ui.Touches
import com.hitales.ui.View
import com.hitales.ui.ViewGroup
import com.hitales.ui.utils.PixelUtil

class ViewHelper {

    var mView:View

    constructor(androidView: android.view.View,view:View){
        mView = view
        androidView.setBackgroundColor(Colors.TRANSPARENT)
    }

    fun onAttachedToWindow() {
        mView.onAttachedToWindow()
    }

    fun onDetachedFromWindow() {
        mView.onDetachedFromWindow()
    }

    fun draw(canvas: Canvas) {
        if(mView is ViewGroup){
            drawShadow(mView as ViewGroup,canvas)
        }
    }

    fun dispatchTouchEvent(ev:MotionEvent):Boolean{
        val touches = Touches(ev)
        return mView.dispatchTouchEvent(touches)
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


    fun drawShadow(viewGroup: ViewGroup,canvas: Canvas){
//        viewGroup.children.forEach {
//            val background = it.mBackground
//            if(background != null && background.shadowRadius > 0f){
//                val x = it.getWidget().left.toFloat()
//                val y = it.getWidget().top.toFloat()
//                canvas.translate(x,y)
//                val paint = background.mPaint
//                paint.setShadowLayer(PixelUtil.toPixelFromDIP(background.shadowRadius),PixelUtil.toPixelFromDIP(background.shadowDx),PixelUtil.toPixelFromDIP(background.shadowDy),background.shadowColor)
//                paint.style = Paint.Style.FILL
//                paint.color = Colors.RED
//                canvas.drawPath(background.shadowPath,background.mPaint)
//                paint.clearShadowLayer()
//                canvas.translate(-x,-y)
//            }
//        }
    }
}