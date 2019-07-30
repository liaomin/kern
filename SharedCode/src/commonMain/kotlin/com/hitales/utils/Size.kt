package com.hitales.utils

open class Size(var width:Float = 0f, var height:Float = 0f) {

    fun set(width: Float,height: Float){
        this.width = width
        this.height = height
    }

    fun set(width: Int,height: Int){
        this.width = width.toFloat()
        this.height = height.toFloat()
    }

    fun reset(){
        width = 0f
        height = 0f
    }

    override fun toString(): String {
        return "width:$width height:$height"
    }

}