package com.hitales.ui

import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
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
        widget.includeFontPadding = false
        widget.setTextColor(textColorList)
    }

    override fun createWidget(): android.widget.TextView {
        return AppCompatTextView(Platform.getApplication())
    }

    override fun getWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }

    protected open fun getDefaultColorList(): StateListColor =
        StateListColor(Color.BLACK)

}