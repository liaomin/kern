package com.hitales.ui.recycler

import com.hitales.ui.Orientation
import com.hitales.utils.Frame
import com.hitales.utils.LinkedList
import com.hitales.utils.Size
import com.hitales.utils.WeakReference


abstract class CollectionViewLayout {

    open class PageLayoutInfo(val layoutRef:WeakReference<CollectionViewLayout>){

        var page:Int = 0

        val frame = Frame()

        val attributes = ArrayList<LayoutAttribute>()

        var isLayout:Boolean = false

        var isEnd = false

        open fun reset(){
            page = 0
            layoutRef.get()?.apply {
                attributes.forEach {
                    cacheAttribute(it)
                }
            }
            attributes.clear()
            frame.reset()
            isEnd = false
            isLayout = false
        }

        fun isEmpty():Boolean{
            return attributes.isEmpty()
        }

    }

    var collectionViewRef: WeakReference<CollectionView>?= null

    protected val attributesPool = LinkedList<LayoutAttribute>()

    abstract fun prepareLayout()

    abstract fun getPageLayoutInfo(lastPage:PageLayoutInfo,currentPage: PageLayoutInfo,nextPage:PageLayoutInfo)

    abstract fun getContentSize(size: Size)

    abstract fun onScroll(scrollX:Float,scrollY:Float)

    abstract fun adjustVerticalRow(row: ArrayList<LayoutAttribute>,offsetX:Float,offsetY:Float,maxRowHeight:Float)

    abstract fun adjustHorizontalRow(row: ArrayList<LayoutAttribute>,offsetX:Float,offsetY:Float,maxRowWidth:Float)


    open fun cacheAttribute(attribute: LayoutAttribute){
        attribute.reset()
        attributesPool.append(attribute)
    }

    open fun getCacheAttribute():LayoutAttribute{
        var cache = attributesPool.pop()
        if(cache == null){
            cache = LayoutAttribute()
        }
        return cache
    }
}