package com.hitales.ui

import com.hitales.ui.layout.LayoutManager
import com.hitales.ui.layout.LinearLayoutManager
import com.hitales.utils.Frame

open class LinearViewGroup : ViewGroup {

    constructor(frame: Frame = Frame.zero()):super(frame)

    override fun createLayoutManage(): LayoutManager {
        return LinearLayoutManager()
    }
}