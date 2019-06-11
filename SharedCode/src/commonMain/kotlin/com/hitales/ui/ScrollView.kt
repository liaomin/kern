package com.hitales.ui

import com.hitales.utils.Frame

expect open class ScrollView : ViewGroup {

    constructor(frame: Frame = Frame.zero())

    open fun layoutSubViews(offsetX:Float,offsetY:Float)

}
