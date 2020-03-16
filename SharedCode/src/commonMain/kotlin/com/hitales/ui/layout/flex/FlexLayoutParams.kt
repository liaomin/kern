package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams

enum class LayoutPosition(val value:Int) {
    ABSOLUTE(1),
    RELATIVE(2),
}

open class FlexLayoutParams : LayoutParams(){

    companion object{
        const val position_absolute = 1
        const val position_relative = 2
    }

    /**
     * like android weight
     */
    var flex:Float = Float.NaN

    var position = LayoutPosition.RELATIVE

    var zIndex:Int = 0

    var left:Float = Float.NaN

    var top:Float = Float.NaN

    var right:Float = Float.NaN

    var bottom:Float = Float.NaN

    var minWidth = Float.NaN

    var maxWidth = Float.NaN

    var minHeight = Float.NaN

    var maxHeight = Float.NaN

}