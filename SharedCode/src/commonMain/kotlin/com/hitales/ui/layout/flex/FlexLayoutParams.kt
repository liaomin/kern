package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.utils.EdgeInsets

enum class LayoutPosition(val value:Int) {
    ABSOLUTE(1),
    RELATIVE(2),
}

open class FlexLayoutParams : LayoutParams(){

    companion object {
        internal const val FLAG_FLEX_MASK = 1 shl 3
        internal const val FLAG_LEFT_MASK = 1 shl 4
        internal const val FLAG_TOP_MASK = 1 shl 5
        internal const val FLAG_RIGHT_MASK = 1 shl 6
        internal const val FLAG_BOTTOM_MASK = 1 shl 7
        internal const val FLAG_MIN_WIDTH_MASK = 1 shl 8
        internal const val FLAG_MAX_WIDTH_MASK = 1 shl 9
        internal const val FLAG_MIN_HEIGHT_MASK = 1 shl 10
        internal const val FLAG_MAX_HEIGHT_MASK = 1 shl 11
    }



    /**
     * like android weight
     */
    var flex:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_FLEX_MASK.inv()
            }else{
                flag = flag or FLAG_FLEX_MASK
            }
        }

    var position = LayoutPosition.RELATIVE

    var zIndex:Int = 0

    var left:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_LEFT_MASK.inv()
            }else{
                flag = flag or FLAG_LEFT_MASK
            }
        }

    var top:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_TOP_MASK.inv()
            }else{
                flag = flag or FLAG_TOP_MASK
            }
        }

    var right:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_RIGHT_MASK.inv()
            }else{
                flag = flag or FLAG_RIGHT_MASK
            }
        }

    var bottom:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_BOTTOM_MASK.inv()
            }else{
                flag = flag or FLAG_BOTTOM_MASK
            }
        }

    var minWidth = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_MIN_WIDTH_MASK.inv()
            }else{
                flag = flag or FLAG_MIN_WIDTH_MASK
            }
        }

    var maxWidth = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_MAX_WIDTH_MASK.inv()
            }else{
                flag = flag or FLAG_MAX_WIDTH_MASK
            }
        }

    var minHeight = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_MIN_HEIGHT_MASK.inv()
            }else{
                flag = flag or FLAG_MIN_HEIGHT_MASK
            }
        }

    var maxHeight = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_MAX_HEIGHT_MASK.inv()
            }else{
                flag = flag or FLAG_MAX_HEIGHT_MASK
            }
        }

    var margin: EdgeInsets? = null
}