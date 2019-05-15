package com.hitales.ui.android

import android.graphics.*
import android.graphics.drawable.StateListDrawable
import com.hitales.ui.utils.PixelUtil
import com.hitales.ui.Colors
import com.hitales.ui.StateListColor
import com.hitales.ui.ViewState


inline fun Int.overlayColor(b:Int):Int{
    val a = this
    val alphaA = a.ushr(24)  / 255f
    val alphaB =  b.ushr(24) / 255f
    if(alphaA == 1f){
        return a
    }
    if(alphaA == 0f ){
        return b
    }
    val rA = a shr 16 and 0xFF
    val gA = a shr 8 and 0xFF
    val bA = a and 0xFF
    val rB = b shr 16 and 0xFF
    val gB = b shr 8 and 0xFF
    val bB = b and 0xFF
    val alpha = 1-(1-alphaA) * (1-alphaB)
    val r =  (rA * (1 - alpha) + rB * alpha)
    val g =  (gA * (1 - alpha) + gB * alpha)
    val blue =  (bA * (1 - alpha) + bB * alpha)
    return Color.argb((alpha*255).toInt(),r.toInt(),g.toInt(),blue.toInt())
}

open class Background : StateListDrawable() {

    private val mOuterPath:Path by lazy { Path() }

    private val mInnerPath:Path by lazy { Path() }

    private val mPaint:Paint by lazy {
      Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val backgroundColors: StateListColor =
        StateListColor(Colors.TRANSPARENT)

    private val mTempRectF:RectF by lazy {RectF(0f,0f,0f,0f)}

    var borderLeftWidth:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderTopWidth:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderRightWidth:Float = 0f
        private set(value) {
            field =Math.max(0f,value)
        }

    var borderBottomWidth:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderTopLeftRadius:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderTopRightRadius:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderBottomRightRadius:Float = 0f
        private set(value) {
            field = Math.max(0f,value)
        }

    var borderBottomLeftRadius:Float = 0f
        private set(value) {
            field =  Math.max(0f,value)
        }

    var borderLeftColor:Int = Colors.BLACK
        private set(value) {
             field = value
         }

    var borderTopColor:Int = Colors.BLACK
        private set(value) {
            field = value
        }

    var borderRightColor:Int = Colors.BLACK
        private set(value) {
            field = value
        }

    var borderBottomColor:Int = Colors.BLACK
        private set(value) {
            field = value
        }

    fun setColorForState(color:Int,state: ViewState){
        backgroundColors.setColorForState(color,state)
    }

    override fun onStateChange(stateSet: IntArray?): Boolean {
        return super.onStateChange(stateSet)
    }

    override fun isStateful(): Boolean {
        return true
    }

    override fun draw(canvas: Canvas) {
        drawBorderAndBackground(canvas)
        super.draw(canvas)
    }

    private fun drawBorderAndBackground(canvas: Canvas){
        val haveBorderWidth = haveBorderWidth()
        mTempRectF.set(bounds)
        val width = mTempRectF.width()
        val height = mTempRectF.height()
        val halfWidth = width / 2
        val halfHeight = height / 2
        val maxRadius = Math.min(halfWidth,halfHeight)

        if(width <=0 || height <= 0) {
            return
        }
        mPaint.xfermode = null
        var borderLeftWidth = Math.min(PixelUtil.toPixelFromDIP(borderLeftWidth).toInt().toFloat(),halfWidth)
        var borderTopWidth =  Math.min(PixelUtil.toPixelFromDIP(borderTopWidth).toInt().toFloat(),halfHeight)
        var borderRightWidth = Math.min(PixelUtil.toPixelFromDIP(borderRightWidth).toInt().toFloat(),halfWidth)
        var borderBottomWidth = Math.min(PixelUtil.toPixelFromDIP(borderBottomWidth).toInt().toFloat(),halfHeight)
        var borderTopLeftRadius = Math.min(PixelUtil.toPixelFromDIP(borderTopLeftRadius).toInt().toFloat(),maxRadius)
        var borderTopRightRadius = Math.min(PixelUtil.toPixelFromDIP(borderTopRightRadius).toInt().toFloat(),maxRadius)
        var borderBottomRightRadius = Math.min(PixelUtil.toPixelFromDIP(borderBottomRightRadius).toInt().toFloat(),maxRadius)
        var borderBottomLeftRadius = Math.min(PixelUtil.toPixelFromDIP(borderBottomLeftRadius).toInt().toFloat(),maxRadius)
        if(haveBorderRadius()){
            //有圆角
            canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
            //首先绘制背景色
            mOuterPath.rewind()
            mOuterPath.addRoundRect(mTempRectF, floatArrayOf(borderTopLeftRadius,borderTopLeftRadius,borderTopRightRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomRightRadius,borderBottomLeftRadius,borderBottomLeftRadius),Path.Direction.CW)
//            mOuterPath.rewind()
//            mOuterPath.moveTo(borderTopLeftRadius,0f)
//            mOuterPath.lineTo(width - borderTopRightRadius, 0f)
//            mOuterPath.quadTo(width,0f,width,borderTopRightRadius)
//            mOuterPath.lineTo(width,height - borderBottomRightRadius)
//            mOuterPath.quadTo(width,height,width - borderBottomRightRadius,height)
//            mOuterPath.lineTo(borderBottomLeftRadius,height)
//            mOuterPath.quadTo(0f,height,0f,height-borderBottomLeftRadius)
//            mOuterPath.lineTo(0f,borderTopLeftRadius)
//            mOuterPath.quadTo(0f,0f,borderTopLeftRadius,0f)


            mPaint.isAntiAlias = true
            val backgroundColor = getCurrentColor()
            mPaint.style = Paint.Style.FILL
            if(haveBorderWidth){
                val sameBorderColor = sameBorderColor()
                if(sameBorderColor && borderTopColor ushr 24 == 0){
                    if(backgroundColor ushr 24 != 0){
                        mPaint.color = backgroundColor
                        canvas.drawPath(mOuterPath,mPaint)
                    }
                    return
                }
                var points = floatArrayOf(
                    Math.max(borderTopLeftRadius,borderLeftWidth),borderTopWidth,
                    width-borderRightWidth,borderTopWidth,
                    width-borderRightWidth,borderTopWidth,
                    width-borderRightWidth,height-borderBottomWidth,
                    width-borderRightWidth,height-borderBottomWidth,
                    borderLeftWidth,height-borderBottomWidth,
                    borderLeftWidth,height-borderBottomWidth,
                    borderLeftWidth,borderTopWidth
                )
                mInnerPath.rewind()
                mInnerPath.moveTo(points[0],points[1])
                if(borderTopRightRadius > 0){
                    points[2] = width-Math.max(borderRightWidth,borderTopRightRadius)
                    points[5] = Math.max(borderTopRightRadius,borderTopWidth)
                    mInnerPath.lineTo(points[2], points[3])
                    if(borderTopWidth == borderRightWidth){
                        //圆形
                        mTempRectF.set(points[2] - (points[4] - points[2]),points[3],points[4],points[5]+points[5]-points[3])
                        mInnerPath.arcTo(mTempRectF,-90f,90f,false)
                    }else{
                        mInnerPath.quadTo(width-borderRightWidth,borderTopWidth, points[4],points[5])
                    }
                }else{
                    mInnerPath.lineTo(width-borderRightWidth,borderTopWidth)
                }
                if(borderBottomRightRadius > 0){
                    points[7] = height - Math.max(borderBottomRightRadius,borderBottomWidth)
                    points[8] = width-Math.max(borderRightWidth,borderBottomRightRadius)

                    mInnerPath.lineTo( points[6],points[7])
                    if(borderRightWidth == borderBottomWidth){
                        mTempRectF.set(points[8] - (points[6] - points[8]),points[7] - (points[9] - points[7]),points[6],points[9])
                        mInnerPath.arcTo(mTempRectF,0f,90f,false)
                    }else{
                        mInnerPath.quadTo(width-borderRightWidth,height - borderBottomWidth, points[8], points[9])
                    }
                }else{
                    mInnerPath.lineTo(width-borderRightWidth,height-borderBottomWidth)
                }
                if(borderBottomLeftRadius > 0){
                    points[10] = Math.max(borderLeftWidth,borderBottomLeftRadius)
                    points[13] = height - Math.max(borderBottomLeftRadius,borderBottomWidth)
                    mInnerPath.lineTo(points[10],points[11])
                    if(borderBottomWidth == borderLeftWidth){
                        mTempRectF.set(points[12],points[13]-(points[11]-points[13]),points[10]+points[10]-points[12],points[11])
                        mInnerPath.arcTo(mTempRectF,90f,90f,false)
                    }else{
                        mInnerPath.quadTo(borderLeftWidth,height - borderBottomWidth,points[12],points[13])
                    }
                }else{
                    mInnerPath.lineTo(borderLeftWidth,height-borderBottomWidth)
                }
                if(borderTopLeftRadius > 0){
                    points[15] = Math.max(borderTopLeftRadius,borderTopWidth)
                    mInnerPath.lineTo(points[14],points[15] )
                    if(borderTopWidth == borderLeftWidth){
                        mTempRectF.set(points[14],points[1],points[0]+points[0]-points[14],points[15] + points[15]-points[1])
                        mInnerPath.arcTo(mTempRectF,180f,90f,false)
                    }else {
                        mInnerPath.quadTo(borderLeftWidth, borderTopWidth, points[0], borderTopWidth)
                    }
                }else{
                    mInnerPath.lineTo(borderLeftWidth,borderTopWidth)
                }
                mInnerPath.close()

                mTempRectF.set(bounds)


                if(sameBorderColor){
                    mPaint.style = Paint.Style.FILL
                    mPaint.color = backgroundColor
                    canvas.drawPath(mOuterPath,mPaint)
                    mPaint.color = borderLeftColor
                    canvas.drawPath(mOuterPath,mPaint)
                }else{
                    mPaint.color = Colors.WHITE
                    canvas.drawPath(mOuterPath,mPaint)
                    mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

                    var topLeftX = points[0]
                    var topLeftY = borderTopWidth
                    var topRightX = points[2]
                    var topRightY = borderTopWidth
                    var bottomRightX =  points[8]
                    var bottomRightY =  height - borderBottomWidth
                    var bottomLeftX =  points[10]
                    var bottomLeftY =  height - borderBottomWidth

                    if(borderLeftWidth > 0){
                        val k = topLeftX / borderLeftWidth
                        topLeftY =  borderTopWidth *k
                        bottomLeftY =  height - borderBottomWidth * k
                    }
                    if(borderRightWidth > 0){
                        val k =  (width - topRightX) /  borderRightWidth
                        topRightY =  borderTopWidth * k
                        bottomRightY =  height - borderBottomWidth * k
                    }

                    if(borderTopWidth > 0 && borderTopColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(0f,0f,width,0f,topRightX,topRightY,topLeftX,topLeftY),borderTopColor.overlayColor(backgroundColor))
                    }
                    if(borderRightWidth > 0 && borderRightColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(width,0f,width,height,bottomRightX,bottomRightY,topRightX,topRightY),borderRightColor.overlayColor(backgroundColor))
                    }
                    if(borderBottomWidth > 0){
                        drawPointsPath(canvas, floatArrayOf(0f,height,width,height,bottomRightX,bottomRightY,bottomLeftX,bottomLeftY),borderBottomColor.overlayColor(backgroundColor))
                    }
                    if(borderLeftWidth > 0){
                        drawPointsPath(canvas, floatArrayOf(0f,0f,0f,height,bottomLeftX,bottomLeftY,topLeftX,topLeftY),borderLeftColor.overlayColor(backgroundColor))
                    }
                }

                mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
//                    //后绘制背景色
                mPaint.color = Colors.WHITE
                canvas.drawPath(mInnerPath,mPaint)
                mPaint.xfermode = null
                mPaint.color = backgroundColor
                canvas.drawPath(mInnerPath,mPaint)

//                mPaint.strokeWidth = 20f
//                mPaint.color = Color.RED
//                canvas.drawPoints(points,mPaint)
//                mPaint.strokeWidth = 5f
//                canvas.drawLine(halfWidth,0f,halfWidth,height,mPaint)
//                canvas.drawLine(0f,halfHeight,width,halfHeight,mPaint)
            }
            else{
                mPaint.style = Paint.Style.FILL
                mPaint.color = backgroundColor
                if(backgroundColor ushr 24 != 0){
                    canvas.drawPath(mOuterPath,mPaint)
                }
            }
        }else{
            //没有圆角
            //首先绘制背景色
            mPaint.isAntiAlias = false
            mPaint.style = Paint.Style.FILL
            val backgroundColor = getCurrentColor()
            mPaint.color = backgroundColor
            if(backgroundColor ushr 24 != 0){
                canvas.drawRect(mTempRectF,mPaint)
            }
            if(haveBorderWidth){
                val sameBorderWidth = sameBorderWidth()
                val sameBorderColor = sameBorderColor()
                if(sameBorderColor && borderTopColor ushr 24 == 0){
                    return
                }
                if(sameBorderWidth && sameBorderColor){
                    //大小和颜色一样，直接绘制矩形
                    mPaint.style = Paint.Style.STROKE
                    mPaint.color = borderTopColor
                    mPaint.strokeWidth = borderTopWidth
                    mTempRectF.inset(borderTopWidth/2,borderTopWidth/2)
                    canvas.drawRect(mTempRectF,mPaint)
                }else{
                    //绘制各个边
                    mPaint.isAntiAlias = true
                    mPaint.style = Paint.Style.FILL
                    if(borderTopWidth > 0 && borderTopColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(0f,0f,width,0f,width-borderRightWidth,borderTopWidth,borderLeftWidth,borderTopWidth),borderTopColor)
                    }
                    if(borderRightWidth > 0 && borderRightColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(width,0f,width,height,width-borderRightWidth,height-borderBottomWidth,width-borderRightWidth,borderTopWidth),borderRightColor)
                    }
                    if(borderBottomWidth > 0 && borderBottomColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(0f,height,width,height,width-borderRightWidth,height-borderBottomWidth,borderLeftWidth,height-borderBottomWidth),borderBottomColor)
                    }
                    if(borderLeftWidth > 0 && borderLeftColor ushr  24 != 0){
                        drawPointsPath(canvas, floatArrayOf(0f,0f,0f,height,borderLeftWidth,height-borderBottomWidth,borderLeftWidth,borderTopWidth),borderLeftColor)
                    }
                }
            }
        }
    }

    fun drawPointsPath(canvas: Canvas,points:FloatArray,color: Int){
        mOuterPath.rewind()
        mOuterPath.moveTo(points[0],points[1])
        mOuterPath.lineTo(points[2],points[3])
        mOuterPath.lineTo(points[4],points[5])
        mOuterPath.lineTo(points[6],points[7])
        mOuterPath.close()
        mPaint.color = color
        canvas.drawPath(mOuterPath,mPaint)
    }


    protected fun getCurrentColor(): Int {
        return backgroundColors.getColorForState(state,0)
    }

    open fun setBorderColor(color: Int) {
        setBorderColor(color,color,color,color)
    }

    open fun setBorderColor(
        leftColor: Int,
        topColor: Int,
        rightColor: Int,
        bottomColor: Int
    ) {
        borderLeftColor = leftColor
        borderTopColor = topColor
        borderRightColor = rightColor
        borderBottomColor = bottomColor
        invalidateSelf()
    }

   open fun setBorderWidth(borderWidth: Float) {
       setBorderWidth(borderWidth,borderWidth,borderWidth,borderWidth)
    }

   open fun setBorderWidth(
        leftWidth: Float,
        topWidth: Float,
        rightWidth: Float,
        bottomWidth: Float
    ) {
       val changed = borderLeftWidth != leftWidth ||  borderTopWidth != topWidth || borderRightWidth != rightWidth || borderBottomWidth != bottomWidth
       borderLeftWidth = leftWidth
       borderTopWidth = topWidth
       borderRightWidth = rightWidth
       borderBottomWidth = bottomWidth
       if(changed){
           invalidateSelf()
       }
    }

   open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float) {
       val changed =  borderTopLeftRadius != topLeftRadius || borderTopRightRadius != topRightRadius || borderBottomRightRadius != bottomRightRadius || borderBottomLeftRadius != bottomLeftRadius
       borderTopLeftRadius = topLeftRadius
       borderTopRightRadius = topRightRadius
       borderBottomRightRadius = bottomRightRadius
       borderBottomLeftRadius = bottomLeftRadius
       if(changed){
           invalidateSelf()
       }
    }

    private fun haveBorderWidth():Boolean{
        return borderLeftWidth > 0 || borderTopWidth > 0 || borderRightWidth >0 || borderBottomWidth > 0
    }

    private fun sameBorderWidth():Boolean{
        return borderLeftWidth == borderRightWidth && borderRightWidth == borderBottomWidth && borderRightWidth == borderTopWidth
    }

    private fun sameBorderColor():Boolean{
        return borderLeftColor ==  borderTopColor && borderTopColor ==  borderLeftColor && borderLeftColor == borderBottomColor
    }

    private fun haveBorderRadius():Boolean{
        return borderTopLeftRadius > 0 || borderTopRightRadius > 0 || borderBottomLeftRadius >0 || borderBottomRightRadius > 0
    }

    fun getOutterPath(path: Path,rect: RectF){
        path.rewind()
        mOuterPath.addRoundRect(rect, floatArrayOf(borderTopLeftRadius,borderTopLeftRadius,borderTopRightRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomRightRadius,borderBottomLeftRadius,borderBottomLeftRadius),Path.Direction.CW)
    }

    fun clipPath():Boolean{
        return haveBorderWidth() && haveBorderRadius() && !sameBorderColor()
    }

}