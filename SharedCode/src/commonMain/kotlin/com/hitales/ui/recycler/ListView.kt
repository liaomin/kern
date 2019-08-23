package com.hitales.ui.recycler

import com.hitales.utils.Frame

open class ListViewLayout : DefaultCollectionViewLayout(){
    init {
        maxColumns = 1
    }
}

abstract class ListViewAdapter: CollectionViewAdapter()

open class ListView constructor(frame: Frame,layout: ListViewLayout): CollectionView(frame, layout){

}