package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.max

open class ColumnWrapCalculator : ColumnCalculator() {

    private val allRows = ArrayList<Row>()

    override fun calculate(layout: FlexLayout, children: List<View>, width: Float, widthMode: MeasureMode, selfWidthMode: MeasureMode, height: Float, heightMode: MeasureMode, selfHeightMode: MeasureMode, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        var rows = ArrayList<View>()
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val isReverse = isReverse(layout)
        var totalWidth = width - paddingLeft - paddingRight
        var totalHeight = height - paddingTop - paddingBottom
        var spendHeight = 0f
        var rowWidth = 0f
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
                var rowW = rowWidth
                val isSingleRow = isLast && allRows.isEmpty()
                if(isSingleRow){
                    setOutSize(width,selfWidthMode,height,selfHeightMode,rowWidth+paddingLeft+paddingRight , spendHeight+paddingHeight,outSize)
                    rowW = outSize.width - paddingHeight
                }
                val row = warp(layout,rows,flexChildren,flexCount,rowW,totalHeight,rowWidth,spendHeight,offsetX,offsetY, isReverse, isSingleRow ,outSize)
                allRows.add(row)
                offsetX += row.rowWidth
                spendHeight = 0f
                flexCount = 0f
                rowWidth = 0f
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
                val occupyHeight = measureFlexHeight(l)
                if(spendHeight + occupyHeight > totalHeight){
                    wrapFun(isLast)
                }
                flexCount += l.flex
                rows.add(view)
                flexChildren.add(view)
                spendHeight += occupyHeight
                continue
            }
            layout.measureChild(view,measureWidthSpace,wMode,measureHeightSpace,hMode,outSize)

            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            if(spendHeight + occupyHeight > totalHeight){
                wrapFun(isLast)
            }
            rows.add(view)
            spendHeight += occupyHeight
            if(rowWidth < occupyWidth){
                rowWidth = occupyWidth
            }
        }
        wrapFun(true)

        spendHeight = 0f
        var spendWidth = 0f
        for (row in allRows){
            spendWidth += row.rowWidth
            if(spendHeight < row.rowHeight){
                spendHeight = row.rowHeight
            }
        }

        setOutSize(width,selfWidthMode,height,selfHeightMode,spendWidth+paddingWidth,spendHeight+paddingHeight,outSize)

        if(layout.flexWarp == FlexWarp.WARP_REVERSE && allRows.size > 1){
            val last = allRows.last()
            var offsetX = last.originX + last.rowWidth
            for ( row in allRows){
                offsetX -= row.rowWidth
                row.setOriginXTo(offsetX)
            }
        }
        val alignItems = layout.alignItems
        if(alignItems != AlignItems.FLEX_START){
            if(layout !is ScrollView && allRows.size > 1){
                var rowWidth = 0f
                for ( row in allRows){
                    rowWidth += row.rowWidth
                }
                val measuredWidth = outSize.width - paddingWidth
                var offset = 0f
                when(alignItems){
                    AlignItems.CENTER -> {
                        offset = (measuredWidth - rowWidth) / 2f
                    }
                    AlignItems.FLEX_END -> {
                        offset = measuredWidth - rowWidth
                    }
                }
                for ( row in allRows){
                    row.setOriginXBy(offset)
                }
            }
        }
        allRows.clear()
    }


    protected fun warp(layout: FlexLayout, children: ArrayList<View>, flexChildren:ArrayList<View>, flexCount: Float, rowWidth:Float, rowHeight: Float, spendWidth: Float, spendHeight: Float, offsetX: Float, offsetY: Float, isReverse:Boolean, isSingleRow:Boolean, outSize: Size):Row{
        val maxWith = max(rowWidth,calculateFlex(layout,flexChildren,flexCount,rowWidth,rowHeight,spendWidth,outSize))
        val rows = ArrayList(children)
        children.clear()

        //setOrigin
        var originX = offsetX
        var originY = offsetY
        if(isReverse){
            originY += rowHeight
        }
        var spendHeight = 0f
        var offsetX = originX
        var offsetY = originY
        for (view in rows){
            val l = view.layoutParams as FlexLayoutParams
            val frame = view.frame
            val margin = l.margin
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            var marginTop = 0f
            var marginBottom = 0f
            if (margin != null) {
                marginTop = margin.top
                marginBottom = margin.bottom
                occupyWidth += margin.right + margin.left
                occupyHeight += marginTop + marginBottom
            }
            spendHeight += occupyHeight
            if(isReverse){
                frame.x = offsetX + (margin?.left?:0f)
                frame.y = offsetY - occupyHeight + marginTop
                offsetY -= occupyHeight
            }else{
                frame.x = offsetX + (margin?.left?:0f)
                frame.y = offsetY + marginTop
                offsetY += occupyHeight
            }
        }
        val rSpendHeight = spendHeight
        val row = if(isSingleRow) Row(rows,maxWith,rSpendHeight,maxWith,rSpendHeight,originX,originY) else Row(rows,rowWidth,rSpendHeight,maxWith,rSpendHeight,originX,originY)
        adjustRow(layout,row,isReverse,outSize)
        return row
    }

    private fun measureFlexHeight(l:FlexLayoutParams):Float{
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK == LayoutParams.FLAG_HEIGHT_MASK){
            return l.height
        }
        if(l.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK == FlexLayoutParams.FLAG_MIN_HEIGHT_MASK){
            return l.minHeight
        }
        return 0f
    }
}