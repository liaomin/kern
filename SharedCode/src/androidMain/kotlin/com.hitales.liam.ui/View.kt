package com.hitales.liam.ui

import android.widget.FrameLayout
import android.widget.TextView
import com.hitales.liam.utils.Frame
import com.hitales.liam.utils.NotificationCenter

const val NOTIFY_VIEW_LAYOUT_CHANGE = "___NOTIFY_VIEW_LAYOUT_CHANGE___"

val notificationCenter = NotificationCenter.getInstance()

interface ViewWrapper<T> {
    val widget:T
    fun createWidget():T
}


actual open class View {

    private val widget: android.view.View = createWidget()

    actual var frame:Frame
    set(value) {
        field = value
        val params = FrameLayout.LayoutParams(frame.getWdith().toInt(),frame.getHeight().toInt())
        params.topMargin = frame.top.toInt()
        params.leftMargin = frame.left.toInt()
        widget.layoutParams = params
        notificationCenter.notify(NOTIFY_VIEW_LAYOUT_CHANGE,this)
    }

    actual var superView:View? = null

    actual constructor(frame: Frame){
        this.frame = frame
    }

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

    actual constructor(text:CharSequence?,frame: Frame):super(frame){
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
