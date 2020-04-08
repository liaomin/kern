package com.hitales.ui

import com.hitales.ui.layout.flex.FlexLayout


interface ScrollViewDelegate : ViewDelegate {
    fun onBeginScrolling(view: ScrollView)
    fun onEndScrolling(view: ScrollView)
    fun onScroll(view: ScrollView,offsetX: Float, offsetY: Float)
    fun onBeginDragging(view: ScrollView)
    fun onEndDragging(view: ScrollView)
    fun onBeginDecelerating(view: ScrollView)
    fun onEndDecelerating(view: ScrollView)
}

expect open class ScrollView : FlexLayout {

    /**
     * default VERTICAL
     */
    open var orientation:Orientation

    /**
     * default true
     */
    open var showScrollBar:Boolean

    /**
     * default true
     */
    open var scrollEnabled:Boolean

    var scrollX:Float

    var scrollY:Float

    var isPageEnable:Boolean

    constructor(layoutParams: LayoutParams? = null)

    open fun onScroll(offsetX:Float,offsetY:Float)

    fun scrollTo(dx:Float,dy:Float)

    fun scrollBy(dx:Float,dy:Float)

    fun smoothScrollBy(dx:Float,dy:Float)

}


