package com.hitales.ui.recycler

import com.hitales.ui.Orientation
import com.hitales.utils.Frame
import com.hitales.utils.Size
import com.hitales.utils.min

abstract class LayoutHelper{

    val contextSize = Size()

    abstract fun getNextPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, nextPage: CollectionViewLayout.PageLayoutInfo)

    abstract fun getLastPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo, lastPage: CollectionViewLayout.PageLayoutInfo)

    abstract fun adjustRow(row: ArrayList<LayoutAttribute>, offsetX: Float, offsetY: Float, maxRowHeight: Float)
}

open class DefaultCollectionViewLayout : CollectionViewLayout(){

    companion object {
        val HEADER_TYPE = -0xFFFFF0
        val FOOTER_TYPE = -0xFFFFF1
        val HEADER_INDEX = -1
        val FOOTER_INDEX = -2
    }

    override fun prepareLayout() {

    }


    val tempSize = Size()

    val rows = ArrayList<LayoutAttribute>()

    /**
     * 一行最多几列，默认没有限制，放不下才换行
     */
    var maxColumns = -1

    /**
     * 每一行的最小间隔
     */
    var minimumLineSpacing = 10f

    /**
     * 每一列的最小间隔
     */
    var minimumInteritemSpacing = 10f

    var headerAndFooterAddLineSpace = false

    val contextSize = Size()

    private val verticalLayoutHelper = VerticalLayoutHelper()

    private val horizontalLayoutHelper = HorizontalLayoutHelper()

    override fun onScroll(scrollX: Float, scrollY: Float) {

    }


    open fun addMargin(row:ArrayList<LayoutAttribute>,width:Float,height:Float){
        val size = row.size
        if(size > 0){
            val r = row.last().frame.getRight()
            val e = width - r
            val margin = e / (size + 1)
            var offset = margin
            for (i in 0 until size ){
                val attr = row.get(i)
                attr.frame.x += offset
                offset += margin
            }
        }

    }


    override fun getPageLayoutInfo(lastPage:PageLayoutInfo,currentPage: PageLayoutInfo,nextPage:PageLayoutInfo){
        collectionViewRef?.get()?.apply {
            val orientation = this.orientation
            when (orientation){
                Orientation.VERTICAL -> getVerticalPageLayoutInfo(this,lastPage,currentPage,nextPage)
                Orientation.HORIZONTAL -> getHorizontalPageLayoutInfo(this,lastPage,currentPage,nextPage)
            }
        }
    }

    open fun getVerticalPageLayoutInfo(collectionView: CollectionView,lastPage:PageLayoutInfo,currentPage: PageLayoutInfo,nextPage:PageLayoutInfo){
        val adapter = collectionView.adapter
        if(adapter == null) {
            return
        }
        val scrollY = collectionView.scrollY
        var page = 0
        if(lastPage.isEmpty()){
            if(currentPage.isEmpty()){
                //初始化
                getVerticalNextPageLayoutInfo(collectionView,adapter,lastPage,currentPage)
                currentPage.page = page
                page ++
                while(currentPage.frame.getBottom() < scrollY ){
                    getVerticalNextPageLayoutInfo(collectionView,adapter,currentPage,currentPage)
                    currentPage.page = page
                    page ++
                }
                getVerticalLastPageLayoutInfo(collectionView,adapter,currentPage,lastPage)
            }else{
                getVerticalLastPageLayoutInfo(collectionView,adapter,currentPage,lastPage)
            }
        }
        if(currentPage.isEmpty()){
            getVerticalNextPageLayoutInfo(collectionView,adapter,lastPage,currentPage)
            currentPage.page = lastPage.page + 1
        }
        if(nextPage.isEmpty()){
            getVerticalNextPageLayoutInfo(collectionView,adapter,currentPage,nextPage)
            nextPage.page = currentPage.page + 1
        }
    }

    open fun getVerticalNextPageLayoutInfo(collectionView: CollectionView,adapter:CollectionViewAdapter,currentPage: PageLayoutInfo,nextPage: PageLayoutInfo){
        verticalLayoutHelper.getNextPageLayoutInfo(collectionView,this,adapter,currentPage, nextPage)
    }

    open fun getVerticalLastPageLayoutInfo(collectionView: CollectionView,adapter:CollectionViewAdapter,currentPage: PageLayoutInfo,lastPage: PageLayoutInfo){
        verticalLayoutHelper.getLastPageLayoutInfo(collectionView,this,adapter,currentPage, lastPage)
    }

    open fun getHorizontalPageLayoutInfo(collectionView: CollectionView,lastPage:PageLayoutInfo,currentPage: PageLayoutInfo,nextPage:PageLayoutInfo){

    }

    open fun getHorizontalNextPageLayoutInfo(collectionView: CollectionView,adapter:CollectionViewAdapter,currentPage: PageLayoutInfo,nextPage: PageLayoutInfo){
        horizontalLayoutHelper.getNextPageLayoutInfo(collectionView,this,adapter,currentPage, nextPage)
    }

    open fun getHorizontalLastPageLayoutInfo(collectionView: CollectionView,adapter:CollectionViewAdapter,currentPage: PageLayoutInfo,lastPage: PageLayoutInfo){
        horizontalLayoutHelper.getLastPageLayoutInfo(collectionView,this,adapter,currentPage, lastPage)
    }


    override fun getContentSize(size: Size) {
        size.set(contextSize)
    }


    override fun adjustVerticalRow(row: ArrayList<LayoutAttribute>, offsetX: Float, offsetY: Float, maxRowHeight: Float) {

    }

    override fun adjustHorizontalRow(row: ArrayList<LayoutAttribute>, offsetX: Float, offsetY: Float,maxRowWidth: Float) {
    }
}