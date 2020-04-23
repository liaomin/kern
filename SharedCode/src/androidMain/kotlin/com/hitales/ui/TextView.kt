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
import com.hitales.ui.android.CustomLineHeightSpan
import com.hitales.ui.android.StateListColor
import com.hitales.ui.android.font.FontManager
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Log

actual open class TextView :  View {

    protected val textColorList = getDefaultColorList()

    actual open var text:CharSequence
        get() = getWidget().text?.toString()?:""
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

    actual constructor(text:CharSequence?,layoutParams: LayoutParams?):super(layoutParams){
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


    actual open var lineHeight: Float = Float.NaN
        set(value) {
            val v = PixelUtil.toPixelFromDIP(value)
           if(field != v){
               field = v
               dirt()
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

    actual open fun setFontStyle(style: FontStyle) {
        val widget = getWidget()
        val typeface = widget.typeface
        try {
            var fontStyle = Typeface.NORMAL
            when (style){
                FontStyle.BOLD -> fontStyle = Typeface.BOLD
                FontStyle.ITALIC -> fontStyle = Typeface.ITALIC
                FontStyle.BOLD_ITALIC -> fontStyle = Typeface.BOLD_ITALIC
            }
            widget.typeface = Typeface.create(typeface,fontStyle)
        }catch (e:Exception){
            Log.e(e)
        }
    }

    /**
     * 自定义字体
     */
    actual open fun setFontStyle(fontName: String,style: FontStyle) {
        var fontStyle = Typeface.NORMAL
        when (style){
            FontStyle.BOLD -> fontStyle = Typeface.BOLD
            FontStyle.ITALIC -> fontStyle = Typeface.ITALIC
            FontStyle.BOLD_ITALIC -> fontStyle = Typeface.BOLD_ITALIC
        }
        getTextWidget().typeface  = FontManager.getInstance().getTypeface(fontName,fontStyle,Platform.getApplication().assets)
    }

    actual open fun setTextShadow(color: Int, radius: Float, dx: Float, dy: Float) {
        getTextWidget().setShadowLayer(PixelUtil.toPixelFromDIP(radius),PixelUtil.toPixelFromDIP(dx),PixelUtil.toPixelFromDIP(dy), color)
    }


    actual fun enableAutoFontSizeToFit(minFontSize:Int,maxFontSize:Int){
        val textView =  getTextWidget()
        if(textView is AndroidTextView){
            textView.enableAutoFontSizeToFit(minFontSize,maxFontSize)
        }
    }

    actual fun disableAutoFontSizeToFit(){
        val textView =  getTextWidget()
        if(textView is AndroidTextView){
            textView.disableAutoFontSizeToFit()
        }
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
            if(field != value){
                field = value
                dirt()
            }
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
     * default 1
     */
    actual open var numberOfLines : Int = 1
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
            val setLineHeight = !lineHeight.isNaN()
            if(decorationLine != TextDecorationLine.NONE || setLineHeight){
                val sp = SpannableString(textView.text)
                when(decorationLine){
                    TextDecorationLine.UNDERLINE -> {
                        sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    TextDecorationLine.LINE_THROUGH -> {
                        sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                    TextDecorationLine.UNDERLINE_LINE_THROUGH ->  {
                        sp.setSpan(StrikethroughSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        sp.setSpan(UnderlineSpan(),0,text.length , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    }
                }
                if(setLineHeight){
                    sp.setSpan(CustomLineHeightSpan(lineHeight),0,text.length,Spanned.SPAN_INCLUSIVE_INCLUSIVE)
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