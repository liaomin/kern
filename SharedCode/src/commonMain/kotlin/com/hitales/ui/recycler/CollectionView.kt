package com.hitales.ui.recycler

import com.hitales.ui.ScrollView
import com.hitales.utils.Frame

expect open class CollectionView : ScrollView {

    constructor(frame: Frame = Frame.zero(),layout: CollectionViewLayout = DefaultCollectionViewLayout())

    var adapter:CollectionViewAdapter?

    val layout:CollectionViewLayout

    fun reloadData()

}