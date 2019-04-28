package com.hitales.ui

import android.graphics.Color
import com.hitales.utils.Frame


actual open class Button :  com.hitales.ui.TextView {

    actual  var onPressListener:((view:View)->Unit)? = null

    actual  var onLongPressListener:((view:View)->Unit)? = null

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
        return  object : android.widget.Button(Platform.getApplication()){
            override fun drawableStateChanged() {
                super.drawableStateChanged()
            }
        }
    }

    override fun getWidget(): android.widget.Button {
        return super.getWidget() as android.widget.Button
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {
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
