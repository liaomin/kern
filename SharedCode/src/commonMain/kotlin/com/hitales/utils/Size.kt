package com.hitales.utils

open class Size(var width:Float = 0f, var height:Float = 0f) {

    fun set(width: Float,height: Float):Size{
        this.width = width
        this.height = height
        return this
    }

    fun set(width: Int,height: Int):Size{
        this.width = width.toFloat()
        this.height = height.toFloat()
        return this
    }

    fun set(o:Size):Size{
        this.width = o.width
        this.height = o.height
        return this
    }

    fun scale(scale:Float):Size{
        width *= scale
        height *= scale
        return this
    }

    fun reset():Size{
        width = 0f
        height = 0f
        return this
    }

    fun clone():Size{
        return Size(width,height)
    }

    override fun toString(): String {
        return "width:$width height:$height"
    }

}