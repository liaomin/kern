package com.hitales.ui.recycler

import com.hitales.ui.LayoutParams
import com.hitales.ui.ScrollView

expect open class CollectionView : ScrollView {

    constructor(layoutParams: LayoutParams? = null, layout: CollectionViewLayout = DefaultCollectionViewLayout())

    var adapter:CollectionViewAdapter?

    val layout:CollectionViewLayout

    fun reloadData()

}