package com.hitales.ui

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.view.Gravity
import com.hitales.ui.android.AndroidTextView
import com.hitales.ui.android.StateListColor
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame
import com.hitales.utils.Size

actual open class TextView :  View {

    protected val textColorList = getDefaultColorList()

    actual open var text:CharSequence?
        get() = getWidget().text
        set(value) {
            getWidget().text = value
        }

    actual open var textSize: Float
        get() = PixelUtil.toSPFromPixel(getWidget().textSize)
        set(value) {
            getWidget()
            getWidget().setTextSize(TypedValue.COMPLEX_UNIT_SP,value)
        }

    actual open var textColor: Int
        get() = textColorList.defaultColor
        set(value) {
            textColorList.setColor(value)
        }

    actual open var bold:Boolean = false
        set(value) {
            if(field != value){
                val widget = getWidget()
                if(value){
                    widget.typeface = Typeface.DEFAULT_BOLD
                    widget.paint.isFakeBoldText = true
                }else{
                    widget.typeface = Typeface.DEFAULT
                    widget.paint.isFakeBoldText = false
                }
            }
            field = value
        }

    actual constructor(text:CharSequence?,frame: Frame):super(frame){
        val widget = getTextWidget()
        widget.text = text
        widget.setTextColor(textColorList)
    }

    override fun createWidget(): android.widget.TextView {
        return AndroidTextView(this)
    }

    override fun getWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }

    protected open fun getTextWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }


    protected open fun getDefaultColorList(): StateListColor = StateListColor(Color.BLACK)

    private var mHalfLineSpace = 0f

    actual open var lineHeight: Float = 0f
        get() {
            if(field != 0f){
                return field
            }
            return PixelUtil.toDIPFromPixel(getTextWidget().lineHeight.toFloat())
        }
        set(value) {
            val widget = getTextWidget()
            val fontHeight = widget.paint.getFontMetricsInt(null)
            if(value > 0){
                field = value
                val lineHeight = PixelUtil.toPixelFromDIP(value).toInt()
                val space = (lineHeight - fontHeight).toFloat()
                if (lineHeight != fontHeight) {
                    if(space >= 0f){
                        val halfLineSpace= space / 2
                        mHalfLineSpace = halfLineSpace
                        val padding = halfLineSpace.toInt()
                        widget.setLineSpacing(halfLineSpace, 1f)
                        widget.setPadding(0,padding,0,padding)
                    }else{
                        widget.setLineSpacing(space, 1f)
                        mHalfLineSpace = 0f
                    }
                }
            }
        }

    /**
     * default false
     */
    actual open var includeFontPadding: Boolean
        get() {
            if(Build.VERSION.SDK_INT >= 16){
                return getTextWidget().includeFontPadding
            }
            return true
        }
        set(value) {
            if(Build.VERSION.SDK_INT >= 16){
                getTextWidget().includeFontPadding = value
            }
        }

    /**
     * 自定义字体
     */
    actual open fun setFontStyle(fontName: String) {
        getTextWidget().typeface  = Typeface.createFromAsset(Platform.getApplication().assets,fontName)
    }

    actual open fun setShadow(color: Int, dx: Float, dy: Float, radius: Float) {
        getTextWidget().setShadowLayer(PixelUtil.toPixelFromDIP(radius),PixelUtil.toPixelFromDIP(dx),PixelUtil.toPixelFromDIP(dy), color)
    }

    /**
     * default LEFT
     */
    actual open var alignment: TextAlignment = TextAlignment.LEFT
        set(value) {
            field = value
            val textView = getTextWidget()
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                when(value){
                    TextAlignment.CENTER -> textView.textAlignment = android.view.View.TEXT_ALIGNMENT_CENTER
                    TextAlignment.LEFT -> textView.textAlignment = android.view.View.TEXT_ALIGNMENT_TEXT_START
                    TextAlignment.RIGHT -> textView.textAlignment = android.view.View.TEXT_ALIGNMENT_TEXT_END
                }
            }else{
                when(value){
                    TextAlignment.CENTER -> textView.gravity = Gravity.CENTER
                    TextAlignment.LEFT ->  textView.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                    TextAlignment.RIGHT -> textView.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                } }
        }
    /**
     * default NONE
     */
    actual open var decorationLine: TextDecorationLine = TextDecorationLine.NONE
        set(value) {
            field = value
            val textView = getTextWidget()
//            val text = textView.text
//            when(value){
//                TextDecorationLine.NONE -> textView.text = text
//                TextDecorationLine.UNDERLINE -> {
//                    val sp = SpannableString(textView.text)
//                    sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    textView.text = sp
//                }
//                TextDecorationLine.LINE_THROUGH -> {
//                    val sp = SpannableString(textView.text)
//                    sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    textView.text = sp
//                }
//                TextDecorationLine.UNDERLINE_LINE_THROUGH ->  {
//                    val sp = SpannableString(textView.text)
//                    sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//                    textView.text = sp
//                }
//            }
            val paint = textView.paint
            when(value){
                TextDecorationLine.NONE -> paint.flags = 0
                TextDecorationLine.UNDERLINE -> paint.flags = Paint.UNDERLINE_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                TextDecorationLine.LINE_THROUGH ->  paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
                TextDecorationLine.UNDERLINE_LINE_THROUGH ->  paint.flags = Paint.UNDERLINE_TEXT_FLAG or Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
            }
        }

    /**
     * default 0
     */
    actual open var letterSpacing: Float = 0f
        set(value) {
            field = value
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                getTextWidget().letterSpacing = PixelUtil.toPixelFromDIP(value)
            }
        }

    /**
     * default TAIL
     */
    actual open var ellipsizeMode: TextEllipsizeMode = TextEllipsizeMode.TAIL
        set(value) {
            field = value
            when(value){
                TextEllipsizeMode.HEAD -> getTextWidget().ellipsize = TextUtils.TruncateAt.START
                TextEllipsizeMode.MIDDLE -> getTextWidget().ellipsize = TextUtils.TruncateAt.MIDDLE
                TextEllipsizeMode.TAIL -> getTextWidget().ellipsize = TextUtils.TruncateAt.END
            }
        }


    /**
     * default true
     */
    actual open var selectable: Boolean = true
        get() {
            return getTextWidget().isTextSelectable
        }
        set(value) {
            field = value
            getTextWidget().setTextIsSelectable(value)
        }
    /**
     * default 0
     */
    actual open var maxNumberOfLines: Int = 0
        set(value) {
            field = value
            if(value == 1){
                getTextWidget().setSingleLine(true)
            }else{
                getTextWidget().setSingleLine(false)
            }
            getTextWidget().maxLines = value
        }
    /**
     * default AUTO
     */
    actual open var writingDirection: TextWritingDirection = TextWritingDirection.AUTO
        set(value) {
            field = value
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                val textView = getTextWidget()
                when(value){
                    TextWritingDirection.AUTO -> textView.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
                    TextWritingDirection.LTR -> textView.layoutDirection = android.view.View.LAYOUT_DIRECTION_LTR
                    TextWritingDirection.RTL -> textView.layoutDirection = android.view.View.LAYOUT_DIRECTION_RTL
                }
            }
        }

    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        var width = PixelUtil.toPixelFromDIP(maxWidth).toInt()
        var height = PixelUtil.toPixelFromDIP(maxHeight).toInt()
        if( width <= 0 ){
            width = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            width = android.view.View.MeasureSpec.makeMeasureSpec(width, android.view.View.MeasureSpec.AT_MOST)
        }
        if( height <= 0 ){
            height = android.view.View.MeasureSpec.makeMeasureSpec(0, android.view.View.MeasureSpec.UNSPECIFIED)
        }else{
            height = android.view.View.MeasureSpec.makeMeasureSpec(height, android.view.View.MeasureSpec.AT_MOST)
        }
        mWidget.measure(width,height)
        val measuredWidth = mWidget.measuredWidth
        val measuredHeight = mWidget.measuredHeight
        return Size(PixelUtil.toDIPFromPixel(measuredWidth.toFloat()), PixelUtil.toDIPFromPixel(measuredHeight.toFloat()))

    }

}