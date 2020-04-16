package com.hitales.ui

import com.hitales.ui.android.AndroidLayout
import com.hitales.utils.Size
import java.util.*


actual open class Layout : View {

    actual open var clipsToBounds: Boolean = false
        set(value) {
            if(field != value){
                field = value
               val widget = getWidget()
                widget.clipChildren = true
                widget.invalidate()
            }
        }

    actual constructor(layoutParams: LayoutParams?):super(layoutParams){
//        getWidget().clipChildren = true
//        mWidget.isFocusable = true
//        mWidget.isFocusableInTouchMode = true
    }

    actual val children: ArrayList<View> = ArrayList()

    actual open fun addSubView(view: View, index: Int) {
        checkLayoutParams(view)
        val widget = getWidget()
        if(index < 0){
            widget.addView(view.getWidget())
            children.add(view)
        }else{
            widget.addView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = this
        view.onAttachedToView(this)
    }

    actual open fun checkLayoutParams(child: View){
        if(child.layoutParams == null){
            child.layoutParams = LayoutParams()
        }
    }

    override fun createWidget(): android.view.View {
        return AndroidLayout(this)
    }

    override fun getWidget(): android.view.ViewGroup {
        return super.getWidget() as android.view.ViewGroup
    }

    actual open fun removeSubView(view:View){
        if(children.remove(view)){
            view.onDetachedFromView(this)
        }
        getWidget().removeView(view.getWidget())
    }

    actual open fun removeAllSubViews(){
        val widget = getWidget()
        children.forEach {
            it.onDetachedFromView(this)
        }
        widget.removeAllViews()
        children.clear()
    }

    actual open fun measureChild(child: View,width:Float,widthMode: MeasureMode,height:Float,heightMode: MeasureMode,outSize: Size){
        var maxWidth = width
        var maxHeight = height
        val l = child.layoutParams!!
        var wMode = widthMode
        var hMode = heightMode
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK == LayoutParams.FLAG_WIDTH_MASK){
            maxWidth = l.width
            wMode = MeasureMode.EXACTLY
        }
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK == LayoutParams.FLAG_HEIGHT_MASK){
            maxHeight = l.height
            hMode = MeasureMode.EXACTLY
        }
        child.measure(maxWidth,wMode,maxHeight,hMode,outSize)
        var frame = child.frame
        frame.width = outSize.width
        frame.height = outSize.height
    }

    override fun measure(widthSpace: Float, widthMode: MeasureMode, heightSpace: Float, heightMode: MeasureMode, outSize: Size) {
        val maxWidth = 0f
        val maxHeight = 0f
        val paddingLeft = getPaddingLeft()
        val paddingRight = getPaddingRight()
        val paddingTop = getPaddingTop()
        val paddingBottom = getPaddingBottom()
        var w = widthSpace - paddingLeft - paddingRight
        var h = heightSpace - paddingTop - paddingBottom
        var wMode = widthMode
        var hMode = heightMode
        if(widthMode == MeasureMode.EXACTLY){
            wMode = MeasureMode.AT_MOST
        }
        if(heightMode == MeasureMode.EXACTLY){
            hMode = MeasureMode.AT_MOST
        }

        var originX = paddingLeft
        var originY = paddingTop

        for (view in children){
            measureChild(view,w,wMode, h,hMode,outSize)
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

}