package com.hitales.ui.recycler

import com.hitales.ui.View
import com.hitales.utils.Size


abstract class CollectionViewAdapter {

    open fun getNumberOfSection(collectionView: CollectionView):Int{
        return 1
    }

    open fun haveHeaderView(collectionView:CollectionView,section:Int):Boolean{
        return false
    }

    open fun haveFooterView(collectionView:CollectionView,section:Int):Boolean{
        return false
    }

    open fun createHeaderView(collectionView:CollectionView):View?{
        return null
    }

    open fun onBindHeaderView(collectionView:CollectionView,section:Int,view:View){
    }

    open fun createFooterView(collectionView:CollectionView):View?{
        return null
    }

    open fun onBindFooterView(collectionView:CollectionView,section:Int,view:View){
    }

    open fun getSectionHeaderViewSize(collectionView:CollectionView,section:Int,size: Size) {
        size.set(0f,0f)
    }

    open fun getSectionFooterViewSize(collectionView:CollectionView,section:Int,size: Size) {
        size.set(0f,0f)
    }

    open fun getItemType(collectionView:CollectionView,section:Int,row: Int): Int{
        return 0
    }

    abstract fun getNumberOfItem(collectionView:CollectionView,section:Int):Int

    abstract fun createItemView(collectionView:CollectionView,type:Int):View

    abstract fun onBindItemView(collectionView:CollectionView,section:Int,row:Int,type:Int,view: View)

    abstract fun getItemViewSize(collectionView:CollectionView,section:Int,row:Int,type:Int,size: Size)

}