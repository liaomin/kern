package com.hitales.ui

import com.hitales.ui.android.AndroidScrollView
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame


actual open class ScrollView : com.hitales.ui.ViewGroup {

    /**
     * default VERTICAL
     */
    actual open var orientation: Orientation
        get() { return  getWidget().orientation }
        set(value) {
            getWidget().orientation = value
            onOrientationChanged()
        }

    actual constructor(frame: Frame) : super(frame)

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

    /**
     * default true
     */
    actual open var showScrollBar: Boolean
        get() = mWidget.isVerticalScrollBarEnabled
        set(value) {
            mWidget.isVerticalScrollBarEnabled = value
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
    }

    protected open fun onOrientationChanged(){

    }


}