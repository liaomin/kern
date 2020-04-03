package com.hitales.ui

import com.hitales.ui.layout.flex.FlexLayout


interface ScrollViewDelegate : ViewDelegate {
    fun onScroll(view: ScrollView,offsetX: Float, offsetY: Float)
}

//TODO NestedScrolling
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

    constructor(layoutParams: LayoutParams? = null)

    open fun onScroll(offsetX:Float,offsetY:Float)

}


