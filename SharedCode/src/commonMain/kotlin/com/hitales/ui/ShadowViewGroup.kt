package com.hitales.ui

import com.hitales.utils.Frame

expect class ShadowViewGroup : ViewGroup {

    constructor(frame: Frame = Frame.zero())

    open fun setShadow(shadowColor:Int,shadowOffsetX:Float,shadowOffsetY: Float,shadowRadius:Float)
}