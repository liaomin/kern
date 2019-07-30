package com.hitales.ui.recycler

import com.hitales.ui.View
import com.hitales.utils.Size


abstract class CollectionViewAdapter {

    open fun getNumberOfSection(collectionView: CollectionView):Int{
        return 1
    }

    open fun getSectionHeaderView(collectionView:CollectionView,sectionIndex:Int): View?{
        return null
    }

    open fun getSectionFooterView(collectionView:CollectionView,sectionIndex:Int): View?{
        return null
    }

    open fun getSectionHeaderViewSize(collectionView:CollectionView,sectionIndex:Int,size: Size) {
        size.set(0f,0f)
    }

    open fun getSectionFooterViewSize(collectionView:CollectionView,sectionIndex:Int,size: Size) {
        size.set(0f,0f)
    }


    abstract fun getNumberOfItem(view:CollectionView,sectionIndex:Int):Int

    abstract fun getItemView(view:CollectionView,sectionIndex:Int,cachedView: View?): View

    abstract fun getItemViewSize(view:CollectionView,sectionIndex:Int,index:Int,size: Size)

}