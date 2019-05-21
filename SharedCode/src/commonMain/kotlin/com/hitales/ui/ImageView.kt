package com.hitales.ui

import com.hitales.utils.Frame


expect open class ImageView : View {
    constructor(frame: Frame = Frame.zero())
    open var image:Image?

}