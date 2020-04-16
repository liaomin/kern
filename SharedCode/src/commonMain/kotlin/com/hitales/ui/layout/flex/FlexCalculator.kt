package com.hitales.ui.layout.flex

import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.Platform
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.min

abstract class FlexCalculator {

    data class Row(val children: List<View>, val rowWidth:Float,val rowHeight:Float,var spendWidth: Float, var spendHeight: Float,val originX:Float,val originY:Float){

        fun setOriginXTo(originX: Float){
            val offset = originX - this.originX
            children.forEach {
                it.frame.x += offset
            }
        }

        fun setOriginXBy(offset: Float){
            children.forEach {
                it.frame.x += offset
            }
        }

        fun setOriginYTo(originY: Float){
            val offset = originY - this.originY
            children.forEach {
                it.frame.y += offset
            }
        }

        fun setOriginYBy(offset: Float){
            children.forEach {
                it.frame.y += offset
            }
        }
    }

    fun calculate(layout:FlexLayout,children:List<View>,widthSpace:Float,widthMode: MeasureMode,heightSpace:Float,heightMode: MeasureMode,outSize:Size){
        var frameWidth = widthSpace
        var frameHeight = heightSpace
        var wMode = widthMode
        var hMode = heightMode
        val layoutParams = layout.layoutParams!!
        val isFlexLayout = layoutParams is FlexLayoutParams
        if(layoutParams.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK){
            if(isFlexLayout){
                val l = layoutParams as FlexLayoutParams
                if(l.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK){
                    if( frameWidth > l.maxWidth ){
                        frameWidth = l.maxWidth
                        wMode = MeasureMode.AT_MOST
                    }
                }
                if(l.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK && frameWidth < l.minWidth){
                    frameWidth = l.minWidth
                    wMode = MeasureMode.AT_MOST
                }
            }
        }else{
            frameWidth = layoutParams.width
            wMode = MeasureMode.AT_MOST
        }
        if(layoutParams.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK){
            if(isFlexLayout){
                val l = layoutParams as FlexLayoutParams
                if(l.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK){
                    if(frameHeight > l.maxHeight){
                        frameHeight = l.maxHeight
                        hMode = MeasureMode.AT_MOST
                    }
                }
                if(l.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK == FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && frameHeight < l.minHeight){
                    frameHeight = l.minHeight
                    hMode = MeasureMode.AT_MOST
                }
            }
        }else{
            frameHeight = layoutParams.height
            hMode = MeasureMode.AT_MOST
        }

        var paddingLeft = layout.getPaddingLeft()
        var paddingTop = layout.getPaddingTop()
        var paddingRight = layout.getPaddingRight()
        var paddingBottom = layout.getPaddingBottom()

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

        calculate(layout,commonChildren, frameWidth,wMode,widthMode,frameHeight,hMode,heightMode,paddingLeft, paddingTop, paddingRight, paddingBottom, outSize)

        layoutAbsolute(absoluteChildren,outSize.width,outSize.height,paddingLeft,paddingTop,paddingRight,paddingBottom,layout)
    }


    fun setOutSize(width: Float,widthMode: MeasureMode,height: Float,heightMode: MeasureMode,spendWidth: Float,spendHeight: Float,outSize: Size){
        var w = spendWidth
        var h = spendHeight
        if(widthMode == MeasureMode.EXACTLY){
            w = width
        }else if(widthMode == MeasureMode.AT_MOST){
            w = min(width,spendWidth)
        }
        if(heightMode == MeasureMode.EXACTLY){
            h = height
        }else if(heightMode == MeasureMode.AT_MOST){
            h = min(height,spendHeight)
        }
        outSize.set(w,h)
    }

    /**
     * 计算大小，flex
     * 换行
     * 设置位置
     */
    abstract fun calculate(layout:FlexLayout,children:List<View>,width:Float,widthMode: MeasureMode,selfWidthMode:MeasureMode,height:Float,heightMode: MeasureMode,selfHeightMode:MeasureMode,paddingLeft: Float, paddingTop: Float, paddingRight: Float, paddingBottom: Float,outSize:Size)

//    /**
//     * 计算flex空间
//     * @param direction 方向
//     * @param flexChildren 需要计算的flex子类
//     * @param flexCount 总共分了几个flex
//     * @param width 控件宽度
//     * @param height 控件高度
//     * @param spendWidth 已经被占用的宽度
//     * @param spendHeight 已经被占用的高度
//     */
//    open fun calculateFlex(direction: Int,flexChildren: ArrayList<View>,flexCount:Float,width: Float,height: Float,spendWidth: Float,spendHeight: Float,outSize: Size){
//        var remainWith = width - spendWidth
//        if(remainWith < 0) remainWith = 0f
//        var remainHeight = height - spendHeight
//        if(remainHeight < 0) remainHeight = 0f
//        val oneWidth = remainWith / flexCount
//        val oneHeight = remainHeight /flexCount
//        for (view in flexChildren){
//            val frame = view.frame
//            val layoutParams = view.layoutParams as FlexLayoutParams
//            val margin = layoutParams.margin
//
//            var maxWidth = width
//            var maxHeight = height
//            if(margin != null){
//                maxWidth -= margin.right + margin.left
//                maxHeight -= margin.top + margin.bottom
//            }
//            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK){
//                maxWidth = min(layoutParams.maxWidth,width)
//            }
//            if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK){
//                maxHeight = min(layoutParams.maxHeight,height)
//            }
//
//            if(direction == FlexLayout.DIRECTION_RIGHT || direction == FlexLayout.DIRECTION_LEFT){
//                var exactlyWidth = oneWidth * layoutParams.flex
//                if(margin != null){
//                    exactlyWidth -= margin.right + margin.left
//                }
//                if(exactlyWidth < 0){
//                    exactlyWidth = 0f
//                }
//                if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK && exactlyWidth > layoutParams.maxWidth){
//                    exactlyWidth = layoutParams.maxWidth
//                }
//                if(layoutParams.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK && exactlyWidth < layoutParams.minWidth){
//                    exactlyWidth = layoutParams.minWidth
//                }
//                view.measure(exactlyWidth,MeasureMode.EXACTLY,maxHeight, MeasureMode.AT_MOST,outSize)
//                frame.width = outSize.width
//                frame.height = outSize.height
//            }else{
//                var exactlyHeight = oneHeight * layoutParams.flex
//                if(margin != null){
//                    exactlyHeight -= margin.top + margin.bottom
//                }
//                if(exactlyHeight < 0){
//                    exactlyHeight = 0f
//                }
//                if(layoutParams.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK && exactlyHeight > layoutParams.maxHeight){
//                    exactlyHeight = layoutParams.maxHeight
//                }
//                if(layoutParams.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK ==  FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && exactlyHeight < layoutParams.minHeight){
//                    exactlyHeight = layoutParams.minWidth
//                }
//                view.measure(maxWidth,MeasureMode.AT_MOST,exactlyHeight,MeasureMode.EXACTLY, outSize)
//                frame.width = outSize.width
//                frame.height = outSize.height
//            }
//        }
//    }
//
//    /**
//     * 计算位置 justifyContent 和 alignItems
//     * @param direction 方向
//     * @param row 一行所有的子view
//     * @param rowWidth 控件宽
//     * @param rowHeight 控件高
//     * @param spendWidth 子view占用的宽
//     * @param spendHeight 子view占用的高
//     */
//    open fun adjustRow(direction: Int, row:List<View>, rowWidth: Float, rowHeight: Float, spendWidth:Float, spendHeight:Float, justifyContent: JustifyContent, alignItems: AlignItems){
//        val size = row.size
//        if(size == 0){
//            return
//        }
//        var temp = 0f
//        //DIRECTION_RIGHT 有2中状态 ROW && ROW(WARP_REVERSE)
//        //DIRECTION_LEFT 有2中状态 ROW_REVERSE && ROW_REVERSE(WARP_REVERSE)
//        //DIRECTION_DOWN 有2中状态 COLUMN && COLUMN(WARP_REVERSE)
//        //DIRECTION_LEFT 有2中状态 COLUMN_REVERSE && COLUMN_REVERSE(WARP_REVERSE)
//        val isRow = direction == FlexLayout.DIRECTION_RIGHT || direction == FlexLayout.DIRECTION_LEFT
//        val operatorAdd = (isRow && direction == FlexLayout.DIRECTION_RIGHT) || (!isRow && direction == FlexLayout.DIRECTION_DOWN)
//        for (i in 0 until size){
//            val view = row[i]
//            val frame = view.frame
//            var occupyWidth = frame.width
//            var occupyHeight = frame.height
//            val margin = (view.layoutParams as FlexLayoutParams).margin
//            if (margin != null) {
//                occupyWidth += margin.left + margin.right
//                occupyHeight += margin.top + margin.bottom
//            }
//            var remain = if(isRow) rowWidth - spendWidth else rowHeight - spendHeight
//            when (justifyContent){
//                JustifyContent.FLEX_START ->{
//                    //do noting
//                }
//                JustifyContent.CENTER ->{
//                    val offset = remain / 2f
//                    if(operatorAdd){
//                        if(isRow){
//                            frame.x += offset
//                        }else{
//                            frame.y += offset
//                        }
//                    }else{
//                        if(isRow){
//                            frame.x -= offset
//                        }else{
//                            frame.y -= offset
//                        }
//                    }
//                }
//                JustifyContent.FLEX_END ->{
//                    if(operatorAdd) {
//                        if(isRow){
//                            frame.x += remain
//                        }else{
//                            frame.y += remain
//                        }
//                    }else{
//                        if(isRow) {
//                            frame.x -= remain
//                        }else{
//                            frame.y -= remain
//                        }
//                    }
//                }
//                JustifyContent.SPACE_BETWEEN ->{
//                    if(remain > 0 && size > 1){
//                        val offset = remain / (size - 1)*i
//                        if(operatorAdd) {
//                            if(isRow) {
//                                frame.x += offset
//                            }else{
//                                frame.y += offset
//                            }
//                        }else{
//                            if(isRow) {
//                                frame.x -= offset
//                            }else{
//                                frame.y -= offset
//                            }
//                        }
//                    }
//                    if(remain < 0){
//                        var offset = remain / (size - 1)
//                        if(operatorAdd) {
//                            if(isRow) {
//                                frame.x += offset * i
//                            }else{
//                                frame.y += offset * i
//                            }
//                        }else{
//                            if(isRow) {
//                                frame.x -= offset * i
//                            }else{
//                                frame.y -= offset * i
//                            }
//                        }
//                    }
//
//                }
//                JustifyContent.SPACE_AROUND ->{
//                    if(remain > 0){
//                        var offset = remain / size.toFloat()
//                        if ( i == 0){
//                            offset /= 2f
//                        }
//                        temp += offset
//                        if(operatorAdd) {
//                            if(isRow) {
//                                frame.x += temp
//                            }else{
//                                frame.y += temp
//                            }
//                        }else{
//                            if(isRow) {
//                                frame.x -= temp
//                            }else{
//                                frame.y -= temp
//                            }
//                        }
//                    }else{
//                        var offset = remain / (size - 1)
//                        if(operatorAdd) {
//                            if(isRow) {
//                                frame.x += offset * i
//                            }else{
//                                frame.y = offset * i
//                            }
//                        }else{
//                            if(isRow) {
//                                frame.x -= offset * i
//                            }else{
//                                frame.y -= offset * i
//                            }
//                        }
//                    }
//                }
//            }
//
//            when (alignItems){
//                AlignItems.FLEX_START ->{
//                    //do noting
//                }
//                AlignItems.CENTER ->{
//                    if(isRow) {
//                        frame.y += (rowHeight - occupyHeight) / 2f
//                    }else{
//                        frame.x += (rowWidth - occupyWidth) / 2f
//                    }
//                }
//                AlignItems.FLEX_END ->{
//                    if(isRow) {
//                        frame.y += rowHeight - occupyHeight
//                    }else{
//                        frame.x += rowWidth - frame.width
//                    }
//                }
//                AlignItems.STRETCH ->{
//                    if(isRow) {
//                        frame.height = rowHeight
//                    }else{
//                        frame.width = rowWidth
//                    }
//                }
//            }
//        }
//    }


    fun layoutAbsolute(absoluteChildren:List<View>,frameWidth:Float,frameHeight:Float,paddingLeft: Float,paddingTop: Float,paddingRight: Float,paddingBottom: Float,layout: FlexLayout){
        var innerPaddingLeft = 0f
        var innerPaddingTop = 0f
        var innerPaddingRight = 0f
        var innerPaddingBottom = 0f
        var padding = layout.padding
        if(padding != null){
            innerPaddingLeft = paddingLeft - padding.left
            innerPaddingTop = paddingTop - padding.top
            innerPaddingRight = paddingRight - padding.right
            innerPaddingBottom = paddingBottom - padding.bottom
        }
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
                frame.x = l + innerPaddingLeft
                frame.y = t + innerPaddingTop
                val width = frameWidth - l - r - innerPaddingLeft - innerPaddingRight
                val height = frameHeight - t - b - innerPaddingBottom - innerPaddingTop
                val tempW = layoutParams.width
                val tempH = layoutParams.height
                layoutParams.width = width
                layoutParams.height = height
                view.measure(width,MeasureMode.EXACTLY,height,MeasureMode.EXACTLY,outSize)
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