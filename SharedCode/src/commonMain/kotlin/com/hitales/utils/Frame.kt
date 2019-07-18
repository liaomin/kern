package com.hitales.utils

open class Frame(var x:Float = 0f,var y:Float = 0f, var width:Float = 0f,var height:Float = 0f){

    companion object {
        fun zero():Frame{
            return Frame(0f,0f,0f,0f)
        }
    }

    fun set(x:Float,y:Float,width: Float,height: Float){
        this.x = x
        this.y = y
        this.width = width
        this.height = height
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

    fun intersect(frame: Frame):Boolean{
        return getLeft() < frame.getRight() && frame.getLeft() < getRight() && getTop() < frame.getBottom() && frame.getTop() < getBottom()
    }

    fun valid():Boolean{
        return width <= 0 || height <= 0
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