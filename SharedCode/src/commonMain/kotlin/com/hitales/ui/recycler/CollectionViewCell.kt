package com.hitales.ui.recycler

import com.hitales.ui.Layout
import com.hitales.ui.ViewState

expect open class CollectionViewCell {

    val contentView:Layout

    constructor()

    open fun applyAttribute(layoutAttribute: LayoutAttribute)

    open fun setBackgroundColor(color:Int,state: ViewState = ViewState.NORMAL)

}