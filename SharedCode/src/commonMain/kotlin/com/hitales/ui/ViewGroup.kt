package com.hitales.ui

import com.hitales.utils.Frame

expect open class ViewGroup : View {

    constructor(frame: Frame = Frame.zero())

    val children:ArrayList<View>

    open fun addSubView(view: View, index:Int = -1)

    open fun removeSubView(view:View)

    open fun layoutSubviews()

}