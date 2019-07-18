package com.hitales.ui.layout

import com.hitales.ui.Orientation
import com.hitales.ui.View
import com.hitales.ui.ViewGroup

open class LinearLayoutManager : LayoutManager(){

    var orientation =  Orientation.VERTICAL

    override fun layoutSubviews(viewGroup: ViewGroup, subviews: ArrayList<View>) {
        val with = viewGroup.getContentWidth()
        val height = viewGroup.getContentHeight()
        if(with <=0 || height <= 0){
            return
        }
        when (orientation){
            Orientation.HORIZONTAL -> {
                layoutHorizontalSubviews(viewGroup,with,height,subviews)
            }
            Orientation.VERTICAL -> {
                layoutVerticalSubviews(viewGroup,with,height,subviews)
            }
        }
    }

    open fun layoutHorizontalSubviews(viewGroup: ViewGroup,width:Float,height:Float,subviews: ArrayList<View>){
        var offsetX = 0f
        var emptyWidth = width
        for ( i in 0 until subviews.size){
            var measureHeight = width
            val child = subviews[i]
            val frame = child.frame
            frame.x = offsetX
            frame.y = 0f
            emptyWidth = width - offsetX
            if(emptyWidth <= 0){
                frame.width = 0f
                child.frame = frame
                continue
            }
            child.margin?.apply {
                offsetX += this.left
                frame.x = offsetX
                offsetX += this.right
                frame.y = this.top
                measureHeight -= this.top + this.bottom
                emptyWidth = width - offsetX
            }
            val size = child.measureSize(emptyWidth,measureHeight)
            frame.width = size.width
            frame.height = size.height
            child.frame = frame
            offsetX += size.width
        }
    }

    open fun layoutVerticalSubviews(viewGroup: ViewGroup,width:Float,height:Float, subviews: ArrayList<View>){
        var offsetY = 0f
        var emptyHeight = height
        for ( i in 0 until subviews.size){
            var measureWidth = width
            val child = subviews[i]
            val frame = child.frame
            frame.x = 0f
            frame.y = offsetY
            emptyHeight = height - offsetY
            if(emptyHeight <= 0){
                frame.height = 0f
                child.frame = frame
                continue
            }
            child.margin?.apply {
                offsetY += this.top
                frame.x = this.left
                frame.y = offsetY
                measureWidth -= this.left + this.right
                emptyHeight = height - offsetY
                offsetY += this.bottom
            }
            val size = child.measureSize(measureWidth,emptyHeight)
            frame.width = size.width
            frame.height = size.height
            child.frame = frame
            offsetY += size.height
        }
    }

}