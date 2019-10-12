package com.hitales.ui

import android.graphics.Color
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
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame

actual open class TextView :  View {

    protected val textColorList = getDefaultColorList()

    actual open var text:CharSequence
        get() = getWidget().text?:""
        set(value) {
            getWidget().text = value
            dirt()
        }

    actual open var textSize: Float
        get() = PixelUtil.toSPFromPixel(getWidget().textSize)
        set(value) {
            getWidget().setTextSize(TypedValue.COMPLEX_UNIT_SP,value)
        }

    actual open var textColor: Int
        get() = textColorList.defaultColor
        set(value) {
            textColorList.setColorForState(value)
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

    private var linePadding = 0

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
//            if(Build.VERSION.SDK_INT >= 28){
//                widget.lineHeight = PixelUtil.toPixelFromDIP(value).toInt()
//                return
//            }
            field = value
            val lineHeight = PixelUtil.toPixelFromDIP(value).toInt()
            linePadding = 0
            if(value > 0){
                //TODO 计算还有问题
                val space = (lineHeight - fontHeight).toFloat()
                if (lineHeight != fontHeight) {
                    if(space >= 0f){
                        val halfLineSpace= space / 2
                        linePadding = halfLineSpace.toInt()
                        widget.setLineSpacing(space, 1f)
                        widget.setPadding(0,linePadding,0,linePadding)
                    }else{
                        widget.setLineSpacing(space, 1f)
                    }
                }
            }else{
                field = 0f
                widget.setLineSpacing(0f, 1f)
                widget.setPadding(0,0,0,0)
            }
        }

    override fun getPadding(padding: EdgeInsets) {
        super.getPadding(padding)
        padding.top += linePadding
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

    actual open fun setTextShadow(color: Int, dx: Float, dy: Float, radius: Float) {
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
            this.dirt()
//            val paint = textView.paint
//            when(value){
//                TextDecorationLine.NONE -> paint.flags = 0
//                TextDecorationLine.UNDERLINE -> paint.flags = Paint.UNDERLINE_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
//                TextDecorationLine.LINE_THROUGH ->  paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
//                TextDecorationLine.UNDERLINE_LINE_THROUGH ->  paint.flags = Paint.UNDERLINE_TEXT_FLAG or Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
//            }
        }

    /**
     * default 0
     */
    actual open var letterSpacing: Float = 0f
        set(value) {
            field = value
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                getTextWidget().letterSpacing = value
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
    actual open var numberOfLines : Int = 0
        set(value) {
            field = value
            if(value == 1){
                getTextWidget().setSingleLine(true)
                getTextWidget().maxLines = 1
            }else{
                getTextWidget().setSingleLine(false)
            }
            if(value == 0){
                getTextWidget().maxLines = Int.MAX_VALUE
            }else{
                getTextWidget().maxLines = value
            }

        }


    private fun dirt(){
        val text = text
        val textView = getTextWidget()
        if(text != ""){
            if(decorationLine != TextDecorationLine.NONE || lineHeight != 0f){
                val sp = SpannableString(textView.text)
                when(decorationLine){
                    TextDecorationLine.UNDERLINE -> {
                        sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    TextDecorationLine.LINE_THROUGH -> {
                        sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    TextDecorationLine.UNDERLINE_LINE_THROUGH ->  {
                        val sp = SpannableString(textView.text)
                        sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
                textView.text = sp
            }else{
                textView.text = text
            }
        }else{
            textView.text = ""
        }
    }


}