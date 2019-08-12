package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.recycler.CollectionView
import com.hitales.ui.recycler.CollectionViewAdapter
import com.hitales.ui.recycler.CollectionViewLayout
import com.hitales.ui.recycler.DefaultCollectionViewLayout
import com.hitales.utils.*

open class TestCollectionViewController : Controller() {


    override fun onCreate() {
        val collectionView = CollectionView( Frame(0f,0f,Platform.windowWidth,Platform.windowHeight))
//        collectionView.setBackgroundColor(Colors.RED)

        val itemWidth = (Platform.windowWidth - 80) / 3
        val adapter = object : CollectionViewAdapter(){

            override fun haveHeaderView(collectionView: CollectionView, section: Int): Boolean {
                return true
            }

            override fun haveFooterView(collectionView: CollectionView, section: Int): Boolean {
                return false
            }

            override fun getSectionHeaderViewSize(collectionView: CollectionView, section: Int, size: Size) {
                size.set(100f,100f)
            }

            override fun getSectionFooterViewSize(collectionView: CollectionView, section: Int, size: Size) {
                size.set(200f,200f)
            }

            override fun createHeaderView(collectionView: CollectionView): View? {
                val v= Button()
                v.setBackgroundColor(Colors.RED)
                return v
            }

            override fun createFooterView(collectionView: CollectionView): View? {
                val v= Button()
                v.setBackgroundColor(Colors.BLUE)
                return v
            }

            override fun onBindHeaderView(collectionView: CollectionView, section: Int, view: View) {
                (view as Button).text = "Header section:$section"
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)

            }

            override fun onBindFooterView(collectionView: CollectionView, section: Int, view: View) {
                (view as Button).text = "Footer section:$section"
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)
            }

            override fun createItemView(collectionView: CollectionView, type: Int): View {
                val v= Button()
//                v.setBackgroundColor(0xFF000000.toInt() or (0xFFFFFF * random()).toInt() )
                v.setBackgroundColor(Colors.RED,ViewState.PRESSED)
                return v
            }

            override fun onBindItemView(collectionView: CollectionView, section: Int, row: Int,type:Int, view: View) {
                (view as Button).setBackgroundColor(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),ViewState.NORMAL)
                view.text = "$section - $row"
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)
            }

            override fun getItemViewSize(collectionView: CollectionView, section: Int, row: Int,type:Int, size: Size) {
//                size.set((itemWidth * random()).toFloat(),(itemWidth * random()).toFloat() )
                size.set(itemWidth ,itemWidth)
            }

            override fun getNumberOfItem(view: CollectionView, section: Int): Int {
                return 4
            }

            override fun getNumberOfSection(collectionView: CollectionView): Int {
                return 2
            }

        }
        collectionView.padding = EdgeInsets(20f,20f,20f,20f)
        (collectionView.layout as DefaultCollectionViewLayout).minimumInteritemSpacing = 20f
        (collectionView.layout as DefaultCollectionViewLayout).headerAndFooterAddLineSpace = true
//        collectionView.layout.maxColumns = 1
        collectionView.adapter = adapter
//        collectionView.orientation = Orientation.HORIZONTAL
        this.view = collectionView
    }





}