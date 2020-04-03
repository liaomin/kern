package com.hitales.ui

expect open class Slider : View {

    /**
     * min 0
     * default is 100
     */
    var max:Int

    var progress:Int

    var onValueChangeListener:((slider:Slider,value:Int,max:Int)->Unit)?

    constructor(layoutParams: LayoutParams? = null)

}
