package com.hitales.liam.ui

import com.hitales.liam.utils.Frame

expect class FrameViewGroup : View {

    constructor(frame:Frame = Frame(0f,0f,Platform.windowWidth,Platform.windowHeight))

    val children:ArrayList<View>

    open fun addView(view: View, index:Int = -1)

}