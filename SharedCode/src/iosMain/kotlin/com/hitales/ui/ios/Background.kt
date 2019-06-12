package com.hitales.ui.ios

import com.hitales.ios.ui.setContentsWithImage
import com.hitales.ui.BorderStyle
import com.hitales.ui.Colors
import com.hitales.ui.toUIColor
import com.hitales.utils.Frame
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.QuartzCore.CALayer
import platform.UIKit.*
import platform.posix.M_PI
import platform.posix.M_PI_2
import kotlin.math.max
import kotlin.math.min

class Background {

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


    fun toImage(size: CGSize,bgColor:CGColorRef?):UIImage?{
        if(bgColor == null) {
            return  null
        }
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


        CGContextSetFillColorWithColor(ctx,bgColor)
        CGContextAddPath(ctx, outerPath.CGPath)
        CGContextFillPath(ctx)

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


    fun onDraw(layer: CALayer){
        val bgColor = layer.backgroundColor
        val size = layer.bounds.useContents { this.size }
        val haveBorderWidth = haveBorderWidth()
        if(size.width <= 0 || size.height <= 0){
            return
        }
        if(haveBorderWidth){
            val sameBorderColor = sameBorderColor()
            val sameBorderWidth = sameBorderWidth()
            val sameBorderRadius = sameBorderRadius()
            if(sameBorderColor && sameBorderWidth && sameBorderRadius){
                layer.borderColor = borderLeftColor.toUIColor().CGColor
                layer.borderWidth = borderLeftWidth.toDouble()
                layer.cornerRadius = min(min(size.width,size.height) / 2 , borderTopLeftRadius.toDouble())
            }else{
                val image = toImage(size,bgColor)
                if(image != null){
                    layer.backgroundColor = null
                    layer.setContentsWithImage(image)
                    layer.contentsScale = image.scale
                    layer.needsDisplayOnBoundsChange = true
                    layer.mask = null
                }else{
                    layer.contents = null
                    layer.needsDisplayOnBoundsChange = false
                    layer.mask = null
                }
            }
        }else{
            layer.contents = null
            layer.needsDisplayOnBoundsChange = false
            layer.mask = null
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
    }

    open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float) {
        borderTopLeftRadius = topLeftRadius
        borderTopRightRadius = topRightRadius
        borderBottomRightRadius = bottomRightRadius
        borderBottomLeftRadius = bottomLeftRadius
    }

    fun  setBorderStyle(borderStyle: BorderStyle){
        if(this.borderStyle != borderStyle){
            this.borderStyle = borderStyle
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

    private fun sameBorderRadius():Boolean{
        return  borderTopLeftRadius == borderTopRightRadius && borderTopRightRadius == borderBottomRightRadius && borderBottomRightRadius == borderBottomLeftRadius
    }
}