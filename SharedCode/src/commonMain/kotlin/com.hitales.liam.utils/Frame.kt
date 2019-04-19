package com.hitales.liam.utils

open class Frame(var x:Float = 0f,var y:Float = 0f, var width:Float = 0f,var height:Float = 0f) {

    companion object {
        fun zero():Frame{
            return Frame(0f,0f,0f,0f)
        }
    }

    fun getCenterX():Float{
        return x + width / 2f
    }

    fun getCenterY():Float{
        return y + height / 2f
    }

    override fun equals(other: Any?): Boolean {
        if(this == other){
            return true
        }
        if(other != null && other is Frame){
            return x == other.x && y == other.y && width == other.width && height == other.height
        }
        return super.equals(other)
    }

}