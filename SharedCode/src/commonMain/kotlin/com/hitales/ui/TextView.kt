package com.hitales.ui

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

enum class FontStyle(var value: Int){
    DEFAULT(0),
    BOLD(1),
    ITALIC(2),
    BOLD_ITALIC(3),
}

expect open class TextView : View {

    constructor(text:CharSequence? = "",layoutParams: LayoutParams? = null)

    open var text:CharSequence

    open var textSize:Float

    /**
     * set all state color
     */
    open var textColor:Int


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
     * android api >= 21
     */
    open var letterSpacing:Float

    /**
     * default TAIL
     */
    open var ellipsizeMode:TextEllipsizeMode

    /**
     * default true
     */
    open var selectable:Boolean

    /**
     * default 0
     */
    open var numberOfLines:Int

    /**
     * 自定义字体
     */
    open fun setFontStyle(fontName:String,style: FontStyle = FontStyle.DEFAULT)

    open fun setFontStyle(style: FontStyle = FontStyle.DEFAULT)

    open fun setTextShadow(color:Int,radius:Float,dx:Float,dy: Float)

    fun enableAutoFontSizeToFit(minFontSize:Int,maxFontSize:Int)

    fun disableAutoFontSizeToFit()

}
