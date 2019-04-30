package com.hitales.ui
import com.hitales.utils.Frame
import platform.UIKit.UILabel
import platform.UIKit.UIResponder
import platform.UIKit.UIView

actual open class TextView :  View {

    actual open var text:CharSequence?
        get() = getWidget().text
        set(value) {
            getWidget().text = value.toString()
        }

    actual open var textSize: Float
        get() = getWidget().font.pointSize.toFloat()
        set(value) {
            getWidget().font = getWidget().font.fontWithSize(value.toDouble())
        }

    actual open var textColor: Int
        get() = getWidget().textColor.toInt()
        set(value) {
            getWidget().textColor = value.toUIColor()
        }

    actual constructor(text:CharSequence?,frame: Frame):super(frame){
        initWidget(text,frame)
    }

    open fun initWidget(text:CharSequence?,frame: Frame){
        val widget = getWidget()
        widget.text = text?.toString()
        widget.setTextColor(0xFF000000.toInt().toUIColor())
    }

    override fun createWidget(): UIView {
        return UILabel()
    }

    override fun getWidget(): UILabel {
        return super.getWidget() as UILabel
    }

}