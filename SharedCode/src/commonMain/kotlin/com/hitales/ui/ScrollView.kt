package com.hitales.ui

import com.hitales.utils.Frame

expect open class ScrollView : ViewGroup {

    /**
     * default true
     */
    open var showScrollBar:Boolean

    /**
     * default true
     */
    open var scrollEnabled:Boolean

    constructor(frame: Frame = Frame.zero())

    open fun layoutSubViews(offsetX:Float,offsetY:Float)

}


expect open class HorizontalScrollView : ScrollView {

    constructor(frame: Frame = Frame.zero())

}


