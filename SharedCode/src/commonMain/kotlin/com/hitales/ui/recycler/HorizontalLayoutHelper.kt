package com.hitales.ui.recycler

import com.hitales.utils.Size

class HorizontalLayoutHelper : LayoutHelper() {

    override fun getNextPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, nextPage: CollectionViewLayout.PageLayoutInfo){

    }

    override fun getLastPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, lastPage: CollectionViewLayout.PageLayoutInfo){

    }

    override fun calculateContextSize(lastAttribute: LayoutAttribute,rowCount:Int,rowHeight: Float,contextSize: Size){

    }

    override fun adjustRow(row: ArrayList<LayoutAttribute>,layout: DefaultCollectionViewLayout, start: Float, end: Float, maxRowHeight: Float) {

    }
}