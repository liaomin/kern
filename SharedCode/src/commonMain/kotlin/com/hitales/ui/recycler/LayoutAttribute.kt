//package com.hitales.ui.recycler
//
//import com.hitales.utils.Frame
//
//data class Transform(var translateX:Float = 0f, var translateY:Float = 0f, var rotateX:Float = 0f , var rotateY:Float = 0f, var rotateZ:Float = 0f , var scaleX:Float = 1f, var scaleY:Float = 1f)
//
//val ElementKindHeader:String = "CollectionViewHeader"
//
//val ElementKindFooter:String = "CollectionViewFooter"
//
//val ElementKindCell:String = "CollectionViewCell"
//
//val ElementKindNone:String = "CollectionViewNone"
//
//open class LayoutAttribute(var frame: Frame = Frame.zero(), var section:Int = -1, var row:Int = -1) {
//
//    var elementKind:String? = null
//
//    var transform:Transform? = null
//
//    var opacity:Float = 1f
//
//    var zIndex:Int = 0
//
//    var tag:Any? = null
//
//    var view:Any? = null
//
//    var position:Int = -1
//
//    var viewType:Int = 0
//
//    override fun equals(other: Any?): Boolean {
//        if(other != null && other is LayoutAttribute){
//            return position == other.position
//        }
//        return super.equals(other)
//    }
//
//    open fun reset(){
//        elementKind = ElementKindNone
//        transform = null
//        opacity = 1f
//        zIndex = 0
//        tag = null
//        view = null
//        position = -1
//        frame.reset()
//        section = -1
//        row = -1
//        viewType = 0
//    }
//
//}