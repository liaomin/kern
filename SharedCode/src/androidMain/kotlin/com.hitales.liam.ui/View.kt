package com.hitales.liam.ui

import android.graphics.Color
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
        var params = widget.layoutParams
        if(params == null || params  !is FrameLayout.LayoutParams){
            params = FrameLayout.LayoutParams(frame.width.toInt(),frame.height.toInt())
        }else{
            params.width = frame.width.toInt()
            params.height = frame.height.toInt()
        }
        params.topMargin = frame.x.toInt()
        params.leftMargin = frame.y.toInt()
        widget.layoutParams = params
        notificationCenter.notify(NOTIFY_VIEW_LAYOUT_CHANGE,this)
    }

    actual var superView:View? = null

    actual constructor(frame: Frame){
        this.frame = frame
    }

    init {
        setBackgroundColor(0L)
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

    actual open fun setText(text: CharSequence?) {
        getWidget().text = text
    }

    actual open fun getText(): CharSequence? = getWidget().text

}


actual open class Button :  com.hitales.liam.ui.TextView {

    actual  var onPressListener:((view:View)->Unit)? = null

    actual  var onLongPressListener:((view:View)->Unit)? = null

    actual constructor(text:CharSequence?,frame: Frame):super(text,frame){
        val widget = getWidget()
        widget.text = text
        widget.setOnClickListener{ _ -> onPressListener?.invoke(this)}
        widget.setOnLongClickListener{ v ->
            onLongPressListener?.invoke(this)
            if(onLongPressListener != null){
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }

    override fun createWidget(): android.widget.Button {
        return android.widget.Button(Platform.getApplication())
    }

    override fun getWidget(): android.widget.Button {
        return super.getWidget() as android.widget.Button
    }




}
