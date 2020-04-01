package com.hitales.ui

class Screen {

    val width:Float
    val height:Float

    var window:Window

    private constructor(width:Float,height:Float){
        this.width = width
        this.height = height
        window = Window(width,height)
    }

    companion object{

        private var mInstance:Screen? = null

        fun getInstance():Screen{
            if(Platform.debug && mInstance == null){
                throw RuntimeException("framework don't call init method")
            }
            return mInstance!!
        }

        internal fun init(width: Float,height: Float){
            mInstance = Screen(width,height)
        }
    }
}