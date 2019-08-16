package com.hitales.ui

import com.hitales.utils.Frame
import com.hitales.utils.Size

expect open class ScrollView : ViewGroup {

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

    constructor(frame: Frame = Frame.zero())

    open fun onScroll(offsetX:Float,offsetY:Float)

}


