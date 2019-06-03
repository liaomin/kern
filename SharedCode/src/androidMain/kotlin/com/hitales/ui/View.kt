package com.hitales.ui

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import com.hitales.ui.android.AndroidView
import com.hitales.ui.android.Background
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size

actual open class View {

    protected val mWidget: android.view.View = createWidget()

    private var mBackgroundColor = Colors.TRANSPARENT

    var mBackground: Background? = null

    init {
        mWidget.setBackgroundColor(mBackgroundColor)
        mWidget.setOnClickListener{
            onPressListener?.invoke(this)
        }
        mWidget.setOnLongClickListener{
            onLongPressListener?.invoke(this)
            if(onLongPressListener != null){
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    actual var padding: EdgeInsets? = null
        set(value) {
            field = value
            if(value != null){
                mWidget.setPadding(PixelUtil.toPixelFromDIP(value.left).toInt(),PixelUtil.toPixelFromDIP(value.top).toInt(),PixelUtil.toPixelFromDIP(value.right).toInt(),PixelUtil.toPixelFromDIP(value.bottom).toInt())
            }
        }

    actual var margin: EdgeInsets? = null

    actual open var frame: Frame
        set(value) {
            field = value
            onFrameChanged()
        }

    actual open var hidden: Boolean = false
        set(value) {
            field = value
            if(value){
                mWidget.visibility = android.view.View.GONE
            }else{
                mWidget.visibility = android.view.View.VISIBLE
            }
        }


    actual var superView: ViewGroup? = null

    actual open var id: Int
        get() = mWidget.id
        set(value) {
            mWidget.id = value
        }

    actual open var tag: Any?
        get() = mWidget.tag
        set(value) {
            mWidget.tag = value
        }

    actual open var elevation:Float = 0f
        get() {
            if(Build.VERSION.SDK_INT >= 21){
               return mWidget.getElevation()
            }
            return field
        }
        set(value) {
            if(Build.VERSION.SDK_INT >= 21){
                mWidget.elevation = PixelUtil.toPixelFromDIP(value)
            }
            field = value
        }

    actual open var borderStyle:BorderStyle = BorderStyle.SOLID
        set(value) {
            field = value
            mBackground?.setBorderStyle(value)
        }

    private var onPressListener:((view: com.hitales.ui.View)->Unit)? = null

    private var onLongPressListener:((view: com.hitales.ui.View)->Unit)? = null


    actual constructor(frame: Frame) {
        this.frame = frame
    }

    open fun getWidget(): android.view.View {
        return mWidget
    }

    open fun createWidget(): android.view.View {
        return AndroidView(this)
    }

    actual open fun setBackgroundColor(color: Int) {
        if (mBackground != null) {
            mBackground?.setColor(color)
        } else {
            mWidget.setBackgroundColor(color)
        }
        mBackgroundColor = color
    }


    actual open fun removeFromSuperView() {
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

    actual open fun setBorderColor(color: Int) {
        getOrCreateBackground().setBorderColor(color)
    }

    actual open fun setBorderColor(
        leftColor: Int, topColor: Int, rightColor: Int, bottomColor: Int
    ) {

        getOrCreateBackground().setBorderColor(leftColor, topColor, rightColor, bottomColor)
    }

    actual open fun setBorderWidth(borderWidth: Float) {
        getOrCreateBackground().setBorderWidth(borderWidth,borderStyle)
    }

    actual open fun setBorderWidth(leftWidth: Float, topWidth: Float, rightWidth: Float, bottomWidth: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderWidth(leftWidth, topWidth, rightWidth, bottomWidth,borderStyle)
        if (background.clipPath()) {
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        } else {
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    actual open fun setBorderRadius(radius: Float) {
        setBorderRadius(radius, radius, radius, radius)
    }

    actual open fun setBorderRadius(topLeftRadius: Float, topRightRadius: Float, bottomRightRadius: Float, bottomLeftRadius: Float
    ) {
        val background = getOrCreateBackground()
        background.setBorderRadius(topLeftRadius, topRightRadius, bottomRightRadius, bottomLeftRadius)
        if (background.clipPath()) {
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        } else {
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    open fun setLayerType(layerType: Int) {
        if (mWidget.layerType != layerType) {
            mWidget.setLayerType(layerType, null)
        }
    }

    actual fun getBorderLeftWidth(): Float {
        return mBackground?.borderLeftWidth ?: 0f
    }

    actual fun getBorderTopWidth(): Float {
        return mBackground?.borderTopWidth ?: 0f
    }

    actual fun getBorderRightWidth(): Float {
        return mBackground?.borderRightWidth ?: 0f
    }

    actual fun getBorderBottomWidth(): Float {
        return mBackground?.borderBottomWidth ?: 0f
    }

    protected fun getOrCreateBackground(): Background {
        if (mBackground == null) {
            mBackground = Background()
            mWidget.setBackgroundDrawable(mBackground)
            mBackground?.setColor(mBackgroundColor)
        }
        return mBackground!!
    }


    fun getBackground(): Background? {
        return mBackground
    }

    protected open fun onFrameChanged() {
        var params = getLayoutParams()
        mWidget.layoutParams = params
    }

    actual fun setOnPressListener(listener: (view: com.hitales.ui.View) -> Unit) {
        onPressListener = listener
    }

    actual fun setOnLongPressListener(listener: (iew: com.hitales.ui.View) -> Unit) {
        onLongPressListener = listener
    }

    protected open fun getLayoutParams(): FrameLayout.LayoutParams {
        var params = mWidget.layoutParams
        var top = PixelUtil.toPixelFromDIP(frame.y).toInt()
        var left = PixelUtil.toPixelFromDIP(frame.x).toInt()
        var width = PixelUtil.toPixelFromDIP(frame.width).toInt()
        var height = PixelUtil.toPixelFromDIP(frame.height).toInt()
        if (params == null || params !is FrameLayout.LayoutParams) {
            params = FrameLayout.LayoutParams(width, height)
        } else {
            params.width = width
            params.height = height
        }
        params.topMargin = top
        params.leftMargin = left

        return params
    }

    /**
     * @param maxWidth 最大宽度  如果小于等于0表示无限宽
     * @param maxHeight 最大高度  如果小于等于0表示无限高
     */
    actual open fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        return Size(frame.width,frame.height)
    }

    protected open fun setBackgroundDrawable(drawable: Drawable,state: ViewState){
        getOrCreateBackground().setDrawable(drawable,state)
    }

    override fun toString(): String {
        return "${this::class.java.name}: frame :$frame"
    }
}