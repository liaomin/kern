package com.hitales.ui

import com.hitales.utils.Frame
import platform.UIKit.UIView
import platform.UIKit.addSubview
import platform.UIKit.insertSubview


actual open class LayoutView : View {

    actual constructor(frame: Frame):super(frame){

    }

    actual val children: ArrayList<View> = ArrayList<View>()

    actual open fun addView(
        view: View,
        index: Int
    ) {
        if(index < 0){
            getWidget().addSubview(view.getWidget())
            children.add(view)
        }else{
            getWidget().insertSubview(view.getWidget(),index.toLong())
            children.add(index,view)
        }
        view.superView = this
        view.onAttachedToView(this)
    }


    override fun createWidget(): UIView {
        return UIView()
    }


    actual open fun removeView(view:View){
    }


}