//package com.hitales.ui.recycler
//
//import com.hitales.ui.*
//import com.hitales.utils.WeakReference
//
//actual open class CollectionViewCell : ViewDelegate {
//
//    actual val contentView: Layout
//
//    var section:Int = 0
//
//    var row:Int = 0
//
//    var tag:Any? = null
//
//    var collectionView:CollectionView? = null
//
//    actual constructor(){
//        contentView = Layout()
//        contentView.delegate = WeakReference(this)
//    }
//
//    actual open fun applyAttribute(layoutAttribute: LayoutAttribute) {
//        contentView.frame.set(layoutAttribute.frame)
//        contentView.onFrameChanged()
//        section = layoutAttribute.section
//        row = layoutAttribute.row
//    }
//
//    override fun onPress(view: View) {
//        if(section >= 0 && row >= 0){
//            collectionView?.onItemPress(section,row,this)
//        }
//    }
//
//    override fun onLongPress(view: View) {
//        if(section >= 0 && row >= 0){
//            collectionView?.onItemLongPress(section,row,this)
//        }
//    }
//
//    actual open fun setBackgroundColor(color: Int, state: ViewState) {
//        if(state != ViewState.NORMAL){
//            contentView.getOrCreateBackground().setColorForState(color,state)
//        }else{
//            val mBackground = contentView.getBackground()
//            if(mBackground != null){
//                mBackground?.setColorForState(color,state)
//            }else{
//                contentView.setBackgroundColor(color)
//            }
//        }
//    }
//
//}