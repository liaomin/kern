package com.hitales.ui

import com.hitales.utils.Frame


enum class ImageResizeMode(val value:Int) {
    /**
     * 等比缩放，显示全部图片，留空白
     */
    SCALE_FIT(0),

    /**
     * 不是等比缩放，显示全部图片
     */
    SCALE_FILL(1),

    /**
     * 居中显示，不缩放
     */
    CENTER(2),

    /**
     * 等比缩放，居中显示，不留空白
     */
    SCALE_CENTER_CROP(3),
}

expect open class ImageView : View {

    constructor(frame: Frame = Frame.zero())

    open var image:Image?

    /**
     * default SCALE_FIT
     */
    open var resizeMode:ImageResizeMode

}