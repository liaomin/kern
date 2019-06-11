package com.hitales.ui

import com.hitales.utils.Frame

expect open class Button : TextView {

    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())

    open fun setBackgroundColor(color:Int,state: ViewState = ViewState.NORMAL)

    open fun setBackgroundImage(image:Image,state: ViewState = ViewState.NORMAL)

    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)

}