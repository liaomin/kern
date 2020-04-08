package com.hitales.ui.recycler

import com.hitales.ui.LayoutParams

open class ListViewLayout : DefaultCollectionViewLayout(){
    init {
        maxColumns = 1
    }
}

abstract class ListViewAdapter: CollectionViewAdapter()

open class ListView constructor(layoutParams: LayoutParams? = null, layout: ListViewLayout): CollectionView(layoutParams,layout){

}