package com.hitales.ui

import com.hitales.utils.EdgeInsets

open class LayoutParams {

    var width:Float = Float.NaN

    var height:Float = Float.NaN

    var margin:EdgeInsets? = null
}

expect open class Layout : View {

    constructor(layoutParams: LayoutParams = LayoutParams())

    val children:ArrayList<View>

    open fun addSubView(view: View, index:Int = -1)

    open fun removeSubView(view:View)

    open fun removeAllSubViews()

    open fun measureChild(child: View,width:Float,height:Float)
}

/**
 * 自定义布局
 */
abstract class CustomLayout<T:LayoutParams> : Layout {

    constructor(layoutParams: LayoutParams = LayoutParams()):super(layoutParams)

    override fun addSubView(view: View, index: Int) {
        checkLayoutParams(view)
        super.addSubView(view, index)
    }

    abstract fun checkLayoutParams(child: View)
}
