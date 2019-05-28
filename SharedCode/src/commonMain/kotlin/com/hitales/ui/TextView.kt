package com.hitales.ui

import com.hitales.utils.Frame

enum class TextAlignment(val value:Int) {
    LEFT(0),
    CENTER(1),
    RIGHT(2)
}

expect open class TextView : View {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open var text:CharSequence?
    open var textSize:Float
    /**
     * set all state color
     */
    open var textColor:Int
    open var bold:Boolean
    open var lineHeight:Float
    /**
     * default false
     */
    @Platfrom(PLATFORM_ANDROID)
    open var includeFontPadding:Boolean
    /**
     * default CENTER
     */
    open var textAlignment:TextAlignment


    /**
     * 自定义字体
     */
    open fun setFontStyle(fontName:String)
    open fun setShadow(color:Int,dx:Float,dy: Float,radius:Float)



}
