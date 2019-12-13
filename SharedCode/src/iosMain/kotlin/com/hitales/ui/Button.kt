package com.hitales.ui

import com.hitales.ui.ios.IOSButtonView
import com.hitales.ui.ios.StateListColor
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import platform.UIKit.*


actual open class Button :  com.hitales.ui.TextView {

    actual open var isEnabled: Boolean
        get() = getWidget().isEnabled()
        set(value) {
            getWidget().enabled = value
        }

    val bgColorList = StateListColor(Colors.BLUE)

    override var text: CharSequence = ""
        get() = super.text
        set(value) {
            field = value
            getWidget().setTitle(value.toString(), UIControlStateNormal)
        }

    override var textSize: Float
        get() = super.textSize
        set(value) {
            getWidget().setFont(getWidget().font.fontWithSize(value.toDouble()))
        }

    override var textColor: Int = Colors.WHITE
        set(value) {
            field = value
            setTextColor(value)
        }

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame) {
        val widget = getWidget()
        widget.setTitle(text?.toString(), UIControlStateNormal)
        setTextColor(Colors.WHITE)
        padding = EdgeInsets(5f, 5f, 5f, 5f)
        widget.clipsToBounds = true
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(color)
        bgColorList?.setColor(color)
    }

    override fun createWidget(): UIView {
        return IOSButtonView(WeakReference(this))
    }

    override fun getWidget(): UIButton {
        return mWidget as UIButton
    }

    override fun getTextWidget(): UILabel {
        return getWidget().titleLabel!!
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {
        bgColorList.setColorForState(color,state)
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
        getWidget()
        when (state){
            ViewState.NORMAL -> getWidget().setTitleColor(color.toUIColor(), UIControlStateNormal)
            ViewState.PRESSED -> getWidget().setTitleColor(color.toUIColor(), UIControlStateHighlighted)
            ViewState.FOCUSED -> getWidget().setTitleColor(color.toUIColor(), UIControlStateFocused)
            ViewState.DISABLED -> getWidget().setTitleColor(color.toUIColor(), UIControlStateDisabled)
            ViewState.SELECTED -> getWidget().setTitleColor(color.toUIColor(), UIControlStateSelected)
        }
    }

    open fun onIOSStateChange(state:UIControlState){
        mWidget.setBackgroundColor(bgColorList.getColorForState(state).toUIColor())
    }


    actual open fun setBackgroundImage(image: Image, state: ViewState) {
        when (state){
            ViewState.NORMAL -> getWidget().setBackgroundImage(image.mImage, UIControlStateNormal)
            ViewState.PRESSED -> getWidget().setBackgroundImage(image.mImage, UIControlStateHighlighted)
            ViewState.FOCUSED -> getWidget().setBackgroundImage(image.mImage, UIControlStateFocused)
            ViewState.DISABLED -> getWidget().setBackgroundImage(image.mImage, UIControlStateDisabled)
            ViewState.SELECTED -> getWidget().setBackgroundImage(image.mImage, UIControlStateSelected)
        }
    }

    override fun measureSize(maxWidth: Float, maxHeight: Float): Size {
        val size = super.measureSize(maxWidth, maxHeight)
        val p = padding
        if( p != null){
            size.width += p.left + p.right
            size.height += p.top + p.bottom
        }
        return size
    }




}
