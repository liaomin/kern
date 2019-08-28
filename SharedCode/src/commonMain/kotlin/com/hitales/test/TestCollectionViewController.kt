package com.hitales.test

import com.hitales.ui.*
import com.hitales.ui.recycler.*
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.random


open class TestCollectionViewController : BasicController() {

    class Cell : CollectionViewCell(){

        val button = Button()

        init {
            contentView.setBackgroundColor(Colors.RED)
            contentView.addSubView(button)
        }

        override fun applyAttribute(layoutAttribute: LayoutAttribute) {
            super.applyAttribute(layoutAttribute)
            button.frame = button.frame.set(0f,0f,layoutAttribute.frame.width,layoutAttribute.frame.height )
        }

    }



    override fun onCreate() {
        super.onCreate()
        val collectionView = CollectionView( Frame(0f,0f,Platform.windowWidth,Platform.windowHeight - 40))
//        collectionView.setBackgroundColor(Colors.RED)

        val itemWidth = (Platform.windowWidth - 80) / 4
        val adapter = object : CollectionViewAdapter(){

//            override fun haveHeaderView(collectionView: CollectionView, section: Int): Boolean {
//                return true
//            }
//
//            override fun haveFooterView(collectionView: CollectionView, section: Int): Boolean {
//                return true
//            }

            override fun getSectionHeaderViewSize(collectionView: CollectionView, section: Int, size: Size) {
                size.set(100f,100f)
            }

            override fun getSectionFooterViewSize(collectionView: CollectionView, section: Int, size: Size) {
                size.set(200f,200f)
            }

            override fun createHeaderView(collectionView: CollectionView): CollectionViewCell? {
                val v= Cell()
                v.button.setBackgroundColor(Colors.RED)
                return v
            }

            override fun createFooterView(collectionView: CollectionView): CollectionViewCell? {
                val v= Cell()
                v.button.setBackgroundColor(Colors.BLUE)
                return v
            }

            override fun onBindHeaderView(collectionView: CollectionView, section: Int, view: CollectionViewCell) {
                (view as Cell).button.text = "Header section:$section"
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)

            }

            override fun onBindFooterView(collectionView: CollectionView, section: Int, view: CollectionViewCell) {
                (view as Cell).button.text = "Footer section:$section"
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)
            }

            override fun createItemView(collectionView: CollectionView, type: Int): CollectionViewCell {
                val c= Cell()
                val v = c.button
                if(Platform.os == PLATFORM_ANDROID){
                    c.setBackgroundColor(0xFF000000.toInt() or (0xFFFFFF * random()).toInt())
                    c.setBackgroundColor(Colors.BLUE, ViewState.PRESSED)
                    v.setBackgroundColor(Colors.TRANSPARENT)

                    c.contentView.setShadow(Colors.RED,10f,6f,0f)
                    v.setBorderRadius(0f)
                }else{
                    v.setBackgroundColor(0xFF000000.toInt() or (0xFFFFFF * random()).toInt())
                    v.setBackgroundColor(Colors.BLUE, ViewState.PRESSED)
//                    c.setBackgroundColor(Colors.TRANSPARENT)

                    c.contentView.setShadow(Colors.RED,10f,6f,0f)
                    v.setBorderRadius(0f)
                }

//                c.contentView.setBorderRadius(20f)
//                (v as Button).setBackgroundColor(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),ViewState.NORMAL)
                return c
            }

            override fun onBindItemView(collectionView: CollectionView, section: Int, row: Int,type:Int, view: CollectionViewCell) {

                (view as Cell).button.text = "$section - $row"
//                view.button.setOnPressListener {
//                    if(section == 0 && row == 10){
//                        this@TestCollectionViewController.push(TestHCollectionViewController())
//                    }
//                }
//                view.setShadow(Colors.BLUE,4f,0f,0f)
//                if(section == 0 && row == 0){
//                    view.setShadow(Colors.BLUE,4f,0f,0f)
//                    view.textColor = Colors.BLUE
//                }
//                view.setShadow(0xFF000000.toInt() or (0xFFFFFF * random()).toInt(),10f,5f,0f)
            }

            override fun getItemViewSize(collectionView: CollectionView, section: Int, row: Int,type:Int, size: Size) {
//                size.set((itemWidth * random()).toFloat(),(itemWidth * random()).toFloat() )
                size.set(itemWidth ,itemWidth)
            }

            override fun getNumberOfItem(view: CollectionView, section: Int): Int {
                return 200 * 4
            }

            override fun getNumberOfSection(collectionView: CollectionView): Int {
                return 3
            }

//            override fun onItemPress(collectionView: CollectionView, section: Int, row: Int, cell: CollectionViewCell) {
//                println("$section  $row")
//            }
//
//            override fun onItemLongPress(collectionView: CollectionView, section: Int, row: Int, cell: CollectionViewCell) {
//                println("long $section  $row")
//            }

        }
        collectionView.padding = EdgeInsets(20f,20f,20f,20f)
        (collectionView.layout as DefaultCollectionViewLayout).minimumInterItemSpacing = 40f
        (collectionView.layout as DefaultCollectionViewLayout).minimumLineSpacing = 40f
        collectionView.layout.adjustRow = true
//        (collectionView.layout as DefaultCollectionViewLayout).headerAndFooterAddLineSpace = true
//        collectionView.layout.maxColumns = 1
        collectionView.adapter = adapter
//        collectionView.orientation = Orientation.HORIZONTAL
//        this.view = collectionView
//        collectionView.setBackgroundColor(Colors.RED)
        addView(collectionView,0f,0f,Platform.windowHeight - 100)
    }





}