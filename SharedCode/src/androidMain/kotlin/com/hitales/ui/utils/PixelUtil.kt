package com.hitales.ui.utils

import android.util.DisplayMetrics
import android.util.TypedValue
import com.hitales.ui.Platform

class PixelUtil {

    companion object {

        fun toPixelFromDIP(value:Float):Float{
            if(value == 0f) return value
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,Platform.displayMetrics)
        }

        fun toDIPFromPixel(value:Float,displayMetrics : DisplayMetrics = Platform.displayMetrics):Float{
            if(value == 0f) return value
            return value / displayMetrics.density
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