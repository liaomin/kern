package com.hitales.ui

import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame

enum class ViewState(val value:Int) {
    NORMAL(0),
    PRESSED(1),
    FOCUSED(2),
    DISABLED(3),
    SELECTED(4),
}

expect open class View  {
    /**
     * use margin and padding to calculate frame
     */
    var padding:EdgeInsets

    /**
     * use margin and padding to calculate frame
     */
    var margin:EdgeInsets

    var border:EdgeInsets?

    var borderWith:Float

    /**
     * layout params
     */
    open var frame:Frame

    open var id:Int

    open var tag:Any?

    constructor(frame: Frame = Frame.zero())

    var superView:LayoutView?
    /**
     * argb color
     */
    open fun setBackgroundColor(color:Int)
    open fun removeFromSuperView()
    open fun onAttachedToWindow()
    open fun onDetachedFromWindow()
    open fun onAttachedToView(layoutView: LayoutView)
    open fun onDetachedFromView(layoutView: LayoutView)
}

expect open class LayoutView : View {

    constructor(frame: Frame = Frame.zero())

    val children:ArrayList<View>

    open fun addView(view: View, index:Int = -1)

    open fun removeView(view:View)
}

expect open class TextView : View {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open var text:CharSequence?
    open var textSize:Float
    /**
     * set all state color
     */
    open var textColor:Int
}


expect open class Button : TextView {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open fun setBackgroundColor(color:Int,state: ViewState = ViewState.NORMAL)
    open fun setImage(image:Image,state: ViewState = ViewState.NORMAL)
    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)
    fun setOnPressListener(listener:(iew:View)->Unit)
    fun setOnLongPressListener(listener:(iew:View)->Unit)
}

expect open class ImageView : View {
    constructor(frame: Frame = Frame.zero())
    open var image:Image?

}


expect open class Input : TextView {
    constructor(text:CharSequence? = null,frame: Frame = Frame.zero())
    open var placeholderText:CharSequence?
    open var placeholderTextColor:Int
    var autoFocus:Boolean
    open fun setTextColor(color:Int,state: ViewState = ViewState.NORMAL)
    open fun cleanFocus()
}




expect open class ScrollView : LayoutView {
    constructor(frame: Frame = Frame.zero())
}




