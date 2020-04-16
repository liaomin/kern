package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.max
import kotlin.math.min

open class ColumnCalculator : FlexCalculator() {

    override fun calculate(layout: FlexLayout, children: List<View>, width: Float, widthMode: MeasureMode, selfWidthMode: MeasureMode, height: Float, heightMode: MeasureMode, selfHeightMode: MeasureMode, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val isReverse = isReverse(layout)
        var totalWidth = width - paddingLeft - paddingRight
        var totalHeight = height - paddingTop - paddingBottom
        var spendHeight = 0f
        var maxItemWidth = 0f
        var wMode = widthMode
        var hMode = heightMode
        if(widthMode == MeasureMode.EXACTLY){
            wMode = MeasureMode.AT_MOST
        }
        if(heightMode == MeasureMode.EXACTLY){
            hMode = MeasureMode.AT_MOST
        }
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
                flexChildren.add(view)
                continue
            }
            layout.measureChild(view,measureWidthSpace,wMode,measureHeightSpace,hMode,outSize)

            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            spendHeight += occupyHeight
            if(maxItemWidth < occupyWidth){
                maxItemWidth = occupyWidth
            }
        }
        maxItemWidth = max(maxItemWidth,calculateFlex(layout,flexChildren,flexCount,totalWidth,totalHeight,spendHeight,outSize))
        val flexOccupyHeight = outSize.height
        spendHeight += paddingTop + paddingBottom + flexOccupyHeight
        var spendWidth = maxItemWidth + paddingLeft + paddingRight
        setOutSize(width,selfWidthMode,height,selfHeightMode,spendWidth,spendHeight,outSize)

        val measuredWidth = outSize.width
        val measuredHeight = outSize.height


        //setOrigin
        var originX = paddingLeft
        var originY = paddingTop
        if(isReverse){
            originY = measuredHeight - paddingBottom
        }
        spendHeight = 0f
        var offsetX = originX
        var offsetY = originY
        for (view in children){
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
                occupyWidth += margin.left + margin.right
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

        adjustRow(layout,children,measuredWidth-paddingLeft-paddingRight,measuredHeight-paddingTop-paddingBottom,maxItemWidth,spendHeight,isReverse,outSize)
        outSize.set(measuredWidth,measuredHeight)
    }


    fun calculateFlex(layout: FlexLayout,flexChildren: ArrayList<View>,flexCount:Float,width: Float,height: Float,spendHeight: Float,outSize: Size):Float{
        var maxItemWidth = 0f
        var allSpendHeight = 0f
        var remainHeight = width - spendHeight
        if(remainHeight < 0) remainHeight = 0f
        val oneHeight = remainHeight / flexCount
        for (view in flexChildren){
            val layoutParams = view.layoutParams as FlexLayoutParams
            val margin = layoutParams.margin

            var maxWidth = width
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK) {
                maxWidth = min(layoutParams.maxWidth, height)
            }
            var exactlyHeight = oneHeight * layoutParams.flex
            if(margin != null){
                exactlyHeight -= margin.top + margin.bottom
                maxWidth -= margin.left + margin.right
            }
            if(exactlyHeight < 0){
                exactlyHeight = 0f
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK && exactlyHeight > layoutParams.maxHeight){
                exactlyHeight = layoutParams.maxHeight
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK == FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && exactlyHeight < layoutParams.minHeight){
                exactlyHeight = layoutParams.minHeight
            }
            layout.measureChild(view,maxWidth,MeasureMode.AT_MOST,exactlyHeight, MeasureMode.EXACTLY,outSize)
            var occupyWith = outSize.width
            var occupyHeight = outSize.height
            if(margin != null){
                occupyWith += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            allSpendHeight += occupyHeight
            if(maxItemWidth < occupyWith){
                maxItemWidth = occupyWith
            }
        }
        outSize.set(maxItemWidth,allSpendHeight)
        return maxItemWidth
    }

    protected fun adjustRow(layout: FlexLayout,row: Row,isReverse:Boolean,outSize: Size) {
        adjustRow(layout,row.children,row.rowWidth,row.rowHeight,row.spendWidth,row.spendHeight,isReverse,outSize)
    }

    fun adjustRow(layout: FlexLayout,row: List<View>, rowWidth: Float, rowHeight: Float, spendWidth: Float, spendHeight: Float,isReverse:Boolean,outSize: Size) {
        val justifyContent = layout.justifyContent
        val alignItems = layout.alignItems
        val size = row.size
        if(size == 0){
            return
        }
        var temp = 0f
        val operatorAdd = !isReverse
        for (i in 0 until size){
            val view = row[i]
            val frame = view.frame
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            val margin = (view.layoutParams as FlexLayoutParams).margin
            var marginWidth  = 0f
            if (margin != null) {
                marginWidth = margin.left + margin.right
                occupyWidth += marginWidth
                occupyHeight += margin.top + margin.bottom
            }
            var remain =  rowHeight - spendHeight
            when (justifyContent){
                JustifyContent.FLEX_START ->{
                    //do noting
                }
                JustifyContent.CENTER ->{
                    val offset = remain / 2f
                    if(operatorAdd){
                        frame.y += offset
                    }else{
                        frame.y -= offset
                    }
                }
                JustifyContent.FLEX_END ->{
                    if(operatorAdd) {
                        frame.y += remain
                    }else{
                        frame.y -= remain
                    }
                }
                JustifyContent.SPACE_BETWEEN ->{
                    if(remain > 0 && size > 1){
                        val offset = remain / (size - 1)*i
                        if(operatorAdd) {
                            frame.y += offset
                        }else{
                            frame.y -= offset
                        }
                    }
                    if(remain < 0){
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            frame.y += offset * i
                        }else{
                            frame.y -= offset * i
                        }
                    }

                }
                JustifyContent.SPACE_AROUND ->{
                    if(remain > 0){
                        var offset = remain / size.toFloat()
                        if ( i == 0){
                            offset /= 2f
                        }
                        temp += offset
                        if(operatorAdd) {
                            frame.y += temp
                        }else{
                            frame.y -= temp
                        }
                    }else{
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            frame.y += offset * i
                        }else{
                            frame.y -= offset * i
                        }
                    }
                }
            }

            when (alignItems){
                AlignItems.FLEX_START ->{
                    //do noting
                }
                AlignItems.CENTER ->{
                    frame.x += (rowWidth - occupyWidth) / 2f
                }
                AlignItems.FLEX_END ->{
                    frame.x += rowWidth - frame.width
                }
                AlignItems.STRETCH ->{
                    val w = frame.width
                    var newWidth = rowWidth - marginWidth
                    if(w != newWidth){
                        layout.reMeasureChild(view,newWidth,MeasureMode.EXACTLY,frame.height,MeasureMode.EXACTLY,outSize)
                    }
                    frame.width = newWidth
                }
            }
        }
    }

    override fun isReverse(layout: FlexLayout): Boolean {
        return layout.flexDirection == FlexDirection.COLUMN_REVERSE
    }

    override fun isFlex(layoutParams: FlexLayoutParams): Boolean {
        return layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK && layoutParams.flag and FlexLayoutParams.FLAG_FLEX_MASK == FlexLayoutParams.FLAG_FLEX_MASK
    }

}