package com.hitales.liam.utils

open class Frame(var left:Float = 0f,var top:Float = 0f, var right:Float = 0f,var bottom:Float = 0f) {

    companion object {
        fun zero():Frame{
            return Frame(0f,0f,0f,0f)
        }
    }

    fun getCenterX():Float{
        return (right - left) / 2f
    }

    fun getCenterY():Float{
        return (bottom - top) / 2f
    }

    fun getWdith():Float{
        return right - left
    }

    fun getHeight():Float{
        return bottom - top
    }


    override fun equals(other: Any?): Boolean {
        if(this == other){
            return true
        }
        if(other != null && other is Frame){
            return left == other.left && top == other.top && right == other.right && bottom == other.bottom
        }
        return super.equals(other)
    }

}