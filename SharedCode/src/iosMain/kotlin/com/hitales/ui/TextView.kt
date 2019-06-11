package com.hitales.ui
import com.hitales.ui.ios.IOSTextView
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import platform.Foundation.NSMutableAttributedString
import platform.Foundation.NSString
import platform.Foundation.initWithString
import platform.UIKit.*

actual open class TextView :  View {

    actual open var text:CharSequence?
        get() = getTextWidget().text
        set(value) {
            getTextWidget().text = value.toString()
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
        widget.text = text?.toString()
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
            val widget = getTextWidget()
            widget.font = UIFont.boldSystemFontOfSize(widget.font().pointSize)
        }

    actual open var lineHeight: Float = 0f
        set(value) {
            field = value
//            getTextWidget().setAttributedText()
            val widget = getTextWidget()
            val t = NSMutableAttributedString()
        }
    /**
     * default false
     */
    actual open var includeFontPadding: Boolean = false

    /**
     * default LEFT
     */
    actual open var alignment: TextAlignment
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default NONE
     */
    actual open var decorationLine: TextDecorationLine
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default 0
     * android api >= 21
     */
    actual open var letterSpacing: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default TAIL
     */
    actual open var ellipsizeMode: TextEllipsizeMode
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default true
     */
    actual open var selectable: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    /**
     * default 0
     */
    actual open var numberOfLines: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

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
            val str = text.toString()
//            val s  = NSString(str)
//            s.boundingRectWithSize()
            return Size(width,maxHeight)
        }else{
            return super.measureSize(maxWidth, maxHeight)
        }
    }


    protected fun getAttributedString():NSMutableAttributedString{
        val attr = NSMutableAttributedString()
//        attr.setAttributes()
        return attr
    }
}