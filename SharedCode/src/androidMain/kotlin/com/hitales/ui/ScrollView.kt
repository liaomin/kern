package com.hitales.ui

import com.hitales.ui.android.AndroidScrollView
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.WeakReference


actual open class ScrollView : FlexLayout {

    /**
     * default VERTICAL
     */
    actual open var orientation: Orientation
        get() { return  getWidget().orientation }
        set(value) {
            getWidget().orientation = value
            onOrientationChanged()
        }

    actual  constructor(layoutParams: LayoutParams?) : super(layoutParams)

    actual var scrollX: Float
        get() = PixelUtil.toDIPFromPixel(getWidget().getScrolledX().toFloat())
        set(value) {
            getWidget().scrollX = PixelUtil.toPixelFromDIP(value).toInt()
        }

    actual var scrollY: Float
        get() = PixelUtil.toDIPFromPixel(getWidget().getScrolledY().toFloat())
        set(value) {
            getWidget().scrollY = PixelUtil.toPixelFromDIP(value).toInt()
        }

    override fun createWidget(): android.view.View {
        return AndroidScrollView.createFromXLM(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

    override fun addSubView(view: View, index: Int) {
        val widget = getWidget()
        if(index < 0){
            widget.addSubView(view.getWidget())
            children.add(view)
        }else{
            widget.addSubView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = WeakReference(this)
        view.onAttachedToView(this)
    }

    override fun removeSubView(view: View) {
        if(children.remove(view)){
            view.onDetachedFromView(this)
        }
        getWidget().removeSubView(view.getWidget())
    }

    /**
     * default true
     */
    actual open var showScrollBar: Boolean
        get() = if(orientation == Orientation.VERTICAL) mWidget.isVerticalScrollBarEnabled else mWidget.isHorizontalScrollBarEnabled
        set(value) {
            if(orientation == Orientation.VERTICAL){
                mWidget.isVerticalScrollBarEnabled = value
            }else{
                mWidget.isHorizontalScrollBarEnabled = value
            }
        }

    /**
     * default true
     */
    actual open var scrollEnabled: Boolean
        get() = getWidget().scrollEnabled
        set(value) {
            getWidget().scrollEnabled = value
        }

    actual open fun onScroll(offsetX: Float, offsetY: Float) {
        val delegate = this.delegate?.get()
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onScroll(this,offsetX,offsetY)
        }
    }

    protected open fun onOrientationChanged(){

    }


}