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


const val VIEW_ORIENTATION_VERTICAL: Int = 0

const val VIEW_ORIENTATION_HORIZONTAL: Int = 1

const val VIEW_STATE_NORMAL: Int = 0

const val VIEW_STATE_PRESSED: Int = 1

const val VIEW_STATE_FOCUSED: Int = 2

const val VIEW_STATE_DISABLED: Int = 3

const val VIEW_STATE_SELECTED: Int = 4

const val VIEW_STYLE_BORDER_SOLID: Int = 0

const val VIEW_STYLE_BORDER_DOTTED: Int = 1

const val VIEW_STYLE_BORDER_DASHED: Int = 2


expect open class View  {

    var flags:Int

    var delegate:WeakReference<ViewDelegate>?

    /**
     * use margin and padding to calculate frame
     */
    var padding:EdgeInsets?

    /**
     * display frame
     */
    open val frame:Frame

    open var layoutParams:LayoutParams

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

    constructor(layoutParams: LayoutParams = LayoutParams())

    var superView:WeakReference<Layout>?

    /**
     * Android ViewGroup clipChildren
     * IOS clipsToBounds
     */
    open var clipsToBounds:Boolean

    open fun onLayout()
    open fun onDraw()
    open fun needLayout()
    open fun needDisplay()



    open fun removeFromSuperView()
    open fun onAttachedToWindow()
    open fun onDetachedFromWindow()
    open fun onAttachedToView(layoutView: Layout)
    open fun onDetachedFromView(layoutView: Layout)

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
     * measure view width and height
     * @param widthSpace 最大宽度  如果小于等于0返回0
     * @param heightSpace 最大高度  如果小于等于0返回0
     * @param outSize 获取计算出来的宽高
     */
    open fun measure(widthSpace: Float, heightSpace: Float, outSize: Size? = null)

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
}
















