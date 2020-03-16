package com.hitales.ui

import android.view.MotionEvent
import com.hitales.ui.utils.PixelUtil

actual class Touches {

    val event:MotionEvent

    constructor(env: MotionEvent){
        event = env
    }

    actual fun getLocationX(pointerIndex: Int): Float {
       return PixelUtil.toDIPFromPixel(event.getX(pointerIndex))
    }

    actual fun getLocationY(pointerIndex: Int): Float {
        return PixelUtil.toDIPFromPixel(event.getY(pointerIndex))
    }

    actual fun getLocationX(): Float {
        return PixelUtil.toDIPFromPixel(event.x)
    }

    actual fun getLocationY(): Float {
        return PixelUtil.toDIPFromPixel(event.y)
    }

    actual fun getLocationInWindowX(pointerIndex: Int): Float {
        return PixelUtil.toDIPFromPixel(event.rawX)
    }

    actual fun getLocationInWindowY(pointerIndex: Int): Float {
        return PixelUtil.toDIPFromPixel(event.rawY)
    }

    actual fun getPointerSize(): Int {
        return event.pointerCount
    }
}