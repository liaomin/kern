package com.hitales.ui

import android.graphics.Canvas
import android.view.MotionEvent
import android.widget.FrameLayout
//import com.hitales.ui.android.AndroidScrollView
import com.hitales.ui.android.ViewHelper
import com.hitales.utils.Frame
import java.util.*


open class AndroidFrameLayout(private val view:Layout) : FrameLayout(Platform.getApplication()){

    val mViewHelper = ViewHelper(this,view)

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//
//    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mViewHelper.onLayout(changed,left,top,right,bottom)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mViewHelper.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mViewHelper.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        mViewHelper.dispatchDraw(canvas)
        super.dispatchDraw(canvas)
    }

    override fun drawChild(canvas: Canvas?, child: android.view.View?, drawingTime: Long): Boolean {
        return super.drawChild(canvas, child, drawingTime)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return mViewHelper.dispatchTouchEvent(event) || super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewHelper.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

}
actual open class Layout : View {


    actual constructor(frame: Frame):super(frame){
//        mWidget.isFocusable = true
//        mWidget.isFocusableInTouchMode = true
    }


    actual val children: ArrayList<View> = ArrayList()

    actual open fun addSubView(
        view: View,
        index: Int
    ) {
        val widget = getWidget()
        if(index < 0){
            widget.addView(view.getWidget())
            children.add(view)
        }else{
            widget.addView(view.getWidget(),index)
            children.add(index,view)
        }
        view.superView = this
        view.onAttachedToView(this)
    }


    override fun createWidget(): android.view.View {
        return AndroidFrameLayout(this)
    }

    override fun getWidget(): android.view.ViewGroup {
        return super.getWidget() as android.view.ViewGroup
    }

    actual open fun removeSubView(view:View){
        if(children.remove(view)){
            view.onDetachedFromView(this)
        }
        getWidget().removeView(view.getWidget())
    }

    actual open fun removeAllSubViews(){
        val widget = getWidget()
        children.forEach {
            it.onDetachedFromView(this)
        }
        widget.removeAllViews()
        children.clear()
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        var focused = false
//        var focusedView:android.view.View? = null
//        children.forEach {
//            if(it is TextInput && it.autoFocus){
//                if(!focused){
//                    focusedView = it.getWidget()
////                    it.getWidget().requestFocus()
//                    focused = true
//                }
//            }
//        }
//        if(focused){
//            focusedView?.requestFocus()
//        }else{
//            mWidget.isFocusable = true
//            mWidget.isFocusableInTouchMode = true
//        }
//
//        mWidget.isFocusable = false
//        mWidget.isFocusableInTouchMode = false
    }

    actual open fun requestLayout(){
        getWidget().requestLayout()
    }


    override fun releaseResource() {
        super.releaseResource()
        children.forEach {
            it.releaseResource()
        }
        children.clear()
    }

}