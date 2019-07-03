package com.hitales.ui

import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size

enum class Orientation{
    VERTICAL,
    HORIZONTAL
}

enum class ViewState(val value:Int) {
    NORMAL(0),
    PRESSED(1),
    FOCUSED(2),
    DISABLED(3),
    SELECTED(4),
}

enum class BorderStyle(val value:Int) {
    SOLID(0),
    DOTTED(1),
    DASHED(2),
}

expect open class View  {
    /**
     * use margin and padding to calculate frame
     */
    var padding:EdgeInsets?

    /**
     * use margin and padding to calculate frame
     */
    var margin:EdgeInsets?

    /**
     * layout params
     */
    open var frame:Frame

    open var id:Int

    open var tag:Any?

    open var elevation:Float

    open var hidden:Boolean

    open var borderStyle:BorderStyle

    /**
     * 透明度 0~1
     */
    open var opacity:Float

    open var translateX:Float

    open var translateY:Float

    open var rotateX:Float

    open var rotateY:Float

    open var rotateZ:Float

    open var scaleX:Float

    open var scaleY:Float

    constructor(frame: Frame = Frame.zero())

    var superView:ViewGroup?

    open fun removeFromSuperView()
    open fun onAttachedToWindow()
    open fun onDetachedFromWindow()
    open fun onAttachedToView(layoutView: ViewGroup)
    open fun onDetachedFromView(layoutView: ViewGroup)

    /**
     * events
     */
    fun setOnPressListener(listener:(view:View)->Unit)
    fun setOnLongPressListener(listener:(iew:View)->Unit)

    /**
     * background
     */
    // argb color
    open fun setBackgroundColor(color:Int)
    open fun setBorderColor(color:Int)
    open fun setBorderColor(leftColor:Int,topColor: Int,rightColor:Int,bottomColor:Int)
    open fun setBorderWidth(borderWidth:Float)
    open fun setBorderWidth(leftWidth:Float,topWidth: Float,rightWidth:Float,bottomWidth:Float)
    open fun setBorderRadius(radius:Float)
    open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float)

    fun getBorderLeftWidth():Float
    fun getBorderTopWidth():Float
    fun getBorderRightWidth():Float
    fun getBorderBottomWidth():Float

    /**
     * @param maxWidth 最大宽度  如果小于等于0表示无限宽
     * @param maxHeight 最大高度  如果小于等于0表示无限高
     */
    open fun measureSize(maxWidth: Float,maxHeight:Float):Size


    open fun startAnimation(animation: Animation,completion:(()->Unit)? = null)
}
















