package com.hitales.ui.recycler

import com.hitales.ui.Animation
import com.hitales.ui.LayoutParams
import com.hitales.ui.ScrollView
import com.hitales.ui.ViewDelegate
import com.hitales.utils.Size


interface CollectionViewDelegate : ViewDelegate {

    fun onItemPress(collectionView:CollectionView,section:Int,row:Int,cell: CollectionViewCell){

    }

    fun onItemLongPress(collectionView:CollectionView,section:Int,row:Int,cell: CollectionViewCell){

    }
}

interface DataSource<T:CollectionView> {
    fun getNumberOfSection(collectionView: T):Int{
        return 1
    }

    fun haveHeaderView(collectionView:T,section:Int):Boolean{
        return false
    }

    fun haveFooterView(collectionView:T,section:Int):Boolean{
        return false
    }

    fun getItemType(collectionView:T,section:Int,row: Int): Int{
        return 0
    }

    fun getNumberOfItem(collectionView:T,section:Int):Int

    fun createHeaderView(collectionView:T):CollectionViewCell{
        return CollectionViewCell()
    }

    fun onBindHeaderView(collectionView:T,section:Int,view:CollectionViewCell){
    }

    fun createFooterView(collectionView:T):CollectionViewCell{
        return CollectionViewCell()
    }

    fun onBindFooterView(collectionView:T,section:Int,view:CollectionViewCell){
    }

    fun createItemView(collectionView:T,type:Int):CollectionViewCell

    fun onBindItemView(collectionView:T,section:Int,row:Int,type:Int,view: CollectionViewCell)


    fun getSectionHeaderViewSize(collectionView:T,section:Int,size: Size) {
        size.set(0f,0f)
    }

    fun getSectionFooterViewSize(collectionView:T,section:Int,size: Size) {
        size.set(0f,0f)
    }

    fun getItemViewSize(collectionView:T,section:Int,row:Int,type:Int,size: Size)

}

interface CollectionViewDataSource : DataSource<CollectionView>


expect open class CollectionView : ScrollView {

    constructor(layoutParams: LayoutParams? = null, layout: CollectionViewLayout = DefaultCollectionViewLayout())

    var layout:CollectionViewLayout

    var dataSource:CollectionViewDataSource?

    fun setLayoutWithAnimation(layout: CollectionViewLayout,animation: Animation)

    fun getCell(section:Int = 0,row:Int):CollectionViewCell?

    fun reloadData()

}