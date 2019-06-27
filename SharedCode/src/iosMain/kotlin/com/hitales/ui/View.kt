package com.hitales.ui

import com.hitales.ios.ui.setTransformM34
import com.hitales.ui.animation.transAnimation
import com.hitales.ui.ios.Background
import com.hitales.ui.ios.IOSView
import com.hitales.utils.*
import kotlinx.cinterop.*
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.*
import platform.UIKit.*
import platform.darwin.NSObject
import platform.objc.sel_registerName
import kotlin.math.max
import kotlin.system.getTimeMillis


inline fun Int.toUIColor():UIColor{
    val a = this ushr 24 and 0x000000FF
    val r =  this shr 16 and 0x000000FF
    val g =  this shr 8 and 0x000000FF
    val b = this and 0x000000FF
    return UIColor.colorWithRed(r / 255.0 ,g / 255.0,b / 255.0,a / 255.0)
}

inline fun UIColor.toInt():Int{
    return memScoped {
        val r= cValuesOf(0.0).getPointer(this)
        val g= cValuesOf(0.0).getPointer(this)
        val b= cValuesOf(0.0).getPointer(this)
        val a= cValuesOf(0.0).getPointer(this)
        getRed(r,g,b,a)
        return ((a[0]*255).toInt() shl 24) or ((r[0]*255).toInt() shl 16) or ((g[0]*255).toInt() shl 8) or (b[0]*255).toInt()
    }
}


actual open class View {

    protected  var onPressListener:((view:View)->Unit)? = null

    protected var pressGestureRecognizer:UIGestureRecognizer? = null

    private var lastPressEventTime = 0.toLong()

    protected  var onLongPressListener:((view:View)->Unit)? = null

    protected var longPressGestureRecognizer:UIGestureRecognizer? = null

    protected val mWidget: UIView = createWidget()

    var mBackgroundColor = Colors.CLEAR

    var mBackground:Background? = null

    init {
        setBackgroundColor(0)
    }

    actual var padding: EdgeInsets? = null
    actual var margin:EdgeInsets? = null

    actual open var frame:Frame
        set(value) {
            field = value
            setWidgetFrame(value)
        }

    actual var superView:ViewGroup? = null

    actual open var id:Int
        get() = getWidget().tag.toInt()
        set(value) {
            getWidget().tag = value.toLong()
        }

    actual open var tag:Any? = null

    actual constructor(frame: Frame){
        this.frame = frame
    }

    open fun getWidget(): UIView {
        return mWidget
    }

    open fun createWidget(): UIView {
       return IOSView(WeakReference(this))
    }

    actual open fun setBackgroundColor(color: Int) {
        mBackgroundColor = color
        getWidget().backgroundColor = color.toUIColor()
    }

    override fun toString(): String {
        return "${this::class}: frame :$frame"
    }

    actual open fun removeFromSuperView(){
        superView?.removeView(this)
    }

    actual open fun onAttachedToWindow() {

    }

    actual open fun onDetachedFromWindow() {

    }

    actual open fun onAttachedToView(layoutView: ViewGroup) {
    }

    actual open fun onDetachedFromView(layoutView: ViewGroup) {

    }

    open fun setWidgetFrame(value:Frame){
        getWidget().setFrame(CGRectMake(value.x.toDouble(),value.y.toDouble(),value.width.toDouble(),value.height.toDouble()))
    }

    actual open fun setBorderColor(color: Int) {
        getOrCreateBackground().setBorderColor(color)
    }

    actual open fun setBorderColor(
        leftColor: Int,
        topColor: Int,
        rightColor: Int,
        bottomColor: Int
    ) {
        getOrCreateBackground().setBorderColor(leftColor,topColor, rightColor, bottomColor)
    }

    actual open fun setBorderWidth(borderWidth: Float) {
        getOrCreateBackground().setBorderWidth(borderWidth,borderStyle)
    }

    actual open fun setBorderWidth(
        leftWidth: Float,
        topWidth: Float,
        rightWidth: Float,
        bottomWidth: Float
    ) {
        getOrCreateBackground().setBorderWidth(leftWidth, topWidth, rightWidth, bottomWidth, borderStyle)
    }

    actual open fun setBorderRadius(radius: Float) {
        getOrCreateBackground().setBorderRadius(radius,radius,radius,radius)
    }

    actual open fun setBorderRadius(
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomRightRadius: Float,
        bottomLeftRadius: Float
    ) {
        getOrCreateBackground().setBorderRadius(topLeftRadius,topRightRadius,bottomRightRadius,bottomLeftRadius)
    }

    actual open var elevation: Float = 0f

    actual open var hidden: Boolean
        get() = mWidget.hidden
        set(value) {
            mWidget.hidden = value
        }

    actual open var borderStyle: BorderStyle = BorderStyle.SOLID
        set(value) {
            field = value
            getOrCreateBackground().setBorderStyle(value)
        }

    /**
     * events
     */
    actual fun setOnPressListener(listener: (view: View) -> Unit) {
        onPressListener = listener
        if(pressGestureRecognizer == null){
            val gestureRecognizer = UITapGestureRecognizer(this, sel_registerName("onPress"))
            gestureRecognizer.cancelsTouchesInView = false
            mWidget.addGestureRecognizer(gestureRecognizer)
            pressGestureRecognizer = gestureRecognizer
        }
    }

    actual fun setOnLongPressListener(listener: (iew: View) -> Unit) {
        onLongPressListener = listener
        if(longPressGestureRecognizer == null){
            val gestureRecognizer = UILongPressGestureRecognizer(this, sel_registerName("onLongPress"))
            gestureRecognizer.cancelsTouchesInView = false
            mWidget.addGestureRecognizer(gestureRecognizer)
            longPressGestureRecognizer = gestureRecognizer
        }
    }

    fun onPress(){
        onPressListener?.invoke(this)
        lastPressEventTime = getTimeMillis()
    }

    fun onLongPress(){
        onLongPressListener?.invoke(this)
        if(longPressGestureRecognizer?.state  == UIGestureRecognizerStateBegan && ((getTimeMillis() - lastPressEventTime) >= 1000)){
            lastPressEventTime = getTimeMillis()
        }
    }

    /**
     * border
     */
    actual fun getBorderLeftWidth(): Float {
        return mBackground?.borderLeftWidth?:0f
    }

    actual fun getBorderTopWidth(): Float {
        return mBackground?.borderTopWidth?:0f
    }

    actual fun getBorderRightWidth(): Float {
        return mBackground?.borderRightWidth?:0f
    }

    actual fun getBorderBottomWidth(): Float {
        return mBackground?.borderBottomWidth?:0f
    }

    /**
     * @param maxWidth 最大宽度  如果小于等于0表示无限宽
     * @param maxHeight 最大高度  如果小于等于0表示无限高
     */
    actual open fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        return Size(frame.width,frame.height)
    }

    protected fun getOrCreateBackground(): Background {
        if (mBackground == null) {
            mBackground = Background()
        }
        mWidget.layer.setNeedsDisplay()
        return mBackground!!
    }

    actual open fun startAnimation(animation: Animation, completion: (() -> Unit)?) {
        mWidget.layer.setTransformM34(animation.m34)
        val iosAnimation = transAnimation(animation)
        iosAnimation.delegate = object : NSObject(),CAAnimationDelegateProtocol{

            override fun animationDidStart(anim: CAAnimation) {
                animation.delegate?.onAnimationStart(animation)
            }

            override fun animationDidStop(anim: CAAnimation, finished: Boolean) {
                completion?.invoke()
                animation.delegate?.onAnimationFinish(animation)

            }
        }
        mWidget.layer.addAnimation(iosAnimation,"animation")
    }


}