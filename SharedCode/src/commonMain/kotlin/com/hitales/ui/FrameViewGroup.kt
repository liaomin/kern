package com.hitales.ui

import com.hitales.utils.Frame

open class FrameViewGroup : ViewGroup {

    constructor(frame:Frame = Frame(0f,0f,Platform.windowWidth,Platform.windowHeight)):super(frame)

}