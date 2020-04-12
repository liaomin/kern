package com.hitales.ui

import com.hitales.ui.ios.IOSView
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import platform.UIKit.*


actual open class Layout : View {

    private val tempSize:Size by lazy { Size() }

    actual constructor(layoutParams: LayoutParams?):super(layoutParams){}

    actual val children: ArrayList<View> = ArrayList<View>()

    actual open fun addSubView(view: View, index: Int) {
        if(view.superView != null){
            throw RuntimeException("$view already have parent,should remove first")
        }
        checkLayoutParams(view)
        val widget = view.getWidget()
        if(index < 0){
            children.add(view)
            getWidget().addSubview(widget)
        }else{
            children.add(index,view)
            getWidget().insertSubview(widget,index.toLong())
        }
        view.onAttachedToView(this)
        view.superView = this
    }


    override fun createWidget(): UIView {
        return IOSView(WeakReference(this))
    }

    actual open fun removeSubView(view:View){
        if(view.superView == this){
            children.remove(view)
            view.getWidget().removeFromSuperview()
            view.onDetachedFromView(this)
            view.superView = null
        }
    }

    actual open fun removeAllSubViews() {
        children.forEach {
            it.removeFromSuperView()
        }
        children.clear()
    }

    actual open var clipsToBounds: Boolean
        get() = mWidget.clipsToBounds
        set(value) {
            mWidget.clipsToBounds = value
        }

    actual open fun measureChild(child: View, width: Float, widthMode: MeasureMode, height: Float, heightMode: MeasureMode, outSize: Size) {
        var maxWidth = width
        var maxHeight = height
        val l = child.layoutParams!!
        var wMode = widthMode
        var hMode = heightMode
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK ==  LayoutParams.FLAG_WIDTH_MASK){
            maxWidth = l.width
            wMode = MeasureMode.EXACTLY
        }
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK ==  LayoutParams.FLAG_HEIGHT_MASK){
            maxHeight = l.height
            hMode = MeasureMode.EXACTLY
        }
        child.measure(maxWidth,wMode,maxHeight,hMode,outSize)
        var frame = child.frame
        frame.width = outSize.width
        frame.height = outSize.height
    }

    actual open fun checkLayoutParams(child: View) {
        if(child.layoutParams == null){
            child.layoutParams = LayoutParams()
        }
    }


    override fun measure(widthSpace: Float, widthMode: MeasureMode, heightSpace: Float, heightMode: MeasureMode, outSize: Size) {
        val maxWidth = 0f
        val maxHeight = 0f
        val padding = this.padding
        var w = widthSpace
        var h = heightSpace
        var originX = 0f
        var originY = 0f
        if(padding != null){
            originX = padding.left
            originY = padding.top
            w -= padding.left + padding.right
            h -= padding.top + padding.bottom
        }
        for (view in children){
            measureChild(view,w,widthMode, h,heightMode,outSize)
            val frame = view.frame
            frame.x = originX
            frame.y = originY
        }
        val layoutParams = this.layoutParams!!
        if(layoutParams.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK){
            outSize.width = maxWidth
        }else{
            outSize.width = layoutParams.width
        }
        if(layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK){
            outSize.height = maxHeight
        }else{
            outSize.height = layoutParams.height
        }
    }

    open fun layoutSubviews(width:Float,height:Float){
        frame.setSize(width,height)
        measure(width,MeasureMode.AT_MOST,height,MeasureMode.AT_MOST,tempSize)
        layout()
    }

    open fun layout() {
        onLayout()
        for (child in children){
            child.onLayout()
        }
    }
}
