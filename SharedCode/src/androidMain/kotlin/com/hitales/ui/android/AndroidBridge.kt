package com.hitales.ui.android

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import com.hitales.ui.Layout
import com.hitales.ui.Touches
import com.hitales.ui.View
import com.hitales.ui.utils.PixelUtil

class AndroidBridge {

    companion object{

        fun init(androidView: android.view.View,view:View){
            androidView.tag = view
            if(androidView is android.view.ViewGroup){
                androidView.clipToPadding = false
                androidView.clipChildren = false
            }
        }

        fun onAttachedToWindow(mView:View) {
            mView.onAttachedToWindow()
        }

        fun onDetachedFromWindow(mView:View) {
            mView.onDetachedFromWindow()
        }

        fun dispatchTouchEvent(mView:View,ev:MotionEvent):Boolean {
            val touches = Touches(ev)
            return mView.dispatchTouchEvent(touches)
        }

        fun adjustTouchEvent(mView:View,ev: MotionEvent): Boolean {
            val padding = mView.innerPadding
            if(padding != null){
                val x = ev.x + padding.left
                val y = ev.y + padding.top
                ev.setLocation(x,y)
                return true
            }
            return false
        }

        fun interceptTouchEvent(mView:View,ev: MotionEvent): Boolean {
            val padding = mView.innerPadding
            if(padding != null){
                val x = ev.x
                val y = ev.y
                if(ev.action == MotionEvent.ACTION_DOWN){
                    val width = PixelUtil.toPixelFromDIP(mView.frame.width)
                    val height = PixelUtil.toPixelFromDIP(mView.frame.height)
                    if( x < 0 || x > width   || y < 0 || y > height ){
                        return true
                    }
                }
            }
            return false
        }

        fun onTouchEvent(mView:View,ev:MotionEvent){
            val touches = Touches(ev)
            when (ev.action){
                MotionEvent.ACTION_DOWN -> mView.onTouchesBegan(touches)
                MotionEvent.ACTION_MOVE -> mView.onTouchesMoved(touches)
                MotionEvent.ACTION_UP -> mView.onTouchesEnded(touches)
                MotionEvent.ACTION_CANCEL -> mView.onTouchesCancelled(touches)
            }
        }

        fun adjustHitRect(mView:View,rect: Rect){
            val padding = mView.innerPadding
            if(padding != null){
                rect.left += padding.left.toInt()
                rect.top += padding.top.toInt()
                rect.right += padding.right.toInt()
                rect.bottom += padding.bottom.toInt()
            }
        }

        fun dispatchDraw(mView:Layout,androidView: android.view.View,canvas: Canvas){
            try {
                if(mView.clipsToBounds){
                    val bg = mView.mBackground
                    if(bg != null){
                        var width = androidView.width.toFloat()
                        var height = androidView.height.toFloat()
                        val off = bg.offset
                        if(off != null){
                            canvas.translate(-off.x,-off.y)
                            width = off.width
                            height = off.height
                        }
                        canvas.clipPath(bg.getOuterPath(width,height))
                        if(off != null) {
                            canvas.translate(off.x, off.y)
                        }
                    }else{
                        canvas.clipRect(Rect(0,0,androidView.width,androidView.height))
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }
}