package com.hitales.ui.recycler

import com.hitales.ui.LayoutParams

open class ListViewLayout : DefaultCollectionViewLayout(){
    init {
        maxColumns = 1
    }
}

interface ListViewDataSource : DataSource<ListView>{

    override fun getNumberOfSection(collectionView: ListView): Int {
        return 1
    }

}

open class ListView constructor(layoutParams: LayoutParams? = null, layout: ListViewLayout): CollectionView(layoutParams,layout){

}