package com.hitales.ui

import com.hitales.utils.Frame
import platform.UIKit.UIView
import platform.UIKit.addSubview
import platform.UIKit.insertSubview
import platform.UIKit.removeFromSuperview


actual open class ViewGroup : View {

    actual constructor(frame: Frame):super(frame){

    }

    actual val children: ArrayList<View> = ArrayList<View>()

    actual open fun addView(view: View, index: Int) {
        if(view.superView != null){
            throw RuntimeException("$view already have parent,should remove first")
        }
        val widget = view.getWidget()
        if(index < 0){
            children.add(view)
            getWidget().addSubview(widget)
        }else{
            children.add(index,view)
            getWidget().insertSubview(widget,index.toLong())
        }
        view.onAttachedToView(this)
        view.superView = this
    }


    override fun createWidget(): UIView {
        return UIView()
    }

    actual open fun removeView(view:View){
        if(view.superView == this){
            children.remove(view)
            view.getWidget().removeFromSuperview()
            view.onDetachedFromView(this)
        }
    }


}