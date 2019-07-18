package com.hitales.ui.layout

import com.hitales.ui.View
import com.hitales.ui.ViewGroup

abstract class LayoutManager {

    /**
     * calculate children frame
     */
    abstract fun layoutSubviews(viewGroup: ViewGroup,subviews:ArrayList<View> = viewGroup.children)

}