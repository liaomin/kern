package com.hitales.ui

import com.hitales.utils.Frame

expect open class ShadowViewGroup : ViewGroup {

    constructor(frame: Frame = Frame.zero())

    open fun setShadow(color:Int,dx:Float,dy: Float,radius:Float)
}