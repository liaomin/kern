package com.hitales.ui.layout.flex

import com.hitales.ui.CustomLayout
import com.hitales.ui.LayoutParams
import com.hitales.ui.MeasureMode
import com.hitales.ui.View
import com.hitales.utils.Size


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
        internal const val DIRECTION_RIGHT = 0
        internal const val DIRECTION_LEFT = 1
        internal const val DIRECTION_DOWN = 2
        internal const val DIRECTION_UP = 3
    }

    val rowCalculator:RowCalculator by lazy { RowCalculator() }

    val rowWrapCalculator:RowWrapCalculator by lazy { RowWrapCalculator() }

    val columnCalculator:ColumnCalculator by lazy { ColumnCalculator() }

    val columnWarpCalculator:ColumnWrapCalculator by lazy { ColumnWrapCalculator() }

    private data class Row(var width:Float,var height: Float,var items: ArrayList<View>)

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

    constructor(layoutParams: LayoutParams? = null):super(layoutParams)


    override fun measure(widthSpace: Float,widthMode: MeasureMode, heightSpace: Float,heightMode: MeasureMode,outSize: Size) {
        var direction = DIRECTION_RIGHT
        when (flexDirection) {
            FlexDirection.COLUMN -> {
                direction = DIRECTION_DOWN
            }
            FlexDirection.ROW -> {
                direction = DIRECTION_RIGHT
            }
            FlexDirection.COLUMN_REVERSE -> {
                direction = DIRECTION_UP
            }
            FlexDirection.ROW_REVERSE -> {
                direction = DIRECTION_LEFT
            }
        }
        var w = widthSpace
        var h = heightSpace
        var wMode = widthMode
        var hMode = heightMode

        if(flexDirection  == FlexDirection.ROW || flexDirection == FlexDirection.ROW_REVERSE){
            if (flexWarp == FlexWarp.NO_WARP) {
                rowCalculator.calculate(this,direction,children,w,wMode, h,hMode, outSize)
            }else{
                rowWrapCalculator.calculate(this,direction,children,w,wMode, h,hMode, outSize)
            }
        }else{
            if (flexWarp == FlexWarp.NO_WARP) {
                columnCalculator.calculate(this,direction,children,w,wMode, h,hMode, outSize)
            }else{
                columnWarpCalculator.calculate(this,direction,children,w,wMode, h,hMode, outSize)
            }
        }
    }


    override fun measureChild(child: View, width:Float, widthMode: MeasureMode, height:Float, heightMode: MeasureMode, outSize: Size){
        var maxWidth = width
        var maxHeight = height
        val l = child.layoutParams as FlexLayoutParams
        var wMode = widthMode
        var hMode = heightMode
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK ==  LayoutParams.FLAG_WIDTH_MASK){
            maxWidth = l.width
            wMode = MeasureMode.EXACTLY
        }
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK ==  LayoutParams.FLAG_HEIGHT_MASK){
            maxHeight = l.height
            hMode = MeasureMode.EXACTLY
        }
        child.measure(maxWidth,wMode,maxHeight,hMode,outSize)
        var frame = child.frame
        var w = outSize.width
        var h = outSize.height

        var reMeasure = false

        if(l.flag and FlexLayoutParams.FLAG_MAX_WIDTH_MASK == FlexLayoutParams.FLAG_MAX_WIDTH_MASK && w > l.maxWidth){
            maxWidth = l.maxWidth
            wMode = MeasureMode.AT_MOST
            reMeasure = true
        }
        if(l.flag and FlexLayoutParams.FLAG_MAX_HEIGHT_MASK == FlexLayoutParams.FLAG_MAX_HEIGHT_MASK && h > l.maxHeight){
            maxHeight = l.maxHeight
            hMode = MeasureMode.AT_MOST
            reMeasure = true
        }

        val tempW = l.width
        val tempH = l.height
        if(l.flag and LayoutParams.FLAG_WIDTH_MASK != LayoutParams.FLAG_WIDTH_MASK && l.flag and FlexLayoutParams.FLAG_MIN_WIDTH_MASK == FlexLayoutParams.FLAG_MIN_WIDTH_MASK && w < l.minWidth){
            maxWidth= l.minWidth
            wMode = MeasureMode.EXACTLY
            reMeasure = true
        }
        if(l.flag and LayoutParams.FLAG_HEIGHT_MASK != LayoutParams.FLAG_HEIGHT_MASK && l.flag and FlexLayoutParams.FLAG_MIN_HEIGHT_MASK == FlexLayoutParams.FLAG_MIN_HEIGHT_MASK && h < l.minHeight){
            maxHeight = l.minHeight
            hMode = MeasureMode.EXACTLY
            reMeasure = true
        }
        if(reMeasure){
            child.measure(maxWidth,wMode,maxHeight,hMode,outSize)
            l.width = tempW
            l.height = tempH
        }
        frame.width = outSize.width
        frame.height = outSize.height
    }


    override fun convertLayoutParams(child: View):FlexLayoutParams {
        val l = child.layoutParams
        if(l == null){
            return FlexLayoutParams()
        }
        if(l is FlexLayoutParams){
            return l
        }
        return FlexLayoutParams(l.width,l.height)
    }

}