package com.hitales.ui

import com.hitales.utils.Size

open class LayoutParams {

    companion object {
        internal const val FLAG_WIDTH_MASK = 1
        internal const val FLAG_HEIGHT_MASK = 2
        internal const val FLAG_WIDTH_PERCENT_MASK = 3
        internal const val FLAG_HEIGHT_PERCENT_MASK = 4
    }

    constructor(width:Float = Float.NaN,height: Float =  Float.NaN){
        this.width = width
        this.height = height
    }

    /**
     * 用位运算来提高效率，判断是否设置宽高的
     */
    internal var flag = 0

    /**
     *
     */
    fun setWidth(width: Float,isPercentage:Boolean = false){
        var w = width
        if(isPercentage && (!width.isNaN())){
            flag = flag or FLAG_WIDTH_PERCENT_MASK
            if(w < 0) w = 0f
            if(w > 100) w = 100f
        }
        this.width = w
    }

    fun setHeight(height: Float,isPercent:Boolean = false){
        var h = width
        if(isPercent && (!height.isNaN())){
            flag = flag or FLAG_HEIGHT_PERCENT_MASK
            if(h < 0) h = 0f
            if(h > 100) h = 100f
        }
        this.height = h
    }

    fun isPercentWidth():Boolean{
        return flag and FLAG_WIDTH_PERCENT_MASK == FLAG_WIDTH_PERCENT_MASK
    }

    fun isPercentHeight():Boolean{
        return flag and FLAG_HEIGHT_PERCENT_MASK == FLAG_HEIGHT_PERCENT_MASK
    }

    var width:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_WIDTH_MASK.inv()
            }else{
                flag = flag or FLAG_WIDTH_MASK
            }
        }

    var height:Float = Float.NaN
        set(value) {
            field = value
            if(value.isNaN()){
                flag = flag and FLAG_HEIGHT_MASK.inv()
            }else{
                flag = flag or FLAG_HEIGHT_MASK
            }
        }
}

expect open class Layout : View {
    /**
     * clips children
     */
    open var clipsToBounds:Boolean

    constructor(layoutParams: LayoutParams? = null)

    val children:ArrayList<View>

    open fun addSubView(view: View, index:Int = -1)

    open fun removeSubView(view:View)

    open fun removeAllSubViews()

    open fun measureChild(child: View,width:Float,widthMode: MeasureMode,height:Float,heightMode: MeasureMode,outSize: Size)

    open fun checkLayoutParams(child: View)
}

/**
 * 自定义布局
 */
abstract class CustomLayout<T:LayoutParams> : Layout {

    constructor(layoutParams: LayoutParams? = null):super(layoutParams)

    override fun checkLayoutParams(child: View) {
        child.layoutParams = convertLayoutParams(child)
    }

    abstract fun convertLayoutParams(child: View):T
}
