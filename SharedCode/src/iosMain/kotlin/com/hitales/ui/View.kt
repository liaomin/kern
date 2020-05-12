package com.hitales.ui

import com.hitales.ios.ui.setTransformM34
import com.hitales.ui.animation.toIOSAnimation
import com.hitales.ui.ios.IOSView
import com.hitales.utils.*
import com.kern.ios.ui.*
import kotlinx.cinterop.cValuesOf
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSNumber
import platform.Foundation.numberWithFloat
import platform.Foundation.setValue
import platform.Foundation.valueForKeyPath
import platform.QuartzCore.CAAnimation
import platform.QuartzCore.CAAnimationDelegateProtocol
import platform.UIKit.*
import platform.darwin.NSObject
import platform.objc.sel_registerName
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

inline fun Frame.toCGRect():kotlinx.cinterop.CValue<platform.CoreGraphics.CGRect>{
    return CGRectMake(x.toDouble(),y.toDouble(),width.toDouble(),height.toDouble())
}

inline fun MeasureMode.toIOSMeasureMode():com.kern.ios.ui.MeasureMode{
    if(this == MeasureMode.EXACTLY){
        return MeasureModeExactly
    }else if(this == MeasureMode.UNSPECIFIED){
        return MeasureModeUnspecified
    }
    return MeasureModeAtMost
}


const val animation_key = "__animation_key__"

actual open class View {

    protected  var onPressListener:((view:View)->Unit)? = null

    protected var pressGestureRecognizer:UIGestureRecognizer? = null

    private var lastPressEventTime = 0.toLong()

    protected  var onLongPressListener:((view:View)->Unit)? = null

    protected var longPressGestureRecognizer:UIGestureRecognizer? = null

    protected val mWidget: UIView = createWidget()

    private var destructBlocks:LinkedList<(view: com.hitales.ui.View)->Unit>? = null

    var mBackgroundColor = Colors.CLEAR

    actual var padding: EdgeInsets? = null
        set(value) {
            field = value
            if(value != null){
                mWidget.setPadding(UIEdgeInsetsMake(value.top.toDouble(),value.left.toDouble(),value.bottom.toDouble(),value.right.toDouble()))
            }else{
                mWidget.setPadding(UIEdgeInsetsMake(0.0,0.0,0.0,0.0))
            }
        }

    actual open val frame:Frame =  Frame.identity()

    actual var superView:Layout? by Weak()

    actual open var id:Int
        get() = getWidget().tag.toInt()
        set(value) {
            getWidget().tag = value.toLong()
        }

    actual open var tag:Any? = null

    /**
     * 透明度 0~1
     */
    actual open var opacity: Float
        get() = getWidget().alpha.toFloat()
        set(value) {
            getWidget().alpha = value.toDouble()
        }

    private fun getLayerValueForPath(path:String):Float{
        return (getWidget().layer.valueForKeyPath(path) as NSNumber).floatValue
    }

    private fun setLayerValueForPath(value:Float,path:String){
        getWidget().layer.setValue(NSNumber.numberWithFloat(value),forKeyPath = path)
    }

    actual open var translateX: Float
        get() = getLayerValueForPath("transform.translation.x")
        set(value) {
            setLayerValueForPath(value,"transform.translation.x")
        }

    actual open var translateY: Float
        get() = getLayerValueForPath("transform.translation.y")
        set(value) {
            setLayerValueForPath(value,"transform.translation.y")
        }

    actual open var rotateX: Float
        get() = getLayerValueForPath("transform.rotation.x")
        set(value) {
            setLayerValueForPath(value,"transform.rotation.x")
        }

    actual open var rotateY: Float
        get() = getLayerValueForPath("transform.rotation.y")
        set(value) {
            setLayerValueForPath(value,"transform.rotation.y")
        }

    actual open var rotateZ: Float
        get() = getLayerValueForPath("transform.rotation.z")
        set(value) {
            setLayerValueForPath(value,"transform.rotation.z")
        }

    actual open var scaleX: Float
        get() = getLayerValueForPath("transform.scale.x")
        set(value) {
            setLayerValueForPath(value,"transform.scale.x")
        }

    actual open var scaleY: Float
        get() = getLayerValueForPath("transform.scale.y")
        set(value) {
            setLayerValueForPath(value,"transform.scale.y")
        }

    actual open var layoutParams:LayoutParams? = null

    actual constructor(layoutParams: LayoutParams?){
        this.layoutParams = layoutParams
        setBackgroundColor(0)
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
        superView?.removeSubView(this)
    }

    actual open fun onAttachedToWindow() {

    }

    actual open fun onDetachedFromWindow() {

    }

    actual open fun onAttachedToView(layoutView: Layout) {
    }

    actual open fun onDetachedFromView(layoutView: Layout) {

    }

    actual open fun setBorderColor(color: Int) {
        val c = color.toUInt()
        mWidget.setBorderColor(c,c,c,c)
    }

    actual open fun setBorderColor(leftColor: Int, topColor: Int, rightColor: Int, bottomColor: Int) {
        mWidget.setBorderColor(leftColor.toUInt(),topColor.toUInt(),rightColor.toUInt(),bottomColor.toUInt())
    }

    actual open fun setBorderWidth(borderWidth: Float) {
        mWidget.setBorderWidth(borderWidth.toDouble())
    }

    actual open fun setBorderWidth(leftWidth: Float, topWidth: Float, rightWidth: Float, bottomWidth: Float) {
        mWidget.setBorderWidth(leftWidth.toDouble(),topWidth.toDouble(),rightWidth.toDouble(),bottomWidth.toDouble())
    }

    actual open fun setBorderRadius(radius: Float) {
        val r = radius.toDouble()
        mWidget.setBorderRadius(r,r,r,r)
    }

    actual open fun setBorderRadius(topLeftRadius: Float, topRightRadius: Float,bottomLeftRadius: Float, bottomRightRadius: Float) {
        mWidget.setBorderRadius(topLeftRadius.toDouble(),topRightRadius.toDouble(),bottomLeftRadius.toDouble(),bottomRightRadius.toDouble())
    }

    actual open var elevation: Float = 0f

    actual open var isHidden: Boolean
        get() = mWidget.hidden
        set(value) {
            mWidget.hidden = value
        }

    actual open fun setBorderStyle(style: BorderStyle){
        when (style){
            BorderStyle.SOLID -> mWidget.setBorderStyle(BorderStyleSolid)
            BorderStyle.DOTTED -> mWidget.setBorderStyle(BorderStyleDotted)
            BorderStyle.DASHED -> mWidget.setBorderStyle(BorderStyleDashed)
        }
    }

    /**
     * events
     */
    actual open fun setOnPressListener(listener:((view:View)->Unit)?) {
        onPressListener = listener
        if(listener == null){
            val gesture = pressGestureRecognizer
            if(gesture != null){
                mWidget.removeGestureRecognizer(gesture)
            }
            pressGestureRecognizer = null
        }else{
            if(pressGestureRecognizer == null){
                val gestureRecognizer = UITapGestureRecognizer(this, sel_registerName("onPress"))
                gestureRecognizer.cancelsTouchesInView = false
                mWidget.addGestureRecognizer(gestureRecognizer)
                pressGestureRecognizer = gestureRecognizer
            }
        }
    }

    actual open fun setOnLongPressListener(listener:((view:View)->Unit)?) {
        onLongPressListener = listener
        if(listener == null){
            val gesture = longPressGestureRecognizer
            if(gesture != null){
                mWidget.removeGestureRecognizer(gesture)
            }
            longPressGestureRecognizer = null
        }else{
            if(longPressGestureRecognizer == null){
                val gestureRecognizer = UILongPressGestureRecognizer(this, sel_registerName("onLongPress"))
                gestureRecognizer.cancelsTouchesInView = false
                mWidget.addGestureRecognizer(gestureRecognizer)
                longPressGestureRecognizer = gestureRecognizer
            }
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

    protected actual open fun  onFrameChanged(){
        getWidget().setFrame(CGRectMake(frame.x.toDouble(),frame.y.toDouble(),frame.width.toDouble(),frame.height.toDouble()))
    }

    actual open fun startAnimation(animation: Animation, completion: (() -> Unit)?) {
        mWidget.layer.setTransformM34(animation.m34)
        val iosAnimation = animation.toIOSAnimation()
        val weakSelf = WeakReference(this)
        iosAnimation.delegate = object : NSObject(),CAAnimationDelegateProtocol{

            override fun animationDidStart(anim: CAAnimation) {
                animation.delegate?.onAnimationStart(animation,weakSelf.get())
            }

            override fun animationDidStop(anim: CAAnimation, finished: Boolean) {
                completion?.invoke()
                animation.delegate?.onAnimationStop(animation,weakSelf.get())
            }
        }
        mWidget.layer.addAnimation(iosAnimation,animation_key)
    }


    actual var delegate:ViewDelegate? by Weak()

    actual open fun setShadow(color: Int, radius: Float, dx: Float, dy: Float) {
        mWidget.setShadow(color.toUInt(),radius.toDouble(),dx.toDouble(),dy.toDouble())
    }

    /**
     * touches
     */
    actual open fun dispatchTouchEvent(touches: Touches): Boolean {
        return onInterceptTouchEvent(touches)
    }

    actual open fun onInterceptTouchEvent(touches: Touches): Boolean {
        return false
    }

    actual var flags: Int = 0

    actual open fun onLayout() {
        mWidget.setFrame(frame.toCGRect())
    }

    actual open fun needLayout() {
        mWidget.setNeedsLayout()
    }

    actual open fun needDisplay() {
        mWidget.setNeedsDisplay()
    }

    actual open fun getBackgroundColor(): Int {
        return mBackgroundColor
    }

    actual fun getLeftBorderWidth(): Float {
        return mWidget.getLeftBorderWidth().toFloat()
    }

    actual fun getTopBorderWidth(): Float {
        return mWidget.getTopBorderWidth().toFloat()
    }

    actual fun getRightBorderWidth(): Float {
        return mWidget.getRightBorderWidth().toFloat()
    }

    actual fun getBottomBorderWidth(): Float {
        return mWidget.getBottomBorderWidth().toFloat()
    }

    actual fun getTopLeftBorderRadius(): Float {
        return mWidget.getTopLeftBorderRadius().toFloat()
    }

    actual fun getTopRightBorderRadius(): Float {
        return mWidget.getTopRightBorderRadius().toFloat()
    }

    actual fun getBottomLeftBorderRadius(): Float {
        return mWidget.getBottomLeftBorderRadius().toFloat()
    }

    actual fun getBottomRightBorderRadius(): Float {
        return mWidget.getBottomRightBorderRadius().toFloat()
    }

    actual fun getShadowColor(): Int {
        return mWidget.getShadowColor().toInt()
    }

    actual fun getShadowRadius(): Float {
        return mWidget.getShadowRadius().toFloat()
    }

    actual fun getShadowOffsetX(): Float {
        return mWidget.getShadowOffsetX().toFloat()
    }

    actual fun getShadowOffsetY(): Float {
        return mWidget.getShadowOffsetY().toFloat()
    }

    /**
     * measure view width and height
     * @param widthSpace measure宽度
     * @param widthMode
     * @param heightSpace measure高度
     * @param heightMode
     * @param outSize 获取计算出来的宽高
     */
    actual open fun measure(widthSpace: Float, widthMode: MeasureMode, heightSpace: Float, heightMode: MeasureMode, outSize: Size) {
        if(mWidget is MeasureNodeProtocol){
            val widget = mWidget as MeasureNodeProtocol
            widget.measure(widthSpace.toDouble(),widthMode.toIOSMeasureMode(),heightSpace.toDouble(),heightMode.toIOSMeasureMode()).useContents {
                outSize.set(this.width.toFloat(),this.height.toFloat())
            }
            return
        }
        var w = 0f
        var h = 0f
        if(widthMode == MeasureMode.EXACTLY){
            w = widthSpace
        }
        if(heightMode == MeasureMode.EXACTLY){
            h = widthSpace
        }
        outSize.set(w,h)
    }

    actual open fun onTouchesBegan(touches: Touches) {
    }

    actual open fun onTouchesMoved(touches: Touches) {
    }

    actual open fun onTouchesEnded(touches: Touches) {
    }

    actual open fun onTouchesCancelled(touches: Touches) {
    }

    actual open fun getPaddingLeft(): Float {
        return padding?.left?:0f
    }

    actual open fun getPaddingRight(): Float {
        return padding?.right?:0f
    }

    actual open fun getPaddingTop(): Float {
        return padding?.top?:0f
    }

    actual open fun getPaddingBottom(): Float {
        return padding?.bottom?:0f
    }

    actual open fun cleanAnimation() {
        mWidget.layer.removeAnimationForKey(animation_key)
    }

    actual open fun onDestruct() {
        destructBlocks?.forEach {
            it(this)
        }
    }

    actual fun addDestructBlock(block: (view: View) -> Unit) {
        getOrCreateDestructorBlocks().append(block)
    }


    private fun getOrCreateDestructorBlocks():LinkedList<(view: com.hitales.ui.View)->Unit>{
        var blocks = destructBlocks
        if(blocks != null){
            return blocks
        }else{
            blocks = LinkedList()
            destructBlocks = blocks
            return blocks
        }
    }

}