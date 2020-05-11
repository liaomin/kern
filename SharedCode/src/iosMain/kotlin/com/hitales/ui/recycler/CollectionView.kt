package com.hitales.ui.recycler

import com.hitales.ui.Animation
import com.hitales.ui.LayoutParams
import com.hitales.ui.ScrollView
import com.hitales.utils.WeakReference
import platform.UIKit.UICollectionView
import platform.UIKit.UIScrollView


actual open class CollectionView : ScrollView {

    companion object {
        val HEADER_TYPE = -0xFFFFF0
        val FOOTER_TYPE = -0xFFFFF1
    }


    actual var layout: CollectionViewLayout


    actual constructor(layoutParams: LayoutParams?, layout: CollectionViewLayout):super(layoutParams){
        this.layout = layout
        layout.collectionView = this
    }

    override fun createWidget(): UIScrollView {
        val weakSelf = WeakReference(this)
        var widget = IOSCollectionView(weakSelf)
        return widget
    }

    override fun getWidget(): UICollectionView {
        return super.getWidget() as UICollectionView
    }

    actual fun reloadData() {
        getWidget().reloadData()
    }

    override fun onScroll(offsetX: Float, offsetY: Float) {
        super.onScroll(offsetX, offsetY)
        layout?.onScroll(offsetX,offsetY)
    }

    actual var dataSource: CollectionViewDataSource?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    actual fun setLayoutWithAnimation(layout: CollectionViewLayout, animation: Animation) {
    }

    actual fun getCell(section: Int, row: Int): CollectionViewCell? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}