package com.hitales.ui.recycler

import java.lang.RuntimeException

open class AdapterHelper{

    data class PositionInfo(val section:Int,val row:Int)

    val adapter: CollectionViewAdapter

    val collectionView: CollectionView

    val itemCount:Int

    val positionInfoArray:Array<PositionInfo>

    constructor(adapter: CollectionViewAdapter,collectionView: CollectionView){
        this.adapter = adapter
        this.collectionView = collectionView
        val sections = adapter.getNumberOfSection(collectionView)
        val positionInfos = ArrayList<PositionInfo>()
        var itemCount = 0
        for ( i in 0 until sections){
            if(adapter.haveHeaderView(collectionView,i)){
                itemCount += 1
                positionInfos.add(PositionInfo(i,CollectionView.HEADER_INDEX))
            }
            val count = adapter.getNumberOfItem(collectionView,i)
            for ( j in 0 until count){
                positionInfos.add(PositionInfo(i,j))
            }
            itemCount += count
            if(adapter.haveFooterView(collectionView,i)){
                itemCount += 1
                positionInfos.add(PositionInfo(i,CollectionView.FOOTER_INDEX))
            }
        }
        positionInfoArray = arrayOfNulls<PositionInfo>(itemCount) as Array<PositionInfo>
        positionInfos.toArray(positionInfoArray)
        this.itemCount = itemCount
    }

    open fun getItemViewType(position: Int): Int {
        val info=  positionInfoArray[position]
        val type = adapter.getItemType(collectionView,info.section,info.row)
        if(info.row == CollectionView.HEADER_INDEX){
            return CollectionView.HEADER_TYPE
        }
        if(info.row == CollectionView.FOOTER_INDEX){
            return CollectionView.FOOTER_TYPE
        }
        if(type == CollectionView.HEADER_TYPE || type == CollectionView.FOOTER_TYPE){
            throw RuntimeException("内置 type ")
        }
        return type
    }

    open fun getInfo(position: Int): PositionInfo {
        return positionInfoArray[position]
    }

}