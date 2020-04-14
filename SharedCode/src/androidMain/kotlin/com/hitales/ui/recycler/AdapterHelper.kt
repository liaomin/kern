package com.hitales.ui.recycler

open class AdapterHelper{

    data class PositionInfo(val section:Int,val row:Int)

    val collectionView: CollectionView

    var positionInfoArray:Array<PositionInfo>

    constructor(collectionView: CollectionView){
        this.collectionView = collectionView
        val positions = ArrayList<PositionInfo>()
        var itemCount = 0
        val dataSource = collectionView.dataSource
        if(dataSource != null){
            val sections = dataSource.getNumberOfSection(collectionView)
            for ( i in 0 until sections){
                if(dataSource.haveHeaderView(collectionView,i)){
                    itemCount += 1
                    positions.add(PositionInfo(i,CollectionView.HEADER_INDEX))
                }
                val count = dataSource.getNumberOfItem(collectionView,i)
                for ( j in 0 until count){
                    positions.add(PositionInfo(i,j))
                }
                itemCount += count
                if(dataSource.haveFooterView(collectionView,i)){
                    itemCount += 1
                    positions.add(PositionInfo(i,CollectionView.FOOTER_INDEX))
                }
            }
            positionInfoArray = arrayOfNulls<PositionInfo>(itemCount) as Array<PositionInfo>
            positions.toArray(positionInfoArray)
        }else{
            positionInfoArray = arrayOf()
        }
    }

    open fun getItemViewType(position: Int): Int {
        if(position >= 0 && position < positionInfoArray.size){
            val info=  positionInfoArray[position]
            val dataSource = collectionView.dataSource
            val type = dataSource?.getItemType(collectionView,info.section,info.row)?:0
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
        throw RuntimeException("out of bounds position:$position, bounds:[0:${positionInfoArray.size}) ")
    }

    open fun getInfo(position: Int): PositionInfo {
        return positionInfoArray[position]
    }

    fun getCount():Int{
        return positionInfoArray.size
    }

}