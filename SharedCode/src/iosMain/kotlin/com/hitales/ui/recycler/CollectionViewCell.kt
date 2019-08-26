package com.hitales.ui.recycler

import com.hitales.ui.View
import com.hitales.ui.ViewDelegate
import com.hitales.ui.ViewGroup
import com.hitales.ui.ViewState
import com.hitales.utils.WeakReference

actual open class CollectionViewCell : ViewDelegate {

    actual val contentView: ViewGroup

    actual constructor(){
        contentView = ViewGroup()
        contentView.delegate = WeakReference(this)
    }

    actual open fun applyAttribute(layoutAttribute: LayoutAttribute) {
        contentView.frame.set(layoutAttribute.frame)
        contentView.onFrameChanged()
    }

    actual open fun setBackgroundColor(color: Int, state: ViewState) {
        contentView.setBackgroundColor(color)
    }

    override fun onPress(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongPress(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}