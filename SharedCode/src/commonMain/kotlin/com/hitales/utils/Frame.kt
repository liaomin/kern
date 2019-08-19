package com.hitales.utils

open class Frame(var x:Float = 0f,var y:Float = 0f, var width:Float = 0f,var height:Float = 0f){

    companion object {
        fun zero():Frame{
            return Frame(0f,0f,0f,0f)
        }
    }

    fun set(x:Float,y:Float,width: Float,height: Float):Frame{
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        return this
    }

    fun set(other: Frame):Frame{
        this.x = other.x
        this.y = other.y
        this.width = other.width
        this.height = other.height
        return this
    }

    fun scale(scale: Float):Frame{
        this.x *= scale
        this.y *= scale
        this.width *= scale
        this.height *= scale
        return this
    }

    fun reset():Frame{
        x = 0f
        y = 0f
        width = 0f
        height = 0f
        return this
    }

    fun offset(x:Float,y:Float):Frame{
        this.x += x
        this.y += y
        return this
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

    fun contains(o:Frame):Boolean{
        return x <= o.x && y <= o.y && getRight() >= o.getRight() && getBottom() >= o.getBottom()
    }

    fun contains(x:Float,y:Float):Boolean{
        return x >= this.x && x <= getRight() && y >= this.y && y  <= getBottom()
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