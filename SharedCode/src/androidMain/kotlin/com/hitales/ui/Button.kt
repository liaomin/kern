package com.hitales.ui

import android.graphics.Color
import com.hitales.ui.android.AndroidButton
import com.hitales.ui.android.StateListColor
import com.hitales.utils.Frame


actual open class Button :  com.hitales.ui.TextView {

    private var onPressListener:((view:View)->Unit)? = null

    private var onLongPressListener:((view:View)->Unit)? = null

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getWidget()
        widget.text = text
//        setTextColor(Color.BLUE)
        widget.setOnClickListener{
            onPressListener?.invoke(this)
        }
        widget.setOnLongClickListener{
            onLongPressListener?.invoke(this)
            if(onLongPressListener != null){
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    override fun createWidget(): android.widget.Button {
        return AndroidButton(this)
    }

    override fun getWidget(): android.widget.Button {
        return super.getWidget() as android.widget.Button
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {
        if(state == ViewState.NORMAL){
            super.setBackgroundColor(color)
        }else{
            getOrCreateBackground().setColorForState(color,state)
        }
    }

    actual open fun setImage(image: Image, state: ViewState) {
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
        textColorList.setColorForState(color,state)
    }

    override fun getDefaultColorList(): StateListColor {
        return StateListColor(Color.WHITE)
    }

    actual fun setOnPressListener(listener: (iew: View) -> Unit) {
        onPressListener = listener
    }

    actual fun setOnLongPressListener(listener: (iew: View) -> Unit) {
        onLongPressListener = listener
    }

}
