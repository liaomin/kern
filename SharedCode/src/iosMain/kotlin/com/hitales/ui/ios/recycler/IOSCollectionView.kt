package com.hitales.ui.recycler

import com.hitales.ios.ui.HCell
import com.hitales.ios.ui.HCollectionView
import com.hitales.utils.Size
import com.hitales.utils.WeakReference
import kotlinx.cinterop.CValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIScrollView
import platform.darwin.NSInteger

class IOSCollectionView(val collectionViewRef: WeakReference<CollectionView>):HCollectionView(CGRectMake(0.0,0.0,0.0,0.0),IOSCollectionViewLayout(collectionViewRef)){

    init {
        this.delegate = this
        this.setDelaysContentTouches(false)
    }

    val tempSize  = Size()

    override fun getNumberOfSection(): NSInteger {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                return it.getNumberOfSection(collectionView).toLong()
            }
        }
        return 0
    }

    override fun getNumberOfItem(sectionIndex: NSInteger): NSInteger {
        val section = sectionIndex.toInt()
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                var itemCount = it.getNumberOfItem(collectionView,sectionIndex.toInt()).toLong()
                if(it.haveHeaderView(collectionView,section)){
                    itemCount++
                }
                if(it.haveFooterView(collectionView,section)){
                    itemCount++
                }
                return itemCount
            }
        }
        return 0
    }

    override fun getItemType(sectionIndex: NSInteger, row: NSInteger): NSInteger {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                var r = row.toInt()
                val s = sectionIndex.toInt()
                val haveHeader = it.haveHeaderView(collectionView,s)
                if(r == 0 && haveHeader){
                    return CollectionView.HEADER_TYPE.toLong()
                }
                if(haveHeader){
                    r --
                }

                return it.getNumberOfSection(collectionView).toLong()
            }
        }
        return 0
    }

    override fun createHeaderView(sectionIndex: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val cell = it.createHeaderView(collectionView)!!
                withView.setContent(cell.contentView.getWidget(),CellProxy(cell))
            }
        }
    }

    override fun createFooterView(sectionIndex: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val cell = it.createFooterView(collectionView)!!
                withView.setContent(cell.contentView.getWidget(),CellProxy(cell))
            }
        }
    }

    override fun createItemView(sectionIndex: NSInteger, row: NSInteger, cellType: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val cell = it.createItemView(collectionView,cellType.toInt())
                withView.setContent(cell.contentView.getWidget(),CellProxy(cell))
            }
        }
    }

    override fun onBindHeaderView(sectionIndex: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val proxy = withView.store as CellProxy
                it.onBindHeaderView(collectionView,sectionIndex.toInt(),proxy.cell)
            }
        }
    }

    override fun onBindFooterView(sectionIndex: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val proxy = withView.store as CellProxy
                it.onBindFooterView(collectionView,sectionIndex.toInt(),proxy.cell)
            }
        }
    }


    override fun onBindItemView(sectionIndex: NSInteger, row: NSInteger, viewType: NSInteger, withView: HCell) {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                val proxy = withView.store as CellProxy
                it.onBindItemView(collectionView,sectionIndex.toInt(),row.toInt(),viewType.toInt(),proxy.cell)
            }
        }
    }

    override fun getItemSize(sectionIndex: NSInteger, row: NSInteger): CValue<CGSize> {
        val collectionView = collectionViewRef.get()
        if(collectionView != null){
            collectionView.adapter?.let {
                var r = row.toInt()
                val s = sectionIndex.toInt()
                val type = it.getItemType(collectionView,s,r)
                tempSize.reset()
                it.getItemViewSize(collectionView,s,r,0,tempSize)
                return CGSizeMake(tempSize.width.toDouble(),tempSize.height.toDouble())
            }
        }
        return super.getItemSize(sectionIndex, row)
    }

    override fun scrollViewDidScroll(scrollView: UIScrollView) {
        scrollView.contentOffset.useContents {
            collectionViewRef.get()?.onScroll(x.toFloat(),y.toFloat())
        }
    }

    override fun scrollViewDidScrollToTop(scrollView: UIScrollView) {
        scrollView.contentOffset.useContents {
            collectionViewRef.get()?.onScroll(0f,0f)
        }
    }

}