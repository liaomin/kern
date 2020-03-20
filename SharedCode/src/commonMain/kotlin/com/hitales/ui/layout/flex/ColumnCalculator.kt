package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.min

open class ColumnCalculator : FlexCalculator() {

    override fun calculate(layout: FlexLayout,direction:Int, children: List<View>, width: Float, height: Float, paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float, outSize: Size) {
        val flexChildren = ArrayList<View>(0)
        var flexCount = 0f
        val commonChildren = ArrayList<View>(0)
        val isReverse = isReverse(layout)
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
                flexChildren.add(view)
                continue
            }
            commonChildren.add(view)
            layout.measureChild(view,measureWidthSpace,measureHeightSpace,outSize)

            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            spendWidth += occupyWidth
        }

        calculateFlex(direction,flexChildren,flexCount,totalWidth,totalHeight,spendWidth,spendHeight,outSize)

        var originX = paddingLeft
        var originY = paddingTop
        if(isReverse){
            originY = height - paddingTop
        }

        spendWidth = 0f
        spendHeight = 0f
        var offsetX = originX
        var offsetY = originY
        var maxWidth  = 0f
        for (view in children){
            val l = view.layoutParams as FlexLayoutParams
            val frame = view.frame
            val margin = l.margin
            var occupyWidth = frame.width
            var occupyHeight = frame.height
            if (margin != null) {
                occupyWidth += margin.left + margin.right
                occupyHeight += margin.top + margin.bottom
            }
            if(maxWidth < occupyWidth){
                maxWidth = occupyWidth
            }
            spendWidth += occupyWidth
            spendHeight += occupyHeight
            if(isReverse){
                frame.x = offsetX - occupyWidth
                frame.y = offsetY + (margin?.top?:0f)
                offsetY -= occupyHeight
            }else{
                frame.x = offsetX + (margin?.left?:0f)
                frame.y = offsetY + (margin?.top?:0f)
                offsetY += occupyHeight
            }
        }

        val l = layout.layoutParams
        var measuredWidth = width
        var measuredHeight = height
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK){
            measuredWidth = min(width,maxWidth)
        }
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK){
            measuredHeight = min(height,spendHeight)
        }

        adjustRow(direction,children,measuredWidth-paddingLeft-paddingRight,measuredHeight-paddingTop-paddingBottom,spendWidth,spendHeight,layout.justifyContent,layout.alignItems)

        outSize.width = measuredWidth
        outSize.height = measuredHeight

    }

    override fun isReverse(layout: FlexLayout): Boolean {
        return layout.flexDirection == FlexDirection.COLUMN_REVERSE
    }

    override fun isFlex(layoutParams: FlexLayoutParams): Boolean {
        return layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK && layoutParams.flag and FlexLayoutParams.FLAG_FLEX_MASK == FlexLayoutParams.FLAG_FLEX_MASK
    }

}