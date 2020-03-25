package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.View
import com.hitales.utils.Size

open class RowWrapCalculator : RowCalculator() {

    private val allRows = ArrayList<Row>()

    override fun calculate(layout: FlexLayout, direction: Int, children: List<View>, width: Float, widthMode: MeasureMode, height: Float, heightMode: MeasureMode, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        var row = ArrayList<View>()
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val isReverse = isReverse(layout)
        val isWrapReverse = isWrapReverse(layout)
        val totalWidth = width - paddingLeft - paddingRight
        val totalHeight = height - paddingTop - paddingBottom
        var spendWidth = 0f
        var spendHeight = 0f
        for (view in children){
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
                flexCount += l.flex

                val occupyWidth = measureFlexWidth(l)
                if(spendWidth + occupyWidth > totalWidth){
                    wrap(direction, row, flexChildren, flexCount, width, height, spendWidth, spendHeight, outSize)
                    flexChildren.add(view)
                    row.add(view)
                    spendHeight = 0f
                    flexChildren.clear()
                    flexCount = 0f
                }
                row.add(view)
                spendWidth += occupyWidth
                continue
            }
            layout.measureChild(view,measureWidthSpace,widthMode,measureHeightSpace,heightMode,outSize)
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            if(spendWidth + occupyWidth > totalWidth){
                wrap(direction, row, flexChildren, flexCount, width, height, spendWidth, spendHeight, outSize)
                spendWidth = 0f
                spendHeight = 0f
                flexChildren.clear()
                flexCount = 0f
            }
            row.add(view)
            spendWidth += occupyWidth
        }
        wrap(direction, row, flexChildren, flexCount, width, height, spendWidth, spendHeight, outSize)

        var originX = paddingLeft
        var originY = paddingTop
        if(isReverse){
            originX = width - paddingRight
        }
        spendWidth = 0f
        spendHeight = 0f

        var offsetX = originX
        var offsetY = originY
        var maxWidth = 0f
        var maxHeight = 0f
        var usedHeight = 0f
        for (row in allRows){
            val items = row.children
            for (view in items){
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
                if(spendHeight < occupyHeight){
                    spendHeight = occupyHeight
                }
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
            if(maxWidth < spendWidth){
                maxWidth = spendWidth
            }
            if(maxHeight < spendHeight){
                maxHeight = spendHeight
            }
            usedHeight += spendHeight
            adjustRow(direction,items,totalWidth,row.spendHeight,spendWidth,spendHeight,layout.justifyContent,layout.alignItems)
            offsetX = originX
            offsetY += spendHeight
            spendWidth = 0f
            spendHeight = 0f
        }

        outSize.width = maxWidth
        outSize.height = usedHeight

    }

    override fun adjustChildren(layout: FlexLayout, direction: Int, children: List<View>, width: Float, widthMode: MeasureMode, height: Float, heightMode: MeasureMode, spendWidth: Float, spendHeight: Float, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        val totalWidth = width - paddingLeft - paddingRight
        val totalHeight = height - paddingTop - paddingBottom
        var measureWidth = totalWidth
        var measureHeight = totalHeight
        if(widthMode == MeasureMode.UNSPECIFIED){
            measureWidth = spendWidth
        }
        if(heightMode == MeasureMode.UNSPECIFIED){
            measureHeight = spendHeight
        }
        var layoutWidth = width
        var layoutHeight= height
        if(widthMode != MeasureMode.EXACTLY){
            layoutWidth = spendWidth + paddingLeft + paddingRight
        }
        if(widthMode == MeasureMode.AT_MOST){
            if(layoutWidth > width){
                layoutWidth = width
            }
        }
        if(heightMode != MeasureMode.EXACTLY){
            layoutHeight = spendHeight + paddingTop + paddingBottom
        }
        if(heightMode == MeasureMode.AT_MOST){
            if(layoutHeight > height){
                layoutHeight = height
            }
        }
        if(layoutHeight != measureHeight){

        }
        outSize.width = layoutWidth
        outSize.height = layoutHeight
        allRows.clear()
    }

    private fun wrap(direction: Int,row:ArrayList<View>,flexChildren: ArrayList<View>,flexCount:Float,width: Float,height: Float,spendWidth: Float,spendHeight: Float,outSize: Size){
        if(flexChildren.size > 0){
            calculateFlex(direction, flexChildren, flexCount, width, height, spendWidth, spendHeight, outSize)
        }
        var width = 0f
        var maxHeight = 0f
        for (view in row){
            val frame = view.frame
            val margin = (view.layoutParams as FlexLayoutParams).margin
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            width += occupyWidth
            if(maxHeight < occupyHeight){
                maxHeight = occupyHeight
            }
        }
        allRows.add(Row(ArrayList(row),width,maxHeight))
        row.clear()
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