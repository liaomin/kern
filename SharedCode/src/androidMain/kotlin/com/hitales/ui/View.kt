package com.hitales.ui

import android.widget.FrameLayout
import com.hitales.ui.android.Background
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.NotificationCenter

const val NOTIFY_VIEW_LAYOUT_CHANGE = "___NOTIFY_VIEW_LAYOUT_CHANGE___"

val notificationCenter = NotificationCenter.getInstance()


open class AndroidView(private val view:View) : android.view.View(Platform.getApplication()){

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        view.onDetachedFromWindow()
    }

}


actual open class View {

    protected val mWidget: android.view.View = createWidget()

    protected val mBackground: Background by lazy {
       val background = Background()
       mWidget.setBackgroundDrawable(background)
        background
    }

    init {
        mWidget.setBackgroundColor(Colors.TRANSPARENT)
    }

    actual var padding: EdgeInsets? = null

    actual var margin:EdgeInsets? = null

    actual open var frame:Frame
    set(value) {
        field = value
        var params = getLayoutParams()
        params.topMargin = PixelUtil.toPixelFromDIP(frame.y).toInt()
        params.leftMargin = PixelUtil.toPixelFromDIP(frame.x).toInt()
        mWidget.layoutParams = params
        notificationCenter.notify(NOTIFY_VIEW_LAYOUT_CHANGE,this)
    }

    actual var superView:ViewGroup? = null

    actual open var id:Int
        get() = mWidget.id
        set(value) {
            mWidget.id = value
        }

    actual open var tag:Any?
        get() = mWidget.tag
        set(value) {
            mWidget.tag = value
        }

    actual constructor(frame: Frame){
        this.frame = frame
    }

    open fun getWidget(): android.view.View {
        return mWidget
    }

    open fun createWidget(): android.view.View {
       return AndroidView(this)
    }


    actual open fun setBackgroundColor(color: Int) {
        mBackground.setColorForState(color,ViewState.NORMAL)
    }

    protected fun getLayoutParams():FrameLayout.LayoutParams{
        var params = mWidget.layoutParams
        val width = PixelUtil.toPixelFromDIP(frame.width).toInt()
        val height  = PixelUtil.toPixelFromDIP(frame.height).toInt()
        if(params == null || params  !is FrameLayout.LayoutParams){
            params = FrameLayout.LayoutParams(width,height)
        }else{
            params.width = width
            params.height = height
        }
        return params
    }

    override fun toString(): String {
        return "${this::class.java.name}: frame :$frame"
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

    actual open fun setBorderColor(color: Int) {
        mBackground.setBorderColor(color)
    }

    actual open fun setBorderColor(leftColor: Int, topColor: Int, rightColor: Int, bottomColor: Int
    ) {

        mBackground.setBorderColor(leftColor,topColor,rightColor,bottomColor)
    }

    actual open fun setBorderWidth(borderWidth: Float) {
        mBackground.setBorderWidth(borderWidth)
    }

    actual open fun setBorderWidth(
        leftWidth: Float,
        topWidth: Float,
        rightWidth: Float,
        bottomWidth: Float
    ) {
        mBackground.setBorderWidth(leftWidth,topWidth,rightWidth,bottomWidth)
        if(mBackground.clipPath()){
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        }else{
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    actual open fun setBorderRadius(radius: Float) {
        setBorderRadius(radius,radius,radius,radius)
    }

    actual open fun setBorderRadius(topLeftRadius:Float,topRightRadius: Float,bottomRightRadius:Float,bottomLeftRadius:Float) {
        mBackground.setBorderRadius(topLeftRadius,topRightRadius,bottomRightRadius,bottomLeftRadius)
        if(mBackground.clipPath()){
            setLayerType(android.view.View.LAYER_TYPE_SOFTWARE)
        }else{
            setLayerType(android.view.View.LAYER_TYPE_HARDWARE)
        }
    }

    open fun setLayerType(layerType:Int){
        if(mWidget.layerType != layerType) {
            mWidget.setLayerType(layerType, null)
        }
    }
}