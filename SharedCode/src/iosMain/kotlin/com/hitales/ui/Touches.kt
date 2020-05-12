package com.hitales.ui

import com.kern.ios.ui.*
import platform.UIKit.UIEvent
import platform.UIKit.UIView

actual class Touches(val event: UIEvent,val view:UIView) {

    actual fun getLocationX(pointerIndex: Int): Float {
       return  event.getLocationX(view,pointerIndex).toFloat()
    }

    actual fun getLocationY(pointerIndex: Int): Float {
        return  event.getLocationY(view,pointerIndex).toFloat()
    }

    actual fun getLocationInWindowX(pointerIndex: Int): Float {
        return  event.getLocationInWindowX(pointerIndex).toFloat()
    }

    actual fun getLocationInWindowY(pointerIndex: Int): Float {
        return  event.getLocationInWindowY(pointerIndex).toFloat()
    }

    actual fun getPointerSize(): Int {
        return  event.getPointerSize()
    }

    actual fun getLocationX(): Float {
        return  event.getLocationX(view).toFloat()
    }

    actual fun getLocationY(): Float {
        return  event.getLocationY(view).toFloat()
    }

}