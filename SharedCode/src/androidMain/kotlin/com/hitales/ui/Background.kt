package com.hitales.ui

import android.graphics.*
import android.graphics.drawable.StateListDrawable
import android.os.Build
import com.hitales.ui.utils.PixelUtil


open class Background : StateListDrawable() {

    val debug = true

    private val mOuterPath:Path by lazy { Path() }

    private val mInnerPath:Path by lazy { Path() }

    private val mPaint:Paint by lazy {
      Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private val backgroundColors:StateListColor = StateListColor(Colors.TRANSPARENT)

    private val mTempRectF:RectF by lazy {RectF(0f,0f,0f,0f)}

    var borderLeftWidth:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderTopWidth:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderRightWidth:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderBottomWidth:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderTopLeftRadius:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderTopRightRadius:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderBottomRightRadius:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderBottomLeftRadius:Float = 0f
        private set(value) {
            field = PixelUtil.toPixelFromDIP(Math.max(0f,value))
        }

    var borderLeftColor:Int = Colors.DARK
        private set(value) {
             field = value
         }

    var borderTopColor:Int = Colors.DARK
        private set(value) {
            field = value
        }

    var borderRightColor:Int = Colors.DARK
        private set(value) {
            field = value
        }

    var borderBottomColor:Int = Colors.DARK
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
        if(width <=0 || height <= 0) {
            return
        }
        if(haveBorderRadius()){
            //有圆角
            //首先绘制背景色
            mOuterPath.rewind()
            mOuterPath.moveTo(borderTopLeftRadius,0f)
            mOuterPath.lineTo(width - borderTopRightRadius, 0f)
            mOuterPath.quadTo(width,0f,width,borderTopRightRadius)
            mOuterPath.lineTo(width,height - borderBottomRightRadius)
            mOuterPath.quadTo(width,height,width - borderBottomRightRadius,height)
            mOuterPath.lineTo(borderBottomLeftRadius,height)
            mOuterPath.quadTo(0f,height,0f,height-borderBottomLeftRadius)
            mOuterPath.lineTo(0f,borderTopLeftRadius)
            mOuterPath.quadTo(0f,0f,borderTopLeftRadius,0f)

            mPaint.isAntiAlias = true
            mPaint.style = Paint.Style.FILL
            val backgroundColor = getCurrentColor()
            mPaint.color = backgroundColor
            if(backgroundColor ushr 24 != 0){
//                canvas.drawPath(mOuterPath,mPaint)
            }
            if(haveBorderWidth){
                val sameBorderColor = sameBorderColor()
                if(sameBorderColor && borderTopColor ushr 24 == 0){
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
                    mInnerPath.lineTo( points[2], points[3])
                    mInnerPath.quadTo(width-borderRightWidth,borderTopWidth, points[4],points[5])
                }else{
                    mInnerPath.lineTo(width-borderRightWidth,borderTopWidth)
                }
                if(borderBottomRightRadius > 0){
                    points[7] = height - Math.max(borderBottomRightRadius,borderBottomWidth)
                    points[8] = width-Math.max(borderRightWidth,borderBottomRightRadius)
                    mInnerPath.lineTo( points[6],points[7])
                    mInnerPath.quadTo(width-borderRightWidth,height - borderBottomWidth, points[8], points[9])
                }else{
                    mInnerPath.lineTo(width-borderRightWidth,height-borderBottomWidth)
                }
                if(borderBottomLeftRadius > 0){
                    points[10] = Math.max(borderLeftWidth,borderBottomLeftRadius)
                    points[13] = height - Math.max(borderBottomLeftRadius,borderBottomWidth)
                    mInnerPath.lineTo(points[10],points[11])
                    mInnerPath.quadTo(borderLeftWidth,height - borderBottomWidth,points[12],points[13])
                }else{
                    mInnerPath.lineTo(borderLeftWidth,height-borderBottomWidth)
                }
                if(borderTopLeftRadius > 0){
                    points[15] = Math.max(borderTopLeftRadius,borderTopWidth)
                    mInnerPath.lineTo(borderLeftWidth,points[15] )
                    mInnerPath.quadTo(borderLeftWidth, borderTopWidth,points[0],borderTopWidth)
                }else{
                    mInnerPath.lineTo(borderLeftWidth,borderTopWidth)
                }
                mInnerPath.close()
                if(sameBorderColor){
                    if(borderTopColor ushr 24 == 0){
                        return
                    }
                    canvas.save()
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        mOuterPath.op(mInnerPath,Path.Op.DIFFERENCE)
                        mPaint.color = borderTopColor
                        canvas.drawPath(mOuterPath,mPaint)
                    }else{
                        canvas.clipPath(mOuterPath, Region.Op.INTERSECT)
                        canvas.clipPath(mInnerPath, Region.Op.DIFFERENCE)
                        canvas.drawColor(borderTopColor)
                    }
                    canvas.restore()
                }else{
                    canvas.save()
                    val halfWidth = width / 2
                    val halfHeight = height / 2

//                    canvas.clipPath(mOuterPath, Region.Op.INTERSECT)
//                    canvas.clipPath(mInnerPath, Region.Op.DIFFERENCE)

//                    canvas.drawColor(Colors.RED)

                    mPaint.isAntiAlias = true
                    mPaint.style = Paint.Style.FILL
                    if(borderTopWidth > 0 && borderTopColor ushr  24 != 0){
                        var point1X = borderLeftWidth
                        var point1Y = borderTopWidth
                        var point2X = width - borderRightWidth
                        var point2Y = borderTopWidth
                        if(points[15] > borderTopWidth){
                            val k = points[15] / borderTopWidth
                            point1X *= k
                            point1Y = points[15]
                            if(point1X > halfWidth){
                                point1Y *= halfWidth / point1X
                                point1X = halfWidth
                            }
                            if(point1Y > halfHeight){
                                point1X *= halfHeight / point1Y
                                point1Y = halfHeight
                            }
                        }
                        if(points[5] > borderTopWidth){
                            val k = points[4] / borderLeftWidth
                            point2X =  width - borderRightWidth * k
                            point2Y = points[5]
                            if(point2X < halfWidth){
                                point2Y *= halfWidth / (width-point2X)
                                point2X = halfWidth
                            }
                            if(point2Y > halfHeight){
                                point2X = width - (width - point2X)*(halfHeight / point2Y)
                                point2Y = halfHeight
                            }
                        }
                        drawPointsPath(canvas, floatArrayOf(0f,0f,width,0f,point2X,point2Y,point1X,point1Y),borderTopColor)
                    }
                    if(borderRightWidth > 0 && borderRightColor ushr  24 != 0){
                        var point1X = width - borderRightWidth
                        var point1Y = borderTopWidth
                        var point2X = width - borderRightWidth
                        var point2Y = height - borderBottomWidth
                        if(points[2] < point1X){
                            val k = (width -points[2])  / borderRightWidth
                            point1X = width - borderRightWidth * k
                            point1Y *= k
                        }
//                        if(points[5] > borderTopWidth){
//                            val k = points[0] / borderLeftWidth
//                            point2X *= k
//                            point2Y *= k
//                        }
                        drawPointsPath(canvas, floatArrayOf(width,0f,width,height,point2X,point2Y,point1X,point1Y),borderRightColor)
                    }
                    if(borderBottomWidth > 0){
                        drawPointsPath(canvas, floatArrayOf(0f,height,width,height,width-borderRightWidth,height-borderBottomWidth,borderLeftWidth,height-borderBottomWidth),borderBottomColor)
                    }
                    if(borderLeftWidth > 0){
                        drawPointsPath(canvas, floatArrayOf(0f,0f,0f,height,borderLeftWidth,height-borderBottomWidth,borderLeftWidth,borderTopWidth),borderLeftColor)
                    }
                    canvas.restore()
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

    fun clipPath():Boolean{
        return haveBorderWidth() && haveBorderRadius()
    }

}