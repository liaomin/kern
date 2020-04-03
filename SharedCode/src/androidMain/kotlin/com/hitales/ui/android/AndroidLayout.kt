package com.hitales.ui.android

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.hitales.ui.Layout
import com.hitales.ui.MeasureMode
import com.hitales.ui.Platform
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Size

inline fun Int.toMeasureMode():MeasureMode{
    when(this){
        View.MeasureSpec.EXACTLY -> return MeasureMode.EXACTLY
        View.MeasureSpec.AT_MOST -> return MeasureMode.AT_MOST
        View.MeasureSpec.UNSPECIFIED -> return MeasureMode.UNSPECIFIED

    }
    return MeasureMode.AT_MOST
}

open class AndroidLayout(val mView: Layout) : ViewGroup(Platform.getApplication()){

    init {
        AndroidBridge.init(this,mView)
    }

    val tempSize:Size by lazy { Size() }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var w = PixelUtil.toDIPFromPixel(maxWidth)
        var h = PixelUtil.toDIPFromPixel(maxHeight)

        val layoutParams = mView.layoutParams!!
        val frame = mView.frame
        val oldWidth = layoutParams.width
        val oldHeight = layoutParams.height
        var changeWidth = false
        var changeHeight = false
        //没有设置宽高，在measure时又指定宽高，只有作为rootView才会出现
        if(layoutParams.flag and com.hitales.ui.LayoutParams.FLAG_WIDTH_MASK != com.hitales.ui.LayoutParams.FLAG_WIDTH_MASK && widthMode == MeasureSpec.EXACTLY){
            layoutParams.width = w
            changeWidth = true
        }
        if(layoutParams.flag and com.hitales.ui.LayoutParams.FLAG_HEIGHT_MASK != com.hitales.ui.LayoutParams.FLAG_HEIGHT_MASK && heightMode == MeasureSpec.EXACTLY){
            layoutParams.height = h
            changeHeight = true
        }
        mView.measure(w,widthMode.toMeasureMode(),h,heightMode.toMeasureMode(), tempSize)
        frame.width = tempSize.width
        frame.height = tempSize.height
        if(changeWidth){
            layoutParams.width = oldWidth
        }
        if(changeHeight){
            layoutParams.height = oldHeight
        }
        setMeasuredDimension(PixelUtil.toPixelFromDIP(frame.width).toInt(), PixelUtil.toPixelFromDIP(frame.height).toInt())
        mView.onMeasured()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (it in mView.children) {
            it.onLayout()
        }
    }

//    override fun dispatchDraw(canvas: Canvas) {
//        try {
//            if(mView.clipsToBounds){
//                val bg = mView.mBackground
//                if(bg != null){
//                    var width = width.toFloat()
//                    var height = height.toFloat()
//                    val off = bg.offset
//                    if(off != null){
//                        canvas.translate(-off.x,-off.y)
//                        width = off.width
//                        height = off.height
//                    }
//                    canvas.clipPath(bg.getOuterPath(width,height))
//                    if(off != null) {
//                        canvas.translate(off.x, off.y)
//                    }
//                }else{
//                    canvas.clipRect(Rect(0,0,width,height))
//                }
//            }
//        }catch (e:Exception){
//            e.printStackTrace()
//        }finally {
//            super.dispatchDraw(canvas)
//        }
//    }

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