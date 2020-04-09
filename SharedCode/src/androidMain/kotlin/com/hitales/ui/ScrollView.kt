package com.hitales.ui

import androidx.recyclerview.widget.RecyclerView
import com.hitales.ui.android.AndroidScrollView
import com.hitales.ui.layout.flex.FlexDirection
import com.hitales.ui.layout.flex.FlexLayout
import com.hitales.ui.utils.PixelUtil


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

    actual var isPageEnable:Boolean = false

    override fun createWidget(): android.view.View {
        return AndroidScrollView.createFromXLM(this)
    }

    override fun getWidget(): AndroidScrollView {
        return super.getWidget() as AndroidScrollView
    }

    override fun addSubView(view: View, index: Int) {
        checkLayoutParams(view)
        val widget = getWidget()
        if(index < 0){
            widget.addSubView(view.getWidget())
            children.add(view)
        }else{
            widget.addSubView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = this
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
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onScroll(this,offsetX,offsetY)
        }
    }

    open fun onBeginScrolling(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onBeginScrolling(this)
        }
    }

    private fun postSmoothScrollBy(widget:RecyclerView,dx:Int,dy:Int){
        if(dx != 0 || dy != 0){
            widget.post {
                widget.smoothScrollBy(dx,dy)
            }
        }
    }

    open fun onEndScrolling(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onEndScrolling(this)
        }
        if(isPageEnable){
            val widget = getWidget()
            val contextSize = widget.getContextSize()
            if(orientation == Orientation.VERTICAL){
                var contextHeight = contextSize.height
                val height = widget.height
                contextHeight -= height
                val halfHeight = height / 2f
                if(contextHeight > 0 && height > 0){
                    val offsetY = widget.getScrolledY()
                    val mode = offsetY % height
                    if(mode > halfHeight){
                        val offset = height - mode
                        if(offsetY + offset <= contextHeight){
                            postSmoothScrollBy(widget,0,offset)
                        }else{
                            postSmoothScrollBy(widget,0,-mode)
                        }
                    }else{
                        postSmoothScrollBy(widget,0,-mode)
                    }
                }
            }else{
                var contextWidth = contextSize.width
                val width = widget.width
                contextWidth -= width
                val halfWidth = width / 2f
                if(contextWidth > 0 && width > 0){
                    val offsetX = widget.getScrolledX()
                    val mode = offsetX % width
                    if(mode > halfWidth){
                        val offset = width - mode
                        if(offsetX + offset <= contextWidth){
                            postSmoothScrollBy(widget,offset,0)
                        }else{
                            postSmoothScrollBy(widget,-mode,0)
                        }
                    }else{
                        postSmoothScrollBy(widget,-mode,0)
                    }
                }
            }
        }
    }

    open fun onBeginDragging(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onBeginDragging(this)
        }
    }

    open fun onEndDragging(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onEndDragging(this)
        }
    }

    open fun onBeginDecelerating(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onBeginDecelerating(this)
        }
    }

    open fun onEndDecelerating(){
        val delegate = this.delegate
        if(delegate != null && delegate is ScrollViewDelegate){
            delegate.onEndDecelerating(this)
        }
    }

    protected open fun onOrientationChanged(){
        if(orientation == Orientation.VERTICAL){
            flexDirection = FlexDirection.COLUMN
        }else{
            flexDirection = FlexDirection.ROW
        }
    }

    actual fun scrollTo(dx: Float, dy: Float) {
        getWidget().scrollTo(PixelUtil.toPixelFromDIP(dx).toInt(),PixelUtil.toPixelFromDIP(dy).toInt())
    }

    actual fun scrollBy(dx: Float, dy: Float) {
        getWidget().scrollBy(PixelUtil.toPixelFromDIP(dx).toInt(),PixelUtil.toPixelFromDIP(dy).toInt())
    }

    actual fun smoothScrollBy(dx: Float, dy: Float) {
        getWidget().smoothScrollBy(PixelUtil.toPixelFromDIP(dx).toInt(),PixelUtil.toPixelFromDIP(dy).toInt())
    }

}