package com.hitales.ui.recycler

import com.hitales.ui.View
import com.hitales.ui.ViewGroup

expect open class CollectionViewCell {

    val contentView:ViewGroup

    constructor()

    open fun applyAttribute(layoutAttribute: LayoutAttribute)

}