package com.hitales.ui.recycler

import com.hitales.ios.ui.HAttributes
import com.hitales.ios.ui.HCellDelegateProtocol
import com.hitales.ui.View
import com.hitales.ui.ViewDelegate
import com.hitales.ui.ViewGroup
import com.hitales.ui.ViewState
import com.hitales.utils.WeakReference
import platform.darwin.NSObject

class CellProxy(val cell:CollectionViewCell):NSObject(),HCellDelegateProtocol{

    override fun applyLayoutAttributes(layoutAttributes: HAttributes) {
        val tag2 = layoutAttributes.tag2
        if(tag2 != null && tag2 is LayoutAttribute){
            cell.applyAttribute(tag2)
        }
    }

}

actual open class CollectionViewCell : ViewDelegate {

    actual val contentView: ViewGroup

    actual constructor(){
        contentView = ViewGroup()
        contentView.delegate = WeakReference(this)
    }

    actual open fun applyAttribute(layoutAttribute: LayoutAttribute) {
        val frame = layoutAttribute.frame
        contentView.frame.set(0f,0f,frame.width,frame.height)
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