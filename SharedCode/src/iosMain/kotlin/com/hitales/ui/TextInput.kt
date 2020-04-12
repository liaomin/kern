package com.hitales.ui

import com.hitales.ui.ios.IOSInputView
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Weak
import com.hitales.utils.WeakReference
import platform.Foundation.NSMakeRange
import platform.Foundation.NSMutableAttributedString
import platform.Foundation.addAttribute
import platform.Foundation.create
import platform.UIKit.*


actual open class TextInput : com.hitales.ui.TextView {

    override var text: CharSequence = ""
        set(value) {
            field = value
            getWidget().text = value.toString()
        }

    override var textColor: Int
        get() = getWidget().textColor?.toInt()?:Colors.WHITE
        set(value) {
            getWidget().textColor = value.toUIColor()
        }

    override var textSize: Float
        get() = getWidget().font?.pointSize?.toFloat()?:12f
        set(value) {
            getWidget().font = UIFont.systemFontOfSize(value.toDouble())
        }

    override var bold: Boolean = false
        set(value) {
            field = value
            if(value){
                getWidget().font = UIFont.boldSystemFontOfSize(textSize.toDouble())
            }else{
                getWidget().font = UIFont.systemFontOfSize(textSize.toDouble())
            }
        }

    override var letterSpacing: Float = 0f

    override var numberOfLines: Int = 1

    override var alignment: TextAlignment = TextAlignment.LEFT
        set(value) {
            when (value){
                TextAlignment.RIGHT ->  getWidget().setTextAlignment(NSTextAlignmentRight)
                TextAlignment.CENTER ->  getWidget().setTextAlignment(NSTextAlignmentCenter)
                TextAlignment.LEFT ->  getWidget().setTextAlignment(NSTextAlignmentLeft)
            }
        }

    override var decorationLine: TextDecorationLine = TextDecorationLine.NONE

    override var lineHeight: Float = 0f

    override var ellipsizeMode: TextEllipsizeMode = TextEllipsizeMode.TAIL

    actual constructor(text: CharSequence?, layoutParams: LayoutParams?):super(text, layoutParams) {
        padding = EdgeInsets.value(5f)
    }

    override fun createWidget(): IOSInputView {
        return IOSInputView(WeakReference(this))
    }

    override fun initLabel(text: CharSequence?) {

    }

    override fun getWidget(): IOSInputView {
        return mWidget as IOSInputView
    }

    actual open var placeholder: CharSequence?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual open var placeholderColor: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    /**
     * default false
     */
    actual open var autoFocus: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    /**
     * default true
     */
    actual open var singleLine: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual open fun focus() {

    }

    actual open fun setTextColor(color: Int, state: ViewState) {
    }

    actual open fun cleanFocus() {

    }

    override fun getAttributedString(): NSMutableAttributedString {
        val font = getWidget().font?:UIFont.systemFontOfSize(12.0)
        val text = this.text
        val attr =  NSMutableAttributedString.create(text.toString())
        val range = NSMakeRange(0,text.length.toULong())
        attr.addAttribute(NSFontAttributeName,font,range)
        val style = NSMutableParagraphStyle()
        if(lineHeight >= font.lineHeight){
            style.setMaximumLineHeight(lineHeight.toDouble())
            style.setMinimumLineHeight(lineHeight.toDouble())
            val baseLineOffset =  (lineHeight - font.lineHeight)/ 2.0
            attr.addAttribute(NSBaselineOffsetAttributeName,baseLineOffset,range)
        }
        when (alignment){
            TextAlignment.RIGHT ->  style.setAlignment(NSTextAlignmentRight)
            TextAlignment.CENTER ->  style.setAlignment(NSTextAlignmentCenter)
            TextAlignment.LEFT ->  style.setAlignment(NSTextAlignmentLeft)
        }
        attr.addAttribute(NSParagraphStyleAttributeName,style,range)
//        if(letterSpacing >= 0){
//            attr.addAttribute(NSKernAttributeName,font.pointSize*letterSpacing,range)
//        }
        when (decorationLine){
            TextDecorationLine.UNDERLINE ->  attr.addAttribute(NSUnderlineStyleAttributeName,NSUnderlineStyleSingle,range)
            TextDecorationLine.LINE_THROUGH ->  attr.addAttribute(NSStrikethroughStyleAttributeName,NSUnderlineStyleSingle,range)
            TextDecorationLine.UNDERLINE_LINE_THROUGH ->  {
                attr.addAttribute(NSStrikethroughStyleAttributeName,NSUnderlineStyleSingle,range)
                attr.addAttribute(NSUnderlineStyleAttributeName,NSUnderlineStyleSingle,range)
            }
        }
        return attr
    }

    actual var nextInput: TextInput? by Weak()

}