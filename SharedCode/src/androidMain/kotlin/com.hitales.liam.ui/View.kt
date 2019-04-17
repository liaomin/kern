package com.hitales.liam.ui

import android.widget.TextView

interface ViewWrapper<T> {
    val widget:T
    fun createWidget():T
}

actual open class View {

    private val widget: android.view.View = createWidget()


    open fun createWidget(): android.view.View {
       return android.view.View(Platform.getApplication())
    }

    open fun getWidget(): android.view.View {
        return widget
    }


    actual fun setId(id: Int) {
        widget.id = id
    }

    actual fun getId(): Int = widget.id

    actual fun setTag(tag: Any?) {
        widget.tag = tag
    }

    actual fun getTag(): Any? = widget.tag

    actual fun setBackgroundColor(color: Long) {
        widget.setBackgroundColor(color.toInt())
    }


}


actual open class TextView :  View {

    actual constructor(text:CharSequence?):super(){
        getWidget().text = text
    }

    override fun createWidget(): android.widget.TextView {
        return android.widget.TextView(Platform.getApplication())
    }

    override fun getWidget(): android.widget.TextView {
        return super.getWidget() as android.widget.TextView
    }

    actual fun setText(text: CharSequence?) {
        getWidget().text = text
    }

    actual fun getText(): CharSequence? = getWidget().text


}
