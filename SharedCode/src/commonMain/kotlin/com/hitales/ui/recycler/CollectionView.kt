package com.hitales.ui.recycler

import com.hitales.ui.Orientation
import com.hitales.ui.ScrollView
import com.hitales.ui.View
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.WeakReference

val ElementKindHeader:String = "CollectionViewHeader"

val ElementKindFooter:String = "CollectionViewFooter"

val ElementKindCell:String = "CollectionViewCell"



open class DefaultCollectionViewLayout(collectionViewRef: WeakReference<CollectionView>) : CollectionViewLayout(collectionViewRef){

    val tempSize:Size = Size()

    override fun prepareLayout() {

    }

    override fun getAttributesInVisibleFrame(frame: Frame): ArrayList<LayoutAttributes> {
        return ArrayList()
    }

    override fun getContentSize(size: Size) {

    }

}

expect open class CollectionView : ScrollView {

    var orientation: Orientation

    constructor(frame: Frame = Frame.zero())

    var adapter:CollectionViewAdapter?

    var layout:CollectionViewLayout?

    fun reloadData()

}