package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.max
import kotlin.math.min

open class RowCalculator : FlexCalculator() {

    override fun calculate(layout: FlexLayout, children: List<View>, width: Float, widthMode: MeasureMode, selfWidthMode: MeasureMode, height: Float, heightMode: MeasureMode, selfHeightMode: MeasureMode, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val isReverse = isReverse(layout)
        var totalWidth = width - paddingLeft - paddingRight
        var totalHeight = height - paddingTop - paddingBottom
        var spendWidth = 0f
        var maxItemHeight = 0f
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
            spendWidth += occupyWidth
            if(maxItemHeight < occupyHeight){
                maxItemHeight = occupyHeight
            }
        }
        maxItemHeight = max(maxItemHeight,calculateFlex(layout,flexChildren,flexCount,totalWidth,totalHeight,spendWidth,outSize))
        val flexOccupyWidth = outSize.width
        spendWidth += paddingLeft + paddingRight + flexOccupyWidth
        var spendHeight = maxItemHeight + paddingTop + paddingBottom
        setOutSize(width,selfWidthMode,height,selfHeightMode,spendWidth,spendHeight,outSize)

        val measuredWidth = outSize.width
        val measuredHeight = outSize.height

        //setOrigin
        var originX = paddingLeft
        var originY = paddingTop
        if(isReverse){
            originX = measuredWidth - paddingRight
        }
        spendWidth = 0f
        var offsetX = originX
        var offsetY = originY
        for (view in children){
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

        adjustRow(layout,children,measuredWidth-paddingLeft-paddingRight,measuredHeight-paddingTop-paddingBottom,spendWidth,maxItemHeight,isReverse,outSize)
        outSize.set(measuredWidth,measuredHeight)
    }


    fun calculateFlex(layout: FlexLayout,flexChildren: ArrayList<View>,flexCount:Float,width: Float,height: Float,spendWidth: Float,outSize: Size):Float{
        var maxItemHeight = 0f
        var allSpendWidth = 0f
        var remainWith = width - spendWidth
        if(remainWith < 0) remainWith = 0f
        val oneWidth = remainWith / flexCount
        for (view in flexChildren){
            val layoutParams = view.layoutParams as FlexLayoutParams
            val margin = layoutParams.margin

            var maxHeight = height
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK) {
                maxHeight = min(layoutParams.maxHeight, height)
            }
            var exactlyWidth = oneWidth * layoutParams.flex
            if(margin != null){
                exactlyWidth -= margin.right + margin.left
                maxHeight -= margin.top + margin.bottom
            }
            if(exactlyWidth < 0){
                exactlyWidth = 0f
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK && exactlyWidth > layoutParams.maxWidth){
                exactlyWidth = layoutParams.maxWidth
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK && exactlyWidth < layoutParams.minWidth){
                exactlyWidth = layoutParams.minWidth
            }
            layout.measureChild(view,exactlyWidth,MeasureMode.EXACTLY,maxHeight, MeasureMode.AT_MOST,outSize)
            var occupyWith = outSize.width
            var occupyHeight = outSize.height
            if(margin != null){
                occupyWith += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            allSpendWidth += occupyWith
            if(maxItemHeight < occupyHeight){
                maxItemHeight = occupyHeight
            }
        }
        outSize.set(allSpendWidth,maxItemHeight)
        return maxItemHeight
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
            var marginWidth = 0f
            var marginHeight = 0f
            if (margin != null) {
                marginWidth = margin.left + margin.right
                marginHeight = margin.top + margin.bottom
                occupyWidth += marginWidth
                occupyHeight += marginHeight
            }
            var remain = rowWidth - spendWidth
            when (justifyContent){
                JustifyContent.FLEX_START ->{
                    //do noting
                }
                JustifyContent.CENTER ->{
                    val offset = remain / 2f
                    if(operatorAdd){
                        frame.x += offset
                    }else{
                        frame.x -= offset
                    }
                }
                JustifyContent.FLEX_END ->{
                    if(operatorAdd) {
                        frame.x += remain
                    }else{
                        frame.x -= remain
                    }
                }
                JustifyContent.SPACE_BETWEEN ->{
                    if(remain > 0 && size > 1){
                        val offset = remain / (size - 1)*i
                        if(operatorAdd) {
                            frame.x += offset
                        }else{
                            frame.x -= offset
                        }
                    }
                    if(remain < 0){
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            frame.x += offset * i
                        }else{
                            frame.x -= offset * i
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
                            frame.x += temp
                        }else{
                            frame.x -= temp
                        }
                    }else{
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            frame.x += offset * i
                        }else{
                            frame.x -= offset * i
                        }
                    }
                }
            }

            when (alignItems){
                AlignItems.FLEX_START ->{
                    //do noting
                }
                AlignItems.CENTER ->{
                    frame.y += (rowHeight - occupyHeight) / 2f
                }
                AlignItems.FLEX_END ->{
                    frame.y += rowHeight - occupyHeight
                }
                AlignItems.STRETCH ->{
                    val h = frame.height
                    var newHeight = rowHeight - marginHeight
                    if(h != newHeight){
                        layout.reMeasureChild(view,frame.width,MeasureMode.EXACTLY,newHeight,MeasureMode.EXACTLY,outSize)
                    }
                    frame.height = newHeight
                }
            }
        }
    }

    override fun isReverse(layout: FlexLayout): Boolean {
        return layout.flexDirection == FlexDirection.ROW_REVERSE
    }

    override fun isFlex(layoutParams: FlexLayoutParams): Boolean {
        return layoutParams.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK && layoutParams.flag and FlexLayoutParams.FLAG_FLEX_MASK == FlexLayoutParams.FLAG_FLEX_MASK
    }

}