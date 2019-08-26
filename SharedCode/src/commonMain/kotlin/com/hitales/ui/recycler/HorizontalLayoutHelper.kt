package com.hitales.ui.recycler

import com.hitales.utils.Size
import kotlin.math.ceil
import kotlin.math.min

class HorizontalLayoutHelper : LayoutHelper() {

    override fun getNextPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo?, nextPage: CollectionViewLayout.PageLayoutInfo){
        val minimumLineSpacing = layout.minimumLineSpacing
        val tempSize = layout.tempSize
        val headerAndFooterAddLineSpace = layout.headerAndFooterAddLineSpace
        val maxColumns = layout.maxColumns
        val minimumInteritemSpacing = layout.minimumInterItemSpacing

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
        var frameStartX = 0f
        var offsetX = paddingLeft
        var offsetY = paddingTop
        var frameEndX =  viewFrame.width * layout.initPageSize
        var endY = viewFrame.height - paddingBottom + 0.5f
        var rowHeight = 0f
        var kind = ElementKindNone
        var sectionIndex = 0
        var rowIndex = 0
        val frameHeight = viewFrame.height - paddingTop - paddingBottom
        val attributes = nextPage.attributes
        var position = 0
        val sectionCount = adapter.getNumberOfSection(collectionView)
        var haveHeader = false
        if(currentPage != null && !currentPage.isEmpty()){
            frameStartX = currentPage.frame.getRight()
            frameEndX += frameStartX
            val last = currentPage.attributes.last()
            offsetX = last.frame.getRight()
            position = last.position + 1
            sectionIndex = last.section
            rowIndex = last.row
            kind = last.elementKind!!
            if(rowIndex < 0){
                //上一个是header或者footer
                rowIndex = 0
                if(kind == ElementKindHeader){
                    haveHeader = true
                    if(headerAndFooterAddLineSpace){
                        offsetX += minimumLineSpacing
                    }
                }else if(kind == ElementKindFooter){
                    val nextSection = sectionIndex + 1
                    if(nextSection > sectionCount - 1){
                        val frame = nextPage.frame
                        frame.set(frameStartX,0f,0f,viewFrame.height)
                        return
                    }else{
                        sectionIndex = nextSection
                        rowIndex = 0
                    }
                }
            }else {
                haveHeader = true
            }
        }
        val frame = nextPage.frame
        frame.set(frameStartX,0f,frameEndX - frameStartX,viewFrame.height)
        for (i in sectionIndex until sectionCount){
            var section = i
            var number = adapter.getNumberOfItem(collectionView,section)
            //step 1：判断是否有header
            if((rowIndex == 0 && !haveHeader)){
                if(adapter.haveHeaderView(collectionView,section)){
                    adapter.getSectionHeaderViewSize(collectionView,section,tempSize)
                    if(section != 0 && headerAndFooterAddLineSpace){
                        offsetX += minimumLineSpacing
                    }
                    val attribute = layout.getCacheAttribute()
                    attribute.position = position
                    headerHeight = tempSize.width
                    attribute.frame.set(offsetX,paddingTop,tempSize.width,frameHeight)
                    attribute.section = section
                    kind = ElementKindHeader
                    attribute.elementKind = kind
                    attribute.zIndex = 99
                    attributes.add(attribute)
                    position++
                    offsetX += tempSize.width
                    offsetY = paddingTop
                    if(headerAndFooterAddLineSpace){
                        offsetX += minimumLineSpacing
                    }
                    if( offsetX >=  frameEndX){
                        return
                    }
                }else{
                    if(headerAndFooterAddLineSpace){
                        offsetX += minimumLineSpacing
                    }
                }
            }
            rows.clear()
            rowHeight = 0f
            if(rowIndex < 0){
                rowIndex = 0
            }else if(kind == ElementKindCell){
                rowIndex ++
                offsetX += minimumLineSpacing
            }
            for ( j in rowIndex until number){
                rowIndex = j
                val viewType = adapter.getItemType(collectionView,section,rowIndex)
                adapter.getItemViewSize(collectionView,section,rowIndex,viewType,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                val height =  min(tempSize.height,frameHeight)
                val width = tempSize.width
                if(offsetY + height > endY || (maxColumns > 0 && rows.size >= maxColumns)){
                    //换行
                    if(!rows.isEmpty()){
                        adjustRow(rows,layout,paddingTop,endY,rowHeight)
                    }
                    attributes.addAll(rows)
                    rows.clear()
                    offsetX += rowHeight + minimumLineSpacing
                    if( offsetX >=  frameEndX){
                        return
                    }
                    offsetY = paddingTop
                    rowHeight = 0f
                }
                if(width > rowHeight){
                    rowHeight = width
                }
                attribute.frame.set(offsetX,offsetY,width,height)
                attribute.section = section
                attribute.row = rowIndex
                attribute.position = position
                kind = ElementKindCell
                attribute.elementKind = kind
                offsetY += height + minimumInteritemSpacing
                rows.add(attribute)
                position++
            }

            if(!rows.isEmpty()){
                adjustRow(rows,layout,paddingTop,endY,rowHeight)
                attributes.addAll(rows)
                rows.clear()
                offsetY += rowHeight + minimumLineSpacing
                if( offsetX >=  frameEndX){
                    return
                }
            }

            offsetX -= minimumLineSpacing
            offsetY = paddingTop

            if(adapter.haveFooterView(collectionView,section)){
                if(headerAndFooterAddLineSpace){
                    offsetX += minimumLineSpacing
                }
                adapter.getSectionFooterViewSize(collectionView,section,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                footerHeight = tempSize.width
                attribute.frame.set(offsetX,paddingTop,footerHeight,frameHeight)
                attribute.section = section
                kind = ElementKindFooter
                attribute.elementKind = kind
                attribute.zIndex = 99
                attributes.add(attribute)
                position++
                offsetX += tempSize.width
            }
            if( offsetX >=  frameEndX){
                return
            }else if(sectionIndex == sectionCount -1){
                offsetX += paddingRight
                frame.width = offsetX - frame.x
            }
            rowIndex = 0
            haveHeader = false
        }
    }


    override fun adjustRow(row: ArrayList<LayoutAttribute>,layout: DefaultCollectionViewLayout, start: Float, end: Float, maxRowHeight: Float) {
        val size = row.size
        if(this.rowHeight < maxRowHeight){
            this.rowHeight = maxRowHeight
        }
        if( rowColumns < size ){
            rowColumns = size
        }
        var space = end - start
        row.forEach {
            val frame = it.frame
            val frameWidth = frame.width
            if(frameWidth < maxRowHeight){
                frame.x += ( maxRowHeight - frameWidth) / 2f
            }
            space -= frame.height
        }
        if(layout.adjustRow && size > 0 && space > 0f){
            val last = row.last()
            val b = last.frame.getBottom()
            if(b < end){
                if(size == 1){
                    last.frame.y = start + space / 2f
                }else{
                    val itemSpace = space / (size-1)
                    var offset = start
                    for (i in 0 until size){
                        val f = row[i].frame
                        f.y = offset
                        offset += f.height + itemSpace
                    }
                }
            }
        }
    }

    override fun calculateContextSize(collectionView: CollectionView,layout: DefaultCollectionViewLayout,adapter: CollectionViewAdapter, page: CollectionViewLayout.PageLayoutInfo, contextSize: Size) {
        page.attributes.last()?.let {
            val minimumLineSpacing = layout.minimumLineSpacing
            val headerAndFooterAddLineSpace = layout.headerAndFooterAddLineSpace
            var section = it.section
            var row = it.row
            var offset = it.frame.getRight()
            var sections = adapter.getNumberOfSection(collectionView)
            for ( i in section until sections){
                var haveHeader = false
                if(row < 0){
                    val kind = it.elementKind
                    if( ElementKindFooter == kind ){
                        continue
                    }else{
                        haveHeader = true
                    }
                }
                row ++
                if( row == 0 && !haveHeader && adapter.haveHeaderView(collectionView, section)){
                    offset += headerHeight
                    if(headerAndFooterAddLineSpace){
                        offset += minimumLineSpacing
                    }
                }
                val itemsCount = adapter.getNumberOfItem(collectionView,section)
                val remaining = itemsCount - row
                if(remaining > 0){
                    var rows = ceil(remaining / rowColumns.toFloat())
                    offset += rows * (rowHeight + minimumLineSpacing)
                }

                if(adapter.haveFooterView(collectionView, section)){
                    offset += footerHeight
                }
                row = 0
            }
            offset += collectionView.padding?.right?:0f
            contextSize.set(offset,collectionView.frame.height)
        }
    }
}