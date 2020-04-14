//package com.hitales.ui.recycler
//
//import com.hitales.utils.Size
//
//
//interface CollectionViewAdapter {
//
//    fun getNumberOfSection(collectionView: CollectionView):Int{
//        return 1
//    }
//
//    fun haveHeaderView(collectionView:CollectionView,section:Int):Boolean{
//        return false
//    }
//
//    fun haveFooterView(collectionView:CollectionView,section:Int):Boolean{
//        return false
//    }
//
//    fun createHeaderView(collectionView:CollectionView):CollectionViewCell?{
//        return null
//    }
//
//    fun onBindHeaderView(collectionView:CollectionView,section:Int,view:CollectionViewCell){
//    }
//
//    fun createFooterView(collectionView:CollectionView):CollectionViewCell?{
//        return null
//    }
//
//    fun onBindFooterView(collectionView:CollectionView,section:Int,view:CollectionViewCell){
//    }
//
//    fun getSectionHeaderViewSize(collectionView:CollectionView,section:Int,size: Size) {
//        size.set(0f,0f)
//    }
//
//    fun getSectionFooterViewSize(collectionView:CollectionView,section:Int,size: Size) {
//        size.set(0f,0f)
//    }
//
//    fun getItemType(collectionView:CollectionView,section:Int,row: Int): Int{
//        return 0
//    }
//
//    fun getNumberOfItem(collectionView:CollectionView,section:Int):Int
//
//    fun createItemView(collectionView:CollectionView,type:Int):CollectionViewCell
//
//    fun onBindItemView(collectionView:CollectionView,section:Int,row:Int,type:Int,view: CollectionViewCell)
//
//    fun getItemViewSize(collectionView:CollectionView,section:Int,row:Int,type:Int,size: Size)
//
//    fun onItemPress(collectionView:CollectionView,section:Int,row:Int,cell: CollectionViewCell){
//
//    }
//
//    fun onItemLongPress(collectionView:CollectionView,section:Int,row:Int,cell: CollectionViewCell){
//
//    }
//
//}