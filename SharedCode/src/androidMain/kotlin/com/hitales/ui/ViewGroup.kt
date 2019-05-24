package com.hitales.ui

import android.graphics.Canvas
import android.widget.FrameLayout
import com.hitales.utils.Frame
import java.util.ArrayList


open class AndroidFrameLayout(private val view:ViewGroup) : FrameLayout(Platform.getApplication()){

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        view.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        view.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

}
actual open class ViewGroup : View {

    actual constructor(frame: Frame):super(frame){
        mWidget.isFocusable = true
        mWidget.isFocusableInTouchMode = true
    }

    actual val children: ArrayList<View> = ArrayList()

    actual open fun addView(
        view: View,
        index: Int
    ) {
        if(index < 0){
            getWidget().addView(view.getWidget())
            children.add(view)
        }else{
            getWidget().addView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = this
        view.onAttachedToView(this)
    }


    override fun createWidget(): FrameLayout {
        return AndroidFrameLayout(this)
    }

    override fun getWidget(): FrameLayout {
        return super.getWidget() as FrameLayout
    }

    actual open fun removeView(view:View){
        if(children.remove(view)){
            view.onDetachedFromView(this)
        }
        getWidget().removeView(view.getWidget())
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var focused = false
        var focusedView:android.view.View? = null
        children.forEach {
            if(it is TextInput && it.autoFocus){
                if(!focused){
                    focusedView = it.getWidget()
//                    it.getWidget().requestFocus()
                    focused = true
                }
            }
        }
        if(focused){
            focusedView?.requestFocus()
        }else{
            mWidget.isFocusable = true
            mWidget.isFocusableInTouchMode = true
        }
    }


}