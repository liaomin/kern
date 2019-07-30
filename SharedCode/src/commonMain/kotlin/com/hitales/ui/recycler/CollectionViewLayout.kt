package com.hitales.ui.recycler

import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference

abstract class CollectionViewLayout(val collectionViewRef: WeakReference<CollectionView>) {

    abstract fun prepareLayout()

    abstract fun getAttributesInVisibleFrame(frame: Frame):ArrayList<LayoutAttributes>

    abstract fun getContentSize(size: Size)

    open fun getHeaderViewSize(sectionIndex: Int, size: Size){
        collectionViewRef.get()?.apply {
            this.adapter?.getSectionHeaderViewSize(this,sectionIndex,size)
        }
    }

    open fun getFooterViewSize(sectionIndex: Int,size: Size){
        collectionViewRef.get()?.apply {
            this.adapter?.getSectionFooterViewSize(this,sectionIndex,size)
        }
    }

    open fun getItemViewSize(sectionIndex: Int,index: Int,size: Size){
        collectionViewRef.get()?.apply {
            this.adapter?.getItemViewSize(this,sectionIndex,index,size)
        }
    }
}