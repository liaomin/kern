package com.hitales.ui.recycler

import com.hitales.ui.FrameViewGroup
import com.hitales.ui.ViewGroup

actual open class CollectionViewCell{

    actual val contentView: ViewGroup

    actual constructor(){
        contentView = ViewGroup()
    }

    actual open fun applyAttribute(layoutAttribute: LayoutAttribute) {

    }

}