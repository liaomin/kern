package com.hitales.ui
import com.hitales.ios.ui.Convert
import com.hitales.ui.ios.IOSTextView
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.*
import platform.UIKit.*

actual open class TextView :  View {

    actual open var text:CharSequence = ""
        set(value) {
            field = value
            getTextWidget().attributedText = getAttributedString()
        }

    actual open var textSize: Float
        get() = getTextWidget().font.pointSize.toFloat()
        set(value) {
            getTextWidget().font = getTextWidget().font.fontWithSize(value.toDouble())
        }

    actual open var textColor: Int
        get() = getTextWidget().textColor.toInt()
        set(value) {
            getTextWidget().textColor = value.toUIColor()
        }

    actual constructor(text:CharSequence?,frame: Frame):super(frame){
        initWidget(text,frame)
    }

    open fun initWidget(text:CharSequence?,frame: Frame){
        val widget = getTextWidget()
        if(text != null){
            this.text = text!!
        }
        widget.lineBreakMode = NSLineBreakByTruncatingTail
        widget.numberOfLines = 0
        widget.setTextColor(0xFF000000.toInt().toUIColor())
    }

    override fun createWidget(): UIView {
        return IOSTextView(WeakReference(this))
    }

    override fun getWidget(): UIView {
        return super.getWidget() as IOSTextView
    }

    protected open fun getTextWidget(): UILabel {
        return super.getWidget() as UILabel
    }

    /**
     * default false
     */
    actual open var bold: Boolean = false
        set(value) {
            field = value
            val widget = getTextWidget()
            if(value){
                widget.font = UIFont.boldSystemFontOfSize(widget.font().pointSize)
            }else{
                widget.font = UIFont.systemFontOfSize(widget.font().pointSize)
            }
        }

    actual open var lineHeight: Float = 0f
        set(value) {
            field = value
            var attr  = getTextWidget().attributedText
            if(attr != null){
                attr = (attr as NSMutableAttributedString)
                val range = NSMakeRange(0,text.length.toULong())

            }
        }


    /**
     * default false
     */
    actual open var includeFontPadding: Boolean = false

    /**
     * default LEFT
     */
    actual open var alignment: TextAlignment = TextAlignment.LEFT
        set(value) {
            field = value
            when (alignment){
                TextAlignment.RIGHT ->  getTextWidget().textAlignment = NSTextAlignmentRight
                TextAlignment.CENTER ->  getTextWidget().textAlignment = NSTextAlignmentCenter
                TextAlignment.LEFT ->  getTextWidget().textAlignment = NSTextAlignmentLeft
            }
        }

    /**
     * default NONE
     */
    actual open var decorationLine: TextDecorationLine = TextDecorationLine.NONE
        set(value) {
            field = value
            var attr  = getTextWidget().attributedText
            if(attr != null){
                attr = (attr as NSMutableAttributedString)
                val range = NSMakeRange(0,text.length.toULong())
                when (value){
                    TextDecorationLine.UNDERLINE ->  attr.addAttribute(NSUnderlineStyleAttributeName,NSUnderlineStyleSingle,range)
                    TextDecorationLine.LINE_THROUGH ->  attr.addAttribute(NSStrikethroughStyleAttributeName,NSUnderlineStyleSingle,range)
                    TextDecorationLine.UNDERLINE_LINE_THROUGH ->  {
                        attr.addAttribute(NSStrikethroughStyleAttributeName,NSUnderlineStyleSingle,range)
                        attr.addAttribute(NSUnderlineStyleAttributeName,NSUnderlineStyleSingle,range)
                    }
                }
            }
        }

    /**
     * default 0
     * android api >= 21
     */
    actual open var letterSpacing: Float = 0f
        set(value) {
            field = value
            val attr  = getTextWidget().attributedText
            if(attr != null){
                (attr as NSMutableAttributedString).addAttribute(NSKernAttributeName, value,NSMakeRange(0,text.length.toULong()))
            }
        }

    /**
     * default TAIL
     */
    actual open var ellipsizeMode: TextEllipsizeMode = TextEllipsizeMode.TAIL
        set(value) {
            field = value
            when (value){
                TextEllipsizeMode.TAIL -> getTextWidget().lineBreakMode = NSLineBreakByTruncatingTail
                TextEllipsizeMode.HEAD -> getTextWidget().lineBreakMode = NSLineBreakByTruncatingHead
                TextEllipsizeMode.MIDDLE -> getTextWidget().lineBreakMode = NSLineBreakByTruncatingMiddle
            }
        }

    /**
     * default true
     */
    actual open var selectable: Boolean = true

    /**
     * default 0
     */
    actual open var numberOfLines: Int
        get() { return  getTextWidget().numberOfLines.toInt() }
        set(value) {
            getTextWidget().numberOfLines = value.toLong()
        }

    /**
     * 自定义字体
     */
    actual open fun setFontStyle(fontName: String) {
    }

    actual open fun setShadow(color: Int, dx: Float, dy: Float, radius: Float) {
    }


    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        val text = this.text
        if(text != null){
            var width = maxWidth
            var height = maxHeight
            if(width <= 0){
                width = Float.MAX_VALUE
            }
            if(height <= 0){
                height = Float.MAX_VALUE
            }
            return getAttributedString().boundingRectWithSize(CGSizeMake(width.toDouble(),height.toDouble()),
                NSStringDrawingUsesLineFragmentOrigin or NSStringDrawingUsesFontLeading,null).useContents {
                return Size(this.size.width.toFloat(),this.size.height.toFloat())
            }
        }else{
            return super.measureSize(maxWidth, maxHeight)
        }
    }


    protected fun getAttributedString():NSMutableAttributedString{
        val textWidget = getTextWidget()
        val text = this.text
        val attr =  Convert.newNSMutableAttributedString(text.toString())
        val range = NSMakeRange(0,text.length.toULong())
        attr.addAttribute(NSFontAttributeName,textWidget.font,range)
        val style = NSMutableParagraphStyle()
        if(lineHeight >= 0){
            style.setLineSpacing(lineHeight - textWidget.font.pointSize)
            when (alignment){
                TextAlignment.RIGHT ->  style.setAlignment(NSTextAlignmentRight)
                TextAlignment.CENTER ->  style.setAlignment(NSTextAlignmentCenter)
                TextAlignment.LEFT ->  style.setAlignment(NSTextAlignmentLeft)
            }
        }
        attr.addAttribute(NSParagraphStyleAttributeName,style,range)
        if(letterSpacing >= 0){
            attr.addAttribute(NSKernAttributeName,letterSpacing,range)
        }
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
}