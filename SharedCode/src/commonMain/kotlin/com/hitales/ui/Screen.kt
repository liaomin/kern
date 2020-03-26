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

        private var _instsance:Screen? = null

        fun getInstsance():Screen{
            if(Platform.debug && _instsance == null){
                throw RuntimeException("framework don't call init method")
            }
            return _instsance!!
        }

        internal fun init(width: Float,height: Float){
            _instsance = Screen(width,height)
        }
    }
}