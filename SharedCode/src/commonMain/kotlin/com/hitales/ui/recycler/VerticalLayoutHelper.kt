package com.hitales.ui.recycler

import com.hitales.utils.Size
import com.hitales.utils.min
import kotlin.math.ceil
import kotlin.math.roundToInt

class VerticalLayoutHelper : LayoutHelper() {

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
        var frameStartY = 0f
        var offsetX = paddingLeft
        var offsetY = paddingTop
        var frameEndY =  viewFrame.height * layout.initPageSize
        var endX = viewFrame.width - paddingRight + 0.5f
        var rowHeight = 0f
        var kind = ElementKindNone
        var sectionIndex = 0
        var rowIndex = 0
        val frameWidth = viewFrame.width - paddingRight - paddingLeft
        val attributes = nextPage.attributes
        var position = 0
        val sectionCount = adapter.getNumberOfSection(collectionView)
        var haveHeader = false
        if(currentPage != null && !currentPage.isEmpty()){
            frameStartY = currentPage.frame.getBottom()
            frameEndY += frameStartY
            val last = currentPage.attributes.last()
            offsetY = last.frame.getBottom()
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
                        offsetY += minimumLineSpacing
                    }
                }else if(kind == ElementKindFooter){
                    val nextSection = sectionIndex + 1
                    if(nextSection > sectionCount - 1){
                        val frame = nextPage.frame
                        frame.set(0f,frameStartY,viewFrame.width,0f)
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
        frame.set(0f,frameStartY,viewFrame.width,frameEndY - frameStartY)
        for (i in sectionIndex until sectionCount){
            var section = i
            var number = adapter.getNumberOfItem(collectionView,section)
            //step 1：判断是否有header
            if((rowIndex == 0 && !haveHeader)){
                if(adapter.haveHeaderView(collectionView,section)){
                    adapter.getSectionHeaderViewSize(collectionView,section,tempSize)
                    if(section != 0 && headerAndFooterAddLineSpace){
                        offsetY += minimumLineSpacing
                    }
                    val attribute = layout.getCacheAttribute()
                    attribute.position = position
                    headerHeight = tempSize.height
                    attribute.frame.set(paddingLeft,offsetY,frameWidth,tempSize.height)
                    attribute.section = section
                    kind = ElementKindHeader
                    attribute.elementKind = kind
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
                }else{
                    if(headerAndFooterAddLineSpace){
                        offsetY += minimumLineSpacing
                    }
                }
            }
            rows.clear()
            rowHeight = 0f
            if(rowIndex < 0){
                rowIndex = 0
            }else if(kind == ElementKindCell){
                rowIndex ++
                offsetY += minimumLineSpacing
            }
            for ( j in rowIndex until number){
                rowIndex = j
                val viewType = adapter.getItemType(collectionView,section,rowIndex)
                adapter.getItemViewSize(collectionView,section,rowIndex,viewType,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                val height = tempSize.height
                val width = min(tempSize.width,frameWidth)
                if(offsetX + width > endX || (maxColumns > 0 && rows.size >= maxColumns)){
                    //换行
                    if(!rows.isEmpty()){
                        adjustRow(rows,layout,paddingLeft,endX,rowHeight)
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
                attribute.frame.set(offsetX,offsetY,width,height)
                attribute.section = section
                attribute.row = rowIndex
                attribute.position = position
                kind = ElementKindCell
                attribute.elementKind = kind
                offsetX += width + minimumInteritemSpacing
                rows.add(attribute)
                position++
            }

            if(!rows.isEmpty()){
                adjustRow(rows,layout,paddingLeft,endX,rowHeight)
                attributes.addAll(rows)
                rows.clear()
                offsetY += rowHeight + minimumLineSpacing
                if( offsetY >=  frameEndY){
                    return
                }
            }

            offsetY -= minimumLineSpacing
            offsetX = paddingLeft

            if(adapter.haveFooterView(collectionView,section)){
                if(headerAndFooterAddLineSpace){
                    offsetY += minimumLineSpacing
                }
                adapter.getSectionFooterViewSize(collectionView,section,tempSize)
                val attribute = layout.getCacheAttribute()
                attribute.position = position
                footerHeight = tempSize.height
                attribute.frame.set(paddingLeft,offsetY,frameWidth,tempSize.height)
                attribute.section = section
                kind = ElementKindFooter
                attribute.elementKind = kind
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
            val frameHeight = frame.height
            if(frameHeight < maxRowHeight){
                frame.y += ( maxRowHeight - frameHeight) / 2f
            }
            space -= frame.width
        }
        if(layout.adjustRow && size > 0 && space > 0f){
            val last = row.last()
            val r = last.frame.getRight()
            if(r < end){
                if(size == 1){
                    last.frame.x = start + space / 2f
                }else{
                    val itemSpace = space / (size-1)
                    var offset = start
                    for (i in 0 until size){
                        val f = row[i].frame
                        f.x = offset
                        offset += f.width + itemSpace
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
            var offset = it.frame.getBottom()
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
            offset += collectionView.padding?.bottom?:0f
            contextSize.set(collectionView.frame.width,offset)
        }
    }
}