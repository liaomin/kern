package com.hitales.ui.utils

import android.util.DisplayMetrics
import android.util.TypedValue
import com.hitales.ui.Platform


inline fun Float.toPixelFromDIP():Int{
    return PixelUtil.toPixelFromDIP(this).toInt()
}

class PixelUtil {

    companion object {

        fun toPixelFromDIP(value:Float):Float{
            if(value == 0f) return 0f
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Platform.displayMetrics)
        }

        fun toDIPFromPixel(value:Float,displayMetrics : DisplayMetrics = Platform.displayMetrics):Float{
            if(value == 0f) return value
            return value / displayMetrics.density
        }

        fun toDIPFromPixel(value:Int,displayMetrics : DisplayMetrics = Platform.displayMetrics):Float{
            if(value == 0) return 0f
            return value / displayMetrics.density
        }

        fun getScale(displayMetrics : DisplayMetrics = Platform.displayMetrics):Float{
            return displayMetrics.density
        }

        fun toPixelFromSP(value:Float):Float{
            if(value == 0f) return value
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value,Platform.displayMetrics)
        }

        fun toSPFromPixel(value:Float,displayMetrics : DisplayMetrics = Platform.displayMetrics):Float{
            if(value == 0f) return value
            return value / displayMetrics.scaledDensity
        }

    }
}