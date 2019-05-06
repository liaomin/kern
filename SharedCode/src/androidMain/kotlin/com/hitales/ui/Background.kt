package com.hitales.ui

import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets


open class Background : StateListDrawable() {

    private val mPath:Path by lazy { Path() }

    private val mPaint:Paint by lazy {
        val paint =  Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        return@lazy paint
    }

    private val backgroundColors:StateListColor = StateListColor(Colors.TRANSPARENT)

    private val tempRectF:RectF by lazy {RectF(0f,0f,0f,0f)}

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
        if(getBordeRadiusrPath(mPath,bounds)){
            mPaint.color = getCurrentColor()
            mPaint.style = Paint.Style.FILL
            canvas.drawPath(mPath,mPaint)
            mPaint.style = Paint.Style.STROKE
        }else{
            canvas.drawColor(getCurrentColor())
        }
        drawBorder(canvas)
        super.draw(canvas)
    }

    protected open fun drawBorder(canvas: Canvas){
        if(haveBorderWidth()){
            val sameBorderWidth = sameBorderWidth()
            val sameBorderColor = sameBorderColor()
            val rect = bounds
            val width = rect.width().toFloat()
            val height = rect.height().toFloat()
            if(sameBorderWidth && sameBorderColor){
                mPath.rewind()
                tempRectF.set(0f,0f,width,height)
                val halfBorderWidth = borderBottomWidth/2
                tempRectF.set(halfBorderWidth,halfBorderWidth,width-halfBorderWidth,height-halfBorderWidth)
                mPath.addRoundRect(tempRectF, floatArrayOf(borderTopLeftRadius,borderTopLeftRadius,borderTopRightRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomRightRadius,borderBottomLeftRadius,borderBottomLeftRadius),Path.Direction.CCW)
                mPaint.color = borderBottomColor
                mPaint.strokeWidth = borderTopWidth
                canvas.drawPath(mPath,mPaint)
            }
        }
    }

    fun getBordeRadiusrPath(path: Path,rect: Rect):Boolean{
        if(haveBorderRadius()){
            val width = rect.width().toFloat()
            val height = rect.height().toFloat()
            path.rewind()
            if(borderTopLeftRadius > 0){
                tempRectF.set(0f,0f,borderTopLeftRadius*2,borderTopLeftRadius*2)
                path.addArc(tempRectF,180f,90f)
            }else{
                path.moveTo(0f,0f)
            }
            if(borderTopRightRadius > 0){
                path.lineTo(width - borderTopRightRadius,0f)
                tempRectF.set(width - borderTopRightRadius*2,0f,width,borderTopRightRadius*2)
                path.arcTo(tempRectF,270f,90f,false)
            }else{
                path.lineTo(width,0f)
            }
            if(borderBottomRightRadius > 0){
                path.lineTo(width,height - borderBottomRightRadius)
                tempRectF.set(width - borderBottomRightRadius*2,height - borderBottomRightRadius*2,width,height)
                path.arcTo(tempRectF,0f,90f,false)
            }else{
                path.lineTo(width,height)
            }
            if(borderBottomLeftRadius > 0){
                path.lineTo(width - borderBottomLeftRadius,height)
                tempRectF.set(0f,height - borderBottomLeftRadius*2,borderBottomLeftRadius*2,height)
                path.arcTo(tempRectF,90f,90f,false)
            }else{
                path.lineTo(0f,height)
            }
            path.close()
            return true
        }
        return false
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

   open fun setBorderRadius(radius: Float) {
       setBorderRadius(radius,radius,radius,radius)
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

}