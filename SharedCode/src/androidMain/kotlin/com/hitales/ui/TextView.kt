package com.hitales.ui

import android.graphics.Color
import android.util.TypedValue
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
            getWidget().setTextSize(TypedValue.COMPLEX_UNIT_SP,value)
        }

    actual open var textColor: Int
        get() = getWidget().currentTextColor
        set(value) {
            textColorList.setColor(value)
        }

    actual constructor(text:CharSequence?,frame: Frame):super(frame){
        val widget = getWidget()
        widget.text = text
        widget.setTextColor(textColorList)
    }

    override fun createWidget(): android.widget.TextView {
        return android.widget.TextView(Platform.getApplication())
    }

    override fun getWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }

    protected open fun getDefaultColorList():StateListColor = StateListColor(Color.BLACK)

}