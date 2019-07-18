package com.hitales.ui

import com.hitales.utils.Frame
import com.hitales.utils.Size

expect open class ScrollView : ViewGroup {

    /**
     * default true
     */
    open var showScrollBar:Boolean

    /**
     * default true
     */
    open var scrollEnabled:Boolean

    open var contentSize:Size

    constructor(frame: Frame = Frame.zero())

    open fun layoutSubViews(offsetX:Float,offsetY:Float)

    open fun onScroll(offsetX:Float,offsetY:Float)

}


expect open class HorizontalScrollView : ScrollView {

    constructor(frame: Frame = Frame.zero())

}


