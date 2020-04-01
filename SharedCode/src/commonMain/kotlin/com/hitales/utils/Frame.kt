package com.hitales.utils

open class Frame{

    var x:Float = 0f
        set(value) {
            if(field != value){
                changed = true
            }
            field = value
        }

    var y:Float = 0f
        set(value) {
            if(field != value){
                changed = true
            }
            field = value
        }

    var width:Float = 0f
        set(value) {
            if(field != value){
                changed = true
            }
            field = value
        }

    var height:Float = 0f
        set(value) {
            if(field != value){
                changed = true
            }
            field = value
        }

    private var changed = true

    constructor(x:Float = 0f,y:Float = 0f, width:Float = 0f,height:Float = 0f){
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    companion object {
        fun identity():Frame{
            return Frame(0f,0f,0f,0f)
        }
        internal val invalid:Frame by lazy { Frame(0f,0f,0f,0f) }
    }

    fun isChanged():Boolean{
        val temp = changed
        changed = false
        return temp
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

    fun setSize(width:Float,height: Float):Frame{
        this.width = width
        this.height = height
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
        return width >= 0 && height >= 0
    }

    fun isIdentity():Boolean{
        return x == 0f && y == 0f && width == 0f && height == 0f
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