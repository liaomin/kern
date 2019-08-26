package com.hitales.ui
import com.hitales.ui.ios.IOSTextView
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSMakeRange
import platform.Foundation.NSMutableAttributedString
import platform.Foundation.addAttribute
import platform.Foundation.create
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
        this.initLabel(text)
    }

    protected open fun initLabel(text:CharSequence?){
        val widget = getTextWidget()
        if(text != null){
            this.text = text
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
            onFontChanged()
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
                getTextWidget().attributedText = attr
            }
        }

    /**
     * default 0
     */
    actual open var letterSpacing: Float = 0f
        set(value) {
            field = value
            onFontChanged()
        }

    actual open var lineHeight: Float = 0f
        set(value) {
            field = value
            onFontChanged()
        }

    private fun onFontChanged(){
        val textWidget = getTextWidget()
        var attr  = textWidget.attributedText
        if(attr != null){
            attr = (attr as NSMutableAttributedString)
            val range = NSMakeRange(0,text.length.toULong())
            val style = NSMutableParagraphStyle()
            if(letterSpacing >= 0){
                attr.addAttribute(NSKernAttributeName,textWidget.font.pointSize*letterSpacing,range)
            }
            if(lineHeight > textWidget.font.lineHeight){
                style.setMaximumLineHeight(lineHeight.toDouble())
                style.setMinimumLineHeight(lineHeight.toDouble())
                val baseLineOffset =  (lineHeight - textWidget.font.lineHeight)/ 4.0
                println("$lineHeight ${textWidget.font.lineHeight} $baseLineOffset")
                attr.addAttribute(NSBaselineOffsetAttributeName,baseLineOffset,range)
            }else{
                attr.addAttribute(NSBaselineOffsetAttributeName,0,range)
            }
            attr.addAttribute(NSParagraphStyleAttributeName,style,range)
            getTextWidget().attributedText = attr
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
                val p = padding
                if(p != null){
                    return Size(this.size.width.toFloat() + p.left + p.right,this.size.height.toFloat() + p.top + p.bottom)
                }
                return Size(this.size.width.toFloat(),this.size.height.toFloat())
            }
        }else{
            return super.measureSize(maxWidth, maxHeight)
        }
    }

    protected open fun getAttributedString():NSMutableAttributedString{
        val textWidget = getTextWidget()
        val text = this.text
        val attr =  NSMutableAttributedString.create(text.toString())
        val range = NSMakeRange(0,text.length.toULong())
        attr.addAttribute(NSFontAttributeName,textWidget.font,range)
        val style = NSMutableParagraphStyle()
        if(lineHeight >= textWidget.font.lineHeight){
//            style.setLineSpacing(lineHeight - textWidget.font.pointSize)
            style.setMaximumLineHeight(lineHeight.toDouble())
            style.setMinimumLineHeight(lineHeight.toDouble())
            val baseLineOffset =  (lineHeight - textWidget.font.lineHeight)/ 2.0
            attr.addAttribute(NSBaselineOffsetAttributeName,baseLineOffset,range)
        }
        when (alignment){
            TextAlignment.RIGHT ->  style.setAlignment(NSTextAlignmentRight)
            TextAlignment.CENTER ->  style.setAlignment(NSTextAlignmentCenter)
            TextAlignment.LEFT ->  style.setAlignment(NSTextAlignmentLeft)
        }
        attr.addAttribute(NSParagraphStyleAttributeName,style,range)
        if(letterSpacing >= 0){
            attr.addAttribute(NSKernAttributeName,textWidget.font.pointSize*letterSpacing,range)
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

    actual open fun setTextShadow(color: Int, dx: Float, dy: Float, radius: Float) {
    }
}