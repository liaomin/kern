package com.hitales.ui

import com.hitales.ui.android.AndroidLayout
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import java.util.*


actual open class Layout : View {

    actual constructor(layoutParams: LayoutParams):super(layoutParams){
//        mWidget.isFocusable = true
//        mWidget.isFocusableInTouchMode = true
    }

    actual val children: ArrayList<View> = ArrayList()

    actual open fun addSubView(view: View, index: Int) {
        val widget = getWidget()
        if(index < 0){
            widget.addView(view.getWidget())
            children.add(view)
        }else{
            view.getWidget().bringToFront()
            widget.addView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = WeakReference(this)
        view.onAttachedToView(this)
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


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    actual open fun measureChild(child: View,width:Float,height:Float){
        val tempSize = FlexLayout.tempSize
        child.measure(width,height,tempSize)
        var frame = child.frame
        frame.width = tempSize.width
        frame.height = tempSize.height
    }

    override fun measure(widthSpace: Float, heightSpace: Float, outSize: Size?) {
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
            measureChild(view,w, h)
            val frame = view.frame
            frame.x = originX
            frame.y = originY
        }
        if(layoutParams.width.isNaN()){
            outSize?.width = maxWidth
        }else{
            outSize?.width = layoutParams.width
        }
        if(layoutParams.height.isNaN()){
            outSize?.height = maxHeight
        }else{
            outSize?.height = layoutParams.height
        }
    }



}