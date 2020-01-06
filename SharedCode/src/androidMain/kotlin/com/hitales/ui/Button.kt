package com.hitales.ui

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import com.hitales.ui.android.AndroidButton
import com.hitales.ui.android.StateListColor
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame


actual open class Button :  com.hitales.ui.TextView {

    actual open var isEnabled: Boolean
        get() = getWidget().isEnabled
        set(value) {
            getWidget().isEnabled = value
        }


    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        padding = EdgeInsets(5f,5f,5f,5f)
    }

    override fun createWidget(): android.widget.Button {
        return AndroidButton(this)
    }

    override fun getWidget(): android.widget.Button {
        return super.getWidget() as android.widget.Button
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {
        if(state != ViewState.NORMAL){
            getOrCreateBackground().setColorForState(color,state)
        }else{
            if(mBackground != null){
                mBackground?.setColorForState(color,state)
            }else{
                super.setBackgroundColor(color)
            }
        }
    }

    actual open fun setBackgroundImage(image: Image, state: ViewState) {
        val bitmap = image.bitmap
        if(bitmap != null){
            setBackgroundDrawable(BitmapDrawable(bitmap),state)
        }
    }

    actual open fun setTextColor(color: Int, state: ViewState) {
        textColorList.setColorForState(color,state)
    }

    override fun getDefaultColorList(): StateListColor {
        return StateListColor(Color.WHITE)
    }




}
