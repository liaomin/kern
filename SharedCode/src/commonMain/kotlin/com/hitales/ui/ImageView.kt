package com.hitales.ui

import com.hitales.utils.Frame


enum class ImageResizeMode(val value:Int) {
    cover(0),
    contain(1),
    stretch(2),
    repeat(3),
    center(4),
}

expect open class ImageView : View {

    constructor(frame: Frame = Frame.zero())

    open var image:Image?

    open var resizeMode:ImageResizeMode

}