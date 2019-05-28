package com.hitales.ui

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.hitales.ui.android.AndroidTextView
import com.hitales.ui.android.StateListColor
import com.hitales.ui.utils.PixelUtil
import com.hitales.utils.Frame

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
        val widget = getWidget()
        widget.text = text
        widget.setTextColor(textColorList)
    }

    override fun createWidget(): android.widget.TextView {
        return AndroidTextView(this)
    }

    override fun getWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }

    protected open fun getDefaultColorList(): StateListColor =
        StateListColor(Color.BLACK)


    actual open var lineHeight: Float
        get() {
            return getWidget().lineHeight.toFloat()
        }
        set(value) {
            if(Build.VERSION.SDK_INT >= 28){
                getWidget().lineHeight = PixelUtil.toPixelFromDIP(value).toInt()
            }else{
                val widget = getWidget()
                val fontHeight = widget.paint.getFontMetricsInt(null)
                val lineHeight = PixelUtil.toPixelFromDIP(value).toInt()
                if (lineHeight != fontHeight) {
                    widget.setLineSpacing((lineHeight - fontHeight).toFloat(), 1f)
                }
            }
        }

    /**
     * default false
     */
    actual open var includeFontPadding: Boolean
        get() {
            if(Build.VERSION.SDK_INT >= 16){
                return getWidget().includeFontPadding
            }
            return true
        }
        set(value) {
            if(Build.VERSION.SDK_INT >= 16){
                getWidget().includeFontPadding = value
            }
        }

    /**
     * 自定义字体
     */
    actual open fun setFontStyle(fontName: String) {
    }

    actual open fun setShadow(color: Int, dx: Float, dy: Float, radius: Float) {
    }

}