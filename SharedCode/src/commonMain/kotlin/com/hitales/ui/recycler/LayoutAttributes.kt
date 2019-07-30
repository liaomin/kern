package com.hitales.ui.recycler

import com.hitales.utils.Frame

data class Transform(var translateX:Float = 0f, var translateY:Float = 0f, var rotateX:Float = 0f , var rotateY:Float = 0f, var rotateZ:Float = 0f , var scaleX:Float = 1f, var scaleY:Float = 1f)


open class LayoutAttributes(var frame: Frame, var sectionIndex:Int, var index:Int) {

    var elementKind:String? = null

    var transform:Transform? = null

    var opacity:Float = 1f
}