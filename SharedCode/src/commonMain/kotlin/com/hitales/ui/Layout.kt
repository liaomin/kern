package com.hitales.ui

import com.hitales.utils.Size

open class LayoutParams {

    companion object {
        internal const val FLAG_WIDTH_MASK = 1
        internal const val FLAG_HEIGHT_MASK = 2
    }

    constructor(width:Float = Float.NaN,height: Float =  Float.NaN){
        this.width = width
        this.height = height
    }

    /**
     * 用位运算来提高效率，判断是否设置宽高的
     */
    internal var flag = 0

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

}

/**
 * 自定义布局
 */
abstract class CustomLayout<T:LayoutParams> : Layout {

    constructor(layoutParams: LayoutParams? = null):super(layoutParams)

    override fun addSubView(view: View, index: Int) {
        checkLayoutParams(view)
        super.addSubView(view, index)
    }

    abstract fun checkLayoutParams(child: View)
}
