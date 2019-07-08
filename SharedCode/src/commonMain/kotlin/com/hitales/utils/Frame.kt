package com.hitales.utils

open class Frame(var x:Float = 0f,var y:Float = 0f, var width:Float = 0f,var height:Float = 0f){

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

    fun getLeft():Float{
        return x
    }

    fun getRight():Float{
        return x + width
    }

    fun getTop():Float{
        return y
    }

    fun getBottom():Float{
        return y + height
    }

    fun clone():Frame{
        return Frame(x,y,width, height)
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

    override fun toString(): String {
        return "x:$x y:$y width:$width height:$height"
    }

}