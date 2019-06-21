package com.hitales.ui.layout

import com.hitales.ui.ViewGroup

open class LinearLayoutManager : LayoutManager(){

    var orientation = VERTICAL

    override fun layoutSubviews(viewGroup: ViewGroup) {
        val with = viewGroup.frame.width
        val height = viewGroup.frame.height
        if(with <=0 || height <= 0){
            return
        }
        when (orientation){
            HORIZONTAL -> {
                layoutHorizontalSubviews(viewGroup,with,height)
            }
            VERTICAL -> {
                layoutVerticalSubviews(viewGroup,with,height)
            }
        }
    }

    open fun layoutHorizontalSubviews(viewGroup: ViewGroup,width:Float,height:Float){

    }

    open fun layoutVerticalSubviews(viewGroup: ViewGroup,width:Float,height:Float){
        var offsetY = 0f
        for ( i in 0 until viewGroup.children.size){
            var measureWidth = width
            val child = viewGroup.children[i]
            val frame = child.frame
            frame.x = 0f
            frame.y = offsetY
            child.margin?.apply {
                offsetY += this.top
                frame.x = this.left
                frame.y = offsetY
                measureWidth -= this.left + this.right
                offsetY += this.bottom
            }

            val size = child.measureSize(measureWidth,height)
            frame.width = size.width
            frame.height = size.height
            child.frame = frame
            offsetY += size.height
        }
    }

}