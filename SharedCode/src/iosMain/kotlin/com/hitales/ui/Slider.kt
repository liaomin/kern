package com.hitales.ui

actual  open class Slider:View {

    /**
     * min 0
     * default is 100
     */
    actual var max: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual var progress: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    actual var onValueChangeListener: ((slider: Slider, value: Int, max: Int) -> Unit)?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual constructor(layoutParams: LayoutParams?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}