package com.hitales.ui

import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference

enum class Orientation{
    VERTICAL,
    HORIZONTAL
}

enum class ViewState(val value:Int) {
    NORMAL(0),
    /**
     * android 必须设置 setOnPressListener PRESSED状态才会生效
     */
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

interface ViewDelegate{
    fun onPress(view: View)
    fun onLongPress(view: View)
}

expect open class View  {

    var delegate:WeakReference<ViewDelegate>?
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

    open var isHidden:Boolean

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

    var superView:Layout?

    /**
     * Android ViewGroup clipChildren
     * IOS clipsToBounds
     */
    open var clipsToBounds:Boolean

    open fun onFrameChanged()

    open fun removeFromSuperView()
    open fun onAttachedToWindow()
    open fun onDetachedFromWindow()
    open fun onAttachedToView(layoutView: Layout)
    open fun onDetachedFromView(layoutView: Layout)
    open fun releaseResource()

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
    open fun getBackgroundColor():Int

    /**
     * border
     */
    open fun setBorderColor(color:Int)
    open fun setBorderColor(leftColor:Int,topColor: Int,rightColor:Int,bottomColor:Int)
    open fun setBorderWidth(borderWidth:Float)
    open fun setBorderWidth(leftWidth:Float,topWidth: Float,rightWidth:Float,bottomWidth:Float)
    fun getLeftBorderWidth():Float
    fun getTopBorderWidth():Float
    fun getRightBorderWidth():Float
    fun getBottomBorderWidth():Float
    open fun setBorderRadius(radius:Float)
    open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float)
    fun getTopLeftBorderRadius():Float
    fun getTopRightBorderRadius():Float
    fun getBottomLeftBorderRadius():Float
    fun getBottomRightBorderRadius():Float



    /**
     * shadow
     */
    open fun setShadow(color: Int,radius: Float, dx: Float = 0f, dy: Float = 0f)
    fun getShadowColor():Int
    fun getShadowRadius():Float
    fun getShadowOffsetX():Float
    fun getShadowOffsetY():Float


    /**
     * @param widthSpace 最大宽度  如果小于0表示无限宽
     * @param heightSpace 最大高度  如果小于0表示无限高
     */
    open fun measureSize(widthSpace: Float,heightSpace: Float):Size
    open fun measureSize(widthSpace: Float,heightSpace: Float,size: Size)


    /**
     * touches
     */
    open fun dispatchTouchEvent(touches: Touches):Boolean
    open fun onInterceptTouchEvent(touches: Touches):Boolean
    open fun touchesBegan(touches: Touches)
    open fun touchesMoved(touches: Touches)
    open fun touchesEnded(touches: Touches)
    open fun touchesCancelled(touches: Touches)

    open fun startAnimation(animation: Animation,completion:(()->Unit)? = null)
}
















