package com.hitales.utils

data class EdgeInsets( var top:Float = 0f , var left:Float = 0f ,var bottom:Float = 0f, var right:Float = 0f){

    companion object {

        fun zero():EdgeInsets{
            return EdgeInsets()
        }

        fun value(value:Float):EdgeInsets{
            return EdgeInsets(value,value,value,value)
        }
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