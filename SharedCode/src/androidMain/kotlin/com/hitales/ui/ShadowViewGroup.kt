package com.hitales.ui

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.widget.FrameLayout
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame

actual open class ShadowViewGroup : ViewGroup {

    var shadowColor = 0
        private set(value) {
            field = value
        }

    var shadowOffsetX = 0f
        private set(value) {
            field = value
        }

    var shadowOffsetY = 0f
        private set(value) {
            field = value
        }

    var shadowRadius = 0f
        private set(value) {
            field = value
        }

    var shadowOffsetXPixel = 0
        private set(value) {
            field = value
        }

    var shadowOffsetYPixel = 0
        private set(value) {
            field = value
        }

    var shadowRadiusPixel = 0
        private set(value) {
            field = value
        }

    actual constructor(frame: Frame):super(frame){
        mWidget.isFocusable = true
        mWidget.isFocusableInTouchMode = true
    }

    actual open fun setShadow(shadowColor: Int, shadowOffsetX: Float, shadowOffsetY: Float, shadowRadius: Float) {
        if (shadowRadius > 0) {
            this.shadowColor = shadowColor
            this.shadowOffsetX = shadowOffsetX
            this.shadowOffsetY = shadowOffsetY
            this.shadowRadius = shadowRadius
            shadowOffsetXPixel = PixelUtil.toPixelFromDIP(shadowOffsetX).toInt()
            shadowOffsetYPixel = PixelUtil.toPixelFromDIP(shadowOffsetY).toInt()
            shadowRadiusPixel = PixelUtil.toPixelFromDIP(shadowRadius).toInt()
        } else {
            this.shadowColor = 0
            this.shadowOffsetX = 0f
            this.shadowOffsetY = 0f
            this.shadowRadius = 0f
            shadowOffsetXPixel = 0
            shadowOffsetYPixel = 0
            shadowRadiusPixel = 0
        }
    }

//    fun drawShadow(canvas: Canvas, x:Float, y:Float, width:Float, height:Float){
//        mTempRectF.set(x,y,width+x,height+y)
//        val halfWidth = width / 2
//        val halfHeight = height / 2
//        val maxRadius = Math.min(halfWidth,halfHeight)
//        if(width <= 0 || height <= 0) {
//            return
//        }
//        mPaint.reset()
//        var borderTopLeftRadius = Math.min(PixelUtil.toPixelFromDIP(borderTopLeftRadius).toInt().toFloat(),maxRadius)
//        var borderTopRightRadius = Math.min(PixelUtil.toPixelFromDIP(borderTopRightRadius).toInt().toFloat(),maxRadius)
//        var borderBottomRightRadius = Math.min(PixelUtil.toPixelFromDIP(borderBottomRightRadius).toInt().toFloat(),maxRadius)
//        var borderBottomLeftRadius = Math.min(PixelUtil.toPixelFromDIP(borderBottomLeftRadius).toInt().toFloat(),maxRadius)
//        mOuterPath.rewind()
//        mOuterPath.addRoundRect(mTempRectF, floatArrayOf(borderTopLeftRadius,borderTopLeftRadius,borderTopRightRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomRightRadius,borderBottomLeftRadius,borderBottomLeftRadius),
//            Path.Direction.CW)
//        mPaint.style = Paint.Style.FILL
//        mPaint.isAntiAlias = true
//        mPaint.color = Colors.CLEAR
//        mPaint.setShadowLayer(20f  , 5f, 5f, Colors.RED)
//        canvas.drawPath(mOuterPath,mPaint)
//    }

    override fun getLayoutParams(): FrameLayout.LayoutParams {
        var params = mWidget.layoutParams
        var top = PixelUtil.toPixelFromDIP(frame.y).toInt()
        var left = PixelUtil.toPixelFromDIP(frame.x).toInt()
        var width = PixelUtil.toPixelFromDIP(frame.width).toInt()
        var height = PixelUtil.toPixelFromDIP(frame.height).toInt()
//        val shadow = mBackground
//        if(shadow != null){
//            val shadowOffsetXPixel = shadow.shadowOffsetXPixel
//            val shadowOffsetYPixel = shadow.shadowOffsetYPixel
//            val shadowRadiusPixel = shadow.shadowRadiusPixel
//            var l = left + shadowOffsetXPixel - shadowRadiusPixel
//            var t = top + shadowOffsetYPixel - shadowRadiusPixel
//            var r = left + width + shadowOffsetXPixel + shadowRadiusPixel
//            var b = top + height + shadowOffsetYPixel + shadowRadiusPixel
//            left = Math.min(l,left)
//            top = Math.min(t,top)
//            width = Math.max(r,width+left) - left
//            height = Math.max(b,top + height) - top
//        }
        if (params == null || params !is FrameLayout.LayoutParams) {
            params = FrameLayout.LayoutParams(width, height)
        } else {
            params.width = width
            params.height = height
        }
        params.topMargin = top
        params.leftMargin = left
        return params
    }

}