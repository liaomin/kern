package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.max

open class RowWrapCalculator : RowCalculator() {

    private val allRows = ArrayList<Row>()

    override fun calculate(layout: FlexLayout, children: List<View>, width: Float, widthMode: MeasureMode, selfWidthMode: MeasureMode, height: Float, heightMode: MeasureMode, selfHeightMode: MeasureMode, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        var rows = ArrayList<View>()
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val isReverse = isReverse(layout)
        var totalWidth = width - paddingLeft - paddingRight
        var totalHeight = height - paddingTop - paddingBottom
        var spendWidth = 0f
        var rowHeight = 0f
        var offsetX = paddingLeft
        var offsetY = paddingTop
        var wMode = widthMode
        var hMode = heightMode
        val paddingWidth = paddingLeft + paddingRight
        val paddingHeight = paddingTop + paddingBottom
        if(widthMode == MeasureMode.EXACTLY){
            wMode = MeasureMode.AT_MOST
        }
        if(heightMode == MeasureMode.EXACTLY){
            hMode = MeasureMode.AT_MOST
        }
        val wrapFun = {isLast:Boolean ->
            if(rows.isNotEmpty()){
                var rowH = rowHeight
                val isSingleRow = isLast && allRows.isEmpty()
                if(isSingleRow){
                    setOutSize(width,selfWidthMode,height,selfHeightMode,spendWidth+paddingLeft+paddingRight , rowHeight+paddingHeight,outSize)
                    rowH = outSize.height - paddingHeight
                }
                val row = warp(layout,rows,flexChildren,flexCount,totalWidth,rowH,spendWidth,rowHeight,offsetX,offsetY, isReverse, isSingleRow ,outSize)
                allRows.add(row)
                offsetY += row.rowHeight
                spendWidth = 0f
                flexCount = 0f
                rowHeight = 0f
                flexChildren.clear()
                rows.clear()
            }
        }
        for (index in 0 until children.size){
            val view = children[index]
            val isLast = index == children.size - 1
            val frame = view.frame
            val l = view.layoutParams as FlexLayoutParams
            var measureWidthSpace = totalWidth
            var measureHeightSpace = totalHeight
            val margin = l.margin
            if(margin != null){
                measureWidthSpace -= margin.left - margin.right
                measureHeightSpace -= margin.top - margin.bottom
            }
            if(isFlex(l)){
                val occupyWidth = measureFlexWidth(l)
                if(spendWidth + occupyWidth > totalWidth){
                    wrapFun(isLast)
                }
                flexCount += l.flex
                rows.add(view)
                flexChildren.add(view)
                spendWidth += occupyWidth
                continue
            }
            layout.measureChild(view,measureWidthSpace,wMode,measureHeightSpace,hMode,outSize)

            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            if(spendWidth + occupyWidth > totalWidth){
                wrapFun(isLast)
            }
            rows.add(view)
            spendWidth += occupyWidth
            if(rowHeight < occupyHeight){
                rowHeight = occupyHeight
            }
        }
        wrapFun(true)

        spendWidth = 0f
        var spendHeight = 0f
        for (row in allRows){
            spendHeight += row.rowHeight
            if(spendWidth < row.rowWidth){
                spendWidth = row.rowWidth
            }
        }

        setOutSize(width,selfWidthMode,height,selfHeightMode,spendWidth+paddingWidth,spendHeight+paddingHeight,outSize)

        if(layout.flexWarp == FlexWarp.WARP_REVERSE && allRows.size > 1){
            val last = allRows.last()
            var offsetY = last.originY + last.rowHeight
            for ( row in allRows){
                offsetY -= row.rowHeight
                row.setOriginYTo(offsetY)
            }
        }
        val alignItems = layout.alignItems
        if(alignItems != AlignItems.FLEX_START){
            if(layout !is ScrollView && allRows.size > 1){
                var rowHeight = 0f
                for ( row in allRows){
                    rowHeight += row.rowHeight
                }
                val measuredHeight = outSize.height - paddingHeight
                var offset = 0f
                when(alignItems){
                    AlignItems.CENTER -> {
                        offset = (measuredHeight - rowHeight) / 2f
                    }
                    AlignItems.FLEX_END -> {
                        offset = measuredHeight - rowHeight
                    }
                }
                for ( row in allRows){
                    row.setOriginYBy(offset)
                }
            }
        }
        allRows.clear()
    }


    protected fun warp(layout: FlexLayout,children: ArrayList<View>,flexChildren:ArrayList<View>,flexCount: Float,rowWidth:Float,rowHeight: Float,spendWidth: Float,spendHeight: Float,offsetX: Float, offsetY: Float,isReverse:Boolean,isSingleRow:Boolean,outSize: Size):Row{
        val maxHeight = max(rowHeight,calculateFlex(layout,flexChildren,flexCount,rowWidth,rowHeight,spendWidth,outSize))
        val rows = ArrayList(children)
        children.clear()

        //setOrigin
        var originX = offsetX
        var originY = offsetY
        if(isReverse){
            originX += rowWidth
        }
        var spendWidth = 0f
        var offsetX = originX
        var offsetY = originY
        for (view in rows){
            val l = view.layoutParams as FlexLayoutParams
            val frame = view.frame
            val margin = l.margin
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            var marginLeft = 0f
            var marginRight = 0f
            if (margin != null) {
                marginLeft = margin.left
                marginRight = margin.right
                occupyWidth += marginLeft + marginRight
                occupyHeight += margin.top + margin.bottom
            }
            spendWidth += occupyWidth
            if(isReverse){
                frame.x = offsetX - occupyWidth + marginLeft
                frame.y = offsetY + (margin?.top?:0f)
                offsetX -= occupyWidth
            }else{
                frame.x = offsetX + marginLeft
                frame.y = offsetY + (margin?.top?:0f)
                offsetX += occupyWidth
            }
        }
        val rSpendWidth = spendWidth
        val row = if(isSingleRow) Row(rows,rSpendWidth,maxHeight,rSpendWidth,maxHeight,originX,originY) else Row(rows,rowWidth,maxHeight,rSpendWidth,maxHeight,originX,originY)
        adjustRow(layout,row,isReverse,outSize)
        return row
    }

    private fun measureFlexWidth(l:FlexLayoutParams):Float{
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK == LayoutParams.FLAG_WIDTH_MASK){
            return l.width
        }
        if(l.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK){
            return l.minWidth
        }
        return 0f
    }
}