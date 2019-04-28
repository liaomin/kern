package com.hitales.ui

import android.graphics.Color
import android.widget.FrameLayout
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
    init {
        setBackgroundColor(Color.TRANSPARENT)
    }
    actual var padding: EdgeInsets = EdgeInsets.zero()
    actual var margin:EdgeInsets = EdgeInsets.zero()
        set(value) {
            field = value
            mWidget.setPadding(PixelUtil.toDIPFromPixel(value.left).toInt(),PixelUtil.toDIPFromPixel(value.top).toInt(),PixelUtil.toDIPFromPixel(value.right).toInt(),PixelUtil.toDIPFromPixel(value.bottom).toInt())
        }

    actual open var frame:Frame
    set(value) {
        field = value
        var params = getLayoutParams()
        params.topMargin = PixelUtil.toPixelFromDIP(frame.y).toInt()
        params.leftMargin = PixelUtil.toPixelFromDIP(frame.x).toInt()
        mWidget.layoutParams = params
        notificationCenter.notify(NOTIFY_VIEW_LAYOUT_CHANGE,this)
    }

    actual var border:EdgeInsets? =  null

    actual var borderWith:Float = 0f

    actual var superView:LayoutView? = null

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
        mWidget.setBackgroundColor(color)
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

    actual open fun onAttachedToView(layoutView: LayoutView) {
        println()
    }

    actual open fun onDetachedFromView(layoutView: LayoutView) {

    }
}