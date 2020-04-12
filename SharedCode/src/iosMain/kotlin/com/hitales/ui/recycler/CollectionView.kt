package com.hitales.ui.recycler

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


    actual val layout: CollectionViewLayout

    actual var adapter: CollectionViewAdapter? = null
        set(value) {
            field = value
            if(value != null){

            }else{
                getWidget().delegate = null
            }
        }


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
}