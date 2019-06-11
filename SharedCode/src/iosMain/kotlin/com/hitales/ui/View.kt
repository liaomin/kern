package com.hitales.ui

import com.hitales.ui.ios.Background
import com.hitales.ui.ios.IOSView
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.*
import platform.CoreGraphics.CGImageRef
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.QuartzCore.CAShapeLayer
import platform.UIKit.*
import platform.darwin.NSObject
import kotlin.math.max


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

    actual companion object {
        val image = UIImage.imageNamed("1.jpg")
        fun getCGImage():CGImageRef?{
            return image!!.CGImage
        }
        actual fun getCGImage2():Any?{
            return image!!.CGImage
        }
        fun getCGImage3():Any?{
            return memScoped { image!!.CGImage?.getPointer(this) }

        }
    }

    protected  var onPressListener:((view:View)->Unit)? = null

    protected  var onLongPressListener:((view:View)->Unit)? = null

    protected val mWidget: UIView = createWidget()

    var mBackground:Background? = null

    private var mBorderLayer: CAShapeLayer? = null

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

    open fun getIOSWidget(): UIView {
        return mWidget
    }

    actual open fun setBackgroundColor(color: Int) {
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
        mBorderLayer?.frame = CGRectMake(0.0,0.0,value.width.toDouble(),value.height.toDouble())
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

    actual open var hidden: Boolean = false

    actual open var borderStyle: BorderStyle = BorderStyle.SOLID

    /**
     * events
     */
    actual fun setOnPressListener(listener: (view: View) -> Unit) {
        onPressListener = listener
    }

    actual fun setOnLongPressListener(listener: (iew: View) -> Unit) {
        onPressListener = listener
    }

    actual fun getBorderLeftWidth(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getBorderTopWidth(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getBorderRightWidth(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getBorderBottomWidth(): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}