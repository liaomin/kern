package com.hitales.ui.recycler

import com.hitales.ui.Orientation
import com.hitales.ui.ScrollView
import com.hitales.utils.Frame

actual open class CollectionView : ScrollView {

    actual constructor(frame: Frame):super(frame)

    actual var adapter: CollectionViewAdapter? = null

    actual var layout: CollectionViewLayout? = null

    actual var orientation: Orientation = Orientation.VERTICAL
        set(value) {
            field = value
            reloadData()
        }

    actual fun reloadData() {

    }

}