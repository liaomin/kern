package com.hitales.ui.recycler

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

        open fun reset(){
            page = 0
            layoutRef.get()?.apply {
                attributes.forEach {
                    cacheAttribute(it)
                }
            }
            attributes.clear()
            frame.reset()
            isLayout = false
        }

        fun isEmpty():Boolean{
            return attributes.isEmpty()
        }

    }

    val maxAttributesPoolSize = 1000

    var collectionViewRef: WeakReference<CollectionView>?= null

    protected val attributesPool = LinkedList<LayoutAttribute>()

    abstract fun prepareLayout()

    abstract fun getLayoutAttributesInFrame(frame: Frame):ArrayList<LayoutAttribute>

    abstract fun getContentSize(size: Size)

    abstract fun onScroll(scrollX:Float,scrollY:Float)

    open fun clear(){
        attributesPool.clear()
    }

    open fun cacheAttribute(attribute: LayoutAttribute){
        if(attributesPool.count() < maxAttributesPoolSize){
            attribute.reset()
            attributesPool.append(attribute)
        }
    }

    open fun getCacheAttribute():LayoutAttribute{
        var cache = attributesPool.pop()
        if(cache == null){
            cache = LayoutAttribute()
        }
        return cache
    }
}