package com.hitales.ui

import com.hitales.ui.layout.LayoutManager
import com.hitales.utils.Frame

expect open class ViewGroup : View {

    var layoutManager:LayoutManager?

    var scrollX:Float

    var scrollY:Float

    constructor(frame: Frame = Frame.zero())

    val children:ArrayList<View>

    open fun addSubView(view: View, index:Int = -1)

    open fun removeSubView(view:View)

    open fun layoutSubviews()

    open fun getContentWidth():Float

    open fun getContentHeight():Float
}