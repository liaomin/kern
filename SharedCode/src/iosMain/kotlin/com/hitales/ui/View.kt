package com.hitales.ui

import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.WeakReference
import kotlinx.cinterop.*
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
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

class IOSView(private val view: WeakReference<View>) : UIView(CGRectMake(0.0,0.0,0.0,0.0)){

    fun dealloc(){
        this.layer.cornerRadius
        println("~~~dealloc~~")
    }

    companion object {
        @ObjCAction
        fun load(){
            println("~~~load~~")
        }
    }
}


actual open class View {

    protected val mWidget: UIView = createWidget()

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

    actual var superView:LayoutView? = null

    actual open var id:Int
        get() = getWidget().tag.toInt()
        set(value) {
            getWidget().tag = value.toLong()
        }

    actual open var tag:Any? = null

    actual constructor(frame: Frame){
        this.frame = frame
//        mWidget.layer.borderColor = Colors.RED.toUIColor().CGColor
//        mWidget.layer.borderWidth = 2.0
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

    actual open fun onAttachedToView(layoutView: LayoutView) {
    }

    actual open fun onDetachedFromView(layoutView: LayoutView) {

    }

    open fun setWidgetFrame(value:Frame){
        getWidget().setFrame(CGRectMake(value.x.toDouble(),value.y.toDouble(),value.width.toDouble(),value.height.toDouble()))
    }

    actual open fun setBorderColor(color: Int) {
    }

    actual open fun setBorderColor(
        leftColor: Int,
        topColor: Int,
        rightColor: Int,
        bottomColor: Int
    ) {
    }

    actual open fun setBorderWidth(borderWidth: Float) {
    }

    actual open fun setBorderWidth(
        leftWidth: Float,
        topWidth: Float,
        rightWidth: Float,
        bottomWidth: Float
    ) {
    }

    actual open fun setBorderRadius(radius: Float) {
    }

    actual open fun setBorderRadius(
        topLeftRadius: Float,
        topRightRadius: Float,
        bottomRightRadius: Float,
        bottomLeftRadius: Float
    ) {

    }
}