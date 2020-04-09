package com.hitales.utils

class EdgeInsets {

    companion object {

        fun identity():EdgeInsets{
            return EdgeInsets()
        }

        fun value(value:Float):EdgeInsets{
            return EdgeInsets(value,value,value,value)
        }
    }

    var left:Float

    var top:Float

    var right:Float

    var bottom:Float

    constructor(left:Float = 0f,top:Float = 0f,right:Float = 0f,bottom:Float = 0f){
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    constructor(other: EdgeInsets){
        this.left = other.left
        this.top = other.top
        this.right = other.right
        this.bottom = other.bottom
    }

    override fun equals(other: Any?): Boolean {
        if(this == other){
            return true
        }
        if(other != null && other is EdgeInsets){
            return top == other.top && left == other.left && bottom == other.bottom && right == other.right
        }
        return super.equals(other)
    }

    fun add(other: EdgeInsets?) {
        if(other != null){
            top += other.top
            left += other.left
            bottom += other.bottom
            right += other.right
        }
    }
}