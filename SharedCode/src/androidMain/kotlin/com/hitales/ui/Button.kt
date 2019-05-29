package com.hitales.ui

import android.graphics.Color
import com.hitales.ui.android.AndroidButton
import com.hitales.ui.android.StateListColor
import com.hitales.utils.Frame


actual open class Button :  com.hitales.ui.TextView {

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getWidget()
        widget.text = text
//        setTextColor(Color.BLUE)
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

}
