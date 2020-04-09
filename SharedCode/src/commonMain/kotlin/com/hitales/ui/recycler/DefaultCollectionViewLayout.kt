package com.hitales.ui.recycler

import com.hitales.ui.Orientation
import com.hitales.utils.EdgeInsets
import com.hitales.utils.Frame
import com.hitales.utils.Size

abstract class LayoutHelper {

    var rowHeight:Float = 0f

    var rowColumns = 1

    var headerHeight = 0f

    var footerHeight = 0f

    abstract fun getNextPageLayoutInfo(collectionView: CollectionView,layout:DefaultCollectionViewLayout,adapter:CollectionViewAdapter, currentPage: CollectionViewLayout.PageLayoutInfo?, nextPage: CollectionViewLayout.PageLayoutInfo)

    abstract fun adjustRow(row: ArrayList<LayoutAttribute>,layout: DefaultCollectionViewLayout,start: Float, end: Float, maxRowHeight: Float)

    abstract fun calculateContextSize(collectionView: CollectionView,layout: DefaultCollectionViewLayout,adapter:CollectionViewAdapter, page: CollectionViewLayout.PageLayoutInfo,contextSize: Size)

    open fun reset(){
        rowHeight = 0f
        rowColumns = 1
        headerHeight = 0f
        footerHeight = 0f

    }
}

open class DefaultCollectionViewLayout : CollectionViewLayout(){

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
    var minimumInterItemSpacing = 10f

    /**
     * 内容间距
     */
    var sectionInset:EdgeInsets? = null

    /**
     * 等间距分布一行数据
     */
    var adjustRow = false

    /**
     * 初始化预加载几页数据
     */
    var initPageSize = 5

    private val contextSize = Size()

    private val verticalLayoutHelper = VerticalLayoutHelper()

    private val horizontalLayoutHelper = HorizontalLayoutHelper()

    protected val pages = ArrayList<PageLayoutInfo>()

    override fun clear() {
        attributesPool.clear()
        pages.clear()
    }

    override fun prepareLayout() {
        verticalLayoutHelper.reset()
        horizontalLayoutHelper.reset()
        pages.forEach {
            it.attributes.forEach { cacheAttribute(it) }
        }
        pages.clear()
        getNextPage()
    }

    override fun onScroll(scrollX: Float, scrollY: Float) {
        collectionView?.apply {
            if(!pages.isEmpty()){
                val last = pages.last()
                val orientation = orientation
                when (orientation){
                    Orientation.VERTICAL -> {
                        val offsetY = scrollY + frame.height
                        val bottom = last.frame.getBottom()
                        if(offsetY >= bottom - frame.height){
                            getNextPage()
                        }
                    }
                    Orientation.HORIZONTAL ->{
                        val offsetX = scrollX + frame.width
                        val right = last.frame.getRight()
                        if(offsetX >= right - frame.height){
                            getNextPage()
                        }
                    }
                }


            }
        }

    }

    open fun getNextPage(){
        collectionView?.apply {
            var currentPage:PageLayoutInfo? = null
            var page = 0
            if(!pages.isEmpty()){
                currentPage = pages.last()
                if(currentPage.isEmpty()){
                    return
                }
                page = currentPage.page
            }
            while (true){
                val nextPage = PageLayoutInfo(this@DefaultCollectionViewLayout)
                nextPage.page = page
                getNextPageLayoutInfo(this,currentPage,nextPage)
                if(nextPage.isEmpty()){
                    break
                }
                page++
                pages.add(nextPage)
                currentPage = nextPage
                if(nextPage.frame.contains(scrollX+frame.width,scrollY+frame.height)){
                    break
                }
            }
            if(!pages.isEmpty()){
                val lastPage = pages.last()
                val orientation = orientation
                when (orientation){
                    Orientation.VERTICAL -> verticalLayoutHelper.calculateContextSize(this,this@DefaultCollectionViewLayout,adapter!!,lastPage,contextSize)
                    Orientation.HORIZONTAL -> horizontalLayoutHelper.calculateContextSize(this,this@DefaultCollectionViewLayout,adapter!!,lastPage,contextSize)
                }
            }
        }
    }

    fun getNextPageLayoutInfo(collectionView: CollectionView,current:PageLayoutInfo?,next:PageLayoutInfo){
        val adapter = collectionView.adapter
        if(adapter == null) {
            return
        }
        val orientation = collectionView.orientation
        when (orientation){
            Orientation.VERTICAL -> verticalLayoutHelper.getNextPageLayoutInfo(collectionView,this,adapter,current, next)
            Orientation.HORIZONTAL -> horizontalLayoutHelper.getNextPageLayoutInfo(collectionView,this,adapter,current, next)
        }
    }

    override fun getLayoutAttributesInFrame(frame: Frame): ArrayList<LayoutAttribute> {
        val attributes = ArrayList<LayoutAttribute>()
        var intersect = false
        for (i in 0 until pages.size){
            val page = pages[i]
            if(frame.intersect(page.frame)){
                intersect = true
                attributes.addAll(page.attributes.filter { frame.intersect(it.frame) })
            }else if(intersect){
                break
            }
        }
        return attributes
    }

    override fun getContentSize(size: Size) {
        size.set(contextSize)
    }

}