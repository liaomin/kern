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

//typealias ViewState = Int
//const val ViewStateNormal = 0
//const val ViewStatePressed = 1
//const val ViewStateFocused = 2
//const val ViewStateSelected = 3

//
//class ViewState:Enum{
//    constructor(value: Int):super(value)
//    companion object{
//        val NORMAL = ViewState(0)
//        val PRESSED = ViewState(0)
//        val FOCUSED = ViewState(0)
//        val DISABLED = ViewState(0)
//        val SELECTED = ViewState(0)
//    }
//}

enum class BorderStyle(val value:Int) {
    SOLID(0),
    DOTTED(1),
    DASHED(2),
}

enum class MeasureMode(val value: Int){
    /**
     * 不限制
     */
    UNSPECIFIED(0),
    /**
     * 精确
     */
    EXACTLY(1),
    /**
     * 最多
     */
    AT_MOST(2),
}

interface ViewDelegate

expect open class View  {

    var flags:Int

    var delegate:ViewDelegate?

    /**
     * context padding
     * if android set shadow will have innerPadding
     * getPadding use [getPaddingRight] [getPaddingLeft] [getPaddingTop] [getPaddingBottom]
     */
    var padding:EdgeInsets?

    /**
     * display frame
     */
    open val frame:Frame

    open var layoutParams:LayoutParams?

    open var id:Int

    open var tag:Any?

    /**
     * android elevation
     */
    @TargetPlatform(PLATFORM_ANDROID)
    open var elevation:Float

    open var isHidden:Boolean

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

    constructor(layoutParams: LayoutParams? = null)

    var superView:Layout?

    protected open fun onFrameChanged()
    open fun onLayout()
    open fun needLayout()
    open fun needDisplay()
    open fun getPaddingLeft():Float
    open fun getPaddingRight():Float
    open fun getPaddingTop():Float
    open fun getPaddingBottom():Float

    open fun removeFromSuperView()
    open fun onAttachedToWindow()
    open fun onDetachedFromWindow()
    open fun onAttachedToView(layoutView: Layout)
    open fun onDetachedFromView(layoutView: Layout)

    /**
     * events
     */
    open fun setOnPressListener(listener:((view:View)->Unit)?)
    open fun setOnLongPressListener(listener:((view:View)->Unit)?)

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
    open fun setBorderStyle(style: BorderStyle)
    open fun setBorderWidth(leftWidth:Float,topWidth: Float,rightWidth:Float,bottomWidth:Float)
    fun getLeftBorderWidth():Float
    fun getTopBorderWidth():Float
    fun getRightBorderWidth():Float
    fun getBottomBorderWidth():Float
    open fun setBorderRadius(radius:Float)
    open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomLeftRadius: Float, bottomRightRadius: Float)
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
     * measure view width and height
     * @param widthSpace measure宽度
     * @param widthMode
     * @param heightSpace measure高度
     * @param outSize 获取计算出来的宽高
     */
    open fun measure(widthSpace: Float,widthMode:MeasureMode, heightSpace: Float,heightMode:MeasureMode,outSize: Size)

    /**
     * touches
     */
    open fun dispatchTouchEvent(touches: Touches):Boolean
    open fun onInterceptTouchEvent(touches: Touches):Boolean
    open fun onTouchesBegan(touches: Touches)
    open fun onTouchesMoved(touches: Touches)
    open fun onTouchesEnded(touches: Touches)
    open fun onTouchesCancelled(touches: Touches)

    open fun startAnimation(animation: Animation,completion:(()->Unit)? = null)
    open fun cleanAnimation()

    open fun onDestruct()

    fun addDestructBlock(block:((view:View)->Unit))


}
















