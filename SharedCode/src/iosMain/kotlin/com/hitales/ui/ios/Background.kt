package com.hitales.ui.ios

import com.hitales.ios.ui.setContentsWithImage
import com.hitales.ui.BorderStyle
import com.hitales.ui.Colors
import com.hitales.ui.toUIColor
import com.hitales.utils.WeakReference
import kotlinx.cinterop.createValues
import kotlinx.cinterop.useContents
import kotlinx.cinterop.value
import platform.CoreGraphics.*
import platform.QuartzCore.CALayer
import platform.UIKit.*
import platform.posix.M_PI
import platform.posix.M_PI_2
import kotlin.math.max
import kotlin.math.min

class Background(val layerRef:WeakReference<CALayer>) {

    var borderLeftWidth:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderTopWidth:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderRightWidth:Float = 0f
        private set(value) {
            field =max(0f,value)
        }

    var borderBottomWidth:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderTopLeftRadius:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderTopRightRadius:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderBottomRightRadius:Float = 0f
        private set(value) {
            field = max(0f,value)
        }

    var borderBottomLeftRadius:Float = 0f
        private set(value) {
            field =  max(0f,value)
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

    private var borderStyle = BorderStyle.SOLID

    var shadowRadius: Float = 0f

    var shadowDx: Float = 0f

    var shadowDy: Float = 0f

    var shadowColor: Int = 0

    private fun getOuterPath(size: CGSize,borderLeftWidth:Double,borderTopWidth:Double,borderRightWidth:Double,borderBottomWidth:Double,borderTopLeftRadius:Double,borderTopRightRadius:Double,borderBottomRightRadius:Double,borderBottomLeftRadius:Double):UIBezierPath{
        val path = UIBezierPath.bezierPath()
        path.moveToPoint(CGPointMake(0.0,borderTopLeftRadius))
        path.addArcWithCenter(CGPointMake(borderTopLeftRadius,borderTopLeftRadius),borderTopLeftRadius, M_PI,3*M_PI_2,true)

        path.addLineToPoint(CGPointMake(size.width - borderTopRightRadius,0.0))
        path.addArcWithCenter(CGPointMake(size.width - borderTopRightRadius,borderTopRightRadius),borderTopRightRadius, 3*M_PI_2,0.0,true)

        path.addLineToPoint(CGPointMake(size.width ,size.height - borderBottomRightRadius))
        path.addArcWithCenter(CGPointMake(size.width - borderBottomRightRadius,size.height - borderBottomRightRadius),borderBottomRightRadius, 0.0,M_PI_2,true)

        path.addLineToPoint(CGPointMake(borderBottomLeftRadius,size.height))
        path.addArcWithCenter(CGPointMake(borderBottomLeftRadius,size.height - borderBottomLeftRadius),borderBottomLeftRadius, M_PI_2,M_PI,true)
        path.closePath()
        return path
    }

    private fun getOuterPath(size: CGSize):UIBezierPath{

        val halfWidth = size.width / 2
        val halfHeight = size.height / 2
        val maxRadius = min(halfWidth,halfHeight)
        var borderLeftWidth = min(borderLeftWidth.toDouble(),halfWidth)
        var borderTopWidth =  min(borderTopWidth.toDouble(),halfHeight)
        var borderRightWidth = min(borderRightWidth.toDouble(),halfWidth)
        var borderBottomWidth = min(borderBottomWidth.toDouble(),halfHeight)
        var borderTopLeftRadius = min(borderTopLeftRadius.toDouble(),maxRadius)
        var borderTopRightRadius = min(borderTopRightRadius.toDouble(),maxRadius)
        var borderBottomRightRadius = min(borderBottomRightRadius.toDouble(),maxRadius)
        var borderBottomLeftRadius = min(borderBottomLeftRadius.toDouble(),maxRadius)

        return getOuterPath(size, borderLeftWidth, borderTopWidth, borderRightWidth, borderBottomWidth, borderTopLeftRadius, borderTopRightRadius, borderBottomRightRadius, borderBottomLeftRadius)
    }

    private fun getDashedInnerPath(size: CGSize,borderLeftWidth:Double,borderTopWidth:Double,borderRightWidth:Double,borderBottomWidth:Double,borderTopLeftRadius:Double,borderTopRightRadius:Double,borderBottomRightRadius:Double,borderBottomLeftRadius:Double):UIBezierPath{
        val lineWidth = borderLeftWidth
        val halfWidth = lineWidth / 2
        val path = UIBezierPath.bezierPath()

        var r = max(0.0, borderTopLeftRadius - halfWidth)
        if(r > 0){
            path.moveToPoint(CGPointMake(halfWidth,borderTopLeftRadius))
            path.addArcWithCenter(CGPointMake(borderTopLeftRadius,borderTopLeftRadius),r, M_PI,3*M_PI_2,true)
        }else{
            path.moveToPoint(CGPointMake(halfWidth,halfWidth))
        }

        r = max(0.0, borderTopRightRadius - halfWidth)
        if(r > 0){
            path.addLineToPoint(CGPointMake(size.width - borderTopRightRadius,halfWidth))
            path.addArcWithCenter(CGPointMake(size.width - borderTopRightRadius,borderTopRightRadius),r, 3*M_PI_2,0.0,true)
        }else{
            path.addLineToPoint(CGPointMake(size.width - halfWidth,halfWidth))
        }

        r = max(0.0, borderBottomRightRadius - halfWidth)
        if(r > 0){
            path.addLineToPoint(CGPointMake(size.width - halfWidth,size.height - borderBottomRightRadius))
            path.addArcWithCenter(CGPointMake(size.width - borderBottomRightRadius,size.height - borderBottomRightRadius),r, 0.0,M_PI_2,true)
        }else{
            path.addLineToPoint(CGPointMake(size.width - halfWidth,size.height - halfWidth))
        }

        r = max(0.0, borderBottomLeftRadius - halfWidth)
        if(r > 0){
            path.addLineToPoint(CGPointMake(borderBottomLeftRadius,size.height - halfWidth))
            path.addArcWithCenter(CGPointMake(borderBottomLeftRadius,size.height - borderBottomLeftRadius),r, M_PI_2,M_PI,true)
        }else{
            path.addLineToPoint(CGPointMake(halfWidth,size.height - halfWidth))
        }
        path.closePath()
        return path
    }


    fun toDashedImage(size: CGSize,bgColor:Int,dashedScale:Double,image:UIImage?):UIImage?{
        UIGraphicsBeginImageContextWithOptions(CGSizeMake(size.width,size.height),false,0.0)
        val ctx = UIGraphicsGetCurrentContext()
        val halfWidth = size.width / 2
        val halfHeight = size.height / 2
        val maxRadius = min(halfWidth,halfHeight)
        var borderLeftWidth = min(borderLeftWidth.toDouble(),halfWidth)
        var borderTopWidth =  min(borderTopWidth.toDouble(),halfHeight)
        var borderRightWidth = min(borderRightWidth.toDouble(),halfWidth)
        var borderBottomWidth = min(borderBottomWidth.toDouble(),halfHeight)
        var borderTopLeftRadius = min(borderTopLeftRadius.toDouble(),maxRadius)
        var borderTopRightRadius = min(borderTopRightRadius.toDouble(),maxRadius)
        var borderBottomRightRadius = min(borderBottomRightRadius.toDouble(),maxRadius)
        var borderBottomLeftRadius = min(borderBottomLeftRadius.toDouble(),maxRadius)
        val outerPath = getOuterPath(size,borderLeftWidth,borderTopWidth,borderRightWidth,borderBottomWidth,borderTopLeftRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomLeftRadius)
        val innerPath = getDashedInnerPath(size,borderLeftWidth,borderTopWidth,borderRightWidth,borderBottomWidth,borderTopLeftRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomLeftRadius)


        CGContextSetFillColorWithColor(ctx,bgColor.toUIColor().CGColor)
        CGContextAddPath(ctx, outerPath.CGPath)
        CGContextFillPath(ctx)

        if(image != null){
            CGContextDrawImage(ctx, CGRectMake(0.0,0.0,size.width,size.height),image.CGImage)
        }

//        CGContextAddPath(ctx,  innerPath.CGPath)
//        CGContextSetStrokeColorWithColor(ctx, Colors.GREEN.toUIColor().CGColor)
//        CGContextStrokePath(ctx)

        CGContextSetLineWidth(ctx, borderLeftWidth)
        val dashLengths = createValues<CGFloatVar>(2){
            this.value = dashedScale * borderLeftWidth
        }
        CGContextSetLineDash(ctx, 0.0, dashLengths, 2)
        CGContextAddPath(ctx,  innerPath.CGPath)
        CGContextSetStrokeColorWithColor(ctx, borderLeftColor.toUIColor().CGColor)
        CGContextStrokePath(ctx)

        val image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }

    fun toImage(size: CGSize,bgColor:Int,image:UIImage?):UIImage?{
        UIGraphicsBeginImageContextWithOptions(CGSizeMake(size.width,size.height),false,0.0)
        val ctx = UIGraphicsGetCurrentContext()
        val halfWidth = size.width / 2
        val halfHeight = size.height / 2
        val maxRadius = min(halfWidth,halfHeight)
        var borderLeftWidth = min(borderLeftWidth.toDouble(),halfWidth)
        var borderTopWidth =  min(borderTopWidth.toDouble(),halfHeight)
        var borderRightWidth = min(borderRightWidth.toDouble(),halfWidth)
        var borderBottomWidth = min(borderBottomWidth.toDouble(),halfHeight)
        var borderTopLeftRadius = min(borderTopLeftRadius.toDouble(),maxRadius)
        var borderTopRightRadius = min(borderTopRightRadius.toDouble(),maxRadius)
        var borderBottomRightRadius = min(borderBottomRightRadius.toDouble(),maxRadius)
        var borderBottomLeftRadius = min(borderBottomLeftRadius.toDouble(),maxRadius)
        val outerPath = getOuterPath(size,borderLeftWidth,borderTopWidth,borderRightWidth,borderBottomWidth,borderTopLeftRadius,borderTopRightRadius,borderBottomRightRadius,borderBottomLeftRadius)

        val path = UIBezierPath.bezierPath()
        val width = size.width
        val height = size.height
        var points = doubleArrayOf(
            max(borderTopLeftRadius,borderLeftWidth),borderTopWidth,
            width-borderRightWidth,borderTopWidth,
            width-borderRightWidth,borderTopWidth,
            width-borderRightWidth,height-borderBottomWidth,
            width-borderRightWidth,height-borderBottomWidth,
            borderLeftWidth,height-borderBottomWidth,
            borderLeftWidth,height-borderBottomWidth,
            borderLeftWidth,borderTopWidth
        )

        path.moveToPoint(CGPointMake(points[0],points[1]))
        if(borderTopRightRadius > 0){
            points[2] = width-max(borderRightWidth,borderTopRightRadius)
            points[5] = max(borderTopRightRadius,borderTopWidth)
            path.addLineToPoint(CGPointMake(points[2], points[3]))
            if(borderTopWidth == borderRightWidth){
                path.addArcWithCenter(CGPointMake(points[2],points[5]),points[5] - points[3], 3*M_PI_2,0.0,true)
            }else{
                path.addQuadCurveToPoint(CGPointMake(points[4],points[5]),CGPointMake(width-borderRightWidth,borderTopWidth))
            }
        }else{
            path.addLineToPoint(CGPointMake(width-borderRightWidth,borderTopWidth))
        }
        if(borderBottomRightRadius > 0){
            points[7] = height - max(borderBottomRightRadius,borderBottomWidth)
            points[8] = width - max(borderRightWidth,borderBottomRightRadius)
            path.addLineToPoint(CGPointMake(points[6],points[7]))
            if(borderRightWidth == borderBottomWidth){
                path.addArcWithCenter(CGPointMake(points[8],points[7]),points[9] - points[7], 0.0,M_PI_2,true)
            }else{
                path.addQuadCurveToPoint(CGPointMake(points[8],points[9]),CGPointMake(width-borderRightWidth,height - borderBottomWidth))
            }
        }else{
            path.addLineToPoint(CGPointMake(width-borderRightWidth,height-borderBottomWidth))
        }
        if(borderBottomLeftRadius > 0){
            points[10] = max(borderLeftWidth,borderBottomLeftRadius)
            points[13] = height - max(borderBottomLeftRadius,borderBottomWidth)
            path.addLineToPoint(CGPointMake(points[10],points[11]))
            if(borderBottomWidth == borderLeftWidth){
                path.addArcWithCenter(CGPointMake(points[10],points[13]),points[11] - points[13], M_PI_2,M_PI,true)
            }else{
                path.addQuadCurveToPoint(CGPointMake(points[12],points[13]),CGPointMake(borderLeftWidth,height - borderBottomWidth))
            }
        }else{
            path.addLineToPoint(CGPointMake(borderLeftWidth,height-borderBottomWidth))
        }
        if(borderTopLeftRadius > 0){
            points[15] = max(borderTopLeftRadius,borderTopWidth)
            path.addLineToPoint(CGPointMake(points[14],points[15]))
            if(borderTopWidth == borderLeftWidth){
                path.addArcWithCenter(CGPointMake(points[0],points[15]),points[15] - points[1], M_PI,3*M_PI_2,true)
            }else {
                path.addQuadCurveToPoint(CGPointMake(points[0],borderTopWidth),CGPointMake(borderLeftWidth, borderTopWidth))
            }
        }else{
            path.addLineToPoint(CGPointMake(borderLeftWidth,borderTopWidth))
        }
        path.closePath()
        val innerPath = path


        CGContextSetFillColorWithColor(ctx,bgColor.toUIColor().CGColor)
        CGContextAddPath(ctx, outerPath.CGPath)
        CGContextFillPath(ctx)

        if(image != null){
            CGContextDrawImage(ctx, CGRectMake(0.0,0.0,size.width,size.height),image.CGImage)
        }

        CGContextAddPath(ctx, outerPath.CGPath)
        CGContextAddPath(ctx, innerPath.CGPath)
        CGContextEOClip(ctx)

        val sameBorderColor = sameBorderColor()
        if(sameBorderColor){
            CGContextSetFillColorWithColor(ctx,borderLeftColor.toUIColor().CGColor)
            CGContextAddRect(ctx, CGRectMake(0.0,0.0,size.width,size.height))
            CGContextFillPath(ctx)
        }else{
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

            var currentColor = borderLeftColor
            //left
            CGContextSetFillColorWithColor(ctx,currentColor.toUIColor().CGColor)
            if(borderLeftWidth > 0 && currentColor ushr  24 != 0){
                val lines= createValues<CGPoint>(4){ i ->
                    when (i){
                        0 -> {
                            this.x = 0.0
                            this.y = 0.0
                        }
                        1->{
                            this.x = topLeftX
                            this.y = topLeftY
                        }
                        2->{
                            this.x = bottomLeftX
                            this.y = bottomLeftY
                        }
                        3->{
                            this.x = 0.0
                            this.y = size.height
                        }
                    }
                }
                CGContextAddLines(ctx,lines,4)
//                    CGContextAddRect(ctx, CGRectMake(0.0,0.0,halfWidth,size.height))
            }

            //top
            if(currentColor != borderTopColor){
                CGContextFillPath(ctx)
                currentColor = borderTopColor
                CGContextSetFillColorWithColor(ctx,currentColor.toUIColor().CGColor)
            }
            if(borderTopWidth > 0 && currentColor ushr  24 != 0){
                val lines= createValues<CGPoint>(4){ i ->
                    when (i){
                        0 -> {
                            this.x = 0.0
                            this.y = 0.0
                        }
                        1->{
                            this.x = size.width
                            this.y = 0.0
                        }
                        2->{
                            this.x = topRightX
                            this.y = topRightY
                        }
                        3->{
                            this.x = topLeftX
                            this.y = topLeftY
                        }
                    }
                }
                CGContextAddLines(ctx,lines,4)
//                    CGContextAddRect(ctx, CGRectMake(0.0,0.0,size.width,halfHeight))
            }

            //right
            if(currentColor != borderRightColor){
                CGContextFillPath(ctx)
                currentColor = borderRightColor
                CGContextSetFillColorWithColor(ctx,currentColor.toUIColor().CGColor)
            }
            if(borderRightWidth > 0 && currentColor ushr  24 != 0) {
                val lines= createValues<CGPoint>(4){ i ->
                    when (i){
                        0 -> {
                            this.x = topRightX
                            this.y = topRightY
                        }
                        1->{
                            this.x = size.width
                            this.y = 0.0
                        }
                        2->{
                            this.x = size.width
                            this.y = size.height
                        }
                        3->{
                            this.x = bottomRightX
                            this.y = bottomRightY
                        }
                    }
                }
                CGContextAddLines(ctx,lines,4)
//                    CGContextAddRect(ctx, CGRectMake(halfWidth, 0.0, halfWidth, size.height))
            }

            //bottom
            if(currentColor != borderBottomColor){
                CGContextFillPath(ctx)
                currentColor = borderBottomColor
                CGContextSetFillColorWithColor(ctx,currentColor.toUIColor().CGColor)
            }
            if(borderBottomWidth > 0 && currentColor ushr  24 != 0) {
                val lines= createValues<CGPoint>(4){ i ->
                    when (i){
                        0 -> {
                            this.x = bottomLeftX
                            this.y = bottomLeftY
                        }
                        1->{
                            this.x = bottomRightX
                            this.y = bottomRightY
                        }
                        2->{
                            this.x = size.width
                            this.y = size.height
                        }
                        3->{
                            this.x = 0.0
                            this.y = size.height
                        }
                    }
                }
                CGContextAddLines(ctx,lines,4)
//                    CGContextAddRect(ctx, CGRectMake(0.0, halfHeight, size.width, halfHeight))
            }
            CGContextFillPath(ctx)
        }

        val image = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }


    fun onDraw(layer: CALayer,bgColor: Int,image:UIImage? = null){
         if(layer == layerRef.get()){
             layer.bounds.useContents {
                 val size = this.size
                 if(size.width <= 0 || size.height <= 0){
                     return
                 }

                 if(haveShadow()){
                     val outPath = getOuterPath(size)
                     layer.shadowOpacity = 1f
                     layer.shadowColor = shadowColor.toUIColor().CGColor
                     layer.shadowRadius = shadowRadius.toDouble()
                     layer.shadowOffset = CGSizeMake(shadowDx.toDouble(),shadowDy.toDouble())
                     layer.shadowPath = outPath.CGPath
                 }else{
                     layer.shadowOpacity = 0f
                 }

                 val sameBorderRadius = sameBorderRadius()
                 val haveBorderWidth = haveBorderWidth()
                 if(haveBorderWidth || sameBorderRadius){
                     val sameBorderColor = sameBorderColor()
                     val sameBorderWidth = sameBorderWidth()
                     var borderImage:UIImage? = null
                     when (borderStyle){
                         BorderStyle.SOLID -> {
                             if(sameBorderColor && sameBorderWidth && sameBorderRadius){
                                 layer.borderColor = borderLeftColor.toUIColor().CGColor
                                 val halfWidth = size.width / 2
                                 val halfHeight = size.height / 2
                                 val maxRadius = min(halfWidth,halfHeight)
                                 layer.borderWidth = min(borderLeftWidth.toDouble(),maxRadius)
                                 layer.cornerRadius = min(maxRadius , borderTopLeftRadius.toDouble())
                             }else{
                                 borderImage = toImage(size,bgColor,image)
                             }
                         }
                         BorderStyle.DASHED ->{
                             borderImage = toDashedImage(size,bgColor,3.0,image)
                         }
                         BorderStyle.DOTTED ->{
                             borderImage = toDashedImage(size,bgColor,1.0,image)
                         }
                     }
                     if(borderImage != null){
                         layer.backgroundColor = null
                         layer.setContentsWithImage(borderImage)
                         layer.contentsScale = borderImage.scale
                         layer.needsDisplayOnBoundsChange = true
                         layer.mask = null
                     }else{
                         if(image != null){
                             layer.setContentsWithImage(image)
                         }else{
                             layer.contents = null
                         }
                         layer.backgroundColor = bgColor.toUIColor().CGColor
                         layer.needsDisplayOnBoundsChange = false
                         layer.mask = null
                     }
                 }else{
                     if(image != null){
                         layer.setContentsWithImage(image)
                     }else{
                         layer.contents = null
                     }
                     layer.needsDisplayOnBoundsChange = false
                     layer.mask = null
                 }
             }
         }
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

    open fun setBorderWidth(borderWidth: Float, borderStyle: BorderStyle) {
        setBorderWidth(borderWidth,borderWidth,borderWidth,borderWidth,borderStyle)
    }

    open fun setBorderWidth(leftWidth: Float, topWidth: Float, rightWidth: Float, bottomWidth: Float, borderStyle: BorderStyle
    ) {
        borderLeftWidth = leftWidth
        borderTopWidth = topWidth
        borderRightWidth = rightWidth
        borderBottomWidth = bottomWidth
        this.borderStyle = borderStyle
        invalidateSelf()
    }

    open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float) {
        borderTopLeftRadius = topLeftRadius
        borderTopRightRadius = topRightRadius
        borderBottomRightRadius = bottomRightRadius
        borderBottomLeftRadius = bottomLeftRadius
        invalidateSelf()
    }

    fun setBorderStyle(borderStyle: BorderStyle){
        if(this.borderStyle != borderStyle){
            this.borderStyle = borderStyle
            invalidateSelf()
        }
    }

    fun haveShadow():Boolean{
        return shadowRadius > 0f
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

    private fun sameBorderRadius():Boolean{
        return  borderTopLeftRadius == borderTopRightRadius && borderTopRightRadius == borderBottomRightRadius && borderBottomRightRadius == borderBottomLeftRadius
    }

    fun setShadow(radius: Float, dx: Float, dy: Float, color: Int){
        shadowRadius = radius
        shadowDx = dx
        shadowDy = dy
        shadowColor = color
        invalidateSelf()
    }

    fun invalidateSelf(){
        layerRef.get()?.setNeedsDisplay()
    }
}