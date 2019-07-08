package com.hitales.utils

open class Size(var width:Float = 0f, var height:Float = 0f) {

    fun set(width: Float,height: Float){
        this.width = width
        this.height = height
    }

    override fun toString(): String {
        return "width:$width height:$height"
    }

}