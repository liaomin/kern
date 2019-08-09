package com.hitales.ui.recycler

import com.hitales.utils.min

class VerticalLayoutHelper : LayoutHelper() {

    override fun getNextPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, nextPage: CollectionViewLayout.PageLayoutInfo){
        val minimumLineSpacing = layout.minimumLineSpacing
        val tempSize = layout.tempSize
        val headerAndFooterAddLineSpace = layout.headerAndFooterAddLineSpace
        val maxColumns = layout.maxColumns
        val minimumInteritemSpacing = layout.minimumInteritemSpacing

        val rows = layout.rows
        val padding = collectionView.padding
        val viewFrame = collectionView.frame

        var paddingTop = 0f
        var paddingLeft = 0f
        var paddingRight = 0f
        var paddingBottom = 0f
        padding?.let {
            paddingTop = it.top
            paddingBottom = it.bottom
            paddingRight = it.right
            paddingLeft = it.left
        }
        var frameStartY = 0f
        var offsetX = paddingLeft
        var offsetY = paddingTop
        var frameEndY =  viewFrame.height
        var endX = viewFrame.width - paddingRight + 1
        var rowHeight = 0f
        var sectionIndex = 0
        var rowIndex = 0
        val frameWidth = viewFrame.width - paddingRight - paddingLeft
        val attributes = nextPage.attributes
        var position = 0
        val sectionCount = adapter.getNumberOfSection(collectionView)
        if(!currentPage.isEmpty()){
            frameStartY = currentPage.frame.getBottom()
            frameEndY += frameStartY
            val last = currentPage.attributes.last()
            offsetY = last.frame.getBottom()
            position = last.position + 1
            sectionIndex = last.section
            val sectionCount = adapter.getNumberOfSection(collectionView)
            rowIndex = last.row
            val rowCount = adapter.getNumberOfItem(collectionView,sectionIndex)

            if(sectionCount > 0 && (rowIndex == rowCount - 1 || last.elementKind == ElementKindFooter)){
                //上一页结束一个section
                if(sectionIndex < sectionCount - 1 ){
                    //开启新section
                    sectionIndex += 1
                    rowIndex = 0

                    val section = sectionIndex - 1
                    if(last.elementKind != ElementKindFooter && adapter.haveFooterView(collectionView,section)){
                        //上一个section有footer
                        if(headerAndFooterAddLineSpace){
                            offsetY += minimumLineSpacing
                        }
                        adapter.getSectionFooterViewSize(collectionView,section,tempSize)
                        val attribute = layout.getCacheAttribute()
                        attribute.position = position
                        attribute.frame.set(paddingLeft,offsetY,frameWidth,tempSize.height)
                        attribute.section = section
                        attribute.elementKind = ElementKindFooter
                        attribute.zIndex = 99
                        attributes.add(attribute)
                        position++
                        offsetY += tempSize.height
                        if( offsetY >=  frameEndY){
                            return
                        }
                    }
                }else{
                    val frame = nextPage.frame
                    frame.set(0f,frameStartY,viewFrame.width,0f)
                    return
                }
            }else if(last.elementKind == ElementKindHeader){
                if(headerAndFooterAddLineSpace){
                    offsetY += minimumLineSpacing
                }
                rowIndex = -1
            }else{
                offsetY += minimumLineSpacing
                rowIndex ++
            }
        }
        val frame = nextPage.frame
        frame.set(0f,frameStartY,viewFrame.width,frameEndY - frameStartY)
        for (i in sectionIndex until sectionCount){
            var section = i
            var row = rowIndex
            if(row < 0){ row = 0 }
            var number = adapter.getNumberOfItem(collectionView,section)
            if(((position == 0 || row == 0 ) && rowIndex>= 0) && adapter.haveHeaderView(collectionView,section)){
                adapter.getSectionHeaderViewSize(collectionView,section,tempSize)
                if(sectionIndex != 0 && headerAndFooterAddLineSpace){
                    offsetY += minimumLineSpacing
                }
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                attribute.frame.set(paddingLeft,offsetY,frameWidth,tempSize.height)
                attribute.section = section
                attribute.elementKind = ElementKindHeader
                attribute.zIndex = 99
                attributes.add(attribute)
                position++
                offsetY += tempSize.height
                offsetX = paddingLeft
                if(headerAndFooterAddLineSpace){
                    offsetY += minimumLineSpacing
                }
                if( offsetY >=  frameEndY){
                    return
                }
            }
            rows.clear()
            rowHeight = 0f
            if(rowIndex < 0){ rowIndex = 0 }
            for ( j in rowIndex until number){
                row = j
                val viewType = adapter.getItemType(collectionView,section,row)
                adapter.getItemViewSize(collectionView,section,row,viewType,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                val height = tempSize.height
                val width = min(tempSize.width,frameWidth)
                if(offsetX + width > endX || (maxColumns > 0 && rows.size >= maxColumns)){
                    //换行
                    if(!rows.isEmpty()){
                        adjustRow(rows,paddingLeft,offsetY,rowHeight)
                    }
                    attributes.addAll(rows)
                    rows.clear()
                    offsetY += rowHeight + minimumLineSpacing
                    if( offsetY >=  frameEndY){
                        return
                    }
                    offsetX = paddingLeft
                    rowHeight = 0f
                }
                if(height > rowHeight){
                    rowHeight = height
                }
                attribute.frame.set(offsetX,offsetY,tempSize.width,height)
                attribute.section = section
                attribute.row = row
                attribute.position = position
                attribute.elementKind = ElementKindCell
                offsetX += width + minimumInteritemSpacing
                rows.add(attribute)
                position++
            }

            if(!rows.isEmpty()){
                adjustRow(rows,paddingLeft,offsetY,rowHeight)
                attributes.addAll(rows)
                rows.clear()
                offsetY += rowHeight
                if( offsetY >=  frameEndY){
                    return
                }
            }

            offsetX = paddingLeft

            if(adapter.haveFooterView(collectionView,section)){
                if(headerAndFooterAddLineSpace){
                    offsetY += minimumLineSpacing
                }
                adapter.getSectionFooterViewSize(collectionView,section,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                attribute.frame.set(paddingLeft,offsetY,frameWidth,tempSize.height)
                attribute.section = section
                attribute.elementKind = ElementKindFooter
                attribute.zIndex = 99
                attributes.add(attribute)
                position++
                offsetY += tempSize.height
            }
            if( offsetY >=  frameEndY){
                return
            }else if(sectionIndex == sectionCount -1){
                offsetY += paddingBottom
                frame.height = offsetY - frame.y
            }
            rowIndex = 0
        }
    }

    override fun getLastPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, lastPage: CollectionViewLayout.PageLayoutInfo){
        if(currentPage.frame.y == 0f){
            lastPage.frame.set(currentPage.frame)
            lastPage.frame.height = 0f
        }
    }


    override fun adjustRow(row: ArrayList<LayoutAttribute>, offsetX: Float, offsetY: Float, maxRowHeight: Float) {

    }
}