package com.hitales.ui

import com.hitales.ui.layout.LayoutManager
import com.hitales.utils.Frame

expect open class ViewGroup : View {

    protected var mLayoutManager:LayoutManager

    constructor(frame: Frame = Frame.zero())

    val children:ArrayList<View>

    open fun addView(view: View, index:Int = -1)

    open fun removeView(view:View)

    open fun layout()

    actual open fun createLayoutManage():LayoutManager

}