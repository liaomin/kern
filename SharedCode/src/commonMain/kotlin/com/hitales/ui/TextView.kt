package com.hitales.ui

import com.hitales.utils.Frame

expect open class TextView : View {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open var text:CharSequence?
    open var textSize:Float
    /**
     * set all state color
     */
    open var textColor:Int
    open var bold:Boolean
//    open fun
}
