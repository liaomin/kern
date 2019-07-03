package com.hitales.ui

import com.hitales.ui.layout.LayoutManager
import com.hitales.utils.Frame

expect open class ViewGroup : View {

    var layoutManager:LayoutManager?

    constructor(frame: Frame = Frame.zero())

    val children:ArrayList<View>

    open fun addView(view: View, index:Int = -1)

    open fun removeView(view:View)

    open fun layoutSubviews()

    open fun getContentWidth():Float

    open fun getContentHeight():Float

}