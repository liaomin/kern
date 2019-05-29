package com.hitales.ui

import com.hitales.utils.Frame

enum class TextAlignment(val value:Int) {
    LEFT(0),
    CENTER(1),
    RIGHT(2)
}

enum class TextDecorationLine(val value: Int){
    NONE(0),
    UNDERLINE(1),
    LINE_THROUGH(2),
    UNDERLINE_LINE_THROUGH(3),
}

enum class TextEllipsizeMode(var value: Int){
    HEAD(0),
    MIDDLE(1),
    TAIL(2),
}

enum class TextWritingDirection(var value: Int){
    AUTO(0),
    LTR(1),
    RTL(2),
}


expect open class TextView : View {

    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())

    open var text:CharSequence?

    open var textSize:Float

    /**
     * set all state color
     */
    open var textColor:Int
    /**
     * default false
     */
    open var bold:Boolean

    open var lineHeight:Float

    /**
     * default false
     */
    @TargetPlatform(PLATFORM_ANDROID)
    open var includeFontPadding:Boolean

    /**
     * default LEFT
     */
    open var alignment:TextAlignment

    /**
     * default NONE
     */
    open var decorationLine:TextDecorationLine

    /**
     * default 0
     */
    open var letterSpacing:Float

    /**
     * default TAIL
     */
    open var ellipsizeMode:TextEllipsizeMode

    /**
     * default AUTO
     */
    open var writingDirection:TextWritingDirection

    /**
     * default true
     */
    open var selectable:Boolean

    /**
     * default 0
     */
    open var maxNumberOfLines:Int

    /**
     * 自定义字体
     */
    open fun setFontStyle(fontName:String)
    open fun setShadow(color:Int,dx:Float,dy: Float,radius:Float)



}
