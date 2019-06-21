package com.hitales.ui.layout

import com.hitales.ui.ViewGroup

abstract class LayoutManager {

    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
    }

    /**
     * calculate children frame
     */
    abstract fun layoutSubviews(viewGroup: ViewGroup)

}