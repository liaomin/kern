package com.hitales.ui.layout.flex

import com.hitales.ui.CustomLayout
import com.hitales.ui.LayoutParams
import com.hitales.ui.View
import com.hitales.utils.Size
import kotlin.math.min


enum class FlexDirection(val value:Int) {
    ROW(0),             //原点 左上 主轴 向右
    COLUMN(1),          //原点 左上 向下
    ROW_REVERSE(2),     //原点 右上 主轴 向左
    COLUMN_REVERSE(3),  //原点 左下 主轴 向上
}

enum class FlexWarp(val value:Int) {
    NO_WARP(0),
    WARP(1),
    WARP_REVERSE(2),
}

enum class JustifyContent(val value:Int) {
    FLEX_START(0),
    FLEX_END(1),
    CENTER(2),
    SPACE_BETWEEN(3),
    SPACE_AROUND(4),
}

enum class AlignItems(val value:Int) {
    FLEX_START(0),
    FLEX_END(1),
    CENTER(2),
    STRETCH(3),
//    BASELINE(4),
}

/**
 * https://www.runoob.com/w3cnote/flex-grammar.html
 * https://www.runoob.com/try/try.php?filename=trycss3_js_flex-wrap
 */
open class FlexLayout : CustomLayout<FlexLayoutParams> {

    companion object{
        val tempSize: Size by lazy { Size() }
        private const val DIRECTION_RIGHT = 0
        private const val DIRECTION_LEFT = 1
        private const val DIRECTION_DOWN = 2
        private const val DIRECTION_UP = 3
    }

    private data class Row(val width:Float,val height: Float,val items: ArrayList<View>)

    var flexDirection: FlexDirection = FlexDirection.COLUMN
        set(value) {
            if(field != value){
                field = value
                needLayout()
            }
        }

    var flexWarp: FlexWarp = FlexWarp.NO_WARP
        set(value) {
            if(field != value){
                field = value
                needLayout()
            }
        }

    var justifyContent: JustifyContent = JustifyContent.FLEX_START
        set(value) {
            if(field != value){
                field = value
                needLayout()
            }
        }

    var alignItems: AlignItems = AlignItems.FLEX_START
        set(value) {
            if(field != value){
                field = value
                needLayout()
            }
        }

    constructor(layoutParams: LayoutParams = LayoutParams()):super(layoutParams)


    override fun measure(widthSpace: Float, heightSpace: Float, outSize: Size?) {
        var width = widthSpace
        var height = heightSpace
        var paddingLeft = 0f
        var paddingTop = 0f
        var paddingRight = 0f
        var paddingBottom = 0f
        val padding = this.padding
        if(padding != null){
            paddingLeft = padding.left
            paddingRight = padding.right
            paddingTop = padding.top
            paddingBottom = padding.bottom
            width -= paddingLeft + paddingRight
            height -= paddingTop + paddingBottom
        }
        val widthStart = paddingLeft
        val widthEnd = paddingLeft + width
        val heightStart = paddingTop
        val heightEnd = paddingTop + height
        //第一步，设置原点和主轴方向
        var originX = widthStart
        var originY = heightStart
        var direction = DIRECTION_RIGHT
        when (flexDirection) {
            FlexDirection.COLUMN -> {
                direction = DIRECTION_DOWN
                if (flexWarp == FlexWarp.WARP_REVERSE) {
                    originX = widthEnd
                    originY = heightEnd
                }
            }
            FlexDirection.ROW -> {
                direction = DIRECTION_RIGHT
                if (flexWarp == FlexWarp.WARP_REVERSE) {
                    originY = heightEnd
                }
            }
            FlexDirection.COLUMN_REVERSE -> {
                direction = DIRECTION_UP
                if (flexWarp == FlexWarp.WARP_REVERSE) {
                    originX = widthEnd
                    originY = heightEnd
                } else {
                    originY = heightEnd
                }
            }
            FlexDirection.ROW_REVERSE -> {
                direction = DIRECTION_LEFT
                if (flexWarp == FlexWarp.WARP_REVERSE) {
                    originX = widthEnd
                    originY = heightEnd
                } else {
                    originX = widthEnd
                }
            }
        }

        //第二步，计算位置
        var offsetX = originX
        var offsetY = originY
        var frameWidth = -1f
        var frameHeight = -1f
        if(!layoutParams.width.isNaN()){
            frameWidth = layoutParams.width
        }
        if(!layoutParams.height.isNaN()){
            frameHeight = layoutParams.height
        }

        if(flexWarp == FlexWarp.NO_WARP){
            //没有换行
            var spendWidthSpace = 0f
            var spendHeightSpace = 0f
            val flexChildren = ArrayList<View>()
            var flexCount = 0f
            for ( view in children ){
                val l = view.layoutParams as FlexLayoutParams
                val margin = l.margin
                if(direction == DIRECTION_RIGHT || direction == DIRECTION_LEFT){
                    if(!l.flex.isNaN() && l.width.isNaN()){
                        flexCount += l.flex
                        flexChildren.add(view)
                        continue
                    }
                }else{
                    if(!l.flex.isNaN() && l.height.isNaN() ){
                        flexChildren.add(view)
                        flexCount += l.flex
                        continue
                    }
                }


                //measure not flex views and calculate spend size
                val frame = view.frame
                var maxWidth = width
                var maxHeight = height
                if(margin != null){
                    maxWidth -= margin.right + margin.left
                    maxHeight -= margin.top + margin.bottom
                }
                if(!l.maxWidth.isNaN()){
                    maxWidth = min(l.maxWidth,width)
                }
                if(!l.maxHeight.isNaN()){
                    maxHeight = min(l.maxHeight,height)
                }
                view.measure(maxWidth, maxHeight, tempSize)
                val w= tempSize.width
                val h = tempSize.height
                var reMeasure = false
                val tempW = l.width
                val tempH = l.height
                if(!l.minWidth.isNaN() && w < l.minWidth){
                    l.width = l.minWidth
                    reMeasure = true
                }
                if(!l.minHeight.isNaN() && h < l.minHeight){
                    l.height = l.minHeight
                    reMeasure = true
                }
                if(reMeasure){
                    view.measure(maxWidth,maxHeight, tempSize)
                    l.width = tempW
                    l.height = tempH
                }
                frame.width = tempSize.width
                frame.height = tempSize.height

                var occupyWidth = frame.width
                var occupyHeight = frame.height
                if (margin != null) {
                    occupyWidth += margin.left + margin.right
                    occupyHeight += margin.top + margin.bottom
                }
                spendWidthSpace += occupyWidth
                spendHeightSpace += occupyHeight
            }

            if(flexChildren.size > 0){
                var remainWith = width - spendWidthSpace
                if(remainWith < 0) remainWith = 0f
                var remainHeight = height - spendHeightSpace
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
                    if(!layoutParams.maxWidth.isNaN()){
                        maxWidth = min(layoutParams.maxWidth,width)
                    }
                    if(!layoutParams.maxHeight.isNaN()){
                        maxHeight = min(layoutParams.maxHeight,height)
                    }

                    if(direction == DIRECTION_RIGHT || direction == DIRECTION_LEFT){
                        var exactlyWidth = oneWidth * layoutParams.flex
                        if(margin != null){
                            exactlyWidth -= margin.right + margin.left
                        }
                        if(exactlyWidth < 0){
                            exactlyWidth = 0f
                        }
                        if(!layoutParams.maxWidth.isNaN() && exactlyWidth > layoutParams.maxWidth){
                            exactlyWidth = layoutParams.maxWidth
                        }
                        if(!layoutParams.minWidth.isNaN() && exactlyWidth < layoutParams.minWidth){
                            exactlyWidth = layoutParams.minWidth
                        }
                        val temp = layoutParams.width
                        layoutParams.width = exactlyWidth
                        view.measure(maxWidth,maxHeight, tempSize)
                        frame.width = tempSize.width
                        frame.height = tempSize.height
                        layoutParams.width = temp
                    }else{
                        var exactlyHeight = oneHeight * layoutParams.flex
                        if(margin != null){
                            exactlyHeight -= margin.top + margin.bottom
                        }
                        if(exactlyHeight < 0){
                            exactlyHeight = 0f
                        }
                        if(!layoutParams.maxHeight.isNaN() && exactlyHeight > layoutParams.maxHeight){
                            exactlyHeight = layoutParams.maxHeight
                        }
                        if(!layoutParams.minHeight.isNaN() && exactlyHeight < layoutParams.minHeight){
                            exactlyHeight = layoutParams.minWidth
                        }
                        val temp = layoutParams.height
                        layoutParams.height = exactlyHeight
                        view.measure(maxWidth,maxHeight, tempSize)
                        frame.width = tempSize.width
                        frame.height = tempSize.height
                        layoutParams.height = temp
                    }
                }
            }

            spendWidthSpace = 0f
            spendHeightSpace = 0f
            offsetX = originX
            offsetY = originY
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
                spendWidthSpace += occupyWidth
                spendHeightSpace += occupyHeight
                when (direction){
                    DIRECTION_RIGHT -> {
                        //只有 FlexDirection.ROW
                        frame.x = offsetX + (margin?.left?:0f)
                        frame.y = offsetY + (margin?.top?:0f)
                        offsetX += occupyWidth
                    }
                    DIRECTION_LEFT -> {
                        //只有 FlexDirection.ROW_REVERSE
                        frame.x = offsetX - occupyWidth
                        frame.y = offsetY + (margin?.top?:0f)
                        offsetX -= occupyWidth
                    }
                    DIRECTION_DOWN -> {
                        //只有 FlexDirection.COLUMN
                        frame.x = offsetX + (margin?.left?:0f)
                        frame.y = offsetY + (margin?.top?:0f)
                        offsetY += occupyHeight
                    }
                    DIRECTION_UP -> {
                        //只有 FlexDirection.COLUMN_REVERSE
                        frame.x = offsetX + (margin?.left?:0f)
                        frame.y = offsetY - occupyHeight
                        offsetY -= occupyHeight
                    }
                }

            }


            outSize?.width = frameWidth
            outSize?.height = frameHeight

            return
        }else{
            //有换行
            for ( view in children ){
                val l = view.layoutParams as FlexLayoutParams
                val frame = view.frame
                view.measure(width, height, tempSize.reset())
                frame.width = tempSize.width
                frame.height = tempSize.height
                val margin = l.margin
                var occupyWidth = frame.width
                var occupyHeight = frame.height
                if (margin != null) {
                    occupyWidth += margin.left + margin.right
                    occupyHeight += margin.top + margin.bottom
                }
                when (direction){
                    DIRECTION_RIGHT -> {
                        //只有 FlexDirection.ROW
                        frame.x = offsetX + (margin?.left?:0f)
                        frame.y = margin?.top?:0f
                        offsetX += occupyWidth
                    }
                    DIRECTION_LEFT -> {
                        //只有 FlexDirection.ROW_REVERSE
                        frame.x = offsetX - occupyWidth
                        frame.y = margin?.top?:0f
                        offsetX -= occupyWidth
                    }
                    DIRECTION_DOWN -> {
                        //只有 FlexDirection.COLUMN
                        frame.x = margin?.left?:0f
                        frame.y = offsetY + (margin?.top?:0f)
                        offsetY += occupyHeight
                    }
                    DIRECTION_UP -> {
                        //只有 FlexDirection.COLUMN_REVERSE
                        frame.x = margin?.top?:0f
                        frame.y = offsetY - occupyHeight
                        offsetY -= occupyHeight
                    }
                }
            }
            return
        }

        when (flexWarp) {
            FlexWarp.WARP -> {
                var rowHeight = 0f
                val row = ArrayList<View>()
                for (view in children) {
                    val l = view.layoutParams as FlexLayoutParams
                    val frame = view.frame
                    view.measure(
                        width, height,
                        tempSize.reset()
                    )
                    frame.width = tempSize.width
                    frame.height = tempSize.height

                    val margin = l.margin
                    var occupyWidth = frame.width
                    var occupyHeight = frame.height
                    if (margin != null) {
                        occupyWidth += margin.left + margin.right
                        occupyHeight += margin.top + margin.bottom
                    }
                    if (rowHeight < occupyHeight) {
                        rowHeight = occupyHeight
                    }
                    if (offsetX + occupyWidth > width) {
                        if (row.size > 0) {
                            flexRow(width, height, offsetX, rowHeight, row)
                            row.clear()
                        }
                        offsetX = 0f
                        offsetY += rowHeight
                        rowHeight = 0f
                    }
                    if (margin != null) {
                        frame.x = offsetX + margin.left
                        frame.y = offsetY + margin.top
                    } else {
                        frame.x = offsetX
                        frame.y = offsetY
                    }
                    offsetX += occupyWidth
                    row.add(view)
                    //TODO flex all rows
                }
            }
        }
    }

    open fun flexRow(with:Float,height: Float,rowWidth:Float,rowHeight:Float,items:ArrayList<View>){
        val size = items.size
        if(size <= 0){
            return
        }
        when(justifyContent){
            JustifyContent.FLEX_START -> {
                //do noting
            }
            JustifyContent.FLEX_END -> {
                val space = with - rowWidth
                for (v in items){
                    v.frame.x += space
                }
            }
            JustifyContent.CENTER -> {
                val space = (with - rowWidth) / 2f
                for (v in items){
                    v.frame.x += space
                }
            }
            JustifyContent.SPACE_AROUND ->{
                val space = (with - rowWidth) / (size + 1).toFloat()
                for ( i  in 0 until size){
                    items[i].frame.x += space * (i+1)
                }
            }
            JustifyContent.SPACE_BETWEEN ->{
                if(size > 1){
                    val space = (with - rowWidth) / (size - 1).toFloat()
                    for ( i  in 1 until size){
                        items[i].frame.x += space * i
                    }
                }

            }
        }
    }

    open fun adjustRow(direction: Int,frameWidth:Float,frameHeight:Float,width: Float,height: Float,sendWidth:Float,spendHeight:Float){

    }

    override fun checkLayoutParams(child: View) {
        if(child.layoutParams !is FlexLayoutParams){
            val oldLayoutParams = child.layoutParams
            val newLayoutParams = FlexLayoutParams()
            newLayoutParams.width = oldLayoutParams.width
            newLayoutParams.height = oldLayoutParams.height
            newLayoutParams.margin = oldLayoutParams.margin
            child.layoutParams = newLayoutParams
        }
    }

}