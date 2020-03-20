package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.Platform
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.min

abstract class FlexCalculator {

    fun calculate(layout:FlexLayout,direction: Int,children:List<View>,widthSpace:Float,heightSpace:Float,outSize:Size){
        var frameWidth = widthSpace
        var frameHeight = heightSpace
        val layoutParams = layout.layoutParams
        val isFlexLayout = layoutParams is FlexLayoutParams
        if(layoutParams.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK){
            if(isFlexLayout){
                val l = layoutParams as FlexLayoutParams
                if(l.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK){
                    if( frameWidth > l.maxWidth ){
                        frameWidth = l.maxWidth
                    }
                }
                if(l.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK && frameWidth < l.minWidth){
                    frameWidth = l.minWidth
                }
            }
        }else{
            frameWidth = layoutParams.width
        }
        if(layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK){
            if(isFlexLayout){
                val l = layoutParams as FlexLayoutParams
                if(l.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK){
                    if(frameHeight > l.maxHeight){
                        frameHeight = l.maxHeight
                    }
                }
                if(l.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK == FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && frameHeight < l.minHeight){
                    frameHeight = l.minHeight
                }
            }
        }else{
            frameHeight = layoutParams.height
        }

        var paddingLeft = 0f
        var paddingTop = 0f
        var paddingRight = 0f
        var paddingBottom = 0f
        val padding = layout.padding
        if(padding != null){
            paddingLeft = padding.left
            paddingRight = padding.right
            paddingTop = padding.top
            paddingBottom = padding.bottom
        }

        val absoluteChildren = ArrayList<View>()
        val commonChildren = ArrayList<View>()
        for (view in children){
            view.frame.reset()
            val l = view.layoutParams as FlexLayoutParams
            if(l.position == LayoutPosition.ABSOLUTE){
                absoluteChildren.add(view)
            }else{
                commonChildren.add(view)
            }
        }

        calculate(layout,direction,commonChildren, frameWidth, frameHeight, paddingLeft, paddingTop, paddingRight, paddingBottom, outSize)

        layoutAbsolute(absoluteChildren,outSize.width,outSize.height)
    }

    abstract fun calculate(layout:FlexLayout,direction:Int,children:List<View>,width:Float,height:Float,paddingLeft: Float,paddingTop: Float,paddingRight: Float,paddingBottom: Float,outSize:Size)


    /**
     * 计算flex空间
     * @param direction 方向
     * @param flexChildren 需要计算的flex子类
     * @param flexCount 总共分了几个flex
     * @param width 控件宽度
     * @param height 控件高度
     * @param spendWidth 已经被占用的宽度
     * @param spendHeight 已经被占用的高度
     */
    open fun calculateFlex(direction: Int,flexChildren: ArrayList<View>,flexCount:Float,width: Float,height: Float,spendWidth: Float,spendHeight: Float,outSize: Size){
        var remainWith = width - spendWidth
        if(remainWith < 0) remainWith = 0f
        var remainHeight = height - spendHeight
        if(remainHeight < 0) remainHeight = 0f
        val oneWidth = remainWith / flexCount
        val oneHeight = remainHeight /flexCount
        for (view in flexChildren){
            val frame = view.frame
            val layoutParams = view.layoutParams as FlexLayoutParams
            val margin = layoutParams.margin

            var maxWidth = width
            var maxHeight = height
            if(margin != null){
                maxWidth -= margin.right + margin.left
                maxHeight -= margin.top + margin.bottom
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK){
                maxWidth = min(layoutParams.maxWidth,width)
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK){
                maxHeight = min(layoutParams.maxHeight,height)
            }

            if(direction == FlexLayout.DIRECTION_RIGHT || direction == FlexLayout.DIRECTION_LEFT){
                var exactlyWidth = oneWidth * layoutParams.flex
                if(margin != null){
                    exactlyWidth -= margin.right + margin.left
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
                val temp = layoutParams.width
                layoutParams.width = exactlyWidth
                view.measure(maxWidth,maxHeight, outSize)
                frame.width = outSize.width
                frame.height = outSize.height
                layoutParams.width = temp
            }else{
                var exactlyHeight = oneHeight * layoutParams.flex
                if(margin != null){
                    exactlyHeight -= margin.top + margin.bottom
                }
                if(exactlyHeight < 0){
                    exactlyHeight = 0f
                }
                if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK && exactlyHeight > layoutParams.maxHeight){
                    exactlyHeight = layoutParams.maxHeight
                }
                if(layoutParams.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK ==  FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && exactlyHeight < layoutParams.minHeight){
                    exactlyHeight = layoutParams.minWidth
                }
                val temp = layoutParams.height
                layoutParams.height = exactlyHeight
                view.measure(maxWidth,maxHeight, outSize)
                frame.width = outSize.width
                frame.height = outSize.height
                layoutParams.height = temp
            }
        }
    }

    /**
     * 计算位置 justifyContent 和 alignItems
     * @param direction 方向
     * @param row 一行所有的子view
     * @param rowWidth 控件宽
     * @param rowHeight 控件高
     * @param spendWidth 子view占用的宽
     * @param spendHeight 子view占用的高
     */
    open fun adjustRow(direction: Int, row:List<View>, rowWidth: Float, rowHeight: Float, spendWidth:Float, spendHeight:Float, justifyContent: JustifyContent, alignItems: AlignItems){
        val size = row.size
        if(size == 0){
            return
        }
        var temp = 0f
        //DIRECTION_RIGHT 有2中状态 ROW && ROW(WARP_REVERSE)
        //DIRECTION_LEFT 有2中状态 ROW_REVERSE && ROW_REVERSE(WARP_REVERSE)
        //DIRECTION_DOWN 有2中状态 COLUMN && COLUMN(WARP_REVERSE)
        //DIRECTION_LEFT 有2中状态 COLUMN_REVERSE && COLUMN_REVERSE(WARP_REVERSE)
        val isRow = direction == FlexLayout.DIRECTION_RIGHT || direction == FlexLayout.DIRECTION_LEFT
        val operatorAdd = (isRow && direction == FlexLayout.DIRECTION_RIGHT) || (!isRow && direction == FlexLayout.DIRECTION_DOWN)
        for (i in 0 until size){
            val view = row[i]
            val frame = view.frame
            var remain = if(isRow) rowWidth - spendWidth else rowHeight - spendHeight
            when (justifyContent){
                JustifyContent.FLEX_START ->{
                    //do noting
                }
                JustifyContent.CENTER ->{
                    val offset = remain / 2f
                    if(operatorAdd){
                        if(isRow){
                            frame.x += offset
                        }else{
                            frame.y += offset
                        }
                    }else{
                        if(isRow){
                            frame.x -= offset
                        }else{
                            frame.y -= offset
                        }
                    }
                }
                JustifyContent.FLEX_END ->{
                    if(operatorAdd) {
                        if(isRow){
                            frame.x += remain
                        }else{
                            frame.y += remain
                        }
                    }else{
                        if(isRow) {
                            frame.x -= remain
                        }else{
                            frame.y -= remain
                        }
                    }
                }
                JustifyContent.SPACE_BETWEEN ->{
                    if(remain > 0 && size > 1){
                        val offset = remain / (size - 1)*i
                        if(operatorAdd) {
                            if(isRow) {
                                frame.x += offset
                            }else{
                                frame.y += offset
                            }
                        }else{
                            if(isRow) {
                                frame.x -= offset
                            }else{
                                frame.y -= offset
                            }
                        }
                    }
                    if(remain < 0){
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            if(isRow) {
                                frame.x += offset * i
                            }else{
                                frame.y += offset * i
                            }
                        }else{
                            if(isRow) {
                                frame.x -= offset * i
                            }else{
                                frame.y -= offset * i
                            }
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
                            if(isRow) {
                                frame.x += temp
                            }else{
                                frame.y += temp
                            }
                        }else{
                            if(isRow) {
                                frame.x -= temp
                            }else{
                                frame.y -= temp
                            }
                        }
                    }else{
                        var offset = remain / (size - 1)
                        if(operatorAdd) {
                            if(isRow) {
                                frame.x += offset * i
                            }else{
                                frame.y = offset * i
                            }
                        }else{
                            if(isRow) {
                                frame.x -= offset * i
                            }else{
                                frame.y -= offset * i
                            }
                        }
                    }
                }
            }

            when (alignItems){
                AlignItems.FLEX_START ->{
                    //do noting
                }
                AlignItems.CENTER ->{
                    if(isRow) {
                        frame.y += (rowHeight - frame.height) / 2f
                    }else{
                        frame.x += (rowWidth - frame.width) / 2f
                    }
                }
                AlignItems.FLEX_END ->{
                    if(isRow) {
                        frame.y = rowHeight - frame.height
                    }else{
                        frame.x = rowWidth - frame.width
                    }
                }
                AlignItems.STRETCH ->{
                    if(isRow) {
                        frame.height = rowHeight
                    }else{
                        frame.width = rowWidth
                    }
                }
            }

        }
    }


    fun layoutAbsolute(absoluteChildren:List<View>,frameWidth:Float,frameHeight:Float){
        for (view in absoluteChildren){
            val outSize = Size()
            val layoutParams = view.layoutParams as FlexLayoutParams
            val frame = view.frame
            var l = 0f
            var t = 0f
            var r = 0f
            var b = 0f
            var w = 0f
            var h = 0f
            var isSetLeft = false
            var isSetTop = false
            var isSetRight = false
            var isSetBottom = false
            var isSetWidth = false
            var isSetHeight = false
            if(layoutParams.flag and FlexLayoutParams.FLAG_LEFT_MASK == FlexLayoutParams.FLAG_LEFT_MASK){
                l = layoutParams.left
                isSetLeft = true
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_TOP_MASK == FlexLayoutParams.FLAG_TOP_MASK){
                t = layoutParams.top
                isSetTop = true
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_RIGHT_MASK == FlexLayoutParams.FLAG_RIGHT_MASK){
                r = layoutParams.right
                isSetRight = true
            }
            if(layoutParams.flag and FlexLayoutParams.FLAG_BOTTOM_MASK == FlexLayoutParams.FLAG_BOTTOM_MASK){
                b = layoutParams.bottom
                isSetBottom = true
            }
            if(layoutParams.flag and LayoutParams.FLAG_WIDTH_MASK == LayoutParams.FLAG_WIDTH_MASK){
                w = layoutParams.width
                isSetWidth = true
            }
            if(layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK == LayoutParams.FLAG_HEIGHT_MASK){
                h = layoutParams.height
                isSetHeight = true
            }
            var isPosition = false
            var errorMessage:String? = null
            if(isSetLeft && isSetRight && isSetTop && isSetBottom){
                isPosition = true
            }else{
                do {
                    var message = "don't calculate width "
                    if( isSetLeft || isSetRight){
                        if(!(isSetLeft && isSetRight)){
                            if(!isSetWidth){
                                errorMessage = message
                                break
                            }else{
                                if(isSetLeft){
                                    r = frameWidth - l - w
                                }else{
                                    l = frameWidth - r - w
                                }
                            }
                        }
                    }else{
                        errorMessage = message
                        break
                    }
                    message = "don't calculate height "
                    if( isSetTop || isSetBottom){
                        if(!(isSetTop && isSetBottom)){
                            if(!isSetHeight){
                                errorMessage = message
                                break
                            }else{
                                if(isSetTop){
                                    b = frameHeight - t - h
                                }else{
                                    t = frameHeight - b - h
                                }
                            }
                        }
                    }else{
                        errorMessage = message
                    }
                    isPosition = true
                }while (false)
            }
            if(isPosition){
                frame.x = l
                frame.y = t
                val width = frameWidth - l - r
                val height = frameHeight - t - b
                val tempW = layoutParams.width
                val tempH = layoutParams.height
                layoutParams.width = width
                layoutParams.height = height
                view.measure(width,height,outSize)
                layoutParams.width = tempW
                layoutParams.height = tempH
                frame.width = width
                frame.height = height
            }else{
                if(Platform.debug && errorMessage != null){
                    println("calculate absolute position error:$errorMessage")
                }
            }
        }
    }

    abstract fun isReverse(layout: FlexLayout):Boolean

    abstract fun isFlex(layoutParams: FlexLayoutParams):Boolean

    fun isWrapReverse(layout: FlexLayout):Boolean{
        return layout.flexWarp == FlexWarp.WARP_REVERSE
    }

}