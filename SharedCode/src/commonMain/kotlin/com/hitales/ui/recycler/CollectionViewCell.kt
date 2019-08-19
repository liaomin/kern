package com.hitales.ui.recycler

import com.hitales.ui.View
import com.hitales.ui.ViewGroup
import com.hitales.ui.ViewState

expect open class CollectionViewCell {

    val contentView:ViewGroup

    constructor()

    open fun applyAttribute(layoutAttribute: LayoutAttribute)

    open fun setBackgroundColor(color:Int,state: ViewState = ViewState.NORMAL)

}