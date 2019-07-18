package com.hitales.ui

expect class Touches {

    fun getLocationX(pointerIndex:Int = 0):Float

    fun getLocationY(pointerIndex:Int = 0):Float

    fun getLocationInWindowX(pointerIndex:Int = 0):Float

    fun getLocationInWindowY(pointerIndex:Int = 0):Float

    fun getPointerSize():Int
}